/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsResetWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsUploadScreen;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.storage.WorldSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsSelectFileToUploadScreen
extends RealmsScreen {
    private static final Logger field_224547_a = LogManager.getLogger();
    private static final ITextComponent field_243147_b = new TranslationTextComponent("selectWorld.world");
    private static final ITextComponent field_243148_c = new TranslationTextComponent("selectWorld.conversion");
    private static final ITextComponent field_243149_p = new TranslationTextComponent("mco.upload.hardcore").mergeStyle(TextFormatting.DARK_RED);
    private static final ITextComponent field_243150_q = new TranslationTextComponent("selectWorld.cheats");
    private static final DateFormat field_224552_f = new SimpleDateFormat();
    private final RealmsResetWorldScreen field_224548_b;
    private final long field_224549_c;
    private final int field_224550_d;
    private Button field_224551_e;
    private List<WorldSummary> field_224553_g = Lists.newArrayList();
    private int field_224554_h = -1;
    private WorldSelectionList field_224555_i;
    private RealmsLabel field_224559_m;
    private RealmsLabel field_224560_n;
    private RealmsLabel field_224561_o;
    private final Runnable field_237967_A_;

    public RealmsSelectFileToUploadScreen(long l, int n, RealmsResetWorldScreen realmsResetWorldScreen, Runnable runnable) {
        this.field_224548_b = realmsResetWorldScreen;
        this.field_224549_c = l;
        this.field_224550_d = n;
        this.field_237967_A_ = runnable;
    }

    private void func_224541_a() throws Exception {
        this.field_224553_g = this.minecraft.getSaveLoader().getSaveList().stream().sorted(RealmsSelectFileToUploadScreen::lambda$func_224541_a$0).collect(Collectors.toList());
        for (WorldSummary worldSummary : this.field_224553_g) {
            this.field_224555_i.func_237986_a_(worldSummary);
        }
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_224555_i = new WorldSelectionList(this);
        try {
            this.func_224541_a();
        } catch (Exception exception) {
            field_224547_a.error("Couldn't load level list", (Throwable)exception);
            this.minecraft.displayGuiScreen(new RealmsGenericErrorScreen(new StringTextComponent("Unable to load worlds"), ITextComponent.getTextComponentOrEmpty(exception.getMessage()), this.field_224548_b));
            return;
        }
        this.addListener(this.field_224555_i);
        this.field_224551_e = this.addButton(new Button(this.width / 2 - 154, this.height - 32, 153, 20, new TranslationTextComponent("mco.upload.button.name"), this::lambda$init$1));
        this.field_224551_e.active = this.field_224554_h >= 0 && this.field_224554_h < this.field_224553_g.size();
        this.addButton(new Button(this.width / 2 + 6, this.height - 32, 153, 20, DialogTexts.GUI_BACK, this::lambda$init$2));
        this.field_224559_m = this.addListener(new RealmsLabel(new TranslationTextComponent("mco.upload.select.world.title"), this.width / 2, 13, 0xFFFFFF));
        this.field_224560_n = this.addListener(new RealmsLabel(new TranslationTextComponent("mco.upload.select.world.subtitle"), this.width / 2, RealmsSelectFileToUploadScreen.func_239562_k_(-1), 0xA0A0A0));
        this.field_224561_o = this.field_224553_g.isEmpty() ? this.addListener(new RealmsLabel(new TranslationTextComponent("mco.upload.select.world.none"), this.width / 2, this.height / 2 - 20, 0xFFFFFF)) : null;
        this.func_231411_u_();
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    private void func_224544_b() {
        if (this.field_224554_h != -1 && !this.field_224553_g.get(this.field_224554_h).isHardcoreModeEnabled()) {
            WorldSummary worldSummary = this.field_224553_g.get(this.field_224554_h);
            this.minecraft.displayGuiScreen(new RealmsUploadScreen(this.field_224549_c, this.field_224550_d, this.field_224548_b, worldSummary, this.field_237967_A_));
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.field_224555_i.render(matrixStack, n, n2, f);
        this.field_224559_m.func_239560_a_(this, matrixStack);
        this.field_224560_n.func_239560_a_(this, matrixStack);
        if (this.field_224561_o != null) {
            this.field_224561_o.func_239560_a_(this, matrixStack);
        }
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.field_224548_b);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private static ITextComponent func_237977_c_(WorldSummary worldSummary) {
        return worldSummary.getEnumGameType().getDisplayName();
    }

    private static String func_237979_d_(WorldSummary worldSummary) {
        return field_224552_f.format(new Date(worldSummary.getLastTimePlayed()));
    }

    private void lambda$init$2(Button button) {
        this.minecraft.displayGuiScreen(this.field_224548_b);
    }

    private void lambda$init$1(Button button) {
        this.func_224544_b();
    }

    private static int lambda$func_224541_a$0(WorldSummary worldSummary, WorldSummary worldSummary2) {
        if (worldSummary.getLastTimePlayed() < worldSummary2.getLastTimePlayed()) {
            return 0;
        }
        return worldSummary.getLastTimePlayed() > worldSummary2.getLastTimePlayed() ? -1 : worldSummary.getFileName().compareTo(worldSummary2.getFileName());
    }

    static FontRenderer access$000(RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
        return realmsSelectFileToUploadScreen.font;
    }

    static FontRenderer access$100(RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
        return realmsSelectFileToUploadScreen.font;
    }

    static FontRenderer access$200(RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
        return realmsSelectFileToUploadScreen.font;
    }

    static int access$300(int n) {
        return RealmsSelectFileToUploadScreen.func_239562_k_(n);
    }

    class WorldSelectionList
    extends RealmsObjectSelectionList<WorldSelectionEntry> {
        final RealmsSelectFileToUploadScreen this$0;

        public WorldSelectionList(RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen) {
            this.this$0 = realmsSelectFileToUploadScreen;
            super(realmsSelectFileToUploadScreen.width, realmsSelectFileToUploadScreen.height, RealmsSelectFileToUploadScreen.access$300(0), realmsSelectFileToUploadScreen.height - 40, 36);
        }

        public void func_237986_a_(WorldSummary worldSummary) {
            RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen = this.this$0;
            Objects.requireNonNull(realmsSelectFileToUploadScreen);
            this.addEntry(new WorldSelectionEntry(realmsSelectFileToUploadScreen, worldSummary));
        }

        @Override
        public int getMaxPosition() {
            return this.this$0.field_224553_g.size() * 36;
        }

        @Override
        public boolean isFocused() {
            return this.this$0.getListener() == this;
        }

        @Override
        public void renderBackground(MatrixStack matrixStack) {
            this.this$0.renderBackground(matrixStack);
        }

        @Override
        public void func_231400_a_(int n) {
            this.func_239561_k_(n);
            if (n != -1) {
                WorldSummary worldSummary = this.this$0.field_224553_g.get(n);
                String string = I18n.format("narrator.select.list.position", n + 1, this.this$0.field_224553_g.size());
                String string2 = RealmsNarratorHelper.func_239552_b_(Arrays.asList(worldSummary.getDisplayName(), RealmsSelectFileToUploadScreen.func_237979_d_(worldSummary), RealmsSelectFileToUploadScreen.func_237977_c_(worldSummary).getString(), string));
                RealmsNarratorHelper.func_239550_a_(I18n.format("narrator.select", string2));
            }
        }

        @Override
        public void setSelected(@Nullable WorldSelectionEntry worldSelectionEntry) {
            super.setSelected(worldSelectionEntry);
            this.this$0.field_224554_h = this.getEventListeners().indexOf(worldSelectionEntry);
            this.this$0.field_224551_e.active = this.this$0.field_224554_h >= 0 && this.this$0.field_224554_h < this.getItemCount() && !this.this$0.field_224553_g.get(this.this$0.field_224554_h).isHardcoreModeEnabled();
        }

        @Override
        public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
            this.setSelected((WorldSelectionEntry)abstractListEntry);
        }
    }

    class WorldSelectionEntry
    extends ExtendedList.AbstractListEntry<WorldSelectionEntry> {
        private final WorldSummary field_223759_a;
        private final String field_243160_c;
        private final String field_243161_d;
        private final ITextComponent field_243162_e;
        final RealmsSelectFileToUploadScreen this$0;

        public WorldSelectionEntry(RealmsSelectFileToUploadScreen realmsSelectFileToUploadScreen, WorldSummary worldSummary) {
            this.this$0 = realmsSelectFileToUploadScreen;
            this.field_223759_a = worldSummary;
            this.field_243160_c = worldSummary.getDisplayName();
            this.field_243161_d = worldSummary.getFileName() + " (" + RealmsSelectFileToUploadScreen.func_237979_d_(worldSummary) + ")";
            if (worldSummary.requiresConversion()) {
                this.field_243162_e = field_243148_c;
            } else {
                ITextComponent iTextComponent = worldSummary.isHardcoreModeEnabled() ? field_243149_p : RealmsSelectFileToUploadScreen.func_237977_c_(worldSummary);
                if (worldSummary.getCheatsEnabled()) {
                    iTextComponent = iTextComponent.deepCopy().appendString(", ").append(field_243150_q);
                }
                this.field_243162_e = iTextComponent;
            }
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.func_237985_a_(matrixStack, this.field_223759_a, n, n3, n2);
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            this.this$0.field_224555_i.func_231400_a_(this.this$0.field_224553_g.indexOf(this.field_223759_a));
            return false;
        }

        protected void func_237985_a_(MatrixStack matrixStack, WorldSummary worldSummary, int n, int n2, int n3) {
            Object object = this.field_243160_c.isEmpty() ? field_243147_b + " " + (n + 1) : this.field_243160_c;
            RealmsSelectFileToUploadScreen.access$000(this.this$0).drawString(matrixStack, (String)object, n2 + 2, n3 + 1, 0xFFFFFF);
            RealmsSelectFileToUploadScreen.access$100(this.this$0).drawString(matrixStack, this.field_243161_d, n2 + 2, n3 + 12, 0x808080);
            RealmsSelectFileToUploadScreen.access$200(this.this$0).func_243248_b(matrixStack, this.field_243162_e, n2 + 2, n3 + 12 + 10, 0x808080);
        }
    }
}

