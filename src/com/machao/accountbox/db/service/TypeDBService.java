package com.machao.accountbox.db.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.machao.accountbox.db.ABOpenHelper;
import com.machao.accountbox.db.service.model.Type;

public class TypeDBService {
	SQLiteDatabase db;
	public TypeDBService(Context context) {
		ABOpenHelper helper = new ABOpenHelper(context);
		db = helper.getWritableDatabase();
	}
	
	public List<Type> getTypesByParentIdx(Integer parentIdx) {
		List<Type> types = new ArrayList<Type>();
		Type t = null;
		Cursor cursor = db.rawQuery("select * from type where parentidx = ?", new String[]{String.valueOf(parentIdx)});
		while(cursor.moveToNext()) {
			t = new Type();
			t.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			t.setName(cursor.getString(cursor.getColumnIndex("name")));
			t.setParentIdx(parentIdx);
			t.setImgId(cursor.getInt(cursor.getColumnIndex("imgidx")));
			types.add(t);
		}
		return types;
	}
	
	public boolean existByName(String name) {
		Cursor cursor = db.rawQuery("select count(*) from type where name = ?", new String[]{name});
		cursor.moveToNext();
		Long count = cursor.getLong(0);
		return count > 0 ? true : false;
	}
	
	public void saveType(Type t) {
		db.execSQL("insert into type (name, parentidx, imgidx) values (?, ?, ?)", new String[]{t.getName(), String.valueOf(t.getParentIdx()), String.valueOf(t.getImgId())});
	}

	public int getImgIdxByIdx(int idx) {
		int imgIdx = 0;
		Cursor cursor = db.rawQuery("select imgidx from type where idx = ?", new String[]{String.valueOf(idx)});
		if(cursor.moveToFirst()) {
			imgIdx = cursor.getInt(0);
		}
		return imgIdx;
	}
	
	public String getTypeNameByIdx(int idx) {
		String typeName = "";
		Cursor cursor = db.rawQuery("select name from type where idx = ?", new String[]{String.valueOf(idx)});
		if(cursor.moveToFirst()) {
			typeName = cursor.getString(0);
		}
		return typeName;
	}
	
	public void closeDB() {
		db.close();
		db = null;
	}
	
}

	



















