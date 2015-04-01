package cn.way.wandroid;

import cn.way.wandroid.activityadapter.PageAdapter;

public class UsageListPage extends PageAdapter {

	@Override
	public int getLayoutId() {
		return R.layout.usage_list_page;
	}

	@Override
	public int[] getPieceIds() {
		return new int[]{R.id.usageListPiece};
	}

}
