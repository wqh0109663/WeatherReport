package weatherforecast.wqh.example.com.weatherreport;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 搜索城市天气的一个activity类
 * Created by wqh on 2017/12/22.
 */

public class Search extends Activity {

    private Button bt_find;
    private EditText ed_find;
    private String Citypath;
    private ListView lv_showCity;
    private MyDB myDB = null;
    private static String TABLENAME = "myCity_table";
    private SQLiteDatabase sqLiteDatabase;
    List<CityJavaBean> list1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        myDB = new MyDB(this,"My_DB.db");

        if (!(myDB.isTableExits(TABLENAME))) {
            String createTableSql = "CREATE TABLE IF NOT EXISTS " + TABLENAME +
                    "(id VARCHAR(45) primary key ,province VARCHAR(45) ,city VARCHAR(45) " +
                    ",district VARCHAR(45) , pinYin VARCHAR(100), str VARCHAR(100)) ";
            boolean b = myDB.creatTable(createTableSql);
            System.out.println("b::::::" + b);
        }

        lv_showCity = (ListView) findViewById(R.id.lv_showCity);
        //ImageView out = (ImageView) findViewById(R.id.out);
        bt_find = (Button) findViewById(R.id.bt_find);
        ed_find = (EditText) findViewById(R.id.ed_find);
        showList();

        bt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               finish();

            }
        });
        ed_find.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strcha = s.toString();
                System.out.println("ed_find.setText(s)1111" + s.toString());
                if (strcha.length() > 0) {
                    String mohuStr = "SELECT * FROM " + TABLENAME +
                            " WHERE str LIKE '%" + strcha + "%' or pinYin Like '%" + strcha + "%'";
                    Cursor cursor1 = myDB.find(mohuStr, null);

                    if (cursor1 != null) {
                        System.out.println("cursor1 不为空 ::::");
                        list1 = new ArrayList<CityJavaBean>();
                        while (cursor1.moveToNext()) {
                            CityJavaBean cityJavaBean1 = new CityJavaBean();
                            cityJavaBean1.setId(cursor1.getString(0));
                            cityJavaBean1.setProvince(cursor1.getString(1));
                            cityJavaBean1.setCity(cursor1.getString(2));
                            cityJavaBean1.setDistrict(cursor1.getString(3));
                            list1.add(cityJavaBean1);
                        }
                    }
                    //cursor1.close();
                }

                List<CityJavaBean> cityJavaBeanList1 = null;
                if (strcha.length() > 0) {
                    cityJavaBeanList1 = list1;
                }
                final List<CityJavaBean> finalCityJavaBeans1 = cityJavaBeanList1;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        lv_showCity.setAdapter(new ShowCityAdapter(finalCityJavaBeans1, getApplicationContext()));
                        lv_showCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(Search.this, TestDemo.class);
                                Bundle bundle = new Bundle();
                                int pos = position;
                                TextView viewById = (TextView) view.findViewById(R.id.tv_item);
                                String toString = viewById.getText().toString();
                                System.out.println("pos:::" + pos);
                                System.out.println("toString:::" + toString);
                                int i = toString.indexOf('-');
                                int i1 = toString.lastIndexOf('-');
                                String trim = toString.substring(i + 3, i1 - 1).trim();
                                System.out.println("trim:::" + trim + "length:" + trim.length());
                                AllStaic.StaicBoolean = "true";
                                bundle.putString("Citypath", trim);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            }

                        });

                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }


    private void showList() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<CityJavaBean> cityJavaBeanList = null;
                List<CityJavaBean> list = null;

                String sqlFind = "SELECT * FROM  " + TABLENAME;
                Cursor cursor = myDB.find(sqlFind, null);

                if (cursor != null && cursor.getCount() > 0) {
                    int count = cursor.getCount();
                    System.out.println("count::::" + count);
                    list = new ArrayList<CityJavaBean>();
                    while (cursor.moveToNext()) {
                        CityJavaBean cityJavaBean = new CityJavaBean();
                        cityJavaBean.setId(cursor.getString(0));
                        cityJavaBean.setProvince(cursor.getString(1));
                        cityJavaBean.setCity(cursor.getString(2));
                        cityJavaBean.setDistrict(cursor.getString(3));
                        list.add(cityJavaBean);
                    }
                    System.out.println("list.size():::::" + list.size());
                    cursor.close();

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"数据疯狂加载中...",Toast.LENGTH_LONG).show();
                        }
                    });
                    String cityPath = "http://v.juhe.cn/weather/citys?&dtype=xml&key=73a883c719111a73b3694ec9659f185c";
                    InputStream inputStream = PathToInputStream.urlToInputStream(cityPath);
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(Environment.getDataDirectory().toString());
                        byte[] bytes = new byte[1024];
                        int ch = 0 ;
                        while((ch=inputStream.read(bytes))!= -1)
                        {
                            fileOutputStream.write(bytes);
                        }
                          String  string  = Environment.getDataDirectory().toString();
                        System.out.println("string :::::"+string);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    cityJavaBeanList = CityXMLParser.InXMLToCityList(inputStream);
                    final List<CityJavaBean> finalCityJavaBeanList = cityJavaBeanList;

                    //再开一个线程实现将数据存到数据库中
                    new Thread() {
                        @Override
                        public void run() {
                            sqLiteDatabase = myDB.openConnection();
                            //遍历一下cityJavaBeanList数组中的内容 将内容存在数据库中
                            for (int i = 0; i < finalCityJavaBeanList.size(); i++) {
                                String id = finalCityJavaBeanList.get(i).getId();
                                String province = finalCityJavaBeanList.get(i).getProvince();
                                String city = finalCityJavaBeanList.get(i).getCity();
                                String district = finalCityJavaBeanList.get(i).getDistrict();
                                String str = id + province + city + district;
                                String pingYin = ChineseToEnglish.getPingYin(str);
                                String sql = "insert into " + TABLENAME + " VALUES (?, ?, ?,? ,?,?)";
                                sqLiteDatabase.execSQL(sql, new Object[]{id, province, city, district, pingYin, str});
                            }
                            System.out.println("加入数据库操作完成：：：：：");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"完成...",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }.start();

                }
                if (cursor.getCount() > 0) {
                    cityJavaBeanList = list;
                }

                final List<CityJavaBean> finalCityJavaBeans = cityJavaBeanList;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(),"服务器加载中，请稍后...",Toast.LENGTH_LONG).show();
                        lv_showCity.setAdapter(new ShowCityAdapter(finalCityJavaBeans, getApplicationContext()));
                        lv_showCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(Search.this, TestDemo.class);
                                Bundle bundle = new Bundle();
                                int pos = position;
                                TextView viewById = (TextView) view.findViewById(R.id.tv_item);
                                String toString = viewById.getText().toString();
                                System.out.println("pos:::" + pos);
                                System.out.println("toString:::" + toString);
                                int i = toString.indexOf('-');
                                int i1 = toString.lastIndexOf('-');
                                String trim = toString.substring(i + 3, i1 - 1).trim();
                                System.out.println("trim:::" + trim + "length:" + trim.length());
                                AllStaic.StaicBoolean = "true";
                                bundle.putString("Citypath", trim);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            }

                        });

                    }
                });
            }
        }).start();


    }
}


