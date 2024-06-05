package net.minecraft.src;

import java.text.*;
import java.util.*;

public class StatBase
{
    public final int statId;
    private final String statName;
    public boolean isIndependent;
    public String statGuid;
    private final IStatType type;
    private static NumberFormat numberFormat;
    public static IStatType simpleStatType;
    private static DecimalFormat decimalFormat;
    public static IStatType timeStatType;
    public static IStatType distanceStatType;
    
    static {
        StatBase.numberFormat = NumberFormat.getIntegerInstance(Locale.US);
        StatBase.simpleStatType = new StatTypeSimple();
        StatBase.decimalFormat = new DecimalFormat("########0.00");
        StatBase.timeStatType = new StatTypeTime();
        StatBase.distanceStatType = new StatTypeDistance();
    }
    
    public StatBase(final int par1, final String par2Str, final IStatType par3IStatType) {
        this.isIndependent = false;
        this.statId = par1;
        this.statName = par2Str;
        this.type = par3IStatType;
    }
    
    public StatBase(final int par1, final String par2Str) {
        this(par1, par2Str, StatBase.simpleStatType);
    }
    
    public StatBase initIndependentStat() {
        this.isIndependent = true;
        return this;
    }
    
    public StatBase registerStat() {
        if (StatList.oneShotStats.containsKey(this.statId)) {
            throw new RuntimeException("Duplicate stat id: \"" + StatList.oneShotStats.get(this.statId).statName + "\" and \"" + this.statName + "\" at id " + this.statId);
        }
        StatList.allStats.add(this);
        StatList.oneShotStats.put(this.statId, this);
        this.statGuid = AchievementMap.getGuid(this.statId);
        return this;
    }
    
    public boolean isAchievement() {
        return false;
    }
    
    public String func_75968_a(final int par1) {
        return this.type.format(par1);
    }
    
    public String getName() {
        return this.statName;
    }
    
    @Override
    public String toString() {
        return StatCollector.translateToLocal(this.statName);
    }
    
    static NumberFormat getNumberFormat() {
        return StatBase.numberFormat;
    }
    
    static DecimalFormat getDecimalFormat() {
        return StatBase.decimalFormat;
    }
}
