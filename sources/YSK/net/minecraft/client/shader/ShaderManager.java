package net.minecraft.client.shader;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import org.apache.commons.io.*;
import net.minecraft.util.*;
import net.minecraft.client.util.*;
import com.google.gson.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class ShaderManager
{
    private final int program;
    private final ShaderLoader fragmentShaderLoader;
    private static int currentProgram;
    private final Map<String, Object> shaderSamplers;
    private static final Logger logger;
    private final List<String> samplerNames;
    private final boolean useFaceCulling;
    private final List<Integer> shaderSamplerLocations;
    private final List<Integer> shaderUniformLocations;
    private final List<Integer> attribLocations;
    private final List<ShaderUniform> shaderUniforms;
    private static ShaderManager staticShaderManager;
    private static boolean field_148000_e;
    private final Map<String, ShaderUniform> mappedShaderUniforms;
    private static final String[] I;
    private final ShaderLoader vertexShaderLoader;
    private final List<String> attributes;
    private final JsonBlendingMode field_148016_p;
    private boolean isDirty;
    private final String programFilename;
    private static final ShaderDefault defaultShaderUniform;
    
    public void useShader() {
        this.isDirty = ("".length() != 0);
        ShaderManager.staticShaderManager = this;
        this.field_148016_p.func_148109_a();
        if (this.program != ShaderManager.currentProgram) {
            OpenGlHelper.glUseProgram(this.program);
            ShaderManager.currentProgram = this.program;
        }
        if (this.useFaceCulling) {
            GlStateManager.enableCull();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            GlStateManager.disableCull();
        }
        int i = "".length();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (i < this.shaderSamplerLocations.size()) {
            if (this.shaderSamplers.get(this.samplerNames.get(i)) != null) {
                GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + i);
                GlStateManager.enableTexture2D();
                final Object value = this.shaderSamplers.get(this.samplerNames.get(i));
                int n = -" ".length();
                if (value instanceof Framebuffer) {
                    n = ((Framebuffer)value).framebufferTexture;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else if (value instanceof ITextureObject) {
                    n = ((ITextureObject)value).getGlTextureId();
                    "".length();
                    if (0 == -1) {
                        throw null;
                    }
                }
                else if (value instanceof Integer) {
                    n = (int)value;
                }
                if (n != -" ".length()) {
                    GlStateManager.bindTexture(n);
                    OpenGlHelper.glUniform1i(OpenGlHelper.glGetUniformLocation(this.program, this.samplerNames.get(i)), i);
                }
            }
            ++i;
        }
        final Iterator<ShaderUniform> iterator = this.shaderUniforms.iterator();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().upload();
        }
    }
    
    public ShaderManager(final IResourceManager resourceManager, final String programFilename) throws IOException, JsonException {
        this.shaderSamplers = (Map<String, Object>)Maps.newHashMap();
        this.samplerNames = (List<String>)Lists.newArrayList();
        this.shaderSamplerLocations = (List<Integer>)Lists.newArrayList();
        this.shaderUniforms = (List<ShaderUniform>)Lists.newArrayList();
        this.shaderUniformLocations = (List<Integer>)Lists.newArrayList();
        this.mappedShaderUniforms = (Map<String, ShaderUniform>)Maps.newHashMap();
        final JsonParser jsonParser = new JsonParser();
        final ResourceLocation resourceLocation = new ResourceLocation(ShaderManager.I["".length()] + programFilename + ShaderManager.I[" ".length()]);
        this.programFilename = programFilename;
        InputStream inputStream = null;
        try {
            inputStream = resourceManager.getResource(resourceLocation).getInputStream();
            final JsonObject asJsonObject = jsonParser.parse(IOUtils.toString(inputStream, Charsets.UTF_8)).getAsJsonObject();
            final String string = JsonUtils.getString(asJsonObject, ShaderManager.I["  ".length()]);
            final String string2 = JsonUtils.getString(asJsonObject, ShaderManager.I["   ".length()]);
            final JsonArray jsonArray = JsonUtils.getJsonArray(asJsonObject, ShaderManager.I[0x4B ^ 0x4F], null);
            if (jsonArray != null) {
                int length = "".length();
                final Iterator iterator = jsonArray.iterator();
                "".length();
                if (2 < 0) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final JsonElement jsonElement = iterator.next();
                    try {
                        this.parseSampler(jsonElement);
                        "".length();
                        if (0 >= 2) {
                            throw null;
                        }
                    }
                    catch (Exception ex) {
                        final JsonException func_151379_a = JsonException.func_151379_a(ex);
                        func_151379_a.func_151380_a(ShaderManager.I[0xC ^ 0x9] + length + ShaderManager.I[0xF ^ 0x9]);
                        throw func_151379_a;
                    }
                    ++length;
                }
            }
            final JsonArray jsonArray2 = JsonUtils.getJsonArray(asJsonObject, ShaderManager.I[0xC4 ^ 0xC3], null);
            if (jsonArray2 != null) {
                int length2 = "".length();
                this.attribLocations = (List<Integer>)Lists.newArrayListWithCapacity(jsonArray2.size());
                this.attributes = (List<String>)Lists.newArrayListWithCapacity(jsonArray2.size());
                final Iterator iterator2 = jsonArray2.iterator();
                "".length();
                if (3 <= 2) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    final JsonElement jsonElement2 = iterator2.next();
                    try {
                        this.attributes.add(JsonUtils.getString(jsonElement2, ShaderManager.I[0x47 ^ 0x4F]));
                        "".length();
                        if (2 < -1) {
                            throw null;
                        }
                    }
                    catch (Exception ex2) {
                        final JsonException func_151379_a2 = JsonException.func_151379_a(ex2);
                        func_151379_a2.func_151380_a(ShaderManager.I[0x67 ^ 0x6E] + length2 + ShaderManager.I[0xAD ^ 0xA7]);
                        throw func_151379_a2;
                    }
                    ++length2;
                }
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else {
                this.attribLocations = null;
                this.attributes = null;
            }
            final JsonArray jsonArray3 = JsonUtils.getJsonArray(asJsonObject, ShaderManager.I[0x5D ^ 0x56], null);
            if (jsonArray3 != null) {
                int length3 = "".length();
                final Iterator iterator3 = jsonArray3.iterator();
                "".length();
                if (-1 == 2) {
                    throw null;
                }
                while (iterator3.hasNext()) {
                    final JsonElement jsonElement3 = iterator3.next();
                    try {
                        this.parseUniform(jsonElement3);
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    catch (Exception ex3) {
                        final JsonException func_151379_a3 = JsonException.func_151379_a(ex3);
                        func_151379_a3.func_151380_a(ShaderManager.I[0x38 ^ 0x34] + length3 + ShaderManager.I[0xA0 ^ 0xAD]);
                        throw func_151379_a3;
                    }
                    ++length3;
                }
            }
            this.field_148016_p = JsonBlendingMode.func_148110_a(JsonUtils.getJsonObject(asJsonObject, ShaderManager.I[0x7F ^ 0x71], null));
            this.useFaceCulling = JsonUtils.getBoolean(asJsonObject, ShaderManager.I[0xA0 ^ 0xAF], " ".length() != 0);
            this.vertexShaderLoader = ShaderLoader.loadShader(resourceManager, ShaderLoader.ShaderType.VERTEX, string);
            this.fragmentShaderLoader = ShaderLoader.loadShader(resourceManager, ShaderLoader.ShaderType.FRAGMENT, string2);
            this.program = ShaderLinkHelper.getStaticShaderLinkHelper().createProgram();
            ShaderLinkHelper.getStaticShaderLinkHelper().linkProgram(this);
            this.setupUniforms();
            if (this.attributes != null) {
                final Iterator<String> iterator4 = this.attributes.iterator();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
                while (iterator4.hasNext()) {
                    this.attribLocations.add(OpenGlHelper.glGetAttribLocation(this.program, iterator4.next()));
                }
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
        }
        catch (Exception ex4) {
            final JsonException func_151379_a4 = JsonException.func_151379_a(ex4);
            func_151379_a4.func_151381_b(resourceLocation.getResourcePath());
            throw func_151379_a4;
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
        IOUtils.closeQuietly(inputStream);
        this.markDirty();
    }
    
    public void markDirty() {
        this.isDirty = (" ".length() != 0);
    }
    
    private void parseUniform(final JsonElement jsonElement) throws JsonException {
        final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, ShaderManager.I[0x99 ^ 0x80]);
        final String string = JsonUtils.getString(jsonObject, ShaderManager.I[0xB9 ^ 0xA3]);
        final int type = ShaderUniform.parseType(JsonUtils.getString(jsonObject, ShaderManager.I[0xA3 ^ 0xB8]));
        final int int1 = JsonUtils.getInt(jsonObject, ShaderManager.I[0x97 ^ 0x8B]);
        final float[] array = new float[Math.max(int1, 0x31 ^ 0x21)];
        final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, ShaderManager.I[0xD ^ 0x10]);
        if (jsonArray.size() != int1 && jsonArray.size() > " ".length()) {
            throw new JsonException(ShaderManager.I[0x40 ^ 0x5E] + int1 + ShaderManager.I[0x5A ^ 0x45] + jsonArray.size() + ShaderManager.I[0x86 ^ 0xA6]);
        }
        int i = "".length();
        final Iterator iterator = jsonArray.iterator();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final JsonElement jsonElement2 = iterator.next();
            try {
                array[i] = JsonUtils.getFloat(jsonElement2, ShaderManager.I[0xE0 ^ 0xC1]);
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            catch (Exception ex) {
                final JsonException func_151379_a = JsonException.func_151379_a(ex);
                func_151379_a.func_151380_a(ShaderManager.I[0xB7 ^ 0x95] + i + ShaderManager.I[0xAE ^ 0x8D]);
                throw func_151379_a;
            }
            ++i;
        }
        if (int1 > " ".length() && jsonArray.size() == " ".length()) {
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (i < int1) {
                array[i] = array["".length()];
                ++i;
            }
        }
        int length;
        if (int1 > " ".length() && int1 <= (0x33 ^ 0x37) && type < (0x1C ^ 0x14)) {
            length = int1 - " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        final ShaderUniform shaderUniform = new ShaderUniform(string, type + length, int1, this);
        if (type <= "   ".length()) {
            shaderUniform.set((int)array["".length()], (int)array[" ".length()], (int)array["  ".length()], (int)array["   ".length()]);
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else if (type <= (0x47 ^ 0x40)) {
            shaderUniform.func_148092_b(array["".length()], array[" ".length()], array["  ".length()], array["   ".length()]);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            shaderUniform.set(array);
        }
        this.shaderUniforms.add(shaderUniform);
    }
    
    private void parseSampler(final JsonElement jsonElement) throws JsonException {
        final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, ShaderManager.I[0xB4 ^ 0xA2]);
        final String string = JsonUtils.getString(jsonObject, ShaderManager.I[0xB1 ^ 0xA6]);
        if (!JsonUtils.isString(jsonObject, ShaderManager.I[0x70 ^ 0x68])) {
            this.shaderSamplers.put(string, null);
            this.samplerNames.add(string);
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            this.samplerNames.add(string);
        }
    }
    
    public void addSamplerTexture(final String s, final Object o) {
        if (this.shaderSamplers.containsKey(s)) {
            this.shaderSamplers.remove(s);
        }
        this.shaderSamplers.put(s, o);
        this.markDirty();
    }
    
    public ShaderLoader getFragmentShaderLoader() {
        return this.fragmentShaderLoader;
    }
    
    private static void I() {
        (I = new String[0x63 ^ 0x47])["".length()] = I("\u00192\u000b>\u0011\u0018)E*\u0006\u0005=\u0018;\u0019E", "jZjZt");
        ShaderManager.I[" ".length()] = I("a8'<\u0002", "ORTSl");
        ShaderManager.I["  ".length()] = I(">.9250", "HKKFP");
        ShaderManager.I["   ".length()] = I("#5-$\u000f )8", "EGLCb");
        ShaderManager.I[0x61 ^ 0x65] = I("2.\u0002\n&$=\u001c", "AOozJ");
        ShaderManager.I[0x43 ^ 0x46] = I("=\u0015\u0007\u0012\r+\u0006\u00199", "Ntjba");
        ShaderManager.I[0x4B ^ 0x4D] = I("9", "dWqDo");
        ShaderManager.I[0x23 ^ 0x24] = I("*\u001c\u0016\u0019\u001a)\u001d\u0016\u000e\u0000", "Khbks");
        ShaderManager.I[0x7D ^ 0x75] = I(".\u0011\r7\u0018-\u0010\r ", "OeyEq");
        ShaderManager.I[0x56 ^ 0x5F] = I(".<\u00017=-=\u0001 '\u0014", "OHuET");
        ShaderManager.I[0xAF ^ 0xA5] = I("+", "vWCmD");
        ShaderManager.I[0xA8 ^ 0xA3] = I("\u0016(\u0013\u0015=\u0011+\t", "cFzsR");
        ShaderManager.I[0x75 ^ 0x79] = I("\"?#>6%<9\u0003", "WQJXY");
        ShaderManager.I[0x9 ^ 0x4] = I(",", "qLsai");
        ShaderManager.I[0xA6 ^ 0xA8] = I("\n\n!\u00067", "hfDhS");
        ShaderManager.I[0x9A ^ 0x95] = I("\u0001<+6", "bIGZA");
        ShaderManager.I[0x65 ^ 0x75] = I("\u00062\u0012\u000b\b'z", "UZsom");
        ShaderManager.I[0x34 ^ 0x25] = I("4\u0016!\u000f\u001ew\u0017;\u0017Z1\u0010:\u0007Z$\u00189\u0013\u00162\u000bt\r\u001b:\u001c0C", "WyTcz");
        ShaderManager.I[0x21 ^ 0x33] = I("V\u0007\u0001V0\u001e\u000bO\u00054\u0013\r\u0006\u0010-\u0013\nO\u0005,\u0017\n\n\u0004d\u0006\u001c\u0000\u00116\u0017\u0003A", "vnovD");
        ShaderManager.I[0x85 ^ 0x96] = I("\u001b?<\u0005\nx>&\u001dN>9'\rN-> \u000f\u0001*=i\u0007\u000f55-I", "XPIin");
        ShaderManager.I[0x91 ^ 0x85] = I("U\u000e\u0000b>\u001d\u0002N1:\u0010\u0004\u0007$#\u0010\u0003", "ugnBJ");
        ShaderManager.I[0x73 ^ 0x66] = I("O\u0018\u0003$'\n\u0019K51\u0000\f\u0019$.A", "okkEC");
        ShaderManager.I[0xD1 ^ 0xC7] = I("\u0019-\f2\u000e\u000f>", "jLaBb");
        ShaderManager.I[0x6F ^ 0x78] = I("\r4%\u000e", "cUHkS");
        ShaderManager.I[0xB8 ^ 0xA0] = I("\u0003%'(", "eLKMF");
        ShaderManager.I[0xA ^ 0x13] = I("34?\u0016;47", "FZVpT");
        ShaderManager.I[0x9D ^ 0x87] = I("\u0000\u0005=\u0015", "ndPpv");
        ShaderManager.I[0x1A ^ 0x1] = I("<\u0012*\u0011", "HkZtO");
        ShaderManager.I[0x64 ^ 0x78] = I("\u0014 6\u001a\u0010", "wOCtd");
        ShaderManager.I[0x8 ^ 0x15] = I(";&\u0015#+>", "MGyVN");
        ShaderManager.I[0x90 ^ 0x8E] = I("\u000e\u0007%.\".\rs.#(\u001c=;n(\u000fs9/+\u001c6<n4\u00196,'!\u00006+no\f+?+$\u001d6+n", "GiSON");
        ShaderManager.I[0x5B ^ 0x44] = I("[k'\u0019\u0012\u0019/a", "wKAvg");
        ShaderManager.I[0xB9 ^ 0x99] = I("m", "DOgVs");
        ShaderManager.I[0x70 ^ 0x51] = I("0,\u0006\u000f\u0003", "FMjzf");
        ShaderManager.I[0x48 ^ 0x6A] = I("\"\u0019?3\u0002'#", "TxSFg");
        ShaderManager.I[0x5E ^ 0x7D] = I("\u0019", "DmoLn");
    }
    
    public void deleteShader() {
        ShaderLinkHelper.getStaticShaderLinkHelper().deleteShader(this);
    }
    
    private void setupUniforms() {
        int i = "".length();
        int length = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i < this.samplerNames.size()) {
            final String s = this.samplerNames.get(i);
            final int glGetUniformLocation = OpenGlHelper.glGetUniformLocation(this.program, s);
            if (glGetUniformLocation == -" ".length()) {
                ShaderManager.logger.warn(ShaderManager.I[0xB6 ^ 0xA6] + this.programFilename + ShaderManager.I[0x73 ^ 0x62] + s + ShaderManager.I[0x9B ^ 0x89]);
                this.shaderSamplers.remove(s);
                this.samplerNames.remove(length);
                --length;
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                this.shaderSamplerLocations.add(glGetUniformLocation);
            }
            ++i;
            ++length;
        }
        final Iterator<ShaderUniform> iterator = this.shaderUniforms.iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ShaderUniform shaderUniform = iterator.next();
            final String shaderName = shaderUniform.getShaderName();
            final int glGetUniformLocation2 = OpenGlHelper.glGetUniformLocation(this.program, shaderName);
            if (glGetUniformLocation2 == -" ".length()) {
                ShaderManager.logger.warn(ShaderManager.I[0x71 ^ 0x62] + shaderName + ShaderManager.I[0x8F ^ 0x9B] + ShaderManager.I[0x12 ^ 0x7]);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                continue;
            }
            else {
                this.shaderUniformLocations.add(glGetUniformLocation2);
                shaderUniform.setUniformLocation(glGetUniformLocation2);
                this.mappedShaderUniforms.put(shaderName, shaderUniform);
            }
        }
    }
    
    public void endShader() {
        OpenGlHelper.glUseProgram("".length());
        ShaderManager.currentProgram = -" ".length();
        ShaderManager.staticShaderManager = null;
        ShaderManager.field_148000_e = (" ".length() != 0);
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < this.shaderSamplerLocations.size()) {
            if (this.shaderSamplers.get(this.samplerNames.get(i)) != null) {
                GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + i);
                GlStateManager.bindTexture("".length());
            }
            ++i;
        }
    }
    
    public ShaderLoader getVertexShaderLoader() {
        return this.vertexShaderLoader;
    }
    
    public ShaderUniform getShaderUniformOrDefault(final String s) {
        ShaderUniform defaultShaderUniform;
        if (this.mappedShaderUniforms.containsKey(s)) {
            defaultShaderUniform = this.mappedShaderUniforms.get(s);
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            defaultShaderUniform = ShaderManager.defaultShaderUniform;
        }
        return defaultShaderUniform;
    }
    
    public ShaderUniform getShaderUniform(final String s) {
        ShaderUniform shaderUniform;
        if (this.mappedShaderUniforms.containsKey(s)) {
            shaderUniform = this.mappedShaderUniforms.get(s);
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            shaderUniform = null;
        }
        return shaderUniform;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        defaultShaderUniform = new ShaderDefault();
        ShaderManager.staticShaderManager = null;
        ShaderManager.currentProgram = -" ".length();
        ShaderManager.field_148000_e = (" ".length() != 0);
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getProgram() {
        return this.program;
    }
}
