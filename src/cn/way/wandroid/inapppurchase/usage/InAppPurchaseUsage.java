package cn.way.wandroid.inapppurchase.usage;

import android.os.Bundle;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.inapppurchase.InAppPurchaseAlipay;
import cn.way.wandroid.inapppurchase.InAppPurchaser;
import cn.way.wandroid.inapppurchase.InAppPurchaser.InAppPurchaseListener;
import cn.way.wandroid.inapppurchase.InAppPurchaser.Product;

public class InAppPurchaseUsage extends BaseFragmentActivity {
	private InAppPurchaser iap = new InAppPurchaseAlipay();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Product product = new Product();
		iap.setInAppPurchaseListener(new InAppPurchaseListener() {
			@Override
			public void onSuccess(InAppPurchaser purchaser) {
				
			}
			
			@Override
			public void onStarted(InAppPurchaser purchaser) {
				
			}
			
			@Override
			public void onRestored(InAppPurchaser purchaser) {
				
			}
			
			@Override
			public void onFailed(InAppPurchaser purchaser, Error error) {
				
			}
		});
		iap.purchaseProduct(product);
	}
}
