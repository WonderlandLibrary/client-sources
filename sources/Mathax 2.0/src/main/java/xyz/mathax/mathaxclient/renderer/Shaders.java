package xyz.mathax.mathaxclient.renderer;


import xyz.mathax.mathaxclient.init.PreInit;

public class Shaders {
    public static Shader POS_COLOR;
    public static Shader POS_TEXTURED_COLOR;
    public static Shader TEXT;

    @PreInit
    public static void init() {
        POS_COLOR = new Shader("pos_color.vert", "pos_color.frag");
        POS_TEXTURED_COLOR = new Shader("pos_textured_color.vert", "pos_textured_color.frag");
        TEXT = new Shader("text.vert", "text.frag");
    }
}