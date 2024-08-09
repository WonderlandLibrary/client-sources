/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.CreateFlatWorldScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlatPresetsScreen
extends Screen {
    private static final Logger field_238631_a_ = LogManager.getLogger();
    private static final List<LayerItem> FLAT_WORLD_PRESETS = Lists.newArrayList();
    private final CreateFlatWorldScreen parentScreen;
    private ITextComponent presetsShare;
    private ITextComponent listText;
    private SlotList list;
    private Button btnSelect;
    private TextFieldWidget export;
    private FlatGenerationSettings field_241594_u_;

    public FlatPresetsScreen(CreateFlatWorldScreen createFlatWorldScreen) {
        super(new TranslationTextComponent("createWorld.customize.presets.title"));
        this.parentScreen = createFlatWorldScreen;
    }

    @Nullable
    private static FlatLayerInfo func_238638_a_(String string, int n) {
        Block block;
        int n2;
        String[] stringArray = string.split("\\*", 2);
        if (stringArray.length == 2) {
            try {
                n2 = Math.max(Integer.parseInt(stringArray[0]), 0);
            } catch (NumberFormatException numberFormatException) {
                field_238631_a_.error("Error while parsing flat world string => {}", (Object)numberFormatException.getMessage());
                return null;
            }
        } else {
            n2 = 1;
        }
        int n3 = Math.min(n + n2, 256);
        int n4 = n3 - n;
        String string2 = stringArray[stringArray.length - 1];
        try {
            block = Registry.BLOCK.getOptional(new ResourceLocation(string2)).orElse(null);
        } catch (Exception exception) {
            field_238631_a_.error("Error while parsing flat world string => {}", (Object)exception.getMessage());
            return null;
        }
        if (block == null) {
            field_238631_a_.error("Error while parsing flat world string => Unknown block, {}", (Object)string2);
            return null;
        }
        FlatLayerInfo flatLayerInfo = new FlatLayerInfo(n4, block);
        flatLayerInfo.setMinY(n);
        return flatLayerInfo;
    }

    private static List<FlatLayerInfo> func_238637_a_(String string) {
        ArrayList<FlatLayerInfo> arrayList = Lists.newArrayList();
        String[] stringArray = string.split(",");
        int n = 0;
        for (String string2 : stringArray) {
            FlatLayerInfo flatLayerInfo = FlatPresetsScreen.func_238638_a_(string2, n);
            if (flatLayerInfo == null) {
                return Collections.emptyList();
            }
            arrayList.add(flatLayerInfo);
            n += flatLayerInfo.getLayerCount();
        }
        return arrayList;
    }

    public static FlatGenerationSettings func_243299_a(Registry<Biome> registry, String string, FlatGenerationSettings flatGenerationSettings) {
        Object object;
        Iterator<String> iterator2 = Splitter.on(';').split(string).iterator();
        if (!iterator2.hasNext()) {
            return FlatGenerationSettings.func_242869_a(registry);
        }
        List<FlatLayerInfo> list = FlatPresetsScreen.func_238637_a_(iterator2.next());
        if (list.isEmpty()) {
            return FlatGenerationSettings.func_242869_a(registry);
        }
        FlatGenerationSettings flatGenerationSettings2 = flatGenerationSettings.func_241527_a_(list, flatGenerationSettings.func_236943_d_());
        RegistryKey<Biome> registryKey = Biomes.PLAINS;
        if (iterator2.hasNext()) {
            try {
                object = new ResourceLocation(iterator2.next());
                registryKey = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, (ResourceLocation)object);
                registry.getOptionalValue(registryKey).orElseThrow(() -> FlatPresetsScreen.lambda$func_243299_a$0((ResourceLocation)object));
            } catch (Exception exception) {
                field_238631_a_.error("Error while parsing flat world string => {}", (Object)exception.getMessage());
            }
        }
        object = registryKey;
        flatGenerationSettings2.func_242870_a(() -> FlatPresetsScreen.lambda$func_243299_a$1(registry, object));
        return flatGenerationSettings2;
    }

    private static String func_243303_b(Registry<Biome> registry, FlatGenerationSettings flatGenerationSettings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < flatGenerationSettings.getFlatLayers().size(); ++i) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(flatGenerationSettings.getFlatLayers().get(i));
        }
        stringBuilder.append(";");
        stringBuilder.append(registry.getKey(flatGenerationSettings.getBiome()));
        return stringBuilder.toString();
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.presetsShare = new TranslationTextComponent("createWorld.customize.presets.share");
        this.listText = new TranslationTextComponent("createWorld.customize.presets.list");
        this.export = new TextFieldWidget(this.font, 50, 40, this.width - 100, 20, this.presetsShare);
        this.export.setMaxStringLength(1230);
        MutableRegistry<Biome> mutableRegistry = this.parentScreen.createWorldGui.field_238934_c_.func_239055_b_().getRegistry(Registry.BIOME_KEY);
        this.export.setText(FlatPresetsScreen.func_243303_b(mutableRegistry, this.parentScreen.func_238603_g_()));
        this.field_241594_u_ = this.parentScreen.func_238603_g_();
        this.children.add(this.export);
        this.list = new SlotList(this);
        this.children.add(this.list);
        this.btnSelect = this.addButton(new Button(this.width / 2 - 155, this.height - 28, 150, 20, new TranslationTextComponent("createWorld.customize.presets.select"), arg_0 -> this.lambda$init$2(mutableRegistry, arg_0)));
        this.addButton(new Button(this.width / 2 + 5, this.height - 28, 150, 20, DialogTexts.GUI_CANCEL, this::lambda$init$3));
        this.func_213074_a(this.list.getSelected() != null);
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        return this.list.mouseScrolled(d, d2, d3);
    }

    @Override
    public void resize(Minecraft minecraft, int n, int n2) {
        String string = this.export.getText();
        this.init(minecraft, n, n2);
        this.export.setText(string);
    }

    @Override
    public void closeScreen() {
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.list.render(matrixStack, n, n2, f);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0f, 0.0f, 400.0f);
        FlatPresetsScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        FlatPresetsScreen.drawString(matrixStack, this.font, this.presetsShare, 50, 30, 0xA0A0A0);
        FlatPresetsScreen.drawString(matrixStack, this.font, this.listText, 50, 70, 0xA0A0A0);
        RenderSystem.popMatrix();
        this.export.render(matrixStack, n, n2, f);
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public void tick() {
        this.export.tick();
        super.tick();
    }

    public void func_213074_a(boolean bl) {
        this.btnSelect.active = bl || this.export.getText().length() > 1;
    }

    private static void func_238640_a_(ITextComponent iTextComponent, IItemProvider iItemProvider, RegistryKey<Biome> registryKey, List<Structure<?>> list, boolean bl, boolean bl2, boolean bl3, FlatLayerInfo ... flatLayerInfoArray) {
        FLAT_WORLD_PRESETS.add(new LayerItem(iItemProvider.asItem(), iTextComponent, arg_0 -> FlatPresetsScreen.lambda$func_238640_a_$5(list, bl, bl2, bl3, flatLayerInfoArray, registryKey, arg_0)));
    }

    private static FlatGenerationSettings lambda$func_238640_a_$5(List list, boolean bl, boolean bl2, boolean bl3, FlatLayerInfo[] flatLayerInfoArray, RegistryKey registryKey, Registry registry) {
        Object object2;
        HashMap<Structure<?>, StructureSeparationSettings> hashMap = Maps.newHashMap();
        for (Object object2 : list) {
            hashMap.put((Structure<?>)object2, DimensionStructuresSettings.field_236191_b_.get(object2));
        }
        DimensionStructuresSettings dimensionStructuresSettings = new DimensionStructuresSettings(bl ? Optional.of(DimensionStructuresSettings.field_236192_c_) : Optional.empty(), hashMap);
        object2 = new FlatGenerationSettings(dimensionStructuresSettings, registry);
        if (bl2) {
            ((FlatGenerationSettings)object2).func_236936_a_();
        }
        if (bl3) {
            ((FlatGenerationSettings)object2).func_236941_b_();
        }
        for (int i = flatLayerInfoArray.length - 1; i >= 0; --i) {
            ((FlatGenerationSettings)object2).getFlatLayers().add(flatLayerInfoArray[i]);
        }
        ((FlatGenerationSettings)object2).func_242870_a(() -> FlatPresetsScreen.lambda$func_238640_a_$4(registry, registryKey));
        ((FlatGenerationSettings)object2).updateLayers();
        return ((FlatGenerationSettings)object2).func_236937_a_(dimensionStructuresSettings);
    }

    private static Biome lambda$func_238640_a_$4(Registry registry, RegistryKey registryKey) {
        return (Biome)registry.getOrThrow(registryKey);
    }

    private void lambda$init$3(Button button) {
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    private void lambda$init$2(Registry registry, Button button) {
        FlatGenerationSettings flatGenerationSettings = FlatPresetsScreen.func_243299_a(registry, this.export.getText(), this.field_241594_u_);
        this.parentScreen.func_238602_a_(flatGenerationSettings);
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    private static Biome lambda$func_243299_a$1(Registry registry, RegistryKey registryKey) {
        return (Biome)registry.getOrThrow(registryKey);
    }

    private static IllegalArgumentException lambda$func_243299_a$0(ResourceLocation resourceLocation) {
        return new IllegalArgumentException("Invalid Biome: " + resourceLocation);
    }

    static {
        FlatPresetsScreen.func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.classic_flat"), Blocks.GRASS_BLOCK, Biomes.PLAINS, Arrays.asList(Structure.field_236381_q_), false, false, false, new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(2, Blocks.DIRT), new FlatLayerInfo(1, Blocks.BEDROCK));
        FlatPresetsScreen.func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.tunnelers_dream"), Blocks.STONE, Biomes.MOUNTAINS, Arrays.asList(Structure.field_236367_c_), true, true, false, new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(5, Blocks.DIRT), new FlatLayerInfo(230, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
        FlatPresetsScreen.func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.water_world"), Items.WATER_BUCKET, Biomes.DEEP_OCEAN, Arrays.asList(Structure.field_236377_m_, Structure.field_236373_i_, Structure.field_236376_l_), false, false, false, new FlatLayerInfo(90, Blocks.WATER), new FlatLayerInfo(5, Blocks.SAND), new FlatLayerInfo(5, Blocks.DIRT), new FlatLayerInfo(5, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
        FlatPresetsScreen.func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.overworld"), Blocks.GRASS, Biomes.PLAINS, Arrays.asList(Structure.field_236381_q_, Structure.field_236367_c_, Structure.field_236366_b_, Structure.field_236372_h_), true, true, true, new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(59, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
        FlatPresetsScreen.func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.snowy_kingdom"), Blocks.SNOW, Biomes.SNOWY_TUNDRA, Arrays.asList(Structure.field_236381_q_, Structure.field_236371_g_), false, false, false, new FlatLayerInfo(1, Blocks.SNOW), new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(59, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
        FlatPresetsScreen.func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.bottomless_pit"), Items.FEATHER, Biomes.PLAINS, Arrays.asList(Structure.field_236381_q_), false, false, false, new FlatLayerInfo(1, Blocks.GRASS_BLOCK), new FlatLayerInfo(3, Blocks.DIRT), new FlatLayerInfo(2, Blocks.COBBLESTONE));
        FlatPresetsScreen.func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.desert"), Blocks.SAND, Biomes.DESERT, Arrays.asList(Structure.field_236381_q_, Structure.field_236370_f_, Structure.field_236367_c_), true, true, false, new FlatLayerInfo(8, Blocks.SAND), new FlatLayerInfo(52, Blocks.SANDSTONE), new FlatLayerInfo(3, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
        FlatPresetsScreen.func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.redstone_ready"), Items.REDSTONE, Biomes.DESERT, Collections.emptyList(), false, false, false, new FlatLayerInfo(52, Blocks.SANDSTONE), new FlatLayerInfo(3, Blocks.STONE), new FlatLayerInfo(1, Blocks.BEDROCK));
        FlatPresetsScreen.func_238640_a_(new TranslationTextComponent("createWorld.customize.preset.the_void"), Blocks.BARRIER, Biomes.THE_VOID, Collections.emptyList(), false, true, false, new FlatLayerInfo(1, Blocks.AIR));
    }

    class SlotList
    extends ExtendedList<PresetEntry> {
        final FlatPresetsScreen this$0;

        public SlotList(FlatPresetsScreen flatPresetsScreen) {
            this.this$0 = flatPresetsScreen;
            super(flatPresetsScreen.minecraft, flatPresetsScreen.width, flatPresetsScreen.height, 80, flatPresetsScreen.height - 37, 24);
            for (int i = 0; i < FLAT_WORLD_PRESETS.size(); ++i) {
                this.addEntry(new PresetEntry(this));
            }
        }

        @Override
        public void setSelected(@Nullable PresetEntry presetEntry) {
            super.setSelected(presetEntry);
            if (presetEntry != null) {
                NarratorChatListener.INSTANCE.say(new TranslationTextComponent("narrator.select", FLAT_WORLD_PRESETS.get(this.getEventListeners().indexOf(presetEntry)).func_238644_a_()).getString());
            }
            this.this$0.func_213074_a(presetEntry != null);
        }

        @Override
        protected boolean isFocused() {
            return this.this$0.getListener() == this;
        }

        @Override
        public boolean keyPressed(int n, int n2, int n3) {
            if (super.keyPressed(n, n2, n3)) {
                return false;
            }
            if ((n == 257 || n == 335) && this.getSelected() != null) {
                ((PresetEntry)this.getSelected()).func_214399_a();
            }
            return true;
        }

        @Override
        public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
            this.setSelected((PresetEntry)abstractListEntry);
        }

        static Minecraft access$000(SlotList slotList) {
            return slotList.minecraft;
        }

        public class PresetEntry
        extends AbstractList.AbstractListEntry<PresetEntry> {
            final SlotList this$1;

            public PresetEntry(SlotList slotList) {
                this.this$1 = slotList;
            }

            @Override
            public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
                LayerItem layerItem = FLAT_WORLD_PRESETS.get(n);
                this.func_238647_a_(matrixStack, n3, n2, layerItem.icon);
                this.this$1.this$0.font.func_243248_b(matrixStack, layerItem.name, n3 + 18 + 5, n2 + 6, 0xFFFFFF);
            }

            @Override
            public boolean mouseClicked(double d, double d2, int n) {
                if (n == 0) {
                    this.func_214399_a();
                }
                return true;
            }

            private void func_214399_a() {
                this.this$1.setSelected(this);
                LayerItem layerItem = FLAT_WORLD_PRESETS.get(this.this$1.getEventListeners().indexOf(this));
                MutableRegistry<Biome> mutableRegistry = this.this$1.this$0.parentScreen.createWorldGui.field_238934_c_.func_239055_b_().getRegistry(Registry.BIOME_KEY);
                this.this$1.this$0.field_241594_u_ = layerItem.field_238643_c_.apply(mutableRegistry);
                this.this$1.this$0.export.setText(FlatPresetsScreen.func_243303_b(mutableRegistry, this.this$1.this$0.field_241594_u_));
                this.this$1.this$0.export.setCursorPositionZero();
            }

            private void func_238647_a_(MatrixStack matrixStack, int n, int n2, Item item) {
                this.func_238646_a_(matrixStack, n + 1, n2 + 1);
                RenderSystem.enableRescaleNormal();
                this.this$1.this$0.itemRenderer.renderItemIntoGUI(new ItemStack(item), n + 2, n2 + 2);
                RenderSystem.disableRescaleNormal();
            }

            private void func_238646_a_(MatrixStack matrixStack, int n, int n2) {
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                SlotList.access$000(this.this$1).getTextureManager().bindTexture(AbstractGui.STATS_ICON_LOCATION);
                AbstractGui.blit(matrixStack, n, n2, this.this$1.this$0.getBlitOffset(), 0.0f, 0.0f, 18, 18, 128, 128);
            }
        }
    }

    static class LayerItem {
        public final Item icon;
        public final ITextComponent name;
        public final Function<Registry<Biome>, FlatGenerationSettings> field_238643_c_;

        public LayerItem(Item item, ITextComponent iTextComponent, Function<Registry<Biome>, FlatGenerationSettings> function) {
            this.icon = item;
            this.name = iTextComponent;
            this.field_238643_c_ = function;
        }

        public ITextComponent func_238644_a_() {
            return this.name;
        }
    }
}

