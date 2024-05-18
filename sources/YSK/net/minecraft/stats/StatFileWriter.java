package net.minecraft.stats;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;

public class StatFileWriter
{
    protected final Map<StatBase, TupleIntJsonSerializable> statsData;
    
    public int func_150874_c(final Achievement achievement) {
        if (this.hasAchievementUnlocked(achievement)) {
            return "".length();
        }
        int length = "".length();
        Achievement achievement2 = achievement.parentAchievement;
        "".length();
        if (false == true) {
            throw null;
        }
        while (achievement2 != null && !this.hasAchievementUnlocked(achievement2)) {
            achievement2 = achievement2.parentAchievement;
            ++length;
        }
        return length;
    }
    
    public <T extends IJsonSerializable> T func_150872_a(final StatBase statBase, final T jsonSerializableValue) {
        TupleIntJsonSerializable tupleIntJsonSerializable = this.statsData.get(statBase);
        if (tupleIntJsonSerializable == null) {
            tupleIntJsonSerializable = new TupleIntJsonSerializable();
            this.statsData.put(statBase, tupleIntJsonSerializable);
        }
        tupleIntJsonSerializable.setJsonSerializableValue(jsonSerializableValue);
        return jsonSerializableValue;
    }
    
    public <T extends IJsonSerializable> T func_150870_b(final StatBase statBase) {
        final TupleIntJsonSerializable tupleIntJsonSerializable = this.statsData.get(statBase);
        IJsonSerializable jsonSerializableValue;
        if (tupleIntJsonSerializable != null) {
            jsonSerializableValue = tupleIntJsonSerializable.getJsonSerializableValue();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            jsonSerializableValue = null;
        }
        return (T)jsonSerializableValue;
    }
    
    public boolean canUnlockAchievement(final Achievement achievement) {
        if (achievement.parentAchievement != null && !this.hasAchievementUnlocked(achievement.parentAchievement)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void increaseStat(final EntityPlayer entityPlayer, final StatBase statBase, final int n) {
        if (!statBase.isAchievement() || this.canUnlockAchievement((Achievement)statBase)) {
            this.unlockAchievement(entityPlayer, statBase, this.readStat(statBase) + n);
        }
    }
    
    public boolean hasAchievementUnlocked(final Achievement achievement) {
        if (this.readStat(achievement) > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int readStat(final StatBase statBase) {
        final TupleIntJsonSerializable tupleIntJsonSerializable = this.statsData.get(statBase);
        int n;
        if (tupleIntJsonSerializable == null) {
            n = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            n = tupleIntJsonSerializable.getIntegerValue();
        }
        return n;
    }
    
    public StatFileWriter() {
        this.statsData = (Map<StatBase, TupleIntJsonSerializable>)Maps.newConcurrentMap();
    }
    
    public void unlockAchievement(final EntityPlayer entityPlayer, final StatBase statBase, final int integerValue) {
        TupleIntJsonSerializable tupleIntJsonSerializable = this.statsData.get(statBase);
        if (tupleIntJsonSerializable == null) {
            tupleIntJsonSerializable = new TupleIntJsonSerializable();
            this.statsData.put(statBase, tupleIntJsonSerializable);
        }
        tupleIntJsonSerializable.setIntegerValue(integerValue);
    }
}
