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
                        final int index = i;
                        final String capeUrl = capeurls[i];
                        final ResourceLocation resourcelocation = new ResourceLocation("capes/" + username + "/" + index);
                        final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();

                        Thread thread = new Thread(() -> {
                            try {
                                Thread.sleep(100);
                                ThreadDownloadImageData headTexture = new ThreadDownloadImageData(null, capeUrl, null, null);
                                texturemanager.loadTexture(resourcelocation, headTexture);
                                frames[index] = resourcelocation;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                        thread.start();
                    }

                    vantageUser.setCapeResourceLocationArray(frames);
                    capesToDownload.remove(username);
                }
            }
        }
    }
}
