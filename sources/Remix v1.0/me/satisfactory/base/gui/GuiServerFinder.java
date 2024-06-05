package me.satisfactory.base.gui;

import net.minecraft.client.multiplayer.*;
import java.awt.*;
import net.minecraft.client.gui.*;

public class GuiServerFinder extends GuiScreen
{
    private GuiServers gui;
    private String renderString;
    private String status;
    private String serverspinged;
    private GuiTextField field_1;
    
    public GuiServerFinder(final GuiServers gui) {
        this.gui = gui;
        ServerFinderHelper.initOrStop();
        this.updateString("");
        this.updateStatus(false);
        this.serverspinged = "0";
    }
    
    static void access$0(final GuiServerFinder guiServers_Scanner, final String string) {
        guiServers_Scanner.renderString = string;
    }
    
    @Override
    public void initGui() {
        (this.field_1 = new GuiTextField(100, GuiServerFinder.mc.fontRendererObj, GuiServerFinder.width / 2 - 50, GuiServerFinder.height / 2 - 25, 100, 20)).setText("ServerIp");
        this.field_1.setFocused(true);
        this.buttonList.add(new GuiButton(1, GuiServerFinder.width / 2 - 45, GuiServerFinder.height / 2 + 45, 40, 20, "§2Scan"));
        this.buttonList.add(new GuiButton(0, GuiServerFinder.width / 2 - 20, GuiServerFinder.height / 2 + 70, 40, 20, "§1Back"));
        this.buttonList.add(new GuiButton(3, GuiServerFinder.width / 2 + 5, GuiServerFinder.height / 2 + 45, 40, 20, "§cStop"));
        super.initGui();
    }
    
    @Override
    public void updateScreen() {
        if (ServerFinderHelper.isScanning()) {
            this.serverspinged = String.valueOf(ServerFinderHelper.ipsScanned);
            this.updateStatus(true);
        }
        if (ServerFinderHelper.isScannComplet()) {
            this.updateStatus(false);
            for (int i = 0; i != ServerFinderHelper.getScannedIps().size(); ++i) {
                final ServerData s = ServerFinderHelper.getScannedIps().get(i);
                GuiMultiplayer.savedServerList.servers.add(new ServerData("§bServer #" + (i + 1) + " §8| §c" + s.serverIP + "§r", s.serverIP));
            }
            GuiMultiplayer.savedServerList.saveServerList();
            GuiMultiplayer.savedServerList.loadServerList();
            GuiServerFinder.mc.displayGuiScreen(GuiServers.gui);
            GuiMultiplayer.refreshServerList();
        }
        super.updateScreen();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 3: {
                ServerFinderHelper.ips.clear();
                for (int i = 0; i != ServerFinderHelper.getScannedIps().size(); ++i) {
                    final ServerData s = ServerFinderHelper.getScannedIps().get(i);
                    GuiMultiplayer.savedServerList.servers.add(new ServerData("§bServer #" + (i + 1) + " §8| §c" + s.serverIP + "§r", s.serverIP));
                }
                ServerFinderHelper.initOrStop();
                GuiMultiplayer.savedServerList.saveServerList();
                GuiMultiplayer.savedServerList.loadServerList();
                GuiServerFinder.mc.displayGuiScreen(GuiServers.gui);
                GuiMultiplayer.refreshServerList();
                break;
            }
            case 1: {
                if (this.field_1.getText().trim().isEmpty()) {
                    this.updateString("§cPlease enter an IP!");
                    this.updateStatus(false);
                }
                else if (ServerFinderHelper.start(this.field_1.getText().trim())) {
                    this.updateString("Started");
                    this.updateStatus(true);
                }
                else {
                    this.updateString("§cInvalid IP/Domain!");
                    this.updateStatus(false);
                }
                this.serverspinged = "0";
                break;
            }
            case 0: {
                ServerFinderHelper.ips.clear();
                for (int i = 0; i != ServerFinderHelper.getScannedIps().size(); ++i) {
                    final ServerData s = ServerFinderHelper.getScannedIps().get(i);
                    GuiMultiplayer.savedServerList.servers.add(new ServerData("§bServer #" + (i + 1) + " §8| §c" + s.serverIP + "§r", s.serverIP));
                }
                ServerFinderHelper.initOrStop();
                GuiMultiplayer.savedServerList.saveServerList();
                GuiMultiplayer.savedServerList.loadServerList();
                GuiServerFinder.mc.displayGuiScreen(this.gui);
                GuiMultiplayer.refreshServerList();
                break;
            }
        }
        super.actionPerformed(button);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.field_1.drawTextBox();
        Gui.drawCenteredString(GuiServerFinder.mc.fontRendererObj, "Working: " + ServerFinderHelper.ipsWorking, GuiServerFinder.width / 2, GuiServerFinder.height / 2 - 80, Color.GREEN.getRGB());
        Gui.drawCenteredString(GuiServerFinder.mc.fontRendererObj, "Pinged: " + this.serverspinged, GuiServerFinder.width / 2, GuiServerFinder.height / 2 - 70, Color.GREEN.getRGB());
        Gui.drawCenteredString(GuiServerFinder.mc.fontRendererObj, "Status: " + this.status, GuiServerFinder.width / 2, GuiServerFinder.height / 2 - 60, Color.GREEN.getRGB());
        Gui.drawCenteredString(GuiServerFinder.mc.fontRendererObj, this.renderString, GuiServerFinder.width / 2, GuiServerFinder.height / 2 - 50, Color.GREEN.getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.field_1.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        this.field_1.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }
    
    public void updateStatus(final boolean online) {
        this.status = (online ? "§aOnline" : "§cOffline");
    }
    
    public void updateString(final String s) {
        this.renderString = s;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000L);
                    GuiServerFinder.access$0(GuiServerFinder.this, "");
                }
                catch (InterruptedException ex) {}
            }
        }).start();
    }
}
