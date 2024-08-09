/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.BiomeGeneratorTypeScreens;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.IScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.command.Commands;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.FolderPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.ServerPackFinder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.WorldGenSettingsExport;
import net.minecraft.util.registry.WorldSettingsImport;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class WorldOptionsScreen
implements IScreen,
IRenderable {
    private static final Logger field_239028_b_ = LogManager.getLogger();
    private static final ITextComponent field_239029_c_ = new TranslationTextComponent("generator.custom");
    private static final ITextComponent field_239030_d_ = new TranslationTextComponent("generator.amplified.info");
    private static final ITextComponent field_243442_e = new TranslationTextComponent("selectWorld.mapFeatures.info");
    private IBidiRenderer field_243443_f = IBidiRenderer.field_243257_a;
    private FontRenderer field_239031_e_;
    private int field_239032_f_;
    private TextFieldWidget field_239033_g_;
    private Button field_239034_h_;
    public Button field_239027_a_;
    private Button field_239035_i_;
    private Button field_239036_j_;
    private Button field_239037_k_;
    private DynamicRegistries.Impl field_239038_l_;
    private DimensionGeneratorSettings field_239039_m_;
    private Optional<BiomeGeneratorTypeScreens> field_239040_n_;
    private OptionalLong field_243444_q;

    public WorldOptionsScreen(DynamicRegistries.Impl impl, DimensionGeneratorSettings dimensionGeneratorSettings, Optional<BiomeGeneratorTypeScreens> optional, OptionalLong optionalLong) {
        this.field_239038_l_ = impl;
        this.field_239039_m_ = dimensionGeneratorSettings;
        this.field_239040_n_ = optional;
        this.field_243444_q = optionalLong;
    }

    public void func_239048_a_(CreateWorldScreen createWorldScreen, Minecraft minecraft, FontRenderer fontRenderer) {
        this.field_239031_e_ = fontRenderer;
        this.field_239032_f_ = createWorldScreen.width;
        this.field_239033_g_ = new TextFieldWidget(this.field_239031_e_, this.field_239032_f_ / 2 - 100, 60, 200, 20, new TranslationTextComponent("selectWorld.enterSeed"));
        this.field_239033_g_.setText(WorldOptionsScreen.func_243445_a(this.field_243444_q));
        this.field_239033_g_.setResponder(this::lambda$func_239048_a_$0);
        createWorldScreen.addListener(this.field_239033_g_);
        int n = this.field_239032_f_ / 2 - 155;
        int n2 = this.field_239032_f_ / 2 + 5;
        this.field_239034_h_ = createWorldScreen.addButton(new Button(this, n, 100, 150, 20, new TranslationTextComponent("selectWorld.mapFeatures"), this::lambda$func_239048_a_$1){
            final WorldOptionsScreen this$0;
            {
                this.this$0 = worldOptionsScreen;
                super(n, n2, n3, n4, iTextComponent, iPressable);
            }

            @Override
            public ITextComponent getMessage() {
                return DialogTexts.getComposedOptionMessage(super.getMessage(), this.this$0.field_239039_m_.doesGenerateFeatures());
            }

            @Override
            protected IFormattableTextComponent getNarrationMessage() {
                return super.getNarrationMessage().appendString(". ").append(new TranslationTextComponent("selectWorld.mapFeatures.info"));
            }
        });
        this.field_239034_h_.visible = false;
        this.field_239035_i_ = createWorldScreen.addButton(new Button(this, n2, 100, 150, 20, new TranslationTextComponent("selectWorld.mapType"), arg_0 -> this.lambda$func_239048_a_$2(createWorldScreen, arg_0)){
            final WorldOptionsScreen this$0;
            {
                this.this$0 = worldOptionsScreen;
                super(n, n2, n3, n4, iTextComponent, iPressable);
            }

            @Override
            public ITextComponent getMessage() {
                return super.getMessage().deepCopy().appendString(" ").append(this.this$0.field_239040_n_.map(BiomeGeneratorTypeScreens::func_239077_a_).orElse(field_239029_c_));
            }

            @Override
            protected IFormattableTextComponent getNarrationMessage() {
                return Objects.equals(this.this$0.field_239040_n_, Optional.of(BiomeGeneratorTypeScreens.field_239067_b_)) ? super.getNarrationMessage().appendString(". ").append(field_239030_d_) : super.getNarrationMessage();
            }
        });
        this.field_239035_i_.visible = false;
        this.field_239035_i_.active = this.field_239040_n_.isPresent();
        this.field_239036_j_ = createWorldScreen.addButton(new Button(n2, 120, 150, 20, new TranslationTextComponent("selectWorld.customizeType"), arg_0 -> this.lambda$func_239048_a_$3(minecraft, createWorldScreen, arg_0)));
        this.field_239036_j_.visible = false;
        this.field_239027_a_ = createWorldScreen.addButton(new Button(this, n, 151, 150, 20, new TranslationTextComponent("selectWorld.bonusItems"), this::lambda$func_239048_a_$4, createWorldScreen){
            final CreateWorldScreen val$p_239048_1_;
            final WorldOptionsScreen this$0;
            {
                this.this$0 = worldOptionsScreen;
                this.val$p_239048_1_ = createWorldScreen;
                super(n, n2, n3, n4, iTextComponent, iPressable);
            }

            @Override
            public ITextComponent getMessage() {
                return DialogTexts.getComposedOptionMessage(super.getMessage(), this.this$0.field_239039_m_.hasBonusChest() && !this.val$p_239048_1_.hardCoreMode);
            }
        });
        this.field_239027_a_.visible = false;
        this.field_239037_k_ = createWorldScreen.addButton(new Button(n, 185, 150, 20, new TranslationTextComponent("selectWorld.import_worldgen_settings"), arg_0 -> this.lambda$func_239048_a_$7(createWorldScreen, minecraft, arg_0)));
        this.field_239037_k_.visible = false;
        this.field_243443_f = IBidiRenderer.func_243258_a(fontRenderer, field_239030_d_, this.field_239035_i_.getWidth());
    }

    private void func_239052_a_(DynamicRegistries.Impl impl, DimensionGeneratorSettings dimensionGeneratorSettings) {
        this.field_239038_l_ = impl;
        this.field_239039_m_ = dimensionGeneratorSettings;
        this.field_239040_n_ = BiomeGeneratorTypeScreens.func_239079_a_(dimensionGeneratorSettings);
        this.field_243444_q = OptionalLong.of(dimensionGeneratorSettings.getSeed());
        this.field_239033_g_.setText(WorldOptionsScreen.func_243445_a(this.field_243444_q));
        this.field_239035_i_.active = this.field_239040_n_.isPresent();
    }

    @Override
    public void tick() {
        this.field_239033_g_.tick();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.field_239034_h_.visible) {
            this.field_239031_e_.func_243246_a(matrixStack, field_243442_e, this.field_239032_f_ / 2 - 150, 122.0f, -6250336);
        }
        this.field_239033_g_.render(matrixStack, n, n2, f);
        if (this.field_239040_n_.equals(Optional.of(BiomeGeneratorTypeScreens.field_239067_b_))) {
            this.field_243443_f.func_241865_b(matrixStack, this.field_239035_i_.x + 2, this.field_239035_i_.y + 22, 9, 0xA0A0A0);
        }
    }

    protected void func_239043_a_(DimensionGeneratorSettings dimensionGeneratorSettings) {
        this.field_239039_m_ = dimensionGeneratorSettings;
    }

    private static String func_243445_a(OptionalLong optionalLong) {
        return optionalLong.isPresent() ? Long.toString(optionalLong.getAsLong()) : "";
    }

    private static OptionalLong func_239053_a_(String string) {
        try {
            return OptionalLong.of(Long.parseLong(string));
        } catch (NumberFormatException numberFormatException) {
            return OptionalLong.empty();
        }
    }

    public DimensionGeneratorSettings func_239054_a_(boolean bl) {
        OptionalLong optionalLong = this.func_243449_f();
        return this.field_239039_m_.create(bl, optionalLong);
    }

    private OptionalLong func_243449_f() {
        OptionalLong optionalLong;
        String string = this.field_239033_g_.getText();
        OptionalLong optionalLong2 = StringUtils.isEmpty(string) ? OptionalLong.empty() : ((optionalLong = WorldOptionsScreen.func_239053_a_(string)).isPresent() && optionalLong.getAsLong() != 0L ? optionalLong : OptionalLong.of(string.hashCode()));
        return optionalLong2;
    }

    public boolean func_239042_a_() {
        return this.field_239039_m_.func_236227_h_();
    }

    public void func_239059_b_(boolean bl) {
        this.field_239035_i_.visible = bl;
        if (this.field_239039_m_.func_236227_h_()) {
            this.field_239034_h_.visible = false;
            this.field_239027_a_.visible = false;
            this.field_239036_j_.visible = false;
            this.field_239037_k_.visible = false;
        } else {
            this.field_239034_h_.visible = bl;
            this.field_239027_a_.visible = bl;
            this.field_239036_j_.visible = bl && BiomeGeneratorTypeScreens.field_239069_d_.containsKey(this.field_239040_n_);
            this.field_239037_k_.visible = bl;
        }
        this.field_239033_g_.setVisible(bl);
    }

    public DynamicRegistries.Impl func_239055_b_() {
        return this.field_239038_l_;
    }

    void func_243447_a(DataPackRegistries dataPackRegistries) {
        DynamicRegistries.Impl impl = DynamicRegistries.func_239770_b_();
        WorldGenSettingsExport<JsonElement> worldGenSettingsExport = WorldGenSettingsExport.create(JsonOps.INSTANCE, this.field_239038_l_);
        WorldSettingsImport<JsonElement> worldSettingsImport = WorldSettingsImport.create(JsonOps.INSTANCE, dataPackRegistries.getResourceManager(), impl);
        DataResult dataResult = DimensionGeneratorSettings.field_236201_a_.encodeStart(worldGenSettingsExport, this.field_239039_m_).flatMap(arg_0 -> WorldOptionsScreen.lambda$func_243447_a$8(worldSettingsImport, arg_0));
        dataResult.resultOrPartial(Util.func_240982_a_("Error parsing worldgen settings after loading data packs: ", field_239028_b_::error)).ifPresent(arg_0 -> this.lambda$func_243447_a$9(impl, arg_0));
    }

    private void lambda$func_243447_a$9(DynamicRegistries.Impl impl, DimensionGeneratorSettings dimensionGeneratorSettings) {
        this.field_239039_m_ = dimensionGeneratorSettings;
        this.field_239038_l_ = impl;
    }

    private static DataResult lambda$func_243447_a$8(WorldSettingsImport worldSettingsImport, JsonElement jsonElement) {
        return DimensionGeneratorSettings.field_236201_a_.parse(worldSettingsImport, jsonElement);
    }

    private void lambda$func_239048_a_$7(CreateWorldScreen createWorldScreen, Minecraft minecraft, Button button) {
        TranslationTextComponent translationTextComponent = new TranslationTextComponent("selectWorld.import_worldgen_settings.select_file");
        String string = TinyFileDialogs.tinyfd_openFileDialog(translationTextComponent.getString(), (CharSequence)null, (PointerBuffer)null, (CharSequence)null, false);
        if (string != null) {
            DataResult<Object> dataResult;
            Object object;
            Object object2;
            DataPackRegistries dataPackRegistries;
            Object object3;
            DynamicRegistries.Impl impl = DynamicRegistries.func_239770_b_();
            ResourcePackList resourcePackList = new ResourcePackList(new ServerPackFinder(), new FolderPackFinder(createWorldScreen.func_238957_j_().toFile(), IPackNameDecorator.WORLD));
            try {
                MinecraftServer.func_240772_a_(resourcePackList, createWorldScreen.field_238933_b_, false);
                object3 = DataPackRegistries.func_240961_a_(resourcePackList.func_232623_f_(), Commands.EnvironmentType.INTEGRATED, 2, Util.getServerExecutor(), minecraft);
                minecraft.driveUntil(() -> object3.isDone());
                dataPackRegistries = ((CompletableFuture)object3).get();
            } catch (InterruptedException | ExecutionException exception) {
                field_239028_b_.error("Error loading data packs when importing world settings", (Throwable)exception);
                TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("selectWorld.import_worldgen_settings.failure");
                StringTextComponent stringTextComponent = new StringTextComponent(exception.getMessage());
                minecraft.getToastGui().add(SystemToast.func_238534_a_(minecraft, SystemToast.Type.WORLD_GEN_SETTINGS_TRANSFER, translationTextComponent2, stringTextComponent));
                resourcePackList.close();
                return;
            }
            object3 = WorldSettingsImport.create(JsonOps.INSTANCE, dataPackRegistries.getResourceManager(), impl);
            JsonParser jsonParser = new JsonParser();
            try {
                object2 = Files.newBufferedReader(Paths.get(string, new String[0]));
                try {
                    object = jsonParser.parse((Reader)object2);
                    dataResult = DimensionGeneratorSettings.field_236201_a_.parse(object3, object);
                } finally {
                    if (object2 != null) {
                        ((BufferedReader)object2).close();
                    }
                }
            } catch (JsonIOException | JsonSyntaxException | IOException exception) {
                dataResult = DataResult.error("Failed to parse file: " + exception.getMessage());
            }
            if (dataResult.error().isPresent()) {
                object2 = new TranslationTextComponent("selectWorld.import_worldgen_settings.failure");
                object = dataResult.error().get().message();
                field_239028_b_.error("Error parsing world settings: {}", object);
                StringTextComponent stringTextComponent = new StringTextComponent((String)object);
                minecraft.getToastGui().add(SystemToast.func_238534_a_(minecraft, SystemToast.Type.WORLD_GEN_SETTINGS_TRANSFER, (ITextComponent)object2, stringTextComponent));
            }
            dataPackRegistries.close();
            object2 = dataResult.lifecycle();
            dataResult.resultOrPartial(field_239028_b_::error).ifPresent(arg_0 -> this.lambda$func_239048_a_$6(minecraft, createWorldScreen, impl, (Lifecycle)object2, arg_0));
        }
    }

    private void lambda$func_239048_a_$6(Minecraft minecraft, CreateWorldScreen createWorldScreen, DynamicRegistries.Impl impl, Lifecycle lifecycle, DimensionGeneratorSettings dimensionGeneratorSettings) {
        BooleanConsumer booleanConsumer = arg_0 -> this.lambda$func_239048_a_$5(minecraft, createWorldScreen, impl, dimensionGeneratorSettings, arg_0);
        if (lifecycle == Lifecycle.stable()) {
            this.func_239052_a_(impl, dimensionGeneratorSettings);
        } else if (lifecycle == Lifecycle.experimental()) {
            minecraft.displayGuiScreen(new ConfirmScreen(booleanConsumer, new TranslationTextComponent("selectWorld.import_worldgen_settings.experimental.title"), new TranslationTextComponent("selectWorld.import_worldgen_settings.experimental.question")));
        } else {
            minecraft.displayGuiScreen(new ConfirmScreen(booleanConsumer, new TranslationTextComponent("selectWorld.import_worldgen_settings.deprecated.title"), new TranslationTextComponent("selectWorld.import_worldgen_settings.deprecated.question")));
        }
    }

    private void lambda$func_239048_a_$5(Minecraft minecraft, CreateWorldScreen createWorldScreen, DynamicRegistries.Impl impl, DimensionGeneratorSettings dimensionGeneratorSettings, boolean bl) {
        minecraft.displayGuiScreen(createWorldScreen);
        if (bl) {
            this.func_239052_a_(impl, dimensionGeneratorSettings);
        }
    }

    private void lambda$func_239048_a_$4(Button button) {
        this.field_239039_m_ = this.field_239039_m_.func_236232_m_();
        button.queueNarration(250);
    }

    private void lambda$func_239048_a_$3(Minecraft minecraft, CreateWorldScreen createWorldScreen, Button button) {
        BiomeGeneratorTypeScreens.IFactory iFactory = BiomeGeneratorTypeScreens.field_239069_d_.get(this.field_239040_n_);
        if (iFactory != null) {
            minecraft.displayGuiScreen(iFactory.createEditScreen(createWorldScreen, this.field_239039_m_));
        }
    }

    private void lambda$func_239048_a_$2(CreateWorldScreen createWorldScreen, Button button) {
        while (this.field_239040_n_.isPresent()) {
            int n = BiomeGeneratorTypeScreens.field_239068_c_.indexOf(this.field_239040_n_.get()) + 1;
            if (n >= BiomeGeneratorTypeScreens.field_239068_c_.size()) {
                n = 0;
            }
            BiomeGeneratorTypeScreens biomeGeneratorTypeScreens = BiomeGeneratorTypeScreens.field_239068_c_.get(n);
            this.field_239040_n_ = Optional.of(biomeGeneratorTypeScreens);
            this.field_239039_m_ = biomeGeneratorTypeScreens.func_241220_a_(this.field_239038_l_, this.field_239039_m_.getSeed(), this.field_239039_m_.doesGenerateFeatures(), this.field_239039_m_.hasBonusChest());
            if (this.field_239039_m_.func_236227_h_() && !Screen.hasShiftDown()) continue;
        }
        createWorldScreen.func_238955_g_();
        button.queueNarration(250);
    }

    private void lambda$func_239048_a_$1(Button button) {
        this.field_239039_m_ = this.field_239039_m_.func_236231_l_();
        button.queueNarration(250);
    }

    private void lambda$func_239048_a_$0(String string) {
        this.field_243444_q = this.func_243449_f();
    }
}

