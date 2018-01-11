package weatherforecast.wqh.example.com.weatherreport;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

/**百度API中的继承BDAbstractLocationListener的类
 * Created by wqh on 2017/12/22.
 */

public class MyLocationListener extends BDAbstractLocationListener {

    @Override
    public void onReceiveLocation(BDLocation location) {

        int locType = location.getLocType();
        System.out.println("locType::::" + locType);
        AllStaic.Addr = location.getAddrStr();
        AllStaic.CITY = location.getCity();
        AllStaic.Country = location.getCountry();
        AllStaic.District = location.getDistrict();
        System.out.println("Addr:::" + AllStaic.Addr);
        System.out.println("CITY:::" + AllStaic.CITY);
        System.out.println("Country:::" + AllStaic.Country);

        System.out.println("District:::" + AllStaic.District);

    }


}