package me.kaimson.melonclient.gui.utils.blur;

import java.util.*;
import java.io.*;

public class BlurResource implements bnh
{
    private final float BLUR_RADIUS;
    
    public InputStream b() {
        final StringBuilder data = new StringBuilder();
        final Scanner scan = new Scanner(BlurResource.class.getResourceAsStream("/assets/minecraft/melonclient/shaders/post/fade_in_blur.json"));
        try {
            while (scan.hasNextLine()) {
                data.append(scan.nextLine().replaceAll("@radius@", Integer.toString((int)this.BLUR_RADIUS))).append("\n");
            }
        }
        finally {
            scan.close();
        }
        return new ByteArrayInputStream(data.toString().getBytes());
    }
    
    public boolean c() {
        return false;
    }
    
    public <T extends bnw> T a(final String p_110526_1_) {
        return null;
    }
    
    public String d() {
        return null;
    }
    
    public jy a() {
        return null;
    }
    
    public BlurResource(final float BLUR_RADIUS) {
        this.BLUR_RADIUS = BLUR_RADIUS;
    }
}
