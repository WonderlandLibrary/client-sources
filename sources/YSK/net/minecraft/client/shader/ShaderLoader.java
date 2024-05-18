package net.minecraft.client.shader;

import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import org.lwjgl.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.util.*;
import java.nio.*;
import org.apache.commons.io.*;
import java.io.*;
import java.util.*;
import com.google.common.collect.*;

public class ShaderLoader
{
    private int shader;
    private static final String[] I;
    private final String shaderFilename;
    private int shaderAttachCount;
    private final ShaderType shaderType;
    
    public String getShaderFilename() {
        return this.shaderFilename;
    }
    
    private ShaderLoader(final ShaderType shaderType, final int shader, final String shaderFilename) {
        this.shaderAttachCount = "".length();
        this.shaderType = shaderType;
        this.shader = shader;
        this.shaderFilename = shaderFilename;
    }
    
    public void attachShader(final ShaderManager shaderManager) {
        this.shaderAttachCount += " ".length();
        OpenGlHelper.glAttachShader(shaderManager.getProgram(), this.shader);
    }
    
    public static ShaderLoader loadShader(final IResourceManager resourceManager, final ShaderType shaderType, final String s) throws IOException {
        ShaderLoader shaderLoader = shaderType.getLoadedShaders().get(s);
        if (shaderLoader == null) {
            final ResourceLocation resourceLocation = new ResourceLocation(ShaderLoader.I["".length()] + s + shaderType.getShaderExtension());
            final byte[] byteArray = toByteArray(new BufferedInputStream(resourceManager.getResource(resourceLocation).getInputStream()));
            final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(byteArray.length);
            byteBuffer.put(byteArray);
            byteBuffer.position("".length());
            final int glCreateShader = OpenGlHelper.glCreateShader(shaderType.getShaderMode());
            OpenGlHelper.glShaderSource(glCreateShader, byteBuffer);
            OpenGlHelper.glCompileShader(glCreateShader);
            if (OpenGlHelper.glGetShaderi(glCreateShader, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
                final JsonException ex = new JsonException(ShaderLoader.I[" ".length()] + shaderType.getShaderName() + ShaderLoader.I["  ".length()] + StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(glCreateShader, 25105 + 11329 - 27288 + 23622)));
                ex.func_151381_b(resourceLocation.getResourcePath());
                throw ex;
            }
            shaderLoader = new ShaderLoader(shaderType, glCreateShader, s);
            shaderType.getLoadedShaders().put(s, shaderLoader);
        }
        return shaderLoader;
    }
    
    public void deleteShader(final ShaderManager shaderManager) {
        this.shaderAttachCount -= " ".length();
        if (this.shaderAttachCount <= 0) {
            OpenGlHelper.glDeleteShader(this.shader);
            this.shaderType.getLoadedShaders().remove(this.shaderFilename);
        }
    }
    
    protected static byte[] toByteArray(final BufferedInputStream bufferedInputStream) throws IOException {
        byte[] byteArray;
        try {
            byteArray = IOUtils.toByteArray((InputStream)bufferedInputStream);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        finally {
            bufferedInputStream.close();
        }
        bufferedInputStream.close();
        return byteArray;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0006 2\u001c)\u0007;|\b>\u001a/!\u0019!Z", "uHSxL");
        ShaderLoader.I[" ".length()] = I("$ \u000f\u000e\u000e\th\u000eB\t\b\"\n\u000b\u0006\u0002o", "gOzbj");
        ShaderLoader.I["  ".length()] = I("T\u0002$.\u0002\u0006\u0013;{E", "trVAe");
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public enum ShaderType
    {
        VERTEX(ShaderType.I["".length()], "".length(), ShaderType.I[" ".length()], ShaderType.I["  ".length()], OpenGlHelper.GL_VERTEX_SHADER);
        
        private final int shaderMode;
        private final String shaderName;
        private final String shaderExtension;
        
        FRAGMENT(ShaderType.I["   ".length()], " ".length(), ShaderType.I[0x6A ^ 0x6E], ShaderType.I[0x3F ^ 0x3A], OpenGlHelper.GL_FRAGMENT_SHADER);
        
        private static final ShaderType[] ENUM$VALUES;
        private static final String[] I;
        private final Map<String, ShaderLoader> loadedShaders;
        
        protected Map<String, ShaderLoader> getLoadedShaders() {
            return this.loadedShaders;
        }
        
        static {
            I();
            final ShaderType[] enum$VALUES = new ShaderType["  ".length()];
            enum$VALUES["".length()] = ShaderType.VERTEX;
            enum$VALUES[" ".length()] = ShaderType.FRAGMENT;
            ENUM$VALUES = enum$VALUES;
        }
        
        protected String getShaderExtension() {
            return this.shaderExtension;
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
                if (-1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        protected int getShaderMode() {
            return this.shaderMode;
        }
        
        private ShaderType(final String s, final int n, final String shaderName, final String shaderExtension, final int shaderMode) {
            this.loadedShaders = (Map<String, ShaderLoader>)Maps.newHashMap();
            this.shaderName = shaderName;
            this.shaderExtension = shaderExtension;
            this.shaderMode = shaderMode;
        }
        
        private static void I() {
            (I = new String[0x50 ^ 0x56])["".length()] = I("\u0002\u001c9\u0010\u000e\f", "TYkDK");
            ShaderType.I[" ".length()] = I("4\u0017#\u001d\u0017:", "BrQir");
            ShaderType.I["  ".length()] = I("a\u0015\u0004%", "OcwMj");
            ShaderType.I["   ".length()] = I("\u0011\u001f4\u0013\t\u0012\u0003!", "WMuTD");
            ShaderType.I[0x71 ^ 0x75] = I("4\u00030/\u00017\u001f%", "RqQHl");
            ShaderType.I[0x60 ^ 0x65] = I("O\u0015%:", "asVRF");
        }
        
        public String getShaderName() {
            return this.shaderName;
        }
    }
}
