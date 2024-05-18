/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Team
/*    */ {
/*    */   public boolean isSameTeam(Team other) {
/* 14 */     return (other == null) ? false : ((this == other));
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract String getRegisteredName();
/*    */ 
/*    */   
/*    */   public abstract String formatString(String paramString);
/*    */ 
/*    */   
/*    */   public abstract boolean getSeeFriendlyInvisiblesEnabled();
/*    */   
/*    */   public abstract boolean getAllowFriendlyFire();
/*    */   
/*    */   public abstract EnumVisible getNameTagVisibility();
/*    */   
/*    */   public abstract Collection<String> getMembershipCollection();
/*    */   
/*    */   public abstract EnumVisible getDeathMessageVisibility();
/*    */   
/*    */   public enum EnumVisible
/*    */   {
/* 36 */     ALWAYS("always", 0),
/* 37 */     NEVER("never", 1),
/* 38 */     HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
/* 39 */     HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);
/*    */     
/* 41 */     private static Map<String, EnumVisible> field_178828_g = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public final String field_178830_e;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public final int field_178827_f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/*    */       byte b;
/*    */       int i;
/*    */       EnumVisible[] arrayOfEnumVisible;
/* 62 */       for (i = (arrayOfEnumVisible = values()).length, b = 0; b < i; ) { EnumVisible team$enumvisible = arrayOfEnumVisible[b];
/*    */         
/* 64 */         field_178828_g.put(team$enumvisible.field_178830_e, team$enumvisible);
/*    */         b++; }
/*    */     
/*    */     }
/*    */     
/*    */     public static String[] func_178825_a() {
/*    */       return (String[])field_178828_g.keySet().toArray((Object[])new String[field_178828_g.size()]);
/*    */     }
/*    */     
/*    */     public static EnumVisible func_178824_a(String p_178824_0_) {
/*    */       return field_178828_g.get(p_178824_0_);
/*    */     }
/*    */     
/*    */     EnumVisible(String p_i45550_3_, int p_i45550_4_) {
/*    */       this.field_178830_e = p_i45550_3_;
/*    */       this.field_178827_f = p_i45550_4_;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\scoreboard\Team.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */