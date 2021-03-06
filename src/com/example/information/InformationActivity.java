package com.example.information;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.example.arthighlights.ArtHighlightsActivity;
import com.example.eagerness.EagernessActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.filmmuseum.R.layout;
import com.example.filmmuseum.R.menu;
import com.example.navigation.GlanceActivity;
import com.example.navigation.HighFloorActivity;
import com.example.navigation.NavigationActivity;
import com.example.navigation.RouteActivity;
import com.example.screening.FutureScreeningActivity;
import com.example.screening.NowScreeningActivity;
import com.example.screening.ReviewScreeningActivity;
import com.example.screening.ScreeningActivity;
import com.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class InformationActivity extends Activity implements OnClickListener {

	private TextView tv;

	private ImageView ivReturn;

	private ListView lv;
	
	private SlidingMenu menu;
	private ImageView ivMenu;
	
	// 参观资讯
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.information);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("参观资讯");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				menu = null;
				list = null;
				overridePendingTransition(R.anim.a2,R.anim.a1);
			}
		});
		lv = (ListView) findViewById(R.id.lv_information);
		SimpleAdapter adp = new SimpleAdapter(getApplicationContext(),
				getDate(), R.layout.item_information, new String[] { "title" },
				new int[] { R.id.tv_information });
		lv.setAdapter(adp);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent=new Intent();
				switch (position) {
				//博物馆简介
				case 0:
					intent.setClass(InformationActivity.this, IntroductionActivity.class);
					startActivity(intent);
					break;
				//开放时间
				case 1:
					intent.setClass(InformationActivity.this, BusinessActivity.class);
					startActivity(intent);
					break;
				//购票指南
				case 2:
					intent.setClass(InformationActivity.this, TicketActivity.class);
					startActivity(intent);
					break;
				//配套服务
				case 3:
					intent.setClass(InformationActivity.this, SupServicesActivity.class);
					startActivity(intent);
					break;
				//参观须知
				case 4:
					intent.setClass(InformationActivity.this, VisitActivity.class);
					startActivity(intent);
					break;
				//加入我们
				case 5:
					intent.setClass(InformationActivity.this, ConstitutionActivity.class);
					startActivity(intent);
					break;
				//联系方式
				case 6:
					intent.setClass(InformationActivity.this, ContactActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.setBehindOffsetRes);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		View view = LayoutInflater.from(this)
				.inflate(R.layout.menu_right, null);
		view.findViewById(R.id.textView1).getWidth();
		menu.setMenu(view);
		ivMenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				menu.toggle();
			}
		});
		// 艺术亮点
		view.findViewById(R.id.btn_art).setOnClickListener(this);
		// 先睹为快
		view.findViewById(R.id.btn_eag).setOnClickListener(this);
		// 展馆导航
		view.findViewById(R.id.btn_navigation).setOnClickListener(this);
		// 博物馆楼层图
		view.findViewById(R.id.btn_flo).setOnClickListener(this);
		// 速览
		view.findViewById(R.id.btn_glance).setOnClickListener(this);
		// 参观路线
		view.findViewById(R.id.btn_route).setOnClickListener(this);
		// 展映活动
		view.findViewById(R.id.btn_screening).setOnClickListener(this);
		// 当前展映
		view.findViewById(R.id.btn_exhibition).setOnClickListener(this);
		// 展映回顾
		view.findViewById(R.id.btn_review).setOnClickListener(this);
		// 展映计划
		view.findViewById(R.id.btn_program).setOnClickListener(this);
		// 参观资讯
		view.findViewById(R.id.btn_information).setOnClickListener(this);
		// 博物馆简介
		view.findViewById(R.id.btn_museum).setOnClickListener(this);
		// 开放时间
		view.findViewById(R.id.btn_business).setOnClickListener(this);
		// 购票指南
		view.findViewById(R.id.btn_guide).setOnClickListener(this);
		// 配套服务
		view.findViewById(R.id.btn_supporting).setOnClickListener(this);
		// 参观须知
		view.findViewById(R.id.btn_notes).setOnClickListener(this);
		// 加入我们
		view.findViewById(R.id.btn_join).setOnClickListener(this);
		// 联系方式
		view.findViewById(R.id.btn_phone).setOnClickListener(this);

	}

	public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		// 艺术亮点
		case R.id.btn_art:
			intent.setClass(getApplicationContext(),
					ArtHighlightsActivity.class);
			startActivity(intent);
			finish();
			break;
		// 先睹为快
		case R.id.btn_eag:
			intent.setClass(getApplicationContext(), EagernessActivity.class);
			startActivity(intent);
			finish();
			break;
		// 展馆导航
		case R.id.btn_navigation:
			intent.setClass(getApplicationContext(), NavigationActivity.class);
			startActivity(intent);
			finish();
			break;
		// 博物馆楼层图
		case R.id.btn_flo:
			intent.setClass(getApplicationContext(), HighFloorActivity.class);
			startActivity(intent);
			finish();
			break;
		// 速览
		case R.id.btn_glance:
			intent.setClass(getApplicationContext(), GlanceActivity.class);
			startActivity(intent);
			finish();
			break;
		// 参观路线
		case R.id.btn_route:
			intent.setClass(getApplicationContext(), RouteActivity.class);
			startActivity(intent);
			finish();
			break;
		// 展映活动
		case R.id.btn_screening:
			intent.setClass(getApplicationContext(), ScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// 当前展映
		case R.id.btn_exhibition:
			intent.setClass(getApplicationContext(), NowScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// 展映回顾
		case R.id.btn_review:
			intent.setClass(getApplicationContext(),
					ReviewScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// 展映计划
		case R.id.btn_program:
			intent.setClass(getApplicationContext(),
					FutureScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// 参观资讯
		case R.id.btn_information:
			intent.setClass(getApplicationContext(), InformationActivity.class);
			startActivity(intent);
			finish();
			break;
		// 博物馆简介
		case R.id.btn_museum:
			intent.setClass(getApplicationContext(), IntroductionActivity.class);
			startActivity(intent);
			finish();
			break;
		// 开放时间
		case R.id.btn_business:
			intent.setClass(getApplicationContext(), BusinessActivity.class);
			startActivity(intent);
			finish();
			break;
		// 购票指南
		case R.id.btn_guide:
			intent.setClass(getApplicationContext(), TicketActivity.class);
			startActivity(intent);
			finish();
			break;
		// 配套服务
		case R.id.btn_supporting:
			intent.setClass(getApplicationContext(), SupServicesActivity.class);
			startActivity(intent);
			finish();
			break;
		// 参观须知
		case R.id.btn_notes:
			intent.setClass(getApplicationContext(), VisitActivity.class);
			startActivity(intent);
			finish();
			break;
		// 加入我们
		case R.id.btn_join:
			intent.setClass(getApplicationContext(), JoinActivity.class);
			startActivity(intent);
			finish();
			break;
		// 联系方式
		case R.id.btn_phone:
			intent.setClass(getApplicationContext(), ContactActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	// 菜单键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU)
		{
			menu.toggle();
			return true;
		}
		if(keyCode == event.KEYCODE_BACK)
		{
			Timer exit = null;
			if(isExit == false)
			{
				isExit = true;
				Toast.makeText(getApplicationContext(),"再按一次推出程序",Toast.LENGTH_SHORT).show();
				exit=new Timer();
				exit.schedule(new TimerTask() {
					public void run() {
						isExit = false;
					}
				}, 2000);
			}else{
				finish();
				SysApplication.getInstance().exit();
			}
		}
		return false;
	}
	private static boolean isExit = false;

	private List<Map<String, Object>> list;
	public List<Map<String, Object>> getDate() {
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "博物馆简介");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "开放时间");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "购票指南");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "配套服务");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "参观须知");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "加入我们");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "联系方式");
		list.add(map);
		return list;
	}
	protected void onStop() {
		super.onStop();
	}
	
	protected void onDestroy() {
		list = null;
		menu = null;
		System.gc();
		super.onDestroy();
	}
}	
