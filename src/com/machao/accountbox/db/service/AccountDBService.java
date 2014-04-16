package com.machao.accountbox.db.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.machao.accountbox.db.ABOpenHelper;
import com.machao.accountbox.db.service.model.Account;
import com.machao.accountbox.db.service.model.AccountGroup;
import com.machao.accountbox.db.service.model.AccountPinned;

public class AccountDBService {
	SQLiteDatabase db;
	TypeDBService typeDB;

	public AccountDBService(Context context) {
		ABOpenHelper helper = new ABOpenHelper(context);
		db = helper.getWritableDatabase();
		typeDB = new TypeDBService(context);
	}

	public List<Account> findAccountByUsername(String username) {
		List<Account> accounts = new ArrayList<Account>();
		Account ac = null;
		Cursor cursor = db.rawQuery("select * from account where username = ?",
				new String[] { username });
		while (cursor.moveToNext()) {
			ac = new Account();
			ac.setUsername(username);
			ac.setName(cursor.getString(cursor.getColumnIndex("name")));
			ac.setTypeName(cursor.getString(cursor.getColumnIndex("typename")));
			ac.setTypeParentIdx(cursor.getInt(cursor
					.getColumnIndex("typeparentidx")));
			ac.setTypeChildIdx(cursor.getInt(cursor
					.getColumnIndex("typechildidx")));
			ac.setWeb(cursor.getString(cursor.getColumnIndex("web")));
			ac.setAccount(cursor.getString(cursor.getColumnIndex("account")));
			ac.setPassword(cursor.getString(cursor.getColumnIndex("password")));
			ac.setFindbackway(cursor.getString(cursor
					.getColumnIndex("findbackway")));
			ac.setGameServer(cursor.getString(cursor
					.getColumnIndex("gameserver")));
			ac.setLock(cursor.getInt(cursor.getColumnIndex("lock")));
			ac.setImgIdx(cursor.getInt(cursor.getColumnIndex("imgidx")));
			ac.setBz(cursor.getString(cursor.getColumnIndex("bz")));
			accounts.add(ac);
		}
		return accounts;
	}

	public List<AccountPinned> findAccountByUsername2(String username) {
		List<AccountPinned> accounts = new ArrayList<AccountPinned>();
		List<Integer> parentIds = new ArrayList<Integer>();
		int sectionPosition = 0, listPosition = 0;
		Cursor cursor = db
				.rawQuery(
						"select  typeparentidx from account where username = ? group by typeparentidx",
						new String[] { username });
		while (cursor.moveToNext()) {
			parentIds.add(cursor.getInt(0));
		}

		for (Integer i : parentIds) {
			AccountPinned section = new AccountPinned();
			section.type = AccountPinned.SECTION;
			section.setName(typeDB.getTypeNameByIdx(i));
			section.setImgIdx(typeDB.getImgIdxByIdx(i));
			section.setUsername(username);
			section.sectionPosition = sectionPosition;
			section.listPosition = listPosition++;
			accounts.add(section);
			cursor = db
					.rawQuery(
							"select * from account where username = ? and typeparentidx = ?",
							new String[] { username, String.valueOf(i) });
			while (cursor.moveToNext()) {
				AccountPinned item = new AccountPinned();
				item.type = AccountPinned.ITEM;
				item.setUsername(username);
				item.setName(cursor.getString(cursor.getColumnIndex("name")));
				item.setTypeName(cursor.getString(cursor
						.getColumnIndex("typename")));
				item.setTypeParentIdx(cursor.getInt(cursor
						.getColumnIndex("typeparentidx")));
				item.setTypeChildIdx(cursor.getInt(cursor
						.getColumnIndex("typechildidx")));
				item.setWeb(cursor.getString(cursor.getColumnIndex("web")));
				item.setAccount(cursor.getString(cursor
						.getColumnIndex("account")));
				item.setPassword(cursor.getString(cursor
						.getColumnIndex("password")));
				item.setFindbackway(cursor.getString(cursor
						.getColumnIndex("findbackway")));
				item.setGameServer(cursor.getString(cursor
						.getColumnIndex("gameserver")));
				item.setLock(cursor.getInt(cursor.getColumnIndex("lock")));
				item.setImgIdx(cursor.getInt(cursor.getColumnIndex("imgidx")));
				item.setBz(cursor.getString(cursor.getColumnIndex("bz")));
				item.sectionPosition = sectionPosition;
				item.listPosition = listPosition++;
				accounts.add(item);
			}
			sectionPosition++;
		}

		return accounts;
	}

	// idx autoincrement, username , name , typename , typeparentidx integer,
	// typechildidx integer, typeparentselectedindex integer,
	// typechildselectedindex integer, web , account , password , findbackway ,
	// gameserver , lock integer, imgidx integer, bz
	public void updateAccount(Account account) {
		Log.i("a", "修改的数据内容 : " + account.toString());
		db.execSQL(
				"update account set name = ?, typename = ?, typeparentidx = ?, typechildidx = ?, typeparentselectedindex = ?, typechildselectedindex = ?, web = ?, account = ? , password = ? , findbackway = ? , gameserver = ?, lock = ?, imgidx = ?, bz = ? where idx = ?",
				new String[] { account.getName(), account.getTypeName(),
						String.valueOf(account.getTypeParentIdx()),
						String.valueOf(account.getTypeChildIdx()),
						String.valueOf(account.getTypeParentSelectedIndex()),
						String.valueOf(account.getTypeChildSelectedIndex()),
						account.getWeb(), account.getAccount(),
						account.getPassword(), account.getFindbackway(),
						account.getGameServer(),
						String.valueOf(account.getLock()),
						String.valueOf(account.getImgIdx()), account.getBz(),
						String.valueOf(account.getIdx()) });
	}

	public List<AccountGroup> findAccountByUsername3(String username) {
		List<AccountGroup> ags = new ArrayList<AccountGroup>();
		AccountGroup ag = null;
		List<Account> as = new ArrayList<Account>();
		Account ac = null;
		List<Integer> parentIds = new ArrayList<Integer>();
		Cursor cursor = db
				.rawQuery(
						"select  typeparentidx from account where username = ? group by typeparentidx",
						new String[] { username });
		while (cursor.moveToNext()) {
			parentIds.add(cursor.getInt(0));
		}
		for (int i : parentIds) {
			ag = new AccountGroup();
			ag.setGroupName(typeDB.getTypeNameByIdx(i));
			ag.setGroupImgIdx(typeDB.getImgIdxByIdx(i));
			cursor = db
					.rawQuery(
							"select * from account where username = ? and typeparentidx = ?",
							new String[] { username, String.valueOf(i) });
			while (cursor.moveToNext()) {
				ac = new Account();
				ac.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
				ac.setUsername(username);
				ac.setName(cursor.getString(cursor.getColumnIndex("name")));
				ac.setTypeName(cursor.getString(cursor
						.getColumnIndex("typename")));
				ac.setTypeParentIdx(cursor.getInt(cursor
						.getColumnIndex("typeparentidx")));
				ac.setTypeChildIdx(cursor.getInt(cursor
						.getColumnIndex("typechildidx")));
				ac.setTypeParentSelectedIndex(cursor.getInt(cursor
						.getColumnIndex("typeparentselectedindex")));
				ac.setTypeChildSelectedIndex(cursor.getInt(cursor
						.getColumnIndex("typechildselectedindex")));
				ac.setWeb(cursor.getString(cursor.getColumnIndex("web")));
				ac.setAccount(cursor.getString(cursor.getColumnIndex("account")));
				ac.setPassword(cursor.getString(cursor
						.getColumnIndex("password")));
				ac.setFindbackway(cursor.getString(cursor
						.getColumnIndex("findbackway")));
				ac.setGameServer(cursor.getString(cursor
						.getColumnIndex("gameserver")));
				ac.setLock(cursor.getInt(cursor.getColumnIndex("lock")));
				ac.setImgIdx(cursor.getInt(cursor.getColumnIndex("imgidx")));
				ac.setBz(cursor.getString(cursor.getColumnIndex("bz")));
				ag.getAccounts().add(ac);
			}
			ags.add(ag);
		}
		return ags;

	}

	public Long getCount(String username) {
		Cursor cursor = db.rawQuery("select count(*) from account where username = ?", new String[]{username});
		cursor.moveToFirst();
		return cursor.getLong(0);
	}

	public boolean exitsByName(String name, String username) {
		Cursor cursor = db.rawQuery(
				"select count(*) from account where name = ? and username = ?",
				new String[] { name, username });
		cursor.moveToNext();
		Long count = cursor.getLong(0);
		return count > 0 ? true : false;
	}

	// idx autoincrement, username , name , typename , typeparentidx integer,
	// typechildidx integer, typeparentselectedindex integer,
	// typechildselectedindex integer, web , account , password , findbackway ,
	// gameserver , lock integer, imgidx integer, bz
	public void saveAccount(String username, Account account) {
		Log.i("a", "添加账户信息 : " + account.toString());
		db.execSQL(
				"insert into account (username, name, typename, typeparentidx, typechildidx, typeparentselectedindex, typechildselectedindex, web, account, password, findbackway, gameserver, lock, imgidx, bz) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				new String[] { username, account.getName(),
						account.getTypeName(),
						String.valueOf(account.getTypeParentIdx()),
						String.valueOf(account.getTypeChildIdx()),
						String.valueOf(account.getTypeParentSelectedIndex()),
						String.valueOf(account.getTypeChildSelectedIndex()),
						account.getWeb(), account.getAccount(),
						account.getPassword(), account.getFindbackway(),
						account.getGameServer(),
						String.valueOf(account.getLock()),
						String.valueOf(account.getImgIdx()), account.getBz() });
	}

	public void deleteAccountByIdx(int idx) {
		db.execSQL("delete from account where idx = ?",
				new String[] { String.valueOf(idx) });
	}

	public List<String> getAccountNames(String username) {
		List<String> accountNames = new ArrayList<String>();
		Cursor cursor = db.rawQuery(
				"select name from account where username = ?",
				new String[] { username });
		while (cursor.moveToNext()) {
			accountNames.add(cursor.getString(0));
		}
		return accountNames;

	}

	public List<Account> queryAccountByKeyword(String username, String keyword) {
		List<Account> accounts = new ArrayList<Account>();
		Account ac = null;
		String dateParam = "%" + keyword + "%";
		Cursor cursor = db.rawQuery("select * from account where name like ? and username = ?",
				new String[] { dateParam, username });
		while (cursor.moveToNext()) {
			ac = new Account();
			ac.setIdx(cursor.getInt(cursor.getColumnIndex("idx")));
			ac.setUsername(username);
			ac.setName(cursor.getString(cursor.getColumnIndex("name")));
			ac.setTypeName(cursor.getString(cursor.getColumnIndex("typename")));
			ac.setTypeParentIdx(cursor.getInt(cursor
					.getColumnIndex("typeparentidx")));
			ac.setTypeChildIdx(cursor.getInt(cursor
					.getColumnIndex("typechildidx")));
			ac.setTypeParentSelectedIndex(cursor.getInt(cursor
					.getColumnIndex("typeparentselectedindex")));
			ac.setTypeChildSelectedIndex(cursor.getInt(cursor
					.getColumnIndex("typechildselectedindex")));
			ac.setWeb(cursor.getString(cursor.getColumnIndex("web")));
			ac.setAccount(cursor.getString(cursor.getColumnIndex("account")));
			ac.setPassword(cursor.getString(cursor.getColumnIndex("password")));
			ac.setFindbackway(cursor.getString(cursor
					.getColumnIndex("findbackway")));
			ac.setGameServer(cursor.getString(cursor
					.getColumnIndex("gameserver")));
			ac.setLock(cursor.getInt(cursor.getColumnIndex("lock")));
			ac.setImgIdx(cursor.getInt(cursor.getColumnIndex("imgidx")));
			ac.setBz(cursor.getString(cursor.getColumnIndex("bz")));
			accounts.add(ac);
		}
		return accounts;
	}

	public void closeDB() {
		db.close();
		typeDB.closeDB();
		typeDB = null;
		db = null;
	}
}
