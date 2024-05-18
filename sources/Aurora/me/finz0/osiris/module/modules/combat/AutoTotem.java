package me.finz0.osiris.module.modules.combat;

import de.Hero.settings.Setting;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.ModuleManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

//also kami skid
public class AutoTotem extends Module {
    public AutoTotem() {
        super("AutoTotem", Category.COMBAT);
    }

    public int crystals;
    public int totems;
    boolean moving = false;
    boolean returnI = false;
    public Setting mode;
    Setting soft;
    ArrayList<String> modes;

    public void setup() {
        modes = new ArrayList<>();
        soft = new Setting("Soft", this, false, "AutoOffhandSoft");
        AuroraMod.getInstance().settingsManager.rSetting(soft);


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
        totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            totems++;
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
                if (totems == 0) {
                    return;
                }
                int t = -1;
                for (int i = 0; i < 45; i++) {
                    if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
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
        return String.valueOf("\u00A78[\u00A7r"+totems+"\u00A78]");
    }
}