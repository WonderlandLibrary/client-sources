package pw.latematt.xiv.mod.mods.world;

import net.minecraft.item.ItemBlock;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

/**
 * @author Rederpz
 */
public class Tower extends Mod implements Listener<MotionUpdateEvent> {
    public Tower() {
        super("Tower", ModType.WORLD, Keyboard.KEY_NONE, 0xFF0099CC);
    }

    @Override
    public void onEventCalled(MotionUpdateEvent event) {
        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock && event.getCurrentState() == MotionUpdateEvent.State.POST) {
            if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mc.thePlayer.onGround && mc.gameSettings.keyBindUseItem.getIsKeyPressed()) {
                if (mc.thePlayer.rotationPitch > 40) {
                    mc.thePlayer.func_174826_a(mc.thePlayer.getEntityBoundingBox().offset(0.0F, 1.15F, 0.0F));
                }
            }
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}
