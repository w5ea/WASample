package cn.way.wandroid.animation;

import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import cn.way.wandroid.R;
import cn.way.wandroid.slidingmenu.Utils;
import cn.way.wandroid.utils.WTimer;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class DropAnimatorView extends FrameLayout {

	private ArrayList<View> views ;
	
	public DropAnimatorView(Context context) {
		super(context);
	}
	public DropAnimatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
                DropAnimatorView.this.getViewTreeObserver()
                            .removeGlobalOnLayoutListener(this);
                if (views == null) {
        			views = new ArrayList<View>();
        			setAnimationViews();
        			startAnimation();
        		}
			}
		});
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}
	private void setAnimationViews(){
		int topMargin = (int) (getHeight()/2-getResources().getDisplayMetrics().density*60);
		for (int i = 0; i < 2; i++) {
			LinearLayout ll = new LinearLayout(getContext());
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1);
			ll.setLayoutParams(lp);
//			lp.leftMargin = -60+i*30;
			lp.topMargin = topMargin;
			addViews(ll);
			addView(ll);
		}
		for (int i = 0; i < 3; i++) {
			LinearLayout ll = new LinearLayout(getContext());
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1);
			ll.setLayoutParams(lp);
//			lp.leftMargin = -60+i*30;
			lp.topMargin = topMargin;
			addViews(ll);
			addView(ll);
		}
	}
	private void addViews(ViewGroup holder){
		int density = (int) getResources().getDisplayMetrics().density;
		int size = 30*density;
		int count = 320*density/size;
		for (int i = 0; i < count; i++) {
			ImageView iv = null;
			if (i%2==0) {
				iv = new ImageView(getContext());
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size,1);
				iv.setImageResource(R.drawable.ic_launcher);
				iv.setLayoutParams(lp);
			}else{
				iv = new ImageView(getContext());
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size,1);
				iv.setImageResource(R.drawable.empty_photo);
				iv.setLayoutParams(lp);
			}
			if (iv!=null) {
				iv.setVisibility(View.INVISIBLE);
				iv.setScaleType(ScaleType.FIT_CENTER);
				holder.addView(iv);
				views.add(iv);
			}
		}
	}
	private final long dur = 1500*2;
	private final long maxOffset = 1000*2;
	private WTimer timer;
	@SuppressLint("NewApi")
	private void doDropAnimation(final View view){
		if(Utils.hasHoneycomb()){
			view.setRotation(180*new Random().nextFloat());
			float scaleValue = 0.5f+(0.2f*new Random().nextFloat()*(new Random().nextBoolean()?1:-1));
			view.setScaleX(scaleValue);
			view.setScaleY(scaleValue);
		}else{
//			view.setAlpha(0);
		}
//		view.setVisibility(View.VISIBLE);
		
		long delay = (long)(maxOffset*new Random().nextFloat());
		if (view.getTag()!=null) {
			AnimatorSet as = (AnimatorSet) view.getTag();
			as.setStartDelay(delay);
			as.start();
			return;
		}
//		as.setFillAfter(true);
//		as.setFillBefore(true);
		float dropDistance = getHeight()/2;//*getResources().getDisplayMetrics().density;
	
		ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", view.getLeft(),view.getLeft(),view.getTop(),view.getTop()+dropDistance).setDuration(dur);
		translationY.setInterpolator(new AccelerateInterpolator());
		
		AnimatorSet as = new AnimatorSet();
		as.setStartDelay(delay);
		translationY.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
				view.setVisibility(View.VISIBLE);
//				view.setAlpha(1);
			}
			@Override
			public void onAnimationRepeat(Animator arg0) {
				view.setVisibility(View.VISIBLE);
//				view.setAlpha(1);
			}
			@Override
			public void onAnimationEnd(Animator arg0) {
				view.setVisibility(View.INVISIBLE);
//				view.setAlpha(0);
			}
			@Override
			public void onAnimationCancel(Animator arg0) {
			}
		});
        as.playTogether(translationY);
		view.setTag(as);
		as.start();
	}
	public void startAnimation(){
		if (views==null||views.size()==0) {
			return;
		}
		for (View s : views) {
			doDropAnimation(s);
		}
		if (timer==null) {
			timer = new WTimer() {
				@Override
				protected void onTimeGoesBy(long totalTimeLength) {
					for (View view : views) {
						doDropAnimation(view);
					}
				}
			};
			timer.schedule(dur+maxOffset, null, null);
		}
	}
	
	public void stopAnimation(){
		if (timer!=null) {
			timer.cancel();
			timer = null;
		}
	}
}
