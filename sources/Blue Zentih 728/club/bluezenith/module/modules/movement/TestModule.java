package club.bluezenith.module.modules.movement;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.ExtendedModeValue;
import club.bluezenith.util.client.Pair;
import club.bluezenith.util.player.MovementUtil;
import net.minecraft.util.ChatComponentText;

//A dummy module that shows how to implement modes with ExtendedModeValue.
public class TestModule extends Module {

    private final ExtendedModeValue mode = new ExtendedModeValue(this, "Mode",
                                                  new Pair<>("Watchdog", new WatchdogMode()),
                                                  new Pair<>("Ground", new GroundMode())).setIndex(1);

    public TestModule() {
        super("Test", ModuleCategory.MOVEMENT);
        setExtendedModeValue(mode);
    }

    private static final class WatchdogMode implements ExtendedModeValue.Mode {
        @Listener
        public void onUpdatePlayer(UpdatePlayerEvent event) {
            if(!player.onGround) return;

            final float pre = player.jumpMovementFactor;

            player.jumpMovementFactor *= 1.3;
            player.jump();

            player.jumpMovementFactor = pre;
        }

        @Override
        public void onDisable(Module module) {
            mc.thePlayer.addChatMessage(new ChatComponentText("Module disabled"));
        }

        @Override
        public void onEnable(Module module) {
            mc.thePlayer.addChatMessage(new ChatComponentText("Module enabled"));
        }
    }

    private static final class GroundMode implements ExtendedModeValue.Mode {
        @Listener
        public void onUpdatePlayer(MoveEvent event) {
            if(player.onGround && MovementUtil.areMovementKeysPressed())
                MovementUtil.setSpeed(0.5F, event);
        }
    }
}
