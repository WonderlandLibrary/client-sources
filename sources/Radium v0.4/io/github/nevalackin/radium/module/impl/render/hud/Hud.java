package io.github.nevalackin.radium.module.impl.render.hud;

import io.github.nevalackin.radium.RadiumClient;
import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.event.impl.render.Render2DEvent;
import io.github.nevalackin.radium.event.impl.render.WindowResizeEvent;
import io.github.nevalackin.radium.gui.font.FontRenderer;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.Colors;
import io.github.nevalackin.radium.utils.render.LockedResolution;
import io.github.nevalackin.radium.utils.render.RenderingUtils;
import io.github.nevalackin.radium.utils.render.Translate;
import me.zane.basicbus.api.annotations.Listener;
import me.zane.basicbus.api.annotations.Priority;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ModuleInfo(label = "HUD", category = ModuleCategory.RENDER)
public final class Hud extends Module {

    private static final int MODULE_SPACING = 11;

    private final Property<Boolean> cFontProperty = new Property<>("CFont", true);

    public static final Property<String> watermarkText = new Property<>("Watermark Text", RadiumClient.NAME.charAt(0) + "\247R\247F" +
            RadiumClient.NAME.substring(1) + " " + RadiumClient.VERSION);

    private final Property<Boolean> watermarkProperty = new Property<>("Watermark", true);
    private final Property<Boolean> arrayListProperty = new Property<>("ArrayList", true);

    private final DoubleProperty fadeSpeedProperty = new DoubleProperty("Fade Speed", 1.0,
            () -> arrayListProperty.getValue() || watermarkProperty.getValue(),
            0.1, 5, 0.1);

    private final EnumProperty<ArrayListColorMode> arrayListColorModeProperty = new EnumProperty<>("Color Mode",
            ArrayListColorMode.FADE);
    private final Property<Integer> arrayListColorProperty = new Property<>("ArrayList Color", Colors.BLUE,
            () -> arrayListProperty.getValue() && arrayListColorModeProperty.getValue() != ArrayListColorMode.RAINBOW);
    private final Property<Integer> secondaryArrayListColorProperty = new Property<>("Second ArrayList Color", Colors.BLUE,
            () -> arrayListProperty.getValue() && arrayListColorModeProperty.getValue() == ArrayListColorMode.BLEND);

    private final Property<Integer> watermarkColorProperty = new Property<>("Watermark Color", Colors.PINK,
            arrayListProperty::getValue);

    private final Property<Boolean> arrayListBackgroundProperty = new Property<>("Background", true,
            arrayListProperty::getValue);
    private final Property<Boolean> arrayListLineProperty = new Property<>("Line", true,
            arrayListProperty::getValue);

    private final Property<Boolean> potionsProperty = new Property<>("Potions", true);
    private final EnumProperty<SortingMode> sortingModeProperty = new EnumProperty<>("Sorting Mode", SortingMode.LENGTH);
    private List<Module> moduleCache;

    public Hud() {
        toggle();
        cFontProperty.addValueChangeListener(((oldValue, value) ->
                sortingModeProperty.getValue().getSorter().setFontRenderer(getFontRenderer())));
    }

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre())
            moduleCache.sort(sortingModeProperty.getValue().getSorter());
    }

    private void updateModulePositions(ScaledResolution scaledResolution) {
        if (moduleCache == null) {
            setupModuleCache();
        }

        int y = 1;
        for (Module module : moduleCache) {
            if (module.isEnabled())
                module.getTranslate().setX(scaledResolution.getScaledWidth() -
                        Wrapper.getFontRenderer().getWidth(module.getDisplayLabel()) - 2);
            else
                module.getTranslate().setX(scaledResolution.getScaledWidth());
            module.getTranslate().setY(y);
            if (module.isEnabled())
                y += MODULE_SPACING;
        }
    }

    @Listener
    public void onWindowResizeEvent(WindowResizeEvent event) {
        updateModulePositions(event.getScaledResolution());
    }

    private void setupModuleCache() {
        moduleCache = new ArrayList<>(RadiumClient.getInstance().getModuleManager().getModules());
    }

    private FontRenderer getFontRenderer() {
        return cFontProperty.getValue() ?
                Wrapper.getFontRenderer() :
                Wrapper.getMinecraftFontRenderer();
    }

    @Listener(Priority.HIGH)
    public void onRenderOverlay(Render2DEvent e) {
        int color = 0x500D0D0D;
        LockedResolution lockedResolution = e.getResolution();
        int screenX = lockedResolution.getWidth();
        int screenY = lockedResolution.getHeight();

        FontRenderer fontRenderer = getFontRenderer();

        if (potionsProperty.getValue()) {
            int potionY = 11;
            for (PotionEffect effect : Wrapper.getPlayer().getActivePotionEffects()) {
                Potion potion = Potion.potionTypes[effect.getPotionID()];
                String effectName = I18n.format(
                        potion.getName()) + " " +
                        (effect.getAmplifier() + 1) +
                        " \2477" +
                        Potion.getDurationString(effect);
                fontRenderer.drawStringWithShadow(effectName,
                        screenX - 2 - fontRenderer.getWidth(effectName),
                        screenY - potionY,
                        potion.getLiquidColor());

                potionY += fontRenderer.getHeight(effectName);
            }
        }

        float speed = fadeSpeedProperty.getValue().floatValue();

        long ms = (long) (speed * 1000L);

        float darkFactor = 0.7F * 0.7F;

        long currentMillis = -1;

        if (watermarkProperty.getValue()) {
            currentMillis = System.currentTimeMillis();

            final int watermarkColor = watermarkColorProperty.getValue();
            fontRenderer.drawStringWithShadow(watermarkText.getValue(), 2, 2,
                    fadeBetween(
                            watermarkColor,
                            darker(watermarkColor, darkFactor),
                            currentMillis % ms / (ms / 2.0F)));
        }

        if (arrayListProperty.getValue()) {
            if (currentMillis == -1)
                currentMillis = System.currentTimeMillis();

            int arrayListColor = arrayListColorProperty.getValue();
            int sArrayListColor = secondaryArrayListColorProperty.getValue();

            if (moduleCache == null)
                updateModulePositions(RenderingUtils.getScaledResolution());

            final int moduleSpacing = MODULE_SPACING;
            int heightOffset = 9;
            int y = 2;

            int i = 0;

            for (Module module : moduleCache) {
                Translate translate = module.getTranslate();
                String name = module.getDisplayLabel();
                if (module.isEnabled() && !module.isHidden()) {
                    float moduleWidth = fontRenderer.getWidth(name);
                    translate.animate(screenX - moduleWidth - (arrayListLineProperty.getValue() ? 2 : 1), y);
                    y += moduleSpacing;
                } else {
                    translate.animate(screenX, y);
                }

                boolean shown = translate.getX() < screenX;

                if (shown) {
                    int wColor = -1;
                    final float offset = (currentMillis + (i * 100)) % ms / (ms / 2.0F);
                    switch (arrayListColorModeProperty.getValue()) {
                        case FADE:
                            wColor = fadeBetween(
                                    arrayListColor,
                                    darker(arrayListColor, darkFactor),
                                    offset);
                            break;
                        case BLEND:
                            wColor = fadeBetween(
                                    arrayListColor,
                                    sArrayListColor,
                                    offset);
                            break;
                        case STATIC:
                            wColor = arrayListColor;
                            break;
                        case RAINBOW:
                            wColor = RenderingUtils.getRainbow(3000, i);
                            break;
                    }
                    if (arrayListBackgroundProperty.getValue())
                        Gui.drawRect(translate.getX() - 1, translate.getY() - (moduleSpacing - heightOffset), screenX, translate.getY() + heightOffset, color);
                    fontRenderer.drawStringWithShadow(name, (float) translate.getX(), (float) translate.getY(), wColor);
                    if (arrayListLineProperty.getValue())
                        Gui.drawRect(screenX - 1, translate.getY() - 2, screenX, translate.getY() + moduleSpacing - 2, wColor);
                    i++;
                }
            }
        }
    }

    private int darker(int color, float factor) {
        int r = (int) ((color >> 16 & 0xFF) * factor);
        int g = (int) ((color >> 8 & 0xFF) * factor);
        int b = (int) ((color & 0xFF) * factor);
        int a = color >> 24 & 0xFF;

        return ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                (b & 0xFF) |
                ((a & 0xFF) << 24);
    }

    private int fadeBetween(int color1, int color2, float offset) {
        if (offset > 1)
            offset = 1 - offset % 1;

        double invert = 1 - offset;
        int r = (int) ((color1 >> 16 & 0xFF) * invert +
                (color2 >> 16 & 0xFF) * offset);
        int g = (int) ((color1 >> 8 & 0xFF) * invert +
                (color2 >> 8 & 0xFF) * offset);
        int b = (int) ((color1 & 0xFF) * invert +
                (color2 & 0xFF) * offset);
        int a = (int) ((color1 >> 24 & 0xFF) * invert +
                (color2 >> 24 & 0xFF) * offset);
        return ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                (b & 0xFF);
    }

    private enum ArrayListColorMode {
        FADE, BLEND, RAINBOW, STATIC
    }

    private enum SortingMode {
        LENGTH(new LengthComparator()),
        ALPHABETICAL(new AlphabeticalComparator());

        private final ModuleComparator sorter;

        SortingMode(ModuleComparator sorter) {
            this.sorter = sorter;
        }

        public ModuleComparator getSorter() {
            return sorter;
        }
    }

    private abstract static class ModuleComparator implements Comparator<Module> {
        protected FontRenderer fontRenderer;

        public FontRenderer getFontRenderer() {
            return fontRenderer;
        }

        public void setFontRenderer(FontRenderer fontRenderer) {
            this.fontRenderer = fontRenderer;
        }

        @Override
        public abstract int compare(Module o1, Module o2);
    }

    private static class LengthComparator extends ModuleComparator {
        @Override
        public int compare(Module o1, Module o2) {
            return ((int) fontRenderer.getWidth(o2.getDisplayLabel()) -
                    (int) fontRenderer.getWidth(o1.getDisplayLabel()));
        }
    }

    private static class AlphabeticalComparator extends ModuleComparator {
        @Override
        public int compare(Module o1, Module o2) {
            String n = o1.getDisplayLabel();
            String n1 = o2.getDisplayLabel();
            if (n.equals(n1)) return 0;
            if (n.length() == 0 || n1.length() == 0) return 0;
            return n.charAt(0) - n1.charAt(0);
        }
    }
}
