package net.minecraft.client.shader;

import org.lwjgl.util.vector.*;
import net.minecraft.client.resources.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.util.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import org.apache.commons.io.*;
import com.google.gson.*;
import java.io.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;

public class ShaderGroup
{
    private static final String[] I;
    private final Map<String, Framebuffer> mapFramebuffers;
    private float field_148036_j;
    private int mainFramebufferHeight;
    private float field_148037_k;
    private final List<Shader> listShaders;
    private Matrix4f projectionMatrix;
    private String shaderGroupName;
    private final List<Framebuffer> listFramebuffers;
    private int mainFramebufferWidth;
    private IResourceManager resourceManager;
    private Framebuffer mainFramebuffer;
    
    public void loadShaderGroup(final float field_148037_k) {
        if (field_148037_k < this.field_148037_k) {
            this.field_148036_j += 1.0f - this.field_148037_k;
            this.field_148036_j += field_148037_k;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            this.field_148036_j += field_148037_k - this.field_148037_k;
        }
        this.field_148037_k = field_148037_k;
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (this.field_148036_j > 20.0f) {
            this.field_148036_j -= 20.0f;
        }
        final Iterator<Shader> iterator = this.listShaders.iterator();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().loadShader(this.field_148036_j / 20.0f);
        }
    }
    
    public ShaderGroup(final TextureManager textureManager, final IResourceManager resourceManager, final Framebuffer mainFramebuffer, final ResourceLocation resourceLocation) throws IOException, JsonException, JsonSyntaxException {
        this.listShaders = (List<Shader>)Lists.newArrayList();
        this.mapFramebuffers = (Map<String, Framebuffer>)Maps.newHashMap();
        this.listFramebuffers = (List<Framebuffer>)Lists.newArrayList();
        this.resourceManager = resourceManager;
        this.mainFramebuffer = mainFramebuffer;
        this.field_148036_j = 0.0f;
        this.field_148037_k = 0.0f;
        this.mainFramebufferWidth = mainFramebuffer.framebufferWidth;
        this.mainFramebufferHeight = mainFramebuffer.framebufferHeight;
        this.shaderGroupName = resourceLocation.toString();
        this.resetProjectionMatrix();
        this.parseGroup(textureManager, resourceLocation);
    }
    
    public Shader addShader(final String s, final Framebuffer framebuffer, final Framebuffer framebuffer2) throws IOException, JsonException {
        final Shader shader = new Shader(this.resourceManager, s, framebuffer, framebuffer2);
        this.listShaders.add(this.listShaders.size(), shader);
        return shader;
    }
    
    static {
        I();
    }
    
    public void createBindFramebuffers(final int n, final int n2) {
        this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
        this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
        this.resetProjectionMatrix();
        final Iterator<Shader> iterator = this.listShaders.iterator();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().setProjectionMatrix(this.projectionMatrix);
        }
        final Iterator<Framebuffer> iterator2 = this.listFramebuffers.iterator();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (iterator2.hasNext()) {
            iterator2.next().createBindFramebuffer(n, n2);
        }
    }
    
    public void addFramebuffer(final String s, final int n, final int n2) {
        final Framebuffer framebuffer = new Framebuffer(n, n2, " ".length() != 0);
        framebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.mapFramebuffers.put(s, framebuffer);
        if (n == this.mainFramebufferWidth && n2 == this.mainFramebufferHeight) {
            this.listFramebuffers.add(framebuffer);
        }
    }
    
    private void initUniform(final JsonElement jsonElement) throws JsonException {
        final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, ShaderGroup.I[0x15 ^ 0x30]);
        final String string = JsonUtils.getString(jsonObject, ShaderGroup.I[0x6B ^ 0x4D]);
        final ShaderUniform shaderUniform = this.listShaders.get(this.listShaders.size() - " ".length()).getShaderManager().getShaderUniform(string);
        if (shaderUniform == null) {
            throw new JsonException(ShaderGroup.I[0xE0 ^ 0xC7] + string + ShaderGroup.I[0x69 ^ 0x41]);
        }
        final float[] array = new float[0x5E ^ 0x5A];
        int length = "".length();
        final Iterator iterator = JsonUtils.getJsonArray(jsonObject, ShaderGroup.I[0xA0 ^ 0x89]).iterator();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final JsonElement jsonElement2 = iterator.next();
            try {
                array[length] = JsonUtils.getFloat(jsonElement2, ShaderGroup.I[0x91 ^ 0xBB]);
                "".length();
                if (3 == 0) {
                    throw null;
                }
            }
            catch (Exception ex) {
                final JsonException func_151379_a = JsonException.func_151379_a(ex);
                func_151379_a.func_151380_a(ShaderGroup.I[0x6E ^ 0x45] + length + ShaderGroup.I[0x15 ^ 0x39]);
                throw func_151379_a;
            }
            ++length;
        }
        switch (length) {
            default: {
                "".length();
                if (-1 == 0) {
                    throw null;
                }
                break;
            }
            case 1: {
                shaderUniform.set(array["".length()]);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
                break;
            }
            case 2: {
                shaderUniform.set(array["".length()], array[" ".length()]);
                "".length();
                if (2 == 1) {
                    throw null;
                }
                break;
            }
            case 3: {
                shaderUniform.set(array["".length()], array[" ".length()], array["  ".length()]);
                "".length();
                if (4 < 2) {
                    throw null;
                }
                break;
            }
            case 4: {
                shaderUniform.set(array["".length()], array[" ".length()], array["  ".length()], array["   ".length()]);
                break;
            }
        }
    }
    
    public Framebuffer getFramebufferRaw(final String s) {
        return this.mapFramebuffers.get(s);
    }
    
    public void parseGroup(final TextureManager textureManager, final ResourceLocation resourceLocation) throws JsonException, IOException, JsonSyntaxException {
        final JsonParser jsonParser = new JsonParser();
        InputStream inputStream = null;
        try {
            inputStream = this.resourceManager.getResource(resourceLocation).getInputStream();
            final JsonObject asJsonObject = jsonParser.parse(IOUtils.toString(inputStream, Charsets.UTF_8)).getAsJsonObject();
            if (JsonUtils.isJsonArray(asJsonObject, ShaderGroup.I["".length()])) {
                final JsonArray asJsonArray = asJsonObject.getAsJsonArray(ShaderGroup.I[" ".length()]);
                int length = "".length();
                final Iterator iterator = asJsonArray.iterator();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final JsonElement jsonElement = iterator.next();
                    try {
                        this.initTarget(jsonElement);
                        "".length();
                        if (3 < 3) {
                            throw null;
                        }
                    }
                    catch (Exception ex) {
                        final JsonException func_151379_a = JsonException.func_151379_a(ex);
                        func_151379_a.func_151380_a(ShaderGroup.I["  ".length()] + length + ShaderGroup.I["   ".length()]);
                        throw func_151379_a;
                    }
                    ++length;
                }
            }
            if (JsonUtils.isJsonArray(asJsonObject, ShaderGroup.I[0x33 ^ 0x37])) {
                final JsonArray asJsonArray2 = asJsonObject.getAsJsonArray(ShaderGroup.I[0x44 ^ 0x41]);
                int length2 = "".length();
                final Iterator iterator2 = asJsonArray2.iterator();
                "".length();
                if (3 < 1) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    final JsonElement jsonElement2 = iterator2.next();
                    try {
                        this.parsePass(textureManager, jsonElement2);
                        "".length();
                        if (1 <= 0) {
                            throw null;
                        }
                    }
                    catch (Exception ex2) {
                        final JsonException func_151379_a2 = JsonException.func_151379_a(ex2);
                        func_151379_a2.func_151380_a(ShaderGroup.I[0x30 ^ 0x36] + length2 + ShaderGroup.I[0xB2 ^ 0xB5]);
                        throw func_151379_a2;
                    }
                    ++length2;
                }
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
        }
        catch (Exception ex3) {
            final JsonException func_151379_a3 = JsonException.func_151379_a(ex3);
            func_151379_a3.func_151381_b(resourceLocation.getResourcePath());
            throw func_151379_a3;
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
        IOUtils.closeQuietly(inputStream);
    }
    
    private static void I() {
        (I = new String[0x82 ^ 0xAC])["".length()] = I("\u00025\u0003\u000e\b\u0002'", "vTqim");
        ShaderGroup.I[" ".length()] = I("\u001c\t#-\u0012\u001c\u001b", "hhQJw");
        ShaderGroup.I["  ".length()] = I("\u0005\u00068\u001f1\u0005\u0014\u0011", "qgJxT");
        ShaderGroup.I["   ".length()] = I(")", "tRVbJ");
        ShaderGroup.I[0x44 ^ 0x40] = I("!,\t\t\u0004\"", "QMzza");
        ShaderGroup.I[0x43 ^ 0x46] = I("\n\u0007\u0011\u001d\f\t", "zfbni");
        ShaderGroup.I[0x11 ^ 0x17] = I("?\u00187\u0017\u001d<\"", "OyDdx");
        ShaderGroup.I[0x46 ^ 0x41] = I(")", "tyDDR");
        ShaderGroup.I[0x5E ^ 0x56] = I("0$\u00107\u00000", "DEbPe");
        ShaderGroup.I[0x18 ^ 0x11] = I("\t.*\r", "gOGhB");
        ShaderGroup.I[0xA ^ 0x0] = I(":&.\u0001\f", "MOJud");
        ShaderGroup.I[0x7F ^ 0x74] = I("\u0004\u0001\u001c\u000b>\u0018", "ldulV");
        ShaderGroup.I[0xA5 ^ 0xA9] = I("N\n!D2\u0002\u00117\u00057\u0017C6\u00015\u0007\r7\u0000", "ncRdS");
        ShaderGroup.I[0x1 ^ 0xC] = I("\u001c\u000e\u0004)", "lowZw");
        ShaderGroup.I[0xA0 ^ 0xAE] = I("\"0.1", "LQCTK");
        ShaderGroup.I[0x37 ^ 0x38] = I(";&\u0005#\u00045-\u0005", "RHqBv");
        ShaderGroup.I[0xB0 ^ 0xA0] = I("\u0007\u0019\u0019?-\u001a\u000b\b?", "hlmKL");
        ShaderGroup.I[0x71 ^ 0x60] = I("\u001c'(8#u=9?00=xj", "UIXMW");
        ShaderGroup.I[0x2F ^ 0x3D] = I("}u%\b')u/\b6z09\u000e1.", "ZUAgB");
        ShaderGroup.I[0x55 ^ 0x46] = I("\u00027\u000e<29b\u000e-5*'\u000el`", "MBzLG");
        ShaderGroup.I[0x67 ^ 0x73] = I("dL/\u0016\r0L%\u0016\u001cc\t3\u0010\u001b7", "ClKyh");
        ShaderGroup.I[0x81 ^ 0x94] = I("/\u0005?#7<\u0017\"#%", "NpGWV");
        ShaderGroup.I[0x63 ^ 0x75] = I("\u0017\u0018\u001b?1\u0004\n\u0006?", "vmcKP");
        ShaderGroup.I[0x3B ^ 0x2C] = I("\u0007\n\u000b!", "ikfDe");
        ShaderGroup.I[0x6C ^ 0x74] = I("\u0005\u0013", "lwgMb");
        ShaderGroup.I[0x4E ^ 0x57] = I("?&>9\u00029&5b\u0012-%#.\u0003d", "KCFMw");
        ShaderGroup.I[0x75 ^ 0x6F] = I("}\u001d\u001d.", "SmsIC");
        ShaderGroup.I[0xD8 ^ 0xC3] = I("\u0011\r?6\r1H%3\u001a$\r%r\u00071H%7\u00107\u001d#7Hd", "ChQRh");
        ShaderGroup.I[0x23 ^ 0x3F] = I("uS\u0017\u0006\u000e!S\u001d\u0006\u001fr\u0016\u000b\u0000\u0018&", "Rssik");
        ShaderGroup.I[0x6B ^ 0x76] = I("2+\u0005.<", "EBaZT");
        ShaderGroup.I[0x6C ^ 0x72] = I("#$\u001a\b\t?", "KAsoa");
        ShaderGroup.I[0x74 ^ 0x6B] = I(";\b\u0019#;<\u0000\u0007", "YauJU");
        ShaderGroup.I[0xE2 ^ 0xC2] = I("*\u0017\u00148\u00149\u0005\t8\u0006\u0010", "KblLu");
        ShaderGroup.I[0x17 ^ 0x36] = I("\u001a", "GyANd");
        ShaderGroup.I[0xA8 ^ 0x8A] = I("\u001c\t\u000415\u001b\n\u001e", "igmWZ");
        ShaderGroup.I[0x7E ^ 0x5D] = I("8\u001f:\u0016\u0017?\u001c +", "MqSpx");
        ShaderGroup.I[0x1B ^ 0x3F] = I("'", "zJCfT");
        ShaderGroup.I[0x85 ^ 0xA0] = I("\u0001\b?\"\b\u0006\u000b", "tfVDg");
        ShaderGroup.I[0x96 ^ 0xB0] = I("\u0006\u0017\u0019+", "hvtNI");
        ShaderGroup.I[0x5B ^ 0x7C] = I("<\n\u001f2-\u001b\tVs", "idvTB");
        ShaderGroup.I[0x4E ^ 0x66] = I("IF\u000b9\u0007\u001dF\u00019\u0016N\u0003\u0017?\u0011\u001a", "nfoVb");
        ShaderGroup.I[0xED ^ 0xC4] = I("\u0012\u0010/\u0011\t\u0017", "dqCdl");
        ShaderGroup.I[0xBE ^ 0x94] = I("?/:\u000f=", "INVzX");
        ShaderGroup.I[0x8A ^ 0xA1] = I("5\u0002\u001e9\u000b08", "CcrLn");
        ShaderGroup.I[0x23 ^ 0xF] = I("\u001b", "FRGsu");
        ShaderGroup.I[0xA6 ^ 0x8B] = I(".\u0007*\b21\u000f\"\u0019k.\u000f-\u0003", "CnDmQ");
    }
    
    private void resetProjectionMatrix() {
        (this.projectionMatrix = new Matrix4f()).setIdentity();
        this.projectionMatrix.m00 = 2.0f / this.mainFramebuffer.framebufferTextureWidth;
        this.projectionMatrix.m11 = 2.0f / -this.mainFramebuffer.framebufferTextureHeight;
        this.projectionMatrix.m22 = -0.0020001999f;
        this.projectionMatrix.m33 = 1.0f;
        this.projectionMatrix.m03 = -1.0f;
        this.projectionMatrix.m13 = 1.0f;
        this.projectionMatrix.m23 = -1.0001999f;
    }
    
    private void parsePass(final TextureManager textureManager, final JsonElement jsonElement) throws IOException, JsonException {
        final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, ShaderGroup.I[0x83 ^ 0x8E]);
        final String string = JsonUtils.getString(jsonObject, ShaderGroup.I[0x20 ^ 0x2E]);
        final String string2 = JsonUtils.getString(jsonObject, ShaderGroup.I[0x2D ^ 0x22]);
        final String string3 = JsonUtils.getString(jsonObject, ShaderGroup.I[0x9E ^ 0x8E]);
        final Framebuffer framebuffer = this.getFramebuffer(string2);
        final Framebuffer framebuffer2 = this.getFramebuffer(string3);
        if (framebuffer == null) {
            throw new JsonException(ShaderGroup.I[0x5 ^ 0x14] + string2 + ShaderGroup.I[0x4 ^ 0x16]);
        }
        if (framebuffer2 == null) {
            throw new JsonException(ShaderGroup.I[0x8B ^ 0x98] + string3 + ShaderGroup.I[0x88 ^ 0x9C]);
        }
        final Shader addShader = this.addShader(string, framebuffer, framebuffer2);
        final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, ShaderGroup.I[0xA2 ^ 0xB7], null);
        if (jsonArray != null) {
            int length = "".length();
            final Iterator iterator = jsonArray.iterator();
            "".length();
            if (4 <= 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final JsonElement jsonElement2 = iterator.next();
                try {
                    final JsonObject jsonObject2 = JsonUtils.getJsonObject(jsonElement2, ShaderGroup.I[0xA8 ^ 0xBE]);
                    final String string4 = JsonUtils.getString(jsonObject2, ShaderGroup.I[0x38 ^ 0x2F]);
                    final String string5 = JsonUtils.getString(jsonObject2, ShaderGroup.I[0x23 ^ 0x3B]);
                    final Framebuffer framebuffer3 = this.getFramebuffer(string5);
                    if (framebuffer3 == null) {
                        final ResourceLocation resourceLocation = new ResourceLocation(ShaderGroup.I[0x9B ^ 0x82] + string5 + ShaderGroup.I[0x67 ^ 0x7D]);
                        try {
                            this.resourceManager.getResource(resourceLocation);
                            "".length();
                            if (2 <= -1) {
                                throw null;
                            }
                        }
                        catch (FileNotFoundException ex3) {
                            throw new JsonException(ShaderGroup.I[0xB4 ^ 0xAF] + string5 + ShaderGroup.I[0x65 ^ 0x79]);
                        }
                        textureManager.bindTexture(resourceLocation);
                        final ITextureObject texture = textureManager.getTexture(resourceLocation);
                        final int int1 = JsonUtils.getInt(jsonObject2, ShaderGroup.I[0xA3 ^ 0xBE]);
                        final int int2 = JsonUtils.getInt(jsonObject2, ShaderGroup.I[0x19 ^ 0x7]);
                        if (JsonUtils.getBoolean(jsonObject2, ShaderGroup.I[0x60 ^ 0x7F])) {
                            GL11.glTexParameteri(280 + 3031 - 2274 + 2516, 7835 + 948 - 2418 + 3876, 8814 + 3950 - 7322 + 4287);
                            GL11.glTexParameteri(3001 + 2265 - 3696 + 1983, 8865 + 4230 - 7262 + 4407, 4587 + 2952 + 513 + 1677);
                            "".length();
                            if (-1 < -1) {
                                throw null;
                            }
                        }
                        else {
                            GL11.glTexParameteri(1390 + 480 - 1351 + 3034, 8992 + 5455 - 10861 + 6655, 6687 + 5082 - 10576 + 8535);
                            GL11.glTexParameteri(3249 + 3206 - 4888 + 1986, 9533 + 7310 - 9991 + 3388, 3092 + 4154 - 7094 + 9576);
                        }
                        addShader.addAuxFramebuffer(string4, texture.getGlTextureId(), int1, int2);
                        "".length();
                        if (1 == -1) {
                            throw null;
                        }
                    }
                    else {
                        addShader.addAuxFramebuffer(string4, framebuffer3, framebuffer3.framebufferTextureWidth, framebuffer3.framebufferTextureHeight);
                        "".length();
                        if (2 >= 4) {
                            throw null;
                        }
                    }
                }
                catch (Exception ex) {
                    final JsonException func_151379_a = JsonException.func_151379_a(ex);
                    func_151379_a.func_151380_a(ShaderGroup.I[0x90 ^ 0xB0] + length + ShaderGroup.I[0x45 ^ 0x64]);
                    throw func_151379_a;
                }
                ++length;
            }
        }
        final JsonArray jsonArray2 = JsonUtils.getJsonArray(jsonObject, ShaderGroup.I[0x17 ^ 0x35], null);
        if (jsonArray2 != null) {
            int length2 = "".length();
            final Iterator iterator2 = jsonArray2.iterator();
            "".length();
            if (2 == 3) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final JsonElement jsonElement3 = iterator2.next();
                try {
                    this.initUniform(jsonElement3);
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                }
                catch (Exception ex2) {
                    final JsonException func_151379_a2 = JsonException.func_151379_a(ex2);
                    func_151379_a2.func_151380_a(ShaderGroup.I[0x7E ^ 0x5D] + length2 + ShaderGroup.I[0x3D ^ 0x19]);
                    throw func_151379_a2;
                }
                ++length2;
            }
        }
    }
    
    public final String getShaderGroupName() {
        return this.shaderGroupName;
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void deleteShaderGroup() {
        final Iterator<Framebuffer> iterator = this.mapFramebuffers.values().iterator();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().deleteFramebuffer();
        }
        final Iterator<Shader> iterator2 = this.listShaders.iterator();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (iterator2.hasNext()) {
            iterator2.next().deleteShader();
        }
        this.listShaders.clear();
    }
    
    private Framebuffer getFramebuffer(final String s) {
        Framebuffer mainFramebuffer;
        if (s == null) {
            mainFramebuffer = null;
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else if (s.equals(ShaderGroup.I[0x70 ^ 0x5D])) {
            mainFramebuffer = this.mainFramebuffer;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            mainFramebuffer = this.mapFramebuffers.get(s);
        }
        return mainFramebuffer;
    }
    
    private void initTarget(final JsonElement jsonElement) throws JsonException {
        if (JsonUtils.isString(jsonElement)) {
            this.addFramebuffer(jsonElement.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, ShaderGroup.I[0x80 ^ 0x88]);
            final String string = JsonUtils.getString(jsonObject, ShaderGroup.I[0x25 ^ 0x2C]);
            final int int1 = JsonUtils.getInt(jsonObject, ShaderGroup.I[0x83 ^ 0x89], this.mainFramebufferWidth);
            final int int2 = JsonUtils.getInt(jsonObject, ShaderGroup.I[0x6D ^ 0x66], this.mainFramebufferHeight);
            if (this.mapFramebuffers.containsKey(string)) {
                throw new JsonException(String.valueOf(string) + ShaderGroup.I[0x1 ^ 0xD]);
            }
            this.addFramebuffer(string, int1, int2);
        }
    }
}
