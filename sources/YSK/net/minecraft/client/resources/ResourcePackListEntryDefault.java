package net.minecraft.client.resources;

import org.apache.logging.log4j.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import net.minecraft.client.resources.data.*;
import com.google.gson.*;
import net.minecraft.util.*;

public class ResourcePackListEntryDefault extends ResourcePackListEntry
{
    private static final String[] I;
    private static final Logger logger;
    private final IResourcePack field_148320_d;
    private final ResourceLocation resourcePackIcon;
    
    @Override
    protected boolean func_148307_h() {
        return "".length() != 0;
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
            if (!true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    protected int func_183019_a() {
        return " ".length();
    }
    
    @Override
    protected boolean func_148308_f() {
        return "".length() != 0;
    }
    
    public ResourcePackListEntryDefault(final GuiScreenResourcePacks guiScreenResourcePacks) {
        super(guiScreenResourcePacks);
        this.field_148320_d = this.mc.getResourcePackRepository().rprDefaultResourcePack;
        DynamicTexture missingTexture;
        try {
            missingTexture = new DynamicTexture(this.field_148320_d.getPackImage());
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        catch (IOException ex) {
            missingTexture = TextureUtil.missingTexture;
        }
        this.resourcePackIcon = this.mc.getTextureManager().getDynamicTextureLocation(ResourcePackListEntryDefault.I["".length()], missingTexture);
    }
    
    @Override
    protected void func_148313_c() {
        this.mc.getTextureManager().bindTexture(this.resourcePackIcon);
    }
    
    private static void I() {
        (I = new String[0x3B ^ 0x33])["".length()] = I("$\u0007\u000e;4\"\u0007\u0006.\";\u000b\u0015 /", "PbvOA");
        ResourcePackListEntryDefault.I[" ".length()] = I("\b\u0015\u0012 ", "xtqKO");
        ResourcePackListEntryDefault.I["  ".length()] = I("\u000b\b\u0011\u001c\u0014&@\u0010P\u001c'\u0006\u0000P\u001d-\u0013\u0005\u0014\u0011<\u0006D\u0019\u001e.\b", "Hgdpp");
        ResourcePackListEntryDefault.I["   ".length()] = I("((0\u00064\u0005`1J<\u0004&!J=\u000e3$\u000e1\u001f&e\u0003>\r(", "kGEjP");
        ResourcePackListEntryDefault.I[0xA1 ^ 0xA5] = I("\u0002\u0004\u0015\u0014\u0010!\nF", "Omfgy");
        ResourcePackListEntryDefault.I[0x13 ^ 0x16] = I("\u00177\u0004\u0001l\n5\n\u000f6\u0006", "gVgjB");
        ResourcePackListEntryDefault.I[0x9F ^ 0x99] = I("fpl", "FJDLh");
        ResourcePackListEntryDefault.I[0xA1 ^ 0xA6] = I("=\"!43\u00153", "yGGUF");
    }
    
    @Override
    protected boolean func_148309_e() {
        return "".length() != 0;
    }
    
    @Override
    protected String func_148312_b() {
        return ResourcePackListEntryDefault.I[0x19 ^ 0x1E];
    }
    
    @Override
    protected boolean func_148314_g() {
        return "".length() != 0;
    }
    
    @Override
    protected String func_148311_a() {
        try {
            final PackMetadataSection packMetadataSection = this.field_148320_d.getPackMetadata(this.mc.getResourcePackRepository().rprMetadataSerializer, ResourcePackListEntryDefault.I[" ".length()]);
            if (packMetadataSection != null) {
                return packMetadataSection.getPackDescription().getFormattedText();
            }
        }
        catch (JsonParseException ex) {
            ResourcePackListEntryDefault.logger.error(ResourcePackListEntryDefault.I["  ".length()], (Throwable)ex);
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        catch (IOException ex2) {
            ResourcePackListEntryDefault.logger.error(ResourcePackListEntryDefault.I["   ".length()], (Throwable)ex2);
        }
        return EnumChatFormatting.RED + ResourcePackListEntryDefault.I[0x8D ^ 0x89] + ResourcePackListEntryDefault.I[0x86 ^ 0x83] + ResourcePackListEntryDefault.I[0xC2 ^ 0xC4];
    }
    
    @Override
    protected boolean func_148310_d() {
        return "".length() != 0;
    }
}
