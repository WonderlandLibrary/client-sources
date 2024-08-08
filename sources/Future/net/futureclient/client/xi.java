package net.futureclient.client;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.ARBShaderObjects;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.EXTFramebufferObject;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.Minecraft;

public abstract class xi
{
    public final int g;
    public int I;
    private int B;
    public int H;
    public final int l;
    private int L;
    private static Minecraft E;
    public final int A;
    public int j;
    public final int K;
    public static final double M = 0.0;
    public final int d;
    private String a;
    private String D;
    private int k;
    
    public xi(final String a, final String d, final int k, final int a2, final int l, final int n, final int n2) {
        final int i = -1;
        final int j = -1;
        final int b = -1;
        final int h = -1;
        final int m = -1;
        final int k2 = -1;
        super();
        this.k = k2;
        this.j = m;
        this.H = h;
        this.B = b;
        this.L = j;
        this.I = i;
        this.a = a;
        this.D = d;
        this.K = k;
        this.A = a2;
        this.l = l;
        this.g = (int)(n * 0.0);
        this.d = (int)(n2 * 0.0);
        this.C();
        this.b();
    }
    
    public xi(final String s, final String s2, final Framebuffer framebuffer) {
        this(s, s2, framebuffer.framebufferTexture, xi.E.displayWidth, xi.E.displayHeight, new ScaledResolution(xi.E).getScaledWidth(), new ScaledResolution(xi.E).getScaledHeight());
    }
    
    static {
        xi.E = Minecraft.getMinecraft();
    }
    
    private void B() {
        final int glCheckFramebufferStatusEXT;
        switch (glCheckFramebufferStatusEXT = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160)) {
            case 36053:
            case 36054:
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT");
            case 36055:
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT");
            default:
                throw new RuntimeException(new StringBuilder().insert(0, "glCheckFramebufferStatusEXT returned unknown status:").append(glCheckFramebufferStatusEXT).toString());
            case 36057:
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT");
            case 36058:
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT");
            case 36059:
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT");
        }
    }
    
    private void C() {
        final int n = 3553;
        this.j = EXTFramebufferObject.glGenFramebuffersEXT();
        this.k = GL11.glGenTextures();
        this.H = EXTFramebufferObject.glGenRenderbuffersEXT();
        GL11.glBindTexture(n, this.k);
        GL11.glTexParameterf(3553, 10241, 9729.0f);
        GL11.glTexParameterf(3553, 10240, 9729.0f);
        GL11.glTexParameterf(3553, 10242, 10496.0f);
        GL11.glTexParameterf(3553, 10243, 10496.0f);
        GL11.glBindTexture(3553, 0);
        GL11.glBindTexture(3553, this.k);
        GL11.glTexImage2D(3553, 0, 32856, this.A, this.l, 0, 6408, 5121, (ByteBuffer)null);
        EXTFramebufferObject.glBindFramebufferEXT(36160, this.j);
        EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, this.k, 0);
        EXTFramebufferObject.glBindRenderbufferEXT(36161, this.H);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, this.A, this.l);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, this.H);
        this.B();
    }
    
    private void b() {
        if (this.I == -1) {
            this.I = ARBShaderObjects.glCreateProgramObjectARB();
            try {
                if (this.B == -1) {
                    this.B = M("#version 120 \nvoid main() { \ngl_TexCoord[0] = gl_MultiTexCoord0; \ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex; \n}", 35633);
                }
                if (this.L == -1) {
                    this.L = M("#version 120\n\nuniform sampler2D DiffuseSamper;\nuniform vec2 TexelSize;\nuniform int SampleRadius;\n\nvoid main()\n{\n    vec4 centerCol = texture2D(DiffuseSamper, gl_TexCoord[0].st);\n    \n    if(centerCol.a != 0.0F)\n    {\n        gl_FragColor = vec4(0, 0, 0, 0);\n        return;\n    }\n    float closest = SampleRadius * 1.0F;\n    for(int xo = -SampleRadius; xo <= SampleRadius; xo++) \n    {\n        for(int yo = -SampleRadius; yo <= SampleRadius; yo++) \n        {\n            vec4 currCol = texture2D(DiffuseSamper, gl_TexCoord[0].st + vec2(xo * TexelSize.x, yo * TexelSize.y));\n            if(currCol.r != 0.0F || currCol.g != 0.0F || currCol.b != 0.0F || currCol.a != 0.0F)\n            {\n                float currentDist = sqrt(xo * xo + yo * yo);\n                if(currentDist < closest)\n                {\n                    closest = currentDist;\n                }\n            }\n        }\n    }\n    float m = 2.0;\n    float fade = max(0, ((SampleRadius * 1.0F) - (closest - 1)) / (SampleRadius * 1.0F));\n    gl_FragColor = vec4(m - fade, m - fade, m - fade, fade);\n}", 35632);
                }
            }
            catch (Exception ex2) {
                final Exception ex = ex2;
                final int l = -1;
                final int n = -1;
                this.I = n;
                this.B = n;
                this.L = l;
                ex.printStackTrace();
            }
            if (this.I != -1) {
                ARBShaderObjects.glAttachObjectARB(this.I, this.B);
                ARBShaderObjects.glAttachObjectARB(this.I, this.L);
                ARBShaderObjects.glLinkProgramARB(this.I);
                if (ARBShaderObjects.glGetObjectParameteriARB(this.I, 35714) == 0) {
                    s.M().M(Level.ERROR, M(this.I));
                    return;
                }
                ARBShaderObjects.glValidateProgramARB(this.I);
                if (ARBShaderObjects.glGetObjectParameteriARB(this.I, 35715) == 0) {
                    s.M().M(Level.ERROR, M(this.I));
                    return;
                }
                ARBShaderObjects.glUseProgramObjectARB(0);
            }
        }
    }
    
    public void e() {
        final double n = 0.0;
        GL11.glScaled(n, n, n);
        GL11.glDisable(3553);
        GL11.glBegin(4);
        GL11.glTexCoord2d(0.0, 1.0);
        final double n2 = 0.0;
        GL11.glVertex2d(n2, n2);
        final double n3 = 0.0;
        GL11.glTexCoord2d(n3, n3);
        GL11.glVertex2d(0.0, (double)this.d);
        GL11.glTexCoord2d(1.0, 0.0);
        final double n4 = 0.0;
        final double n5 = 1.0;
        final double n6 = 1.0;
        GL11.glVertex2d((double)this.g, (double)this.d);
        GL11.glTexCoord2d(n6, 0.0);
        GL11.glVertex2d((double)this.g, (double)this.d);
        GL11.glTexCoord2d(n5, 1.0);
        GL11.glVertex2d((double)this.g, 0.0);
        GL11.glTexCoord2d(n4, 1.0);
        final double n7 = 0.0;
        GL11.glVertex2d(n7, n7);
        GL11.glEnd();
        final double n8 = 0.0;
        GL11.glScaled(n8, n8, n8);
    }
    
    public void M() {
        if (this.H > -1) {
            EXTFramebufferObject.glDeleteRenderbuffersEXT(this.H);
        }
        if (this.j > -1) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(this.j);
        }
        if (this.k > -1) {
            GL11.glDeleteTextures(this.k);
        }
    }
    
    public static String M(final int n) {
        return ARBShaderObjects.glGetInfoLogARB(n, ARBShaderObjects.glGetObjectParameteriARB(n, 35716));
    }
    
    public abstract xi M();
    
    public int M(final String s) {
        return ARBShaderObjects.glGetUniformLocationARB(this.I, (CharSequence)s);
    }
    
    public int M() {
        return this.k;
    }
    
    public static int M(final String s, final int n) throws Exception {
        int glCreateShaderObjectARB = 0;
        try {
            if ((glCreateShaderObjectARB = ARBShaderObjects.glCreateShaderObjectARB(n)) == 0) {
                return 0;
            }
            ARBShaderObjects.glShaderSourceARB(glCreateShaderObjectARB, (CharSequence)s);
            ARBShaderObjects.glCompileShaderARB(glCreateShaderObjectARB);
            if (ARBShaderObjects.glGetObjectParameteriARB(glCreateShaderObjectARB, 35713) == 0) {
                throw new RuntimeException(new StringBuilder().insert(0, "Error creating shader: ").append(M(glCreateShaderObjectARB)).toString());
            }
            return glCreateShaderObjectARB;
        }
        catch (Exception ex) {
            ARBShaderObjects.glDeleteObjectARB(glCreateShaderObjectARB);
            throw ex;
        }
    }
}
