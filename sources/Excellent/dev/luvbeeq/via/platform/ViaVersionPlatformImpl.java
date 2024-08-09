package dev.luvbeeq.via.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.VersionInfo;
import dev.luvbeeq.via.ViaLoadingBase;
import dev.luvbeeq.via.platform.viaversion.ViaAPIWrapper;
import dev.luvbeeq.via.platform.viaversion.ViaConfig;
import dev.luvbeeq.via.util.ViaTask;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ViaVersionPlatformImpl implements ViaPlatform<UUID> {

    private final ViaAPI<UUID> api = new ViaAPIWrapper();

    private final Logger logger;
    private final ViaConfig config;

    public ViaVersionPlatformImpl(final Logger logger) {
        this.logger = logger;
        config = new ViaConfig(new File(ViaLoadingBase.getInstance().getRunDirectory(), "viaversion.yml"));
    }

    public static List<ProtocolVersion> createVersionList() {
        final List<ProtocolVersion> versions = new ArrayList<>(ProtocolVersion.getProtocols()).stream().filter(protocolVersion -> protocolVersion != ProtocolVersion.unknown && ProtocolVersion.getProtocols().indexOf(protocolVersion) >= 7).collect(Collectors.toList());
        Collections.reverse(versions);
        return versions;
    }

    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        return new ViaCommandSender[0];
    }

    @Override
    public void sendMessage(UUID uuid, String msg) {
        if (uuid == null) {
            this.getLogger().info(msg);
        } else {
            this.getLogger().info("[" + uuid + "] " + msg);
        }
    }

    @Override
    public boolean kickPlayer(UUID uuid, String s) {
        return false;
    }

    @Override
    public boolean disconnect(UserConnection connection, String message) {
        return ViaPlatform.super.disconnect(connection, message);
    }

    @Override
    public ViaTask runAsync(Runnable runnable) {
        return new ViaTask(Via.getManager().getScheduler().execute(runnable));
    }

    @Override
    public ViaTask runRepeatingAsync(Runnable runnable, long ticks) {
        return new ViaTask(Via.getManager().getScheduler().scheduleRepeating(runnable, 0, ticks * 50, TimeUnit.MILLISECONDS));
    }

    @Override
    public ViaTask runSync(Runnable runnable) {
        return this.runAsync(runnable);
    }

    @Override
    public ViaTask runSync(Runnable runnable, long ticks) {
        return new ViaTask(Via.getManager().getScheduler().schedule(runnable, ticks * 50, TimeUnit.MILLISECONDS));
    }

    @Override
    public ViaTask runRepeatingSync(Runnable runnable, long ticks) {
        return this.runRepeatingAsync(runnable, ticks);
    }

    @Override
    public boolean isProxy() {
        return true;
    }

    @Override
    public void onReload() {
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public ViaVersionConfig getConf() {
        return config;
    }

    @Override
    public ViaAPI<UUID> getApi() {
        return api;
    }

    @Override
    public File getDataFolder() {
        return ViaLoadingBase.getInstance().getRunDirectory();
    }

    @Override
    public String getPluginVersion() {
        return VersionInfo.VERSION;
    }

    @Override
    public String getPlatformName() {
        return "ViaVersion by Intave";
    }

    @Override
    public String getPlatformVersion() {
        return ViaLoadingBase.VERSION;
    }

    @Override
    public boolean isPluginEnabled() {
        return true;
    }

    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return config;
    }

    @Override
    public boolean isOldClientsAllowed() {
        return true;
    }

    @Override
    public Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
        return ViaPlatform.super.getUnsupportedSoftwareClasses();
    }

    @Override
    public boolean hasPlugin(String s) {
        return false;
    }

    @Override
    public JsonObject getDump() {
        if (ViaLoadingBase.getInstance().getDumpSupplier() == null) return new JsonObject();

        return ViaLoadingBase.getInstance().getDumpSupplier().get();
    }
}
