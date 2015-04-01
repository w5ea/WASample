package cn.way.wandroid.data.greendao;

import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;
import cn.way.wandroid.data.gen.DaoMaster;
import cn.way.wandroid.data.gen.DaoMaster.DevOpenHelper;
import cn.way.wandroid.data.gen.DaoSession;
import cn.way.wandroid.data.gen.User;
import cn.way.wandroid.data.gen.UserDao;

import com.google.gson.Gson;

import de.greenrobot.dao.query.QueryBuilder;

public class GreenDaoUsage extends BaseFragmentActivity {
	DaoSession daoSession;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_greendao_usage);
		final TextView tv = (TextView) findViewById(R.id.textView1);
		
		DevOpenHelper helper = new DevOpenHelper(this, "UsersTable", null);
		DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
		daoSession = daoMaster.newSession();
		UserDao userDao = daoSession.getUserDao();
		userDao.insertOrReplace(new User(1000L, "GGG", "222", "456", new Date()));
		QueryBuilder<User> qb = userDao.queryBuilder();
		List<User> users = qb.list();
		tv.setText(new Gson().toJson(users));
		
		tv.setClickable(true);
		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tv.setText(new Gson().toJson(queryUserById("GGG")));
			}
		});
		
	}
	
	private User queryUserById(String identity){
		return daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Identity.eq(identity)).unique();
	}
	@SuppressWarnings("unused")
	private void updateUser(){
//		User user = queryUserById("GGG");
//		daoSession.getUserDao().up
	}
}
