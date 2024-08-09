package wtf.resolute.moduled.impl.render.HUD;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.moduled.settings.impl.ModeListSetting;
import wtf.resolute.moduled.settings.impl.ModeSetting;
import wtf.resolute.moduled.impl.render.HUD.HudElement.impl.*;
import wtf.resolute.manage.drag.Dragging;
import wtf.resolute.utiled.render.ColorUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ModuleAnontion(name = "HUD", type = Categories.Render, server = "")
public class HUD extends Module {

    private static final ModeListSetting elements = new ModeListSetting("Элементы",
            new BooleanSetting("Ватермарка", true),
            new BooleanSetting("Список модулей", true),
            new BooleanSetting("Координаты", true),
            new BooleanSetting("Эффекты", true),
            new BooleanSetting("Список модерации", true),
            new BooleanSetting("Активные бинды", true),
            new BooleanSetting("Активный таргет", true),
            new BooleanSetting("Броня", true),
         //   new BooleanSetting("Кей строки", false),
            new BooleanSetting("Расписание событий RW", false)
    );
    public static ModeSetting targethudhat = new ModeSetting("Голова Активного таргета", "1", "1", "2").setVisible(() -> elements.get(1).get());
    public static ModeSetting arraymodel = new ModeSetting("Сторона списка модулей", "правый", "правый").setVisible(() -> elements.get(1).get());
    final WatermarkRenderer watermarkRenderer;
    final KeyStrocesRenderer keystrocesrenderer;
    final ArrayListRenderer arrayListRenderer;
    final CoordsRenderer coordsRenderer;
    final PotionRenderer potionRenderer;
    final KeyBindRenderer keyBindRenderer;
    final TargetHudRender targetInfoRenderer;
   // final FuntimeSchedulesRenderer ftschedulesRenderer;
    final ArmorRenderer armorRenderer;
    final StaffListRenderer staffListRenderer;
    final SchedulesRenderer schedulesRenderer;

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (mc.gameSettings.showDebugInfo) {
            return;
        }

        if (elements.getValueByName("Список модерации").get()) staffListRenderer.update(e);
        if (elements.getValueByName("Список модулей").get()) arrayListRenderer.update(e);
        if (elements.getValueByName("Расписание событий RW").get()) schedulesRenderer.update(e);
       // if (elements.getValueByName("Расписание событий FT").get()) ftschedulesRenderer.update(e);

    }


    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (mc.gameSettings.showDebugInfo || e.getType() != EventDisplay.Type.POST) {
            return;
        }

        if (elements.getValueByName("Координаты").get()) coordsRenderer.render(e);
        if (elements.getValueByName("Эффекты").get()) potionRenderer.render(e);
        if (elements.getValueByName("Ватермарка").get()) watermarkRenderer.render(e);
        // if (elements.getValueByName("Кей строки").get()) keystrocesrenderer.render(e);
        if (elements.getValueByName("Список модулей").get()) arrayListRenderer.render(e);
        if (elements.getValueByName("Активные бинды").get()) keyBindRenderer.render(e);
        if (elements.getValueByName("Список модерации").get()) staffListRenderer.render(e);
        if (elements.getValueByName("Активный таргет").get()) targetInfoRenderer.render(e);
        if (elements.getValueByName("Расписание событий RW").get()) schedulesRenderer.render(e);
      //  if (elements.getValueByName("Расписание событий FT").get()) ftschedulesRenderer.render(e);
        if (elements.getValueByName("Броня").get()) armorRenderer.render(e);

    }

    public HUD() {
        watermarkRenderer = new WatermarkRenderer();
        Dragging keystrocs = ResoluteInfo.getInstance().createDrag(this, "KeyStrocs", 220, 70);
        keystrocesrenderer = new KeyStrocesRenderer(keystrocs);
        arrayListRenderer = new ArrayListRenderer();
        coordsRenderer = new CoordsRenderer();
        Dragging potions = ResoluteInfo.getInstance().createDrag(this, "Potions", 278, 5);
        Dragging armor = ResoluteInfo.getInstance().createDrag(this, "ArmorHud", 370, 90);
        Dragging schedules = ResoluteInfo.getInstance().createDrag(this, "RWSchedules", 73, 43);
        Dragging keyBinds = ResoluteInfo.getInstance().createDrag(this, "KeyBinds", 185, 5);
        Dragging dragging = ResoluteInfo.getInstance().createDrag(this, "TargetHUD", 74, 128);
        Dragging staffList = ResoluteInfo.getInstance().createDrag(this, "StaffList", 96, 5);
        potionRenderer = new PotionRenderer(potions);
        armorRenderer = new ArmorRenderer(armor);
        schedulesRenderer = new SchedulesRenderer(schedules);
       // ftschedulesRenderer = new FuntimeSchedulesRenderer(ftschedules);
        keyBindRenderer = new KeyBindRenderer(keyBinds);
        staffListRenderer = new StaffListRenderer(staffList);
        targetInfoRenderer = new TargetHudRender(dragging);
        addSettings(elements,targethudhat,arraymodel);
    }

    public static int getColor(int index) {
        int firstColor = ColorUtils.getColorStyle(0);
        int secondColor = ColorUtils.getColorStyle(100);
        return ColorUtils.gradient(firstColor,secondColor, index * 16, 8);
    }

    public static int getColor(int index, float mult) {
        int firstColor = ColorUtils.getColorStyle(0);
        int secondColor = ColorUtils.getColorStyle(100);
        return ColorUtils.gradient(firstColor,secondColor, (int) (index * mult), 7);
    }

    public static int getColor(int firstColor, int secondColor, int index, float mult) {
        return ColorUtils.gradient(firstColor, secondColor, (int) (index * mult), 10);
    }
}