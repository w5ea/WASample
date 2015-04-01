package cn.way.wandroid.bluetooth.im;

import java.util.UUID;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;
import cn.way.wandroid.bluetooth.BluetoothManager;
import cn.way.wandroid.bluetooth.BluetoothManager.BluetoothConnectionListener;
import cn.way.wandroid.bluetooth.BluetoothManager.BluetoothServerConnection;
import cn.way.wandroid.bluetooth.BluetoothManager.BluetoothSupportException;
import cn.way.wandroid.bluetooth.BluetoothManager.ConnectionState;
import cn.way.wandroid.bluetooth.BluetoothManager.DeviceState;
import cn.way.wandroid.bluetooth.BluetoothManager.DeviceStateListener;
import cn.way.wandroid.toast.Toaster;

@SuppressLint("NewApi")
public class MainActivity extends BaseFragmentActivity {
	private BluetoothManager bluetoothManager;
	public static final UUID M_UUID =
	        UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a67");
	public static boolean IS_SECURE = true;
	@Override
	protected void onDestroy() {
		if (bluetoothManager!=null) {
			bluetoothManager.release();
		}
		super.onDestroy();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth_page_main);
		try {
			bluetoothManager = BluetoothManager.instance(this);
			bluetoothManager.setDeviceStateListener(new DeviceStateListener() {
				@Override
				public void onStateChanged(DeviceState state) {
					switch (state) {
					case TURNING_ON:
						break;
					case ON:
						FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
						HomepageFragment hf = new HomepageFragment();
						hf.setBluetoothManager(bluetoothManager);
						ft.replace(R.id.bluetooth_page_main_root, hf);
						ft.commit();
					
						final BluetoothServerConnection bsc = bluetoothManager.getServerConnection();
						bsc.connect(M_UUID, IS_SECURE,new BluetoothConnectionListener() {
							@Override
							public void onConnectionStateChanged(ConnectionState state,
									int errorCode) {
								MainActivity.this.getActionBar().setTitle(state.toString()+"|"+bsc.getClientDevice().getName());
							}

							@Override
							public void onDataReceived(byte[] data) {
								Toast.makeText(MainActivity.this, "DataReceived:    "+new String(data), 0).show();
								bsc.write("我收到了，谢谢");
							}
						});
						break;
					case OFF:
						break;
					case TURNING_OFF:
						break;
					}
				}
			});
			bluetoothManager.enable();
		} catch (BluetoothSupportException e) {
			Toaster.instance(this).setup(e.toString()).show();
		}
	}
}
