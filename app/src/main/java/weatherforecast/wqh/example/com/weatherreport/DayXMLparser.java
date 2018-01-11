package weatherforecast.wqh.example.com.weatherreport;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqh on 2018/1/1.
 */

public class DayXMLparser {
    public static List<DayJavaBean> InXMLToDayList(InputStream inputStream) {
        List<DayJavaBean> DayJavaBeansList = null;

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, "UTF-8");
            int type = parser.getEventType();
            DayJavaBean DayJavaBean = null;
            while (type != XmlPullParser.END_DOCUMENT) {

                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("result".equals(parser.getName())) {
                            DayJavaBeansList = new ArrayList<DayJavaBean>();
                        } else if ("lifeindex_uv_typenm".equals(parser.getName())) {
                            DayJavaBean = new DayJavaBean();
                            String lifeindex_uv_typenm = parser.nextText();
                            DayJavaBean.setLifeindex_uv_typenm(lifeindex_uv_typenm);

                        } else if ("lifeindex_uv_attr".equals(parser.getName())) {
                            String lifeindex_uv_attr = parser.nextText();
                            DayJavaBean.setLifeindex_uv_attr(lifeindex_uv_attr);

                        } else if ("lifeindex_uv_dese".equals(parser.getName())) {
                            String lifeindex_uv_dese = parser.nextText();
                            DayJavaBean.setLifeindex_uv_dese(lifeindex_uv_dese);
                        } else if ("lifeindex_gm_typenm".equals(parser.getName())) {
                            String lifeindex_gm_typenm = parser.nextText();
                            DayJavaBean.setLifeindex_gm_typenm(lifeindex_gm_typenm);
                        }
                        else if ("lifeindex_gm_attr".equals(parser.getName())) {
                            String lifeindex_gm_attr = parser.nextText();
                            DayJavaBean.setLifeindex_gm_attr(lifeindex_gm_attr);
                        }
                        else if ("lifeindex_gm_dese".equals(parser.getName())) {
                            String lifeindex_gm_dese = parser.nextText();
                            DayJavaBean.setLifeindex_gm_dese(lifeindex_gm_dese);
                        }
                        else if ("lifeindex_ct_typenm".equals(parser.getName())) {
                            String lifeindex_ct_typenm = parser.nextText();
                            DayJavaBean.setLifeindex_ct_typenm(lifeindex_ct_typenm);
                        }
                        else if ("lifeindex_ct_attr".equals(parser.getName())) {
                            String lifeindex_ct_attr = parser.nextText();
                            DayJavaBean.setLifeindex_ct_attr(lifeindex_ct_attr);
                        }
                        else if ("lifeindex_ct_dese".equals(parser.getName())) {
                            String lifeindex_ct_dese = parser.nextText();
                            DayJavaBean.setLifeindex_ct_dese(lifeindex_ct_dese);
                        }
                        else if ("lifeindex_xc_typenm".equals(parser.getName())) {
                            String lifeindex_xc_typenm = parser.nextText();
                            DayJavaBean.setLifeindex_xc_typenm(lifeindex_xc_typenm);
                        }
                        else if ("lifeindex_xc_attr".equals(parser.getName())) {
                            String lifeindex_xc_attr = parser.nextText();
                            DayJavaBean.setLifeindex_xc_attr(lifeindex_xc_attr);
                        }
                        else if ("lifeindex_xc_dese".equals(parser.getName())) {
                            String lifeindex_xc_dese = parser.nextText();
                            DayJavaBean.setLifeindex_xc_dese(lifeindex_xc_dese);
                        }
                        else if ("lifeindex_kq_typenm".equals(parser.getName())) {
                            String lifeindex_kq_typenm = parser.nextText();
                            DayJavaBean.setLifeindex_kq_typenm(lifeindex_kq_typenm);
                        }
                        else if ("lifeindex_kq_attr".equals(parser.getName())) {
                            String lifeindex_kq_attr = parser.nextText();
                            DayJavaBean.setLifeindex_kq_attr(lifeindex_kq_attr);
                        }
                        else if ("lifeindex_kq_dese".equals(parser.getName())) {
                            String lifeindex_kq_dese = parser.nextText();
                            DayJavaBean.setLifeindex_kq_dese(lifeindex_kq_dese);
                        }

                        break;

                    case XmlPullParser.END_TAG:
                        if ("item_0".equals(parser.getName())) {
                            DayJavaBeansList.add(DayJavaBean);
                            DayJavaBean = null;
                        }
                        break;
                }
                type = parser.next();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return DayJavaBeansList;
    }
}
