package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S14PacketEntity implements Packet
{
  protected int field_149074_a;
  protected byte field_149072_b;
  protected byte field_149073_c;
  protected byte field_149070_d;
  protected byte field_149071_e;
  protected byte field_149068_f;
  protected boolean field_179743_g;
  protected boolean field_149069_g;
  private static final String __OBFID = "CL_00001312";
  
  public S14PacketEntity() {}
  
  public S14PacketEntity(int p_i45206_1_)
  {
    field_149074_a = p_i45206_1_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_149074_a = data.readVarIntFromBuffer();
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeVarIntToBuffer(field_149074_a);
  }
  



  public void processPacket(INetHandlerPlayClient handler)
  {
    handler.handleEntityMovement(this);
  }
  
  public String toString()
  {
    return "Entity_" + super.toString();
  }
  
  public Entity func_149065_a(World worldIn)
  {
    return worldIn.getEntityByID(field_149074_a);
  }
  
  public byte func_149062_c()
  {
    return field_149072_b;
  }
  
  public byte func_149061_d()
  {
    return field_149073_c;
  }
  
  public byte func_149064_e()
  {
    return field_149070_d;
  }
  
  public byte func_149066_f()
  {
    return field_149071_e;
  }
  
  public byte func_149063_g()
  {
    return field_149068_f;
  }
  
  public boolean func_149060_h()
  {
    return field_149069_g;
  }
  
  public boolean func_179742_g()
  {
    return field_179743_g;
  }
  



  public void processPacket(INetHandler handler)
  {
    processPacket((INetHandlerPlayClient)handler);
  }
  
  public static class S15PacketEntityRelMove extends S14PacketEntity
  {
    private static final String __OBFID = "CL_00001313";
    
    public S15PacketEntityRelMove() {}
    
    public S15PacketEntityRelMove(int p_i45974_1_, byte p_i45974_2_, byte p_i45974_3_, byte p_i45974_4_, boolean p_i45974_5_)
    {
      super();
      field_149072_b = p_i45974_2_;
      field_149073_c = p_i45974_3_;
      field_149070_d = p_i45974_4_;
      field_179743_g = p_i45974_5_;
    }
    
    public void readPacketData(PacketBuffer data) throws IOException
    {
      super.readPacketData(data);
      field_149072_b = data.readByte();
      field_149073_c = data.readByte();
      field_149070_d = data.readByte();
      field_179743_g = data.readBoolean();
    }
    
    public void writePacketData(PacketBuffer data) throws IOException
    {
      super.writePacketData(data);
      data.writeByte(field_149072_b);
      data.writeByte(field_149073_c);
      data.writeByte(field_149070_d);
      data.writeBoolean(field_179743_g);
    }
    
    public void processPacket(INetHandler handler)
    {
      super.processPacket((INetHandlerPlayClient)handler);
    }
  }
  
  public static class S16PacketEntityLook extends S14PacketEntity
  {
    private static final String __OBFID = "CL_00001315";
    
    public S16PacketEntityLook()
    {
      field_149069_g = true;
    }
    
    public S16PacketEntityLook(int p_i45972_1_, byte p_i45972_2_, byte p_i45972_3_, boolean p_i45972_4_)
    {
      super();
      field_149071_e = p_i45972_2_;
      field_149068_f = p_i45972_3_;
      field_149069_g = true;
      field_179743_g = p_i45972_4_;
    }
    
    public void readPacketData(PacketBuffer data) throws IOException
    {
      super.readPacketData(data);
      field_149071_e = data.readByte();
      field_149068_f = data.readByte();
      field_179743_g = data.readBoolean();
    }
    
    public void writePacketData(PacketBuffer data) throws IOException
    {
      super.writePacketData(data);
      data.writeByte(field_149071_e);
      data.writeByte(field_149068_f);
      data.writeBoolean(field_179743_g);
    }
    
    public void processPacket(INetHandler handler)
    {
      super.processPacket((INetHandlerPlayClient)handler);
    }
  }
  
  public static class S17PacketEntityLookMove extends S14PacketEntity
  {
    private static final String __OBFID = "CL_00001314";
    
    public S17PacketEntityLookMove()
    {
      field_149069_g = true;
    }
    
    public S17PacketEntityLookMove(int p_i45973_1_, byte p_i45973_2_, byte p_i45973_3_, byte p_i45973_4_, byte p_i45973_5_, byte p_i45973_6_, boolean p_i45973_7_)
    {
      super();
      field_149072_b = p_i45973_2_;
      field_149073_c = p_i45973_3_;
      field_149070_d = p_i45973_4_;
      field_149071_e = p_i45973_5_;
      field_149068_f = p_i45973_6_;
      field_179743_g = p_i45973_7_;
      field_149069_g = true;
    }
    
    public void readPacketData(PacketBuffer data) throws IOException
    {
      super.readPacketData(data);
      field_149072_b = data.readByte();
      field_149073_c = data.readByte();
      field_149070_d = data.readByte();
      field_149071_e = data.readByte();
      field_149068_f = data.readByte();
      field_179743_g = data.readBoolean();
    }
    
    public void writePacketData(PacketBuffer data) throws IOException
    {
      super.writePacketData(data);
      data.writeByte(field_149072_b);
      data.writeByte(field_149073_c);
      data.writeByte(field_149070_d);
      data.writeByte(field_149071_e);
      data.writeByte(field_149068_f);
      data.writeBoolean(field_179743_g);
    }
    
    public void processPacket(INetHandler handler)
    {
      super.processPacket((INetHandlerPlayClient)handler);
    }
  }
}
