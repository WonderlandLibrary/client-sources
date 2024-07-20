/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Render.RenderUtils;

public class GuiWinGame
extends GuiScreen {
    private final long initTime = System.currentTimeMillis();
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation field_194401_g = new ResourceLocation("textures/gui/title/edition.png");
    private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
    private final boolean field_193980_h;
    private final Runnable field_193981_i;
    private float time;
    private List<String> lines;
    private int totalScrollLength;
    private float scrollSpeed = 0.5f;

    public GuiWinGame(boolean p_i47590_1_, Runnable p_i47590_2_) {
        this.field_193980_h = p_i47590_1_;
        this.field_193981_i = p_i47590_2_;
        if (!p_i47590_1_) {
            this.scrollSpeed = 0.75f;
        }
    }

    @Override
    public void updateScreen() {
        this.mc.getMusicTicker().update();
        this.mc.getSoundHandler().update();
        float f = (float)(this.totalScrollLength + height + height + 24) / this.scrollSpeed;
        if (this.time > f) {
            this.sendRespawnPacket();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.sendRespawnPacket();
        }
    }

    private void sendRespawnPacket() {
        this.field_193981_i.run();
        this.mc.displayGuiScreen(null);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initGui() {
        if (this.lines == null) {
            this.lines = Lists.newArrayList();
            IResource iresource = null;
            try {
                String s4;
                String s = "" + TextFormatting.WHITE + TextFormatting.OBFUSCATED + TextFormatting.GREEN + TextFormatting.AQUA;
                int i = 274;
                if (this.field_193980_h) {
                    Object s1;
                    iresource = this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt"));
                    InputStream inputstream = iresource.getInputStream();
                    BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
                    Random random = new Random(8124371L);
                    while ((s1 = bufferedreader.readLine()) != null) {
                        s1 = ((String)s1).replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
                        while (((String)s1).contains(s)) {
                            int j = ((String)s1).indexOf(s);
                            String s2 = ((String)s1).substring(0, j);
                            String s3 = ((String)s1).substring(j + s.length());
                            s1 = s2 + TextFormatting.WHITE + TextFormatting.OBFUSCATED + "XXXXXXXX".substring(0, random.nextInt(4) + 3) + s3;
                        }
                        this.lines.addAll(this.mc.fontRendererObj.listFormattedStringToWidth((String)s1, 274));
                        this.lines.add("");
                    }
                    inputstream.close();
                    for (int k = 0; k < 8; ++k) {
                        this.lines.add("");
                    }
                }
                InputStream inputstream1 = this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream();
                BufferedReader bufferedreader1 = new BufferedReader(new InputStreamReader(inputstream1, StandardCharsets.UTF_8));
                while ((s4 = bufferedreader1.readLine()) != null) {
                    s4 = s4.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
                    s4 = s4.replaceAll("\t", "    ");
                    this.lines.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(s4, 274));
                    this.lines.add("");
                }
                inputstream1.close();
                this.totalScrollLength = this.lines.size() * 12;
                IOUtils.closeQuietly((Closeable)iresource);
            } catch (Exception exception) {
                LOGGER.error("Couldn't load credits", (Throwable)exception);
            } finally {
                IOUtils.closeQuietly(iresource);
            }
        }
    }

    private void drawWinGameScreen(int p_146575_1_, int p_146575_2_, float p_146575_3_) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        int i = width;
        float f = -this.time * 0.5f * this.scrollSpeed;
        float f1 = (float)height - this.time * 0.5f * this.scrollSpeed;
        float f2 = 0.015625f;
        float f3 = this.time * 0.02f;
        float f4 = (float)(this.totalScrollLength + height + height + 24) / this.scrollSpeed;
        float f5 = (f4 - 20.0f - this.time) * 0.005f;
        if (f5 < f3) {
            f3 = f5;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        f3 *= f3;
        f3 = f3 * 96.0f / 255.0f;
        bufferbuilder.pos(0.0, height, this.zLevel).tex(0.0, f * 0.015625f).color(f3, f3, f3, 1.0f).endVertex();
        bufferbuilder.pos(i, height, this.zLevel).tex((float)i * 0.015625f, f * 0.015625f).color(f3, f3, f3, 1.0f).endVertex();
        bufferbuilder.pos(i, 0.0, this.zLevel).tex((float)i * 0.015625f, f1 * 0.015625f).color(f3, f3, f3, 1.0f).endVertex();
        bufferbuilder.pos(0.0, 0.0, this.zLevel).tex(0.0, f1 * 0.015625f).color(f3, f3, f3, 1.0f).endVertex();
        tessellator.draw();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        if (!Panic.stop) {
            RenderUtils.drawScreenShaderBackground(sr, mouseX, mouseY);
        } else {
            this.drawWinGameScreen(mouseX, mouseY, partialTicks);
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        int i = 274;
        int j = width / 2 - 137;
        int k = height + 50;
        this.time += partialTicks;
        float f = -this.time * this.scrollSpeed;
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, f, 0.0f);
        this.mc.getTextureManager().bindTexture(MINECRAFT_LOGO);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableAlpha();
        this.drawTexturedModalRect(j, k, 0, 0, 155, 44);
        this.drawTexturedModalRect(j + 155, k, 0, 45, 155, 44);
        this.mc.getTextureManager().bindTexture(field_194401_g);
        GuiWinGame.drawModalRectWithCustomSizedTexture(j + 88, k + 37, 0.0f, 0.0f, 98, 14, 128.0f, 16.0f);
        GlStateManager.disableAlpha();
        int l = k + 100;
        for (int i1 = 0; i1 < this.lines.size(); ++i1) {
            float f1;
            if (i1 == this.lines.size() - 1 && (f1 = (float)l + f - (float)(height / 2 - 6)) < 0.0f) {
                GlStateManager.translate(0.0f, -f1, 0.0f);
            }
            if ((float)l + f + 12.0f + 8.0f > 0.0f && (float)l + f < (float)height) {
                String s = this.lines.get(i1);
                if (s.startsWith("[C]")) {
                    this.fontRendererObj.drawStringWithShadow(s.substring(3), j + (274 - this.fontRendererObj.getStringWidth(s.substring(3))) / 2, l, 0xFFFFFF);
                } else {
                    this.fontRendererObj.fontRandom.setSeed((long)((float)((long)i1 * 4238972211L) + this.time / 4.0f));
                    this.fontRendererObj.drawStringWithShadow(s, j, l, 0xFFFFFF);
                }
            }
            l += 12;
        }
        GlStateManager.popMatrix();
        this.mc.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
        int j1 = width;
        int k1 = height;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0, k1, this.zLevel).tex(0.0, 1.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferbuilder.pos(j1, k1, this.zLevel).tex(1.0, 1.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferbuilder.pos(j1, 0.0, this.zLevel).tex(1.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferbuilder.pos(0.0, 0.0, this.zLevel).tex(0.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

