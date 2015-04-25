package cn.way.wandroid.views.layouts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import cn.way.wandroid.R;
import cn.way.wandroid.imageloader.ImageManager;
import cn.way.wandroid.toast.Toaster;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

/**
 * @author Wayne
 * @2015年3月27日
 */
public class ImagePreviewDialog extends Dialog {
	private ImageView imageView;
	private View progressBar;
//	private
	@SuppressLint("InflateParams")
	public ImagePreviewDialog(Context context) {
		super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		View dialogContentView = getLayoutInflater().inflate(R.layout.dialog_image_preview, null);
		imageView = (ImageView) dialogContentView.findViewById(R.id.imageView);
		progressBar =  dialogContentView.findViewById(R.id.progressBar);
		setContentView(dialogContentView);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		imageView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				showConfirmDialog();
				return true;
			}
		});
	}
	
	public void showConfirmDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),android.R.style.Theme_Holo_Panel);
		builder.setTitle("温馨提示");
		builder.setMessage("确定保存到相册？");
		builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveImage();
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}
	
	private File file;
	private String fileName;
	public void setImageUrl(String url){
		progressBar.setVisibility(View.VISIBLE);
		fileName = System.currentTimeMillis()+"";
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new FileAsyncHttpResponseHandler(getContext()) {
			@Override
			public void onFinish() {
				super.onFinish();
				progressBar.setVisibility(View.INVISIBLE);
			}
			@Override
			public void onSuccess(int arg0, Header[] arg1, File arg2) {
				file = arg2;
				if (file!=null&&file.exists()) {
					imageView.setImageURI(Uri.fromFile(arg2));
				}
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2, File arg3) {
			}
		});
	}
	private void saveImage(){
		if (file==null) {
			return;
		}
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			saveBitmap(bitmap);
			ImageManager.saveImageToPhotoAlbum(getContext(), file.getAbsolutePath());
		} catch (Exception e) {
		}
	}

	void saveImageView(ImageView iv){
		iv.setDrawingCacheEnabled(true);  
		Bitmap bitmap = Bitmap.createBitmap(iv.getDrawingCache());  
		iv.setDrawingCacheEnabled(false); 
		saveBitmap(bitmap);
	}
	void saveBitmap(Bitmap bitmap){
//		File dir = new File(Environment.getExternalStorageDirectory()+"/DCIM/Pictures/");
		File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		try {
			dir.mkdirs();
			FileOutputStream os = new FileOutputStream(new File(dir, fileName+".png"));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
			os.flush();os.close();
			Toaster.instance(getContext()).setup("图片保存完毕").show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
