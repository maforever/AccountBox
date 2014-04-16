package com.machao.accountbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.machao.accountbox.adapter.TypeImageGridViewAdapter;
import com.machao.accountbox.db.service.TypeDBService;
import com.machao.accountbox.db.service.model.Type;

public class TypeAddInputActivity extends Activity {
	private ImageView imageSelected;
	private GridView gridView;
	private EditText nameEt;
	private TextView titleTv;
	private int parentIdx, imgIdx;
	private String titleStr, nameStr;
	private TypeImageGridViewAdapter adapter;
	private TypeDBService typeDB;
	private int[] imgIds = { R.drawable.qq, R.drawable.weixin,
			R.drawable.yinxin, R.drawable.feixin, R.drawable.yy,
			R.drawable.line, R.drawable.momo, R.drawable.wangxin,
			R.drawable.youxin, R.drawable.laiwang, R.drawable.weibo,
			R.drawable.renren, R.drawable.qqzone, R.drawable.qqweibo,
			R.drawable.mopo, R.drawable.kaixinwang, R.drawable.tianyashequ,
			R.drawable.facebook, R.drawable.twitter, R.drawable.qichezhijia,
			R.drawable.youyuan, R.drawable.taobaowang, R.drawable.tianmo,
			R.drawable.jingdong, R.drawable.suning, R.drawable.fanke,
			R.drawable.tongcheng58, R.drawable.qitian, R.drawable.ganjiwang,
			R.drawable.alibaba, R.drawable.zhilian, R.drawable.fiveonejob,
			R.drawable.dazhongdianping, R.drawable.lashou, R.drawable.meituan,
			R.drawable.wowo, R.drawable.tuangou360, R.drawable.ruomi,
			R.drawable.dangdangwang, R.drawable.jumei, R.drawable.juhuasuan,
			R.drawable.xiechenglvxing, R.drawable.weipinhui,
			R.drawable.mojitianqi, R.drawable.tianqi360, R.drawable.tianqitong,
			R.drawable.youku, R.drawable.tudou, R.drawable.kuaibo,
			R.drawable.baofengyingyin, R.drawable.fengxing, R.drawable.leshi,
			R.drawable.pplive, R.drawable.souhushipin, R.drawable.xunleikankan,
			R.drawable.fengyunzhibo, R.drawable.kugou, R.drawable.qqyinyue,
			R.drawable.tiantiandongting, R.drawable.kuwo, R.drawable.wangyi,
			R.drawable.sina, R.drawable.xinwen360, R.drawable.routiao,
			R.drawable.yangshixinwen, R.drawable.mail163, R.drawable.qqmail,
			R.drawable.cn21, R.drawable.gmail, R.drawable.hotmail,
			R.drawable.womail, R.drawable.jiamimail, R.drawable.yun360,
			R.drawable.qqweiyun, R.drawable.kuyun, R.drawable.wangyiyunyuedu,
			R.drawable.youdaoyun, R.drawable.wangyiyunxiangce,
			R.drawable.yunxiangce, R.drawable.wojiaomt,
			R.drawable.daotachuanqi, R.drawable.tianchaoxiaojiang,
			R.drawable.wow, R.drawable.jianling, R.drawable.lol,
			R.drawable.lualu, R.drawable.dazhangmen, R.drawable.erzhanfengyun,
			R.drawable.koudaihaizeiwang, R.drawable.koudailongzhu,
			R.drawable.koudairenzhe, R.drawable.nbamengzhidui,
			R.drawable.sanguohehuoren, R.drawable.sanguoshidai,
			R.drawable.img_empty };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_type_addinput);

		typeDB = new TypeDBService(this);
		parentIdx = this.getIntent().getIntExtra("parentIdx", 0);
		titleStr = this.getIntent().getStringExtra("titleStr");

		findViews();
	}

	private void findViews() {
		imageSelected = (ImageView) this.findViewById(R.id.seletedImage);
		gridView = (GridView) this.findViewById(R.id.gridView);
		nameEt = (EditText) this.findViewById(R.id.name);
		titleTv = (TextView) this.findViewById(R.id.title);
		titleTv.setText(titleStr);

		imgIdx = imgIds[imgIds.length - 1];
		imageSelected.setImageResource(imgIdx);
		adapter = new TypeImageGridViewAdapter(this, imgIds,
				R.layout.item_type_gridview);
		gridView.setAdapter(adapter);
		adapter.setSelected(imgIds.length - 1);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				imgIdx = imgIds[position];
				imageSelected.setImageResource(imgIdx);
				adapter.setSelected(position);
			}
		});

	}

	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.add:
			nameStr = nameEt.getText().toString().trim();
			if ("".equals(nameStr)) {
				Toast.makeText(this, "请输入类别名称!", 0).show();
			} else {

				if (typeDB.existByName(nameStr)) {
					Toast.makeText(this, "类别名称重复，请另取名称!", 0).show();
				} else {
					Type t = new Type();
					t.setName(nameStr);
					t.setImgId(imgIdx);
					t.setParentIdx(parentIdx);
					typeDB.saveType(t);
					Toast.makeText(this, "类别添加成功!", 0).show();
					back();
				}

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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		typeDB.closeDB();
		typeDB = null;
	}

}
