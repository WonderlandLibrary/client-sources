// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.gui.element.elements;

import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import net.minecraft.entity.player.EntityPlayer;
import gg.childtrafficking.smokex.gui.element.Element;

public final class FaceElement extends Element
{
    private EntityPlayer player;
    
    public FaceElement(final String identifier, final float x, final float y) {
        super(identifier, x, y);
        this.player = this.mc.thePlayer;
    }
    
    public FaceElement(final String identifier, final float x, final float y, final EntityPlayer player) {
        super(identifier, x, y);
        this.player = player;
    }
    
    public Element setPlayer(final EntityPlayer player) {
        this.player = player;
        return this;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    @Override
    public void render(final double partialTicks) {
        if (this.player != null) {
            RenderingUtils.drawFace(this.player, (int)this.getRenderX(), (int)this.getRenderY());
            super.render(partialTicks);
        }
    }
}
