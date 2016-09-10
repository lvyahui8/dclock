package org.lyh.dclock;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2016/9/10 16:17
 */
public class TimeView extends LinearLayout {

    private TextView tvTime;
    private Handler timeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            refreshTime();
            if(getVisibility() == View.VISIBLE){
                timeHandler.sendEmptyMessageDelayed(0,1000);
            }
        }
    };
    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化完成之后执行的操作
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tvTime = (TextView) findViewById(R.id.tvTime);
        timeHandler.sendEmptyMessage(0);
    }


    public void refreshTime(){
        Calendar c = Calendar.getInstance();

        tvTime.setText(String.format("%d : %d : %d",
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));
    }

    /**
     * 可见性发生变化
     * @param changedView
     * @param visibility
     */
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility == View.VISIBLE){
            timeHandler.sendEmptyMessage(0);
        }else{
            timeHandler.removeMessages(0);
        }
    }
}
