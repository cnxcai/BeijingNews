package cn.chennuoxian.beijingnews.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cn.chennuoxian.beijingnews.base.BaseFragment;
import cn.chennuoxian.beijingnews.utils.LogUtil;

public class LeftmenuFragment extends BaseFragment {

    private TextView textView;
    @Override
    public View initView() {
        LogUtil.e("左侧菜单视图被初始化了");
        textView=new TextView(context);
        textView.setTextSize(23);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("左侧菜单数据被初始化了");
        textView.setText("左侧菜单页面");
    }
}
