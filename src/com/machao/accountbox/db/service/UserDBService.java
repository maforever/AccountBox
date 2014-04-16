package com.machao.accountbox.db.service;

import com.machao.accountbox.db.ABOpenHelper;
import com.machao.accountbox.db.service.model.User;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebChromeClient.CustomViewCallback;

public class UserDBService {
	SQLiteDatabase db;
	public UserDBService(Context context) {
		ABOpenHelper openHelper = new ABOpenHelper(context);
		db = openHelper.getWritableDatabase();
	}
	
	public boolean isExistByUsername(String username) {
		Cursor cursor = db.rawQuery("select count(*) from users where username = ?", new String[]{username});
		cursor.moveToFirst();
		Long count = cursor.getLong(0);
		return count > 0 ? true : false;
	}
	
	public String getSafePasswordByUsername(String username) {
		Cursor cursor = db.rawQuery("select password2 from users where username = ?", new String[]{username});
		cursor.moveToNext();
		return cursor.getString(0);
	}
	
	public boolean Login(String username, String password) {
		Cursor cursor = db.rawQuery("select count(*) from users where username = ? and password = ?", new String[]{username, password});
		cursor.moveToFirst();
		Long count = cursor.getLong(0);
		return count > 0 ? true : false;
	}
	
	public void saveOrUpdateUser(User user) {
		if(isExistByUsername(user.getUsername())) {
			//修改
			db.execSQL("update users set password = ?, password2 = ? where username = ?", new String[]{user.getPassword(), user.getPassword2(), user.getUsername()});
		}else {
			//添加
			db.execSQL("insert into users (username, password, password2, questionindex, answer) values (?, ?, ?, ?, ?)", new Object[]{user.getUsername(), user.getPassword(), user.getPassword2(), user.getQuestionIndex(), user.getAnswer()}) ;
		}
	}
	
	public User findUserByUsername(String username) {
		User user = null;
		Cursor cursor = db.rawQuery("select * from users where username = ?", new String[]{username});
		if(cursor.moveToNext()) {
			user = new User();
			user.setUsername(username);
			user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
			user.setQuestionIndex(cursor.getInt(cursor.getColumnIndex("questionindex")));
			user.setAnswer(cursor.getString(cursor.getColumnIndex("answer")));
		}
		return user;
	}
	
	public void closeDB() {
		db.close();
		db = null;
	}
	
}






















