package cn.way.wandroid.text;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.URLSpan;
import android.widget.TextView;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;

public class TextUseage extends BaseFragmentActivity {
//	ParcelableSpan
	//TODO: using span to set special words with be different in line 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usage_applation);
		TextView tv = (TextView) findViewById(R.id.textVeiw);
		tv.setTextColor(Color.WHITE);//默认颜色WHITE
		
		String part1 = "默认";
		String part2s = "abc 特 殊 123";
		String part3 = "默认\n";
		String part4s = "http://www.baidu.com";
//		Parcel p = Parcel.obtain();
//		p.writeInt(Color.YELLOW);
//		p.setDataPosition(0);
//		ForegroundColorSpan fcsYellow = new ForegroundColorSpan(p);
//		p.recycle();
		Spannable spn = SpannableStringBuilder.valueOf(part1+part2s+part3+part4s);
		int start = part1.length();
		int end = start + part2s.length();
		spn.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		spn.setSpan(new BackgroundColorSpan(Color.WHITE), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		spn.setSpan(new StrikethroughSpan(), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		start = end + part3.length();
		end = start + part4s.length();
		
		URLSpan urlSpan = new URLSpan("http://www.baidu.com");
		spn.setSpan(urlSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);//SPAN_EXCLUSIVE_INCLUSIVE|SPAN_EXCLUSIVE_EXCLUSIVE
		tv.setMovementMethod(LinkMovementMethod.getInstance());
		tv.setText(spn);
	}
}
