package cn.chennuoxian.beijingnews.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import cn.chennuoxian.beijingnews.R;
import cn.chennuoxian.beijingnews.base.BaseFragment;
import cn.chennuoxian.beijingnews.base.BasePager;
import cn.chennuoxian.beijingnews.pager.GovaffairPager;
import cn.chennuoxian.beijingnews.pager.HomePager;
import cn.chennuoxian.beijingnews.pager.NewsCenterPager;
import cn.chennuoxian.beijingnews.pager.SettingPager;
import cn.chennuoxian.beijingnews.pager.SmartServicePager;
import cn.chennuoxian.beijingnews.utils.LogUtil;

public class ContentFragment extends BaseFragment {
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
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

        rg_main.check(R.id.rb_home);
    }

}
