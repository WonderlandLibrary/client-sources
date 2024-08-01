package wtf.diablo.client.shader.shaders;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import wtf.diablo.client.shader.ShaderImpl;
import wtf.diablo.client.shader.actual.ShaderConstants;

public final class MainMenuShader extends ShaderImpl {
    private final long lastTime = System.currentTimeMillis();
    public MainMenuShader() {
        super(ShaderConstants.MAIN_MENU);
    }

    public void render(final double x, final double y, final double width, final double height) {
        this.useProgram();
        this.setupUniforms();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.drawCanvas(x, y, width, height);
        this.unUseProgram();
    }
    public void setupUniforms() {
        this.setUniformFloat("time", (System.currentTimeMillis() - lastTime) / 100000f);
        this.setUniformFloat("resolution", Display.getWidth(), Display.getHeight());
    }
}