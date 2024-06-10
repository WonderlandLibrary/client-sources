/*    */ package nightmare.utils.render;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ import net.minecraft.client.shader.Shader;
/*    */ import net.minecraft.client.shader.ShaderGroup;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.mixin.mixins.accessor.MinecraftAccessor;
/*    */ import nightmare.utils.AccessorUtils;
/*    */ import nightmare.utils.ScreenUtils;
/*    */ 
/*    */ public class BlurUtils {
/*    */   private static ShaderGroup blurShader;
/* 15 */   private static Minecraft mc = Minecraft.func_71410_x();
/* 16 */   private static MinecraftAccessor mcAccessor = (MinecraftAccessor)mc;
/*    */   
/*    */   private static Framebuffer buffer;
/* 19 */   private static float lastScale = 0.0F;
/* 20 */   private static float lastScaleWidth = 0.0F;
/* 21 */   private static float lastScaleHeight = 0.0F;
/*    */   
/*    */   private static void reinitShader() {
/*    */     try {
/* 25 */       buffer = new Framebuffer(mc.field_71443_c, mc.field_71440_d, true);
/* 26 */       buffer.func_147604_a(0.0F, 0.0F, 0.0F, 0.0F);
/* 27 */       blurShader = new ShaderGroup(mc.func_110434_K(), mc.func_110442_L(), mc.func_147110_a(), new ResourceLocation("shaders/post/blurArea.json"));
/* 28 */       blurShader.func_148026_a(mc.field_71443_c, mc.field_71440_d);
/* 29 */     } catch (Exception e) {
/* 30 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void drawBlurRect(float x, float y, float width, float height) {
/* 36 */     int factor = ScreenUtils.getScaleFactor();
/* 37 */     int factor2 = ScreenUtils.getWidth();
/* 38 */     int factor3 = ScreenUtils.getHeight();
/*    */     
/* 40 */     if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3) {
/* 41 */       reinitShader();
/*    */     }
/*    */     
/* 44 */     lastScale = factor;
/* 45 */     lastScaleWidth = factor2;
/* 46 */     lastScaleHeight = factor3;
/*    */     
/* 48 */     ((Shader)((AccessorUtils)blurShader).getListShaders().get(0)).func_148043_c().func_147991_a("BlurXY").func_148087_a(x * ScreenUtils.getScaleFactor() / 2.0F, (factor3 - height) * ScreenUtils.getScaleFactor() / 2.0F);
/* 49 */     ((Shader)((AccessorUtils)blurShader).getListShaders().get(1)).func_148043_c().func_147991_a("BlurXY").func_148087_a(x * ScreenUtils.getScaleFactor() / 2.0F, (factor3 - height) * ScreenUtils.getScaleFactor() / 2.0F);
/* 50 */     ((Shader)((AccessorUtils)blurShader).getListShaders().get(0)).func_148043_c().func_147991_a("BlurCoord").func_148087_a((width - x) * ScreenUtils.getScaleFactor() / 2.0F, (height - y) * ScreenUtils.getScaleFactor() / 2.0F);
/* 51 */     ((Shader)((AccessorUtils)blurShader).getListShaders().get(1)).func_148043_c().func_147991_a("BlurCoord").func_148087_a((width - x) * ScreenUtils.getScaleFactor() / 2.0F, (height - y) * ScreenUtils.getScaleFactor() / 2.0F);
/* 52 */     ((Shader)((AccessorUtils)blurShader).getListShaders().get(0)).func_148043_c().func_147991_a("Radius").func_148090_a((int)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Blur"), "Radius").getValDouble());
/* 53 */     ((Shader)((AccessorUtils)blurShader).getListShaders().get(1)).func_148043_c().func_147991_a("Radius").func_148090_a((int)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Blur"), "Radius").getValDouble());
/* 54 */     blurShader.func_148018_a((mcAccessor.timer()).field_74281_c);
/* 55 */     mc.func_147110_a().func_147610_a(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\render\BlurUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */