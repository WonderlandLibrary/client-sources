package me.vaziak.sensation.client.impl.visual;

import org.lwjgl.opengl.GL11;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.EntityLivingRenderEvent;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.ColorProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import net.minecraft.entity.player.EntityPlayer;

public class Chams extends Module {

	public static StringsProperty mode = new StringsProperty("Mode", "The mode of the module", false, true, new String[] {"Hurttime", "Rainbow", "Fill", "Normal"});
    public static ColorProperty fillColor = new ColorProperty("Fill Color", "The color of the fill", () -> mode.getValue().get("Fill") && !mode.getValue().get("Rainbow") && !mode.getValue().get("Normal"), 1F, 0F, 1F, 255);
    public static BooleanProperty viewCheck = new BooleanProperty("View Check", "Only show the cham overlay on the entity when it isn't in view", () -> mode.getValue().get("Rainbow") || mode.getValue().get("Fill"), false);

    public Chams() {
        super("Chams", Category.VISUAL);
        registerValue(mode, fillColor, viewCheck);
    }

    @Collect
    public void onUpdate(PlayerUpdateEvent event) {
    	setMode(mode.getSelectedStrings().get(0) + (viewCheck.getValue() ? " [C]" : ""));
    }
    
    @Collect
    public void onRender(EntityLivingRenderEvent e) {

        if (e.isPre() && e.getEntity() instanceof EntityPlayer) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        } else if (e.isPost() && e.getEntity() instanceof EntityPlayer) {
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0f, 1100000.0f);
        }
    }
}