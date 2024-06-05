package net.minecraft.src;

import argo.saj.*;
import argo.jdom.*;

public class McoServerAddress extends ValueObject
{
    public String field_96417_a;
    
    public static McoServerAddress func_98162_a(final String par0Str) {
        final McoServerAddress var1 = new McoServerAddress();
        try {
            final JsonRootNode var2 = new JdomParser().parse(par0Str);
            var1.field_96417_a = var2.getStringValue("address");
        }
        catch (InvalidSyntaxException ex) {}
        catch (IllegalArgumentException ex2) {}
        return var1;
    }
}
