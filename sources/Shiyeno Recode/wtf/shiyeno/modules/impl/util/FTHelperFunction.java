package wtf.shiyeno.modules.impl.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.game.EventKey;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BindSetting;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(name = "FTHelper", type = Type.Util)
public class FTHelperFunction extends Function {
    private final BindSetting disorient = new BindSetting("Дезориентация", 0);
    private final BindSetting trap = new BindSetting("Трапка", 0);

    private final TimerUtil disorientTimer = new TimerUtil();

    private final TimerUtil trapTimer = new TimerUtil();

    public FTHelperFunction() {
        addSettings(new Setting[] { (Setting)this.disorient, (Setting)this.trap });
    }

    public void onEvent(Event event) {
        if (event instanceof EventKey) {
            EventKey eventKey = (EventKey)event;
            if (eventKey.key == this.disorient.getKey() && this.disorientTimer.hasTimeElapsed(3000L) && mc.currentScreen == null) {
                use(getDisorientAtHotBar(), getDisorientAtInventory());
                this.disorientTimer.reset();
            }
            if (eventKey.key == this.trap.getKey() && this.trapTimer.hasTimeElapsed(3000L) && mc.currentScreen == null) {
                use(getTrapAtHotBar(), getTrapAtInventory());
                this.trapTimer.reset();
            }
        }
    }
    private void use(int n, int n2) {
        if (n != -1) {
            int n3 = mc.player.inventory.currentItem;
            mc.player.inventory.currentItem = n;
            mc.player.connection.sendPacket((IPacket)new CHeldItemChangePacket(n));
            mc.player.connection.sendPacket((IPacket)new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.player.inventory.currentItem = n3;
            mc.player.connection.sendPacket((IPacket)new CHeldItemChangePacket(n3));
        } else if (n2 != -1) {
            mc.playerController.windowClick(0, n2, mc.player.inventory.currentItem, ClickType.SWAP, (PlayerEntity)mc.player);
            mc.player.connection.sendPacket((IPacket)new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            mc.playerController.windowClick(0, n2, mc.player.inventory.currentItem, ClickType.SWAP, (PlayerEntity)mc.player);
        }
        this.disorientTimer.reset();
    }

    private int getDisorientAtHotBar() {
        for (int i = 0; i < 9; ) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() != Items.ENDER_EYE) {
                i++;
                continue;
            }
            return i;
        }
        return -1;
    }

    private int getTrapAtHotBar() {
        for (int i = 0; i < 9; ) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() != Items.NETHERITE_SCRAP) {
                i++;
                continue;
            }
            return i;
        }
        return -1;
    }

    private int getDisorientAtInventory() {
        for (int i = 36; i >= 0; ) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() != Items.ENDER_EYE) {
                i--;
                continue;
            }
            if (i < 9)
                i += 36;
            return i;
        }
        return -1;
    }

    private int getTrapAtInventory() {
        for (int i = 36; i >= 0; ) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() != Items.NETHERITE_SCRAP) {
                i--;
                continue;
            }
            if (i < 9)
                i += 36;
            return i;
        }
        return -1;
    }
}