/*    */ package nightmare.mixin;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
/*    */ import org.spongepowered.asm.launch.MixinBootstrap;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.Mixins;
/*    */ 
/*    */ @MCVersion("1.8.9")
/*    */ public class MixinLoader
/*    */   implements IFMLLoadingPlugin
/*    */ {
/*    */   public MixinLoader() {
/* 15 */     MixinBootstrap.init();
/* 16 */     Mixins.addConfiguration("mixins.nightmare.json");
/* 17 */     MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getASMTransformerClass() {
/* 22 */     return new String[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModContainerClass() {
/* 27 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSetupClass() {
/* 32 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void injectData(Map<String, Object> data) {}
/*    */ 
/*    */   
/*    */   public String getAccessTransformerClass() {
/* 40 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\MixinLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */