package cn.chennuoxian.beijingnews.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.chennuoxian.beijingnews.R;
import cn.chennuoxian.beijingnews.activity.MainActivity;
import cn.chennuoxian.beijingnews.base.MenuDetailBasePager;
import cn.chennuoxian.beijingnews.domain.NewsCenterPagerBean2;
import cn.chennuoxian.beijingnews.menudetailpager.tabdetailpager.TabDetailPager;

//新闻详情页面
public class NewsMenuDetailPager extends MenuDetailBasePager {
    @ViewInject(R.id.tabPageIndicator)
    private TabPageIndicator tabPageIndicator;

    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    @ViewInject(R.id.ib_tab_next)
    private ImageButton ib_tab_next;

    //页签页面数据的集合
    private List<NewsCenterPagerBean2.DataBean.ChildrenBean> children;
    //页签页面的集合
    private ArrayList<TabDetailPager> tabDetailPagers;

    public NewsMenuDetailPager(Context context, NewsCenterPagerBean2.DataBean dataBean){
        super(context);
        children=dataBean.getChildren();
    }

    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.newsmenu_detail_pager,null);
        x.view().inject(NewsMenuDetailPager.this,view);
        //设置点击事件
        ib_tab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
        return view;
    }
    @Override
    public void initData() {
        super.initData();

        LogUtil.e("新闻详情页面数据被初始化了..");
        //准备新闻详情页面的数据
        tabDetailPagers=new ArrayList<>();
        for (int i=0;i<children.size();i++){
            tabDetailPagers.add(new TabDetailPager(context,children.get(i)));
        }
        //设置viewpager的适配器
        viewPager.setAdapter(new MyNewsMenuDetailPagerAdapter());
        //ViewPager和TabPageIndicator关联
        tabPageIndicator.setViewPager(viewPager);
        //注意以后监听页面的变化，TabPageIndicator监听变化
        tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void isEnableSlidingMenu(int touchmodeFullscreen){
        MainActivity mainActivity=(MainActivity)context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position==0){
                //SlidingMenu可用全屏滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else {//SlidingMenu不可用全屏滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyNewsMenuDetailPagerAdapter extends PagerAdapter{
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TabDetailPager tabDetailPager=tabDetailPagers.get(position);
            View rootView=tabDetailPager.rootView;
            tabDetailPager.initData();//初始化数据
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }
    }

}