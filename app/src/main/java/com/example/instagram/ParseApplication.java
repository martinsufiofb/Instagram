package com.example.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("7MAVzDFC0piA81UI9EdqKMQVxYhKhTzZIk0MRcNT")
                .clientKey("xkaGzLkAsY1cstaRG1P6pTYsPkGCe5Z6jG8fX02k")
                .server("https://parseapi.back4app.com")
                .build());
    }
}
