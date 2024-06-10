 package me.vene.skilled.modules.mods.other;
 
 import java.awt.Color;
 import me.vene.skilled.SkilledClient;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.gui.GuiScreen;
 
public class ClickGUI extends Module
{
    private static NumberValue red;
    private static NumberValue green;
    private static NumberValue blue;
    private static int hudR;
    private static int hudG;
    private static int hudB;
    public static int moduleR;
    public static int moduleG;
    public static int moduleB;
    private static BooleanValue rainbow;
    
    public ClickGUI() {
        super(StringRegistry.register(new String(new char[] { 'G', 'U', 'I', ' ', 'C', 'o', 'l', 'o', 'r' })), 54, Category.O);
        this.addValue(ClickGUI.red = new NumberValue(StringRegistry.register(new String(new char[] { 'R', 'e', 'd' })), 35.0, 1.0, 255.0));
        this.addValue(ClickGUI.green = new NumberValue(StringRegistry.register(new String(new char[] { 'G', 'r', 'e', 'e', 'n' })), 245.0, 1.0, 255.0));
        this.addValue(ClickGUI.blue = new NumberValue(StringRegistry.register(new String(new char[] { 'B', 'l', 'u', 'e' })), 15.0, 1.0, 255.0));
        this.addOption(ClickGUI.rainbow);
    }
    
    @Override
    public void onEnable() {
        this.setState(false);
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().currentScreen == null) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)SkilledClient.getClickGUI());
            this.toggle();
        }
    }
    
    public static int getColor() {
        return ClickGUI.rainbow.getState() ? Color.getHSBColor((float)(System.currentTimeMillis() * 0.5 % 3000.0 / 3000.0), 0.8f, 0.8f).getRGB() : rgb((int)ClickGUI.red.getValue(), (int)ClickGUI.green.getValue(), (int)ClickGUI.blue.getValue());
    }
    
    public static int getHudColor() {
        return rgb(ClickGUI.hudR, ClickGUI.hudB, ClickGUI.hudG);
    }
    
    public static int getModuleColor() {
        return rgb(ClickGUI.moduleR, ClickGUI.moduleG, ClickGUI.moduleB);
    }
    
    public static int rgb(final int red, final int green, final int blue) {
        return -16777216 + (red << 16) + (green << 8) + blue;
    }
    
    static {
        ClickGUI.hudR = 21;
        ClickGUI.hudG = 19;
        ClickGUI.hudB = 21;
        ClickGUI.moduleR = 26;
        ClickGUI.moduleG = 26;
        ClickGUI.moduleB = 26;
        ClickGUI.rainbow = new BooleanValue(StringRegistry.register(new String(new char[] { 'R', 'a', 'i', 'n', 'b', 'o', 'w' })), false);
    }
}
