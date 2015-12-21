package com.example.user.day8_kangminkyoung;


import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.Calendar;

public class CalendarMonthAdapter extends BaseAdapter {

    public static final String TAG = "CalendarMonthAdapter";
    Context mContext;

    public static int oddColor = Color.rgb(225, 225, 225);
    public static int headColor = Color.rgb(12, 32, 158);

    // 선택 포지션
    private int selectedPosition = -1;

    // 날짜값 배열
    private MonthItem[] items;

    //컬럼 갯수
    private int countColumn = 7;

    //한주의 시작일
    int mStartDay;
    int startDay;

    // 현재 년도/월
    int curYear;
    int curMonth;

    // 시작일/말일
    int firstDay;
    int lastDay;

    // 달력 선언
    Calendar mCalendar;
    boolean recreateItems = false;


    // 생성자
    public CalendarMonthAdapter(Context context) {
        super();
        mContext = context;
        init();
    }
    public CalendarMonthAdapter(Context context, AttributeSet attrs) {
        super();
        mContext = context;
        init();
    }

    //초기 설정
    private void init() {
        // 날짜값 배열 설정
        items = new MonthItem[7 * 6];
        // 그레고리 달력
        mCalendar = Calendar.getInstance();
        recalculate();
        resetDayNumbers();
    }

    // 한달의 일자 계산
    public void recalculate() {
        // 첫째날 설정
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);

        // get week day
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        firstDay = getFirstDay(dayOfWeek);
        Log.d(TAG, "firstDay : " + firstDay);

        // 한 주의 시작일, 년도, 월, 말일
        mStartDay = mCalendar.getFirstDayOfWeek();
        curYear = mCalendar.get(Calendar.YEAR);
        curMonth = mCalendar.get(Calendar.MONTH);
        lastDay = getMonthLastDay(curYear, curMonth);
        Log.d(TAG, "curYear : " + curYear + ", curMonth : " + curMonth + ", lastDay : " + lastDay);

        int diff = mStartDay - Calendar.SUNDAY - 1;
        startDay = getFirstDayOfWeek();
        Log.d(TAG, "mStartDay : " + mStartDay + ", startDay : " + startDay);
    }

    // 이전달 설정
    public void setPreviousMonth() {
        mCalendar.add(Calendar.MONTH, -1);
        recalculate();
        resetDayNumbers();
        selectedPosition = -1;
    }

    // 다음달 설정
    public void setNextMonth() {
        mCalendar.add(Calendar.MONTH, 1);
        recalculate();
        resetDayNumbers();
        selectedPosition = -1;
    }

    // 날짜 재설정
    public void resetDayNumbers() {
        for (int i = 0; i < 42; i++) {
            // calculate day number
            int dayNumber = (i + 1) - firstDay;
            if (dayNumber < 1 || dayNumber > lastDay) {
                dayNumber = 0;
            }

            // save as a data item
            items[i] = new MonthItem(dayNumber);
        }
    }

    // 첫째날 설정
    private int getFirstDay(int dayOfWeek) {
        int result = 0;
        if (dayOfWeek == Calendar.SUNDAY) {
            result = 0;
        } else if (dayOfWeek == Calendar.MONDAY) {
            result = 1;
        } else if (dayOfWeek == Calendar.TUESDAY) {
            result = 2;
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            result = 3;
        } else if (dayOfWeek == Calendar.THURSDAY) {
            result = 4;
        } else if (dayOfWeek == Calendar.FRIDAY) {
            result = 5;
        } else if (dayOfWeek == Calendar.SATURDAY) {
            result = 6;
        }
        return result;
    }

    // 올해, 이번달 반환
    public int getCurYear() {
        return curYear;
    }
    public int getCurMonth() { return curMonth; }

    // 컬럼 갯수 반환
    public int getNumColumns() {
        return 7;
    }

    // 칸 갯수 반환
    public int getCount() {
        return 7 * 6;
    }

    // 해당 일자의 위치를 넣으면 일자 반환
    public Object getItem(int position) { return items[position]; }

    // 해당 아이템의 위치를 넣으면 위치 도로 반환
    public long getItemId(int position) {
        return position;
    }

    // MonthItemView에 계산한 값 보이기
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView(" + position + ") called.");

        MonthItemView itemView;
        if (convertView == null) {
            itemView = new MonthItemView(mContext);
        } else {
            itemView = (MonthItemView) convertView;
        }

        // create a params
        GridView.LayoutParams params = new GridView.LayoutParams(
                GridView.LayoutParams.MATCH_PARENT,
                120);

        // calculate row and column
        int rowIndex = position / countColumn;
        int columnIndex = position % countColumn;

        Log.d(TAG, "Index : " + rowIndex + ", " + columnIndex);

        // set item data and properties
        itemView.setItem(items[position]);
        itemView.setLayoutParams(params);
        itemView.setPadding(2, 2, 2, 2);

        // set properties
        itemView.setGravity(Gravity.LEFT);

        // 일요일일 경우 빨간 글씨로
        if (columnIndex == 0) {
            itemView.setTextColor(Color.RED);
        } else {
            itemView.setTextColor(Color.BLACK);
        }

        // 배경색 지정
        // 위치가 선택된 칸일 경우 노란색 배경
        if (position == getSelectedPosition()) {
            itemView.setBackgroundColor(Color.YELLOW);
        } else {
                itemView.setBackgroundColor(Color.WHITE);
        }
        return itemView;
    }

    // 한주의 시작일 반환
    public static int getFirstDayOfWeek() {
        int startDay = Calendar.getInstance().getFirstDayOfWeek();
        if (startDay == Calendar.SATURDAY) {
            return Time.SATURDAY;
        } else if (startDay == Calendar.MONDAY) {
            return Time.MONDAY;
        } else {
            return Time.SUNDAY;
        }
    }

    // 한달에 몇일 있는지
    private int getMonthLastDay(int year, int month) {
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return (31);

            case 3:
            case 5:
            case 8:
            case 10:
                return (30);

            default:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return (29);   // 2월 윤년계산
                } else {
                    return (28);
                }
        }
    }

    // 선택된 위치 입력받기
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    // 선택된 위치 반환하기
    public int getSelectedPosition() {
        return selectedPosition;
    }

}

