/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import optifine.Config;
import optifine.PlayerItemModel;

public class PlayerConfiguration {
    private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
    private boolean initialized = false;

    public void renderPlayerItems(ModelBiped modelBiped, AbstractClientPlayer player, float scale, float partialTicks) {
        if (this.initialized) {
            for (int i2 = 0; i2 < this.playerItemModels.length; ++i2) {
                PlayerItemModel model = this.playerItemModels[i2];
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
        this.playerItemModels = (PlayerItemModel[])Config.addObjectToArray(this.playerItemModels, playerItemModel);
    }
}

