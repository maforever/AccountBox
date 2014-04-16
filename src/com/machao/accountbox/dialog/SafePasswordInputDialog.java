package com.machao.accountbox.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.machao.accountbox.AccountUpdateInputActivity;
import com.machao.accountbox.ActivityAccountDetail;
import com.machao.accountbox.R;
import com.machao.accountbox.db.service.UserDBService;
import com.machao.accountbox.db.service.model.Account;
import com.machao.accountbox.utils.MyApp;

public class SafePasswordInputDialog extends Activity {
	private TextView titleTv;
	private String titleStr;
	private String passwordInputStr;
	private EditText passwordEt;
	private UserDBService userDB;
	private MyApp myApp;
	private Account account;
	private String flag;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_safeinput);
		
		myApp = (MyApp) this.getApplication();
		
		flag = this.getIntent().getStringExtra("flag");
		titleStr = this.getIntent().getStringExtra("title");
		account = (Account) this.getIntent().getSerializableExtra("account");
		findViews();
	}
	
	private void findViews() {
		titleTv = (TextView) this.findViewById(R.id.title);
		passwordEt = (EditText) this.findViewById(R.id.password);
		titleTv.setText(titleStr);
		
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.sure:
			passwordInputStr = passwordEt.getText().toString().trim();
			userDB = new UserDBService(this);
			if(passwordInputStr.equals(userDB.getSafePasswordByUsername(myApp.getUsername()))) {
				
				if("read".equals(flag)) {
					intent = new Intent(this, ActivityAccountDetail.class);
				}else if("update".equals(flag)) {
					intent = new Intent(this, AccountUpdateInputActivity.class);
				}
				intent.putExtra("account", account);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
				
				
			}else {
				Toast.makeText(this, "安全密码错误!", 0).show();
			}
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
		if(userDB != null) {
			userDB.closeDB();
			userDB = null;
		}
	}
}

























