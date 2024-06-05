package net.minecraft.src;

import java.lang.reflect.*;
import java.io.*;

public abstract class ValueObject
{
    @Override
    public String toString() {
        final StringBuilder var1 = new StringBuilder("{");
        for (final Field var5 : this.getClass().getFields()) {
            if (!func_96394_a(var5)) {
                try {
                    var1.append(var5.getName()).append("=").append(var5.get(this)).append(" ");
                }
                catch (IllegalAccessException ex) {}
            }
        }
        var1.deleteCharAt(var1.length() - 1);
        var1.append('}');
        return var1.toString();
    }
    
    private static boolean func_96394_a(final Field par0Field) {
        return Modifier.isStatic(par0Field.getModifiers());
    }
}
