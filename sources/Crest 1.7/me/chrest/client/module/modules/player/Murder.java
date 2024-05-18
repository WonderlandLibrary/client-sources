// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.player;

import me.chrest.event.EventTarget;
import java.util.Iterator;
import me.chrest.utils.ClientUtils;
import net.minecraft.init.Items;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import me.chrest.event.events.UpdateEvent;
import net.minecraft.client.Minecraft;
import me.chrest.client.module.Module;

@Mod(displayName = "Murder")
public class Murder extends Module
{
    Minecraft mc;
    
    public Murder() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @EventTarget
    public void onPre(final UpdateEvent event) {
        for (final Object o : this.mc.theWorld.loadedEntityList) {
            if (!(o instanceof EntityPlayerSP) && o instanceof EntityPlayer) {
                final EntityLivingBase ent = (EntityLivingBase)o;
                for (int i = 0; i < 1; ++i) {
                    if (ent != null && ent.getName() != this.mc.thePlayer.getName() && ent.getHeldItem().getItem() == Items.iron_sword) {
                        ClientUtils.sendMessage("§c" + ent.getName().toUpperCase() + " IS THE MURDURER");
                    }
                }
            }
        }
    }
}
