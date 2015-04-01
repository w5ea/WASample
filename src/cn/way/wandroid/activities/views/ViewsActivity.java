package cn.way.wandroid.activities.views;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import cn.way.wandroid.R;
import cn.way.wandroid.utils.WLog;
import cn.way.wandroid.views.CountdownView;
import cn.way.wandroid.views.CountdownView.CountdownListener;
import cn.way.wandroid.views.PageIndicator;
import cn.way.wandroid.views.WSeekBar;

public class ViewsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_views);
		PageIndicator pageIndicator = (PageIndicator) findViewById(R.id.pageIndicator);
		pageIndicator.init(R.drawable.selector_dot, 8,0,0);
		final CountdownView cv = (CountdownView) findViewById(R.id.countdownView);
		cv.start(5);
		cv.setCountdownListener(new CountdownListener() {
			@Override
			public void onCountdown(int leftTimeSeconds) {
				WLog.d("wwwww");
				if (leftTimeSeconds==0) {
					cv.stop();
				}
			}
		});
		
		final TextView sbValueTV = (TextView) findViewById(R.id.seekbar_progress_tv);
		WSeekBar sb = (WSeekBar) findViewById(R.id.seekbar);
//		sb.setIndeterminateDrawable(new BitmapDrawable(getResources(),BitmapFactory.decodeResource(getResources(), R.drawable.empty_photo)));
//		sb.setProgressDrawable(new BitmapDrawable(getResources(),BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		sb.setMax(10);
		sb.setThumb(new BitmapDrawable(getResources(),BitmapFactory.decodeResource(getResources(), R.drawable.selector_dot)));
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				sbValueTV.setText("progress="+progress);
			}
		});
		RatingBar rb = (RatingBar) findViewById(R.id.ratingbar);
		rb.setStepSize(1);
		rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				sbValueTV.setText("rating="+rating);
			}
		});
	}
}
