package studio.dreamys.mixin.gui;

import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = GuiIngameForge.class, remap = false)
public abstract class MixinGuiIngameForge {

    @Overwrite
    protected void renderArmor(int width, int height) {

    }

    @Overwrite
    protected void renderAir(int width, int height) {

    }

    @Overwrite
    public void renderFood(int width, int height) {

    }

    @Overwrite
    protected void renderJumpBar(int width, int height) {

    }

    @Overwrite
    protected void renderHealthMount(int width, int height) {

    }
}
