package fun.ellant.functions.impl.hud;

import com.google.common.eventbus.Subscribe;
import com.jhlabs.vecmath.AxisAngle4f;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.ModeListSetting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.ui.display.impl.*;
import fun.ellant.ui.styles.StyleManager;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.render.ColorUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "HUD", type = Category.RENDER, desc = "Визуалы")
public class HUD extends Function {
    public AxisAngle4f shadow;

    private final ModeListSetting elements = new ModeListSetting("Элементы",
            new BooleanSetting("Ватермарка", true),
            new BooleanSetting("Список модулей", true),
            new BooleanSetting("Координаты", true),
            new BooleanSetting("Эффекты", true),
            new BooleanSetting("Список модерации", true),
            new BooleanSetting("Активные бинды", true),
            new BooleanSetting("Активный таргет", true),
            new BooleanSetting("Броня", true),
            new BooleanSetting("Нажатие клавиш", true),
            new BooleanSetting("Информация", true),
            new BooleanSetting("Мотион график", true),
            new BooleanSetting("Ближайшие ивенты", true),
            new BooleanSetting("Инвентарь", false)
    );

    private final ModeSetting potionMode = new ModeSetting("Выбор активных эффекта", "Nursultan", "Nursultan", "Новая", "WexSide", "Client", "Ancient");
    private final ModeSetting staffMode = new ModeSetting("Выбор стафф листа", "Nursultan", "Nursultan", "Новая", "WexSide", "Client", "Ancient");
    private final ModeSetting keyMode = new ModeSetting("Выбор активных биндов", "Nursultan", "Nursultan", "Новая", "WexSide", "Client", "Ancient");
    private final ModeSetting wtMode = new ModeSetting("Выбор ватермарки", "Wild", "Wild", "Ватермарка 2", "Ватермарка 3", "Ватермарка 4", "Ancient");
    private final ModeSetting thudMode = new ModeSetting("Режим таргет худа", "Обычный", "Обычный", "Новый", "Vega Line", "Dark Flow", "Самая новая", "WexSide", "Крутая", "Ancient");
    private final ModeSetting arraylistmode = new ModeSetting("Режим списка функций", "Обычный", "Обычный", "Новый", "Ancient");
    final WatermarkRenderer watermarkRenderer;
    final ArrayListRenderer arrayListRenderer;
    final WexSideStaffRenderer wexSideStaffRenderer;
    final EllantKeyRenderer ellantKeyRenderer;
    final EllantStaffRenderer ellantStaffRenderer;
    final EllantPotionRenderer ellantPotionRenderer;
    final WexsidePotionRenderer wexsidePotionRenderer;
    final WexSideTargetRenderer wexSideTargetRenderer;
    final WexSideKeyBindRenderer wexSideKeyBindRenderer;
    final RwSchleduleRender rwSchleduleRender;
    final ArrayListRenderer2 arrayListRenderer2;
    final CoordsRenderer coordsRenderer;
    final PotionRenderer potionRenderer;
    final Information information;
    final TargetInfoRenderer7 targetInfoRenderer7;
    final PotionRenderer2 potionRenderer2;
    final PotionRenderer3 potionRenderer3;
    final AncientWaterMark ancientWaterMark;
    final AncientArrayList ancientArrayList;
    final AncientPotion ancientPotion;
    final AncientHotKeys ancientHotKeys;
    final AncientStaff ancientStaff;
    final AncientTargetHUD ancientTargetHUD;
    final MotionGraphRenderer motionGraphRenderer;
    final PotionRenderer4 potionRenderer4;
    final KeyBindRenderer keyBindRenderer;
    final KeyBindRenderer2 keyBindRenderer2;
    final KeyBindRenderer3 keyBindRenderer3;
    final KeyBindRenderer4 keyBindRenderer4;
    final TargetInfoRenderer3 targetInfoRenderer3;
    final TargetInfoRenderer targetInfoRenderer;
    final TargetInfoRenderer6 targetInfoRenderer6;
    final TargetInfoRenderer5 targetInfoRenderer5;
    final ArmorRenderer armorRenderer;
    final WatermarkRenderer7 watermarkRenderer7;
    final StaffListRenderer staffListRenderer;
    final StaffListRenderer2 staffListRenderer2;
    final StaffListRenderer3 staffListRenderer3;
    final KeyStrokesRenderer keyStrokesRenderer;
    final WatermarkRenderer3 watermarkRenderer3;
    final WatermarkRenderer5 watermarkRenderer5;
    final CoordsRenderer2 coordsRenderer2;
    final NotificationsRenderer notificationsRenderer;
    final TestKeyRenderer5 testKeyRenderer5;
    final SeeInventoryRenderer seeInventoryRenderer;
    final TargetInfoRenderer1488 targetInfoRenderer1488;
    final TargetInfoRenderer2 targetInfoRenderer2;
    final TargetInfoRenderer4 targetInfoRenderer4;
    final WatermarkRenderer4 watermarkRenderer4;

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (mc.gameSettings.showDebugInfo) {
            return;
        }
        if (elements.getValueByName("Список модерации").get()) {
            if (staffMode.is("Nursultan")) {
                staffListRenderer.update(e);
            } else if (staffMode.is("Новая")) {
                staffListRenderer3.update(e);
            } else if (staffMode.is("WexSide")) {
                wexSideStaffRenderer.update(e);
            } else if (staffMode.is("Client")) {
                ellantStaffRenderer.update(e);
            } else if (staffMode.is("Ancient")) {
                ancientStaff.update(e);
            }
        }

        if (elements.getValueByName("Мотион график").get()) motionGraphRenderer.update(e);
        if (elements.getValueByName("Ближайшие ивенты").get()) rwSchleduleRender.update(e);
        if (elements.getValueByName("Информация").get()) information.update(e);

            if (elements.getValueByName("Список модулей").get()) {
                if (arraylistmode.is("Обычный")) {
                    arrayListRenderer.update(e);
                } else if (arraylistmode.is("Новый")) {
                    arrayListRenderer2.update(e);
                } else if (arraylistmode.is("Ancient")) {
                    ancientArrayList.update(e);
                }
            }
    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (mc.gameSettings.showDebugInfo || e.getType() != EventDisplay.Type.POST) {
            return;
        }
        if (elements.getValueByName("Мотион график").get()) motionGraphRenderer.render(e);

        if (elements.getValueByName("Ватермарка").get()) {
            if (wtMode.is("Wild")) {
                watermarkRenderer.render(e);
            } else if (wtMode.is("Ватермарка 2")) {
                watermarkRenderer3.render(e);
            } else if (wtMode.is("Ватермарка 3")) {
                watermarkRenderer5.render(e);
            } else if (wtMode.is("Ватермарка 4")) {
                watermarkRenderer7.render(e);
            } else if (wtMode.is("Ancient")) {
                ancientWaterMark.render(e);
            }
        }
        if (elements.getValueByName("Ближайшие ивенты").get()) rwSchleduleRender.render(e);
        if (elements.getValueByName("Активные бинды").get()) {
            if (keyMode.is("Nursultan")) {
                keyBindRenderer.render(e);
            } if (keyMode.is("WexSide")) {
                wexSideKeyBindRenderer.render(e);
            } if (keyMode.is("Новая")) {
                keyBindRenderer4.render(e);
            } if (keyMode.is("Client")) {
                ellantKeyRenderer.render(e);
            } if (keyMode.is("Ancient")) {
                ancientHotKeys.render(e);
            }
        }
        if (elements.getValueByName("Эффекты").get()) {
            if (potionMode.is("Nursultan")) {
                potionRenderer.render(e);
            } if (potionMode.is("WexSide")) {
                wexsidePotionRenderer.render(e);
            } if (potionMode.is("Новая")) {
                potionRenderer4.render(e);
            } if (potionMode.is("Client")) {
                ellantPotionRenderer.render(e);
            } if (potionMode.is("Ancient")) {
                ancientPotion.render(e);
            }
        }
        //if (elements.getValueByName("Трекер мыши").get()) mouseTrackerRenderer.render(e);
        if (elements.getValueByName("Нажатие клавиш").get()) keyStrokesRenderer.render(e);
        if (elements.getValueByName("Координаты").get()) coordsRenderer.render(e);
        if (elements.getValueByName("Активный таргет").get()) {
            if (thudMode.is("Обычный")) {
                targetInfoRenderer.render(e);
            } else if (thudMode.is("Новый")) {
                targetInfoRenderer2.render(e);
            } else if (thudMode.is("Vega Line")) {
                targetInfoRenderer3.render(e);
            } else if (thudMode.is("Wild")) {
                targetInfoRenderer4.render(e);
            } else if (thudMode.is("Nursultan")) {
                targetInfoRenderer5.render(e);
            } else if (thudMode.is("Dark Flow")) {
                targetInfoRenderer6.render(e);
            } else if (thudMode.is("Самая новая")) {
                targetInfoRenderer7.render(e);
            } else if (thudMode.is("WexSide")) {
                wexSideTargetRenderer.render(e);
            } else if (thudMode.is("Крутая")) {
                targetInfoRenderer1488.render(e);
            } else if (thudMode.is("Ancient")) {
                ancientTargetHUD.render(e);

            }
        }
        if (elements.getValueByName("Список модерации").get()) {
            if (staffMode.is("Nursultan")) {
                staffListRenderer.render(e);
            } if (staffMode.is("WexSide")) {
                wexSideStaffRenderer.render(e);
            } if (staffMode.is("Новая")) {
                staffListRenderer3.render(e);
            } if (staffMode.is("Client")) {
                ellantStaffRenderer.render(e);
            } if (staffMode.is("Ancient")) {
                ancientStaff.render(e);
            }
        }
        if (elements.getValueByName("Список модулей").get()) {
            if (arraylistmode.is("Обычный")) {
                arrayListRenderer.render(e);
            } if (arraylistmode.is("Новый")) {
                arrayListRenderer2.render(e);
            } if (arraylistmode.is("Ancient")) {
                ancientArrayList.render(e);
            }
        }
        if (elements.getValueByName("Броня").get()) armorRenderer.render(e);
        if (elements.getValueByName("Информация").get()) information.render(e);
        if (elements.getValueByName("Инвентарь").get()) seeInventoryRenderer.render(e);
    }
    public HUD() {
        watermarkRenderer = new WatermarkRenderer();
        watermarkRenderer3 = new WatermarkRenderer3();
        ancientWaterMark = new AncientWaterMark();
        watermarkRenderer5 = new WatermarkRenderer5();
        watermarkRenderer4 = new WatermarkRenderer4();
        watermarkRenderer7 = new WatermarkRenderer7();
        arrayListRenderer = new ArrayListRenderer();
        arrayListRenderer2 = new ArrayListRenderer2();
        ancientArrayList = new AncientArrayList();
        notificationsRenderer = new NotificationsRenderer();

        Dragging rw = Ellant.getInstance().createDrag(this, "Schelude",278, 5);
        Dragging armor = Ellant.getInstance().createDrag(this, "Броня",278, 5);
        Dragging potions = Ellant.getInstance().createDrag(this, "Зелья", 278, 5);
        Dragging keyBinds = Ellant.getInstance().createDrag(this, "Кейбинды", 185, 5);
        Dragging dragging = Ellant.getInstance().createDrag(this, "TargetHUD", 74, 128);
        Dragging staffList = Ellant.getInstance().createDrag(this, "Список администрации", 96, 5);
        Dragging keystrokes = Ellant.getInstance().createDrag(this, "Кейстрокс", 132, 5);
        Dragging dragseeinv = Ellant.getInstance().createDrag(this, "Инвентарь", 163, 5);
        Dragging motion = Ellant.getInstance().createDrag(this, "Motion Graphics", 163, 5);
        Dragging infa = Ellant.getInstance().createDrag(this, "Информация", 153, 5);
        motionGraphRenderer = new MotionGraphRenderer(motion);
        rwSchleduleRender = new RwSchleduleRender(rw);
        armorRenderer = new ArmorRenderer(armor);
        information = new Information(infa);
        potionRenderer = new PotionRenderer(potions);
        potionRenderer2 = new PotionRenderer2(potions);
        potionRenderer3 = new PotionRenderer3(potions);
        ancientPotion = new AncientPotion(potions);
        ancientTargetHUD = new AncientTargetHUD(dragging);
        ellantPotionRenderer = new EllantPotionRenderer(potions);
        ellantKeyRenderer = new EllantKeyRenderer(keyBinds);
        potionRenderer4 = new PotionRenderer4(potions);
        wexsidePotionRenderer = new WexsidePotionRenderer(potions);
        coordsRenderer = new CoordsRenderer();
        coordsRenderer2 = new CoordsRenderer2();
        keyBindRenderer = new KeyBindRenderer(keyBinds);
        keyBindRenderer2 = new KeyBindRenderer2(keyBinds);
        keyBindRenderer3 = new KeyBindRenderer3(keyBinds);
        keyBindRenderer4 = new KeyBindRenderer4(keyBinds);
        ancientHotKeys = new AncientHotKeys(keyBinds);

        wexSideKeyBindRenderer = new WexSideKeyBindRenderer(keyBinds);
        testKeyRenderer5 = new TestKeyRenderer5(keyBinds);
        staffListRenderer = new StaffListRenderer(staffList);
        staffListRenderer2 = new StaffListRenderer2(staffList);
        staffListRenderer3 = new StaffListRenderer3(staffList);
        ancientStaff = new AncientStaff(staffList);

        ellantStaffRenderer = new EllantStaffRenderer(staffList);
        wexSideStaffRenderer = new WexSideStaffRenderer(staffList);
        targetInfoRenderer = new TargetInfoRenderer(dragging);
        targetInfoRenderer2 = new TargetInfoRenderer2(dragging);
        targetInfoRenderer1488 = new TargetInfoRenderer1488(dragging);
        targetInfoRenderer3 = new TargetInfoRenderer3(dragging);
        targetInfoRenderer4 = new TargetInfoRenderer4(dragging);
        targetInfoRenderer5 = new TargetInfoRenderer5(dragging);
        targetInfoRenderer6 = new TargetInfoRenderer6(dragging);
        targetInfoRenderer7 = new TargetInfoRenderer7(dragging);
        wexSideTargetRenderer = new WexSideTargetRenderer(dragging);
        keyStrokesRenderer = new KeyStrokesRenderer(keystrokes);
        seeInventoryRenderer = new SeeInventoryRenderer(dragseeinv);
        addSettings(elements, thudMode, wtMode, arraylistmode, keyMode, potionMode, staffMode);
    }

    public static int getColor(int index) {
        StyleManager styleManager = Ellant.getInstance().getStyleManager();
        return ColorUtils.gradient(styleManager.getCurrentStyle().getFirstColor().getRGB(), styleManager.getCurrentStyle().getSecondColor().getRGB(), index * 16, 10);
    }

    public static int getColor(int index, float mult) {
        StyleManager styleManager = Ellant.getInstance().getStyleManager();
        return ColorUtils.gradient(styleManager.getCurrentStyle().getFirstColor().getRGB(), styleManager.getCurrentStyle().getSecondColor().getRGB(), (int) (index * mult), 10);
    }

    public static int getColor(int firstColor, int secondColor, int index, float mult) {
        return ColorUtils.gradient(firstColor, secondColor, (int) (index * mult), 10);
    }
}