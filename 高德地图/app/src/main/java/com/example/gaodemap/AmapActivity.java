package com.example.gaodemap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;

public class AmapActivity extends AppCompatActivity implements LocationSource {
    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private LocationUtil locationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        if(aMap == null){
            aMap = mapView.getMap();
        }

        setLocationCallBack();

        //设置定位监听
        aMap.setLocationSource(this);
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        //显示定位层并可触发，默认false
        aMap.setMyLocationEnabled(true);
    }

    private void setLocationCallBack(){
        locationUtil = new LocationUtil();
        locationUtil.setLocationCallBack(new LocationUtil.ILocationCallBack() {
            @Override
            public void callBack(String str,double lat,double lgt,AMapLocation aMapLocation) {

                //根据获取的经纬度，将地图移动到定位位置
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat,lgt)));
                mListener.onLocationChanged(aMapLocation);
                //添加定位图标
                aMap.addMarker(locationUtil.getMarkerOption(str,lat,lgt));
            }
        });
    }

    //定位激活回调
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;

        locationUtil.startLocate(getApplicationContext());
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新绘制加载地图
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
