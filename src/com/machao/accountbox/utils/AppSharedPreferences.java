package com.machao.accountbox.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppSharedPreferences {
	SharedPreferences sp = null;
	public AppSharedPreferences(Context context, String name) {
		sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}
	
	public void setUsername(String username) {
		Editor editor = sp.edit();
		editor.putString("username", username);
		editor.commit();
	}
	
	public String getUsername() {
		return sp.getString("username", "");
	}
	
	public void setPassword(String password) {
		Editor editor = sp.edit();
		editor.putString("password", password);
		editor.commit();
	}
	
	public String getPassword() {
		return sp.getString("password", "");
	}
	
	public void setJiZhuMiMa(boolean flag) {
		Editor editor = sp.edit();
		editor.putBoolean("jzmm", flag);
		editor.commit();
	}
	
	public boolean getJiZhuMiMa() {
		return sp.getBoolean("jzmm", false);
	}
	
	public void setZiDongDengLu(boolean flag) {
		Editor editor = sp.edit();
		editor.putBoolean("zddl", flag);
		editor.commit();
	}
	
	public boolean getZiDongDengLu() {
		return sp.getBoolean("zddl", false);
	}
	
	public int getAccountMax() {
		return sp.getInt("max", 5);
	}
	
	public void setAccountMax() {
		Editor editor = sp.edit();
		editor.putInt("max", 99999);
		editor.commit();
	}
	
}















































