package koks.gui.customhud.hudcomponent;

import koks.Koks;
import koks.gui.customhud.Component;
import koks.gui.customhud.ComponentSettings;
import koks.gui.customhud.valuehudsystem.ValueHUD;
import koks.modules.Module;
import koks.modules.impl.visuals.ClearTag;
import koks.utilities.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 05:56
 */
public class ModuleList extends Component {

    public ValueHUD clientColor = new ValueHUD("Client Color", true, this);
    public ValueHUD modes = new ValueHUD("Background", "Static", new String[]{"Static", "Shadow"}, this);

    public ValueHUD size = new ValueHUD("Size", 0, 0,9,true,this);

    public ModuleList() {
        super(100, 0, 0, 0);
        setWidth(getMcFontRenderer().getStringWidth(Koks.getKoks().CLIENT_NAME));
        setHeight(getMcFontRenderer().FONT_HEIGHT);

        Koks.getKoks().valueHUDManager.getValueHUDS().add(clientColor);
        Koks.getKoks().valueHUDManager.getValueHUDS().add(size);
        Koks.getKoks().valueHUDManager.getValueHUDS().add(modes);

        this.componentSettings = new ComponentSettings(this, "WaterMark Settings");
    }

    @Override
    public void drawTemplate() {


    }


}
