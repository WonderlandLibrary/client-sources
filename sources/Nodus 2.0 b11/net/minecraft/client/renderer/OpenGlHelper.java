/*   1:    */ package net.minecraft.client.renderer;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.Minecraft;
/*   4:    */ import net.minecraft.client.settings.GameSettings;
/*   5:    */ import net.minecraft.client.settings.GameSettings.Options;
/*   6:    */ import net.minecraft.src.Config;
/*   7:    */ import org.lwjgl.opengl.ARBMultitexture;
/*   8:    */ import org.lwjgl.opengl.ContextCapabilities;
/*   9:    */ import org.lwjgl.opengl.GL11;
/*  10:    */ import org.lwjgl.opengl.GL13;
/*  11:    */ import org.lwjgl.opengl.GL14;
/*  12:    */ import org.lwjgl.opengl.GLContext;
/*  13:    */ 
/*  14:    */ public class OpenGlHelper
/*  15:    */ {
/*  16:    */   public static boolean openGL21;
/*  17:    */   public static int defaultTexUnit;
/*  18:    */   public static int lightmapTexUnit;
/*  19:    */   public static boolean anisotropicFilteringSupported;
/*  20:    */   public static int anisotropicFilteringMax;
/*  21:    */   private static boolean useMultitextureARB;
/*  22:    */   private static boolean openGL14;
/*  23:    */   public static boolean framebufferSupported;
/*  24:    */   public static boolean shadersSupported;
/*  25: 37 */   public static float lastBrightnessX = 0.0F;
/*  26: 38 */   public static float lastBrightnessY = 0.0F;
/*  27:    */   private static final String __OBFID = "CL_00001179";
/*  28:    */   
/*  29:    */   public static void initializeTextures()
/*  30:    */   {
/*  31: 46 */     Config.initDisplay();
/*  32: 47 */     useMultitextureARB = (GLContext.getCapabilities().GL_ARB_multitexture) && (!GLContext.getCapabilities().OpenGL13);
/*  33: 49 */     if (useMultitextureARB)
/*  34:    */     {
/*  35: 51 */       defaultTexUnit = 33984;
/*  36: 52 */       lightmapTexUnit = 33985;
/*  37:    */     }
/*  38:    */     else
/*  39:    */     {
/*  40: 56 */       defaultTexUnit = 33984;
/*  41: 57 */       lightmapTexUnit = 33985;
/*  42:    */     }
/*  43: 60 */     openGL14 = GLContext.getCapabilities().OpenGL14;
/*  44: 61 */     framebufferSupported = (openGL14) && (GLContext.getCapabilities().GL_ARB_framebuffer_object);
/*  45: 62 */     anisotropicFilteringSupported = GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic;
/*  46: 63 */     anisotropicFilteringMax = (int)(anisotropicFilteringSupported ? GL11.glGetFloat(34047) : 0.0F);
/*  47: 64 */     GameSettings.Options.ANISOTROPIC_FILTERING.setValueMax(anisotropicFilteringMax);
/*  48: 65 */     openGL21 = GLContext.getCapabilities().OpenGL21;
/*  49: 66 */     shadersSupported = (framebufferSupported) && (openGL21);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static void setActiveTexture(int par0)
/*  53:    */   {
/*  54: 74 */     if (useMultitextureARB) {
/*  55: 76 */       ARBMultitexture.glActiveTextureARB(par0);
/*  56:    */     } else {
/*  57: 80 */       GL13.glActiveTexture(par0);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static void setClientActiveTexture(int par0)
/*  62:    */   {
/*  63: 89 */     if (useMultitextureARB) {
/*  64: 91 */       ARBMultitexture.glClientActiveTextureARB(par0);
/*  65:    */     } else {
/*  66: 95 */       GL13.glClientActiveTexture(par0);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static void setLightmapTextureCoords(int par0, float par1, float par2)
/*  71:    */   {
/*  72:104 */     if (useMultitextureARB) {
/*  73:106 */       ARBMultitexture.glMultiTexCoord2fARB(par0, par1, par2);
/*  74:    */     } else {
/*  75:110 */       GL13.glMultiTexCoord2f(par0, par1, par2);
/*  76:    */     }
/*  77:113 */     if (par0 == lightmapTexUnit)
/*  78:    */     {
/*  79:115 */       lastBrightnessX = par1;
/*  80:116 */       lastBrightnessY = par2;
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static void glBlendFunc(int p_148821_0_, int p_148821_1_, int p_148821_2_, int p_148821_3_)
/*  85:    */   {
/*  86:122 */     if (openGL14) {
/*  87:124 */       GL14.glBlendFuncSeparate(p_148821_0_, p_148821_1_, p_148821_2_, p_148821_3_);
/*  88:    */     } else {
/*  89:128 */       GL11.glBlendFunc(p_148821_0_, p_148821_1_);
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static boolean isFramebufferEnabled()
/*  94:    */   {
/*  95:134 */     return (framebufferSupported) && (Minecraft.getMinecraft().gameSettings.fboEnable);
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.OpenGlHelper
 * JD-Core Version:    0.7.0.1
 */