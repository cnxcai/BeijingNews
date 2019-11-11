package cn.chennuoxian.beijingnews.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import cn.chennuoxian.beijingnews.R;
import cn.chennuoxian.beijingnews.base.BaseFragment;
import cn.chennuoxian.beijingnews.utils.LogUtil;

public class ContentFragment extends BaseFragment {
    private ViewPager viewPager;
    private RadioGroup rg_main;


    @Override
    public View initView() {
        LogUtil.e("正文Fragment视图被初始化了");
        View view=View.inflate(context, R.layout.content_fragment,null);
        viewPager=(ViewPager)view.findViewById(R.id.viewpager);
        rg_main=(RadioGroup)view.findViewById(R.id.rg_main);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文Fragment数据被初始化了");

        rg_main.check(R.id.rb_home);
    }

}
