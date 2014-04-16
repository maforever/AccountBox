package com.machao.accountbox;

import net.youmi.android.diy.banner.DiyAdSize;
import net.youmi.android.diy.banner.DiyBanner;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.machao.accountbox.db.service.model.Account;

public class ActivityAccountDetail extends Activity {
	private Account account;
	private TextView nameTv, typeTv, webTv, accountTv, passwordTv, findBackWayTv, gameServerTv, bzTv;
	private CheckBox lockCb;
	private LinearLayout webLayout, gameServerLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_account_details);
		
		// 获取要嵌入迷你广告条的布局
		RelativeLayout adLayout=(RelativeLayout)findViewById(R.id.AdLayout);
		// Demo 1 迷你Banner : 宽满屏，高32dp
		// 传入宽度满屏，高度为 32dp 的 AdSize 来定义迷你 Banner
		DiyBanner banner = new DiyBanner(this, DiyAdSize.SIZE_MATCH_SCREENx32);
		// Demo 2 迷你Banner : 宽320dp，高32dp
		// 将积分 Banner 加入到布局中
		adLayout.addView(banner);
		account = (Account) this.getIntent().getSerializableExtra("account");
		
		findViews();
		initViewDatas();
	}
	
	private void findViews() {
		nameTv = (TextView) this.findViewById(R.id.name);
		typeTv = (TextView) this.findViewById(R.id.type);
		webTv = (TextView) this.findViewById(R.id.web);
		accountTv = (TextView) this.findViewById(R.id.account);
		passwordTv = (TextView) this.findViewById(R.id.password);
		findBackWayTv = (TextView) this.findViewById(R.id.findbackway);
		gameServerTv = (TextView) this.findViewById(R.id.bz);
		webLayout = (LinearLayout) this.findViewById(R.id.weblayout);
		gameServerLayout = (LinearLayout) this.findViewById(R.id.gameserverlayout);
		bzTv = (TextView) this.findViewById(R.id.bz);
		lockCb = (CheckBox) this.findViewById(R.id.lockCb);
	}
	
	private void initViewDatas() {
		
		String nameStr = account.getName();
		String typeStr = account.getTypeName();
		String webStr = account.getWeb();
		String accountStr = account.getAccount();
		String passwordStr = account.getPassword();
		String findBackWayStr = account.getFindbackway();
		String gameServerStr = account.getGameServer();
		String bzStr = account.getBz();
		
		nameTv.setText(nameStr);
		typeTv.setText(typeStr);
		accountTv.setText(accountStr);
		passwordTv.setText(passwordStr);
		findBackWayTv.setText(findBackWayStr);
		bzTv.setText(bzStr);
		
		
		int typeIdx = account.getTypeParentIdx();
		if(typeIdx == 6) {
			//游戏类型
			webLayout.setVisibility(View.GONE);
			gameServerTv.setText(gameServerStr);
		}else {
			webTv.setText(webStr);
			gameServerLayout.setVisibility(View.GONE);
		}
		
		
		int lock = account.getLock();
		if(lock == 1) {
			lockCb.setChecked(true);
		}
	}
	

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		}
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

}
