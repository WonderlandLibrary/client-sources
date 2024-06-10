/*  1:   */ package net.minecraft.command;
/*  2:   */ 
/*  3:   */ public class SyntaxErrorException
/*  4:   */   extends CommandException
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00001189";
/*  7:   */   
/*  8:   */   public SyntaxErrorException()
/*  9:   */   {
/* 10: 9 */     this("commands.generic.snytax", new Object[0]);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public SyntaxErrorException(String par1Str, Object... par2ArrayOfObj)
/* 14:   */   {
/* 15:14 */     super(par1Str, par2ArrayOfObj);
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.command.SyntaxErrorException
 * JD-Core Version:    0.7.0.1
 */