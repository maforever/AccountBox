package com.machao.accountbox.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.machao.accountbox.AccountUpdateInputActivity;
import com.machao.accountbox.ActivityAccountDetail;
import com.machao.accountbox.R;
import com.machao.accountbox.adapter.AccountGroupExpandableListAdapter.ViewHolder;
import com.machao.accountbox.db.service.model.Account;
import com.machao.accountbox.dialog.SafePasswordInputDialog;

public class AccountListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Account> accounts;
	private Context context;
	private int currentSelectedIndex = -1;
	private Activity activity;
	public AccountListAdapter(Context context, List<Account> accounts,
			Activity activity) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.accounts = accounts;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return accounts.size();
	}

	@Override
	public Object getItem(int position) {
		return accounts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setSelection(int selectedIdx) {
		this.currentSelectedIndex = selectedIdx;
		Log.i("a", "currentSelectedIndex = " + currentSelectedIndex);
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView image, lock, arrow;
		TextView name, account;
		final LinearLayout toolLayout, openBtn, updateBtn, deleteBtn;
		ViewHolder vh;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_account_list, null);
			image = (ImageView) convertView.findViewById(R.id.image);
			openBtn = (LinearLayout) convertView.findViewById(R.id.openBtn);
			deleteBtn = (LinearLayout) convertView.findViewById(R.id.deleteBtn);
			updateBtn = (LinearLayout) convertView.findViewById(R.id.updateBtn);
			lock = (ImageView) convertView.findViewById(R.id.lock);
			name = (TextView) convertView.findViewById(R.id.name);
			account = (TextView) convertView.findViewById(R.id.account);
			toolLayout = (LinearLayout) convertView
					.findViewById(R.id.toolLayout);
			arrow = (ImageView) convertView.findViewById(R.id.arrow);
			vh = new ViewHolder();
			vh.image = image;
			vh.openBtn = openBtn;
			vh.deleteBtn = deleteBtn;
			vh.updateBtn = updateBtn;
			vh.lock = lock;
			vh.name = name;
			vh.account = account;
			vh.toolLayout = toolLayout;
			vh.arrow = arrow;
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
			image = vh.image;
			openBtn = vh.openBtn;
			deleteBtn = vh.deleteBtn;
			updateBtn = vh.updateBtn;
			lock = vh.lock;
			name = vh.name;
			account = vh.account;
			toolLayout = vh.toolLayout;
			arrow = vh.arrow;
		}

		final Account a = accounts.get(position);
		toolLayout.setVisibility(View.GONE);
		image.setImageResource(a.getImgIdx());
		name.setText(a.getName());
		account.setText(a.getAccount());
		arrow.setImageResource(R.drawable.check_up);
		final boolean isLocked = a.getLock() == 1 ? true : false;
		if (isLocked) {
			lock.setVisibility(View.VISIBLE);
		} else {
			lock.setVisibility(View.GONE);
		}

		openBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isLocked) {
					Intent intent = new Intent(context, SafePasswordInputDialog.class);
					intent.putExtra("title", "安全保护");
					intent.putExtra("flag", "read");
					intent.putExtra("account", a);
					context.startActivity(intent);
					activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
				}else {
					Intent intent = new Intent(context, ActivityAccountDetail.class);
					intent.putExtra("account", a);
					context.startActivity(intent);
					activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
				}
				
				
			}
		});
		
		
		deleteBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, com.machao.accountbox.dialog.DeleteAlertDialog.class);
				intent.putExtra("title", "提示");
				intent.putExtra("message", "您确定要删除该条账户信息吗?删除后无法恢复!");
				intent.putExtra("idx", a.getIdx());
				context.startActivity(intent);
				activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
			}
		});
		

		updateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(isLocked) {
					Intent intent = new Intent(context, SafePasswordInputDialog.class);
					intent.putExtra("title", "安全保护");
					intent.putExtra("flag", "update");
					intent.putExtra("account", a);
					context.startActivity(intent);
					activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
				}else {
					Intent intent = new Intent(context, AccountUpdateInputActivity.class);
					intent.putExtra("account", a);
					context.startActivity(intent);
					activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
				}
			}
		});

		if (currentSelectedIndex == position) {
			toolLayout.setVisibility(View.VISIBLE);
			arrow.setImageResource(R.drawable.check_down);
		}

		return convertView;
	}

	static class ViewHolder {
		ImageView image, lock, arrow;
		TextView name, account;
		CheckBox cb;
		LinearLayout toolLayout, openBtn, updateBtn, deleteBtn;

	}

}
