package io.github.liticane.clients.feature.module.impl.ghost;


import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.LClickEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.util.misc.Random;
import io.github.liticane.clients.util.misc.TimerUtil;
import io.github.liticane.clients.util.player.MoveUtil;
import net.minecraft.util.MovingObjectPosition;

@Module.Info(name = "AutoClicker", category = Module.Category.GHOST)
public class AutoClicker extends Module {
    public BooleanProperty leftclick = new BooleanProperty("Left Click",this,false);
    public BooleanProperty rightclick = new BooleanProperty("Right Click",this,false);

    public NumberProperty mincps = new NumberProperty("Min Cps", this, 10, 1, 20, 1);
    public NumberProperty maxcps = new NumberProperty("Max Cps", this, 15, 1, 20, 1);
    private final TimerUtil clickStopWatch = new TimerUtil();
    private final TimerUtil blockTimer = new TimerUtil();
    private final TimerUtil unblockTimer = new TimerUtil();
    private int ticksDown;
    private long nextSwing;
    @SubscribeEvent
    private final EventListener<LClickEvent> onTick = e -> {
        this.setSuffix(mincps.getValue() + " - " + maxcps.getValue());
        //if(blockTimer.hasTimeElapsed(500) && (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) && mc.settings.keyBindAttack.isKeyDown()) {
        //            mc.settings.keyBindUseItem.pressed = true;
        //            blockTimer.reset();
        //        }
        //        if(unblockTimer.hasTimeElapsed(510) && (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) && mc.player.isBlocking()) {
        //            mc.settings.keyBindUseItem.pressed = false;
        //            ChatUtil.display("sd");
        //            unblockTimer.reset();
        //        }
        if (clickStopWatch.finished(this.nextSwing) ||
                (mc.player.hurtTime > 0 && clickStopWatch.finished(this.nextSwing)) && mc.currentScreen == null) {
            final long clicks = (long) (Math.round(Random.nextDouble(this.mincps.getValue(), this.maxcps.getValue())) * 1.5);

            if (mc.settings.keyBindAttack.isKeyDown()) {
                ticksDown++;
            } else {
                ticksDown = 0;
            }

            this.nextSwing = 1000 / clicks;

            if (rightclick.isToggled() && mc.settings.keyBindUseItem.isKeyDown() && !mc.settings.keyBindAttack.isKeyDown()) {
                MoveUtil.sendClick(1, true);

                if (Math.random() > 0.9) {
                    MoveUtil.sendClick(1, true);
                }
            }
            //maybe make a check fo if it isnt eating or something isuingtime
            if (leftclick.isToggled() && ticksDown > 1 && (Math.sin(nextSwing) + 1 > Math.random() || Math.random() > 0.25 || clickStopWatch.finished(4 * 50)) && (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)) {
                MoveUtil.sendClick(0, true);
            }

            this.clickStopWatch.reset();
        }
    };


}
