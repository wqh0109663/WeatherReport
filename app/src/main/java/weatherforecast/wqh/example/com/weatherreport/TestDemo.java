package weatherforecast.wqh.example.com.weatherreport;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;


/**
 * 主页面 显示相关的信息的activity类
 * Created by wqh on 2017/12/19.
 */

public class TestDemo extends Activity {

    private TextView tv_weiZi;
    private TextView tv_tiAnqi;
    private TextView tv_tem;
    private TextView tv_weekToday, max_tem, min_tem, feNgDir;
    private TextView feNgDaXiAo;
    private TextView shiDu;
    private TextView mingTiAn;
    private TextView DierGeWenDu;
    private TextView mingTiAn1;
    private TextView mingTiAn2;
    private TextView mingTiAn3;
    private TextView mingTiAn4;
    private TextView DierGeWenDu1;
    private TextView DierGeWenDu2;
    private TextView DierGeWenDu3;
    private TextView DierGeWenDu4;
    private ImageView DiEr, DiEr1, DiEr2, DiEr3, DiEr4;
    private ImageView DiEr5;
    private ImageButton menu_top_info;
    private String cityName = null;
    private String city = null;
    private MyDB myDB = null;
    private TextView mingTiAn5;
    private TextView DierGeWenDu5;
    private TextView zi, zi_desc, gan, gan_desc, chuan, chuan_desc, xi, xi_desc, kong, kong_desc;
    private static String NEWTABLE = "my_table";
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        zi = (TextView) findViewById(R.id.zi);
        zi_desc = (TextView) findViewById(R.id.zi_desc);
        gan = (TextView) findViewById(R.id.gan);
        gan_desc = (TextView) findViewById(R.id.gan_desc);
        chuan = (TextView) findViewById(R.id.chuan);
        chuan_desc = (TextView) findViewById(R.id.chuan_desc);
        xi = (TextView) findViewById(R.id.xi);
        xi_desc = (TextView) findViewById(R.id.xi_desc);
        kong = (TextView) findViewById(R.id.kong);
        kong_desc = (TextView) findViewById(R.id.kong_desc);

        tv_weiZi = (TextView) findViewById(R.id.tv_weiZi);
        tv_tiAnqi = (TextView) findViewById(R.id.tv_tiAnqi);
        tv_tem = (TextView) findViewById(R.id.tv_tem);
        tv_weekToday = (TextView) findViewById(R.id.tv_weekToday);
        max_tem = (TextView) findViewById(R.id.max_tem);
        min_tem = (TextView) findViewById(R.id.min_tem);
        feNgDir = (TextView) findViewById(R.id.feNgDir);
        feNgDaXiAo = (TextView) findViewById(R.id.feNgDaXiAo);
        shiDu = (TextView) findViewById(R.id.shiDu);
        mingTiAn = (TextView) findViewById(R.id.mingTiAn);
        mingTiAn1 = (TextView) findViewById(R.id.mingTiAn1);
        mingTiAn2 = (TextView) findViewById(R.id.mingTiAn2);
        mingTiAn3 = (TextView) findViewById(R.id.mingTiAn3);
        mingTiAn4 = (TextView) findViewById(R.id.mingTiAn4);
        mingTiAn5 = (TextView) findViewById(R.id.mingTiAn5);
        DierGeWenDu = (TextView) findViewById(R.id.DierGeWenDu);
        DierGeWenDu1 = (TextView) findViewById(R.id.DierGeWenDu1);
        DierGeWenDu2 = (TextView) findViewById(R.id.DierGeWenDu2);
        DierGeWenDu3 = (TextView) findViewById(R.id.DierGeWenDu3);
        DierGeWenDu4 = (TextView) findViewById(R.id.DierGeWenDu4);
        DierGeWenDu5 = (TextView) findViewById(R.id.DierGeWenDu5);
        DiEr = (ImageView) findViewById(R.id.DiEr);
        DiEr1 = (ImageView) findViewById(R.id.DiEr1);
        DiEr2 = (ImageView) findViewById(R.id.DiEr2);
        DiEr3 = (ImageView) findViewById(R.id.DiEr3);
        DiEr4 = (ImageView) findViewById(R.id.DiEr4);
        DiEr5 = (ImageView) findViewById(R.id.DiEr5);

        menu_top_info = (ImageButton) findViewById(R.id.menu_top_info);
        menu_top_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestDemo.this, Search.class);
                startActivity(intent);

            }
        });
        myDB = new MyDB(this, "New.db");
        sqLiteDatabase = myDB.getReadableDatabase();
        if (!(myDB.isTableExits(NEWTABLE))) {
            String createTableSql = "CREATE TABLE IF NOT EXISTS " + NEWTABLE +
                    " ( cid int , city6 VARCHAR(45) primary key ) ";
            boolean b = myDB.creatTable(createTableSql);
            System.out.println("b::::::" + b);
        }


        if (AllStaic.CITY.endsWith("市")) {
            AllStaic.QUSHI = AllStaic.CITY.substring(0, AllStaic.CITY.length() - 1);
        }
        //将市字去掉后判断一下数据库中是否含有这条数据，如果没有就加
        String t = "SELECT * FROM  " + NEWTABLE + " where city6 =  " + AllStaic.QUSHI;
        Cursor cursor3 = myDB.find(t, null);
        if (cursor3 == null) {
            //将定位到的数据存到数据库2的表中
            ContentValues contentValues1 = new ContentValues();
            contentValues1.put("cid", 0);
            contentValues1.put("city6", AllStaic.QUSHI);
            myDB.save(NEWTABLE, contentValues1);
        }

        if (!(AllStaic.StaicBoolean.equals("false"))) {
            Bundle bundle = this.getIntent().getExtras();
            city = bundle.getString("Citypath");
            //定义一个i 和 anint 实现让cid的值不断加一
            int i = 1;
            int anInt = 1;
            String sqlFind = "SELECT * FROM  " + NEWTABLE;
            Cursor cursor = myDB.find(sqlFind, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                anInt = cursor.getInt(0);
                if (anInt >= i) {
                    i = anInt + 1;
                }
            }
            try {
                ContentValues contentValues = new ContentValues();
                //ContentValues的put方法 第一个参数是表里面的字段 第二个是你要传进去的值
                contentValues.put("cid", i);
                contentValues.put("city6", city);
                myDB.save(NEWTABLE, contentValues);

                System.out.println("跳转测试的全局变量：：：" + city + "添加成功");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        initListDemo();
        testMap();
        truetoFalse();

    }

    private void truetoFalse() {
        //等待前面两个线程执行完毕 我用了一个最蠢的办法，也是最不值得推荐的
        // 将AllStaic.StaicBoolean的值再设置为flase
        new Thread() {
            @Override
            public void run() {
                if (AllStaic.StaicBoolean.equals("true")) {

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AllStaic.StaicBoolean = "false";
                }
            }
        }.start();
    }


    private void testMap() {

        new Thread(new Runnable() {
            private InputStream stream = null;

            @Override
            public void run() {
                if (!(AllStaic.StaicBoolean.equals("false"))) {
                    cityName = city;
                    if (cityName.endsWith("市")) {
                        cityName = cityName.substring(0, cityName.length() - 1);
                    }


                } else if (AllStaic.MAXCITY != null && AllStaic.MAXCITY.length() > 0) {
                    cityName = AllStaic.MAXCITY;
                } else {
                    if (AllStaic.CITY != null) {
                        cityName = AllStaic.CITY;

                        if (cityName.endsWith("市")) {
                            cityName = cityName.substring(0, cityName.length() - 1);
                        }
                        System.out.println("substring:::::" + cityName);
                    } else {
                        cityName = "武汉";
                        System.out.println("第一个地方执行的是else");
                    }
                }

                //获取生活指数的逻辑
                String dayPath = "http://api.k780.com/?app=weather.lifeindex&weaid=" + cityName +
                        "&appkey=30669&sign=0cc218551d4177bcbada6b43c7d48be7&format=xml";
                InputStream inputStream6 = PathToInputStream.urlToInputStream(dayPath);
                final List<DayJavaBean> dayJavaBeanList = DayXMLparser.InXMLToDayList(inputStream6);
                System.out.println("dayJavaBeanList:::::::" + dayJavaBeanList.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //更新ui 更新的是生活指数部分
                        zi.setText(dayJavaBeanList.get(0).getLifeindex_uv_attr());
                        zi_desc.setText(dayJavaBeanList.get(0).getLifeindex_uv_dese());
                        gan.setText(dayJavaBeanList.get(0).getLifeindex_gm_attr());
                        gan_desc.setText(dayJavaBeanList.get(0).getLifeindex_gm_dese());
                        chuan.setText(dayJavaBeanList.get(0).getLifeindex_ct_attr());
                        chuan_desc.setText(dayJavaBeanList.get(0).getLifeindex_ct_dese());
                        xi.setText(dayJavaBeanList.get(0).getLifeindex_xc_attr());
                        xi_desc.setText(dayJavaBeanList.get(0).getLifeindex_xc_dese());
                        kong.setText(dayJavaBeanList.get(0).getLifeindex_kq_attr());
                        kong_desc.setText(dayJavaBeanList.get(0).getLifeindex_kq_dese());

                    }
                });

                //设置天气图片的逻辑
                String path1 = "http://api.k780.com/?app=weather.future&weaid=" + cityName
                        + "&appkey=30669&sign=0cc218551d4177bcbada6b43c7d48be7&format=xml";
                stream = PathToInputStream.urlToInputStream(path1);
                List<PictureJavaBean> pictureJavaBeans = InToListPicture.InXMLToList(stream);

                if (pictureJavaBeans != null && pictureJavaBeans.size() > 1) {

                    String picturePath = pictureJavaBeans.get(1).getWeather_icon();
                    InputStream inputStream = PathToInputStream.urlToInputStream(picturePath);
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    String picturePath1 = pictureJavaBeans.get(2).getWeather_icon();
                    InputStream inputStream1 = PathToInputStream.urlToInputStream(picturePath1);
                    final Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream1);

                    String picturePath2 = pictureJavaBeans.get(3).getWeather_icon();
                    InputStream inputStream2 = PathToInputStream.urlToInputStream(picturePath2);
                    final Bitmap bitmap2 = BitmapFactory.decodeStream(inputStream2);

                    String picturePath3 = pictureJavaBeans.get(4).getWeather_icon();
                    InputStream inputStream3 = PathToInputStream.urlToInputStream(picturePath3);
                    final Bitmap bitmap3 = BitmapFactory.decodeStream(inputStream3);

                    String picturePath4 = pictureJavaBeans.get(5).getWeather_icon();
                    InputStream inputStream4 = PathToInputStream.urlToInputStream(picturePath4);
                    final Bitmap bitmap4 = BitmapFactory.decodeStream(inputStream4);
                   if (pictureJavaBeans.size() >= 7){

                    }
                   /* String picturePath5 = pictureJavaBeans.get(6).getWeather_icon();
                    InputStream inputStream5 = PathToInputStream.urlToInputStream(picturePath5);*/
                    //final Bitmap bitmap5 = BitmapFactory.decodeStream(inputStream5);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DiEr.setImageBitmap(bitmap);
                            DiEr1.setImageBitmap(bitmap1);
                            DiEr2.setImageBitmap(bitmap2);
                            DiEr3.setImageBitmap(bitmap3);
                            DiEr4.setImageBitmap(bitmap4);
                           // DiEr5.setImageBitmap(bitmap5);
                        }
                    });


                    if (stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();


    }

    //这个界面就不需要后退键
    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.add(1, -1, 0, AllStaic.CITY);
        String sqlFind = "SELECT * FROM  " + NEWTABLE;
        Cursor cursor = myDB.find(sqlFind, null);
        int i = 0;
        while (cursor.moveToNext()) {
            String string = cursor.getString(1);
            menu.add(1, i, 0, string);
            i++;
        }
        cursor.close();

        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        //首先将存了选择的城市的表的数据拿出来与点击的itemID进行比对
        String sqlFind = "SELECT * FROM  " + NEWTABLE;
        Cursor cursor = myDB.find(sqlFind, null);
        if (cursor.getCount() > 0) {//没办法能力有限 只能从菜单实现不同城市之间进行来回的切换
            while (cursor.moveToNext()) {
                int cursorInt = cursor.getInt(0);
                if (cursorInt == item.getItemId()) {
                    AllStaic.XXX = cursorInt;
                    break;
                }
            }
        }
        //将比对来的数据进行查询出相应的城市
        String sqlFind1 = "SELECT  *  FROM  " + NEWTABLE + " where cid =  " + AllStaic.XXX;
        Cursor cursor1 = myDB.find(sqlFind1, null);
        if (cursor1.getCount() > 0) {
            cursor1.moveToNext();
            AllStaic.MAXCITY = cursor1.getString(1);
        }
        Intent intent = new Intent();
        intent.setClass(this, TestDemo.class);
        startActivity(intent);

        return true;
    }

    private void initListDemo() {
        final String[] threadName = new String[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadName[0] = Thread.currentThread().getName();
                System.out.println("threadName[0]:::::" + threadName[0]);
                InputStream in = null;
                System.out.println();
                System.out.println("66666" + AllStaic.CITY);
                if (!(AllStaic.StaicBoolean.equals("false"))) {
                    cityName = city;
                } else if (AllStaic.MAXCITY != null && AllStaic.MAXCITY.length() > 0) {
                    cityName = AllStaic.MAXCITY;
                } else {
                    try {
                        if (AllStaic.CITY != null) {
                            cityName = AllStaic.CITY;
                        } else {
                            cityName = "武汉";
                        }
                        cityName = URLEncoder.encode(cityName, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                String path = "http://v.juhe.cn/weather/index?cityname=" + cityName + "&dtype=xml&format=&key=73a883c719111a73b3694ec9659f185c";

                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        in = connection.getInputStream();
                        final List<CityDemo> cityDemoList = WeatherXMLParser.parserXml(in);
                        int size = cityDemoList.size();
                        System.out.println("size:::" + size);
                        //获取平均温度的逻辑 从下面一行开始到jun值那一行
                        StringBuffer temperature = new StringBuffer();
                        temperature.append(cityDemoList.get(0).getTemperature().toString());
                        System.out.println("temperature:::" + temperature);
                        temperature = temperature.deleteCharAt(temperature.length() - 1);
                        String temperature1 = temperature.toString();
                        int indexOf = temperature1.indexOf('℃');
                        int indexOf1 = temperature1.indexOf('~');
                        temperature = temperature.deleteCharAt(indexOf);
                        String substring = temperature.substring(indexOf1);
                        //拿到的是最高温度parseInt1
                        int parseInt1 = Integer.parseInt(substring.toString());
                        final String maxTem = "" + parseInt1;
                        System.out.println("parseInt1::::" + parseInt1);
                        String substring1 = temperature.substring(0, indexOf1 - 1);
                        //拿到的是最低的温度parseInt2
                        int parseInt2 = Integer.parseInt(substring1.toString());
                        final String minTem = "" + parseInt2;
                        System.out.println("parseInt2::::" + parseInt2);
                        int jun = (parseInt1 + parseInt2) / 2;
                        final String temp = "" + jun;
                        System.out.println("temp::::" + temp);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //显示地区
                                tv_weiZi.setText(cityDemoList.get(0).getCity().toString());
                                //显示的是天气晴朗与否
                                tv_tiAnqi.setText(cityDemoList.get(0).getWeather().toString());
                                //显示的是平均的温度
                                tv_tem.setText(temp + "°");
                                //显示的是今天的是星期几
                                tv_weekToday.setText(cityDemoList.get(0).getWeek().toString());
                                //显示的是今天的最高的温度
                                max_tem.setText("↑  " + maxTem + " °");
                                //显示的是今天的最低的温度
                                min_tem.setText("↓  " + minTem + " °");
                                //显示的是风的位置
                                feNgDir.setText(cityDemoList.get(0).getWind_direction().toString());
                                //显示的是风的级别
                                feNgDaXiAo.setText(cityDemoList.get(0).getWind_strength().toString());
                                //显示的是湿度
                                shiDu.setText(cityDemoList.get(0).getHumidity().toString());
                                //下面显示的是周几到最后六天的
                                mingTiAn.setText(cityDemoList.get(1).getWeek().toString());
                                mingTiAn1.setText(cityDemoList.get(2).getWeek().toString());
                                mingTiAn2.setText(cityDemoList.get(3).getWeek().toString());
                                mingTiAn3.setText(cityDemoList.get(4).getWeek().toString());
                                mingTiAn4.setText(cityDemoList.get(5).getWeek().toString());
                                mingTiAn5.setText(cityDemoList.get(6).getWeek().toString());
                                DierGeWenDu.setText(cityDemoList.get(1).getTemperature().toString());
                                DierGeWenDu1.setText(cityDemoList.get(2).getTemperature().toString());
                                DierGeWenDu2.setText(cityDemoList.get(3).getTemperature().toString());
                                DierGeWenDu3.setText(cityDemoList.get(4).getTemperature().toString());
                                DierGeWenDu4.setText(cityDemoList.get(5).getTemperature().toString());
                                DierGeWenDu5.setText(cityDemoList.get(6).getTemperature().toString());
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }).start();

        //System.out.println("alive::::"+Thread.currentThread().getName()+":"+alive);


    }

}
