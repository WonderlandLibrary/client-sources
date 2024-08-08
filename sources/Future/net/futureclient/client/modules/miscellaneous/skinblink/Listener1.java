package net.futureclient.client.modules.miscellaneous.skinblink;

import net.futureclient.client.events.Event;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.futureclient.client.modules.miscellaneous.SkinBlink;
import net.futureclient.client.xe;
import net.futureclient.client.n;

public class Listener1 extends n<xe>
{
    public final SkinBlink k;
    
    public Listener1(final SkinBlink k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final xe xe) {
        if (SkinBlink.M(this.k).e(SkinBlink.M(this.k).B().floatValue() * 1000.0f)) {
            final EnumPlayerModelParts[] values;
            final int length = (values = EnumPlayerModelParts.values()).length;
            int i = 0;
            int n = 0;
            while (i < length) {
                final EnumPlayerModelParts enumPlayerModelParts = values[n];
                SkinBlink.getMinecraft1().gameSettings.setModelPartEnabled(enumPlayerModelParts, ((boolean)SkinBlink.M(this.k).M()) ? (Math.random() < 0.0) : (!SkinBlink.getMinecraft().gameSettings.getModelParts().contains(enumPlayerModelParts)));
                i = ++n;
            }
            SkinBlink.M(this.k).e();
        }
    }
    
    public void M(final Event event) {
        this.M((xe)event);
    }
}
