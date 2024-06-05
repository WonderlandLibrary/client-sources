/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package cc.diablo.clickgui;

import cc.diablo.Main;
import cc.diablo.font.TTFFontRenderer;
import cc.diablo.font.TTFRenderer;
import cc.diablo.helpers.render.ColorHelper;
import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.Setting;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ClickGUI
extends GuiScreen {
    TTFFontRenderer fr = Main.getInstance().getSFUI(20);
    double x;
    double y;
    double x2;
    double y2;
    double width;
    double height;
    public Category selectedCategory = null;
    public Module module1;
    public int catagoryCount;
    public int btnOffset;
    TTFRenderer fontTitle = new TTFRenderer("Comfortaa", 0, 20);
    TTFFontRenderer moduleTitle = Main.getInstance().getFontManager().getFont("clean 18");
    TTFFontRenderer moduleSubtitle = Main.getInstance().getFontManager().getFont("clean 16");
    public String activeDesc = "";
    public MouseEvent mouseEvt;
    public ArrayList<Module> combatModules = new ArrayList();
    public ArrayList<Module> movementModules = new ArrayList();
    public ArrayList<Module> playerModules = new ArrayList();
    public ArrayList<Module> renderModules = new ArrayList();
    public ArrayList<Module> exploitModules = new ArrayList();
    public ArrayList<Module> miscModules = new ArrayList();
    public ArrayList<Module> ghostModules = new ArrayList();
    public ArrayList<Module> expandedModules = new ArrayList();
    public boolean combatExpanded;
    public boolean movementExpanded;
    public boolean playerExpanded;
    public boolean renderExpanded;
    public boolean exploitExpanded;
    public boolean miscExpanded;
    public boolean ghostExpanded = false;
    public int yHeight = 29;
    public boolean dragging = false;

    @Override
    public void initGui() {
        this.combatModules.clear();
        this.movementModules.clear();
        this.playerModules.clear();
        this.renderModules.clear();
        this.exploitModules.clear();
        this.miscModules.clear();
        this.ghostModules.clear();
        this.module1 = null;
        this.btnOffset = 20;
        this.catagoryCount = 0;
        this.x = this.sr.getScaledWidth_double();
        this.y = this.sr.getScaledHeight_double();
        this.width = 100.0;
        this.height = 180.0;
        this.x2 = this.x;
        this.y2 = this.y;
        for (Module module : ModuleManager.getModules()) {
            if (module.getName() == "ClickGUI") continue;
            if (module.getCategory() == Category.Combat) {
                this.combatModules.add(module);
                continue;
            }
            if (module.getCategory() == Category.Movement) {
                this.movementModules.add(module);
                continue;
            }
            if (module.getCategory() == Category.Player) {
                this.playerModules.add(module);
                continue;
            }
            if (module.getCategory() == Category.Render) {
                this.renderModules.add(module);
                continue;
            }
            if (module.getCategory() == Category.Exploit) {
                this.exploitModules.add(module);
                continue;
            }
            if (module.getCategory() == Category.Misc) {
                this.miscModules.add(module);
                continue;
            }
            if (module.getCategory() != Category.Ghost) continue;
            this.ghostModules.add(module);
        }
        super.initGui();
    }

    public TTFFontRenderer getFont(String font, double size) {
        return Main.getInstance().getFontManager().getFont(font + " " + size);
    }

    @Subscribe
    public void onClick(MouseEvent e) {
        this.mouseEvt = e;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            MovementInput.moveForward = 1.0f;
            if (Keyboard.isKeyDown((int)208)) {
                this.mc.thePlayer.rotationPitch += 2.0f;
            }
            if (Keyboard.isKeyDown((int)200)) {
                this.mc.thePlayer.rotationPitch -= 2.0f;
            }
            if (Keyboard.isKeyDown((int)205)) {
                this.mc.thePlayer.rotationYaw += 2.0f;
            }
            if (Keyboard.isKeyDown((int)203)) {
                this.mc.thePlayer.rotationYaw -= 2.0f;
            }
            KeyBinding[] keys = new KeyBinding[]{this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindJump};
            Arrays.stream(keys).forEach(key -> KeyBinding.setKeyBindState(key.getKeyCode(), Keyboard.isKeyDown((int)key.getKeyCode())));
        }
        catch (Exception keys) {
            // empty catch block
        }
        GL11.glPushMatrix();
        this.combatModules.sort(Comparator.comparing(module -> Float.valueOf(this.moduleSubtitle.getWidth(((Module)module).getName()))).reversed());
        this.movementModules.sort(Comparator.comparing(module -> Float.valueOf(this.moduleSubtitle.getWidth(((Module)module).getName()))).reversed());
        this.playerModules.sort(Comparator.comparing(module -> Float.valueOf(this.moduleSubtitle.getWidth(((Module)module).getName()))).reversed());
        this.renderModules.sort(Comparator.comparing(module -> Float.valueOf(this.moduleSubtitle.getWidth(((Module)module).getName()))).reversed());
        this.miscModules.sort(Comparator.comparing(module -> Float.valueOf(this.moduleSubtitle.getWidth(((Module)module).getName()))).reversed());
        this.ghostModules.sort(Comparator.comparing(module -> Float.valueOf(this.moduleSubtitle.getWidth(((Module)module).getName()))).reversed());
        int cX = 5;
        int cY = 29;
        int cW = 100;
        int cOff = cY + 1;
        int cH = this.combatExpanded ? 17 * this.combatModules.size() : 0;
        int cH2 = 0;
        for (Module m : this.combatModules) {
            if (!this.expandedModules.contains(m)) continue;
            for (Setting s : m.getSettingList()) {
                if (s instanceof ModeSetting) {
                    cH2 += 28;
                    if (((ModeSetting)s).getExpanded()) {
                        for (String modes : ((ModeSetting)s).getSettings()) {
                            if (modes == ((ModeSetting)s).getMode()) continue;
                            cH2 += 12;
                        }
                    }
                    cH2 += 2;
                }
                if (s instanceof NumberSetting) {
                    cH2 += 17;
                }
                if (!(s instanceof BooleanSetting)) continue;
                cH2 += 17;
            }
        }
        if (this.combatExpanded) {
            cH += cH2;
        }
        RenderUtils.drawRect(cX - 1, cY - 18, cX + cW + 1, cY + cH + 1, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.6f));
        RenderUtils.drawRect(cX, cY - 15, cX + cW, cY, RenderUtils.transparency(new Color(29, 29, 29, 255).getRGB(), 0.8f));
        Main.getInstance().getFontManager().getFont("guicons 24").drawStringWithShadow("c", cX + 1, cY - 11, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow("Combat", (float)cX + Main.getInstance().getFontManager().getFont("guicons 24").getWidth("c") + 1.0f, cY - 12, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow(this.combatExpanded ? "-" : "+", (float)(cX + cW) - Main.getInstance().getFontManager().getFont("clean").getWidth(this.combatExpanded ? "-" : "+") - 1.0f, cY - 12, -1);
        RenderUtils.drawRect(cX, cY, cX + cW, cY + cH, RenderUtils.transparency(new Color(45, 45, 45, 255).getRGB(), 0.8f));
        RenderUtils.drawRect(cX, cY - 17, cX + cW, cY - 15, ColorHelper.getColor(0));
        if (this.combatExpanded) {
            this.drawModuleList(this.combatModules, cX, cW, mouseX, mouseY);
        }
        int mX = cX + cW + 5;
        int mY = 29;
        int mW = 100;
        int mOff = mY + 1;
        int mH = this.movementExpanded ? 17 * this.movementModules.size() : 0;
        int mH2 = 0;
        for (Module m : this.movementModules) {
            if (!this.expandedModules.contains(m)) continue;
            for (Setting s : m.getSettingList()) {
                if (s instanceof ModeSetting) {
                    mH2 += 28;
                    if (((ModeSetting)s).getExpanded()) {
                        for (String modes : ((ModeSetting)s).getSettings()) {
                            if (modes == ((ModeSetting)s).getMode()) continue;
                            mH2 += 12;
                        }
                    }
                    mH2 += 2;
                }
                if (s instanceof NumberSetting) {
                    mH2 += 17;
                }
                if (!(s instanceof BooleanSetting)) continue;
                mH2 += 17;
            }
        }
        if (this.movementExpanded) {
            mH += mH2;
        }
        RenderUtils.drawRect(mX - 1, mY - 18, mX + mW + 1, mY + mH + 1, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.6f));
        RenderUtils.drawRect(mX, mY - 15, mX + mW, mY, RenderUtils.transparency(new Color(29, 29, 29, 255).getRGB(), 0.8f));
        Main.getInstance().getFontManager().getFont("guicons 24").drawStringWithShadow("n", mX + 1, mY - 11, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow("Movement", (float)mX + Main.getInstance().getFontManager().getFont("guicons 24").getWidth("n") + 1.0f, mY - 12, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow(this.movementExpanded ? "-" : "+", (float)(mX + mW) - Main.getInstance().getFontManager().getFont("clean").getWidth(this.movementExpanded ? "-" : "+") - 1.0f, mY - 12, -1);
        RenderUtils.drawRect(mX, mY, mX + mW, mY + mH, RenderUtils.transparency(new Color(45, 45, 45, 255).getRGB(), 0.8f));
        RenderUtils.drawRect(mX, mY - 17, mX + mW, mY - 15, ColorHelper.getColor(0));
        if (this.movementExpanded) {
            this.drawModuleList(this.movementModules, mX, mW, mouseX, mouseY);
        }
        int pX = mX + mW + 5;
        int pY = 29;
        int pW = 100;
        int pOff = cY + 1;
        int pH = this.playerExpanded ? 17 * this.playerModules.size() : 0;
        int pH2 = 0;
        for (Module m : this.playerModules) {
            if (!this.expandedModules.contains(m)) continue;
            for (Setting s : m.getSettingList()) {
                if (s instanceof ModeSetting) {
                    pH2 += 28;
                    if (((ModeSetting)s).getExpanded()) {
                        for (String modes : ((ModeSetting)s).getSettings()) {
                            if (modes == ((ModeSetting)s).getMode()) continue;
                            pH2 += 12;
                        }
                    }
                    pH2 += 2;
                }
                if (s instanceof NumberSetting) {
                    pH2 += 17;
                }
                if (!(s instanceof BooleanSetting)) continue;
                pH2 += 17;
            }
        }
        if (this.playerExpanded) {
            pH += pH2;
        }
        RenderUtils.drawRect(pX - 1, pY - 18, pX + pW + 1, pY + pH + 1, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.6f));
        RenderUtils.drawRect(pX, pY - 15, pX + pW, pY, RenderUtils.transparency(new Color(29, 29, 29, 255).getRGB(), 0.8f));
        Main.getInstance().getFontManager().getFont("guicons 24").drawStringWithShadow("n", pX + 1, pY - 11, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow("Player", (float)pX + Main.getInstance().getFontManager().getFont("guicons 24").getWidth("n") + 1.0f, pY - 12, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow(this.playerExpanded ? "-" : "+", (float)(pX + pW) - Main.getInstance().getFontManager().getFont("clean").getWidth(this.playerExpanded ? "-" : "+") - 1.0f, pY - 12, -1);
        RenderUtils.drawRect(pX, pY, pX + pW, pY + pH, RenderUtils.transparency(new Color(45, 45, 45, 255).getRGB(), 0.8f));
        RenderUtils.drawRect(pX, pY - 17, pX + pW, pY - 15, ColorHelper.getColor(0));
        if (this.playerExpanded) {
            this.drawModuleList(this.playerModules, pX, pW, mouseX, mouseY);
        }
        int rX = pX + pW + 5;
        int rY = 29;
        int rW = 100;
        int rOff = cY + 1;
        int rH = this.renderExpanded ? 17 * this.renderModules.size() : 0;
        int rH2 = 0;
        for (Module m : this.renderModules) {
            if (!this.expandedModules.contains(m)) continue;
            for (Setting s : m.getSettingList()) {
                if (s instanceof ModeSetting) {
                    rH2 += 28;
                    if (((ModeSetting)s).getExpanded()) {
                        for (String modes : ((ModeSetting)s).getSettings()) {
                            if (modes == ((ModeSetting)s).getMode()) continue;
                            rH2 += 12;
                        }
                    }
                    rH2 += 2;
                }
                if (s instanceof NumberSetting) {
                    rH2 += 17;
                }
                if (!(s instanceof BooleanSetting)) continue;
                rH2 += 17;
            }
        }
        if (this.renderExpanded) {
            rH += rH2;
        }
        RenderUtils.drawRect(rX - 1, rY - 18, rX + rW + 1, rY + rH + 1, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.6f));
        RenderUtils.drawRect(rX, rY - 15, rX + rW, rY, RenderUtils.transparency(new Color(29, 29, 29, 255).getRGB(), 0.8f));
        Main.getInstance().getFontManager().getFont("guicons 24").drawStringWithShadow("i", rX + 1, rY - 11, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow("Render", (float)rX + Main.getInstance().getFontManager().getFont("guicons 24").getWidth("i") + 1.0f, rY - 12, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow(this.renderExpanded ? "-" : "+", (float)(rX + rW) - Main.getInstance().getFontManager().getFont("clean").getWidth(this.renderExpanded ? "-" : "+") - 1.0f, rY - 12, -1);
        RenderUtils.drawRect(rX, rY, rX + rW, rY + rH, RenderUtils.transparency(new Color(45, 45, 45, 255).getRGB(), 0.8f));
        RenderUtils.drawRect(rX, rY - 17, rX + rW, rY - 15, ColorHelper.getColor(0));
        if (this.renderExpanded) {
            this.drawModuleList(this.renderModules, rX, rW, mouseX, mouseY);
        }
        int eX = rX + rW + 5;
        int eY = 29;
        int eW = 100;
        int eOff = cY + 1;
        int eH = this.exploitExpanded ? 17 * this.exploitModules.size() : 0;
        int eH2 = 0;
        for (Module m : this.exploitModules) {
            if (!this.expandedModules.contains(m)) continue;
            for (Setting s : m.getSettingList()) {
                if (s instanceof ModeSetting) {
                    eH2 += 28;
                    if (((ModeSetting)s).getExpanded()) {
                        for (String modes : ((ModeSetting)s).getSettings()) {
                            if (modes == ((ModeSetting)s).getMode()) continue;
                            eH2 += 12;
                        }
                    }
                    eH2 += 2;
                }
                if (s instanceof NumberSetting) {
                    eH2 += 17;
                }
                if (!(s instanceof BooleanSetting)) continue;
                eH2 += 17;
            }
        }
        if (this.exploitExpanded) {
            eH += eH2;
        }
        RenderUtils.drawRect(eX - 1, eY - 18, eX + eW + 1, eY + eH + 1, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.6f));
        RenderUtils.drawRect(eX, eY - 15, eX + eW, eY, RenderUtils.transparency(new Color(29, 29, 29, 255).getRGB(), 0.8f));
        Main.getInstance().getFontManager().getFont("guicons 24").drawStringWithShadow("e", eX + 1, eY - 11, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow("Exploit", (float)eX + Main.getInstance().getFontManager().getFont("guicons 24").getWidth("e") + 1.0f, eY - 12, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow(this.exploitExpanded ? "-" : "+", (float)(eX + eW) - Main.getInstance().getFontManager().getFont("clean").getWidth(this.exploitExpanded ? "-" : "+") - 1.0f, eY - 12, -1);
        RenderUtils.drawRect(eX, eY, eX + eW, eY + eH, RenderUtils.transparency(new Color(45, 45, 45, 255).getRGB(), 0.8f));
        RenderUtils.drawRect(eX, eY - 17, eX + eW, eY - 15, ColorHelper.getColor(0));
        if (this.exploitExpanded) {
            this.drawModuleList(this.exploitModules, eX, eW, mouseX, mouseY);
        }
        int miscX = eX + eW + 5;
        int miscY = 29;
        int miscW = 100;
        int miscOff = cY + 1;
        int miscH = this.miscExpanded ? 17 * this.miscModules.size() : 0;
        int miscH2 = 0;
        for (Module m : this.miscModules) {
            if (!this.expandedModules.contains(m)) continue;
            for (Setting s : m.getSettingList()) {
                if (s instanceof ModeSetting) {
                    miscH2 += 28;
                    if (((ModeSetting)s).getExpanded()) {
                        for (String modes : ((ModeSetting)s).getSettings()) {
                            if (modes == ((ModeSetting)s).getMode()) continue;
                            miscH2 += 12;
                        }
                    }
                    miscH2 += 2;
                }
                if (s instanceof NumberSetting) {
                    miscH2 += 17;
                }
                if (!(s instanceof BooleanSetting)) continue;
                miscH2 += 17;
            }
        }
        if (this.miscExpanded) {
            miscH += miscH2;
        }
        RenderUtils.drawRect(miscX - 1, miscY - 18, miscX + miscW + 1, miscY + miscH + 1, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.6f));
        RenderUtils.drawRect(miscX, miscY - 15, miscX + miscW, miscY, RenderUtils.transparency(new Color(29, 29, 29, 255).getRGB(), 0.8f));
        Main.getInstance().getFontManager().getFont("guicons 24").drawStringWithShadow("m", miscX + 1, miscY - 11, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow("Misc", (float)miscX + Main.getInstance().getFontManager().getFont("guicons 24").getWidth("m") + 1.0f, miscY - 12, -1);
        Main.getInstance().getFontManager().getFont("clean").drawStringWithShadow(this.miscExpanded ? "-" : "+", (float)(miscX + miscW) - Main.getInstance().getFontManager().getFont("clean").getWidth(this.miscExpanded ? "-" : "+") - 1.0f, miscY - 12, -1);
        RenderUtils.drawRect(miscX, miscY, miscX + miscW, miscY + miscH, RenderUtils.transparency(new Color(45, 45, 45, 255).getRGB(), 0.8f));
        RenderUtils.drawRect(miscX, miscY - 17, miscX + miscW, miscY - 15, ColorHelper.getColor(0));
        if (this.miscExpanded) {
            this.drawModuleList(this.miscModules, miscX, miscW, mouseX, mouseY);
        }
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    public void drawModuleButton(Module module, double x, double y, int mouseX, int mouseY) {
        RenderUtils.drawRoundedRectangle(x, y, x + 100.0, y + 16.0, 10.0, new Color(30, 30, 30).getRGB());
        this.fr.drawString(module.getName(), (float)x + 5.0f, (float)y + 3.0f, -1);
        if (this.isHovered(mouseX, mouseY, x, y, x + 100.0, y + 16.0)) {
            this.activeDesc = module.getDescription();
        }
    }

    public void drawModuleList(ArrayList<Module> category, float x, float width, int mouseX, int mouseY) {
        int offset = this.yHeight + 1;
        for (Module m : category) {
            int offSave = offset;
            if (m.isToggled()) {
                RenderUtils.drawRect(x + 0.5f, offset, x + width - 0.5f, offset + 17, RenderUtils.transparency(new Color(35, 35, 35, 255).getRGB(), 0.8f));
            }
            this.moduleTitle.drawString(m.getName(), x + width / 2.0f - this.moduleTitle.getWidth(m.getName()) / 2.0f, offset + 4, m.toggled ? -1 : new Color(166, 166, 166).getRGB());
            offset += 17;
            if (!this.expandedModules.contains(m)) continue;
            for (Setting s : m.getSettingList()) {
                try {
                    if (s instanceof ModeSetting) {
                        ModeSetting mode = (ModeSetting)s;
                        RenderUtils.drawRect(x + 2.0f, offset + 2, x + width - 2.0f, offset + 3, ColorHelper.getColor(0));
                        RenderUtils.drawRect(x + 2.0f, offset + 3, x + width - 2.0f, offset + 14, RenderUtils.transparency(new Color(29, 29, 29, 255).getRGB(), 0.8f));
                        RenderUtils.drawRect(x + 2.0f, offset + 14, x + width - 2.0f, offset + 28, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.8f));
                        this.moduleSubtitle.drawString(mode.name, x + 4.0f, (float)offset + 4.5f, new Color(255, 255, 255).getRGB());
                        this.moduleSubtitle.drawString(mode.getMode(), x + 4.0f, offset + 17, new Color(255, 255, 255).getRGB());
                        offset += 28;
                        if (((ModeSetting)s).getExpanded()) {
                            for (String modes : ((ModeSetting)s).getSettings()) {
                                if (modes == mode.getMode()) continue;
                                RenderUtils.drawRect(x + 2.0f, offset, x + width - 2.0f, offset + 12, new Color(40, 40, 40).getRGB());
                                this.moduleSubtitle.drawString(modes, x + 4.0f, offset + 2, new Color(160, 160, 160).getRGB());
                                offset += 12;
                            }
                        }
                        offset += 2;
                    }
                    if (s instanceof NumberSetting) {
                        float width2 = width - 2.0f;
                        float var37 = 150.0f;
                        float var42 = (float)((double)Math.round(((NumberSetting)s).getVal() * (double)width2) / (double)width2);
                        float var46 = (float)((double)width2 / ((NumberSetting)s).getMax());
                        float var48 = var42 * var46;
                        float var51 = (var37 - 50.0f) / width2;
                        RenderUtils.drawRect(x + 2.0f, offset, x + width - 2.0f, offset + 14, RenderUtils.transparency(new Color(29, 29, 29, 255).getRGB(), 0.8f));
                        int widthFinal = (int)((double)width / (((NumberSetting)s).getMax() - ((NumberSetting)s).getVal()));
                        RenderUtils.drawRect((double)(x + 2.0f), (double)offset + 13.5, (double)(x + var48 * var51 - 2.0f), (double)(offset + 15), ColorHelper.getColor(0));
                        this.moduleSubtitle.drawString(s.name, x + 3.0f, offset + 3, new Color(215, 215, 215).getRGB());
                        this.moduleSubtitle.drawString(String.valueOf(((NumberSetting)s).getVal()), x + width - 3.0f - this.moduleSubtitle.getWidth(String.valueOf(((NumberSetting)s).getVal())), offset + 3, new Color(215, 215, 215).getRGB());
                        if (this.dragging && this.isHovered(mouseX, mouseY, x, offset + 5, x + width, offset + 15)) {
                            double difference = ((NumberSetting)s).getMax() - ((NumberSetting)s).getMin();
                            double value = ((NumberSetting)s).getMin() + MathHelper.clamp_double(((float)mouseX - x) / 100.0f, 0.0, 1.0) * difference;
                            double set = cc.diablo.helpers.MathHelper.getIncremental(value, ((NumberSetting)s).getInc());
                            ((NumberSetting)s).setVal(set);
                        }
                        offset += 17;
                    }
                    if (!(s instanceof BooleanSetting)) continue;
                    this.moduleSubtitle.drawString(s.name, x + 3.0f, offset + 5, new Color(215, 215, 215).getRGB());
                    RenderUtils.drawRoundedRectangle(x + width - 16.0f, offset + 4, x + width - 6.0f, offset + 13, 5.0, ((BooleanSetting)s).isChecked() ? ColorHelper.getColor(0) : new Color(27, 27, 27).getRGB());
                    offset += 17;
                }
                catch (Exception e2) {
                    if (!(s instanceof ModeSetting)) continue;
                    ((ModeSetting)s).setMode(((ModeSetting)s).getDefaultMode());
                }
            }
        }
    }

    public void detectClicks(ArrayList<Module> category, int mouseX, int mouseY, int mouseButton, float x, float w) {
        int off = this.yHeight + 1;
        for (Module m : category) {
            if (this.isHovered(mouseX, mouseY, x + 1.0f, off + 1, x + w - 1.0f, off + 15)) {
                if (mouseButton == 0) {
                    m.toggle();
                } else if (mouseButton == 1) {
                    if (this.expandedModules.contains(m)) {
                        this.expandedModules.remove(m);
                    } else {
                        this.expandedModules.add(m);
                    }
                }
            }
            if (this.expandedModules.contains(m)) {
                for (Setting s : m.getSettingList()) {
                    if (s instanceof ModeSetting) {
                        if (this.isHovered(mouseX, mouseY, x + 2.0f, off += 28, (double)x + this.width - 2.0, off + 14)) {
                            ((ModeSetting)s).setExpanded(!((ModeSetting)s).getExpanded());
                        }
                        if (((ModeSetting)s).getExpanded()) {
                            off += 3;
                            for (String modes : ((ModeSetting)s).getSettings()) {
                                if (modes == ((ModeSetting)s).getMode() || !this.isHovered(mouseX, mouseY, x + 2.0f, off += 12, (double)x + this.width - 2.0, off + 12)) continue;
                                ((ModeSetting)s).setMode(modes);
                                ((ModeSetting)s).setExpanded(false);
                            }
                        }
                        off += 2;
                    }
                    if (s instanceof NumberSetting) {
                        off += 17;
                    }
                    if (!(s instanceof BooleanSetting)) continue;
                    if (this.isHovered(mouseX, mouseY, x + w - 16.0f, (off += 15) + 4, x + w - 6.0f, off + 13)) {
                        ((BooleanSetting)s).setChecked(!((BooleanSetting)s).isChecked());
                    }
                    off += 2;
                }
            }
            off += 17;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.dragging = true;
        int cX = 5;
        int cY = 29;
        int cW = 100;
        int cOff = cY + 1;
        int cH = 17 * this.combatModules.size();
        if (this.isHovered(mouseX, mouseY, cX, cY - 15, cX + cW, cY) && mouseButton == 1) {
            boolean bl = this.combatExpanded = !this.combatExpanded;
        }
        if (this.combatExpanded) {
            this.detectClicks(this.combatModules, mouseX, mouseY, mouseButton, cX, cW);
        }
        int mX = cX + cW + 5;
        int mY = 29;
        int mW = 100;
        int mOff = mY + 1;
        int mH = 17 * this.movementModules.size();
        if (this.isHovered(mouseX, mouseY, mX, mY - 15, mX + mW, mY) && mouseButton == 1) {
            boolean bl = this.movementExpanded = !this.movementExpanded;
        }
        if (this.movementExpanded) {
            this.detectClicks(this.movementModules, mouseX, mouseY, mouseButton, mX, mW);
        }
        int pX = mX + mW + 5;
        int pY = 29;
        int pW = 100;
        int pOff = pY + 1;
        int pH = 17 * this.playerModules.size();
        if (this.isHovered(mouseX, mouseY, pX, pY - 15, pX + pW, pY) && mouseButton == 1) {
            boolean bl = this.playerExpanded = !this.playerExpanded;
        }
        if (this.playerExpanded) {
            this.detectClicks(this.playerModules, mouseX, mouseY, mouseButton, pX, pW);
        }
        int rX = pX + pW + 5;
        int rY = 29;
        int rW = 100;
        int rOff = rY + 1;
        int rH = 17 * this.renderModules.size();
        if (this.isHovered(mouseX, mouseY, rX, rY - 15, rX + rW, rY) && mouseButton == 1) {
            boolean bl = this.renderExpanded = !this.renderExpanded;
        }
        if (this.renderExpanded) {
            this.detectClicks(this.renderModules, mouseX, mouseY, mouseButton, rX, rW);
        }
        int eX = rX + rW + 5;
        int eY = 29;
        int eW = 100;
        int eOff = eY + 1;
        int eH = 17 * this.exploitModules.size();
        if (this.isHovered(mouseX, mouseY, eX, eY - 15, eX + eW, eY) && mouseButton == 1) {
            boolean bl = this.exploitExpanded = !this.exploitExpanded;
        }
        if (this.exploitExpanded) {
            this.detectClicks(this.exploitModules, mouseX, mouseY, mouseButton, eX, eW);
        }
        int miscX = eX + eW + 5;
        int miscY = 29;
        int miscW = 100;
        int miscOff = miscY + 1;
        int miscH = 17 * this.miscModules.size();
        if (this.isHovered(mouseX, mouseY, miscX, miscY - 15, miscX + miscW, miscY) && mouseButton == 1) {
            boolean bl = this.miscExpanded = !this.miscExpanded;
        }
        if (this.miscExpanded) {
            this.detectClicks(this.miscModules, mouseX, mouseY, mouseButton, miscX, miscW);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
    }

    @Override
    public void handleInput() throws IOException {
        super.handleInput();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    public boolean isHovered(int mouseX, int mouseY, double x, double y, double width, double height) {
        return (double)mouseX >= x && (double)mouseX <= width && (double)mouseY >= y && (double)mouseY <= height;
    }
}

