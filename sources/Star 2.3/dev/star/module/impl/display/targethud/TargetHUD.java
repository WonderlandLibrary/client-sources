package dev.star.module.impl.display.targethud;

import dev.star.utils.Utils;
import dev.star.utils.objects.GradientColorWheel;
import dev.star.utils.render.GLUtil;
import lombok.Getter;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;

import java.util.HashMap;

public abstract class TargetHUD implements Utils {

    protected GradientColorWheel colorWheel;
    private float width, height;
    @Getter
    private final String name;

    public TargetHUD(String name) {
        this.name = name;
    }

    public void setColorWheel(GradientColorWheel colorWheel) {
        this.colorWheel = colorWheel;
    }

    protected void renderPlayer2D(float x, float y, float width, float height, AbstractClientPlayer player) {
        GLUtil.startBlend();
        mc.getTextureManager().bindTexture(player.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(x, y, (float) 8.0, (float) 8.0, 8, 8, width, height, 64.0F, 64.0F);
        GLUtil.endBlend();
    }

    public abstract void render(float x, float y, float alpha, EntityLivingBase target);

    public abstract void renderEffects(float x, float y, float alpha);

    private static final HashMap<Class<? extends TargetHUD>, TargetHUD> targetHuds = new HashMap<>();

    public static TargetHUD get(String name) {
        return targetHuds.values().stream().filter(hud -> hud.getName().equals(name)).findFirst().orElse(null);
    }

    public static <T extends TargetHUD> T get(Class<T> clazz) {
        return (T) targetHuds.get(clazz);
    }

    public static void init() {
        targetHuds.put(OldStarTargetHUD.class, new OldStarTargetHUD());
        targetHuds.put(RavenTargetHUD.class, new RavenTargetHUD());
        targetHuds.put(AkrienTargetHUD.class, new AkrienTargetHUD());
        targetHuds.put(AstolfoTargetHUD.class, new AstolfoTargetHUD());
        targetHuds.put(NovolineTargetHUD.class, new NovolineTargetHUD());
        targetHuds.put(StarTargetHUD.class, new StarTargetHUD());
        targetHuds.put(ExireTargetHUD.class, new ExireTargetHUD());
        targetHuds.put(JelloTargetHUD.class, new JelloTargetHUD());
        targetHuds.put(StraightTargetHUD.class, new StraightTargetHUD());
        targetHuds.put(ZeroDayTargetHUD.class, new ZeroDayTargetHUD());
        targetHuds.put(EchoTargetHUD.class, new EchoTargetHUD());
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
