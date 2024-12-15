package com.alan.clients.module.impl.player;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.NumberValue;
import com.alan.clients.value.impl.SubMode;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo(aliases = {"module.player.fastbreak.name"}, description = "module.player.fastbreak.description", category = Category.PLAYER)
public final class FastBreak extends Module {
     public final ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Percentage"))
            .add(new SubMode("Ticks"))
            .setDefault("Ticks");

    @Getter
    public final NumberValue speed = new NumberValue("Speed", this, 50, 0, 100, 1, () -> mode.getValue().getName().equals("Ticks"));
    public final NumberValue ticks = new NumberValue("Ticks", this, 1, 1, 100, 1, () -> mode.getValue().getName().equals("Percentage"));
    public final BooleanValue ignoringMiningFatigue = new BooleanValue("Ignore Mining Fatigue", this, false);
    public final BooleanValue equalAirGroundDig = new BooleanValue("Equal Air/Ground Dig", this, true);

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (ignoringMiningFatigue.getValue()) {
            mc.thePlayer.removePotionEffect(Potion.digSlowdown.getId());
        }

        mc.playerController.blockHitDelay = 0;

        double percentageFaster = 0;

        switch (mode.getValue().getName()) {
            case "Percentage":

                percentageFaster = speed.getValue().doubleValue() / 100f;
                if(mc.thePlayer.offGroundTicks == 1 && equalAirGroundDig.getValue()){
                    mc.playerController.curBlockDamageMP = mc.playerController.curBlockDamageMP/5;
                    percentageFaster = 0.8f;
                }

                if (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air && !mc.thePlayer.onGround  && equalAirGroundDig.getValue()){
                    mc.playerController.curBlockDamageMP = mc.playerController.curBlockDamageMP*5;
                    percentageFaster =   percentageFaster-0.8f;
                }
                break;

            case "Ticks":
                if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    BlockPos blockPos = mc.objectMouseOver.getBlockPos();
                    Block block = PlayerUtil.block(blockPos);

                    float blockHardness = block.getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, blockPos);
                    percentageFaster = blockHardness * ticks.getValue().intValue();
                }

                if(mc.thePlayer.offGroundTicks == 1 && equalAirGroundDig.getValue()){
                    mc.playerController.curBlockDamageMP = mc.playerController.curBlockDamageMP/5;
                    percentageFaster = 0.81f;
                }

                if (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air && !mc.thePlayer.onGround  && equalAirGroundDig.getValue()){
                    mc.playerController.curBlockDamageMP = mc.playerController.curBlockDamageMP*5;
                    percentageFaster =   percentageFaster-0.81f;
                }
                break;
        }

        if (mc.playerController.curBlockDamageMP > 1 - percentageFaster && mc.playerController.curBlockDamageMP < 0.99f) {
            mc.playerController.curBlockDamageMP = 0.99f;
        }
    };
}
