package me.finz0.osiris.gui.clickgui.theme;

import me.finz0.osiris.gui.clickgui.base.ComponentRenderer;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.model.obj.OBJModel;

import java.util.HashMap;

public class Theme {

    public FontRenderer fontRenderer;

    public OBJModel.Texture icons;

    private HashMap<ComponentType, ComponentRenderer> rendererHashMap = new HashMap<>();

    private String themeName;

    private int frameHeight = 15;

    public Theme(String themeName) {

        this.themeName = themeName;
    }

    public void addRenderer(ComponentType componentType, ComponentRenderer componentRenderer) {

        this.rendererHashMap.put(componentType, componentRenderer);
    }

    public HashMap<ComponentType, ComponentRenderer> getRenderer() {

        return rendererHashMap;
    }

    public String getThemeName() {

        return themeName;
    }

    public FontRenderer getFontRenderer() {

        return fontRenderer;
    }

    public int getFrameHeight() {

        return frameHeight;
    }
}
