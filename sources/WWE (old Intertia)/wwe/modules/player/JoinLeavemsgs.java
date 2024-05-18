package wwe.modules.player;

import wwe.*;
import net.minecraft.client.network.*;
import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.client.*;
import java.io.*;
import java.util.*;

public class JoinLeavemsgs extends qMIe
{
    static ArrayList<NetworkPlayerInfo> yFHA;
    static int XcYd;
    boolean KSGk;
    public static ArrayList<String> vMez;
    public static ArrayList<String> rmoi;
    public static ArrayList<String> BOOX;
    public static ArrayList<String> EmRl;
    
    public JoinLeavemsgs() {
        super("Join/Leave msgs", "Sends a message when a player joins/leaves", 0, Category.PLAYER);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (Wrapper.mc.player != null) {
            if (Wrapper.mc.player.ticksExisted % 10 == 0) {
                this.leLj();
                if (JoinLeavemsgs.\u2009\u2002\u2008\u2007\u2003) {
                    throw null;
                }
            }
            else if (Wrapper.mc.isSingleplayer()) {
                this.GDVp();
            }
        }
    }
    
    @Override
    public void LPxx() {
        if (Wrapper.mc.world != null && Wrapper.mc.player != null && Wrapper.mc.getConnection() != null) {
            this.guhH();
        }
        super.LPxx();
    }
    
    private void leLj() {
        final ArrayList<Object> list = new ArrayList<Object>(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
        if (list.size() != JoinLeavemsgs.XcYd) {
            final ArrayList list2 = (ArrayList)list.clone();
            list2.removeAll(JoinLeavemsgs.yFHA);
            if (list2.size() > 5) {
                JoinLeavemsgs.XcYd = JoinLeavemsgs.yFHA.size();
                this.guhH();
                return;
            }
            final ArrayList list3 = (ArrayList)JoinLeavemsgs.yFHA.clone();
            list3.removeAll(list);
            final Iterator<NetworkPlayerInfo> iterator = list2.iterator();
            while (iterator.hasNext()) {
                this.PWeW(iterator.next());
                if (JoinLeavemsgs.\u2009\u2002\u2008\u2007\u2003) {
                    throw null;
                }
            }
            final Iterator<NetworkPlayerInfo> iterator2 = list3.iterator();
            while (iterator2.hasNext()) {
                this.tjiB(iterator2.next());
                if (JoinLeavemsgs.\u2009\u2002\u2008\u2007\u2003) {
                    throw null;
                }
            }
            JoinLeavemsgs.XcYd = JoinLeavemsgs.yFHA.size();
            this.guhH();
        }
    }
    
    private void guhH() {
        JoinLeavemsgs.yFHA = new ArrayList<NetworkPlayerInfo>(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
        JoinLeavemsgs.XcYd = JoinLeavemsgs.yFHA.size();
        this.KSGk = true;
    }
    
    protected void PWeW(final NetworkPlayerInfo networkPlayerInfo) {
        try {
            JoinLeavemsgs.BOOX = Wrapper.ypJK().jRoj(networkPlayerInfo);
            if (JoinLeavemsgs.\u2009\u2002\u2008\u2007\u2003) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        Wrapper.mc.player.sendChatMessage((String)JoinLeavemsgs.BOOX.get(new Random().nextInt(JoinLeavemsgs.BOOX.size())));
    }
    
    protected void tjiB(final NetworkPlayerInfo networkPlayerInfo) {
        try {
            JoinLeavemsgs.EmRl = Wrapper.ypJK().Nprd(networkPlayerInfo);
            if (JoinLeavemsgs.\u2009\u2002\u2008\u2007\u2003) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        Wrapper.mc.player.sendChatMessage((String)JoinLeavemsgs.EmRl.get(new Random().nextInt(JoinLeavemsgs.EmRl.size())));
    }
    
    static {
        JoinLeavemsgs.BOOX = new ArrayList<String>();
        JoinLeavemsgs.EmRl = new ArrayList<String>();
        JoinLeavemsgs.yFHA = new ArrayList<NetworkPlayerInfo>();
        JoinLeavemsgs.vMez = new ArrayList<String>();
        JoinLeavemsgs.rmoi = new ArrayList<String>();
    }
}
