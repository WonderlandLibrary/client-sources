package mods.dmi.main;

import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.Module;
import de.labystudio.utils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class DamageIndicator extends Module
{
    public static DMIListener listener = new DMIListener();
    public static boolean blocked = false;

    public void onEnable()
    {
        Settings.loadProperties();
        ModAPI.registerListener(listener);
        ModAPI.addSettingsButton("DamageIndicator", new GuiSettings());
    }

    public void onDisable()
    {
    }

    public void pluginMessage(String key, boolean value)
    {
        if (key.equalsIgnoreCase("damageindicator"))
        {
            blocked = !value;
        }
    }

    public static boolean allowed(String ip)
    {
        ip = ip.toLowerCase();
        return blocked ? false : (ip.contains("timolia") ? false : (ip.contains("gommehd") ? false : (ip.contains("hypixel") ? false : (ip.contains("rewinside") ? false : (ip.contains("mineplex") ? false : (ip.contains("underthedom") ? false : (ip.contains("wave-mc") ? false : (ip.contains("91.121.83.112") ? false : (ip.contains("deinprojekthost") ? false : (ip.contains("miniminerlps") ? false : (ip.contains("dph-games") ? false : !ip.contains("bergwerklabs"))))))))))));
    }

    public static void disableMessage(String ip)
    {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(Color.cl("c") + "Damage Indicator is not allowed on " + ip + "!"));
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(Color.cl("c") + "Your Damage Indicator is now " + Color.cl("n") + "disabled" + Color.cl("c") + "."));
    }
}
