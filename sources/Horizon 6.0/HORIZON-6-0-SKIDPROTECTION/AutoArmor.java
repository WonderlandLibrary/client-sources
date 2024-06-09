package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.COMBAT, Ý = -4179669, Â = "Automaticly wears the best Armor.", HorizonCode_Horizon_È = "AutoArmor")
public class AutoArmor extends Mod
{
    private final TimeHelper Ý;
    
    public AutoArmor() {
        this.Ý = new TimeHelper();
    }
    
    @Handler
    private void HorizonCode_Horizon_È(final EventTick event) {
        if (this.Ý.Â(50L) && !Minecraft.áŒŠà().á.áˆºáˆºáŠ.Ø­áŒŠá && (Minecraft.áŒŠà().¥Æ == null || Minecraft.áŒŠà().¥Æ instanceof GuiChat)) {
            for (byte b = 5; b <= 8; ++b) {
                if (this.HorizonCode_Horizon_È(b)) {
                    this.Ý.Ø­áŒŠá();
                    this.Â.Âµá€.Âµá€();
                    break;
                }
            }
        }
    }
    
    private boolean HorizonCode_Horizon_È(final byte b) {
        int currentProt = -1;
        byte slot = -1;
        ItemArmor current = null;
        if (Minecraft.áŒŠà().á.ŒÂ.HorizonCode_Horizon_È(b).HorizonCode_Horizon_È() != null && Minecraft.áŒŠà().á.ŒÂ.HorizonCode_Horizon_È(b).HorizonCode_Horizon_È().HorizonCode_Horizon_È() instanceof ItemArmor) {
            current = (ItemArmor)Minecraft.áŒŠà().á.ŒÂ.HorizonCode_Horizon_È(b).HorizonCode_Horizon_È().HorizonCode_Horizon_È();
            currentProt = current.áŒŠÆ + EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Ý.ŒÏ, Minecraft.áŒŠà().á.ŒÂ.HorizonCode_Horizon_È(b).HorizonCode_Horizon_È());
        }
        for (byte isNull = 9; isNull <= 44; ++isNull) {
            final ItemStack stack = Minecraft.áŒŠà().á.ŒÂ.HorizonCode_Horizon_È(isNull).HorizonCode_Horizon_È();
            if (stack != null && stack.HorizonCode_Horizon_È() instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor)stack.HorizonCode_Horizon_È();
                final int armorProt = armor.áŒŠÆ + EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Ý.ŒÏ, stack);
                if (this.HorizonCode_Horizon_È(armor, b) && (current == null || currentProt < armorProt)) {
                    currentProt = armorProt;
                    current = armor;
                    slot = isNull;
                }
            }
        }
        if (slot != -1) {
            final boolean var9 = Minecraft.áŒŠà().á.ŒÂ.HorizonCode_Horizon_È(b).HorizonCode_Horizon_È() == null;
            if (!var9) {
                this.HorizonCode_Horizon_È(b, 0, false);
            }
            this.HorizonCode_Horizon_È(slot, 0, true);
            if (!var9) {
                this.HorizonCode_Horizon_È(slot, 0, false);
            }
            return true;
        }
        return false;
    }
    
    private boolean HorizonCode_Horizon_È(final ItemArmor item, final byte b) {
        return (b == 5 && this.HorizonCode_Horizon_È(item)) || (b == 6 && this.Â(item)) || (b == 7 && this.Ý(item)) || (b == 8 && this.Ø­áŒŠá(item));
    }
    
    private boolean HorizonCode_Horizon_È(final ItemArmor item) {
        return item.Ø().startsWith("item.helmet");
    }
    
    private boolean Â(final ItemArmor item) {
        return item.Ø().startsWith("item.chestplate");
    }
    
    private boolean Ý(final ItemArmor item) {
        return item.Ø().startsWith("item.leggings");
    }
    
    private boolean Ø­áŒŠá(final ItemArmor item) {
        return item.Ø().startsWith("item.boots");
    }
    
    private void HorizonCode_Horizon_È(final int slot, final int mouseButton, final boolean shiftClick) {
        Minecraft.áŒŠà().Âµá€.HorizonCode_Horizon_È(Minecraft.áŒŠà().á.ŒÂ.Ø­áŒŠá, slot, mouseButton, shiftClick ? 1 : 0, Minecraft.áŒŠà().á);
    }
}
