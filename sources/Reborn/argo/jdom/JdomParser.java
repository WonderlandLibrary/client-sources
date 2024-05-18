package argo.jdom;

import argo.saj.*;
import java.io.*;

public final class JdomParser
{
    public JsonRootNode parse(final Reader par1Reader) throws IOException, InvalidSyntaxException {
        final JsonListenerToJdomAdapter var2 = new JsonListenerToJdomAdapter();
        new SajParser().parse(par1Reader, var2);
        return var2.getDocument();
    }
    
    public JsonRootNode parse(final String par1Str) throws InvalidSyntaxException {
        try {
            final JsonRootNode var2 = this.parse(new StringReader(par1Str));
            return var2;
        }
        catch (IOException var3) {
            throw new RuntimeException("Coding failure in Argo:  StringWriter gave an IOException", var3);
        }
    }
}
