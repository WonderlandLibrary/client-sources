package io.github.nevalackin.radium.module.impl.render;

import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.utils.render.Colors;
import io.github.nevalackin.radium.utils.render.RenderingUtils;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.function.Supplier;

import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(label = "Chams", category = ModuleCategory.RENDER)
public final class Chams extends Module {

    private static Chams cached;
    private final Property<Boolean> hurtEffectProperty = new Property<>("Hurt Effect", true);
    private final EnumProperty<HurtEffect> hurtEffectStyleProperty = new EnumProperty<>("Hurt Effect Style", HurtEffect.COLOR,
            hurtEffectProperty::getValue);
    private final Property<Boolean> handsProperty = new Property<>("Hands", true);
    private final Property<Boolean> occludedFlatProperty = new Property<>("Occluded Flat", true);
    private final Property<Boolean> visibleFlatProperty = new Property<>("Visible Flat", true);
    private final Property<Boolean> rainbow = new Property<>("Rainbow", true);
    private final Property<Integer> visibleColorProperty = new Property<>("Visible Color", Colors.RED);
    private final Property<Integer> occludedColorProperty = new Property<>("Occluded Color", Colors.GREEN);
    private final Property<Integer> hurtEffectColorProperty = new Property<>("Hurt Effect Color", Colors.PURPLE,
            () -> hurtEffectProperty.getValue() && hurtEffectStyleProperty.isSelected(HurtEffect.COLOR));
    private final Property<Integer> handsColorProperty = new Property<Integer>("Hands Color", Colors.RED, handsProperty::getValue);
    private float[] visibleColor;
    private float[] occludedColor;
    private float[] hurtEffectColor;
    private float[] handsColor;


    public Chams() {
        visibleColorProperty.addValueChangeListener(((oldValue, value) -> visibleColor = getRGB(value)));
        occludedColorProperty.addValueChangeListener(((oldValue, value) -> occludedColor = getRGB(value)));
        hurtEffectColorProperty.addValueChangeListener(((oldValue, value) -> hurtEffectColor = getRGB(value)));
        handsColorProperty.addValueChangeListener(((oldValue, value) -> handsColor = getRGB(value)));
    }

    public static void preRender(Chams instance, Entity entity) {
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        if (instance.occludedFlatProperty.getValue())
            glDisable(GL_LIGHTING);
        glEnable(GL_POLYGON_OFFSET_FILL);
        glPolygonOffset(0.0F, -1000000.0F);
        OpenGlHelper.setLightmapTextureCoords(1, 240.0F, 240.0F);
        glDepthMask(false);

        if (instance.rainbow.getValue()) {
            int rgb = RenderingUtils.getRainbowFromEntity(entity, 6000, false);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = (rgb) & 0xFF;
            int a = (int) (instance.occludedColor[3] * 255.0F);
            rgb = ((r & 0xFF) << 16) |
                    ((g & 0xFF) << 8) |
                    (b & 0xFF) |
                    ((a & 0xFF) << 24);
            RenderingUtils.color(rgb);
        } else {
            float[] rgb = instance.occludedColor;
            glColor4f(rgb[0], rgb[1], rgb[2], rgb[3]);
        }
    }

    public static void render(Chams instance, Entity entity) {
        glDepthMask(true);
        if (instance.occludedFlatProperty.getValue() && !instance.visibleFlatProperty.getValue())
            glEnable(GL_LIGHTING);
        else if (!instance.occludedFlatProperty.getValue() && instance.visibleFlatProperty.getValue())
            glDisable(GL_LIGHTING);

        if (instance.rainbow.getValue()) {
            int rgb = RenderingUtils.getRainbowFromEntity(entity, 6000, true);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = (rgb) & 0xFF;
            int a = (int) (instance.visibleColor[3] * 255.0F);
            rgb = ((r & 0xFF) << 16) |
                    ((g & 0xFF) << 8) |
                    (b & 0xFF) |
                    ((a & 0xFF) << 24);
            RenderingUtils.color(rgb);
        } else {
            float[] rgb = instance.visibleColor;
            glColor4f(rgb[0], rgb[1], rgb[2], rgb[3]);
        }
        glDisable(GL_POLYGON_OFFSET_FILL);
    }

    public static void postRender(Chams instance) {
        if (instance.visibleFlatProperty.getValue())
            glEnable(GL_LIGHTING);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void preHandRender() {
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glDisable(GL_LIGHTING);
        float[] rgb = getInstance().handsColor;
        glColor4f(rgb[0], rgb[1], rgb[2], rgb[3]);
    }

    public static void postHandRender() {
        glEnable(GL_LIGHTING);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    private static float[] getRGB(int hex) {
        return new float[]{
                (float) (hex >> 16 & 255) / 255.0F,
                (float) (hex >> 8 & 255) / 255.0F,
                (float) (hex & 255) / 255.0F,
                (float) (hex >> 24 & 255) / 255.0F
        };
    }

    public static boolean shouldRenderHand() {
        return getInstance().isEnabled() &&
                getInstance().handsProperty.getValue();
    }

    public static boolean isRendering(EntityLivingBase entity) {
        return getInstance().isEnabled() && isValid(entity);
    }

    public static boolean isChamsEnabled() {
        return getInstance().isEnabled();
    }

    public static boolean isRenderHurtEffect() {
        return getInstance().hurtEffectProperty.getValue();
    }

    public static HurtEffect getHurtEffect() {
        return getInstance().hurtEffectStyleProperty.getValue();
    }

    public static boolean isValid(EntityLivingBase entity) {
        return !entity.isInvisible() &&
                entity.isEntityAlive() &&
                entity instanceof EntityPlayer;
    }

    public static Chams getInstance() {
        if (cached != null) return cached;
        else return (cached = ModuleManager.getInstance(Chams.class));
    }

    public enum HurtEffect {
        OLD(() -> 1.0F,
                () -> 0.0F,
                () -> 0.0F,
                () -> 0.4F),
        NEW(() -> 1.0F,
                () -> 0.0F,
                () -> 0.0F,
                () -> 0.3F),
        COLOR(() -> cached.hurtEffectColor[0],
                () -> cached.hurtEffectColor[1],
                () -> cached.hurtEffectColor[2],
                () -> 0.3F);

        private final Supplier<Float> red;
        private final Supplier<Float> green;
        private final Supplier<Float> blue;
        private final Supplier<Float> alpha;

        HurtEffect(Supplier<Float> red, Supplier<Float> g, Supplier<Float> b, Supplier<Float> a) {
            this.red = red;
            this.green = g;
            this.blue = b;
            this.alpha = a;
        }

        public float getRed() {
            return red.get();
        }

        public float getGreen() {
            return green.get();
        }

        public float getBlue() {
            return blue.get();
        }

        public float getAlpha() {
            return alpha.get();
        }
    }
}
