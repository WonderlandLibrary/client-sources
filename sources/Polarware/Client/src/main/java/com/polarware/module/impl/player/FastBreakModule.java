package com.polarware.module.impl.player;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.impl.ModeValue;
import com.polarware.value.impl.NumberValue;
import com.polarware.value.impl.SubMode;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo(name = "module.player.fastbreak.name", description = "module.player.fastbreak.description", category = Category.PLAYER)
public final class FastBreakModule extends Module {
    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Percentage"))
            .add(new SubMode("Ticks"))
            .setDefault("Ticks");

    private final NumberValue speed = new NumberValue("Speed", this, 50, 0, 100, 1,  () -> mode.getValue().getName().equals("Ticks"));
    private final NumberValue ticks = new NumberValue("Ticks", this, 1, 1, 100, 1, () -> mode.getValue().getName().equals("Percentage"));

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        mc.playerController.blockHitDelay = 0;

        double percentageFaster = 0;

        switch (mode.getValue().getName()) {
            case "Percentage":
                percentageFaster = speed.getValue().doubleValue() / 100f;
                break;

            case "Ticks":
                if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                    Block block = PlayerUtil.block(blockPos);

                    float blockHardness = block.getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, blockPos);
                    percentageFaster = blockHardness * ticks.getValue().intValue();
                }
                break;
        }

        if (mc.playerController.curBlockDamageMP > 1 - percentageFaster) {
            mc.playerController.curBlockDamageMP = 1;
        }
    };
}
