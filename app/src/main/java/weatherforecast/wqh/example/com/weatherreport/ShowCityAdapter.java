package weatherforecast.wqh.example.com.weatherreport;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**数据适配器类
 * Created by wqh on 2017/12/26.
 */

public class ShowCityAdapter extends BaseAdapter {
    private List<CityJavaBean> list;
    private Context context;

    public ShowCityAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item, null);
        } else {
            view = convertView;
        }
        TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
        CityJavaBean cityJavaBean = list.get(position);
        tv_item.setText(cityJavaBean.getId() + "  -  " +  cityJavaBean.getCity() + "  -  " + cityJavaBean.getDistrict());
        return view;
    }
}
