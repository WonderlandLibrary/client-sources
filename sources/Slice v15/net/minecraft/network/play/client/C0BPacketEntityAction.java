package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumChatFormatting;


public class C0BPacketEntityAction
  implements Packet
{
  private int entityID;
  private Action action;
  private int auxData;
  
  public C0BPacketEntityAction() {}
  
  public C0BPacketEntityAction(Entity p_i46869_1_, Action p_i46869_2_)
  {
    this(p_i46869_1_, p_i46869_2_, 0);
  }
  
  public C0BPacketEntityAction(Entity p_i46870_1_, Action p_i46870_2_, int p_i46870_3_)
  {
    entityID = p_i46870_1_.getEntityId();
    action = p_i46870_2_;
    auxData = p_i46870_3_;
  }
  
  public void readPacketData(PacketBuffer buf)
    throws IOException
  {
    entityID = buf.readVarIntFromBuffer();
    action = ((Action)buf.readEnumValue(Action.class));
    auxData = buf.readVarIntFromBuffer();
  }
  
  public void writePacketData(PacketBuffer buf)
    throws IOException
  {
    buf.writeVarIntToBuffer(entityID);
    buf.writeEnumValue(action);
    buf.writeVarIntToBuffer(auxData);
  }
  
  public void processPacket(INetHandlerPlayServer handler)
  {
    handler.processEntityAction(this);
  }
  
  public Action getAction()
  {
    return action;
  }
  


  public int getAuxData() { return auxData; }
  
  public void processPacket(INetHandler handler) {}
  
  public static enum Action {
    START_SNEAKING,  STOP_SNEAKING,  STOP_SLEEPING,  START_SPRINTING,  STOP_SPRINTING,  START_RIDING_JUMP,  STOP_RIDING_JUMP,  OPEN_INVENTORY,  START_FALL_FLYING,  RIDING_JUMP;
  }
  






  public Enum<EnumChatFormatting> func_180764_b()
  {
    return null;
  }
  
  public int func_149512_e()
  {
    return 0;
  }
}
