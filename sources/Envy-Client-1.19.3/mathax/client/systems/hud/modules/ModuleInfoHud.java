package mathax.client.systems.hud.modules;

import mathax.client.settings.*;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.combat.*;
import mathax.client.utils.render.color.SettingColor;
import mathax.client.systems.hud.HUD;
import mathax.client.systems.hud.HudElement;
import mathax.client.systems.hud.HudRenderer;

import java.util.List;

public class ModuleInfoHud extends HudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<List<Module>> modules = sgGeneral.add(new ModuleListSetting.Builder()
        .name("modules")
        .description("Which modules to display")
        .defaultValue(
            KillAura.class,
            CrystalAura.class,
            AnchorAura.class,
            BedAura.class,
            Surround.class
        )
        .build()
    );

    private final Setting<Boolean> info = sgGeneral.add(new BoolSetting.Builder()
        .name("additional-info")
        .description("Shows additional info from the module next to the name in the module info list.")
        .defaultValue(true)
        .build()
    );

    private final Setting<SettingColor> onColor = sgGeneral.add(new ColorSetting.Builder()
        .name("on-color")
        .description("Color when module is on.")
        .defaultValue(new SettingColor(25, 225, 25))
        .build()
    );

    private final Setting<SettingColor> offColor = sgGeneral.add(new ColorSetting.Builder()
        .name("off-color")
        .description("Color when module is off.")
        .defaultValue(new SettingColor(225, 25, 25))
        .build()
    );

    public ModuleInfoHud(HUD hud) {
        super(hud, "module-info", "Shows activity information about specified modules.", true);
    }

    @Override
    public void update(HudRenderer renderer) {
        if (Modules.get() == null || modules.get().isEmpty()) {
            box.setSize(renderer.textWidth("Module Info"), renderer.textHeight());
            return;
        }

        double width = 0;
        double height = 0;

        int i = 0;
        for (Module module : modules.get()) {
            width = Math.max(width, getModuleWidth(renderer, module));
            height += renderer.textHeight();
            if (i > 0) height += 2;

            i++;
        }

        box.setSize(width, height);
    }

    @Override
    public void render(HudRenderer renderer) {
        double x = box.getX();
        double y = box.getY();

        if (Modules.get() == null || modules.get().isEmpty()) {
            renderer.text("Module Info", x, y, hud.primaryColor.get());
            return;
        }

        for (Module module : modules.get()) {
            renderModule(renderer, module, x + box.alignX(getModuleWidth(renderer, module)), y);

            y += 2 + renderer.textHeight();
        }
    }

    private void renderModule(HudRenderer renderer, Module module, double x, double y) {
        renderer.text(module.title, x, y, hud.primaryColor.get());

        String info = getModuleInfo(module);
        renderer.text(info, x + renderer.textWidth(module.title) + renderer.textWidth(" "), y, module.isActive() ? onColor.get() : offColor.get());
    }

    private double getModuleWidth(HudRenderer renderer, Module module) {
        double width = renderer.textWidth(module.title);
        if (info.get()) width += renderer.textWidth(" ") + renderer.textWidth(getModuleInfo(module));
        return width;
    }

    private String getModuleInfo(Module module) {
        if (module.getInfoString() != null && module.isActive() && info.get()) return module.getInfoString();
        else if (module.isActive()) return "ON";
        else return "OFF";
    }
}
