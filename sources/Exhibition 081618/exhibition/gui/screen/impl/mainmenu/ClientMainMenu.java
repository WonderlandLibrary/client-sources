package exhibition.gui.screen.impl.mainmenu;

import exhibition.Client;
import exhibition.gui.screen.PanoramaScreen;
import exhibition.management.SubFolder;
import exhibition.util.security.AuthenticatedUser;
import exhibition.util.security.Crypto;
import java.io.File;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;

public class ClientMainMenu extends PanoramaScreen {
   private static int key = 41;
   private static final GuiVanillaMainMenu menuVanilla = new GuiVanillaMainMenu();
   private static final GuiModdedMainMenu menuModded = new GuiModdedMainMenu();

   public ClientMainMenu() {
	   
   }

   public ClientMainMenu(AuthenticatedUser authUser, String testIfNull) {
	      assert authUser != null;

	      assert !authUser.getDecryptedUsername().equals(null);
	   }

   public void initGui() {
      this.load();
      if (this.getClass().equals(ClientMainMenu.class)) {
         this.display();
      }
   }

   private void load() {
      String file = "";

      try {
         file = FileUtils.readFileToString(this.getFile());
      } catch (IOException var9) {
         return;
      }

      String[] var2 = file.split("\n");
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String line = var2[var4];
         if (line.contains("key")) {
            String[] split = line.split(":");
            if (split.length > 1) {
               try {
                  key = Integer.parseInt(split[1]);
               } catch (NumberFormatException var8) {
                  ;
               }
            }
         }
      }

   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (keyCode == key) {
         this.toggleVanilla();
         this.display();
      }

   }

   private void display() {
      if (Client.isHidden()) {
         Minecraft.getMinecraft().displayGuiScreen(menuVanilla);
      } else {
         Minecraft.getMinecraft().displayGuiScreen(new GuiModdedMainMenu());
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   public void toggleVanilla() {
      Client.setHidden(!Client.isHidden());
      this.save();
   }

   public void save() {
      try {
         FileUtils.write(this.getFile(), "Swap key (Toggles menus):" + key);
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public File getFile() {
      File file = new File(this.getFolder().getAbsolutePath() + File.separator + "MainMenu.txt");
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

      return file;
   }

   public File getFolder() {
      File folder = new File(Client.getDataDir().getAbsolutePath() + File.separator + SubFolder.Other.getFolderName());
      if (!folder.exists()) {
         folder.mkdirs();
      }

      return folder;
   }
}
