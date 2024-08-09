package wtf.shiyeno.modules.impl.util;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CEntityActionPacket.Action;
import net.minecraft.util.text.TextFormatting;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.game.EventKey;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BindSetting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.world.InventoryUtil;

@FunctionAnnotation(
        name = "ElytraSwap",
        type = Type.Util
)
public class ElytraSwap extends Function {
    private BindSetting swapKey = new BindSetting("Кнопка свапа", 0);
    private BindSetting fireworkKey = new BindSetting("Кнопка феерверков", 0);
    private ItemStack oldStack = null;
    boolean startFallFlying;
    private BooleanOption autoFly = new BooleanOption("Авто взлёт", true);
    private BooleanOption autoFireWork = (new BooleanOption("Авто фейерверк", true)).setVisible(() -> {
        return this.autoFly.get();
    });
    private BooleanOption notif = new BooleanOption("Оповещение", true);
    private BooleanOption swapBypass = new BooleanOption("Обход элитры несьемности", true);
    private final TimerUtil timerUtil = new TimerUtil();

    public ElytraSwap() {
        this.addSettings(new Setting[]{this.swapKey, this.fireworkKey, this.notif, this.autoFly, this.autoFireWork, this.swapBypass});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate && this.autoFly.get() && mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == Items.ELYTRA) {
            if (mc.player.isOnGround() && !mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.jump();
                return;
            }

            boolean startFlyElytra = false;
            if (mc.player.fallDistance != 0.0F && !mc.player.isElytraFlying()) {
                mc.player.startFallFlying();
                mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, Action.START_FALL_FLYING));
                if (this.autoFireWork.get()) {
                    startFlyElytra = true;
                }
            }

            if (mc.player.isElytraFlying() && startFlyElytra && this.timerUtil.hasTimeElapsed(500L)) {
                InventoryUtil.inventorySwapClick(Items.FIREWORK_ROCKET, false);
                this.timerUtil.reset();
            }
        }

        if (event instanceof EventKey e) {
            ItemStack itemStack = mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            if (e.key == this.swapKey.getKey()) {
                int elytraSlot = InventoryUtil.getItemSlot(Items.ELYTRA);
                if (elytraSlot == -1) {
                    ClientUtil.sendMesage(TextFormatting.RED + "Не найдена элитра в инвентаре!");
                    return;
                }

                if (this.reasonToEquipElytra(itemStack)) {
                    ItemStack n = mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
                    this.oldStack = n.copy();
                    InventoryUtil.moveItem(elytraSlot, 6, true);
                    if (this.notif.get()) {
                        ClientUtil.sendMesage(TextFormatting.RED + "Свапнул на элитру!");
                    }
                } else if (this.oldStack != null) {
                    int oldStackSlot = InventoryUtil.getItemSlot(this.oldStack.getItem());
                    if (!this.swapBypass.get()) {
                        InventoryUtil.moveItem(oldStackSlot, 6, true);
                    }

                    if (this.swapBypass.get()) {
                        mc.playerController.windowClick(0, oldStackSlot < 9 ? oldStackSlot + 36 : oldStackSlot, 38, ClickType.SWAP, mc.player);
                    }

                    if (this.notif.get()) {
                        ClientUtil.sendMesage(TextFormatting.RED + "Свапнул на нагрудник!");
                    }

                    this.oldStack = null;
                }
            }

            if (e.key == this.fireworkKey.getKey() && itemStack.getItem() == Items.ELYTRA) {
                this.useFirework();
            }
        }
    }

    private void useFirework() {
        if (InventoryUtil.getItemSlot(Items.FIREWORK_ROCKET) == -1) {
            ClientUtil.sendMesage(TextFormatting.RED + "У вас отсутствуют фейерверки!");
        } else {
            InventoryUtil.inventorySwapClick(Items.FIREWORK_ROCKET, false);
        }
    }

    private boolean reasonToEquipElytra(ItemStack stack) {
        return stack.getItem() != Items.ELYTRA;
    }
}