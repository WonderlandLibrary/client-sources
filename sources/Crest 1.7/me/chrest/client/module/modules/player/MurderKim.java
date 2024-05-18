// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.player;

import me.chrest.event.EventTarget;
import java.util.Iterator;
import net.minecraft.init.Items;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import me.chrest.event.events.UpdateEvent;
import net.minecraft.client.Minecraft;
import me.chrest.client.module.Module;

@Module.Mod(displayName = "MurderKim")
public class MurderKim
{
    Minecraft mc;
    
    public MurderKim() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @EventTarget
    public void onPre(final UpdateEvent event) {
        for (final Object o : this.mc.theWorld.loadedEntityList) {
            if (!(o instanceof EntityPlayerSP) && o instanceof EntityPlayer) {
                final EntityLivingBase ent = (EntityLivingBase)o;
                for (int i = 0; i < 1; ++i) {
                    if (ent != null && ent.getName() != this.mc.thePlayer.getName() && ent.getHeldItem().getItem() == Items.iron_sword) {
                        this.msg("§c" + ent.getName().toUpperCase() + " IS THE MURDURER");
                    }
                }
            }
        }
    }
    
    private void msg(final String string) {
    }
}
