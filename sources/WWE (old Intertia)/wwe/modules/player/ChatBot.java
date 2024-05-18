package wwe.modules.player;

import wwe.modules.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import wwe.utils.*;
import net.minecraft.client.network.*;
import java.util.*;
import wwe.*;

public class ChatBot extends qMIe
{
    sbfc Xijd;
    public static ArrayList<UUBS.wwe/UUBS> aVmP;
    public ModeValue mode;
    public NumberValue delay;
    
    public ChatBot() {
        super("Chat Bot", "Its a chat bot, people command it, -help", 0, Category.PLAYER);
        this.Xijd = new sbfc();
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("<name>");
        list.add("name:");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "<name>", list);
        this.delay = new NumberValue("Delay", this, "Delay before you reply, antikick", 1.5, 0.0, 5.0);
    }
    
    @oqrr
    public void lSil(final omqH omqH) {
        if (this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
            this.Xijd.BBwj();
        }
    }
    
    @oqrr
    public void DwMG(final LqJY lqJY) {
        if (lqJY.MYlK() instanceof SPacketChat) {
            final String stripControlCodes = StringUtils.stripControlCodes(((SPacketChat)lqJY.MYlK()).getChatComponent().getUnformattedText());
            String replaceFirst;
            if (this.mode.OnGi().equalsIgnoreCase("<name>")) {
                replaceFirst = stripControlCodes.trim().substring(0, stripControlCodes.indexOf(">")).replaceFirst("<", "");
                if (ChatBot.\u200c\u2000\u2006) {
                    throw null;
                }
            }
            else {
                replaceFirst = stripControlCodes.trim().split(":")[0];
            }
            final String s = replaceFirst;
            String s2;
            if (this.mode.OnGi().equalsIgnoreCase("<name>")) {
                s2 = stripControlCodes.trim().substring(stripControlCodes.indexOf(">"), stripControlCodes.length()).replaceFirst("> ", "");
                if (ChatBot.\u200c\u2000\u2006) {
                    throw null;
                }
            }
            else {
                s2 = stripControlCodes.trim().split(":")[1].replaceFirst(" ", "");
            }
            final String s3 = s2;
            if (s3.startsWith("-")) {
                this.Xijd.gCgh();
            }
            if (!this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                return;
            }
            for (final UUBS.wwe/UUBS wwe/UUBS : ChatBot.aVmP) {
                if (s3.startsWith("-" + wwe/UUBS.vMpJ)) {
                    Wrapper.mc.player.sendChatMessage(wwe/UUBS.SfQz[new Random().nextInt(wwe/UUBS.SfQz.length)].replaceAll("!object1", s));
                    return;
                }
                if (ChatBot.\u200c\u2000\u2006) {
                    throw null;
                }
            }
            if (s3.equalsIgnoreCase("-help")) {
                Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.iNhF.xlWb[new Random().nextInt(UUBS.wwe/UUBS.iNhF.xlWb.length)].replaceAll("!object1", s));
                return;
            }
            if (!s.equals(Wrapper.mc.getSession().getUsername()) && (s3.contains("hi " + Wrapper.mc.getSession().getUsername()) || s3.contains("hello " + Wrapper.mc.getSession().getUsername()) || s3.contains("Hi " + Wrapper.mc.getSession().getUsername()) || s3.contains("Hello " + Wrapper.mc.getSession().getUsername()))) {
                Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.laFO.xlWb[new Random().nextInt(UUBS.wwe/UUBS.laFO.xlWb.length)].replaceAll("!object1", s));
                return;
            }
            if (!s.equals(Wrapper.mc.getSession().getUsername()) && (s3.contains("bye " + Wrapper.mc.getSession().getUsername()) || s3.contains("bye bye " + Wrapper.mc.getSession().getUsername()) || s3.contains("Bye " + Wrapper.mc.getSession().getUsername()) || s3.contains("Bye bye " + Wrapper.mc.getSession().getUsername()))) {
                Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.Dnge.xlWb[new Random().nextInt(UUBS.wwe/UUBS.Dnge.xlWb.length)].replaceAll("!object1", s));
                return;
            }
            if (s3.equalsIgnoreCase("-tps")) {
                Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.UwxW.xlWb[new Random().nextInt(UUBS.wwe/UUBS.UwxW.xlWb.length)].replaceAll("!object1", PJxY.Npqe + ""));
                return;
            }
            if (s3.equalsIgnoreCase("-ping")) {
                Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.sSpe.xlWb[new Random().nextInt(UUBS.wwe/UUBS.sSpe.xlWb.length)]);
                return;
            }
            if (s3.equalsIgnoreCase("-myping")) {
                for (final NetworkPlayerInfo networkPlayerInfo : Wrapper.mc.player.connection.getPlayerInfoMap()) {
                    if (networkPlayerInfo.getGameProfile().getName().equals(s)) {
                        Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.yGLD.xlWb[new Random().nextInt(UUBS.wwe/UUBS.yGLD.xlWb.length)].replaceAll("!object1", networkPlayerInfo.getResponseTime() + ""));
                        if (ChatBot.\u200c\u2000\u2006) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        if (ChatBot.\u200c\u2000\u2006) {
                            throw null;
                        }
                        continue;
                    }
                }
                return;
            }
            if (s3.contains("-ping ")) {
                boolean b = false;
                String name = "";
                long n = 0xFEFF15A2E94E9C72L ^ 0x100EA5D16B1638DL;
                for (final NetworkPlayerInfo networkPlayerInfo2 : Wrapper.mc.player.connection.getPlayerInfoMap()) {
                    if (s3.contains(networkPlayerInfo2.getGameProfile().getName())) {
                        b = true;
                        name = networkPlayerInfo2.getGameProfile().getName();
                        n = networkPlayerInfo2.getResponseTime();
                        if (ChatBot.\u200c\u2000\u2006) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        if (ChatBot.\u200c\u2000\u2006) {
                            throw null;
                        }
                        continue;
                    }
                }
                if (b) {
                    Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.OHcM.xlWb[new Random().nextInt(UUBS.wwe/UUBS.OHcM.xlWb.length)].replaceAll("!object1", name).replaceAll("!object2", n + ""));
                    return;
                }
                if (ChatBot.\u200c\u2000\u2006) {
                    throw null;
                }
            }
            else {
                if (s3.equalsIgnoreCase("-worstping")) {
                    long n2 = 0xFFC6CF531A4CAE49L ^ 0x3930ACE5B351B6L;
                    String name2 = "";
                    for (final NetworkPlayerInfo networkPlayerInfo3 : Wrapper.mc.player.connection.getPlayerInfoMap()) {
                        if (n2 < networkPlayerInfo3.getResponseTime()) {
                            n2 = networkPlayerInfo3.getResponseTime();
                            name2 = networkPlayerInfo3.getGameProfile().getName();
                        }
                        if (ChatBot.\u200c\u2000\u2006) {
                            throw null;
                        }
                    }
                    Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.xwDq.xlWb[new Random().nextInt(UUBS.wwe/UUBS.xwDq.xlWb.length)].replaceAll("!object1", name2).replaceAll("!object2", n2 + ""));
                    return;
                }
                if (s3.equalsIgnoreCase("-bestping")) {
                    long n3 = 0x1149B25282957A24L ^ 0x6EB64DAD7D6A85DBL;
                    String name3 = "";
                    for (final NetworkPlayerInfo networkPlayerInfo4 : Wrapper.mc.player.connection.getPlayerInfoMap()) {
                        if (n3 > networkPlayerInfo4.getResponseTime()) {
                            n3 = networkPlayerInfo4.getResponseTime();
                            name3 = networkPlayerInfo4.getGameProfile().getName();
                        }
                        if (ChatBot.\u200c\u2000\u2006) {
                            throw null;
                        }
                    }
                    Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.LtQS.xlWb[new Random().nextInt(UUBS.wwe/UUBS.LtQS.xlWb.length)].replaceAll("!object1", name3).replaceAll("!object2", n3 + ""));
                    return;
                }
                if (s3.equalsIgnoreCase("-randomping")) {
                    final NetworkPlayerInfo networkPlayerInfo5 = (NetworkPlayerInfo)Wrapper.mc.player.connection.getPlayerInfoMap().toArray()[new Random().nextInt(Wrapper.mc.player.connection.getPlayerInfoMap().size())];
                    Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.DFPB.xlWb[new Random().nextInt(UUBS.wwe/UUBS.DFPB.xlWb.length)].replaceAll("!object1", networkPlayerInfo5.getGameProfile().getName()).replaceAll("!object2", networkPlayerInfo5.getResponseTime() + ""));
                    return;
                }
                if (s3.equalsIgnoreCase(Wrapper.mc.getSession().getUsername()) && !s.equalsIgnoreCase(Wrapper.mc.getSession().getUsername())) {
                    Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.ADwi.xlWb[new Random().nextInt(UUBS.wwe/UUBS.ADwi.xlWb.length)].replaceAll("!object1", s));
                    return;
                }
                if (s3.startsWith("-")) {
                    Wrapper.mc.player.sendChatMessage(UUBS.wwe/UUBS.MHTz.xlWb[new Random().nextInt(UUBS.wwe/UUBS.MHTz.xlWb.length)].replaceAll("!object1", s));
                }
            }
        }
    }
    
    @Override
    public void LPxx() {
        Wrapper.ypJK().QFCM();
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
    
    static {
        ChatBot.aVmP = new ArrayList<UUBS.wwe/UUBS>();
    }
}
