package com.example.gaodemap;

import android.content.Context;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;

/**
 * Created by libo on 2018/2/2.
 */

public class LocationUtil implements AMapLocationListener {
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption clientOption;
    private ILocationCallBack callBack;

    public void startLocate(Context context){
        aMapLocationClient = new AMapLocationClient(context);

        //设置监听回调
        aMapLocationClient.setLocationListener(this);

        //初始化定位参数
        clientOption = new AMapLocationClientOption();
        clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        clientOption.setNeedAddress(true);
        clientOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        clientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        clientOption.setMockEnable(false);
        //设置定位间隔
        clientOption.setInterval(2000);
        aMapLocationClient.setLocationOption(clientOption);

        aMapLocationClient.startLocation();
    }

    //完成定位回调
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation != null){
            if(aMapLocation.getErrorCode() == 0){
                //定位成功完成回调
                String country = aMapLocation.getCountry();
                String province = aMapLocation.getProvince();
                String city = aMapLocation.getCity();
                String district = aMapLocation.getDistrict();
                String street = aMapLocation.getStreet();
                double lat = aMapLocation.getLatitude();
                double lgt = aMapLocation.getLongitude();

                callBack.callBack(country + province + city + district + street,lat,lgt,aMapLocation);
            }else{
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 自定义图标
     * @return
     */
    public MarkerOptions getMarkerOption(String str,double lat,double lgt){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.position));
        markerOptions.position(new LatLng(lat,lgt));
        markerOptions.title(str);
        markerOptions.snippet("纬度:" + lat + "   经度:" + lgt);
        markerOptions.period(100);

        return markerOptions;
    }

    public interface ILocationCallBack{
        void callBack(String str,double lat,double lgt,AMapLocation aMapLocation);
    }

    public void setLocationCallBack(ILocationCallBack callBack){
        this.callBack = callBack;
    }

}
