package com.machao.accountbox.db;

import com.machao.accountbox.utils.DBInitUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ABOpenHelper extends SQLiteOpenHelper {

	
	
	public ABOpenHelper(Context context) {
		super(context, "box.db", null, 1);
	}
	
	public ABOpenHelper(Context context, int versionId) {
		super(context, "box.db", null, versionId);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("a", "创建数据库");
		db.execSQL("create table if not exists users("
				+ "idx integer primary key autoincrement, username text, password text, password2 text, questionindex text, answer text"
				+ ")");
		

		db.execSQL("create table if not exists type("
				+ "idx integer primary key autoincrement, name text, parentidx integer, imgidx integer"
				+ ")");
		
		db.execSQL("create table if not exists account("
				+ "idx integer primary key autoincrement, username text, name text, typename text, typeparentidx integer, typechildidx integer, typeparentselectedindex integer, typechildselectedindex integer, web text, account text, password text, findbackway text, gameserver text, lock integer, imgidx integer, bz text"
				+ ")");
		
		
		DBInitUtil dbUtil = new DBInitUtil(db);
		dbUtil.init();
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("ALTER TABLE dbversion ADD COLUMN marktes integer");
	}

}
