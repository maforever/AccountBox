package com.machao.accountbox;

import net.youmi.android.diy.banner.DiyAdSize;
import net.youmi.android.diy.banner.DiyBanner;
import net.youmi.android.spot.SpotManager;

import com.machao.accountbox.db.service.UserDBService;
import com.machao.accountbox.db.service.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistActivity extends Activity {
	private Spinner spinner;
	private String[] quesionts = null;
	private ArrayAdapter<String> adapter;
	private EditText usernameEt, password1Et, password2Et, answerEt, password3Et;
	private int questionIndex = 0;
	private String usernameStr, password1Str, password2Str, password3Str, answerStr;
	private UserDBService userDB = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_regist);
		
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
		
		
		quesionts = this.getResources().getStringArray(R.array.questions);
		userDB = new UserDBService(this);
		findViews();
	}
	
	private void findViews() {
		usernameEt = (EditText) this.findViewById(R.id.username);
		password1Et = (EditText) this.findViewById(R.id.password1);
		password2Et = (EditText) this.findViewById(R.id.password2);
		password3Et = (EditText) this.findViewById(R.id.password3);
		answerEt = (EditText) this.findViewById(R.id.answer);
		spinner = (Spinner) this.findViewById(R.id.spinner);
		spinner.setPrompt("用于找回账号");
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				questionIndex = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.registBtn:
			getViewDatas();
			if(validateDatas()) {
				if(userDB.isExistByUsername(usernameStr)) {
					Toast.makeText(this, "用户名 " + usernameStr + " 已经存在!", 0).show();
				}else {
//					Log.i("a", "username = " + usernameStr + " password = " + password1Str + " questionIndex = " + questionIndex + " answer = " + answerStr);
					User user = new User();
					user.setUsername(usernameStr);
					user.setPassword(password1Str);
					user.setPassword2(password3Str);
					user.setQuestionIndex(questionIndex);
					user.setAnswer(answerStr);
					userDB.saveOrUpdateUser(user);
					back();
				}
			}
			break;
		case R.id.resetBtn:
			setReset();
			break;
		}
	}
	
	private void getViewDatas() {
		usernameStr = usernameEt.getText().toString().trim();
		password1Str = password1Et.getText().toString().trim();
		password2Str = password2Et.getText().toString().trim();
		password3Str = password3Et.getText().toString().trim();
		answerStr = answerEt.getText().toString().trim();
	}
	
	private boolean validateDatas() {
		if("".equals(usernameStr)) {
			Toast.makeText(this, "用户名不能为空!", 0).show();
			usernameEt.requestFocus();
			return false;
		}
		if("".equals(password1Str)) {
			Toast.makeText(this, "密码名不能为空!", 0).show();
			password1Et.requestFocus();
			return false;
		}
		if("".equals(password2Str)) {
			Toast.makeText(this, "确认密码名不能为空!", 0).show();
			password2Et.requestFocus();
			return false;
		}
		if(!password1Str.equals(password2Str)) {
			Toast.makeText(this, "两次输入的密码不一致!", 0).show();
			password1Et.requestFocus();
			return false;
		}
		if("".equals(answerStr)) {
			Toast.makeText(this, "问题答案不能为空!", 0).show();
			answerEt.requestFocus();
			return false;
		}
		if("".equals(password3Str)) {
			Toast.makeText(this, "二级密码不能为空!", 0).show();
			password3Et.requestFocus();
			return false;
		}
		return true;
	}
	
	private void setReset() {
		usernameEt.setText("");
		password1Et.setText("");
		password2Et.setText("");
		password3Et.setText("");
		answerEt.setText("");
		spinner.setSelection(0);
		questionIndex = 0;
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
