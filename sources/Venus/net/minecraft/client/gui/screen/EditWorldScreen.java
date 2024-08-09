/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.ConfirmBackupScreen;
import net.minecraft.client.gui.screen.OptimizeWorldScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.WorldGenSettingsExport;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.WorldSummary;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditWorldScreen
extends Screen {
    private static final Logger field_239014_a_ = LogManager.getLogger();
    private static final Gson field_239015_b_ = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
    private static final ITextComponent field_243441_c = new TranslationTextComponent("selectWorld.enterName");
    private Button saveButton;
    private final BooleanConsumer field_214311_b;
    private TextFieldWidget nameEdit;
    private final SaveFormat.LevelSave field_239016_r_;

    public EditWorldScreen(BooleanConsumer booleanConsumer, SaveFormat.LevelSave levelSave) {
        super(new TranslationTextComponent("selectWorld.edit.title"));
        this.field_214311_b = booleanConsumer;
        this.field_239016_r_ = levelSave;
    }

    @Override
    public void tick() {
        this.nameEdit.tick();
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        Button button = this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 0 + 5, 200, 20, new TranslationTextComponent("selectWorld.edit.resetIcon"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 24 + 5, 200, 20, new TranslationTextComponent("selectWorld.edit.openFolder"), this::lambda$init$1));
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 48 + 5, 200, 20, new TranslationTextComponent("selectWorld.edit.backup"), this::lambda$init$2));
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 72 + 5, 200, 20, new TranslationTextComponent("selectWorld.edit.backupFolder"), this::lambda$init$3));
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 96 + 5, 200, 20, new TranslationTextComponent("selectWorld.edit.optimize"), this::lambda$init$5));
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120 + 5, 200, 20, new TranslationTextComponent("selectWorld.edit.export_worldgen_settings"), this::lambda$init$8));
        this.saveButton = this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 144 + 5, 98, 20, new TranslationTextComponent("selectWorld.edit.save"), this::lambda$init$9));
        this.addButton(new Button(this.width / 2 + 2, this.height / 4 + 144 + 5, 98, 20, DialogTexts.GUI_CANCEL, this::lambda$init$10));
        button.active = this.field_239016_r_.getIconFile().isFile();
        WorldSummary worldSummary = this.field_239016_r_.readWorldSummary();
        String string = worldSummary == null ? "" : worldSummary.getDisplayName();
        this.nameEdit = new TextFieldWidget(this.font, this.width / 2 - 100, 38, 200, 20, new TranslationTextComponent("selectWorld.enterName"));
        this.nameEdit.setText(string);
        this.nameEdit.setResponder(this::lambda$init$11);
        this.children.add(this.nameEdit);
        this.setFocusedDefault(this.nameEdit);
    }

    @Override
    public void resize(Minecraft minecraft, int n, int n2) {
        String string = this.nameEdit.getText();
        this.init(minecraft, n, n2);
        this.nameEdit.setText(string);
    }

    @Override
    public void closeScreen() {
        this.field_214311_b.accept(false);
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    private void saveChanges() {
        try {
            this.field_239016_r_.updateSaveName(this.nameEdit.getText().trim());
            this.field_214311_b.accept(true);
        } catch (IOException iOException) {
            field_239014_a_.error("Failed to access world '{}'", (Object)this.field_239016_r_.getSaveName(), (Object)iOException);
            SystemToast.func_238535_a_(this.minecraft, this.field_239016_r_.getSaveName());
            this.field_214311_b.accept(true);
        }
    }

    public static void func_241651_a_(SaveFormat saveFormat, String string) {
        boolean bl = false;
        try (SaveFormat.LevelSave levelSave = saveFormat.getLevelSave(string);){
            bl = true;
            EditWorldScreen.func_239019_a_(levelSave);
        } catch (IOException iOException) {
            if (!bl) {
                SystemToast.func_238535_a_(Minecraft.getInstance(), string);
            }
            field_239014_a_.warn("Failed to create backup of level {}", (Object)string, (Object)iOException);
        }
    }

    public static boolean func_239019_a_(SaveFormat.LevelSave levelSave) {
        long l = 0L;
        IOException iOException = null;
        try {
            l = levelSave.createBackup();
        } catch (IOException iOException2) {
            iOException = iOException2;
        }
        if (iOException != null) {
            TranslationTextComponent translationTextComponent = new TranslationTextComponent("selectWorld.edit.backupFailed");
            StringTextComponent stringTextComponent = new StringTextComponent(iOException.getMessage());
            Minecraft.getInstance().getToastGui().add(new SystemToast(SystemToast.Type.WORLD_BACKUP, translationTextComponent, stringTextComponent));
            return true;
        }
        TranslationTextComponent translationTextComponent = new TranslationTextComponent("selectWorld.edit.backupCreated", levelSave.getSaveName());
        TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("selectWorld.edit.backupSize", MathHelper.ceil((double)l / 1048576.0));
        Minecraft.getInstance().getToastGui().add(new SystemToast(SystemToast.Type.WORLD_BACKUP, translationTextComponent, translationTextComponent2));
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        EditWorldScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        EditWorldScreen.drawString(matrixStack, this.font, field_243441_c, this.width / 2 - 100, 24, 0xA0A0A0);
        this.nameEdit.render(matrixStack, n, n2, f);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$11(String string) {
        this.saveButton.active = !string.trim().isEmpty();
    }

    private void lambda$init$10(Button button) {
        this.field_214311_b.accept(false);
    }

    private void lambda$init$9(Button button) {
        this.saveChanges();
    }

    private void lambda$init$8(Button button) {
        DataResult<Object> dataResult;
        Object object;
        Object object2;
        DynamicRegistries.Impl impl = DynamicRegistries.func_239770_b_();
        try {
            object2 = this.minecraft.reloadDatapacks(impl, Minecraft::loadDataPackCodec, Minecraft::loadWorld, false, this.field_239016_r_);
            try {
                object = WorldGenSettingsExport.create(JsonOps.INSTANCE, impl);
                DataResult<JsonElement> dataResult2 = DimensionGeneratorSettings.field_236201_a_.encodeStart(object, ((Minecraft.PackManager)object2).getServerConfiguration().getDimensionGeneratorSettings());
                dataResult = dataResult2.flatMap(this::lambda$init$6);
            } finally {
                if (object2 != null) {
                    ((Minecraft.PackManager)object2).close();
                }
            }
        } catch (InterruptedException | ExecutionException exception) {
            dataResult = DataResult.error("Could not parse level data!");
        }
        object2 = new StringTextComponent(dataResult.get().map(Function.identity(), DataResult.PartialResult::message));
        object = new TranslationTextComponent(dataResult.result().isPresent() ? "selectWorld.edit.export_worldgen_settings.success" : "selectWorld.edit.export_worldgen_settings.failure");
        dataResult.error().ifPresent(EditWorldScreen::lambda$init$7);
        this.minecraft.getToastGui().add(SystemToast.func_238534_a_(this.minecraft, SystemToast.Type.WORLD_GEN_SETTINGS_TRANSFER, (ITextComponent)object, (ITextComponent)object2));
    }

    private static void lambda$init$7(DataResult.PartialResult partialResult) {
        field_239014_a_.error("Error exporting world settings: {}", (Object)partialResult);
    }

    private DataResult lambda$init$6(JsonElement jsonElement) {
        Path path = this.field_239016_r_.resolveFilePath(FolderName.DOT).resolve("worldgen_settings_export.json");
        try (JsonWriter jsonWriter = field_239015_b_.newJsonWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8, new OpenOption[0]));){
            field_239015_b_.toJson(jsonElement, jsonWriter);
        } catch (JsonIOException | IOException exception) {
            return DataResult.error("Error writing file: " + exception.getMessage());
        }
        return DataResult.success(path.toString());
    }

    private void lambda$init$5(Button button) {
        this.minecraft.displayGuiScreen(new ConfirmBackupScreen(this, this::lambda$init$4, new TranslationTextComponent("optimizeWorld.confirm.title"), new TranslationTextComponent("optimizeWorld.confirm.description"), true));
    }

    private void lambda$init$4(boolean bl, boolean bl2) {
        if (bl) {
            EditWorldScreen.func_239019_a_(this.field_239016_r_);
        }
        this.minecraft.displayGuiScreen(OptimizeWorldScreen.func_239025_a_(this.minecraft, this.field_214311_b, this.minecraft.getDataFixer(), this.field_239016_r_, bl2));
    }

    private void lambda$init$3(Button button) {
        SaveFormat saveFormat = this.minecraft.getSaveLoader();
        Path path = saveFormat.getBackupsFolder();
        try {
            Files.createDirectories(Files.exists(path, new LinkOption[0]) ? path.toRealPath(new LinkOption[0]) : path, new FileAttribute[0]);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        Util.getOSType().openFile(path.toFile());
    }

    private void lambda$init$2(Button button) {
        boolean bl = EditWorldScreen.func_239019_a_(this.field_239016_r_);
        this.field_214311_b.accept(!bl);
    }

    private void lambda$init$1(Button button) {
        Util.getOSType().openFile(this.field_239016_r_.resolveFilePath(FolderName.DOT).toFile());
    }

    private void lambda$init$0(Button button) {
        FileUtils.deleteQuietly(this.field_239016_r_.getIconFile());
        button.active = false;
    }
}

