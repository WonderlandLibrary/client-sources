package net.minecraft.client.resources;

import com.google.common.collect.*;
import com.google.common.base.*;
import com.google.common.io.*;
import net.minecraft.util.*;
import org.apache.commons.io.*;
import com.google.gson.*;
import java.io.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class ResourceIndex
{
    private static final String[] I;
    private final Map<String, File> resourceMap;
    private static final Logger logger;
    
    public ResourceIndex(final File file, final String s) {
        this.resourceMap = (Map<String, File>)Maps.newHashMap();
        if (s != null) {
            final File file2 = new File(file, ResourceIndex.I["".length()]);
            final File file3 = new File(file, ResourceIndex.I[" ".length()] + s + ResourceIndex.I["  ".length()]);
            Reader reader = null;
            Label_0516: {
                try {
                    reader = Files.newReader(file3, Charsets.UTF_8);
                    final JsonObject jsonObject = JsonUtils.getJsonObject(new JsonParser().parse(reader).getAsJsonObject(), ResourceIndex.I["   ".length()], null);
                    if (jsonObject == null) {
                        break Label_0516;
                    }
                    final Iterator iterator = jsonObject.entrySet().iterator();
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final Map.Entry<K, JsonObject> entry = iterator.next();
                        final JsonObject jsonObject2 = entry.getValue();
                        final String[] split = ((String)entry.getKey()).split(ResourceIndex.I[0x9C ^ 0x98], "  ".length());
                        String string;
                        if (split.length == " ".length()) {
                            string = split["".length()];
                            "".length();
                            if (-1 >= 0) {
                                throw null;
                            }
                        }
                        else {
                            string = String.valueOf(split["".length()]) + ResourceIndex.I[0xA ^ 0xF] + split[" ".length()];
                        }
                        final String s2 = string;
                        final String string2 = JsonUtils.getString(jsonObject2, ResourceIndex.I[0x11 ^ 0x17]);
                        this.resourceMap.put(s2, new File(file2, String.valueOf(string2.substring("".length(), "  ".length())) + ResourceIndex.I[0x8C ^ 0x8B] + string2));
                    }
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                catch (JsonParseException ex) {
                    ResourceIndex.logger.error(ResourceIndex.I[0x76 ^ 0x7E] + file3);
                    IOUtils.closeQuietly(reader);
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                    return;
                }
                catch (FileNotFoundException ex2) {
                    ResourceIndex.logger.error(ResourceIndex.I[0x43 ^ 0x4A] + file3);
                    IOUtils.closeQuietly(reader);
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                    return;
                }
                finally {
                    IOUtils.closeQuietly(reader);
                }
            }
            IOUtils.closeQuietly(reader);
        }
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    private static void I() {
        (I = new String[0x90 ^ 0x9A])["".length()] = I("\t/\u0018\u001f0\u0012>", "fMrzS");
        ResourceIndex.I[" ".length()] = I(":\u001a\t#\u00016\u0007B", "StmFy");
        ResourceIndex.I["  ".length()] = I("d)\u0011)4", "JCbFZ");
        ResourceIndex.I["   ".length()] = I(" 7+54;&", "OUAPW");
        ResourceIndex.I[0x36 ^ 0x32] = I("m", "BkdjP");
        ResourceIndex.I[0xBA ^ 0xBF] = I("n", "TXtJx");
        ResourceIndex.I[0x64 ^ 0x62] = I("\u0001\u00045'", "ieFOR");
        ResourceIndex.I[0xA8 ^ 0xAF] = I("W", "xagmu");
        ResourceIndex.I[0x3E ^ 0x36] = I(";(\u0000\u0016\u0002\u000bf\u0015\u001bN\u001e'\u0013\u0007\u000bN4\u0004\u0007\u0001\u001b4\u0002\u0011N\u0007(\u0005\u0011\u0016N \b\u0018\u000bTf", "nFatn");
        ResourceIndex.I[0x8B ^ 0x82] = I(",2-P:O5*\u0019*O'+\u0012n\u001d60\u0018;\u001d0&W'\u00017&\u000fn\t:/\u0012tO", "oSCwN");
    }
    
    public Map<String, File> getResourceMap() {
        return this.resourceMap;
    }
}
