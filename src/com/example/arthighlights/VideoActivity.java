package com.example.arthighlights;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.*;
import android.view.SurfaceHolder.Callback;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.util.ArtContent;
import com.example.util.ArtContentVideo;
import com.example.util.Download;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class VideoActivity extends Activity implements Callback,
		OnCompletionListener, OnErrorListener, OnInfoListener,
		OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener {

	private TextView tv, tv2, tv3;

	private ImageView iv1, iv2, iv3;

	private ImageView ivReturn, ivMenu;

	private SeekBar seekBar; // ������
	private upDateSeekBar update; // ���½�������
	private boolean flag = true; // �����ж���Ƶ�Ƿ��ڲ�����

	private String path = null; // ����·��
	// �����΢�� appid
	private static final String APP_ID = "wx6462caed59df1b17";
	private int x, y;
	private IWXAPI api;
	private PopupWindow pop;

	private Display currentDisplay;
	private SurfaceView surface;
	private SurfaceHolder holder;
	// ʹ��media������Ƶ
	private MediaPlayer mediaPlayer;
	// ��Ƶ�Ŀ���
	private int videoWidth = 0;
	private int videoHeight = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ر�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video);
		regToWx();
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		x = wm.getDefaultDisplay().getWidth();
		y = wm.getDefaultDisplay().getHeight();
		SysApplication.getInstance().addActivity(this);

		seekBar = (SeekBar) findViewById(R.id.sb_player);
		tv = (TextView) findViewById(R.id.tv_title);
		tv2 = (TextView) findViewById(R.id.tv_video);
		tv3 = (TextView) findViewById(R.id.tv2_video);
		iv1 = (ImageView) findViewById(R.id.iv_player);
		iv2 = (ImageView) findViewById(R.id.iv_suki);
		iv3 = (ImageView) findViewById(R.id.iv_download);

		tv.setText("������Ӱ������");
		Download dow = new Download();
		List<ArtContent> list = dow.readConXml(getExternalStoragePath()
				+ "/FilmMuseum/system/content.xml");
		List<ArtContentVideo> video = dow
				.readConXmlVideo(getExternalStoragePath()
						+ "/FilmMuseum/system/content3.xml");
		for (ArtContentVideo vi : video) {
			if (vi.getType().equals("image")) {
				if (vi.getId() == 1) {
					Bitmap bm = BitmapFactory
							.decodeFile(getExternalStoragePath()
									+ "/FilmMuseum/system/image/" + vi.getSrc());
					LayoutParams params = new LayoutParams(vi.getWidth(),
							vi.getHeight());
					iv1.setImageBitmap(bm);
					iv1.setLayoutParams(params);
				}
//				if (vi.getId() == 2) {
//					Bitmap bm = BitmapFactory
//							.decodeFile(getExternalStoragePath()
//									+ "/FilmMuseum/system/image/" + vi.getSrc());
//					iv2.setImageBitmap(bm);
//					iv2.setX(vi.getX());
//					iv2.setY(vi.getY());
//				}
//				if (vi.getId() == 3) {
//					Bitmap bm = BitmapFactory
//							.decodeFile(getExternalStoragePath()
//									+ "/FilmMuseum/system/image/" + vi.getSrc());
//					iv3.setImageBitmap(bm);
//					iv3.setX(vi.getX());
//					iv3.setY(vi.getY());
//				}
			}
			if (vi.getType().equals("text")) {
				if (vi.getId() == 4) {
					tv2.setX(vi.getX());
					tv2.setY(vi.getY());
					tv2.setWidth(vi.getWidth());
					tv2.setTextSize(vi.getTextsize());
				}
				if (vi.getId() == 5) {
					tv3.setX(vi.getX());
					tv3.setY(vi.getY());
					tv3.setWidth(vi.getWidth());
					tv3.setTextSize(vi.getTextsize());
				}
			}
		}
		Bundle bundle = getIntent().getExtras();
		int id = bundle.getInt("id");
		for (ArtContent art : list) {
			if (id == art.getId()) {
				tv.setText(art.getTitle());
				tv2.setText(art.getTitle());
				tv3.setText(art.getContent());
				path = getExternalStoragePath() + "/FilmMuseum/system/image/"
						+ art.getSrc();
			}
		}

		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);

		surface = (SurfaceView) findViewById(R.id.surface);
		holder = surface.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnSeekCompleteListener(this);
		mediaPlayer.setOnVideoSizeChangedListener(this);
		try {
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepare();
			mediaPlayer.start();
			update = new upDateSeekBar();
			new Thread(update).start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentDisplay = getWindowManager().getDefaultDisplay();
		iv1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (!mediaPlayer.isPlaying()) {
					mediaPlayer.start();
					// ���ſ�ʼ���ý���������
					new Thread(update).start();
					iv1.setImageResource(R.drawable.pause);
					flag = true;
				} else {
					mediaPlayer.pause();
					new Thread(update).start();
					iv1.setImageResource(R.drawable.player);
					flag = false;
				}
			}
		});
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer arg0) {
				iv1.setImageResource(R.drawable.player);
				seekBar.setProgress(0);
			}
		});

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

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (mediaPlayer == null) {
				flag = false;
			} else if (mediaPlayer.isPlaying() == true) {
				flag = true;
				int position = mediaPlayer.getCurrentPosition();
				int mMax = mediaPlayer.getDuration();
				int sMax = seekBar.getMax();
				seekBar.setProgress(position * sMax / mMax);
			} else {
				return;
			}
		};
	};

	@SuppressWarnings("static-access")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
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

	// ÿ�����һ�ν�����
	class upDateSeekBar implements Runnable {
		public void run() {
			mHandler.sendMessage(Message.obtain());
			if (flag) {
				mHandler.postDelayed(update, 1000);
			}
		}
	}

	private void showPopUp(View v) {
		View view = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.share, null);
		Button btn = (Button) view.findViewById(R.id.share_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (pop != null && pop.isShowing() == true) {
					pop.dismiss();
				}
			}
		});
		ImageView iv1 = (ImageView) view.findViewById(R.id.share_iv);
		iv1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.v("HTTWs", "000");
				String text = "΢�ŷ���";
				WXTextObject textObj = new WXTextObject();
				textObj.text = text;
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = textObj;
				msg.description = text;
				SendMessageToWX.Req req = new SendMessageToWX.Req();

				req.transaction = buildTransaction("text");
				req.message = msg;

				api.sendReq(req);

				SysApplication.getInstance().exit();
			}
		});
		ImageView iv2 = (ImageView) view.findViewById(R.id.share_iv2);
		iv2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl = "http://www.baidu.com";
				WXMediaMessage msg = new WXMediaMessage(webpage);
				msg.title = "����";
				msg.description = "made in HTTWs";
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = String.valueOf(System.currentTimeMillis());
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				api.sendReq(req);
			}
		});
		pop = new PopupWindow(view, LayoutParams.FILL_PARENT, 600);
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.showAtLocation(v, Gravity.NO_GRAVITY, 0, (int) y);
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	private void regToWx() {
		// ����WXAPIFactory��������ȡIWXAPI��ʵ��
		api = WXAPIFactory.createWXAPI(this, APP_ID, true);
		boolean bl = api.registerApp(APP_ID);
		Log.v("HTTWs", "bl " + bl);
	}

	protected void onDestroy() {
		Log.v("HTTWs", "VideoActivity����ondestroy");
		mediaPlayer.release();
		mediaPlayer = null;
		System.gc();
		super.onDestroy();
	}

	protected void onStop() {
		Log.v("HTTWs", "VideoActivity����onstop");
		mediaPlayer.stop();
		finish();
		super.onStop();
	}

	public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
	}

	public void onSeekComplete(MediaPlayer arg0) {
	}

	public void onPrepared(MediaPlayer arg0) {
		videoWidth = 1600;
		videoHeight = 1200;
		if (videoWidth > currentDisplay.getWidth()
				|| videoHeight > currentDisplay.getHeight()) {
			float heightRatio = (float) videoHeight
					/ (float) currentDisplay.getHeight();
			float widthRatio = (float) videoWidth
					/ (float) currentDisplay.getWidth();
			if (heightRatio > 1 || widthRatio > 1) {
				if (heightRatio > widthRatio) {
					videoHeight = (int) Math.ceil((float) videoHeight
							/ (float) heightRatio);
					videoWidth = (int) Math.ceil((float) videoWidth
							/ (float) heightRatio);
				} else {
					videoHeight = (int) Math.ceil((float) videoHeight
							/ (float) widthRatio);
					videoWidth = (int) Math.ceil((float) videoWidth
							/ (float) widthRatio);
				}
			}
		}
		seekBar.setX(100);
		seekBar.setY(videoHeight+20);
		iv1.setX(10);
		iv1.setY(videoHeight);
		surface.setLayoutParams(new RelativeLayout.LayoutParams(videoWidth,
				videoHeight));
		mediaPlayer.start();
	}

	public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
		return false;
	}

	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		return false;
	}

	public void onCompletion(MediaPlayer arg0) {
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		mediaPlayer.setDisplay(holder);
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
	}

}