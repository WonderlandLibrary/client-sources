/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.vialoadingbase.platform;

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
import dev.intave.vialoadingbase.ViaLoadingBase;
import dev.intave.vialoadingbase.platform.viaversion.VLBViaAPIWrapper;
import dev.intave.vialoadingbase.platform.viaversion.VLBViaConfig;
import dev.intave.vialoadingbase.util.VLBTask;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ViaVersionPlatformImpl
implements ViaPlatform<UUID> {
    private final ViaAPI<UUID> api = new VLBViaAPIWrapper();
    private final Logger logger;
    private final VLBViaConfig config;

    public ViaVersionPlatformImpl(Logger logger) {
        this.logger = logger;
        this.config = new VLBViaConfig(new File(ViaLoadingBase.getInstance().getRunDirectory(), "viaversion.yml"));
    }

    public static List<ProtocolVersion> createVersionList() {
        List<ProtocolVersion> versions = new ArrayList<ProtocolVersion>(ProtocolVersion.getProtocols()).stream().filter(protocolVersion -> protocolVersion != ProtocolVersion.unknown && ProtocolVersion.getProtocols().indexOf(protocolVersion) >= 7).collect(Collectors.toList());
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
    public VLBTask runAsync(Runnable runnable) {
        return new VLBTask(Via.getManager().getScheduler().execute(runnable));
    }

    @Override
    public VLBTask runRepeatingAsync(Runnable runnable, long ticks) {
        return new VLBTask(Via.getManager().getScheduler().scheduleRepeating(runnable, 0L, ticks * 50L, TimeUnit.MILLISECONDS));
    }

    @Override
    public VLBTask runSync(Runnable runnable) {
        return this.runAsync(runnable);
    }

    @Override
    public VLBTask runSync(Runnable runnable, long ticks) {
        return new VLBTask(Via.getManager().getScheduler().schedule(runnable, ticks * 50L, TimeUnit.MILLISECONDS));
    }

    @Override
    public VLBTask runRepeatingSync(Runnable runnable, long ticks) {
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
        return this.config;
    }

    @Override
    public ViaAPI<UUID> getApi() {
        return this.api;
    }

    @Override
    public File getDataFolder() {
        return ViaLoadingBase.getInstance().getRunDirectory();
    }

    @Override
    public String getPluginVersion() {
        return "4.8.1";
    }

    @Override
    public String getPlatformName() {
        return "ViaLoadingBase by FlorianMichael";
    }

    @Override
    public String getPlatformVersion() {
        return "${vialoadingbase_version}";
    }

    @Override
    public boolean isPluginEnabled() {
        return true;
    }

    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return this.config;
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
        if (ViaLoadingBase.getInstance().getDumpSupplier() == null) {
            return new JsonObject();
        }
        return ViaLoadingBase.getInstance().getDumpSupplier().get();
    }
}

