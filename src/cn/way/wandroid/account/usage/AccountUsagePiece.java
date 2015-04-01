package cn.way.wandroid.account.usage;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.way.wandroid.R;
import cn.way.wandroid.account.Account;
import cn.way.wandroid.account.AccountManager;
import cn.way.wandroid.account.SpAccountPersister;
import cn.way.wandroid.activityadapter.Piece;

public class AccountUsagePiece extends Piece<AccountUsagePageAdapter> {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SpAccountPersister ap = SpAccountPersister.defaultInstance(getActivity());
		AccountManager am = AccountManager.instance(ap);
		Account account = new Account();
//		if (ap.add(account)) {
//			Toast.makeText(getActivity(), "success", 0).show();
//		}else{
//			Toast.makeText(getActivity(), "failure", 0).show();
//		}
//		ArrayList<Account>accounts = ap.readAll();
//		if (accounts.size()>0) {
//			account = accounts.get(0);
//			account.setIdentity("123456");
//		Log.w("test", accounts.get(0).toString());
//		}else{
//			ap.add(new Account());
//		}
//		Log.w("test", accounts.getClass().toString());
//		ap.update(account);
		Gson gson = new Gson();
		Account[] as = new Account[]{new Account(),new Account()};
		ArrayList<Account>accounts = new ArrayList<Account>(Arrays.asList(as));
		Account a = new Account();
		a.setIdentity("123456");
		accounts.add(a);
		accounts.toArray();
		Log.w("test", gson.fromJson(gson.toJson(accounts.toArray()), Account[].class)[accounts.size()-1].toString());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.account_usage_piece, container, false);
		return view;
	}
	@Override
	public void onPageReady() {
		// TODO Auto-generated method stub
		
	}

}
