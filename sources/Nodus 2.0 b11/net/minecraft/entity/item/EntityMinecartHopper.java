/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.command.IEntitySelector;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.nbt.NBTTagCompound;
/*  10:    */ import net.minecraft.tileentity.IHopper;
/*  11:    */ import net.minecraft.tileentity.TileEntityHopper;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.util.DamageSource;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class EntityMinecartHopper
/*  17:    */   extends EntityMinecartContainer
/*  18:    */   implements IHopper
/*  19:    */ {
/*  20: 18 */   private boolean isBlocked = true;
/*  21: 19 */   private int transferTicker = -1;
/*  22:    */   private static final String __OBFID = "CL_00001676";
/*  23:    */   
/*  24:    */   public EntityMinecartHopper(World par1World)
/*  25:    */   {
/*  26: 24 */     super(par1World);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public EntityMinecartHopper(World par1World, double par2, double par4, double par6)
/*  30:    */   {
/*  31: 29 */     super(par1World, par2, par4, par6);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int getMinecartType()
/*  35:    */   {
/*  36: 34 */     return 5;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Block func_145817_o()
/*  40:    */   {
/*  41: 39 */     return Blocks.hopper;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int getDefaultDisplayTileOffset()
/*  45:    */   {
/*  46: 44 */     return 1;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getSizeInventory()
/*  50:    */   {
/*  51: 52 */     return 5;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean interactFirst(EntityPlayer par1EntityPlayer)
/*  55:    */   {
/*  56: 60 */     if (!this.worldObj.isClient) {
/*  57: 62 */       par1EntityPlayer.displayGUIHopperMinecart(this);
/*  58:    */     }
/*  59: 65 */     return true;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void onActivatorRailPass(int par1, int par2, int par3, boolean par4)
/*  63:    */   {
/*  64: 73 */     boolean var5 = !par4;
/*  65: 75 */     if (var5 != getBlocked()) {
/*  66: 77 */       setBlocked(var5);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean getBlocked()
/*  71:    */   {
/*  72: 86 */     return this.isBlocked;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setBlocked(boolean par1)
/*  76:    */   {
/*  77: 94 */     this.isBlocked = par1;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public World getWorldObj()
/*  81:    */   {
/*  82:102 */     return this.worldObj;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public double getXPos()
/*  86:    */   {
/*  87:110 */     return this.posX;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public double getYPos()
/*  91:    */   {
/*  92:118 */     return this.posY;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public double getZPos()
/*  96:    */   {
/*  97:126 */     return this.posZ;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void onUpdate()
/* 101:    */   {
/* 102:134 */     super.onUpdate();
/* 103:136 */     if ((!this.worldObj.isClient) && (isEntityAlive()) && (getBlocked()))
/* 104:    */     {
/* 105:138 */       this.transferTicker -= 1;
/* 106:140 */       if (!canTransfer())
/* 107:    */       {
/* 108:142 */         setTransferTicker(0);
/* 109:144 */         if (func_96112_aD())
/* 110:    */         {
/* 111:146 */           setTransferTicker(4);
/* 112:147 */           onInventoryChanged();
/* 113:    */         }
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean func_96112_aD()
/* 119:    */   {
/* 120:155 */     if (TileEntityHopper.func_145891_a(this)) {
/* 121:157 */       return true;
/* 122:    */     }
/* 123:161 */     List var1 = this.worldObj.selectEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.25D, 0.0D, 0.25D), IEntitySelector.selectAnything);
/* 124:163 */     if (var1.size() > 0) {
/* 125:165 */       TileEntityHopper.func_145898_a(this, (EntityItem)var1.get(0));
/* 126:    */     }
/* 127:168 */     return false;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void killMinecart(DamageSource par1DamageSource)
/* 131:    */   {
/* 132:174 */     super.killMinecart(par1DamageSource);
/* 133:175 */     func_145778_a(Item.getItemFromBlock(Blocks.hopper), 1, 0.0F);
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 137:    */   {
/* 138:183 */     super.writeEntityToNBT(par1NBTTagCompound);
/* 139:184 */     par1NBTTagCompound.setInteger("TransferCooldown", this.transferTicker);
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 143:    */   {
/* 144:192 */     super.readEntityFromNBT(par1NBTTagCompound);
/* 145:193 */     this.transferTicker = par1NBTTagCompound.getInteger("TransferCooldown");
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setTransferTicker(int par1)
/* 149:    */   {
/* 150:201 */     this.transferTicker = par1;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean canTransfer()
/* 154:    */   {
/* 155:209 */     return this.transferTicker > 0;
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityMinecartHopper
 * JD-Core Version:    0.7.0.1
 */