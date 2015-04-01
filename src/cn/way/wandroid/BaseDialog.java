package cn.way.wandroid;

import android.app.Dialog;
import android.content.Context;

/**
 * @author Wayne
 *
 */
public class BaseDialog extends Dialog {
	public BaseDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	public BaseDialog(Context context, int theme) {
		super(context, theme);
	}
	public BaseDialog(Context context) {
		super(context);
	}
}
