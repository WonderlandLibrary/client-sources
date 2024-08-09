package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventKey;
import im.expensive.events.EventPacket;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.Setting;
import im.expensive.functions.settings.impl.BindSetting;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.utils.player.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;

@FunctionRegister(
        name = "HWHelper",
        type = Category.Miscellaneous
)
public class HWHelper extends Function {
    private final ModeListSetting mode = new ModeListSetting("Тип", new BooleanSetting[]{new BooleanSetting("Использование по бинду", true), new BooleanSetting("Закрывать меню", true)});
    private final BindSetting fireKey = (new BindSetting("Кнопка взрывной штучки", -1)).setVisible(() -> {
        return (Boolean)this.mode.getValueByName("Использование по бинду").get();
    });
    private final BindSetting stanKey = (new BindSetting("Кнопка стана", -1)).setVisible(() -> {
        return (Boolean)this.mode.getValueByName("Использование по бинду").get();
    });
    private final BindSetting trapKey = (new BindSetting("Кнопка взрывной трапки", -1)).setVisible(() -> {
        return (Boolean)this.mode.getValueByName("Использование по бинду").get();
    });
    InventoryUtil.Hand handUtil = new InventoryUtil.Hand();
    long delay;
    boolean FireThrow;
    boolean trapThrow;
    boolean StanThrow;

    public HWHelper() {
        this.addSettings(new Setting[]{this.fireKey, this.trapKey, this.stanKey});
    }

    @Subscribe
    private void onKey(EventKey e) {
        if (e.getKey() == (Integer)this.fireKey.get()) {
            this.FireThrow = true;
        }

        if (e.getKey() == (Integer)this.stanKey.get()) {
            this.StanThrow = true;
        }

        if (e.getKey() == (Integer)this.trapKey.get()) {
            this.trapThrow = true;
        }

    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        int old;
        int invSlot;
        int hbSlot;
        if (this.FireThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            hbSlot = this.getItemForName("взрывная штучка", true);
            invSlot = this.getItemForName("взрывная штучка", false);
            if (invSlot == -1 && hbSlot == -1) {
                this.print("Взрывная штучка не найдена!");
                this.FireThrow = false;
                return;
            }

            if (!Minecraft.player.getCooldownTracker().hasCooldown(Items.FIRE_CHARGE)) {
                this.print("Заюзал взрывную штучку!");
                old = this.findAndTrowItem(hbSlot, invSlot);
                if (old > 8) {
                    GriefHelper.mc.playerController.pickItem(old);
                }
            }

            this.FireThrow = false;
        }

        int slot;
        if (this.StanThrow) {
            hbSlot = this.getItemForName("стан", true);
            invSlot = this.getItemForName("стан", true);
            if (invSlot == -1 && hbSlot == -1) {
                this.print("Стан не найден!");
                this.StanThrow = false;
                return;
            }

            if (!Minecraft.player.getCooldownTracker().hasCooldown(Items.NETHER_STAR)) {
                this.print("Заюзал стан!");
                old = Minecraft.player.inventory.currentItem;
                slot = this.findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    GriefHelper.mc.playerController.pickItem(slot);
                }

                if (InventoryUtil.findEmptySlot(true) != -1 && Minecraft.player.inventory.currentItem != old) {
                    Minecraft.player.inventory.currentItem = old;
                }
            }

            this.StanThrow = false;
        }

        if (this.trapThrow) {
            hbSlot = this.getItemForName("взрывная трапка", true);
            invSlot = this.getItemForName("взрывная трапка", false);
            if (invSlot == -1 && hbSlot == -1) {
                this.print("Взрывная трапка не найдена!");
                this.trapThrow = false;
                return;
            }

            if (!Minecraft.player.getCooldownTracker().hasCooldown(Items.PRISMARINE_SHARD)) {
                this.print("Заюзал взрывную трапку!");
                old = Minecraft.player.inventory.currentItem;
                slot = this.findAndTrowItem(hbSlot, invSlot);
                if (slot > 8) {
                    GriefHelper.mc.playerController.pickItem(slot);
                }

                if (InventoryUtil.findEmptySlot(true) != -1 && Minecraft.player.inventory.currentItem != old) {
                    Minecraft.player.inventory.currentItem = old;
                }
            }

            this.trapThrow = false;
        }

        this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
    }

    @Subscribe
    private void onPacket(EventPacket e) {
        this.handUtil.onEventPacket(e);
    }

    private int findAndTrowItem(int hbSlot, int invSlot) {
        if (hbSlot != -1) {
            this.handUtil.setOriginalSlot(Minecraft.player.inventory.currentItem);
            Minecraft.player.connection.sendPacket(new CHeldItemChangePacket(hbSlot));
            Minecraft.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            Minecraft.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return hbSlot;
        } else if (invSlot != -1) {
            this.handUtil.setOriginalSlot(Minecraft.player.inventory.currentItem);
            GriefHelper.mc.playerController.pickItem(invSlot);
            Minecraft.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            Minecraft.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return invSlot;
        } else {
            return -1;
        }
    }

    public void onDisable() {
        this.FireThrow = false;
        this.trapThrow = false;
        this.delay = 0L;
        super.onDisable();
    }

    private int getItemForName(String name, boolean inHotBar) {
        int firstSlot = inHotBar ? 0 : 9;
        int lastSlot = inHotBar ? 9 : 36;

        for(int i = firstSlot; i < lastSlot; ++i) {
            ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
            String displayName;
            if (!(itemStack.getItem() instanceof AirItem) && (displayName = TextFormatting.getTextWithoutFormattingCodes(itemStack.getDisplayName().getString())) != null && displayName.toLowerCase().contains(name)) {
                return i;
            }
        }

        return -1;
    }
}