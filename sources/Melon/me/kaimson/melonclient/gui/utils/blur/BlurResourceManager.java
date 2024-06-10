package me.kaimson.melonclient.gui.utils.blur;

import java.io.*;
import java.util.*;

public class BlurResourceManager implements bni
{
    private final float BLUR_RADIUS;
    
    public Set<String> a() {
        return null;
    }
    
    public bnh a(final jy location) throws IOException {
        return (bnh)new BlurResource(this.BLUR_RADIUS);
    }
    
    public List<bnh> b(final jy location) throws IOException {
        return null;
    }
    
    public BlurResourceManager(final float BLUR_RADIUS) {
        this.BLUR_RADIUS = BLUR_RADIUS;
    }
}
