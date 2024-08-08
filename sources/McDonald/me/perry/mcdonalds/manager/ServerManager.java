// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.manager;

import java.util.Objects;
import net.minecraft.client.network.NetHandlerPlayClient;
import java.util.Arrays;
import me.perry.mcdonalds.features.modules.client.HUD;
import me.perry.mcdonalds.util.Timer;
import java.text.DecimalFormat;
import me.perry.mcdonalds.features.Feature;

public class ServerManager extends Feature
{
    private final float[] tpsCounts;
    private final DecimalFormat format;
    private final Timer timer;
    private float TPS;
    private long lastUpdate;
    private String serverBrand;
    
    public ServerManager() {
        this.tpsCounts = new float[10];
        this.format = new DecimalFormat("##.00#");
        this.timer = new Timer();
        this.TPS = 20.0f;
        this.lastUpdate = -1L;
        this.serverBrand = "";
    }
    
    public void onPacketReceived() {
        this.timer.reset();
    }
    
    public boolean isServerNotResponding() {
        return this.timer.passedMs(HUD.getInstance().lagTime.getValue());
    }
    
    public long serverRespondingTime() {
        return this.timer.getPassedTimeMs();
    }
    
    public void update() {
        final long currentTime = System.currentTimeMillis();
        if (this.lastUpdate == -1L) {
            this.lastUpdate = currentTime;
            return;
        }
        final long timeDiff = currentTime - this.lastUpdate;
        float tickTime = timeDiff / 20.0f;
        if (tickTime == 0.0f) {
            tickTime = 50.0f;
        }
        float tps;
        if ((tps = 1000.0f / tickTime) > 20.0f) {
            tps = 20.0f;
        }
        System.arraycopy(this.tpsCounts, 0, this.tpsCounts, 1, this.tpsCounts.length - 1);
        this.tpsCounts[0] = tps;
        double total = 0.0;
        for (final float f : this.tpsCounts) {
            total += f;
        }
        if ((total /= this.tpsCounts.length) > 20.0) {
            total = 20.0;
        }
        this.TPS = Float.parseFloat(this.format.format(total));
        this.lastUpdate = currentTime;
    }
    
    @Override
    public void reset() {
        Arrays.fill(this.tpsCounts, 20.0f);
        this.TPS = 20.0f;
    }
    
    public float getTpsFactor() {
        return 20.0f / this.TPS;
    }
    
    public float getTPS() {
        return this.TPS;
    }
    
    public String getServerBrand() {
        return this.serverBrand;
    }
    
    public void setServerBrand(final String brand) {
        this.serverBrand = brand;
    }
    
    public int getPing() {
        if (fullNullCheck()) {
            return 0;
        }
        try {
            return Objects.requireNonNull(ServerManager.mc.getConnection()).getPlayerInfo(ServerManager.mc.getConnection().getGameProfile().getId()).getResponseTime();
        }
        catch (Exception e) {
            return 0;
        }
    }
}
