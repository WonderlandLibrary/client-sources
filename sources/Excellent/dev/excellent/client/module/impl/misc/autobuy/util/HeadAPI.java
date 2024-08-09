package dev.excellent.client.module.impl.misc.autobuy.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.UUID;

public class HeadAPI {

    public static void encodeBase64(ItemStack head, String base64) {
        if (head.getItem() == Items.PLAYER_HEAD) {
            CompoundNBT skullOwner = head.getOrCreateChildTag("SkullOwner");
            
            UUID randomUUID = UUID.randomUUID();
            skullOwner.putUniqueId("Id", randomUUID);

            CompoundNBT propertiesNBT = new CompoundNBT();
            ListNBT texturesNBT = new ListNBT();
            CompoundNBT valueNBT = new CompoundNBT();
            valueNBT.putString("Value", base64);
            texturesNBT.add(valueNBT);
            propertiesNBT.put("textures", texturesNBT);

            skullOwner.put("Properties", propertiesNBT);
        }
    }

}
