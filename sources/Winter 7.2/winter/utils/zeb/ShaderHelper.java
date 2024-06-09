package winter.utils.zeb;

import java.io.PrintStream;
import java.util.Scanner;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL20;
import winter.utils.zeb.util.shader.Shader;
import winter.utils.zeb.util.shader.ShaderType;

public enum ShaderHelper
{
  INSTANCE;
  
  public int create(String shaderCode, ShaderType type)
  {
    int shader = 0;
    try
    {
      shader = ARBShaderObjects.glCreateShaderObjectARB(type.getTypeId());
      if (shader == 0) {
        return 0;
      }
      ARBShaderObjects.glShaderSourceARB(shader, shaderCode);
      ARBShaderObjects.glCompileShaderARB(shader);
      if (ARBShaderObjects.glGetObjectParameteriARB(shader, 35713) == 0)
      {
        System.out.println(GL20.glGetShaderInfoLog(shader, 500));
        System.err.println("Could not compile shader!");
        throw new RuntimeException("Error creating shader: " + Shader.getLogInfo(shader));
      }
      return shader;
    }
    catch (Exception exception)
    {
      ARBShaderObjects.glDeleteObjectARB(shader);
      
      exception.printStackTrace();
    }
    return 0;
  }
  
  public int create(Asset shader, ShaderType type)
  {
    String code = "";
    
    Scanner scanner = new Scanner(shader.asInputStream());
    while (scanner.hasNext()) {
      code = code + scanner.nextLine() + "\n";
    }
    return create(code, type);
  }
}
