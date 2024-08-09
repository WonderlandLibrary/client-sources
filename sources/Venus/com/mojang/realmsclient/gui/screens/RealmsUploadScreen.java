/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.client.FileUpload;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.client.UploadStatus;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
import com.mojang.realmsclient.gui.screens.UploadResult;
import com.mojang.realmsclient.util.UploadTokenCache;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.UploadSpeed;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.storage.WorldSummary;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsUploadScreen
extends RealmsScreen {
    private static final Logger field_224696_a = LogManager.getLogger();
    private static final ReentrantLock field_238081_b_ = new ReentrantLock();
    private static final String[] field_224713_r = new String[]{"", ".", ". .", ". . ."};
    private static final ITextComponent field_243187_p = new TranslationTextComponent("mco.upload.verifying");
    private final RealmsResetWorldScreen field_224697_b;
    private final WorldSummary field_224698_c;
    private final long field_224699_d;
    private final int field_224700_e;
    private final UploadStatus field_224701_f;
    private final RateLimiter field_224702_g;
    private volatile ITextComponent[] field_224703_h;
    private volatile ITextComponent field_224704_i = new TranslationTextComponent("mco.upload.preparing");
    private volatile String field_224705_j;
    private volatile boolean field_224706_k;
    private volatile boolean field_224707_l;
    private volatile boolean field_224708_m = true;
    private volatile boolean field_224709_n;
    private Button field_224710_o;
    private Button field_224711_p;
    private int field_238079_E_;
    private Long field_224715_t;
    private Long field_224716_u;
    private long field_224717_v;
    private final Runnable field_238080_I_;

    public RealmsUploadScreen(long l, int n, RealmsResetWorldScreen realmsResetWorldScreen, WorldSummary worldSummary, Runnable runnable) {
        this.field_224699_d = l;
        this.field_224700_e = n;
        this.field_224697_b = realmsResetWorldScreen;
        this.field_224698_c = worldSummary;
        this.field_224701_f = new UploadStatus();
        this.field_224702_g = RateLimiter.create(0.1f);
        this.field_238080_I_ = runnable;
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_224710_o = this.addButton(new Button(this.width / 2 - 100, this.height - 42, 200, 20, DialogTexts.GUI_BACK, this::lambda$init$0));
        this.field_224710_o.visible = false;
        this.field_224711_p = this.addButton(new Button(this.width / 2 - 100, this.height - 42, 200, 20, DialogTexts.GUI_CANCEL, this::lambda$init$1));
        if (!this.field_224709_n) {
            if (this.field_224697_b.field_224455_a == -1) {
                this.func_224682_h();
            } else {
                this.field_224697_b.func_237952_a_(this::lambda$init$2);
            }
        }
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    private void func_224679_c() {
        this.field_238080_I_.run();
    }

    private void func_224695_d() {
        this.field_224706_k = true;
        this.minecraft.displayGuiScreen(this.field_224697_b);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            if (this.field_224708_m) {
                this.func_224695_d();
            } else {
                this.func_224679_c();
            }
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        if (!this.field_224707_l && this.field_224701_f.field_224978_a != 0L && this.field_224701_f.field_224978_a == this.field_224701_f.field_224979_b) {
            this.field_224704_i = field_243187_p;
            this.field_224711_p.active = false;
        }
        RealmsUploadScreen.drawCenteredString(matrixStack, this.font, this.field_224704_i, this.width / 2, 50, 0xFFFFFF);
        if (this.field_224708_m) {
            this.func_238086_b_(matrixStack);
        }
        if (this.field_224701_f.field_224978_a != 0L && !this.field_224706_k) {
            this.func_238088_c_(matrixStack);
            this.func_238089_d_(matrixStack);
        }
        if (this.field_224703_h != null) {
            for (int i = 0; i < this.field_224703_h.length; ++i) {
                RealmsUploadScreen.drawCenteredString(matrixStack, this.font, this.field_224703_h[i], this.width / 2, 110 + 12 * i, 0xFF0000);
            }
        }
        super.render(matrixStack, n, n2, f);
    }

    private void func_238086_b_(MatrixStack matrixStack) {
        int n = this.font.getStringPropertyWidth(this.field_224704_i);
        this.font.drawString(matrixStack, field_224713_r[this.field_238079_E_ / 10 % field_224713_r.length], this.width / 2 + n / 2 + 5, 50.0f, 0xFFFFFF);
    }

    private void func_238088_c_(MatrixStack matrixStack) {
        double d = Math.min((double)this.field_224701_f.field_224978_a / (double)this.field_224701_f.field_224979_b, 1.0);
        this.field_224705_j = String.format(Locale.ROOT, "%.1f", d * 100.0);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableTexture();
        double d2 = this.width / 2 - 100;
        double d3 = 0.5;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(d2 - 0.5, 95.5, 0.0).color(217, 210, 210, 255).endVertex();
        bufferBuilder.pos(d2 + 200.0 * d + 0.5, 95.5, 0.0).color(217, 210, 210, 255).endVertex();
        bufferBuilder.pos(d2 + 200.0 * d + 0.5, 79.5, 0.0).color(217, 210, 210, 255).endVertex();
        bufferBuilder.pos(d2 - 0.5, 79.5, 0.0).color(217, 210, 210, 255).endVertex();
        bufferBuilder.pos(d2, 95.0, 0.0).color(128, 128, 128, 255).endVertex();
        bufferBuilder.pos(d2 + 200.0 * d, 95.0, 0.0).color(128, 128, 128, 255).endVertex();
        bufferBuilder.pos(d2 + 200.0 * d, 80.0, 0.0).color(128, 128, 128, 255).endVertex();
        bufferBuilder.pos(d2, 80.0, 0.0).color(128, 128, 128, 255).endVertex();
        tessellator.draw();
        RenderSystem.enableTexture();
        RealmsUploadScreen.drawCenteredString(matrixStack, this.font, this.field_224705_j + " %", this.width / 2, 84, 0xFFFFFF);
    }

    private void func_238089_d_(MatrixStack matrixStack) {
        if (this.field_238079_E_ % 20 == 0) {
            if (this.field_224715_t != null) {
                long l = Util.milliTime() - this.field_224716_u;
                if (l == 0L) {
                    l = 1L;
                }
                this.field_224717_v = 1000L * (this.field_224701_f.field_224978_a - this.field_224715_t) / l;
                this.func_238083_a_(matrixStack, this.field_224717_v);
            }
            this.field_224715_t = this.field_224701_f.field_224978_a;
            this.field_224716_u = Util.milliTime();
        } else {
            this.func_238083_a_(matrixStack, this.field_224717_v);
        }
    }

    private void func_238083_a_(MatrixStack matrixStack, long l) {
        if (l > 0L) {
            int n = this.font.getStringWidth(this.field_224705_j);
            String string = "(" + UploadSpeed.func_237684_b_(l) + "/s)";
            this.font.drawString(matrixStack, string, this.width / 2 + n / 2 + 15, 84.0f, 0xFFFFFF);
        }
    }

    @Override
    public void tick() {
        super.tick();
        ++this.field_238079_E_;
        if (this.field_224704_i != null && this.field_224702_g.tryAcquire(0)) {
            ArrayList<Object> arrayList = Lists.newArrayList();
            arrayList.add(this.field_224704_i.getString());
            if (this.field_224705_j != null) {
                arrayList.add(this.field_224705_j + "%");
            }
            if (this.field_224703_h != null) {
                Stream.of(this.field_224703_h).map(ITextComponent::getString).forEach(arrayList::add);
            }
            RealmsNarratorHelper.func_239550_a_(String.join((CharSequence)System.lineSeparator(), arrayList));
        }
    }

    private void func_224682_h() {
        this.field_224709_n = true;
        new Thread(this::lambda$func_224682_h$4).start();
    }

    private void func_238085_a_(ITextComponent ... iTextComponentArray) {
        this.field_224703_h = iTextComponentArray;
    }

    private void func_224676_i() {
        this.field_224704_i = new TranslationTextComponent("mco.upload.cancelled");
        field_224696_a.debug("Upload was cancelled");
    }

    private boolean func_224692_a(File file) {
        return file.length() < 0x140000000L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private File func_224675_b(File file) throws IOException {
        File file2;
        try (TarArchiveOutputStream tarArchiveOutputStream = null;){
            File file3 = File.createTempFile("realms-upload-file", ".tar.gz");
            tarArchiveOutputStream = new TarArchiveOutputStream(new GZIPOutputStream(new FileOutputStream(file3)));
            tarArchiveOutputStream.setLongFileMode(3);
            this.func_224669_a(tarArchiveOutputStream, file.getAbsolutePath(), "world", false);
            tarArchiveOutputStream.finish();
            file2 = file3;
        }
        return file2;
    }

    private void func_224669_a(TarArchiveOutputStream tarArchiveOutputStream, String string, String string2, boolean bl) throws IOException {
        if (!this.field_224706_k) {
            File file = new File(string);
            String string3 = bl ? string2 : string2 + file.getName();
            TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(file, string3);
            tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
            if (file.isFile()) {
                IOUtils.copy(new FileInputStream(file), tarArchiveOutputStream);
                tarArchiveOutputStream.closeArchiveEntry();
            } else {
                tarArchiveOutputStream.closeArchiveEntry();
                File[] fileArray = file.listFiles();
                if (fileArray != null) {
                    for (File file2 : fileArray) {
                        this.func_224669_a(tarArchiveOutputStream, file2.getAbsolutePath(), string3 + "/", true);
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void lambda$func_224682_h$4() {
        File file = null;
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        long l = this.field_224699_d;
        try {
            if (field_238081_b_.tryLock(1L, TimeUnit.SECONDS)) {
                UploadInfo uploadInfo = null;
                for (int i = 0; i < 20; ++i) {
                    block35: {
                        if (!this.field_224706_k) break block35;
                        this.func_224676_i();
                    }
                    try {
                        uploadInfo = realmsClient.func_224934_h(l, UploadTokenCache.func_225235_a(l));
                        if (uploadInfo == null) continue;
                        break;
                    } catch (RetryCallException retryCallException) {
                        Thread.sleep(retryCallException.field_224985_e * 1000);
                    }
                }
                if (uploadInfo == null) {
                    this.field_224704_i = new TranslationTextComponent("mco.upload.close.failure");
                }
                UploadTokenCache.func_225234_a(l, uploadInfo.func_230795_a_());
                if (!uploadInfo.func_230799_c_()) {
                    this.field_224704_i = new TranslationTextComponent("mco.upload.close.failure");
                }
                if (this.field_224706_k) {
                    this.func_224676_i();
                }
                File file2 = new File(this.minecraft.gameDir.getAbsolutePath(), "saves");
                file = this.func_224675_b(new File(file2, this.field_224698_c.getFileName()));
                if (this.field_224706_k) {
                    this.func_224676_i();
                }
                if (this.func_224692_a(file)) {
                    this.field_224704_i = new TranslationTextComponent("mco.upload.uploading", this.field_224698_c.getDisplayName());
                    FileUpload fileUpload = new FileUpload(file, this.field_224699_d, this.field_224700_e, uploadInfo, this.minecraft.getSession(), SharedConstants.getVersion().getName(), this.field_224701_f);
                    fileUpload.func_224874_a(arg_0 -> this.lambda$func_224682_h$3(l, arg_0));
                    while (!fileUpload.func_224881_b()) {
                        if (this.field_224706_k) {
                            fileUpload.func_224878_a();
                            this.func_224676_i();
                        }
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException interruptedException) {
                            field_224696_a.error("Failed to check Realms file upload status");
                        }
                    }
                }
                long l2 = file.length();
                UploadSpeed uploadSpeed = UploadSpeed.func_237682_a_(l2);
                UploadSpeed uploadSpeed2 = UploadSpeed.func_237682_a_(0x140000000L);
                if (UploadSpeed.func_237685_b_(l2, uploadSpeed).equals(UploadSpeed.func_237685_b_(0x140000000L, uploadSpeed2)) && uploadSpeed != UploadSpeed.B) {
                    UploadSpeed uploadSpeed3 = UploadSpeed.values()[uploadSpeed.ordinal() - 1];
                    this.func_238085_a_(new TranslationTextComponent("mco.upload.size.failure.line1", this.field_224698_c.getDisplayName()), new TranslationTextComponent("mco.upload.size.failure.line2", UploadSpeed.func_237685_b_(l2, uploadSpeed3), UploadSpeed.func_237685_b_(0x140000000L, uploadSpeed3)));
                }
                this.func_238085_a_(new TranslationTextComponent("mco.upload.size.failure.line1", this.field_224698_c.getDisplayName()), new TranslationTextComponent("mco.upload.size.failure.line2", UploadSpeed.func_237685_b_(l2, uploadSpeed), UploadSpeed.func_237685_b_(0x140000000L, uploadSpeed2)));
            }
            this.field_224704_i = new TranslationTextComponent("mco.upload.close.failure");
        } catch (IOException iOException) {
            this.func_238085_a_(new TranslationTextComponent("mco.upload.failed", iOException.getMessage()));
        } catch (RealmsServiceException realmsServiceException) {
            this.func_238085_a_(new TranslationTextComponent("mco.upload.failed", realmsServiceException.toString()));
        } catch (InterruptedException interruptedException) {
            field_224696_a.error("Could not acquire upload lock");
        } finally {
            this.field_224707_l = true;
            if (field_238081_b_.isHeldByCurrentThread()) {
                field_238081_b_.unlock();
                this.field_224708_m = false;
                this.field_224710_o.visible = true;
                this.field_224711_p.visible = false;
                if (file != null) {
                    field_224696_a.debug("Deleting file " + file.getAbsolutePath());
                    file.delete();
                }
            }
            return;
        }
    }

    private void lambda$func_224682_h$3(long l, UploadResult uploadResult) {
        if (uploadResult.field_225179_a >= 200 && uploadResult.field_225179_a < 300) {
            this.field_224707_l = true;
            this.field_224704_i = new TranslationTextComponent("mco.upload.done");
            this.field_224710_o.setMessage(DialogTexts.GUI_DONE);
            UploadTokenCache.func_225233_b(l);
        } else if (uploadResult.field_225179_a == 400 && uploadResult.field_225180_b != null) {
            this.func_238085_a_(new TranslationTextComponent("mco.upload.failed", uploadResult.field_225180_b));
        } else {
            this.func_238085_a_(new TranslationTextComponent("mco.upload.failed", uploadResult.field_225179_a));
        }
    }

    private void lambda$init$2() {
        if (!this.field_224709_n) {
            this.field_224709_n = true;
            this.minecraft.displayGuiScreen(this);
            this.func_224682_h();
        }
    }

    private void lambda$init$1(Button button) {
        this.func_224695_d();
    }

    private void lambda$init$0(Button button) {
        this.func_224679_c();
    }
}

