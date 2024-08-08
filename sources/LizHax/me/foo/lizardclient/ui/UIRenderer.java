/*    */ package me.foo.lizardclient.ui;
/*    */ 
/*    */ import java.awt.Color;

/*    */ import me.foo.lizardclient.Client;
/*    */ import me.foo.lizardclient.module.Module;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.client.FMLClientHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UIRenderer
/*    */   extends Gui
/*    */ {
/* 14 */   private Minecraft mc = FMLClientHandler.instance().getClient();
/*    */   
/* 16 */   public Boolean ghost = Boolean.valueOf(false);
/*    */   
/* 18 */   private Color colour = new Color(255, 0, 0);
/*    */   
/* 20 */   float offset = 0.0F;
/*    */   
/*    */   public void draw() {
/* 23 */     if (!this.ghost.booleanValue()) {
/* 24 */       this.mc.fontRenderer.drawStringWithShadow(String.valueOf(Client.clientName) + " " + Client.clientVersion, 1.0F, 1.0F, 4953600);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 29 */       this.mc.fontRenderer.drawString(Float.toString((float)Math.round(this.mc.player.posX)), 90, 1, 16777215);
/* 30 */       this.mc.fontRenderer.drawString(Float.toString((float)Math.round(this.mc.player.posY)), 90, 11, 16777215);
/* 31 */       this.mc.fontRenderer.drawString(Float.toString((float)Math.round(this.mc.player.posZ)), 90, 21, 16777215);
/*    */ 
/*    */       
/* 34 */       if (Client.moduleManager.getEnabledModules().size() > 0) {
/* 35 */         int i = 0;
/* 36 */         this.colour = Color.getHSBColor(0.0F, 1.0F, 1.0F);
/* 37 */         float b = 1.0F;
/* 38 */         for (Module module : Client.moduleManager.getEnabledModules()) {
/* 39 */           if (i / 20.0F < 1.0F) {
/* 40 */             this.colour = Color.getHSBColor(i / 20.0F + this.offset, 1.0F, b);
/*    */           } else {
/* 42 */             this.colour = Color.getHSBColor(1.0F, 1.0F, b);
/*    */           } 
/* 44 */           this.mc.fontRenderer.drawString(module.name, 1, (i + 3) * 10, Integer.decode(String.format("#%02X%02X%02X", new Object[] { Integer.valueOf(this.colour.getRed()), Integer.valueOf(this.colour.getGreen()), Integer.valueOf(this.colour.getBlue()) })).intValue());
/* 45 */           i++;
/*    */         } 
/*    */         
/* 48 */         this.offset += 0.01F;
/* 49 */         if (this.offset > 1.0F)
/* 50 */           this.offset = 0.0F; 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void initColorList() {}
/*    */ }


/* Location:              C:\Users\Ben\Downloads\Lizard Client.jar!\me\foo\lizardclien\\ui\UIRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */