package com.machao.accountbox;

import java.util.List;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.diy.banner.DiyAdSize;
import net.youmi.android.diy.banner.DiyBanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.machao.accountbox.adapter.AccountGroupExpandableListAdapter;
import com.machao.accountbox.db.service.AccountDBService;
import com.machao.accountbox.db.service.model.AccountGroup;
import com.machao.accountbox.dialog.ExitDialog;
import com.machao.accountbox.dialog.ShowYouMiOfferDialog;
import com.machao.accountbox.fragmenst.AccountCheckFragment;
import com.machao.accountbox.fragmenst.AccountListFargment;
import com.machao.accountbox.utils.AppSharedPreferences;
import com.machao.accountbox.utils.MyApp;

public class IndexActivity extends FragmentActivity{
	private AccountDBService accountDB;
	private int orangeColor = 0;
	private RadioButton listRb, findRb;
	private Intent intent;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private Fragment fragment;
	private RadioGroup rg;
	private AppSharedPreferences asp;
	private long currentCounts = 0l;
	private MyApp myApp;
	private TextView addBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_index);

		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// 获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
		// 将广告条加入到布局中
		adLayout.addView(adView);
		
		myApp = (MyApp) this.getApplication();
		asp = new AppSharedPreferences(this, "user");
		accountDB = new AccountDBService(this);
		currentCounts = accountDB.getCount(myApp.getUsername());
		fm = this.getSupportFragmentManager();
		
		findViews();
	}

	private void findViews() {
		orangeColor = this.getResources().getColor(R.color.orange);
		rg = (RadioGroup) this.findViewById(R.id.rg);
		listRb = (RadioButton) this.findViewById(R.id.listRb);
		listRb.setChecked(true);
		findRb = (RadioButton) this.findViewById(R.id.findRb);
		addBtn = (TextView) this.findViewById(R.id.add);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.listRb:
					listRb.setTextColor(Color.WHITE);
					findRb.setTextColor(orangeColor);
					ft = fm.beginTransaction();
					fragment = new AccountListFargment(accountDB);
					ft.replace(R.id.fragmentContainer, fragment);
					ft.commit();
					addBtn.setVisibility(View.VISIBLE);
					break;
				case R.id.findRb:
					listRb.setTextColor(orangeColor);
					findRb.setTextColor(Color.WHITE);
					ft = fm.beginTransaction();
					fragment = new AccountCheckFragment();
					ft.replace(R.id.fragmentContainer, fragment);
					ft.commit();
					addBtn.setVisibility(View.GONE);
					break;
				}
			}
		});
		
		
		ft = fm.beginTransaction();
		fragment = new AccountListFargment(accountDB);
		ft.replace(R.id.fragmentContainer, fragment);
		ft.commit();
	}


	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.add:
		case R.id.emptyLayout:
			int max = asp.getAccountMax();
			currentCounts = accountDB.getCount(myApp.getUsername());
			if(currentCounts < max) {
				goAddInput();
			}else {
				
				int myPointBalance = net.youmi.android.offers.PointsManager.getInstance(this).queryPoints();
				Log.i("a", "积分 = " + myPointBalance);
				if(myPointBalance < 50) {
					//不够50分就打开积分墙
					intent = new Intent(IndexActivity.this, ShowYouMiOfferDialog.class);
					intent.putExtra("title", "提  示");
					intent.putExtra("message", "感谢您对账户保的使用与支持，默认最多添加5个账户，您可以点击确认打开广告墙下载任意广告并使用即可开启无限添加账户模式。再次感谢您的支持！");
					startActivity(intent);
					overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
				}else {
					//够50分就扣除50分并修改max值
					net.youmi.android.offers.PointsManager.getInstance(this).spendPoints(50);
					asp.setAccountMax();
					goAddInput();
				}
			}
			break;
		}
	}

	private void goAddInput() {
		intent = new Intent(this, AccountAddInputActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 99 && resultCode == 100) {
			int flag = data.getIntExtra("flag", 0);
			Log.i("a", "flag = " + flag);
			switch (flag) {
			case 1:
				// 退出
				this.finish();
				break;
			case 2:
				// 注销
				intent = new Intent(IndexActivity.this, WelcomeActivity.class);
				intent.putExtra("type", 1);
				startActivity(intent);
				this.finish();
				break;
			case 3:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		accountDB.closeDB();
		accountDB = null;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			intent = new Intent(IndexActivity.this, ExitDialog.class);
			startActivityForResult(intent, 99);
		}
		return true;
	}
}
