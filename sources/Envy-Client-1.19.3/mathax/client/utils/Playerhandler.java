package mathax.client.utils;

import com.mojang.authlib.GameProfile;
import mathax.client.eventbus.EventHandler;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.client.CapesModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import mathax.client.systems.modules.client.CapesModule;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static mathax.client.MatHax.mc;

public class Playerhandler {
    public static Map<String, Identifier> capes = new HashMap<>();

    public interface ReturnCapeTexture {
        void response(Identifier id);
    }

    public static void loadPlayerCape(GameProfile player, ReturnCapeTexture response) {
        if(Modules.get().isActive(CapesModule.class)) {
            Util.getMainWorkerExecutor().execute(() -> {
                try {
                    if (Modules.get().get(CapesModule.class).capeurl == "https://raw.githubusercontent.com/Volcanware/Envy-Client/Now-Fixed/EnvyCape.png") {
                        String uuid = player.getId().toString();
                        NativeImageBackedTexture nIBT = getCapeFromURL(Modules.get().get(CapesModule.class).capeurl);
                        Identifier capeTexture = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("of-capes-" + uuid, nIBT);
                        capes.put(uuid, capeTexture);
                        response.response(capeTexture);
                    }
                    if (Modules.get().get(CapesModule.class).capeurl ==  "http://s.optifine.net/capes/%s.png") {
                        String uuid = player.getId().toString();
                        NativeImageBackedTexture nIBT = getCapeFromURL(String.format("http://s.optifine.net/capes/%s.png", player.getName()));
                        Identifier capeTexture = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("of-capes-" + uuid, nIBT);
                        capes.put(uuid, capeTexture);
                        response.response(capeTexture);
                    }
                    if (Modules.get().get(CapesModule.class).capeurl ==  "23.95.137.176") {
                        String uuid = player.getId().toString();
                        NativeImageBackedTexture nIBT = getCapeFromURL(String.format("https://api.cosmetica.cc/get/cloak?username=" + player.getName()));
                        Identifier capeTexture = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("of-capes-" + uuid, nIBT);
                        capes.put(uuid, capeTexture);
                        response.response(capeTexture);
                    }
                    if (Modules.get().get(CapesModule.class).capeurl ==  "https://raw.githubusercontent.com/Volcanware/Envy-Client/Now-Fixed/ToxinCape.png") {
                        String uuid = player.getId().toString();
                        NativeImageBackedTexture nIBT = getCapeFromURL(String.format("https://raw.githubusercontent.com/Volcanware/Envy-Client/Now-Fixed/ToxinCape.png"));
                        Identifier capeTexture = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("of-capes-" + uuid, nIBT);
                        capes.put(uuid, capeTexture);
                        response.response(capeTexture);
                    }
                    if (Modules.get().get(CapesModule.class).capeurl ==  "https://raw.githubusercontent.com/Volcanware/Envy-Client/Now-Fixed/VolcanwareCape.png") {
                        String uuid = player.getId().toString();
                        NativeImageBackedTexture nIBT = getCapeFromURL(String.format("https://raw.githubusercontent.com/Volcanware/Envy-Client/Now-Fixed/VolcanwareCape.png"));
                        Identifier capeTexture = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("of-capes-" + uuid, nIBT);
                        capes.put(uuid, capeTexture);
                        response.response(capeTexture);
                    }
                    if (Modules.get().get(CapesModule.class).CDOSCape ==  "https://cdn.discordapp.com/attachments/1121034355796619337/1156810304974495744/EnvyCapeCDOS.png?ex=6516530d&is=6515018d&hm=95ab8864826b5ae7f3b9d8274dd1e1d3232cbfedb5d81b832de2dde9fc0ddc0e&") {
                        if (mc.player.getUuid().equals("f3611166-e8a6-4123-a9e1-f7cc01463698")) {
                            String uuid = "f3611166-e8a6-4123-a9e1-f7cc01463698";
                            NativeImageBackedTexture nIBT = getCapeFromURL(String.format("https://cdn.discordapp.com/attachments/1121034355796619337/1156810304974495744/EnvyCapeCDOS.png?ex=6516530d&is=6515018d&hm=95ab8864826b5ae7f3b9d8274dd1e1d3232cbfedb5d81b832de2dde9fc0ddc0e&"));
                            Identifier capeTexture = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("of-capes-" + uuid, nIBT);
                            capes.put(uuid, capeTexture);
                            response.response(capeTexture);
                        }
                    }
                } catch (Exception ignored) {
                }
            });
        }
    }

    public static NativeImageBackedTexture getCapeFromURL(String capeStringURL) {
        try {
            URL capeURL = new URL(capeStringURL);
            return getCapeFromStream(capeURL.openStream());
        } catch (IOException e) {
            return null;
        }
    }

    public static NativeImageBackedTexture getCapeFromStream(InputStream image) {
        NativeImage cape = null;
        try {
            cape = NativeImage.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (cape != null) {
            NativeImageBackedTexture nIBT = new NativeImageBackedTexture(parseCape(cape));
            return nIBT;
        }
        return null;
    }

    public static NativeImage parseCape(NativeImage image) {
        int imageWidth = 64;
        int imageHeight = 32;
        int imageSrcWidth = image.getWidth();
        int srcHeight = image.getHeight();

        for (int imageSrcHeight = image.getHeight(); imageWidth < imageSrcWidth
            || imageHeight < imageSrcHeight; imageHeight *= 2) {
            imageWidth *= 2;
        }

        NativeImage imgNew = new NativeImage(imageWidth, imageHeight, true);
        for (int x = 0; x < imageSrcWidth; x++) {
            for (int y = 0; y < srcHeight; y++) {
                imgNew.setColor(x, y, image.getColor(x, y));
            }
        }
        image.close();
        return imgNew;
    }
}
