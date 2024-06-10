package me.kaimson.melonclient.features.modules.utils;

import java.util.*;
import org.lwjgl.input.*;
import com.google.common.collect.*;

public class CPSUtils
{
    private final Queue<Long> clicks;
    private boolean wasDown;
    private final Type type;
    
    public CPSUtils click() {
        this.clicks.add(System.currentTimeMillis() + 1000L);
        return this;
    }
    
    public int getCPS() {
        while (!this.clicks.isEmpty() && this.clicks.peek() < System.currentTimeMillis()) {
            this.clicks.remove();
        }
        return this.clicks.size();
    }
    
    public void tick() {
        Mouse.poll();
        final boolean down = Mouse.isButtonDown((this.type != Type.LEFT) ? 1 : 0);
        if (down != this.wasDown && down) {
            this.click();
        }
        this.wasDown = down;
    }
    
    public CPSUtils(final Type type) {
        this.clicks = (Queue<Long>)Lists.newLinkedList();
        this.type = type;
    }
    
    public enum Type
    {
        LEFT, 
        RIGHT;
    }
}
