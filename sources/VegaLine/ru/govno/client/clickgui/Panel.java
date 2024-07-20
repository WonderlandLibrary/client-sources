/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.clickgui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.clickgui.ClickGuiScreen;
import ru.govno.client.clickgui.Mod;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClickGui;
import ru.govno.client.module.modules.ClientTune;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class Panel {
    AnimationUtils anim = new AnimationUtils(0.0f, 0.0f, 0.15f);
    AnimationUtils rotate = new AnimationUtils(0.0f, 0.0f, 0.075f);
    AnimationUtils press = new AnimationUtils(0.0f, 0.0f, 0.075f);
    AnimationUtils drag = new AnimationUtils(0.0f, 0.0f, 0.1f);
    int oldMouseX;
    int oldMouseY;
    float height = 0.0f;
    boolean wantToClose = false;
    public boolean callClicked;
    ArrayList<Mod> mods = new ArrayList();
    public Module.Category category;
    public float X;
    public float Y;
    public AnimationUtils posX = new AnimationUtils(0.0f, 0.0f, 0.13f);
    public AnimationUtils posY = new AnimationUtils(0.0f, 0.0f, 0.13f);
    boolean open = true;
    boolean dragging = false;
    float dragX;
    float dragY;
    AnimationUtils animLine = new AnimationUtils(0.0f, 0.0f, 0.1f);
    AnimationUtils animOpen = new AnimationUtils(0.0f, this.getHeight(), 0.1f);
    boolean wantToClick = true;

    public Panel(Module.Category category) {
        this.category = category;
        List<Module> sorted = Client.moduleManager.getModuleList().stream().filter(mod -> mod.category == category && mod.isVisible()).collect(Collectors.toList());
        sorted.forEach(mod -> this.mods.add(new Mod((Module)mod, mod == sorted.get(sorted.size() - 1), mod == sorted.get(0))));
    }

    public Panel() {
    }

    public int getColor(int step) {
        return ClickGuiScreen.getColor(step, this.category);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float exX;
        this.height = this.getHeight();
        int ScaledAlpha = (int)ClickGuiScreen.globalAlpha.anim;
        float ScaledAlphaPercent = (float)ScaledAlpha / 255.0f;
        GL11.glPushMatrix();
        this.posX.getAnim();
        if (this.posX.to == this.X && MathUtils.getDifferenceOf(this.posX.anim, this.posX.to) < 0.001 && MathUtils.getDifferenceOf(this.posX.anim, this.posX.to) != 0.0 || this.X == 0.0f) {
            this.posX.setAnim(this.X == 0.0f ? 0.0f : this.posX.to);
        }
        this.posY.getAnim();
        if (this.posY.to == this.Y && MathUtils.getDifferenceOf(this.posY.anim, this.posY.to) < 0.001 && MathUtils.getDifferenceOf(this.posY.anim, this.posY.to) != 0.0 || this.Y == 0.0f) {
            this.posY.setAnim(this.Y == 0.0f ? 0.0f : this.posY.to);
        }
        this.posX.to = (float)((int)this.X) + ((double)(this.X - (float)((int)this.X)) >= 0.5 ? 0.5f : 0.0f);
        this.posY.to = (float)((int)this.Y) + ((double)(this.Y - (float)((int)this.Y)) >= 0.5 ? 0.5f : 0.0f);
        ClickGui.setPositionPanel(this, this.X, this.Y);
        if (this.dragging) {
            this.drag.to = 1.016f;
            this.X = (float)mouseX - this.dragX;
            this.Y = (float)mouseY - this.dragY;
            this.rotate.to = (this.posX.anim - this.posX.to) / 7.5f;
            this.press.to = (this.posY.anim - this.posY.to) / 7.5f;
        } else {
            this.drag.to = 1.0f;
            this.rotate.to = -ClickGuiScreen.scrollSmoothX;
            this.press.to = 0.0f;
            if (MathUtils.getDifferenceOf(0.0f, ClickGuiScreen.scrollSmoothX) < 0.01 && MathUtils.getDifferenceOf(0.0f, ClickGuiScreen.scrollSmoothY) < 0.01) {
                float[] pos = ClickGui.getPositionPanel(this);
                this.X = pos[0];
                this.Y = pos[1];
            }
        }
        this.drag.getAnim();
        if (this.ishover(this.X - 5.0f, this.Y - 5.0f, this.X + this.getWidth() + 5.0f, this.Y + 27.0f, mouseX, mouseY)) {
            ClickGuiScreen.resetHolds();
        }
        this.anim.getAnim();
        float f = this.wantToClose || !this.open ? 0.0f : (this.anim.to = this.open ? 1.0f : this.anim.anim);
        if (this.open && this.wantToClose && (double)this.anim.anim < 0.2) {
            this.open = false;
            this.wantToClose = false;
            ClientTune.get.playGuiPenelOpenOrCloseSong(this.open);
        }
        this.animOpen.getAnim();
        this.animOpen.speed = 0.15f + (this.open && Math.abs(MathUtils.getDifferenceOf(this.animOpen.anim, this.height)) < 2.5 ? 0.5f : 0.0f);
        this.animOpen.to = this.height + 1.5f;
        if (MathUtils.getDifferenceOf(this.animOpen.anim, this.height + 1.5f) < (double)((this.height + 1.5f) / ((float)this.mods.size() * 2.0f))) {
            this.animOpen.setAnim(this.height + 1.5f);
        }
        this.animLine.to = MathUtils.clamp(ClickGuiScreen.scale.anim * (ClickGuiScreen.globalAlpha.anim / 255.0f) * ClickGuiScreen.scale.anim, 0.0f, this.open ? 1.0f : 0.37f);
        this.animLine.getAnim();
        GL11.glTranslated(this.posX.anim + this.dragX, this.posY.anim + this.dragY, 0.0);
        GL11.glRotatef(MathUtils.clamp(-this.rotate.getAnim() * 4.0f, -95.0f, 95.0f), 0.0f, 0.0f, 1.0f);
        GL11.glScaled(1.0, 1.0f + this.press.getAnim() / 30.0f, 1.0);
        GL11.glTranslated(-(this.posX.getAnim() + this.dragX), -(this.posY.anim + this.dragY), 0.0);
        int cli1 = ColorUtils.swapAlpha(ClickGuiScreen.getColor(0, this.category), 85.0f * ScaledAlphaPercent);
        int cli2 = ColorUtils.swapAlpha(ClickGuiScreen.getColor(-324, this.category), 85.0f * ScaledAlphaPercent);
        int cli3 = ColorUtils.swapAlpha(ClickGuiScreen.getColor(0, this.category), 45.0f * ScaledAlphaPercent);
        int cli4 = ColorUtils.swapAlpha(ClickGuiScreen.getColor(972, this.category), 45.0f * ScaledAlphaPercent);
        int col1 = ColorUtils.getColor(9, 9, 9, ClickGuiScreen.globalAlpha.anim / 1.6f * ClickGuiScreen.scale.anim);
        int col2 = ColorUtils.getColor(6, 6, 6, ClickGuiScreen.globalAlpha.anim / 1.3f * ClickGuiScreen.scale.anim);
        RenderUtils.drawOutsideAndInsideFullRoundedFullGradientShadowRectWithBloomBool(this.posX.anim + 0.5f, this.posY.anim + 0.5f, this.posX.anim + this.getWidth() - 0.5f, this.posY.anim + this.animOpen.anim - 3.5f, 5.0f * ScaledAlphaPercent, cli1, cli2, cli3, cli4, true);
        RenderUtils.fixShadows();
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(this.posX.anim + 0.5f, this.posY.anim + 0.5f, this.posX.anim + this.getWidth() - 0.5f, this.posY.anim + this.animOpen.anim - 3.5f, 4.0f, 0.5f, col1, col1, col2, col2, false, true, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(this.posX.anim, this.posY.anim, this.posX.anim + this.getWidth(), this.posY.anim + this.animOpen.anim - 3.0f, 4.0f, 0.5f, ColorUtils.toDark(cli1, 0.4f), ColorUtils.toDark(cli2, 0.4f), ColorUtils.toDark(cli3, 0.4f), ColorUtils.toDark(cli4, 0.4f), true, true, true);
        RenderUtils.drawFullGradientRectPro(this.posX.anim + 1.0f, this.posY.anim + 20.0f + 6.0f * (1.0f - this.animLine.anim), this.posX.anim + this.getWidth() - 1.0f, this.posY.anim + 23.5f, 0, 0, ColorUtils.getColor(9, 9, 9, ClickGuiScreen.globalAlpha.anim / 2.2f * ClickGuiScreen.scale.anim), ColorUtils.getColor(9, 9, 9, ClickGuiScreen.globalAlpha.anim / 2.2f * ClickGuiScreen.scale.anim), false);
        RenderUtils.fixShadows();
        GlStateManager.resetColor();
        float extend = 18.0f;
        float f2 = this.category == Module.Category.MOVEMENT ? -0.5f : (this.category == Module.Category.RENDER ? -1.0f : (exX = this.category == Module.Category.PLAYER ? -0.5f : 0.0f));
        float exY = this.category == Module.Category.PLAYER ? -0.5f : (this.category == Module.Category.MISC ? 3.0f : 0.0f);
        float yho = 2.0f - this.animLine.anim * 2.0f;
        int setB = ColorUtils.swapAlpha(ClickGuiScreen.getColor((int)this.posX.anim / 20, this.category), (int)(90.0f * this.animLine.anim * ScaledAlphaPercent));
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(this.posX.anim + 2.5f + yho, this.posY.anim + 2.5f + yho, this.posX.anim + extend + yho - 1.0f, this.posY.anim + extend + yho - 1.0f, 3.0f, 1.0f, setB, ColorUtils.toDark(setB, 0.75f), ColorUtils.toDark(setB, 0.2f), ColorUtils.toDark(setB, 0.5f), true, true, true);
        String cattegoryed = this.category == Module.Category.COMBAT ? "e" : (this.category == Module.Category.MOVEMENT ? "d" : (this.category == Module.Category.RENDER ? "f" : (this.category == Module.Category.PLAYER ? "b" : (this.category == Module.Category.MISC ? "c" : ""))));
        int texC = ColorUtils.swapAlpha(-1, MathUtils.clamp(this.animLine.anim * ScaledAlphaPercent * 220.0f, 0.0f, 255.0f));
        if (RenderUtils.alpha(texC) >= 33) {
            RenderUtils.fixShadows();
            Fonts.iconswex_36.drawString(cattegoryed, this.posX.anim + 2.5f + exX + yho + 0.5f, this.posY.anim + 5.5f + exY + yho + 0.5f, texC);
            String reCategory = this.category.name().charAt(0) + this.category.name().substring(1).toLowerCase();
            Fonts.comfortaaRegular_22.drawString(reCategory, this.posX.anim + 4.0f + extend + yho, this.posY.anim + 6.0f + yho, texC);
            texC = ColorUtils.swapAlpha(texC, (float)ColorUtils.getAlphaFromColor(texC) / 5.0f);
            if (ColorUtils.getAlphaFromColor(texC) >= 33) {
                Fonts.comfortaaRegular_22.drawString(reCategory, this.posX.anim + 4.0f + extend + yho + 1.0f, this.posY.anim + 6.0f + yho, texC);
                Fonts.comfortaaRegular_22.drawString(reCategory, this.posX.anim + 4.0f + extend + yho - 1.0f, this.posY.anim + 6.0f + yho, texC);
                Fonts.comfortaaRegular_22.drawString(reCategory, this.posX.anim + 4.0f + extend + yho, this.posY.anim + 6.0f + yho + 1.0f, texC);
                Fonts.comfortaaRegular_22.drawString(reCategory, this.posX.anim + 4.0f + extend + yho, this.posY.anim + 6.0f + yho - 1.0f, texC);
                texC = ColorUtils.swapAlpha(texC, (float)ColorUtils.getAlphaFromColor(texC) / 1.25f);
                if (ColorUtils.getAlphaFromColor(texC) >= 33) {
                    Fonts.iconswex_36.drawString(cattegoryed, this.posX.anim + 2.5f + exX + yho + 0.5f + 1.0f, this.posY.anim + 5.5f + exY + yho + 0.5f, texC);
                    Fonts.iconswex_36.drawString(cattegoryed, this.posX.anim + 2.5f + exX + yho + 0.5f - 1.0f, this.posY.anim + 5.5f + exY + yho + 0.5f, texC);
                    Fonts.iconswex_36.drawString(cattegoryed, this.posX.anim + 2.5f + exX + yho + 0.5f, this.posY.anim + 5.5f + exY + yho + 1.5f, texC);
                    Fonts.iconswex_36.drawString(cattegoryed, this.posX.anim + 2.5f + exX + yho + 0.5f, this.posY.anim + 5.5f + exY + yho - 0.5f, texC);
                }
            }
        }
        if (this.dragging || (double)this.drag.anim > 1.001) {
            int outAlpha = (int)(37.0 * ((double)(this.drag.getAnim() - 1.0f) * 261.375));
            int ouC = ColorUtils.getColor(255 - outAlpha / 5, (int)((float)outAlpha * this.animLine.getAnim()));
            float ext = 2.0f;
            RenderUtils.drawOutsideAndInsideFullRoundedFullGradientShadowRectWithBloomBoolShadowsBoolChangeShadowSize(this.posX.getAnim() - ext - this.drag.anim, this.posY.getAnim() - ext - this.drag.anim, this.posX.anim + this.getWidth() + ext + this.drag.anim, this.posY.anim + this.animOpen.anim + this.drag.anim - 1.0f, 5.0f, 2.5f, 1.5f, ouC, ouC, ouC, ouC, true, true, true);
            RenderUtils.fixShadows();
        }
        int step = 1;
        int i = 23;
        if (this.animOpen.getAnim() > 27.5f) {
            for (Mod mod : this.mods) {
                if (MathUtils.getDifferenceOf(this.animOpen.getAnim() - 2.5f, this.height - 1.0f) > 0.5) {
                    StencilUtil.initStencilToWrite();
                    RenderUtils.drawRect(this.posX.anim, this.posY.anim + 24.0f, this.posX.anim + this.getWidth(), this.posY.anim + this.animOpen.anim - 2.0f, -1);
                    StencilUtil.readStencilBuffer(1);
                } else if (mod == this.mods.get(0) || mod == this.mods.get(this.mods.size() - 1)) {
                    StencilUtil.initStencilToWrite();
                    RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(this.posX.anim + 0.5f, this.posY.anim + 23.5f, this.posX.anim + this.getWidth() - 0.5f, this.posY.anim + this.animOpen.anim - 3.5f, 3.5f, 0.0f, -1, -1, -1, -1, false, true, false);
                    StencilUtil.readStencilBuffer(1);
                }
                if (this.anim.anim * this.height > (float)i) {
                    ++step;
                }
                try {
                    if ((float)(i + 10) < this.animOpen.getAnim()) {
                        mod.drawScreen(this.posX.getAnim(), this.posY.getAnim() + (float)i, step, mouseX, mouseY, partialTicks);
                    }
                    if (!this.open) {
                        mod.open = false;
                        mod.openAnim.to = 16.0f;
                    }
                } catch (Exception exception) {
                    // empty catch block
                }
                i = (int)((float)i + ((mod.openAnim.anim > 20.15f ? mod.openAnim.anim + 0.5f : mod.openAnim.anim) + 1.0f));
                if (!(MathUtils.getDifferenceOf(this.animOpen.anim - 2.5f, this.height - 1.0f) > 0.5) && mod != this.mods.get(0) && mod != this.mods.get(this.mods.size() - 1)) continue;
                StencilUtil.uninitStencilBuffer();
            }
        }
        GL11.glPopMatrix();
        this.oldMouseX = mouseX;
        this.oldMouseY = mouseY;
    }

    public void keyPressed(int key) {
        if (this.open) {
            for (Mod mod : this.mods) {
                mod.keyPressed(key);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.wantToClick = true;
        if (mouseButton == 0) {
            this.dragging = false;
        }
        if (this.open) {
            for (Mod mod : this.mods) {
                mod.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.callClicked = this.ishover(this.X, this.Y, this.X + this.getWidth(), this.Y + this.height, mouseX, mouseY);
        if (this.wantToClick && this.ishover(this.X, this.Y, this.X + this.getWidth(), this.Y + 25.0f, mouseX, mouseY)) {
            if (Mouse.isButtonDown(0) && Client.clickGuiScreen.panels.stream().allMatch(panel -> !panel.dragging)) {
                this.dragging = true;
                this.dragX = (float)mouseX - this.X;
                this.dragY = (float)mouseY - this.Y;
            } else if (Mouse.isButtonDown(1)) {
                if (this.open) {
                    this.wantToClose = true;
                    this.anim.to = 0.0f;
                    this.mods.forEach(mod -> {
                        mod.binding = false;
                    });
                } else {
                    this.open = true;
                    ClientTune.get.playGuiPenelOpenOrCloseSong(this.open);
                }
            }
            this.wantToClick = false;
        }
        if (this.open) {
            int i = 26;
            for (Mod mod2 : this.mods) {
                mod2.mouseClicked((int)this.X, (int)this.Y + i, mouseX, mouseY, mouseButton);
                i = (int)((float)i + (mod2.openAnim.anim + 1.0f));
            }
        }
    }

    public float getHeight() {
        float i = 25.0f;
        if (this.open) {
            for (Mod mod : this.mods) {
                i += mod.getHeight() + 1.0f;
            }
            i += 0.5f;
        }
        return i;
    }

    public float getWidth() {
        return 120.0f;
    }

    public void onCloseGui() {
        this.mods.forEach(mod -> mod.onGuiClosed());
    }

    public boolean ishover(float x1, float y1, float x2, float y2, int mouseX, int mouseY) {
        return (float)mouseX >= x1 && (float)mouseX <= x2 && (float)mouseY >= y1 && (float)mouseY <= y2;
    }
}

