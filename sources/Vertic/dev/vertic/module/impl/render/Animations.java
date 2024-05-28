package dev.vertic.module.impl.render;

import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.ModeSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;

public class Animations extends Module {

    public final ModeSetting mode = new ModeSetting("Mode", "1.8", "1.7", "1.8", "LiquidBounce", "LoL", "Exhibition", "Exhibition 2", "Swong");

    public Animations() {
        super("Animations", "Does funny animations lol.", Category.RENDER);
        this.addSettings(mode);
    }

    @Override
    public String getSuffix() {
        return mode.getMode();
    }

}
