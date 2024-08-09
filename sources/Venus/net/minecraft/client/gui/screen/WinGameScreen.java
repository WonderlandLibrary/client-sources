/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.resources.IResource;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WinGameScreen
extends Screen {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
    private static final ResourceLocation MINECRAFT_EDITION = new ResourceLocation("textures/gui/title/edition.png");
    private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
    private static final String field_238663_q_ = "" + TextFormatting.WHITE + TextFormatting.OBFUSCATED + TextFormatting.GREEN + TextFormatting.AQUA;
    private final boolean poem;
    private final Runnable onFinished;
    private float time;
    private List<IReorderingProcessor> lines;
    private IntSet field_238664_v_;
    private int totalScrollLength;
    private float scrollSpeed = 0.5f;

    public WinGameScreen(boolean bl, Runnable runnable) {
        super(NarratorChatListener.EMPTY);
        this.poem = bl;
        this.onFinished = runnable;
        if (!bl) {
            this.scrollSpeed = 0.75f;
        }
    }

    @Override
    public void tick() {
        this.minecraft.getMusicTicker().tick();
        this.minecraft.getSoundHandler().tick(true);
        float f = (float)(this.totalScrollLength + this.height + this.height + 24) / this.scrollSpeed;
        if (this.time > f) {
            this.sendRespawnPacket();
        }
    }

    @Override
    public void closeScreen() {
        this.sendRespawnPacket();
    }

    private void sendRespawnPacket() {
        this.onFinished.run();
        this.minecraft.displayGuiScreen(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void init() {
        if (this.lines == null) {
            this.lines = Lists.newArrayList();
            this.field_238664_v_ = new IntOpenHashSet();
            IResource iResource = null;
            try {
                Object object;
                BufferedReader bufferedReader;
                InputStream inputStream;
                int n = 274;
                if (this.poem) {
                    int n2;
                    Object object2;
                    iResource = this.minecraft.getResourceManager().getResource(new ResourceLocation("texts/end.txt"));
                    inputStream = iResource.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    object = new Random(8124371L);
                    while ((object2 = bufferedReader.readLine()) != null) {
                        object2 = ((String)object2).replaceAll("PLAYERNAME", this.minecraft.getSession().getUsername());
                        while ((n2 = ((String)object2).indexOf(field_238663_q_)) != -1) {
                            String object3 = ((String)object2).substring(0, n2);
                            String string = ((String)object2).substring(n2 + field_238663_q_.length());
                            object2 = object3 + TextFormatting.WHITE + TextFormatting.OBFUSCATED + "XXXXXXXX".substring(0, ((Random)object).nextInt(4) + 3) + string;
                        }
                        this.lines.addAll(this.minecraft.fontRenderer.trimStringToWidth(new StringTextComponent((String)object2), 274));
                        this.lines.add(IReorderingProcessor.field_242232_a);
                    }
                    inputStream.close();
                    for (n2 = 0; n2 < 8; ++n2) {
                        this.lines.add(IReorderingProcessor.field_242232_a);
                    }
                }
                inputStream = this.minecraft.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                while ((object = bufferedReader.readLine()) != null) {
                    boolean bl;
                    object = ((String)object).replaceAll("PLAYERNAME", this.minecraft.getSession().getUsername());
                    if (((String)(object = ((String)object).replaceAll("\t", "    "))).startsWith("[C]")) {
                        object = ((String)object).substring(3);
                        bl = true;
                    } else {
                        bl = false;
                    }
                    for (IReorderingProcessor iReorderingProcessor : this.minecraft.fontRenderer.trimStringToWidth(new StringTextComponent((String)object), 274)) {
                        if (bl) {
                            this.field_238664_v_.add(this.lines.size());
                        }
                        this.lines.add(iReorderingProcessor);
                    }
                    this.lines.add(IReorderingProcessor.field_242232_a);
                }
                inputStream.close();
                this.totalScrollLength = this.lines.size() * 12;
                IOUtils.closeQuietly((Closeable)iResource);
            } catch (Exception exception) {
                LOGGER.error("Couldn't load credits", (Throwable)exception);
            } finally {
                IOUtils.closeQuietly(iResource);
            }
        }
    }

    private void drawWinGameScreen(int n, int n2, float f) {
        this.minecraft.getTextureManager().bindTexture(AbstractGui.BACKGROUND_LOCATION);
        int n3 = this.width;
        float f2 = -this.time * 0.5f * this.scrollSpeed;
        float f3 = (float)this.height - this.time * 0.5f * this.scrollSpeed;
        float f4 = 0.015625f;
        float f5 = this.time * 0.02f;
        float f6 = (float)(this.totalScrollLength + this.height + this.height + 24) / this.scrollSpeed;
        float f7 = (f6 - 20.0f - this.time) * 0.005f;
        if (f7 < f5) {
            f5 = f7;
        }
        if (f5 > 1.0f) {
            f5 = 1.0f;
        }
        f5 *= f5;
        f5 = f5 * 96.0f / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos(0.0, this.height, this.getBlitOffset()).tex(0.0f, f2 * 0.015625f).color(f5, f5, f5, 1.0f).endVertex();
        bufferBuilder.pos(n3, this.height, this.getBlitOffset()).tex((float)n3 * 0.015625f, f2 * 0.015625f).color(f5, f5, f5, 1.0f).endVertex();
        bufferBuilder.pos(n3, 0.0, this.getBlitOffset()).tex((float)n3 * 0.015625f, f3 * 0.015625f).color(f5, f5, f5, 1.0f).endVertex();
        bufferBuilder.pos(0.0, 0.0, this.getBlitOffset()).tex(0.0f, f3 * 0.015625f).color(f5, f5, f5, 1.0f).endVertex();
        tessellator.draw();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        int n3;
        this.drawWinGameScreen(n, n2, f);
        int n4 = 274;
        int n5 = this.width / 2 - 137;
        int n6 = this.height + 50;
        this.time += f;
        float f2 = -this.time * this.scrollSpeed;
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0f, f2, 0.0f);
        this.minecraft.getTextureManager().bindTexture(MINECRAFT_LOGO);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        this.blitBlackOutline(n5, n6, (arg_0, arg_1) -> this.lambda$render$0(matrixStack, arg_0, arg_1));
        RenderSystem.disableBlend();
        this.minecraft.getTextureManager().bindTexture(MINECRAFT_EDITION);
        WinGameScreen.blit(matrixStack, n5 + 88, n6 + 37, 0.0f, 0.0f, 98, 14, 128, 16);
        RenderSystem.disableAlphaTest();
        int n7 = n6 + 100;
        for (n3 = 0; n3 < this.lines.size(); ++n3) {
            float f3;
            if (n3 == this.lines.size() - 1 && (f3 = (float)n7 + f2 - (float)(this.height / 2 - 6)) < 0.0f) {
                RenderSystem.translatef(0.0f, -f3, 0.0f);
            }
            if ((float)n7 + f2 + 12.0f + 8.0f > 0.0f && (float)n7 + f2 < (float)this.height) {
                IReorderingProcessor iReorderingProcessor = this.lines.get(n3);
                if (this.field_238664_v_.contains(n3)) {
                    this.font.func_238407_a_(matrixStack, iReorderingProcessor, n5 + (274 - this.font.func_243245_a(iReorderingProcessor)) / 2, n7, 0xFFFFFF);
                } else {
                    this.font.random.setSeed((long)((float)((long)n3 * 4238972211L) + this.time / 4.0f));
                    this.font.func_238407_a_(matrixStack, iReorderingProcessor, n5, n7, 0xFFFFFF);
                }
            }
            n7 += 12;
        }
        RenderSystem.popMatrix();
        this.minecraft.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
        n3 = this.width;
        int n8 = this.height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos(0.0, n8, this.getBlitOffset()).tex(0.0f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferBuilder.pos(n3, n8, this.getBlitOffset()).tex(1.0f, 1.0f).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferBuilder.pos(n3, 0.0, this.getBlitOffset()).tex(1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        bufferBuilder.pos(0.0, 0.0, this.getBlitOffset()).tex(0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        tessellator.draw();
        RenderSystem.disableBlend();
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$render$0(MatrixStack matrixStack, Integer n, Integer n2) {
        this.blit(matrixStack, n + 0, n2, 0, 0, 155, 44);
        this.blit(matrixStack, n + 155, n2, 0, 45, 155, 44);
    }
}

