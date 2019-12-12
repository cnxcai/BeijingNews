package cn.chennuoxian.beijingnews.menudetailpager.tabdetailpager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import cn.chennuoxian.beijingnews.R;
import cn.chennuoxian.beijingnews.base.MenuDetailBasePager;
import cn.chennuoxian.beijingnews.domain.NewsCenterPagerBean2;
import cn.chennuoxian.beijingnews.domain.TabDetailPagerBean;
import cn.chennuoxian.beijingnews.utils.CacheUtils;
import cn.chennuoxian.beijingnews.utils.Constants;
import cn.chennuoxian.beijingnews.utils.LogUtil;

//页签详情页面
public class TabDetailPager extends MenuDetailBasePager {

    private ImageOptions imageOptions;
    //顶部新闻的数据
    private List<TabDetailPagerBean.DataBean.TopnewsData> topnews;

    private final NewsCenterPagerBean2.DataBean.ChildrenBean childrenBean;
    /*private TextView textView;*/

    private List<TabDetailPagerBean.DataBean.NewsData> news;

    private TabDetailPagerListAdapter adapter;
    private String url;

    private ViewPager viewpager;
    private TextView tv_title;
    private ListView listview;
    private LinearLayout ll_point_group;

    public TabDetailPager(Context context, NewsCenterPagerBean2.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean=childrenBean;
        imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(100), DensityUtil.dip2px(100))
                .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.news_pic_default)
                .setFailureDrawableId(R.drawable.news_pic_default)
                .build();
    }

    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.tabdetail_pager,null);
        listview=(ListView)view.findViewById(R.id.listview);

        View topNewsView=View.inflate(context,R.layout.topnews,null);

        viewpager=(ViewPager) topNewsView.findViewById(R.id.viewpager);
        tv_title=(TextView)topNewsView.findViewById(R.id.tv_title);
        ll_point_group=(LinearLayout)topNewsView.findViewById(R.id.ll_point_group);

        //把顶部轮播图部分视图，以头的方式添加到listview
        listview.addHeaderView(topNewsView);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
       /* textView.setText(childrenBean.getTitle());*/
        url= Constants.BASE_URL+childrenBean.getUrl();
        //把缓存数据取出
        String saveJson=CacheUtils.getString(context,url);
        if (!TextUtils.isEmpty(saveJson)){
            //解析数据
            processData(saveJson);
        }
/*
        LogUtil.e(childrenBean.getTitle()+"的联网地址=="+url);
*/
        //联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //缓存数据
                CacheUtils.putString(context,url,result);
                LogUtil.e(childrenBean.getTitle()+"-页面数据请求成功=="+result);
                //解析数据，显示数据
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(childrenBean.getTitle()+"-页面数据请求失败=="+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(childrenBean.getTitle()+"-页面数据请求onCancelled=="+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e(childrenBean.getTitle()+"onFinished==");
            }
        });
    }
    //之前点亮的位置
    private int prePosition;

    private void processData(String json) {
        TabDetailPagerBean bean=parsedJson(json);
        LogUtil.e(childrenBean.getTitle()+"解析成功=="+bean.getData().getNews().get(0).getTitle());

        topnews= bean.getData().getTopnews();
        //设置viewpager的适配器
        viewpager.setAdapter(new TabDetailPagerTopNewsAdapter());


        //添加红点
        ll_point_group.removeAllViews();//移除所有的红点
        for (int i=0;i<topnews.size();i++){
            ImageView imageView=new ImageView(context);
            //设置背景选择器
            imageView.setBackgroundResource(R.drawable.point_selector);

            ViewGroup.LayoutParams params=new LinearLayout.LayoutParams(DensityUtil.dip2px(8),DensityUtil.dip2px(8));

            if (i==0){
                imageView.setEnabled(true);
            }else {
                imageView.setEnabled(false);
                ((LinearLayout.LayoutParams) params).leftMargin=DensityUtil.dip2px(8);
            }
            imageView.setLayoutParams(params);
            ll_point_group.addView(imageView);
        }

        //监听页面的改变，设置红点变化和文本变化
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        tv_title.setText(topnews.get(prePosition).getTitle());

        //准备listview对应的集合数据
        news=bean.getData().getNews();
        //设置listview的适配器
        adapter=new TabDetailPagerListAdapter();
        listview.setAdapter(adapter);

    }

    class TabDetailPagerListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return news.size();
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
            ViewHolder viewHolder;
            if (convertView==null){
                convertView=View.inflate(context,R.layout.item_tabdetail_pager,null);
                viewHolder=new ViewHolder();
                viewHolder.iv_icon=(ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time=(TextView) convertView.findViewById(R.id.tv_time);

                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }

            //根据位置得到数据
            TabDetailPagerBean.DataBean.NewsData newsData=news.get(position);
            String imageUrl=Constants.BASE_URL+newsData.getListimage();
            //请求图片
            x.image().bind(viewHolder.iv_icon,imageUrl,imageOptions);
            //设置标题
            viewHolder.tv_title.setText(newsData.getTitle());
            //设置时间
            viewHolder.tv_time.setText(newsData.getPubdate());
            return convertView;
        }
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //设置文本
            tv_title.setText(topnews.get(position).getTitle());
            //红点亮
            ll_point_group.getChildAt(prePosition).setEnabled(false);
                //之前变灰色，当前红色
            ll_point_group.getChildAt(position).setEnabled(true);
            prePosition=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class TabDetailPagerTopNewsAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView=new ImageView(context);
            //设置图片默认背景
            imageView.setBackgroundResource(R.drawable.home_scroll_default);
            //xy拉伸图片
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //把图片添加到容器
            container.addView(imageView);

            TabDetailPagerBean.DataBean.TopnewsData topnewsData=topnews.get(position);
            String imageUrl=Constants.BASE_URL+topnewsData.getTopimage();
            //联网请求图片
            x.image().bind(imageView,imageUrl);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
/*
            super.destroyItem(container, position, object);
*/
            container.removeView((View) object);
        }
    }
    private TabDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json,TabDetailPagerBean.class);
    }
}
