package com.mayank.musicx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   private ArrayList<Songs> songs = new ArrayList<>();
   private RecyclerView recyclerView;
   private Context context;
   private ContentResolver contentResolver;
   private Cursor cursor;
   private Uri uri;
   private RecyclerViewAdapter recyclerViewAdapter;

    public static final int REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListeners();


    }

    private void initListeners() {

    }

    private void initData() {
        context = getApplicationContext();
        contentResolver = context.getContentResolver();
        recyclerViewAdapter=new RecyclerViewAdapter(songs,MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(recyclerViewAdapter);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Permission Required");
                builder.setMessage("Require permission to read external storage to display all songs");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

                    }
                });
                builder.setNeutralButton("CANCEL", null);
                builder.show();


            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);


            }

        } else {
            getMediaFiles();



        }

    }

    private void initView() {
        recyclerView = findViewById(R.id.rvSongs);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMediaFiles();
                }else
                        if(grantResults.length!=0&&grantResults[0]==PackageManager.PERMISSION_DENIED){

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Permission Required");
                    builder.setMessage("Require permission to read external storage to display all songs");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

                        }
                    });
                    builder.setNeutralButton("CANCEL", null);
                    builder.show();

                }

            }

        }


    }

    private void getMediaFiles() {


        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            Toast.makeText(this, "Something Went Wrong Please Check Persmission", Toast.LENGTH_SHORT).show();
        } else if (!cursor.moveToFirst()) {
            Toast.makeText(this, "No music in external card", Toast.LENGTH_SHORT).show();
        } else {
            int Title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

            do {

                String sTitle = cursor.getString(Title);
                songs.add(new Songs(sTitle, Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)))));

                recyclerViewAdapter.notifyDataSetChanged();
            } while (cursor.moveToNext());
        }

            uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
            cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor == null) {
                Toast.makeText(this, "Something Went Wrong Please Check Persmission", Toast.LENGTH_SHORT).show();
            } else if (!cursor.moveToFirst()) {
                Toast.makeText(this, "No music in external card", Toast.LENGTH_SHORT).show();
            } else {
                int Title2 = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

                do {

                    String sTitle = cursor.getString(Title2);
                    songs.add(new Songs(sTitle, Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)))));
                        recyclerViewAdapter.notifyDataSetChanged();
                } while (cursor.moveToNext());


            }



    }
}
