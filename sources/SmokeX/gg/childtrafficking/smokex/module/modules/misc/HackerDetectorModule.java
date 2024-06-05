// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.misc;

import gg.childtrafficking.smokex.SmokeXClient;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import net.minecraft.potion.Potion;
import net.minecraft.client.entity.EntityPlayerSP;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import java.util.UUID;
import java.util.Map;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "HackerDetector", renderName = "Hacker Detector", description = "Catches dirty little Rise users", category = ModuleCategory.MISC)
public final class HackerDetectorModule extends Module
{
    private final BooleanProperty alert;
    private final NumberProperty<Integer> vlAlertProperty;
    private final Map<UUID, Integer> VL_FAP;
    private final EventListener<EventUpdate> updateEventListener;
    
    public HackerDetectorModule() {
        this.alert = new BooleanProperty("Alert", true);
        this.vlAlertProperty = new NumberProperty<Integer>("VL-Alert", "VL to Alert", 10, 1, 100, 1, this.alert::getValue);
        this.VL_FAP = new HashMap<UUID, Integer>();
        this.updateEventListener = (event -> {
            if (event.isPre()) {
                this.mc.theWorld.playerEntities.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final EntityPlayer entityPlayer = iterator.next();
                    if (!entityPlayer.isInvisibleToPlayer(this.mc.thePlayer) && PlayerUtils.checkPing(entityPlayer) && entityPlayer.ticksExisted >= 100) {
                        final double DIST = Math.sqrt((entityPlayer.posX - entityPlayer.lastTickPosX) * (entityPlayer.posX - entityPlayer.lastTickPosX) + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * (entityPlayer.posZ - entityPlayer.lastTickPosZ));
                        if (!(entityPlayer instanceof EntityPlayerSP)) {
                            if (DIST != 0.0 && entityPlayer.hurtTime == 0 && !entityPlayer.isPotionActive(Potion.moveSpeed)) {
                                ChatUtils.addChatMessage(entityPlayer.getName() + " " + DIST);
                                this.addVL(entityPlayer, "Speed (A)");
                            }
                            if (entityPlayer.isSwingInProgress && PlayerUtils.isLookingAtEntity(this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, (EntityPlayer)entityPlayer.getLastAttacker(), entityPlayer, 6.0, true, true)) {
                                this.addVL(entityPlayer, "Kill Aura (A)");
                            }
                            if (entityPlayer.hurtTime != 0 && DIST == 0.0) {
                                this.addVL(entityPlayer, "Velocity (A)");
                            }
                            else {
                                continue;
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
    
    private void addVL(final EntityPlayer player, final String reason) {
        final UUID uuid = player.getUniqueID();
        int vl = 0;
        if (this.VL_FAP.containsKey(uuid)) {
            vl = this.VL_FAP.get(uuid) + 1;
        }
        this.VL_FAP.put(uuid, vl);
        if (this.alert.getValue() && vl % this.vlAlertProperty.getValue() == 0) {
            ChatUtils.addChatMessage(player.getName() + " flagged for " + reason + " (" + vl * this.vlAlertProperty.getValue() + " total VL)");
        }
        if (this.VL_FAP.get(uuid) > this.vlAlertProperty.getValue() * 7) {
            ChatUtils.addChatMessage(player.getName() + " §c is a hacker! Added to enemies.");
            SmokeXClient.getInstance().getPlayerManager().addEnemy(player.getName());
        }
    }
}
