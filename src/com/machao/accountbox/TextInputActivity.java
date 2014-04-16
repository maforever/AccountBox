package com.machao.accountbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TextInputActivity extends Activity {
	private Intent intent;
	private TextView titleTv, editNumTv;
	private EditText contentEt;
	private String title, content; // 输入的文本内容
	private String flag;
	private int editNum = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_text_input);
		
		initDatas();
		findViews();
		initViews();
	}
	
	private void initDatas() {
		title = this.getIntent().getStringExtra("title");
		flag = this.getIntent().getStringExtra("flag");
		editNum = this.getIntent().getIntExtra("editNum", 0);
		content = this.getIntent().getStringExtra("content");
		
	}
	
	private void findViews() {
		titleTv = (TextView) this.findViewById(R.id.title);
		editNumTv = (TextView) this.findViewById(R.id.editNum);
		contentEt = (EditText) this.findViewById(R.id.content);
		contentEt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// Log.i("a", "count = " + count);
				content = contentEt.getText().toString();
				if (content.length() <= editNum) {
					editNumTv.setText((editNum - content.length()) + "");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}
	
	private void initViews() {
		titleTv.setText(title);
		contentEt.setText(content);
		int contentLength = content.length();
		Log.i("a", "contentLength = " + contentLength);
		editNumTv.setText((editNum - contentLength) + "");

		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(editNum);
		contentEt.setFilters(filters);
		
		CharSequence text = contentEt.getText();
		if(text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;	
		case R.id.add:
			content = contentEt.getText().toString().trim();
			if("".equals(content)) {
				Toast.makeText(this, "请输入内容", 0).show();
			}else {
				intent = new Intent();
				intent.putExtra("content", content);
				if("findbackway".equals(flag)) {
					this.setResult(98, intent);
				}else if("bz".equals(flag)) {
					this.setResult(97, intent);
				}
				back();
			}
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
