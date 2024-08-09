/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.CharacterManager;
import net.minecraft.util.text.TextFormatting;

public class TextInputUtil {
    private final Supplier<String> textSupplier;
    private final Consumer<String> textConsumer;
    private final Supplier<String> clipboardSupplier;
    private final Consumer<String> clipboardConsumer;
    private final Predicate<String> textLimiter;
    private int endIndex;
    private int startIndex;

    public TextInputUtil(Supplier<String> supplier, Consumer<String> consumer, Supplier<String> supplier2, Consumer<String> consumer2, Predicate<String> predicate) {
        this.textSupplier = supplier;
        this.textConsumer = consumer;
        this.clipboardSupplier = supplier2;
        this.clipboardConsumer = consumer2;
        this.textLimiter = predicate;
        this.moveCursorToEnd();
    }

    public static Supplier<String> getClipboardTextSupplier(Minecraft minecraft) {
        return () -> TextInputUtil.lambda$getClipboardTextSupplier$0(minecraft);
    }

    public static String getClipboardText(Minecraft minecraft) {
        return TextFormatting.getTextWithoutFormattingCodes(minecraft.keyboardListener.getClipboardString().replaceAll("\\r", ""));
    }

    public static Consumer<String> getClipboardTextSetter(Minecraft minecraft) {
        return arg_0 -> TextInputUtil.lambda$getClipboardTextSetter$1(minecraft, arg_0);
    }

    public static void setClipboardText(Minecraft minecraft, String string) {
        minecraft.keyboardListener.setClipboardString(string);
    }

    public boolean putChar(char c) {
        if (SharedConstants.isAllowedCharacter(c)) {
            this.insertTextAtSelection(this.textSupplier.get(), Character.toString(c));
        }
        return false;
    }

    public boolean specialKeyPressed(int n) {
        if (Screen.isSelectAll(n)) {
            this.selectAll();
            return false;
        }
        if (Screen.isCopy(n)) {
            this.copySelectedText();
            return false;
        }
        if (Screen.isPaste(n)) {
            this.insertClipboardText();
            return false;
        }
        if (Screen.isCut(n)) {
            this.cutText();
            return false;
        }
        if (n == 259) {
            this.deleteCharAtSelection(-1);
            return false;
        }
        if (n == 261) {
            this.deleteCharAtSelection(1);
        } else {
            if (n == 263) {
                if (Screen.hasControlDown()) {
                    this.moveCursorByWords(-1, Screen.hasShiftDown());
                } else {
                    this.moveCursorByChar(-1, Screen.hasShiftDown());
                }
                return false;
            }
            if (n == 262) {
                if (Screen.hasControlDown()) {
                    this.moveCursorByWords(1, Screen.hasShiftDown());
                } else {
                    this.moveCursorByChar(1, Screen.hasShiftDown());
                }
                return false;
            }
            if (n == 268) {
                this.moveCursorToStart(Screen.hasShiftDown());
                return false;
            }
            if (n == 269) {
                this.moveCursorToEnd(Screen.hasShiftDown());
                return false;
            }
        }
        return true;
    }

    private int clampIndexToTextLength(int n) {
        return MathHelper.clamp(n, 0, this.textSupplier.get().length());
    }

    private void insertTextAtSelection(String string, String string2) {
        if (this.startIndex != this.endIndex) {
            string = this.deleteSelectionFromText(string);
        }
        this.endIndex = MathHelper.clamp(this.endIndex, 0, string.length());
        String string3 = new StringBuilder(string).insert(this.endIndex, string2).toString();
        if (this.textLimiter.test(string3)) {
            this.textConsumer.accept(string3);
            this.startIndex = this.endIndex = Math.min(string3.length(), this.endIndex + string2.length());
        }
    }

    public void putText(String string) {
        this.insertTextAtSelection(this.textSupplier.get(), string);
    }

    private void deselectSelection(boolean bl) {
        if (!bl) {
            this.startIndex = this.endIndex;
        }
    }

    public void moveCursorByChar(int n, boolean bl) {
        this.endIndex = Util.func_240980_a_(this.textSupplier.get(), this.endIndex, n);
        this.deselectSelection(bl);
    }

    public void moveCursorByWords(int n, boolean bl) {
        this.endIndex = CharacterManager.func_238351_a_(this.textSupplier.get(), n, this.endIndex, true);
        this.deselectSelection(bl);
    }

    public void deleteCharAtSelection(int n) {
        String string = this.textSupplier.get();
        if (!string.isEmpty()) {
            String string2;
            if (this.startIndex != this.endIndex) {
                string2 = this.deleteSelectionFromText(string);
            } else {
                int n2 = Util.func_240980_a_(string, this.endIndex, n);
                int n3 = Math.min(n2, this.endIndex);
                int n4 = Math.max(n2, this.endIndex);
                string2 = new StringBuilder(string).delete(n3, n4).toString();
                if (n < 0) {
                    this.startIndex = this.endIndex = n3;
                }
            }
            this.textConsumer.accept(string2);
        }
    }

    public void cutText() {
        String string = this.textSupplier.get();
        this.clipboardConsumer.accept(this.getSelectedText(string));
        this.textConsumer.accept(this.deleteSelectionFromText(string));
    }

    public void insertClipboardText() {
        this.insertTextAtSelection(this.textSupplier.get(), this.clipboardSupplier.get());
        this.startIndex = this.endIndex;
    }

    public void copySelectedText() {
        this.clipboardConsumer.accept(this.getSelectedText(this.textSupplier.get()));
    }

    public void selectAll() {
        this.startIndex = 0;
        this.endIndex = this.textSupplier.get().length();
    }

    private String getSelectedText(String string) {
        int n = Math.min(this.endIndex, this.startIndex);
        int n2 = Math.max(this.endIndex, this.startIndex);
        return string.substring(n, n2);
    }

    private String deleteSelectionFromText(String string) {
        if (this.startIndex == this.endIndex) {
            return string;
        }
        int n = Math.min(this.endIndex, this.startIndex);
        int n2 = Math.max(this.endIndex, this.startIndex);
        String string2 = string.substring(0, n) + string.substring(n2);
        this.startIndex = this.endIndex = n;
        return string2;
    }

    private void moveCursorToStart(boolean bl) {
        this.endIndex = 0;
        this.deselectSelection(bl);
    }

    public void moveCursorToEnd() {
        this.moveCursorToEnd(true);
    }

    private void moveCursorToEnd(boolean bl) {
        this.endIndex = this.textSupplier.get().length();
        this.deselectSelection(bl);
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public void moveCursorTo(int n, boolean bl) {
        this.endIndex = this.clampIndexToTextLength(n);
        this.deselectSelection(bl);
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public void setSelection(int n, int n2) {
        int n3 = this.textSupplier.get().length();
        this.endIndex = MathHelper.clamp(n, 0, n3);
        this.startIndex = MathHelper.clamp(n2, 0, n3);
    }

    public boolean hasSelection() {
        return this.endIndex != this.startIndex;
    }

    private static void lambda$getClipboardTextSetter$1(Minecraft minecraft, String string) {
        TextInputUtil.setClipboardText(minecraft, string);
    }

    private static String lambda$getClipboardTextSupplier$0(Minecraft minecraft) {
        return TextInputUtil.getClipboardText(minecraft);
    }
}

