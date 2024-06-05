package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventBus;
import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Misc.EventKey;
import gq.vapu.czfclient.API.Events.Render.EventRender2D;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.API.Value.Value;
import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Manager.Manager;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.UI.Font.CFontRenderer;
import gq.vapu.czfclient.UI.Font.FontLoaders;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.Math.MathUtil;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class TabUI implements Manager {
    private static int[] CNMSB;
    private Section section = Section.TYPES;
    private ModuleType selectedType = ModuleType.values()[0];
    private Module selectedModule = null;
    private Value selectedValue = null;
    private int currentType = 0;
    private int currentModule = 0;
    private int currentValue = 0;
    private final int height = 100;
    private int maxType;
    private int maxModule;
    private int maxValue;

    static /* synthetic */ int[] QwQ() {
        int[] arrn;
        int[] arrn2 = CNMSB;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[Section.values().length];
        try {
            arrn[Section.MODULES.ordinal()] = 2;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[Section.TYPES.ordinal()] = 1;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        try {
            arrn[Section.VALUES.ordinal()] = 3;
        } catch (NoSuchFieldError noSuchFieldError) {
        }
        CNMSB = arrn;
        return CNMSB;
    }

    @Override
    public void init() {
        CFontRenderer font = FontLoaders.GoogleSans18;
        ModuleType[] arrmoduleType = ModuleType.values();
        int n = arrmoduleType.length;
        int n2 = 0;
        while (n2 < n) {
            ModuleType mt = arrmoduleType[n2];
            if (this.maxType <= font.getStringWidth(mt.name().toUpperCase())) {
                this.maxType = font.getStringWidth(mt.name().toUpperCase());
            }
            ++n2;
        }
        Client.getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            if (this.maxModule > font.getStringWidth(m.getName().toUpperCase()) + 4)
                continue;
            this.maxModule = font.getStringWidth(m.getName().toUpperCase()) + 4;
        }
        Client.getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            if (m.getValues().isEmpty())
                continue;
            for (Value val : m.getValues()) {
                if (this.maxValue > font.getStringWidth(val.getDisplayName().toUpperCase()) + 4)
                    continue;
                this.maxValue = font.getStringWidth(val.getDisplayName().toUpperCase()) + 4;
            }
        }
        this.maxModule += 12;
        this.maxValue += 24;
        boolean highestWidth = false;
        this.maxType = this.maxType < this.maxModule ? this.maxModule : this.maxType;
        this.maxModule += this.maxType;
        this.maxValue += this.maxModule;
        EventBus.getInstance().register(this);
    }

    private void resetValuesLength() {
        this.maxValue = 0;
        for (Value val : this.selectedModule.getValues()) {
            int off;
            int n = off = val instanceof Option ? 6
                    : Helper.mc.fontRendererObj.getStringWidth(String.format(" \u00a77%s", val.getValue().toString()))
                    + 6;
            if (this.maxValue > Helper.mc.fontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + off)
                continue;
            this.maxValue = Helper.mc.fontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + off;
        }
        this.maxValue += this.maxModule;
    }

    @EventHandler
    public void renderTabGUI(EventRender2D e) {
        if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            return;
        }
        if (!ModuleManager.getModuleByClass(HUD.class).isEnabled()) {
            return;
        }
        CFontRenderer font = FontLoaders.GoogleSans16;
        CFontRenderer icon = FontLoaders.NovICON28;
        int categoryY = 22;
        int moduleY = categoryY;
        int valueY = categoryY;
        RenderUtil.R2DUtils.drawRoundedRect(2, categoryY, maxType - 20, categoryY + 14 * 4,
                new Color(255, 255, 255, 90).getRGB(), new Color(255, 255, 255, 90).getRGB());
        ModuleType[] moduleArray = ModuleType.values();
        int mA = moduleArray.length;
        int mA2 = 0;
        while (mA2 < mA) {
            ModuleType mt = moduleArray[mA2];
            if (this.selectedType == mt) {
                //Gui.drawRect(4.5, categoryY + 2, 5.5, categoryY + 12, new Color(47, 154, 241).getRGB());
                RenderUtil.R2DUtils.drawRoundedRect(2.0f, categoryY, maxType - 20, categoryY + 14, new Color(255, 255, 255).getRGB(), new Color(255, 255, 255).getRGB());
                moduleY = categoryY;
            }
            if (this.selectedType == mt) {
                font.drawString(mt.name(), 19, categoryY + 5, new Color(47, 154, 241).getRGB());
                if (mt.name().equals("Combat")) {
//                    FontLoaders.NovICON24.drawString("H", 8, categoryY + 4, new Color(47, 154, 241).getRGB());
                }
                if (mt.name().equals("Render")) {
//                    FontLoaders.NovICON18.drawString("F", 7, categoryY + 4, new Color(47, 154, 241).getRGB());
                }
                if (mt.name().equals("Move")) {
//                    FontLoaders.NovICON18.drawString("I", 7, categoryY + 4, new Color(47, 154, 241).getRGB());
                }
                if (mt.name().equals("Player")) {
//                    FontLoaders.NovICON20.drawString("C", 8, categoryY + 4, new Color(48, 155, 241).getRGB());
                }
                if (mt.name().equals("World")) {
//                    FontLoaders.NovICON20.drawString("E", 8, categoryY + 4, new Color(48, 155, 241).getRGB());
                }
            } else {
                font.drawString(mt.name(), 19, categoryY + 5, new Color(90, 90, 90).getRGB());
                if (mt.name().equals("Combat")) {
//                    FontLoaders.NovICON24.drawString("H", 8, categoryY + 4, new Color(90, 90, 90).getRGB());
                }
                if (mt.name().equals("Render")) {
//                    FontLoaders.NovICON18.drawString("F", 7, categoryY + 4, new Color(90, 90, 90).getRGB());
                }
                if (mt.name().equals("Move")) {
//                    FontLoaders.NovICON18.drawString("I", 7, categoryY + 4, new Color(90, 90, 90).getRGB());
                }
                if (mt.name().equals("Player")) {
//                    FontLoaders.NovICON20.drawString("C", 8, categoryY + 4, new Color(90, 90, 90).getRGB());
                }
                if (mt.name().equals("World")) {
//                    FontLoaders.NovICON20.drawString("E", 8, categoryY + 4, new Color(90, 90, 90).getRGB());
                }
            }
            categoryY += 14;
            ++mA2;
        }
        if (this.section == Section.MODULES || this.section == Section.VALUES) {
            for (Module m : Client.getModuleManager().getModulesInType(this.selectedType)) {
                Gui.drawRect(this.maxType - 14, moduleY + 2, this.maxModule - 22, moduleY + 14,
                        new Color(255, 255, 255, 90).getRGB());
                if (this.selectedModule == m) {
                    //Gui.drawRect(this.maxType - 12, moduleY + 4, this.maxType - 11, moduleY + 12,
                    //new Color(47, 154, 241).getRGB());
                    Gui.drawRect(this.maxType - 14, moduleY + 2, this.maxModule - 22, moduleY + 14, new Color(255, 255, 255).getRGB());
                    valueY = moduleY;
                }
                if (this.selectedModule == m) {
                    font.drawString(m.getName(), this.maxType - 8, moduleY + 6,
                            m.isEnabled() ? new Color(47, 154, 241).getRGB() : new Color(108, 108, 108).getRGB());
                } else {
                    font.drawString(m.getName(), this.maxType - 8, moduleY + 6,
                            m.isEnabled() ? new Color(47, 154, 241).getRGB() : new Color(108, 108, 108).getRGB());
                }
                if (!m.getValues().isEmpty()) {
                    FontLoaders.NovICON20.drawString("G", this.maxType + 56, moduleY + 6,
                            new Color(128, 128, 128).getRGB());
                    if (this.section == Section.VALUES && this.selectedModule == m) {
                        for (Value val : this.selectedModule.getValues()) {
                            Gui.drawRect(this.maxModule - 16, valueY + 2, this.maxValue, valueY + 14,
                                    new Color(255, 255, 255, 90).getRGB());
                            //RenderUtil.R2DUtils.drawRoundedRect(this.maxModule - 16, valueY+3, this.maxValue, valueY+13,
                            //new Color(234, 234, 234,200).getRGB(), new Color(234, 234, 234 ,200).getRGB());
                            if (this.selectedValue == val) {
                                Gui.drawRect(this.maxModule - 16, valueY + 2, this.maxValue, valueY + 14,
                                        new Color(255, 255, 255).getRGB());
                            }
                            if (val instanceof Option) {
                                font.drawString(val.getDisplayName(), this.maxModule - 10, valueY + 6,
                                        (Boolean) val.getValue() != false ? new Color(47, 154, 241).getRGB()
                                                : new Color(108, 108, 108).getRGB());
                            } else {
                                String toRender = String.format("%s: \u00a77%s", val.getDisplayName(),
                                        val.getValue().toString());
                                if (this.selectedValue == val) {
                                    font.drawString(toRender, this.maxModule - 10, valueY + 6,
                                            new Color(108, 108, 108).getRGB());
                                } else {
                                    font.drawString(toRender, this.maxModule - 10, valueY + 6,
                                            new Color(108, 108, 108).getRGB());
                                }
                            }
                            valueY += 12;
                        }
                    }
                }
                moduleY += 12;
            }
        }
    }

    @EventHandler
    private void onKey(EventKey e) {
        if (!Helper.mc.gameSettings.showDebugInfo) {
            block0:
            switch (e.getKey()) {
                case 208: {
                    switch (TabUI.QwQ()[this.section.ordinal()]) {
                        case 1: {
                            ++this.currentType;
                            if (this.currentType > ModuleType.values().length - 1) {
                                this.currentType = 0;
                            }
                            this.selectedType = ModuleType.values()[this.currentType];
                            break block0;
                        }
                        case 2: {
                            ++this.currentModule;
                            if (this.currentModule > Client.getModuleManager().getModulesInType(this.selectedType)
                                    .size() - 1) {
                                this.currentModule = 0;
                            }
                            this.selectedModule = Client.getModuleManager().getModulesInType(this.selectedType)
                                    .get(this.currentModule);
                            break block0;
                        }
                        case 3: {
                            ++this.currentValue;
                            if (this.currentValue > this.selectedModule.getValues().size() - 1) {
                                this.currentValue = 0;
                            }
                            this.selectedValue = this.selectedModule.getValues().get(this.currentValue);
                        }
                    }
                    break;
                }
                case 200: {
                    switch (TabUI.QwQ()[this.section.ordinal()]) {
                        case 1: {
                            --this.currentType;
                            if (this.currentType < 0) {
                                this.currentType = ModuleType.values().length - 1;
                            }
                            this.selectedType = ModuleType.values()[this.currentType];
                            break block0;
                        }
                        case 2: {
                            --this.currentModule;
                            if (this.currentModule < 0) {
                                this.currentModule = Client.getModuleManager().getModulesInType(this.selectedType)
                                        .size() - 1;
                            }
                            this.selectedModule = Client.getModuleManager().getModulesInType(this.selectedType)
                                    .get(this.currentModule);
                            break block0;
                        }
                        case 3: {
                            --this.currentValue;
                            if (this.currentValue < 0) {
                                this.currentValue = this.selectedModule.getValues().size() - 1;
                            }
                            this.selectedValue = this.selectedModule.getValues().get(this.currentValue);
                        }
                    }
                    break;
                }
                case 205: {
                    switch (TabUI.QwQ()[this.section.ordinal()]) {
                        case 1: {
                            this.currentModule = 0;
                            this.selectedModule = Client.getModuleManager().getModulesInType(this.selectedType)
                                    .get(this.currentModule);
                            this.section = Section.MODULES;
                            break block0;
                        }
                        case 2: {
                            if (this.selectedModule.getValues().isEmpty())
                                break block0;
                            this.resetValuesLength();
                            this.currentValue = 0;
                            this.selectedValue = this.selectedModule.getValues().get(this.currentValue);
                            this.section = Section.VALUES;
                            break block0;
                        }
                        case 3: {
                            if (Helper.onServer("enjoytheban"))
                                break block0;
                            if (this.selectedValue instanceof Option) {
                                this.selectedValue.setValue((Boolean) this.selectedValue.getValue() == false);
                            } else if (this.selectedValue instanceof Numbers) {
                                Numbers value = (Numbers) this.selectedValue;
                                double inc = (Double) value.getValue();
                                inc += value.getIncrement().doubleValue();
                                if ((inc = MathUtil.toDecimalLength(inc, 1)) > (Double) value.getMaximum()) {
                                    inc = (Double) ((Numbers) this.selectedValue).getMinimum();
                                }
                                this.selectedValue.setValue(inc);
                            } else if (this.selectedValue instanceof Mode) {
                                Mode theme = (Mode) this.selectedValue;
                                Enum current = (Enum) theme.getValue();
                                int next = current.ordinal() + 1 >= theme.getModes().length ? 0 : current.ordinal() + 1;
                                this.selectedValue.setValue(theme.getModes()[next]);
                            }
                            this.resetValuesLength();
                        }
                    }
                    break;
                }
                case 28: {
                    switch (TabUI.QwQ()[this.section.ordinal()]) {
                        case 1: {
                            break block0;
                        }
                        case 2: {
                            this.selectedModule.setEnabled(!this.selectedModule.isEnabled());
                            break block0;
                        }
                        case 3: {
                            this.section = Section.MODULES;
                        }
                    }
                    break;
                }
                case 203: {
                    switch (TabUI.QwQ()[this.section.ordinal()]) {
                        case 1: {
                            break block0;
                        }
                        case 2: {
                            this.section = Section.TYPES;
                            this.currentModule = 0;
                            break block0;
                        }
                        case 3: {
                            if (Helper.onServer("enjoytheban"))
                                break block0;
                            if (this.selectedValue instanceof Option) {
                                this.selectedValue.setValue((Boolean) this.selectedValue.getValue() == false);
                            } else if (this.selectedValue instanceof Numbers) {
                                Numbers value = (Numbers) this.selectedValue;
                                double inc = (Double) value.getValue();
                                inc -= value.getIncrement().doubleValue();
                                if ((inc = MathUtil.toDecimalLength(inc, 1)) < (Double) value.getMinimum()) {
                                    inc = (Double) ((Numbers) this.selectedValue).getMaximum();
                                }
                                this.selectedValue.setValue(inc);
                            } else if (this.selectedValue instanceof Mode) {
                                Mode theme = (Mode) this.selectedValue;
                                Enum current = (Enum) theme.getValue();
                                int next = current.ordinal() - 1 < 0 ? theme.getModes().length - 1 : current.ordinal() - 1;
                                this.selectedValue.setValue(theme.getModes()[next]);
                            }
                            this.maxValue = 0;
                            for (Value val : this.selectedModule.getValues()) {
                                int off;
                                int n = off = val instanceof Option ? 6
                                        : Minecraft.getMinecraft().fontRendererObj
                                        .getStringWidth(String.format(" \u00a77%s", val.getValue().toString())) + 6;
                                if (this.maxValue > Minecraft.getMinecraft().fontRendererObj
                                        .getStringWidth(val.getDisplayName().toUpperCase()) + off)
                                    continue;
                                this.maxValue = Minecraft.getMinecraft().fontRendererObj
                                        .getStringWidth(val.getDisplayName().toUpperCase()) + off;
                            }
                            this.maxValue += this.maxModule;
                        }
                    }
                }
            }
        }
    }

    public enum Section {
        TYPES, MODULES, VALUES
    }

}
