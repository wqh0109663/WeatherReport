package weatherforecast.wqh.example.com.weatherreport;

/**专门用来存放天气图片的一个Javabean
 * Created by jkx5 on 2017/12/21.
 */

public class PictureJavaBean {
    private String weather_icon;

    @Override
    public String toString() {
        return "PictureJavaBean{" +
                "weather_icon='" + weather_icon + '\'' +
                ", weather_icon1='" + weather_icon1 + '\'' +
                '}';
    }

    public String getWeather_icon1() {
        return weather_icon1;
    }

    public void setWeather_icon1(String weather_icon1) {
        this.weather_icon1 = weather_icon1;
    }

    private String weather_icon1;

    public String getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(String weather_icon) {
        this.weather_icon = weather_icon;
    }
}
