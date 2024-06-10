 package me.vene.skilled.utilities;
 
 import java.lang.reflect.Field;
 import java.nio.ByteBuffer;
 import net.minecraftforge.client.event.MouseEvent;
 import net.minecraftforge.common.MinecraftForge;
 import net.minecraftforge.fml.common.eventhandler.Event;
 import org.lwjgl.input.Mouse;
 
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
        catch (IllegalAccessException ex) {}
        MouseUtil.buttonField.setAccessible(false);
        MouseUtil.buttonStateField.setAccessible(true);
        try {
            MouseUtil.buttonStateField.set(mouseEvent, state);
        }
        catch (IllegalAccessException ex2) {}
        MouseUtil.buttonStateField.setAccessible(false);
        MinecraftForge.EVENT_BUS.post((Event)mouseEvent);
        try {
            MouseUtil.buttonsField.setAccessible(true);
            final ByteBuffer buffer = (ByteBuffer)MouseUtil.buttonsField.get(null);
            MouseUtil.buttonsField.setAccessible(false);
            buffer.put(button, (byte)(state ? 1 : 0));
        }
        catch (IllegalAccessException ex3) {}
    }
    
    static {
        try {
            MouseUtil.buttonField = MouseEvent.class.getDeclaredField(StringRegistry.register(new String(new char[] { 'b', 'u', 't', 't', 'o', 'n' })));
        }
        catch (NoSuchFieldException ex) {}
        try {
            MouseUtil.buttonStateField = MouseEvent.class.getDeclaredField(StringRegistry.register(new String(new char[] { 'b', 'u', 't', 't', 'o', 'n', 's', 't', 'a', 't', 'e' })));
        }
        catch (NoSuchFieldException ex2) {}
        try {
            MouseUtil.buttonsField = Mouse.class.getDeclaredField(StringRegistry.register(new String(new char[] { 'b', 'u', 't', 't', 'o', 'n', 's' })));
        }
        catch (NoSuchFieldException ex3) {}
    }
}
