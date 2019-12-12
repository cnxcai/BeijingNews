package cn.chennuoxian.beijingnews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NoScrollViewPager extends ViewPager {
    //通常在代码中实例化的时候用该方法
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }
//在布局文件中使用该类的时候，实例化该类用该构造方法，这个方法不能少，少的话就崩溃
    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
//重写触摸事件，消耗掉
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
