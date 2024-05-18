package me.cpacket.ipv6impl;

import java.util.Iterator;

import com.googlecode.ipv6.IPv6Address;
import com.googlecode.ipv6.IPv6Network;
import com.googlecode.ipv6.IPv6NetworkMask;

import ooo.cpacket.ruby.Ruby;

public class IPv6Pool {
	public static IPv6Network network;
	public static IPv6Network rofl;
	static {
		IPv6Pool.network = IPv6Network.fromString("2001:470:b091:" + Ruby.getRuby.get56() + "::" + Ruby.getRuby.get64() + "/64");
	}
	
	public static IPv6Address getAddressFromPool() {
		if (rofl == null)
			rofl = IPv6Pool.network.split(new IPv6NetworkMask(64)).next();
		
		return rofl.iterator().next();
	}
	
}
