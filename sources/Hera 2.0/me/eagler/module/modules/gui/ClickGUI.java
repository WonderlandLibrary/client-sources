/*    */ package me.eagler.module.modules.gui;
/*    */ 
/*    */ import me.eagler.Client;
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClickGUI
/*    */   extends Module
/*    */ {
/*    */   public ClickGUI() {
/* 15 */     super("ClickGUI", "ClickGUI", 54, Category.Gui);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 20 */     this.mc.displayGuiScreen((GuiScreen)Client.instance.getClickGui());
/* 21 */     setEnabled(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\gui\ClickGUI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */