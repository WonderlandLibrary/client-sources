package ooo.cpacket.ruby.util.mc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketImpl;

import io.netty.channel.socket.oio.OioSocketChannel;

public class ProxyAdapter implements io.netty.bootstrap.ChannelFactory<io.netty.channel.socket.oio.OioSocketChannel> {

	private Proxy proxy;

	public ProxyAdapter(Proxy proxy) {
		this.proxy = proxy;
		XD2 = true;
	}

	boolean XD2;

	@Override
	public OioSocketChannel newChannel() {
		Socket socks = new Socket(this.proxy);
		Class clazzSocks  = socks.getClass();
		Method setSockVersion  = null;
		Field sockImplField = null; 
		SocketImpl socksimpl = null; 
		 try {
		    sockImplField = clazzSocks.getDeclaredField("impl");
		    sockImplField.setAccessible(true);
		    socksimpl  = (SocketImpl) sockImplField.get(socks);
		    Class clazzSocksImpl  =  socksimpl.getClass();
		    setSockVersion  = clazzSocksImpl.getDeclaredMethod("setV4");
		    setSockVersion.setAccessible(true);
		    if(null != setSockVersion){
		        setSockVersion.invoke(socksimpl);
		    }
		    sockImplField.set(socks, socksimpl);
		    } 
		        catch (Exception e) {
		      // TODO Auto-generated catch block
		            e.printStackTrace();
		    } 

		return new OioSocketChannel(socks);
	}

}
