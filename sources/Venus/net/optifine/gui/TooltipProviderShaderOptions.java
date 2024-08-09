/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.TooltipProviderOptions;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.gui.GuiButtonShaderOption;
import net.optifine.util.StrUtils;

public class TooltipProviderShaderOptions
extends TooltipProviderOptions {
    @Override
    public String[] getTooltipLines(Widget widget, int n) {
        if (!(widget instanceof GuiButtonShaderOption)) {
            return null;
        }
        GuiButtonShaderOption guiButtonShaderOption = (GuiButtonShaderOption)widget;
        ShaderOption shaderOption = guiButtonShaderOption.getShaderOption();
        return this.makeTooltipLines(shaderOption, n);
    }

    private String[] makeTooltipLines(ShaderOption shaderOption, int n) {
        Object object;
        String string = shaderOption.getNameText();
        String string2 = Config.normalize(shaderOption.getDescriptionText()).trim();
        String[] stringArray = this.splitDescription(string2);
        GameSettings gameSettings = Config.getGameSettings();
        String string3 = null;
        if (!string.equals(shaderOption.getName()) && gameSettings.advancedItemTooltips) {
            string3 = "\u00a78" + Lang.get("of.general.id") + ": " + shaderOption.getName();
        }
        String string4 = null;
        if (shaderOption.getPaths() != null && gameSettings.advancedItemTooltips) {
            string4 = "\u00a78" + Lang.get("of.general.from") + ": " + Config.arrayToString(shaderOption.getPaths());
        }
        String string5 = null;
        if (shaderOption.getValueDefault() != null && gameSettings.advancedItemTooltips) {
            object = shaderOption.isEnabled() ? shaderOption.getValueText(shaderOption.getValueDefault()) : Lang.get("of.general.ambiguous");
            string5 = "\u00a78" + Lang.getDefault() + ": " + (String)object;
        }
        object = new ArrayList<String>();
        object.add(string);
        object.addAll(Arrays.asList(stringArray));
        if (string3 != null) {
            object.add(string3);
        }
        if (string4 != null) {
            object.add(string4);
        }
        if (string5 != null) {
            object.add(string5);
        }
        return this.makeTooltipLines(n, (List<String>)object);
    }

    private String[] splitDescription(String string) {
        if (string.length() <= 0) {
            return new String[0];
        }
        string = StrUtils.removePrefix(string, "//");
        String[] stringArray = string.split("\\. ");
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = "- " + stringArray[i].trim();
            stringArray[i] = StrUtils.removeSuffix(stringArray[i], ".");
        }
        return stringArray;
    }

    private String[] makeTooltipLines(int n, List<String> list) {
        FontRenderer fontRenderer = Config.getMinecraft().fontRenderer;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < list.size(); ++i) {
            String string = list.get(i);
            if (string == null || string.length() <= 0) continue;
            StringTextComponent stringTextComponent = new StringTextComponent(string);
            for (ITextProperties iTextProperties : fontRenderer.getCharacterManager().func_238362_b_(stringTextComponent, n, Style.EMPTY)) {
                arrayList.add(iTextProperties.getString());
            }
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }
}

