/*  1:   */ 
/*  2:   */ 
/*  3:   */ me.connorm.Nodus.command.NodusCommandManager
/*  4:   */ me.connorm.Nodus.event.other.EventStartup
/*  5:   */ me.connorm.Nodus.file.NodusFileManager
/*  6:   */ me.connorm.Nodus.module.NodusModuleManager
/*  7:   */ me.connorm.Nodus.module.value.NodusValueManager
/*  8:   */ me.connorm.Nodus.relations.NodusRelationsManager
/*  9:   */ me.connorm.Nodus.ui.gui.NodusGuiManager
/* 10:   */ me.connorm.Nodus.ui.gui.theme.nodus.NodusTheme
/* 11:   */ me.connorm.Nodus.ui.tab.NodusTabGuiHandler
/* 12:   */ me.connorm.irc.NodusIRCBot
/* 13:   */ me.connorm.irc.NodusIRCHandler
/* 14:   */ me.connorm.lib.EventManager
/* 15:   */ net.minecraft.client.Minecraft
/* 16:   */ net.minecraft.client.entity.EntityClientPlayerMP
/* 17:   */ net.minecraft.util.ChatComponentText
/* 18:   */ net.minecraft.util.EnumChatFormatting
/* 19:   */ 
/* 20:   */ Nodus
/* 21:   */ 
/* 22:22 */   theNodus = ()
/* 23:   */   CLIENT_NAME = "Nodus 2.0"
/* 24:   */   CLIENT_VERSION = "b11"
/* 25:   */   theMinecraft
/* 26:   */   commandManager
/* 27:   */   guiManager
/* 28:   */   moduleManager
/* 29:   */   fileManager
/* 30:   */   valueManager
/* 31:   */   tabGuiHandler
/* 32:   */   relationsManager
/* 33:   */   ircHandler
/* 34:   */   
/* 35:   */   startNodus
/* 36:   */   
/* 37:48 */     theMinecraft = 
/* 38:   */     
/* 39:50 */     relationsManager = new NodusRelationsManager();
/* 40:   */     
/* 41:52 */     this.moduleManager = new NodusModuleManager();
/* 42:   */     
/* 43:54 */     this.guiManager = new NodusGuiManager();
/* 44:55 */     this.guiManager.setTheme(new NodusTheme());
/* 45:56 */     this.guiManager.setup();
/* 46:   */     
/* 47:58 */     this.fileManager = new NodusFileManager();
/* 48:   */     
/* 49:60 */     this.valueManager = new NodusValueManager();
/* 50:   */     
/* 51:62 */     this.commandManager = new NodusCommandManager();
/* 52:   */     
/* 53:64 */     this.tabGuiHandler = new NodusTabGuiHandler();
/* 54:   */     
/* 55:66 */     NodusIRCBot.runBot();
/* 56:   */     
/* 57:68 */     this.ircHandler = new NodusIRCHandler();
/* 58:   */     
/* 59:70 */     EventManager.call(new EventStartup());
/* 60:   */   }
/* 61:   */   
/* 62:73 */   private int mcVersion = 4;
/* 63:   */   
/* 64:   */   public int getMinecraftVersion()
/* 65:   */   {
/* 66:77 */     return this.mcVersion;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void setMinecraftVersion(int newVersion)
/* 70:   */   {
/* 71:82 */     this.mcVersion = newVersion;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public Minecraft getMinecraft()
/* 75:   */   {
/* 76:87 */     return Minecraft.getMinecraft();
/* 77:   */   }
/* 78:   */   
/* 79:   */   public void addChatMessage(String chatMessage)
/* 80:   */   {
/* 81:92 */     getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[" + EnumChatFormatting.GREEN + "N" + "§r" + "] " + chatMessage));
/* 82:   */   }
/* 83:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.Nodus
 * JD-Core Version:    0.7.0.1
 */