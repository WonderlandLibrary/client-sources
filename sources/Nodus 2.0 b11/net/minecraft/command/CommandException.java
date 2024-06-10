/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ public class CommandException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   private Object[] errorObjects;
/*  7:   */   private static final String __OBFID = "CL_00001187";
/*  8:   */   
/*  9:   */   public CommandException(String par1Str, Object... par2ArrayOfObj)
/* 10:   */   {
/* 11:10 */     super(par1Str);
/* 12:11 */     this.errorObjects = par2ArrayOfObj;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Object[] getErrorOjbects()
/* 16:   */   {
/* 17:16 */     return this.errorObjects;
/* 18:   */   }
/* 19:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.CommandException
 * JD-Core Version:    0.7.0.1
 */