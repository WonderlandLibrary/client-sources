package digital.rbq.gui.plugingui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import digital.rbq.Lycoris;
import digital.rbq.addon.AddonManager;
import digital.rbq.addon.LycorisAPI;
import digital.rbq.addon.api.LycorisAddon;
import digital.rbq.gui.clickgui.classic.RenderUtil;
import digital.rbq.gui.fontRenderer.FontManager;
import digital.rbq.utility.ChatUtils;

import java.io.IOException;
import java.util.ArrayList;

public class GuiPluginMgr extends GuiScreen {
    public ScaledResolution res;
    public GuiScreen lastScreen;
    public GuiPluginMgr(GuiScreen screen) {
        this.lastScreen = screen;
    }

    public ArrayList<PluginButton> buttons = new ArrayList<>();
    public PluginScrollableList list;
    public LycorisAddon selectedAPI = null;
    public boolean needToUpdateList = false;

    @Override
    public void initGui() {
        res = new ScaledResolution(mc);
        this.buttons.clear();
        //Buttons

        this.buttons.add(new PluginButton(0, "Back", res.getScaledWidth() / 2f - 115, res.getScaledHeight() - 25, 235, 20, button -> mc.displayGuiScreen(lastScreen), () -> true));

        this.buttons.add(new PluginButton(0, "Load", res.getScaledWidth() / 2f - 115, res.getScaledHeight() - 50, 75, 20, button -> {
            if (Lycoris.INSTANCE.api.getAddonManager().getEnabledLycorisAddonList().contains(this.selectedAPI)) {
                ChatUtils.sendErrorToPlayer(String.format("[Lycoris API] \2477%s has already loaded!", selectedAPI.getAPIName()));
            } else {
                AddonManager.getEnabledAddonsName().add(selectedAPI.getAPIName());
                LycorisAPI.FLUX_API.saveAddons();
                Lycoris.INSTANCE.api = new LycorisAPI();
                needToUpdateList = true;
                ChatUtils.sendOutputMessage(String.format("[Lycoris API] \2477%s successfully loaded!", selectedAPI.getAPIName()));
                this.selectedAPI = null;
            }
        }, () -> this.selectedAPI != null && !Lycoris.INSTANCE.api.getAddonManager().getEnabledLycorisAddonList().contains(this.selectedAPI)));

        this.buttons.add(new PluginButton(0, "Unload", res.getScaledWidth() / 2f - 35, res.getScaledHeight() - 50, 75, 20, button -> {
            if (!Lycoris.INSTANCE.api.getAddonManager().getEnabledLycorisAddonList().contains(selectedAPI)) {
                ChatUtils.sendErrorToPlayer(String.format("[Lycoris API] \2477%s already unloaded!", selectedAPI.getAPIName()));
            } else {
                AddonManager.getEnabledAddonsName().remove(selectedAPI.getAPIName());
                LycorisAPI.FLUX_API.saveAddons();
                AddonManager.reload();
                needToUpdateList = true;
                ChatUtils.sendOutputMessage(String.format("[Lycoris API] \2477%s successfully unloaded!", selectedAPI.getAPIName()));

                this.selectedAPI = null;
            }
        }, () -> this.selectedAPI != null && Lycoris.INSTANCE.api.getAddonManager().getEnabledLycorisAddonList().contains(this.selectedAPI)));

        this.buttons.add(new PluginButton(0, "Reload all", res.getScaledWidth() / 2f + 45, res.getScaledHeight() - 50, 75, 20, button -> {
            AddonManager.reload();
            needToUpdateList = true;
        }, () -> true));

        //List handler
        this.list = new PluginScrollableList(this, Lycoris.INSTANCE.api.getAddonManager().getLycorisAddonList(), selected -> this.selectedAPI = selected);
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(mc.theWorld != null) {
            RenderUtil.drawRect(0, 0, width, height, 0xdd36393f);
        } else {
            RenderUtil.drawRect(0, 0, width, height, 0xff36393f);
        }

        FontManager.sans18.drawCenteredString("Lycoris Addon", res.getScaledWidth() / 2f, 20, 0xffffffff);

        if(this.buttons.size() > 0) {
            for(PluginButton button : this.buttons) {
                button.drawButton(mouseX, mouseY);
            }
        }

        if(this.list != null) {
            this.list.draw(res.getScaledWidth() / 2f - 115, 40, 235, res.getScaledHeight() - 100, mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(this.buttons.size() > 0) {
            for(PluginButton button : this.buttons) {
                button.onClick(mouseButton);
            }
        }

        if(this.list != null) {
            this.list.onClick(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        res = new ScaledResolution(mc);
        super.updateScreen();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
