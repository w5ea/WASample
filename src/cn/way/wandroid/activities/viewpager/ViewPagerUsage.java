package cn.way.wandroid.activities.viewpager;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.way.wandroid.R;
import cn.way.wandroid.imageloader.displayingbitmaps.ui.FocusImageFragment;
import cn.way.wandroid.imageloader.loader.ImageLoader;
import cn.way.wandroid.utils.PageNavigateManager;
import cn.way.wandroid.utils.PageNavigateManager.PageNavigateTag;
import cn.way.wandroid.utils.WTimer;
import cn.way.wandroid.views.PageIndicator;
import cn.way.wandroid.views.WViewPager;

public class ViewPagerUsage extends FragmentActivity {
	private ArrayList<Fragment>fragments = new ArrayList<Fragment>();
//	private ArrayList<View> views = new ArrayList<View>();
	WTimer timer;
	private ImageLoader imageLoader;
    public ImageLoader getImageLoader() {
		if (imageLoader==null) {
			imageLoader = new ImageLoader();
			int width = (int) (100*getResources().getDisplayMetrics().density*2);
			int height = (int) (100*getResources().getDisplayMetrics().density*2);
			imageLoader.init(this, width,height);
		}
		return imageLoader;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);
		
		PageNavigateManager.setTag(PageNavigateTag.PageNavigateTagPrizes);
		if (PageNavigateManager.getTag()==PageNavigateTag.PageNavigateTagPrizes) {
			
		}
		final WViewPager pager = (WViewPager) findViewById(R.id.viewPager);
		pager.setScrollable(false);
//		final ArrayList<String>imageUrls = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
//			fragments.add(new SimpleFragment().setTitle(""+i));
			
			fragments.add(FocusImageFragment.newInstance(R.layout.image_detail_fragment,getImageLoader(), "http://cn.bing.com/th?id=OJ.7V064eoyjkShZg&pid=MSNJVFeeds",new OnClickListener() {
				@Override
				public void onClick(View v) {
//					Toast.makeText(getApplicationContext(), "wwww", 0).show();
					int currentIndex = pager.getCurrentItem()+1;
					if (currentIndex>=fragments.size()) {
						currentIndex = 0;
					}
					pager.setCurrentItem(currentIndex,true);
				}
			}));
			
//			imageUrls.add("http://cn.bing.com/th?id=OJ.7V064eoyjkShZg&pid=MSNJVFeeds");
			
//			View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
//			TextView tv = (TextView) view.findViewById(android.R.id.text1);
//			tv.setText(i+"");
//			views.add(view);
		}
		final PageIndicator pageIndicator = (PageIndicator) findViewById(R.id.pageIndicator);
		pageIndicator.init(R.drawable.selector_dot, fragments.size(),0,0);
//		pager.setAdapter(new FocusImagePagerAdapter(getSupportFragmentManager(), getImageLoader(), imageUrls));
		pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return fragments.size();
			}
			@Override
			public Fragment getItem(int position) {
				return fragments.get(position);
			}
			/*重写这个方法。来保证在调用pager.getAdapter().notifyDataSetChanged();时能正确执行*/
			@Override 
		    public int getItemPosition(Object object) { 
		        return POSITION_NONE; 
		    }
		});
		timer = new WTimer() {
			@Override
			protected void onTimeGoesBy(long totalTimeLength) {
//				imageUrls.add("http://cn.bing.com/th?id=OJ.7V064eoyjkShZg&pid=MSNJVFeeds");
//				imageUrls.add("http://cn.bing.com/th?id=OJ.7V064eoyjkShZg&pid=MSNJVFeeds");
//				imageUrls.add("http://cn.bing.com/th?id=OJ.7V064eoyjkShZg&pid=MSNJVFeeds");
//				imageUrls.remove(0);
//				imageUrls.remove(0);
//				imageUrls.remove(0);
				fragments.clear();
				for (int i = 0; i < 3; i++) {
					fragments.add(FocusImageFragment.newInstance(R.layout.image_detail_fragment,getImageLoader(), "http://cn.bing.com/th?id=OJ.7V064eoyjkShZg&pid=MSNJVFeeds",null));
				}
//				fragments.remove(0);
//				fragments.remove(0);
				pager.getAdapter().notifyDataSetChanged();
				pageIndicator.setNumberOfPages(fragments.size());
			}
		}.schedule(1000l, 1000l, 3000l);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				pageIndicator.setCurrentPageIndex(position);
			}
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
//		pager.setAdapter(new PagerAdapter() {
//			
//			@Override
//			public Object instantiateItem(ViewGroup container, int position) {
//				View view = views.get(position);
//				container.addView(view,0);
//				return view;
//			}
//			
//			@Override
//			public void destroyItem(ViewGroup container, int position,
//					Object object) {
//				container.removeView(views.get(position));
//			}
//			
//			@Override
//			public boolean isViewFromObject(View view, Object object) {
//				return view == object;
//			}
//			
//			@Override
//			public int getCount() {
//				return views.size();
//			}
//		});
		
	}
	@Override
	protected void onStop() {
		super.onStop();
		if (timer!=null) {
			timer.cancel();
			timer = null;
		}
	}
	public static class SimpleFragment extends Fragment{
		View view;
		TextView tv;
		String title;
		@SuppressLint("InflateParams")
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			view = getActivity().getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null);
			tv = (TextView) view.findViewById(android.R.id.text1);
			tv.setText(title);
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			if (view.getParent() != null)
				((ViewGroup) view.getParent()).removeView(view);
			return view;
		}
		
		public SimpleFragment setTitle(String title){
			this.title = title;
			return this;
		}
	}
	
}
