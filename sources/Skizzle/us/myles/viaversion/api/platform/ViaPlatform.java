/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.platform;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.ViaVersionConfig;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.configuration.ConfigurationProvider;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.platform.TaskId;
import us.myles.ViaVersion.api.platform.ViaConnectionManager;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.viaversion.libs.gson.JsonObject;

public interface ViaPlatform<T> {
    public Logger getLogger();

    public String getPlatformName();

    public String getPlatformVersion();

    default public boolean isProxy() {
        return false;
    }

    public String getPluginVersion();

    public TaskId runAsync(Runnable var1);

    public TaskId runSync(Runnable var1);

    public TaskId runSync(Runnable var1, Long var2);

    public TaskId runRepeatingSync(Runnable var1, Long var2);

    public void cancelTask(TaskId var1);

    public ViaCommandSender[] getOnlinePlayers();

    public void sendMessage(UUID var1, String var2);

    public boolean kickPlayer(UUID var1, String var2);

    default public boolean disconnect(UserConnection connection, String message) {
        if (connection.isClientSide()) {
            return false;
        }
        UUID uuid = connection.get(ProtocolInfo.class).getUuid();
        if (uuid == null) {
            return false;
        }
        return this.kickPlayer(uuid, message);
    }

    public boolean isPluginEnabled();

    public ViaAPI<T> getApi();

    public ViaVersionConfig getConf();

    public ConfigurationProvider getConfigurationProvider();

    public File getDataFolder();

    public void onReload();

    public JsonObject getDump();

    public boolean isOldClientsAllowed();

    public ViaConnectionManager getConnectionManager();
}

