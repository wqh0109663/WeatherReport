package weatherforecast.wqh.example.com.weatherreport;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**解析七天天气信息的一个工具类
 * Created by wqh on 2017/12/19.
 */

public class WeatherXMLParser {

    public   static List<CityDemo> parserXml(InputStream inputStream){
        List<CityDemo> cityDemoList = null ;

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream,"UTF-8");
            int type = parser.getEventType();
            CityDemo cityDemo = null ;
            while (type != XmlPullParser.END_DOCUMENT)
            {

                switch (type)
                {
                    case XmlPullParser.START_TAG:
                        if ("sk".equals(parser.getName()))
                        {
                            cityDemoList = new ArrayList<CityDemo>();
                        }else if ("temp".equals(parser.getName()))
                        {
                            cityDemo = new CityDemo();
                            String temp = parser.nextText();
                            cityDemo.setTemp(temp);
                        }else if ("wind_direction".equals(parser.getName()))
                        {
                            String wind_direction = parser.nextText();
                            cityDemo.setWind_direction(wind_direction);
                        }
                        else if ("wind_strength".equals(parser.getName()))
                        {
                            String wind_strength = parser.nextText();
                            cityDemo.setWind_strength(wind_strength);
                        }
                        else if ("humidity".equals(parser.getName()))
                        {
                            String humidity = parser.nextText();
                            cityDemo.setHumidity(humidity);
                        }else if ("temperature".equals(parser.getName()))
                        {
                           /*
                            * 因为今天的天气和以后几天的天气的节点是不一样的
                            * 所以 需要找到相同的开始节点来进行判断，让后几天的信息也可以加到集合中
                            */
                            if (cityDemo == null)
                            {
                                cityDemo = new CityDemo();
                            }
                            String temperature = parser.nextText();
                            cityDemo.setTemperature(temperature);
                        }else if ("weather".equals(parser.getName()))
                        {
                            String weather = parser.nextText();
                            cityDemo.setWeather(weather);
                        }else if ("wind".equals(parser.getName()))
                        {
                            String wind = parser.nextText();
                            cityDemo.setWind(wind);
                        }else if ("week".equals(parser.getName()))
                        {
                            String week = parser.nextText();
                            cityDemo.setWeek(week);
                        } else if ("city".equals(parser.getName()))
                        {
                            String city = parser.nextText();
                            cityDemo.setCity(city);
                        }
                        else if ("date_y".equals(parser.getName()))
                        {
                            String date_y = parser.nextText();
                            cityDemo.setDate_y(date_y);
                        }else if ("dressing_index".equals(parser.getName()))
                        {
                            String dressing_index = parser.nextText();
                            cityDemo.setDressing_index(dressing_index);
                        }else if ("dressing_advice".equals(parser.getName()))
                        {
                            String dressing_advice = parser.nextText();
                            cityDemo.setDressing_advice(dressing_advice);
                        }else if ("uv_index".equals(parser.getName()))
                        {
                            String uv_index = parser.nextText();
                            cityDemo.setUv_index(uv_index);
                        }else if ("wash_index".equals(parser.getName()))
                        {
                            String wash_index = parser.nextText();
                            cityDemo.setWash_index(wash_index);
                        }else if ("travel_index".equals(parser.getName()))
                        {
                            String travel_index = parser.nextText();
                            cityDemo.setTravel_index(travel_index);
                        }else if ("exercise_index".equals(parser.getName()))
                        {
                            String exercise_index = parser.nextText();
                            cityDemo.setExercise_index(exercise_index);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                            if ("date".equals(parser.getName()))
                            {
                                cityDemoList.add(cityDemo);
                                cityDemo = null ;
                            }
                        break;
                }
                 type = parser.next();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return cityDemoList;
    }

}
