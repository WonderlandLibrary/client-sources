/*  1:   */ package me.connorm.Nodus.module.modules;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.module.core.Category;
/*  5:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  6:   */ import net.minecraft.client.Minecraft;
/*  7:   */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  8:   */ import net.minecraft.potion.PotionEffect;
/*  9:   */ 
/* 10:   */ public class HighJump
/* 11:   */   extends NodusModule
/* 12:   */ {
/* 13:   */   public HighJump()
/* 14:   */   {
/* 15:12 */     super("HighJump", Category.PLAYER);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void onEnable()
/* 19:   */   {
/* 20:17 */     if (Nodus.theNodus.getMinecraft().theWorld == null) {
/* 21:18 */       return;
/* 22:   */     }
/* 23:19 */     Nodus.theNodus.getMinecraft().thePlayer.addPotionEffect(new PotionEffect(8, 999999999, 5));
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void onDisable()
/* 27:   */   {
/* 28:24 */     Nodus.theNodus.getMinecraft().thePlayer.removePotionEffect(8);
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.modules.HighJump
 * JD-Core Version:    0.7.0.1
 */