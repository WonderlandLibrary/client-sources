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

@FunctionRegister(name="FT Helper", type=Category.Misc)
public class GriefHelper
extends Function {
    private final ModeListSetting mode = new ModeListSetting("\u0422\u0438\u043f", new BooleanSetting("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443", true), new BooleanSetting("\u0417\u0430\u043a\u0440\u044b\u0432\u0430\u0442\u044c \u043c\u0435\u043d\u044e", true));
    private final BindSetting disorientationKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u0414\u0435\u0437\u043e\u0440\u0435\u043d\u0442\u0430:", -1).setVisible(this::lambda$new$0);
    private final BindSetting trapKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u0422\u0440\u0430\u043f\u043a\u0438:", -1).setVisible(this::lambda$new$1);
    private final BindSetting plastKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u041f\u043b\u0430\u0441\u0442\u0430:", -1).setVisible(this::lambda$new$2);
    private final BindSetting yavkaKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u042f\u0432\u043d\u043e\u0439 \u043f\u044b\u043b\u0438:", -1).setVisible(this::lambda$new$3);
    private final BindSetting phKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u0411\u043e\u0436\u044c\u0435\u0439 \u0410\u0443\u0440\u044b:", -1).setVisible(this::lambda$new$4);
    private final BindSetting shalkKey = new BindSetting("\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u0428\u0430\u043b\u043a\u0435\u0440:", -1).setVisible(this::lambda$new$5);
    private final BindSetting arbKey = new BindSetting("\u0412\u044b\u0441\u0442\u0440\u0435\u043b \u0441 \u0430\u0440\u0431\u0430\u043b\u0435\u0442\u0430:", -1).setVisible(this::lambda$new$6);
    private final BindSetting otrKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u041e\u0442\u0440\u044b\u0436\u043a\u0438:", -1).setVisible(this::lambda$new$7);
    private final BindSetting serKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u0421\u0435\u0440\u043a\u0438:", -1).setVisible(this::lambda$new$8);
    private final BindSetting kilkey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u041a\u0438\u043b\u043b\u0435\u0440\u0430:", -1).setVisible(this::lambda$new$9);
    private final BindSetting agentKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u0410\u0433\u0435\u043d\u0442\u0430:", -1).setVisible(this::lambda$new$10);
    private final BindSetting vspKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u0412\u0441\u043f\u044b\u0448\u043a\u0438:", -1).setVisible(this::lambda$new$11);
    private final BindSetting flashkey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u0424\u043b\u0435\u0448\u0430:", -1).setVisible(this::lambda$new$12);
    private final BindSetting medKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u041c\u0435\u0434\u0438\u043a\u0430:", -1).setVisible(this::lambda$new$13);
    private final BindSetting pobKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u041f\u043e\u0431\u0435\u0434\u0438\u043b\u043a\u0438:", -1).setVisible(this::lambda$new$14);
    final StopWatch stopWatch = new StopWatch();
    InventoryUtil.Hand handUtil = new InventoryUtil.Hand();
    long delay;
    boolean disorientationThrow;
    boolean trapThrow;
    boolean plastThrow;
    boolean yavkaThrow;
    boolean phThrow;
    boolean shalkThrow;
    boolean arbThrow;
    boolean otrThrow;
    boolean serThrow;
    boolean kilThrow;
    boolean agentThrow;
    boolean vspThrow;
    boolean flashThrow;
    boolean medThrow;
    boolean pobThrow;

    public GriefHelper() {
        this.addSettings(this.disorientationKey, this.trapKey, this.plastKey, this.yavkaKey, this.phKey, this.shalkKey, this.arbKey, this.otrKey, this.serKey, this.kilkey, this.agentKey, this.vspKey, this.flashkey, this.medKey, this.pobKey);
    }

    @Subscribe
    private void onKey(EventKey eventKey) {
        if (eventKey.getKey() == ((Integer)this.disorientationKey.get()).intValue()) {
            this.disorientationThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.trapKey.get()).intValue()) {
            this.trapThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.plastKey.get()).intValue()) {
            this.plastThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.yavkaKey.get()).intValue()) {
            this.yavkaThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.phKey.get()).intValue()) {
            this.phThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.shalkKey.get()).intValue()) {
            this.shalkThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.arbKey.get()).intValue()) {
            this.arbThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.otrKey.get()).intValue()) {
            this.otrThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.serKey.get()).intValue()) {
            this.serThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.kilkey.get()).intValue()) {
            this.kilThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.agentKey.get()).intValue()) {
            this.agentThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.vspKey.get()).intValue()) {
            this.vspThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.flashkey.get()).intValue()) {
            this.flashThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.medKey.get()).intValue()) {
            this.medThrow = true;
        }
        if (eventKey.getKey() == ((Integer)this.pobKey.get()).intValue()) {
            this.pobThrow = true;
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
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.CROSSBOW)) {
                this.print("\u0412\u044b\u0441\u0442\u0440\u0435\u043b \u0438\u0437 \u0430\u0440\u0431\u0430\u043b\u0435\u0442\u0430!");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.arbThrow = false;
        }
        if (this.pobThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0437\u0435\u043b\u044c\u0435 \u043f\u043e\u0431\u0435\u0434\u0438\u0442\u0435\u043b\u044f", true);
            n2 = this.getItemForName("\u0437\u0435\u043b\u044c\u0435 \u043f\u043e\u0431\u0435\u0434\u0438\u0442\u0435\u043b\u044f", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u041f\u043e\u0431\u0435\u0434\u0438\u043b\u043a\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.pobThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
                this.print("\u0412\u044b\u043a\u0438\u043d\u0443\u043b \u043f\u043e\u0431\u0435\u0434\u0438\u043b\u043a\u0443");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.pobThrow = false;
        }
        if (this.medThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0437\u0435\u043b\u044c\u0435 \u043c\u0435\u0434\u0438\u043a\u0430", true);
            n2 = this.getItemForName("\u0437\u0435\u043b\u044c\u0435 \u043c\u0435\u0434\u0438\u043a\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u041c\u0435\u0434\u0438\u043a \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d");
                this.medThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
                this.print("\u0412\u044b\u043a\u0438\u043d\u0443\u043b \u043c\u0435\u0434\u0438\u043a\u0430");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.medThrow = false;
        }
        if (this.flashThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u043c\u043e\u0447\u0430 \u0444\u043b\u0435\u0448\u0430", true);
            n2 = this.getItemForName("\u043c\u043e\u0447\u0430 \u0444\u043b\u0435\u0448\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u041c\u043e\u0447\u0430 \u0444\u043b\u0435\u0448\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.flashThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
                this.print("\u0412\u044b\u043a\u0438\u043d\u0443\u043b \u043c\u043e\u0447\u0443 \u0444\u043b\u0435\u0448\u0430");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.flashThrow = false;
        }
        if (this.vspThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0432\u0441\u043f\u044b\u0448\u043a\u0430", true);
            n2 = this.getItemForName("\u0432\u0441\u043f\u044b\u0448\u043a\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0412\u0441\u043f\u044b\u0448\u043a\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.vspThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
                this.print("\u0412\u044b\u043a\u0438\u043d\u0443\u043b \u0432\u0441\u043f\u044b\u0448\u043a\u0443");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.vspThrow = false;
        }
        if (this.agentThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0437\u0435\u043b\u044c\u0435 \u0430\u0433\u0435\u043d\u0442\u0430", true);
            n2 = this.getItemForName("\u0437\u0435\u043b\u044c\u0435 \u0430\u0433\u0435\u043d\u0442\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0417\u0435\u043b\u044c\u0435 \u0430\u0433\u0435\u043d\u0442\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d");
                this.agentThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
                this.print("\u0412\u044b\u043a\u0438\u043d\u0443\u043b \u0437\u0435\u043b\u044c\u0435 \u0430\u0433\u0435\u043d\u0442\u0430");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.agentThrow = false;
        }
        if (this.kilThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0437\u0435\u043b\u044c\u0435 \u043a\u0438\u043b\u043b\u0435\u0440\u0430", true);
            n2 = this.getItemForName("\u0437\u0435\u043b\u044c\u0435 \u043a\u0438\u043b\u043b\u0435\u0440\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0417\u0435\u043b\u044c\u0435 \u043a\u0438\u043b\u043b\u0435\u0440\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d");
                this.kilThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
                this.print("\u0412\u044b\u043a\u0438\u043d\u0443\u043b \u0437\u0435\u043b\u044c\u0435 \u043a\u0438\u043b\u043b\u0435\u0440\u0430");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.kilThrow = false;
        }
        if (this.serThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0441\u0435\u0440\u043d\u0430\u044f \u043a\u0438\u0441\u043b\u043e\u0442\u0430", true);
            n2 = this.getItemForName("\u0441\u0435\u0440\u043d\u0430\u044f \u043a\u0438\u0441\u043b\u043e\u0442\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0421\u0435\u0440\u043d\u0430\u044f \u043a\u0438\u0441\u043b\u043e\u0442\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.serThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
                this.print("\u0412\u044b\u043a\u0438\u043d\u0443\u043b \u0441\u0435\u0440\u043d\u0443\u044e \u043a\u0438\u0441\u043b\u043e\u0442\u0443");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.serThrow = false;
        }
        if (this.otrThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0417\u0435\u043b\u044c\u0435 \u041e\u0442\u0440\u044b\u0436\u043a\u0438", true);
            n2 = this.getItemForName("\u0417\u0435\u043b\u044c\u0435 \u041e\u0442\u0440\u044b\u0436\u043a\u0438", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u041e\u0442\u0440\u044b\u0436\u043a\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.otrThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.SPLASH_POTION)) {
                this.print("\u0412\u044b\u043a\u0438\u043d\u0443\u043b \u043e\u0442\u0440\u044b\u0433\u0443");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.otrThrow = false;
        }
        if (this.disorientationThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0434\u0435\u0437\u043e\u0440\u0438\u0435\u043d\u0442\u0430\u0446\u0438\u044f", true);
            n2 = this.getItemForName("\u0434\u0435\u0437\u043e\u0440\u0438\u0435\u043d\u0442\u0430\u0446\u0438\u044f", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0414\u0435\u0437\u043e\u0440\u0438\u0435\u043d\u0442\u0430\u0446\u0438\u044f \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.disorientationThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.ENDER_EYE)) {
                this.print("\u0417\u0430\u044e\u0437\u0430\u043b \u0434\u0435\u0437\u043e\u0440\u0438\u0435\u043d\u0442\u0430\u0446\u0438\u044e");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.disorientationThrow = false;
        }
        if (this.shalkThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0448\u0430\u043b\u043a\u0435\u0440\u043e\u0432\u044b\u0439 \u044f\u0449\u0438\u043a", true);
            n2 = this.getItemForName("\u0448\u0430\u043b\u043a\u0435\u0440\u043e\u0432\u044b\u0439 \u044f\u0449\u0438\u043a", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0428\u0430\u043b\u043a\u0435\u0440 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\n" + TextFormatting.DARK_RED + "\u0415\u0441\u043b\u0438 \u0443 \u0432\u0430\u0441 \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u043c\u043e\u0434\u0443\u043b\u044c \u043f\u043e\u0441\u0442\u0430\u0432\u044c\u0442\u0435 \u0440\u0443\u0441\u0441\u043a\u0438\u0439 \u044f\u0437\u044b\u043a");
                this.shalkThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.SHULKER_BOX)) {
                this.print("\u041e\u0442\u043a\u0440\u044b\u043b \u0448\u0430\u043b\u043a\u0435\u0440");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.shalkThrow = false;
        }
        if (this.yavkaThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u044f\u0432\u043d\u0430\u044f \u043f\u044b\u043b\u044c", true);
            n2 = this.getItemForName("\u044f\u0432\u043d\u0430\u044f \u043f\u044b\u043b\u044c", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u042f\u0432\u043d\u0430\u044f \u043f\u044b\u043b\u044c \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.yavkaThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.SUGAR)) {
                this.print("\u0417\u0430\u044e\u0437\u0430\u043b \u044f\u0432\u043d\u0443\u044e \u043f\u044b\u043b\u044c");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.yavkaThrow = false;
        }
        if (this.phThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u0431\u043e\u0436\u044c\u044f \u0430\u0443\u0440\u0430", true);
            n2 = this.getItemForName("\u0431\u043e\u0436\u044c\u044f \u0430\u0443\u0440\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0411\u043e\u0436\u044c\u044f \u0410\u0443\u0440\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.phThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.PHANTOM_MEMBRANE)) {
                this.print("\u0417\u0430\u044e\u0437\u0430\u043b \u0411\u043e\u0436\u044c\u044e \u0410\u0443\u0440\u0443");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.phThrow = false;
        }
        if (this.plastThrow) {
            this.handUtil.handleItemChange(System.currentTimeMillis() - this.delay > 200L);
            n3 = this.getItemForName("\u043f\u043b\u0430\u0441\u0442", true);
            n2 = this.getItemForName("\u043f\u043b\u0430\u0441\u0442", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u041f\u043b\u0430\u0441\u0442 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d");
                this.plastThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.DRIED_KELP)) {
                this.print("\u0417\u0430\u044e\u0437\u0430\u043b \u043f\u043b\u0430\u0441\u0442");
                n = this.findAndTrowItem(n3, n2);
                if (n > 8) {
                    GriefHelper.mc.playerController.pickItem(n);
                }
            }
            this.plastThrow = false;
        }
        if (this.trapThrow) {
            n3 = this.getItemForName("\u0442\u0440\u0430\u043f\u043a\u0430", true);
            n2 = this.getItemForName("\u0442\u0440\u0430\u043f\u043a\u0430", false);
            if (n2 == -1 && n3 == -1) {
                this.print("\u0422\u0440\u0430\u043f\u043a\u0430 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430");
                this.trapThrow = false;
                return;
            }
            minecraft = mc;
            if (!GriefHelper.mc.player.getCooldownTracker().hasCooldown(Items.NETHERITE_SCRAP)) {
                this.print("\u0417\u0430\u044e\u0437\u0430\u043b \u0442\u0440\u0430\u043f\u043a\u0443");
                minecraft = mc;
                n = GriefHelper.mc.player.inventory.currentItem;
                int n4 = this.findAndTrowItem(n3, n2);
                if (n4 > 8) {
                    GriefHelper.mc.playerController.pickItem(n4);
                }
                if (InventoryUtil.findEmptySlot(true) != -1) {
                    minecraft = mc;
                    if (GriefHelper.mc.player.inventory.currentItem != n) {
                        minecraft = mc;
                        GriefHelper.mc.player.inventory.currentItem = n;
                    }
                }
            }
            this.trapThrow = false;
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
            this.handUtil.setOriginalSlot(GriefHelper.mc.player.inventory.currentItem);
            Minecraft minecraft2 = mc;
            GriefHelper.mc.player.connection.sendPacket(new CHeldItemChangePacket(n));
            minecraft2 = mc;
            GriefHelper.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            minecraft2 = mc;
            GriefHelper.mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return n;
        }
        if (n2 != -1) {
            Minecraft minecraft = mc;
            this.handUtil.setOriginalSlot(GriefHelper.mc.player.inventory.currentItem);
            GriefHelper.mc.playerController.pickItem(n2);
            Minecraft minecraft3 = mc;
            GriefHelper.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
            minecraft3 = mc;
            GriefHelper.mc.player.swingArm(Hand.MAIN_HAND);
            this.delay = System.currentTimeMillis();
            return n2;
        }
        return 0;
    }

    @Override
    public void onDisable() {
        this.disorientationThrow = false;
        this.trapThrow = false;
        this.plastThrow = false;
        this.yavkaThrow = false;
        this.shalkThrow = false;
        this.arbThrow = false;
        this.serThrow = false;
        this.otrThrow = false;
        this.delay = 0L;
        super.onDisable();
    }

    private int getItemForName(String string, boolean bl) {
        int n = bl ? 0 : 9;
        int n2 = bl ? 9 : 36;
        for (int i = n; i < n2; ++i) {
            String string2;
            Minecraft minecraft = mc;
            ItemStack itemStack = GriefHelper.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() instanceof AirItem || (string2 = TextFormatting.getTextWithoutFormattingCodes(itemStack.getDisplayName().getString())) == null || !string2.toLowerCase().contains(string)) continue;
            return i;
        }
        return 0;
    }

    private Boolean lambda$new$14() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$13() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$12() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$11() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$10() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$9() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
    }

    private Boolean lambda$new$8() {
        return (Boolean)this.mode.getValueByName("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043f\u043e \u0431\u0438\u043d\u0434\u0443").get();
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

