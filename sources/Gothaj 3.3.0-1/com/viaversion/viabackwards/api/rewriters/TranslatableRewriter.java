package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.qual.Nullable;

public class TranslatableRewriter<C extends ClientboundPacketType> extends ComponentRewriter<C> {
   private static final Map<String, Map<String, String>> TRANSLATABLES = new HashMap<>();
   private final Map<String, String> translatables;

   public static void loadTranslatables() {
      JsonObject jsonObject = VBMappingDataLoader.loadFromDataDir("translation-mappings.json");

      for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
         Map<String, String> versionMappings = new HashMap<>();
         TRANSLATABLES.put(entry.getKey(), versionMappings);

         for (Entry<String, JsonElement> translationEntry : entry.getValue().getAsJsonObject().entrySet()) {
            versionMappings.put(translationEntry.getKey(), translationEntry.getValue().getAsString());
         }
      }
   }

   public TranslatableRewriter(BackwardsProtocol<C, ?, ?, ?> protocol, ComponentRewriter.ReadType type) {
      this(protocol, type, protocol.getClass().getSimpleName().split("To")[1].replace("_", "."));
   }

   public TranslatableRewriter(BackwardsProtocol<C, ?, ?, ?> protocol, ComponentRewriter.ReadType type, String sectionIdentifier) {
      super(protocol, type);
      Map<String, String> translatableMappings = TRANSLATABLES.get(sectionIdentifier);
      if (translatableMappings == null) {
         ViaBackwards.getPlatform().getLogger().warning("Missing " + sectionIdentifier + " translatables!");
         this.translatables = new HashMap<>();
      } else {
         this.translatables = translatableMappings;
      }
   }

   @Override
   protected void handleTranslate(JsonObject root, String translate) {
      String newTranslate = this.mappedTranslationKey(translate);
      if (newTranslate != null) {
         root.addProperty("translate", newTranslate);
      }
   }

   @Nullable
   public String mappedTranslationKey(String translationKey) {
      return this.translatables.get(translationKey);
   }
}
