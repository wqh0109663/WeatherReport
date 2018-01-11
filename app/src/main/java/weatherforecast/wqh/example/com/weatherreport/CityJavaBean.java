package weatherforecast.wqh.example.com.weatherreport;

/**专门用来存放全国城市的一个Javabean
 * Created by wqh on 2017/12/26.
 */

public class CityJavaBean {



    private String id;//城市id
    private String province;//省份
    private String city;//城市
    private String district;//区
    private String pinYin ;//获取所有的拼音

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
