/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import java.util.Collection;

public interface IRequirementsStrategy {
    public static final IRequirementsStrategy AND = IRequirementsStrategy::lambda$static$0;
    public static final IRequirementsStrategy OR = IRequirementsStrategy::lambda$static$1;

    public String[][] createRequirements(Collection<String> var1);

    private static String[][] lambda$static$1(Collection collection) {
        return new String[][]{collection.toArray(new String[0])};
    }

    private static String[][] lambda$static$0(Collection collection) {
        String[][] stringArray = new String[collection.size()][];
        int n = 0;
        for (String string : collection) {
            stringArray[n++] = new String[]{string};
        }
        return stringArray;
    }
}

