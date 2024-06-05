package net.minecraft.realms;

import java.lang.reflect.Constructor;
import net.af_;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.realms.RealmsScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsBridge extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private GuiScreen previousScreen;

   public void switchToRealms(GuiScreen var1) {
      this.previousScreen = var1;

      try {
         Class var2 = af_.a("com.mojang.realmsclient.RealmsMainScreen");
         Constructor var3 = var2.getDeclaredConstructor(new Class[]{RealmsScreen.class});
         var3.setAccessible(true);
         Object var4 = var3.newInstance(new Object[]{this});
         Minecraft.getInstance().displayGuiScreen(((RealmsScreen)var4).getProxy());
      } catch (Exception var5) {
         LOGGER.error("Realms module missing", var5);
      }

   }

   public void init() {
      Minecraft.getInstance().displayGuiScreen(this.previousScreen);
   }
}
