package me.satisfactory.base.gui;

import me.satisfactory.base.gui.Buttons.*;
import java.awt.datatransfer.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;
import java.util.*;
import java.awt.*;
import net.minecraft.client.gui.*;

public class GuiServers extends GuiScreen
{
    public static GuiMultiplayer gui;
    private Checkbox box_1;
    private Checkbox box_2;
    private Checkbox box_3;
    private Checkbox box_4;
    private GuiTextField field_1;
    private String renderString;
    
    public GuiServers(final GuiMultiplayer gui) {
        this.renderString = "";
        GuiServers.gui = gui;
    }
    
    public static void copyString(final String toCopy) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(toCopy), null);
    }
    
    @Override
    public void initGui() {
        this.field_1 = new GuiTextField(100, GuiServers.mc.fontRendererObj, GuiServers.width / 2 + 15, GuiServers.height / 2 - 100, 105, 10);
        this.box_1 = new Checkbox(GuiServers.width / 2 - 25, GuiServers.height / 2 - 100, 10, 10, false);
        this.box_2 = new Checkbox(GuiServers.width / 2 - 25, GuiServers.height / 2 - 80, 10, 10, false);
        this.box_3 = new Checkbox(GuiServers.width / 2 - 25, GuiServers.height / 2 - 60, 10, 10, false);
        this.box_4 = new Checkbox(GuiServers.width / 2 - 25, GuiServers.height / 2 - 40, 10, 10, false);
        this.buttonList.add(new GuiButton(1, GuiServers.width / 2 - 100, GuiServers.height / 2 - 10, 60, 20, "Remove"));
        this.buttonList.add(new GuiButton(4, GuiServers.width / 2 + 25, GuiServers.height / 2 - 80, 85, 20, "Find Player"));
        this.buttonList.add(new GuiButton(5, GuiServers.width / 2 + 25, GuiServers.height / 2, 85, 20, "Copy"));
        this.buttonList.add(new GuiButton(2, GuiServers.width / 2 - 50, GuiServers.height / 2 + 80, 100, 20, "Back"));
        this.buttonList.add(new GuiButton(3, GuiServers.width / 2 - 50, GuiServers.height / 2 + 55, 100, 20, "Portscan"));
        this.buttonList.add(new GuiButton(6, GuiServers.width / 2 - 50, GuiServers.height / 2 + 30, 100, 20, "ServerScanner"));
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 6: {
                GuiServers.mc.displayGuiScreen(new GuiServerFinder(this));
                break;
            }
            case 5: {
                copyString(this.renderString);
                break;
            }
            case 4: {
                if (this.field_1.getText().trim().isEmpty()) {
                    return;
                }
                boolean hasFound = false;
                final GuiMultiplayer gui = GuiServers.gui;
                for (final ServerData sd : GuiMultiplayer.savedServerList.servers) {
                    try {
                        final String[] arrstring;
                        final String[] players = arrstring = sd.playerList.split("\n");
                        for (final String s : arrstring) {
                            if (s.equalsIgnoreCase(this.field_1.getText().trim())) {
                                hasFound = true;
                                this.renderString = sd.serverIP;
                            }
                        }
                    }
                    catch (Exception ex) {}
                }
                if (hasFound) {
                    break;
                }
                this.renderString = "§cNicht gefunden!";
                break;
            }
            case 1: {
                final ArrayList<ServerData> filterdServers = new ArrayList<ServerData>();
                final GuiMultiplayer gui2 = GuiServers.gui;
                for (final ServerData data : GuiMultiplayer.savedServerList.servers) {
                    boolean mayAdd = true;
                    if (this.box_1.isChecked() && data.pingToServer <= 0L) {
                        mayAdd = false;
                    }
                    if (this.box_2.isChecked() && data.version != 47) {
                        mayAdd = false;
                    }
                    try {
                        if (this.box_3.isChecked() && data.populationInfo.split("/").length == 2 && Integer.valueOf(StringUtils.stripControlCodes(data.populationInfo.split("/")[0])) <= 0) {
                            mayAdd = false;
                        }
                    }
                    catch (Exception ex2) {}
                    if (this.box_4.isChecked()) {
                        mayAdd = false;
                    }
                    if (!mayAdd) {
                        continue;
                    }
                    filterdServers.add(data);
                }
                final GuiMultiplayer gui3 = GuiServers.gui;
                GuiMultiplayer.savedServerList.servers.clear();
                for (int i = 0; i != filterdServers.size(); ++i) {
                    final ServerData d = filterdServers.get(i);
                    d.serverName = "§bServer #" + (i + 1) + " §8| §c" + d.serverIP + "§r";
                    final GuiMultiplayer gui4 = GuiServers.gui;
                    GuiMultiplayer.savedServerList.servers.add(d);
                }
                final GuiMultiplayer gui5 = GuiServers.gui;
                GuiMultiplayer.savedServerList.saveServerList();
                final GuiMultiplayer gui6 = GuiServers.gui;
                GuiMultiplayer.savedServerList.loadServerList();
                GuiServers.mc.displayGuiScreen(GuiServers.gui);
                final GuiMultiplayer gui7 = GuiServers.gui;
                GuiMultiplayer.refreshServerList();
                break;
            }
            case 2: {
                GuiServers.mc.displayGuiScreen(GuiServers.gui);
                break;
            }
            case 3: {
                GuiServers.mc.displayGuiScreen(new GuiPortscan(this));
                break;
            }
        }
        super.actionPerformed(button);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.box_1.drawBoxString("No Connection", Color.green);
        this.box_2.drawBoxString("Wrong version", Color.green);
        this.box_3.drawBoxString("No Players", Color.green);
        this.box_4.drawBoxString("All", Color.green);
        this.box_1.drawScreen(Color.black);
        this.box_2.drawScreen(Color.black);
        this.box_3.drawScreen(Color.black);
        this.box_4.drawScreen(Color.black);
        GuiScreen.drawOutline(Color.black.getRGB(), GuiServers.width / 2 - 130, GuiServers.height / 2 - 110, GuiServers.width / 2 - 5, GuiServers.height / 2 + 20);
        GuiScreen.drawOutline(Color.black.getRGB(), GuiServers.width / 2 + 5, GuiServers.height / 2 - 110, GuiServers.width / 2 + 130, GuiServers.height / 2 + 20);
        Gui.drawCenteredString(GuiServers.mc.fontRendererObj, this.renderString, GuiServers.width / 2 + 70, GuiServers.height / 2 - 20, Color.green.getRGB());
        this.field_1.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.box_1.mouseClicked(mouseX, mouseY);
        this.box_2.mouseClicked(mouseX, mouseY);
        this.box_3.mouseClicked(mouseX, mouseY);
        this.box_4.mouseClicked(mouseX, mouseY);
        this.field_1.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        this.field_1.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }
}
