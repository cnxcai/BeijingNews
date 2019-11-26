package cn.chennuoxian.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.chennuoxian.beijingnews.activity.MainActivity;
import cn.chennuoxian.beijingnews.base.BasePager;
import cn.chennuoxian.beijingnews.domain.NewsCenterPagerBean;
import cn.chennuoxian.beijingnews.fragment.LeftmenuFragment;
import cn.chennuoxian.beijingnews.utils.Constants;
import cn.chennuoxian.beijingnews.utils.LogUtil;

public class NewsCenterPager extends BasePager {

    private List<NewsCenterPagerBean.DataBean> data;

    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻中心数据被初始化..");
        ib_menu.setVisibility(View.VISIBLE);
        tv_title.setText("新闻中心");
        TextView textView=new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);

        fl_content.addView(textView);
        textView.setText("新闻中心内容");

        //联网请求数据
        getDataFromNet();

    }
//使用xUtils3联网请求
    private void getDataFromNet() {
        RequestParams params=new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                LogUtil.e("使用xUtils3联网请求成功=="+result);

                //设置适配器
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("使用xUtils3联网请求失败=="+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("使用xUtils3-onCancelled"+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("使用xUtils3-onFinished");
            }
        });
    }

    private void processData(String json) {
        //解析json数据和显示数据
        NewsCenterPagerBean bean=parsedJson(json);
        String title=bean.getData().get(0).getChildren().get(0).getTitle();
        LogUtil.e("使用Gson解析json数据成功-title=="+title);

        //把左侧菜单传递数据
        data=bean.getData();
        MainActivity mainActivity=(MainActivity)context;
        //得到左侧菜单
        LeftmenuFragment leftmenuFragment=mainActivity.getLeftmenuFragment();
        //把数据传递给左侧
        leftmenuFragment.setData(data);

    }
//解析json数据，1，使用系统的API解析json,2,使用第三方框架，例如Gson
    private NewsCenterPagerBean parsedJson(String json) {
        /*Gson gson=new Gson();
        NewsCenterPagerBean bean=gson.fromJson(json,NewsCenterPagerBean.class);*/
        return new Gson().fromJson(json,NewsCenterPagerBean.class);
    }
}
