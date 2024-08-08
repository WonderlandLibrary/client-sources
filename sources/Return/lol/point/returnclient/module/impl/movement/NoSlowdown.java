package lol.point.returnclient.module.impl.movement;

import lol.point.returnclient.events.impl.player.EventMotion;
import lol.point.returnclient.events.impl.player.EventSlowdown;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.StringSetting;
import lol.point.returnclient.settings.impl.BooleanSetting;
import lol.point.returnclient.settings.impl.NumberSetting;
import lol.point.returnclient.util.minecraft.MoveUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

@ModuleInfo(
        name = "NoSlowdown",
        description = "removes slowdown effects",
        category = Category.MOVEMENT
)
public class NoSlowdown extends Module {

    private final StringSetting mode = new StringSetting("Mode", new String[]{"Vanilla", "Switch", "Polar"});

    private final BooleanSetting swords = new BooleanSetting("Swords", true);
    private final NumberSetting swordForward = new NumberSetting("Sword forward", 1, 0, 1, 2).hideSetting(() -> !swords.value);
    private final NumberSetting swordStrafe = new NumberSetting("Sword strafe", 1, 0, 1, 2).hideSetting(() -> !swords.value);
    private final BooleanSetting food = new BooleanSetting("Food", false);
    private final NumberSetting foodForward = new NumberSetting("Food forward", 1, 0, 1, 2).hideSetting(() -> !food.value);
    private final NumberSetting foodStrafe = new NumberSetting("Food strafe", 1, 0, 1, 2).hideSetting(() -> !food.value);
    private final BooleanSetting bow = new BooleanSetting("Bow", false);
    private final NumberSetting bowForward = new NumberSetting("Bow forward", 1, 0, 1, 2).hideSetting(() -> !bow.value);
    private final NumberSetting bowStrafe = new NumberSetting("Bow strafe", 1, 0, 1, 2).hideSetting(() -> !bow.value);

    public NoSlowdown() {
        addSettings(mode, swords, swordForward, swordStrafe, food, foodForward, foodStrafe, bow, bowForward, bowStrafe);
    }

    private int polarTickCounter = 0;

    public void onDisable() {
        polarTickCounter = 0;
    }

    public String getSuffix() {
        return mode.value;
    }

    @Subscribe
    private final Listener<EventSlowdown> onSlow = new Listener<>(eventSlowdown -> {
        ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
        if (currentItem == null || !mc.thePlayer.isUsingItem() || !MoveUtil.isMoving()) {
            return;
        }

        if (swords.value && currentItem.getItem() instanceof ItemSword) {
            eventSlowdown.forward = swordForward.value.floatValue();
            eventSlowdown.strafe = swordStrafe.value.floatValue();
        }

        if (food.value && currentItem.getItem() instanceof ItemFood) {
            eventSlowdown.forward = foodForward.value.floatValue();
            eventSlowdown.strafe = foodStrafe.value.floatValue();
        }

        if (bow.value && currentItem.getItem() instanceof ItemBow) {
            eventSlowdown.forward = bowForward.value.floatValue();
            eventSlowdown.strafe = bowStrafe.value.floatValue();
        }
    });

    @Subscribe
    private final Listener<EventMotion> onMotion = new Listener<>(eventMotion -> {
        ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
        if (currentItem == null || !mc.thePlayer.isUsingItem() || !MoveUtil.isMoving()) {
            return;
        }

        switch (mode.value) {
            case "Polar" -> {
                polarTickCounter++;
                if (polarTickCounter >= 20) {
                    mc.thePlayer.stopUsingItem();
                    mc.thePlayer.setItemInUse(currentItem, currentItem.getMaxItemUseDuration());
                    polarTickCounter = 0;
                }
            }
            case "Switch" -> {
                if (eventMotion.isPre()) {
                    sendPacket(new C09PacketHeldItemChange((mc.thePlayer.inventory.currentItem + 1) % 9));
                    sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
            }
        }
    });

}
