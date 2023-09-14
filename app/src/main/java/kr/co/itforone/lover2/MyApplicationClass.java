package kr.co.itforone.lover2;

import android.app.Application;
import android.os.Build;

import com.igaworks.v2.core.application.AbxActivityHelper;
import com.igaworks.v2.core.application.AbxActivityLifecycleCallbacks;

public class MyApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AbxActivityHelper.initializeSdk(MyApplicationClass.this, "CjZWE4yCXki5fS6v1LLF1g", "OuHP75km1UC0FY2rY2xg5g");

        if (Build.VERSION.SDK_INT >= 14) {
            registerActivityLifecycleCallbacks(new AbxActivityLifecycleCallbacks());
        }
    }
}
