package com.machao.accountbox.adapter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.machao.accountbox.AccountUpdateInputActivity;
import com.machao.accountbox.ActivityAccountDetail;
import com.machao.accountbox.R;
import com.machao.accountbox.adapter.AccountListAdapter.ViewHolder;
import com.machao.accountbox.db.service.AccountDBService;
import com.machao.accountbox.db.service.model.Account;
import com.machao.accountbox.db.service.model.AccountGroup;
import com.machao.accountbox.dialog.SafePasswordInputDialog;

public class AccountGroupExpandableListAdapter extends
		BaseExpandableListAdapter {
    private static final int[] COLORS = new int[] {
        R.color.green_light, R.color.orange_light,
        R.color.blue_light, R.color.red_light };
	private LayoutInflater inflater;
	private final Context context;
	private List<AccountGroup> ags;
	private int groupSelectedIdx = -1,  childSelectedIdx= -1;
	private Activity activity;
	private AlertDialog ad;
	public AccountGroupExpandableListAdapter(Context context, List<AccountGroup> ags, Activity activity) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.ags = ags;
		this.activity = activity;
	}
	
	@Override
	public int getGroupCount() {
		return ags.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return ags.get(groupPosition).getAccounts().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return ags.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return ags.get(groupPosition).getAccounts().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	public void showToolBar(int groupIndex, int childIndex) {
		this.groupSelectedIdx = groupIndex;
		this.childSelectedIdx = childIndex;
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView name;
		ImageView image;
		ViewHolder vh;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.item_type_list, null);
			convertView.setBackgroundColor(parent.getResources().getColor(COLORS[groupPosition % COLORS.length]));
			name = (TextView) convertView.findViewById(R.id.name);
			image =(ImageView) convertView.findViewById(R.id.image);
			vh = new ViewHolder();
			vh.name = name;
			vh.image = image;
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
			name = vh.name;
			image = vh.image;
		}
		
		name.setText(ags.get(groupPosition).getGroupName());
		image.setImageResource(ags.get(groupPosition).getGroupImgIdx());
		
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
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
		
		final Account a = ags.get(groupPosition).getAccounts().get(childPosition);
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

		if (groupSelectedIdx == groupPosition && childSelectedIdx == childPosition) {
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
	
	
	
	
	
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	

}
