package com.machao.accountbox;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.smart.SmartBannerManager;
import net.youmi.android.spot.SpotManager;

import com.machao.accountbox.db.service.UserDBService;
import com.machao.accountbox.dialog.ExitDialog;
import com.machao.accountbox.utils.AppSharedPreferences;
import com.machao.accountbox.utils.MyApp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	private ImageView runImage;
	private TranslateAnimation left, right;
	private Intent intent;
	private String username, password;
	private EditText usernameEt, passwordEt;
	private UserDBService userDb;
	private CheckBox jzmm, zddl;
	private AppSharedPreferences asp;
	private MyApp myApp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		AdManager.getInstance(this).init("ab284e72cec18e94",
				"e84bfb17c2441552", false);
		OffersManager.getInstance(this).onAppLaunch();
		SpotManager.getInstance(this).loadSpotAds();

		userDb = new UserDBService(this);
		asp = new AppSharedPreferences(this, "user");
		myApp = (MyApp) this.getApplication();

		findViews();
		initViews();
		runAnimation();
	}

	private void findViews() {
		runImage = (ImageView) this.findViewById(R.id.run_image);
		usernameEt = (EditText) this.findViewById(R.id.username);
		passwordEt = (EditText) this.findViewById(R.id.password);
		jzmm = (CheckBox) this.findViewById(R.id.jzmmCb);
		zddl = (CheckBox) this.findViewById(R.id.zddlCb);
	}

	private void initViews() {
		if (asp.getJiZhuMiMa()) {
			usernameEt.setText(asp.getUsername());
			passwordEt.setText(asp.getPassword());
			jzmm.setChecked(true);
		}

		int type = this.getIntent().getIntExtra("type", 0);
		if (type == 1) {
			// 来自于注销方式
			if (asp.getZiDongDengLu()) {
				jzmm.setChecked(true);
				zddl.setChecked(true);
				usernameEt.setText(asp.getUsername());
				passwordEt.setText(asp.getPassword());
			}
		} else {
			if (asp.getZiDongDengLu()) {
				myApp.setUsername(asp.getUsername());
				intent = new Intent(WelcomeActivity.this, IndexActivity.class);
				startActivity(intent);
				this.finish();
				overridePendingTransition(R.anim.activity_in,
						R.anim.activity_steady);
			}
		}
	}

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.loginBtn:
			if (getAndValidateViewDatas()) {
				if (userDb.Login(username, password)) {
					// 登陆成功
					asp.setUsername(username);
					asp.setPassword(password);
					asp.setJiZhuMiMa(jzmm.isChecked());
					asp.setZiDongDengLu(zddl.isChecked());
					myApp.setUsername(username);
					intent = new Intent(WelcomeActivity.this,
							IndexActivity.class);
					startActivity(intent);
					this.finish();
					overridePendingTransition(R.anim.activity_in,
							R.anim.activity_steady);
				} else {
					// 登陆失败
					Toast.makeText(this, "用户名或密码错误!", 0).show();
				}
			}

			break;
		case R.id.registBtn:
			intent = new Intent(WelcomeActivity.this, RegistActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_in,
					R.anim.activity_steady);
			break;
		case R.id.findbackBtn:
			intent = new Intent(WelcomeActivity.this, FindBackActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_in,
					R.anim.activity_steady);
			break;
		}
	}

	private boolean getAndValidateViewDatas() {
		username = usernameEt.getText().toString().trim();
		password = passwordEt.getText().toString().trim();
		if ("".equals(username)) {
			Toast.makeText(this, "用户名不能为空!", 0).show();
			usernameEt.requestFocus();
			return false;
		}
		if ("".equals(password)) {
			Toast.makeText(this, "密码不能为空!", 0).show();
			passwordEt.requestFocus();
			return false;
		}
		return true;
	}

	public void runAnimation() {


		right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		right.setDuration(25000);
		left.setDuration(25000);
		right.setFillAfter(true);
		left.setFillAfter(true);

		right.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(left);
			}
		});
		left.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(right);
			}
		});
		runImage.startAnimation(right);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		userDb.closeDB();
		userDb = null;
	}

}
