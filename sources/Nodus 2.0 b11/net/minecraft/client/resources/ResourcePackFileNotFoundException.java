/*  1:   */ package net.minecraft.client.resources;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.FileNotFoundException;
/*  5:   */ 
/*  6:   */ public class ResourcePackFileNotFoundException
/*  7:   */   extends FileNotFoundException
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00001086";
/* 10:   */   
/* 11:   */   public ResourcePackFileNotFoundException(File par1File, String par2Str)
/* 12:   */   {
/* 13:12 */     super(String.format("'%s' in ResourcePack '%s'", new Object[] { par2Str, par1File }));
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.ResourcePackFileNotFoundException
 * JD-Core Version:    0.7.0.1
 */