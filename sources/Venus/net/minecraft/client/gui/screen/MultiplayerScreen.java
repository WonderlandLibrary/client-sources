/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.venusfr;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.AddServerScreen;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.ConnectingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ServerListScreen;
import net.minecraft.client.gui.screen.ServerSelectionList;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.network.ServerPinger;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import via.VersionSelectScreen;

public class MultiplayerScreen
extends Screen {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ServerPinger oldServerPinger = new ServerPinger();
    private final Screen parentScreen;
    protected ServerSelectionList serverListSelector;
    private ServerList savedServerList;
    private Button btnEditServer;
    private Button btnSelectServer;
    private Button btnDeleteServer;
    private List<ITextComponent> hoveringText;
    private ServerData selectedServer;
    private LanServerDetector.LanServerList lanServerList;
    private LanServerDetector.LanServerFindThread lanServerDetector;
    private boolean initialized;
    private final VersionSelectScreen viaScreen = venusfr.getInstance().getViaMCP().getViaScreen();

    public MultiplayerScreen(Screen screen) {
        super(new TranslationTextComponent("multiplayer.title"));
        this.parentScreen = screen;
    }

    @Override
    protected void init() {
        super.init();
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        if (this.initialized) {
            this.serverListSelector.updateSize(this.width, this.height, 32, this.height - 64);
        } else {
            this.initialized = true;
            this.savedServerList = new ServerList(this.minecraft);
            this.savedServerList.loadServerList();
            this.lanServerList = new LanServerDetector.LanServerList();
            try {
                this.lanServerDetector = new LanServerDetector.LanServerFindThread(this.lanServerList);
                this.lanServerDetector.start();
            } catch (Exception exception) {
                LOGGER.warn("Unable to start LAN server detection: {}", (Object)exception.getMessage());
            }
            this.serverListSelector = new ServerSelectionList(this, this.minecraft, this.width, this.height, 32, this.height - 64, 36);
            this.serverListSelector.updateOnlineServers(this.savedServerList);
        }
        this.addButton(venusfr.getInstance().getViaMCP().viaScreen);
        this.children.add(this.serverListSelector);
        this.btnSelectServer = this.addButton(new Button(this.width / 2 - 154, this.height - 52, 100, 20, new TranslationTextComponent("selectServer.select"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 50, this.height - 52, 100, 20, new TranslationTextComponent("selectServer.direct"), this::lambda$init$1));
        this.addButton(new Button(this.width / 2 + 4 + 50, this.height - 52, 100, 20, new TranslationTextComponent("selectServer.add"), this::lambda$init$2));
        this.btnEditServer = this.addButton(new Button(this.width / 2 - 154, this.height - 28, 70, 20, new TranslationTextComponent("selectServer.edit"), this::lambda$init$3));
        this.btnDeleteServer = this.addButton(new Button(this.width / 2 - 74, this.height - 28, 70, 20, new TranslationTextComponent("selectServer.delete"), this::lambda$init$4));
        this.addButton(new Button(this.width / 2 + 4, this.height - 28, 70, 20, new TranslationTextComponent("selectServer.refresh"), this::lambda$init$5));
        this.addButton(new Button(this.width / 2 + 4 + 76, this.height - 28, 75, 20, DialogTexts.GUI_CANCEL, this::lambda$init$6));
        this.func_214295_b();
    }

    @Override
    public void tick() {
        super.tick();
        venusfr.getInstance().getViaMCP().viaScreen.tick();
        if (this.lanServerList.getWasUpdated()) {
            List<LanServerInfo> list = this.lanServerList.getLanServers();
            this.lanServerList.setWasNotUpdated();
            this.serverListSelector.updateNetworkServers(list);
        }
        this.oldServerPinger.pingPendingNetworks();
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        if (this.lanServerDetector != null) {
            this.lanServerDetector.interrupt();
            this.lanServerDetector = null;
        }
        this.oldServerPinger.clearPendingNetworks();
    }

    private void refreshServerList() {
        this.minecraft.displayGuiScreen(new MultiplayerScreen(this.parentScreen));
    }

    private void func_214285_a(boolean bl) {
        ServerSelectionList.Entry entry = (ServerSelectionList.Entry)this.serverListSelector.getSelected();
        if (bl && entry instanceof ServerSelectionList.NormalEntry) {
            this.savedServerList.func_217506_a(((ServerSelectionList.NormalEntry)entry).getServerData());
            this.savedServerList.saveServerList();
            this.serverListSelector.setSelected((ServerSelectionList.Entry)null);
            this.serverListSelector.updateOnlineServers(this.savedServerList);
        }
        this.minecraft.displayGuiScreen(this);
    }

    private void func_214292_b(boolean bl) {
        ServerSelectionList.Entry entry = (ServerSelectionList.Entry)this.serverListSelector.getSelected();
        if (bl && entry instanceof ServerSelectionList.NormalEntry) {
            ServerData serverData = ((ServerSelectionList.NormalEntry)entry).getServerData();
            serverData.serverName = this.selectedServer.serverName;
            serverData.serverIP = this.selectedServer.serverIP;
            serverData.copyFrom(this.selectedServer);
            this.savedServerList.saveServerList();
            this.serverListSelector.updateOnlineServers(this.savedServerList);
        }
        this.minecraft.displayGuiScreen(this);
    }

    private void func_214284_c(boolean bl) {
        if (bl) {
            this.savedServerList.addServerData(this.selectedServer);
            this.savedServerList.saveServerList();
            this.serverListSelector.setSelected((ServerSelectionList.Entry)null);
            this.serverListSelector.updateOnlineServers(this.savedServerList);
        }
        this.minecraft.displayGuiScreen(this);
    }

    private void func_214290_d(boolean bl) {
        if (bl) {
            this.connectToServer(this.selectedServer);
        } else {
            this.minecraft.displayGuiScreen(this);
        }
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (super.keyPressed(n, n2, n3)) {
            return false;
        }
        if (n == 294) {
            this.refreshServerList();
            return false;
        }
        if (this.serverListSelector.getSelected() != null) {
            if (n != 257 && n != 335) {
                return this.serverListSelector.keyPressed(n, n2, n3);
            }
            this.connectToSelected();
            return false;
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        this.viaScreen.mouseClicked(d, d2, n);
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        this.hoveringText = null;
        this.renderBackground(matrixStack);
        this.serverListSelector.render(matrixStack, n, n2, f);
        MultiplayerScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
        if (this.hoveringText != null) {
            this.func_243308_b(matrixStack, this.hoveringText, n, n2);
        }
        this.viaScreen.render(matrixStack, n, n2, f);
    }

    public void connectToSelected() {
        ServerSelectionList.Entry entry = (ServerSelectionList.Entry)this.serverListSelector.getSelected();
        if (entry instanceof ServerSelectionList.NormalEntry) {
            this.connectToServer(((ServerSelectionList.NormalEntry)entry).getServerData());
        } else if (entry instanceof ServerSelectionList.LanDetectedEntry) {
            LanServerInfo lanServerInfo = ((ServerSelectionList.LanDetectedEntry)entry).getServerData();
            this.connectToServer(new ServerData(lanServerInfo.getServerMotd(), lanServerInfo.getServerIpPort(), true));
        }
    }

    private void connectToServer(ServerData serverData) {
        this.minecraft.displayGuiScreen(new ConnectingScreen(this, this.minecraft, serverData));
    }

    public void func_214287_a(ServerSelectionList.Entry entry) {
        this.serverListSelector.setSelected(entry);
        this.func_214295_b();
    }

    protected void func_214295_b() {
        this.btnSelectServer.active = false;
        this.btnEditServer.active = false;
        this.btnDeleteServer.active = false;
        ServerSelectionList.Entry entry = (ServerSelectionList.Entry)this.serverListSelector.getSelected();
        if (entry != null && !(entry instanceof ServerSelectionList.LanScanEntry)) {
            this.btnSelectServer.active = true;
            if (entry instanceof ServerSelectionList.NormalEntry) {
                this.btnEditServer.active = true;
                this.btnDeleteServer.active = true;
            }
        }
    }

    public ServerPinger getOldServerPinger() {
        return this.oldServerPinger;
    }

    public void func_238854_b_(List<ITextComponent> list) {
        this.hoveringText = list;
    }

    public ServerList getServerList() {
        return this.savedServerList;
    }

    private void lambda$init$6(Button button) {
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    private void lambda$init$5(Button button) {
        this.refreshServerList();
    }

    private void lambda$init$4(Button button) {
        String string;
        ServerSelectionList.Entry entry = (ServerSelectionList.Entry)this.serverListSelector.getSelected();
        if (entry instanceof ServerSelectionList.NormalEntry && (string = ((ServerSelectionList.NormalEntry)entry).getServerData().serverName) != null) {
            TranslationTextComponent translationTextComponent = new TranslationTextComponent("selectServer.deleteQuestion");
            TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("selectServer.deleteWarning", string);
            TranslationTextComponent translationTextComponent3 = new TranslationTextComponent("selectServer.deleteButton");
            ITextComponent iTextComponent = DialogTexts.GUI_CANCEL;
            this.minecraft.displayGuiScreen(new ConfirmScreen(this::func_214285_a, translationTextComponent, translationTextComponent2, translationTextComponent3, iTextComponent));
        }
    }

    private void lambda$init$3(Button button) {
        ServerSelectionList.Entry entry = (ServerSelectionList.Entry)this.serverListSelector.getSelected();
        if (entry instanceof ServerSelectionList.NormalEntry) {
            ServerData serverData = ((ServerSelectionList.NormalEntry)entry).getServerData();
            this.selectedServer = new ServerData(serverData.serverName, serverData.serverIP, false);
            this.selectedServer.copyFrom(serverData);
            this.minecraft.displayGuiScreen(new AddServerScreen(this, this::func_214292_b, this.selectedServer));
        }
    }

    private void lambda$init$2(Button button) {
        this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false);
        this.minecraft.displayGuiScreen(new AddServerScreen(this, this::func_214284_c, this.selectedServer));
    }

    private void lambda$init$1(Button button) {
        this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false);
        this.minecraft.displayGuiScreen(new ServerListScreen(this, this::func_214290_d, this.selectedServer));
    }

    private void lambda$init$0(Button button) {
        this.connectToSelected();
    }
}

