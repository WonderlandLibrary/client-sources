package net.minecraft.src;

import org.lwjgl.input.*;
import java.util.*;
import org.lwjgl.opengl.*;
import java.net.*;
import java.io.*;
import net.minecraft.client.*;

public class GuiScreenOnlineServers extends GuiScreen
{
    private GuiScreen field_96188_a;
    private GuiSlotOnlineServerList field_96186_b;
    private static int field_96187_c;
    private static final Object field_96185_d;
    private int field_96189_n;
    private GuiButton field_96190_o;
    private GuiButton field_96198_p;
    private GuiButtonLink field_96197_q;
    private GuiButton field_96196_r;
    private String field_96195_s;
    private McoServerList field_96194_t;
    private boolean field_96193_u;
    private List field_96192_v;
    private volatile int field_96199_x;
    private Long field_102019_y;
    private int field_104044_y;
    
    static {
        GuiScreenOnlineServers.field_96187_c = 0;
        field_96185_d = new Object();
    }
    
    public GuiScreenOnlineServers(final GuiScreen par1) {
        this.field_96189_n = -1;
        this.field_96195_s = null;
        this.field_96192_v = Collections.emptyList();
        this.field_104044_y = 0;
        this.field_96188_a = par1;
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.field_96194_t = new McoServerList(this.mc.session);
        if (!this.field_96193_u) {
            this.field_96193_u = true;
            this.field_96186_b = new GuiSlotOnlineServerList(this);
        }
        else {
            this.field_96186_b.func_104084_a(this.width, this.height, 32, this.height - 64);
        }
        new ThreadOnlineScreen(this).start();
        this.func_96178_g();
    }
    
    public void func_96178_g() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.add(this.field_96196_r = new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, var1.translateKey("mco.selectServer.select")));
        this.buttonList.add(this.field_96198_p = new GuiButton(2, this.width / 2 - 48, this.height - 52, 100, 20, var1.translateKey("mco.selectServer.create")));
        this.buttonList.add(this.field_96190_o = new GuiButton(3, this.width / 2 + 58, this.height - 52, 100, 20, var1.translateKey("mco.selectServer.configure")));
        this.buttonList.add(this.field_96197_q = new GuiButtonLink(4, this.width / 2 - 154, this.height - 28, 154, 20, var1.translateKey("mco.selectServer.moreinfo")));
        this.buttonList.add(new GuiButton(0, this.width / 2 + 6, this.height - 28, 153, 20, var1.translateKey("gui.cancel")));
        final boolean var2 = this.field_96189_n >= 0 && this.field_96189_n < this.field_96186_b.getSize();
        this.field_96196_r.enabled = (var2 && this.field_96192_v.get(this.field_96189_n).field_96404_d.equals("OPEN") && !this.field_96192_v.get(this.field_96189_n).field_98166_h);
        this.field_96190_o.enabled = (var2 && this.mc.session.username.equals(this.field_96192_v.get(this.field_96189_n).field_96405_e));
        this.field_96198_p.enabled = (this.field_96199_x > 0);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.field_104044_y;
        if (this.field_96194_t.func_98251_a()) {
            final List var1 = this.field_96194_t.func_98252_c();
            for (final McoServer var3 : var1) {
                for (final McoServer var5 : this.field_96192_v) {
                    if (var3.field_96408_a == var5.field_96408_a) {
                        var3.func_96401_a(var5);
                        if (this.field_102019_y != null && this.field_102019_y == var3.field_96408_a) {
                            this.field_102019_y = null;
                            var3.field_96411_l = false;
                            break;
                        }
                        break;
                    }
                }
            }
            this.field_96192_v = var1;
            this.field_96194_t.func_98250_b();
        }
        this.field_96198_p.enabled = (this.field_96199_x > 0);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 1) {
                this.func_96159_a(this.field_96189_n);
            }
            else if (par1GuiButton.id == 3) {
                final List var2 = this.field_96194_t.func_98252_c();
                if (this.field_96189_n < var2.size()) {
                    final McoServer var3 = var2.get(this.field_96189_n);
                    final McoServer var4 = this.func_98086_a(var3.field_96408_a);
                    if (var4 != null) {
                        this.field_96194_t.func_98248_d();
                        this.mc.displayGuiScreen(new GuiScreenConfigureWorld(this, var4));
                    }
                }
            }
            else if (par1GuiButton.id == 0) {
                this.field_96194_t.func_98248_d();
                this.mc.displayGuiScreen(this.field_96188_a);
            }
            else if (par1GuiButton.id == 2) {
                this.field_96194_t.func_98248_d();
                this.mc.displayGuiScreen(new GuiScreenCreateOnlineWorld(this));
            }
            else if (par1GuiButton.id == 4) {
                this.field_96197_q.func_96135_a("http://realms.minecraft.net/");
            }
            else {
                this.field_96186_b.actionPerformed(par1GuiButton);
            }
        }
    }
    
    public void func_102018_a(final long par1) {
        this.field_96189_n = -1;
        this.field_102019_y = par1;
    }
    
    private McoServer func_98086_a(final long par1) {
        final McoClient var3 = new McoClient(this.mc.session);
        try {
            return var3.func_98176_a(par1);
        }
        catch (ExceptionMcoService exceptionMcoService) {}
        catch (IOException ex) {}
        return null;
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == 59) {
            this.mc.gameSettings.hideServerAddress = !this.mc.gameSettings.hideServerAddress;
            this.mc.gameSettings.saveOptions();
        }
        else if (par1 == '\r') {
            this.actionPerformed(this.buttonList.get(2));
        }
        else {
            super.keyTyped(par1, par2);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.field_96195_s = null;
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.field_96186_b.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, var4.translateKey("mco.title"), this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
        if (this.field_96195_s != null) {
            this.func_96165_a(this.field_96195_s, par1, par2);
        }
    }
    
    private void func_96159_a(final int par1) {
        if (par1 >= 0 && par1 < this.field_96192_v.size()) {
            final McoServer var2 = this.field_96192_v.get(par1);
            this.field_96194_t.func_98248_d();
            final GuiScreenLongRunningTask var3 = new GuiScreenLongRunningTask(this.mc, this, new TaskOnlineConnect(this, var2));
            var3.func_98117_g();
            this.mc.displayGuiScreen(var3);
        }
    }
    
    private void func_101008_c(final int par1, final int par2, final int par3, final int par4) {
        this.mc.renderEngine.bindTexture("/gui/gui.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.drawTexturedModalRect(par1 * 2, par2 * 2, 191, 0, 16, 15);
        GL11.glPopMatrix();
        if (par3 >= par1 && par3 <= par1 + 9 && par4 >= par2 && par4 <= par2 + 9) {
            this.field_96195_s = "Expired World";
        }
    }
    
    private void func_104039_b(final int par1, final int par2, final int par3, final int par4, final int par5) {
        if (this.field_104044_y % 20 < 10) {
            this.mc.renderEngine.bindTexture("/gui/gui.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            this.drawTexturedModalRect(par1 * 2, par2 * 2, 207, 0, 16, 15);
            GL11.glPopMatrix();
        }
        if (par3 >= par1 && par3 <= par1 + 9 && par4 >= par2 && par4 <= par2 + 9) {
            if (par5 == 0) {
                this.field_96195_s = "Expires in < a day";
            }
            else {
                this.field_96195_s = "Expires in " + par5 + ((par5 > 1) ? " days" : " day");
            }
        }
    }
    
    private void func_101006_d(final int par1, final int par2, final int par3, final int par4) {
        this.mc.renderEngine.bindTexture("/gui/gui.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.drawTexturedModalRect(par1 * 2, par2 * 2, 207, 0, 16, 15);
        GL11.glPopMatrix();
        if (par3 >= par1 && par3 <= par1 + 9 && par4 >= par2 && par4 <= par2 + 9) {
            this.field_96195_s = "Open World";
        }
    }
    
    private void func_101001_e(final int par1, final int par2, final int par3, final int par4) {
        this.mc.renderEngine.bindTexture("/gui/gui.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.drawTexturedModalRect(par1 * 2, par2 * 2, 223, 0, 16, 15);
        GL11.glPopMatrix();
        if (par3 >= par1 && par3 <= par1 + 9 && par4 >= par2 && par4 <= par2 + 9) {
            this.field_96195_s = "Closed World";
        }
    }
    
    protected void func_96165_a(final String par1Str, final int par2, final int par3) {
        if (par1Str != null) {
            final int var4 = par2 + 12;
            final int var5 = par3 - 12;
            final int var6 = this.fontRenderer.getStringWidth(par1Str);
            this.drawGradientRect(var4 - 3, var5 - 3, var4 + var6 + 3, var5 + 8 + 3, -1073741824, -1073741824);
            this.fontRenderer.drawStringWithShadow(par1Str, var4, var5, -1);
        }
    }
    
    private void func_96174_a(final McoServer par1McoServer) throws IOException {
        if (par1McoServer.field_96414_k.equals("")) {
            par1McoServer.field_96414_k = new StringBuilder().append(EnumChatFormatting.GRAY).append(0).toString();
        }
        par1McoServer.field_96415_h = 61;
        final ServerAddress var2 = ServerAddress.func_78860_a(par1McoServer.field_96403_g);
        Socket var3 = null;
        DataInputStream var4 = null;
        DataOutputStream var5 = null;
        try {
            var3 = new Socket();
            var3.setSoTimeout(3000);
            var3.setTcpNoDelay(true);
            var3.setTrafficClass(18);
            var3.connect(new InetSocketAddress(var2.getIP(), var2.getPort()), 3000);
            var4 = new DataInputStream(var3.getInputStream());
            var5 = new DataOutputStream(var3.getOutputStream());
            var5.write(254);
            var5.write(1);
            if (var4.read() != 255) {
                throw new IOException("Bad message");
            }
            String var6 = Packet.readString(var4, 256);
            final char[] var7 = var6.toCharArray();
            for (int var8 = 0; var8 < var7.length; ++var8) {
                if (var7[var8] != '§' && var7[var8] != '\0' && ChatAllowedCharacters.allowedCharacters.indexOf(var7[var8]) < 0) {
                    var7[var8] = '?';
                }
            }
            var6 = new String(var7);
            if (var6.startsWith("§") && var6.length() > 1) {
                final String[] var9 = var6.substring(1).split("\u0000");
                if (MathHelper.parseIntWithDefault(var9[0], 0) == 1) {
                    par1McoServer.field_96415_h = MathHelper.parseIntWithDefault(var9[1], par1McoServer.field_96415_h);
                    par1McoServer.field_96413_j = var9[2];
                    final int var8 = MathHelper.parseIntWithDefault(var9[4], 0);
                    final int var10 = MathHelper.parseIntWithDefault(var9[5], 0);
                    if (var8 >= 0 && var10 >= 0) {
                        par1McoServer.field_96414_k = new StringBuilder().append(EnumChatFormatting.GRAY).append(var8).toString();
                    }
                    else {
                        par1McoServer.field_96414_k = EnumChatFormatting.DARK_GRAY + "???";
                    }
                }
                else {
                    par1McoServer.field_96413_j = "???";
                    par1McoServer.field_96415_h = 62;
                    par1McoServer.field_96414_k = EnumChatFormatting.DARK_GRAY + "???";
                }
            }
            else {
                final String[] var9 = var6.split("§");
                var6 = var9[0];
                int var8 = -1;
                int var10 = -1;
                try {
                    var8 = Integer.parseInt(var9[1]);
                    var10 = Integer.parseInt(var9[2]);
                }
                catch (Exception ex) {}
                par1McoServer.field_96407_c = EnumChatFormatting.GRAY + var6;
                if (var8 >= 0 && var10 > 0) {
                    par1McoServer.field_96414_k = new StringBuilder().append(EnumChatFormatting.GRAY).append(var8).toString();
                }
                else {
                    par1McoServer.field_96414_k = EnumChatFormatting.DARK_GRAY + "???";
                }
                par1McoServer.field_96413_j = "1.3";
                par1McoServer.field_96415_h = 60;
            }
        }
        finally {
            try {
                if (var4 != null) {
                    var4.close();
                }
            }
            catch (Throwable t) {}
            try {
                if (var5 != null) {
                    var5.close();
                }
            }
            catch (Throwable t2) {}
            try {
                if (var3 != null) {
                    var3.close();
                }
            }
            catch (Throwable t3) {}
        }
        try {
            if (var4 != null) {
                var4.close();
            }
        }
        catch (Throwable t4) {}
        try {
            if (var5 != null) {
                var5.close();
            }
        }
        catch (Throwable t5) {}
        try {
            if (var3 != null) {
                var3.close();
            }
        }
        catch (Throwable t6) {}
    }
    
    static Minecraft func_96177_a(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.mc;
    }
    
    static int func_98081_a(final GuiScreenOnlineServers par0GuiScreenOnlineServers, final int par1) {
        return par0GuiScreenOnlineServers.field_96199_x = par1;
    }
    
    static Minecraft func_98075_b(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.mc;
    }
    
    static List func_98094_c(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.field_96192_v;
    }
    
    static int func_98089_b(final GuiScreenOnlineServers par0GuiScreenOnlineServers, final int par1) {
        return par0GuiScreenOnlineServers.field_96189_n = par1;
    }
    
    static int func_98072_d(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.field_96189_n;
    }
    
    static GuiButton func_96161_f(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.field_96190_o;
    }
    
    static Minecraft func_98076_f(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.mc;
    }
    
    static GuiButton func_98092_g(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.field_96196_r;
    }
    
    static void func_98078_c(final GuiScreenOnlineServers par0GuiScreenOnlineServers, final int par1) {
        par0GuiScreenOnlineServers.func_96159_a(par1);
    }
    
    static Minecraft func_98091_h(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.mc;
    }
    
    static FontRenderer func_104038_i(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.fontRenderer;
    }
    
    static void func_101012_b(final GuiScreenOnlineServers par0GuiScreenOnlineServers, final int par1, final int par2, final int par3, final int par4) {
        par0GuiScreenOnlineServers.func_101008_c(par1, par2, par3, par4);
    }
    
    static void func_101009_c(final GuiScreenOnlineServers par0GuiScreenOnlineServers, final int par1, final int par2, final int par3, final int par4) {
        par0GuiScreenOnlineServers.func_101001_e(par1, par2, par3, par4);
    }
    
    static Minecraft func_104032_j(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.mc;
    }
    
    static void func_104030_a(final GuiScreenOnlineServers par0GuiScreenOnlineServers, final int par1, final int par2, final int par3, final int par4, final int par5) {
        par0GuiScreenOnlineServers.func_104039_b(par1, par2, par3, par4, par5);
    }
    
    static void func_104031_c(final GuiScreenOnlineServers par0GuiScreenOnlineServers, final int par1, final int par2, final int par3, final int par4) {
        par0GuiScreenOnlineServers.func_101006_d(par1, par2, par3, par4);
    }
    
    static FontRenderer func_98084_i(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.fontRenderer;
    }
    
    static FontRenderer func_101005_j(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.fontRenderer;
    }
    
    static Object func_101007_h() {
        return GuiScreenOnlineServers.field_96185_d;
    }
    
    static int func_101010_i() {
        return GuiScreenOnlineServers.field_96187_c;
    }
    
    static int func_101014_j() {
        return GuiScreenOnlineServers.field_96187_c++;
    }
    
    static void func_101002_a(final GuiScreenOnlineServers par0GuiScreenOnlineServers, final McoServer par1McoServer) throws IOException {
        par0GuiScreenOnlineServers.func_96174_a(par1McoServer);
    }
    
    static int func_101013_k() {
        return GuiScreenOnlineServers.field_96187_c--;
    }
    
    static FontRenderer func_98079_k(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.fontRenderer;
    }
    
    static FontRenderer func_98087_l(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.fontRenderer;
    }
    
    static FontRenderer func_98074_m(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.fontRenderer;
    }
    
    static FontRenderer func_101000_n(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.fontRenderer;
    }
    
    static Minecraft func_101004_o(final GuiScreenOnlineServers par0GuiScreenOnlineServers) {
        return par0GuiScreenOnlineServers.mc;
    }
    
    static String func_101011_a(final GuiScreenOnlineServers par0GuiScreenOnlineServers, final String par1Str) {
        return par0GuiScreenOnlineServers.field_96195_s = par1Str;
    }
}
