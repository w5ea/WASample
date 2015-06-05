/**
 * 
 */
package cn.way.wandroid.applation;

import java.io.File;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.way.wandroid.R;
import cn.way.wandroid.toast.Toaster;
import cn.way.wandroid.utils.WLog;

/**
 * 
 * @author Wayne
 * @d2015年5月14日
 */
public class PhotoPickerUsage extends Fragment {
	private String[] areas = { "拍照", "从手机相册中选择", "取消" };
	private Bitmap photo;
	private PhotoView iv;
	private File photoTempOFile;//通过一个缓存文件来保存原始图像
	private File photoTempFile;//通过一个缓存文件来保存
	private int imageWidth = 1024;//最终图片的宽
	private int imageHeight = 1024;//最终图片的高
	/**拍照*/
	private static final int TAKE_PHOTO = 1001;
	/**从相册选取*/
	private static final int PICK_PHOTO = 1002;
	/**剪裁图片*/
	private static final int CROP_IMAGE = 1003;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		photoTempOFile = new File(getActivity().getExternalCacheDir(),"ophoto_temp.jpg");
		photoTempFile = new File(getActivity().getExternalCacheDir(),"photo_temp.jpg");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(cn.way.wandroid.R.layout.usage_photo_picker, container, false);
		iv = (PhotoView) view.findViewById(R.id.imageView);
		view.findViewById(R.id.photoBtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showMenuAlert();
			}
		});
		return view;
	}

	private void showMenuAlert(){
		new AlertDialog.Builder(getActivity()).setTitle("选择操作")
		.setItems(areas, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					takePhoto(photoTempOFile);
					break;
				case 1:
					pickPhoto();
					break;
				}
			}
		}).show();
	}
	
	private void takePhoto(File target){
		Uri imageOutPutUri = Uri.fromFile(target);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra("noFaceDetection", false);//选择false，保证图片是有方向的。
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageOutPutUri);
		startActivityForResult(intent, TAKE_PHOTO);
	}
	private void pickPhoto(){
		Intent intent = new Intent();
		intent.setType("image/*");
//		intent.putExtra("crop", "true");
//		// aspectX aspectY 是宽高的比例
//		intent.putExtra("aspectX", 1);
//		intent.putExtra("aspectY", 1);
//		//outputX 为图片的宽 outputY 为图片的高
//		intent.putExtra("outputX", imageWidth);
//		intent.putExtra("outputY", imageHeight);
//		intent.putExtra("scale", false);
//		intent.putExtra("return-data", true);
//		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//		intent.putExtra("noFaceDetection", false);//选择false，保证图片是有方向的。
		if (Build.VERSION.SDK_INT <19) {  
			intent.setAction(Intent.ACTION_GET_CONTENT);  
		}else {  
			intent.setAction(Intent.ACTION_PICK);  
		}  
		startActivityForResult(intent, PICK_PHOTO);
	}
	/**
	 * 剪裁图片
	 * @param inputUri 要被剪裁的图片URI
	 * @param outputUri 剪裁后的图片保存到的URI
	 * @param imageWidth 剪裁后的图片宽
	 * @param imageHeight 剪裁后的图片高
	 * @param requestCode 请求码
	 */
	private void cropImage(Uri inputUri, Uri outputUri, int imageWidth, int imageHeight, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(inputUri, "image/*");
		intent.putExtra("crop", "true");
		//aspectX aspectY 表示图片宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		//如果限制图片大小需要设置下面的属性，outputX 为图片的宽 outputY 为图片的高
		intent.putExtra("outputX", imageWidth);
		intent.putExtra("outputY", imageHeight);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		WLog.d("resultCode: "+resultCode+" data = " + data);
		switch (requestCode) {
		case TAKE_PHOTO:
			//设置竖屏来保证图片正立,当在extra中noFaceDetection设置为false时就不需要了。
//			getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			if (resultCode == Activity.RESULT_OK) {
				Uri imageUriO = Uri.fromFile(photoTempOFile);
				Uri imageUri = Uri.fromFile(photoTempFile);
				cropImage(imageUriO, imageUri, imageWidth, imageHeight, CROP_IMAGE);
				return;
//				photo = BitmapFactory.decodeFile(photoTempFile.getAbsolutePath());
			}
			break;
		case PICK_PHOTO:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					Uri uri = 
							data.getData();
//					Uri.fromFile(photoTempFile);
//					photo = data.getParcelableExtra("data");
//					if(photo == null&&uri!=null){
//						photo = getBitmap(uri);
//						WLog.d("PICK_PHOTO frome URI:"+uri);
//					}
//					if (photo!=null&&uri!=null) {
//						if(photo.getWidth() > imageWidth && photo.getHeight() > imageHeight){
//							if(!photo.isRecycled())photo.recycle();
							if (uri!=null) {
								cropImage(uri, Uri.fromFile(photoTempFile), imageWidth, imageHeight, CROP_IMAGE);
								return;
							}
//						}
//					}
				} else {
					
				}
			}
			break;
		case CROP_IMAGE:
			if (resultCode == Activity.RESULT_OK) {
				Uri imageUri = Uri.fromFile(photoTempFile);
				if (imageUri != null) {
					photo = getBitmap(imageUri);
				} else {
				}
			}
			break;
		}
		photoTempOFile.delete();
		photoTempFile.delete();
		if (photo != null) {
			iv.setImageBitmap(photo);
		} else {
			if (resultCode != Activity.RESULT_CANCELED) {
				toast("获取照片失败");
			}
		}
	}
	void toast(String text){
		Toaster.instance(getActivity()).setup(text).show();
	}

	private Bitmap getBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
//			bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
//					.openInputStream(uri));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	void test(){
		int requestCode = 0;
		String contentType;
		{
		//选择图片 requestCode 返回的标识
		Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT"
		contentType = "image/*";
		innerIntent.setType(contentType); //查看类型 String IMAGE_UNSPECIFIED = "image/*";
		Intent wrapperIntent = Intent.createChooser(innerIntent, null);
		startActivityForResult(wrapperIntent, requestCode);
		}
		{
		//视频
		Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
		innerIntent.setType(contentType); //String VIDEO_UNSPECIFIED = "video/*";
		Intent wrapperIntent = Intent.createChooser(innerIntent, null);
		startActivityForResult(wrapperIntent, requestCode);
		}
		{
		//添加音频
		Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
		innerIntent.setType(contentType); //String VIDEO_UNSPECIFIED = "video/*";
		Intent wrapperIntent = Intent.createChooser(innerIntent, null);
		startActivityForResult(wrapperIntent, requestCode);
		}
		{
		//录音
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(contentType); //String AUDIO_AMR = "audio/amr";
		intent.setClassName("com.android.soundrecorder","com.android.soundrecorder.SoundRecorder");
		startActivityForResult(intent, requestCode);
		}
		{
		//拍摄视频
		int durationLimit = 60;
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 10*1024*1024);
		intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, durationLimit);
		startActivityForResult(intent, requestCode);
		}
		{
		//拍照 REQUEST_CODE_TAKE_PICTURE 为返回的标识
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //"android.media.action.IMAGE_CAPTURE";
		intent.putExtra(MediaStore.EXTRA_OUTPUT, ""); // output,Uri.parse("content://mms/scrapSpace");
		startActivityForResult(intent, requestCode);
		}
	}
	
}
