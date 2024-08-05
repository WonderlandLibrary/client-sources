package fr.dog.module.impl.player;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.move.MoveInputEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.NumberProperty;
import fr.dog.util.math.TimeUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;

public class LegitScaffold extends Module {
    public LegitScaffold() {
        super("LegitScaffold", ModuleCategory.PLAYER);
        this.registerProperties(numberProperty,faster);
    }


    private BooleanProperty faster = BooleanProperty.newInstance("Faster", false);
    private NumberProperty numberProperty = NumberProperty.newInstance("Delay", 50f, 50f, 500f, 50f);

    private TimeUtil timerUtil = new TimeUtil();

    @SubscribeEvent
    public void moveInputEvent(MoveInputEvent event){
        if (event.isSneaking()) {
            return;
        }

        if ((mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).down()).getBlock() instanceof BlockAir && mc.thePlayer.onGround)) {
            timerUtil.reset();
        }
        if (event.getForward() < 0 && !timerUtil.finished(numberProperty.getValue().longValue())) {
            event.setSneaking(true);
            if (faster.getValue()) {
                event.setSneakMultiplier(0.34);
            }
        }
    }

}
