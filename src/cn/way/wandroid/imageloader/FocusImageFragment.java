package cn.way.wandroid.imageloader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.way.wandroid.R;
import cn.way.wandroid.imageloader.universal.ImageManager;

public class FocusImageFragment extends Fragment {
    private static final String IMAGE_DATA_EXTRA = "extra_image_data";
    private String mImageUrl;
    private ImageView mImageView;
    private int layoutId;
    private OnClickListener clickListener;
  
    public static FocusImageFragment newInstance(int layoutId,String imageUrl,OnClickListener clickListener) {
        final FocusImageFragment f = new FocusImageFragment();
        f.setLayoutId(layoutId);
        f.setClickListener(clickListener);
        final Bundle args = new Bundle();
        args.putString(IMAGE_DATA_EXTRA, imageUrl);
        f.setArguments(args);
        return f;
    }

    /**
     * Populate image using a url from extras, use the convenience factory method
     * {@link FocusImageFragment#newInstance(String)} to create this fragment.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString(IMAGE_DATA_EXTRA) : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate and locate the main ImageView
    	View v = null;
        try {
			v = inflater.inflate(getLayoutId(), container, false);
			mImageView = (ImageView) v.findViewById(R.id.imageView);
			mImageView.setOnClickListener(clickListener);
		} catch (Exception e) {
		}
		return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageManager.instance(getActivity()).loadImage(mImageUrl, mImageView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImageView != null) {
            mImageView.setImageDrawable(null);
        }
    }

	
	public OnClickListener getClickListener() {
		return clickListener;
	}

	public void setClickListener(OnClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public int getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
	}
}
