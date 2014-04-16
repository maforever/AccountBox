package com.machao.accountbox.utils;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

import com.machao.accountbox.R;
import com.machao.accountbox.db.service.model.Type;

public class DBInitUtil {
	SQLiteDatabase db;
	public DBInitUtil(SQLiteDatabase db) {
		this.db = db;
	}
	
	public void init() {
		//初始化一级Type
		List<Type> types = new ArrayList<Type>();
		Type t = new Type("聊天通讯", 0, R.drawable.feixin);
		types.add(t);
		 t = new Type("生活购物", 0, R.drawable.wowo);
		types.add(t);
		 t = new Type("影音影像", 0, R.drawable.baofengyingyin);
		types.add(t);
		 t = new Type("新闻", 0, R.drawable.xinwen360);
		types.add(t);
		 t = new Type("邮箱", 0, R.drawable.qqmail);
		types.add(t);
		t = new Type("云服务", 0, R.drawable.yun360);
		types.add(t);
		t = new Type("游戏", 0, R.drawable.wow);
		types.add(t);
		t = new Type("其他", 0, R.drawable.wifi);
		types.add(t);
		
		for(Type temp : types) {
			db.execSQL("insert into type (name, parentidx, imgidx) values (?, ?, ?)", new String[]{temp.getName(), String.valueOf(temp.getParentIdx()), String.valueOf(temp.getImgId())});
		}
		
		//初始化二级type
		types = new ArrayList<Type>();
		t = new Type("QQ", 1, R.drawable.qq);
		types.add(t);
		t = new Type("微信", 1, R.drawable.weixin);
		types.add(t);
		t = new Type("易信", 1, R.drawable.yinxin);
		types.add(t);
		t = new Type("飞信", 1, R.drawable.feixin);
		types.add(t);
		t = new Type("YY", 1, R.drawable.yy);
		types.add(t);
		t = new Type("Line", 1, R.drawable.line);
		types.add(t);
		t = new Type("陌陌", 1, R.drawable.momo);
		types.add(t);
		t = new Type("旺信", 1, R.drawable.wangxin);
		types.add(t);
		t = new Type("有信", 1, R.drawable.youxin);
		types.add(t);
		t = new Type("来往", 1, R.drawable.laiwang);
		types.add(t);
		t = new Type("网易微博", 1, R.drawable.weibo);
		types.add(t);
		t = new Type("人人网", 1, R.drawable.renren);
		types.add(t);
		t = new Type("QQ空间", 1, R.drawable.qqzone);
		types.add(t);
		t = new Type("QQ微博", 1, R.drawable.qqweibo);
		types.add(t);
		t = new Type("猫扑", 1, R.drawable.mopo);
		types.add(t);
		t = new Type("开心网", 1, R.drawable.kaixinwang);
		types.add(t);
		t = new Type("天涯社区", 1, R.drawable.tianyashequ);
		types.add(t);
		t = new Type("facebook", 1, R.drawable.facebook);
		types.add(t);
		t = new Type("twitter", 1, R.drawable.twitter);
		types.add(t);
		t = new Type("汽车之家", 1, R.drawable.qichezhijia);
		types.add(t);
		t = new Type("有缘网", 1, R.drawable.youyuan);
		types.add(t);
		t = new Type("百合网", 1, R.drawable.baihe);
		types.add(t);
		t = new Type("约会吧", 1, R.drawable.yuehuiba);
		types.add(t);
		
		for(Type temp : types) {
			db.execSQL("insert into type (name, parentidx, imgidx) values (?, ?, ?)", new String[]{temp.getName(), String.valueOf(temp.getParentIdx()), String.valueOf(temp.getImgId())});
		}
		
		types = new ArrayList<Type>();
		t = new Type("淘宝", 2, R.drawable.taobaowang);
		types.add(t);
		t = new Type("天猫", 2, R.drawable.tianmo);
		types.add(t);
		t = new Type("京东商城", 2, R.drawable.jingdong);
		types.add(t);
		t = new Type("苏宁", 2, R.drawable.suning);
		types.add(t);
		t = new Type("凡客诚品", 2, R.drawable.fanke);
		types.add(t);
		t = new Type("58同城", 2, R.drawable.tongcheng58);
		types.add(t);
		t = new Type("七天酒店", 2, R.drawable.qitian);
		types.add(t);
		t = new Type("赶集网", 2, R.drawable.ganjiwang);
		types.add(t);
		t = new Type("阿里巴巴", 2, R.drawable.alibaba);
		types.add(t);
		t = new Type("智联招聘", 2, R.drawable.zhilian);
		types.add(t);
		t = new Type("51Job", 2, R.drawable.fiveonejob);
		types.add(t);
		t = new Type("大众点评", 2, R.drawable.dazhongdianping);
		types.add(t);
		t = new Type("拉手网", 2, R.drawable.lashou);
		types.add(t);
		t = new Type("美团网", 2, R.drawable.meituan);
		types.add(t);
		t = new Type("窝窝网", 2, R.drawable.wowo);
		types.add(t);
		t = new Type("360团购", 2, R.drawable.tuangou360);
		types.add(t);
		t = new Type("糯米网", 2, R.drawable.ruomi);
		types.add(t);
		t = new Type("当当网", 2, R.drawable.dangdangwang);
		types.add(t);
		t = new Type("聚美网", 2, R.drawable.jumei);
		types.add(t);
		t = new Type("聚划算", 2, R.drawable.juhuasuan);
		types.add(t);
		t = new Type("携程旅行", 2, R.drawable.xiechenglvxing);
		types.add(t);
		t = new Type("唯品会", 2, R.drawable.weipinhui);
		types.add(t);
		t = new Type("360天气", 2, R.drawable.tianqi360);
		types.add(t);
		t = new Type("墨迹天气", 2, R.drawable.mojitianqi);
		types.add(t);
		t = new Type("汽车之家", 2, R.drawable.qichezhijia);
		types.add(t);
		for(Type temp : types) {
			db.execSQL("insert into type (name, parentidx, imgidx) values (?, ?, ?)", new String[]{temp.getName(), String.valueOf(temp.getParentIdx()), String.valueOf(temp.getImgId())});
		}
		
		types = new ArrayList<Type>();
		t = new Type("优酷", 3, R.drawable.youku);
		types.add(t);
		t = new Type("土豆", 3, R.drawable.tudou);
		types.add(t);
		t = new Type("快播", 3, R.drawable.kuaibo);
		types.add(t);
		t = new Type("暴风影音", 3, R.drawable.baofengyingyin);
		types.add(t);
		t = new Type("风行", 3, R.drawable.fengxing);
		types.add(t);
		t = new Type("乐视", 3, R.drawable.leshi);
		types.add(t);
		t = new Type("PPLIVE", 3, R.drawable.pplive);
		types.add(t);
		t = new Type("搜狐视频", 3, R.drawable.souhushipin);
		types.add(t);
		t = new Type("迅雷看看", 3, R.drawable.xunleikankan);
		types.add(t);
		t = new Type("风云直播", 3, R.drawable.fengyunzhibo);
		types.add(t);
		t = new Type("酷狗", 3, R.drawable.kugou);
		types.add(t);
		t = new Type("QQ音乐", 3, R.drawable.qqyinyue);
		types.add(t);
		t = new Type("天天动听", 3, R.drawable.tiantiandongting);
		types.add(t);
		t = new Type("酷我音乐", 3, R.drawable.kuwo);
		types.add(t);
		for(Type temp : types) {
			db.execSQL("insert into type (name, parentidx, imgidx) values (?, ?, ?)", new String[]{temp.getName(), String.valueOf(temp.getParentIdx()), String.valueOf(temp.getImgId())});
		}
		
		types = new ArrayList<Type>();
		t = new Type("网易新闻", 4, R.drawable.wangyi);
		types.add(t);
		t = new Type("新浪新闻", 4, R.drawable.sina);
		types.add(t);	
		t = new Type("360新闻", 4, R.drawable.xinwen360);
		types.add(t);	
		t = new Type("头条", 4, R.drawable.routiao);
		types.add(t);	
		t = new Type("央视新闻", 4, R.drawable.yangshixinwen);
		types.add(t);	
		for(Type temp : types) {
			db.execSQL("insert into type (name, parentidx, imgidx) values (?, ?, ?)", new String[]{temp.getName(), String.valueOf(temp.getParentIdx()), String.valueOf(temp.getImgId())});
		}
		
		types = new ArrayList<Type>();
		t = new Type("163邮箱", 5, R.drawable.mail163);
		types.add(t);
		t = new Type("QQ邮箱", 5, R.drawable.qqmail);
		types.add(t);
		t = new Type("CN21邮箱", 5, R.drawable.cn21);
		types.add(t);
		t = new Type("GMail", 5, R.drawable.gmail);
		types.add(t);
		t = new Type("HotMail", 5, R.drawable.hotmail);
		types.add(t);
		t = new Type("沃邮箱", 5, R.drawable.womail);
		types.add(t);
		t = new Type("加密邮箱", 5, R.drawable.jiamimail);
		types.add(t);
		for(Type temp : types) {
			db.execSQL("insert into type (name, parentidx, imgidx) values (?, ?, ?)", new String[]{temp.getName(), String.valueOf(temp.getParentIdx()), String.valueOf(temp.getImgId())});
		}
		
		types = new ArrayList<Type>();
		t = new Type("360云", 6, R.drawable.yun360);
		types.add(t);
		t = new Type("QQ微云", 6, R.drawable.qqweiyun);
		types.add(t);
		t = new Type("酷云", 6, R.drawable.kuyun);
		types.add(t);
		t = new Type("网易云阅读", 6, R.drawable.wangyiyunyuedu);
		types.add(t);
		t = new Type("有道云笔记", 6, R.drawable.youdaoyun);
		types.add(t);
		t = new Type("网易云相册", 6, R.drawable.wangyiyunxiangce);
		types.add(t);
		t = new Type("云相册", 6, R.drawable.yunxiangce);
		types.add(t);
		for(Type temp : types) {
			db.execSQL("insert into type (name, parentidx, imgidx) values (?, ?, ?)", new String[]{temp.getName(), String.valueOf(temp.getParentIdx()), String.valueOf(temp.getImgId())});
		}
		
		types = new ArrayList<Type>();
		t = new Type("我叫MT", 7, R.drawable.wojiaomt);
		types.add(t);
		t = new Type("刀塔传奇", 7, R.drawable.daotachuanqi);
		types.add(t);
		t = new Type("天朝小将", 7, R.drawable.tianchaoxiaojiang);
		types.add(t);
		t = new Type("魔兽世界", 7, R.drawable.wow);
		types.add(t);
		t = new Type("剑灵", 7, R.drawable.jianling);
		types.add(t);
		t = new Type("英雄联盟", 7, R.drawable.lol);
		types.add(t);
		t = new Type("撸啊撸", 7, R.drawable.lualu);
		types.add(t);
		t = new Type("大掌门", 7, R.drawable.dazhangmen);
		types.add(t);
		t = new Type("怪物X联盟", 7, R.drawable.guaiwuxlianmeng);
		types.add(t);
		t = new Type("三国合伙人", 7, R.drawable.sanguohehuoren);
		types.add(t);
		t = new Type("霸气三国", 7, R.drawable.baqisanguo);
		types.add(t);
		t = new Type("二战风云", 7, R.drawable.erzhanfengyun);
		types.add(t);
		t = new Type("放开那三国", 7, R.drawable.fangkainasanguo);
		types.add(t);
		t = new Type("格斗之皇", 7, R.drawable.gedouzhihuan);
		types.add(t);
		t = new Type("幻仙", 7, R.drawable.huanxian);
		types.add(t);
		t = new Type("口袋海贼王", 7, R.drawable.koudaihaizeiwang);
		types.add(t);
		t = new Type("口袋龙珠", 7, R.drawable.koudailongzhu);
		types.add(t);
		t = new Type("口袋忍者", 7, R.drawable.koudairenzhe);
		types.add(t);
		t = new Type("恋舞OL", 7, R.drawable.lianwuol);
		types.add(t);
		t = new Type("灵异阴阳录", 7, R.drawable.lingyiyinyanglu);
		types.add(t);
		t = new Type("龙之召唤", 7, R.drawable.longzhizhaohuan);
		types.add(t);
		t = new Type("萌江湖", 7, R.drawable.mengjianghu);
		types.add(t);
		t = new Type("NBA梦之队", 7, R.drawable.nbamengzhidui);
		types.add(t);
		t = new Type("逆天仙魔录", 7, R.drawable.nitianxianmolu);
		types.add(t);
		t = new Type("跑跑卡丁车", 7, R.drawable.pappaokading);
		types.add(t);
		t = new Type("三国时代", 7, R.drawable.sanguoshidai);
		types.add(t);
		t = new Type("神魔", 7, R.drawable.shenmo);
		types.add(t);
		t = new Type("时空猎人", 7, R.drawable.shikonglieren);
		types.add(t);
		t = new Type("坦克风云", 7, R.drawable.tankefengyun);
		types.add(t);
		t = new Type("天天西游", 7, R.drawable.tiantianaixitou);
		types.add(t);
		t = new Type("武侠Q传", 7, R.drawable.wuxiangqzhuan);
		types.add(t);
		t = new Type("降魔神话", 7, R.drawable.xiangmoshenhua);
		types.add(t);
		t = new Type("仙战OL", 7, R.drawable.xianzhanol);
		types.add(t);
		
		for(Type temp : types) {
			db.execSQL("insert into type (name, parentidx, imgidx) values (?, ?, ?)", new String[]{temp.getName(), String.valueOf(temp.getParentIdx()), String.valueOf(temp.getImgId())});
		}
		
		types = new ArrayList<Type>();
		t = new Type("WIFI账号", 8, R.drawable.wifi);
		types.add(t);
		
		for(Type temp : types) {
			db.execSQL("insert into type (name, parentidx, imgidx) values (?, ?, ?)", new String[]{temp.getName(), String.valueOf(temp.getParentIdx()), String.valueOf(temp.getImgId())});
		}
	}
	
}


















