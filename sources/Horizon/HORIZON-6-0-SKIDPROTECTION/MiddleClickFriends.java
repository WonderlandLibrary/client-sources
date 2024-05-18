package HORIZON-6-0-SKIDPROTECTION;

import java.util.Objects;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = 0, Â = "MiddleClick Players to Fried/Unfriend them", HorizonCode_Horizon_È = "MiddleClickFriends")
public class MiddleClickFriends extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final MouseClicked ev) {
        if (ev.Ý() != 2 || Objects.isNull(this.Â.áŒŠà.Ø­áŒŠá)) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)this.Â.áŒŠà.Ø­áŒŠá;
        if (!FriendManager.HorizonCode_Horizon_È.containsKey(player.v_())) {
            this.Â.á.Ó(String.valueOf(Horizon.Ø­áŒŠá) + "§7Added §a\"" + player.v_() + "\" §7as Friend");
            FriendManager.HorizonCode_Horizon_È(player.v_(), player.v_());
            return;
        }
        this.Â.á.Ó(String.valueOf(Horizon.Ø­áŒŠá) + "§7Removed §a\"" + player.v_() + "\" §7as Friend");
        FriendManager.HorizonCode_Horizon_È(player.v_());
    }
}
