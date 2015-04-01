/**
 * YaoJi-GuanWei
 * 2014年12月16日
 * 把BluetoothServerConnection 改为 BluetoothConnectionServer(增加属性来控制是否开启连接后关闭)
 * 架构改变
 * ServerConnection为真正的连接类，并增加表示是服务器连接还是客户端连接（只读取，初始化时传入）
 */
package cn.way.wandroid.bluetooth;