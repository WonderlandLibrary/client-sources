package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.NumberSetting;

@ModuleMetaData(name = "Fast Break", description = "Breaks blocks faster than normal", category = ModuleCategoryEnum.PLAYER)
public final class FastBreakModule extends AbstractModule {
    private final ModeSetting<FastBreakMode> mode =  new ModeSetting<>("Mode",FastBreakMode.INSTANT);
    private final NumberSetting<Double> speed = new NumberSetting<>("Speed",5D,1D,10D,0.05D);

    public FastBreakModule() {
        this.registerSettings(mode,speed);
    }

    private int ticks;

    @Override
    protected void onEnable() {
        ticks = 0;
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event -> {
        this.setSuffix(mode.getValue().getName());

        final MovingObjectPosition objectPosition = mc.objectMouseOver;

        if (objectPosition == null)
            return;

        /*
        if (!objectPosition.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK))
            return;

         */

        final BlockPos blockPos = objectPosition.getBlockPos();
        final Block block = mc.theWorld.getBlockState(blockPos).getBlock();
        final float hardness = block.getPlayerRelativeBlockHardness(mc.thePlayer,mc.theWorld,blockPos);

        switch (mode.getValue()) {
            case INSTANT:
                if (event.getEventType() != EventTypeEnum.POST)
                    return;

                if(mc.playerController.curBlockDamageMP < 1)
                    mc.playerController.curBlockDamageMP += hardness * 100 - hardness;
                return;
            case CUSTOM:
                if (event.getEventType() != EventTypeEnum.POST)
                    return;

                if(mc.playerController.curBlockDamageMP < 1)
                    mc.playerController.curBlockDamageMP += hardness * speed.getValue().floatValue() - hardness;

                return;
            case WATCHDOG:
                if (event.getEventType() != EventTypeEnum.PRE)
                    return;

                if (Double.isNaN(mc.playerController.curBlockDamageMP) || mc.playerController.curBlockDamageMP > 1) {
                    ticks = 0;
                    return;
                }
                
                ticks++;

                if (ticks > 2) {
                    if(mc.playerController.curBlockDamageMP < 1)
                        mc.playerController.curBlockDamageMP += hardness * speed.getValue().floatValue() - hardness;
                }
                return;
        }
    };

    enum FastBreakMode implements IMode {
        INSTANT("Instant"),
        CUSTOM("Custom"),
        WATCHDOG("Watchdog");

        private final String name;

        FastBreakMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
