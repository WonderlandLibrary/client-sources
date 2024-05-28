package arsenic.utils.mixin;

import arsenic.injection.accessor.IMixinEntityPlayerSP;
import arsenic.utils.java.PlayerInfo;
import arsenic.utils.java.UtilityClass;
import net.minecraft.client.entity.EntityPlayerSP;

public class UtilMixinEntityPlayerSP extends UtilityClass {

    public static PlayerInfo getPlayerInfo(EntityPlayerSP entityPlayerSP) {
     IMixinEntityPlayerSP playerSP = (IMixinEntityPlayerSP) entityPlayerSP;
     return new PlayerInfo(playerSP.getLastReportedYaw(), playerSP.getLastReportedPitch(), playerSP.getServerSprintState());
    }

    public static void setPlayerInfo(EntityPlayerSP entityPlayerSP, PlayerInfo playerInfo) {
        IMixinEntityPlayerSP playerSP = (IMixinEntityPlayerSP) entityPlayerSP;
        playerSP.setLastReportedPitch(playerInfo.getLastReportedPitch());
        playerSP.setLastReportedYaw(playerInfo.getLastReportedYaw());
        playerSP.setServerSprintState(playerInfo.isServerSprintState());
    }
}
