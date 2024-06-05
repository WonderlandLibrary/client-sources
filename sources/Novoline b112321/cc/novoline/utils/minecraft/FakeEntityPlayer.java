package cc.novoline.utils.minecraft;

import cc.novoline.utils.minecraft.FakeWorld;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FakeEntityPlayer extends EntityOtherPlayerMP {
   private static final List ITEM_STACKS = Arrays.asList(new ItemStack[]{new ItemStack(Items.bow), new ItemStack(Items.iron_sword), new ItemStack(Items.wooden_sword), new ItemStack(Items.stone_pickaxe), new ItemStack(Items.diamond_pickaxe), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.red_flower)});
   private final ResourceLocation locationSkin;
   private final JsonParser jsonParser = new JsonParser();
   private String skinType;

   public FakeEntityPlayer(@NotNull GameProfile var1, @Nullable ResourceLocation var2) {
      super(new FakeWorld(), var1);
      FakeEntityPlayer var10000 = this;

      label24: {
         try {
            ((Collection)var10000.gameProfile.getProperties().asMap().get("textures")).stream().findFirst().map(this::lambda$new$0).ifPresent(this::lambda$new$1);
         } catch (Throwable var4) {
            if(this.skinType == null) {
               this.skinType = "slim";
            }
            break label24;
         }

         if(this.skinType == null) {
            this.skinType = "slim";
         }
      }

      this.locationSkin = var2;
      this.setCurrentItemOrArmor(0, (ItemStack)ITEM_STACKS.get(ThreadLocalRandom.current().nextInt(ITEM_STACKS.size())));
   }

   public String getSkinType() {
      return this.skinType;
   }

   @Nullable
   public ResourceLocation getLocationSkin() {
      return this.locationSkin;
   }

   private void lambda$new$1(JsonObject var1) {
      String[] var2 = FakeWorld.b();
      this.skinType = var1.has("metadata")?var1.getAsJsonObject("metadata").get("model").getAsString():"default";
   }

   private JsonObject lambda$new$0(Property var1) {
      return this.jsonParser.parse(new String(Base64.getDecoder().decode(var1.getValue()))).getAsJsonObject().getAsJsonObject("textures").getAsJsonObject("SKIN");
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
