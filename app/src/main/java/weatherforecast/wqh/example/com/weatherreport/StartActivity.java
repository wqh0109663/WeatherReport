package weatherforecast.wqh.example.com.weatherreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**app启动的页面
 * Created by wqh on 2017/12/30.
 */

public class StartActivity extends Activity {
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startacivity);
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        //option.setAddrType("all");
        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.start();
        waitShow();
    }

    private void waitShow() {
        new Thread(){
            @Override
            public void run() {
                try {

                    sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setClass(StartActivity.this, TestDemo.class);
                startActivity(intent);
            }
        }.start();

    }
}
