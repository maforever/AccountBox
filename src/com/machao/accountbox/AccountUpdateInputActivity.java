package com.machao.accountbox;

import net.youmi.android.diy.banner.DiyAdSize;
import net.youmi.android.diy.banner.DiyBanner;

import com.machao.accountbox.db.service.AccountDBService;
import com.machao.accountbox.db.service.TypeDBService;
import com.machao.accountbox.db.service.model.Account;
import com.machao.accountbox.utils.MyApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AccountUpdateInputActivity extends Activity {
	private Intent intent;
	private int parentSelectedIndex =0;
	private int childSelectedIndex = 0;
	private int childIdx = 1;
	private int parentIdx = 1;
	private String nameStr, typeName = "聊天通讯-QQ", findbackwayStr = "", bzStr = "", webStr, accountStr, passwordStr, gameserverStr;
	private TextView typeNameTv, findbackwayTv, bzTv;
	private LinearLayout webLayout, gameserverLayout, lockLayout;
	private CheckBox lockCb;
	private EditText nameEt, webEt, accountEt, passwordEt, gameserverEt;
	private AlertDialog ad;
	private AccountDBService accountDB;
	private TypeDBService typeDB;
	private MyApp myApp;
	private Account account;
	private String nameTemp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_account_updateinput);
		
		// 获取要嵌入迷你广告条的布局
		RelativeLayout adLayout=(RelativeLayout)findViewById(R.id.AdLayout);
		// Demo 1 迷你Banner : 宽满屏，高32dp
		// 传入宽度满屏，高度为 32dp 的 AdSize 来定义迷你 Banner
		DiyBanner banner = new DiyBanner(this, DiyAdSize.SIZE_MATCH_SCREENx32);
		// Demo 2 迷你Banner : 宽320dp，高32dp
		// 将积分 Banner 加入到布局中
		adLayout.addView(banner);
		
		accountDB = new AccountDBService(this);
		typeDB = new TypeDBService(this);
		myApp = (MyApp) this.getApplication();
		account = (Account) this.getIntent().getSerializableExtra("account");
		findViews();
	}
	
	private void findViews() {
		nameEt = (EditText) this.findViewById(R.id.name);
		typeNameTv = (TextView) this.findViewById(R.id.type);
		typeNameTv.setText(typeName);
		findbackwayTv = (TextView) this.findViewById(R.id.findbackway);
		bzTv = (TextView) this.findViewById(R.id.bz);
		webEt = (EditText) this.findViewById(R.id.web);
		accountEt = (EditText) this.findViewById(R.id.account);
		passwordEt = (EditText) this.findViewById(R.id.password);
		gameserverEt = (EditText) this.findViewById(R.id.gameserver);
		webLayout = (LinearLayout) this.findViewById(R.id.weblayout);
		gameserverLayout = (LinearLayout) this.findViewById(R.id.gameserverlayout);
		lockCb = (CheckBox) this.findViewById(R.id.lockCb);
		
		nameStr = account.getName();
		nameTemp = account.getName();
		typeName = account.getTypeName();
		parentIdx = account.getTypeParentIdx();
		childIdx = account.getTypeChildIdx();
		parentSelectedIndex = account.getTypeParentSelectedIndex();
		childSelectedIndex = account.getTypeChildSelectedIndex();
		webStr = account.getWeb();
		accountStr = account.getAccount();
		passwordStr = account.getPassword();
		gameserverStr = account.getGameServer();
		findbackwayStr = account.getFindbackway();
		bzStr = account.getBz();
		
		int lockInt = account.getLock();
		if(lockInt == 1) {
			lockCb.setChecked(true);
		}else {
			lockCb.setChecked(false);
		}
		
		if(parentIdx == 6) {
			webLayout.setVisibility(View.GONE);
			gameserverLayout.setVisibility(View.VISIBLE);
			gameserverEt.setText(gameserverStr);
		}else {
			webLayout.setVisibility(View.VISIBLE);
			gameserverLayout.setVisibility(View.GONE);
			webEt.setText(webStr);
		}
		
		nameEt.setText(nameStr);
		typeNameTv.setText(typeName);
		accountEt.setText(accountStr);
		passwordEt.setText(passwordStr);
		findbackwayTv.setText(findbackwayStr);
		bzTv.setText(bzStr);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.typeLayout:
			//选择类别
			intent = new Intent(AccountUpdateInputActivity.this, TypeListActivity.class);
			intent.putExtra("parentSelectedIndex", parentSelectedIndex);
			intent.putExtra("childSelectedIndex", childSelectedIndex);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
			break;
		case R.id.lockLayout:
			lockCb.toggle();
			break;
		case R.id.findbackwayLayout:
			intent = new Intent(AccountUpdateInputActivity.this, TextInputActivity.class);
			intent.putExtra("flag", "findbackway");
			intent.putExtra("title", "找回方式");
			intent.putExtra("editNum", 50);
			intent.putExtra("content", findbackwayStr);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
			break;
		case R.id.bzLayout:
			intent = new Intent(AccountUpdateInputActivity.this, TextInputActivity.class);
			intent.putExtra("flag", "bz");
			intent.putExtra("title", "备    注");
			intent.putExtra("editNum", 100);
			intent.putExtra("content", bzStr);
			startActivityForResult(intent, 100);
			overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
			break;
		case R.id.add:
			if(getAndValidateViewDatas()) {
				account.setName(nameStr);
				account.setTypeName(typeName);
				account.setTypeParentIdx(parentIdx);
				account.setTypeChildIdx(childIdx);
				account.setTypeParentSelectedIndex(parentSelectedIndex);
				account.setTypeChildSelectedIndex(childSelectedIndex);
				account.setWeb(webStr);
				account.setAccount(accountStr);
				account.setPassword(passwordStr);
				account.setFindbackway(findbackwayStr);
				account.setGameServer(gameserverStr);
				account.setBz(bzStr);
				int imgIdx = typeDB.getImgIdxByIdx(childIdx);
				if(imgIdx == 0) {
					imgIdx = R.drawable.img_empty;
				}
				
				account.setImgIdx(imgIdx);
				boolean isLock = lockCb.isChecked();
				if(isLock) {
					account.setLock(1);
					ad = new AlertDialog.Builder(this)
					.setTitle("提示")
					.setMessage("由于您勾选了安全保护，所以您今后查看账户信息的时候需要您输入提示问题的答案才能显示账户与密码，您仍勾选安全保护吗?")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							updateAccount();
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ad.dismiss();
						}
					})
					.create();
					ad.show();
				}else {
					account.setLock(0);
					updateAccount();
				}
			}
			break;
		}
	}
	
	private void updateAccount() {
		if(!nameTemp.equals(nameStr) && accountDB.exitsByName(nameStr, myApp.getUsername())) {
			Toast.makeText(this, "账户标题重复，请修改!", 0).show();
			nameEt.requestFocus();
		}else {
			accountDB.updateAccount(account);
			Toast.makeText(this, "修改账户成功!", 0).show();
			back();
		}
	}
	
	private boolean getAndValidateViewDatas() {
		nameStr = nameEt.getText().toString().trim();
		accountStr = accountEt.getText().toString().trim();
		passwordStr = passwordEt.getText().toString().trim();
		findbackwayStr = findbackwayTv.getText().toString().trim();
		webStr = webEt.getText().toString().trim();
		gameserverStr = gameserverEt.getText().toString().trim();
		if("".equals(nameStr)) {
			Toast.makeText(this, "账户标题不能为空!", 0).show();
			nameEt.requestFocus();
			return false;
		}
		if("".equals(accountStr)) {
			Toast.makeText(this, "账户号不能为空!", 0).show();
			accountEt.requestFocus();
			return false;
		}
		if("".equals(passwordStr)) {
			Toast.makeText(this, "密码不能为空!", 0).show();
			passwordEt.requestFocus();
			return false;
		}
		if(parentSelectedIndex == 6) {
			webStr = "";
		}else {
			gameserverStr = "";
		}
		
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 99) {
			parentSelectedIndex = data.getIntExtra("parentSelectedIndex", 0);
			childSelectedIndex = data.getIntExtra("childSelectedIndex", 0);
			typeName = data.getStringExtra("typeName");
			typeNameTv.setText(typeName);
			childIdx = data.getIntExtra("childIdx", 0);
			parentIdx = data.getIntExtra("parentIdx", 0);
			if(parentSelectedIndex == 6) {
				//选择了游戏
				gameserverLayout.setVisibility(View.VISIBLE);
				webLayout.setVisibility(View.GONE);
			}else {
				gameserverLayout.setVisibility(View.GONE);
				webLayout.setVisibility(View.VISIBLE);
			}
		}else if(resultCode == 98) {
			//findbackway
			findbackwayStr = data.getStringExtra("content");
			findbackwayTv.setText(findbackwayStr);
		}else if(resultCode == 97) {
			//bz
			bzStr = data.getStringExtra("content");
			bzTv.setText(bzStr);
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void back() {
		this.finish();
		overridePendingTransition(R.anim.activity_steady, R.anim.activity_out);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		back();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		accountDB.closeDB();
		typeDB.closeDB();
		accountDB = null;
		typeDB = null;
	}
	
}

