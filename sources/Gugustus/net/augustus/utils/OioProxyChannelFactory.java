package net.augustus.utils;

import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.socket.oio.OioSocketChannel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Proxy;
import java.net.Socket;
import net.augustus.ui.augustusmanager.AugustusProxy;

public class OioProxyChannelFactory implements ChannelFactory<OioSocketChannel> {
   private Proxy proxy;

   public OioProxyChannelFactory(Proxy proxy) {
      this.proxy = proxy;
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   public void setProxy(Proxy proxy) {
      this.proxy = proxy;
   }

   public OioSocketChannel newChannel() {
      if (this.proxy != null && this.proxy != Proxy.NO_PROXY) {
         Socket sock = new Socket(this.proxy);

         try {
            if (AugustusProxy.type.equals("Socks4")) {
               Method m = sock.getClass().getDeclaredMethod("getImpl");
               m.setAccessible(true);
               Object sd = m.invoke(sock);
               m = sd.getClass().getDeclaredMethod("setV4");
               m.setAccessible(true);
               m.invoke(sd);
            }

            return new OioSocketChannel(sock);
         } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var4) {
            throw new RuntimeException("Failed to create socks 4 proxy!", var4);
         }
      } else {
         return new OioSocketChannel(new Socket(Proxy.NO_PROXY));
      }
   }
}
