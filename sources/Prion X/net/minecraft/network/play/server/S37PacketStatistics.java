package net.minecraft.network.play.server;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.stats.StatBase;

public class S37PacketStatistics implements Packet
{
  private Map field_148976_a;
  private static final String __OBFID = "CL_00001283";
  
  public S37PacketStatistics() {}
  
  public S37PacketStatistics(Map p_i45173_1_)
  {
    field_148976_a = p_i45173_1_;
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleStatistics(this);
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    int var2 = data.readVarIntFromBuffer();
    field_148976_a = Maps.newHashMap();
    
    for (int var3 = 0; var3 < var2; var3++)
    {
      StatBase var4 = net.minecraft.stats.StatList.getOneShotStat(data.readStringFromBuffer(32767));
      int var5 = data.readVarIntFromBuffer();
      
      if (var4 != null)
      {
        field_148976_a.put(var4, Integer.valueOf(var5));
      }
    }
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(field_148976_a.size());
    Iterator var2 = field_148976_a.entrySet().iterator();
    
    while (var2.hasNext())
    {
      Map.Entry var3 = (Map.Entry)var2.next();
      data.writeString(getKeystatId);
      data.writeVarIntToBuffer(((Integer)var3.getValue()).intValue());
    }
  }
  
  public Map func_148974_c()
  {
    return field_148976_a;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
}
