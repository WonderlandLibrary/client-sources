package me.finz0.osiris.module.modules.combat;

import de.Hero.settings.Setting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.Crystals;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class OffHandGap extends Module {
    public OffHandGap() {
        super("OffHandGap", Category.COMBAT);
    }

    public int crystals;
    public int gapples;
    Setting totemdisable;
    boolean moving = false;
    boolean returnI = false;
    public Setting mode;
    Setting soft;
    Setting Gappleamount;
    ArrayList<String> modes;

    public void setup() {
        modes = new ArrayList<>();
        soft = new Setting("Soft", this, false, "AutoOffhandSoft");
        AuroraMod.getInstance().settingsManager.rSetting(soft);
        Gappleamount = new Setting("GappleChatCount", this, true, "AutoOffhandGappleamount");
        AuroraMod.getInstance().settingsManager.rSetting(Gappleamount);
        totemdisable = new Setting("TotemDisable", this, false, "AutoOffhandTotemDisable");
        AuroraMod.getInstance().settingsManager.rSetting(totemdisable);

    }

    public void onEnable(){
        if (totemdisable.getValBoolean()) {
            ModuleManager.getModuleByName("AutoTotem").disable();
        }

        if(mc.player != null)
            Command.sendClientMessage("\u00A7aOffHandGap On");
        int gapples = Crystals.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum();
        if (Crystals.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
            ++gapples;
        }
        if (Gappleamount.getValBoolean() && mc.player != null) {
            Command.sendClientMessage("\u00A7a"+gapples + " \u00A7eGapples");
        }
    }
    public void onDisable(){
        if (totemdisable.getValBoolean()) {
            ModuleManager.getModuleByName("AutoTotem").enable();
        }

        if(mc.player != null)
            Command.sendClientMessage("\u00A7cOffHandGap Off");
    }


    @Override
    public void onUpdate() {
        if (mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (returnI) {
            int t = -1;
            for (int i = 0; i < 45; i++) {
                if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
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
            if (soft.getValBoolean() && !mc.player.getHeldItemOffhand().isEmpty()) {
                return;
            }
            if (moving) {
                mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                moving = false;
                if (!mc.player.inventory.getItemStack().isEmpty()) {
                    returnI = true;
                }
                return;
            }
            if (mc.player.inventory.getItemStack().isEmpty()) {
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
            } else if (!soft.getValBoolean()) {
                int t = -1;
                for (int i = 0; i < 45; i++) {
                    if (mc.player.inventory.getStackInSlot(i).isEmpty()) {
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
        return String.valueOf("\u00A78[\u00A7r"+gapples+"\u00A78]");
    }

}