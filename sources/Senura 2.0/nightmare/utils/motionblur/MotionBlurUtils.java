/*    */ package nightmare.utils.motionblur;
/*    */ 
/*    */ import com.google.gson.JsonSyntaxException;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.shader.Shader;
/*    */ import net.minecraft.client.shader.ShaderGroup;
/*    */ import net.minecraft.client.shader.ShaderUniform;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.utils.AccessorUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MotionBlurUtils
/*    */ {
/* 19 */   public static final MotionBlurUtils instance = new MotionBlurUtils();
/*    */   
/* 21 */   private static final ResourceLocation location = new ResourceLocation("minecraft:shaders/post/motion_blur.json");
/* 22 */   private static final Logger logger = LogManager.getLogger();
/*    */   
/* 24 */   private Minecraft mc = Minecraft.func_71410_x();
/*    */   private ShaderGroup shader;
/*    */   private float shaderBlur;
/*    */   
/*    */   public float getBlurFactor() {
/* 29 */     return (float)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("MotionBlur"), "Amount").getValDouble();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ShaderGroup getShader() {
/* 35 */     if (this.shader == null) {
/* 36 */       this.shaderBlur = Float.NaN;
/*    */       
/*    */       try {
/* 39 */         this.shader = new ShaderGroup(this.mc.func_110434_K(), this.mc.func_110442_L(), this.mc.func_147110_a(), location);
/* 40 */         this.shader.func_148026_a(this.mc.field_71443_c, this.mc.field_71440_d);
/* 41 */       } catch (JsonSyntaxException|java.io.IOException error) {
/* 42 */         logger.error("Could not load motion blur shader", error);
/* 43 */         return null;
/*    */       } 
/*    */     } 
/*    */     
/* 47 */     if (this.shaderBlur != getBlurFactor()) {
/* 48 */       ((AccessorUtils)this.shader).getListShaders().forEach(shader -> {
/*    */             ShaderUniform blendFactorUniform = shader.func_148043_c().func_147991_a("BlurFactor");
/*    */             
/*    */             if (blendFactorUniform != null) {
/*    */               blendFactorUniform.func_148090_a(getBlurFactor());
/*    */             }
/*    */           });
/*    */       
/* 56 */       this.shaderBlur = getBlurFactor();
/*    */     } 
/*    */     
/* 59 */     return this.shader;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\motionblur\MotionBlurUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */