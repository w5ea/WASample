package cn.way.wandroid.java;

public class EnumUsage {
	public enum Color{
		RED(){
			@Override
			public int value() {
				return 0XFF0000;
			}
		}
		,
		WHITE(){
			@Override
			public int value() {
				return 0XFFFFFF;
			}
		}
		;
		public abstract int value();
	}
}
