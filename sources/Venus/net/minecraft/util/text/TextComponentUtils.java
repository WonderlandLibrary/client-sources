/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITargetedTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;

public class TextComponentUtils {
    public static IFormattableTextComponent func_240648_a_(IFormattableTextComponent iFormattableTextComponent, Style style) {
        if (style.isEmpty()) {
            return iFormattableTextComponent;
        }
        Style style2 = iFormattableTextComponent.getStyle();
        if (style2.isEmpty()) {
            return iFormattableTextComponent.setStyle(style);
        }
        return style2.equals(style) ? iFormattableTextComponent : iFormattableTextComponent.setStyle(style2.mergeStyle(style));
    }

    public static IFormattableTextComponent func_240645_a_(@Nullable CommandSource commandSource, ITextComponent iTextComponent, @Nullable Entity entity2, int n) throws CommandSyntaxException {
        if (n > 100) {
            return iTextComponent.deepCopy();
        }
        IFormattableTextComponent iFormattableTextComponent = iTextComponent instanceof ITargetedTextComponent ? ((ITargetedTextComponent)((Object)iTextComponent)).func_230535_a_(commandSource, entity2, n + 1) : iTextComponent.copyRaw();
        for (ITextComponent iTextComponent2 : iTextComponent.getSiblings()) {
            iFormattableTextComponent.append(TextComponentUtils.func_240645_a_(commandSource, iTextComponent2, entity2, n + 1));
        }
        return iFormattableTextComponent.mergeStyle(TextComponentUtils.func_240646_a_(commandSource, iTextComponent.getStyle(), entity2, n));
    }

    private static Style func_240646_a_(@Nullable CommandSource commandSource, Style style, @Nullable Entity entity2, int n) throws CommandSyntaxException {
        ITextComponent iTextComponent;
        HoverEvent hoverEvent = style.getHoverEvent();
        if (hoverEvent != null && (iTextComponent = hoverEvent.getParameter(HoverEvent.Action.SHOW_TEXT)) != null) {
            HoverEvent hoverEvent2 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponentUtils.func_240645_a_(commandSource, iTextComponent, entity2, n + 1));
            return style.setHoverEvent(hoverEvent2);
        }
        return style;
    }

    public static ITextComponent getDisplayName(GameProfile gameProfile) {
        if (gameProfile.getName() != null) {
            return new StringTextComponent(gameProfile.getName());
        }
        return gameProfile.getId() != null ? new StringTextComponent(gameProfile.getId().toString()) : new StringTextComponent("(unknown)");
    }

    public static ITextComponent makeGreenSortedList(Collection<String> collection) {
        return TextComponentUtils.makeSortedList(collection, TextComponentUtils::lambda$makeGreenSortedList$0);
    }

    public static <T extends Comparable<T>> ITextComponent makeSortedList(Collection<T> collection, Function<T, ITextComponent> function) {
        if (collection.isEmpty()) {
            return StringTextComponent.EMPTY;
        }
        if (collection.size() == 1) {
            return function.apply((Comparable)collection.iterator().next());
        }
        ArrayList<T> arrayList = Lists.newArrayList(collection);
        arrayList.sort(Comparable::compareTo);
        return TextComponentUtils.func_240649_b_(arrayList, function);
    }

    public static <T> IFormattableTextComponent func_240649_b_(Collection<T> collection, Function<T, ITextComponent> function) {
        if (collection.isEmpty()) {
            return new StringTextComponent("");
        }
        if (collection.size() == 1) {
            return function.apply(collection.iterator().next()).deepCopy();
        }
        StringTextComponent stringTextComponent = new StringTextComponent("");
        boolean bl = true;
        for (T t : collection) {
            if (!bl) {
                stringTextComponent.append(new StringTextComponent(", ").mergeStyle(TextFormatting.GRAY));
            }
            stringTextComponent.append(function.apply(t));
            bl = false;
        }
        return stringTextComponent;
    }

    public static IFormattableTextComponent wrapWithSquareBrackets(ITextComponent iTextComponent) {
        return new TranslationTextComponent("chat.square_brackets", iTextComponent);
    }

    public static ITextComponent toTextComponent(Message message) {
        return message instanceof ITextComponent ? (ITextComponent)message : new StringTextComponent(message.getString());
    }

    private static ITextComponent lambda$makeGreenSortedList$0(String string) {
        return new StringTextComponent(string).mergeStyle(TextFormatting.GREEN);
    }
}

