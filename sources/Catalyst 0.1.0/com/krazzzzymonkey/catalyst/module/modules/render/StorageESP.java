package com.krazzzzymonkey.catalyst.module.modules.render;

import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntityEnderChest;
import com.krazzzzymonkey.catalyst.utils.visual.RenderUtils;
import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.TileEntity;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import net.minecraft.tileentity.TileEntityChest;
import java.util.ArrayDeque;
import com.krazzzzymonkey.catalyst.module.Modules;

public class StorageESP extends Modules
{
    public boolean shouldInform;
    private ArrayDeque<TileEntityChest> emptyChests;
    private int maxChests;
    private ArrayDeque<TileEntityChest> nonEmptyChests;

    
    @Override
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        int int1 = 0;
        int int2 = 0;
        while (int2 < Wrapper.INSTANCE.world().loadedTileEntityList.size()) {
            final TileEntity tileEntity = Wrapper.INSTANCE.world().loadedTileEntityList.get(int2);
            if (int1 >= this.maxChests) {
                break;
            }
            else {
                if (tileEntity instanceof TileEntityChest) {
                    ++int1;
                    final TileEntityChest chest = (TileEntityChest)tileEntity;
                    boolean n;
                    if (chest.getChestType() == BlockChest.Type.TRAP) {
                        n = true;
                    }
                    else {
                        n = false;
                    }
                    final boolean bool1 = n;
                    if (this.emptyChests.contains(tileEntity)) {
                        RenderUtils.drawBlockESP(chest.getPos(), 0.25f, 0.25f, 0.25f);
                    }
                    else if (this.nonEmptyChests.contains(tileEntity)) {
                        if (bool1) {
                            RenderUtils.drawBlockESP(chest.getPos(), 0.0f, 1.0f, 0.0f);
                        }
                        else {
                            RenderUtils.drawBlockESP(chest.getPos(), 1.0f, 0.0f, 0.0f);
                        }
                    }
                    else if (bool1) {
                        RenderUtils.drawBlockESP(chest.getPos(), 0.0f, 1.0f, 0.0f);
                    }
                    else {
                        RenderUtils.drawBlockESP(chest.getPos(), 1.0f, 0.0f, 0.0f);
                    }
                    if (bool1) {
                        RenderUtils.drawBlockESP(chest.getPos(), 0.0f, 1.0f, 0.0f);
                    }
                    else {
                        RenderUtils.drawBlockESP(chest.getPos(), 1.0f, 0.0f, 0.0f);
                    }
                }
                else if (tileEntity instanceof TileEntityEnderChest) {
                    ++int1;
                    RenderUtils.drawBlockESP(((TileEntityEnderChest)tileEntity).getPos(), 1.0f, 0.0f, 1.0f);
                }
                ++int2;
                continue;
            }
        }
        int int3 = 0;
        while (int3 < Wrapper.INSTANCE.world().loadedEntityList.size()) {
            final Entity entity = Wrapper.INSTANCE.world().loadedEntityList.get(int3);
            if (int1 >= this.maxChests) {
                break;
            }
            else {
                if (entity instanceof EntityMinecartChest) {
                    ++int1;
                    RenderUtils.drawBlockESP(((EntityMinecartChest)entity).getPosition(), 1.0f, 1.0f, 1.0f);
                }
                ++int3;
                continue;
            }
        }
        if ((int1v >= this.maxChests) && (this.shouldInform)) {
            ChatUtils.warning(String.valueOf(new StringBuilder().append("To prevent lag, it will only show the first ").append(this.maxChests).append(" chests.")));
            this.shouldInform = false;
        }
        else if (int1 < this.maxChests) {
            this.shouldInform = true;
        }
        super.onRenderWorldLast(event);
    }
    
    @Override
    public void onEnable() {
        this.shouldInform = (1 != 0);
        this.emptyChests.clear();
        this.nonEmptyChests.clear();
        super.onEnable();
    }
    
    public StorageESP() {
        super("StorageESP", ModuleCategory.RENDER);
        this.maxChests = 1000;
        this.shouldInform = true;
        this.emptyChests = new ArrayDeque<TileEntityChest>();
        this.nonEmptyChests = new ArrayDeque<TileEntityChest>();
    }

}