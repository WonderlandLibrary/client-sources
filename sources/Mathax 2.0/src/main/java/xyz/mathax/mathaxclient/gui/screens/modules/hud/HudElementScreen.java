package xyz.mathax.mathaxclient.gui.screens.modules.hud;

import xyz.mathax.mathaxclient.gui.WindowScreen;
import xyz.mathax.mathaxclient.gui.renderer.GuiRenderer;
import xyz.mathax.mathaxclient.gui.widgets.containers.WContainer;
import xyz.mathax.mathaxclient.gui.widgets.containers.WHorizontalList;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WButton;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WCheckbox;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.hud.HudElement;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.utils.Utils;

public class HudElementScreen extends WindowScreen {
    public final HudElement element;
    private WContainer settings;

    public HudElementScreen(Theme theme, HudElement element) {
        super(theme, element.name);

        this.element = element;
    }

    @Override
    public void initWidgets() {
        // Description
        add(theme.label(element.description, Utils.getWindowWidth() / 2.0));

        // Settings
        if (element.settings.sizeGroups() > 0) {
            settings = add(theme.verticalList()).expandX().widget();
            settings.add(theme.settings(element.settings)).expandX();

            add(theme.horizontalSeparator()).expandX();
        }

        // Bottom
        WHorizontalList bottomList = add(theme.horizontalList()).expandX().widget();

        //   Active
        bottomList.add(theme.label("Active:"));
        WCheckbox enabled = bottomList.add(theme.checkbox(element.enabled)).widget();
        enabled.action = () -> {
            if (element.enabled != enabled.checked) element.toggle();
        };

        WButton reset = bottomList.add(theme.button(GuiRenderer.RESET)).expandCellX().right().widget();
        reset.action = () -> {
            if (element.enabled != element.defaultEnabled) {
                element.enabled = enabled.checked = element.defaultEnabled;
            }
        };
    }

    @Override
    public void tick() {
        super.tick();

        if (settings != null) {
            element.settings.tick(settings, theme);
        }
    }

    @Override
    protected void onRenderBefore(float delta) {
        if (!Utils.canUpdate()) {
            Systems.get(Hud.class).render(delta, hudElement -> true);
        }
    }
}