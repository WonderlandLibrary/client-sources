/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TextPropertiesManager;

public class RenderComponentsUtil {
    private static final IReorderingProcessor field_238502_a_ = IReorderingProcessor.fromCodePoint(32, Style.EMPTY);

    private static String func_238504_a_(String string) {
        return Minecraft.getInstance().gameSettings.chatColor ? string : TextFormatting.getTextWithoutFormattingCodes(string);
    }

    public static List<IReorderingProcessor> func_238505_a_(ITextProperties iTextProperties, int n, FontRenderer fontRenderer) {
        TextPropertiesManager textPropertiesManager = new TextPropertiesManager();
        iTextProperties.getComponentWithStyle((arg_0, arg_1) -> RenderComponentsUtil.lambda$func_238505_a_$0(textPropertiesManager, arg_0, arg_1), Style.EMPTY);
        ArrayList<IReorderingProcessor> arrayList = Lists.newArrayList();
        fontRenderer.getCharacterManager().func_243242_a(textPropertiesManager.func_238156_b_(), n, Style.EMPTY, (arg_0, arg_1) -> RenderComponentsUtil.lambda$func_238505_a_$1(arrayList, arg_0, arg_1));
        return arrayList.isEmpty() ? Lists.newArrayList(IReorderingProcessor.field_242232_a) : arrayList;
    }

    private static void lambda$func_238505_a_$1(List list, ITextProperties iTextProperties, Boolean bl) {
        IReorderingProcessor iReorderingProcessor = LanguageMap.getInstance().func_241870_a(iTextProperties);
        list.add(bl != false ? IReorderingProcessor.func_242234_a(field_238502_a_, iReorderingProcessor) : iReorderingProcessor);
    }

    private static Optional lambda$func_238505_a_$0(TextPropertiesManager textPropertiesManager, Style style, String string) {
        textPropertiesManager.func_238155_a_(ITextProperties.func_240653_a_(RenderComponentsUtil.func_238504_a_(string), style));
        return Optional.empty();
    }
}

