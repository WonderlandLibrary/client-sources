package net.minecraft.client.resources;

import net.minecraft.client.gui.GuiScreenResourcePacks;

public class ResourcePackListEntryFound extends ResourcePackListEntry {
   private static final String __OBFID = "CL_00000823";
   private final ResourcePackRepository.Entry field_148319_c;

   protected String func_148312_b() {
      return this.field_148319_c.getResourcePackName();
   }

   public ResourcePackListEntryFound(GuiScreenResourcePacks var1, ResourcePackRepository.Entry var2) {
      super(var1);
      this.field_148319_c = var2;
   }

   protected void func_148313_c() {
      this.field_148319_c.bindTexturePackIcon(this.field_148317_a.getTextureManager());
   }

   public ResourcePackRepository.Entry func_148318_i() {
      return this.field_148319_c;
   }

   protected String func_148311_a() {
      return this.field_148319_c.getTexturePackDescription();
   }
}
