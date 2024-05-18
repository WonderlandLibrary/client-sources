package wwe;

import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class PQVr extends Gui
{
    private final int ofoi;
    private final FontRenderer HTCH;
    public int vfNB;
    public int UPTf;
    private final int hGZK;
    private final int Rjof;
    private String bvSU;
    private int Khba;
    private int haCx;
    private boolean DkzG;
    private boolean JXqq;
    private boolean eZDl;
    private boolean TFCV;
    private int bxJC;
    private int nQvb;
    private int pKai;
    private int cyTH;
    private int omsv;
    private boolean oSVh;
    private GuiPageButtonList.GuiResponder BLjY;
    private Predicate<String> iBRc;
    
    public PQVr(final int ofoi, final FontRenderer htch, final int vfNB, final int upTf, final int hgzk, final int rjof) {
        super();
        this.bvSU = "";
        this.Khba = 32;
        this.DkzG = true;
        this.JXqq = true;
        this.TFCV = true;
        this.cyTH = 14737632;
        this.omsv = 7368816;
        this.oSVh = true;
        this.iBRc = (Predicate<String>)Predicates.alwaysTrue();
        this.ofoi = ofoi;
        this.HTCH = htch;
        this.vfNB = vfNB;
        this.UPTf = upTf;
        this.hGZK = hgzk;
        this.Rjof = rjof;
    }
    
    public void AQcP(final GuiPageButtonList.GuiResponder bLjY) {
        this.BLjY = bLjY;
    }
    
    public void DHVQ() {
        ++this.haCx;
    }
    
    public void MCrG(final String bvSU) {
        if (this.iBRc.apply(bvSU)) {
            if (bvSU.length() > this.Khba) {
                this.bvSU = bvSU.substring(0, this.Khba);
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else {
                this.bvSU = bvSU;
            }
            this.FUtr();
        }
    }
    
    public String pBDN() {
        return this.bvSU;
    }
    
    public String sOIT() {
        int n;
        if (this.nQvb < this.pKai) {
            n = this.nQvb;
            if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                throw null;
            }
        }
        else {
            n = this.pKai;
        }
        final int n2 = n;
        int n3;
        if (this.nQvb < this.pKai) {
            n3 = this.pKai;
            if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                throw null;
            }
        }
        else {
            n3 = this.nQvb;
        }
        return this.bvSU.substring(n2, n3);
    }
    
    public void POVA(final Predicate<String> ibRc) {
        this.iBRc = ibRc;
    }
    
    public void FBRk(final String s) {
        String string = "";
        final String filterAllowedCharacters = ChatAllowedCharacters.filterAllowedCharacters(s);
        int n;
        if (this.nQvb < this.pKai) {
            n = this.nQvb;
            if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                throw null;
            }
        }
        else {
            n = this.pKai;
        }
        final int n2 = n;
        int n3;
        if (this.nQvb < this.pKai) {
            n3 = this.pKai;
            if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                throw null;
            }
        }
        else {
            n3 = this.nQvb;
        }
        final int n4 = n3;
        final int n5 = this.Khba - this.bvSU.length() - (n2 - n4);
        if (!this.bvSU.isEmpty()) {
            string += this.bvSU.substring(0, n2);
        }
        String bvSU;
        int length;
        if (n5 < filterAllowedCharacters.length()) {
            bvSU = string + filterAllowedCharacters.substring(0, n5);
            length = n5;
            if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                throw null;
            }
        }
        else {
            bvSU = string + filterAllowedCharacters;
            length = filterAllowedCharacters.length();
        }
        if (!this.bvSU.isEmpty() && n4 < this.bvSU.length()) {
            bvSU += this.bvSU.substring(n4);
        }
        if (this.iBRc.apply(bvSU)) {
            this.bvSU = bvSU;
            this.Wwxn(n2 - this.pKai + length);
            this.afCQ(this.ofoi, this.bvSU);
        }
    }
    
    public void afCQ(final int n, final String s) {
        if (this.BLjY != null) {
            this.BLjY.setEntryValue(n, s);
        }
    }
    
    public void BJNy(final int n) {
        if (!this.bvSU.isEmpty()) {
            if (this.pKai != this.nQvb) {
                this.FBRk("");
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else {
                this.vdET(this.qlpZ(n) - this.nQvb);
            }
        }
    }
    
    public void vdET(final int n) {
        if (!this.bvSU.isEmpty()) {
            if (this.pKai != this.nQvb) {
                this.FBRk("");
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else {
                boolean b;
                if (n < 0) {
                    b = true;
                    if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                        throw null;
                    }
                }
                else {
                    b = false;
                }
                final boolean b2 = b;
                int nQvb;
                if (b2) {
                    nQvb = this.nQvb + n;
                    if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                        throw null;
                    }
                }
                else {
                    nQvb = this.nQvb;
                }
                final int n2 = nQvb;
                int nQvb2;
                if (b2) {
                    nQvb2 = this.nQvb;
                    if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                        throw null;
                    }
                }
                else {
                    nQvb2 = this.nQvb + n;
                }
                final int n3 = nQvb2;
                String bvSU = "";
                if (n2 >= 0) {
                    bvSU = this.bvSU.substring(0, n2);
                }
                if (n3 < this.bvSU.length()) {
                    bvSU += this.bvSU.substring(n3);
                }
                if (this.iBRc.apply(bvSU)) {
                    this.bvSU = bvSU;
                    if (b2) {
                        this.Wwxn(n);
                    }
                    this.afCQ(this.ofoi, this.bvSU);
                }
            }
        }
    }
    
    public int oYEe() {
        return this.ofoi;
    }
    
    public int qlpZ(final int n) {
        return this.YCEU(n, this.jXjn());
    }
    
    public int YCEU(final int n, final int n2) {
        return this.vrkY(n, n2, true);
    }
    
    public int vrkY(final int n, final int n2, final boolean b) {
        int index = n2;
        if (n < 0) {
            if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                throw null;
            }
        }
        final int abs = Math.abs(n);
        int i = 0;
        while (i < abs) {
            final int length = this.bvSU.length();
            index = this.bvSU.indexOf(32, index);
            if (index == -1) {
                index = length;
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else {
                while (b && index < length && this.bvSU.charAt(index) == ' ') {
                    ++index;
                    if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                        throw null;
                    }
                }
            }
            if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                throw null;
            }
            ++i;
            if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                throw null;
            }
        }
        return index;
    }
    
    public void Wwxn(final int n) {
        this.MBxR(this.pKai + n);
    }
    
    public void MBxR(final int nQvb) {
        this.nQvb = nQvb;
        this.GycT(this.nQvb = MathHelper.clamp(this.nQvb, 0, this.bvSU.length()));
    }
    
    public void uRPp() {
        this.MBxR(0);
    }
    
    public void FUtr() {
        this.MBxR(this.bvSU.length());
    }
    
    public boolean SifW(final char c, final int n) {
        if (!this.eZDl) {
            return false;
        }
        if (GuiScreen.isKeyComboCtrlA(n)) {
            this.FUtr();
            this.GycT(0);
            return true;
        }
        if (GuiScreen.isKeyComboCtrlC(n)) {
            GuiScreen.setClipboardString(this.sOIT());
            return true;
        }
        if (GuiScreen.isKeyComboCtrlV(n)) {
            if (this.TFCV) {
                this.FBRk(GuiScreen.getClipboardString());
            }
            return true;
        }
        if (GuiScreen.isKeyComboCtrlX(n)) {
            GuiScreen.setClipboardString(this.sOIT());
            if (this.TFCV) {
                this.FBRk("");
            }
            return true;
        }
        switch (n) {
            case 14: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.TFCV) {
                        this.BJNy(-1);
                        if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                            throw null;
                        }
                    }
                }
                else if (this.TFCV) {
                    this.vdET(-1);
                }
                return true;
            }
            case 199: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.GycT(0);
                    if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                        throw null;
                    }
                }
                else {
                    this.uRPp();
                }
                return true;
            }
            case 203: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.GycT(this.YCEU(-1, this.hGrN()));
                        if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                            throw null;
                        }
                    }
                    else {
                        this.GycT(this.hGrN() - 1);
                        if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                            throw null;
                        }
                    }
                }
                else if (GuiScreen.isCtrlKeyDown()) {
                    this.MBxR(this.qlpZ(-1));
                    if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                        throw null;
                    }
                }
                else {
                    this.Wwxn(-1);
                }
                return true;
            }
            case 205: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.GycT(this.YCEU(1, this.hGrN()));
                        if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                            throw null;
                        }
                    }
                    else {
                        this.GycT(this.hGrN() + 1);
                        if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                            throw null;
                        }
                    }
                }
                else if (GuiScreen.isCtrlKeyDown()) {
                    this.MBxR(this.qlpZ(1));
                    if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                        throw null;
                    }
                }
                else {
                    this.Wwxn(1);
                }
                return true;
            }
            case 207: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.GycT(this.bvSU.length());
                    if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                        throw null;
                    }
                }
                else {
                    this.FUtr();
                }
                return true;
            }
            case 211: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.TFCV) {
                        this.BJNy(1);
                        if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                            throw null;
                        }
                    }
                }
                else if (this.TFCV) {
                    this.vdET(1);
                }
                return true;
            }
            default: {
                if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                    if (this.TFCV) {
                        this.FBRk(Character.toString(c));
                    }
                    return true;
                }
                return false;
            }
        }
    }
    
    public void iqZX(final int n, final int n2, final int n3) {
        boolean b;
        if (n >= this.vfNB && n < this.vfNB + this.hGZK && n2 >= this.UPTf && n2 < this.UPTf + this.Rjof) {
            b = true;
            if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                throw null;
            }
        }
        else {
            b = false;
        }
        final boolean b2 = b;
        if (this.JXqq) {
            this.iCKJ(b2);
        }
        if (this.eZDl && b2) {
            int n4 = n - this.vfNB;
            if (this.DkzG) {
                n4 -= 4;
            }
            this.MBxR(this.HTCH.trimStringToWidth(this.HTCH.trimStringToWidth(this.bvSU.substring(this.bxJC), this.ZPns()), n4).length() + this.bxJC);
        }
    }
    
    public void zJhX() {
        if (this.xbHB()) {
            if (this.XvLy()) {
                drawRect(this.vfNB - 1, this.UPTf - 1, this.vfNB + this.hGZK + 1, this.UPTf + this.Rjof + 1, -6250336);
                drawRect(this.vfNB, this.UPTf, this.vfNB + this.hGZK, this.UPTf + this.Rjof, -16777216);
            }
            int n;
            if (this.TFCV) {
                n = this.cyTH;
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else {
                n = this.omsv;
            }
            final int n2 = n;
            final int n3 = this.nQvb - this.bxJC;
            int length = this.pKai - this.bxJC;
            final String trimStringToWidth = this.HTCH.trimStringToWidth(this.bvSU.substring(this.bxJC), this.ZPns());
            boolean b;
            if (n3 >= 0 && n3 <= trimStringToWidth.length()) {
                b = true;
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else {
                b = false;
            }
            final boolean b2 = b;
            boolean b3;
            if (this.eZDl && this.haCx / 6 % 2 == 0 && b2) {
                b3 = true;
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else {
                b3 = false;
            }
            final boolean b4 = b3;
            int vfNB;
            if (this.DkzG) {
                vfNB = this.vfNB + 4;
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else {
                vfNB = this.vfNB;
            }
            final int n4 = vfNB;
            int upTf;
            if (this.DkzG) {
                upTf = this.UPTf + (this.Rjof - 8) / 2;
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else {
                upTf = this.UPTf;
            }
            final int n5 = upTf;
            int drawStringWithShadow = n4;
            if (length > trimStringToWidth.length()) {
                length = trimStringToWidth.length();
            }
            if (!trimStringToWidth.isEmpty()) {
                String substring;
                if (b2) {
                    substring = trimStringToWidth.substring(0, n3);
                    if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                        throw null;
                    }
                }
                else {
                    substring = trimStringToWidth;
                }
                drawStringWithShadow = this.HTCH.drawStringWithShadow(substring.replaceAll("(?s).", "*"), (float)n4, (float)n5, n2);
            }
            boolean b5;
            if (this.nQvb < this.bvSU.length() || this.bvSU.length() >= this.PoHu()) {
                b5 = true;
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else {
                b5 = false;
            }
            final boolean b6 = b5;
            int n6;
            if (n3 > 0) {
                n6 = n4 + this.hGZK;
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else {
                n6 = n4;
            }
            final int n7 = n6;
            if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                throw null;
            }
            if (!trimStringToWidth.isEmpty() && b2 && n3 < trimStringToWidth.length()) {
                this.HTCH.drawStringWithShadow(trimStringToWidth.substring(n3), (float)drawStringWithShadow, (float)n5, n2);
            }
            if (b4) {
                if (b6) {
                    Gui.drawRect(n7, n5 - 1, n7 + 1, n5 + 1 + this.HTCH.FONT_HEIGHT, -3092272);
                    if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                        throw null;
                    }
                }
                else {
                    this.HTCH.drawStringWithShadow("_", (float)n7, (float)n5, n2);
                }
            }
            if (length != n3) {
                this.kTbU(n7, n5 - 1, n4 + this.HTCH.getStringWidth(trimStringToWidth.substring(0, length)) - 1, n5 + 1 + this.HTCH.FONT_HEIGHT);
            }
        }
    }
    
    private void kTbU(int n, int n2, int n3, int n4) {
        if (n < n3) {
            final int n5 = n;
            n = n3;
            n3 = n5;
        }
        if (n2 < n4) {
            final int n6 = n2;
            n2 = n4;
            n4 = n6;
        }
        if (n3 > this.vfNB + this.hGZK) {
            n3 = this.vfNB + this.hGZK;
        }
        if (n > this.vfNB + this.hGZK) {
            n = this.vfNB + this.hGZK;
        }
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        GlStateManager.color(0.0f, 0.0f, 255.0f, 255.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos((double)n, (double)n4, 0.0).endVertex();
        buffer.pos((double)n3, (double)n4, 0.0).endVertex();
        buffer.pos((double)n3, (double)n2, 0.0).endVertex();
        buffer.pos((double)n, (double)n2, 0.0).endVertex();
        instance.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }
    
    public void cRhr(final int khba) {
        this.Khba = khba;
        if (this.bvSU.length() > khba) {
            this.bvSU = this.bvSU.substring(0, khba);
        }
    }
    
    public int PoHu() {
        return this.Khba;
    }
    
    public int jXjn() {
        return this.nQvb;
    }
    
    public boolean XvLy() {
        return this.DkzG;
    }
    
    public void ppvC(final boolean dkzG) {
        this.DkzG = dkzG;
    }
    
    public void DoOm(final int cyTH) {
        this.cyTH = cyTH;
    }
    
    public void ERrP(final int omsv) {
        this.omsv = omsv;
    }
    
    public void iCKJ(final boolean ezDl) {
        if (ezDl && !this.eZDl) {
            this.haCx = 0;
        }
        this.eZDl = ezDl;
    }
    
    public boolean mIOc() {
        return this.eZDl;
    }
    
    public void XvJW(final boolean tfcv) {
        this.TFCV = tfcv;
    }
    
    public int hGrN() {
        return this.pKai;
    }
    
    public int ZPns() {
        int hgzk;
        if (this.XvLy()) {
            hgzk = this.hGZK - 8;
            if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                throw null;
            }
        }
        else {
            hgzk = this.hGZK;
        }
        return hgzk;
    }
    
    public void GycT(int pKai) {
        final int length = this.bvSU.length();
        if (pKai > length) {
            pKai = length;
        }
        if (pKai < 0) {
            pKai = 0;
        }
        this.pKai = pKai;
        if (this.HTCH != null) {
            if (this.bxJC > length) {
                this.bxJC = length;
            }
            final int zPns = this.ZPns();
            final int n = this.HTCH.trimStringToWidth(this.bvSU.substring(this.bxJC), zPns).length() + this.bxJC;
            if (pKai == this.bxJC) {
                this.bxJC -= this.HTCH.trimStringToWidth(this.bvSU, zPns, true).length();
            }
            if (pKai > n) {
                this.bxJC += pKai - n;
                if (PQVr.\u200d\u2006\u2003\u200c\u2003) {
                    throw null;
                }
            }
            else if (pKai <= this.bxJC) {
                this.bxJC -= this.bxJC - pKai;
            }
            this.bxJC = MathHelper.clamp(this.bxJC, 0, length);
        }
    }
    
    public void YOqX(final boolean jXqq) {
        this.JXqq = jXqq;
    }
    
    public boolean xbHB() {
        return this.oSVh;
    }
    
    public void JCkp(final boolean osVh) {
        this.oSVh = osVh;
    }
}
