package com.example.eagerness;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.arthighlights.ArtHighlightsActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.information.*;
import com.example.navigation.GlanceActivity;
import com.example.navigation.HighFloorActivity;
import com.example.navigation.NavigationActivity;
import com.example.navigation.RouteActivity;
import com.example.screening.FutureScreeningActivity;
import com.example.screening.NowScreeningActivity;
import com.example.screening.ReviewScreeningActivity;
import com.example.screening.ScreeningActivity;
import com.example.util.ArtMenu;
import com.example.util.Download;
import com.slidingmenu.lib.SlidingMenu;

import java.util.*;

public class EagernessActivity extends Activity implements View.OnClickListener {

	private TextView tv;

	private ImageView ivReturn;

	private ListView lv;

	private SlidingMenu menu;
	private ImageView ivMenu;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ر�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.eagerness);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("�ȶ�Ϊ��");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		lv = (ListView) findViewById(R.id.lv_eagerness);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				getList(), R.layout.item_eagerness, new String[] { "image",
						"title", "image2" }, new int[] { R.id.iv_eagerness,
						R.id.tv_eagerness, R.id.iv3_eagerness });
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Download dow = new Download();
				List<ArtMenu> menu = dow.readMenuXml(getExternalStoragePath()
						+ "/FilmMuseum/system/menu2.xml");
				Intent intent = new Intent();
				for(ArtMenu m:menu)
				{
					Bundle bundle = new Bundle();
					bundle.putString("src", m.getSrc());
					intent.putExtras(bundle);
					intent.setClass(EagernessActivity.this,MovieActivity.class);
					startActivity(intent);
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
		// ��������
		view.findViewById(R.id.btn_art).setOnClickListener(this);
		// �ȶ�Ϊ��
		view.findViewById(R.id.btn_eag).setOnClickListener(this);
		// չ�ݵ���
		view.findViewById(R.id.btn_navigation).setOnClickListener(this);
		// �����¥��ͼ
		view.findViewById(R.id.btn_flo).setOnClickListener(this);
		// ����
		view.findViewById(R.id.btn_glance).setOnClickListener(this);
		// �ι�·��
		view.findViewById(R.id.btn_route).setOnClickListener(this);
		// չӳ�
		view.findViewById(R.id.btn_screening).setOnClickListener(this);
		// ��ǰչӳ
		view.findViewById(R.id.btn_exhibition).setOnClickListener(this);
		// չӳ�ع�
		view.findViewById(R.id.btn_review).setOnClickListener(this);
		// չӳ�ƻ�
		view.findViewById(R.id.btn_program).setOnClickListener(this);
		// �ι���Ѷ
		view.findViewById(R.id.btn_information).setOnClickListener(this);
		// ����ݼ��
		view.findViewById(R.id.btn_museum).setOnClickListener(this);
		// ����ʱ��
		view.findViewById(R.id.btn_business).setOnClickListener(this);
		// ��Ʊָ��
		view.findViewById(R.id.btn_guide).setOnClickListener(this);
		// ���׷���
		view.findViewById(R.id.btn_supporting).setOnClickListener(this);
		// �ι���֪
		view.findViewById(R.id.btn_notes).setOnClickListener(this);
		// ��������
		view.findViewById(R.id.btn_join).setOnClickListener(this);
		// ��ϵ��ʽ
		view.findViewById(R.id.btn_phone).setOnClickListener(this);

	}

	// ��ȡsd����·��
	public static String getExternalStoragePath() {
		String state = android.os.Environment.getExternalStorageState();
		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
			if (android.os.Environment.getExternalStorageDirectory().canRead()) {
				return android.os.Environment.getExternalStorageDirectory()
						.getPath();
			}
		}
		return null;
	}

	public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		// ��������
		case R.id.btn_art:
			intent.setClass(getApplicationContext(),
					ArtHighlightsActivity.class);
			startActivity(intent);
			finish();
			break;
		// �ȶ�Ϊ��
		case R.id.btn_eag:
			intent.setClass(getApplicationContext(), EagernessActivity.class);
			startActivity(intent);
			finish();
			break;
		// չ�ݵ���
		case R.id.btn_navigation:
			intent.setClass(getApplicationContext(), NavigationActivity.class);
			startActivity(intent);
			finish();
			break;
		// �����¥��ͼ
		case R.id.btn_flo:
			intent.setClass(getApplicationContext(), HighFloorActivity.class);
			startActivity(intent);
			finish();
			break;
		// ����
		case R.id.btn_glance:
			intent.setClass(getApplicationContext(), GlanceActivity.class);
			startActivity(intent);
			finish();
			break;
		// �ι�·��
		case R.id.btn_route:
			intent.setClass(getApplicationContext(), RouteActivity.class);
			startActivity(intent);
			finish();
			break;
		// չӳ�
		case R.id.btn_screening:
			intent.setClass(getApplicationContext(), ScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// ��ǰչӳ
		case R.id.btn_exhibition:
			intent.setClass(getApplicationContext(), NowScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// չӳ�ع�
		case R.id.btn_review:
			intent.setClass(getApplicationContext(),
					ReviewScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// չӳ�ƻ�
		case R.id.btn_program:
			intent.setClass(getApplicationContext(),
					FutureScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// �ι���Ѷ
		case R.id.btn_information:
			intent.setClass(getApplicationContext(), InformationActivity.class);
			startActivity(intent);
			finish();
			break;
		// ����ݼ��
		case R.id.btn_museum:
			intent.setClass(getApplicationContext(), IntroductionActivity.class);
			startActivity(intent);
			finish();
			break;
		// ����ʱ��
		case R.id.btn_business:
			intent.setClass(getApplicationContext(), BusinessActivity.class);
			startActivity(intent);
			finish();
			break;
		// ��Ʊָ��
		case R.id.btn_guide:
			intent.setClass(getApplicationContext(), TicketActivity.class);
			startActivity(intent);
			finish();
			break;
		// ���׷���
		case R.id.btn_supporting:
			intent.setClass(getApplicationContext(), SupServicesActivity.class);
			startActivity(intent);
			finish();
			break;
		// �ι���֪
		case R.id.btn_notes:
			intent.setClass(getApplicationContext(), VisitActivity.class);
			startActivity(intent);
			finish();
			break;
		// ��������
		case R.id.btn_join:
			intent.setClass(getApplicationContext(), JoinActivity.class);
			startActivity(intent);
			finish();
			break;
		// ��ϵ��ʽ
		case R.id.btn_phone:
			intent.setClass(getApplicationContext(), ContactActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	// �˵���
	@SuppressWarnings("static-access")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			menu.toggle();
			return true;
		}
		if (keyCode == event.KEYCODE_BACK) {
			Timer exit = null;
			if (isExit == false) {
				isExit = true;
				Toast.makeText(getApplicationContext(), "�ٰ�һ���Ƴ�����",
						Toast.LENGTH_SHORT).show();
				exit = new Timer();
				exit.schedule(new TimerTask() {
					public void run() {
						isExit = false;
					}
				}, 2000);
			} else {
				finish();
				SysApplication.getInstance().exit();
			}
		}
		return false;
	}

	private static boolean isExit = false;
	private List<Map<String, Object>> list;
	public List<Map<String, Object>> getList() {
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("image", R.drawable.banner1);
		map.put("title", "΢��Ӱ");
		map.put("image2", R.drawable.play);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("image", R.drawable.banner2);
		map.put("title", "΢��Ӱ");
		map.put("image2", R.drawable.play);
		list.add(map);
		return list;
	}

	// ����ͼƬ�ķ���
	@SuppressWarnings("deprecation")
	public LayerDrawable initBitmap(Bitmap b1, Bitmap b2) {
		Drawable[] array = new Drawable[2];
		array[0] = new BitmapDrawable(b1);
		array[1] = new BitmapDrawable(b2);
		LayerDrawable la = new LayerDrawable(array);
		la.setLayerInset(0, 0, 0, 0, 0);
		la.setLayerInset(1, 20, 20, 20, 20);
		return la;
	}
	
	protected void onDestroy() {
		Log.v("HTTWs","--->Eagerness����ondestroy");
		if(list.size()!=0)
		{
			list = null;
			System.gc();
		}
		super.onDestroy();
	}
	
	protected void onStop() {
		Log.v("HTTWs","--->Eagerness����onstop");
		super.onStop();
	}
}