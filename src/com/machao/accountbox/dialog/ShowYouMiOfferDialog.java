package com.machao.accountbox.dialog;

import com.machao.accountbox.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowYouMiOfferDialog extends Activity {
	private String titleStr, messageStr;
	private TextView titleTv, messageTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_deletealert);
		
		
		titleStr = this.getIntent().getStringExtra("title");
		messageStr = this.getIntent().getStringExtra("message");
		
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
			net.youmi.android.offers.OffersManager.getInstance(this).showOffersWallDialog(this);	
			break;
		case R.id.cancel:
			this.finish();
			overridePendingTransition(R.anim.activity_steady, R.anim.activity_out);
			break;
		}
	}
	
}
