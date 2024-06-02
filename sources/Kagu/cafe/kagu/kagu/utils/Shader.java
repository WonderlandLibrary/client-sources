package cafe.kagu.kagu.utils;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL20;

public class Shader {

	private int programId, shaderId = 0, shaderType;
	private String shaderCode = "";
	private HashMap<String, Integer> uniforms = new HashMap<>();
	
	public static Logger logger = LogManager.getLogger();

	public Shader(int programId, ShaderType type, String shaderCode) throws Exception {
		if (programId == 0) {
			throw new Exception("Could not create program");
		}
		this.programId = programId;
		shaderType = type.type;
		this.shaderCode = shaderCode;
	}
	
	public Shader(ShaderType type, String shaderCode) throws Exception {
		this(glCreateProgram(), type, shaderCode);
	}
	
	/**
	 * Sets the shader code
	 * @param shaderCode The shader code
	 */
	public void setShaderCode(String shaderCode) {
		this.shaderCode = shaderCode;
	}
	
	/**
	 * Creates the shader
	 * @return The shader id
	 * @throws Exception ???
	 */
	public int create() throws Exception {

		// Create a new shader and get the id
		shaderId = glCreateShader(shaderType);
		if (shaderId == 0) {
			throw new Exception("Error creating shader. Type: " + shaderType);
		}

		// Attach the shader source to the shader and compile it
		glShaderSource(shaderId, shaderCode);
		glCompileShader(shaderId);

		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
			throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
		}

		// Attach the shader to the current program
		glAttachShader(programId, shaderId);

		// Return shader id in case we want to use it
		return shaderId;

	}
	
	/**
	 * Links the shader
	 * @throws Exception ???
	 */
	public void link() throws Exception {

		// Link to the current program
		glLinkProgram(programId);

		if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
			throw new Exception("Error linking Shader code (maybe you forgot to create first?): " + glGetProgramInfoLog(programId, 1024));
		}

		// Detach shader
		if (shaderId != 0) {
			glDetachShader(programId, shaderType);
		}

		// Validates that the shader can be used in the current opengl state, mostly
		// used for debugging
		glValidateProgram(programId);
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
			logger.error("Error validating Shader code" + glGetProgramInfoLog(programId, 1024));
		}

	}
	
	/**
	 * Binds the shader
	 */
	public void bind() {
		glUseProgram(programId);
	}
	
	/**
	 * Unbinds the shader
	 */
	public void unbind() {
		glUseProgram(0);
	}
	
	/**
	 * Deletes the shader and cleans up
	 */
	public void cleanup() {
		unbind();
		if (programId != 0) {
			glDeleteProgram(programId);
		}
	}
	
	/**
	 * Creates a uniform
	 * @param uniform The uniform name
	 */
	public void createUniform(String uniform) {
		uniforms.put(uniform, GL20.glGetUniformLocation(programId, uniform));
	}
	
	/**
	 * Gets a uniform id
	 * @param uniform The uniform name
	 * @return The uniform id
	 */
	public int getUniform(String uniform) {
		return uniforms.get(uniform);
	}

	/**
	 * @return the shaderId
	 */
	public int getShaderId() {
		return shaderId;
	}
	
	public static enum ShaderType {
		VERTEX(GL_VERTEX_SHADER), FRAGMENT(GL_FRAGMENT_SHADER);

		public int type;

		ShaderType(int type) {
			this.type = type;
		}

	}

}