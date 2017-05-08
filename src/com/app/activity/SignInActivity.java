package com.app.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.app.base.BaseActivity;
import com.app.component.CalendarView;
import com.app.component.CalendarView.OnItemClickListener;
import com.app.entity.SignIn;
import com.app.utils.DateConvert;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shop.app.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 签到
 * 
 * @author admin
 * 
 */
public class SignInActivity extends BaseActivity {
	private CalendarView calendar;
	private ImageButton calendarLeft;
	private TextView calendarCenter;
	private ImageButton calendarRight;
	private SimpleDateFormat format;
	Calendar myCalender;
	List<Date> signDates = new ArrayList<Date>();
	@ViewInject(R.id.barTxt)
	TextView barTxt;
	// 获取当月的 天数
	public static int getCurrentMonthDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	@Override
	protected void setMainLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_sign);
	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		calendarLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击上一月 同样返回年月
				String leftYearAndmonth = calendar.clickLeftMonth();
				String[] ya = leftYearAndmonth.split("-");
				calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
			}
		});

		calendarRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击下一月
				String rightYearAndmonth = calendar.clickRightMonth();
				String[] ya = rightYearAndmonth.split("-");
				calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
			}
		});
		calendar.setDates(signDates);
		calendar.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void OnItemClick(Date selectedStartDate,
					Date selectedEndDate, Date downDate) {
				/**
				 * 签到(今天)
				 */
				if (format.format(myCalender.getTime()).equals(
						format.format(downDate))) {
					/**
					 * 第一次签到，发送到服务器上去，有返回成功，在user中增加积分，本地保存， 再次签到，本地查询如果有记录，则提示
					 * 不能重复签到
					 */
					showShortToast("签到");
					//signDates.add(downDate);
					//calendar.setDates(signDates);
				}
			}
		});
	}
	@Override
	protected void initBeforeData() {
		// TODO Auto-generated method stub
		barTxt.setText("我要签到");
		format = new SimpleDateFormat("yyyy-MM-dd");
		Set<SignIn> siginSet = new HashSet<SignIn>();
		try {
			siginSet.add(new SignIn("签到表Id", format.parse("2015-09-01"),7,"已签到"));
			siginSet.add(new SignIn("签到表Id", format.parse("2015-09-04"),7,"已签到"));
			siginSet.add(new SignIn("签到表Id", format.parse("2015-09-08"),7,"已签到"));
			siginSet.add(new SignIn("签到表Id", format.parse("2015-09-010"),7,"已签到"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		for (SignIn sign : siginSet) {
			signDates.add(DateConvert.convertSignIn(sign.getSignDate()));
		}
		myCalender = Calendar.getInstance();
		// 获取日历控件对象
		calendar = (CalendarView) findViewById(R.id.calendar);
		calendar.setSelectMore(false); // 单选

		calendarLeft = (ImageButton) findViewById(R.id.calendarLeft);
		calendarCenter = (TextView) findViewById(R.id.calendarCenter);
		calendarRight = (ImageButton) findViewById(R.id.calendarRight);
		try {
			// 设置日历日期
			Date date = format.parse("2015-01-01");
			calendar.setCalendarData(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
		String[] ya = calendar.getYearAndmonth().split("-");
		calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
	}

	@Override
	protected void initAfterData() {
		// TODO Auto-generated method stub
	}

	@Override
	public void back(View view) {
		// TODO Auto-generated method stub
		this.finish();
	}
}
