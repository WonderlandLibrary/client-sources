package de.labystudio.utils;

import de.labystudio.labymod.ConfigManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;

public class LeftHand
{
    public static boolean use(Entity entity)
    {
        boolean flag = ConfigManager.settings.leftHand;

        if (entity == null)
        {
            return flag;
        }
        else if (!ConfigManager.settings.leftBow)
        {
            return flag;
        }
        else if (!(entity instanceof AbstractClientPlayer))
        {
            return flag;
        }
        else
        {
            AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entity;
            return abstractclientplayer.inventory == null ? flag : (abstractclientplayer.inventory.getCurrentItem() == null ? flag : (abstractclientplayer.inventory.getCurrentItem().getItem() == null ? flag : (abstractclientplayer.inventory.getCurrentItem().getItem() instanceof ItemBow ? !flag : flag)));
        }
    }

    public static boolean use(ItemStack itemToRender)
    {
        boolean flag = ConfigManager.settings.leftHand;
        return itemToRender != null && itemToRender.getItem() != null && itemToRender.getItem() instanceof ItemMap ? false : (!ConfigManager.settings.leftBow ? flag : (itemToRender == null ? flag : (itemToRender.getItem() == null ? flag : (itemToRender.getItem() instanceof ItemBow ? !flag : flag))));
    }
}
