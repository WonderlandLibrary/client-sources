package wwe.modules.world;

import wwe.modules.*;
import net.minecraft.block.*;
import wwe.utils.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import wwe.*;

public class NoteBot extends qMIe
{
    public int mbRf;
    public static ajLR DbuO;
    public ZhDd yFHK;
    public ArrayList<BlockPos> bGhe;
    public int fbZI;
    public int PEaL;
    public ModeValue mode;
    public BooleanValue tune;
    public BooleanValue lyrics;
    public NumberValue maxNotes;
    public BooleanValue esp;
    
    public NoteBot() {
        super("Note Bot", "Play music from note blocks", 0, Category.WORLD);
        this.bGhe = new ArrayList<BlockPos>();
    }
    
    @Override
    public void HrDr() {
        this.azsP(false);
        final ArrayList<String> list = new ArrayList<String>();
        list.add("5x5");
        list.add("All");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "All", list);
        this.tune = new BooleanValue("Tune", this, "Tune the note blocks to the correct notes", false);
        this.lyrics = new BooleanValue("Lyrics", this, "Types in chat the lyrics, if the song has them", true);
        this.maxNotes = new NumberValue("Max Notes", this, "The max notes to hit per tick", 7.0, 1.0, 20.0);
        this.esp = new BooleanValue("ESP", this, "Draws a box around targeted not blocks", false);
    }
    
    @oqrr
    public void leGN(final omqH omqH) {
        if (NoteBot.DbuO == null && !this.tune.OnGi()) {
            HAxG.TuzD("No song is loaded, use the command .notebot");
            this.GDVp();
            return;
        }
        this.bGhe.clear();
        if (this.tune.OnGi()) {
            int i = 0;
            while (i < this.yFHK.zBJd.size()) {
                final PsqP psqP = this.yFHK.zBJd.get(i);
                if (psqP.SKIQ != psqP.lUAg) {
                    int abs = Math.abs(psqP.SKIQ - psqP.lUAg);
                    if (abs <= 0 || abs >= 23) {
                        abs = 0;
                    }
                    final int fbZI = this.fbZI;
                    int n;
                    if (abs > 2) {
                        n = 0;
                        if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                            throw null;
                        }
                    }
                    else {
                        n = 2;
                    }
                    if (fbZI >= n) {
                        this.bGhe.add(psqP.maCT);
                        omqH.prFd = WFux.PMvk(this.bGhe.get(0).getX() + 0.5, this.bGhe.get(0).getY() + 0.5, this.bGhe.get(0).getZ() + 0.5)[0];
                        omqH.OjyD = WFux.PMvk(this.bGhe.get(0).getX() + 0.5, this.bGhe.get(0).getY() + 0.5, this.bGhe.get(0).getZ() + 0.5)[1];
                        this.fbZI = 0;
                        if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++this.fbZI;
                        if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                            throw null;
                        }
                        break;
                    }
                }
                else {
                    ++i;
                    if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                        throw null;
                    }
                    continue;
                }
            }
            if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                throw null;
            }
        }
        else {
            int n2 = 0;
            for (final ajLR.wwe/WkII wwe/WkII : NoteBot.DbuO.FgWb) {
                if (n2 >= this.maxNotes.OnGi()) {
                    if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                        throw null;
                    }
                    break;
                }
                else {
                    if (wwe/WkII.cCcA == this.mbRf) {
                        for (final PsqP psqP2 : this.yFHK.zBJd) {
                            Label_0578: {
                                if (this.mode.OnGi().equalsIgnoreCase("5x5")) {
                                    if (psqP2.lUAg != wwe/WkII.fljR) {
                                        break Label_0578;
                                    }
                                    if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                                        throw null;
                                    }
                                }
                                else if (psqP2.lUAg != wwe/WkII.fljR || wwe/WkII.eEsY != psqP2.CPyB) {
                                    break Label_0578;
                                }
                                if (Wrapper.mc.world.getBlockState(psqP2.maCT).getBlock() instanceof BlockNote) {
                                    this.bGhe.add(psqP2.maCT);
                                }
                            }
                            if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                                throw null;
                            }
                        }
                        ++n2;
                    }
                    if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                        throw null;
                    }
                    continue;
                }
            }
            if (this.lyrics.OnGi()) {
                for (final ajLR.wwe/ABpb wwe/ABpb : NoteBot.DbuO.nOsS) {
                    if (wwe/ABpb.RZpY == this.mbRf && wwe/ABpb.MSAn != "") {
                        Wrapper.mc.player.sendChatMessage(wwe/ABpb.MSAn);
                        if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                            throw null;
                        }
                        continue;
                    }
                }
            }
            if (!this.bGhe.isEmpty()) {
                for (final BlockPos blockPos : this.bGhe) {
                    omqH.prFd = WFux.PMvk(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5)[0];
                    omqH.OjyD = WFux.PMvk(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5)[1];
                    if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                        throw null;
                    }
                }
            }
        }
        if (!this.tune.OnGi()) {
            ++this.mbRf;
            if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                throw null;
            }
        }
        else {
            this.mbRf = 0;
        }
    }
    
    @oqrr
    public void jxpe(final ILBH ilbh) {
        if (!this.bGhe.isEmpty()) {
            for (final BlockPos blockPos : this.bGhe) {
                if (this.tune.OnGi()) {
                    Wrapper.mc.playerController.processRightClickBlock(Wrapper.mc.player, Wrapper.mc.world, blockPos, EnumFacing.UP, new Vec3d(0.5, 0.5, 0.5), EnumHand.MAIN_HAND);
                    LKsL.LInL();
                    if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                        throw null;
                    }
                }
                else {
                    Wrapper.mc.playerController.clickBlock(blockPos, EnumFacing.UP);
                    LKsL.LInL();
                }
                if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                    throw null;
                }
            }
        }
    }
    
    @oqrr
    public void DzLX(final RTIo rtIo) {
        if (!this.esp.OnGi()) {
            return;
        }
        if (!this.bGhe.isEmpty()) {
            final Iterator<BlockPos> iterator = this.bGhe.iterator();
            while (iterator.hasNext()) {
                BIIV.zIBn(iterator.next(), Color.red, 1.0, 1.0);
                if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                    throw null;
                }
            }
        }
        if (this.tune.OnGi()) {
            for (final PsqP psqP : this.yFHK.zBJd) {
                GL11.glPushMatrix();
                GL11.glTranslated(psqP.maCT.getX() + 0.5 - yYGD.onRX(), psqP.maCT.getY() + 1 - yYGD.UuJK(), psqP.maCT.getZ() + 0.5 - yYGD.LkVR());
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GL11.glRotated((double)(-yYGD.BqWL()), 0.0, 1.0, 0.0);
                GL11.glRotated((double)yYGD.uDFs(), 1.0, 0.0, 0.0);
                final double n = 0.01666666753590107;
                GL11.glScaled(-n, -n, n);
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GL11.glDisable(2929);
                if (psqP.SKIQ == psqP.lUAg) {
                    Wrapper.fontRenderer.drawString(psqP.SKIQ + "", 0, 0, -11141291);
                    if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                        throw null;
                    }
                }
                else {
                    Wrapper.fontRenderer.drawString(psqP.SKIQ + "", -5, 0, -43691);
                    Wrapper.fontRenderer.drawString(psqP.lUAg + "", 5, 0, -11141291);
                }
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
                if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                    throw null;
                }
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @oqrr
    public void qsxv(final jWya jWya) {
        if (this.tune.OnGi()) {
            for (final PsqP psqP : this.yFHK.zBJd) {
                if (psqP.maCT.equals((Object)jWya.phME)) {
                    psqP.SKIQ = jWya.GFhL;
                    this.fbZI = 0;
                    this.PEaL = 0;
                    if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                        throw null;
                    }
                    break;
                }
                else {
                    if (NoteBot.\u2003\u200f\u2004\u2004\u200d) {
                        throw null;
                    }
                    continue;
                }
            }
        }
    }
    
    @Override
    public void LPxx() {
        this.mbRf = 0;
        (this.yFHK = new ZhDd()).lPuW(this.mode.OnGi().equalsIgnoreCase("5x5"));
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        this.mbRf = 0;
        hSNE.kduK(this);
    }
    
    static {
        NoteBot.DbuO = null;
    }
}
