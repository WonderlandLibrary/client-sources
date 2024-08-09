/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorFlag;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
@ApiStatus.NonExtendable
public interface ComponentIteratorType {
    public static final ComponentIteratorType DEPTH_FIRST = ComponentIteratorType::lambda$static$0;
    public static final ComponentIteratorType BREADTH_FIRST = ComponentIteratorType::lambda$static$1;

    public void populate(@NotNull Component var1, @NotNull Deque<Component> var2, @NotNull Set<ComponentIteratorFlag> var3);

    private static void lambda$static$1(Component component, Deque deque, Set set) {
        HoverEvent<?> hoverEvent;
        if (set.contains((Object)ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS) && component instanceof TranslatableComponent) {
            deque.addAll(((TranslatableComponent)component).args());
        }
        if ((hoverEvent = component.hoverEvent()) != null) {
            HoverEvent.Action<?> action = hoverEvent.action();
            if (set.contains((Object)ComponentIteratorFlag.INCLUDE_HOVER_SHOW_ENTITY_NAME) && action == HoverEvent.Action.SHOW_ENTITY) {
                deque.addLast(((HoverEvent.ShowEntity)hoverEvent.value()).name());
            } else if (set.contains((Object)ComponentIteratorFlag.INCLUDE_HOVER_SHOW_TEXT_COMPONENT) && action == HoverEvent.Action.SHOW_TEXT) {
                deque.addLast((Component)hoverEvent.value());
            }
        }
        deque.addAll(component.children());
    }

    private static void lambda$static$0(Component component, Deque deque, Set set) {
        int n;
        List<Component> list;
        HoverEventSource<Object> hoverEventSource;
        if (set.contains((Object)ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS) && component instanceof TranslatableComponent) {
            hoverEventSource = (TranslatableComponent)component;
            list = hoverEventSource.args();
            for (n = list.size() - 1; n >= 0; --n) {
                deque.addFirst(list.get(n));
            }
        }
        if ((hoverEventSource = component.hoverEvent()) != null) {
            list = ((HoverEvent)hoverEventSource).action();
            if (set.contains((Object)ComponentIteratorFlag.INCLUDE_HOVER_SHOW_ENTITY_NAME) && list == HoverEvent.Action.SHOW_ENTITY) {
                deque.addFirst(((HoverEvent.ShowEntity)((HoverEvent)hoverEventSource).value()).name());
            } else if (set.contains((Object)ComponentIteratorFlag.INCLUDE_HOVER_SHOW_TEXT_COMPONENT) && list == HoverEvent.Action.SHOW_TEXT) {
                deque.addFirst((Component)((HoverEvent)hoverEventSource).value());
            }
        }
        list = component.children();
        for (n = list.size() - 1; n >= 0; --n) {
            deque.addFirst(list.get(n));
        }
    }
}

