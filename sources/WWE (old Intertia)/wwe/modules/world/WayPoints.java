package wwe.modules.world;

import net.minecraft.item.*;
import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.gui.*;
import wwe.*;

public class WayPoints extends qMIe
{
    public static ArrayList<Xssq> COfQ;
    public EnumDyeColor mPFV;
    
    public WayPoints() {
        super("Way Points", "Shows esp/tracer where you set one", 0, Category.WORLD);
    }
    
    @oqrr
    public void CRUW(final RTIo rtIo) {
        for (final Xssq xssq : WayPoints.COfQ) {
            if (xssq.ADFS) {
                Wrapper.mc.renderEngine.bindTexture(new ResourceLocation("textures/entity/beacon_beam.png"));
                BIIV.tDuv(xssq.Nehz.getX() - yYGD.onRX(), xssq.Nehz.getY() - yYGD.UuJK(), xssq.Nehz.getZ() - yYGD.LkVR(), yYGD.XgOL().renderPartialTicks, 1.0, (double)Wrapper.mc.world.getWorldTime(), 0, 255, xssq.rnCG.getColorComponentValues());
            }
            if (xssq.kKMJ) {
                BIIV.XrBQ(xssq.Nehz, xssq.rnCG.getColorComponentValues()[0], xssq.rnCG.getColorComponentValues()[1], xssq.rnCG.getColorComponentValues()[2]);
            }
            if (xssq.MUAZ) {
                BIIV.nieu(xssq.Nehz, xssq.rnCG.getColorComponentValues()[0], xssq.rnCG.getColorComponentValues()[1], xssq.rnCG.getColorComponentValues()[2]);
            }
            if (WayPoints.\u200f\u200b\u2003\u200b\u2008\u200d\u200e\u2007) {
                throw null;
            }
        }
    }
    
    @oqrr
    public void Ewag(final CTFs.wwe/BnvE wwe/BnvE) {
        for (final Xssq xssq : WayPoints.COfQ) {
            if (xssq.Nehz.getX() == wwe/BnvE.phME.getX() && xssq.Nehz.getZ() == wwe/BnvE.phME.getZ()) {
                Wrapper.mc.displayGuiScreen((GuiScreen)new bSlU(xssq));
            }
            if (WayPoints.\u200f\u200b\u2003\u200b\u2008\u200d\u200e\u2007) {
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
    
    public static boolean VlSt(final Xssq xssq) {
        if (!WayPoints.COfQ.contains(xssq)) {
            WayPoints.COfQ.add(xssq);
            return true;
        }
        return false;
    }
    
    public static boolean ccNC(final Xssq xssq) {
        if (WayPoints.COfQ.contains(xssq)) {
            WayPoints.COfQ.remove(xssq);
            return true;
        }
        return false;
    }
    
    static {
        WayPoints.COfQ = new ArrayList<Xssq>();
    }
}
