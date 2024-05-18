/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class NumberInvalidException
/*    */   extends CommandException
/*    */ {
/*    */   public NumberInvalidException() {
/*  7 */     this("commands.generic.num.invalid", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public NumberInvalidException(String message, Object... replacements) {
/* 12 */     super(message, replacements);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\NumberInvalidException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */