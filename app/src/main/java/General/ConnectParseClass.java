package General;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

/**
 *  Copyright (C) 2015 KeyBellSoft. All rights reserved.
 *  Created by Fabian Rosales  on 25/10/2015.
 *  www.frosquivel.com
 *
 *
 *  Use for example
 *  https://github.com/rey5137/material
 *  www.parse.com
 */
public class ConnectParseClass extends Application {

    private static final String sApplicationId="8laFx82XJYJZ4bnOGNyQDYcEeisNcmqxrtD3vpXq";
    private static final String sClienId="JjTbaN1xB5WJhOweqip2QwXkG7jvi8wMmVkjZ0pq";

    public void onCreate(){
        super.onCreate();
    }

    public void onCreateObject(String classEntity)
    {
        ParseObject.create(classEntity);
    }

    public void onInitialParse(Context context)
    {
        Parse.initialize(context, sApplicationId, sClienId);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

}
