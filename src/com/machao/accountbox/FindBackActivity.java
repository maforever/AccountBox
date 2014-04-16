package com.machao.accountbox;

import net.youmi.android.diy.banner.DiyAdSize;
import net.youmi.android.diy.banner.DiyBanner;
import net.youmi.android.spot.SpotManager;

import com.machao.accountbox.db.service.UserDBService;
import com.machao.accountbox.db.service.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FindBackActivity extends Activity {
	private TextView btn1, btn2, btn3, questionTv;
	private EditText usernameEt, answerEt, password1Et, password2Et, password3Et;
	private UserDBService userDB;
	private String usernameStr, answerStr, password1Str, password2Str, password3Str;
	private LinearLayout layout, passwordLayout;
	private User user = null;
	private String[] questions;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_findback);
		
		SpotManager.getInstance(this).setSpotTimeout(5000); //
		SpotManager.getInstance(this).showSpotAds(this);
		
		
		// 获取要嵌入迷你广告条的布局
		RelativeLayout adLayout=(RelativeLayout)findViewById(R.id.AdLayout);
		// Demo 1 迷你Banner : 宽满屏，高32dp
		// 传入宽度满屏，高度为 32dp 的 AdSize 来定义迷你 Banner
		DiyBanner banner = new DiyBanner(this, DiyAdSize.SIZE_MATCH_SCREENx32);
		// Demo 2 迷你Banner : 宽320dp，高32dp
		// 将积分 Banner 加入到布局中
		adLayout.addView(banner);
		
		userDB = new UserDBService(this);
		questions = this.getResources().getStringArray(R.array.questions);
		findViews();
	}

	private void findViews() {
		btn1 = (TextView) this.findViewById(R.id.btn1);
		btn2 = (TextView) this.findViewById(R.id.btn2);
		btn3 = (TextView) this.findViewById(R.id.btn3);
		usernameEt = (EditText) this.findViewById(R.id.username);
		layout = (LinearLayout) this.findViewById(R.id.layout);
		passwordLayout = (LinearLayout) this.findViewById(R.id.passwordLayout);
		questionTv = (TextView) this.findViewById(R.id.question);
		answerEt = (EditText) this.findViewById(R.id.answer);
		password1Et = (EditText) this.findViewById(R.id.password1);
		password2Et = (EditText) this.findViewById(R.id.password2);
		password3Et = (EditText) this.findViewById(R.id.password3);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.btn1:
			usernameStr = usernameEt.getText().toString().trim();
			if("".equals(usernameStr)) {
				Toast.makeText(this, "请输入用户名!", 0).show();
			}else {
				user = userDB.findUserByUsername(usernameStr);
				if(user != null) {
					btn1.setVisibility(View.GONE);
					btn2.setVisibility(View.VISIBLE);
					layout.setVisibility(View.VISIBLE);
					usernameEt.setEnabled(false);
					questionTv.setText(questions[user.getQuestionIndex()]);
				}else {
					Toast.makeText(this, "用户名有误，请检查是否正确!", 0).show();
				}
			}
			break;
		case R.id.btn2:
			answerStr = this.answerEt.getText().toString().trim();
			if("".equals(answerStr)) {
				Toast.makeText(this, "请输入答案!", 0).show();
			}else {
				if(!answerStr.equals(user.getAnswer())) {
					Toast.makeText(this, "答案错误!", 0).show();
				}else {
					btn2.setVisibility(View.GONE);
					layout.setVisibility(View.GONE);
					btn3.setVisibility(View.VISIBLE);
					passwordLayout.setVisibility(View.VISIBLE);
				}
			}
			break;
		case R.id.btn3:
		
			if(getPasswordAndValidate()) {
				user.setPassword(password1Str);
				user.setPassword2(password3Str);
				userDB.saveOrUpdateUser(user);
				Toast.makeText(this, "修改密码成功!", 0).show();
				back();
			}
			
			break;
		}
	}
	
	private boolean  getPasswordAndValidate() {
		password1Str = password1Et.getText().toString().trim();
		password2Str = password2Et.getText().toString().trim();
		password3Str = password3Et.getText().toString().trim();
		if("".equals(password1Str)) {
			Toast.makeText(this, "新密码不能为空!", 0).show();
			password1Et.requestFocus();
			return false;
		}
		if("".equals(password2Str)) {
			Toast.makeText(this, "确认密码不能为空!", 0).show();
			password2Et.requestFocus();
			return false;
		}
		if(!password1Str.equals(password2Str)) {
			Toast.makeText(this, "两次输入的密码不一致!", 0).show();
			password1Et.requestFocus();
			return false;
		}
		if("".equals(password3Str)) {
			Toast.makeText(this, "二级密码不能为空!", 0).show();
			password3Et.requestFocus();
			return false;
		}
		return true;
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
		userDB.closeDB();
	}
}























