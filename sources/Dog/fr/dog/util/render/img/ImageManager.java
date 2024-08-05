package fr.dog.util.render.img;

import net.minecraft.util.ResourceLocation;
import java.util.HashMap;

public class ImageManager {
    public static HashMap<String, ImageObject> menuImages = new HashMap<>(){{

        put("wallpaper", new ImageObject(new ResourceLocation("dogclient/background/wallpaper.jpg")));
        put("singleplayer", new ImageObject(new ResourceLocation("dogclient/icons/singleplayer.png")));
        put("multiplayer", new ImageObject(new ResourceLocation("dogclient/icons/multiplayer.png")));
        put("settings", new ImageObject(new ResourceLocation("dogclient/icons/settings.png")));
        put("close", new ImageObject(new ResourceLocation("dogclient/icons/close.png")));
        put("altmanager", new ImageObject(new ResourceLocation("dogclient/icons/altmanager.png")));

    }};

    public static HashMap<String, ImageObject> inGameImages = new HashMap<>(){{

        put("sessionWin", new ImageObject(new ResourceLocation("dogclient/icons/session/wins.png")));
        put("sessionTime", new ImageObject(new ResourceLocation("dogclient/icons/session/time.png")));
        put("sessionKD", new ImageObject(new ResourceLocation("dogclient/icons/session/swords.png")));

    }};

    public static void loadMenuImages(){
        menuImages.values().forEach(ImageObject::loadAsync);
    }
    public static void loadInGameImages(){
        inGameImages.values().forEach(ImageObject::loadAsync);
    }
    public static void unloadMenuImages(){
        menuImages.values().forEach(ImageObject::unload);
    }
    public static void unloadInGameImages(){
        inGameImages.values().forEach(ImageObject::unload);
    }

}
