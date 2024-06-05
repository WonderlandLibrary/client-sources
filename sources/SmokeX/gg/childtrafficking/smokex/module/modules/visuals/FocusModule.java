// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.visuals;

import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import gg.childtrafficking.smokex.utils.player.PitUtils;
import gg.childtrafficking.smokex.SmokeXClient;
import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(renderName = "Focus", name = "Focus", description = "Focus on certiain players", category = ModuleCategory.VISUALS)
public final class FocusModule extends Module
{
    private final EnumProperty<Mode> modeEnumProperty;
    private EventListener<EventUpdate> eventUpdateEventListener;
    
    public FocusModule() {
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.PIT);
        this.eventUpdateEventListener = (event -> {
            if (event.isPre()) {
                this.mc.theWorld.playerEntities.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final EntityPlayer retard = iterator.next();
                    if (retard instanceof EntityPlayerSP) {
                        continue;
                    }
                    else if (!this.shouldShow(retard)) {
                        retard.posY = 1.23456789E8;
                    }
                    else {
                        continue;
                    }
                }
            }
        });
    }
    
    @Override
    public void onDisable() {
        for (final EntityPlayer retard : this.mc.theWorld.playerEntities) {
            if (!(retard instanceof EntityPlayerSP) && retard.posY > 69420.0) {
                retard.posY = retard.serverPosY / 32.0;
            }
        }
        super.onDisable();
    }
    
    private boolean shouldShow(final EntityPlayer entity) {
        if (entity instanceof EntityPlayerSP) {
            return true;
        }
        if (SmokeXClient.getInstance().getPlayerManager().isEnemy(entity.getName())) {
            return true;
        }
        switch (this.modeEnumProperty.getValue()) {
            case PIT: {
                return !PitUtils.isNon(entity);
            }
            case BOTS: {
                return PlayerUtils.checkPing(entity);
            }
            case STREAKING: {
                return entity.getHealth() < 10.0f || PlayerUtils.checkPing(entity);
            }
            default: {
                return false;
            }
        }
    }
    
    private enum Mode
    {
        PIT, 
        BOTS, 
        STREAKING;
    }
}
