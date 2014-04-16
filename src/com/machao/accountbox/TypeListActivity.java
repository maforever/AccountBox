package com.machao.accountbox;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.machao.accountbox.adapter.TypeListAdapter;
import com.machao.accountbox.db.service.TypeDBService;
import com.machao.accountbox.db.service.model.Type;

public class TypeListActivity extends Activity {
	private ListView listView1, listView2;
	private TypeDBService typeDB;
	private List<Type> parents, childs;
	private TypeListAdapter parentAdapter, childAdapter;
	private Type addType;
	private int parentSelectedIndex = 0, childSelectedIndex = 0, parentSelectedIndexTemp;
	private String parentTypeName, childTypeName;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_type_list);
		
		typeDB = new TypeDBService(this);
		parentSelectedIndex = this.getIntent().getIntExtra("parentSelectedIndex", 0);
		childSelectedIndex = this.getIntent().getIntExtra("childSelectedIndex", 0);
		
		
		parentSelectedIndexTemp = parentSelectedIndex;
		addType = new Type("新建", 0, R.drawable.add_type);
		parents = typeDB.getTypesByParentIdx(0);
		parents.add(addType);
		parentTypeName = parents.get(0).getName();
		childs = typeDB.getTypesByParentIdx(parents.get(parentSelectedIndex).getIdx());
		childs.add(addType);
		childTypeName = childs.get(childSelectedIndex).getName();
		findViews();
		setAdapters();

		Log.i("a", "create parentSelectedIndex = " + parentSelectedIndex + " childSelectedIndex = " + childSelectedIndex);

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(typeDB != null) {
			Log.i("a", "resume parentSelectedIndex = " + parentSelectedIndex + " childSelectedIndex = " + childSelectedIndex);
			parentSelectedIndexTemp = parentSelectedIndex;
			addType = new Type("新建", 0, R.drawable.add_type);
			parents = typeDB.getTypesByParentIdx(0);
			parents.add(addType);
			parentTypeName = parents.get(0).getName();
			childs = typeDB.getTypesByParentIdx(parents.get(parentSelectedIndex).getIdx());
			childs.add(addType);
			childTypeName = childs.get(childSelectedIndex).getName();
			findViews();
			setAdapters();
		}
	}
	
	private void findViews() {
		listView1 = (ListView) this.findViewById(R.id.listView1);
		listView2 = (ListView) this.findViewById(R.id.listView2);
	}
	
	
	private void setAdapters() {
		parentAdapter = new TypeListAdapter(this, parents, R.layout.item_type_list);
		childAdapter = new TypeListAdapter(this, childs, R.layout.item_type_list);
		parentAdapter.setParentSelected(parentSelectedIndex);
		childAdapter.setChildSelected(childSelectedIndex);
//		Log.i("a", "parentSelected = " + parentSelectedIndex + " childSelected = " + childSelectedIndex);
		listView1.setAdapter(parentAdapter);
		listView2.setAdapter(childAdapter);
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position != parents.size() - 1) {
					int parentIdx = parents.get(position).getIdx();
					childs = typeDB.getTypesByParentIdx(parentIdx);
					childs.add(addType);
					childAdapter = new TypeListAdapter(TypeListActivity.this, childs, R.layout.item_type_list);
					listView2.setAdapter(childAdapter);
					parentSelectedIndex = position;
					parentAdapter.setParentSelected(parentSelectedIndex);
					parentTypeName = parents.get(position).getName();
					if(position == parentSelectedIndexTemp) {
						childAdapter.setChildSelected(childSelectedIndex);
					}
//					Log.i("a", "parentSelected = " + parentSelectedIndex + " childSelected = " + childSelectedIndex);
				}else {
					//添加父类别界面
					intent = new Intent(TypeListActivity.this, TypeAddInputActivity.class);
					intent.putExtra("parentIdx", 0);
					intent.putExtra("titleStr", "添加类别");
					startActivity(intent);
					overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
				}
			}
		});
		listView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position != childs.size() - 1) {
					childSelectedIndex = position;
					childTypeName = childs.get(position).getName();
					childAdapter.setChildSelected(childSelectedIndex);
//					Log.i("a", "parentSelected = " + parentSelectedIndex + " childSelected = " + childSelectedIndex);
					intent = new Intent();
					intent.putExtra("parentSelectedIndex", parentSelectedIndex);
					intent.putExtra("childSelectedIndex", childSelectedIndex);
					intent.putExtra("typeName", parentTypeName + "-" + childTypeName);
					intent.putExtra("parentIdx", parents.get(parentSelectedIndex).getIdx());
					intent.putExtra("childIdx", childs.get(childSelectedIndex).getIdx());
					TypeListActivity.this.setResult(99, intent);
					TypeListActivity.this.finish();
					overridePendingTransition(R.anim.activity_steady, R.anim.activity_out);
				}else {
					//添加子类别界面
					intent = new Intent(TypeListActivity.this, TypeAddInputActivity.class);
					intent.putExtra("parentIdx", parents.get(parentSelectedIndex).getIdx());
					intent.putExtra("titleStr", "添加子类别");
					startActivity(intent);
					overridePendingTransition(R.anim.activity_in, R.anim.activity_steady);
				}
			}
		});
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
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
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		typeDB.closeDB();
		typeDB = null;
	}
	
}
