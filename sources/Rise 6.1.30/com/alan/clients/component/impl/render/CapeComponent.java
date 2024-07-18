package com.alan.clients.component.impl.render;

import com.alan.clients.component.Component;
import com.alan.clients.component.impl.player.IRCInfoComponent;
import com.alan.clients.component.impl.player.UserInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class CapeComponent extends Component {
    public static HashMap<String, String[]> capesToDownload = new HashMap<>();
    public static void updateCapes() {
        for (HashMap.Entry<String, UserInfo> entry : IRCInfoComponent.usersMap.entrySet()) {
            UserInfo vantageUser = entry.getValue();
            if (vantageUser.hasCape() && vantageUser.getCapeResourceLocationArray() != null) {
                vantageUser.setFpt(vantageUser.getFpt() + 1);
                if (vantageUser.getFpt() >= vantageUser.getCapeResourceLocationArray().length) {
                    vantageUser.setFpt(0);
                }
            }
        }
    }
    public static void loadCapes() {
        if (!capesToDownload.isEmpty()) {
            for (HashMap.Entry<String, String[]> entry : capesToDownload.entrySet()) {
                String username = entry.getKey();
                String[] capeurls = entry.getValue();
                UserInfo vantageUser = IRCInfoComponent.usersMap.get(username);

                ResourceLocation[] frames = new ResourceLocation[capeurls.length];

                if (!vantageUser.isDownloading()) {
                    vantageUser.setDownloading(true);

                    for (int i = 0; i < capeurls.length; i++) {
                        final ResourceLocation resourcelocation = new ResourceLocation("capes/" + username + "/" + i);
                        final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();

                        capesToDownload.remove(username);
                        ThreadDownloadImageData headTexture = new ThreadDownloadImageData(null, capeurls[i], null, null);

                        texturemanager.loadTexture(resourcelocation, headTexture);
                        frames[i] = resourcelocation;
                    }
                    vantageUser.setCapeResourceLocationArray(frames);
                }

//                ThreadDownloadImageData headTexture = new ThreadDownloadImageData(null, capeurl, null, new ImageBufferDownload());
//                mc.getTextureManager().loadTexture(resourceLocation, headTexture);
            }
        }
    }
}
