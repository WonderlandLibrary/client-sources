package best.actinium.module.impl.combat;

import best.actinium.Actinium;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.module.impl.world.ScaffoldModule;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.NumberProperty;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*
 * Main part of the code is in Timer.java
 */

/**
 * @author Nyghtfull
 * @since 1/11/2024
 */
@ModuleInfo(
        name = "Tick Base",
        description = "Some Skip ticks and timer balance that gives u adavantage or smth i am lazy to make an actually description balls coockc.",
        category = ModuleCategory.COMBAT
)
public class TickBaseModule extends Module {
    public NumberProperty coolDown = new NumberProperty("Cool Down", this, 0, 1000L, 10000L, 1),
        minRange = new NumberProperty("Min Range", this, 0, 1, 6, 0.1),
        maxRange = new NumberProperty("Max Range", this, 0, 1, 6, 0.1),
        minElapsted = new NumberProperty("Min Elapsted",this,0,3,10,0.1),
        maxElapsted = new NumberProperty("Max Elapsted",this,0,3,10,0.1);
    public final BooleanProperty shouldTimer = new BooleanProperty("Should Timer", this, false);
    public final BooleanProperty shouldLastLonger = new BooleanProperty("Should Last longer", this, false);
    public NumberProperty boostTime = new NumberProperty("Time Untill Boost", this, 0, 100, 2000, 1)
                .setHidden(() -> !shouldTimer.isEnabled()),
        resetTime = new NumberProperty("Time Untill Reset", this, 0, 100, 2000, 1)
                .setHidden(() -> !shouldTimer.isEnabled()),
        skipTimeAmount = new NumberProperty("Skip Ticks Time MS", this, 0, 100, 2000, 1)
                .setHidden(() -> !shouldLastLonger.isEnabled()),
        minStarTimer = new NumberProperty("Min Start Timer",this,0,1,10,0.05)
                .setHidden(() -> !shouldTimer.isEnabled()),
        maxStarTimer = new NumberProperty("Max Start Timer",this,0,1,10,0.05)
                .setHidden(() -> !shouldTimer.isEnabled()),
        minEndTimer = new NumberProperty("Min End Timer",this,0,1,10,0.05)
                .setHidden(() -> !shouldTimer.isEnabled()),
        maxEndTimer = new NumberProperty("Max End Timer",this,0,1,10,0.05)
                .setHidden(() -> !shouldTimer.isEnabled());
    public long balanceCounter  = 0L;
    private long lastTime;
    private WorldClient lastWorld = null;

    @Callback
    public void onPacket(PacketEvent event) {
        this.setSuffix(minRange.getValue().toString());
        if(event.getType() == EventType.OUTGOING) {
            final Packet<?> packet = event.getPacket();

            if (
                    Actinium.INSTANCE.getModuleManager().get(ScaffoldModule.class).isEnabled()
            ) {
                return;
            }

            if (packet instanceof C03PacketPlayer) {
                if (this.lastWorld != null && this.lastWorld != mc.theWorld) {
                    this.balanceCounter = 0L;
                }

                if (this.balanceCounter > 0L) {
                    --this.balanceCounter;
                }

                final long diff = System.currentTimeMillis() - this.lastTime;
                this.balanceCounter += (diff - 50L) * -3L;
                this.lastTime = System.currentTimeMillis();

                this.lastWorld = mc.theWorld;
            }
        } else if(event.getType() == EventType.INCOMING) {
            final Packet<?> packet = event.getPacket();
            if (
                    Actinium.INSTANCE.getModuleManager().get(ScaffoldModule.class).isEnabled()
            ) {
                return;
            }

            if (packet instanceof S08PacketPlayerPosLook) {
                this.balanceCounter -= 100L;
            }
        }
    }
}
