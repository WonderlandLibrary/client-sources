package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.client.renderering.EventShader;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;
import wtf.diablo.client.font.FontRepository;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.api.management.IModule;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.MultiModeSetting;
import wtf.diablo.client.setting.impl.NumberSetting;

import java.awt.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@ModuleMetaData(
        name = "ArrayList",
        description = "Displays a list of enabled modules",
        category = ModuleCategoryEnum.RENDER)
public final class ArrayListModule extends AbstractModule {
    private final NumberSetting<Integer> xOff = new NumberSetting<>("X Offset", 0, 0, 30, 1);
    private final NumberSetting<Integer> yOff = new NumberSetting<>("Y Offset", 0, 0, 30, 1);
    private final MultiModeSetting<ArrayListBoarders> boardersMode = new MultiModeSetting<>("Borders", ArrayListBoarders.values());
    private final NumberSetting<Integer> colorOffset = new NumberSetting<>("Color Offset", 150, 50, 300, 10);
    private final NumberSetting<Long> colorSpeed = new NumberSetting<>("Color Speed", 3000L, 100L, 10000L, 100L);
    private final BooleanSetting background = new BooleanSetting("Background", true);
    private final ModeSetting<CasingMode> casingMode = new ModeSetting<>("Casing Mode", CasingMode.DEFAULT);
    private final ModeSetting<SuffixMode> suffixMode = new ModeSetting<>("Suffix Mode", SuffixMode.NONE);
    private final BooleanSetting hideRender = new BooleanSetting("Hide Render", false);
    private final NumberSetting<Integer> opacity = new NumberSetting<>("Opacity", 80, 0, 255, 1);
    private final NumberSetting<Integer> fontHeight = new NumberSetting<>("Font Height", 2, 1, 5, 1);
    private final BooleanSetting font = new BooleanSetting("Font", false);

    private final BooleanSetting syncBloomColorWithHud = new BooleanSetting("Sync Bloom", false);

    public ArrayListModule() {
        this.toggle(true);
        this.registerSettings(xOff, yOff, boardersMode, colorOffset, colorSpeed, background, casingMode, suffixMode, hideRender, opacity, fontHeight, syncBloomColorWithHud);
    }

    private int y;

    @EventHandler
    private final Listener<EventShader> eventShaderListener = event -> {
        //ArrayList
        final Collection<IModule> abstractModuleCollection = Diablo.getInstance().getModuleRepository().getModules().stream().sorted(Comparator.comparing(module -> font.getValue() ? FontRepository.SFREG18.getWidth(((AbstractModule) module).getDisplayName()) : mc.fontRendererObj.getStringWidth(((AbstractModule) module).getDisplayName())).reversed()).filter((mod) -> (mod).isAnimating() || mod.isEnabled()).collect(Collectors.toList());

        final AtomicInteger y = new AtomicInteger(yOff.getValue());
        final AtomicInteger count = new AtomicInteger();
        final AtomicInteger lastInt = new AtomicInteger(-1);
        final AtomicInteger lessMod = new AtomicInteger(1);
        final ScaledResolution scaledResolution = new ScaledResolution(mc);

        abstractModuleCollection.forEach(abstractModule -> {
            if (abstractModule instanceof ArrayListModule || abstractModule instanceof ClickGuiModule || abstractModule instanceof HudModule || (hideRender.getValue() && abstractModule.getData().category() == ModuleCategoryEnum.RENDER) || ((AbstractModule) abstractModule).isHidden()) {
                lessMod.getAndAdd(1);
                return;
            }

            ((AbstractModule) abstractModule).updateRender();

            final int color = ColorModule.getColor(colorOffset.getValue() * 2 * count.getAndIncrement());

            String display = abstractModule.getDisplayName();


            final int width = font.getValue() ? (int) (FontRepository.SFREG18.getWidth(display) + 3) : mc.fontRendererObj.getStringWidth(display) + 3;
            final int height = font.getValue() ? (int) (FontRepository.SFREG18.getHeight(display) + fontHeight.getValue()) : mc.fontRendererObj.FONT_HEIGHT + fontHeight.getValue();

            int x = scaledResolution.getScaledWidth() - width - xOff.getValue();
            int arrY = y.get();

            if (((AbstractModule) abstractModule).isAnimating()) {
                float diffX = scaledResolution.getScaledWidth() - x + 4;
                x -= (((AbstractModule) abstractModule).getAnimation().getPercent() / 100f - 1) * diffX;

                float diffY = 12;
                arrY += (((AbstractModule) abstractModule).getAnimation().getPercent() / 100f - 1) * diffY;
            }

            final int leftSide = x - 2;
            final int bottom = arrY + height;

            if (background.getValue())
                Gui.drawRect(leftSide, arrY, x + width, bottom, new Color(0, 0, 0, 255).getRGB());

            mc.fontRendererObj.drawStringWithShadow(display, x + 1, arrY + 1, syncBloomColorWithHud.getValue() ? color : new Color(0, 0, 0, 255).getRGB());

            y.set(arrY + height);

            lastInt.set(leftSide);

            this.y = y.get();
        });

    };

    @EventHandler
    private final Listener<OverlayEvent> overlayEvent = event -> {
        //ArrayList
        final Collection<IModule> abstractModuleCollection = Diablo.getInstance().getModuleRepository().getModules().stream().sorted(Comparator.comparing(module -> font.getValue() ? FontRepository.SFREG18.getWidth(((AbstractModule) module).getDisplayName()) : mc.fontRendererObj.getStringWidth(((AbstractModule) module).getDisplayName())).reversed()).filter((mod) -> (mod).isAnimating() || mod.isEnabled()).collect(Collectors.toList());

        final AtomicInteger y = new AtomicInteger(yOff.getValue());
        final AtomicInteger count = new AtomicInteger();
        final AtomicInteger lastInt = new AtomicInteger(-1);
        final AtomicInteger lessMod = new AtomicInteger(1);

        abstractModuleCollection.forEach(abstractModule -> {
            if (abstractModule instanceof ArrayListModule || abstractModule instanceof ClickGuiModule || abstractModule instanceof HudModule || (hideRender.getValue() && abstractModule.getData().category() == ModuleCategoryEnum.RENDER) || ((AbstractModule) abstractModule).isHidden()) {
                lessMod.getAndAdd(1);
                return;
            }

            ((AbstractModule) abstractModule).updateRender();

            final int color = ColorModule.getColor(colorOffset.getValue() * 2 * count.getAndIncrement());

            String display = abstractModule.getDisplayName();


            final int width = font.getValue() ? (int) (FontRepository.SFREG18.getWidth(display) + 3) : mc.fontRendererObj.getStringWidth(display) + 3;
            final int height = font.getValue() ? (int) (FontRepository.SFREG18.getHeight(display) + fontHeight.getValue()) : mc.fontRendererObj.FONT_HEIGHT + fontHeight.getValue();

            int x = event.getScaledResolution().getScaledWidth() - width - xOff.getValue();
            int arrY = y.get();

            if (((AbstractModule) abstractModule).isAnimating()) {
                float diffX = event.getScaledResolution().getScaledWidth() - x + 4;
                x -= (((AbstractModule) abstractModule).getAnimation().getPercent() / 100f - 1) * diffX;

                float diffY = 12;
                arrY += (((AbstractModule) abstractModule).getAnimation().getPercent() / 100f - 1) * diffY;
            }

            final int leftSide = x - 2;
            final int bottom = arrY + height;

            if (background.getValue())
                Gui.drawRect(leftSide, arrY, x + width, bottom, new Color(0, 0, 0, opacity.getValue()).getRGB());

            if (boardersMode.containsValue(ArrayListBoarders.LEFT)) {
                Gui.drawRect(leftSide - 1, arrY, leftSide, bottom, color);
            }

            if (boardersMode.containsValue(ArrayListBoarders.RIGHT)) {
                Gui.drawRect(x + width, arrY, x + width + 1, bottom, color);
            }

            if (this.font.getValue()) {
                FontRepository.SFREG18.drawStringWithShadow(display, x + 1, this.casingMode.getValue() == CasingMode.LOWERCASE ? arrY : arrY + 1, color);
            } else {
                mc.fontRendererObj.drawStringWithShadow(display, x + 1, this.casingMode.getValue() == CasingMode.LOWERCASE ? arrY : arrY + 1, color);
            }
            y.set(arrY + height);

            final boolean lastModule = abstractModuleCollection.size() - lessMod.get() == count.get() - 1;

            if (lastInt.get() != -1 && boardersMode.containsValue(ArrayListBoarders.IN_BETWEEN)) {
                if (!((AbstractModule) abstractModule).isAnimating())
                    Gui.drawRect(lastInt.get() - (boardersMode.containsValue(ArrayListBoarders.LEFT) ? 1 : 0), arrY, leftSide, arrY + 1, color);
            }

            if (lastModule && boardersMode.containsValue(ArrayListBoarders.BOTTOM)) {
                Gui.drawRect(leftSide - (boardersMode.containsValue(ArrayListBoarders.LEFT) ? 1 : 0), bottom, x + width + (boardersMode.containsValue(ArrayListBoarders.RIGHT) ? 1 : 0), bottom + 1, color);
            }

            if (boardersMode.containsValue(ArrayListBoarders.TOP) && count.get() == 1) {
                Gui.drawRect(leftSide - (boardersMode.containsValue(ArrayListBoarders.LEFT) ? 1 : 0), arrY, x + width + (boardersMode.containsValue(ArrayListBoarders.RIGHT) ? 1 : 0), arrY + 1, color);
            }

            lastInt.set(leftSide);

            this.y = y.get();
        });

    };

    public int getY() {
        return y;
    }

    public Number getColorSpeed() {
        return colorSpeed.getValue();
    }

    public SuffixMode getSuffixMode() {
        return suffixMode.getValue();
    }

    public CasingMode getCasingMode() {
        return casingMode.getValue();
    }

    enum ArrayListBoarders implements IMode {
        LEFT("Left"),
        RIGHT("Right"),
        TOP("Top"),
        BOTTOM("Bottom"),
        IN_BETWEEN("In Between");

        private final String name;

        ArrayListBoarders(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum CasingMode implements IMode {
        DEFAULT("Default"),
        LOWERCASE("Lowercase"),
        UPPERCASE("Uppercase");

        private final String name;

        CasingMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum SuffixMode implements IMode {
        NONE("None"),
        DASH("Dash"),
        COLON("Colon"),
        BRACKET("Bracket"),
        PARENTHESIS("Parenthesis");

        private final String name;

        SuffixMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
