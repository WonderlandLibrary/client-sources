package pw.cinque.keystrokes.render;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Key
{
    private final String name;
    private boolean isMouseKey;
    private int key;
    private final int x;
    private final int y;
    private final int width;
    private boolean pressed = false;

    public Key(String name, int x, int y)
    {
        this.name = name;
        this.isMouseKey = false;
        this.key = Keyboard.getKeyIndex(name);

        if (this.key == 0)
        {
            if (name.equalsIgnoreCase("LMB"))
            {
                this.key = 0;
                this.isMouseKey = true;
            }
            else
            {
                if (!name.equalsIgnoreCase("RMB"))
                {
                    throw new IllegalArgumentException("Invalid key \'" + name + "\'");
                }

                this.key = 1;
                this.isMouseKey = true;
            }
        }

        this.x = x;
        this.y = y;
        this.width = Math.max(18, Minecraft.getMinecraft().fontRendererObj.getStringWidth(name) + 8);
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isMouseKey()
    {
        return this.isMouseKey;
    }

    public int getKey()
    {
        return this.key;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return 18;
    }

    public boolean isPressed()
    {
        return this.pressed;
    }

    public void setPressed(boolean pressed)
    {
        this.pressed = pressed;
    }
}
