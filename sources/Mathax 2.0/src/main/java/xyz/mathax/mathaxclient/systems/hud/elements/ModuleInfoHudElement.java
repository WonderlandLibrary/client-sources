package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.gui.renderer.OverlayRenderer;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.hud.HudElement;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.combat.CrystalAura;
import xyz.mathax.mathaxclient.systems.modules.combat.KillAura;
import xyz.mathax.mathaxclient.systems.modules.combat.Surround;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;

import java.util.List;

public class ModuleInfoHudElement extends HudElement {
    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup colorsSettings = settings.createGroup("Colors");

    // General

    private final Setting<List<Module>> modulesSetting = generalSettings.add(new ModuleListSetting.Builder()
            .name("Modules")
            .description("Modules to display")
            .defaultValue(
                    KillAura.class,
                    CrystalAura.class,
                    /*AnchorAura.class,
                    BedAura.class,*/
                    Surround.class
            )
            .build()
    );

    private final Setting<Boolean> infoSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Additional info")
            .description("Shows additional info from the module next to the name in the module info list.")
            .defaultValue(true)
            .build()
    );

    // Colors

    private final Setting<SettingColor> onColorSetting = colorsSettings.add(new ColorSetting.Builder()
            .name("On color")
            .description("Color when module is on.")
            .defaultValue(new SettingColor(25, 225, 25))
            .build()
    );

    private final Setting<SettingColor> offColorSetting = colorsSettings.add(new ColorSetting.Builder()
            .name("Off color")
            .description("Color when module is off.")
            .defaultValue(new SettingColor(225, 25, 25))
            .build()
    );

    public ModuleInfoHudElement(Hud hud) {
        super(hud, "Module Info", "Displays if selected modules are enabled or disabled.");
    }

    @Override
    public void update(OverlayRenderer renderer) {
        if (Modules.get() == null || modulesSetting.get().isEmpty()) {
            box.setSize(renderer.textWidth(name), renderer.textHeight());
            return;
        }

        double width = 0;
        double height = 0;

        int i = 0;
        for (Module module : modulesSetting.get()) {
            width = Math.max(width, getModuleWidth(renderer, module));
            height += renderer.textHeight();
            if (i > 0) {
                height += 2;
            }

            i++;
        }

        box.setSize(width, height);
    }

    @Override
    public void render(OverlayRenderer renderer) {
        double x = box.getX();
        double y = box.getY();

        if (Modules.get() == null || modulesSetting.get().isEmpty()) {
            renderer.text(name, x, y, hud.primaryColorSetting.get());
            return;
        }

        for (Module module : modulesSetting.get()) {
            renderModule(renderer, module, x + box.alignX(getModuleWidth(renderer, module)), y);

            y += 2 + renderer.textHeight();
        }
    }

    private void renderModule(OverlayRenderer renderer, Module module, double x, double y) {
        renderer.text(module.name, x, y, hud.primaryColorSetting.get());

        String info = getModuleInfo(module);
        renderer.text(info, x + renderer.textWidth(module.name) + renderer.textWidth(" "), y, module.isEnabled() ? onColorSetting.get() : offColorSetting.get());
    }

    private double getModuleWidth(OverlayRenderer renderer, Module module) {
        double width = renderer.textWidth(module.name);
        if (infoSetting.get()) {
            width += renderer.textWidth(" ") + renderer.textWidth(getModuleInfo(module));
        }

        return width;
    }

    private String getModuleInfo(Module module) {
        if (module.getInfoString() != null && module.isEnabled() && infoSetting.get()) {
            return module.getInfoString();
        } else if (module.isEnabled()) {
            return "ON";
        } else {
            return "OFF";
        }
    }
}
