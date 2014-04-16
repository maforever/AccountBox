package com.machao.accountbox;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

import com.machao.accountbox.adapter.AccountGroupExpandableListAdapter;
import com.machao.accountbox.db.service.AccountDBService;
import com.machao.accountbox.db.service.model.AccountGroup;
import com.machao.accountbox.dialog.ExitDialog;
import com.machao.accountbox.utils.MyApp;

public class IndexActivityBAK extends FragmentActivity{
	private RadioGroup rg;
	private RadioButton listRb, findRb;
	private int orangeColor = 0;
	private Intent intent;
	private ExpandableListView listView;
	private AccountGroupExpandableListAdapter adapter;
	private AccountDBService accountDB;
	private List<AccountGroup> ags = null;
	private MyApp myApp;
	private RelativeLayout emptyLayout;
	private int listGroupSelectedIdx = -1, listChildSelectedIdx = -1;
	private Long accountSize = 0l;
	private AlertDialog ad;
	private boolean isFirst = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_index);
		accountDB = new AccountDBService(this);
		myApp = (MyApp) this.getApplication();
		orangeColor = this.getResources().getColor(R.color.orange);
		ags = accountDB.findAccountByUsername3(myApp.getUsername());
		accountSize = accountDB.getCount(myApp.getUsername());
		for (AccountGroup ag : ags) {
			Log.i("a", "a = " + ag.toString());
		}
		isFirst = true;
		Log.i("a", "oncreate");
		findViews();
		initViews();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i("a", "onPause");
	}

	@Override
	protected void onResume() {
		super.onResume();

		if(isFirst) {
			Log.i("a", "onresume");
			ags = accountDB.findAccountByUsername3(myApp.getUsername());
			if (ags.size() > 0) {
				Log.i("a", "***********************************");
				emptyLayout.setVisibility(View.GONE);
				adapter = new AccountGroupExpandableListAdapter(this, ags, this);
				listView.setAdapter(adapter);
				int groupCount = adapter.getGroupCount();
				for (int i = 0; i < groupCount; i++) {
					listView.expandGroup(i);
				}

				listView.setVisibility(View.VISIBLE);
			} else {
				listView.setVisibility(View.GONE);
				emptyLayout.setVisibility(View.VISIBLE);
			}
		}

	}

	private void initViews() {
		if (listRb.isChecked()) {
			// 选择列表
			if (ags.size() > 0) {
				Log.i("a", "***********************************");
				emptyLayout.setVisibility(View.GONE);
				adapter = new AccountGroupExpandableListAdapter(this, ags, this);
				listView.setAdapter(adapter);
				int groupCount = adapter.getGroupCount();
				for (int i = 0; i < groupCount; i++) {
					listView.expandGroup(i);
				}
				listView.setVisibility(View.VISIBLE);
			} else {
				listView.setVisibility(View.GONE);
				emptyLayout.setVisibility(View.VISIBLE);
			}
		}
		if (findRb.isChecked()) {
			// 选择查询
		}
	}

	private void findViews() {
		rg = (RadioGroup) this.findViewById(R.id.rg);
		listRb = (RadioButton) this.findViewById(R.id.listRb);
		listRb.setChecked(true);
		findRb = (RadioButton) this.findViewById(R.id.findRb);
		listView = (ExpandableListView) this.findViewById(R.id.listView);
		listView.setFastScrollEnabled(true);
		emptyLayout = (RelativeLayout) this.findViewById(R.id.emptyLayout);

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.listRb:
					listRb.setTextColor(Color.WHITE);
					findRb.setTextColor(orangeColor);
					break;
				case R.id.findRb:
					listRb.setTextColor(orangeColor);
					findRb.setTextColor(Color.WHITE);
					break;
				}
			}
		});

		listView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if (listGroupSelectedIdx != groupPosition
						|| listChildSelectedIdx != childPosition) {
					adapter.showToolBar(groupPosition, childPosition);
					listGroupSelectedIdx = groupPosition;
					listChildSelectedIdx = childPosition;
				} else {
					adapter.showToolBar(-1, -1);
					listGroupSelectedIdx = -1;
					listChildSelectedIdx = -1;
				}

				LinearLayout deleteBtn = (LinearLayout) v
						.findViewById(R.id.deleteBtn);
				deleteBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ad = new AlertDialog.Builder(IndexActivityBAK.this)
								.setMessage("A")
								.setPositiveButton("sure",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												ad.dismiss();
											}
										}).create();
						ad.show();
					}
				});

				return false;
			}
		});

	}

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.add:
		case R.id.emptyLayout:
			goAddInput();
			break;
		}
	}

	private void goAddInput() {
		intent = new Intent(IndexActivityBAK.this, AccountAddInputActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			intent = new Intent(IndexActivityBAK.this, ExitDialog.class);
			startActivityForResult(intent, 99);
		}
		return true;
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
				intent = new Intent(IndexActivityBAK.this, WelcomeActivity.class);
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

}
