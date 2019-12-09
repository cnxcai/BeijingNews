package cn.chennuoxian.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.chennuoxian.beijingnews.activity.MainActivity;
import cn.chennuoxian.beijingnews.base.BasePager;
import cn.chennuoxian.beijingnews.base.MenuDetailBasePager;

import cn.chennuoxian.beijingnews.domain.NewsCenterPagerBean2;
import cn.chennuoxian.beijingnews.fragment.LeftmenuFragment;
import cn.chennuoxian.beijingnews.menudetailpager.InteracMenuDetailPager;
import cn.chennuoxian.beijingnews.menudetailpager.NewsMenuDetailPager;
import cn.chennuoxian.beijingnews.menudetailpager.PhotosMenuDetailPager;
import cn.chennuoxian.beijingnews.menudetailpager.TopicMenuDetailPager;
import cn.chennuoxian.beijingnews.utils.CacheUtils;
import cn.chennuoxian.beijingnews.utils.Constants;
import cn.chennuoxian.beijingnews.utils.LogUtil;

public class NewsCenterPager extends BasePager {

    private List<NewsCenterPagerBean2.DataBean> data;

    private ArrayList<MenuDetailBasePager> detailBasePagers;

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
        //获取缓存数据
        String saveJson=CacheUtils.getString(context,Constants.NEWSCENTER_PAGER_URL);
        if (!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

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

                //缓存数据
                CacheUtils.putString(context,Constants.NEWSCENTER_PAGER_URL,result);

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
        //NewsCenterPagerBean bean=parsedJson(json);
        NewsCenterPagerBean2 bean=parsedJson2(json);

        //String title=bean.getData().get(0).getChildren().get(0).getTitle();
        //LogUtil.e("使用Gson解析json数据成功---------title=="+title);

        String title2=bean.getData().get(0).getChildren().get(0).getTitle();
        LogUtil.e("使用手动解析json数据成功-------------------------title2=="+title2);

        //把左侧菜单传递数据
        data=bean.getData();
        MainActivity mainActivity=(MainActivity)context;
        //得到左侧菜单
        LeftmenuFragment leftmenuFragment=mainActivity.getLeftmenuFragment();
        //添加详情页面
        detailBasePagers=new ArrayList<>();
        detailBasePagers.add(new NewsMenuDetailPager(context,data.get(0)));
        detailBasePagers.add(new TopicMenuDetailPager(context));
        detailBasePagers.add(new PhotosMenuDetailPager(context));
        detailBasePagers.add(new InteracMenuDetailPager(context));

        //把数据传递给左侧
        leftmenuFragment.setData(data);
    }
//手动装入数据，显示数据
    private NewsCenterPagerBean2 parsedJson2(String json) {
        NewsCenterPagerBean2 bean2=new NewsCenterPagerBean2();
        try {
            JSONObject object=new JSONObject(json);

            int retcode=object.optInt("retcode");
            bean2.setRetcode(retcode);//retcode字段解析成功

            JSONArray data=object.optJSONArray("data");
            if (data !=null && data.length()>0){

                List<NewsCenterPagerBean2.DataBean> dataBeans=new ArrayList<>();
                //设置列表数据
                bean2.setData(dataBeans);
                //for循环,解析每条数据
                for (int i=0;i<data.length();i++){
                    JSONObject jsonObject= (JSONObject) data.get(i);

                    NewsCenterPagerBean2.DataBean dataBean=new NewsCenterPagerBean2.DataBean();
                    //添加到集合中
                    dataBeans.add(dataBean);

                    int id=jsonObject.optInt("id");
                    dataBean.setId(id);
                    int type=jsonObject.optInt("type");
                    dataBean.setType(type);
                    String title=jsonObject.optString("title");
                    dataBean.setTitle(title);
                    String url=jsonObject.optString("url");
                    dataBean.setUrl(url);
                    String url1=jsonObject.optString("url1");
                    dataBean.setUrl1(url1);
                    String dayurl=jsonObject.optString("dayurl");
                    dataBean.setDayurl(dayurl);
                    String excurl=jsonObject.optString("excurl");
                    dataBean.setExcurl(excurl);
                    String weekurl=jsonObject.optString("weekurl");
                    dataBean.setWeekurl(weekurl);

                    JSONArray children=jsonObject.optJSONArray("children");
                    if (children !=null && children.length()>0){
                        List<NewsCenterPagerBean2.DataBean.ChildrenBean> childrenBeans=new ArrayList<>();

                        dataBean.setChildren(childrenBeans);

                        for (int j=0;j<children.length();j++){
                            JSONObject childrenItem= (JSONObject) children.get(j);

                            NewsCenterPagerBean2.DataBean.ChildrenBean childrenBean=new NewsCenterPagerBean2.DataBean.ChildrenBean();
                            //添加到集合中
                            childrenBeans.add(childrenBean);

                            int childid=childrenItem.optInt("id");
                            childrenBean.setId(childid);
                            int childtype=childrenItem.optInt("type");
                            childrenBean.setType(childtype);
                            String childtitle=childrenItem.optString("title");
                            childrenBean.setTitle(childtitle);
                            String childurl=childrenItem.optString("url");
                            childrenBean.setUrl(childurl);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean2;
    }

    //解析json数据，1，使用系统的API解析json,2,使用第三方框架，例如Gson
    private NewsCenterPagerBean2 parsedJson(String json) {
        /*Gson gson=new Gson();
        NewsCenterPagerBean bean=gson.fromJson(json,NewsCenterPagerBean.class);*/
        return new Gson().fromJson(json,NewsCenterPagerBean2.class);
    }
//切换详情页面
    public void swichPager(int position) {
        //1.设置标题
        tv_title.setText(data.get(position).getTitle());
        //2.移除内容
        fl_content.removeAllViews();
        //3.添加新内容
        MenuDetailBasePager detailBasePager=detailBasePagers.get(position);
        View rootView=detailBasePager.rootView;
        detailBasePager.initData();
        fl_content.addView(rootView);
    }
}
