package studio.dreamys.module.render;

import net.minecraft.client.Minecraft;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", Category.RENDER);
        if (Minecraft.getMinecraft().gameSettings.gammaSetting != 1 && Minecraft.getMinecraft().gameSettings.gammaSetting != 15) {
            Minecraft.getMinecraft().gameSettings.gammaSetting = 1;
        }
    }

    @Override
    public void onEnable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 1000;
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 1;
    }
}
