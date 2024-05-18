package net.minecraft.src;

import net.minecraft.client.*;
import java.net.*;
import java.util.regex.*;

public class ChatClickData
{
    public static final Pattern pattern;
    private final FontRenderer fontR;
    private final ChatLine line;
    private final int field_78312_d;
    private final int field_78313_e;
    private final String field_78310_f;
    private final String clickedUrl;
    
    static {
        pattern = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,4})(/\\S*)?$");
    }
    
    public ChatClickData(final FontRenderer par1FontRenderer, final ChatLine par2ChatLine, final int par3, final int par4) {
        this.fontR = par1FontRenderer;
        this.line = par2ChatLine;
        this.field_78312_d = par3;
        this.field_78313_e = par4;
        this.field_78310_f = par1FontRenderer.trimStringToWidth(par2ChatLine.getChatLineString(), par3);
        this.clickedUrl = this.findClickedUrl();
    }
    
    public String getClickedUrl() {
        return this.clickedUrl;
    }
    
    public URI getURI() {
        final String var1 = this.getClickedUrl();
        if (var1 == null) {
            return null;
        }
        final Matcher var2 = ChatClickData.pattern.matcher(var1);
        if (var2.matches()) {
            try {
                String var3 = var2.group(0);
                if (var2.group(1) == null) {
                    var3 = "http://" + var3;
                }
                return new URI(var3);
            }
            catch (URISyntaxException var4) {
                Minecraft.getMinecraft().getLogAgent().logSevereException("Couldn't create URI from chat", var4);
            }
        }
        return null;
    }
    
    private String findClickedUrl() {
        int var1 = this.field_78310_f.lastIndexOf(" ", this.field_78310_f.length()) + 1;
        if (var1 < 0) {
            var1 = 0;
        }
        int var2 = this.line.getChatLineString().indexOf(" ", var1);
        if (var2 < 0) {
            var2 = this.line.getChatLineString().length();
        }
        return StringUtils.stripControlCodes(this.line.getChatLineString().substring(var1, var2));
    }
}
