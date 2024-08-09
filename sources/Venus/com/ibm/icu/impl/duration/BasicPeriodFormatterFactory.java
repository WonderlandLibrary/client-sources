/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.BasicPeriodFormatter;
import com.ibm.icu.impl.duration.BasicPeriodFormatterService;
import com.ibm.icu.impl.duration.PeriodFormatter;
import com.ibm.icu.impl.duration.PeriodFormatterFactory;
import com.ibm.icu.impl.duration.impl.PeriodFormatterData;
import com.ibm.icu.impl.duration.impl.PeriodFormatterDataService;
import java.util.Locale;

public class BasicPeriodFormatterFactory
implements PeriodFormatterFactory {
    private final PeriodFormatterDataService ds;
    private PeriodFormatterData data;
    private Customizations customizations;
    private boolean customizationsInUse;
    private String localeName;

    BasicPeriodFormatterFactory(PeriodFormatterDataService periodFormatterDataService) {
        this.ds = periodFormatterDataService;
        this.customizations = new Customizations();
        this.localeName = Locale.getDefault().toString();
    }

    public static BasicPeriodFormatterFactory getDefault() {
        return (BasicPeriodFormatterFactory)BasicPeriodFormatterService.getInstance().newPeriodFormatterFactory();
    }

    @Override
    public PeriodFormatterFactory setLocale(String string) {
        this.data = null;
        this.localeName = string;
        return this;
    }

    @Override
    public PeriodFormatterFactory setDisplayLimit(boolean bl) {
        this.updateCustomizations().displayLimit = bl;
        return this;
    }

    public boolean getDisplayLimit() {
        return this.customizations.displayLimit;
    }

    @Override
    public PeriodFormatterFactory setDisplayPastFuture(boolean bl) {
        this.updateCustomizations().displayDirection = bl;
        return this;
    }

    public boolean getDisplayPastFuture() {
        return this.customizations.displayDirection;
    }

    @Override
    public PeriodFormatterFactory setSeparatorVariant(int n) {
        this.updateCustomizations().separatorVariant = (byte)n;
        return this;
    }

    public int getSeparatorVariant() {
        return this.customizations.separatorVariant;
    }

    @Override
    public PeriodFormatterFactory setUnitVariant(int n) {
        this.updateCustomizations().unitVariant = (byte)n;
        return this;
    }

    public int getUnitVariant() {
        return this.customizations.unitVariant;
    }

    @Override
    public PeriodFormatterFactory setCountVariant(int n) {
        this.updateCustomizations().countVariant = (byte)n;
        return this;
    }

    public int getCountVariant() {
        return this.customizations.countVariant;
    }

    @Override
    public PeriodFormatter getFormatter() {
        this.customizationsInUse = true;
        return new BasicPeriodFormatter(this, this.localeName, this.getData(), this.customizations);
    }

    private Customizations updateCustomizations() {
        if (this.customizationsInUse) {
            this.customizations = this.customizations.copy();
            this.customizationsInUse = false;
        }
        return this.customizations;
    }

    PeriodFormatterData getData() {
        if (this.data == null) {
            this.data = this.ds.get(this.localeName);
        }
        return this.data;
    }

    PeriodFormatterData getData(String string) {
        return this.ds.get(string);
    }

    static class Customizations {
        boolean displayLimit = true;
        boolean displayDirection = true;
        byte separatorVariant = (byte)2;
        byte unitVariant = 0;
        byte countVariant = 0;

        Customizations() {
        }

        public Customizations copy() {
            Customizations customizations = new Customizations();
            customizations.displayLimit = this.displayLimit;
            customizations.displayDirection = this.displayDirection;
            customizations.separatorVariant = this.separatorVariant;
            customizations.unitVariant = this.unitVariant;
            customizations.countVariant = this.countVariant;
            return customizations;
        }
    }
}

