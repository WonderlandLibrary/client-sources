/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class StatBasic
/*    */   extends StatBase
/*    */ {
/*    */   public StatBasic(String statIdIn, IChatComponent statNameIn, IStatType typeIn) {
/*  9 */     super(statIdIn, statNameIn, typeIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public StatBasic(String statIdIn, IChatComponent statNameIn) {
/* 14 */     super(statIdIn, statNameIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StatBase registerStat() {
/* 22 */     super.registerStat();
/* 23 */     StatList.generalStats.add(this);
/* 24 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\stats\StatBasic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */