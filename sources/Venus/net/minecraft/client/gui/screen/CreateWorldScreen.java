/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.BiomeGeneratorTypeScreens;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.EditGamerulesScreen;
import net.minecraft.client.gui.screen.PackScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldOptionsScreen;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.Commands;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.FolderPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.ServerPackFinder;
import net.minecraft.util.FileUtil;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.SaveFormat;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateWorldScreen
extends Screen {
    private static final Logger field_238935_p_ = LogManager.getLogger();
    private static final ITextComponent field_243417_q = new TranslationTextComponent("selectWorld.gameMode");
    private static final ITextComponent field_243418_r = new TranslationTextComponent("selectWorld.enterSeed");
    private static final ITextComponent field_243419_s = new TranslationTextComponent("selectWorld.seedInfo");
    private static final ITextComponent field_243420_t = new TranslationTextComponent("selectWorld.enterName");
    private static final ITextComponent field_243421_u = new TranslationTextComponent("selectWorld.resultFolder");
    private static final ITextComponent field_243422_v = new TranslationTextComponent("selectWorld.allowCommands.info");
    private final Screen parentScreen;
    private TextFieldWidget worldNameField;
    private String saveDirName;
    private GameMode field_228197_f_ = GameMode.SURVIVAL;
    @Nullable
    private GameMode field_228198_g_;
    private Difficulty field_238936_v_ = Difficulty.NORMAL;
    private Difficulty field_238937_w_ = Difficulty.NORMAL;
    private boolean allowCheats;
    private boolean allowCheatsWasSetByUser;
    public boolean hardCoreMode;
    protected DatapackCodec field_238933_b_;
    @Nullable
    private Path field_238928_A_;
    @Nullable
    private ResourcePackList field_243416_G;
    private boolean inMoreWorldOptionsDisplay;
    private Button btnCreateWorld;
    private Button btnGameMode;
    private Button field_238929_E_;
    private Button btnMoreOptions;
    private Button field_238930_G_;
    private Button field_238931_H_;
    private Button btnAllowCommands;
    private ITextComponent gameModeDesc1;
    private ITextComponent gameModeDesc2;
    private String worldName;
    private GameRules field_238932_M_ = new GameRules();
    public final WorldOptionsScreen field_238934_c_;

    public CreateWorldScreen(@Nullable Screen screen, WorldSettings worldSettings, DimensionGeneratorSettings dimensionGeneratorSettings, @Nullable Path path, DatapackCodec datapackCodec, DynamicRegistries.Impl impl) {
        this(screen, datapackCodec, new WorldOptionsScreen(impl, dimensionGeneratorSettings, BiomeGeneratorTypeScreens.func_239079_a_(dimensionGeneratorSettings), OptionalLong.of(dimensionGeneratorSettings.getSeed())));
        this.worldName = worldSettings.getWorldName();
        this.allowCheats = worldSettings.isCommandsAllowed();
        this.allowCheatsWasSetByUser = true;
        this.field_238937_w_ = this.field_238936_v_ = worldSettings.getDifficulty();
        this.field_238932_M_.func_234899_a_(worldSettings.getGameRules(), null);
        if (worldSettings.isHardcoreEnabled()) {
            this.field_228197_f_ = GameMode.HARDCORE;
        } else if (worldSettings.getGameType().isSurvivalOrAdventure()) {
            this.field_228197_f_ = GameMode.SURVIVAL;
        } else if (worldSettings.getGameType().isCreative()) {
            this.field_228197_f_ = GameMode.CREATIVE;
        }
        this.field_238928_A_ = path;
    }

    public static CreateWorldScreen func_243425_a(@Nullable Screen screen) {
        DynamicRegistries.Impl impl = DynamicRegistries.func_239770_b_();
        return new CreateWorldScreen(screen, DatapackCodec.VANILLA_CODEC, new WorldOptionsScreen(impl, DimensionGeneratorSettings.func_242751_a(impl.getRegistry(Registry.DIMENSION_TYPE_KEY), impl.getRegistry(Registry.BIOME_KEY), impl.getRegistry(Registry.NOISE_SETTINGS_KEY)), Optional.of(BiomeGeneratorTypeScreens.field_239066_a_), OptionalLong.empty()));
    }

    private CreateWorldScreen(@Nullable Screen screen, DatapackCodec datapackCodec, WorldOptionsScreen worldOptionsScreen) {
        super(new TranslationTextComponent("selectWorld.create"));
        this.parentScreen = screen;
        this.worldName = I18n.format("selectWorld.newWorld", new Object[0]);
        this.field_238933_b_ = datapackCodec;
        this.field_238934_c_ = worldOptionsScreen;
    }

    @Override
    public void tick() {
        this.worldNameField.tick();
        this.field_238934_c_.tick();
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.worldNameField = new TextFieldWidget(this, this.font, this.width / 2 - 100, 60, 200, 20, (ITextComponent)new TranslationTextComponent("selectWorld.enterName")){
            final CreateWorldScreen this$0;
            {
                this.this$0 = createWorldScreen;
                super(fontRenderer, n, n2, n3, n4, iTextComponent);
            }

            @Override
            protected IFormattableTextComponent getNarrationMessage() {
                return super.getNarrationMessage().appendString(". ").append(new TranslationTextComponent("selectWorld.resultFolder")).appendString(" ").appendString(this.this$0.saveDirName);
            }
        };
        this.worldNameField.setText(this.worldName);
        this.worldNameField.setResponder(this::lambda$init$0);
        this.children.add(this.worldNameField);
        int n = this.width / 2 - 155;
        int n2 = this.width / 2 + 5;
        this.btnGameMode = this.addButton(new Button(this, n, 100, 150, 20, StringTextComponent.EMPTY, this::lambda$init$1){
            final CreateWorldScreen this$0;
            {
                this.this$0 = createWorldScreen;
                super(n, n2, n3, n4, iTextComponent, iPressable);
            }

            @Override
            public ITextComponent getMessage() {
                return new TranslationTextComponent("options.generic_value", field_243417_q, new TranslationTextComponent("selectWorld.gameMode." + this.this$0.field_228197_f_.field_228217_e_));
            }

            @Override
            protected IFormattableTextComponent getNarrationMessage() {
                return super.getNarrationMessage().appendString(". ").append(this.this$0.gameModeDesc1).appendString(" ").append(this.this$0.gameModeDesc2);
            }
        });
        this.field_238929_E_ = this.addButton(new Button(this, n2, 100, 150, 20, new TranslationTextComponent("options.difficulty"), this::lambda$init$2){
            final CreateWorldScreen this$0;
            {
                this.this$0 = createWorldScreen;
                super(n, n2, n3, n4, iTextComponent, iPressable);
            }

            @Override
            public ITextComponent getMessage() {
                return new TranslationTextComponent("options.difficulty").appendString(": ").append(this.this$0.field_238937_w_.getDisplayName());
            }
        });
        this.btnAllowCommands = this.addButton(new Button(this, n, 151, 150, 20, new TranslationTextComponent("selectWorld.allowCommands"), this::lambda$init$3){
            final CreateWorldScreen this$0;
            {
                this.this$0 = createWorldScreen;
                super(n, n2, n3, n4, iTextComponent, iPressable);
            }

            @Override
            public ITextComponent getMessage() {
                return DialogTexts.getComposedOptionMessage(super.getMessage(), this.this$0.allowCheats && !this.this$0.hardCoreMode);
            }

            @Override
            protected IFormattableTextComponent getNarrationMessage() {
                return super.getNarrationMessage().appendString(". ").append(new TranslationTextComponent("selectWorld.allowCommands.info"));
            }
        });
        this.field_238931_H_ = this.addButton(new Button(n2, 151, 150, 20, new TranslationTextComponent("selectWorld.dataPacks"), this::lambda$init$4));
        this.field_238930_G_ = this.addButton(new Button(n, 185, 150, 20, new TranslationTextComponent("selectWorld.gameRules"), this::lambda$init$7));
        this.field_238934_c_.func_239048_a_(this, this.minecraft, this.font);
        this.btnMoreOptions = this.addButton(new Button(n2, 185, 150, 20, new TranslationTextComponent("selectWorld.moreWorldOptions"), this::lambda$init$8));
        this.btnCreateWorld = this.addButton(new Button(n, this.height - 28, 150, 20, new TranslationTextComponent("selectWorld.create"), this::lambda$init$9));
        this.btnCreateWorld.active = !this.worldName.isEmpty();
        this.addButton(new Button(n2, this.height - 28, 150, 20, DialogTexts.GUI_CANCEL, this::lambda$init$10));
        this.func_238955_g_();
        this.setFocusedDefault(this.worldNameField);
        this.func_228200_a_(this.field_228197_f_);
        this.calcSaveDirName();
    }

    private void func_228199_a_() {
        this.gameModeDesc1 = new TranslationTextComponent("selectWorld.gameMode." + this.field_228197_f_.field_228217_e_ + ".line1");
        this.gameModeDesc2 = new TranslationTextComponent("selectWorld.gameMode." + this.field_228197_f_.field_228217_e_ + ".line2");
    }

    private void calcSaveDirName() {
        this.saveDirName = this.worldNameField.getText().trim();
        if (this.saveDirName.isEmpty()) {
            this.saveDirName = "World";
        }
        try {
            this.saveDirName = FileUtil.findAvailableName(this.minecraft.getSaveLoader().getSavesDir(), this.saveDirName, "");
        } catch (Exception exception) {
            this.saveDirName = "World";
            try {
                this.saveDirName = FileUtil.findAvailableName(this.minecraft.getSaveLoader().getSavesDir(), this.saveDirName, "");
            } catch (Exception exception2) {
                throw new RuntimeException("Could not create save folder", exception2);
            }
        }
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    private void createWorld() {
        this.minecraft.forcedScreenTick(new DirtMessageScreen(new TranslationTextComponent("createWorld.preparing")));
        if (this.func_238960_x_()) {
            WorldSettings worldSettings;
            this.func_243432_s();
            DimensionGeneratorSettings dimensionGeneratorSettings = this.field_238934_c_.func_239054_a_(this.hardCoreMode);
            if (dimensionGeneratorSettings.func_236227_h_()) {
                GameRules gameRules = new GameRules();
                gameRules.get(GameRules.DO_DAYLIGHT_CYCLE).set(false, null);
                worldSettings = new WorldSettings(this.worldNameField.getText().trim(), GameType.SPECTATOR, false, Difficulty.PEACEFUL, true, gameRules, DatapackCodec.VANILLA_CODEC);
            } else {
                worldSettings = new WorldSettings(this.worldNameField.getText().trim(), this.field_228197_f_.field_228218_f_, this.hardCoreMode, this.field_238937_w_, this.allowCheats && !this.hardCoreMode, this.field_238932_M_, this.field_238933_b_);
            }
            this.minecraft.createWorld(this.saveDirName, worldSettings, this.field_238934_c_.func_239055_b_(), dimensionGeneratorSettings);
        }
    }

    private void toggleMoreWorldOptions() {
        this.showMoreWorldOptions(!this.inMoreWorldOptionsDisplay);
    }

    private void func_228200_a_(GameMode gameMode) {
        if (!this.allowCheatsWasSetByUser) {
            boolean bl = this.allowCheats = gameMode == GameMode.CREATIVE;
        }
        if (gameMode == GameMode.HARDCORE) {
            this.hardCoreMode = true;
            this.btnAllowCommands.active = false;
            this.field_238934_c_.field_239027_a_.active = false;
            this.field_238937_w_ = Difficulty.HARD;
            this.field_238929_E_.active = false;
        } else {
            this.hardCoreMode = false;
            this.btnAllowCommands.active = true;
            this.field_238934_c_.field_239027_a_.active = true;
            this.field_238937_w_ = this.field_238936_v_;
            this.field_238929_E_.active = true;
        }
        this.field_228197_f_ = gameMode;
        this.func_228199_a_();
    }

    public void func_238955_g_() {
        this.showMoreWorldOptions(this.inMoreWorldOptionsDisplay);
    }

    private void showMoreWorldOptions(boolean bl) {
        this.inMoreWorldOptionsDisplay = bl;
        this.btnGameMode.visible = !this.inMoreWorldOptionsDisplay;
        boolean bl2 = this.field_238929_E_.visible = !this.inMoreWorldOptionsDisplay;
        if (this.field_238934_c_.func_239042_a_()) {
            this.field_238931_H_.visible = false;
            this.btnGameMode.active = false;
            if (this.field_228198_g_ == null) {
                this.field_228198_g_ = this.field_228197_f_;
            }
            this.func_228200_a_(GameMode.DEBUG);
            this.btnAllowCommands.visible = false;
        } else {
            this.btnGameMode.active = true;
            if (this.field_228198_g_ != null) {
                this.func_228200_a_(this.field_228198_g_);
            }
            this.btnAllowCommands.visible = !this.inMoreWorldOptionsDisplay;
            this.field_238931_H_.visible = !this.inMoreWorldOptionsDisplay;
        }
        this.field_238934_c_.func_239059_b_(this.inMoreWorldOptionsDisplay);
        this.worldNameField.setVisible(!this.inMoreWorldOptionsDisplay);
        if (this.inMoreWorldOptionsDisplay) {
            this.btnMoreOptions.setMessage(DialogTexts.GUI_DONE);
        } else {
            this.btnMoreOptions.setMessage(new TranslationTextComponent("selectWorld.moreWorldOptions"));
        }
        this.field_238930_G_.visible = !this.inMoreWorldOptionsDisplay;
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (super.keyPressed(n, n2, n3)) {
            return false;
        }
        if (n != 257 && n != 335) {
            return true;
        }
        this.createWorld();
        return false;
    }

    @Override
    public void closeScreen() {
        if (this.inMoreWorldOptionsDisplay) {
            this.showMoreWorldOptions(true);
        } else {
            this.func_243430_k();
        }
    }

    public void func_243430_k() {
        this.minecraft.displayGuiScreen(this.parentScreen);
        this.func_243432_s();
    }

    private void func_243432_s() {
        if (this.field_243416_G != null) {
            this.field_243416_G.close();
        }
        this.func_238959_w_();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        CreateWorldScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, -1);
        if (this.inMoreWorldOptionsDisplay) {
            CreateWorldScreen.drawString(matrixStack, this.font, field_243418_r, this.width / 2 - 100, 47, -6250336);
            CreateWorldScreen.drawString(matrixStack, this.font, field_243419_s, this.width / 2 - 100, 85, -6250336);
            this.field_238934_c_.render(matrixStack, n, n2, f);
        } else {
            CreateWorldScreen.drawString(matrixStack, this.font, field_243420_t, this.width / 2 - 100, 47, -6250336);
            CreateWorldScreen.drawString(matrixStack, this.font, new StringTextComponent("").append(field_243421_u).appendString(" ").appendString(this.saveDirName), this.width / 2 - 100, 85, -6250336);
            this.worldNameField.render(matrixStack, n, n2, f);
            CreateWorldScreen.drawString(matrixStack, this.font, this.gameModeDesc1, this.width / 2 - 150, 122, -6250336);
            CreateWorldScreen.drawString(matrixStack, this.font, this.gameModeDesc2, this.width / 2 - 150, 134, -6250336);
            if (this.btnAllowCommands.visible) {
                CreateWorldScreen.drawString(matrixStack, this.font, field_243422_v, this.width / 2 - 150, 172, -6250336);
            }
        }
        super.render(matrixStack, n, n2, f);
    }

    @Override
    protected <T extends IGuiEventListener> T addListener(T t) {
        return super.addListener(t);
    }

    @Override
    protected <T extends Widget> T addButton(T t) {
        return super.addButton(t);
    }

    @Nullable
    protected Path func_238957_j_() {
        if (this.field_238928_A_ == null) {
            try {
                this.field_238928_A_ = Files.createTempDirectory("mcworld-", new FileAttribute[0]);
            } catch (IOException iOException) {
                field_238935_p_.warn("Failed to create temporary dir", (Throwable)iOException);
                SystemToast.func_238539_c_(this.minecraft, this.saveDirName);
                this.func_243430_k();
            }
        }
        return this.field_238928_A_;
    }

    private void func_238958_v_() {
        Pair<File, ResourcePackList> pair = this.func_243423_B();
        if (pair != null) {
            this.minecraft.displayGuiScreen(new PackScreen(this, pair.getSecond(), this::func_241621_a_, pair.getFirst(), new TranslationTextComponent("dataPack.title")));
        }
    }

    private void func_241621_a_(ResourcePackList resourcePackList) {
        ImmutableList<String> immutableList = ImmutableList.copyOf(resourcePackList.func_232621_d_());
        List list = resourcePackList.func_232616_b_().stream().filter(arg_0 -> CreateWorldScreen.lambda$func_241621_a_$11(immutableList, arg_0)).collect(ImmutableList.toImmutableList());
        DatapackCodec datapackCodec = new DatapackCodec(immutableList, list);
        if (immutableList.equals(this.field_238933_b_.getEnabled())) {
            this.field_238933_b_ = datapackCodec;
        } else {
            this.minecraft.enqueue(this::lambda$func_241621_a_$12);
            DataPackRegistries.func_240961_a_(resourcePackList.func_232623_f_(), Commands.EnvironmentType.INTEGRATED, 2, Util.getServerExecutor(), this.minecraft).handle((arg_0, arg_1) -> this.lambda$func_241621_a_$16(datapackCodec, arg_0, arg_1));
        }
    }

    private void func_238959_w_() {
        if (this.field_238928_A_ != null) {
            try (Stream<Path> stream = Files.walk(this.field_238928_A_, new FileVisitOption[0]);){
                stream.sorted(Comparator.reverseOrder()).forEach(CreateWorldScreen::lambda$func_238959_w_$17);
            } catch (IOException iOException) {
                field_238935_p_.warn("Failed to list temporary dir {}", (Object)this.field_238928_A_);
            }
            this.field_238928_A_ = null;
        }
    }

    private static void func_238945_a_(Path path, Path path2, Path path3) {
        try {
            Util.func_240984_a_(path, path2, path3);
        } catch (IOException iOException) {
            field_238935_p_.warn("Failed to copy datapack file from {} to {}", (Object)path3, (Object)path2);
            throw new DatapackException(iOException);
        }
    }

    private boolean func_238960_x_() {
        if (this.field_238928_A_ != null) {
            try (SaveFormat.LevelSave levelSave = this.minecraft.getSaveLoader().getLevelSave(this.saveDirName);
                 Stream<Path> stream = Files.walk(this.field_238928_A_, new FileVisitOption[0]);){
                Path path = levelSave.resolveFilePath(FolderName.DATAPACKS);
                Files.createDirectories(path, new FileAttribute[0]);
                stream.filter(this::lambda$func_238960_x_$18).forEach(arg_0 -> this.lambda$func_238960_x_$19(path, arg_0));
            } catch (IOException | DatapackException exception) {
                field_238935_p_.warn("Failed to copy datapacks to world {}", (Object)this.saveDirName, (Object)exception);
                SystemToast.func_238539_c_(this.minecraft, this.saveDirName);
                this.func_243430_k();
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static Path func_238943_a_(Path path, Minecraft minecraft) {
        MutableObject mutableObject = new MutableObject();
        try (Stream<Path> stream = Files.walk(path, new FileVisitOption[0]);){
            stream.filter(arg_0 -> CreateWorldScreen.lambda$func_238943_a_$20(path, arg_0)).forEach(arg_0 -> CreateWorldScreen.lambda$func_238943_a_$21(mutableObject, path, arg_0));
        } catch (IOException | DatapackException exception) {
            field_238935_p_.warn("Failed to copy datapacks from world {}", (Object)path, (Object)exception);
            SystemToast.func_238539_c_(minecraft, path.toString());
            return null;
        }
        return (Path)mutableObject.getValue();
    }

    @Nullable
    private Pair<File, ResourcePackList> func_243423_B() {
        Path path = this.func_238957_j_();
        if (path != null) {
            File file = path.toFile();
            if (this.field_243416_G == null) {
                this.field_243416_G = new ResourcePackList(new ServerPackFinder(), new FolderPackFinder(file, IPackNameDecorator.PLAIN));
                this.field_243416_G.reloadPacksFromFinders();
            }
            this.field_243416_G.setEnabledPacks(this.field_238933_b_.getEnabled());
            return Pair.of(file, this.field_243416_G);
        }
        return null;
    }

    private static void lambda$func_238943_a_$21(MutableObject mutableObject, Path path, Path path2) {
        Path path3 = (Path)mutableObject.getValue();
        if (path3 == null) {
            try {
                path3 = Files.createTempDirectory("mcworld-", new FileAttribute[0]);
            } catch (IOException iOException) {
                field_238935_p_.warn("Failed to create temporary dir");
                throw new DatapackException(iOException);
            }
            mutableObject.setValue(path3);
        }
        CreateWorldScreen.func_238945_a_(path, path3, path2);
    }

    private static boolean lambda$func_238943_a_$20(Path path, Path path2) {
        return !path2.equals(path);
    }

    private void lambda$func_238960_x_$19(Path path, Path path2) {
        CreateWorldScreen.func_238945_a_(this.field_238928_A_, path, path2);
    }

    private boolean lambda$func_238960_x_$18(Path path) {
        return !path.equals(this.field_238928_A_);
    }

    private static void lambda$func_238959_w_$17(Path path) {
        try {
            Files.delete(path);
        } catch (IOException iOException) {
            field_238935_p_.warn("Failed to remove temporary file {}", (Object)path, (Object)iOException);
        }
    }

    private Object lambda$func_241621_a_$16(DatapackCodec datapackCodec, DataPackRegistries dataPackRegistries, Throwable throwable) {
        if (throwable != null) {
            field_238935_p_.warn("Failed to validate datapack", throwable);
            this.minecraft.enqueue(this::lambda$func_241621_a_$14);
        } else {
            this.minecraft.enqueue(() -> this.lambda$func_241621_a_$15(datapackCodec, dataPackRegistries));
        }
        return null;
    }

    private void lambda$func_241621_a_$15(DatapackCodec datapackCodec, DataPackRegistries dataPackRegistries) {
        this.field_238933_b_ = datapackCodec;
        this.field_238934_c_.func_243447_a(dataPackRegistries);
        dataPackRegistries.close();
        this.minecraft.displayGuiScreen(this);
    }

    private void lambda$func_241621_a_$14() {
        this.minecraft.displayGuiScreen(new ConfirmScreen(this::lambda$func_241621_a_$13, new TranslationTextComponent("dataPack.validation.failed"), StringTextComponent.EMPTY, new TranslationTextComponent("dataPack.validation.back"), new TranslationTextComponent("dataPack.validation.reset")));
    }

    private void lambda$func_241621_a_$13(boolean bl) {
        if (bl) {
            this.func_238958_v_();
        } else {
            this.field_238933_b_ = DatapackCodec.VANILLA_CODEC;
            this.minecraft.displayGuiScreen(this);
        }
    }

    private void lambda$func_241621_a_$12() {
        this.minecraft.displayGuiScreen(new DirtMessageScreen(new TranslationTextComponent("dataPack.validation.working")));
    }

    private static boolean lambda$func_241621_a_$11(List list, String string) {
        return !list.contains(string);
    }

    private void lambda$init$10(Button button) {
        this.func_243430_k();
    }

    private void lambda$init$9(Button button) {
        this.createWorld();
    }

    private void lambda$init$8(Button button) {
        this.toggleMoreWorldOptions();
    }

    private void lambda$init$7(Button button) {
        this.minecraft.displayGuiScreen(new EditGamerulesScreen(this.field_238932_M_.clone(), this::lambda$init$6));
    }

    private void lambda$init$6(Optional optional) {
        this.minecraft.displayGuiScreen(this);
        optional.ifPresent(this::lambda$init$5);
    }

    private void lambda$init$5(GameRules gameRules) {
        this.field_238932_M_ = gameRules;
    }

    private void lambda$init$4(Button button) {
        this.func_238958_v_();
    }

    private void lambda$init$3(Button button) {
        this.allowCheatsWasSetByUser = true;
        this.allowCheats = !this.allowCheats;
        button.queueNarration(250);
    }

    private void lambda$init$2(Button button) {
        this.field_238937_w_ = this.field_238936_v_ = this.field_238936_v_.getNextDifficulty();
        button.queueNarration(250);
    }

    private void lambda$init$1(Button button) {
        switch (5.$SwitchMap$net$minecraft$client$gui$screen$CreateWorldScreen$GameMode[this.field_228197_f_.ordinal()]) {
            case 1: {
                this.func_228200_a_(GameMode.HARDCORE);
                break;
            }
            case 2: {
                this.func_228200_a_(GameMode.CREATIVE);
                break;
            }
            case 3: {
                this.func_228200_a_(GameMode.SURVIVAL);
            }
        }
        button.queueNarration(250);
    }

    private void lambda$init$0(String string) {
        this.worldName = string;
        this.btnCreateWorld.active = !this.worldNameField.getText().isEmpty();
        this.calcSaveDirName();
    }

    static enum GameMode {
        SURVIVAL("survival", GameType.SURVIVAL),
        HARDCORE("hardcore", GameType.SURVIVAL),
        CREATIVE("creative", GameType.CREATIVE),
        DEBUG("spectator", GameType.SPECTATOR);

        private final String field_228217_e_;
        private final GameType field_228218_f_;

        private GameMode(String string2, GameType gameType) {
            this.field_228217_e_ = string2;
            this.field_228218_f_ = gameType;
        }
    }

    static class DatapackException
    extends RuntimeException {
        public DatapackException(Throwable throwable) {
            super(throwable);
        }
    }
}

