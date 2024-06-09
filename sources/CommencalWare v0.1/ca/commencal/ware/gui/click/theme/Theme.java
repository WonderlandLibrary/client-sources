package ca.commencal.ware.gui.click.theme;

import ca.commencal.ware.gui.click.base.ComponentRenderer;
import ca.commencal.ware.gui.click.base.ComponentType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.model.obj.OBJModel.Texture;

import java.util.HashMap;

public class Theme {

    public FontRenderer fontRenderer;

    public Texture icons;

    private HashMap<ComponentType, ComponentRenderer> rendererHashMap = new HashMap<ComponentType, ComponentRenderer>();

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
