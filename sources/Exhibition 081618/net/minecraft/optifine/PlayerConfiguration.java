package net.minecraft.optifine;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

public class PlayerConfiguration {
   private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
   private boolean initialized = false;

   public void renderPlayerItems(ModelBiped modelBiped, AbstractClientPlayer player, float scale, float partialTicks) {
      if (this.initialized) {
         PlayerItemModel[] var5 = this.playerItemModels;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            PlayerItemModel model = var5[var7];
            model.render(modelBiped, player, scale, partialTicks);
         }
      }

   }

   public boolean isInitialized() {
      return this.initialized;
   }

   public void setInitialized(boolean initialized) {
      this.initialized = initialized;
   }

   public PlayerItemModel[] getPlayerItemModels() {
      return this.playerItemModels;
   }

   public void addPlayerItemModel(PlayerItemModel playerItemModel) {
      this.playerItemModels = (PlayerItemModel[])((PlayerItemModel[])Config.addObjectToArray(this.playerItemModels, playerItemModel));
   }
}
