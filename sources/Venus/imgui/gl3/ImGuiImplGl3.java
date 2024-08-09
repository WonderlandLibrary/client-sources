/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.gl3;

import imgui.ImDrawData;
import imgui.ImFontAtlas;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.callback.ImPlatformFuncViewport;
import imgui.type.ImInt;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.lwjgl.opengl.GL32;

public final class ImGuiImplGl3 {
    private int glVersion = 0;
    private String glslVersion = "";
    private int gFontTexture = 0;
    private int gShaderHandle = 0;
    private int gVertHandle = 0;
    private int gFragHandle = 0;
    private int gAttribLocationTex = 0;
    private int gAttribLocationProjMtx = 0;
    private int gAttribLocationVtxPos = 0;
    private int gAttribLocationVtxUV = 0;
    private int gAttribLocationVtxColor = 0;
    private int gVboHandle = 0;
    private int gElementsHandle = 0;
    private int gVertexArrayObjectHandle = 0;
    private final ImVec2 displaySize = new ImVec2();
    private final ImVec2 framebufferScale = new ImVec2();
    private final ImVec2 displayPos = new ImVec2();
    private final ImVec4 clipRect = new ImVec4();
    private final float[] orthoProjMatrix = new float[16];
    private final int[] lastActiveTexture = new int[1];
    private final int[] lastProgram = new int[1];
    private final int[] lastTexture = new int[1];
    private final int[] lastArrayBuffer = new int[1];
    private final int[] lastVertexArrayObject = new int[1];
    private final int[] lastViewport = new int[4];
    private final int[] lastScissorBox = new int[4];
    private final int[] lastBlendSrcRgb = new int[1];
    private final int[] lastBlendDstRgb = new int[1];
    private final int[] lastBlendSrcAlpha = new int[1];
    private final int[] lastBlendDstAlpha = new int[1];
    private final int[] lastBlendEquationRgb = new int[1];
    private final int[] lastBlendEquationAlpha = new int[1];
    private boolean lastEnableBlend = false;
    private boolean lastEnableCullFace = false;
    private boolean lastEnableDepthTest = false;
    private boolean lastEnableStencilTest = false;
    private boolean lastEnableScissorTest = false;

    public void init() {
        this.init(null);
    }

    public void init(String string) {
        this.readGlVersion();
        this.setupBackendCapabilitiesFlags();
        this.glslVersion = string == null ? "#version 130" : string;
        this.createDeviceObjects();
        if (ImGui.getIO().hasConfigFlags(1)) {
            this.initPlatformInterface();
        }
    }

    public void renderDrawData(ImDrawData imDrawData) {
        if (imDrawData.getCmdListsCount() <= 0) {
            return;
        }
        imDrawData.getDisplaySize(this.displaySize);
        imDrawData.getDisplayPos(this.displayPos);
        imDrawData.getFramebufferScale(this.framebufferScale);
        float f = this.displayPos.x;
        float f2 = this.displayPos.y;
        float f3 = this.framebufferScale.x;
        float f4 = this.framebufferScale.y;
        int n = (int)(this.displaySize.x * this.framebufferScale.x);
        int n2 = (int)(this.displaySize.y * this.framebufferScale.y);
        if (n <= 0 || n2 <= 0) {
            return;
        }
        this.backupGlState();
        this.bind(n, n2);
        for (int i = 0; i < imDrawData.getCmdListsCount(); ++i) {
            GL32.glBufferData(34962, imDrawData.getCmdListVtxBufferData(i), 35040);
            GL32.glBufferData(34963, imDrawData.getCmdListIdxBufferData(i), 35040);
            for (int j = 0; j < imDrawData.getCmdListCmdBufferSize(i); ++j) {
                imDrawData.getCmdListCmdBufferClipRect(i, j, this.clipRect);
                float f5 = (this.clipRect.x - f) * f3;
                float f6 = (this.clipRect.y - f2) * f4;
                float f7 = (this.clipRect.z - f) * f3;
                float f8 = (this.clipRect.w - f2) * f4;
                if (f7 <= f5 || f8 <= f6) continue;
                GL32.glScissor((int)f5, (int)((float)n2 - f8), (int)(f7 - f5), (int)(f8 - f6));
                int n3 = imDrawData.getCmdListCmdBufferTextureId(i, j);
                int n4 = imDrawData.getCmdListCmdBufferElemCount(i, j);
                int n5 = imDrawData.getCmdListCmdBufferIdxOffset(i, j);
                int n6 = imDrawData.getCmdListCmdBufferVtxOffset(i, j);
                int n7 = n5 * 2;
                GL32.glBindTexture(3553, n3);
                if (this.glVersion >= 320) {
                    GL32.glDrawElementsBaseVertex(4, n4, 5123, n7, n6);
                    continue;
                }
                GL32.glDrawElements(4, n4, 5123, n7);
            }
        }
        this.unbind();
        this.restoreModifiedGlState();
    }

    public void dispose() {
        GL32.glDeleteBuffers(this.gVboHandle);
        GL32.glDeleteBuffers(this.gElementsHandle);
        GL32.glDetachShader(this.gShaderHandle, this.gVertHandle);
        GL32.glDetachShader(this.gShaderHandle, this.gFragHandle);
        GL32.glDeleteProgram(this.gShaderHandle);
        GL32.glDeleteTextures(this.gFontTexture);
        this.shutdownPlatformInterface();
    }

    public void updateFontsTexture() {
        GL32.glDeleteTextures(this.gFontTexture);
        ImFontAtlas imFontAtlas = ImGui.getIO().getFonts();
        ImInt imInt = new ImInt();
        ImInt imInt2 = new ImInt();
        ByteBuffer byteBuffer = imFontAtlas.getTexDataAsRGBA32(imInt, imInt2);
        this.gFontTexture = GL32.glGenTextures();
        GL32.glBindTexture(3553, this.gFontTexture);
        GL32.glTexParameteri(3553, 10241, 9729);
        GL32.glTexParameteri(3553, 10240, 9729);
        GL32.glTexImage2D(3553, 0, 6408, imInt.get(), imInt2.get(), 0, 6408, 5121, byteBuffer);
        imFontAtlas.setTexID(this.gFontTexture);
    }

    private void readGlVersion() {
        int[] nArray = new int[1];
        int[] nArray2 = new int[1];
        GL32.glGetIntegerv(33307, nArray);
        GL32.glGetIntegerv(33308, nArray2);
        this.glVersion = nArray[0] * 100 + nArray2[0] * 10;
    }

    private void setupBackendCapabilitiesFlags() {
        ImGuiIO imGuiIO = ImGui.getIO();
        imGuiIO.setBackendRendererName("imgui_java_impl_opengl3");
        if (this.glVersion >= 320) {
            imGuiIO.addBackendFlags(8);
        }
        imGuiIO.addBackendFlags(4096);
    }

    private void createDeviceObjects() {
        int[] nArray = new int[1];
        int[] nArray2 = new int[1];
        int[] nArray3 = new int[1];
        GL32.glGetIntegerv(32873, nArray);
        GL32.glGetIntegerv(34964, nArray2);
        GL32.glGetIntegerv(34229, nArray3);
        this.createShaders();
        this.gAttribLocationTex = GL32.glGetUniformLocation(this.gShaderHandle, "Texture");
        this.gAttribLocationProjMtx = GL32.glGetUniformLocation(this.gShaderHandle, "ProjMtx");
        this.gAttribLocationVtxPos = GL32.glGetAttribLocation(this.gShaderHandle, "Position");
        this.gAttribLocationVtxUV = GL32.glGetAttribLocation(this.gShaderHandle, "UV");
        this.gAttribLocationVtxColor = GL32.glGetAttribLocation(this.gShaderHandle, "Color");
        this.gVboHandle = GL32.glGenBuffers();
        this.gElementsHandle = GL32.glGenBuffers();
        this.updateFontsTexture();
        GL32.glBindTexture(3553, nArray[0]);
        GL32.glBindBuffer(34962, nArray2[0]);
        GL32.glBindVertexArray(nArray3[0]);
    }

    private void createShaders() {
        String string;
        String string2;
        int n = this.parseGlslVersionString();
        if (n < 130) {
            string2 = this.getVertexShaderGlsl120();
            string = this.getFragmentShaderGlsl120();
        } else if (n == 300) {
            string2 = this.getVertexShaderGlsl300es();
            string = this.getFragmentShaderGlsl300es();
        } else if (n >= 410) {
            string2 = this.getVertexShaderGlsl410Core();
            string = this.getFragmentShaderGlsl410Core();
        } else {
            string2 = this.getVertexShaderGlsl130();
            string = this.getFragmentShaderGlsl130();
        }
        this.gVertHandle = this.createAndCompileShader(35633, string2);
        this.gFragHandle = this.createAndCompileShader(35632, string);
        this.gShaderHandle = GL32.glCreateProgram();
        GL32.glAttachShader(this.gShaderHandle, this.gVertHandle);
        GL32.glAttachShader(this.gShaderHandle, this.gFragHandle);
        GL32.glLinkProgram(this.gShaderHandle);
        if (GL32.glGetProgrami(this.gShaderHandle, 35714) == 0) {
            throw new IllegalStateException("Failed to link shader program:\n" + GL32.glGetProgramInfoLog(this.gShaderHandle));
        }
    }

    private int parseGlslVersionString() {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(this.glslVersion);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        throw new IllegalArgumentException("Invalid GLSL version string: " + this.glslVersion);
    }

    private void backupGlState() {
        GL32.glGetIntegerv(34016, this.lastActiveTexture);
        GL32.glActiveTexture(33984);
        GL32.glGetIntegerv(35725, this.lastProgram);
        GL32.glGetIntegerv(32873, this.lastTexture);
        GL32.glGetIntegerv(34964, this.lastArrayBuffer);
        GL32.glGetIntegerv(34229, this.lastVertexArrayObject);
        GL32.glGetIntegerv(2978, this.lastViewport);
        GL32.glGetIntegerv(3088, this.lastScissorBox);
        GL32.glGetIntegerv(32969, this.lastBlendSrcRgb);
        GL32.glGetIntegerv(32968, this.lastBlendDstRgb);
        GL32.glGetIntegerv(32971, this.lastBlendSrcAlpha);
        GL32.glGetIntegerv(32970, this.lastBlendDstAlpha);
        GL32.glGetIntegerv(32777, this.lastBlendEquationRgb);
        GL32.glGetIntegerv(34877, this.lastBlendEquationAlpha);
        this.lastEnableBlend = GL32.glIsEnabled(3042);
        this.lastEnableCullFace = GL32.glIsEnabled(2884);
        this.lastEnableDepthTest = GL32.glIsEnabled(2929);
        this.lastEnableStencilTest = GL32.glIsEnabled(2960);
        this.lastEnableScissorTest = GL32.glIsEnabled(3089);
    }

    private void restoreModifiedGlState() {
        GL32.glUseProgram(this.lastProgram[0]);
        GL32.glBindTexture(3553, this.lastTexture[0]);
        GL32.glActiveTexture(this.lastActiveTexture[0]);
        GL32.glBindVertexArray(this.lastVertexArrayObject[0]);
        GL32.glBindBuffer(34962, this.lastArrayBuffer[0]);
        GL32.glBlendEquationSeparate(this.lastBlendEquationRgb[0], this.lastBlendEquationAlpha[0]);
        GL32.glBlendFuncSeparate(this.lastBlendSrcRgb[0], this.lastBlendDstRgb[0], this.lastBlendSrcAlpha[0], this.lastBlendDstAlpha[0]);
        if (this.lastEnableBlend) {
            GL32.glEnable(3042);
        } else {
            GL32.glDisable(3042);
        }
        if (this.lastEnableCullFace) {
            GL32.glEnable(2884);
        } else {
            GL32.glDisable(2884);
        }
        if (this.lastEnableDepthTest) {
            GL32.glEnable(2929);
        } else {
            GL32.glDisable(2929);
        }
        if (this.lastEnableStencilTest) {
            GL32.glEnable(2960);
        } else {
            GL32.glDisable(2960);
        }
        if (this.lastEnableScissorTest) {
            GL32.glEnable(3089);
        } else {
            GL32.glDisable(3089);
        }
        GL32.glViewport(this.lastViewport[0], this.lastViewport[1], this.lastViewport[2], this.lastViewport[3]);
        GL32.glScissor(this.lastScissorBox[0], this.lastScissorBox[1], this.lastScissorBox[2], this.lastScissorBox[3]);
    }

    private void bind(int n, int n2) {
        this.gVertexArrayObjectHandle = GL32.glGenVertexArrays();
        GL32.glEnable(3042);
        GL32.glBlendEquation(32774);
        GL32.glBlendFuncSeparate(770, 771, 1, 771);
        GL32.glDisable(2884);
        GL32.glDisable(2929);
        GL32.glDisable(2960);
        GL32.glEnable(3089);
        GL32.glViewport(0, 0, n, n2);
        float f = this.displayPos.x;
        float f2 = this.displayPos.x + this.displaySize.x;
        float f3 = this.displayPos.y;
        float f4 = this.displayPos.y + this.displaySize.y;
        this.orthoProjMatrix[0] = 2.0f / (f2 - f);
        this.orthoProjMatrix[5] = 2.0f / (f3 - f4);
        this.orthoProjMatrix[10] = -1.0f;
        this.orthoProjMatrix[12] = (f2 + f) / (f - f2);
        this.orthoProjMatrix[13] = (f3 + f4) / (f4 - f3);
        this.orthoProjMatrix[15] = 1.0f;
        GL32.glUseProgram(this.gShaderHandle);
        GL32.glUniform1i(this.gAttribLocationTex, 0);
        GL32.glUniformMatrix4fv(this.gAttribLocationProjMtx, false, this.orthoProjMatrix);
        GL32.glBindVertexArray(this.gVertexArrayObjectHandle);
        GL32.glBindBuffer(34962, this.gVboHandle);
        GL32.glBindBuffer(34963, this.gElementsHandle);
        GL32.glEnableVertexAttribArray(this.gAttribLocationVtxPos);
        GL32.glEnableVertexAttribArray(this.gAttribLocationVtxUV);
        GL32.glEnableVertexAttribArray(this.gAttribLocationVtxColor);
        GL32.glVertexAttribPointer(this.gAttribLocationVtxPos, 2, 5126, false, 20, 0L);
        GL32.glVertexAttribPointer(this.gAttribLocationVtxUV, 2, 5126, false, 20, 8L);
        GL32.glVertexAttribPointer(this.gAttribLocationVtxColor, 4, 5121, true, 20, 16L);
    }

    private void unbind() {
        GL32.glDeleteVertexArrays(this.gVertexArrayObjectHandle);
    }

    private void initPlatformInterface() {
        ImGui.getPlatformIO().setRendererRenderWindow(new ImPlatformFuncViewport(this){
            final ImGuiImplGl3 this$0;
            {
                this.this$0 = imGuiImplGl3;
            }

            @Override
            public void accept(ImGuiViewport imGuiViewport) {
                if (!imGuiViewport.hasFlags(1)) {
                    GL32.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    GL32.glClear(16384);
                }
                this.this$0.renderDrawData(imGuiViewport.getDrawData());
            }
        });
    }

    private void shutdownPlatformInterface() {
        ImGui.destroyPlatformWindows();
    }

    private int createAndCompileShader(int n, CharSequence charSequence) {
        int n2 = GL32.glCreateShader(n);
        GL32.glShaderSource(n2, charSequence);
        GL32.glCompileShader(n2);
        if (GL32.glGetShaderi(n2, 35713) == 0) {
            throw new IllegalStateException("Failed to compile shader:\n" + GL32.glGetShaderInfoLog(n2));
        }
        return n2;
    }

    private String getVertexShaderGlsl120() {
        return this.glslVersion + "\nuniform mat4 ProjMtx;\nattribute vec2 Position;\nattribute vec2 UV;\nattribute vec4 Color;\nvarying vec2 Frag_UV;\nvarying vec4 Frag_Color;\nvoid main()\n{\n    Frag_UV = UV;\n    Frag_Color = Color;\n    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n}\n";
    }

    private String getVertexShaderGlsl130() {
        return this.glslVersion + "\nuniform mat4 ProjMtx;\nin vec2 Position;\nin vec2 UV;\nin vec4 Color;\nout vec2 Frag_UV;\nout vec4 Frag_Color;\nvoid main()\n{\n    Frag_UV = UV;\n    Frag_Color = Color;\n    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n}\n";
    }

    private String getVertexShaderGlsl300es() {
        return this.glslVersion + "\nprecision highp float;\nlayout (location = 0) in vec2 Position;\nlayout (location = 1) in vec2 UV;\nlayout (location = 2) in vec4 Color;\nuniform mat4 ProjMtx;\nout vec2 Frag_UV;\nout vec4 Frag_Color;\nvoid main()\n{\n    Frag_UV = UV;\n    Frag_Color = Color;\n    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n}\n";
    }

    private String getVertexShaderGlsl410Core() {
        return this.glslVersion + "\nlayout (location = 0) in vec2 Position;\nlayout (location = 1) in vec2 UV;\nlayout (location = 2) in vec4 Color;\nuniform mat4 ProjMtx;\nout vec2 Frag_UV;\nout vec4 Frag_Color;\nvoid main()\n{\n    Frag_UV = UV;\n    Frag_Color = Color;\n    gl_Position = ProjMtx * vec4(Position.xy,0,1);\n}\n";
    }

    private String getFragmentShaderGlsl120() {
        return this.glslVersion + "\n#ifdef GL_ES\n    precision mediump float;\n#endif\nuniform sampler2D Texture;\nvarying vec2 Frag_UV;\nvarying vec4 Frag_Color;\nvoid main()\n{\n    gl_FragColor = Frag_Color * texture2D(Texture, Frag_UV.st);\n}\n";
    }

    private String getFragmentShaderGlsl130() {
        return this.glslVersion + "\nuniform sampler2D Texture;\nin vec2 Frag_UV;\nin vec4 Frag_Color;\nout vec4 Out_Color;\nvoid main()\n{\n    Out_Color = Frag_Color * texture(Texture, Frag_UV.st);\n}\n";
    }

    private String getFragmentShaderGlsl300es() {
        return this.glslVersion + "\nprecision mediump float;\nuniform sampler2D Texture;\nin vec2 Frag_UV;\nin vec4 Frag_Color;\nlayout (location = 0) out vec4 Out_Color;\nvoid main()\n{\n    Out_Color = Frag_Color * texture(Texture, Frag_UV.st);\n}\n";
    }

    private String getFragmentShaderGlsl410Core() {
        return this.glslVersion + "\nin vec2 Frag_UV;\nin vec4 Frag_Color;\nuniform sampler2D Texture;\nlayout (location = 0) out vec4 Out_Color;\nvoid main()\n{\n    Out_Color = Frag_Color * texture(Texture, Frag_UV.st);\n}\n";
    }
}

