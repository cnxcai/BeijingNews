package cn.chennuoxian.beijingnews.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import cn.chennuoxian.beijingnews.R;
import cn.chennuoxian.beijingnews.activity.MainActivity;
import cn.chennuoxian.beijingnews.adapter.ContentFragmentAdapter;
import cn.chennuoxian.beijingnews.base.BaseFragment;
import cn.chennuoxian.beijingnews.base.BasePager;
import cn.chennuoxian.beijingnews.pager.GovaffairPager;
import cn.chennuoxian.beijingnews.pager.HomePager;
import cn.chennuoxian.beijingnews.pager.NewsCenterPager;
import cn.chennuoxian.beijingnews.pager.SettingPager;
import cn.chennuoxian.beijingnews.pager.SmartServicePager;
import cn.chennuoxian.beijingnews.utils.LogUtil;
import cn.chennuoxian.beijingnews.view.NoScrollViewPager;

public class ContentFragment extends BaseFragment {
    @ViewInject(R.id.viewpager)
    private NoScrollViewPager viewPager;
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;

    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        LogUtil.e("正文Fragment视图被初始化了");
        View view=View.inflate(context, R.layout.content_fragment,null);
//      viewPager=(ViewPager)view.findViewById(R.id.viewpager);
//      g_main=(RadioGroup)view.findViewById(R.id.rg_main);
        //把视图弄到框架
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文Fragment数据被初始化了");

        basePagers=new ArrayList<>();
        basePagers.add(new HomePager(context));
        basePagers.add(new NewsCenterPager(context));
        basePagers.add(new SmartServicePager(context));
        basePagers.add(new GovaffairPager(context));
        basePagers.add(new SettingPager(context));

        viewPager.setAdapter(new ContentFragmentAdapter(basePagers));

        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        //监听某个页面选中，初始化页面数据
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        rg_main.check(R.id.rb_home);
        basePagers.get(0).initData();
        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //调用被选中的页面的initData
            basePagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_home:
                    viewPager.setCurrentItem(0,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_newscenter:
                    viewPager.setCurrentItem(1,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.rb_smartservice:
                    viewPager.setCurrentItem(2,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_govaffair:
                    viewPager.setCurrentItem(3,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_setting:
                    viewPager.setCurrentItem(4,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
            }
        }
    }

    private void isEnableSlidingMenu(int touchmodeFullscreen){
        MainActivity mainActivity=(MainActivity)context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
    }

}
