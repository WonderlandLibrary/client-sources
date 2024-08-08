package net.futureclient.client.modules.combat.autolog;

import net.futureclient.client.events.Event;
import java.util.Iterator;
import net.futureclient.client.of;
import net.futureclient.client.modules.combat.AutoTotem;
import net.futureclient.client.fb;
import net.futureclient.client.modules.miscellaneous.AutoReconnect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.client.entity.EntityPlayerSP;
import net.futureclient.client.ZG;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.futureclient.client.pg;
import net.futureclient.client.te;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.combat.AutoLog;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final AutoLog k;
    
    public Listener1(final AutoLog k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        this.k.e(String.format("AutoLog §7[§F%s§7]", AutoLog.M(this.k).B()));
        if (AutoLog.getMinecraft2().player.getHealth() <= 0.0f) {
            return;
        }
        if (AutoLog.M(this.k).M()) {
            final te te = (te)pg.M().M().M((Class)te.class);
            final Iterator<Entity> iterator = AutoLog.getMinecraft10().world.playerEntities.iterator();
            while (iterator.hasNext()) {
                final EntityPlayer entityPlayer;
                if (ZG.b((Entity)(entityPlayer = (EntityPlayer)iterator.next())) && !AutoLog.M(this.k).contains(entityPlayer) && (!te.q.M() || !pg.M().M().M(entityPlayer.getName())) && !entityPlayer.getName().equals(AutoLog.getMinecraft().player.getName()) && !(entityPlayer instanceof EntityPlayerSP)) {
                    if (AutoLog.getMinecraft5().getConnection() == null) {
                        AutoLog.getMinecraft4().world.sendQuittingDisconnectingPacket();
                    }
                    else {
                        AutoLog.getMinecraft1().getConnection().getNetworkManager().closeChannel((ITextComponent)new TextComponentString(new StringBuilder().insert(0, "[AutoLog] ").append(entityPlayer.getName()).append(" came into render distance.").toString()));
                    }
                    ((AutoReconnect)pg.M().M().M((Class)fb.class)).M(false);
                    this.k.M(false);
                }
            }
        }
        final AutoTotem autoTotem;
        if ((autoTotem = (AutoTotem)pg.M().M().M((Class)of.class)) != null && autoTotem.M() && autoTotem.e() > 0) {
            return;
        }
        if (AutoLog.getMinecraft7().player.getHealth() <= AutoLog.M(this.k).B().floatValue()) {
            if (AutoLog.getMinecraft6().getConnection() == null) {
                AutoLog.getMinecraft8().world.sendQuittingDisconnectingPacket();
            }
            else {
                AutoLog.getMinecraft3().getConnection().getNetworkManager().closeChannel((ITextComponent)new TextComponentString(new StringBuilder().insert(0, "[AutoLog] Logged out with ").append(AutoLog.getMinecraft9().player.getHealth()).append(" health remaining.").toString()));
            }
            ((AutoReconnect)pg.M().M().M((Class)fb.class)).M(false);
            this.k.M(false);
        }
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
