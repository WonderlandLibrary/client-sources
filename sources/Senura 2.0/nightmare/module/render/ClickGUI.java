/*    */ package nightmare.module.render;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ 
/*    */ public class ClickGUI
/*    */   extends Module
/*    */ {
/*    */   public ClickGUI() {
/* 15 */     super("ClickGUI", 54, Category.RENDER);
/*    */     
/* 17 */     ArrayList<String> options = new ArrayList<>();
/*    */     
/* 19 */     options.add("Classic");
/* 20 */     options.add("New");
/*    */     
/* 22 */     Nightmare.instance.settingsManager.rSetting(new Setting("Visual", this, "Classic", options));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 27 */     super.onEnable();
/* 28 */     mc.func_147108_a((GuiScreen)Nightmare.instance.clickGUI);
/* 29 */     setToggled(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\render\ClickGUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */