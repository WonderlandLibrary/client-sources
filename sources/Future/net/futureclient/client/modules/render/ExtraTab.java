package net.futureclient.client.modules.render;

import net.futureclient.client.modules.render.extratab.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.AE;
import net.futureclient.client.Ea;

public final class ExtraTab extends Ea
{
    public ExtraTab() {
        super("ExtraTab", new String[] { AE.M("#=\u00127\u0007\u0011\u0007'"), "ExtraTabList" }, true, -6196819, Category.RENDER);
        this.M(new n[] { new Listener1(this) });
    }
}
