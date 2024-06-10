package xyz.gucciclient.utils;

import java.lang.reflect.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.nio.*;
import org.lwjgl.input.*;

public class MouseUtil
{
    private static Field buttonStateField;
    private static Field buttonField;
    private static Field buttonsField;
    
    public static void sendClick(final int button, final boolean state) {
        final MouseEvent mouseEvent = new MouseEvent();
        MouseUtil.buttonField.setAccessible(true);
        try {
            MouseUtil.buttonField.set(mouseEvent, button);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        MouseUtil.buttonField.setAccessible(false);
        MouseUtil.buttonStateField.setAccessible(true);
        try {
            MouseUtil.buttonStateField.set(mouseEvent, state);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        MouseUtil.buttonStateField.setAccessible(false);
        MinecraftForge.EVENT_BUS.post((Event)mouseEvent);
        try {
            MouseUtil.buttonsField.setAccessible(true);
            final ByteBuffer buffer = (ByteBuffer)MouseUtil.buttonsField.get(null);
            MouseUtil.buttonsField.setAccessible(false);
            buffer.put(button, (byte)(state ? 1 : 0));
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    static {
        try {
            MouseUtil.buttonField = MouseEvent.class.getDeclaredField("button");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            MouseUtil.buttonStateField = MouseEvent.class.getDeclaredField("buttonstate");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            MouseUtil.buttonsField = Mouse.class.getDeclaredField("buttons");
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
