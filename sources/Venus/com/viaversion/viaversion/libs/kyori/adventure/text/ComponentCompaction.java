/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.VisibleForTesting
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleSetter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

final class ComponentCompaction {
    @VisibleForTesting
    static final boolean SIMPLIFY_STYLE_FOR_BLANK_COMPONENTS = false;

    private ComponentCompaction() {
    }

    static Component compact(@NotNull Component component, @Nullable Style style) {
        Component component2;
        StyleSetter<Component> styleSetter;
        int n;
        StyleSetter<Style> styleSetter2;
        int n2;
        List<Component> list = component.children();
        Component component3 = component.children(Collections.emptyList());
        if (style != null) {
            component3 = component3.style(component.style().unmerge(style));
        }
        if ((n2 = list.size()) == 0) {
            if (ComponentCompaction.isBlank(component3)) {
                component3 = component3.style(ComponentCompaction.simplifyStyleForBlank(component3.style(), style));
            }
            return component3;
        }
        if (n2 == 1 && component3 instanceof TextComponent && (styleSetter2 = (TextComponent)component3).content().isEmpty()) {
            Component component4 = list.get(0);
            return component4.style(component4.style().merge(component3.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET)).compact();
        }
        styleSetter2 = component3.style();
        if (style != null) {
            styleSetter2 = styleSetter2.merge(style, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
        }
        ArrayList<Component> arrayList = new ArrayList<Component>(list.size());
        for (n = 0; n < list.size(); ++n) {
            styleSetter = list.get(n);
            if ((styleSetter = ComponentCompaction.compact((Component)styleSetter, styleSetter2)).children().isEmpty() && styleSetter instanceof TextComponent && (component2 = (TextComponent)styleSetter).content().isEmpty()) continue;
            arrayList.add((Component)styleSetter);
        }
        if (component3 instanceof TextComponent) {
            while (!arrayList.isEmpty()) {
                Component component5 = (Component)arrayList.get(0);
                styleSetter = component5.style().merge((Style)styleSetter2, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
                if (!(component5 instanceof TextComponent) || !Objects.equals(styleSetter, styleSetter2)) break;
                component3 = ComponentCompaction.joinText((TextComponent)component3, (TextComponent)component5);
                arrayList.remove(0);
                arrayList.addAll(0, component5.children());
            }
        }
        n = 0;
        while (n + 1 < arrayList.size()) {
            Style style2;
            Style style3;
            styleSetter = (Component)arrayList.get(n);
            component2 = (Component)arrayList.get(n + 1);
            if (styleSetter.children().isEmpty() && styleSetter instanceof TextComponent && component2 instanceof TextComponent && (style3 = styleSetter.style().merge((Style)styleSetter2, Style.Merge.Strategy.IF_ABSENT_ON_TARGET)).equals(style2 = component2.style().merge((Style)styleSetter2, Style.Merge.Strategy.IF_ABSENT_ON_TARGET))) {
                TextComponent textComponent = ComponentCompaction.joinText((TextComponent)styleSetter, (TextComponent)component2);
                arrayList.set(n, textComponent);
                arrayList.remove(n + 1);
                continue;
            }
            ++n;
        }
        if (arrayList.isEmpty() && ComponentCompaction.isBlank(component3)) {
            component3 = component3.style(ComponentCompaction.simplifyStyleForBlank(component3.style(), style));
        }
        return component3.children(arrayList);
    }

    private static boolean isBlank(Component component) {
        if (component instanceof TextComponent) {
            TextComponent textComponent = (TextComponent)component;
            String string = textComponent.content();
            for (int i = 0; i < string.length(); ++i) {
                char c = string.charAt(i);
                if (c == ' ') continue;
                return true;
            }
            return false;
        }
        return true;
    }

    @NotNull
    private static Style simplifyStyleForBlank(@NotNull Style style, @Nullable Style style2) {
        return style;
    }

    private static TextComponent joinText(TextComponent textComponent, TextComponent textComponent2) {
        return TextComponentImpl.create(textComponent2.children(), textComponent.style(), textComponent.content() + textComponent2.content());
    }
}

