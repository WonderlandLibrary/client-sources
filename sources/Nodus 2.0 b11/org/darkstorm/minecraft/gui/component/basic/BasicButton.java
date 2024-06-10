/*  1:   */ package org.darkstorm.minecraft.gui.component.basic;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.module.core.NodusModule;
/*  4:   */ import org.darkstorm.minecraft.gui.component.AbstractComponent;
/*  5:   */ import org.darkstorm.minecraft.gui.component.Button;
/*  6:   */ import org.darkstorm.minecraft.gui.component.ButtonGroup;
/*  7:   */ import org.darkstorm.minecraft.gui.listener.ButtonListener;
/*  8:   */ import org.darkstorm.minecraft.gui.listener.ComponentListener;
/*  9:   */ 
/* 10:   */ public class BasicButton
/* 11:   */   extends AbstractComponent
/* 12:   */   implements Button
/* 13:   */ {
/* 14:10 */   protected String text = "";
/* 15:   */   protected ButtonGroup group;
/* 16:   */   protected NodusModule theModule;
/* 17:   */   
/* 18:   */   public BasicButton() {}
/* 19:   */   
/* 20:   */   public BasicButton(String text)
/* 21:   */   {
/* 22:19 */     this.text = text;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getText()
/* 26:   */   {
/* 27:24 */     return this.text;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setText(String text)
/* 31:   */   {
/* 32:29 */     this.text = text;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setModule(NodusModule theModule)
/* 36:   */   {
/* 37:34 */     this.theModule = theModule;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public NodusModule getModule()
/* 41:   */   {
/* 42:40 */     return this.theModule;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void press()
/* 46:   */   {
/* 47:46 */     for (ComponentListener listener : getListeners()) {
/* 48:47 */       ((ButtonListener)listener).onButtonPress(this);
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void addButtonListener(ButtonListener listener)
/* 53:   */   {
/* 54:52 */     addListener(listener);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void removeButtonListener(ButtonListener listener)
/* 58:   */   {
/* 59:57 */     removeListener(listener);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public ButtonGroup getGroup()
/* 63:   */   {
/* 64:62 */     return this.group;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void setGroup(ButtonGroup group)
/* 68:   */   {
/* 69:67 */     this.group = group;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public String getCommand()
/* 73:   */   {
/* 74:73 */     return "toggle " + getText().toLowerCase();
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.darkstorm.minecraft.gui.component.basic.BasicButton
 * JD-Core Version:    0.7.0.1
 */