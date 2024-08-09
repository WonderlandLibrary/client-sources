/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.multiplayer;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ServerData {
    public String serverName;
    public String serverIP;
    public ITextComponent populationInfo;
    public ITextComponent serverMOTD;
    public long pingToServer;
    public int version = SharedConstants.getVersion().getProtocolVersion();
    public ITextComponent gameVersion = new StringTextComponent(SharedConstants.getVersion().getName());
    public boolean pinged;
    public List<ITextComponent> playerList = Collections.emptyList();
    private ServerResourceMode resourceMode = ServerResourceMode.PROMPT;
    @Nullable
    private String serverIcon;
    private boolean lanServer;

    public ServerData(String string, String string2, boolean bl) {
        this.serverName = string;
        this.serverIP = string2;
        this.lanServer = bl;
    }

    public CompoundNBT getNBTCompound() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("name", this.serverName);
        compoundNBT.putString("ip", this.serverIP);
        if (this.serverIcon != null) {
            compoundNBT.putString("icon", this.serverIcon);
        }
        if (this.resourceMode == ServerResourceMode.ENABLED) {
            compoundNBT.putBoolean("acceptTextures", false);
        } else if (this.resourceMode == ServerResourceMode.DISABLED) {
            compoundNBT.putBoolean("acceptTextures", true);
        }
        return compoundNBT;
    }

    public ServerResourceMode getResourceMode() {
        return this.resourceMode;
    }

    public void setResourceMode(ServerResourceMode serverResourceMode) {
        this.resourceMode = serverResourceMode;
    }

    public static ServerData getServerDataFromNBTCompound(CompoundNBT compoundNBT) {
        ServerData serverData = new ServerData(compoundNBT.getString("name"), compoundNBT.getString("ip"), false);
        if (compoundNBT.contains("icon", 1)) {
            serverData.setBase64EncodedIconData(compoundNBT.getString("icon"));
        }
        if (compoundNBT.contains("acceptTextures", 0)) {
            if (compoundNBT.getBoolean("acceptTextures")) {
                serverData.setResourceMode(ServerResourceMode.ENABLED);
            } else {
                serverData.setResourceMode(ServerResourceMode.DISABLED);
            }
        } else {
            serverData.setResourceMode(ServerResourceMode.PROMPT);
        }
        return serverData;
    }

    @Nullable
    public String getBase64EncodedIconData() {
        return this.serverIcon;
    }

    public void setBase64EncodedIconData(@Nullable String string) {
        this.serverIcon = string;
    }

    public boolean isOnLAN() {
        return this.lanServer;
    }

    public void copyFrom(ServerData serverData) {
        this.serverIP = serverData.serverIP;
        this.serverName = serverData.serverName;
        this.setResourceMode(serverData.getResourceMode());
        this.serverIcon = serverData.serverIcon;
        this.lanServer = serverData.lanServer;
    }

    public static enum ServerResourceMode {
        ENABLED("enabled"),
        DISABLED("disabled"),
        PROMPT("prompt");

        private final ITextComponent motd;

        private ServerResourceMode(String string2) {
            this.motd = new TranslationTextComponent("addServer.resourcePack." + string2);
        }

        public ITextComponent getMotd() {
            return this.motd;
        }
    }
}

