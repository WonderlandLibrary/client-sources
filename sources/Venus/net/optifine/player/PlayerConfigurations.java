/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.optifine.http.FileDownloadThread;
import net.optifine.http.HttpUtils;
import net.optifine.player.PlayerConfiguration;
import net.optifine.player.PlayerConfigurationReceiver;

public class PlayerConfigurations {
    private static Map mapConfigurations = null;
    private static boolean reloadPlayerItems = Boolean.getBoolean("player.models.reload");
    private static long timeReloadPlayerItemsMs = System.currentTimeMillis();

    public static void renderPlayerItems(BipedModel bipedModel, AbstractClientPlayerEntity abstractClientPlayerEntity, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        PlayerConfiguration playerConfiguration = PlayerConfigurations.getPlayerConfiguration(abstractClientPlayerEntity);
        if (playerConfiguration != null) {
            playerConfiguration.renderPlayerItems(bipedModel, abstractClientPlayerEntity, matrixStack, iRenderTypeBuffer, n, n2);
        }
    }

    public static synchronized PlayerConfiguration getPlayerConfiguration(AbstractClientPlayerEntity abstractClientPlayerEntity) {
        Object object;
        if (reloadPlayerItems && System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L && (object = Minecraft.getInstance().player) != null) {
            PlayerConfigurations.setPlayerConfiguration(((AbstractClientPlayerEntity)object).getNameClear(), null);
            timeReloadPlayerItemsMs = System.currentTimeMillis();
        }
        if ((object = abstractClientPlayerEntity.getNameClear()) == null) {
            return null;
        }
        PlayerConfiguration playerConfiguration = (PlayerConfiguration)PlayerConfigurations.getMapConfigurations().get(object);
        if (playerConfiguration == null) {
            playerConfiguration = new PlayerConfiguration();
            PlayerConfigurations.getMapConfigurations().put(object, playerConfiguration);
            PlayerConfigurationReceiver playerConfigurationReceiver = new PlayerConfigurationReceiver((String)object);
            String string = HttpUtils.getPlayerItemsUrl() + "/users/" + (String)object + ".cfg";
            FileDownloadThread fileDownloadThread = new FileDownloadThread(string, playerConfigurationReceiver);
            fileDownloadThread.start();
        }
        return playerConfiguration;
    }

    public static synchronized void setPlayerConfiguration(String string, PlayerConfiguration playerConfiguration) {
        PlayerConfigurations.getMapConfigurations().put(string, playerConfiguration);
    }

    private static Map getMapConfigurations() {
        if (mapConfigurations == null) {
            mapConfigurations = new HashMap();
        }
        return mapConfigurations;
    }
}

