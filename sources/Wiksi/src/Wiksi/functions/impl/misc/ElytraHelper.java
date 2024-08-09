package src.Wiksi.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventKey;
import src.Wiksi.events.EventPacket;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.BindSetting;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.utils.math.StopWatch;
import src.Wiksi.utils.player.InventoryUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import src.Wiksi.utils.TimerUtil;
@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "ElytraHelper", type = Category.Misc)
public class ElytraHelper extends Function {

    private final BindSetting swapChestKey = new BindSetting("Бинд свапа", -1);
    private final BindSetting fireWorkKey = new BindSetting("Бинд фееров", -1);
    private final BooleanSetting autoFly = new BooleanSetting("Авто-взлёт", true);
    private final BooleanSetting considerGrim = new BooleanSetting("Учитывать Grim", false);
    private final InventoryUtil.Hand handUtil = new InventoryUtil.Hand();
    private ItemStack currentStack;
    public static StopWatch stopWatch = new StopWatch();
    private long delay;
    private boolean fireworkUsed;
    public StopWatch wait;
    private final TimerUtil timerUtil;

    public ElytraHelper() {
        this.currentStack = ItemStack.EMPTY;
        this.wait = new StopWatch();
        this.timerUtil = new TimerUtil();
        this.addSettings(new Setting[]{this.swapChestKey, this.fireWorkKey, this.autoFly, this.considerGrim});
    }

    @Subscribe
    private void onEventKey(EventKey e) {
        if (e.getKey() == (Integer)this.swapChestKey.get() && stopWatch.isReached(100L)) {
            this.timerUtil.reset();
            if ((Boolean)this.considerGrim.get()) {
                if (!this.wait.isReached(400L)) {
                    this.stopMovement();
                    return;
                }

                mc.player.setMotion(0.0, mc.player.getMotion().y, 0.0);
                this.changeChestPlate(this.currentStack);
                stopWatch.reset();
                this.wait.reset();
            } else {
                this.changeChestPlate(this.currentStack);
                stopWatch.reset();
            }
        }

        if (e.getKey() == (Integer)this.fireWorkKey.get() && stopWatch.isReached(200L)) {
            this.fireworkUsed = true;
        }

    }

    private void stopMovement() {
        KeyBinding[] pressedKeys = new KeyBinding[]{mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump, mc.gameSettings.keyBindSprint};
        KeyBinding[] var2 = pressedKeys;
        int var3 = pressedKeys.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            KeyBinding keyBinding = var2[var4];
            keyBinding.setPressed(false);
            mc.player.setMotion(0.4000000059604645, 0.0, 0.4000000059604645);
        }

    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        this.currentStack = mc.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        if ((Boolean)this.autoFly.get() && this.currentStack.getItem() == Items.ELYTRA) {
            if (mc.player.isOnGround()) {
                mc.player.jump();
            } else if (ElytraItem.isUsable(this.currentStack) && !mc.player.isElytraFlying()) {
                mc.player.startFallFlying();
                mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
            }
        }

        if (this.fireworkUsed) {
            int hbSlot = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.FIREWORK_ROCKET, true);
            int invSlot = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.FIREWORK_ROCKET, false);
            if (invSlot == -1 && hbSlot == -1) {
                this.print("Феерверки не найдены!");
                this.fireworkUsed = false;
                return;
            }

            if (!mc.player.getCooldownTracker().hasCooldown(Items.FIREWORK_ROCKET)) {
                int slot = this.findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    mc.playerController.pickItem(slot);
                }
            }

            this.fireworkUsed = false;
        }

        this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
    }

    @Subscribe
    private void onPacket(EventPacket e) {
        this.handUtil.onEventPacket(e);
    }

    private int findAndTrowItem(int hbSlot, int invSlot) {
        if (hbSlot != -1) {
            this.handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            if (hbSlot != mc.player.inventory.currentItem) {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
            }

            mc.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            if (hbSlot != mc.player.inventory.currentItem) {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
            }

            mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return hbSlot;
        } else if (invSlot != -1) {
            this.handUtil.setOriginalSlot(mc.player.inventory.currentItem);
            mc.playerController.pickItem(invSlot);
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return invSlot;
        } else {
            return -1;
        }
    }

    private void changeChestPlate(ItemStack stack) {
        if (mc.currentScreen == null) {
            int armorSlot;
            if (stack.getItem() != Items.ELYTRA) {
                armorSlot = this.getItemSlot(Items.ELYTRA);
                if (armorSlot >= 0) {
                    InventoryUtil.moveItem(armorSlot, 6);
                    return;
                }

                this.print("Элитра не найдена!");
            }

            armorSlot = this.getChestPlateSlot();
            if (armorSlot >= 0) {
                InventoryUtil.moveItem(armorSlot, 6);
            } else {
                this.print("Нагрудник не найден!");
            }

        }
    }

    private int getChestPlateSlot() {
        Item[] items = new Item[]{Items.NETHERITE_CHESTPLATE, Items.DIAMOND_CHESTPLATE};
        Item[] var2 = items;
        int var3 = items.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Item item = var2[var4];

            for(int i = 0; i < 36; ++i) {
                Item stack = mc.player.inventory.getStackInSlot(i).getItem();
                if (stack == item) {
                    if (i < 9) {
                        i += 36;
                    }

                    return i;
                }
            }
        }

        return -1;
    }

    public void onDisable() {
        stopWatch.reset();
        super.onDisable();
    }

    private int getItemSlot(Item input) {
        int slot = -1;

        for(int i = 0; i < 36; ++i) {
            ItemStack s = mc.player.inventory.getStackInSlot(i);
            if (s.getItem() == input) {
                slot = i;
                break;
            }
        }

        if (slot < 9 && slot != -1) {
            slot += 36;
        }

        return slot;
    }
}
