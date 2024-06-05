package net.minecraft.client.resources.data;

import java.util.Collection;

public class LanguageMetadataSection implements IMetadataSection {
   private final Collection languages;
   private static final String __OBFID = "CL_00001110";

   public Collection getLanguages() {
      return this.languages;
   }

   public LanguageMetadataSection(Collection var1) {
      this.languages = var1;
   }
}
