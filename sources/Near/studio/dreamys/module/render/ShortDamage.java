package studio.dreamys.module.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShortDamage extends Module {
    public static final ArrayList<String> colors = new ArrayList<>(Arrays.asList("§r", "§e", "§6", "§c", "§c"));

    public ShortDamage() {
        super("Short Damage", Category.RENDER);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        if (e.entity instanceof EntityArmorStand) {
            Matcher m = Pattern.compile("✧(.*)✧").matcher(ChatFormatting.stripFormatting(e.entity.getName().toLowerCase()));
            //if crit damage found && not already formatted
            if (m.find() && !Pattern.compile("[a-z]").matcher(ChatFormatting.stripFormatting(e.entity.getName().toLowerCase())).find()) {
                e.entity.setCustomNameTag(formatDamage(Double.parseDouble(m.group(1))));
            }
        }
    }

    public static String formatDamage(double dmg) {
        //number formatting
        String formattedDamage;
        if (dmg >= 1000000000) formattedDamage = BigDecimal.valueOf(dmg / 1000000000).setScale(2, RoundingMode.HALF_UP).doubleValue() + "b"; //billions
        else if (dmg >= 1000000) formattedDamage = BigDecimal.valueOf(dmg / 1000000).setScale(2, RoundingMode.HALF_UP).doubleValue() + "m"; //millions
        else if (dmg >= 1000) formattedDamage = BigDecimal.valueOf(dmg / 1000).setScale(1, RoundingMode.HALF_UP).doubleValue() + "k"; //thousands
        else formattedDamage = Double.toString(dmg); //hundreds

        //color formatting (tried to replicate hypixel)
        StringBuilder sb = new StringBuilder(formattedDamage);
        for (int i = 0; i < formattedDamage.length(); i++) {
            sb.insert(i * 3, colors.get(i % 5));
        }

        return "✧" + sb + "§r✧";
    }
}
