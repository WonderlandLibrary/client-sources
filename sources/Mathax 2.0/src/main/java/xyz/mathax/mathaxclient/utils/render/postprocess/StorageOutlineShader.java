package xyz.mathax.mathaxclient.utils.render.postprocess;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.StorageESP;
import net.minecraft.entity.Entity;

public class StorageOutlineShader extends PostProcessShader {
    private static StorageESP storageESP;

    public StorageOutlineShader() {
        init("outline");
    }

    @Override
    protected void preDraw() {
        framebuffer.clear(false);
        framebuffer.beginWrite(false);
    }

    @Override
    protected boolean shouldDraw() {
        if (storageESP == null) {
            storageESP = Modules.get().get(StorageESP.class);
        }

        return storageESP.isShader();
    }

    @Override
    public boolean shouldDraw(Entity entity) {
        return true;
    }

    @Override
    protected void setUniforms() {
        shader.set("u_Width", storageESP.outlineWidthSetting.get());
        shader.set("u_FillOpacity", storageESP.fillOpacitySetting.get() / 255.0);
        shader.set("u_ShapeMode", storageESP.shapeModeSetting.get().ordinal());
        shader.set("u_GlowMultiplier", storageESP.glowMultiplierSetting.get());
    }
}