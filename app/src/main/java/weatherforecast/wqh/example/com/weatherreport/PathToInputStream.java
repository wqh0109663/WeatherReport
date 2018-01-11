package weatherforecast.wqh.example.com.weatherreport;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 我将根据path得到一个流抽取取来为一个工具类
 *
 * Created by wqh on 2017/12/21.
 */

public class PathToInputStream {
    private static InputStream stream;

    /*
    * parameter path是路径
    * return InputStream
    *
    * */
    public static InputStream urlToInputStream(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            int code = urlConnection.getResponseCode();
            if (code == 200) {
                stream = urlConnection.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stream;
    }
}
