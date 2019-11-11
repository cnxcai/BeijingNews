package cn.chennuoxian.beijingnews.activity;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cn.chennuoxian.beijingnews.R;
import cn.chennuoxian.beijingnews.SplashActivity;
import cn.chennuoxian.beijingnews.utils.CacheUtils;
import cn.chennuoxian.beijingnews.utils.DensityUtil;

public class GuideActivity extends Activity {
    private static final String TAG = "GuideActivity";
    private ViewPager viewPager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;
    private ImageView iv_red_point;
    private int leftmax;
    private int widthdpi;
    private ArrayList<ImageView> imageViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager=(ViewPager)findViewById(R.id.viewpager);
        btn_start_main=(Button)findViewById(R.id.btn_start_main);
        ll_point_group=(LinearLayout)findViewById(R.id.ll_point_group);
        iv_red_point=(ImageView)findViewById(R.id.iv_red_point);
        btn_start_main.setVisibility(View.GONE);
        //准备数据
        int[] ids=new int[]{
               R.drawable.guide_1,
               R.drawable.guide_2,
               R.drawable.guide_3
        };

        widthdpi= DensityUtil.dip2px(this,10);

        imageViews=new ArrayList<>();
        for (int i=0;i<ids.length;i++){
            ImageView imageView=new ImageView(this);
            //设置背景
            imageView.setBackgroundResource(ids[i]);

            //添加到集合中
            imageViews.add(imageView);

            //创建点
            ImageView  point=new ImageView(this);
            //添加到布局页面
            point.setBackgroundResource(R.drawable.point_normal);

            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(widthdpi,widthdpi);
            if (i !=0){
                params.leftMargin=widthdpi;
            }
            point.setLayoutParams(params);
            //添加到布局
            ll_point_group.addView(point);
        }
        //设置viewpager的适配器
        viewPager.setAdapter(new MyPagerAdapter());

        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        btn_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存曾经进入过主页面
                CacheUtils.putBoolean(GuideActivity.this, SplashActivity.START_MAIN,true);
                //跳转到主页面
                Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                //关闭引导页面
                finish();
            }
        });
    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //int leftmargin=(int)(positionOffset * leftmax);
            //Log.e(TAG,"position=="+position+",positionOffset=="+positionOffset+",positionOffsetPixels=="+positionOffsetPixels);

            int leftmargin=(int)(position * leftmax+(positionOffset * leftmax));

            RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)iv_red_point.getLayoutParams();
            params.leftMargin=leftmargin;
            iv_red_point.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            if (position==imageViews.size()-1){
                btn_start_main.setVisibility(View.VISIBLE);
            }else{
                btn_start_main.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{

        @Override
        public void onGlobalLayout() {
            //执行不只一次
            iv_red_point.getViewTreeObserver().removeGlobalOnLayoutListener(MyOnGlobalLayoutListener.this);
            leftmax=ll_point_group.getChildAt(1).getLeft()-ll_point_group.getChildAt(0).getLeft();
            Log.e(TAG, "leftmax=="+leftmax);
        }
    }
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViews.size();
        }

        ///作用getview
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView=imageViews.get(position);
            container.addView(imageView);

            //return position;
            return imageView;
            //return super.instantiateItem(container, position);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            //return view ==imageViews.get(Integer.parseInt((String) object));
            return view ==object;
        }


        //销毁界面
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
