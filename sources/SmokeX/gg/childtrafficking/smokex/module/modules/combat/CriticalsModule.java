// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.combat;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.client.Minecraft;
import gg.childtrafficking.smokex.module.ModuleManager;
import net.minecraft.network.play.client.C0APacketAnimation;
import gg.childtrafficking.smokex.utils.player.MovementUtils;
import gg.childtrafficking.smokex.utils.system.StringUtils;
import gg.childtrafficking.smokex.event.events.network.EventSendPacket;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import net.minecraft.entity.EntityLivingBase;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Criticals", renderName = "Criticals", aliases = { "crits", "crit" }, category = ModuleCategory.COMBAT)
public final class CriticalsModule extends Module
{
    private final EnumProperty<Mode> modeEnumProperty;
    private final BooleanProperty antiCritProperty;
    private final NumberProperty<Integer> ticksProperty;
    private int groundTicks;
    private int ticks;
    private EntityLivingBase target;
    private boolean antiCrittus;
    private final EventListener<EventUpdate> updateEvent;
    private final EventListener<EventSendPacket> sendPacketEvent;
    
    public CriticalsModule() {
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.WATCHDOG);
        this.antiCritProperty = new BooleanProperty("AntiCrit", true);
        this.ticksProperty = new NumberProperty<Integer>("Ticks", 20, 1, 20, 1);
        this.antiCrittus = false;
        this.updateEvent = (event -> {
            this.setSuffix(StringUtils.upperSnakeCaseToPascal(this.modeEnumProperty.getValue().toString()) + " " + this.ticksProperty.getValue());
            if (event.isPre()) {
                this.groundTicks = (MovementUtils.isOnGround() ? (this.groundTicks + 1) : 0);
            }
            ++this.ticks;
            return;
        });
        this.sendPacketEvent = (event -> {
            this.getTarget();
            if (event.getPacket() instanceof C0APacketAnimation && this.target != null && this.ticks + 1 >= 20 / this.ticksProperty.getValue()) {
                this.ticks = 0;
                if (this.antiCritProperty.getValue()) {
                    if (this.target.hurtResistantTime <= 16) {
                        this.antiCrittus = false;
                        if (this.groundTicks > 1) {
                            this.crit();
                        }
                    }
                    else if (!this.antiCrittus && this.groundTicks == 0) {
                        this.antiCrittus = true;
                    }
                }
                else if (this.groundTicks > 1) {
                    this.crit();
                }
            }
        });
    }
    
    private void getTarget() {
        if (ModuleManager.getInstance(KillauraModule.class).getTarget() != null) {
            this.target = ModuleManager.getInstance(KillauraModule.class).getTarget();
            return;
        }
        final MovingObjectPosition mouseOver = Minecraft.getMinecraft().objectMouseOver;
        if (mouseOver != null) {
            if (mouseOver.entityHit instanceof EntityLivingBase) {
                this.target = (EntityLivingBase)mouseOver.entityHit;
            }
            else {
                this.target = null;
            }
        }
    }
    
    public void crit() {
    }
    
    private enum Mode
    {
        WATCHDOG;
    }
}
