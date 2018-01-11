package weatherforecast.wqh.example.com.weatherreport;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**解析流里面的图片的工具类
 * Created by wqh on 2017/12/21.
 */

public class InToListPicture {
    public static List<PictureJavaBean> InXMLToList(InputStream inputStream){
        List<PictureJavaBean> pictureJavaBeansList = null ;

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream,"UTF-8");
            int type = parser.getEventType();
            PictureJavaBean pictureJavaBean = null ;
            while (type != XmlPullParser.END_DOCUMENT)
            {

                switch (type)
                {
                    case XmlPullParser.START_TAG:
                        if ("success".equals(parser.getName()))
                        {
                            pictureJavaBeansList = new ArrayList<PictureJavaBean>();
                        }else if ("weather_icon".equals(parser.getName()))
                        {
                            pictureJavaBean = new PictureJavaBean();
                            String weather_icon = parser.nextText();
                            pictureJavaBean.setWeather_icon(weather_icon);
                        } else if ("weather_icon1".equals(parser.getName()))
                        {
                            String weather_icon1 = parser.nextText();
                            pictureJavaBean.setWeather_icon1(weather_icon1);
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        if ("winpid".equals(parser.getName()))
                        {
                            pictureJavaBeansList.add(pictureJavaBean);
                            pictureJavaBean = null ;
                        }
                        break;
                }
                type = parser.next();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return pictureJavaBeansList;
    }
}
