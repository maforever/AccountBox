package com.machao.accountbox.fragmenst;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.machao.accountbox.R;
import com.machao.accountbox.adapter.AccountListAdapter;
import com.machao.accountbox.db.service.AccountDBService;
import com.machao.accountbox.db.service.model.Account;
import com.machao.accountbox.utils.MyApp;

public class AccountCheckFragment extends Fragment {
	private AutoCompleteTextView acTv;
	private ImageView clearIv;
	private AccountDBService accountDB;
	private MyApp myApp;
	private List<String> accountNames = new ArrayList<String>();
	private TextView checkBtn;
	private ListView listView;
	private List<Account> accounts = new ArrayList<Account>();
	private LinearLayout emptyLayout;
	private int listSelected = -1;
	private AccountListAdapter adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		accountDB = new AccountDBService(getActivity());
		myApp = (MyApp) getActivity().getApplication();
		accountNames = accountDB.getAccountNames(myApp.getUsername());
		for (String name : accountNames) {
			Log.i("a", "name = " + name);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_account_check,
				container, false);

		findViews(contentView);
		 ArrayAdapter<String> adapter = new
		 ArrayAdapter<String>(getActivity(),    android.R.layout.simple_dropdown_item_1line,
		 accountNames);
		 acTv.setAdapter(adapter);
		return contentView;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		String keyword = acTv.getText().toString().trim();
		Log.i("a", "onResume keyword = " + keyword);
		if(!"".equals(acTv.getText().toString().trim())) {
			initDatas();
			listView.setAdapter(adapter);
		}
	}

	private void findViews(View contentView) {
		acTv = (AutoCompleteTextView) contentView.findViewById(R.id.act);
		clearIv = (ImageView) contentView.findViewById(R.id.clear);
		checkBtn = (TextView) contentView.findViewById(R.id.check);
		listView = (ListView) contentView.findViewById(R.id.listView);
		emptyLayout = (LinearLayout) contentView.findViewById(R.id.emptyLayout);
		listView.setVisibility(View.GONE);
		acTv.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String content = acTv.getText().toString().trim();
				if (content.length() > 0) {
					clearIv.setVisibility(View.VISIBLE);
				} else {
					clearIv.setVisibility(View.GONE);
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

		clearIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				acTv.setText("");
			}
		});

		
		
		checkBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String keyword = acTv.getText().toString().trim();
				if("".equals(keyword)) {
					emptyLayout.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
				}else {
					initDatas();
				}
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(listSelected != position) {
					adapter.setSelection(position);
					listSelected = position;
				}else {
					adapter.setSelection(-1);
					listSelected = -1;
				}
			}
		});
		
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		accountDB.closeDB();
		accountDB = null;
	}

	private void initDatas() {
		accounts = accountDB.queryAccountByKeyword(myApp.getUsername(), acTv.getText().toString().trim());
		if(accounts.size() > 0) {
			adapter = new AccountListAdapter(getActivity(), accounts, getActivity());
			listView.setAdapter(adapter);
			emptyLayout.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
		}else {
			emptyLayout.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		}
	}
	
}

