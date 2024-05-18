package me.cpacket.ipv6impl;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import ooo.cpacket.ruby.Ruby;

public class IPv6Socket extends Socket {
	
	private Inet6Address address;
	public IPv6Socket(String ipv6Addr) {
		 try {
			this.address = (Inet6Address) InetAddress.getByName(ipv6Addr);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void bind(SocketAddress xddddd) {
		
	}
	
	@Override
	public void connect(SocketAddress ipv6, int timeout) {
		try {
			super.bind(new InetSocketAddress(this.address.getHostAddress(), 42069));
			super.connect(ipv6, timeout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
