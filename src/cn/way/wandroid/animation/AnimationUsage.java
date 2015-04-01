package cn.way.wandroid.animation;

import java.util.ArrayList;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;
import cn.way.wandroid.utils.WTimer;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

public class AnimationUsage extends BaseFragmentActivity {
	private WTimer timer ;
	private ArrayList<View> views = new ArrayList<View>();
//	FlakeView flakeView;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(getApplicationContext(), "qqqq", 0).show();
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_animation_usage, null);
		setContentView(viewGroup);
		
		final View v1 = findViewById(R.id.v1);
		final View v2 = findViewById(R.id.v2);
		v1.setVisibility(View.INVISIBLE);
		v2.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
//				android.animation.ObjectAnimator.ofFloat(v2, "rotationY", 0, 180, 0).setDuration(3000).start();
				AnimationUtil.doFlipAnimation(1000,v1.getVisibility()==View.INVISIBLE?v1:v2, v2.getVisibility()==View.VISIBLE?v2:v1,new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
					}
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					@Override
					public void onAnimationEnd(Animation animation) {
					}
				});
			}
		});
		v1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AnimationUtil.doFlipAnimation(1000,v1.getVisibility()==View.INVISIBLE?v1:v2, v2.getVisibility()==View.VISIBLE?v2:v1,new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
					}
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					@Override
					public void onAnimationEnd(Animation animation) {
					}
				});
			}
		});
		
		try {
			new Thread() {
				@Override
				public void run() {
					super.run();
					Looper.prepare();
					handler.sendEmptyMessage(0);
					Looper.loop();
//					new AlertDialog.Builder(AnimationUsage.this).setTitle("wwwww").show();
//					Toast.makeText(getApplicationContext(), "wwwww", 0).show();
				}
			}.start();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.toString(), 0).show();
		}
		
		
//		flakeView = new FlakeView(this);
//		flakeView.setLayoutParams(new LayoutParams(500, 500));
//		viewGroup.addView(flakeView);
//		flakeView.setShowFps(true);
//		flakeView.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @SuppressWarnings("deprecation")
//					@TargetApi(VERSION_CODES.JELLY_BEAN)
//                    @Override
//                    public void onGlobalLayout() {
//                        if (Utils.hasJellyBean()) {
//                        	flakeView.getViewTreeObserver()
//                                    .removeOnGlobalLayoutListener(this);
//                        } else {
//                        	flakeView.getViewTreeObserver()
//                                    .removeGlobalOnLayoutListener(this);
//                        }
//                            
//                    }
//                });
//		viewGroup.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				flakeView.addFlakes(32, R.drawable.ic_launcher);
//				flakeView.addFlakes(32, R.drawable.empty_photo);
//			}
//		});
//		flakeView.start();
//		View iv1 =  findViewById(R.id.imageView1);
//		iv1.setVisibility(View.INVISIBLE);
//		views.add(iv1);
//		View iv2 = findViewById(R.id.imageView2);
//		iv2.setVisibility(View.INVISIBLE);
//		views.add(iv2);
//		View iv3 = findViewById(R.id.imageView3);
//		iv3.setVisibility(View.INVISIBLE);
//		views.add(iv3);
//		View iv4 = findViewById(R.id.imageView4);
//		iv4.setVisibility(View.INVISIBLE);
//		views.add(iv4);
//		View iv5 = findViewById(R.id.imageView5);
//		iv5.setVisibility(View.INVISIBLE);
//		views.add(iv5);
//		
//		for (View view : views) {
//			doFlashAnimation(view);
//		}
//		if (timer==null) {
//			timer = new WTimer() {
//				@Override
//				protected void onTimeGoesBy(long totalTimeLength) {
//					for (View view : views) {
//						doDropAnimation(view);
//					}
//				}
//			};
//			timer.schedule(dur+maxOffset+stay, null, null);
//		}
	}
	private final long dur = 2000*1;
	private final long maxOffset = 1000*4;
	private final long stay = 500;
	private void doFlashAnimation(View view){
		AnimationSet as = new AnimationSet(false);
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(dur);
		AlphaAnimation aar = new AlphaAnimation(1, 0);
		aar.setDuration(dur);
		aar.setStartOffset(stay);
		as.addAnimation(aa);
		as.addAnimation(aar);
		as.setStartOffset((long)(maxOffset*new Random().nextFloat()));
		view.startAnimation(as);
	}
	
	private void doDropAnimation(View view){
		AnimationSet as = new AnimationSet(false);
		
		AlphaAnimation aar = new AlphaAnimation(0, 1);
		aar.setDuration(100);
		as.addAnimation(aar);
		
		float dropDistance = 960*getResources().getDisplayMetrics().density;
		TranslateAnimation aa = new TranslateAnimation(view.getLeft(),view.getLeft(),view.getTop(),view.getTop()+dropDistance);
		aa.setDuration(dur);
		as.addAnimation(aa);
		
//		RotateAnimation ra = new RotateAnimation(0, 180*new Random().nextFloat(), view.getWidth()/2, view.getHeight()/2);
//		float fdur = dur/2*new Random().nextFloat();
//		ra.setDuration((long)(dur/2+fdur));
//		as.addAnimation(ra);
		
		as.setStartOffset((long)(maxOffset*new Random().nextFloat()));
		view.startAnimation(as);
	}
	public void doJumpAnimation(View view){
		ObjectAnimator move = ObjectAnimator.ofFloat(view, "translationY",
				0,-600,
				0,-550,
				0,-490,
				0,-420,
				0,-360,0).setDuration(500);
		move.setInterpolator(new AccelerateDecelerateInterpolator());
		move.start();
	}
	private void doBounceAnimation(View view){
		float startY = getY(view);
        float endY = startY - 500f;
        float h = (float)view.getHeight();
//        float eventY = event.getY();
        int duration = (int)(5000 * ((h - startY)/h));
        ValueAnimator bounceAnim = ObjectAnimator.ofFloat(view, "y", startY, endY);
        bounceAnim.setDuration(duration);
        bounceAnim.setInterpolator(new AccelerateInterpolator());
        ValueAnimator squashAnim1 = ObjectAnimator.ofFloat(view, "x", getX(view),
                getX(view) - 25f);
        squashAnim1.setDuration(duration/4);
        squashAnim1.setRepeatCount(1);
        squashAnim1.setRepeatMode(ValueAnimator.REVERSE);
        squashAnim1.setInterpolator(new DecelerateInterpolator());
        ValueAnimator squashAnim2 = ObjectAnimator.ofFloat(view, "width", view.getWidth(),
                view.getWidth() + 50);
        squashAnim2.setDuration(duration/4);
        squashAnim2.setRepeatCount(1);
        squashAnim2.setRepeatMode(ValueAnimator.REVERSE);
        squashAnim2.setInterpolator(new DecelerateInterpolator());
        ValueAnimator stretchAnim1 = ObjectAnimator.ofFloat(view, "y", endY,
                endY + 25f);
        stretchAnim1.setDuration(duration/4);
        stretchAnim1.setRepeatCount(1);
        stretchAnim1.setInterpolator(new DecelerateInterpolator());
        stretchAnim1.setRepeatMode(ValueAnimator.REVERSE);
        ValueAnimator stretchAnim2 = ObjectAnimator.ofFloat(view, "height",
                view.getHeight(), view.getHeight() - 25);
        stretchAnim2.setDuration(duration/4);
        stretchAnim2.setRepeatCount(1);
        stretchAnim2.setInterpolator(new DecelerateInterpolator());
        stretchAnim2.setRepeatMode(ValueAnimator.REVERSE);
        ValueAnimator bounceBackAnim = ObjectAnimator.ofFloat(view, "y", endY,
                startY);
        bounceBackAnim.setDuration(duration);
        bounceBackAnim.setInterpolator(new DecelerateInterpolator());
        // Sequence the down/squash&stretch/up animations
        AnimatorSet bouncer = new AnimatorSet();
        bouncer.play(bounceAnim).before(squashAnim1);
        bouncer.play(squashAnim1).with(squashAnim2);
        bouncer.play(squashAnim1).with(stretchAnim1);
        bouncer.play(squashAnim1).with(stretchAnim2);
        bouncer.play(bounceBackAnim).after(stretchAnim2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(bouncer);
        animatorSet.start();
	}
	private float getX(View view){
		return ViewHelper.getX(view);
	}
	private float getY(View view){
		return ViewHelper.getY(view);
	}
	@Override
    protected void onPause() {
        super.onPause();
//        flakeView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        flakeView.resume();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
//    	flakeView.cancel();
    }
}
