/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.overlay;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ISoundEventListener;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;

public class SubtitleOverlayGui
extends AbstractGui
implements ISoundEventListener {
    private final Minecraft client;
    private final List<Subtitle> subtitles = Lists.newArrayList();
    private boolean enabled;

    public SubtitleOverlayGui(Minecraft minecraft) {
        this.client = minecraft;
    }

    public void render(MatrixStack matrixStack) {
        if (!this.enabled && this.client.gameSettings.showSubtitles) {
            this.client.getSoundHandler().addListener(this);
            this.enabled = true;
        } else if (this.enabled && !this.client.gameSettings.showSubtitles) {
            this.client.getSoundHandler().removeListener(this);
            this.enabled = false;
        }
        if (this.enabled && !this.subtitles.isEmpty()) {
            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            Vector3d vector3d = new Vector3d(this.client.player.getPosX(), this.client.player.getPosYEye(), this.client.player.getPosZ());
            Vector3d vector3d2 = new Vector3d(0.0, 0.0, -1.0).rotatePitch(-this.client.player.rotationPitch * ((float)Math.PI / 180)).rotateYaw(-this.client.player.rotationYaw * ((float)Math.PI / 180));
            Vector3d vector3d3 = new Vector3d(0.0, 1.0, 0.0).rotatePitch(-this.client.player.rotationPitch * ((float)Math.PI / 180)).rotateYaw(-this.client.player.rotationYaw * ((float)Math.PI / 180));
            Vector3d vector3d4 = vector3d2.crossProduct(vector3d3);
            int n = 0;
            int n2 = 0;
            Iterator<Subtitle> iterator2 = this.subtitles.iterator();
            while (iterator2.hasNext()) {
                Subtitle subtitle = iterator2.next();
                if (subtitle.getStartTime() + 3000L <= Util.milliTime()) {
                    iterator2.remove();
                    continue;
                }
                n2 = Math.max(n2, this.client.fontRenderer.getStringPropertyWidth(subtitle.func_238526_a_()));
            }
            n2 = n2 + this.client.fontRenderer.getStringWidth("<") + this.client.fontRenderer.getStringWidth(" ") + this.client.fontRenderer.getStringWidth(">") + this.client.fontRenderer.getStringWidth(" ");
            for (Subtitle subtitle : this.subtitles) {
                int n3 = 255;
                ITextComponent iTextComponent = subtitle.func_238526_a_();
                Vector3d vector3d5 = subtitle.getLocation().subtract(vector3d).normalize();
                double d = -vector3d4.dotProduct(vector3d5);
                double d2 = -vector3d2.dotProduct(vector3d5);
                boolean bl = d2 > 0.5;
                int n4 = n2 / 2;
                int n5 = 9;
                int n6 = n5 / 2;
                float f = 1.0f;
                int n7 = this.client.fontRenderer.getStringPropertyWidth(iTextComponent);
                int n8 = MathHelper.floor(MathHelper.clampedLerp(255.0, 75.0, (float)(Util.milliTime() - subtitle.getStartTime()) / 3000.0f));
                int n9 = n8 << 16 | n8 << 8 | n8;
                RenderSystem.pushMatrix();
                RenderSystem.translatef((float)this.client.getMainWindow().getScaledWidth() - (float)n4 * 1.0f - 2.0f, (float)(this.client.getMainWindow().getScaledHeight() - 30) - (float)(n * (n5 + 1)) * 1.0f, 0.0f);
                RenderSystem.scalef(1.0f, 1.0f, 1.0f);
                SubtitleOverlayGui.fill(matrixStack, -n4 - 1, -n6 - 1, n4 + 1, n6 + 1, this.client.gameSettings.getTextBackgroundColor(0.8f));
                RenderSystem.enableBlend();
                if (!bl) {
                    if (d > 0.0) {
                        this.client.fontRenderer.drawString(matrixStack, ">", n4 - this.client.fontRenderer.getStringWidth(">"), -n6, n9 + -16777216);
                    } else if (d < 0.0) {
                        this.client.fontRenderer.drawString(matrixStack, "<", -n4, -n6, n9 + -16777216);
                    }
                }
                this.client.fontRenderer.func_243248_b(matrixStack, iTextComponent, -n7 / 2, -n6, n9 + -16777216);
                RenderSystem.popMatrix();
                ++n;
            }
            RenderSystem.disableBlend();
            RenderSystem.popMatrix();
        }
    }

    @Override
    public void onPlaySound(ISound iSound, SoundEventAccessor soundEventAccessor) {
        if (soundEventAccessor.getSubtitle() != null) {
            ITextComponent iTextComponent = soundEventAccessor.getSubtitle();
            if (!this.subtitles.isEmpty()) {
                for (Subtitle subtitle : this.subtitles) {
                    if (!subtitle.func_238526_a_().equals(iTextComponent)) continue;
                    subtitle.refresh(new Vector3d(iSound.getX(), iSound.getY(), iSound.getZ()));
                    return;
                }
            }
            this.subtitles.add(new Subtitle(this, iTextComponent, new Vector3d(iSound.getX(), iSound.getY(), iSound.getZ())));
        }
    }

    public class Subtitle {
        private final ITextComponent subtitle;
        private long startTime;
        private Vector3d location;
        final SubtitleOverlayGui this$0;

        public Subtitle(SubtitleOverlayGui subtitleOverlayGui, ITextComponent iTextComponent, Vector3d vector3d) {
            this.this$0 = subtitleOverlayGui;
            this.subtitle = iTextComponent;
            this.location = vector3d;
            this.startTime = Util.milliTime();
        }

        public ITextComponent func_238526_a_() {
            return this.subtitle;
        }

        public long getStartTime() {
            return this.startTime;
        }

        public Vector3d getLocation() {
            return this.location;
        }

        public void refresh(Vector3d vector3d) {
            this.location = vector3d;
            this.startTime = Util.milliTime();
        }
    }
}

