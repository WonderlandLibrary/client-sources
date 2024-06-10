/*  1:   */ package net.minecraft.client.renderer;
/*  2:   */ 
/*  3:   */ import org.lwjgl.opengl.ContextCapabilities;
/*  4:   */ import org.lwjgl.opengl.GLContext;
/*  5:   */ 
/*  6:   */ public class OpenGlCapsChecker
/*  7:   */ {
/*  8:   */   private static final String __OBFID = "CL_00000649";
/*  9:   */   
/* 10:   */   public static boolean checkARBOcclusion()
/* 11:   */   {
/* 12:14 */     return GLContext.getCapabilities().GL_ARB_occlusion_query;
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.OpenGlCapsChecker
 * JD-Core Version:    0.7.0.1
 */