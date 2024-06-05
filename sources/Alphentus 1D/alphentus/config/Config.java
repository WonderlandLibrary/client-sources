package alphentus.config;

import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModManager;
import alphentus.mod.mods.combat.KillAura;
import alphentus.mod.mods.combat.Teams;
import alphentus.mod.mods.combat.Velocity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/**
 * @author avox | lmao
 * @since on 07.08.2020.
 */
public class Config {

    public final ModManager modManager = Init.getInstance().modManager;
    public final Minecraft mc = Minecraft.getMinecraft();

    String name;

    public Config(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void loadConfig() {
        mc.thePlayer.addChatMessage(new ChatComponentText("Config loaded: " + getName()));
    }
}