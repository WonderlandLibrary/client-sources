package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class RecipeRewriter<C extends ClientboundPacketType> {
   protected final Protocol<C, ?, ?, ?> protocol;
   protected final Map<String, RecipeRewriter.RecipeConsumer> recipeHandlers = new HashMap<>();

   public RecipeRewriter(Protocol<C, ?, ?, ?> protocol) {
      this.protocol = protocol;
      this.recipeHandlers.put("crafting_shapeless", this::handleCraftingShapeless);
      this.recipeHandlers.put("crafting_shaped", this::handleCraftingShaped);
      this.recipeHandlers.put("smelting", this::handleSmelting);
      this.recipeHandlers.put("blasting", this::handleSmelting);
      this.recipeHandlers.put("smoking", this::handleSmelting);
      this.recipeHandlers.put("campfire_cooking", this::handleSmelting);
      this.recipeHandlers.put("stonecutting", this::handleStonecutting);
      this.recipeHandlers.put("smithing", this::handleSmithing);
      this.recipeHandlers.put("smithing_transform", this::handleSmithingTransform);
      this.recipeHandlers.put("smithing_trim", this::handleSmithingTrim);
      this.recipeHandlers.put("crafting_decorated_pot", this::handleSimpleRecipe);
   }

   public void handleRecipeType(PacketWrapper wrapper, String type) throws Exception {
      RecipeRewriter.RecipeConsumer handler = this.recipeHandlers.get(type);
      if (handler != null) {
         handler.accept(wrapper);
      }
   }

   public void register(C packetType) {
      this.protocol.registerClientbound(packetType, wrapper -> {
         int size = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < size; i++) {
            String type = wrapper.passthrough(Type.STRING);
            wrapper.passthrough(Type.STRING);
            this.handleRecipeType(wrapper, Key.stripMinecraftNamespace(type));
         }
      });
   }

   public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
      int ingredientsNo = wrapper.passthrough(Type.VAR_INT) * wrapper.passthrough(Type.VAR_INT);
      wrapper.passthrough(Type.STRING);

      for (int i = 0; i < ingredientsNo; i++) {
         this.handleIngredient(wrapper);
      }

      this.rewrite(wrapper.passthrough(this.itemType()));
   }

   public void handleCraftingShapeless(PacketWrapper wrapper) throws Exception {
      wrapper.passthrough(Type.STRING);
      this.handleIngredients(wrapper);
      this.rewrite(wrapper.passthrough(this.itemType()));
   }

   public void handleSmelting(PacketWrapper wrapper) throws Exception {
      wrapper.passthrough(Type.STRING);
      this.handleIngredient(wrapper);
      this.rewrite(wrapper.passthrough(this.itemType()));
      wrapper.passthrough(Type.FLOAT);
      wrapper.passthrough(Type.VAR_INT);
   }

   public void handleStonecutting(PacketWrapper wrapper) throws Exception {
      wrapper.passthrough(Type.STRING);
      this.handleIngredient(wrapper);
      this.rewrite(wrapper.passthrough(this.itemType()));
   }

   public void handleSmithing(PacketWrapper wrapper) throws Exception {
      this.handleIngredient(wrapper);
      this.handleIngredient(wrapper);
      this.rewrite(wrapper.passthrough(this.itemType()));
   }

   public void handleSimpleRecipe(PacketWrapper wrapper) throws Exception {
      wrapper.passthrough(Type.VAR_INT);
   }

   public void handleSmithingTransform(PacketWrapper wrapper) throws Exception {
      this.handleIngredient(wrapper);
      this.handleIngredient(wrapper);
      this.handleIngredient(wrapper);
      this.rewrite(wrapper.passthrough(this.itemType()));
   }

   public void handleSmithingTrim(PacketWrapper wrapper) throws Exception {
      this.handleIngredient(wrapper);
      this.handleIngredient(wrapper);
      this.handleIngredient(wrapper);
   }

   protected void rewrite(@Nullable Item item) {
      if (this.protocol.getItemRewriter() != null) {
         this.protocol.getItemRewriter().handleItemToClient(item);
      }
   }

   protected void handleIngredient(PacketWrapper wrapper) throws Exception {
      Item[] items = wrapper.passthrough(this.itemArrayType());

      for (Item item : items) {
         this.rewrite(item);
      }
   }

   protected void handleIngredients(PacketWrapper wrapper) throws Exception {
      int ingredients = wrapper.passthrough(Type.VAR_INT);

      for (int i = 0; i < ingredients; i++) {
         this.handleIngredient(wrapper);
      }
   }

   protected Type<Item> itemType() {
      return Type.ITEM1_13_2;
   }

   protected Type<Item[]> itemArrayType() {
      return Type.ITEM1_13_2_ARRAY;
   }

   @FunctionalInterface
   public interface RecipeConsumer {
      void accept(PacketWrapper var1) throws Exception;
   }
}
