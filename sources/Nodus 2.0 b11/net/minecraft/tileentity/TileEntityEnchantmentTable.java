/*   1:    */ package net.minecraft.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.nbt.NBTTagCompound;
/*   6:    */ import net.minecraft.world.World;
/*   7:    */ 
/*   8:    */ public class TileEntityEnchantmentTable
/*   9:    */   extends TileEntity
/*  10:    */ {
/*  11:    */   public int field_145926_a;
/*  12:    */   public float field_145933_i;
/*  13:    */   public float field_145931_j;
/*  14:    */   public float field_145932_k;
/*  15:    */   public float field_145929_l;
/*  16:    */   public float field_145930_m;
/*  17:    */   public float field_145927_n;
/*  18:    */   public float field_145928_o;
/*  19:    */   public float field_145925_p;
/*  20:    */   public float field_145924_q;
/*  21: 19 */   private static Random field_145923_r = new Random();
/*  22:    */   private String field_145922_s;
/*  23:    */   private static final String __OBFID = "CL_00000354";
/*  24:    */   
/*  25:    */   public void writeToNBT(NBTTagCompound p_145841_1_)
/*  26:    */   {
/*  27: 25 */     super.writeToNBT(p_145841_1_);
/*  28: 27 */     if (func_145921_b()) {
/*  29: 29 */       p_145841_1_.setString("CustomName", this.field_145922_s);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void readFromNBT(NBTTagCompound p_145839_1_)
/*  34:    */   {
/*  35: 35 */     super.readFromNBT(p_145839_1_);
/*  36: 37 */     if (p_145839_1_.func_150297_b("CustomName", 8)) {
/*  37: 39 */       this.field_145922_s = p_145839_1_.getString("CustomName");
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void updateEntity()
/*  42:    */   {
/*  43: 45 */     super.updateEntity();
/*  44: 46 */     this.field_145927_n = this.field_145930_m;
/*  45: 47 */     this.field_145925_p = this.field_145928_o;
/*  46: 48 */     EntityPlayer var1 = this.worldObj.getClosestPlayer(this.field_145851_c + 0.5F, this.field_145848_d + 0.5F, this.field_145849_e + 0.5F, 3.0D);
/*  47: 50 */     if (var1 != null)
/*  48:    */     {
/*  49: 52 */       double var2 = var1.posX - (this.field_145851_c + 0.5F);
/*  50: 53 */       double var4 = var1.posZ - (this.field_145849_e + 0.5F);
/*  51: 54 */       this.field_145924_q = ((float)Math.atan2(var4, var2));
/*  52: 55 */       this.field_145930_m += 0.1F;
/*  53: 57 */       if ((this.field_145930_m < 0.5F) || (field_145923_r.nextInt(40) == 0))
/*  54:    */       {
/*  55: 59 */         float var6 = this.field_145932_k;
/*  56:    */         do
/*  57:    */         {
/*  58: 63 */           this.field_145932_k += field_145923_r.nextInt(4) - field_145923_r.nextInt(4);
/*  59: 65 */         } while (var6 == this.field_145932_k);
/*  60:    */       }
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64: 70 */       this.field_145924_q += 0.02F;
/*  65: 71 */       this.field_145930_m -= 0.1F;
/*  66:    */     }
/*  67: 74 */     while (this.field_145928_o >= 3.141593F) {
/*  68: 76 */       this.field_145928_o -= 6.283186F;
/*  69:    */     }
/*  70: 79 */     while (this.field_145928_o < -3.141593F) {
/*  71: 81 */       this.field_145928_o += 6.283186F;
/*  72:    */     }
/*  73: 84 */     while (this.field_145924_q >= 3.141593F) {
/*  74: 86 */       this.field_145924_q -= 6.283186F;
/*  75:    */     }
/*  76: 89 */     while (this.field_145924_q < -3.141593F) {
/*  77: 91 */       this.field_145924_q += 6.283186F;
/*  78:    */     }
/*  79: 96 */     for (float var7 = this.field_145924_q - this.field_145928_o; var7 >= 3.141593F; var7 -= 6.283186F) {}
/*  80:101 */     while (var7 < -3.141593F) {
/*  81:103 */       var7 += 6.283186F;
/*  82:    */     }
/*  83:106 */     this.field_145928_o += var7 * 0.4F;
/*  84:108 */     if (this.field_145930_m < 0.0F) {
/*  85:110 */       this.field_145930_m = 0.0F;
/*  86:    */     }
/*  87:113 */     if (this.field_145930_m > 1.0F) {
/*  88:115 */       this.field_145930_m = 1.0F;
/*  89:    */     }
/*  90:118 */     this.field_145926_a += 1;
/*  91:119 */     this.field_145931_j = this.field_145933_i;
/*  92:120 */     float var3 = (this.field_145932_k - this.field_145933_i) * 0.4F;
/*  93:121 */     float var8 = 0.2F;
/*  94:123 */     if (var3 < -var8) {
/*  95:125 */       var3 = -var8;
/*  96:    */     }
/*  97:128 */     if (var3 > var8) {
/*  98:130 */       var3 = var8;
/*  99:    */     }
/* 100:133 */     this.field_145929_l += (var3 - this.field_145929_l) * 0.9F;
/* 101:134 */     this.field_145933_i += this.field_145929_l;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String func_145919_a()
/* 105:    */   {
/* 106:139 */     return func_145921_b() ? this.field_145922_s : "container.enchant";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean func_145921_b()
/* 110:    */   {
/* 111:144 */     return (this.field_145922_s != null) && (this.field_145922_s.length() > 0);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void func_145920_a(String p_145920_1_)
/* 115:    */   {
/* 116:149 */     this.field_145922_s = p_145920_1_;
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityEnchantmentTable
 * JD-Core Version:    0.7.0.1
 */