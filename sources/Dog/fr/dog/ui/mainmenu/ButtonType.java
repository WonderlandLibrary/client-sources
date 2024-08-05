package fr.dog.ui.mainmenu;

import fr.dog.util.InstanceAccess;
import fr.dog.util.render.img.ImageObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;
import org.lwjglx.opengl.InputImplementation;

@Getter
@AllArgsConstructor
public enum ButtonType implements InstanceAccess {

    SINGLEPLAYER("Singleplayer", menuImages.get("singleplayer")),
    MULTIPLAYER("Multiplayer", menuImages.get("multiplayer")),
    SETTINGS("Settings", menuImages.get("settings")),
    ALTMANAGER("AltManager", menuImages.get("altmanager")),
    CLOSE("Close", menuImages.get("close"));


    private final String name;
    private final ImageObject image;
}
