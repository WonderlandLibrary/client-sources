/*   1:    */ package net.minecraft.entity;
/*   2:    */ 
/*   3:    */ import io.netty.buffer.ByteBuf;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.command.server.CommandBlockLogic;
/*   6:    */ import net.minecraft.entity.item.EntityMinecart;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.nbt.NBTTagCompound;
/*  10:    */ import net.minecraft.util.ChunkCoordinates;
/*  11:    */ import net.minecraft.util.IChatComponent.Serializer;
/*  12:    */ import net.minecraft.util.MathHelper;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class EntityMinecartCommandBlock
/*  16:    */   extends EntityMinecart
/*  17:    */ {
/*  18: 17 */   private final CommandBlockLogic field_145824_a = new CommandBlockLogic()
/*  19:    */   {
/*  20:    */     private static final String __OBFID = "CL_00001673";
/*  21:    */     
/*  22:    */     public void func_145756_e()
/*  23:    */     {
/*  24: 22 */       EntityMinecartCommandBlock.this.getDataWatcher().updateObject(23, func_145753_i());
/*  25: 23 */       EntityMinecartCommandBlock.this.getDataWatcher().updateObject(24, IChatComponent.Serializer.func_150696_a(func_145749_h()));
/*  26:    */     }
/*  27:    */     
/*  28:    */     public int func_145751_f()
/*  29:    */     {
/*  30: 27 */       return 1;
/*  31:    */     }
/*  32:    */     
/*  33:    */     public void func_145757_a(ByteBuf p_145757_1_)
/*  34:    */     {
/*  35: 31 */       p_145757_1_.writeInt(EntityMinecartCommandBlock.this.getEntityId());
/*  36:    */     }
/*  37:    */     
/*  38:    */     public ChunkCoordinates getPlayerCoordinates()
/*  39:    */     {
/*  40: 35 */       return new ChunkCoordinates(MathHelper.floor_double(EntityMinecartCommandBlock.this.posX), MathHelper.floor_double(EntityMinecartCommandBlock.this.posY + 0.5D), MathHelper.floor_double(EntityMinecartCommandBlock.this.posZ));
/*  41:    */     }
/*  42:    */     
/*  43:    */     public World getEntityWorld()
/*  44:    */     {
/*  45: 39 */       return EntityMinecartCommandBlock.this.worldObj;
/*  46:    */     }
/*  47:    */   };
/*  48: 42 */   private int field_145823_b = 0;
/*  49:    */   private static final String __OBFID = "CL_00001672";
/*  50:    */   
/*  51:    */   public EntityMinecartCommandBlock(World p_i45321_1_)
/*  52:    */   {
/*  53: 47 */     super(p_i45321_1_);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public EntityMinecartCommandBlock(World p_i45322_1_, double p_i45322_2_, double p_i45322_4_, double p_i45322_6_)
/*  57:    */   {
/*  58: 52 */     super(p_i45322_1_, p_i45322_2_, p_i45322_4_, p_i45322_6_);
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected void entityInit()
/*  62:    */   {
/*  63: 57 */     super.entityInit();
/*  64: 58 */     getDataWatcher().addObject(23, "");
/*  65: 59 */     getDataWatcher().addObject(24, "");
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  69:    */   {
/*  70: 67 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  71: 68 */     this.field_145824_a.func_145759_b(par1NBTTagCompound);
/*  72: 69 */     getDataWatcher().updateObject(23, func_145822_e().func_145753_i());
/*  73: 70 */     getDataWatcher().updateObject(24, IChatComponent.Serializer.func_150696_a(func_145822_e().func_145749_h()));
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  77:    */   {
/*  78: 78 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  79: 79 */     this.field_145824_a.func_145758_a(par1NBTTagCompound);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getMinecartType()
/*  83:    */   {
/*  84: 84 */     return 6;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Block func_145817_o()
/*  88:    */   {
/*  89: 89 */     return Blocks.command_block;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public CommandBlockLogic func_145822_e()
/*  93:    */   {
/*  94: 94 */     return this.field_145824_a;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void onActivatorRailPass(int par1, int par2, int par3, boolean par4)
/*  98:    */   {
/*  99:102 */     if ((par4) && (this.ticksExisted - this.field_145823_b >= 4))
/* 100:    */     {
/* 101:104 */       func_145822_e().func_145755_a(this.worldObj);
/* 102:105 */       this.field_145823_b = this.ticksExisted;
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean interactFirst(EntityPlayer par1EntityPlayer)
/* 107:    */   {
/* 108:114 */     if (this.worldObj.isClient) {
/* 109:116 */       par1EntityPlayer.func_146095_a(func_145822_e());
/* 110:    */     }
/* 111:119 */     return super.interactFirst(par1EntityPlayer);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void func_145781_i(int p_145781_1_)
/* 115:    */   {
/* 116:124 */     super.func_145781_i(p_145781_1_);
/* 117:126 */     if (p_145781_1_ == 24) {
/* 118:    */       try
/* 119:    */       {
/* 120:130 */         this.field_145824_a.func_145750_b(IChatComponent.Serializer.func_150699_a(getDataWatcher().getWatchableObjectString(24)));
/* 121:    */       }
/* 122:    */       catch (Throwable localThrowable) {}
/* 123:137 */     } else if (p_145781_1_ == 23) {
/* 124:139 */       this.field_145824_a.func_145752_a(getDataWatcher().getWatchableObjectString(23));
/* 125:    */     }
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.EntityMinecartCommandBlock
 * JD-Core Version:    0.7.0.1
 */