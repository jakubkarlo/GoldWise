package jakubkarlo.com.goldwise;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;


/**
 * Created by Jakub on 06.11.2017.
 */



public class StarterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Database.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("c290ee361451bbb36d3c892bb493a3a913cfce7c")
                .clientKey("b8372ca5a1371b486b69681b59ba1a7284191a75")
                .server("http://18.194.221.72:80/parse/")
                .build()
        );


        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);




    }
}