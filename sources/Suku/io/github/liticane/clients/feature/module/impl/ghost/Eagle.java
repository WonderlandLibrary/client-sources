package io.github.liticane.clients.feature.module.impl.ghost;


import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.util.misc.TimerUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import java.util.Random;
@Module.Info(name = "LegitBridge", category = Module.Category.GHOST)
public class Eagle extends Module {
    private final TimerUtil timerend = new TimerUtil();
    private final TimerUtil timestart = new TimerUtil();

    public BooleanProperty smart = new BooleanProperty("Smart",this,false);
    public NumberProperty startmin = new NumberProperty("Start Min", this, 50, 1, 400, 1);
    public NumberProperty startmax = new NumberProperty("Start Max", this, 50, 1, 400, 1);
    public NumberProperty endmin = new NumberProperty("Min Min", this, 50, 1, 400, 1);
    public NumberProperty endmax = new NumberProperty("Min Max", this, 50, 1, 400, 1);
    private boolean sneaking;
    @Override
    public void onDisable() {
        timerend.reset();
        timestart.reset();
        if (!mc.settings.isKeyDown(mc.settings.keyBindSneak)) {
            mc.settings.keyBindSneak.pressed = false;
        }
    }

    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir
                && mc.player.onGround) {
            if(timestart.hasTimeElapsed(MathHelper.randomFloatClamp(new Random(),(float)startmin.getValue(),(float)startmax.getValue()))) {
                sneaking = true;
                timestart.reset();
                mc.settings.keyBindSneak.pressed = true;
            }
        } else {
            if (sneaking) {
                if (timestart.hasTimeElapsed(MathHelper.randomFloatClamp(new Random(), (float)endmin.getValue(), (float)endmax.getValue()))) {
                    // if(mc.player.ticksExisted % 2 == 0) {
                    mc.settings.keyBindSneak.pressed = false;
                    timerend.reset();
                    sneaking = false;
                }
            }
        }

    };



}
