package digital.rbq.module.implement.Ghost;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.item.ItemSword;
import digital.rbq.event.PreUpdateEvent;
import digital.rbq.event.TickEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.BooleanValue;
import digital.rbq.module.value.FloatValue;
import digital.rbq.utility.DelayTimer;
import digital.rbq.utility.RandomUtils;
import digital.rbq.utility.TimeHelper;

public class AutoClicker extends Module {
    private TimeHelper timer = new TimeHelper();
    private TimeHelper blocktimer = new TimeHelper();
    public static FloatValue mincps = new FloatValue("AutoClicker", "Min CPS", 8, 1.0f, 20.0f, 1.0f);
    public static FloatValue maxcps = new FloatValue("AutoClicker", "Max CPS", 12, 1.0f, 20.0f, 1.0f);
    public static BooleanValue autoblock = new BooleanValue("AutoClicker", "Auto Block", false);

    private int delay;

    public AutoClicker() {
        super("AutoClicker", Category.Ghost, false);
    }

    @Override
    public void onEnable() {
        setDelay();
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(PreUpdateEvent event) {
        if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
            return;
        }
        if (mc.playerController.getCurBlockDamageMP() != 0F) {
            return;
        }
        if (timer.delay(delay) && mc.gameSettings.keyBindAttack.pressed) {
            mc.gameSettings.keyBindAttack.pressed = false;
            // autoblock
            if (autoblock.getValue() && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit.isEntityAlive()){
                if (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword && blocktimer.delay(100)) {
                    mc.thePlayer.getCurrentEquippedItem().useItemRightClick(mc.theWorld, mc.thePlayer);
                    blocktimer.reset();
                }
            }
            mc.setLeftClickCounter(0);
            mc.clickMouse();
            mc.gameSettings.keyBindAttack.pressed = true;
            setDelay();
            timer.reset();
        }
    }

    @EventTarget
    public void onTick(TickEvent event) {
        // 防止最小cps大于最大cps
        if (mincps.getValue() > maxcps.getValue()) {
            mincps.setValue(maxcps.getValue());
        }
    }

    private void setDelay()
    {
        delay = (int) RandomUtils.nextFloat(1000.0F / mincps.getValue(), 1000.0F / maxcps.getValue());
    }
}
