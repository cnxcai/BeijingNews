package cn.chennuoxian.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import cn.chennuoxian.beijingnews.base.BasePager;
import cn.chennuoxian.beijingnews.utils.LogUtil;

public class SettingPager extends BasePager {
    public SettingPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("设置中心数据被初始化..");
        tv_title.setText("设置中心");
        TextView textView=new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);

        fl_content.addView(textView);
        textView.setText("设置中心内容");
    }
}
