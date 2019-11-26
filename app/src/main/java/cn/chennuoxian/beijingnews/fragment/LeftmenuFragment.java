package cn.chennuoxian.beijingnews.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.chennuoxian.beijingnews.R;
import cn.chennuoxian.beijingnews.activity.MainActivity;
import cn.chennuoxian.beijingnews.base.BaseFragment;
import cn.chennuoxian.beijingnews.domain.NewsCenterPagerBean;
import cn.chennuoxian.beijingnews.utils.DensityUtil;
import cn.chennuoxian.beijingnews.utils.LogUtil;

public class LeftmenuFragment extends BaseFragment {

    private ListView listView;
    private List<NewsCenterPagerBean.DataBean> data;
    private LeftmenuFragmentAdapter adapter;
    private int prePosition;

    @Override
    public View initView() {
        LogUtil.e("左侧菜单视图被初始化了");
        listView=new ListView(context);
        listView.setPadding(0, DensityUtil.dip2px(context,40),0,0);
        listView.setDividerHeight(0);//设置分割线高度为0
        listView.setCacheColorHint(Color.TRANSPARENT);
        //设置按下listview的item不变色
        listView.setSelector(android.R.color.transparent);

        //设置item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录点击的位置，变成红色
                    prePosition=position;
                    adapter.notifyDataSetChanged();
                //2.把左侧菜单关闭
                MainActivity mainActivity= (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
                //3.切换到对应的详情页面:新闻详情页，专题详情页，图组详情页面，互动详情页面
            }
        });
        return listView;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("左侧菜单数据被初始化了");
    }
    //接收数据
    public void setData(List<NewsCenterPagerBean.DataBean> data) {
        this.data=data;
        for (int i=0;i<data.size();i++){
            LogUtil.e("title=="+data.get(i).getTitle());
        }
        adapter=new LeftmenuFragmentAdapter();
        //设置适配器
        listView.setAdapter(adapter);
    }

    class LeftmenuFragmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView= (TextView) View.inflate(context, R.layout.item_leftmenu,null);
            textView.setText(data.get(position).getTitle());

           /* if (position==prePosition){
                textView.setEnabled(true);
            }else {
                textView.setEnabled(false);
            }*/
           textView.setEnabled(position==prePosition);
            return textView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }
}
