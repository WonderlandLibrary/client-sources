package wwe.modules.render;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import java.awt.*;
import net.minecraft.entity.passive.*;
import java.util.*;
import wwe.*;

public class MobESP extends qMIe
{
    public MobESP() {
        super("Mob ESP", "Makes mobs glow", 0, Category.RENDER);
    }
    
    @oqrr
    public void CRUW(final RTIo rtIo) {
        for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
            if (entity instanceof EntityMob) {
                BIIV.ITDu(entity, Color.yellow);
            }
            if (entity instanceof EntityAnimal) {
                BIIV.ITDu(entity, Color.green);
            }
            if (MobESP.\u2002) {
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
