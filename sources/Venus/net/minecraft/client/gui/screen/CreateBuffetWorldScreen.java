/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;

public class CreateBuffetWorldScreen
extends Screen {
    private static final ITextComponent field_243277_a = new TranslationTextComponent("createWorld.customize.buffet.biome");
    private final Screen parent;
    private final Consumer<Biome> field_238592_b_;
    private final MutableRegistry<Biome> field_243278_p;
    private BiomeList biomeList;
    private Biome field_238593_p_;
    private Button field_205313_u;

    public CreateBuffetWorldScreen(Screen screen, DynamicRegistries dynamicRegistries, Consumer<Biome> consumer, Biome biome) {
        super(new TranslationTextComponent("createWorld.customize.buffet.title"));
        this.parent = screen;
        this.field_238592_b_ = consumer;
        this.field_238593_p_ = biome;
        this.field_243278_p = dynamicRegistries.getRegistry(Registry.BIOME_KEY);
    }

    @Override
    public void closeScreen() {
        this.minecraft.displayGuiScreen(this.parent);
    }

    @Override
    protected void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.biomeList = new BiomeList(this);
        this.children.add(this.biomeList);
        this.field_205313_u = this.addButton(new Button(this.width / 2 - 155, this.height - 28, 150, 20, DialogTexts.GUI_DONE, this::lambda$init$0));
        this.addButton(new Button(this.width / 2 + 5, this.height - 28, 150, 20, DialogTexts.GUI_CANCEL, this::lambda$init$1));
        this.biomeList.setSelected(this.biomeList.getEventListeners().stream().filter(this::lambda$init$2).findFirst().orElse(null));
    }

    private void func_205306_h() {
        this.field_205313_u.active = this.biomeList.getSelected() != null;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderDirtBackground(0);
        this.biomeList.render(matrixStack, n, n2, f);
        CreateBuffetWorldScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        CreateBuffetWorldScreen.drawCenteredString(matrixStack, this.font, field_243277_a, this.width / 2, 28, 0xA0A0A0);
        super.render(matrixStack, n, n2, f);
    }

    private boolean lambda$init$2(BiomeList.BiomeEntry biomeEntry) {
        return Objects.equals(biomeEntry.field_238599_b_, this.field_238593_p_);
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(this.parent);
    }

    private void lambda$init$0(Button button) {
        this.field_238592_b_.accept(this.field_238593_p_);
        this.minecraft.displayGuiScreen(this.parent);
    }

    class BiomeList
    extends ExtendedList<BiomeEntry> {
        final CreateBuffetWorldScreen this$0;

        private BiomeList(CreateBuffetWorldScreen createBuffetWorldScreen) {
            this.this$0 = createBuffetWorldScreen;
            super(createBuffetWorldScreen.minecraft, createBuffetWorldScreen.width, createBuffetWorldScreen.height, 40, createBuffetWorldScreen.height - 37, 16);
            createBuffetWorldScreen.field_243278_p.getEntries().stream().sorted(Comparator.comparing(BiomeList::lambda$new$0)).forEach(this::lambda$new$1);
        }

        @Override
        protected boolean isFocused() {
            return this.this$0.getListener() == this;
        }

        @Override
        public void setSelected(@Nullable BiomeEntry biomeEntry) {
            super.setSelected(biomeEntry);
            if (biomeEntry != null) {
                this.this$0.field_238593_p_ = biomeEntry.field_238599_b_;
                NarratorChatListener.INSTANCE.say(new TranslationTextComponent("narrator.select", this.this$0.field_243278_p.getKey(biomeEntry.field_238599_b_)).getString());
            }
            this.this$0.func_205306_h();
        }

        @Override
        public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
            this.setSelected((BiomeEntry)abstractListEntry);
        }

        private void lambda$new$1(Map.Entry entry) {
            this.addEntry(new BiomeEntry(this, (Biome)entry.getValue()));
        }

        private static String lambda$new$0(Map.Entry entry) {
            return ((RegistryKey)entry.getKey()).getLocation().toString();
        }

        class BiomeEntry
        extends ExtendedList.AbstractListEntry<BiomeEntry> {
            private final Biome field_238599_b_;
            private final ITextComponent field_243282_c;
            final BiomeList this$1;

            public BiomeEntry(BiomeList biomeList, Biome biome) {
                this.this$1 = biomeList;
                this.field_238599_b_ = biome;
                ResourceLocation resourceLocation = biomeList.this$0.field_243278_p.getKey(biome);
                String string = "biome." + resourceLocation.getNamespace() + "." + resourceLocation.getPath();
                this.field_243282_c = LanguageMap.getInstance().func_230506_b_(string) ? new TranslationTextComponent(string) : new StringTextComponent(resourceLocation.toString());
            }

            @Override
            public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
                AbstractGui.drawString(matrixStack, this.this$1.this$0.font, this.field_243282_c, n3 + 5, n2 + 2, 0xFFFFFF);
            }

            @Override
            public boolean mouseClicked(double d, double d2, int n) {
                if (n == 0) {
                    this.this$1.setSelected(this);
                    return false;
                }
                return true;
            }
        }
    }
}

