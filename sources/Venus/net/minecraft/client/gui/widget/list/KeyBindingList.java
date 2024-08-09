/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.list;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.ControlsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.ArrayUtils;

public class KeyBindingList
extends AbstractOptionList<Entry> {
    private final ControlsScreen controlsScreen;
    private int maxListLabelWidth;

    public KeyBindingList(ControlsScreen controlsScreen, Minecraft minecraft) {
        super(minecraft, controlsScreen.width + 45, controlsScreen.height, 43, controlsScreen.height - 32, 20);
        this.controlsScreen = controlsScreen;
        KeyBinding[] keyBindingArray = ArrayUtils.clone(minecraft.gameSettings.keyBindings);
        Arrays.sort(keyBindingArray);
        String string = null;
        for (KeyBinding keyBinding : keyBindingArray) {
            TranslationTextComponent translationTextComponent;
            int n;
            String string2 = keyBinding.getKeyCategory();
            if (!string2.equals(string)) {
                string = string2;
                this.addEntry(new CategoryEntry(this, new TranslationTextComponent(string2)));
            }
            if ((n = minecraft.fontRenderer.getStringPropertyWidth(translationTextComponent = new TranslationTextComponent(keyBinding.getKeyDescription()))) > this.maxListLabelWidth) {
                this.maxListLabelWidth = n;
            }
            this.addEntry(new KeyEntry(this, keyBinding, translationTextComponent));
        }
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 15;
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 32;
    }

    public class CategoryEntry
    extends Entry {
        private final ITextComponent labelText;
        private final int labelWidth;
        final KeyBindingList this$0;

        public CategoryEntry(KeyBindingList keyBindingList, ITextComponent iTextComponent) {
            this.this$0 = keyBindingList;
            this.labelText = iTextComponent;
            this.labelWidth = keyBindingList.minecraft.fontRenderer.getStringPropertyWidth(this.labelText);
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.this$0.minecraft.fontRenderer.func_243248_b(matrixStack, this.labelText, this.this$0.minecraft.currentScreen.width / 2 - this.labelWidth / 2, n2 + n5 - 9 - 1, 0xFFFFFF);
        }

        @Override
        public boolean changeFocus(boolean bl) {
            return true;
        }

        @Override
        public List<? extends IGuiEventListener> getEventListeners() {
            return Collections.emptyList();
        }
    }

    public class KeyEntry
    extends Entry {
        private final KeyBinding keybinding;
        private final ITextComponent keyDesc;
        private final Button btnChangeKeyBinding;
        private final Button btnReset;
        final KeyBindingList this$0;

        private KeyEntry(KeyBindingList keyBindingList, KeyBinding keyBinding, ITextComponent iTextComponent) {
            this.this$0 = keyBindingList;
            this.keybinding = keyBinding;
            this.keyDesc = iTextComponent;
            this.btnChangeKeyBinding = new Button(this, 0, 0, 75, 20, iTextComponent, arg_0 -> this.lambda$new$0(keyBinding, arg_0), keyBindingList, keyBinding, iTextComponent){
                final KeyBindingList val$this$0;
                final KeyBinding val$p_i232281_2_;
                final ITextComponent val$p_i232281_3_;
                final KeyEntry this$1;
                {
                    this.this$1 = keyEntry;
                    this.val$this$0 = keyBindingList;
                    this.val$p_i232281_2_ = keyBinding;
                    this.val$p_i232281_3_ = iTextComponent2;
                    super(n, n2, n3, n4, iTextComponent, iPressable);
                }

                @Override
                protected IFormattableTextComponent getNarrationMessage() {
                    return this.val$p_i232281_2_.isInvalid() ? new TranslationTextComponent("narrator.controls.unbound", this.val$p_i232281_3_) : new TranslationTextComponent("narrator.controls.bound", this.val$p_i232281_3_, super.getNarrationMessage());
                }
            };
            this.btnReset = new Button(this, 0, 0, 50, 20, new TranslationTextComponent("controls.reset"), arg_0 -> this.lambda$new$1(keyBinding, arg_0), keyBindingList, iTextComponent){
                final KeyBindingList val$this$0;
                final ITextComponent val$p_i232281_3_;
                final KeyEntry this$1;
                {
                    this.this$1 = keyEntry;
                    this.val$this$0 = keyBindingList;
                    this.val$p_i232281_3_ = iTextComponent2;
                    super(n, n2, n3, n4, iTextComponent, iPressable);
                }

                @Override
                protected IFormattableTextComponent getNarrationMessage() {
                    return new TranslationTextComponent("narrator.controls.reset", this.val$p_i232281_3_);
                }
            };
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            boolean bl2 = this.this$0.controlsScreen.buttonId == this.keybinding;
            this.this$0.minecraft.fontRenderer.func_243248_b(matrixStack, this.keyDesc, n3 + 90 - this.this$0.maxListLabelWidth, n2 + n5 / 2 - 4, 0xFFFFFF);
            this.btnReset.x = n3 + 190;
            this.btnReset.y = n2;
            this.btnReset.active = !this.keybinding.isDefault();
            this.btnReset.render(matrixStack, n6, n7, f);
            this.btnChangeKeyBinding.x = n3 + 105;
            this.btnChangeKeyBinding.y = n2;
            this.btnChangeKeyBinding.setMessage(this.keybinding.func_238171_j_());
            boolean bl3 = false;
            if (!this.keybinding.isInvalid()) {
                for (KeyBinding keyBinding : this.this$0.minecraft.gameSettings.keyBindings) {
                    if (keyBinding == this.keybinding || !this.keybinding.conflicts(keyBinding)) continue;
                    bl3 = true;
                    break;
                }
            }
            if (bl2) {
                this.btnChangeKeyBinding.setMessage(new StringTextComponent("> ").append(this.btnChangeKeyBinding.getMessage().deepCopy().mergeStyle(TextFormatting.YELLOW)).appendString(" <").mergeStyle(TextFormatting.YELLOW));
            } else if (bl3) {
                this.btnChangeKeyBinding.setMessage(this.btnChangeKeyBinding.getMessage().deepCopy().mergeStyle(TextFormatting.RED));
            }
            this.btnChangeKeyBinding.render(matrixStack, n6, n7, f);
        }

        @Override
        public List<? extends IGuiEventListener> getEventListeners() {
            return ImmutableList.of(this.btnChangeKeyBinding, this.btnReset);
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            if (this.btnChangeKeyBinding.mouseClicked(d, d2, n)) {
                return false;
            }
            return this.btnReset.mouseClicked(d, d2, n);
        }

        @Override
        public boolean mouseReleased(double d, double d2, int n) {
            return this.btnChangeKeyBinding.mouseReleased(d, d2, n) || this.btnReset.mouseReleased(d, d2, n);
        }

        private void lambda$new$1(KeyBinding keyBinding, Button button) {
            this.this$0.minecraft.gameSettings.setKeyBindingCode(keyBinding, keyBinding.getDefault());
            KeyBinding.resetKeyBindingArrayAndHash();
        }

        private void lambda$new$0(KeyBinding keyBinding, Button button) {
            this.this$0.controlsScreen.buttonId = keyBinding;
        }
    }

    public static abstract class Entry
    extends AbstractOptionList.Entry<Entry> {
    }
}

