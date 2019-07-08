package com.jwstudio.enwords.home.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;



class ZWSignCalendar extends View {

    private String[] weekTitles = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//Locale.CHINA
    private Calendar calendar = Calendar.getInstance();
    //今天所在的年月日信息
    private int currentYear, currentMonth, currentDay;
    private GregorianCalendar date = new GregorianCalendar();
    private ZWSignCalendarView.Config config;
    private HashSet<String> signRecords;
    private Paint paint = new Paint();
    private LunarHelper lunarHelper;
    private int itemWidth, itemHeight;
    private float solarTextHeight;
    private int currentPosition;

    private GestureDetectorCompat detectorCompat;

    //今天所在的年月日信息
    private int selectYear, selectMonth;

    //点击选中的年月日信息
    private int clickYear, clickMonth, clickDay;


    public ZWSignCalendar(Context context) {
        super(context);
    }

    public ZWSignCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZWSignCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void init(ZWSignCalendarView.Config config) {
        this.config = config;
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DATE);

        selectYear = currentYear;
        selectMonth = currentMonth;
        clickYear = currentYear;
        clickMonth = currentMonth;
        clickDay = currentDay;

        detectorCompat = new GestureDetectorCompat(getContext(), gestureListener);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStrokeWidth(sp2px(0.6f));
        currentPosition = (currentYear - 1970) * 12 + currentMonth + 1;
        setClickable(true);
        if (config.isShowLunar) lunarHelper = new LunarHelper();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        itemWidth = getWidth() / 7;
        itemHeight = (getHeight() - (int) config.weekHeight) / 6;
        paint.setTextSize(config.calendarTextSize);
        solarTextHeight = getTextHeight();
        if (config.signSize == 0) config.signSize = Math.min(itemHeight, itemWidth);
    }

    final void selectDate(int position) {
        currentPosition = position - 1;
        selectYear = 1970 + currentPosition / 12;
        selectMonth = currentPosition % 12;
        invalidate();
    }

    final void initSelect(int clickYear, int clickMonth, int clickDay) {
        this.clickYear = clickYear;
        this.clickMonth = clickMonth;
        this.clickDay = clickDay;
//        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.LTGRAY);
        canvas.drawLine(0, config.weekHeight, 0, getHeight() - config.weekHeight, paint);
        //画日历顶部周的标题
        paint.setColor(config.weekBackgroundColor);
        canvas.drawRect(0, 0, getWidth(), config.weekHeight, paint);
        paint.setTextSize(config.weekTextSize);
        paint.setColor(config.weekTextColor);
        float delay = getTextHeight() / 4;
        for (int i = 0; i < 7; i++) {
            canvas.drawText(weekTitles[i], itemWidth * (i + 0.5f), config.weekHeight / 2 + delay, paint);
        }
        //画日历
        int year = 1970 + currentPosition / 12;
        int month = currentPosition % 12;
        calendar.set(year, month, 1);
        int firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int selectMonthMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        delay = solarTextHeight / 4;
        if (config.isShowLunar) delay = 0;
        for (int i = 1; i <= selectMonthMaxDay; i++) {
            int position = i - 1 + firstDay;
            int x = (position % 7) * itemWidth + itemWidth / 2;
            int y = (position / 7) * itemHeight + itemHeight / 2 + (int) config.weekHeight + (int) delay;
            int day = i;
            //签到背景
            boolean isSign = false;
            if (signRecords != null) {
                date.set(year, month, day);
                String dateStr = format.format(date.getTime());
                if (signRecords.contains(dateStr)) {
                    paint.setColor(config.signColor);
                    canvas.drawCircle(x, y, config.signSize / 2, paint);
                    isSign = true;
                }
            }
            //农历
            if (config.isShowLunar) {
                paint.setColor(isSign ? config.signTextColor : config.lunarTextColor);
                String lunar = lunarHelper.SolarToLunarString(year, month + 1, day);
                paint.setTextSize(config.lunarTextSize);
                canvas.drawText(lunar, x, y + solarTextHeight * 2 / 3, paint);
            }
            //阳历
            if (isSign) {
                paint.setColor(config.signTextColor);
            } else if (year == currentYear && month == currentMonth && day == currentDay) {//今天
                paint.setColor(config.todayTextColor);
            } else {//其他天
                paint.setColor(config.calendarTextColor);
            }
            paint.setTextSize(config.calendarTextSize);
            canvas.drawText(String.valueOf(day), x, y, paint);
        }
    }

    void setSignRecords(HashSet<String> signRecords) {
        this.signRecords = signRecords;
    }

    private float sp2px(float spValue) {
        float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    private float getTextHeight() {
        return paint.descent() - paint.ascent();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private GestureDetector.OnGestureListener
            gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int position = getPosition(e.getX(), e.getY());
            if (dateClickListener != null) {
                calendar.set(selectYear, selectMonth, 1);
                int firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                calendar.set(selectYear, selectMonth, position - firstDay + 1);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
                if (month == selectMonth) {
                    clickYear = year;
                    clickMonth = month;
                    clickDay = day;
//                    invalidate();
                    dateClickListener.dateSelect(year, month, day);
                }
            }
            return super.onSingleTapUp(e);
        }
    };

    private int getPosition(float x, float y) {
        y -= config.weekHeight;
        int y1 = (int) (y / itemHeight);
        int x1 = (int) (x / itemWidth);
        return y1 * 7 + x1;
    }

    interface DateSelectListener {
        void dateSelect(int year, int month, int day);
    }

    private DateSelectListener dateClickListener;

    void setDateSelectListener(DateSelectListener clickListener) {
        dateClickListener = clickListener;
    }
}
