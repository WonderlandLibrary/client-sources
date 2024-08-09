/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventKey;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;

@FunctionRegister(name="HW Helper", type=Category.Misc)
public class HWHelper
extends Function {
    private final ModeListSetting mode = new ModeListSetting("\u0422\u0438\u043f", new BooleanSetting("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443", true), new BooleanSetting("\u0417\u0430\u043a\u0440\u044b\u0432\u0430\u0442\u044c \u043c\u0435\u043d\u044e", true));
    private final BindSetting vzrivtrapKey = new BindSetting("\u0412\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0442\u0440\u0430\u043f\u043a\u0430:", -1).setVisible(this::lambda$new$0);
    private final BindSetting palKey = new BindSetting("\u0412\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u043f\u0430\u043b\u043e\u0447\u043a\u0430:", -1).setVisible(this::lambda$new$1);
    private final BindSetting shtKey = new BindSetting("\u0412\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0448\u0442\u0443\u0447\u043a\u0430:", -1).setVisible(this::lambda$new$2);
    private final BindSetting oglKey = new BindSetting("\u041e\u0433\u043b\u0443\u0448\u0435\u043d\u0438\u0435:", -1).setVisible(this::lambda$new$3);
    private final BindSetting opitKey = new BindSetting("\u041f\u0443\u0437\u044b\u0440\u044c \u041e\u043f\u044b\u0442\u0430:", -1).setVisible(this::lambda$new$4);
    private final BindSetting snegKey = new BindSetting("\u041a\u043e\u043c \u0421\u043d\u0435\u0433\u0430:", -1).setVisible(this::lambda$new$5);
    private final BindSetting arbKey = new BindSetting("\u0412\u044b\u0441\u0442\u0440\u0435\u043b \u0441 \u0430\u0440\u0431\u0430\u043b\u0435\u0442\u0430:", -1).setVisible(this::lambda$new$6);
    private final BindSetting molKey = new BindSetting("\u041c\u043e\u043b\u043e\u0447\u043d\u043e\u0435 \u0437\u0435\u043b\u044c\u0435:", -1).setVisible(this::lambda$new$7);
    final StopWatch stopWatch = new StopWatch();
    InventoryUtil.Hand handUtil = new InventoryUtil.Hand();
    long delay;
    boolean vzrivtrapThrow;
    boolean palThrow;
    boolean shtThrow;
    boolean oglThrow;
    boolean opitThrow;
    boolean snegThrow;
    boolean arbThrow;
    boolean molThrow;

    public HWHelper() {
        this.addSettings(this.vzrivtrapKey, this.palKey, this.shtKey, this.oglKey, this.opitKey, this.snegKey, this.arbKey, this.molKey);
    }

    @Subscribe
    private void onKey(EventKey eventKey) {
        if (eventKey.getKey() == ((Integer)this.vzrivtrapKey.get()).intValue()) {
            this.vzrivtrapThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.palKey.get()).intValue()) {
            this.palThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.shtKey.get()).intValue()) {
            this.shtThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.oglKey.get()).intValue()) {
            this.oglThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.opitKey.get()).intValue()) {
            this.opitThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.snegKey.get()).intValue()) {
            this.snegThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.arbKey.get()).intValue()) {
            this.arbThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.molKey.get()).intValue()) {
            this.molThrow = true;
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        int n;
        Minecraft minecraft;
        int n2;
        int n3;
        if (this.arbThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0430\u0440\u0431\u0430\u043b\u0435\u0442", true);
            n2 = this.getItemForName("\u0430\u0440\u0431\u0430\u043b\u0435\u0442", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0410\u0440\u0431\u0430\u043b\u0435\u0442 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\n" + TextFormatting.RED + "\u0415\u0441\u043b\u0438 \u0443 \u0432\u0430\u0441 \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u043c\u043e\u0434\u0443\u043b\u044c \u043f\u043e\u0441\u0442\u0430\u0432\u044c\u0442\u0435 \u0440\u0443\u0441\u0441\u043a\u0438\u0439 \u044f\u0437\u044b\u043a");
                this.arbThrow = false;
                return;
            }
            minecraft = mc;
            if (!HWHelper.mc.player.getCooldownTracker().hasCooldown(Items.CROSSBOW)) {
                this.print("\u0412\u044b\u0441\u0442\u0440\u0435\u043b \u0438\u0437 \u0430\u0440\u0431\u0430\u043b\u0435\u0442\u0430!");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    HWHelper.mc.playerController.pickItem(n);
                }
            }
            this.arbThrow = false;
        }
        if (this.molThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u041c\u043e\u043b\u043e\u0447\u043d\u043e\u0435 \u0437\u0435\u043b\u044c\u0435", true);
            n2 = this.getItemForName("\u041c\u043e\u043b\u043e\u0447\u043d\u043e\u0435 \u0437\u0435\u043b\u044c\u0435", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u041c\u043e\u043b\u043e\u0447\u043d\u043e\u0435 \u0437\u0435\u043b\u044c\u0435 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e");
                this.molThrow = false;
                return;
            }
            minecraft = mc;
            if (!HWHelper.mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
                this.print("\u0412\u044b\u043a\u0438\u043d\u0443\u043b \u043c\u043e\u043b\u043e\u0447\u043d\u043e\u0435 \u0437\u0435\u043b\u044c\u0435");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    HWHelper.mc.playerController.pickItem(n);
                }
            }
            this.molThrow = false;
        }
        if (this.vzrivtrapThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0432\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0442\u0440\u0430\u043f\u043a\u0430", true);
            n2 = this.getItemForName("\u0432\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0442\u0440\u0430\u043f\u043a\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0412\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0442\u0440\u0430\u043f\u043a\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.vzrivtrapThrow = false;
                return;
            }
            minecraft = mc;
            if (!HWHelper.mc.player.getCooldownTracker().hasCooldown(Items.ENDER_EYE)) {
                this.print("\u0417\u0430\u044e\u0437\u0430\u043b \u0432\u0437\u0440\u044b\u0432\u043d\u0443\u044e \u0442\u0440\u0430\u043f\u043a\u0443");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    HWHelper.mc.playerController.pickItem(n);
                }
            }
            this.vzrivtrapThrow = false;
        }
        if (this.palThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0432\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u043f\u0430\u043b\u043e\u0447\u043a\u0430", true);
            n2 = this.getItemForName("\u0432\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u043f\u0430\u043b\u043e\u0447\u043a\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0412\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u043f\u0430\u043b\u043e\u0447\u043a\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.palThrow = false;
                return;
            }
            minecraft = mc;
            if (!HWHelper.mc.player.getCooldownTracker().hasCooldown(Items.ENDER_EYE)) {
                this.print("\u0417\u0430\u044e\u0437\u0430\u043b \u0432\u0437\u0440\u044b\u0432\u043d\u0443\u044e \u043f\u0430\u043b\u043e\u0447\u043a\u0443");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    HWHelper.mc.playerController.pickItem(n);
                }
            }
            this.palThrow = false;
        }
        if (this.shtThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0432\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0448\u0442\u0443\u0447\u043a\u0430", true);
            n2 = this.getItemForName("\u0432\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0448\u0442\u0443\u0447\u043a\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0412\u0437\u0440\u044b\u0432\u043d\u0430\u044f \u0448\u0442\u0443\u0447\u043a\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.shtThrow = false;
                return;
            }
            minecraft = mc;
            if (!HWHelper.mc.player.getCooldownTracker().hasCooldown(Items.ENDER_EYE)) {
                this.print("\u0417\u0430\u044e\u0437\u0430\u043b \u0432\u0437\u0440\u044b\u0432\u043d\u0443\u044e \u0448\u0442\u0443\u0447\u043a\u0443");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    HWHelper.mc.playerController.pickItem(n);
                }
            }
            this.shtThrow = false;
        }
        if (this.oglThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u043e\u0433\u043b\u0443\u0448\u0435\u043d\u0438\u0435", true);
            n2 = this.getItemForName("\u043e\u0433\u043b\u0443\u0448\u0435\u043d\u0438\u0435", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u041e\u0433\u043b\u0443\u0448\u0435\u043d\u0438\u0435 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u043e");
                this.oglThrow = false;
                return;
            }
            minecraft = mc;
            if (!HWHelper.mc.player.getCooldownTracker().hasCooldown(Items.ENDER_EYE)) {
                this.print("\u0417\u0430\u044e\u0437\u0430\u043b \u043e\u0433\u043b\u0443\u0448\u0435\u043d\u0438\u0435");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    HWHelper.mc.playerController.pickItem(n);
                }
            }
            this.oglThrow = false;
        }
        if (this.opitThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u043f\u0443\u0437\u044b\u0440\u044c \u043e\u043f\u044b\u0442\u0430", true);
            n2 = this.getItemForName("\u043f\u0443\u0437\u044b\u0440\u044c \u043e\u043f\u044b\u0442\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u041f\u0443\u0437\u044b\u0440\u044c \u043e\u043f\u044b\u0442\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d");
                this.opitThrow = false;
                return;
            }
            minecraft = mc;
            if (!HWHelper.mc.player.getCooldownTracker().hasCooldown(Items.ENDER_EYE)) {
                this.print("\u0417\u0430\u044e\u0437\u0430\u043b \u043f\u0443\u0437\u044b\u0440\u044c \u043e\u043f\u044b\u0442\u0430");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    HWHelper.mc.playerController.pickItem(n);
                }
            }
            this.opitThrow = false;
        }
        if (this.snegThrow) {
            n3 = this.getItemForName("\u043a\u043e\u043c \u0441\u043d\u0435\u0433\u0430", true);
            n2 = this.getItemForName("\u043a\u043e\u043c \u0441\u043d\u0435\u0433\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u041a\u043e\u043c \u0441\u043d\u0435\u0433\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d");
                this.snegThrow = false;
                return;
            }
            minecraft = mc;
            if (!HWHelper.mc.player.getCooldownTracker().hasCooldown(Items.ENDER_EYE)) {
                this.print("\u0417\u0430\u044e\u0437\u0430\u043b \u043a\u043e\u043c \u0441\u043d\u0435\u0433\u0430");
                minecraft = mc;
                n = HWHelper.mc.player.inventory.currentItem;
                int n4 = this.findAndTrowItem(n3, n2);
                if (n4 > 8) {
                    HWHelper.mc.playerController.pickItem(n4);
                }
                if (InventoryUtil.findEmptySlot(true) != -1) {
                    minecraft = mc;
                    if (HWHelper.mc.player.inventory.currentItem != n) {
                        minecraft = mc;
                        HWHelper.mc.player.inventory.currentItem = n;
                    }
                }
            }
            this.snegThrow = false;
        }
        this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        this.handUtil.onEventPacket(eventPacket);
    }

    private int findAndTrowItem(int n, int n2) {
        if (n != -1) {
            Minecraft minecraft = mc;
            this.handUtil.setOriginalSlot(HWHelper.mc.player.inventory.currentItem);
            Minecraft minecraft2 = mc;
            HWHelper.mc.player.connection.sendPacket(new CHeldItemChangePacket(n));
            minecraft2 = mc;
            HWHelper.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            minecraft2 = mc;
            HWHelper.mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return n;
        }
        if (n2 != -1) {
            Minecraft minecraft = mc;
            this.handUtil.setOriginalSlot(HWHelper.mc.player.inventory.currentItem);
            HWHelper.mc.playerController.pickItem(n2);
            Minecraft minecraft3 = mc;
            HWHelper.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            minecraft3 = mc;
            HWHelper.mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return n2;
        }
        return 0;
    }

    @Override
    public void onDisable() {
        this.vzrivtrapThrow = false;
        this.palThrow = false;
        this.oglThrow = false;
        this.opitThrow = false;
        this.snegThrow = false;
        this.arbThrow = false;
        this.molThrow = false;
        this.delay = 0L;
        super.onDisable();
    }

    private int getItemForName(String string, boolean bl) {
        int n = bl ? 0 : 9;
        int n2 = bl ? 9 : 36;
        for (int i = n; i < n2; ++i) {
            String string2;
            Minecraft minecraft = mc;
            ItemStack itemStack = HWHelper.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() instanceof AirItem || (string2 = TextFormatting.getTextWithoutFormattingCodes(itemStack.getDisplayName().getString())) == null || !string2.toLowerCase().contains(string)) continue;
            return i;
        }
        return 0;
    }

    private Boolean lambda$new$7() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$6() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$5() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$4() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$3() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$2() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$1() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$0() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }
}

