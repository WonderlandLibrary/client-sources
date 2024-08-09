/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.PatternReplacementResult;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleSetter;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.ComponentRenderer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class TextReplacementRenderer
implements ComponentRenderer<State> {
    static final TextReplacementRenderer INSTANCE = new TextReplacementRenderer();

    private TextReplacementRenderer() {
    }

    @Override
    @NotNull
    public Component render(@NotNull Component component, @NotNull State state) {
        ComponentLike componentLike;
        Object object;
        int n;
        ArrayList<Component> arrayList;
        HoverEvent<?> hoverEvent;
        if (!state.running) {
            return component;
        }
        boolean bl = state.firstMatch;
        state.firstMatch = true;
        List<Component> list = component.children();
        int n2 = list.size();
        StyleSetter<Style> styleSetter = component.style();
        ArrayList<TextComponent> arrayList2 = null;
        Component component2 = component;
        if (component instanceof TextComponent) {
            hoverEvent = ((TextComponent)component).content();
            arrayList = state.pattern.matcher((CharSequence)((Object)hoverEvent));
            n = 0;
            while (((Matcher)((Object)arrayList)).find()) {
                if ((object = state.continuer.shouldReplace((MatchResult)((Object)arrayList), ++state.matchCount, state.replaceCount)) == PatternReplacementResult.CONTINUE) continue;
                if (object == PatternReplacementResult.STOP) {
                    state.running = false;
                    break;
                }
                if (((Matcher)((Object)arrayList)).start() == 0) {
                    if (((Matcher)((Object)arrayList)).end() == ((String)((Object)hoverEvent)).length()) {
                        componentLike = state.replacement.apply((MatchResult)((Object)arrayList), (TextComponent.Builder)Component.text().content(((Matcher)((Object)arrayList)).group()).style(component.style()));
                        Component component3 = component2 = componentLike == null ? Component.empty() : componentLike.asComponent();
                        if (component2.style().hoverEvent() != null) {
                            styleSetter = styleSetter.hoverEvent((HoverEventSource)null);
                        }
                        component2 = component2.style(component2.style().merge(component.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET));
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList(n2 + component2.children().size());
                            arrayList2.addAll(component2.children());
                        }
                    } else {
                        component2 = Component.text("", component.style());
                        componentLike = state.replacement.apply((MatchResult)((Object)arrayList), Component.text().content(((Matcher)((Object)arrayList)).group()));
                        if (componentLike != null) {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList(n2 + 1);
                            }
                            arrayList2.add((TextComponent)componentLike.asComponent());
                        }
                    }
                } else {
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList(n2 + 2);
                    }
                    if (state.firstMatch) {
                        component2 = ((TextComponent)component).content(((String)((Object)hoverEvent)).substring(0, ((Matcher)((Object)arrayList)).start()));
                    } else if (n < ((Matcher)((Object)arrayList)).start()) {
                        arrayList2.add(Component.text(((String)((Object)hoverEvent)).substring(n, ((Matcher)((Object)arrayList)).start())));
                    }
                    componentLike = state.replacement.apply((MatchResult)((Object)arrayList), Component.text().content(((Matcher)((Object)arrayList)).group()));
                    if (componentLike != null) {
                        arrayList2.add((TextComponent)componentLike.asComponent());
                    }
                }
                ++state.replaceCount;
                state.firstMatch = false;
                n = ((Matcher)((Object)arrayList)).end();
            }
            if (n < ((String)((Object)hoverEvent)).length() && n > 0) {
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList<TextComponent>(n2);
                }
                arrayList2.add(Component.text(((String)((Object)hoverEvent)).substring(n)));
            }
        } else if (component2 instanceof TranslatableComponent) {
            hoverEvent = ((TranslatableComponent)component2).args();
            arrayList = null;
            int n3 = hoverEvent.size();
            for (n = 0; n < n3; ++n) {
                componentLike = (Component)hoverEvent.get(n);
                Component component4 = this.render((Component)componentLike, state);
                if (component4 != component && arrayList == null) {
                    arrayList = new ArrayList<Component>(n3);
                    if (n > 0) {
                        arrayList.addAll(hoverEvent.subList(0, n));
                    }
                }
                if (arrayList == null) continue;
                arrayList.add(component4);
            }
            if (arrayList != null) {
                component2 = ((TranslatableComponent)component2).args((List<? extends ComponentLike>)arrayList);
            }
        }
        if (state.running) {
            hoverEvent = styleSetter.hoverEvent();
            if (hoverEvent != null && hoverEvent != (arrayList = hoverEvent.withRenderedValue(this, state))) {
                component2 = component2.style(arg_0 -> TextReplacementRenderer.lambda$render$0((HoverEvent)((Object)arrayList), arg_0));
            }
            boolean bl2 = true;
            for (n = 0; n < n2; ++n) {
                object = list.get(n);
                componentLike = this.render((Component)object, state);
                if (componentLike != object) {
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList(n2);
                    }
                    if (bl2) {
                        arrayList2.addAll(list.subList(0, n));
                    }
                    bl2 = false;
                }
                if (arrayList2 == null) continue;
                arrayList2.add((TextComponent)componentLike);
                bl2 = false;
            }
        } else if (arrayList2 != null) {
            arrayList2.addAll(list);
        }
        state.firstMatch = bl;
        if (arrayList2 != null) {
            return component2.children((List<? extends ComponentLike>)arrayList2);
        }
        return component2;
    }

    @Override
    @NotNull
    public Component render(@NotNull Component component, @NotNull Object object) {
        return this.render(component, (State)object);
    }

    private static void lambda$render$0(HoverEvent hoverEvent, Style.Builder builder) {
        builder.hoverEvent((HoverEventSource)hoverEvent);
    }

    static final class State {
        final Pattern pattern;
        final BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike> replacement;
        final TextReplacementConfig.Condition continuer;
        boolean running = true;
        int matchCount = 0;
        int replaceCount = 0;
        boolean firstMatch = true;

        State(@NotNull Pattern pattern, @NotNull BiFunction<MatchResult, TextComponent.Builder, @Nullable ComponentLike> biFunction,  @NotNull TextReplacementConfig.Condition condition) {
            this.pattern = pattern;
            this.replacement = biFunction;
            this.continuer = condition;
        }
    }
}

