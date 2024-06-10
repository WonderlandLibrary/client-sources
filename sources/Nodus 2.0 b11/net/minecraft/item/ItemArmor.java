/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.BlockDispenser;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.command.IEntitySelector.ArmoredMob;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*   9:    */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*  10:    */ import net.minecraft.dispenser.IBlockSource;
/*  11:    */ import net.minecraft.entity.EntityLiving;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.player.EntityPlayer;
/*  14:    */ import net.minecraft.init.Items;
/*  15:    */ import net.minecraft.nbt.NBTTagCompound;
/*  16:    */ import net.minecraft.util.AABBPool;
/*  17:    */ import net.minecraft.util.AxisAlignedBB;
/*  18:    */ import net.minecraft.util.EnumFacing;
/*  19:    */ import net.minecraft.util.IIcon;
/*  20:    */ import net.minecraft.util.IRegistry;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ 
/*  23:    */ public class ItemArmor
/*  24:    */   extends Item
/*  25:    */ {
/*  26: 24 */   private static final int[] maxDamageArray = { 11, 16, 15, 13 };
/*  27: 25 */   private static final String[] CLOTH_OVERLAY_NAMES = { "leather_helmet_overlay", "leather_chestplate_overlay", "leather_leggings_overlay", "leather_boots_overlay" };
/*  28: 26 */   public static final String[] EMPTY_SLOT_NAMES = { "empty_armor_slot_helmet", "empty_armor_slot_chestplate", "empty_armor_slot_leggings", "empty_armor_slot_boots" };
/*  29: 27 */   private static final IBehaviorDispenseItem dispenserBehavior = new BehaviorDefaultDispenseItem()
/*  30:    */   {
/*  31:    */     private static final String __OBFID = "CL_00001767";
/*  32:    */     
/*  33:    */     protected ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
/*  34:    */     {
/*  35: 32 */       EnumFacing var3 = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata());
/*  36: 33 */       int var4 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
/*  37: 34 */       int var5 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
/*  38: 35 */       int var6 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
/*  39: 36 */       AxisAlignedBB var7 = AxisAlignedBB.getAABBPool().getAABB(var4, var5, var6, var4 + 1, var5 + 1, var6 + 1);
/*  40: 37 */       List var8 = par1IBlockSource.getWorld().selectEntitiesWithinAABB(EntityLivingBase.class, var7, new IEntitySelector.ArmoredMob(par2ItemStack));
/*  41: 39 */       if (var8.size() > 0)
/*  42:    */       {
/*  43: 41 */         EntityLivingBase var9 = (EntityLivingBase)var8.get(0);
/*  44: 42 */         int var10 = (var9 instanceof EntityPlayer) ? 1 : 0;
/*  45: 43 */         int var11 = EntityLiving.getArmorPosition(par2ItemStack);
/*  46: 44 */         ItemStack var12 = par2ItemStack.copy();
/*  47: 45 */         var12.stackSize = 1;
/*  48: 46 */         var9.setCurrentItemOrArmor(var11 - var10, var12);
/*  49: 48 */         if ((var9 instanceof EntityLiving)) {
/*  50: 50 */           ((EntityLiving)var9).setEquipmentDropChance(var11, 2.0F);
/*  51:    */         }
/*  52: 53 */         par2ItemStack.stackSize -= 1;
/*  53: 54 */         return par2ItemStack;
/*  54:    */       }
/*  55: 58 */       return super.dispenseStack(par1IBlockSource, par2ItemStack);
/*  56:    */     }
/*  57:    */   };
/*  58:    */   public final int armorType;
/*  59:    */   public final int damageReduceAmount;
/*  60:    */   public final int renderIndex;
/*  61:    */   private final ArmorMaterial material;
/*  62:    */   private IIcon overlayIcon;
/*  63:    */   private IIcon emptySlotIcon;
/*  64:    */   private static final String __OBFID = "CL_00001766";
/*  65:    */   
/*  66:    */   public ItemArmor(ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_)
/*  67:    */   {
/*  68: 85 */     this.material = p_i45325_1_;
/*  69: 86 */     this.armorType = p_i45325_3_;
/*  70: 87 */     this.renderIndex = p_i45325_2_;
/*  71: 88 */     this.damageReduceAmount = p_i45325_1_.getDamageReductionAmount(p_i45325_3_);
/*  72: 89 */     setMaxDamage(p_i45325_1_.getDurability(p_i45325_3_));
/*  73: 90 */     this.maxStackSize = 1;
/*  74: 91 */     setCreativeTab(CreativeTabs.tabCombat);
/*  75: 92 */     BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserBehavior);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
/*  79:    */   {
/*  80: 97 */     if (par2 > 0) {
/*  81: 99 */       return 16777215;
/*  82:    */     }
/*  83:103 */     int var3 = getColor(par1ItemStack);
/*  84:105 */     if (var3 < 0) {
/*  85:107 */       var3 = 16777215;
/*  86:    */     }
/*  87:110 */     return var3;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean requiresMultipleRenderPasses()
/*  91:    */   {
/*  92:116 */     return this.material == ArmorMaterial.CLOTH;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getItemEnchantability()
/*  96:    */   {
/*  97:124 */     return this.material.getEnchantability();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ArmorMaterial getArmorMaterial()
/* 101:    */   {
/* 102:132 */     return this.material;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean hasColor(ItemStack par1ItemStack)
/* 106:    */   {
/* 107:140 */     return !par1ItemStack.getTagCompound().func_150297_b("display", 10) ? false : !par1ItemStack.hasTagCompound() ? false : this.material != ArmorMaterial.CLOTH ? false : par1ItemStack.getTagCompound().getCompoundTag("display").func_150297_b("color", 3);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public int getColor(ItemStack par1ItemStack)
/* 111:    */   {
/* 112:148 */     if (this.material != ArmorMaterial.CLOTH) {
/* 113:150 */       return -1;
/* 114:    */     }
/* 115:154 */     NBTTagCompound var2 = par1ItemStack.getTagCompound();
/* 116:156 */     if (var2 == null) {
/* 117:158 */       return 10511680;
/* 118:    */     }
/* 119:162 */     NBTTagCompound var3 = var2.getCompoundTag("display");
/* 120:163 */     return var3.func_150297_b("color", 3) ? var3.getInteger("color") : var3 == null ? 10511680 : 10511680;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public IIcon getIconFromDamageForRenderPass(int par1, int par2)
/* 124:    */   {
/* 125:173 */     return par2 == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(par1, par2);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void removeColor(ItemStack par1ItemStack)
/* 129:    */   {
/* 130:181 */     if (this.material == ArmorMaterial.CLOTH)
/* 131:    */     {
/* 132:183 */       NBTTagCompound var2 = par1ItemStack.getTagCompound();
/* 133:185 */       if (var2 != null)
/* 134:    */       {
/* 135:187 */         NBTTagCompound var3 = var2.getCompoundTag("display");
/* 136:189 */         if (var3.hasKey("color")) {
/* 137:191 */           var3.removeTag("color");
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void func_82813_b(ItemStack par1ItemStack, int par2)
/* 144:    */   {
/* 145:199 */     if (this.material != ArmorMaterial.CLOTH) {
/* 146:201 */       throw new UnsupportedOperationException("Can't dye non-leather!");
/* 147:    */     }
/* 148:205 */     NBTTagCompound var3 = par1ItemStack.getTagCompound();
/* 149:207 */     if (var3 == null)
/* 150:    */     {
/* 151:209 */       var3 = new NBTTagCompound();
/* 152:210 */       par1ItemStack.setTagCompound(var3);
/* 153:    */     }
/* 154:213 */     NBTTagCompound var4 = var3.getCompoundTag("display");
/* 155:215 */     if (!var3.func_150297_b("display", 10)) {
/* 156:217 */       var3.setTag("display", var4);
/* 157:    */     }
/* 158:220 */     var4.setInteger("color", par2);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/* 162:    */   {
/* 163:229 */     return this.material.func_151685_b() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void registerIcons(IIconRegister par1IconRegister)
/* 167:    */   {
/* 168:234 */     super.registerIcons(par1IconRegister);
/* 169:236 */     if (this.material == ArmorMaterial.CLOTH) {
/* 170:238 */       this.overlayIcon = par1IconRegister.registerIcon(CLOTH_OVERLAY_NAMES[this.armorType]);
/* 171:    */     }
/* 172:241 */     this.emptySlotIcon = par1IconRegister.registerIcon(EMPTY_SLOT_NAMES[this.armorType]);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/* 176:    */   {
/* 177:249 */     int var4 = EntityLiving.getArmorPosition(par1ItemStack) - 1;
/* 178:250 */     ItemStack var5 = par3EntityPlayer.getCurrentArmor(var4);
/* 179:252 */     if (var5 == null)
/* 180:    */     {
/* 181:254 */       par3EntityPlayer.setCurrentItemOrArmor(var4, par1ItemStack.copy());
/* 182:255 */       par1ItemStack.stackSize = 0;
/* 183:    */     }
/* 184:258 */     return par1ItemStack;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public static IIcon func_94602_b(int par0)
/* 188:    */   {
/* 189:263 */     switch (par0)
/* 190:    */     {
/* 191:    */     case 0: 
/* 192:266 */       return Items.diamond_helmet.emptySlotIcon;
/* 193:    */     case 1: 
/* 194:269 */       return Items.diamond_chestplate.emptySlotIcon;
/* 195:    */     case 2: 
/* 196:272 */       return Items.diamond_leggings.emptySlotIcon;
/* 197:    */     case 3: 
/* 198:275 */       return Items.diamond_boots.emptySlotIcon;
/* 199:    */     }
/* 200:278 */     return null;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static enum ArmorMaterial
/* 204:    */   {
/* 205:284 */     CLOTH("CLOTH", 0, 5, new int[] { 1, 3, 2, 1 }, 15),  CHAIN("CHAIN", 1, 15, new int[] { 2, 5, 4, 1 }, 12),  IRON("IRON", 2, 15, new int[] { 2, 6, 5, 2 }, 9),  GOLD("GOLD", 3, 7, new int[] { 2, 5, 3, 1 }, 25),  DIAMOND("DIAMOND", 4, 33, new int[] { 3, 8, 6, 3 }, 10);
/* 206:    */     
/* 207:    */     private int maxDamageFactor;
/* 208:    */     private int[] damageReductionAmountArray;
/* 209:    */     private int enchantability;
/* 210:293 */     private static final ArmorMaterial[] $VALUES = { CLOTH, CHAIN, IRON, GOLD, DIAMOND };
/* 211:    */     private static final String __OBFID = "CL_00001768";
/* 212:    */     
/* 213:    */     private ArmorMaterial(String par1Str, int par2, int par3, int[] par4ArrayOfInteger, int par5)
/* 214:    */     {
/* 215:298 */       this.maxDamageFactor = par3;
/* 216:299 */       this.damageReductionAmountArray = par4ArrayOfInteger;
/* 217:300 */       this.enchantability = par5;
/* 218:    */     }
/* 219:    */     
/* 220:    */     public int getDurability(int par1)
/* 221:    */     {
/* 222:305 */       return ItemArmor.maxDamageArray[par1] * this.maxDamageFactor;
/* 223:    */     }
/* 224:    */     
/* 225:    */     public int getDamageReductionAmount(int par1)
/* 226:    */     {
/* 227:310 */       return this.damageReductionAmountArray[par1];
/* 228:    */     }
/* 229:    */     
/* 230:    */     public int getEnchantability()
/* 231:    */     {
/* 232:315 */       return this.enchantability;
/* 233:    */     }
/* 234:    */     
/* 235:    */     public Item func_151685_b()
/* 236:    */     {
/* 237:320 */       return this == DIAMOND ? Items.diamond : this == IRON ? Items.iron_ingot : this == GOLD ? Items.gold_ingot : this == CHAIN ? Items.iron_ingot : this == CLOTH ? Items.leather : null;
/* 238:    */     }
/* 239:    */   }
/* 240:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemArmor
 * JD-Core Version:    0.7.0.1
 */