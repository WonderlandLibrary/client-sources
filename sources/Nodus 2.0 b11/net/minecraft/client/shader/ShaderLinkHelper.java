/*  1:   */ package net.minecraft.client.shader;
/*  2:   */ 
/*  3:   */ import net.minecraft.client.util.JsonException;
/*  4:   */ import org.apache.logging.log4j.LogManager;
/*  5:   */ import org.apache.logging.log4j.Logger;
/*  6:   */ import org.lwjgl.opengl.GL20;
/*  7:   */ 
/*  8:   */ public class ShaderLinkHelper
/*  9:   */ {
/* 10:10 */   private static final Logger logger = ;
/* 11:   */   private static ShaderLinkHelper staticShaderLinkHelper;
/* 12:   */   private static final String __OBFID = "CL_00001045";
/* 13:   */   
/* 14:   */   public static void setNewStaticShaderLinkHelper()
/* 15:   */   {
/* 16:16 */     staticShaderLinkHelper = new ShaderLinkHelper();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static ShaderLinkHelper getStaticShaderLinkHelper()
/* 20:   */   {
/* 21:21 */     return staticShaderLinkHelper;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void func_148077_a(ShaderManager p_148077_1_)
/* 25:   */   {
/* 26:26 */     p_148077_1_.func_147994_f().func_148054_b(p_148077_1_);
/* 27:27 */     p_148077_1_.func_147989_e().func_148054_b(p_148077_1_);
/* 28:28 */     GL20.glDeleteProgram(p_148077_1_.func_147986_h());
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int func_148078_c()
/* 32:   */     throws JsonException
/* 33:   */   {
/* 34:33 */     int var1 = GL20.glCreateProgram();
/* 35:35 */     if (var1 <= 0) {
/* 36:37 */       throw new JsonException("Could not create shader program (returned program ID " + var1 + ")");
/* 37:   */     }
/* 38:41 */     return var1;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void func_148075_b(ShaderManager p_148075_1_)
/* 42:   */   {
/* 43:47 */     p_148075_1_.func_147994_f().func_148056_a(p_148075_1_);
/* 44:48 */     p_148075_1_.func_147989_e().func_148056_a(p_148075_1_);
/* 45:49 */     GL20.glLinkProgram(p_148075_1_.func_147986_h());
/* 46:50 */     int var2 = GL20.glGetProgrami(p_148075_1_.func_147986_h(), 35714);
/* 47:52 */     if (var2 == 0)
/* 48:   */     {
/* 49:54 */       logger.warn("Error encountered when linking program containing VS " + p_148075_1_.func_147989_e().func_148055_a() + " and FS " + p_148075_1_.func_147994_f().func_148055_a() + ". Log output:");
/* 50:55 */       logger.warn(GL20.glGetProgramInfoLog(p_148075_1_.func_147986_h(), 32768));
/* 51:   */     }
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.shader.ShaderLinkHelper
 * JD-Core Version:    0.7.0.1
 */