package wwe;

import javax.net.ssl.*;
import java.net.*;
import wwe.utils.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.util.math.*;
import java.io.*;
import org.apache.commons.io.*;

public class faUw extends GuiScreen
{
    public ArrayList<XOnZ> tTLQ;
    public XOnZ LPyl;
    public int rvwt;
    public GuiTextField yHeb;
    public ArrayList<String> tHbn;
    public GuiScreen Hpjt;
    
    public faUw(final GuiScreen hpjt) {
        super();
        this.tTLQ = new ArrayList<XOnZ>();
        this.tHbn = new ArrayList<String>();
        this.Hpjt = hpjt;
    }
    
    public void func_73866_w_() {
        this.tTLQ.clear();
        try {
            final HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URL("https://wweclient.com/notebot.php").openConnection();
            httpsURLConnection.setRequestProperty("User-Agent", "WWE CLIENT BEST CLIENT USER-AGENT FOR CURRENT USERS");
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                this.tHbn.add(line);
                if (faUw.\u200b\u2002) {
                    throw null;
                }
            }
            bufferedReader.close();
            if (faUw.\u200b\u2002) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        int n = 40;
        final int n2 = 200;
        final Iterator<String> iterator = this.tHbn.iterator();
        while (iterator.hasNext()) {
            this.tTLQ.add(new XOnZ(this, iterator.next(), this.width / 2 - n2 / 2, n, n2, 10));
            n += 12;
            if (faUw.\u200b\u2002) {
                throw null;
            }
        }
        this.addButton(new GuiButton(0, this.width / 2 - 53, this.height - 25, 50, 20, "Back"));
        this.addButton(new GuiButton(1, this.width / 2 + 3, this.height - 25, 50, 20, "Download"));
        (this.yHeb = new GuiTextField(0, Wrapper.fontRenderer, this.width / 2 - 100, 10, 200, 20)).setFocused(true);
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        if (this.tTLQ.size() < (this.height - 70) / 12) {
            this.rvwt = 0;
            if (faUw.\u200b\u2002) {
                throw null;
            }
        }
        else if (this.rvwt > 0) {
            this.rvwt = 0;
            if (faUw.\u200b\u2002) {
                throw null;
            }
        }
        else if (Math.abs(this.rvwt) > this.tTLQ.size() * 12 - (this.height - 70)) {
            this.rvwt = -(this.tTLQ.size() * 12 - (this.height - 70));
        }
        GL11.glPushMatrix();
        gnNa.zfJJ(0.0, 40.0, this.width, new ScaledResolution(Wrapper.mc).getScaledHeight_double() - 30.0);
        GL11.glEnable(3089);
        int i = 0;
        while (i < this.tTLQ.size()) {
            this.tTLQ.get(i).SLyu(n, n2, n3);
            ++i;
            if (faUw.\u200b\u2002) {
                throw null;
            }
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        this.yHeb.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    public void func_73876_c() {
        this.yHeb.updateCursorCounter();
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        int i = 0;
        while (i < this.tTLQ.size()) {
            final XOnZ lPyl = this.tTLQ.get(i);
            if (lPyl.Fpdu) {
                if (this.LPyl == lPyl) {
                    this.LPyl = null;
                    if (faUw.\u200b\u2002) {
                        throw null;
                    }
                }
                else {
                    this.LPyl = lPyl;
                }
                return;
            }
            ++i;
            if (faUw.\u200b\u2002) {
                throw null;
            }
        }
        this.LPyl = null;
        this.yHeb.mouseClicked(n, n2, n3);
    }
    
    public void func_146286_b(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
    }
    
    public void func_146274_d() throws IOException {
        final int dWheel = Mouse.getDWheel();
        if (dWheel > 0 || dWheel < 0) {
            this.rvwt -= MathHelper.clamp(dWheel, 3, -3);
        }
        super.handleMouseInput();
    }
    
    public void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            Wrapper.mc.displayGuiScreen(this.Hpjt);
        }
        if (guiButton.id == 1 && this.LPyl != null) {
            final HttpsURLConnection httpsURLConnection = (HttpsURLConnection)new URL("https://wweclient.com/notebot.php?song=" + this.LPyl.NIFv.replaceAll(" ", "%20")).openConnection();
            httpsURLConnection.setRequestProperty("User-Agent", "WWE CLIENT BEST CLIENT USER-AGENT FOR CURRENT USERS");
            FileUtils.copyInputStreamToFile(httpsURLConnection.getInputStream(), new File(Wrapper.ypJK().aClN.getAbsolutePath() + File.separator + this.LPyl.NIFv + ".txt"));
        }
    }
    
    public void func_73869_a(final char c, final int n) throws IOException {
        if (n == 1) {
            Wrapper.mc.displayGuiScreen(this.Hpjt);
        }
        if (this.yHeb.textboxKeyTyped(c, n)) {
            this.tTLQ.clear();
            this.rvwt = 0;
            int n2 = 40;
            final int n3 = 200;
            for (final String s : this.tHbn) {
                if (this.yHeb.getText().isEmpty() || s.toLowerCase().contains(this.yHeb.getText().toLowerCase())) {
                    this.tTLQ.add(new XOnZ(this, s, this.width / 2 - n3 / 2, n2, n3, 10));
                    n2 += 12;
                }
                if (faUw.\u200b\u2002) {
                    throw null;
                }
            }
        }
    }
}
