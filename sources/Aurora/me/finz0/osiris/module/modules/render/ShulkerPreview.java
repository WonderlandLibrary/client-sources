package me.finz0.osiris.module.modules.render;

import me.finz0.osiris.module.Module;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;

public class ShulkerPreview extends Module {
    public ShulkerPreview() {
        super("ShulkerPreview", Category.RENDER, "Show shulker contents when you hover over them");
    }

    public static boolean pinned = false;
    public static int drawX = 0;
    public static int drawY = 0;
    public static NBTTagCompound nbt;
    public static ItemStack itemStack;
    public static boolean active;
    public static int mouseX = 0;
    public static int mouseY = 0;
    public static int guiLeft = 0;
    public static int guiTop = 0;

    public void onUpdate(){
        if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) pinned = false;
    }

}
