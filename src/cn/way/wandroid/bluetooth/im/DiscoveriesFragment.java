package cn.way.wandroid.bluetooth.im;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IllegalFormatCodePointException;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.way.wandroid.BaseFragment;
import cn.way.wandroid.R;
import cn.way.wandroid.bluetooth.BluetoothManager;
import cn.way.wandroid.bluetooth.BluetoothManager.BluetoothClientConnection;
import cn.way.wandroid.bluetooth.BluetoothManager.BluetoothConnectionListener;
import cn.way.wandroid.bluetooth.BluetoothManager.ConnectionState;
import cn.way.wandroid.bluetooth.BluetoothManager.DeviceState;
import cn.way.wandroid.bluetooth.BluetoothManager.DeviceStateListener;
import cn.way.wandroid.bluetooth.BluetoothManager.DiscoveryListener;
import cn.way.wandroid.toast.Toaster;
/**
 * 
 * @author Wayne
 * @2014年12月31日
 */
@SuppressLint("InflateParams")
public class DiscoveriesFragment extends BaseFragment {
	private Button searchBtn;
	private ListView lv ;
	private BluetoothManager bluetoothManager;
	private ArrayList<BluetoothDevice> nearbyDevices = new ArrayList<BluetoothDevice>();
	private ArrayAdapter<BluetoothDevice> adapter;
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	private void backAction(){
		bluetoothManager.cancelDiscovery();
		bluetoothManager = null;
		getActivity().getSupportFragmentManager().popBackStack();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bluetooth_im_page_discoveries, container,false);
		lv = (ListView) view.findViewById(R.id.im_discoveries_list);
		adapter = new ArrayAdapter<BluetoothDevice>(getActivity(), 0){
			@Override
			public int getCount() {
				return nearbyDevices.size();
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = convertView;
				if (view == null) {
					view = getActivity().getLayoutInflater().inflate(R.layout.bluetooth_im_list_friends_cell, null);
				}
				ViewHolder holder = (ViewHolder) view.getTag();
				if (holder==null) {
					holder = new ViewHolder();
					holder.nameTV = (TextView) view.findViewById(R.id.name);
					holder.stateTV = (TextView) view.findViewById(R.id.state);
					view.setTag(holder);
				}
				BluetoothDevice bd = nearbyDevices.get(position);
				holder.nameTV.setText(bd.getName()+"");
				holder.stateTV.setText(bd.getAddress());
				return view;
			}
			class ViewHolder{
				TextView nameTV;//可设置备注
				TextView stateTV;//对好友可见|对附近所有人可见
			}
		};
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BluetoothDevice bd = nearbyDevices.get(position);
				Toast.makeText(getActivity(), ""+bd, 0).show();
				if(getBluetoothManager()!=null){
					final BluetoothClientConnection bcc = 
					getBluetoothManager().createClientConnection();
					bcc.connect(MainActivity.M_UUID, bd, MainActivity.IS_SECURE,new BluetoothConnectionListener() {
						@Override
						public void onDataReceived(byte[] data) {
							Toast.makeText(getActivity(), new String(data), 0).show();
						}
						@Override
						public void onConnectionStateChanged(ConnectionState state, int errorCode) {
							Toast.makeText(getActivity(), state.toString(), 0).show();
							if (state == ConnectionState.CONNECTED) {
								bcc.write(new String("我是客户端。。。"));
							}
						}
					});
				}
			}
		});
		
		
		view.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				backAction();
			}
		});
		
		searchBtn = (Button) view.findViewById(R.id.searchBtn);
		searchBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doDescovery();
			}
		});
		return view;
	}
	private void doDescovery(){
		if (!getBluetoothManager().isEnabled()) {
			getBluetoothManager().setDeviceStateListener(new DeviceStateListener() {
				@Override
				public void onStateChanged(DeviceState state) {
					if (state == DeviceState.ON) {
						doDescovery();
					}
					Toaster.instance(getActivity()).setup("state : "+state).show();
					if (state == DeviceState.OFF) {
						searchBtn.setText("查找");
					}
				}
			});
			getBluetoothManager().enable();
			return;
		}
		if (getBluetoothManager()!=null) {
			searchBtn.setText("正在查找...");
			getBluetoothManager().startDiscovery(new DiscoveryListener() {
				@Override
				public void onFinished() {
					if (bluetoothManager!=null) {
						searchBtn.setText("查找");
					}
				}
				
				@Override
				public void onDevicesChanged(Collection<BluetoothDevice> devices) {
					if (bluetoothManager!=null) {
						Toaster.instance(getActivity()).setup("found : "+devices.toString()).show();
						nearbyDevices.clear();
						nearbyDevices.addAll(devices);
						adapter.notifyDataSetChanged();
					}
				}
			});
		}
	}
	public BluetoothManager getBluetoothManager() {
		return bluetoothManager;
	}
	public void setBluetoothManager(BluetoothManager bluetoothManager) {
		this.bluetoothManager = bluetoothManager;
	}
}
