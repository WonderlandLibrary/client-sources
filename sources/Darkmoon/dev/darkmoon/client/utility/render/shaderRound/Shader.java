package dev.darkmoon.client.utility.render.shaderRound;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glCompileShader;

public class Shader {

    private int programId;

    public void init() {
        int program = glCreateProgram();

        glAttachShader(program, parseShader("#version 120PIDORAS" +
                "PIDORAS" +
                "void main() {PIDORAS" +
                "    gl_Position = gl_Vertex;PIDORAS" +
                "}", GL_VERTEX_SHADER));
        glAttachShader(program, parseShader("#ifdef GL_ESPIDORAS" +
                "precision mediump float;PIDORAS" +
                "#endifPIDORAS" +
                "PIDORAS" +
                "#extension GL_OES_standard_derivatives : enablePIDORAS" +
                "PIDORAS" +
                "#define NUM_OCTAVES 16PIDORAS" +
                "PIDORAS" +
                "uniform float time;PIDORAS" +
                "uniform vec2 resolution;PIDORAS" +
                "PIDORAS" +
                "mat3 rotX(float a) {PIDORAS" +
                "\tfloat c = cos(a);PIDORAS" +
                "\tfloat s = sin(a);PIDORAS" +
                "\treturn mat3(PIDORAS" +
                "\t\t1, 0, 0,PIDORAS" +
                "\t\t0, c, -s,PIDORAS" +
                "\t\t0, s, cPIDORAS" +
                "\t);PIDORAS" +
                "}PIDORAS" +
                "mat3 rotY(float a) {PIDORAS" +
                "\tfloat c = cos(a);PIDORAS" +
                "\tfloat s = sin(a);PIDORAS" +
                "\treturn mat3(PIDORAS" +
                "\t\tc, 0, -s,PIDORAS" +
                "\t\t0, 1, 0,PIDORAS" +
                "\t\ts, 0, cPIDORAS" +
                "\t);PIDORAS" +
                "}PIDORAS" +
                "PIDORAS" +
                "float random(vec2 pos) {PIDORAS" +
                "\treturn fract(sin(dot(pos.xy, vec2(1399.9898, 78.233))) * 43758.5453123);PIDORAS" +
                "}PIDORAS" +
                "PIDORAS" +
                "float noise(vec2 pos) {PIDORAS" +
                "\tvec2 i = floor(pos);PIDORAS" +
                "\tvec2 f = fract(pos);PIDORAS" +
                "\tfloat a = random(i + vec2(0.0, 0.0));PIDORAS" +
                "\tfloat b = random(i + vec2(1.0, 0.0));PIDORAS" +
                "\tfloat c = random(i + vec2(0.0, 1.0));PIDORAS" +
                "\tfloat d = random(i + vec2(1.0, 1.0));PIDORAS" +
                "\tvec2 u = f * f * (3.0 - 2.0 * f);PIDORAS" +
                "\treturn mix(a, b, u.x) + (c - a) * u.y * (1.0 - u.x) + (d - b) * u.x * u.y;PIDORAS" +
                "}PIDORAS" +
                "PIDORAS" +
                "float fbm(vec2 pos) {PIDORAS" +
                "\tfloat v = 0.0;PIDORAS" +
                "\tfloat a = 0.5;PIDORAS" +
                "\tvec2 shift = vec2(100.0);PIDORAS" +
                "\tmat2 rot = mat2(cos(0.5), sin(0.5), -sin(0.5), cos(0.5));PIDORAS" +
                "\tfor (int i=0; i<NUM_OCTAVES; i++) {PIDORAS" +
                "\t\tv += a * noise(pos);PIDORAS" +
                "\t\tpos = rot * pos * 2.0 + shift;PIDORAS" +
                "\t\ta *= 0.5;PIDORAS" +
                "\t}PIDORAS" +
                "\treturn v;PIDORAS" +
                "}PIDORAS" +
                "PIDORAS" +
                "void main(void) {PIDORAS" +
                "\tvec2 p = (gl_FragCoord.xy * 1.0 - resolution.xy) / min(resolution.x, resolution.y);PIDORAS" +
                "PIDORAS" +
                "\tfloat t = 0.0, d;PIDORAS" +
                "PIDORAS" +
                "\tfloat time2 = 0.6 * time / 2.0;PIDORAS" +
                "PIDORAS" +
                "\tvec2 q = vec2(0.0);PIDORAS" +
                "\tq.x = fbm(p + 0.30 * time2);PIDORAS" +
                "\tq.y = fbm(p + vec2(1.0));PIDORAS" +
                "\tvec2 r = vec2(0.0);PIDORAS" +
                "\tr.x = fbm(p + 1.0 * q + vec2(1.2, 3.2) + 0.135 * time2);PIDORAS" +
                "\tr.y = fbm(p + 1.0 * q + vec2(8.8, 2.8) + 0.126 * time2);PIDORAS" +
                "\tfloat f = fbm(p + r);PIDORAS" +
                "\tvec3 color = mix(PIDORAS" +
                "\t\tvec3(0.90, 0.2, 0.2),PIDORAS" +
                "\t\tvec3(0.50, 0.07, 0.07),PIDORAS" +
                "\t\tclamp((f * f) * 3.0, 0.0, 1.0)PIDORAS" +
                "\t);PIDORAS" +
                "PIDORAS" +
                "\tcolor = mix(PIDORAS" +
                "\t\tcolor,PIDORAS" +
                "\t\tvec3(0.90, 0.18, 0.20),PIDORAS" +
                "\t\tclamp(length(q), 0.0, 1.0)PIDORAS" +
                "\t);PIDORAS" +
                "PIDORAS" +
                "PIDORAS" +
                "\tcolor = mix(PIDORAS" +
                "\t\tcolor,PIDORAS" +
                "\t\tvec3(0.0, 0.0, 0.5),PIDORAS" +
                "\t\tclamp(length(r.x), 0.0, 1.0)PIDORAS" +
                "\t);PIDORAS" +
                "PIDORAS" +
                "\tcolor = (f * f * f + 0.6 * f * f + 0.9 * f) * color;PIDORAS" +
                "PIDORAS" +
                "\tgl_FragColor = vec4(color, 1.0);PIDORAS" +
                "}", GL_FRAGMENT_SHADER));

        glLinkProgram(program);

        this.programId = program;
    }

    public void attach(int width, int height, float mouseX, float mouseY, float time) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);

        glUseProgram(programId);

        glUniform2f(glGetUniformLocation(programId, "resolution"), width, height);
        glUniform2f(glGetUniformLocation(programId, "mouse"), mouseX / width, 1 - mouseY / height);
        glUniform1f(glGetUniformLocation(programId, "time"), time);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-1, -1);
        GL11.glVertex2f(-1, 1);
        GL11.glVertex2f(1, 1);
        GL11.glVertex2f(1, -1);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public void detach() {
        GL20.glUseProgram(0);
    }

    private int parseShader(String str, int shaderType) {
        int shader = glCreateShader(shaderType);

        glShaderSource(shader, str.replace("PIDORAS", "\n"));

        glCompileShader(shader);

        return shader;
    }
}
