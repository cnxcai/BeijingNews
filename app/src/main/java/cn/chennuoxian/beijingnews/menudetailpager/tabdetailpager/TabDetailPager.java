package cn.chennuoxian.beijingnews.menudetailpager.tabdetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cn.chennuoxian.beijingnews.base.MenuDetailBasePager;
import cn.chennuoxian.beijingnews.domain.NewsCenterPagerBean2;

//页签详情页面
public class TabDetailPager extends MenuDetailBasePager {
    private final NewsCenterPagerBean2.DataBean.ChildrenBean childrenBean;
    private TextView textView;

    public TabDetailPager(Context context, NewsCenterPagerBean2.DataBean.ChildrenBean childrenBean) {
        super(context);
        this.childrenBean=childrenBean;
    }

    @Override
    public View initView() {
        textView=new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText(childrenBean.getTitle());
    }
}
