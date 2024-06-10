/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Random;
/*   8:    */ import net.minecraft.block.Block;
/*   9:    */ import net.minecraft.block.BlockLiquid;
/*  10:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  11:    */ import net.minecraft.creativetab.CreativeTabs;
/*  12:    */ import net.minecraft.entity.Entity;
/*  13:    */ import net.minecraft.entity.EntityList;
/*  14:    */ import net.minecraft.entity.EntityList.EntityEggInfo;
/*  15:    */ import net.minecraft.entity.EntityLiving;
/*  16:    */ import net.minecraft.entity.EntityLivingBase;
/*  17:    */ import net.minecraft.entity.player.EntityPlayer;
/*  18:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  19:    */ import net.minecraft.util.IIcon;
/*  20:    */ import net.minecraft.util.MathHelper;
/*  21:    */ import net.minecraft.util.MovingObjectPosition;
/*  22:    */ import net.minecraft.util.MovingObjectPosition.MovingObjectType;
/*  23:    */ import net.minecraft.util.StatCollector;
/*  24:    */ import net.minecraft.world.World;
/*  25:    */ 
/*  26:    */ public class ItemMonsterPlacer
/*  27:    */   extends Item
/*  28:    */ {
/*  29:    */   private IIcon theIcon;
/*  30:    */   private static final String __OBFID = "CL_00000070";
/*  31:    */   
/*  32:    */   public ItemMonsterPlacer()
/*  33:    */   {
/*  34: 29 */     setHasSubtypes(true);
/*  35: 30 */     setCreativeTab(CreativeTabs.tabMisc);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getItemStackDisplayName(ItemStack par1ItemStack)
/*  39:    */   {
/*  40: 35 */     String var2 = StatCollector.translateToLocal(new StringBuilder(String.valueOf(getUnlocalizedName())).append(".name").toString()).trim();
/*  41: 36 */     String var3 = EntityList.getStringFromID(par1ItemStack.getItemDamage());
/*  42: 38 */     if (var3 != null) {
/*  43: 40 */       var2 = var2 + " " + StatCollector.translateToLocal(new StringBuilder("entity.").append(var3).append(".name").toString());
/*  44:    */     }
/*  45: 43 */     return var2;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
/*  49:    */   {
/*  50: 48 */     EntityList.EntityEggInfo var3 = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(par1ItemStack.getItemDamage()));
/*  51: 49 */     return var3 != null ? var3.secondaryColor : par2 == 0 ? var3.primaryColor : 16777215;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean requiresMultipleRenderPasses()
/*  55:    */   {
/*  56: 54 */     return true;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public IIcon getIconFromDamageForRenderPass(int par1, int par2)
/*  60:    */   {
/*  61: 62 */     return par2 > 0 ? this.theIcon : super.getIconFromDamageForRenderPass(par1, par2);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/*  65:    */   {
/*  66: 71 */     if (par3World.isClient) {
/*  67: 73 */       return true;
/*  68:    */     }
/*  69: 77 */     Block var11 = par3World.getBlock(par4, par5, par6);
/*  70: 78 */     par4 += net.minecraft.util.Facing.offsetsXForSide[par7];
/*  71: 79 */     par5 += net.minecraft.util.Facing.offsetsYForSide[par7];
/*  72: 80 */     par6 += net.minecraft.util.Facing.offsetsZForSide[par7];
/*  73: 81 */     double var12 = 0.0D;
/*  74: 83 */     if ((par7 == 1) && (var11.getRenderType() == 11)) {
/*  75: 85 */       var12 = 0.5D;
/*  76:    */     }
/*  77: 88 */     Entity var14 = spawnCreature(par3World, par1ItemStack.getItemDamage(), par4 + 0.5D, par5 + var12, par6 + 0.5D);
/*  78: 90 */     if (var14 != null)
/*  79:    */     {
/*  80: 92 */       if (((var14 instanceof EntityLivingBase)) && (par1ItemStack.hasDisplayName())) {
/*  81: 94 */         ((EntityLiving)var14).setCustomNameTag(par1ItemStack.getDisplayName());
/*  82:    */       }
/*  83: 97 */       if (!par2EntityPlayer.capabilities.isCreativeMode) {
/*  84: 99 */         par1ItemStack.stackSize -= 1;
/*  85:    */       }
/*  86:    */     }
/*  87:103 */     return true;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  91:    */   {
/*  92:112 */     if (par2World.isClient) {
/*  93:114 */       return par1ItemStack;
/*  94:    */     }
/*  95:118 */     MovingObjectPosition var4 = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
/*  96:120 */     if (var4 == null) {
/*  97:122 */       return par1ItemStack;
/*  98:    */     }
/*  99:126 */     if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
/* 100:    */     {
/* 101:128 */       int var5 = var4.blockX;
/* 102:129 */       int var6 = var4.blockY;
/* 103:130 */       int var7 = var4.blockZ;
/* 104:132 */       if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7)) {
/* 105:134 */         return par1ItemStack;
/* 106:    */       }
/* 107:137 */       if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var4.sideHit, par1ItemStack)) {
/* 108:139 */         return par1ItemStack;
/* 109:    */       }
/* 110:142 */       if ((par2World.getBlock(var5, var6, var7) instanceof BlockLiquid))
/* 111:    */       {
/* 112:144 */         Entity var8 = spawnCreature(par2World, par1ItemStack.getItemDamage(), var5, var6, var7);
/* 113:146 */         if (var8 != null)
/* 114:    */         {
/* 115:148 */           if (((var8 instanceof EntityLivingBase)) && (par1ItemStack.hasDisplayName())) {
/* 116:150 */             ((EntityLiving)var8).setCustomNameTag(par1ItemStack.getDisplayName());
/* 117:    */           }
/* 118:153 */           if (!par3EntityPlayer.capabilities.isCreativeMode) {
/* 119:155 */             par1ItemStack.stackSize -= 1;
/* 120:    */           }
/* 121:    */         }
/* 122:    */       }
/* 123:    */     }
/* 124:161 */     return par1ItemStack;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static Entity spawnCreature(World par0World, int par1, double par2, double par4, double par6)
/* 128:    */   {
/* 129:172 */     if (!EntityList.entityEggs.containsKey(Integer.valueOf(par1))) {
/* 130:174 */       return null;
/* 131:    */     }
/* 132:178 */     Entity var8 = null;
/* 133:180 */     for (int var9 = 0; var9 < 1; var9++)
/* 134:    */     {
/* 135:182 */       var8 = EntityList.createEntityByID(par1, par0World);
/* 136:184 */       if ((var8 != null) && ((var8 instanceof EntityLivingBase)))
/* 137:    */       {
/* 138:186 */         EntityLiving var10 = (EntityLiving)var8;
/* 139:187 */         var8.setLocationAndAngles(par2, par4, par6, MathHelper.wrapAngleTo180_float(par0World.rand.nextFloat() * 360.0F), 0.0F);
/* 140:188 */         var10.rotationYawHead = var10.rotationYaw;
/* 141:189 */         var10.renderYawOffset = var10.rotationYaw;
/* 142:190 */         var10.onSpawnWithEgg(null);
/* 143:191 */         par0World.spawnEntityInWorld(var8);
/* 144:192 */         var10.playLivingSound();
/* 145:    */       }
/* 146:    */     }
/* 147:196 */     return var8;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
/* 151:    */   {
/* 152:205 */     Iterator var4 = EntityList.entityEggs.values().iterator();
/* 153:207 */     while (var4.hasNext())
/* 154:    */     {
/* 155:209 */       EntityList.EntityEggInfo var5 = (EntityList.EntityEggInfo)var4.next();
/* 156:210 */       p_150895_3_.add(new ItemStack(p_150895_1_, 1, var5.spawnedID));
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void registerIcons(IIconRegister par1IconRegister)
/* 161:    */   {
/* 162:216 */     super.registerIcons(par1IconRegister);
/* 163:217 */     this.theIcon = par1IconRegister.registerIcon(getIconString() + "_overlay");
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemMonsterPlacer
 * JD-Core Version:    0.7.0.1
 */