package wwe.modules.player;

import wwe.*;
import wwe.modules.*;
import net.minecraft.client.network.*;
import wwe.utils.*;
import java.util.*;

public class Insulter extends qMIe
{
    sbfc Xijd;
    public NumberValue delay;
    public static ArrayList<String> FIes;
    
    public Insulter() {
        super("Insulter", "Insults People", 0, Category.PLAYER);
        this.Xijd = new sbfc();
    }
    
    @Override
    public void HrDr() {
        this.delay = new NumberValue("Delay", this, "Delay before next message", 10.0, 0.0, 30.0);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        this.AhaP((NetworkPlayerInfo)Wrapper.mc.getConnection().getPlayerInfoMap().toArray()[new Random().nextInt(Wrapper.mc.getConnection().getPlayerInfoMap().toArray().length)], (NetworkPlayerInfo)Wrapper.mc.getConnection().getPlayerInfoMap().toArray()[new Random().nextInt(Wrapper.mc.getConnection().getPlayerInfoMap().toArray().length)]);
    }
    
    public void AhaP(final NetworkPlayerInfo networkPlayerInfo, final NetworkPlayerInfo networkPlayerInfo2) {
        this.Xijd.gCgh();
        if (this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
            final String replace = Insulter.FIes.get(new Random().nextInt(Insulter.FIes.size())).replace("!object1", networkPlayerInfo.getGameProfile().getName()).replace("!object2", networkPlayerInfo2.getGameProfile().getName());
            if (replace.contains(Wrapper.mc.player.getName())) {
                return;
            }
            Wrapper.mc.player.sendChatMessage(replace);
            this.Xijd.BBwj();
        }
    }
    
    @Override
    public void LPxx() {
        try {
            Insulter.FIes = Wrapper.ypJK().MFbs();
            if (Insulter.\u200a\u2005\u2001\u200a\u2000\u200a\u2001) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    static {
        Insulter.FIes = new ArrayList<String>();
    }
}
