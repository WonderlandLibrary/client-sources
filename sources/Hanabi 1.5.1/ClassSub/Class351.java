package ClassSub;

import net.minecraft.client.*;
import org.jetbrains.annotations.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public class Class351
{
    private static final String VERTEX_SHADER = "#version 130\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
    private Minecraft mc;
    private int program;
    private long startTime;
    
    
    public Class351(@NotNull final String s) {
        this.mc = Minecraft.getMinecraft();
        this.program = GL20.glCreateProgram();
        this.startTime = System.currentTimeMillis();
        this.initShader(s);
    }
    
    private void initShader(@NotNull final String s) {
        final int glCreateShader = GL20.glCreateShader(35633);
        final int glCreateShader2 = GL20.glCreateShader(35632);
        GL20.glShaderSource(glCreateShader, (CharSequence)"#version 130\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}");
        GL20.glShaderSource(glCreateShader2, (CharSequence)s);
        GL20.glValidateProgram(this.program);
        GL20.glCompileShader(glCreateShader);
        GL20.glCompileShader(glCreateShader2);
        GL20.glAttachShader(this.program, glCreateShader);
        GL20.glAttachShader(this.program, glCreateShader2);
        GL20.glLinkProgram(this.program);
    }
    
    public void renderFirst() {
        GL11.glClear(16640);
        GL20.glUseProgram(this.program);
    }
    
    public void renderSecond() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(0.0, (double)scaledResolution.getScaledHeight());
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d((double)scaledResolution.getScaledWidth(), (double)scaledResolution.getScaledHeight());
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d((double)scaledResolution.getScaledWidth(), 0.0);
        GL11.glEnd();
        GL20.glUseProgram(0);
    }
    
    public void bind() {
        GL20.glUseProgram(this.program);
    }
    
    public int getProgram() {
        return this.program;
    }
    
    @NotNull
    public Class351 uniform1i(@NotNull final String s, final int n) {
        GL20.glUniform1i(GL20.glGetUniformLocation(this.program, (CharSequence)s), n);
        return this;
    }
    
    @NotNull
    public Class351 uniform2i(@NotNull final String s, final int n, final int n2) {
        GL20.glUniform2i(GL20.glGetUniformLocation(this.program, (CharSequence)s), n, n2);
        return this;
    }
    
    @NotNull
    public Class351 uniform3i(@NotNull final String s, final int n, final int n2, final int n3) {
        GL20.glUniform3i(GL20.glGetUniformLocation(this.program, (CharSequence)s), n, n2, n3);
        return this;
    }
    
    @NotNull
    public Class351 uniform4i(@NotNull final String s, final int n, final int n2, final int n3, final int n4) {
        GL20.glUniform4i(GL20.glGetUniformLocation(this.program, (CharSequence)s), n, n2, n3, n4);
        return this;
    }
    
    @NotNull
    public Class351 uniform1f(@NotNull final String s, final float n) {
        GL20.glUniform1f(GL20.glGetUniformLocation(this.program, (CharSequence)s), n);
        return this;
    }
    
    @NotNull
    public Class351 uniform2f(@NotNull final String s, final float n, final float n2) {
        GL20.glUniform2f(GL20.glGetUniformLocation(this.program, (CharSequence)s), n, n2);
        return this;
    }
    
    @NotNull
    public Class351 uniform3f(@NotNull final String s, final float n, final float n2, final float n3) {
        GL20.glUniform3f(GL20.glGetUniformLocation(this.program, (CharSequence)s), n, n2, n3);
        return this;
    }
    
    @NotNull
    public Class351 uniform4f(@NotNull final String s, final float n, final float n2, final float n3, final float n4) {
        GL20.glUniform4f(GL20.glGetUniformLocation(this.program, (CharSequence)s), n, n2, n3, n4);
        return this;
    }
    
    @NotNull
    public Class351 uniform1b(@NotNull final String s, final boolean b) {
        GL20.glUniform1i(GL20.glGetUniformLocation(this.program, (CharSequence)s), (int)(b ? 1 : 0));
        return this;
    }
    
    public void addDefaultUniforms() {
        GL20.glUniform2f(GL20.glGetUniformLocation(this.program, (CharSequence)"resolution"), (float)this.mc.displayWidth, (float)this.mc.displayHeight);
        GL20.glUniform1f(GL20.glGetUniformLocation(this.program, (CharSequence)"time"), (System.currentTimeMillis() - this.startTime) / 1000.0f);
    }
}
