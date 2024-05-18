package optfine;

import net.minecraft.client.model.*;
import net.minecraft.client.entity.*;

public class PlayerConfiguration
{
    private PlayerItemModel[] playerItemModels;
    private boolean initialized;
    
    public void renderPlayerItems(final ModelBiped modelBiped, final AbstractClientPlayer abstractClientPlayer, final float n, final float n2) {
        if (this.initialized) {
            int i = "".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
            while (i < this.playerItemModels.length) {
                this.playerItemModels[i].render(modelBiped, abstractClientPlayer, n, n2);
                ++i;
            }
        }
    }
    
    public void addPlayerItemModel(final PlayerItemModel playerItemModel) {
        this.playerItemModels = (PlayerItemModel[])Config.addObjectToArray(this.playerItemModels, playerItemModel);
    }
    
    public PlayerItemModel[] getPlayerItemModels() {
        return this.playerItemModels;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public PlayerConfiguration() {
        this.playerItemModels = new PlayerItemModel["".length()];
        this.initialized = ("".length() != 0);
    }
    
    public void setInitialized(final boolean initialized) {
        this.initialized = initialized;
    }
    
    public boolean isInitialized() {
        return this.initialized;
    }
}
