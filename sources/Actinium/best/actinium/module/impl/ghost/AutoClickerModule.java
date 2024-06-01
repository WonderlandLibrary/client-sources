package best.actinium.module.impl.ghost;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.TickEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.IAccess;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.math.RandomUtil;
import best.actinium.util.player.PlayerUtil;
import best.actinium.util.render.ChatUtil;
import best.actinium.Actinium;
import best.actinium.module.impl.world.NoClickDelay;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.MovingObjectPosition;
import org.lwjglx.input.Mouse;

@ModuleInfo(
        name = "Auto Clicker",
        description = "Automaticly Clicks(i think)",
        category = ModuleCategory.GHOST
)
public class AutoClickerModule extends Module {
    public NumberProperty minCps = new NumberProperty("Min CPS", this, 1, 10, 20, 1);
    public NumberProperty maxCps = new NumberProperty("Max CPS", this, 1, 10, 20, 1);
    private BooleanProperty guiClicker = new BooleanProperty("Work With Gui",this,false);
    private BooleanProperty leftClick = new BooleanProperty("Left Click",this,false);
    private BooleanProperty rightClick = new BooleanProperty("Right Click",this,false);
    private final TimerUtil downTime = new TimerUtil();
    private int downTicks, attackTicks, mouseDownTicks;
    private long predictionSwing;

    @Callback
    public void onTick(TickEvent event) {
        if(!Actinium.INSTANCE.getModuleManager().get(NoClickDelay.class).isEnabled()) {
            return;
        }

        this.attackTicks++;
        HitSelectModule hitSelect = Actinium.INSTANCE.getModuleManager().get(HitSelectModule.class);
        if (downTime.finished(this.predictionSwing) && (!hitSelect.isEnabled() || ((hitSelect.isEnabled() && attackTicks >= 10) ||
                (IAccess.mc.thePlayer.hurtTime > 0 && downTime.finished(this.predictionSwing)))) && IAccess.mc.currentScreen == null) {
            final long clicks = (long) (Math.round(RandomUtil.getAdvancedRandom(this.minCps.getValue().intValue(), this.maxCps.getValue().intValue())) * 1.5);

            if (IAccess.mc.gameSettings.keyBindAttack.isKeyDown()) {
                downTicks++;
            } else {
                downTicks = 0;
            }

            this.predictionSwing = 1000 / clicks;

            if (rightClick.isEnabled() && IAccess.mc.gameSettings.keyBindUseItem.isKeyDown() && !IAccess.mc.gameSettings.keyBindAttack.isKeyDown()) {
                PlayerUtil.sendClick(1, true);

                if (Math.random() > 0.9) {
                    PlayerUtil.sendClick(1, true);
                }
            }

            if (leftClick.isEnabled() && downTicks > 1 && (Math.sin(predictionSwing) + 1 > Math.random() || Math.random() > 0.25 || downTime.finished(4 * 50)) && !IAccess.mc.gameSettings.keyBindUseItem.isKeyDown() && (IAccess.mc.objectMouseOver == null || IAccess.mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)) {
                PlayerUtil.sendClick(0, true);
            }

            this.downTime.reset();
        }

        if (mc.currentScreen instanceof GuiContainer && guiClicker.isEnabled()) {
            GuiContainer container = ((GuiContainer) mc.currentScreen);

            final int i = Mouse.getEventX() * container.width / this.mc.displayWidth;
            final int j = container.height - Mouse.getEventY() * container.height / this.mc.displayHeight - 1;

            try {
                if (Mouse.isButtonDown(0)) {
                    mouseDownTicks++;
                    if (mouseDownTicks > 2 && Math.random() > 0.1) container.mouseClicked(i, j, 0);
                } else if (Mouse.isButtonDown(1)) {
                    mouseDownTicks++;
                    if (mouseDownTicks > 2 && Math.random() > 0.1) container.mouseClicked(i, j, 1);
                } else {
                    mouseDownTicks = 0;
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
