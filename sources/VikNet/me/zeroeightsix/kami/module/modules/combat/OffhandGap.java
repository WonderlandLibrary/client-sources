package me.zeroeightsix.kami.module.modules.combat;

import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.combat.AutoTotem;
import me.zeroeightsix.kami.module.modules.dev.AutoTotemDev;
import me.zeroeightsix.kami.module.modules.render.Crystals;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

/**
 * Created 25 November 2019 by 3arthqu4ke
 */
@Module.Info(name = "OffhandGap", category = Module.Category.COMBAT, description = "Auto Offhand Gapple")
public class OffhandGap extends Module {

    private int gapples;
    private boolean moving = false;
    private boolean returnI = false;
    private long ViktisenSystemTime;

    private Setting<Boolean> soft = register(Settings.b("Soft", false));
    private Setting<Boolean> totemOnDisable = register(Settings.b("TotemOnDisable", false));
    private Setting<TotemMode> totemMode = register(Settings.enumBuilder(TotemMode.class).withName("TotemMode").withValue(TotemMode.KAMI).withVisibility(v -> totemOnDisable.getValue()).build());
    private Setting<Boolean> message = register(Settings.b("message", true));
    private Setting<Boolean> DelayCONST = register(Settings.b("DelayCONST", false));

    @Override
    public void onDisable() {
        if (!totemOnDisable.getValue()) {
            return;
        }
        if (totemMode.getValue().equals(TotemMode.KAMI)) {
            AutoTotem autoTotem = (AutoTotem) ModuleManager.getModuleByName("AutoTotem");
            autoTotem.disableSoft();
            if (autoTotem.isDisabled()) {
                autoTotem.enable();
            }
        }
        if (totemMode.getValue().equals(TotemMode.ASIMOV)) {
            AutoTotemDev autoTotemDev = (AutoTotemDev) ModuleManager.getModuleByName("AutoTotemDev");
            autoTotemDev.disableSoft();
            if (autoTotemDev.isDisabled()) {
                autoTotemDev.enable();
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (DelayCONST.getValue())
        if (System.nanoTime() / 1000000L - this.ViktisenSystemTime >= 200) {
            this.ViktisenSystemTime = System.nanoTime() / 1000000L;
        }else{
            return;
        }
        if (mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (returnI) {
            int t = -1;
            for (int i = 0; i < 45; i++) {
                if (mc.player.inventory.getStackInSlot(i).isEmpty) {
                    t = i;
                    break;
                }
            }
            if (t == -1) {
                return;
            }
            mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
            returnI = false;
        }
        gapples = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
            gapples++;
        } else {
            if (soft.getValue() && !mc.player.getHeldItemOffhand().isEmpty) {
                return;
            }
            if (moving) {
                mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                moving = false;
                if (!mc.player.inventory.itemStack.isEmpty()) {
                    returnI = true;
                }
                return;
            }
            if (mc.player.inventory.itemStack.isEmpty()) {
                if (gapples == 0) {
                    return;
                }
                int t = -1;
                for (int i = 0; i < 45; i++) {
                    if (mc.player.inventory.getStackInSlot(i).getItem() == Items.GOLDEN_APPLE) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return; // Should never happen!
                }
                mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
                moving = true;
            } else if (!soft.getValue()) {
                int t = -1;
                for (int i = 0; i < 45; i++) {
                    if (mc.player.inventory.getStackInSlot(i).isEmpty) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
            }
        }
    }

    @Override
    public String getHudInfo() {
        return String.valueOf(gapples);
    }

    private enum TotemMode {
        KAMI, ASIMOV
    }

    @Override
    public void onEnable() {
        this.ViktisenSystemTime = System.nanoTime() / 1000000L;
        if (ModuleManager.getModuleByName("AutoTotem").isEnabled()) {
            ModuleManager.getModuleByName("AutoTotem").disable();
        }
        if (ModuleManager.getModuleByName("AutoTotemDev").isEnabled()) {
            ModuleManager.getModuleByName("AutoTotemDev").disable();
        }
        {
            if ((Boolean)this.message.getValue()) {
                Command.sendChatMessage("\u00A7eG=" + gapples);
            }
        }
    }

}
