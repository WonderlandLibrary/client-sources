package net.minecraft.client.gui;

import net.minecraft.client.network.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import java.util.*;
import me.satisfactory.base.gui.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.client.multiplayer.*;
import org.apache.logging.log4j.*;

public class GuiMultiplayer extends GuiScreen implements GuiYesNoCallback
{
    private static final Logger logger;
    public static ServerList savedServerList;
    private static GuiScreen parentScreen;
    private final OldServerPinger oldServerPinger;
    private ServerSelectionList serverListSelector;
    private GuiButton btnEditServer;
    private GuiButton btnSelectServer;
    private GuiButton btnDeleteServer;
    private boolean deletingServer;
    private boolean addingServer;
    private boolean editingServer;
    private boolean directConnect;
    private String field_146812_y;
    private ServerData selectedServer;
    private LanServerDetector.LanServerList lanServerList;
    private LanServerDetector.ThreadLanServerFind lanServerDetector;
    private boolean initialized;
    
    public GuiMultiplayer(final GuiScreen parentScreen) {
        this.oldServerPinger = new OldServerPinger();
        GuiMultiplayer.parentScreen = parentScreen;
    }
    
    public static void refreshServerList() {
        GuiMultiplayer.mc.displayGuiScreen(new GuiMultiplayer(GuiMultiplayer.parentScreen));
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        if (!this.initialized) {
            this.initialized = true;
            (GuiMultiplayer.savedServerList = new ServerList(GuiMultiplayer.mc)).loadServerList();
            this.lanServerList = new LanServerDetector.LanServerList();
            try {
                (this.lanServerDetector = new LanServerDetector.ThreadLanServerFind(this.lanServerList)).start();
            }
            catch (Exception var2) {
                GuiMultiplayer.logger.warn("Unable to start LAN server detection: " + var2.getMessage());
            }
            (this.serverListSelector = new ServerSelectionList(this, GuiMultiplayer.mc, GuiMultiplayer.width, GuiMultiplayer.height, 32, GuiMultiplayer.height - 64, 36)).func_148195_a(GuiMultiplayer.savedServerList);
        }
        else {
            this.serverListSelector.setDimensions(GuiMultiplayer.width, GuiMultiplayer.height, 32, GuiMultiplayer.height - 64);
        }
        this.createButtons();
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        this.serverListSelector.func_178039_p();
    }
    
    public void createButtons() {
        this.buttonList.add(this.btnSelectServer = new GuiButton(1, GuiMultiplayer.width / 2 - 154 - 5 - 4, GuiMultiplayer.height - 52, 80, 20, I18n.format("selectServer.select", new Object[0])));
        this.buttonList.add(new GuiButton(4, GuiMultiplayer.width / 2 - 74 - 5 - 2, GuiMultiplayer.height - 52, 80, 20, I18n.format("selectServer.direct", new Object[0])));
        this.buttonList.add(new GuiButton(3, GuiMultiplayer.width / 2 + 4 - 5 + 2, GuiMultiplayer.height - 52, 80, 20, I18n.format("selectServer.add", new Object[0])));
        this.buttonList.add(new GuiButton(5, GuiMultiplayer.width / 2 + 4 + 76 + 5 - 2, GuiMultiplayer.height - 52, 80, 20, "§ Set nick/UUID"));
        this.buttonList.add(this.btnEditServer = new GuiButton(7, GuiMultiplayer.width / 2 - 154 - 5 - 4, GuiMultiplayer.height - 28, 80, 20, I18n.format("selectServer.edit", new Object[0])));
        this.buttonList.add(this.btnDeleteServer = new GuiButton(2, GuiMultiplayer.width / 2 - 74 - 5 - 2, GuiMultiplayer.height - 28, 80, 20, I18n.format("selectServer.delete", new Object[0])));
        this.buttonList.add(new GuiButton(8, GuiMultiplayer.width / 2 + 4 - 5 + 2, GuiMultiplayer.height - 28, 80, 20, I18n.format("selectServer.refresh", new Object[0])));
        this.buttonList.add(new GuiButton(0, GuiMultiplayer.width / 2 + 4 + 76 + 5 - 2, GuiMultiplayer.height - 28, 80, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(new GuiButton(69, GuiMultiplayer.width - 85, GuiMultiplayer.height / 50, 80, 20, "Direct login"));
        this.buttonList.add(new GuiButton(6969, GuiMultiplayer.width - 85 - 85, GuiMultiplayer.height / 50, 80, 20, "Server tools"));
        this.selectServer(this.serverListSelector.func_148193_k());
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.lanServerList.getWasUpdated()) {
            final List var1 = this.lanServerList.getLanServers();
            this.lanServerList.setWasNotUpdated();
            this.serverListSelector.func_148194_a(var1);
        }
        this.oldServerPinger.pingPendingNetworks();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        if (this.lanServerDetector != null) {
            this.lanServerDetector.interrupt();
            this.lanServerDetector = null;
        }
        this.oldServerPinger.clearPendingNetworks();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.enabled) {
            final GuiListExtended.IGuiListEntry var2 = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
            if (button.id == 2 && var2 instanceof ServerListEntryNormal) {
                final String var3 = ((ServerListEntryNormal)var2).getServerData().serverName;
                if (var3 != null) {
                    this.deletingServer = true;
                    final String var4 = I18n.format("selectServer.deleteQuestion", new Object[0]);
                    final String var5 = "'" + var3 + "' " + I18n.format("selectServer.deleteWarning", new Object[0]);
                    final String var6 = I18n.format("selectServer.deleteButton", new Object[0]);
                    final String var7 = I18n.format("gui.cancel", new Object[0]);
                    final GuiYesNo var8 = new GuiYesNo(this, var4, var5, var6, var7, this.serverListSelector.func_148193_k());
                    GuiMultiplayer.mc.displayGuiScreen(var8);
                }
            }
            else if (button.id == 1) {
                this.connectToSelected();
            }
            else if (button.id == 4) {
                this.directConnect = true;
                GuiMultiplayer.mc.displayGuiScreen(new GuiScreenServerList(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "")));
            }
            else if (button.id == 5) {
                GuiMultiplayer.mc.displayGuiScreen(new GuiBungeeCord(this));
            }
            else if (button.id == 3) {
                this.addingServer = true;
                GuiMultiplayer.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "")));
            }
            else if (button.id == 69) {
                GuiMultiplayer.mc.displayGuiScreen(new GuiAltLogin(this));
            }
            else if (button.id == 6969) {
                GuiMultiplayer.mc.displayGuiScreen(new GuiServers(this));
            }
            else if (button.id == 7 && var2 instanceof ServerListEntryNormal) {
                this.editingServer = true;
                final ServerData var9 = ((ServerListEntryNormal)var2).getServerData();
                (this.selectedServer = new ServerData(var9.serverName, var9.serverIP)).copyFrom(var9);
                GuiMultiplayer.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
            }
            else if (button.id == 0) {
                GuiMultiplayer.mc.displayGuiScreen(GuiMultiplayer.parentScreen);
            }
            else if (button.id == 8) {
                refreshServerList();
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean result, final int id) {
        final GuiListExtended.IGuiListEntry var3 = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
        if (this.deletingServer) {
            this.deletingServer = false;
            if (result && var3 instanceof ServerListEntryNormal) {
                GuiMultiplayer.savedServerList.removeServerData(this.serverListSelector.func_148193_k());
                GuiMultiplayer.savedServerList.saveServerList();
                this.serverListSelector.func_148192_c(-1);
                this.serverListSelector.func_148195_a(GuiMultiplayer.savedServerList);
            }
            GuiMultiplayer.mc.displayGuiScreen(this);
        }
        else if (this.directConnect) {
            this.directConnect = false;
            if (result) {
                this.connectToServer(this.selectedServer);
            }
            else {
                GuiMultiplayer.mc.displayGuiScreen(this);
            }
        }
        else if (this.addingServer) {
            this.addingServer = false;
            if (result) {
                GuiMultiplayer.savedServerList.addServerData(this.selectedServer);
                GuiMultiplayer.savedServerList.saveServerList();
                this.serverListSelector.func_148192_c(-1);
                this.serverListSelector.func_148195_a(GuiMultiplayer.savedServerList);
            }
            GuiMultiplayer.mc.displayGuiScreen(this);
        }
        else if (this.editingServer) {
            this.editingServer = false;
            if (result && var3 instanceof ServerListEntryNormal) {
                final ServerData var4 = ((ServerListEntryNormal)var3).getServerData();
                var4.serverName = this.selectedServer.serverName;
                var4.serverIP = this.selectedServer.serverIP;
                var4.copyFrom(this.selectedServer);
                GuiMultiplayer.savedServerList.saveServerList();
                this.serverListSelector.func_148195_a(GuiMultiplayer.savedServerList);
            }
            GuiMultiplayer.mc.displayGuiScreen(this);
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        final int var3 = this.serverListSelector.func_148193_k();
        final GuiListExtended.IGuiListEntry var4 = (var3 < 0) ? null : this.serverListSelector.getListEntry(var3);
        if (keyCode == 63) {
            refreshServerList();
        }
        else if (var3 >= 0) {
            if (keyCode == 200) {
                if (isShiftKeyDown()) {
                    if (var3 > 0 && var4 instanceof ServerListEntryNormal) {
                        GuiMultiplayer.savedServerList.swapServers(var3, var3 - 1);
                        this.selectServer(this.serverListSelector.func_148193_k() - 1);
                        this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                        this.serverListSelector.func_148195_a(GuiMultiplayer.savedServerList);
                    }
                }
                else if (var3 > 0) {
                    this.selectServer(this.serverListSelector.func_148193_k() - 1);
                    this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                    if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
                        if (this.serverListSelector.func_148193_k() > 0) {
                            this.selectServer(this.serverListSelector.getSize() - 1);
                            this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                        }
                        else {
                            this.selectServer(-1);
                        }
                    }
                }
                else {
                    this.selectServer(-1);
                }
            }
            else if (keyCode == 208) {
                if (isShiftKeyDown()) {
                    if (var3 < GuiMultiplayer.savedServerList.countServers() - 1) {
                        GuiMultiplayer.savedServerList.swapServers(var3, var3 + 1);
                        this.selectServer(var3 + 1);
                        this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                        this.serverListSelector.func_148195_a(GuiMultiplayer.savedServerList);
                    }
                }
                else if (var3 < this.serverListSelector.getSize()) {
                    this.selectServer(this.serverListSelector.func_148193_k() + 1);
                    this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                    if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
                        if (this.serverListSelector.func_148193_k() < this.serverListSelector.getSize() - 1) {
                            this.selectServer(this.serverListSelector.getSize() + 1);
                            this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                        }
                        else {
                            this.selectServer(-1);
                        }
                    }
                }
                else {
                    this.selectServer(-1);
                }
            }
            else if (keyCode != 28 && keyCode != 156) {
                super.keyTyped(typedChar, keyCode);
            }
            else {
                this.actionPerformed(this.buttonList.get(2));
            }
        }
        else {
            super.keyTyped(typedChar, keyCode);
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.field_146812_y = null;
        this.drawDefaultBackground();
        this.serverListSelector.drawScreen(mouseX, mouseY, partialTicks);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title", new Object[0]), GuiMultiplayer.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.field_146812_y != null) {
            this.drawHoveringText(Lists.newArrayList(Splitter.on("\n").split((CharSequence)this.field_146812_y)), mouseX, mouseY);
        }
    }
    
    public void connectToSelected() {
        final GuiListExtended.IGuiListEntry var1 = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
        if (var1 instanceof ServerListEntryNormal) {
            this.connectToServer(((ServerListEntryNormal)var1).getServerData());
        }
        else if (var1 instanceof ServerListEntryLanDetected) {
            final LanServerDetector.LanServer var2 = ((ServerListEntryLanDetected)var1).getLanServer();
            this.connectToServer(new ServerData(var2.getServerMotd(), var2.getServerIpPort()));
        }
    }
    
    private void connectToServer(final ServerData server) {
        GuiMultiplayer.mc.displayGuiScreen(new GuiConnecting(this, GuiMultiplayer.mc, server));
    }
    
    public void selectServer(final int index) {
        this.serverListSelector.func_148192_c(index);
        final GuiListExtended.IGuiListEntry var2 = (index < 0) ? null : this.serverListSelector.getListEntry(index);
        this.btnSelectServer.enabled = false;
        this.btnEditServer.enabled = false;
        this.btnDeleteServer.enabled = false;
        if (var2 != null && !(var2 instanceof ServerListEntryLanScan)) {
            this.btnSelectServer.enabled = true;
            if (var2 instanceof ServerListEntryNormal) {
                this.btnEditServer.enabled = true;
                this.btnDeleteServer.enabled = true;
            }
        }
    }
    
    public OldServerPinger getOldServerPinger() {
        return this.oldServerPinger;
    }
    
    public void func_146793_a(final String p_146793_1_) {
        this.field_146812_y = p_146793_1_;
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.serverListSelector.func_148179_a(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.serverListSelector.func_148181_b(mouseX, mouseY, state);
    }
    
    public ServerList getServerList() {
        return GuiMultiplayer.savedServerList;
    }
    
    public boolean func_175392_a(final ServerListEntryNormal p_175392_1_, final int p_175392_2_) {
        return p_175392_2_ > 0;
    }
    
    public boolean func_175394_b(final ServerListEntryNormal p_175394_1_, final int p_175394_2_) {
        return p_175394_2_ < GuiMultiplayer.savedServerList.countServers() - 1;
    }
    
    public void func_175391_a(final ServerListEntryNormal p_175391_1_, final int p_175391_2_, final boolean p_175391_3_) {
        final int var4 = p_175391_3_ ? 0 : (p_175391_2_ - 1);
        GuiMultiplayer.savedServerList.swapServers(p_175391_2_, var4);
        if (this.serverListSelector.func_148193_k() == p_175391_2_) {
            this.selectServer(var4);
        }
        this.serverListSelector.func_148195_a(GuiMultiplayer.savedServerList);
    }
    
    public void func_175393_b(final ServerListEntryNormal p_175393_1_, final int p_175393_2_, final boolean p_175393_3_) {
        final int var4 = p_175393_3_ ? (GuiMultiplayer.savedServerList.countServers() - 1) : (p_175393_2_ + 1);
        GuiMultiplayer.savedServerList.swapServers(p_175393_2_, var4);
        if (this.serverListSelector.func_148193_k() == p_175393_2_) {
            this.selectServer(var4);
        }
        this.serverListSelector.func_148195_a(GuiMultiplayer.savedServerList);
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
