// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.combat;

import java.util.Iterator;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Aimbot", renderName = "Aim Bot", description = "Cops and crims moment", category = ModuleCategory.COMBAT)
public final class AimbotModule extends Module
{
    private float yaw;
    private float pitch;
    private final EventListener<EventUpdate> updateEventListener;
    
    public AimbotModule() {
        this.updateEventListener = (event -> {
            if (!this.mc.isSingleplayer()) {
                this.mc.theWorld.playerEntities.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final EntityPlayer entityPlayer = iterator.next();
                    if (!(!PlayerUtils.isHoldingItem())) {
                        if (!PlayerUtils.isShooterTeammate(entityPlayer) && entityPlayer != this.mc.thePlayer && entityPlayer.posY - this.mc.thePlayer.posY <= 10.0 && this.mc.thePlayer.canEntityBeSeen(entityPlayer)) {
                            if (event.isPre()) {
                                final float[] rotations = PlayerUtils.getPredictedRotations(entityPlayer);
                                this.yaw = rotations[0];
                                this.pitch = rotations[1];
                                event.setYaw(this.yaw);
                                event.setPitch(this.pitch);
                            }
                            else {
                                this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem());
                            }
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        });
    }
}
