package mathax.client.systems.modules.render;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.render.Render2DEvent;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class CustomCrosshair extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    public CustomCrosshair() { //this just doesnt work at all
        super(Categories.Render, Items.COMPASS, "custom-crosshair", "Renders a customizable crosshair instead of the Minecraft one.");
    }

    @EventHandler
    public void onRender2D(Render2DEvent event) {
        //TODO: Make.
    }
}
