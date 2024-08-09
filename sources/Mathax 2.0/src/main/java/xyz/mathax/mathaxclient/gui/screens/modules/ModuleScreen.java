package xyz.mathax.mathaxclient.gui.screens.modules;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.mathax.ModuleBindChangedEvent;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.WindowScreen;
import xyz.mathax.mathaxclient.utils.gui.Cell;
import xyz.mathax.mathaxclient.gui.widgets.WKeybind;
import xyz.mathax.mathaxclient.gui.widgets.WWidget;
import xyz.mathax.mathaxclient.gui.widgets.containers.WContainer;
import xyz.mathax.mathaxclient.gui.widgets.containers.WHorizontalList;
import xyz.mathax.mathaxclient.gui.widgets.containers.WSection;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WCheckbox;
import xyz.mathax.mathaxclient.gui.widgets.pressable.WFavorite;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.utils.Utils;

public class ModuleScreen extends WindowScreen {
    private final Module module;

    private WContainer settingsContainer;

    private WKeybind keybind;

    public ModuleScreen(Theme theme, Module module) {
        super(theme, theme.favorite(module.favorite), module.name);
        ((WFavorite) window.icon).action = () -> module.favorite = ((WFavorite) window.icon).checked;

        this.module = module;
    }

    @Override
    public void initWidgets() {
        // Description
        add(theme.label(module.description, Utils.getWindowWidth() / 2.0));

        // Settings
        if (module.settings.groups.size() > 0) {
            settingsContainer = add(theme.verticalList()).expandX().widget();
            settingsContainer.add(theme.settings(module.settings)).expandX();
        }

        // Custom widget
        WWidget widget = module.getWidget(theme);

        if (widget != null) {
            add(theme.horizontalSeparator()).expandX();
            Cell<WWidget> cell = add(widget);
            if (widget instanceof WContainer) {
                cell.expandX();
            }
        }

        // Bind
        WSection section = add(theme.section("Bind", true)).expandX().widget();
        keybind = section.add(theme.keybind(module.keybind)).expandX().widget();
        keybind.actionOnSet = () -> Modules.get().setModuleToBind(module);

        // Toggle on bind release
        WHorizontalList tobr = section.add(theme.horizontalList()).widget();

        tobr.add(theme.label("Toggle on bind release: "));
        WCheckbox tobrC = tobr.add(theme.checkbox(module.toggleOnBindRelease)).widget();
        tobrC.action = () -> module.toggleOnBindRelease = tobrC.checked;

        // Chat feedback
        WHorizontalList cf = section.add(theme.horizontalList()).widget();

        cf.add(theme.label("Chat feedback: "));
        WCheckbox cfC = cf.add(theme.checkbox(module.chatFeedback)).widget();
        cfC.action = () -> module.chatFeedback = cfC.checked;

        // Toasts
        WHorizontalList t = section.add(theme.horizontalList()).expandCellX().widget();

        t.add(theme.label("Toasts: "));
        WCheckbox tC = t.add(theme.checkbox(module.toasts)).widget();
        tC.action = () -> module.toasts = tC.checked;

        add(theme.horizontalSeparator()).expandX();

        // Bottom
        WHorizontalList bottom = add(theme.horizontalList()).expandX().widget();

        //   Active
        bottom.add(theme.label("Active: "));
        WCheckbox enabled = bottom.add(theme.checkbox(module.isEnabled())).expandCellX().widget();
        enabled.action = () -> {
            if (module.isEnabled() != enabled.checked) {
                module.toggle();
            }
        };
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return !Modules.get().isBinding();
    }

    @Override
    public void tick() {
        super.tick();

        module.settings.tick(settingsContainer, theme);
    }

    @EventHandler
    private void onModuleBindChanged(ModuleBindChangedEvent event) {
        keybind.reset();
    }
}
