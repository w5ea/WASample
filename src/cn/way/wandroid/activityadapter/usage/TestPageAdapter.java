package cn.way.wandroid.activityadapter.usage;

import cn.way.wandroid.R;
import cn.way.wandroid.activityadapter.PageAdapter;

public class TestPageAdapter extends PageAdapter {

	@Override
	public int getLayoutId() {
		return R.layout.activityadapter_page_test;
	}

	@Override
	public int[] getPieceIds() {
		return new int[]{R.id.testPiece};
	}

}
