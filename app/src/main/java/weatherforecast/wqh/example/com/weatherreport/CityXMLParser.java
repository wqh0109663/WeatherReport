package weatherforecast.wqh.example.com.weatherreport;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**专门用来解析全国城市的工具类
 * Created by wqh on 2017/12/26.
 */

public class CityXMLParser {
    public static List<CityJavaBean> InXMLToCityList(InputStream inputStream) {
        List<CityJavaBean> cityJavaBeansList = null;

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, "UTF-8");
            int type = parser.getEventType();
            CityJavaBean cityJavaBean = null;
            while (type != XmlPullParser.END_DOCUMENT) {

                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("result".equals(parser.getName())) {
                            cityJavaBeansList = new ArrayList<CityJavaBean>();
                        } else if ("id".equals(parser.getName())) {
                            cityJavaBean = new CityJavaBean();
                            String id = parser.nextText();
                            cityJavaBean.setId(id);
                        } else if ("province".equals(parser.getName())) {
                            String province = parser.nextText();
                            cityJavaBean.setProvince(province);
                        } else if ("city".equals(parser.getName())) {
                            String city = parser.nextText();
                            cityJavaBean.setCity(city);
                        } else if ("district".equals(parser.getName())) {
                            String district = parser.nextText();
                            cityJavaBean.setDistrict(district);
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        if ("item".equals(parser.getName())) {
                            cityJavaBeansList.add(cityJavaBean);
                            cityJavaBean = null;
                        }
                        break;
                }
                type = parser.next();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return cityJavaBeansList;
    }
}
