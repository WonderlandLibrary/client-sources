package argo.format;

import java.io.*;
import argo.jdom.*;
import java.util.*;

public final class CompactJsonFormatter implements JsonFormatter
{
    @Override
    public String format(final JsonRootNode par1JsonRootNode) {
        final StringWriter var2 = new StringWriter();
        try {
            this.format(par1JsonRootNode, var2);
        }
        catch (IOException var3) {
            throw new RuntimeException("Coding failure in Argo:  StringWriter gave an IOException", var3);
        }
        return var2.toString();
    }
    
    public void format(final JsonRootNode par1JsonRootNode, final Writer par2Writer) throws IOException {
        this.formatJsonNode(par1JsonRootNode, par2Writer);
    }
    
    private void formatJsonNode(final JsonNode par1JsonNode, final Writer par2Writer) throws IOException {
        boolean var3 = true;
        switch (CompactJsonFormatter_JsonNodeType.enumJsonNodeTypeMappingArray[par1JsonNode.getType().ordinal()]) {
            case 1: {
                par2Writer.append('[');
                for (final JsonNode var5 : par1JsonNode.getElements()) {
                    if (!var3) {
                        par2Writer.append(',');
                    }
                    var3 = false;
                    this.formatJsonNode(var5, par2Writer);
                }
                par2Writer.append(']');
                break;
            }
            case 2: {
                par2Writer.append('{');
                for (final JsonStringNode var6 : new TreeSet(par1JsonNode.getFields().keySet())) {
                    if (!var3) {
                        par2Writer.append(',');
                    }
                    var3 = false;
                    this.formatJsonNode(var6, par2Writer);
                    par2Writer.append(':');
                    this.formatJsonNode(par1JsonNode.getFields().get(var6), par2Writer);
                }
                par2Writer.append('}');
                break;
            }
            case 3: {
                par2Writer.append('\"').append((CharSequence)new JsonEscapedString(par1JsonNode.getText()).toString()).append('\"');
                break;
            }
            case 4: {
                par2Writer.append((CharSequence)par1JsonNode.getText());
                break;
            }
            case 5: {
                par2Writer.append((CharSequence)"false");
                break;
            }
            case 6: {
                par2Writer.append((CharSequence)"true");
                break;
            }
            case 7: {
                par2Writer.append((CharSequence)"null");
                break;
            }
            default: {
                throw new RuntimeException("Coding failure in Argo:  Attempt to format a JsonNode of unknown type [" + par1JsonNode.getType() + "];");
            }
        }
    }
}
