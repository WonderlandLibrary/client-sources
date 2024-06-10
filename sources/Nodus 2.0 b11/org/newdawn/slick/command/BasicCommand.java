/*  1:   */ package org.newdawn.slick.command;
/*  2:   */ 
/*  3:   */ public class BasicCommand
/*  4:   */   implements Command
/*  5:   */ {
/*  6:   */   private String name;
/*  7:   */   
/*  8:   */   public BasicCommand(String name)
/*  9:   */   {
/* 10:18 */     this.name = name;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public String getName()
/* 14:   */   {
/* 15:27 */     return this.name;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int hashCode()
/* 19:   */   {
/* 20:34 */     return this.name.hashCode();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean equals(Object other)
/* 24:   */   {
/* 25:41 */     if ((other instanceof BasicCommand)) {
/* 26:42 */       return ((BasicCommand)other).name.equals(this.name);
/* 27:   */     }
/* 28:45 */     return false;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String toString()
/* 32:   */   {
/* 33:52 */     return "[Command=" + this.name + "]";
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.command.BasicCommand
 * JD-Core Version:    0.7.0.1
 */