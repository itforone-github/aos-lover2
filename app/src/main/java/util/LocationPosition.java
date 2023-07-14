package util;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Locale;

/**
 * Created by 투덜이2 on 2016-10-24.
 */

public class LocationPosition {
    public static double lat=0, lng=0;
    public static double startPointLat=0,startPointLng=0,way1PointLat=0,way1PointLng=0,way2PointLat=0,way2PointLng=0,desPointLat=0,desPointLng=0;
    public static String startPlace="",way1Place="",way2Place="",desPlace="";
    static public Activity act;
    public LocationPosition(Activity act) {
        this.act=act;
    }
    public static void  setPosition(Activity act){
        final LocationManager lm = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);
        boolean gps= Common.getPref(act.getApplicationContext(),"gps",false);//KimbiseoPreferences.get(getApplicationContext(),"push",false);
        try {
            if(gps) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
            }
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0,mLocationListener);

        }catch (SecurityException e){
        }
    }
    public double getLat(){
        return lat;
    }
    public double getLng(){
        return lng;
    }
     public static LocationListener mLocationListener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(location.getLongitude()>0&&location.getLatitude()>0) {
                Common.savePref(act.getApplicationContext(), "lng", location.getLongitude() + "");
                Common.savePref(act.getApplicationContext(), "lat", location.getLatitude() + "");
            }
            lng=location.getLongitude();
            lat=location.getLatitude();
            Log.d("위치1",lat+"");
            Log.d("위치2",lng+"");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    public static String getAddress(double lat, double lng){
        String address = "";

        //위치정보를 활용하기 위한 구글 API 객체
        Geocoder geocoder = new Geocoder(act.getApplicationContext(), Locale.getDefault());

        //주소 목록을 담기 위한 HashMap
        List<Address> list = null;

        try{
            list = geocoder.getFromLocation(lat, lng, 1);
        } catch(Exception e){
            e.printStackTrace();
        }

        if(list == null){
            Log.e("getAddress", "주소 데이터 얻기 실패");
            return null;
        }

        if(list.size() > 0){
            Address addr = list.get(0);
            address = addr.getAddressLine(0).toString().replace("대한민국 ","");
        }

        return address;

    }
}
