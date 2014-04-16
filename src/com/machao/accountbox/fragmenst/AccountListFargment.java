package com.machao.accountbox.fragmenst;

import java.util.List;
import com.machao.accountbox.AccountAddInputActivity;
import com.machao.accountbox.IndexActivity;
import com.machao.accountbox.R;
import com.machao.accountbox.adapter.AccountGroupExpandableListAdapter;
import com.machao.accountbox.db.service.AccountDBService;
import com.machao.accountbox.db.service.model.AccountGroup;
import com.machao.accountbox.dialog.ExitDialog;
import com.machao.accountbox.utils.MyApp;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class AccountListFargment extends Fragment {
	private ExpandableListView listView;
	private AccountGroupExpandableListAdapter adapter;
	private List<AccountGroup> ags = null;
	private MyApp myApp;
	private RelativeLayout emptyLayout;
	private int listGroupSelectedIdx = -1, listChildSelectedIdx = -1;
	private Long accountSize = 0l;
	private AlertDialog ad;
	private AccountDBService accountDB;
	private TextView accountCountTv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		myApp = (MyApp) this.getActivity().getApplication();
		super.onCreate(savedInstanceState);
	}

	public AccountListFargment(AccountDBService adb) {
		this.accountDB = adb;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("a", "fragment onresume");
		initViews();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("a", "fragment onCreateView");
		View contentView = inflater.inflate(R.layout.fragment_account_list,
				container, false);

		findViews(contentView);
		initViews();
		return contentView;
	}

	private void findViews(View contentView) {

		listView = (ExpandableListView) contentView.findViewById(R.id.listView);
		listView.setFastScrollEnabled(true);
		emptyLayout = (RelativeLayout) contentView
				.findViewById(R.id.emptyLayout);
		accountCountTv = (TextView) contentView.findViewById(R.id.accountCount);

		listView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if (listGroupSelectedIdx != groupPosition
						|| listChildSelectedIdx != childPosition) {
					adapter.showToolBar(groupPosition, childPosition);
					listGroupSelectedIdx = groupPosition;
					listChildSelectedIdx = childPosition;
				} else {
					adapter.showToolBar(-1, -1);
					listGroupSelectedIdx = -1;
					listChildSelectedIdx = -1;
				}

				LinearLayout deleteBtn = (LinearLayout) v
						.findViewById(R.id.deleteBtn);
				deleteBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ad = new AlertDialog.Builder(getActivity())
								.setMessage("A")
								.setPositiveButton("sure",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												ad.dismiss();
											}
										}).create();
						ad.show();
					}
				});

				return false;
			}
		});

	}

	private void initViews() {
		ags = accountDB.findAccountByUsername3(myApp.getUsername());
		accountSize = accountDB.getCount(myApp.getUsername());
		if (ags.size() > 0) {
			accountCountTv.setText("已保存账户数:" + accountSize);
			emptyLayout.setVisibility(View.GONE);
			adapter = new AccountGroupExpandableListAdapter(getActivity(), ags,
					getActivity());
			listView.setAdapter(adapter);
			int groupCount = adapter.getGroupCount();
			for (int i = 0; i < groupCount; i++) {
				listView.expandGroup(i);
			}
			listView.setVisibility(View.VISIBLE);
		} else {
			listView.setVisibility(View.GONE);
			emptyLayout.setVisibility(View.VISIBLE);
		}
	}

}
