package util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by 투덜이2 on 2017-07-14.
 */

public class Common {
    static final String PREF = "LOCKER";
    public static String TOKEN = "";
    public static String logout = "NO";

    /*
    public static String getMyNumber(Activity act){
        TelephonyManager manager =(TelephonyManager)act.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getLine1Number();
    }*/
     // 푸시 디바이스 아이디값 가져오기
    public static String getMyDeviceId(Activity act) {
        TelephonyManager manager = (TelephonyManager) act.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(act.getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        return manager.getDeviceId();
    }
    public static void savePref(Context context, String key, String value){
        SharedPreferences pref=context.getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public static String getPref(Context context, String key, String def){
        SharedPreferences pref=context.getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        String value;
        try {
            value = pref.getString(key, def);
        }catch(Exception e){
            value=def;
        }
        return value;
    }
    public static void savePref(Context context, String key, boolean value){
        SharedPreferences pref=context.getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    public static boolean getPref(Context context, String key, boolean def){
        SharedPreferences pref=context.getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        boolean value;
        try {
            value = pref.getBoolean(key, def);
        }catch(Exception e){
            value=def;
        }
        return value;
    }
    public static File ScreenShot(View view){
        view.setDrawingCacheEnabled(true);// 화면 캐쉬 사용
        Bitmap screenBitmap = view.getDrawingCache();

        String filename="screenshot.png";
        File file = new File(Environment.getExternalStorageDirectory()+"/Pictures", filename);
        FileOutputStream os=null;
        try {
            os=new FileOutputStream(file);
            screenBitmap.compress(Bitmap.CompressFormat.PNG,90,os);
            os.close();

        }catch (Exception e){
            e.printStackTrace();
            Log.d("screen-error1",e.toString());
        }
        view.setDrawingCacheEnabled(false);
        return file;
    }
    public static void sharedPhoto(Activity activity){
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            File file = new File(Environment.getExternalStorageDirectory() + "/Pictures", "screenshot.png");
            Uri uri= Uri.fromFile(file);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM,uri);
            activity.startActivity(Intent.createChooser(intent,"Choose"));
        }catch (Exception e){
            Log.d("screen-error",e.toString());
        }

    }

}
