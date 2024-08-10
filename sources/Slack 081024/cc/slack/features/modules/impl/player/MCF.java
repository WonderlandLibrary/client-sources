package cc.slack.features.modules.impl.player;

import cc.slack.start.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.utils.other.PrintUtil;
import cc.slack.utils.other.TimeUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

@ModuleInfo(
        name = "MCF",
        category = Category.PLAYER
)
public class MCF extends Module {

    private final TimeUtil timer = new TimeUtil();
    
    
    @Listen
    public void onUpdate (UpdateEvent event) {
        if(timer.hasReached(500) && Mouse.isButtonDown(2) && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            Entity entity = mc.objectMouseOver.entityHit;
            if(Slack.getInstance().getFriendManager().isFriend(entity)) {
                Slack.getInstance().getFriendManager().removeFriend(entity.getCommandSenderName());
                PrintUtil.message("Removed §a" + entity.getCommandSenderName() + " §f as friend");
            } else {
                Slack.getInstance().getFriendManager().addFriend(entity.getCommandSenderName());
                PrintUtil.message("Added §a" + entity.getCommandSenderName() + "§f as friend");
            }
            timer.reset();
        }
    }


}
