package net.futureclient.client.modules.combat.trigger;

import net.futureclient.client.IE;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.client.ZG;
import net.futureclient.client.pg;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Mouse;
import net.futureclient.client.HD;
import net.futureclient.client.Ke;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.combat.Trigger;
import net.futureclient.client.xe;
import net.futureclient.client.n;

public class Listener1 extends n<xe>
{
    public final Trigger k;
    
    public Listener1(final Trigger k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((xe)event);
    }
    
    @Override
    public void M(final xe xe) {
        if (Trigger.getMinecraft7().currentScreen != null || Trigger.getMinecraft3().playerController == null) {
            return;
        }
        int n = 0;
        int buttonDown = 0;
        Label_0237: {
            switch (Ke.k[((HD.lg)Trigger.M(this.k).M()).ordinal()]) {
                case 1:
                    buttonDown = (Mouse.isButtonDown(0) ? 1 : 0);
                    break Label_0237;
                case 2:
                    buttonDown = ((Trigger.getMinecraft1().objectMouseOver != null && Trigger.getMinecraft5().objectMouseOver.entityHit instanceof EntityLivingBase && (!Trigger.B(this.k).M() || (!Trigger.getMinecraft9().player.isOnSameTeam(Trigger.getMinecraft6().objectMouseOver.entityHit) && (!Trigger.M(this.k).M() || !Trigger.getMinecraft().objectMouseOver.entityHit.isInvisible()) && (!Trigger.e(this.k).M() || !pg.M().M().M(Trigger.getMinecraft2().objectMouseOver.entityHit.getName()))))) ? 1 : 0);
                    break Label_0237;
                case 3:
                    n = 1;
                    break;
            }
            buttonDown = n;
        }
        if (buttonDown != 0 && (!Trigger.b(this.k).M() || ZG.e()) && Trigger.M(this.k).M(Trigger.M(this.k))) {
            ((IMinecraft)Trigger.getMinecraft8()).setLeftClickCounter(0);
            ((IMinecraft)Trigger.getMinecraft4()).clickMouse(IE.RD.d);
            Trigger.M(this.k, Math.max(Trigger.M(this.k).B().doubleValue() + (Math.random() * Trigger.e(this.k).B().doubleValue() * 0.0 - Trigger.e(this.k).B().doubleValue()), 0.0));
            Trigger.M(this.k).e();
        }
    }
}
