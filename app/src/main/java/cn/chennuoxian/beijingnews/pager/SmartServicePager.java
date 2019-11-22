package cn.chennuoxian.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import cn.chennuoxian.beijingnews.base.BasePager;
import cn.chennuoxian.beijingnews.utils.LogUtil;

public class SmartServicePager extends BasePager {
    public SmartServicePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("智慧服务数据被初始化..");
        tv_title.setText("智慧服务");
        TextView textView=new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);

        fl_content.addView(textView);
        textView.setText("智慧服务内容");
    }
}
