package org.lyh.dclock;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2016/9/10 16:43
 */
public class AlarmView extends LinearLayout {

    private static final long DAY = 24 * 60 * 60 * 1000;
    public static final String LIST = "alarmlist";
    private Button buttonAddAlrarm;

    private ListView lvAlarmList;

    private ArrayAdapter<AlarmData> adapter;

    public AlarmView(Context context) {
        super(context);
    }

    public AlarmView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlarmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        buttonAddAlrarm = (Button) findViewById(R.id.add_alarm);
        lvAlarmList = (ListView) findViewById(R.id.lvAlarmList);

        adapter = new ArrayAdapter<>(getContext(),R.layout.alarm_item);

        lvAlarmList.setAdapter(adapter);

        buttonAddAlrarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlarm();
            }
        });
    }

    private void addAlarm(){
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);

                Calendar currentTime = Calendar.getInstance();
                if(calendar.getTimeInMillis() <= currentTime.getTimeInMillis()){
                    // 往后推一天执行
                    calendar.setTimeInMillis(calendar.getTimeInMillis() + DAY);
                }

                adapter.add(new AlarmData(calendar.getTimeInMillis()));
                saveAlarmList();
            }
        },c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),true).show();
    }

    private void saveAlarmList() {
        SharedPreferences.Editor editor = getContext()
                .getSharedPreferences(AlarmView.class.getName(),Context.MODE_PRIVATE).edit();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < adapter.getCount(); i ++){
            if(i > 0){
                sb.append(',');
            }

            sb.append(adapter.getItem(i).getTime());
        }
        editor.putString(LIST,sb.toString());
    }


    private class AlarmData{
        private long time ;

        private String timeLabel;

        private Calendar date;

        public AlarmData(long time) {
            this.time = time;
            date = Calendar.getInstance();
            date.setTimeInMillis(time);
            timeLabel = String.format("%d-%d-%d %d:%d",
                    date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH),
                    date.get(Calendar.HOUR_OF_DAY),date.get(Calendar.SECOND));
        }

        public long getTime() {
            return time;
        }

        @Override
        public String toString() {
            return timeLabel;
        }
    }


}
