/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.list;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.client.gui.widget.list.AbstractOptionList;

public class OptionsRowList
extends AbstractOptionList<Row> {
    public OptionsRowList(Minecraft minecraft, int n, int n2, int n3, int n4, int n5) {
        super(minecraft, n, n2, n3, n4, n5);
        this.centerListVertically = false;
    }

    public int addOption(AbstractOption abstractOption) {
        return this.addEntry(Row.create(this.minecraft.gameSettings, this.width, abstractOption));
    }

    public void addOption(AbstractOption abstractOption, @Nullable AbstractOption abstractOption2) {
        this.addEntry(Row.create(this.minecraft.gameSettings, this.width, abstractOption, abstractOption2));
    }

    public void addOptions(AbstractOption[] abstractOptionArray) {
        for (int i = 0; i < abstractOptionArray.length; i += 2) {
            this.addOption(abstractOptionArray[i], i < abstractOptionArray.length - 1 ? abstractOptionArray[i + 1] : null);
        }
    }

    @Override
    public int getRowWidth() {
        return 1;
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 32;
    }

    @Nullable
    public Widget func_243271_b(AbstractOption abstractOption) {
        for (Row row : this.getEventListeners()) {
            for (Widget widget : row.widgets) {
                if (!(widget instanceof OptionButton) || ((OptionButton)widget).func_238517_a_() != abstractOption) continue;
                return widget;
            }
        }
        return null;
    }

    public Optional<Widget> func_238518_c_(double d, double d2) {
        for (Row row : this.getEventListeners()) {
            for (Widget widget : row.widgets) {
                if (!widget.isMouseOver(d, d2)) continue;
                return Optional.of(widget);
            }
        }
        return Optional.empty();
    }

    public static class Row
    extends AbstractOptionList.Entry<Row> {
        private final List<Widget> widgets;

        private Row(List<Widget> list) {
            this.widgets = list;
        }

        public static Row create(GameSettings gameSettings, int n, AbstractOption abstractOption) {
            return new Row(ImmutableList.of(abstractOption.createWidget(gameSettings, n / 2 - 155, 0, 310)));
        }

        public static Row create(GameSettings gameSettings, int n, AbstractOption abstractOption, @Nullable AbstractOption abstractOption2) {
            Widget widget = abstractOption.createWidget(gameSettings, n / 2 - 155, 0, 150);
            return abstractOption2 == null ? new Row(ImmutableList.of(widget)) : new Row(ImmutableList.of(widget, abstractOption2.createWidget(gameSettings, n / 2 - 155 + 160, 0, 150)));
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.widgets.forEach(arg_0 -> Row.lambda$render$0(n2, matrixStack, n6, n7, f, arg_0));
        }

        @Override
        public List<? extends IGuiEventListener> getEventListeners() {
            return this.widgets;
        }

        private static void lambda$render$0(int n, MatrixStack matrixStack, int n2, int n3, float f, Widget widget) {
            widget.y = n;
            widget.render(matrixStack, n2, n3, f);
        }
    }
}

