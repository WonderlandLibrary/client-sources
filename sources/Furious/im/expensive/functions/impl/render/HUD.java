package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import im.expensive.Furious;
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
@FunctionRegister(name = "HUD", type = Category.Render)
public class HUD extends Function {

    private final BooleanSetting wt = new BooleanSetting("Ватермарка", true);
    private final BooleanSetting al = new BooleanSetting("Аррай лист", true);
    private final BooleanSetting ht = new BooleanSetting("Активные бинды", true);
    private final BooleanSetting th = new BooleanSetting("Таргет худ", true);
    private final BooleanSetting pt = new BooleanSetting("Активные зелья", true);
    private final BooleanSetting st = new BooleanSetting("Список модерации", true);
    private final BooleanSetting inf = new BooleanSetting("Прочая информация", true);

    final WatermarkRenderer watermarkRenderer;
    final ArrayListRenderer arrayListRenderer;
    final CoordsRenderer coordsRenderer;
    final PotionRenderer potionRenderer;

    final KeyBindRenderer keyBindRenderer;
    final TargetInfoRenderer targetInfoRenderer;
    final ArmorRenderer armorRenderer;
    final StaffListRenderer staffListRenderer;

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (mc.gameSettings.showDebugInfo) {
            return;
        }

        if (st.get()) staffListRenderer.update(e);
        if (al.get()) arrayListRenderer.update(e);
    }


    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (mc.gameSettings.showDebugInfo || e.getType() != EventDisplay.Type.POST) {
            return;
        }

        if (inf.get()) coordsRenderer.render(e);
        if (pt.get()) potionRenderer.render(e);
        if (wt.get()) watermarkRenderer.render(e);
        if (al.get()) arrayListRenderer.render(e);
        if (ht.get()) keyBindRenderer.render(e);
        if (st.get()) staffListRenderer.render(e);
        if (th.get()) targetInfoRenderer.render(e);

    }

    public HUD() {
        watermarkRenderer = new WatermarkRenderer();
        arrayListRenderer = new ArrayListRenderer();
        coordsRenderer = new CoordsRenderer();
        Dragging potions = Furious.getInstance().createDrag(this, "Potions", 278, 5);
        armorRenderer = new ArmorRenderer();
        Dragging keyBinds = Furious.getInstance().createDrag(this, "KeyBinds", 185, 5);
        Dragging dragging = Furious.getInstance().createDrag(this, "TargetHUD", 74, 128);
        Dragging staffList = Furious.getInstance().createDrag(this, "StaffList", 96, 5);
        potionRenderer = new PotionRenderer(potions);
        keyBindRenderer = new KeyBindRenderer(keyBinds);
        staffListRenderer = new StaffListRenderer(staffList);
        targetInfoRenderer = new TargetInfoRenderer(dragging);
        addSettings(wt,al,ht,th,pt,st,inf);
    }

    public static int getColor(int index) {
        StyleManager styleManager = Furious.getInstance().getStyleManager();
        return ColorUtils.gradient(styleManager.getCurrentStyle().getFirstColor().getRGB(), styleManager.getCurrentStyle().getSecondColor().getRGB(), index * 16, 10);
    }

    public static int getColor(int index, float mult) {
        StyleManager styleManager = Furious.getInstance().getStyleManager();
        return ColorUtils.gradient(styleManager.getCurrentStyle().getFirstColor().getRGB(), styleManager.getCurrentStyle().getSecondColor().getRGB(), (int) (index * mult), 10);
    }

    public static int getColor(int firstColor, int secondColor, int index, float mult) {
        return ColorUtils.gradient(firstColor, secondColor, (int) (index * mult), 10);
    }
}