package in.momin5.cookieclient.client.modules.combat;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingMode;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

public class Offhand extends Module {

    private SettingMode mode = register(new SettingMode("Item",this,"Crystal","Totem","Gapple","Crystal"));
    private SettingNumber healfSwitch = register(new SettingNumber("Healf",this,16,1,36,1));
    private SettingBoolean gap = register(new SettingBoolean("RightClickGap",this,false));

    public Offhand() {
        super("Offhand", Category.COMBAT);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onUpdate() {
        if(nullCheck())
            return;
        if(mc.currentScreen == null) {
            boolean rightClicked = false;
            float healf = mc.player.getHealth() + mc.player.getAbsorptionAmount();
            if(healf > healfSwitch.getValue()) {
                if(gap.isEnabled()) {
                    if(mc.gameSettings.keyBindUseItem.isKeyDown()) {
                        rightClicked = true;
                    }
                }

                if(mode.is("Crystal") && rightClicked) {
                    changeSlot(findSlot(Items.GOLDEN_APPLE));
                    return;
                }

                if(mode.is("Totem") && rightClicked) {
                    changeSlot(findSlot(Items.GOLDEN_APPLE));
                    return;
                }

                if(mode.is("Crystal") && mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL && !rightClicked) {
                    changeSlot(findSlot(Items.END_CRYSTAL));
                    return;
                }
                if(mode.is("Gapple") && mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE) {
                    changeSlot(findSlot(Items.GOLDEN_APPLE));
                    return;
                }
                if(mode.is("Totem") && mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
                    changeSlot(findSlot(Items.TOTEM_OF_UNDYING));
                    return;
                }

                if(mc.player.getHeldItemOffhand().getItem() == Items.AIR) {
                    changeSlot(findSlot(Items.TOTEM_OF_UNDYING));
                }

            }else {
                if(mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
                    changeSlot(findSlot(Items.TOTEM_OF_UNDYING));
                }
            }
        }
    }

    public void changeSlot(int slot) {
        if (slot != -1) {
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.updateController();
        }
    }

    private int findSlot(Item input) {
        if (input == mc.player.getHeldItemOffhand().getItem()) return -1;
        for (int i = 36; i >= 0; i--) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if (item == input) {
                if (i < 9) {
                    if (input == Items.GOLDEN_APPLE) {
                        return -1;
                    }
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
}
