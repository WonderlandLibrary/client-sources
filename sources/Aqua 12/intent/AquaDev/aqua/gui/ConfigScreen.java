// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.gui;

import java.net.URLConnection;
import intent.AquaDev.aqua.utils.ChatUtil;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.util.Objects;
import java.io.File;
import intent.AquaDev.aqua.utils.FileUtil;
import net.minecraft.client.gui.GuiButton;
import java.io.IOException;
import intent.AquaDev.aqua.config.ConfigOnline;
import java.util.Iterator;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Mouse;
import intent.AquaDev.aqua.modules.visual.ShaderMultiplier;
import intent.AquaDev.aqua.utils.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.config.Config;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class ConfigScreen extends GuiScreen
{
    private int scrollAdd;
    private ArrayList<Config> configList;
    private ArrayList<String> configListName;
    private ArrayList<String> onlineConfigList;
    private ArrayList<String> onlineConfigDate;
    private int configHeight;
    private boolean onlineConfigs;
    public static boolean loadVisuals;
    
    public ConfigScreen() {
        this.scrollAdd = 0;
        this.configList = new ArrayList<Config>();
        this.configListName = new ArrayList<String>();
        this.onlineConfigList = new ArrayList<String>();
        this.onlineConfigDate = new ArrayList<String>();
        this.configHeight = 0;
        this.onlineConfigs = false;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final int hudColor = Aqua.setmgr.getSetting("HUDColor").getColor();
        final ScaledResolution sr = new ScaledResolution(this.mc);
        if (Aqua.moduleManager.getModuleByName("ShaderMultiplier").isToggled()) {
            ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRectSmooth(sr.getScaledWidth() / 2.0f - 162.0f, 118.0, 300.0, 244.0, 3.0, new Color(Aqua.setmgr.getSetting("HUDColor").getColor())), false);
        }
        RenderUtil.drawRoundedRectSmooth(sr.getScaledWidth() / 2.0f - 162.0f, 118.0, 300.0, 244.0, 3.0, new Color(Aqua.setmgr.getSetting("HUDColor").getColor()));
        RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() / 2.0f - 160.0f, 120.0, 296.0, 240.0, 2.0, new Color(40, 40, 40, 255));
        RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() / 2.0f - 140.0f, 150.0, 256.0, 195.0, 3.0, new Color(35, 35, 35));
        Aqua.INSTANCE.comfortaa3.drawString("Load Visuals ", sr.getScaledWidth() / 2.0f + 30.0f, 133.0f, -1);
        if (this.mouseOver(mouseX, mouseY, (int)(sr.getScaledWidth() / 2.0f + 100.0f), 132, 13, 12)) {
            if (Aqua.moduleManager.getModuleByName("ShaderMultiplier").isToggled()) {
                ShaderMultiplier.drawGlowESP(() -> RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() / 2.0f + 138.0f, 98.0, 105.0, 14.0, 3.0, new Color(Aqua.setmgr.getSetting("HUDColor").getColor())), false);
            }
            RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() / 2.0f + 138.0f, 98.0, 105.0, 14.0, 3.0, new Color(40, 40, 40, 100));
            Aqua.INSTANCE.comfortaa3.drawString("Right Click to Disable", sr.getScaledWidth() / 2.0f + 140.0f, 100.0f, -1);
            if (Mouse.isButtonDown(0)) {
                ConfigScreen.loadVisuals = true;
            }
            else if (Mouse.isButtonDown(1)) {
                ConfigScreen.loadVisuals = false;
            }
        }
        if (ConfigScreen.loadVisuals) {
            RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() / 2.0f + 100.0f, 132.0, 13.0, 12.0, 3.0, new Color(hudColor));
        }
        else {
            RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() / 2.0f + 100.0f, 132.0, 13.0, 12.0, 3.0, new Color(29, 29, 29));
        }
        if (this.mouseOver(mouseX, mouseY, (int)(sr.getScaledWidth() / 2.0f - 140.0f), 133, 29, 12)) {
            Aqua.INSTANCE.comfortaa3.drawString("Local", sr.getScaledWidth() / 2.0f - 140.0f, 133.0f, -1);
        }
        else {
            Aqua.INSTANCE.comfortaa3.drawString("Local", sr.getScaledWidth() / 2.0f - 140.0f, 133.0f, hudColor);
        }
        if (this.mouseOver(mouseX, mouseY, (int)(sr.getScaledWidth() / 2.0f - 100.0f), 133, 32, 12)) {
            Aqua.INSTANCE.comfortaa3.drawString("Online", sr.getScaledWidth() / 2.0f - 100.0f, 133.0f, -1);
        }
        else {
            Aqua.INSTANCE.comfortaa3.drawString("Online", sr.getScaledWidth() / 2.0f - 100.0f, 133.0f, hudColor);
        }
        final int currentY = this.scrollAdd;
        final int dateY = this.scrollAdd;
        if (this.onlineConfigs) {
            this.configHeight = 25 * this.onlineConfigList.size();
            this.renderConfig(this.onlineConfigList, this.onlineConfigDate, this.configHeight, currentY, dateY, sr, mouseX, mouseY);
        }
        else {
            this.renderConfig(this.configListName, this.onlineConfigDate, this.configHeight, currentY, dateY, sr, mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private void renderConfig(final ArrayList<String> configList, final ArrayList<String> onlineConfigDate, final int configHeight, int currentY, int dateY, final ScaledResolution sr, final int mouseX, final int mouseY) {
        GL11.glEnable(3089);
        RenderUtil.scissor(sr.getScaledWidth() / 2.0f - 100.0f, 155.0, 190.0, 188.0);
        if (this.onlineConfigs) {
            for (final String date : onlineConfigDate) {
                dateY += 25;
                Aqua.INSTANCE.comfortaa4.drawString("" + date, sr.getScaledWidth() / 2.0f + 31.0f, (float)(136 + dateY), -1);
            }
        }
        for (final String config : configList) {
            currentY += 25;
            RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() / 2.0f - 97.0f, currentY + 127, 173.0, 25.0, 0.0, new Color(0, 0, 0, 30));
            RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() / 2.0f - 97.0f, currentY + 127, 173.0, 1.0, 0.0, new Color(Aqua.setmgr.getSetting("HUDColor").getColor()));
            Aqua.INSTANCE.comfortaa4.drawString("" + config, sr.getScaledWidth() / 2.0f - 90.0f, (float)(136 + currentY), -1);
        }
        if (this.mouseOver(mouseX, mouseY, (int)(sr.getScaledWidth() / 2.0f - 100.0f), 150, 190, 200) && configHeight > 200) {
            final int mouseDelta = Aqua.INSTANCE.mouseWheelUtil.mouseDelta;
            this.scrollAdd += mouseDelta / 5;
            this.scrollAdd = MathHelper.clamp_int(this.scrollAdd, -configHeight + 188, 0);
        }
        GL11.glDisable(3089);
    }
    
    @Override
    public void initGui() {
        this.initConfigSystem();
    }
    
    private void initConfigSystem() {
        this.configList = this.findConfigs();
        this.configListName.clear();
        for (final Config config : this.configList) {
            this.configListName.add(config.getConfigName());
        }
        this.configHeight = 25 * this.configList.size();
        this.onlineConfigList.clear();
        this.onlineConfigDate.clear();
        this.findOnlineConfig();
        this.findOnlineConfigDate();
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        int currentY = this.scrollAdd;
        if (this.onlineConfigs) {
            for (final String config : this.onlineConfigList) {
                currentY += 25;
                if (this.mouseOver(mouseX, mouseY, (int)(sr.getScaledWidth() / 2.0f - 97.0f), currentY + 125, 183, 22)) {
                    final ConfigOnline skid = new ConfigOnline();
                    skid.loadConfigOnline(config);
                    break;
                }
            }
        }
        else {
            for (final Config config2 : this.configList) {
                currentY += 25;
                if (this.mouseOver(mouseX, mouseY, (int)(sr.getScaledWidth() / 2.0f - 97.0f), currentY + 125, 183, 22)) {
                    config2.load();
                    break;
                }
            }
        }
        if (this.mouseOver(mouseX, mouseY, (int)(sr.getScaledWidth() / 2.0f - 140.0f), 133, 29, 12)) {
            if (this.onlineConfigs) {
                this.initConfigSystem();
                this.onlineConfigs = false;
            }
        }
        else if (this.mouseOver(mouseX, mouseY, (int)(sr.getScaledWidth() / 2.0f - 100.0f), 133, 32, 12) && !this.onlineConfigs) {
            this.initConfigSystem();
            this.onlineConfigs = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
    }
    
    private ArrayList<Config> findConfigs() {
        final ArrayList<Config> configs = new ArrayList<Config>();
        final File confDir = new File(FileUtil.DIRECTORY, "configs/");
        for (final File file : Objects.requireNonNull(confDir.listFiles())) {
            final Config config = new Config(file.getName().replaceAll(".json", ""));
            configs.add(config);
        }
        return configs;
    }
    
    private void findOnlineConfig() {
        final ArrayList<String> configs;
        URLConnection urlConnection;
        final BufferedReader bufferedReader2;
        BufferedReader bufferedReader;
        String text;
        final Object o;
        final Throwable t2;
        final Thread thread = new Thread(() -> {
            configs = new ArrayList<String>();
            try {
                urlConnection = new URL("https://raw.githubusercontent.com/aquaclient/aquaclient.github.io/main/configs/configs.json").openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.connect();
                new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                bufferedReader = bufferedReader2;
                try {
                    while (true) {
                        text = bufferedReader.readLine();
                        if (o != null) {
                            if (text.contains("404: Not Found")) {
                                ChatUtil.sendChatMessageWithPrefix("An error occurred while loading the configs from Github.");
                                return;
                            }
                            else {
                                configs.add(text);
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
                catch (Throwable t) {
                    throw t;
                }
                finally {
                    if (bufferedReader != null) {
                        if (t2 != null) {
                            try {
                                bufferedReader.close();
                            }
                            catch (Throwable exception) {
                                t2.addSuppressed(exception);
                            }
                        }
                        else {
                            bufferedReader.close();
                        }
                    }
                }
            }
            catch (IOException e) {
                ChatUtil.sendChatMessageWithPrefix("An error occurred while loading the configs from Github.");
                e.printStackTrace();
            }
            this.onlineConfigList = configs;
            return;
        });
        thread.start();
    }
    
    private void findOnlineConfigDate() {
        final ArrayList<String> date;
        URLConnection urlConnection;
        final BufferedReader bufferedReader2;
        BufferedReader bufferedReader;
        String text;
        final Object o;
        final Throwable t2;
        final Thread thread = new Thread(() -> {
            date = new ArrayList<String>();
            try {
                urlConnection = new URL("https://raw.githubusercontent.com/aquaclient/aquaclient.github.io/main/ConfigDate.json").openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.connect();
                new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                bufferedReader = bufferedReader2;
                try {
                    while (true) {
                        text = bufferedReader.readLine();
                        if (o != null) {
                            if (text.contains("404: Not Found")) {
                                ChatUtil.sendChatMessageWithPrefix("An error occurred while loading the configs from Github.");
                                return;
                            }
                            else {
                                date.add(text);
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
                catch (Throwable t) {
                    throw t;
                }
                finally {
                    if (bufferedReader != null) {
                        if (t2 != null) {
                            try {
                                bufferedReader.close();
                            }
                            catch (Throwable exception) {
                                t2.addSuppressed(exception);
                            }
                        }
                        else {
                            bufferedReader.close();
                        }
                    }
                }
            }
            catch (IOException e) {
                ChatUtil.sendChatMessageWithPrefix("An error occurred while loading the configs from Github.");
                e.printStackTrace();
            }
            this.onlineConfigDate = date;
            return;
        });
        thread.start();
    }
    
    private boolean mouseOver(final int x, final int y, final int modX, final int modY, final int modWidth, final int modHeight) {
        return x >= modX && x <= modX + modWidth && y >= modY && y <= modY + modHeight;
    }
    
    static {
        ConfigScreen.loadVisuals = false;
    }
}
