package wtf.diablo.client.util.mc.player.skin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

import static net.minecraft.entity.player.EntityPlayer.getOfflineUUID;

public final class SkinUtil {
    public static String getSkinURL(String uuid) {
        try {
            String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid;
            ArrayList<String> lines = getLinesFromURL(url);
            StringBuilder responseLines = new StringBuilder();
            for (String string : lines) {
                responseLines.append(string);
            }
            JsonObject jsonObject = new JsonParser().parse(responseLines.toString()).getAsJsonObject();
            String value = new String(Base64.getDecoder().decode(jsonObject.get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString()));
            JsonObject jsonObject1 = new JsonParser().parse(value).getAsJsonObject();
            return jsonObject1.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return "";
    }

    public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String uuid, String username) {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        ITextureObject object = texturemanager.getTexture(resourceLocationIn);

        if (object == null) {
            object = new ThreadDownloadImageData(null, getSkinURL(uuid), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(username)), new ImageBufferDownload());
            texturemanager.loadTexture(resourceLocationIn, object);
        }

        return (ThreadDownloadImageData) object;
    }

    private static ArrayList<String> getLinesFromURL(String urlString) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            br.lines().forEach(lines::add);
            connection.disconnect();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return lines;
    }
}
