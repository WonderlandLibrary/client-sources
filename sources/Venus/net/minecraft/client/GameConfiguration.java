/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import com.mojang.authlib.properties.PropertyMap;
import java.io.File;
import java.net.Proxy;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.ScreenSize;
import net.minecraft.client.resources.FolderResourceIndex;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.util.Session;

public class GameConfiguration {
    public static GameConfiguration instance;
    public final UserInformation userInfo;
    public final ScreenSize displayInfo;
    public final FolderInformation folderInfo;
    public final GameInformation gameInfo;
    public final ServerInformation serverInfo;

    public GameConfiguration(UserInformation userInformation, ScreenSize screenSize, FolderInformation folderInformation, GameInformation gameInformation, ServerInformation serverInformation) {
        this.userInfo = userInformation;
        this.displayInfo = screenSize;
        this.folderInfo = folderInformation;
        this.gameInfo = gameInformation;
        this.serverInfo = serverInformation;
        instance = this;
    }

    public static class UserInformation {
        public final Session session;
        public final PropertyMap userProperties;
        public final PropertyMap profileProperties;
        public final Proxy proxy;

        public UserInformation(Session session, PropertyMap propertyMap, PropertyMap propertyMap2, Proxy proxy) {
            this.session = session;
            this.userProperties = propertyMap;
            this.profileProperties = propertyMap2;
            this.proxy = proxy;
        }
    }

    public static class FolderInformation {
        public final File gameDir;
        public final File resourcePacksDir;
        public final File assetsDir;
        @Nullable
        public final String assetIndex;

        public FolderInformation(File file, File file2, File file3, @Nullable String string) {
            this.gameDir = file;
            this.resourcePacksDir = file2;
            this.assetsDir = file3;
            this.assetIndex = string;
        }

        public ResourceIndex getAssetsIndex() {
            return this.assetIndex == null ? new FolderResourceIndex(this.assetsDir) : new ResourceIndex(this.assetsDir, this.assetIndex);
        }
    }

    public static class GameInformation {
        public final boolean isDemo;
        public final String version;
        public final String versionType;
        public final boolean disableMultiplayer;
        public final boolean disableChat;

        public GameInformation(boolean bl, String string, String string2, boolean bl2, boolean bl3) {
            this.isDemo = bl;
            this.version = string;
            this.versionType = string2;
            this.disableMultiplayer = bl2;
            this.disableChat = bl3;
        }
    }

    public static class ServerInformation {
        @Nullable
        public final String serverName;
        public final int serverPort;

        public ServerInformation(@Nullable String string, int n) {
            this.serverName = string;
            this.serverPort = n;
        }
    }
}

