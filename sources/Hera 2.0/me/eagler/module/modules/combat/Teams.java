/*    */ package me.eagler.module.modules.combat;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class Teams
/*    */   extends Module
/*    */ {
/* 12 */   public static ArrayList<EntityPlayer> teams = new ArrayList<EntityPlayer>();
/*    */   
/*    */   public Teams() {
/* 15 */     super("Teams", Category.Combat);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 20 */     teams.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 26 */     for (Entity e : this.mc.theWorld.loadedEntityList) {
/*    */       
/* 28 */       if (e instanceof EntityPlayer) {
/*    */         
/* 30 */         EntityPlayer ep = (EntityPlayer)e;
/*    */         
/* 32 */         if (ep.getDisplayName().getFormattedText().startsWith("§" + this.mc.thePlayer.getDisplayName().getFormattedText().charAt(1))) {
/*    */           
/* 34 */           teams.add(ep);
/*    */           
/*    */           continue;
/*    */         } 
/* 38 */         if (teams.contains(ep))
/*    */         {
/* 40 */           teams.remove(ep);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\combat\Teams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */