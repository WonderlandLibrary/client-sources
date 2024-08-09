package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.input.KeyboardPressEvent;
import dev.excellent.api.event.impl.input.MouseInputEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.script.ScriptConstructor;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.player.InvUtil;
import dev.excellent.impl.value.impl.KeyValue;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;

@ModuleInfo(name = "FunTime Helper", description = "Помощник для фантайм", category = Category.MISC)
public class FunTimeHelper extends Module {
    public static Singleton<FunTimeHelper> singleton = Singleton.create(() -> Module.link(FunTimeHelper.class));
    private final KeyValue useDesorient = new KeyValue("Исп дизориент", this, -1);
    private final KeyValue useTrapka = new KeyValue("Исп Трапку", this, -1);
    private final KeyValue useDust = new KeyValue("Исп Явн пыль", this, -1);
    private final KeyValue usePlast = new KeyValue("Исп Пласт", this, -1);
    private final ScriptConstructor scriptConstructor = ScriptConstructor.create();
    private final InvUtil.Hand handUtil = new InvUtil.Hand();
    private long delay;

    @Override
    public void onDisable() {
        super.onDisable();
        delay = 0;
    }

    private final Listener<KeyboardPressEvent> onKey = event -> {
        if (event.getKeyCode() == useDesorient.getValue()) {
            useItemFromInventory(ItemType.DESORIENT);
        }
        if (event.getKeyCode() == useTrapka.getValue()) {
            useItemFromInventory(ItemType.TRAP);
        }
        if (event.getKeyCode() == useDust.getValue()) {
            useItemFromInventory(ItemType.DUST);
        }
        if (event.getKeyCode() == usePlast.getValue()) {
            useItemFromInventory(ItemType.PLAST);
        }
    };
    private final Listener<MouseInputEvent> onMouse = event -> {
        if (event.getMouseButton() == useDesorient.getValue()) {
            useItemFromInventory(ItemType.DESORIENT);
        }
        if (event.getMouseButton() == useTrapka.getValue()) {
            useItemFromInventory(ItemType.TRAP);
        }
        if (event.getMouseButton() == useDust.getValue()) {
            useItemFromInventory(ItemType.DUST);
        }
        if (event.getMouseButton() == usePlast.getValue()) {
            useItemFromInventory(ItemType.PLAST);
        }
    };
    private final Listener<PacketEvent> packet = handUtil::onEventPacket;
    private final Listener<UpdateEvent> update = event -> {
        scriptConstructor.update();
        this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
    };

    public void useItemFromInventory(ItemType itemType) {

        scriptConstructor.cleanup().addStep(0, () -> {
            switch (itemType) {
                case DESORIENT -> findAndUseItem("дезориентация", Items.ENDER_EYE);
                case TRAP -> findAndUseItem("трапка", Items.NETHERITE_SCRAP);
                case DUST -> findAndUseItem("явная пыль", Items.SUGAR);
                case PLAST -> findAndUseItem("пласт", Items.DRIED_KELP);
            }
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
        });
    }

    private void findAndUseItem(String name, Item netheriteScrap) {
        int hbSlot = getItemForName(name, true);
        int invSlot = getItemForName(name, false);


        if (invSlot == -1 && hbSlot == -1) {
            return;
        }

        if (!mc.player.getCooldownTracker().hasCooldown(netheriteScrap)) {
            int old = mc.player.inventory.currentItem;

            int slot = findAndTrowItem(hbSlot, invSlot);
            if (slot > 8) {
                mc.playerController.pickItem(slot);
            }
            if (InvUtil.findEmptySlot(true) != -1 && mc.player.inventory.currentItem != old) {
                mc.player.inventory.currentItem = old;
            }
        }
    }

    private int findAndTrowItem(int hbSlot, int invSlot) {
        if (hbSlot != -1) {
            this.handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            mc.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return hbSlot;
        }
        if (invSlot != -1) {
            handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            mc.playerController.pickItem(invSlot);
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return invSlot;
        }
        return -1;
    }

    private int getItemForName(String name, boolean inHotBar) {
        int firstSlot = inHotBar ? 0 : 9;
        int lastSlot = inHotBar ? 9 : 36;
        for (int i = firstSlot; i < lastSlot; i++) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);

            if (itemStack.getItem() instanceof AirItem) {
                continue;
            }

            String displayName = TextFormatting.getTextWithoutFormattingCodes(itemStack.getDisplayName().getString());
            if (displayName != null && displayName.toLowerCase().contains(name)) {
                return i;
            }
        }
        return -1;
    }

    public enum ItemType {
        DESORIENT, TRAP, DUST, PLAST
    }
}
