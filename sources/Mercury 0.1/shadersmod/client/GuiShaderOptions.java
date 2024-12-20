/*
 * Decompiled with CFR 0.145.
 */
package shadersmod.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import optifine.Config;
import optifine.Lang;
import optifine.StrUtils;
import shadersmod.client.GuiButtonShaderOption;
import shadersmod.client.ShaderOption;
import shadersmod.client.ShaderOptionProfile;
import shadersmod.client.ShaderOptionScreen;
import shadersmod.client.Shaders;

public class GuiShaderOptions
extends GuiScreen {
    private GuiScreen prevScreen;
    protected String title = "Shader Options";
    private GameSettings settings;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private long mouseStillTime = 0L;
    private String screenName = null;
    private String screenText = null;
    private boolean changed = false;
    public static final String OPTION_PROFILE = "<profile>";
    public static final String OPTION_EMPTY = "<empty>";
    public static final String OPTION_REST = "*";

    public GuiShaderOptions(GuiScreen guiscreen, GameSettings gamesettings) {
        this.prevScreen = guiscreen;
        this.settings = gamesettings;
    }

    public GuiShaderOptions(GuiScreen guiscreen, GameSettings gamesettings, String screenName) {
        this(guiscreen, gamesettings);
        this.screenName = screenName;
        if (screenName != null) {
            this.screenText = Shaders.translate("screen." + screenName, screenName);
        }
    }

    @Override
    public void initGui() {
        this.title = I18n.format("of.options.shaderOptionsTitle", new Object[0]);
        int baseId = 100;
        boolean baseX = false;
        int baseY = 30;
        int stepY = 20;
        int btnX = this.width - 130;
        int btnWidth = 120;
        int btnHeight = 20;
        int columns = 2;
        ShaderOption[] ops = Shaders.getShaderPackOptions(this.screenName);
        if (ops != null) {
            if (ops.length > 18) {
                columns = ops.length / 9 + 1;
            }
            for (int i2 = 0; i2 < ops.length; ++i2) {
                ShaderOption so2 = ops[i2];
                if (so2 == null || !so2.isVisible()) continue;
                int col = i2 % columns;
                int row = i2 / columns;
                int colWidth = Math.min(this.width / columns, 200);
                int var21 = (this.width - colWidth * columns) / 2;
                int x2 = col * colWidth + 5 + var21;
                int y2 = baseY + row * stepY;
                int w2 = colWidth - 10;
                String text = this.getButtonText(so2, w2);
                GuiButtonShaderOption btn = new GuiButtonShaderOption(baseId + i2, x2, y2, w2, btnHeight, so2, text);
                btn.enabled = so2.isEnabled();
                this.buttonList.add(btn);
            }
        }
        this.buttonList.add(new GuiButton(201, this.width / 2 - btnWidth - 20, this.height / 6 + 168 + 11, btnWidth, btnHeight, I18n.format("controls.reset", new Object[0])));
        this.buttonList.add(new GuiButton(200, this.width / 2 + 20, this.height / 6 + 168 + 11, btnWidth, btnHeight, I18n.format("gui.done", new Object[0])));
    }

    private String getButtonText(ShaderOption so2, int btnWidth) {
        String labelName = so2.getNameText();
        if (so2 instanceof ShaderOptionScreen) {
            ShaderOptionScreen fr1 = (ShaderOptionScreen)so2;
            return String.valueOf(labelName) + "...";
        }
        FontRenderer fr2 = Config.getMinecraft().fontRendererObj;
        int lenSuffix = fr2.getStringWidth(": " + Lang.getOff()) + 5;
        while (fr2.getStringWidth(labelName) + lenSuffix >= btnWidth && labelName.length() > 0) {
            labelName = labelName.substring(0, labelName.length() - 1);
        }
        String col = so2.isChanged() ? so2.getValueColor(so2.getValue()) : "";
        String labelValue = so2.getValueText(so2.getValue());
        return String.valueOf(labelName) + ": " + col + labelValue;
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        if (guibutton.enabled) {
            if (guibutton.id < 200 && guibutton instanceof GuiButtonShaderOption) {
                GuiButtonShaderOption opts = (GuiButtonShaderOption)guibutton;
                ShaderOption i2 = opts.getShaderOption();
                if (i2 instanceof ShaderOptionScreen) {
                    String var8 = i2.getName();
                    GuiShaderOptions scr = new GuiShaderOptions(this, this.settings, var8);
                    this.mc.displayGuiScreen(scr);
                    return;
                }
                i2.nextValue();
                this.updateAllButtons();
                this.changed = true;
            }
            if (guibutton.id == 201) {
                ShaderOption[] var6 = Shaders.getChangedOptions(Shaders.getShaderPackOptions());
                for (int var7 = 0; var7 < var6.length; ++var7) {
                    ShaderOption opt = var6[var7];
                    opt.resetValue();
                    this.changed = true;
                }
                this.updateAllButtons();
            }
            if (guibutton.id == 200) {
                if (this.changed) {
                    Shaders.saveShaderPackOptions();
                    Shaders.uninit();
                }
                this.mc.displayGuiScreen(this.prevScreen);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ShaderOption so2;
        GuiButton btn;
        GuiButtonShaderOption btnSo;
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 1 && (btn = this.getSelectedButton(mouseX, mouseY)) instanceof GuiButtonShaderOption && (so2 = (btnSo = (GuiButtonShaderOption)btn).getShaderOption()).isChanged()) {
            btnSo.playPressSound(this.mc.getSoundHandler());
            so2.resetValue();
            this.changed = true;
            this.updateAllButtons();
        }
    }

    private void updateAllButtons() {
        for (GuiButton btn : this.buttonList) {
            if (!(btn instanceof GuiButtonShaderOption)) continue;
            GuiButtonShaderOption gbso = (GuiButtonShaderOption)btn;
            ShaderOption opt = gbso.getShaderOption();
            if (opt instanceof ShaderOptionProfile) {
                ShaderOptionProfile optProf = (ShaderOptionProfile)opt;
                optProf.updateProfile();
            }
            gbso.displayString = this.getButtonText(opt, gbso.getButtonWidth());
        }
    }

    @Override
    public void drawScreen(int x2, int y2, float f2) {
        this.drawDefaultBackground();
        if (this.screenText != null) {
            this.drawCenteredString(this.fontRendererObj, this.screenText, this.width / 2, 15, 16777215);
        } else {
            this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
        }
        super.drawScreen(x2, y2, f2);
        if (Math.abs(x2 - this.lastMouseX) <= 5 && Math.abs(y2 - this.lastMouseY) <= 5) {
            this.drawTooltips(x2, y2, this.buttonList);
        } else {
            this.lastMouseX = x2;
            this.lastMouseY = y2;
            this.mouseStillTime = System.currentTimeMillis();
        }
    }

    private void drawTooltips(int x2, int y2, List buttonList) {
        int activateDelay = 700;
        if (System.currentTimeMillis() >= this.mouseStillTime + (long)activateDelay) {
            int x1 = this.width / 2 - 150;
            int y1 = this.height / 6 - 7;
            if (y2 <= y1 + 98) {
                y1 += 105;
            }
            int x22 = x1 + 150 + 150;
            int y22 = y1 + 84 + 10;
            GuiButton btn = this.getSelectedButton(x2, y2);
            if (btn instanceof GuiButtonShaderOption) {
                GuiButtonShaderOption btnSo = (GuiButtonShaderOption)btn;
                ShaderOption so2 = btnSo.getShaderOption();
                String[] lines = this.makeTooltipLines(so2, x22 - x1);
                if (lines == null) {
                    return;
                }
                this.drawGradientRect(x1, y1, x22, y22, -536870912, -536870912);
                for (int i2 = 0; i2 < lines.length; ++i2) {
                    String line = lines[i2];
                    int col = 14540253;
                    if (line.endsWith("!")) {
                        col = 16719904;
                    }
                    this.fontRendererObj.func_175063_a(line, x1 + 5, y1 + 5 + i2 * 11, col);
                }
            }
        }
    }

    private String[] makeTooltipLines(ShaderOption so2, int width) {
        if (so2 instanceof ShaderOptionProfile) {
            return null;
        }
        String name = so2.getNameText();
        String desc = Config.normalize(so2.getDescriptionText()).trim();
        String[] descs = this.splitDescription(desc);
        String id2 = null;
        if (!name.equals(so2.getName())) {
            id2 = String.valueOf(Lang.get("of.general.id")) + ": " + so2.getName();
        }
        String source = null;
        if (so2.getPaths() != null) {
            source = String.valueOf(Lang.get("of.general.from")) + ": " + Config.arrayToString(so2.getPaths());
        }
        String def = null;
        if (so2.getValueDefault() != null) {
            String list = so2.isEnabled() ? so2.getValueText(so2.getValueDefault()) : Lang.get("of.general.ambiguous");
            def = String.valueOf(Lang.getDefault()) + ": " + list;
        }
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add(name);
        list1.addAll(Arrays.asList(descs));
        if (id2 != null) {
            list1.add(id2);
        }
        if (source != null) {
            list1.add(source);
        }
        if (def != null) {
            list1.add(def);
        }
        String[] lines = this.makeTooltipLines(width, list1);
        return lines;
    }

    private String[] splitDescription(String desc) {
        if (desc.length() <= 0) {
            return new String[0];
        }
        desc = StrUtils.removePrefix(desc, "//");
        String[] descs = desc.split("\\. ");
        for (int i2 = 0; i2 < descs.length; ++i2) {
            descs[i2] = "- " + descs[i2].trim();
            descs[i2] = StrUtils.removeSuffix(descs[i2], ".");
        }
        return descs;
    }

    private String[] makeTooltipLines(int width, List<String> args) {
        FontRenderer fr2 = Config.getMinecraft().fontRendererObj;
        ArrayList<String> list = new ArrayList<String>();
        for (int lines = 0; lines < args.size(); ++lines) {
            String arg2 = args.get(lines);
            if (arg2 == null || arg2.length() <= 0) continue;
            List parts = fr2.listFormattedStringToWidth(arg2, width);
            for (String part : parts) {
                list.add(part);
            }
        }
        String[] var10 = list.toArray(new String[list.size()]);
        return var10;
    }

    private GuiButton getSelectedButton(int x2, int y2) {
        for (int i2 = 0; i2 < this.buttonList.size(); ++i2) {
            GuiButton btn = (GuiButton)this.buttonList.get(i2);
            int btnWidth = GuiVideoSettings.getButtonWidth(btn);
            int btnHeight = GuiVideoSettings.getButtonHeight(btn);
            if (x2 < btn.xPosition || y2 < btn.yPosition || x2 >= btn.xPosition + btnWidth || y2 >= btn.yPosition + btnHeight) continue;
            return btn;
        }
        return null;
    }
}

