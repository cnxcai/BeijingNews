package cn.chennuoxian.beijingnews.activity;

import android.app.Activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import cn.chennuoxian.beijingnews.R;
import cn.chennuoxian.beijingnews.fragment.ContentFragment;
import cn.chennuoxian.beijingnews.fragment.LeftmenuFragment;
import cn.chennuoxian.beijingnews.utils.DensityUtil;
import cn.chennuoxian.beijingnews.utils.NavigationBarUtil;

public class MainActivity extends SlidingFragmentActivity {

    public static final String MAIN_CONTENT_TAG = "main_content_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSlidingMenu();

        //初始化fragment
        initFragment();

        if(NavigationBarUtil.hasNavigationBar(this)){
            NavigationBarUtil.initActivity(findViewById(android.R.id.content));
        }
    }

    private void initSlidingMenu(){
        //设置主页面
        setContentView(R.layout.activity_main);

        //设置左侧菜单
        setBehindContentView(R.layout.activity_leftmenu);

        //设置右侧菜单
        SlidingMenu slidingMenu=getSlidingMenu();
        slidingMenu.setSecondaryMenu(R.layout.activity_rightmenu);

        //设置显示的模式,右侧菜单+主页，左侧菜单+主页面+右侧菜单；主页面+右侧菜单
        slidingMenu.setMode(SlidingMenu.LEFT);

        //设置滑动模式：滑动边缘，全屏滑动，不可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //设置主页占据的宽度
        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this,250));
    }

    private void initFragment() {
        //得到fragmentManger
        FragmentManager fm=getSupportFragmentManager();
        //开启事务
        FragmentTransaction ft=fm.beginTransaction();
        //替换
        ft.replace(R.id.fl_main_content,new ContentFragment(), MAIN_CONTENT_TAG);//主页
        ft.replace(R.id.fl_leftmenu,new LeftmenuFragment(), LEFTMENU_TAG);//左侧菜单
        //提交
        ft.commit();

        //getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_content,new ContentFragment(), MAIN_CONTENT_TAG).replace(R.id.fl_leftmenu,new LeftmenuFragment(), LEFTMENU_TAG).commit();

    }
    //得到左侧菜单
    public LeftmenuFragment getLeftmenuFragment() {
        return (LeftmenuFragment) getSupportFragmentManager().findFragmentByTag(LEFTMENU_TAG);
    }
//得到正文Fragment
    public ContentFragment getContentFragment() {
        return (ContentFragment) getSupportFragmentManager().findFragmentByTag(MAIN_CONTENT_TAG);
    }
}
