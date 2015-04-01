package cn.way.wandroid.bluetooth.im;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.way.wandroid.BaseFragment;
import cn.way.wandroid.R;
import cn.way.wandroid.bluetooth.BluetoothManager;
import cn.way.wandroid.bluetooth.BluetoothManager.BluetoothClientConnection;
import cn.way.wandroid.bluetooth.BluetoothManager.BluetoothConnectionListener;
import cn.way.wandroid.bluetooth.BluetoothManager.ConnectionState;

@SuppressLint({ "InflateParams", "NewApi" })
public class FriendsFragment extends BaseFragment {
	private ViewGroup view;//主视图
	private ListView lv;//列表
	private ArrayAdapter<BluetoothDevice> adapter;
	private ArrayList<BluetoothDevice> friends = new ArrayList<BluetoothDevice>();
	private BluetoothManager bluetoothManager;
	
	private HashMap<String,BluetoothClientConnection> conns = new HashMap<String, BluetoothManager.BluetoothClientConnection>() ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.bluetooth_im_page_friends, null);
		lv = (ListView) view.findViewById(R.id.im_friends_list);
		view.findViewById(R.id.addFriendBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				DiscoveriesFragment df = new DiscoveriesFragment();
				df.setBluetoothManager(bluetoothManager);
				ft.replace(R.id.bluetooth_page_main_root,df);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		adapter = new ArrayAdapter<BluetoothDevice>(getActivity(), 0){
			@Override
			public int getCount() {
				return friends.size();
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
				BluetoothDevice bd = friends.get(position);
				holder.nameTV.setText(bd.getName());
				holder.stateTV.setText(BluetoothManager.getBondState(bd));
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
				final BluetoothDevice bd = friends.get(position);
				Toast.makeText(getActivity(), ""+bd, 0).show();
				if(getManager()!=null){
					if (conns.size()==0||conns.get(bd.getAddress())==null) {
						final BluetoothClientConnection bcc = 
						getManager().createClientConnection();
						bcc.connect(MainActivity.M_UUID, bd, MainActivity.IS_SECURE,new BluetoothConnectionListener() {
							
							@Override
							public void onConnectionStateChanged(ConnectionState state,
									int errorCode) {
								if (state == ConnectionState.CONNECTED) {
									bcc.write("你好吗");
								}
								getActivity().getActionBar().setTitle(state.toString()+"|"+bd.getName());
							}
							
							@Override
							public void onDataReceived(byte[] data) {
								Toast.makeText(getActivity(), "DataReceived:    "+new String(data), 0).show();
							}
							
						});
						
						conns.put(bd.getAddress(), bcc);
					}else{
						BluetoothClientConnection bcc = conns.get(bd.getAddress());
						if (bcc.getState()==ConnectionState.CONNECTED) {
							bcc.write("你好吗");
						}else if (bcc.getState()==ConnectionState.DISCONNECTED){
							conns.remove(bd.getAddress());
						}
					}
				}
			}
		});
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view!=null&&view.getParent()!=null) {
			((ViewGroup)view.getParent()).removeView(view);
		}
		return view;
	}
	@Override
	public void onResume() {
		super.onResume();
		updateView();	
	}
	public void updateView(){
		friends.clear();
		if (getManager()!=null) {
			friends.addAll(getManager().getBondedDevices());
		}
		adapter.notifyDataSetChanged();
	}
	public BluetoothManager getManager() {
		return bluetoothManager;
	}
	public void setManager(BluetoothManager manager) {
		this.bluetoothManager = manager;
	}
}
