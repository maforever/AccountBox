package com.machao.accountbox.dialog;

import com.machao.accountbox.R;
import com.machao.accountbox.db.service.AccountDBService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteAlertDialog extends Activity {
	private TextView titleTv, messageTv;
	private String titleStr, messageStr;
	private int idx;
	private AccountDBService accountDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_deletealert);
		
		titleStr = this.getIntent().getStringExtra("title");
		messageStr = this.getIntent().getStringExtra("message");
		idx = this.getIntent().getIntExtra("idx", 0);
		findViews();
	}
	
	private void findViews() {
		titleTv = (TextView) this.findViewById(R.id.title);
		messageTv = (TextView) this.findViewById(R.id.message);
		
		titleTv.setText(titleStr);
		messageTv.setText(messageStr);
		
	}
	
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.sure:
			accountDB = new AccountDBService(this);
			if(idx > 0) {
				accountDB.deleteAccountByIdx(idx);
			}
			Toast.makeText(this, "删除账号信息成功!", 0).show();
			this.finish();
			overridePendingTransition(R.anim.activity_steady, R.anim.activity_out);
			break;
		case R.id.cancel:
			this.finish();
			overridePendingTransition(R.anim.activity_steady, R.anim.activity_out);
			break;
		}
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(accountDB != null) {
			accountDB.closeDB();
			accountDB = null;
		}
	}
}















