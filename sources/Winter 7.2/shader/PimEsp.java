package shader;

import net.minecraft.client.shader.Framebuffer;
import winter.module.Module;
import winter.module.Module.Category;
import winter.utils.zeb.util.shader.Shader;

public class PimEsp
  extends Module
{
  private Shader outlineShader;
  private Framebuffer entityBuffer;
  
  public PimEsp()
  {
    super("ESP", Module.Category.Render, -149025);
  }
}
