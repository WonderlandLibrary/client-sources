package net.minecraft.src;

import argo.saj.*;
import argo.jdom.*;
import java.util.*;

public class ValueObjectList extends ValueObject
{
    public List field_96430_d;
    
    public static ValueObjectList func_98161_a(final String par0Str) {
        final ValueObjectList var1 = new ValueObjectList();
        var1.field_96430_d = new ArrayList();
        try {
            final JsonRootNode var2 = new JdomParser().parse(par0Str);
            if (var2.isArrayNode("servers")) {
                for (final JsonNode var4 : var2.getArrayNode("servers")) {
                    var1.field_96430_d.add(McoServer.func_98163_a(var4));
                }
            }
        }
        catch (InvalidSyntaxException ex) {}
        catch (IllegalArgumentException ex2) {}
        return var1;
    }
}
