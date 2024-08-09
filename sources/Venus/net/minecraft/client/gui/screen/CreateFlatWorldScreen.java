/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.FlatPresetsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.FlatLayerInfo;

public class CreateFlatWorldScreen
extends Screen {
    protected final CreateWorldScreen createWorldGui;
    private final Consumer<FlatGenerationSettings> field_238601_b_;
    private FlatGenerationSettings generatorInfo;
    private ITextComponent materialText;
    private ITextComponent heightText;
    private DetailsList createFlatWorldListSlotGui;
    private Button removeLayerButton;

    public CreateFlatWorldScreen(CreateWorldScreen createWorldScreen, Consumer<FlatGenerationSettings> consumer, FlatGenerationSettings flatGenerationSettings) {
        super(new TranslationTextComponent("createWorld.customize.flat.title"));
        this.createWorldGui = createWorldScreen;
        this.field_238601_b_ = consumer;
        this.generatorInfo = flatGenerationSettings;
    }

    public FlatGenerationSettings func_238603_g_() {
        return this.generatorInfo;
    }

    public void func_238602_a_(FlatGenerationSettings flatGenerationSettings) {
        this.generatorInfo = flatGenerationSettings;
    }

    @Override
    protected void init() {
        this.materialText = new TranslationTextComponent("createWorld.customize.flat.tile");
        this.heightText = new TranslationTextComponent("createWorld.customize.flat.height");
        this.createFlatWorldListSlotGui = new DetailsList(this);
        this.children.add(this.createFlatWorldListSlotGui);
        this.removeLayerButton = this.addButton(new Button(this.width / 2 - 155, this.height - 52, 150, 20, new TranslationTextComponent("createWorld.customize.flat.removeLayer"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 + 5, this.height - 52, 150, 20, new TranslationTextComponent("createWorld.customize.presets"), this::lambda$init$1));
        this.addButton(new Button(this.width / 2 - 155, this.height - 28, 150, 20, DialogTexts.GUI_DONE, this::lambda$init$2));
        this.addButton(new Button(this.width / 2 + 5, this.height - 28, 150, 20, DialogTexts.GUI_CANCEL, this::lambda$init$3));
        this.generatorInfo.updateLayers();
        this.onLayersChanged();
    }

    private void onLayersChanged() {
        this.removeLayerButton.active = this.hasSelectedLayer();
    }

    private boolean hasSelectedLayer() {
        return this.createFlatWorldListSlotGui.getSelected() != null;
    }

    @Override
    public void closeScreen() {
        this.minecraft.displayGuiScreen(this.createWorldGui);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.createFlatWorldListSlotGui.render(matrixStack, n, n2, f);
        CreateFlatWorldScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        int n3 = this.width / 2 - 92 - 16;
        CreateFlatWorldScreen.drawString(matrixStack, this.font, this.materialText, n3, 32, 0xFFFFFF);
        CreateFlatWorldScreen.drawString(matrixStack, this.font, this.heightText, n3 + 2 + 213 - this.font.getStringPropertyWidth(this.heightText), 32, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$3(Button button) {
        this.minecraft.displayGuiScreen(this.createWorldGui);
        this.generatorInfo.updateLayers();
    }

    private void lambda$init$2(Button button) {
        this.field_238601_b_.accept(this.generatorInfo);
        this.minecraft.displayGuiScreen(this.createWorldGui);
        this.generatorInfo.updateLayers();
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(new FlatPresetsScreen(this));
        this.generatorInfo.updateLayers();
        this.onLayersChanged();
    }

    private void lambda$init$0(Button button) {
        if (this.hasSelectedLayer()) {
            List<FlatLayerInfo> list = this.generatorInfo.getFlatLayers();
            int n = this.createFlatWorldListSlotGui.getEventListeners().indexOf(this.createFlatWorldListSlotGui.getSelected());
            int n2 = list.size() - n - 1;
            list.remove(n2);
            this.createFlatWorldListSlotGui.setSelected(list.isEmpty() ? null : (DetailsList.LayerEntry)this.createFlatWorldListSlotGui.getEventListeners().get(Math.min(n, list.size() - 1)));
            this.generatorInfo.updateLayers();
            this.createFlatWorldListSlotGui.func_214345_a();
            this.onLayersChanged();
        }
    }

    class DetailsList
    extends ExtendedList<LayerEntry> {
        final CreateFlatWorldScreen this$0;

        public DetailsList(CreateFlatWorldScreen createFlatWorldScreen) {
            this.this$0 = createFlatWorldScreen;
            super(createFlatWorldScreen.minecraft, createFlatWorldScreen.width, createFlatWorldScreen.height, 43, createFlatWorldScreen.height - 60, 24);
            for (int i = 0; i < createFlatWorldScreen.generatorInfo.getFlatLayers().size(); ++i) {
                this.addEntry(new LayerEntry(this));
            }
        }

        @Override
        public void setSelected(@Nullable LayerEntry layerEntry) {
            FlatLayerInfo flatLayerInfo;
            Item item;
            super.setSelected(layerEntry);
            if (layerEntry != null && (item = (flatLayerInfo = this.this$0.generatorInfo.getFlatLayers().get(this.this$0.generatorInfo.getFlatLayers().size() - this.getEventListeners().indexOf(layerEntry) - 1)).getLayerMaterial().getBlock().asItem()) != Items.AIR) {
                NarratorChatListener.INSTANCE.say(new TranslationTextComponent("narrator.select", item.getDisplayName(new ItemStack(item))).getString());
            }
            this.this$0.onLayersChanged();
        }

        @Override
        protected boolean isFocused() {
            return this.this$0.getListener() == this;
        }

        @Override
        protected int getScrollbarPosition() {
            return this.width - 70;
        }

        public void func_214345_a() {
            int n = this.getEventListeners().indexOf(this.getSelected());
            this.clearEntries();
            for (int i = 0; i < this.this$0.generatorInfo.getFlatLayers().size(); ++i) {
                this.addEntry(new LayerEntry(this));
            }
            List list = this.getEventListeners();
            if (n >= 0 && n < list.size()) {
                this.setSelected((LayerEntry)list.get(n));
            }
        }

        @Override
        public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
            this.setSelected((LayerEntry)abstractListEntry);
        }

        static Minecraft access$000(DetailsList detailsList) {
            return detailsList.minecraft;
        }

        class LayerEntry
        extends AbstractList.AbstractListEntry<LayerEntry> {
            final DetailsList this$1;

            private LayerEntry(DetailsList detailsList) {
                this.this$1 = detailsList;
            }

            @Override
            public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
                FlatLayerInfo flatLayerInfo = this.this$1.this$0.generatorInfo.getFlatLayers().get(this.this$1.this$0.generatorInfo.getFlatLayers().size() - n - 1);
                BlockState blockState = flatLayerInfo.getLayerMaterial();
                Item item = blockState.getBlock().asItem();
                if (item == Items.AIR) {
                    if (blockState.isIn(Blocks.WATER)) {
                        item = Items.WATER_BUCKET;
                    } else if (blockState.isIn(Blocks.LAVA)) {
                        item = Items.LAVA_BUCKET;
                    }
                }
                ItemStack itemStack = new ItemStack(item);
                this.func_238605_a_(matrixStack, n3, n2, itemStack);
                this.this$1.this$0.font.func_243248_b(matrixStack, item.getDisplayName(itemStack), n3 + 18 + 5, n2 + 3, 0xFFFFFF);
                String string = n == 0 ? I18n.format("createWorld.customize.flat.layer.top", flatLayerInfo.getLayerCount()) : (n == this.this$1.this$0.generatorInfo.getFlatLayers().size() - 1 ? I18n.format("createWorld.customize.flat.layer.bottom", flatLayerInfo.getLayerCount()) : I18n.format("createWorld.customize.flat.layer", flatLayerInfo.getLayerCount()));
                this.this$1.this$0.font.drawString(matrixStack, string, n3 + 2 + 213 - this.this$1.this$0.font.getStringWidth(string), n2 + 3, 0xFFFFFF);
            }

            @Override
            public boolean mouseClicked(double d, double d2, int n) {
                if (n == 0) {
                    this.this$1.setSelected(this);
                    return false;
                }
                return true;
            }

            private void func_238605_a_(MatrixStack matrixStack, int n, int n2, ItemStack itemStack) {
                this.func_238604_a_(matrixStack, n + 1, n2 + 1);
                RenderSystem.enableRescaleNormal();
                if (!itemStack.isEmpty()) {
                    this.this$1.this$0.itemRenderer.renderItemIntoGUI(itemStack, n + 2, n2 + 2);
                }
                RenderSystem.disableRescaleNormal();
            }

            private void func_238604_a_(MatrixStack matrixStack, int n, int n2) {
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                DetailsList.access$000(this.this$1).getTextureManager().bindTexture(AbstractGui.STATS_ICON_LOCATION);
                AbstractGui.blit(matrixStack, n, n2, this.this$1.this$0.getBlitOffset(), 0.0f, 0.0f, 18, 18, 128, 128);
            }
        }
    }
}

