package com.machao.accountbox.dialog;

import com.machao.accountbox.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ExitDialog extends Activity {
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_exit);
		intent = this.getIntent();
	}

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.sure:
			intent.putExtra("flag", 1);
			break;
		case R.id.loginOut:
			intent.putExtra("flag", 2);
			break;
		case R.id.cancel:
			intent.putExtra("flag", 3);

			break;
		}
		setResult(100, intent);
		this.finish();
	}

}
