package dev.excellent.client.module.impl.render;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.api.interfaces.shader.IShader;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.shader.factory.RenderFactory;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.DragValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import dev.excellent.impl.value.mode.SubMode;
import i.gishreloaded.protection.annotation.Native;
import lombok.Getter;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2d;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ModuleInfo(name = "Array List", description = "Получает список активных модулей.", category = Category.RENDER, autoEnabled = true)
public class ArrayList extends Module implements IShader {
    public static Singleton<ArrayList> singleton = Singleton.create(() -> Module.link(ArrayList.class));

    public BooleanValue moduleCase = new BooleanValue("Без заглавных", this, true);
    public BooleanValue moduleSplit = new BooleanValue("Без пробелов", this, true);
    public BooleanValue line = new BooleanValue("Линия", this, true);
    public BooleanValue split = new BooleanValue("Показывание модов", this, true);

    public ModeValue splitType = new ModeValue("Разделение модов", this, () -> !split.getValue()).add(SubMode.of("/", "->", ">", "-"));

    public MultiBooleanValue shaders = new MultiBooleanValue("Шейдеры", this)
            .add(
                    new BooleanValue("Блюр", false),
                    new BooleanValue("Блум", false)
            );
    public ModeValue modulesToShow = new ModeValue("Отображать", this) {{
        add(new SubMode("Всё"));
        add(new SubMode("Всё кроме рендера"));
        add(new SubMode("Только бинды"));
        setDefault("Всё кроме рендера");
    }};
    private final DragValue drag = new DragValue("Position", this, new Vector2d(5, 60));
    private List<ModuleComponent> allModuleComponent = new java.util.ArrayList<>();
    public List<ModuleComponent> activeModuleComponent = new java.util.ArrayList<>();
    private final Cache<ModuleListCacheKey, List<ModuleComponent>> moduleListCache = Caffeine.newBuilder().build();
    private final Cache<SortedListCacheKey, List<ModuleComponent>> sortedListCache = Caffeine.newBuilder().build();
    private final Font inter16 = Fonts.INTER_MEDIUM.get(16);
    private final Font inter12 = Fonts.INTER_BOLD.get(12);

    public ArrayList() {
        createModuleList();
        sortArrayList();
    }

    @Override
    public void toggle() {
        super.toggle();
    }

    private final Listener<Render2DEvent> onRender2D = event -> {
        if (mc.gameSettings.showDebugInfo) return;
        sortArrayList();
        float posX, posY;
        posX = (float) drag.position.x;
        posY = (float) drag.position.y;

        float width, height;
        width = (float) activeModuleComponent
                .stream()
                .mapToDouble(module -> inter16.getWidth(getModuleName(module)) + 5)
                .max()
                .orElse(0);
        height = (inter16.getHeight() + 2) * (activeModuleComponent
                .stream()
                .toList()
                .size());

        drag.size.set(width, height);

        MatrixStack matrix = event.getMatrix();

        boolean leftSide = (posX + width / 2F) < scaled().x / 2F;

        float multAlpha = 0.5F;
        float multDark = 0.3333F;

        if (shaders.isEnabled("Блюр")) {
            BLUR_SHADER.addTask2D(() -> drawBackground(matrix, posX, posY, width, leftSide, false, 1, 1));
        }
        if (shaders.isEnabled("Блум")) {
            BLOOM_SHADER.addTask2D(() -> drawBackground(matrix, posX, posY, width, leftSide, false, 1, 0.3333F));
        }

        RenderFactory.addTask(() -> {
            drawBackground(matrix, posX, posY, width, leftSide, line.getValue(), multAlpha, multDark);

            float offseta = 0;
            for (ModuleComponent module : activeModuleComponent) {

                String name = getModuleName(module);
                float append = inter16.getHeight() + 2;
                float yPos = posY + offseta;

                int color = ColorUtil.overCol(getTheme().getClientColor(-(int) (offseta * 2.5F + append * 2.5F * append), 1F), -1);
                if (leftSide) {
                    inter16.drawShadow(matrix, name, posX + 2.5F, (int) yPos + 1, color);
                } else {
                    inter16.drawShadow(matrix, name, posX + width - 2.5F - inter16.getWidth(name), (int) yPos + 1, color);
                }


                offseta += append;
            }
        });
    };

    private void drawBackground(MatrixStack matrix, float posX, float posY, float width, boolean leftSide, boolean line, float multAlpha, float multDark) {
        float offsetb = 0;
        for (ModuleComponent module : activeModuleComponent) {

            String name = getModuleName(module);
            float moduleWidth = inter16.getWidth(name) + 5F;
            float append = inter16.getHeight() + 2;
            float yPos = posY + offsetb;

            int color1 = getTheme().getClientColor(-(int) (offsetb * 2.5F), 1F);
            int color2 = getTheme().getClientColor(-(int) (offsetb * 2.5F + append * 2.5F), 1F);

            if (leftSide) {
                RectUtil.drawRect(matrix, posX, yPos, posX + moduleWidth, yPos + append, ColorUtil.multAlpha(color1, multAlpha), ColorUtil.multAlpha(ColorUtil.multDark(color1, multDark), multAlpha), ColorUtil.multAlpha(ColorUtil.multDark(color2, multDark), multAlpha), ColorUtil.multAlpha(color2, multAlpha), false, false);
            } else {
                RectUtil.drawRect(matrix, posX + width - moduleWidth, yPos, posX + width, yPos + append, ColorUtil.multAlpha(ColorUtil.multDark(color1, multDark), multAlpha), ColorUtil.multAlpha(color1, multAlpha), ColorUtil.multAlpha(color2, multAlpha), ColorUtil.multAlpha(ColorUtil.multDark(color2, multDark), multAlpha), false, false);
            }
            if (line) {
                float lineX = posX + (leftSide ? -1 : width);
                RectUtil.drawGradientV(matrix, lineX, yPos, lineX + 1F, yPos + append, color1, color2, true);
            }
            offsetb += append;
        }
    }

    private String getModuleName(ModuleComponent moduleComponent) {
        String moduleName = moduleComponent.module().getModuleInfo().name();
        String lowercase = moduleCase.getValue() ? moduleName.toLowerCase() : moduleName;
        return (moduleSplit.getValue() ? lowercase.replace(" ", "") : lowercase) + (split.getValue() ? (!moduleComponent.module.getSuffix().isEmpty() ? String.format("§7 %s ", splitType.getValue().getName()) + (!moduleCase.getValue() ? moduleComponent.module.getSuffix() : moduleComponent.module.getSuffix().toLowerCase()) : "") : "");
    }

    private Comparator<ModuleComponent> getModuleComponentComparator() {
        return Comparator.comparingDouble(module -> -inter16.getWidth(getModuleName(module)));
    }

    @Native
    public void createModuleList() {
        ModuleListCacheKey cacheKey = new ModuleListCacheKey();
        List<ModuleComponent> cachedList = moduleListCache.getIfPresent(cacheKey);
        if (cachedList == null) {
            allModuleComponent.clear();
            allModuleComponent = excellent.getModuleManager()
                    .stream()
                    .map(ModuleComponent::new)
                    .sorted(getModuleComponentComparator())
                    .collect(Collectors.toList());

            moduleListCache.put(cacheKey, allModuleComponent);
        } else {
            allModuleComponent = cachedList;
        }
    }

    public void sortArrayList() {
        SortedListCacheKey cacheKey = new SortedListCacheKey();
        List<ModuleComponent> cachedList = sortedListCache.getIfPresent(cacheKey);
        if (cachedList == null) {
            activeModuleComponent.clear();
            activeModuleComponent = allModuleComponent.stream()
                    .filter(x -> shouldDisplay(x) && x.module().isEnabled())
                    .sorted(getModuleComponentComparator())
                    .collect(Collectors.toList());

            sortedListCache.put(cacheKey, activeModuleComponent);
        } else {
            activeModuleComponent = cachedList;
        }
    }

    public record ModuleComponent(Module module) {
    }

    public boolean shouldDisplay(ModuleComponent component) {
        if (component.module() instanceof ClickGui) return false;
        if (!component.module().getModuleInfo().allowDisable()) return false;
        if (!component.module().isEnabled()) return false;

        switch (getModulesToShow().getValue().getName()) {
            case "Всё" -> {
                return true;
            }
            case "Всё кроме рендера" -> {
                return !component.module().getModuleInfo().category().equals(Category.RENDER);
            }
            case "Только бинды" -> {
                return component.module().getKeyCode() != -1;
            }
        }
        return true;
    }

    private static class ModuleListCacheKey {
    }

    private static class SortedListCacheKey {
    }
}
