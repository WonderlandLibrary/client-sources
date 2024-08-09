package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import net.minecraft.util.Timer;
import src.Wiksi.Wiksi;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.settings.impl.ModeListSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.functions.settings.impl.StringSetting;
import src.Wiksi.ui.display.impl.*;
import src.Wiksi.ui.styles.StyleManager;
import src.Wiksi.utils.drag.DragManager;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.render.ColorUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.lwjgl.system.CallbackI;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "HUD", type = Category.Render)
public class HUD extends Function {

    private final ModeListSetting elements = new ModeListSetting("Элементы",

            new BooleanSetting("Ватермарка", true),
            new BooleanSetting("Ватермарка | вид 2", false),
            new BooleanSetting("Список модулей", true),
            new BooleanSetting("Список модулей | вид 2", false),
            new BooleanSetting("Оперативная память", false),
            new BooleanSetting("Координаты", true),
            new BooleanSetting("Координаты | вид 2", false),
            new BooleanSetting("Эффекты", true),
            new BooleanSetting("Эффекты | вид 2", false),
            new BooleanSetting("Список модерации", true),
            new BooleanSetting("Список модерации | вид 2", false),
            new BooleanSetting("Активные бинды", true),
            new BooleanSetting("Активные бинды | вид 2", false),
            new BooleanSetting("Активный таргет", true),
            new BooleanSetting("Активный таргет | вид 2", true),
            new BooleanSetting("Броня", true),
            new BooleanSetting("Броня | вид 2", false),
            new BooleanSetting("Время", true),
            new BooleanSetting("Инвентарь", true),
            new BooleanSetting("Клавиши | вид 2", false),
            new BooleanSetting("Инвентарь | вид 2", false),
            new BooleanSetting("Клавиши", true)
    );

    final WatermarkRenderer watermarkRenderer;
    Watermark2Renderer watermark2Renderer;
    final ArrayListRenderer arrayListRenderer;
    ArrayList2Renderer arrayList2Renderer;
    final MemoryHudRenderer memoryRenderer;
    final CoordsRenderer coordsRenderer;
    Coords2Renderer coords2Renderer;


    final PotionRenderer potionRenderer;
    final KeyStrokesRenderer keyStrokesRenderer;
    Potion2Renderer potion2Renderer;
    KeyStrokes2Renderer keyStrokes2Renderer;

    final SeeInventoryRenderer seeInventoryRenderer;
    SeeInventory2Renderer seeInventory2Renderer;
    final KeyBindRenderer keyBindRenderer;
    KeyBind2Renderer keyBind2Renderer;
    final TargetInfoRenderer targetInfoRenderer;
    final ArmorRenderer armorRenderer;
    final StaffListRenderer staffListRenderer;
    Armor2Renderer armor2Renderer;
    StaffList2Renderer staffList2Renderer;
    final TimeRenderer timeRenderer;
    TargetHUD2Renderer targetHUD2Renderer;

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (mc.gameSettings.showDebugInfo) {
            return;
        }

        if (elements.getValueByName("Список модерации").get()) staffListRenderer.update(e);
        if (elements.getValueByName("Список модерации | вид 2").get()) staffList2Renderer.update(e);
        if (elements.getValueByName("Список модулей").get()) arrayListRenderer.update(e);
        if (elements.getValueByName("Список модулей | вид 2").get()) arrayList2Renderer.update(e);
    }


    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (mc.gameSettings.showDebugInfo || e.getType() != EventDisplay.Type.POST) {
            return;
        }

        if (elements.getValueByName("Координаты").get()) coordsRenderer.render(e);
        if (elements.getValueByName("Эффекты").get()) potionRenderer.render(e);
        if (elements.getValueByName("Координаты | вид 2").get()) coords2Renderer.render(e);
        if (elements.getValueByName("Эффекты | вид 2").get()) potion2Renderer.render(e);
        if (elements.getValueByName("Ватермарка").get()) watermarkRenderer.render(e);
        if (elements.getValueByName("Ватермарка | вид 2").get()) watermark2Renderer.render(e);
        if (elements.getValueByName("Список модулей").get()) arrayListRenderer.render(e);
        if (elements.getValueByName("Оперативная память").get()) memoryRenderer.render(e);
        if (elements.getValueByName("Список модулей | вид 2").get()) arrayList2Renderer.render(e);
        if (elements.getValueByName("Активные бинды").get()) keyBindRenderer.render(e);
        if (elements.getValueByName("Список модерации").get()) staffListRenderer.render(e);
        if (elements.getValueByName("Активные бинды | вид 2").get()) keyBind2Renderer.render(e);
        if (elements.getValueByName("Список модерации | вид 2").get()) staffList2Renderer.render(e);
        if (elements.getValueByName("Активный таргет").get()) targetInfoRenderer.render(e);
        if (elements.getValueByName("Активный таргет | вид 2").get()) targetHUD2Renderer.render(e);
        if (elements.getValueByName("Броня").get()) armorRenderer.render(e);
        if (elements.getValueByName("Броня | вид 2").get()) armor2Renderer.render(e);
        if(elements.getValueByName("Время").get()) timeRenderer.render(e);
        if (elements.getValueByName("Клавиши").get()) keyStrokesRenderer.render(e);
        if (elements.getValueByName("Инвентарь").get()) seeInventoryRenderer.render(e);
        if (elements.getValueByName("Клавиши | вид 2").get()) keyStrokes2Renderer.render(e);
        if (elements.getValueByName("Инвентарь | вид 2").get()) seeInventory2Renderer.render(e);
    }

    public HUD() {
        watermarkRenderer = new WatermarkRenderer();
        watermark2Renderer = new Watermark2Renderer();
        coordsRenderer = new CoordsRenderer();
        coords2Renderer = new Coords2Renderer();
        Dragging potions = Wiksi.getInstance().createDrag(this, "Potions", 278, 5);
        Dragging potions2 = Wiksi.getInstance().createDrag(this, "Potions | 2", 278, 5);
        Dragging keyBinds = Wiksi.getInstance().createDrag(this, "KeyBinds", 185, 5);
        Dragging keyBinds2 = Wiksi.getInstance().createDrag(this, "KeyBinds | 2", 185, 5);
        Dragging dragging = Wiksi.getInstance().createDrag(this, "TargetHUD", 74, 128);
        Dragging target = Wiksi.getInstance().createDrag(this, "TargetHUD2", 74, 128);
        Dragging staffList = Wiksi.getInstance().createDrag(this, "StaffList", 96, 5);
        Dragging dragging7 = Wiksi.getInstance().createDrag(this, "MemoryHud", 74.0f, 128.0f);
        Dragging staffList2 = Wiksi.getInstance().createDrag(this, "StaffList | 2", 96, 5);
        Dragging armorRendere = Wiksi.getInstance().createDrag(this, "Броня", 96, 120);
        Dragging armorRendere22 = Wiksi.getInstance().createDrag(this, "Броня | 2", 96, 120);
        Dragging time = Wiksi.getInstance().createDrag(this, "Время", 225, 125);
        Dragging keystrokes = Wiksi.getInstance().createDrag(this, "Клавиши", 132, 50);
        Dragging dragseeinv = Wiksi.getInstance().createDrag(this, "Инвентарь", 163, 5);
        Dragging keystrokes2 = Wiksi.getInstance().createDrag(this, "Клавиши | 2", 132, 50);
        Dragging dragseeinv2 = Wiksi.getInstance().createDrag(this, "Инвентарь | 2", 163, 5);
        seeInventoryRenderer = new SeeInventoryRenderer(dragseeinv);
        keyStrokesRenderer = new KeyStrokesRenderer(keystrokes);
        memoryRenderer = new MemoryHudRenderer(dragging7);
        seeInventory2Renderer = new SeeInventory2Renderer(dragseeinv2);
        keyStrokes2Renderer = new KeyStrokes2Renderer(keystrokes2);
        timeRenderer = new TimeRenderer(time);
        potionRenderer = new PotionRenderer(potions);
        potion2Renderer = new Potion2Renderer(potions2);
        arrayListRenderer = new ArrayListRenderer();
        arrayList2Renderer = new ArrayList2Renderer();
        keyBindRenderer = new KeyBindRenderer(keyBinds);
        staffListRenderer = new StaffListRenderer(staffList);
        armorRenderer = new ArmorRenderer(armorRendere);
        keyBind2Renderer = new KeyBind2Renderer(keyBinds2);
        staffList2Renderer = new StaffList2Renderer(staffList2);
        armor2Renderer = new Armor2Renderer(armorRendere22);
        targetInfoRenderer = new TargetInfoRenderer(dragging);
        targetHUD2Renderer = new TargetHUD2Renderer(target);
        addSettings(elements);
        DragManager.load();
    }

    public static int getColor(int index) {
        StyleManager styleManager = Wiksi.getInstance().getStyleManager();
        return ColorUtils.gradient(styleManager.getCurrentStyle().getFirstColor().getRGB(), styleManager.getCurrentStyle().getSecondColor().getRGB(), index * 16, 15);
    }

    public static int getColor(int index, float mult) {
        StyleManager styleManager = Wiksi.getInstance().getStyleManager();
        return ColorUtils.gradient(styleManager.getCurrentStyle().getFirstColor().getRGB(), styleManager.getCurrentStyle().getSecondColor().getRGB(), (int) (index * mult), 15);
    }

    public static int getColor(int firstColor, int secondColor, int index, float mult) {
        return ColorUtils.gradient(firstColor, secondColor, (int) (index * mult), 15);
    }
}