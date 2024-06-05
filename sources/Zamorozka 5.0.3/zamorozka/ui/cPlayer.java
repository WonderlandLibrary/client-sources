package zamorozka.ui;

import net.minecraft.entity.player.EntityPlayer;

public final class cPlayer {


    public static void copyPlayerModel(EntityPlayer from, EntityPlayer to) {
        to.getDataManager().set(EntityPlayer.PLAYER_MODEL_FLAG,
                from.getDataManager().get(EntityPlayer.PLAYER_MODEL_FLAG));
    }
}
