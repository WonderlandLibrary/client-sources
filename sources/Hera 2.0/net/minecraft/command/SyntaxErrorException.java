/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class SyntaxErrorException
/*    */   extends CommandException
/*    */ {
/*    */   public SyntaxErrorException() {
/*  7 */     this("commands.generic.snytax", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public SyntaxErrorException(String message, Object... replacements) {
/* 12 */     super(message, replacements);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\SyntaxErrorException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */