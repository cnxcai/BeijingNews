package cn.chennuoxian.beijingnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.chennuoxian.beijingnews.activity.GuideActivity;
import cn.chennuoxian.beijingnews.activity.MainActivity;
import cn.chennuoxian.beijingnews.utils.CacheUtils;

public class SplashActivity extends Activity {

    public static final String START_MAIN="start_main";
    private RelativeLayout rl_splahs_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rl_splahs_root=(RelativeLayout)findViewById(R.id.rl_splahs_root);
        //渐变动画
        AlphaAnimation aa=new AlphaAnimation(0,1);
        //aa.setDuration(500); //持续播放时间
        aa.setFillAfter(true);

        ScaleAnimation sa=new ScaleAnimation(0,1,0,1,
                ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        //sa.setDuration(500);
        sa.setFillAfter(true);

        RotateAnimation ra=new RotateAnimation(0,360,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        //ra.setDuration(500);
        ra.setFillAfter(true);

        AnimationSet set=new AnimationSet(false);
        //添加三个动画没有先后顺序,便于同时播放动画
        set.addAnimation(ra);
        set.addAnimation(aa);
        set.addAnimation(sa);
        set.setDuration(2000);

        rl_splahs_root.startAnimation(set);

        set.setAnimationListener(new MyAnimationListener());
    }
    class MyAnimationListener implements Animation.AnimationListener{

//当动画开始播放
        @Override
        public void onAnimationStart(Animation animation) {

        }
//当动画结束播放
        @Override
        public void onAnimationEnd(Animation animation) {
            //判断是否进入过主界面
            boolean isStartMain= CacheUtils.getBoolean(SplashActivity.this,START_MAIN);
            Intent intent;
            if (isStartMain){
                //进过，直接进，否则进入引导
                intent=new Intent(SplashActivity.this, MainActivity.class);
            }else{
                intent=new Intent(SplashActivity.this, GuideActivity.class);
            }
            startActivity(intent);

            finish();
            //Toast.makeText(SplashActivity.this, "动画播放完成了", Toast.LENGTH_SHORT).show();
        }
//当动画重复播放
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
