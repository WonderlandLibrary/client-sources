/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.AlertScreen;
import net.minecraft.client.gui.screen.ConfirmBackupScreen;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.EditWorldScreen;
import net.minecraft.client.gui.screen.ErrorScreen;
import net.minecraft.client.gui.screen.WorkingScreen;
import net.minecraft.client.gui.screen.WorldSelectionScreen;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.WorldSummary;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldSelectionList
extends ExtendedList<Entry> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateFormat field_214377_b = new SimpleDateFormat();
    private static final ResourceLocation field_214378_c = new ResourceLocation("textures/misc/unknown_server.png");
    private static final ResourceLocation field_214379_d = new ResourceLocation("textures/gui/world_selection.png");
    private static final ITextComponent field_243462_r = new TranslationTextComponent("selectWorld.tooltip.fromNewerVersion1").mergeStyle(TextFormatting.RED);
    private static final ITextComponent field_243463_s = new TranslationTextComponent("selectWorld.tooltip.fromNewerVersion2").mergeStyle(TextFormatting.RED);
    private static final ITextComponent field_243464_t = new TranslationTextComponent("selectWorld.tooltip.snapshot1").mergeStyle(TextFormatting.GOLD);
    private static final ITextComponent field_243465_u = new TranslationTextComponent("selectWorld.tooltip.snapshot2").mergeStyle(TextFormatting.GOLD);
    private static final ITextComponent field_243466_v = new TranslationTextComponent("selectWorld.locked").mergeStyle(TextFormatting.RED);
    private final WorldSelectionScreen worldSelection;
    @Nullable
    private List<WorldSummary> field_212331_y;

    public WorldSelectionList(WorldSelectionScreen worldSelectionScreen, Minecraft minecraft, int n, int n2, int n3, int n4, int n5, Supplier<String> supplier, @Nullable WorldSelectionList worldSelectionList) {
        super(minecraft, n, n2, n3, n4, n5);
        this.worldSelection = worldSelectionScreen;
        if (worldSelectionList != null) {
            this.field_212331_y = worldSelectionList.field_212331_y;
        }
        this.func_212330_a(supplier, true);
    }

    public void func_212330_a(Supplier<String> supplier, boolean bl) {
        this.clearEntries();
        SaveFormat saveFormat = this.minecraft.getSaveLoader();
        if (this.field_212331_y == null || bl) {
            try {
                this.field_212331_y = saveFormat.getSaveList();
            } catch (AnvilConverterException anvilConverterException) {
                LOGGER.error("Couldn't load level list", (Throwable)anvilConverterException);
                this.minecraft.displayGuiScreen(new ErrorScreen(new TranslationTextComponent("selectWorld.unable_to_load"), new StringTextComponent(anvilConverterException.getMessage())));
                return;
            }
            Collections.sort(this.field_212331_y);
        }
        if (this.field_212331_y.isEmpty()) {
            this.minecraft.displayGuiScreen(CreateWorldScreen.func_243425_a(null));
        } else {
            String string = supplier.get().toLowerCase(Locale.ROOT);
            for (WorldSummary worldSummary : this.field_212331_y) {
                if (!worldSummary.getDisplayName().toLowerCase(Locale.ROOT).contains(string) && !worldSummary.getFileName().toLowerCase(Locale.ROOT).contains(string)) continue;
                this.addEntry(new Entry(this, this, worldSummary));
            }
        }
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 20;
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 50;
    }

    @Override
    protected boolean isFocused() {
        return this.worldSelection.getListener() == this;
    }

    @Override
    public void setSelected(@Nullable Entry entry) {
        super.setSelected(entry);
        if (entry != null) {
            WorldSummary worldSummary = entry.field_214451_d;
            NarratorChatListener.INSTANCE.say(new TranslationTextComponent("narrator.select", new TranslationTextComponent("narrator.select.world", worldSummary.getDisplayName(), new Date(worldSummary.getLastTimePlayed()), worldSummary.isHardcoreModeEnabled() ? new TranslationTextComponent("gameMode.hardcore") : new TranslationTextComponent("gameMode." + worldSummary.getEnumGameType().getName()), worldSummary.getCheatsEnabled() ? new TranslationTextComponent("selectWorld.cheats") : StringTextComponent.EMPTY, worldSummary.getVersionName())).getString());
        }
        this.worldSelection.func_214324_a(entry != null && !entry.field_214451_d.isLocked());
    }

    @Override
    protected void moveSelection(AbstractList.Ordering ordering) {
        this.func_241572_a_(ordering, WorldSelectionList::lambda$moveSelection$0);
    }

    public Optional<Entry> func_214376_a() {
        return Optional.ofNullable((Entry)this.getSelected());
    }

    public WorldSelectionScreen getGuiWorldSelection() {
        return this.worldSelection;
    }

    @Override
    public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
        this.setSelected((Entry)abstractListEntry);
    }

    private static boolean lambda$moveSelection$0(Entry entry) {
        return !entry.field_214451_d.isLocked();
    }

    public final class Entry
    extends AbstractList.AbstractListEntry<Entry>
    implements AutoCloseable {
        private final Minecraft field_214449_b;
        private final WorldSelectionScreen field_214450_c;
        private final WorldSummary field_214451_d;
        private final ResourceLocation field_214452_e;
        private File field_214453_f;
        @Nullable
        private final DynamicTexture field_214454_g;
        private long field_214455_h;
        final WorldSelectionList this$0;

        public Entry(WorldSelectionList worldSelectionList, WorldSelectionList worldSelectionList2, WorldSummary worldSummary) {
            this.this$0 = worldSelectionList;
            this.field_214450_c = worldSelectionList2.getGuiWorldSelection();
            this.field_214451_d = worldSummary;
            this.field_214449_b = Minecraft.getInstance();
            String string = worldSummary.getFileName();
            this.field_214452_e = new ResourceLocation("minecraft", "worlds/" + Util.func_244361_a(string, ResourceLocation::validatePathChar) + "/" + Hashing.sha1().hashUnencodedChars(string) + "/icon");
            this.field_214453_f = worldSummary.getIconFile();
            if (!this.field_214453_f.isFile()) {
                this.field_214453_f = null;
            }
            this.field_214454_g = this.func_214446_f();
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            Object object = this.field_214451_d.getDisplayName();
            String string = this.field_214451_d.getFileName() + " (" + field_214377_b.format(new Date(this.field_214451_d.getLastTimePlayed())) + ")";
            if (StringUtils.isEmpty((CharSequence)object)) {
                object = I18n.format("selectWorld.world", new Object[0]) + " " + (n + 1);
            }
            ITextComponent iTextComponent = this.field_214451_d.getDescription();
            this.field_214449_b.fontRenderer.drawString(matrixStack, (String)object, n3 + 32 + 3, n2 + 1, 0xFFFFFF);
            this.field_214449_b.fontRenderer.drawString(matrixStack, string, n3 + 32 + 3, n2 + 9 + 3, 0x808080);
            this.field_214449_b.fontRenderer.func_243248_b(matrixStack, iTextComponent, n3 + 32 + 3, n2 + 9 + 9 + 3, 0x808080);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_214449_b.getTextureManager().bindTexture(this.field_214454_g != null ? this.field_214452_e : field_214378_c);
            RenderSystem.enableBlend();
            AbstractGui.blit(matrixStack, n3, n2, 0.0f, 0.0f, 32, 32, 32, 32);
            RenderSystem.disableBlend();
            if (this.field_214449_b.gameSettings.touchscreen || bl) {
                int n8;
                this.field_214449_b.getTextureManager().bindTexture(field_214379_d);
                AbstractGui.fill(matrixStack, n3, n2, n3 + 32, n2 + 32, -1601138544);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                int n9 = n6 - n3;
                boolean bl2 = n9 < 32;
                int n10 = n8 = bl2 ? 32 : 0;
                if (this.field_214451_d.isLocked()) {
                    AbstractGui.blit(matrixStack, n3, n2, 96.0f, n8, 32, 32, 256, 256);
                    if (bl2) {
                        this.field_214450_c.func_239026_b_(this.field_214449_b.fontRenderer.trimStringToWidth(field_243466_v, 175));
                    }
                } else if (this.field_214451_d.markVersionInList()) {
                    AbstractGui.blit(matrixStack, n3, n2, 32.0f, n8, 32, 32, 256, 256);
                    if (this.field_214451_d.askToOpenWorld()) {
                        AbstractGui.blit(matrixStack, n3, n2, 96.0f, n8, 32, 32, 256, 256);
                        if (bl2) {
                            this.field_214450_c.func_239026_b_(ImmutableList.of(field_243462_r.func_241878_f(), field_243463_s.func_241878_f()));
                        }
                    } else if (!SharedConstants.getVersion().isStable()) {
                        AbstractGui.blit(matrixStack, n3, n2, 64.0f, n8, 32, 32, 256, 256);
                        if (bl2) {
                            this.field_214450_c.func_239026_b_(ImmutableList.of(field_243464_t.func_241878_f(), field_243465_u.func_241878_f()));
                        }
                    }
                } else {
                    AbstractGui.blit(matrixStack, n3, n2, 0.0f, n8, 32, 32, 256, 256);
                }
            }
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            if (this.field_214451_d.isLocked()) {
                return false;
            }
            this.this$0.setSelected(this);
            this.field_214450_c.func_214324_a(this.this$0.func_214376_a().isPresent());
            if (d - (double)this.this$0.getRowLeft() <= 32.0) {
                this.func_214438_a();
                return false;
            }
            if (Util.milliTime() - this.field_214455_h < 250L) {
                this.func_214438_a();
                return false;
            }
            this.field_214455_h = Util.milliTime();
            return true;
        }

        public void func_214438_a() {
            if (!this.field_214451_d.isLocked()) {
                if (this.field_214451_d.askToCreateBackup()) {
                    TranslationTextComponent translationTextComponent = new TranslationTextComponent("selectWorld.backupQuestion");
                    TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("selectWorld.backupWarning", this.field_214451_d.getVersionName(), SharedConstants.getVersion().getName());
                    this.field_214449_b.displayGuiScreen(new ConfirmBackupScreen(this.field_214450_c, this::lambda$func_214438_a$0, translationTextComponent, translationTextComponent2, false));
                } else if (this.field_214451_d.askToOpenWorld()) {
                    this.field_214449_b.displayGuiScreen(new ConfirmScreen(this::lambda$func_214438_a$2, new TranslationTextComponent("selectWorld.versionQuestion"), new TranslationTextComponent("selectWorld.versionWarning", this.field_214451_d.getVersionName(), new TranslationTextComponent("selectWorld.versionJoinButton"), DialogTexts.GUI_CANCEL)));
                } else {
                    this.func_214443_e();
                }
            }
        }

        public void func_214442_b() {
            this.field_214449_b.displayGuiScreen(new ConfirmScreen(this::lambda$func_214442_b$4, new TranslationTextComponent("selectWorld.deleteQuestion"), new TranslationTextComponent("selectWorld.deleteWarning", this.field_214451_d.getDisplayName()), new TranslationTextComponent("selectWorld.deleteButton"), DialogTexts.GUI_CANCEL));
        }

        public void func_214444_c() {
            String string = this.field_214451_d.getFileName();
            try {
                SaveFormat.LevelSave levelSave = this.field_214449_b.getSaveLoader().getLevelSave(string);
                this.field_214449_b.displayGuiScreen(new EditWorldScreen(arg_0 -> this.lambda$func_214444_c$6(levelSave, string, arg_0), levelSave));
            } catch (IOException iOException) {
                SystemToast.func_238535_a_(this.field_214449_b, string);
                LOGGER.error("Failed to access level {}", (Object)string, (Object)iOException);
                this.this$0.func_212330_a(this::lambda$func_214444_c$7, false);
            }
        }

        public void func_214445_d() {
            this.func_241653_f_();
            DynamicRegistries.Impl impl = DynamicRegistries.func_239770_b_();
            try (SaveFormat.LevelSave levelSave = this.field_214449_b.getSaveLoader().getLevelSave(this.field_214451_d.getFileName());
                 Minecraft.PackManager packManager = this.field_214449_b.reloadDatapacks(impl, Minecraft::loadDataPackCodec, Minecraft::loadWorld, false, levelSave);){
                WorldSettings worldSettings = packManager.getServerConfiguration().getWorldSettings();
                DatapackCodec datapackCodec = worldSettings.getDatapackCodec();
                DimensionGeneratorSettings dimensionGeneratorSettings = packManager.getServerConfiguration().getDimensionGeneratorSettings();
                Path path = CreateWorldScreen.func_238943_a_(levelSave.resolveFilePath(FolderName.DATAPACKS), this.field_214449_b);
                if (dimensionGeneratorSettings.func_236229_j_()) {
                    this.field_214449_b.displayGuiScreen(new ConfirmScreen(arg_0 -> this.lambda$func_214445_d$8(worldSettings, dimensionGeneratorSettings, path, datapackCodec, impl, arg_0), new TranslationTextComponent("selectWorld.recreate.customized.title"), new TranslationTextComponent("selectWorld.recreate.customized.text"), DialogTexts.GUI_PROCEED, DialogTexts.GUI_CANCEL));
                } else {
                    this.field_214449_b.displayGuiScreen(new CreateWorldScreen(this.field_214450_c, worldSettings, dimensionGeneratorSettings, path, datapackCodec, impl));
                }
            } catch (Exception exception) {
                LOGGER.error("Unable to recreate world", (Throwable)exception);
                this.field_214449_b.displayGuiScreen(new AlertScreen(this::lambda$func_214445_d$9, new TranslationTextComponent("selectWorld.recreate.error.title"), new TranslationTextComponent("selectWorld.recreate.error.text")));
            }
        }

        private void func_214443_e() {
            this.field_214449_b.getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            if (this.field_214449_b.getSaveLoader().canLoadWorld(this.field_214451_d.getFileName())) {
                this.func_241653_f_();
                this.field_214449_b.loadWorld(this.field_214451_d.getFileName());
            }
        }

        private void func_241653_f_() {
            this.field_214449_b.forcedScreenTick(new DirtMessageScreen(new TranslationTextComponent("selectWorld.data_read")));
        }

        @Nullable
        private DynamicTexture func_214446_f() {
            boolean bl;
            boolean bl2 = bl = this.field_214453_f != null && this.field_214453_f.isFile();
            if (bl) {
                DynamicTexture dynamicTexture;
                FileInputStream fileInputStream = new FileInputStream(this.field_214453_f);
                try {
                    NativeImage nativeImage = NativeImage.read(fileInputStream);
                    Validate.validState(nativeImage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                    Validate.validState(nativeImage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                    DynamicTexture dynamicTexture2 = new DynamicTexture(nativeImage);
                    this.field_214449_b.getTextureManager().loadTexture(this.field_214452_e, dynamicTexture2);
                    dynamicTexture = dynamicTexture2;
                } catch (Throwable throwable) {
                    try {
                        try {
                            ((InputStream)fileInputStream).close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        throw throwable;
                    } catch (Throwable throwable3) {
                        LOGGER.error("Invalid icon for world {}", (Object)this.field_214451_d.getFileName(), (Object)throwable3);
                        this.field_214453_f = null;
                        return null;
                    }
                }
                ((InputStream)fileInputStream).close();
                return dynamicTexture;
            }
            this.field_214449_b.getTextureManager().deleteTexture(this.field_214452_e);
            return null;
        }

        @Override
        public void close() {
            if (this.field_214454_g != null) {
                this.field_214454_g.close();
            }
        }

        private void lambda$func_214445_d$9() {
            this.field_214449_b.displayGuiScreen(this.field_214450_c);
        }

        private void lambda$func_214445_d$8(WorldSettings worldSettings, DimensionGeneratorSettings dimensionGeneratorSettings, Path path, DatapackCodec datapackCodec, DynamicRegistries.Impl impl, boolean bl) {
            this.field_214449_b.displayGuiScreen(bl ? new CreateWorldScreen(this.field_214450_c, worldSettings, dimensionGeneratorSettings, path, datapackCodec, impl) : this.field_214450_c);
        }

        private String lambda$func_214444_c$7() {
            return this.field_214450_c.searchField.getText();
        }

        private void lambda$func_214444_c$6(SaveFormat.LevelSave levelSave, String string, boolean bl) {
            try {
                levelSave.close();
            } catch (IOException iOException) {
                LOGGER.error("Failed to unlock level {}", (Object)string, (Object)iOException);
            }
            if (bl) {
                this.this$0.func_212330_a(this::lambda$func_214444_c$5, false);
            }
            this.field_214449_b.displayGuiScreen(this.field_214450_c);
        }

        private String lambda$func_214444_c$5() {
            return this.field_214450_c.searchField.getText();
        }

        private void lambda$func_214442_b$4(boolean bl) {
            if (bl) {
                this.field_214449_b.displayGuiScreen(new WorkingScreen());
                SaveFormat saveFormat = this.field_214449_b.getSaveLoader();
                String string = this.field_214451_d.getFileName();
                try (SaveFormat.LevelSave levelSave = saveFormat.getLevelSave(string);){
                    levelSave.deleteSave();
                } catch (IOException iOException) {
                    SystemToast.func_238538_b_(this.field_214449_b, string);
                    LOGGER.error("Failed to delete world {}", (Object)string, (Object)iOException);
                }
                this.this$0.func_212330_a(this::lambda$func_214442_b$3, false);
            }
            this.field_214449_b.displayGuiScreen(this.field_214450_c);
        }

        private String lambda$func_214442_b$3() {
            return this.field_214450_c.searchField.getText();
        }

        private void lambda$func_214438_a$2(boolean bl) {
            if (bl) {
                try {
                    this.func_214443_e();
                } catch (Exception exception) {
                    LOGGER.error("Failure to open 'future world'", (Throwable)exception);
                    this.field_214449_b.displayGuiScreen(new AlertScreen(this::lambda$func_214438_a$1, new TranslationTextComponent("selectWorld.futureworld.error.title"), new TranslationTextComponent("selectWorld.futureworld.error.text")));
                }
            } else {
                this.field_214449_b.displayGuiScreen(this.field_214450_c);
            }
        }

        private void lambda$func_214438_a$1() {
            this.field_214449_b.displayGuiScreen(this.field_214450_c);
        }

        private void lambda$func_214438_a$0(boolean bl, boolean bl2) {
            if (bl) {
                String string = this.field_214451_d.getFileName();
                try (SaveFormat.LevelSave levelSave = this.field_214449_b.getSaveLoader().getLevelSave(string);){
                    EditWorldScreen.func_239019_a_(levelSave);
                } catch (IOException iOException) {
                    SystemToast.func_238535_a_(this.field_214449_b, string);
                    LOGGER.error("Failed to backup level {}", (Object)string, (Object)iOException);
                }
            }
            this.func_214443_e();
        }
    }
}

