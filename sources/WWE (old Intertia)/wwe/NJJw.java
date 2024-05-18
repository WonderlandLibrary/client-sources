package wwe;

import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import wwe.utils.*;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;

public class NJJw extends GuiScreen
{
    public GuiScreen XpKS;
    public GuiTextField eYsx;
    public PQVr PFfH;
    public String lwDZ;
    
    public NJJw(final GuiScreen xpKS) {
        super();
        this.XpKS = xpKS;
    }
    
    public void func_73866_w_() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + 12, "Login"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96 + 36, "Back"));
        this.eYsx = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, 51, 200, 20);
        this.PFfH = new PQVr(2, this.fontRenderer, this.width / 2 - 100, 91, 200, 20);
        this.eYsx.setMaxStringLength(200);
        this.PFfH.cRhr(200);
    }
    
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void func_73876_c() {
        this.eYsx.updateCursorCounter();
        this.PFfH.DHVQ();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        this.eYsx.mouseClicked(n, n2, n3);
        this.PFfH.iqZX(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146284_a(final GuiButton guiButton) {
        if (guiButton.id == 1) {
            final KULU kulu = new KULU(this.eYsx.getText(), this.PFfH.pBDN());
            if (kulu.smZj()) {
                yYGD.VHzJ(kulu.sEFp());
            }
            if (NJJw.\u2002\u200a\u200d\u2008\u200f) {
                throw null;
            }
        }
        else if (guiButton.id == 2) {
            Wrapper.mc.displayGuiScreen(this.XpKS);
        }
    }
    
    public static String Qzjd(final String s, final String s2) {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpsURLConnection)new URL(s).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Type", Integer.toString(s2.getBytes().length));
            httpURLConnection.setRequestProperty("Content-Language", "en-US");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(s2);
            dataOutputStream.flush();
            dataOutputStream.close();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            final StringBuffer sb = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append('\r');
                if (NJJw.\u2002\u200a\u200d\u2008\u200f) {
                    throw null;
                }
            }
            bufferedReader.close();
            return sb.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }
    
    protected void func_73869_a(final char c, final int n) {
        this.eYsx.textboxKeyTyped(c, n);
        this.PFfH.SifW(c, n);
        if (c == '\t') {
            if (this.eYsx.isFocused()) {
                this.eYsx.setFocused(false);
                this.PFfH.iCKJ(true);
                if (NJJw.\u2002\u200a\u200d\u2008\u200f) {
                    throw null;
                }
            }
            else if (this.PFfH.mIOc()) {
                this.eYsx.setFocused(false);
                this.PFfH.iCKJ(false);
            }
        }
        if (c == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.drawString(this.fontRenderer, "Username", this.width / 2 - 100, 38, 10526880);
        this.drawString(this.fontRenderer, "§4*", this.width / 2 - 106, 38, 10526880);
        this.drawString(this.fontRenderer, "Password", this.width / 2 - 100, 79, 10526880);
        if (this.lwDZ != null) {
            Wrapper.fontRenderer.drawString(this.lwDZ, this.width / 2 - Wrapper.fontRenderer.getStringWidth(this.lwDZ) / 2, 13, -1);
        }
        try {
            this.eYsx.drawTextBox();
            this.PFfH.zJhX();
            if (NJJw.\u2002\u200a\u200d\u2008\u200f) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        super.drawScreen(n, n2, n3);
    }
}
