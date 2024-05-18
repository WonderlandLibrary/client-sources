package wwe.modules.render;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import java.awt.*;
import java.util.*;
import wwe.*;

public class ItemESP extends qMIe
{
    public BooleanValue rainbow;
    
    public ItemESP() {
        super("Item ESP", "Makes items glow", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        this.rainbow = new BooleanValue("Rainbow", this, "Make the color a rain bow", true);
    }
    
    @oqrr
    public void CRUW(final RTIo rtIo) {
        for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem || entity instanceof EntityArrow) {
                if (this.rainbow.OnGi()) {
                    BIIV.ITDu(entity, JIfG.JzFV(0x9E6822DD07024A02L ^ 0x9E6822DD07024A03L, 1.0f));
                    if (ItemESP.\u2003\u2000\u2008\u2003\u2008) {
                        throw null;
                    }
                }
                else {
                    BIIV.ITDu(entity, Color.red);
                }
            }
            if (ItemESP.\u2003\u2000\u2008\u2003\u2008) {
                throw null;
            }
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
}
