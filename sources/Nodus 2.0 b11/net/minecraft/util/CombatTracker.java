/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.EntityLivingBase;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class CombatTracker
/*  15:    */ {
/*  16: 16 */   private final List combatEntries = new ArrayList();
/*  17:    */   private final EntityLivingBase fighter;
/*  18:    */   private int field_94555_c;
/*  19:    */   private boolean field_94552_d;
/*  20:    */   private boolean field_94553_e;
/*  21:    */   private String field_94551_f;
/*  22:    */   private static final String __OBFID = "CL_00001520";
/*  23:    */   
/*  24:    */   public CombatTracker(EntityLivingBase par1EntityLivingBase)
/*  25:    */   {
/*  26: 28 */     this.fighter = par1EntityLivingBase;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void func_94545_a()
/*  30:    */   {
/*  31: 33 */     func_94542_g();
/*  32: 35 */     if (this.fighter.isOnLadder())
/*  33:    */     {
/*  34: 37 */       Block var1 = this.fighter.worldObj.getBlock(MathHelper.floor_double(this.fighter.posX), MathHelper.floor_double(this.fighter.boundingBox.minY), MathHelper.floor_double(this.fighter.posZ));
/*  35: 39 */       if (var1 == Blocks.ladder) {
/*  36: 41 */         this.field_94551_f = "ladder";
/*  37: 43 */       } else if (var1 == Blocks.vine) {
/*  38: 45 */         this.field_94551_f = "vines";
/*  39:    */       }
/*  40:    */     }
/*  41: 48 */     else if (this.fighter.isInWater())
/*  42:    */     {
/*  43: 50 */       this.field_94551_f = "water";
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void func_94547_a(DamageSource par1DamageSource, float par2, float par3)
/*  48:    */   {
/*  49: 56 */     func_94549_h();
/*  50: 57 */     func_94545_a();
/*  51: 58 */     CombatEntry var4 = new CombatEntry(par1DamageSource, this.fighter.ticksExisted, par2, par3, this.field_94551_f, this.fighter.fallDistance);
/*  52: 59 */     this.combatEntries.add(var4);
/*  53: 60 */     this.field_94555_c = this.fighter.ticksExisted;
/*  54: 61 */     this.field_94553_e = true;
/*  55: 62 */     this.field_94552_d |= var4.func_94559_f();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public IChatComponent func_151521_b()
/*  59:    */   {
/*  60: 67 */     if (this.combatEntries.size() == 0) {
/*  61: 69 */       return new ChatComponentTranslation("death.attack.generic", new Object[] { this.fighter.func_145748_c_() });
/*  62:    */     }
/*  63: 73 */     CombatEntry var1 = func_94544_f();
/*  64: 74 */     CombatEntry var2 = (CombatEntry)this.combatEntries.get(this.combatEntries.size() - 1);
/*  65: 75 */     IChatComponent var4 = var2.func_151522_h();
/*  66: 76 */     Entity var5 = var2.getDamageSrc().getEntity();
/*  67:    */     Object var3;
/*  68:    */     Object var3;
/*  69: 79 */     if ((var1 != null) && (var2.getDamageSrc() == DamageSource.fall))
/*  70:    */     {
/*  71: 81 */       IChatComponent var6 = var1.func_151522_h();
/*  72:    */       Object var3;
/*  73: 83 */       if ((var1.getDamageSrc() != DamageSource.fall) && (var1.getDamageSrc() != DamageSource.outOfWorld))
/*  74:    */       {
/*  75:    */         Object var3;
/*  76: 85 */         if ((var6 != null) && ((var4 == null) || (!var6.equals(var4))))
/*  77:    */         {
/*  78: 87 */           Entity var9 = var1.getDamageSrc().getEntity();
/*  79: 88 */           ItemStack var8 = (var9 instanceof EntityLivingBase) ? ((EntityLivingBase)var9).getHeldItem() : null;
/*  80:    */           Object var3;
/*  81: 90 */           if ((var8 != null) && (var8.hasDisplayName())) {
/*  82: 92 */             var3 = new ChatComponentTranslation("death.fell.assist.item", new Object[] { this.fighter.func_145748_c_(), var6, var8.func_151000_E() });
/*  83:    */           } else {
/*  84: 96 */             var3 = new ChatComponentTranslation("death.fell.assist", new Object[] { this.fighter.func_145748_c_(), var6 });
/*  85:    */           }
/*  86:    */         }
/*  87:    */         else
/*  88:    */         {
/*  89:    */           Object var3;
/*  90: 99 */           if (var4 != null)
/*  91:    */           {
/*  92:101 */             ItemStack var7 = (var5 instanceof EntityLivingBase) ? ((EntityLivingBase)var5).getHeldItem() : null;
/*  93:    */             Object var3;
/*  94:103 */             if ((var7 != null) && (var7.hasDisplayName())) {
/*  95:105 */               var3 = new ChatComponentTranslation("death.fell.finish.item", new Object[] { this.fighter.func_145748_c_(), var4, var7.func_151000_E() });
/*  96:    */             } else {
/*  97:109 */               var3 = new ChatComponentTranslation("death.fell.finish", new Object[] { this.fighter.func_145748_c_(), var4 });
/*  98:    */             }
/*  99:    */           }
/* 100:    */           else
/* 101:    */           {
/* 102:114 */             var3 = new ChatComponentTranslation("death.fell.killer", new Object[] { this.fighter.func_145748_c_() });
/* 103:    */           }
/* 104:    */         }
/* 105:    */       }
/* 106:    */       else
/* 107:    */       {
/* 108:119 */         var3 = new ChatComponentTranslation("death.fell.accident." + func_94548_b(var1), new Object[] { this.fighter.func_145748_c_() });
/* 109:    */       }
/* 110:    */     }
/* 111:    */     else
/* 112:    */     {
/* 113:124 */       var3 = var2.getDamageSrc().func_151519_b(this.fighter);
/* 114:    */     }
/* 115:127 */     return (IChatComponent)var3;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public EntityLivingBase func_94550_c()
/* 119:    */   {
/* 120:133 */     EntityLivingBase var1 = null;
/* 121:134 */     EntityPlayer var2 = null;
/* 122:135 */     float var3 = 0.0F;
/* 123:136 */     float var4 = 0.0F;
/* 124:137 */     Iterator var5 = this.combatEntries.iterator();
/* 125:139 */     while (var5.hasNext())
/* 126:    */     {
/* 127:141 */       CombatEntry var6 = (CombatEntry)var5.next();
/* 128:143 */       if (((var6.getDamageSrc().getEntity() instanceof EntityPlayer)) && ((var2 == null) || (var6.func_94563_c() > var4)))
/* 129:    */       {
/* 130:145 */         var4 = var6.func_94563_c();
/* 131:146 */         var2 = (EntityPlayer)var6.getDamageSrc().getEntity();
/* 132:    */       }
/* 133:149 */       if (((var6.getDamageSrc().getEntity() instanceof EntityLivingBase)) && ((var1 == null) || (var6.func_94563_c() > var3)))
/* 134:    */       {
/* 135:151 */         var3 = var6.func_94563_c();
/* 136:152 */         var1 = (EntityLivingBase)var6.getDamageSrc().getEntity();
/* 137:    */       }
/* 138:    */     }
/* 139:156 */     if ((var2 != null) && (var4 >= var3 / 3.0F)) {
/* 140:158 */       return var2;
/* 141:    */     }
/* 142:162 */     return var1;
/* 143:    */   }
/* 144:    */   
/* 145:    */   private CombatEntry func_94544_f()
/* 146:    */   {
/* 147:168 */     CombatEntry var1 = null;
/* 148:169 */     CombatEntry var2 = null;
/* 149:170 */     byte var3 = 0;
/* 150:171 */     float var4 = 0.0F;
/* 151:173 */     for (int var5 = 0; var5 < this.combatEntries.size(); var5++)
/* 152:    */     {
/* 153:175 */       CombatEntry var6 = (CombatEntry)this.combatEntries.get(var5);
/* 154:176 */       CombatEntry var7 = var5 > 0 ? (CombatEntry)this.combatEntries.get(var5 - 1) : null;
/* 155:178 */       if (((var6.getDamageSrc() == DamageSource.fall) || (var6.getDamageSrc() == DamageSource.outOfWorld)) && (var6.func_94561_i() > 0.0F) && ((var1 == null) || (var6.func_94561_i() > var4)))
/* 156:    */       {
/* 157:180 */         if (var5 > 0) {
/* 158:182 */           var1 = var7;
/* 159:    */         } else {
/* 160:186 */           var1 = var6;
/* 161:    */         }
/* 162:189 */         var4 = var6.func_94561_i();
/* 163:    */       }
/* 164:192 */       if ((var6.func_94562_g() != null) && ((var2 == null) || (var6.func_94563_c() > var3))) {
/* 165:194 */         var2 = var6;
/* 166:    */       }
/* 167:    */     }
/* 168:198 */     if ((var4 > 5.0F) && (var1 != null)) {
/* 169:200 */       return var1;
/* 170:    */     }
/* 171:202 */     if ((var3 > 5) && (var2 != null)) {
/* 172:204 */       return var2;
/* 173:    */     }
/* 174:208 */     return null;
/* 175:    */   }
/* 176:    */   
/* 177:    */   private String func_94548_b(CombatEntry par1CombatEntry)
/* 178:    */   {
/* 179:214 */     return par1CombatEntry.func_94562_g() == null ? "generic" : par1CombatEntry.func_94562_g();
/* 180:    */   }
/* 181:    */   
/* 182:    */   private void func_94542_g()
/* 183:    */   {
/* 184:219 */     this.field_94551_f = null;
/* 185:    */   }
/* 186:    */   
/* 187:    */   private void func_94549_h()
/* 188:    */   {
/* 189:224 */     int var1 = this.field_94552_d ? 300 : 100;
/* 190:226 */     if ((this.field_94553_e) && (this.fighter.ticksExisted - this.field_94555_c > var1))
/* 191:    */     {
/* 192:228 */       this.combatEntries.clear();
/* 193:229 */       this.field_94553_e = false;
/* 194:230 */       this.field_94552_d = false;
/* 195:    */     }
/* 196:    */   }
/* 197:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.CombatTracker
 * JD-Core Version:    0.7.0.1
 */