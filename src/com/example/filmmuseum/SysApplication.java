package com.example.filmmuseum;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class SysApplication extends Application {
	//定义list集合保存activity
	private List<Activity> mList = new LinkedList<Activity>();
	//为了实现每次使用该类时不创建新的对象而创建的静态对象
	private static SysApplication instance;
	//构造方法
	public SysApplication() {}
	//实例化一次
	public synchronized static SysApplication getInstance()
	{
		if(null == instance)
		{
			instance = new SysApplication();
		}
		return instance;
	}
	//将activity装入list集合
	public void addActivity(Activity activity)
	{
		mList.add(activity);
	}
	
	//关闭所有的activity
	public void exit()
	{
		try {
			for(Activity activity:mList)
			{
				if(activity != null)
				{
					activity.finish();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			System.exit(0);
		}
	}
	
	public void onLowMemory()
	{
		super.onLowMemory();
		System.gc();
	}
}
