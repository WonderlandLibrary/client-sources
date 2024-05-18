package net.minecraft.src;

import argo.saj.*;
import argo.jdom.*;

public class ValueObjectSubscription extends ValueObject
{
    public long field_98171_a;
    public int field_98170_b;
    
    public static ValueObjectSubscription func_98169_a(final String par0Str) {
        final ValueObjectSubscription var1 = new ValueObjectSubscription();
        try {
            final JsonRootNode var2 = new JdomParser().parse(par0Str);
            var1.field_98171_a = Long.parseLong(var2.getNumberValue("startDate"));
            var1.field_98170_b = Integer.parseInt(var2.getNumberValue("daysLeft"));
        }
        catch (InvalidSyntaxException ex) {}
        catch (IllegalArgumentException ex2) {}
        return var1;
    }
}
