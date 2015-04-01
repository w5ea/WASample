package cn.way.wandroid.activityadapter.usage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.way.wandroid.activityadapter.Piece;

public class TestPiece extends Piece<TestPageAdapter> {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return new TextView(getActivity());
	}
	@Override
	public void onPageReady() {
		Toast.makeText(getActivity(), "PageReady", Toast.LENGTH_SHORT).show();
	}

}
