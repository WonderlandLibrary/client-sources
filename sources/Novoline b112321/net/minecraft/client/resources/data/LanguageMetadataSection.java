package net.minecraft.client.resources.data;

import java.util.Collection;
import net.minecraft.client.resources.data.IMetadataSection;

public class LanguageMetadataSection implements IMetadataSection {
   private final Collection languages;

   public LanguageMetadataSection(Collection var1) {
      this.languages = var1;
   }

   public Collection getLanguages() {
      return this.languages;
   }
}
