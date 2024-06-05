package net.minecraft.src;

import java.util.*;
import org.lwjgl.input.*;
import java.net.*;
import java.io.*;

public class GuiMultiplayer extends GuiScreen
{
    private static int threadsPending;
    private static Object lock;
    private GuiScreen parentScreen;
    private GuiSlotServer serverSlotContainer;
    private ServerList internetServerList;
    private int selectedServer;
    private GuiButton field_96289_p;
    private GuiButton buttonSelect;
    private GuiButton buttonDelete;
    private boolean deleteClicked;
    private boolean addClicked;
    private boolean editClicked;
    private boolean directClicked;
    private String lagTooltip;
    private ServerData theServerData;
    private LanServerList localNetworkServerList;
    private ThreadLanServerFind localServerFindThread;
    private int ticksOpened;
    private boolean field_74024_A;
    private List listofLanServers;
    
    static {
        GuiMultiplayer.threadsPending = 0;
        GuiMultiplayer.lock = new Object();
    }
    
    public GuiMultiplayer(final GuiScreen par1GuiScreen) {
        this.selectedServer = -1;
        this.deleteClicked = false;
        this.addClicked = false;
        this.editClicked = false;
        this.directClicked = false;
        this.lagTooltip = null;
        this.theServerData = null;
        this.listofLanServers = Collections.emptyList();
        this.parentScreen = par1GuiScreen;
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        if (!this.field_74024_A) {
            this.field_74024_A = true;
            (this.internetServerList = new ServerList(this.mc)).loadServerList();
            this.localNetworkServerList = new LanServerList();
            try {
                (this.localServerFindThread = new ThreadLanServerFind(this.localNetworkServerList)).start();
            }
            catch (Exception var2) {
                this.mc.getLogAgent().logWarning("Unable to start LAN server detection: " + var2.getMessage());
            }
            this.serverSlotContainer = new GuiSlotServer(this);
        }
        else {
            this.serverSlotContainer.func_77207_a(this.width, this.height, 32, this.height - 64);
        }
        this.initGuiControls();
    }
    
    public void initGuiControls() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.add(this.field_96289_p = new GuiButton(7, this.width / 2 - 154, this.height - 28, 70, 20, var1.translateKey("selectServer.edit")));
        this.buttonList.add(this.buttonDelete = new GuiButton(2, this.width / 2 - 74, this.height - 28, 70, 20, var1.translateKey("selectServer.delete")));
        this.buttonList.add(this.buttonSelect = new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, var1.translateKey("selectServer.select")));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height - 52, 100, 20, var1.translateKey("selectServer.direct")));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 52, 100, 20, var1.translateKey("selectServer.add")));
        this.buttonList.add(new GuiButton(8, this.width / 2 + 4, this.height - 28, 70, 20, var1.translateKey("selectServer.refresh")));
        this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 76, this.height - 28, 75, 20, var1.translateKey("gui.cancel")));
        final boolean var2 = this.selectedServer >= 0 && this.selectedServer < this.serverSlotContainer.getSize();
        this.buttonSelect.enabled = var2;
        this.field_96289_p.enabled = var2;
        this.buttonDelete.enabled = var2;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.ticksOpened;
        if (this.localNetworkServerList.getWasUpdated()) {
            this.listofLanServers = this.localNetworkServerList.getLanServers();
            this.localNetworkServerList.setWasNotUpdated();
        }
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        if (this.localServerFindThread != null) {
            this.localServerFindThread.interrupt();
            this.localServerFindThread = null;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 2) {
                final String var2 = this.internetServerList.getServerData(this.selectedServer).serverName;
                if (var2 != null) {
                    this.deleteClicked = true;
                    final StringTranslate var3 = StringTranslate.getInstance();
                    final String var4 = var3.translateKey("selectServer.deleteQuestion");
                    final String var5 = "'" + var2 + "' " + var3.translateKey("selectServer.deleteWarning");
                    final String var6 = var3.translateKey("selectServer.deleteButton");
                    final String var7 = var3.translateKey("gui.cancel");
                    final GuiYesNo var8 = new GuiYesNo(this, var4, var5, var6, var7, this.selectedServer);
                    this.mc.displayGuiScreen(var8);
                }
            }
            else if (par1GuiButton.id == 1) {
                this.joinServer(this.selectedServer);
            }
            else if (par1GuiButton.id == 4) {
                this.directClicked = true;
                this.mc.displayGuiScreen(new GuiScreenServerList(this, this.theServerData = new ServerData(StatCollector.translateToLocal("selectServer.defaultName"), "")));
            }
            else if (par1GuiButton.id == 3) {
                this.addClicked = true;
                this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.theServerData = new ServerData(StatCollector.translateToLocal("selectServer.defaultName"), "")));
            }
            else if (par1GuiButton.id == 7) {
                this.editClicked = true;
                final ServerData var9 = this.internetServerList.getServerData(this.selectedServer);
                (this.theServerData = new ServerData(var9.serverName, var9.serverIP)).setHideAddress(var9.isHidingAddress());
                this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.theServerData));
            }
            else if (par1GuiButton.id == 0) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (par1GuiButton.id == 8) {
                this.mc.displayGuiScreen(new GuiMultiplayer(this.parentScreen));
            }
            else {
                this.serverSlotContainer.actionPerformed(par1GuiButton);
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean par1, final int par2) {
        if (this.deleteClicked) {
            this.deleteClicked = false;
            if (par1) {
                this.internetServerList.removeServerData(par2);
                this.internetServerList.saveServerList();
                this.selectedServer = -1;
            }
            this.mc.displayGuiScreen(this);
        }
        else if (this.directClicked) {
            this.directClicked = false;
            if (par1) {
                this.connectToServer(this.theServerData);
            }
            else {
                this.mc.displayGuiScreen(this);
            }
        }
        else if (this.addClicked) {
            this.addClicked = false;
            if (par1) {
                this.internetServerList.addServerData(this.theServerData);
                this.internetServerList.saveServerList();
                this.selectedServer = -1;
            }
            this.mc.displayGuiScreen(this);
        }
        else if (this.editClicked) {
            this.editClicked = false;
            if (par1) {
                final ServerData var3 = this.internetServerList.getServerData(this.selectedServer);
                var3.serverName = this.theServerData.serverName;
                var3.serverIP = this.theServerData.serverIP;
                var3.setHideAddress(this.theServerData.isHidingAddress());
                this.internetServerList.saveServerList();
            }
            this.mc.displayGuiScreen(this);
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        final int var3 = this.selectedServer;
        if (par2 == 59) {
            this.mc.gameSettings.hideServerAddress = !this.mc.gameSettings.hideServerAddress;
            this.mc.gameSettings.saveOptions();
        }
        else if (GuiScreen.isShiftKeyDown() && par2 == 200) {
            if (var3 > 0 && var3 < this.internetServerList.countServers()) {
                this.internetServerList.swapServers(var3, var3 - 1);
                --this.selectedServer;
                if (var3 < this.internetServerList.countServers() - 1) {
                    this.serverSlotContainer.func_77208_b(-this.serverSlotContainer.slotHeight);
                }
            }
        }
        else if (GuiScreen.isShiftKeyDown() && par2 == 208) {
            if (var3 < this.internetServerList.countServers() - 1) {
                this.internetServerList.swapServers(var3, var3 + 1);
                ++this.selectedServer;
                if (var3 > 0) {
                    this.serverSlotContainer.func_77208_b(this.serverSlotContainer.slotHeight);
                }
            }
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
        this.lagTooltip = null;
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.serverSlotContainer.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, var4.translateKey("multiplayer.title"), this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
        if (this.lagTooltip != null) {
            this.func_74007_a(this.lagTooltip, par1, par2);
        }
    }
    
    private void joinServer(int par1) {
        if (par1 < this.internetServerList.countServers()) {
            this.connectToServer(this.internetServerList.getServerData(par1));
        }
        else {
            par1 -= this.internetServerList.countServers();
            if (par1 < this.listofLanServers.size()) {
                final LanServer var2 = this.listofLanServers.get(par1);
                this.connectToServer(new ServerData(var2.getServerMotd(), var2.getServerIpPort()));
            }
        }
    }
    
    private void connectToServer(final ServerData par1ServerData) {
        this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, par1ServerData));
    }
    
    private static void func_74017_b(final ServerData par1ServerData) throws IOException {
        final ServerAddress var1 = ServerAddress.func_78860_a(par1ServerData.serverIP);
        Socket var2 = null;
        DataInputStream var3 = null;
        DataOutputStream var4 = null;
        try {
            var2 = new Socket();
            var2.setSoTimeout(3000);
            var2.setTcpNoDelay(true);
            var2.setTrafficClass(18);
            var2.connect(new InetSocketAddress(var1.getIP(), var1.getPort()), 3000);
            var3 = new DataInputStream(var2.getInputStream());
            var4 = new DataOutputStream(var2.getOutputStream());
            var4.write(254);
            var4.write(1);
            if (var3.read() != 255) {
                throw new IOException("Bad message");
            }
            String var5 = Packet.readString(var3, 256);
            final char[] var6 = var5.toCharArray();
            for (int var7 = 0; var7 < var6.length; ++var7) {
                if (var6[var7] != '§' && var6[var7] != '\0' && ChatAllowedCharacters.allowedCharacters.indexOf(var6[var7]) < 0) {
                    var6[var7] = '?';
                }
            }
            var5 = new String(var6);
            if (var5.startsWith("§") && var5.length() > 1) {
                final String[] var8 = var5.substring(1).split("\u0000");
                if (MathHelper.parseIntWithDefault(var8[0], 0) == 1) {
                    par1ServerData.serverMOTD = var8[3];
                    par1ServerData.field_82821_f = MathHelper.parseIntWithDefault(var8[1], par1ServerData.field_82821_f);
                    par1ServerData.gameVersion = var8[2];
                    final int var7 = MathHelper.parseIntWithDefault(var8[4], 0);
                    final int var9 = MathHelper.parseIntWithDefault(var8[5], 0);
                    if (var7 >= 0 && var9 >= 0) {
                        par1ServerData.populationInfo = new StringBuilder().append(EnumChatFormatting.GRAY).append(var7).append(EnumChatFormatting.DARK_GRAY).append("/").append(EnumChatFormatting.GRAY).append(var9).toString();
                    }
                    else {
                        par1ServerData.populationInfo = EnumChatFormatting.DARK_GRAY + "???";
                    }
                }
                else {
                    par1ServerData.gameVersion = "???";
                    par1ServerData.serverMOTD = EnumChatFormatting.DARK_GRAY + "???";
                    par1ServerData.field_82821_f = 62;
                    par1ServerData.populationInfo = EnumChatFormatting.DARK_GRAY + "???";
                }
            }
            else {
                final String[] var8 = var5.split("§");
                var5 = var8[0];
                int var7 = -1;
                int var9 = -1;
                try {
                    var7 = Integer.parseInt(var8[1]);
                    var9 = Integer.parseInt(var8[2]);
                }
                catch (Exception ex) {}
                par1ServerData.serverMOTD = EnumChatFormatting.GRAY + var5;
                if (var7 >= 0 && var9 > 0) {
                    par1ServerData.populationInfo = new StringBuilder().append(EnumChatFormatting.GRAY).append(var7).append(EnumChatFormatting.DARK_GRAY).append("/").append(EnumChatFormatting.GRAY).append(var9).toString();
                }
                else {
                    par1ServerData.populationInfo = EnumChatFormatting.DARK_GRAY + "???";
                }
                par1ServerData.gameVersion = "1.3";
                par1ServerData.field_82821_f = 60;
            }
        }
        finally {
            try {
                if (var3 != null) {
                    var3.close();
                }
            }
            catch (Throwable t) {}
            try {
                if (var4 != null) {
                    var4.close();
                }
            }
            catch (Throwable t2) {}
            try {
                if (var2 != null) {
                    var2.close();
                }
            }
            catch (Throwable t3) {}
        }
        try {
            if (var3 != null) {
                var3.close();
            }
        }
        catch (Throwable t4) {}
        try {
            if (var4 != null) {
                var4.close();
            }
        }
        catch (Throwable t5) {}
        try {
            if (var2 != null) {
                var2.close();
            }
        }
        catch (Throwable t6) {}
    }
    
    protected void func_74007_a(final String par1Str, final int par2, final int par3) {
        if (par1Str != null) {
            final int var4 = par2 + 12;
            final int var5 = par3 - 12;
            final int var6 = this.fontRenderer.getStringWidth(par1Str);
            this.drawGradientRect(var4 - 3, var5 - 3, var4 + var6 + 3, var5 + 8 + 3, -1073741824, -1073741824);
            this.fontRenderer.drawStringWithShadow(par1Str, var4, var5, -1);
        }
    }
    
    static ServerList getInternetServerList(final GuiMultiplayer par0GuiMultiplayer) {
        return par0GuiMultiplayer.internetServerList;
    }
    
    static List getListOfLanServers(final GuiMultiplayer par0GuiMultiplayer) {
        return par0GuiMultiplayer.listofLanServers;
    }
    
    static int getSelectedServer(final GuiMultiplayer par0GuiMultiplayer) {
        return par0GuiMultiplayer.selectedServer;
    }
    
    static int getAndSetSelectedServer(final GuiMultiplayer par0GuiMultiplayer, final int par1) {
        return par0GuiMultiplayer.selectedServer = par1;
    }
    
    static GuiButton getButtonSelect(final GuiMultiplayer par0GuiMultiplayer) {
        return par0GuiMultiplayer.buttonSelect;
    }
    
    static GuiButton getButtonEdit(final GuiMultiplayer par0GuiMultiplayer) {
        return par0GuiMultiplayer.field_96289_p;
    }
    
    static GuiButton getButtonDelete(final GuiMultiplayer par0GuiMultiplayer) {
        return par0GuiMultiplayer.buttonDelete;
    }
    
    static void func_74008_b(final GuiMultiplayer par0GuiMultiplayer, final int par1) {
        par0GuiMultiplayer.joinServer(par1);
    }
    
    static int getTicksOpened(final GuiMultiplayer par0GuiMultiplayer) {
        return par0GuiMultiplayer.ticksOpened;
    }
    
    static Object getLock() {
        return GuiMultiplayer.lock;
    }
    
    static int getThreadsPending() {
        return GuiMultiplayer.threadsPending;
    }
    
    static int increaseThreadsPending() {
        return GuiMultiplayer.threadsPending++;
    }
    
    static void func_82291_a(final ServerData par0ServerData) throws IOException {
        func_74017_b(par0ServerData);
    }
    
    static int decreaseThreadsPending() {
        return GuiMultiplayer.threadsPending--;
    }
    
    static String getAndSetLagTooltip(final GuiMultiplayer par0GuiMultiplayer, final String par1Str) {
        return par0GuiMultiplayer.lagTooltip = par1Str;
    }
}
