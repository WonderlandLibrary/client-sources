/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.EntityHanging;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*   9:    */ import net.minecraft.init.Items;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class EntityPainting
/*  15:    */   extends EntityHanging
/*  16:    */ {
/*  17:    */   public EnumArt art;
/*  18:    */   private static final String __OBFID = "CL_00001556";
/*  19:    */   
/*  20:    */   public EntityPainting(World par1World)
/*  21:    */   {
/*  22: 19 */     super(par1World);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public EntityPainting(World par1World, int par2, int par3, int par4, int par5)
/*  26:    */   {
/*  27: 24 */     super(par1World, par2, par3, par4, par5);
/*  28: 25 */     ArrayList var6 = new ArrayList();
/*  29: 26 */     EnumArt[] var7 = EnumArt.values();
/*  30: 27 */     int var8 = var7.length;
/*  31: 29 */     for (int var9 = 0; var9 < var8; var9++)
/*  32:    */     {
/*  33: 31 */       EnumArt var10 = var7[var9];
/*  34: 32 */       this.art = var10;
/*  35: 33 */       setDirection(par5);
/*  36: 35 */       if (onValidSurface()) {
/*  37: 37 */         var6.add(var10);
/*  38:    */       }
/*  39:    */     }
/*  40: 41 */     if (!var6.isEmpty()) {
/*  41: 43 */       this.art = ((EnumArt)var6.get(this.rand.nextInt(var6.size())));
/*  42:    */     }
/*  43: 46 */     setDirection(par5);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public EntityPainting(World par1World, int par2, int par3, int par4, int par5, String par6Str)
/*  47:    */   {
/*  48: 51 */     this(par1World, par2, par3, par4, par5);
/*  49: 52 */     EnumArt[] var7 = EnumArt.values();
/*  50: 53 */     int var8 = var7.length;
/*  51: 55 */     for (int var9 = 0; var9 < var8; var9++)
/*  52:    */     {
/*  53: 57 */       EnumArt var10 = var7[var9];
/*  54: 59 */       if (var10.title.equals(par6Str))
/*  55:    */       {
/*  56: 61 */         this.art = var10;
/*  57: 62 */         break;
/*  58:    */       }
/*  59:    */     }
/*  60: 66 */     setDirection(par5);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  64:    */   {
/*  65: 74 */     par1NBTTagCompound.setString("Motive", this.art.title);
/*  66: 75 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  70:    */   {
/*  71: 83 */     String var2 = par1NBTTagCompound.getString("Motive");
/*  72: 84 */     EnumArt[] var3 = EnumArt.values();
/*  73: 85 */     int var4 = var3.length;
/*  74: 87 */     for (int var5 = 0; var5 < var4; var5++)
/*  75:    */     {
/*  76: 89 */       EnumArt var6 = var3[var5];
/*  77: 91 */       if (var6.title.equals(var2)) {
/*  78: 93 */         this.art = var6;
/*  79:    */       }
/*  80:    */     }
/*  81: 97 */     if (this.art == null) {
/*  82: 99 */       this.art = EnumArt.Kebab;
/*  83:    */     }
/*  84:102 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getWidthPixels()
/*  88:    */   {
/*  89:107 */     return this.art.sizeX;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getHeightPixels()
/*  93:    */   {
/*  94:112 */     return this.art.sizeY;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void onBroken(Entity par1Entity)
/*  98:    */   {
/*  99:120 */     if ((par1Entity instanceof EntityPlayer))
/* 100:    */     {
/* 101:122 */       EntityPlayer var2 = (EntityPlayer)par1Entity;
/* 102:124 */       if (var2.capabilities.isCreativeMode) {
/* 103:126 */         return;
/* 104:    */       }
/* 105:    */     }
/* 106:130 */     entityDropItem(new ItemStack(Items.painting), 0.0F);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static enum EnumArt
/* 110:    */   {
/* 111:135 */     Kebab("Kebab", 0, "Kebab", 16, 16, 0, 0),  Aztec("Aztec", 1, "Aztec", 16, 16, 16, 0),  Alban("Alban", 2, "Alban", 16, 16, 32, 0),  Aztec2("Aztec2", 3, "Aztec2", 16, 16, 48, 0),  Bomb("Bomb", 4, "Bomb", 16, 16, 64, 0),  Plant("Plant", 5, "Plant", 16, 16, 80, 0),  Wasteland("Wasteland", 6, "Wasteland", 16, 16, 96, 0),  Pool("Pool", 7, "Pool", 32, 16, 0, 32),  Courbet("Courbet", 8, "Courbet", 32, 16, 32, 32),  Sea("Sea", 9, "Sea", 32, 16, 64, 32),  Sunset("Sunset", 10, "Sunset", 32, 16, 96, 32),  Creebet("Creebet", 11, "Creebet", 32, 16, 128, 32),  Wanderer("Wanderer", 12, "Wanderer", 16, 32, 0, 64),  Graham("Graham", 13, "Graham", 16, 32, 16, 64),  Match("Match", 14, "Match", 32, 32, 0, 128),  Bust("Bust", 15, "Bust", 32, 32, 32, 128),  Stage("Stage", 16, "Stage", 32, 32, 64, 128),  Void("Void", 17, "Void", 32, 32, 96, 128),  SkullAndRoses("SkullAndRoses", 18, "SkullAndRoses", 32, 32, 128, 128),  Wither("Wither", 19, "Wither", 32, 32, 160, 128),  Fighters("Fighters", 20, "Fighters", 64, 32, 0, 96),  Pointer("Pointer", 21, "Pointer", 64, 64, 0, 192),  Pigscene("Pigscene", 22, "Pigscene", 64, 64, 64, 192),  BurningSkull("BurningSkull", 23, "BurningSkull", 64, 64, 128, 192),  Skeleton("Skeleton", 24, "Skeleton", 64, 48, 192, 64),  DonkeyKong("DonkeyKong", 25, "DonkeyKong", 64, 48, 192, 112);
/* 112:    */     
/* 113:161 */     public static final int maxArtTitleLength = "SkullAndRoses".length();
/* 114:    */     public final String title;
/* 115:    */     public final int sizeX;
/* 116:    */     public final int sizeY;
/* 117:    */     public final int offsetX;
/* 118:    */     public final int offsetY;
/* 119:168 */     private static final EnumArt[] $VALUES = { Kebab, Aztec, Alban, Aztec2, Bomb, Plant, Wasteland, Pool, Courbet, Sea, Sunset, Creebet, Wanderer, Graham, Match, Bust, Stage, Void, SkullAndRoses, Wither, Fighters, Pointer, Pigscene, BurningSkull, Skeleton, DonkeyKong };
/* 120:    */     private static final String __OBFID = "CL_00001557";
/* 121:    */     
/* 122:    */     private EnumArt(String par1Str, int par2, String par3Str, int par4, int par5, int par6, int par7)
/* 123:    */     {
/* 124:173 */       this.title = par3Str;
/* 125:174 */       this.sizeX = par4;
/* 126:175 */       this.sizeY = par5;
/* 127:176 */       this.offsetX = par6;
/* 128:177 */       this.offsetY = par7;
/* 129:    */     }
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityPainting
 * JD-Core Version:    0.7.0.1
 */