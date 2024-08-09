package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import im.expensive.Expensive;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.ui.display.impl.*;
import im.expensive.ui.styles.StyleManager;
import im.expensive.utils.drag.Dragging;
import im.expensive.utils.render.ColorUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "HUD", type = Category.RENDER)
public class HUD extends Function {

    private final ModeListSetting elements = new ModeListSetting("Элементы",
            new BooleanSetting("Ватермарка", true),
            new BooleanSetting("Список модулей", true),
            new BooleanSetting("Координаты", true),
            new BooleanSetting("Эффекты", true),
            new BooleanSetting("Список модерации", true),
            new BooleanSetting("Активные бинды", true),
            new BooleanSetting("Активный таргет", true),
            new BooleanSetting("Броня", true)
    );

    final WatermarkRenderer watermarkRenderer;
    final ArrayListRenderer arrayListRenderer;
    final CoordsRenderer coordsRenderer;
    final PotionRenderer potionRenderer;

    final KeyBindRenderer keyBindRenderer;
    final TargetInfoRenderer targetInfoRenderer;
    final StaffListRenderer staffListRenderer;

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (mc.gameSettings.showDebugInfo) {
            return;
        }

        if (elements.getValueByName("Список модерации").get()) staffListRenderer.update(e);
        if (elements.getValueByName("Список модулей").get()) arrayListRenderer.update(e);
    }


    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (mc.gameSettings.showDebugInfo || e.getType() != EventDisplay.Type.POST) {
            return;
        }

        if (elements.getValueByName("Координаты").get()) coordsRenderer.render(e);
        if (elements.getValueByName("Эффекты").get()) potionRenderer.render(e);
        if (elements.getValueByName("Ватермарка").get()) watermarkRenderer.render(e);
        if (elements.getValueByName("Список модулей").get()) arrayListRenderer.render(e);
        if (elements.getValueByName("Активные бинды").get()) keyBindRenderer.render(e);
        if (elements.getValueByName("Список модерации").get()) staffListRenderer.render(e);
        if (elements.getValueByName("Активный таргет").get()) targetInfoRenderer.render(e);

    }

    public HUD() {
        watermarkRenderer = new WatermarkRenderer();
        arrayListRenderer = new ArrayListRenderer();
        coordsRenderer = new CoordsRenderer();
        Dragging potions = Expensive.getInstance().createDrag(this, "Potions", 278, 5);
        Dragging keyBinds = Expensive.getInstance().createDrag(this, "KeyBinds", 185, 5);
        Dragging dragging = Expensive.getInstance().createDrag(this, "TargetHUD", 74, 128);
        Dragging staffList = Expensive.getInstance().createDrag(this, "StaffList", 96, 5);
        potionRenderer = new PotionRenderer(potions);
        keyBindRenderer = new KeyBindRenderer(keyBinds);
        staffListRenderer = new StaffListRenderer(staffList);
        targetInfoRenderer = new TargetInfoRenderer(dragging);
        addSettings(elements);
    }

    public static int getColor(int index) {
        StyleManager styleManager = Expensive.getInstance().getStyleManager();
        return ColorUtils.gradient(styleManager.getCurrentStyle().getFirstColor().getRGB(), styleManager.getCurrentStyle().getSecondColor().getRGB(), index * 16, 10);
    }

    public static int getColor(int index, float mult) {
        StyleManager styleManager = Expensive.getInstance().getStyleManager();
        return ColorUtils.gradient(styleManager.getCurrentStyle().getFirstColor().getRGB(), styleManager.getCurrentStyle().getSecondColor().getRGB(), (int) (index * mult), 10);
    }

    public static int getColor(int firstColor, int secondColor, int index, float mult) {
        return ColorUtils.gradient(firstColor, secondColor, (int) (index * mult), 10);
    }
}