package wwe.modules.player;

import java.text.*;
import wwe.modules.*;
import wwe.utils.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.*;
import wwe.*;

public class Announcer extends qMIe
{
    public ArrayList<xbUc.wwe/xbUc> objE;
    public xbUc.wwe/xbUc nElk;
    public xbUc.wwe/xbUc vHFG;
    public xbUc.wwe/xbUc MnIL;
    public xbUc.wwe/xbUc uYwf;
    public xbUc.wwe/xbUc trvG;
    public xbUc.wwe/xbUc mjnA;
    public xbUc.wwe/xbUc oxFS;
    public xbUc.wwe/xbUc sbUj;
    public xbUc.wwe/xbUc qKVr;
    public xbUc.wwe/xbUc IORz;
    public xbUc.wwe/xbUc IXSc;
    public xbUc.wwe/xbUc rFwE;
    public xbUc.wwe/xbUc BYEn;
    public xbUc.wwe/xbUc Bqom;
    public xbUc.wwe/xbUc yEUX;
    public xbUc.wwe/xbUc DHdQ;
    public xbUc.wwe/xbUc lIBj;
    public xbUc.wwe/xbUc ozzf;
    public xbUc.wwe/xbUc AwzZ;
    public xbUc.wwe/xbUc nrXO;
    public xbUc.wwe/xbUc sEjj;
    public long fICx;
    public DecimalFormat PxbF;
    public double Dbqg;
    public NumberValue delay;
    
    public Announcer() {
        super("Announcer", "Announces stuff you do in chat", 0, Category.PLAYER);
        this.objE = new ArrayList<xbUc.wwe/xbUc>();
        this.nElk = new xbUc.wwe/xbUc("BlocksWalkedTask", new String[] { "I recently walked !object1 blocks" }, this);
        this.vHFG = new xbUc.wwe/xbUc("ItemDropedTask", new String[] { "I just droped a !object1 called, !object2!" }, this);
        this.MnIL = new xbUc.wwe/xbUc("BlockPlacedTask", new String[] { "I just placed a block called, !object1!" }, this);
        this.uYwf = new xbUc.wwe/xbUc("BlockBreakingTask", new String[] { "Im currently breaking a block called, !object1!" }, this);
        this.trvG = new xbUc.wwe/xbUc("BlockBrokeTask", new String[] { "I just broke a block called, !object1!" }, this);
        this.mjnA = new xbUc.wwe/xbUc("ChatOpenTask", new String[] { "I just opened chat!" }, this);
        this.oxFS = new xbUc.wwe/xbUc("CommandTask", new String[] { "I just opened chat and typed a slash!" }, this);
        this.sbUj = new xbUc.wwe/xbUc("PauseGameTask", new String[] { "I just paused my game!" }, this);
        this.qKVr = new xbUc.wwe/xbUc("InventoryOpenTask", new String[] { "I just opened my inventory!" }, this);
        this.IORz = new xbUc.wwe/xbUc("PlayerListOpenTask", new String[] { "I just looked at the player list!" }, this);
        this.IXSc = new xbUc.wwe/xbUc("PerspectiveChangeTask", new String[] { "I just changed perspectives, now im in !object1!" }, this);
        this.rFwE = new xbUc.wwe/xbUc("SneakTask", new String[] { "I just crouched!" }, this);
        this.BYEn = new xbUc.wwe/xbUc("JumpTask", new String[] { "I just jumped!" }, this);
        this.Bqom = new xbUc.wwe/xbUc("AttackTask", new String[] { "I just attacked a entity called, !object1 with a !object2" }, this);
        this.yEUX = new xbUc.wwe/xbUc("EattingTask", new String[] { "Im currently eatting a peice of food called, !object1!" }, this);
        this.DHdQ = new xbUc.wwe/xbUc("ItemUsingTask", new String[] { "Im currently using a item called, !object1!" }, this);
        this.lIBj = new xbUc.wwe/xbUc("FullScreenTask", new String[] { "I just toggled full screen mode!" }, this);
        this.ozzf = new xbUc.wwe/xbUc("ScreenShotTask", new String[] { "I just took a screen shot!" }, this);
        this.AwzZ = new xbUc.wwe/xbUc("SwapHandsTask", new String[] { "I just swaped hands and now theres a !object1 in my main hand and a !object2 in my off hand!" }, this);
        this.nrXO = new xbUc.wwe/xbUc("PickBlockTask", new String[] { "I just used pick block on a block called, !object1!" }, this);
        this.sEjj = new xbUc.wwe/xbUc("NewDayTask", new String[] { "Ra just completed his blazing ark", "Its a new day yes it is" }, this);
        this.fICx = (0xF63F3C6A2C51680L ^ 0xF09C0C395D3AE97FL);
        this.PxbF = new DecimalFormat("#.#");
        this.objE.add((xbUc.wwe/xbUc)this.nElk);
        this.objE.add(this.vHFG);
        this.objE.add(this.MnIL);
        this.objE.add(this.uYwf);
        this.objE.add(this.trvG);
        this.objE.add(this.mjnA);
        this.objE.add(this.oxFS);
        this.objE.add(this.sbUj);
        this.objE.add(this.qKVr);
        this.objE.add(this.IORz);
        this.objE.add(this.IXSc);
        this.objE.add(this.rFwE);
        this.objE.add(this.BYEn);
        this.objE.add(this.Bqom);
        this.objE.add(this.yEUX);
        this.objE.add(this.DHdQ);
        this.objE.add(this.lIBj);
        this.objE.add(this.ozzf);
        this.objE.add(this.AwzZ);
        this.objE.add(this.nrXO);
        this.objE.add(this.sEjj);
    }
    
    @Override
    public void HrDr() {
        this.delay = new NumberValue("Delay", this, "Delay till next message is sent", 30.0, 0.0, 120.0);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        final Iterator<xbUc.wwe/xbUc> iterator = this.objE.iterator();
        while (iterator.hasNext()) {
            iterator.next().uffc.gCgh();
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        if (Wrapper.mc.player.getActiveHand() != null) {
            (this.yEUX.NhFM = new ArrayList()).add(new xbUc.wwe/xbUc("!object1", Wrapper.mc.player.getHeldItem(Wrapper.mc.player.getActiveHand()).getDisplayName()));
            final xbUc.wwe/xbUc yeux = this.yEUX;
            boolean b;
            if (Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown() && Wrapper.mc.player.isHandActive() && Wrapper.mc.player.getHeldItem(Wrapper.mc.player.getActiveHand()).getItem() instanceof ItemFood) {
                b = true;
                if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                    throw null;
                }
            }
            else {
                b = false;
            }
            yeux.leSX(b);
            this.yEUX.NhFM = null;
            (this.DHdQ.NhFM = new ArrayList()).add(new xbUc.wwe/xbUc("!object1", Wrapper.mc.player.getHeldItem(Wrapper.mc.player.getActiveHand()).getDisplayName()));
            final xbUc.wwe/xbUc dHdQ = this.DHdQ;
            boolean b2;
            if (Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown() && Wrapper.mc.player.isHandActive() && !(Wrapper.mc.player.getHeldItem(Wrapper.mc.player.getActiveHand()).getItem() instanceof ItemFood)) {
                b2 = true;
                if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                    throw null;
                }
            }
            else {
                b2 = false;
            }
            dHdQ.leSX(b2);
            this.DHdQ.NhFM = null;
        }
        if (this.fICx != (0x70220061FCAD9314L ^ 0x8FDDFF9E03526CEBL) && this.fICx != Wrapper.mc.world.getWorldTime() / (0xC43A147036D89BE4L ^ 0xC43A147036D8C624L)) {
            this.sEjj.leSX(true);
        }
        this.fICx = Wrapper.mc.world.getWorldTime() / (0xC515953D72B432D9L ^ 0xC515953D72B46F19L);
        final double n = Wrapper.mc.player.distanceWalkedModified / 0.6 - this.Dbqg;
        if (this.nElk.XSxB() && n > 0.3) {
            (this.nElk.NhFM = new ArrayList()).add(new xbUc.wwe/xbUc("!object1", this.PxbF.format(n)));
            this.nElk.leSX(true);
            this.nElk.NhFM = null;
        }
    }
    
    public void wZaB(final int n) {
        if (!this.IuiN()) {
            return;
        }
        this.vHFG.NhFM = new ArrayList();
        final ArrayList nhFM = this.vHFG.NhFM;
        final String s = "!object1";
        String s2;
        if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) {
            s2 = "block";
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            s2 = "item";
        }
        nhFM.add(new xbUc.wwe/xbUc(s, s2));
        this.vHFG.NhFM.add(new xbUc.wwe/xbUc("!object2", Wrapper.mc.player.getHeldItemMainhand().getDisplayName()));
        final xbUc.wwe/xbUc vhfg = this.vHFG;
        boolean b;
        if (n == Wrapper.mc.gameSettings.keyBindDrop.getKeyCode() && !(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemAir)) {
            b = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b = false;
        }
        vhfg.leSX(b);
        this.vHFG.NhFM = null;
        final xbUc.wwe/xbUc mjnA = this.mjnA;
        boolean b2;
        if (n == Wrapper.mc.gameSettings.keyBindChat.getKeyCode()) {
            b2 = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b2 = false;
        }
        mjnA.leSX(b2);
        final xbUc.wwe/xbUc oxFS = this.oxFS;
        boolean b3;
        if (n == Wrapper.mc.gameSettings.keyBindCommand.getKeyCode()) {
            b3 = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b3 = false;
        }
        oxFS.leSX(b3);
        final xbUc.wwe/xbUc liBj = this.lIBj;
        boolean b4;
        if (n == Wrapper.mc.gameSettings.keyBindFullscreen.getKeyCode()) {
            b4 = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b4 = false;
        }
        liBj.leSX(b4);
        final xbUc.wwe/xbUc sbUj = this.sbUj;
        boolean b5;
        if (n == 1) {
            b5 = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b5 = false;
        }
        sbUj.leSX(b5);
        final xbUc.wwe/xbUc qkVr = this.qKVr;
        boolean b6;
        if (n == Wrapper.mc.gameSettings.keyBindInventory.getKeyCode()) {
            b6 = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b6 = false;
        }
        qkVr.leSX(b6);
        final xbUc.wwe/xbUc ioRz = this.IORz;
        boolean b7;
        if (n == Wrapper.mc.gameSettings.keyBindPlayerList.getKeyCode()) {
            b7 = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b7 = false;
        }
        ioRz.leSX(b7);
        final xbUc.wwe/xbUc ozzf = this.ozzf;
        boolean b8;
        if (n == Wrapper.mc.gameSettings.keyBindScreenshot.getKeyCode()) {
            b8 = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b8 = false;
        }
        ozzf.leSX(b8);
        this.AwzZ.NhFM = new ArrayList();
        final ArrayList nhFM2 = this.AwzZ.NhFM;
        final String s3 = "!object1";
        String displayName;
        if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemAir) {
            displayName = "Nothing";
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            displayName = Wrapper.mc.player.getHeldItemMainhand().getDisplayName();
        }
        nhFM2.add(new xbUc.wwe/xbUc(s3, displayName));
        final ArrayList nhFM3 = this.AwzZ.NhFM;
        final String s4 = "!object2";
        String displayName2;
        if (Wrapper.mc.player.getHeldItemOffhand().getItem() instanceof ItemAir) {
            displayName2 = "Nothing";
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            displayName2 = Wrapper.mc.player.getHeldItemOffhand().getDisplayName();
        }
        nhFM3.add(new xbUc.wwe/xbUc(s4, displayName2));
        final xbUc.wwe/xbUc awzZ = this.AwzZ;
        boolean b9;
        if (n == Wrapper.mc.gameSettings.keyBindSwapHands.getKeyCode()) {
            b9 = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b9 = false;
        }
        awzZ.leSX(b9);
        this.AwzZ.NhFM = null;
        final xbUc.wwe/xbUc rFwE = this.rFwE;
        boolean b10;
        if (n == Wrapper.mc.gameSettings.keyBindSneak.getKeyCode()) {
            b10 = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b10 = false;
        }
        rFwE.leSX(b10);
        String s5;
        if (Wrapper.mc.gameSettings.thirdPersonView == 0) {
            s5 = "third person";
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else if (Wrapper.mc.gameSettings.thirdPersonView == 1) {
            s5 = "second person";
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else if (Wrapper.mc.gameSettings.thirdPersonView == 2) {
            s5 = "first person";
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            s5 = "idk";
        }
        (this.IXSc.NhFM = new ArrayList()).add(new xbUc.wwe/xbUc("!object1", s5));
        final xbUc.wwe/xbUc ixSc = this.IXSc;
        boolean b11;
        if (n == Wrapper.mc.gameSettings.keyBindTogglePerspective.getKeyCode()) {
            b11 = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b11 = false;
        }
        ixSc.leSX(b11);
        final xbUc.wwe/xbUc byEn = this.BYEn;
        boolean b12;
        if (n == Wrapper.mc.gameSettings.keyBindJump.getKeyCode()) {
            b12 = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b12 = false;
        }
        byEn.leSX(b12);
    }
    
    @oqrr
    public void hebm(final MUMg muMg) {
        (this.Bqom.NhFM = new ArrayList()).add(new xbUc.wwe/xbUc("!object1", muMg.SiIq().getDisplayName().getUnformattedText()));
        final ArrayList nhFM = this.Bqom.NhFM;
        final String s = "!object2";
        String displayName;
        if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemAir) {
            displayName = "Nothing";
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            displayName = Wrapper.mc.player.getHeldItemMainhand().getDisplayName();
        }
        nhFM.add(new xbUc.wwe/xbUc(s, displayName));
        this.Bqom.leSX(true);
        this.Bqom.NhFM = null;
    }
    
    @oqrr
    public void GGpl(final CTFs.wwe/MQil wwe/MQil) {
        (this.uYwf.NhFM = new ArrayList()).add(new xbUc.wwe/xbUc("!object1", Wrapper.mc.world.getBlockState(wwe/MQil.phME).getBlock().getLocalizedName()));
        this.uYwf.leSX(true);
        this.uYwf.NhFM = null;
    }
    
    @oqrr
    public void IiGS(final CTFs.wwe/GJnV wwe/GJnV) {
        (this.trvG.NhFM = new ArrayList()).add(new xbUc.wwe/xbUc("!object1", Wrapper.mc.world.getBlockState(wwe/GJnV.phME).getBlock().getLocalizedName()));
        this.trvG.leSX(true);
        this.trvG.NhFM = null;
    }
    
    @oqrr
    public void gbjy(final uqzS uqzS) {
        if (uqzS.MYlK() instanceof CPacketPlayerTryUseItemOnBlock) {
            (this.MnIL.NhFM = new ArrayList()).add(new xbUc.wwe/xbUc("!object1", Wrapper.mc.player.getHeldItem(((CPacketPlayerTryUseItemOnBlock)uqzS.MYlK()).getHand()).getDisplayName()));
            this.MnIL.leSX(Wrapper.mc.player.getHeldItem(((CPacketPlayerTryUseItemOnBlock)uqzS.MYlK()).getHand()).getItem() instanceof ItemBlock);
            this.MnIL.NhFM = null;
        }
    }
    
    @Override
    public void OgHM() {
        if (!this.IuiN()) {
            return;
        }
        (this.nrXO.NhFM = new ArrayList()).add(new xbUc.wwe/xbUc("!object1", Wrapper.mc.world.getBlockState(Wrapper.mc.objectMouseOver.getBlockPos()).getBlock().getLocalizedName()));
        final xbUc.wwe/xbUc nrXO = this.nrXO;
        boolean b;
        if (Wrapper.mc.objectMouseOver.getBlockPos() != null && !(Wrapper.mc.world.getBlockState(Wrapper.mc.objectMouseOver.getBlockPos()).getBlock() instanceof BlockAir)) {
            b = true;
            if (Announcer.\u2003\u2006\u2003\u2005\u2005\u2003\u2007) {
                throw null;
            }
        }
        else {
            b = false;
        }
        nrXO.leSX(b);
        this.nrXO.NhFM = null;
    }
    
    @Override
    public void LPxx() {
        Wrapper.ypJK().wBGm(this);
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
    }
    
    public void eydA() {
        this.fICx = (0xA8DAE57A2DB276B2L ^ 0x57251A85D24D894DL);
        this.Dbqg = 0.0;
        this.nElk.uffc.BBwj();
    }
}
