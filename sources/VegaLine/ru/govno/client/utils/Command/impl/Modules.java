/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import net.minecraft.client.Minecraft;
import ru.govno.client.Client;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientTune;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Command.Command;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Render.ColorUtils;

public class Modules
extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public Modules() {
        super("Modules", new String[]{"ss", "setsetting", "module", "m"});
    }

    @Override
    public void onCommand(String[] args) {
        try {
            if (args[1].equalsIgnoreCase("reload")) {
                Panic.enablePanic();
                Panic.disablePanic();
                Client.msg("\u00a76\u00a7lClient:\u00a7r \u00a77\u0412\u0441\u0435 \u0441\u0438\u0441\u0442\u0435\u043c\u044b \u0432\u043e\u0437\u043e\u0431\u043d\u043e\u0432\u043b\u0435\u043d\u044b.", false);
            } else if (Client.moduleManager.getModule(args[1]) == null) {
                Client.msg("\u00a72\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434\u0443\u043b\u044f \u0441 \u0438\u043c\u0435\u043d\u0435\u043c [\u00a7l" + args[1] + "\u00a7r\u00a77] \u00a77\u043d\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442", false);
            } else {
                Module module = Client.moduleManager.getModule(args[1]);
                if (args[2].equalsIgnoreCase("enable") || args[2].equalsIgnoreCase("on") || args[2].equalsIgnoreCase("+")) {
                    if (!module.actived) {
                        module.toggle(true);
                        Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434\u0443\u043b\u044c [\u00a7l" + args[1] + "\u00a7r\u00a77] \u00a77\u0442\u0435\u043f\u0435\u0440\u044c \u0432\u043a\u043b\u044e\u0447\u0435\u043d", false);
                    } else {
                        Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434\u0443\u043b\u044c [\u00a7l" + args[1] + "\u00a7r\u00a77] \u00a77\u0443\u0436\u0435 \u0432\u043a\u043b\u044e\u0447\u0435\u043d", false);
                    }
                } else if (args[2].equalsIgnoreCase("disable") || args[2].equalsIgnoreCase("off") || args[2].equalsIgnoreCase("-")) {
                    if (module.actived) {
                        module.toggle(false);
                        Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434\u0443\u043b\u044c [\u00a7l" + args[1] + "\u00a7r\u00a77] \u00a77\u0442\u0435\u043f\u0435\u0440\u044c \u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d", false);
                    } else {
                        Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434\u0443\u043b\u044c [\u00a7l" + args[1] + "\u00a7r\u00a77] \u00a77\u0443\u0436\u0435 \u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d", false);
                    }
                } else if (args[2].equalsIgnoreCase("toggle") || args[2].equalsIgnoreCase("tog")) {
                    module.toggle(!module.actived);
                    Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434\u0443\u043b\u044c [\u00a7l" + args[1] + "\u00a7r\u00a77] \u00a77\u0442\u0435\u043f\u0435\u0440\u044c " + (module.actived ? "\u0432\u043a\u043b\u044e\u0447\u0435\u043d" : "\u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d"), false);
                } else {
                    String[] num2;
                    Settings setting = null;
                    Settings.Category category = null;
                    for (Settings set : module.settings) {
                        String num = set.getName();
                        num2 = set.getName().toLowerCase();
                        String num3 = set.getName().toUpperCase();
                        if (!num.equalsIgnoreCase(args[2].replace("_", " ")) && !num2.equalsIgnoreCase(args[2].replace("_", " ")) && !num3.equalsIgnoreCase(args[2].replace("_", " "))) continue;
                        setting = set;
                        category = set.category;
                    }
                    if (setting == null) {
                        Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u0441 \u0438\u043c\u0435\u043d\u0435\u043c [\u00a7l" + args[2].replace("_", " ") + "\u00a7r\u00a77] \u00a77\u043d\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442", false);
                    } else if (category == Settings.Category.Boolean) {
                        boolean correct;
                        boolean bl = correct = args[3].equalsIgnoreCase("on") || args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("+") || args[3].equalsIgnoreCase("off") || args[3].equalsIgnoreCase("false") || args[3].equalsIgnoreCase("-") || args[3].equalsIgnoreCase("toggle") || args[3].equalsIgnoreCase("tog");
                        if (!correct) {
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0447\u0435\u043a: ss [\u00a7lNAME\u00a7r\u00a77] [\u00a7lCheck\u00a7r\u00a77] [\u00a7lon/true/+/off/false/-/toggle/tog\u00a7r\u00a77]", false);
                        } else {
                            boolean act;
                            boolean bl2 = args[3].equalsIgnoreCase("on") || args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("+") || !args[3].equalsIgnoreCase("off") && !args[3].equalsIgnoreCase("false") && !args[3].equalsIgnoreCase("-") && (args[3].equalsIgnoreCase("toggle") || args[3].equalsIgnoreCase("tog")) != setting.bValue ? true : (act = false);
                            if (setting.bValue == act) {
                                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430 [\u00a7l" + setting.getName() + "\u00a7r\u00a77] \u00a77\u0443 " + module.getName() + " \u0443\u0436\u0435 " + (setting.bValue ? "\u0432\u043a\u043b\u044e\u0447\u0435\u043d\u0430" : "\u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d\u0430"), false);
                            } else {
                                setting.bValue = act;
                                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430 [\u00a7l" + setting.getName() + "\u00a7r\u00a77] \u00a77\u0443 " + module.getName() + " \u0442\u0435\u043f\u0435\u0440\u044c " + (setting.bValue ? "\u0432\u043a\u043b\u044e\u0447\u0435\u043d\u0430" : "\u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d\u0430"), false);
                                ClientTune.get.playGuiScreenCheckBox(!setting.bValue);
                            }
                        }
                    } else if (category == Settings.Category.String_Massive) {
                        String mod = setting.currentMode;
                        boolean correct = false;
                        String newMode = null;
                        num2 = setting.modes;
                        int num3 = num2.length;
                        for (int i = 0; i < num3; ++i) {
                            String mode;
                            String numMode = mode = num2[i];
                            String numMode2 = mode.toLowerCase();
                            String numMode3 = mode.toUpperCase();
                            if (!args[3].equalsIgnoreCase(numMode) && !args[3].equalsIgnoreCase(numMode2) && !args[3].equalsIgnoreCase(numMode3)) continue;
                            newMode = mode;
                        }
                        if (newMode == null) {
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434\u0430 \u0441 \u0438\u043c\u0435\u043d\u0435\u043c [\u00a7l" + args[3] + "\u00a7r\u00a77] \u00a77\u043d\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442", false);
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0441\u043f\u0438\u043e\u043a \u0441\u0443\u0449\u0435\u0441\u0432\u0443\u044e\u0449\u0438\u0445 \u043c\u043e\u0434\u043e\u0432:", false);
                            int number = 0;
                            for (String mode : setting.modes) {
                                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434 \u2116" + ++number + ": " + mode, false);
                            }
                        } else if (setting.currentMode == newMode) {
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434 [\u00a7l" + setting.getName() + "\u00a7r\u00a77]  \u0443\u0436\u0435 \u044f\u0432\u043b\u044f\u0435\u0442\u0441\u044f [\u00a7l" + newMode + "\u00a7r\u00a77] \u00a77", false);
                        } else {
                            setting.currentMode = newMode;
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434 [\u00a7l" + setting.getName() + "\u00a7r\u00a77]  \u0442\u0435\u043f\u0435\u0440\u044c \u044f\u0432\u043b\u044f\u0435\u0442\u0441\u044f [\u00a7l" + newMode + "\u00a7r\u00a77] \u00a77", false);
                        }
                    } else if (category == Settings.Category.Color) {
                        int col = setting.color;
                        int red = ColorUtils.getRedFromColor(col);
                        int green = ColorUtils.getGreenFromColor(col);
                        int blue = ColorUtils.getBlueFromColor(col);
                        int alpha = ColorUtils.getAlphaFromColor(col);
                        boolean rgba = args.length == 7 && this.isColorValue(args[3]) && this.isColorValue(args[4]) && this.isColorValue(args[5]) && this.isColorValue(args[6]);
                        boolean rgb = args.length == 6 && this.isColorValue(args[3]) && this.isColorValue(args[4]) && this.isColorValue(args[5]);
                        boolean brightAlpha = args.length == 5 && this.isColorValue(args[3]) && this.isColorValue(args[4]);
                        boolean bright = args.length == 4 && this.isColorValue(args[3]);
                        boolean isPreset = this.isColor(args[3]);
                        int preset = this.getColorValue(args[3]);
                        if (rgba) {
                            red = Integer.parseInt(args[3]);
                            green = Integer.parseInt(args[4]);
                            blue = Integer.parseInt(args[5]);
                            alpha = Integer.parseInt(args[6]);
                        } else if (rgb) {
                            red = Integer.parseInt(args[3]);
                            green = Integer.parseInt(args[4]);
                            blue = Integer.parseInt(args[5]);
                            alpha = 255;
                        } else if (brightAlpha) {
                            red = Integer.parseInt(args[3]);
                            green = Integer.parseInt(args[3]);
                            blue = Integer.parseInt(args[3]);
                            alpha = Integer.parseInt(args[4]);
                        } else if (bright) {
                            red = Integer.parseInt(args[3]);
                            green = Integer.parseInt(args[3]);
                            blue = Integer.parseInt(args[3]);
                            alpha = 255;
                        } else if (isPreset) {
                            red = ColorUtils.getRedFromColor(preset);
                            green = ColorUtils.getGreenFromColor(preset);
                            blue = ColorUtils.getBlueFromColor(preset);
                            alpha = ColorUtils.getAlphaFromColor(preset);
                        } else {
                            int c = Integer.parseInt(args[3]);
                            red = ColorUtils.getRedFromColor(c);
                            green = ColorUtils.getGreenFromColor(c);
                            blue = ColorUtils.getBlueFromColor(c);
                            alpha = ColorUtils.getAlphaFromColor(c);
                        }
                        col = ColorUtils.getColor(red, green, blue, alpha);
                        if (col > Integer.MAX_VALUE || col < Integer.MIN_VALUE) {
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0446\u0432\u0435\u0442 \u043d\u0435 \u044f\u0432\u043b\u044f\u0435\u0442\u0441\u044f \u0434\u043e\u043f\u0443\u0441\u0442\u0438\u043c\u044b\u043c \u043a \u043f\u0440\u0438\u043c\u0435\u043d\u0435\u043d\u0438\u044e\u00a77", false);
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0446\u0432\u0435\u0442: ss [\u00a7lNAME\u00a7r\u00a77] [\u00a7lColor\u00a7r\u00a77] [\u00a7lrgba/rgb/ba/b/integer\u00a7r\u00a77]", false);
                            return;
                        }
                        String rgbaSee = (String)(this.getColorName(col) != null ? this.getColorName(col) : "red:" + red + ", green:" + green + ", blue:" + blue) + (String)(alpha == 255 || alpha == 0 ? "" : ", alpha:" + alpha);
                        if (setting.color == col) {
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0446\u0432\u0435\u0442 [\u00a7l" + setting.getName() + "\u00a7r\u00a77]  \u0443\u0436\u0435 \u0440\u0430\u0432\u0435\u043d [\u00a7l" + rgbaSee + "\u00a7r\u00a77] \u00a77", false);
                        } else {
                            setting.color = col;
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0446\u0432\u0435\u0442 [\u00a7l" + setting.getName() + "\u00a7r\u00a77]  \u0442\u0435\u043f\u0435\u0440\u044c \u0440\u0430\u0432\u0435\u043d [\u00a7l" + rgbaSee + "\u00a7r\u00a77] \u00a77", false);
                        }
                    } else if (category == Settings.Category.Float) {
                        float flot = setting.fValue;
                        Float val = Float.valueOf(Float.parseFloat(args[3]));
                        if (val.isNaN()) {
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0432\u044b \u0432\u0432\u0435\u043b\u0438 \u043d\u0435\u0434\u043e\u043f\u0443\u0441\u0442\u0438\u043c\u043e\u0435 \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u0434\u043b\u044f \u044d\u0442\u043e\u0439 \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438", false);
                            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0441\u043b\u0430\u0439\u0434\u0435\u0440: ss [\u00a7lNAME\u00a7r\u00a77] [\u00a7lSlider\u00a7r\u00a77] [\u00a7lValue\u00a7r\u00a77]", false);
                        } else {
                            Object value = "" + val;
                            if (val.floatValue() == (float)((int)val.floatValue()) && ((String)value).endsWith(".0")) {
                                value = ((String)value).replace(".0", "");
                            }
                            if (setting.fValue == val.floatValue()) {
                                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430 [\u00a7l" + setting.getName() + "\u00a7r\u00a77] \u00a77\u0443 " + module.getName() + " \u0443\u0436\u0435 \u0440\u0430\u0432\u043d\u0430 " + (String)value, false);
                            } else {
                                setting.fValue = val.floatValue();
                                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430 [\u00a7l" + setting.getName() + "\u00a7r\u00a77] \u00a77\u0443 " + module.getName() + " \u0442\u0435\u043f\u0435\u0440\u044c \u0440\u0430\u0432\u043d\u0430 " + (String)value, false);
                            }
                        }
                    } else {
                        Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0442\u0435\u0445\u043d\u0438\u0447\u0435\u0441\u043a\u0438\u0435 \u0448\u043e\u043a\u043e\u043b\u0430\u0434\u043a\u0438 (\u043d\u0435\u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e \u0443\u0437\u043d\u0430\u0442\u044c \u0442\u0438\u043f \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u043c\u043e\u0434\u0443\u043b\u044f :/)", false);
                    }
                }
            }
        } catch (Exception formatException) {
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u044c: ss/setsetting/module/m", false);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043f\u0435\u0440\u0435\u0437\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u0432\u0441\u0451: reload", false);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0432\u043a\u043b\u044e\u0447\u0438\u0442\u044c:[\u00a7lNAME\u00a7r\u00a77] true/on/+", false);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0432\u044b\u043a\u043b\u044e\u0447\u0438\u0442\u044c:[\u00a7lNAME\u00a7r\u00a77] false/off/-", false);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0441\u043b\u0430\u0439\u0434\u0435\u0440:[\u00a7lNAME\u00a7r\u00a77] [\u00a7lSlider\u00a7r\u00a77] [\u00a7lValue\u00a7r\u00a77]", false);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0447\u0435\u043a:[\u00a7lNAME\u00a7r\u00a77] [\u00a7lCheck\u00a7r\u00a77] [\u00a7ltrue/+/off/-/toggle/tog\u00a7r\u00a77]", false);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u043c\u043e\u0434\u044b:[\u00a7lNAME\u00a7r\u00a77] [\u00a7lModes\u00a7r\u00a77] [\u00a7lSelected\u00a7r\u00a77]", false);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77\u0446\u0432\u0435\u0442:[\u00a7lNAME\u00a7r\u00a77] [\u00a7lColor\u00a7r\u00a77] [\u00a7lrgba/rgb/ba/b/int\u00a7r\u00a77]", false);
            formatException.printStackTrace();
        }
    }

    int getColorValue(String text) {
        int c = 1111111111;
        if (text.equalsIgnoreCase("black")) {
            c = ColorUtils.getColor(0, 0, 0);
        } else if (text.equalsIgnoreCase("white")) {
            c = -1;
        } else if (text.equalsIgnoreCase("red")) {
            c = ColorUtils.getColor(255, 0, 0);
        } else if (text.equalsIgnoreCase("green")) {
            c = ColorUtils.getColor(0, 255, 0);
        } else if (text.equalsIgnoreCase("blue")) {
            c = ColorUtils.getColor(0, 0, 255);
        } else if (text.equalsIgnoreCase("gray")) {
            c = ColorUtils.getColor(127, 127, 127);
        }
        return 1111111111;
    }

    String getColorName(int color) {
        if (color == ColorUtils.getColor(0, 0, 0, 0)) {
            return "none";
        }
        if (color == ColorUtils.getColor(0, 0, 0)) {
            return "black";
        }
        if (color == -1) {
            return "white";
        }
        if (color == ColorUtils.getColor(255, 0, 0)) {
            return "red";
        }
        if (color == ColorUtils.getColor(0, 255, 0)) {
            return "green";
        }
        if (color == ColorUtils.getColor(0, 0, 255)) {
            return "blue";
        }
        if (color == ColorUtils.getColor(127, 127, 127)) {
            return "gray";
        }
        return null;
    }

    boolean isColor(String text) {
        return this.getColorValue(text) != 1111111111;
    }

    boolean isColorValue(String text) {
        int val = 1111111111;
        try {
            val = Integer.parseInt(text);
        } catch (Exception exception) {
            // empty catch block
        }
        return val != 1111111111 && val >= 0 && val <= 255;
    }
}

