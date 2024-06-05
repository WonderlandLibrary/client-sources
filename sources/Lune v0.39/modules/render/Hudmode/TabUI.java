package me.superskidder.lune.modules.render.Hudmode;

import java.awt.Color;

import me.superskidder.lune.Lune;
import me.superskidder.lune.events.EventKey;
import me.superskidder.lune.events.EventRender2D;
import me.superskidder.lune.font.CFontRenderer;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.manager.EventManager;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.render.HUD;
import me.superskidder.lune.utils.math.MathUtil;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.values.Value;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class TabUI {
    private Section section = Section.TYPES;
    private ModCategory selectedType = ModCategory.values()[0];
    private Mod selectedMod = null;
    private Value selectedValue = null;
    private int currentType = 0;
    private int currentMod = 0;
    private int currentValue = 0;
    private int height = 12;
    private int maxType;
    private int maxMod;
    private int maxValue;
    private static int[] sections;

    public void init() {
        ModCategory[] arrModCategory = ModCategory.values();
        int n = arrModCategory.length;
        int n2 = 0;
        while (n2 < n) {
            ModCategory mt = arrModCategory[n2];
            if (this.maxType <= FontLoaders.F16.getStringWidth(mt.name().toUpperCase()) + 4) {
                this.maxType = FontLoaders.F16.getStringWidth(mt.name().toUpperCase()) + 4;
            }
            ++n2;
        }

        for (Mod m : Lune.moduleManager.modList) {
            if (this.maxMod > FontLoaders.F16.getStringWidth(m.getName().toUpperCase()) + 4) continue;
            this.maxMod = FontLoaders.F16.getStringWidth(m.getName().toUpperCase()) + 4;
        }

        for (Mod m : Lune.moduleManager.modList) {
            if (m.getValues().isEmpty()) continue;
            for (Value val : m.getValues()) {
                if (this.maxValue > FontLoaders.F16.getStringWidth(val.getName().toUpperCase()) + 4) continue;
                this.maxValue = FontLoaders.F16.getStringWidth(val.getName().toUpperCase()) + 4;
            }
        }
        this.maxMod += 12;
        this.maxValue += 24;
        boolean highestWidth = false;
        this.maxType = this.maxType < this.maxMod ? this.maxMod : this.maxType;
        this.maxMod += this.maxType;
        this.maxValue += this.maxMod;
        EventManager.register(this);
    }

    private void resetValuesLength() {
        this.maxValue = 0;
        for (Value val : this.selectedMod.getValues()) {
            int off;
            int n = off = val instanceof Bool ? 6 : FontLoaders.F16.getStringWidth(String.format(" \u00a77%s", val.getValue().toString())) + 6;
            if (this.maxValue > FontLoaders.F16.getStringWidth(val.getName().toUpperCase()) + off) continue;
            this.maxValue = FontLoaders.F16.getStringWidth(val.getName().toUpperCase()) + off;
        }
        this.maxValue += this.maxMod;
    }

    @EventTarget
    private void EventRender(EventRender2D e) {
        if(!((Boolean) HUD.TabUi.getValue())){
            return;
        }
        CFontRenderer font = FontLoaders.F16;
        if (Lune.mc.gameSettings.showDebugInfo || !Lune.moduleManager.getModByClass(HUD.class).getState())
            return;
        if(HUD.mod.getValue() == HUD.HUDMode.Flux){
            height = 16;
        }else if(HUD.mod.getValue() == HUD.HUDMode.Sigma){
            height = 30;
        }else if(HUD.mod.getValue() == HUD.HUDMode.New){
            height = 12;
        }else if(HUD.mod.getValue() == HUD.HUDMode.Old){
            height = 14;
        }else if(HUD.mod.getValue() == HUD.HUDMode.Lune){
            height = 26;
        }

        int categoryY = this.height;//this
        int ModY = categoryY;
        int valueY = categoryY;
        if(HUD.mod.getValue() == HUD.HUDMode.Sigma) {
            RenderUtil.blurArea(2.0f, categoryY, this.maxType - 25, 12 * ModCategory.values().length, 12);
        }else{
            RenderUtil.drawRect(2.0f, categoryY, this.maxType - 25, categoryY + 12 * ModCategory.values().length, new Color(0, 0, 0, 130).getRGB());
        }
        ModCategory[] ModArray = ModCategory.values();
        int mA = ModArray.length;
        int mA2 = 0;
        while (mA2 < mA) {
            ModCategory mt = ModArray[mA2];
            if (this.selectedType == mt) {
                Gui.drawRect(3.5, (double) categoryY + 1.5, 4.5, (double) (categoryY + FontLoaders.F16.getStringHeight("")) + 1.5, new Color(102, 172, 255).getRGB());
                ModY = categoryY;
            }
            if (this.selectedType == mt) {
                font.drawStringWithShadow(mt.name(), 7.0, categoryY + 3, -1);
            } else {
                font.drawStringWithShadow(mt.name(), 5.0, categoryY + 3, new Color(180, 180, 180).getRGB());
            }
            categoryY += 12;
            ++mA2;
        }
        if (this.section == Section.ModS || this.section == Section.VALUES) {
            RenderUtil.drawRect(this.maxType - 20, ModY, this.maxMod - 38, ModY + 12 * Lune.moduleManager.getModsByCategory(this.selectedType).size(), new Color(0, 0, 0, 130).getRGB());
            for (Mod m : Lune.moduleManager.getModsByCategory(this.selectedType)) {
                if (this.selectedMod == m) {
                    Gui.drawRect((double) this.maxType - 18.5, (double) ModY + 1.5, (double) this.maxType - 17.5, (double) (ModY + FontLoaders.F16.getStringHeight("")) + 1.5, new Color(102, 172, 255).getRGB());
                    valueY = ModY;
                }
                if (this.selectedMod == m) {
                    font.drawStringWithShadow(m.getName(), this.maxType - 15, ModY + 3, m.getState() ? -1 : 11184810);
                } else {
                    font.drawStringWithShadow(m.getName(), this.maxType - 17, ModY + 3, m.getState() ? -1 : 11184810);
                }
                if (!m.getValues().isEmpty()) {
                    //Gui.drawRect(this.maxMod - 38, (double) ModY + 0.5, this.maxMod - 39, (double) (ModY + FontLoaders.F16.getStringHeight("")) + 2.5, new Color(153, 200, 255).getRGB());
                    FontLoaders.F14.drawString("...", this.maxMod - 45, ModY + 0.5f, new Color(255, 255, 255).getRGB());

                    if (this.section == Section.VALUES && this.selectedMod == m) {
                        RenderUtil.drawRect(this.maxMod - 32, valueY, this.maxValue - 25, valueY + 12 * this.selectedMod.getValues().size(), new Color(10, 10, 10, 150).getRGB());
                        for (Value val : this.selectedMod.getValues()) {
                            Gui.drawRect((double) this.maxMod - 30.5, (double) valueY + 1.5, (double) this.maxMod - 29.5, (double) (valueY + FontLoaders.F16.getStringHeight("")) + 2.5, this.selectedValue == val ? new Color(102, 172, 255).getRGB() : 0);
                            if (val instanceof Bool) {
                                font.drawStringWithShadow(val.getName(), this.selectedValue == val ? this.maxMod - 27 : this.maxMod - 29, valueY + 3, (Boolean) val.getValue() != false ? new Color(153, 200, 255).getRGB() : 11184810);
                            } else {
                                String toRender = String.format("%s: \u00a77%s", val.getName(), val.getValue().toString());
                                if (this.selectedValue == val) {
                                    font.drawStringWithShadow(toRender, this.maxMod - 27, valueY + 3, -1);
                                } else {
                                    font.drawStringWithShadow(toRender, this.maxMod - 29, valueY + 3, -1);
                                }
                            }
                            valueY += 12;
                        }
                    }
                }
                ModY += 12;
            }
        }

    }

    @EventTarget
    private void onKey(EventKey e) {
        if (!Lune.mc.gameSettings.showDebugInfo) {
            switch (e.getKey()) {
                case 208: {
                    switch (TabUI.Section()[this.section.ordinal()]) {
                        case 1: {
                            ++this.currentType;
                            if (this.currentType > ModCategory.values().length - 1) {
                                this.currentType = 0;
                            }
                            this.selectedType = ModCategory.values()[this.currentType];
                            return;
                        }
                        case 2: {
                            ++this.currentMod;
                            if (this.currentMod > Lune.moduleManager.getModsByCategory(this.selectedType).size() - 1) {
                                this.currentMod = 0;
                            }
                            this.selectedMod = Lune.moduleManager.getModsByCategory(this.selectedType).get(this.currentMod);
                            return;
                        }
                        case 3: {
                            ++this.currentValue;
                            if (this.currentValue > this.selectedMod.getValues().size() - 1) {
                                this.currentValue = 0;
                            }
                            this.selectedValue = this.selectedMod.getValues().get(this.currentValue);
                        }
                    }
                    break;
                }
                case 200: {
                    switch (TabUI.Section()[this.section.ordinal()]) {
                        case 1: {
                            --this.currentType;
                            if (this.currentType < 0) {
                                this.currentType = ModCategory.values().length - 1;
                            }
                            this.selectedType = ModCategory.values()[this.currentType];
                            return;
                        }
                        case 2: {
                            --this.currentMod;
                            if (this.currentMod < 0) {
                                this.currentMod = Lune.moduleManager.getModsByCategory(this.selectedType).size() - 1;
                            }
                            this.selectedMod = Lune.moduleManager.getModsByCategory(this.selectedType).get(this.currentMod);
                            return;
                        }
                        case 3: {
                            --this.currentValue;
                            if (this.currentValue < 0) {
                                this.currentValue = this.selectedMod.getValues().size() - 1;
                            }
                            this.selectedValue = this.selectedMod.getValues().get(this.currentValue);
                        }
                    }
                    break;
                }
                case 205: {
                    switch (TabUI.Section()[this.section.ordinal()]) {
                        case 1: {
                            this.currentMod = 0;
                            this.selectedMod = Lune.moduleManager.getModsByCategory(this.selectedType).get(this.currentMod);
                            this.section = Section.ModS;
                            return;
                        }
                        case 2: {
                            if (this.selectedMod.getValues().isEmpty()) return;
                            this.resetValuesLength();
                            this.currentValue = 0;
                            this.selectedValue = this.selectedMod.getValues().get(this.currentValue);
                            this.section = Section.VALUES;
                            return;
                        }
                        case 3: {
                            if (this.selectedValue instanceof Bool) {
                                this.selectedValue.setValue((Boolean) this.selectedValue.getValue() == false);
                            } else if (this.selectedValue instanceof Num) {
                                Num value = (Num) this.selectedValue;
                                double inc = (Double) value.getValue();
                                inc += ((Double) value.getValue()).doubleValue();
                                if ((inc = MathUtil.toDecimalLength(inc, 1)) > (Double) value.getMax()) {
                                    inc = (Double) ((Num) this.selectedValue).getMin();
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
                    switch (TabUI.Section()[this.section.ordinal()]) {
                        case 1: {
                            return;
                        }
                        case 2: {
                            this.selectedMod.setStage(!this.selectedMod.getState());
                            return;
                        }
                        case 3: {
                            this.section = Section.ModS;
                        }
                    }
                    break;
                }
                case 203: {
                    switch (TabUI.Section()[this.section.ordinal()]) {
                        case 1: {
                            return;
                        }
                        case 2: {
                            this.section = Section.TYPES;
                            this.currentMod = 0;
                            return;
                        }
                        case 3: {
                            if (this.selectedValue instanceof Bool) {
                                this.selectedValue.setValue((Boolean) this.selectedValue.getValue() == false);
                            } else if (this.selectedValue instanceof Num) {
                                Num value = (Num) this.selectedValue;
                                double inc = (Double) value.getValue();
                                inc -= ((Double) value.getValue()).doubleValue();
                                if ((inc = MathUtil.toDecimalLength(inc, 1)) < (Double) value.getMin()) {
                                    inc = (Double) ((Num) this.selectedValue).getMax();
                                }
                                this.selectedValue.setValue(inc);
                            } else if (this.selectedValue instanceof Mode) {
                                Mode theme = (Mode) this.selectedValue;
                                Enum current = (Enum) theme.getValue();
                                int next = current.ordinal() - 1 < 0 ? theme.getModes().length - 1 : current.ordinal() - 1;
                                this.selectedValue.setValue(theme.getModes()[next]);
                            }
                            this.maxValue = 0;
                            for (Value val : this.selectedMod.getValues()) {
                                int off;
                                int n = off = val instanceof Bool ? 6 : Minecraft.getMinecraft().fontRendererObj.getStringWidth(String.format(" \u00a77%s", val.getValue().toString())) + 6;
                                if (this.maxValue > Minecraft.getMinecraft().fontRendererObj.getStringWidth(val.getName().toUpperCase()) + off)
                                    continue;
                                this.maxValue = Minecraft.getMinecraft().fontRendererObj.getStringWidth(val.getName().toUpperCase()) + off;
                            }
                            this.maxValue += this.maxMod;
                        }
                    }
                }
            }
        }
    }

    static int[] Section() {
        int[] arrn;
        int[] arrn2 = sections;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[Section.values().length];
        try {
            arrn[Section.ModS.ordinal()] = 2;
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
        sections = arrn;
        return sections;
    }


    public static enum Section {
        TYPES,
        ModS,
        VALUES;
    }
}

