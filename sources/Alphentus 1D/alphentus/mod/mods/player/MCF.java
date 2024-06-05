package alphentus.mod.mods.player;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import com.darkmagician6.eventapi.EventTarget;
import org.lwjgl.input.Mouse;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class MCF extends Mod {

    public int coolDown = 0;

    public MCF() {
        super("MCF", 0, true, ModCategory.PLAYER);
    }

    @EventTarget
    public void event(Event event) {
        if (!getState())
            return;
        if (event.getType() != Type.TICKUPDATE)
            return;

        if (mc.objectMouseOver != null && Mouse.hasWheel() && Mouse.isButtonDown(2) && !(coolDown > 1)) {
            coolDown = 500;
            if (!Init.getInstance().friendSystem.getFriends().contains(mc.objectMouseOver.entityHit.getName())) {
                Init.getInstance().friendSystem.addFriend(mc.objectMouseOver.entityHit.getName());
            } else {
                Init.getInstance().friendSystem.removeFriend(mc.objectMouseOver.entityHit.getName());
            }
        }

        if (coolDown > 0)
            coolDown -= 10;

    }

}
