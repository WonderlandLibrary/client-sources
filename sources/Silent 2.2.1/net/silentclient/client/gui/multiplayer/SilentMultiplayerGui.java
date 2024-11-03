package net.silentclient.client.gui.multiplayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.renderer.GlStateManager;
import net.silentclient.client.Client;
import net.silentclient.client.ServerDataFeature;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.Input;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.minecraft.GuiConnecting;
import net.silentclient.client.gui.multiplayer.components.ServerComponent;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.mixin.ducks.ServerListExt;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.ScrollHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SilentMultiplayerGui extends SilentScreen {
    private final OldServerPinger oldServerPinger = new OldServerPinger();
    private int blockX = 0;
    private int blockY = 0;
    private int blockWidth = 0;
    private int blockHeight = 0;
    private GuiScreen parentScreen;
    private ServerList savedServerList;
    private Button btnEditServer;
    private Button btnSelectServer;
    private Button btnDeleteServer;
    private boolean initialized;
    private ArrayList<ServerComponent> servers = new ArrayList<>();
    private int selectedServer = -1;
    private ScrollHelper scrollHelper = new ScrollHelper();

    public SilentMultiplayerGui(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        super.initGui();
        defaultCursor = false;
        Client.backgroundPanorama.updateWidthHeight(this.width, this.height);
        blockWidth = 250;
        blockHeight = height - 20;
        blockX = (width / 2) - (blockWidth / 2);
        blockY = 10;

        if (!this.initialized) {
            this.initialized = true;
            this.savedServerList = new ServerList(this.mc);
            this.savedServerList.loadServerList();

            this.getServers();
        }

        this.silentInputs.clear();
        this.buttonList.clear();

        this.silentInputs.add(new Input("Search", ""));

        this.createButtons();
    }

    public void createButtons()
    {
        this.buttonList.add(this.btnSelectServer = new Button(1, blockX + 5, blockY + blockHeight - 39, 77, 14, "Join Server"));
        this.buttonList.add(new Button(4, blockX + 5 + 5 + 77, blockY + blockHeight - 39, 77, 14, "Direct Connect"));
        this.buttonList.add(new Button(3, blockX + 5 + 5 + 77 + 5 + 77, blockY + blockHeight - 39, 77, 14, "Add Server"));
        this.buttonList.add(this.btnEditServer = new Button(7, blockX + 5, blockY + blockHeight - 20, 56, 14, "Edit"));
        this.buttonList.add(this.btnDeleteServer = new Button(2, blockX + 5 + 56 + 6, blockY + blockHeight - 20, 56, 14, "Delete"));
        this.buttonList.add(new Button(8, blockX + 5 + 56 + 6 + 56 + 6, blockY + blockHeight - 20, 56, 14, "Refresh"));
        this.buttonList.add(new Button(0, blockX + 5 + 56 + 6 + 56 + 6 + 56 + 5, blockY + blockHeight - 20, 56, 14, "Cancel"));
        this.selectServer(-1);
    }

    public void getServers() {
        servers.clear();
        for(ServerDataFeature serverDataFeature : Client.getInstance().getFeaturedServers()) {
            serverDataFeature.resetData();
            servers.add(new ServerComponent(this, serverDataFeature));
        }
        for (int i = 0; i < savedServerList.countServers(); ++i)
        {
            ServerData serverData = savedServerList.getServerData(i);

            servers.add(new ServerComponent(this, serverData));
        }
    }

    public void selectServer(int index)
    {
        this.selectedServer = index;
        this.btnSelectServer.enabled = false;
        this.btnEditServer.enabled = false;
        this.btnDeleteServer.enabled = false;

        if(index != -1) {
            ServerComponent serverComponent = this.servers.get(index);

            if(serverComponent != null) {
                this.btnSelectServer.enabled = true;
                if(!(serverComponent.getServer() instanceof ServerDataFeature)) {
                    this.btnEditServer.enabled = true;
                    this.btnDeleteServer.enabled = true;
                }
            } else {
                Client.logger.info("Server " + index + " not found!");
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        GlStateManager.disableAlpha();
        Client.backgroundPanorama.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();
        if(Client.getInstance().getGlobalSettings().isLite()) {
            this.drawGradientRect(0, 0, this.width, this.height, new Color(0, 0, 0, 127).getRGB(), new Color(0, 0, 0, 200).getRGB());
        } else {
            this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        }

        RenderUtils.drawRect(blockX, blockY, blockWidth, blockHeight, Theme.backgroundColor().getRGB());

        Client.getInstance().getSilentFontRenderer().drawCenteredString("Play Multiplayer", blockX + blockWidth / 2, blockY + 3, 16, SilentFontRenderer.FontType.TITLE);

        this.silentInputs.get(0).render(mouseX, mouseY, blockX + 5, blockY + 3 + 16 + 5, blockWidth - 10, true);
        scrollHelper.setStep(5);
        scrollHelper.setElementsHeight(servers.size() * 38);
        scrollHelper.setMaxScroll(blockHeight - 43 - 41);
        scrollHelper.setSpeed(200);
        float serverY = blockY + 43 + scrollHelper.getScroll();
        trimContentStart();
        int serverIndex = -1;
        for (ServerComponent serverComponent : servers)
        {
            serverIndex += 1;
            if((!this.silentInputs.get(0).getValue().trim().equals("") && !serverComponent.getServer().serverName.toLowerCase().contains(this.silentInputs.get(0).getValue().trim().toLowerCase()) && !serverComponent.getServer().serverIP.toLowerCase().contains(this.silentInputs.get(0).getValue().trim().toLowerCase()))) {
                continue;
            }
            if(MouseUtils.isInside(blockX + 5, (int) serverY, blockX + 5, blockY + 43, blockWidth - 10, blockHeight - 43 - 39) || MouseUtils.isInside(blockX + 5, (int) serverY + 38, blockX + 5, blockY + 43, blockWidth - 10, blockHeight - 43 - 39)) {
                MouseCursorHandler.CursorType cursor = serverComponent.draw(serverIndex, mouseX, mouseY, blockX + 5, serverY, serverIndex == selectedServer);
                if(cursor != null) {
                    cursorType = cursor;
                }
            }
            serverY += 38;
        }
        trimContentEnd();

        super.drawScreen(mouseX, mouseY, partialTicks);

        Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);
    }

    public void connectToSelected() {
        ServerComponent server = servers.get(selectedServer);
        if(server != null) {
            this.connect(new ServerData(server.getServer().serverName, server.getServer().serverIP, false));
        }
    }

    public void connect(ServerData serverData) {
        if(this.mc.theWorld != null) {
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld(null);
        }
        this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, serverData));
    }

    public boolean canUpSwap(int index)
    {
        return index > Client.getInstance().getFeaturedServers().size();
    }

    public boolean canDownSwap(int index)
    {
        return (index - Client.getInstance().getFeaturedServers().size()) < this.savedServerList.countServers() - 1;
    }

    public void swapUp(int index, boolean fullSwap)
    {
        index = index - (Client.getInstance().getFeaturedServers().size());
        int i = fullSwap ? 0 : index - 1;
        this.savedServerList.swapServers(index, i);

        if (this.selectedServer == index)
        {
            this.selectServer(i);
        }

        getServers();
    }

    public void swapDown(int index, boolean fullSwap)
    {
        index = index - (Client.getInstance().getFeaturedServers().size());
        int i = fullSwap ? this.savedServerList.countServers() - 1 : index + 1;
        this.savedServerList.swapServers(index, i);

        if (this.selectedServer == index)
        {
            this.selectServer(i);
        }

        getServers();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(parentScreen);
                break;
            case 1:
                this.connectToSelected();
                break;
            case 2:
                if(this.selectedServer != -1) {
                    this.savedServerList.removeServerData(this.selectedServer - Client.getInstance().getFeaturedServers().size());
                    this.savedServerList.saveServerList();
                    this.selectServer(-1);
                    this.refreshServerList();
                }
                break;
            case 3:
                mc.displayGuiScreen(new AddServerGui(this, new ServerData("Minecraft Server", "", false), false));
                break;
            case 4:
                mc.displayGuiScreen(new DirectConnectGui(this));
                break;
            case 7:
                ServerComponent serverComponent = this.servers.get(selectedServer);
                if(serverComponent != null && serverComponent.getServer() != null) {
                    mc.displayGuiScreen(new AddServerGui(this, serverComponent.getServer(), true, selectedServer - Client.getInstance().getFeaturedServers().size()));
                }
                break;
            case 8:
                this.refreshServerList();
                break;
        }
    }

    public void editServer(ServerData serverData, int index) {
        ServerList newServerList = new ServerList(mc);
        ((ServerListExt) newServerList).clearServerList();
        Client.logger.info("Updating server list...");
        for (int i = 0; i < savedServerList.countServers(); ++i)
        {
            if(i == index) {
                Client.logger.info("Editing server...");
                newServerList.addServerData(serverData);
            } else {
                Client.logger.info("Adding server...");
                newServerList.addServerData(savedServerList.getServerData(i));
            }
        }

        Client.logger.info("Saving server list...");
        newServerList.saveServerList();
        getServers();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0)
        {
            for (int i = 0; i < this.buttonList.size(); ++i)
            {
                GuiButton guibutton = (GuiButton)this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY))
                {
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                    return;
                }
            }
        }
        this.silentInputs.get(0).onClick(mouseX, mouseY, blockX + 5, blockY + 3 + 16 + 5, blockWidth - 10, true);
        float serverY = blockY + 43 + scrollHelper.getScroll();
        int serverIndex = -1;
        boolean isReset = true;
        for (ServerComponent serverComponent : servers)
        {
            serverIndex += 1;
            if((!this.silentInputs.get(0).getValue().trim().equals("") && !serverComponent.getServer().serverName.toLowerCase().contains(this.silentInputs.get(0).getValue().trim().toLowerCase()) && !serverComponent.getServer().serverIP.toLowerCase().contains(this.silentInputs.get(0).getValue().trim().toLowerCase()))) {
                continue;
            }
            if((MouseUtils.isInside(blockX + 5, (int) serverY, blockX + 5, blockY + 43, blockWidth - 10, blockHeight - 43 - 39) || MouseUtils.isInside(blockX + 5, (int) serverY + 38, blockX + 5, blockY + 43, blockWidth - 10, blockHeight - 43 - 39)) && isReset) {
                isReset = !serverComponent.mouseClicked(serverIndex, mouseX, mouseY, blockX + 5, serverY, serverIndex == selectedServer);
                if(!isReset) {
                    break;
                }
            }
            serverY += 38;
        }

        if(isReset) {
            this.selectServer(-1);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.silentInputs.get(0).onKeyTyped(typedChar, keyCode);
        if(this.silentInputs.get(0).isFocused()) {
            scrollHelper.resetScroll();
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        Client.backgroundPanorama.tickPanorama();
    }

    public void trimContentStart() {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution r = new ScaledResolution(Minecraft.getMinecraft());
        int s = r.getScaleFactor();
        int listHeight = blockHeight - 43 - 41;
        int translatedY = r.getScaledHeight() - blockY - 43 - listHeight;
        GL11.glScissor(0 * s, translatedY * s, width * s, listHeight * s);
    }

    public static void trimContentEnd() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    public void refreshServerList()
    {
        this.mc.displayGuiScreen(new SilentMultiplayerGui(this.parentScreen));
    }

    public OldServerPinger getOldServerPinger() {
        return oldServerPinger;
    }

    public ServerList getServerList() {
        return savedServerList;
    }

    public void setParentScreen(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }
}
