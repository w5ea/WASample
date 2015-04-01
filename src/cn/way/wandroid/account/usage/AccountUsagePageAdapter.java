package cn.way.wandroid.account.usage;

import cn.way.wandroid.R;
import cn.way.wandroid.activityadapter.PageAdapter;

public class AccountUsagePageAdapter extends PageAdapter{

	@Override
	public int getLayoutId() {
		return R.layout.account_usage_page;
	}

	@Override
	public int[] getPieceIds() {
		return new int[]{R.id.accountUsagePiece};
	}

}
