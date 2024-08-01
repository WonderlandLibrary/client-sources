package wtf.diablo.client.util.render.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import wtf.diablo.client.util.render.shader.exceptions.ShaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.*;

public class ShaderCreator {

    private final ResourceLocation vertexFile;
    private final ResourceLocation fragmentFile;
    private final String identifier;

    public ShaderCreator(String identifier, ResourceLocation vertexShaderFile, ResourceLocation fragmentShaderFile) {
        this.vertexFile = vertexShaderFile;
        this.fragmentFile = fragmentShaderFile;
        this.identifier = identifier;
    }

    public Shader setupShader() throws ShaderException {

        boolean vertexExists = true;
        boolean fragmentExists = true;
        if (!vertexExists || !fragmentExists) {
            System.err.println("Vertex Shader Info");
            System.err.println("File location: " + vertexFile.getResourcePath());
            System.err.println("Exists: " + vertexExists);
            System.err.println("Fragment Shader Info");
            System.err.println("File location: " + fragmentFile.getResourcePath());
            System.err.println("Exists: " + fragmentExists);
            throw new ShaderException("Shader Files Not Found");
        }

        int programID = glCreateProgram();

        int vertexID = createShaderProgram(vertexFile, GL_VERTEX_SHADER);
        int fragmentID = createShaderProgram(fragmentFile, GL_FRAGMENT_SHADER);

        glAttachShader(programID, vertexID);
        glAttachShader(programID, fragmentID);


        return new Shader(identifier, programID, vertexID, fragmentID);
    }

    private int createShaderProgram(ResourceLocation shaderFile, int shaderType) throws ShaderException {
        int shaderID = glCreateShader(shaderType);

        StringBuilder shaderCode = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(shaderFile).getInputStream()))) {
            reader.lines().forEach((line) -> shaderCode.append(line).append("\n"));
        } catch (IOException e) {
            throw new ShaderException("Failed to create Shader", e);
        }

        glShaderSource(shaderID, shaderCode.toString());
        glCompileShader(shaderID);

        int status = glGetShaderi(shaderID, GL_COMPILE_STATUS);
        if (status == 0) {
            System.err.println(glGetShaderInfoLog(shaderID, glGetShaderi(shaderID, GL_INFO_LOG_LENGTH)));
            throw new ShaderException("Failed to compile shader");
        }

        return shaderID;
    }

}
