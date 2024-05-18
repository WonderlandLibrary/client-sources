package wwe.modules.player;

import wwe.*;
import wwe.modules.*;
import java.util.*;
import wwe.utils.*;
import net.minecraft.client.entity.*;
import java.io.*;

public class Spammer extends qMIe
{
    public static ArrayList<String> AGlV;
    sbfc Xijd;
    public NumberValue delay;
    public BooleanValue random;
    
    public Spammer() {
        super("Spammer", "Spams words in chat, That you set", 0, Category.PLAYER);
        this.Xijd = new sbfc();
    }
    
    @Override
    public void HrDr() {
        this.delay = new NumberValue("Delay", this, "Delay till next message is sent", 7.0, 0.0, 30.0);
        this.random = new BooleanValue("Random", this, "Adds random numers to the chat message", false);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        this.Xijd.gCgh();
        if (this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
            final Random random = new Random();
            final EntityPlayerSP player = Wrapper.mc.player;
            final StringBuilder append = new StringBuilder().append(Spammer.AGlV.get(random.nextInt(Spammer.AGlV.size())));
            String string;
            if (this.random.OnGi()) {
                string = " " + new Random().nextInt();
                if (Spammer.\u200f\u2007\u2005\u200c\u2004\u2003\u200a) {
                    throw null;
                }
            }
            else {
                string = "";
            }
            player.sendChatMessage(append.append(string).toString());
            this.Xijd.BBwj();
        }
    }
    
    @Override
    public void LPxx() {
        try {
            Spammer.AGlV = Wrapper.ypJK().fmny();
            if (Spammer.\u200f\u2007\u2005\u200c\u2004\u2003\u200a) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    static {
        Spammer.AGlV = new ArrayList<String>();
    }
}
