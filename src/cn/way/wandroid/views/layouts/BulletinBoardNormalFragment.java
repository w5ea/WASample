package cn.way.wandroid.views.layouts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import cn.way.wandroid.R;
import cn.way.wandroid.animation.FlipAnimation;

public class BulletinBoardNormalFragment extends Fragment {

	View bulletin1_1;
	View bulletin1_2;

	View bulletin1_currentView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bulletin_board_normal,
				container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		bulletin1_1 = getView().findViewById(R.id.normal_bulletin1_1);
		bulletin1_2 = getView().findViewById(R.id.normal_bulletin1_2);
		bulletin1_currentView = bulletin1_1;
		bulletin1_2.setVisibility(View.INVISIBLE);

		final View mainView = getView();
		mainView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						// Ensure you call it only once :
//						mainView.getViewTreeObserver()
//								.removeOnGlobalLayoutListener(this);
						doExchangeAnimation(bulletin1_2, bulletin1_1);
					}
				});
	}

	void doExchangeAnimation(final View viewToShow, final View viewToHide) {
		long dur = 5000;
		float scaleValue = 0.95f;
		FlipAnimation fa1 = new FlipAnimation(90, 0, viewToHide.getWidth() / 2,
				0, scaleValue, FlipAnimation.ScaleUpDownEnum.SCALE_UP);
		fa1.setDuration(dur);
		fa1.setDirection(FlipAnimation.ROTATION_X);
		fa1.setInterpolator(new AccelerateInterpolator(0.75f));
		viewToHide.startAnimation(fa1);

		FlipAnimation fa2 = new FlipAnimation(0, -90,
				viewToShow.getWidth() / 2, viewToShow.getHeight(), scaleValue,
				FlipAnimation.ScaleUpDownEnum.SCALE_DOWN);
		fa2.setDuration(dur);
		fa2.setInterpolator(new DecelerateInterpolator(0.95f));
		fa2.setDirection(FlipAnimation.ROTATION_X);
		viewToShow.startAnimation(fa2);

		fa2.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				bulletin1_currentView.setVisibility(View.INVISIBLE);
				if (bulletin1_currentView == bulletin1_1) {
					bulletin1_currentView = bulletin1_2;
				} else {
					bulletin1_currentView = bulletin1_1;
				}
				bulletin1_currentView.setVisibility(View.VISIBLE);

				doExchangeAnimation(viewToHide, viewToShow);
			}
		});
	}
}
