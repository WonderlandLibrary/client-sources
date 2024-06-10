/*  1:   */ package me.connorm.Nodus.module.core;
/*  2:   */ 
/*  3:   */ import me.connorm.Nodus.Nodus;
/*  4:   */ import me.connorm.Nodus.module.NodusModuleManager;
/*  5:   */ import me.connorm.lib.EventManager;
/*  6:   */ 
/*  7:   */ public class NodusModule
/*  8:   */   implements IModule
/*  9:   */ {
/* 10:   */   private String theName;
/* 11:   */   private Category theCategory;
/* 12:   */   private int theKeyBind;
/* 13:   */   private boolean isToggled;
/* 14:   */   private byte serialID;
/* 15:   */   
/* 16:   */   public NodusModule(String theName, Category theCategory)
/* 17:   */   {
/* 18:19 */     this.theName = theName;
/* 19:20 */     this.theCategory = theCategory;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getName()
/* 23:   */   {
/* 24:26 */     return this.theName;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Category getCategory()
/* 28:   */   {
/* 29:32 */     return this.theCategory;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int getKeyBind()
/* 33:   */   {
/* 34:38 */     return this.theKeyBind;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean isToggled()
/* 38:   */   {
/* 39:44 */     return this.isToggled;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public byte getSerialID()
/* 43:   */   {
/* 44:50 */     return this.serialID;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void setKeyBind(int newKey)
/* 48:   */   {
/* 49:56 */     this.theKeyBind = newKey;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setToggled(boolean shouldToggle)
/* 53:   */   {
/* 54:62 */     onToggle();
/* 55:63 */     if (shouldToggle)
/* 56:   */     {
/* 57:65 */       onEnable();
/* 58:66 */       this.isToggled = true;
/* 59:   */     }
/* 60:   */     else
/* 61:   */     {
/* 62:69 */       onDisable();
/* 63:70 */       this.isToggled = false;
/* 64:   */     }
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void setSerialID(byte ID)
/* 68:   */   {
/* 69:77 */     this.serialID = ID;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void toggleModule()
/* 73:   */   {
/* 74:83 */     setToggled(!this.isToggled);
/* 75:   */   }
/* 76:   */   
/* 77:   */   public void onToggle() {}
/* 78:   */   
/* 79:   */   public void onEnable()
/* 80:   */   {
/* 81:92 */     EventManager.register(Nodus.theNodus.moduleManager.getModuleByID(getSerialID()));
/* 82:   */   }
/* 83:   */   
/* 84:   */   public void onDisable()
/* 85:   */   {
/* 86:98 */     EventManager.unregister(Nodus.theNodus.moduleManager.getModuleByID(getSerialID()));
/* 87:   */   }
/* 88:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.core.NodusModule
 * JD-Core Version:    0.7.0.1
 */