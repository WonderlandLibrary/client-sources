/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class EntityNotFoundException
/*    */   extends CommandException
/*    */ {
/*    */   public EntityNotFoundException() {
/*  7 */     this("commands.generic.entity.notFound", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityNotFoundException(String p_i46035_1_, Object... p_i46035_2_) {
/* 12 */     super(p_i46035_1_, p_i46035_2_);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\EntityNotFoundException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */