package dev.vertic.module.impl.other;

import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.BooleanSetting;
import dev.vertic.setting.impl.ModeSetting;
import net.minecraft.entity.player.EntityPlayer;

import java.util.regex.Pattern;

public class AntiBot extends Module {

    private final BooleanSetting tabCheck = new BooleanSetting("Tab Check", true);
    private final ModeSetting tabMode = new ModeSetting("Tab Mode", "Contains", "Contains", "Equals");
    private final BooleanSetting armorCheck = new BooleanSetting("Armor Check", false);

    private static final Pattern COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");

    public AntiBot() {
        super("AntiBot", "Excludes bots from certain modules.", Category.OTHER);
        this.addSettings(tabCheck, tabMode, armorCheck);
        this.enable();
    }

    public boolean isBot(EntityPlayer player) {
        if (!this.isEnabled()) {
            return false;
        }
        if (tabCheck.isEnabled()) {
            boolean equals = tabMode.getMode().equalsIgnoreCase("Equals");
            String targetName = stripColor(player.getDisplayName().getFormattedText());

            boolean shouldReturn = mc.getNetHandler().getPlayerInfoMap().stream()
                    .anyMatch(networkPlayerInfo -> {
                        String networkName = stripColor(networkPlayerInfo.getFullName());
                        if (equals) {
                            return targetName.equals(networkName);
                        } else {
                            return networkName.contains(targetName);
                        }
                    });
            return !shouldReturn;
        }
        if (armorCheck.isEnabled()) {
            return player.inventory.armorInventory[0] == null &&
                    player.inventory.armorInventory[1] == null &&
                    player.inventory.armorInventory[2] == null &&
                    player.inventory.armorInventory[3] == null;
        }
        return false;
    }

    /*
     * LiquidBounce Hacked Client
     * https://github.com/CCBlueX/LiquidBounce/
     */
    public String stripColor(String input) {
        return COLOR_PATTERN.matcher(input).replaceAll("");
    }

}
