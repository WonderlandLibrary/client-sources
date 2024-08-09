/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.ui.display.impl.ArmorRenderer;
import mpp.venusfr.ui.display.impl.ArrayListRenderer;
import mpp.venusfr.ui.display.impl.KeyBindRenderer;
import mpp.venusfr.ui.display.impl.MemoryHudRenderer;
import mpp.venusfr.ui.display.impl.PotionRenderer;
import mpp.venusfr.ui.display.impl.SeeInventoryRenderer;
import mpp.venusfr.ui.display.impl.StaffListRenderer;
import mpp.venusfr.ui.display.impl.TargetInfoRenderer;
import mpp.venusfr.ui.display.impl.TimerRenderer;
import mpp.venusfr.ui.display.impl.WatermarkRenderer;
import mpp.venusfr.ui.styles.StyleManager;
import mpp.venusfr.utils.drag.DragManager;
import mpp.venusfr.utils.drag.Dragging;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.venusfr;

@FunctionRegister(name="HUD", type=Category.Visual)
public class HUD
extends Function {
    private final ModeListSetting elements = new ModeListSetting("\u042d\u043b\u0435\u043c\u0435\u043d\u0442\u044b", new BooleanSetting("\u0412\u0430\u0442\u0435\u0440\u043c\u0430\u0440\u043a\u0430", true), new BooleanSetting("\u0421\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0443\u043b\u0435\u0439", true), new BooleanSetting("\u041a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b", true), new BooleanSetting("\u042d\u0444\u0444\u0435\u043a\u0442\u044b", true), new BooleanSetting("\u0421\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0435\u0440\u0430\u0446\u0438\u0438", true), new BooleanSetting("\u0410\u043a\u0442\u0438\u0432\u043d\u044b\u0435 \u0431\u0438\u043d\u0434\u044b", true), new BooleanSetting("\u0410\u043a\u0442\u0438\u0432\u043d\u044b\u0439 \u0442\u0430\u0440\u0433\u0435\u0442", true), new BooleanSetting("\u0411\u0440\u043e\u043d\u044f", true), new BooleanSetting("\u0425\u043e\u0442\u0431\u0430\u0440", true), new BooleanSetting("\u0423\u0432\u0435\u0434\u043e\u043c\u043b\u0435\u043d\u0438\u044f", true), new BooleanSetting("\u0418\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u044c", true), new BooleanSetting("\u0411\u0443\u0441\u0442", true), new BooleanSetting("\u0422\u0430\u0439\u043c\u0435\u0440", true));
    private final WatermarkRenderer watermarkRenderer = new WatermarkRenderer();
    private final ArrayListRenderer arrayListRenderer = new ArrayListRenderer();
    private final PotionRenderer potionRenderer;
    private final KeyBindRenderer keyBindRenderer;
    private final TargetInfoRenderer targetInfoRenderer;
    private final ArmorRenderer armorRenderer;
    private final StaffListRenderer staffListRenderer;
    private final SeeInventoryRenderer seeInventoryRenderer;
    private final MemoryHudRenderer memoryHudRenderer;
    private final TimerRenderer timerRenderer;

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        if (HUD.mc.gameSettings.showDebugInfo) {
            return;
        }
        if (((Boolean)this.elements.getValueByName("\u0421\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0435\u0440\u0430\u0446\u0438\u0438").get()).booleanValue()) {
            this.staffListRenderer.update(eventUpdate);
        }
        if (((Boolean)this.elements.getValueByName("\u0421\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0443\u043b\u0435\u0439").get()).booleanValue()) {
            this.arrayListRenderer.update(eventUpdate);
        }
    }

    @Subscribe
    private void onDisplay(EventDisplay eventDisplay) {
        if (HUD.mc.gameSettings.showDebugInfo || eventDisplay.getType() != EventDisplay.Type.POST) {
            return;
        }
        if (((Boolean)this.elements.getValueByName("\u0411\u0440\u043e\u043d\u044f").get()).booleanValue()) {
            this.armorRenderer.render(eventDisplay);
        }
        if (((Boolean)this.elements.getValueByName("\u042d\u0444\u0444\u0435\u043a\u0442\u044b").get()).booleanValue()) {
            this.potionRenderer.render(eventDisplay);
        }
        if (((Boolean)this.elements.getValueByName("\u0412\u0430\u0442\u0435\u0440\u043c\u0430\u0440\u043a\u0430").get()).booleanValue()) {
            this.watermarkRenderer.render(eventDisplay);
        }
        if (((Boolean)this.elements.getValueByName("\u0421\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0443\u043b\u0435\u0439").get()).booleanValue()) {
            this.arrayListRenderer.render(eventDisplay);
        }
        if (((Boolean)this.elements.getValueByName("\u0410\u043a\u0442\u0438\u0432\u043d\u044b\u0435 \u0431\u0438\u043d\u0434\u044b").get()).booleanValue()) {
            this.keyBindRenderer.render(eventDisplay);
        }
        if (((Boolean)this.elements.getValueByName("\u0421\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0435\u0440\u0430\u0446\u0438\u0438").get()).booleanValue()) {
            this.staffListRenderer.render(eventDisplay);
        }
        if (((Boolean)this.elements.getValueByName("\u0410\u043a\u0442\u0438\u0432\u043d\u044b\u0439 \u0442\u0430\u0440\u0433\u0435\u0442").get()).booleanValue()) {
            this.targetInfoRenderer.render(eventDisplay);
        }
        if (((Boolean)this.elements.getValueByName("\u0418\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u044c").get()).booleanValue()) {
            this.seeInventoryRenderer.render(eventDisplay);
        }
        if (((Boolean)this.elements.getValueByName("\u0411\u0443\u0441\u0442").get()).booleanValue()) {
            this.memoryHudRenderer.render(eventDisplay);
        }
        if (((Boolean)this.elements.getValueByName("\u0422\u0430\u0439\u043c\u0435\u0440").get()).booleanValue()) {
            this.timerRenderer.render(eventDisplay);
        }
    }

    public HUD() {
        Dragging dragging = venusfr.getInstance().createDrag(this, "Potions", 278.0f, 5.0f);
        Dragging dragging2 = venusfr.getInstance().createDrag(this, "ArmorHUD", 279.0f, 5.0f);
        this.armorRenderer = new ArmorRenderer(dragging2);
        Dragging dragging3 = venusfr.getInstance().createDrag(this, "SeeInventory", 279.0f, 5.0f);
        this.seeInventoryRenderer = new SeeInventoryRenderer(dragging3);
        Dragging dragging4 = venusfr.getInstance().createDrag(this, "KeyBinds", 185.0f, 5.0f);
        Dragging dragging5 = venusfr.getInstance().createDrag(this, "TargetHUD", 74.0f, 128.0f);
        Dragging dragging6 = venusfr.getInstance().createDrag(this, "StaffList", 96.0f, 5.0f);
        Dragging dragging7 = venusfr.getInstance().createDrag(this, "MemoryHud", 74.0f, 128.0f);
        Dragging dragging8 = venusfr.getInstance().createDrag(this, "Timers", 74.0f, 128.0f);
        this.timerRenderer = new TimerRenderer(dragging8);
        this.memoryHudRenderer = new MemoryHudRenderer(dragging7);
        this.potionRenderer = new PotionRenderer(dragging);
        this.keyBindRenderer = new KeyBindRenderer(dragging4);
        this.staffListRenderer = new StaffListRenderer(dragging6);
        this.targetInfoRenderer = new TargetInfoRenderer(dragging5);
        this.addSettings(this.elements);
        DragManager.load();
    }

    public static int getColor(int n) {
        StyleManager styleManager = venusfr.getInstance().getStyleManager();
        return ColorUtils.gradient(styleManager.getCurrentStyle().getFirstColor().getRGB(), styleManager.getCurrentStyle().getSecondColor().getRGB(), n * 16, 10);
    }

    public static int getColor(int n, float f) {
        StyleManager styleManager = venusfr.getInstance().getStyleManager();
        return ColorUtils.gradient(styleManager.getCurrentStyle().getFirstColor().getRGB(), styleManager.getCurrentStyle().getSecondColor().getRGB(), (int)((float)n * f), 10);
    }

    public static int getColor(int n, int n2, int n3, float f) {
        return ColorUtils.gradient(n, n2, (int)((float)n3 * f), 10);
    }
}

