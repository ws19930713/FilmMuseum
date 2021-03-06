package com.example.screening;

import java.util.Timer;
import java.util.TimerTask;

import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.filmmuseum.R.layout;
import com.example.filmmuseum.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DragonActivity extends Activity {

	private TextView tv;
	
	private ImageView iv1,iv2,iv3;
	private Bitmap bm1,bm2,bm3;
	
	private ImageView ivReturn,ivMenu;

	// 鱼龙勇士
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dragon);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("《鱼龙勇士》");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		
		iv1=(ImageView) findViewById(R.id.iv_dragon);
		iv2=(ImageView) findViewById(R.id.iv1_dragon);
		iv3=(ImageView) findViewById(R.id.iv2_dragon);
		bm1=BitmapFactory.decodeResource(getResources(), R.drawable.dragons);
		bm2=BitmapFactory.decodeResource(getResources(), R.drawable.suki);
		bm3=BitmapFactory.decodeResource(getResources(), R.drawable.down);
		iv1.setImageBitmap(bm1);
		iv2.setImageBitmap(bm2);
		iv3.setImageBitmap(bm3);
		
		
	}

	// 退出程序
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			Timer exit = null;
			if (isExit == false) {
				isExit = true;
				Toast.makeText(getApplicationContext(), "再按一次推出程序",
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
	protected void onStop() {
		super.onStop();
	}
	
	protected void onDestroy() {
		Log.v("HTTWs", "DragonActivity进入ondestroy");
		if(!(bm1.isRecycled()))
		{
			bm1.recycle();
			System.gc();
		}
		if(!(bm2.isRecycled()))
		{
			bm2.recycle();
			System.gc();
		}
		if(!(bm3.isRecycled()))
		{
			bm3.recycle();
			System.gc();
		}
		super.onDestroy();
	}
}
