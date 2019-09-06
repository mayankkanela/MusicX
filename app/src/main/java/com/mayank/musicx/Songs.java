package com.mayank.musicx;

import android.net.Uri;

public class Songs {
    String name;
    Uri uri;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }



    public Songs(String name, Uri uri) {
        this.name = name;
        this.uri = uri;
    }
}
