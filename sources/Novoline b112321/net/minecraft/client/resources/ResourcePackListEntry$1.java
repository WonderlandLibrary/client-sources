package net.minecraft.client.resources;

import java.util.List;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.ResourcePackListEntry;

class ResourcePackListEntry$1 implements GuiYesNoCallback {
   final ResourcePackListEntry this$0;

   ResourcePackListEntry$1(ResourcePackListEntry var1) {
      this.this$0 = var1;
   }

   public void confirmClicked(boolean var1, int var2) {
      List var3 = this.this$0.resourcePacksGUI.getListContaining(this.this$0);
      this.this$0.mc.displayGuiScreen(this.this$0.resourcePacksGUI);
      var3.remove(this.this$0);
      this.this$0.resourcePacksGUI.getSelectedResourcePacks().add(0, this.this$0);
   }
}
