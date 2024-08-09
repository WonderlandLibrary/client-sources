package wtf.shiyeno.util.render.shader;

import wtf.shiyeno.util.render.shader.core.Shader;
import wtf.shiyeno.util.render.shader.impl.AlphaShader;

import java.util.ArrayList;
import java.util.List;

public class Shaders {

    public List<Shader> shaderList = new ArrayList<>();

    public Shaders() {

        shaderList.addAll(List.of(
           new AlphaShader("")
        ));

    }

}
