package net.minecraft.client.network;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerDetector
{
  private static final AtomicInteger field_148551_a = new AtomicInteger(0);
  private static final Logger logger = LogManager.getLogger();
  private static final String __OBFID = "CL_00001133";
  
  public LanServerDetector() {}
  
  public static class LanServer {
    private String lanServerMotd;
    private String lanServerIpPort;
    private long timeLastSeen;
    private static final String __OBFID = "CL_00001134";
    
    public LanServer(String p_i1319_1_, String p_i1319_2_) {
      lanServerMotd = p_i1319_1_;
      lanServerIpPort = p_i1319_2_;
      timeLastSeen = Minecraft.getSystemTime();
    }
    
    public String getServerMotd()
    {
      return lanServerMotd;
    }
    
    public String getServerIpPort()
    {
      return lanServerIpPort;
    }
    
    public void updateLastSeen()
    {
      timeLastSeen = Minecraft.getSystemTime();
    }
  }
  
  public static class LanServerList
  {
    private List listOfLanServers = Lists.newArrayList();
    boolean wasUpdated;
    private static final String __OBFID = "CL_00001136";
    
    public LanServerList() {}
    
    public synchronized boolean getWasUpdated() { return wasUpdated; }
    

    public synchronized void setWasNotUpdated()
    {
      wasUpdated = false;
    }
    
    public synchronized List getLanServers()
    {
      return Collections.unmodifiableList(listOfLanServers);
    }
    
    public synchronized void func_77551_a(String p_77551_1_, InetAddress p_77551_2_)
    {
      String var3 = ThreadLanServerPing.getMotdFromPingResponse(p_77551_1_);
      String var4 = ThreadLanServerPing.getAdFromPingResponse(p_77551_1_);
      
      if (var4 != null)
      {
        var4 = p_77551_2_.getHostAddress() + ":" + var4;
        boolean var5 = false;
        Iterator var6 = listOfLanServers.iterator();
        
        while (var6.hasNext())
        {
          LanServerDetector.LanServer var7 = (LanServerDetector.LanServer)var6.next();
          
          if (var7.getServerIpPort().equals(var4))
          {
            var7.updateLastSeen();
            var5 = true;
            break;
          }
        }
        
        if (!var5)
        {
          listOfLanServers.add(new LanServerDetector.LanServer(var3, var4));
          wasUpdated = true;
        }
      }
    }
  }
  
  public static class ThreadLanServerFind extends Thread
  {
    private final LanServerDetector.LanServerList localServerList;
    private final InetAddress broadcastAddress;
    private final MulticastSocket socket;
    private static final String __OBFID = "CL_00001135";
    
    public ThreadLanServerFind(LanServerDetector.LanServerList p_i1320_1_) throws IOException
    {
      super();
      localServerList = p_i1320_1_;
      setDaemon(true);
      socket = new MulticastSocket(4445);
      broadcastAddress = InetAddress.getByName("224.0.2.60");
      socket.setSoTimeout(5000);
      socket.joinGroup(broadcastAddress);
    }
    
    public void run()
    {
      byte[] var2 = new byte['Ѐ'];
      
      while (!isInterrupted())
      {
        DatagramPacket var1 = new DatagramPacket(var2, var2.length);
        
        try
        {
          socket.receive(var1);
        }
        catch (SocketTimeoutException var5)
        {
          continue;
        }
        catch (IOException var6)
        {
          LanServerDetector.logger.error("Couldn't ping server", var6);
          break;
        }
        
        String var3 = new String(var1.getData(), var1.getOffset(), var1.getLength());
        LanServerDetector.logger.debug(var1.getAddress() + ": " + var3);
        localServerList.func_77551_a(var3, var1.getAddress());
      }
      
      try
      {
        socket.leaveGroup(broadcastAddress);
      }
      catch (IOException localIOException1) {}
      



      socket.close();
    }
  }
}
