/*  1:   */ package net.minecraft.tileentity;
/*  2:   */ 
/*  3:   */ import io.netty.buffer.ByteBuf;
/*  4:   */ import net.minecraft.command.server.CommandBlockLogic;
/*  5:   */ import net.minecraft.nbt.NBTTagCompound;
/*  6:   */ import net.minecraft.network.Packet;
/*  7:   */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  8:   */ import net.minecraft.util.ChunkCoordinates;
/*  9:   */ import net.minecraft.world.World;
/* 10:   */ 
/* 11:   */ public class TileEntityCommandBlock
/* 12:   */   extends TileEntity
/* 13:   */ {
/* 14:13 */   private final CommandBlockLogic field_145994_a = new CommandBlockLogic()
/* 15:   */   {
/* 16:   */     private static final String __OBFID = "CL_00000348";
/* 17:   */     
/* 18:   */     public ChunkCoordinates getPlayerCoordinates()
/* 19:   */     {
/* 20:18 */       return new ChunkCoordinates(TileEntityCommandBlock.this.field_145851_c, TileEntityCommandBlock.this.field_145848_d, TileEntityCommandBlock.this.field_145849_e);
/* 21:   */     }
/* 22:   */     
/* 23:   */     public World getEntityWorld()
/* 24:   */     {
/* 25:22 */       return TileEntityCommandBlock.this.getWorldObj();
/* 26:   */     }
/* 27:   */     
/* 28:   */     public void func_145752_a(String p_145752_1_)
/* 29:   */     {
/* 30:26 */       super.func_145752_a(p_145752_1_);
/* 31:27 */       TileEntityCommandBlock.this.onInventoryChanged();
/* 32:   */     }
/* 33:   */     
/* 34:   */     public void func_145756_e()
/* 35:   */     {
/* 36:31 */       TileEntityCommandBlock.this.getWorldObj().func_147471_g(TileEntityCommandBlock.this.field_145851_c, TileEntityCommandBlock.this.field_145848_d, TileEntityCommandBlock.this.field_145849_e);
/* 37:   */     }
/* 38:   */     
/* 39:   */     public int func_145751_f()
/* 40:   */     {
/* 41:35 */       return 0;
/* 42:   */     }
/* 43:   */     
/* 44:   */     public void func_145757_a(ByteBuf p_145757_1_)
/* 45:   */     {
/* 46:39 */       p_145757_1_.writeInt(TileEntityCommandBlock.this.field_145851_c);
/* 47:40 */       p_145757_1_.writeInt(TileEntityCommandBlock.this.field_145848_d);
/* 48:41 */       p_145757_1_.writeInt(TileEntityCommandBlock.this.field_145849_e);
/* 49:   */     }
/* 50:   */   };
/* 51:   */   private static final String __OBFID = "CL_00000347";
/* 52:   */   
/* 53:   */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 54:   */   {
/* 55:48 */     super.writeToNBT(p_145841_1_);
/* 56:49 */     this.field_145994_a.func_145758_a(p_145841_1_);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 60:   */   {
/* 61:54 */     super.readFromNBT(p_145839_1_);
/* 62:55 */     this.field_145994_a.func_145759_b(p_145839_1_);
/* 63:   */   }
/* 64:   */   
/* 65:   */   public Packet getDescriptionPacket()
/* 66:   */   {
/* 67:63 */     NBTTagCompound var1 = new NBTTagCompound();
/* 68:64 */     writeToNBT(var1);
/* 69:65 */     return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 2, var1);
/* 70:   */   }
/* 71:   */   
/* 72:   */   public CommandBlockLogic func_145993_a()
/* 73:   */   {
/* 74:70 */     return this.field_145994_a;
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityCommandBlock
 * JD-Core Version:    0.7.0.1
 */