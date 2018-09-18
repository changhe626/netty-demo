package com.onyx.my_encode_decode.common.fuben.response;

import com.onyx.my_encode_decode.common.Serializer;


public class FightResponse extends Serializer{

	/**
	 * 获取金币
	 */
	private int gold;
	
	

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	@Override
	protected void read() {
		this.gold = readInt();
	}

	@Override
	protected void write() {
		writeInt(gold);
	}
}
