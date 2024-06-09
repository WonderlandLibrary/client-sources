package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;

public class C08PacketPlayerBlockPlacement implements Packet
{
  private static final BlockPos field_179726_a = new BlockPos(-1, -1, -1);
  private BlockPos field_179725_b;
  private int placedBlockDirection;
  private ItemStack stack;
  private float facingX;
  private float facingY;
  private float facingZ;
  private static final String __OBFID = "CL_00001371";
  
  public C08PacketPlayerBlockPlacement() {}
  
  public C08PacketPlayerBlockPlacement(ItemStack p_i45930_1_)
  {
    this(field_179726_a, 255, p_i45930_1_, 0.0F, 0.0F, 0.0F);
  }
  
  public C08PacketPlayerBlockPlacement(BlockPos p_i45931_1_, int p_i45931_2_, ItemStack p_i45931_3_, float p_i45931_4_, float p_i45931_5_, float p_i45931_6_)
  {
    field_179725_b = p_i45931_1_;
    placedBlockDirection = p_i45931_2_;
    stack = (p_i45931_3_ != null ? p_i45931_3_.copy() : null);
    facingX = p_i45931_4_;
    facingY = p_i45931_5_;
    facingZ = p_i45931_6_;
  }
  


  public void readPacketData(PacketBuffer data)
    throws IOException
  {
    field_179725_b = data.readBlockPos();
    placedBlockDirection = data.readUnsignedByte();
    stack = data.readItemStackFromBuffer();
    facingX = (data.readUnsignedByte() / 16.0F);
    facingY = (data.readUnsignedByte() / 16.0F);
    facingZ = (data.readUnsignedByte() / 16.0F);
  }
  


  public void writePacketData(PacketBuffer data)
    throws IOException
  {
    data.writeBlockPos(field_179725_b);
    data.writeByte(placedBlockDirection);
    data.writeItemStackToBuffer(stack);
    data.writeByte((int)(facingX * 16.0F));
    data.writeByte((int)(facingY * 16.0F));
    data.writeByte((int)(facingZ * 16.0F));
  }
  
  public void func_180769_a(INetHandlerPlayServer p_180769_1_)
  {
    p_180769_1_.processPlayerBlockPlacement(this);
  }
  
  public BlockPos func_179724_a()
  {
    return field_179725_b;
  }
  
  public int getPlacedBlockDirection()
  {
    return placedBlockDirection;
  }
  
  public ItemStack getStack()
  {
    return stack;
  }
  



  public float getPlacedBlockOffsetX()
  {
    return facingX;
  }
  



  public float getPlacedBlockOffsetY()
  {
    return facingY;
  }
  



  public float getPlacedBlockOffsetZ()
  {
    return facingZ;
  }
  



  public void processPacket(INetHandler handler)
  {
    func_180769_a((INetHandlerPlayServer)handler);
  }
}
