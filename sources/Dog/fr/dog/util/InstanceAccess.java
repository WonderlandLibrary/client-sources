package fr.dog.util;

import fr.dog.util.render.img.ImageManager;
import fr.dog.util.render.img.ImageObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.HashMap;

public interface InstanceAccess {
    Minecraft mc = Minecraft.getMinecraft();
    HashMap<String, ImageObject> menuImages = ImageManager.menuImages;
    HashMap<String, ImageObject> inGameImages = ImageManager.inGameImages;
    default ScaledResolution scaledResolution() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }
}