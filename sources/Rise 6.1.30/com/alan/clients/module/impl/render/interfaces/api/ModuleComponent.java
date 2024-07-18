package com.alan.clients.module.impl.render.interfaces.api;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.impl.render.ClickGUI;
import com.alan.clients.module.impl.render.Interface;
import com.alan.clients.util.vector.Vector2d;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@Getter
@Setter
public final class ModuleComponent {

    public Module module;
    public Vector2d position = new Vector2d(5000, 0), targetPosition = new Vector2d(5000, 0);
    public float animationTime;
    public String tag = "";
    public float nameWidth = 0, tagWidth;
    public Color color = Color.WHITE;
    public String translatedName = "";
    public boolean hidden = false;

    public String displayName = "";
    public String displayTag = "";
    public boolean hasTag;

    public float getTotalWidth() {
        return nameWidth + tagWidth;
    }

    public ModuleComponent(final Module module) {
        this.module = module;
    }


    public boolean shouldDisplay(Interface instance) {
        if (this.getModule() instanceof ClickGUI) return false;
        if (!this.getModule().getModuleInfo().allowDisable()) return false;

        switch (instance.getModulesToShow().getValue().getName()) {
            case "All": {
                return true;
            }
            case "Exclude render": {
                return !this.getModule().getModuleInfo().category().equals(Category.RENDER);
            }
            case "Only bound": {
                return this.getModule().getKey() != Keyboard.KEY_NONE;
            }
        }
        return true;
    }
}