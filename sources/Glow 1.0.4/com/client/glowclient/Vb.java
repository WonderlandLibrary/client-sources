package com.client.glowclient;

import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.world.gen.structure.template.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.item.*;

public class vb extends Sd
{
    @Override
    public String D() {
        return "schematica.format.structure";
    }
    
    @Override
    public String M() {
        return ".nbt";
    }
    
    @Override
    public boolean M(final NBTTagCompound nbtTagCompound, final J j) {
        final Template template2;
        final Template template = template2 = new Template();
        template2.size = new BlockPos(j.D(), j.M(), j.A());
        template2.setAuthor(j.M());
        final BlockPos origin = BlockPos.ORIGIN;
        final BlockPos size = template.size;
        final int n = -1;
        final Iterator iterator2;
        Iterator<BlockPos> iterator = (Iterator<BlockPos>)(iterator2 = BlockPos.getAllInBox(origin, size.add(n, n, n)).iterator());
        while (iterator.hasNext()) {
            final BlockPos blockPos = iterator2.next();
            final TileEntity m;
            NBTTagCompound i;
            Template template3;
            if ((m = j.M(blockPos)) != null) {
                i = KA.M(m);
                template3 = template;
                final String s = "M";
                final NBTTagCompound nbtTagCompound2 = i;
                final String s2 = "Z";
                i.removeTag("x");
                nbtTagCompound2.removeTag(kb.M(s2));
                nbtTagCompound2.removeTag(Ea.M(s));
            }
            else {
                i = null;
                template3 = template;
            }
            final List blocks = template3.blocks;
            final BlockPos blockPos2 = blockPos;
            blocks.add(new Template$BlockInfo(blockPos2, j.M(blockPos2), i));
            iterator = (Iterator<BlockPos>)iterator2;
        }
        for (final Entity entity : j.D()) {
            try {
                final Vec3d vec3d = new Vec3d(entity.posX, entity.posY, entity.posZ);
                final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
                entity.writeToNBTOptional(nbtTagCompound3);
                template.entities.add(new Template$EntityInfo(vec3d, new BlockPos(vec3d), nbtTagCompound3));
            }
            catch (Throwable t) {
                ld.H.error("Entity {} failed to save, skipping!", (Object)entity, (Object)t);
            }
        }
        template.writeToNBT(nbtTagCompound);
        return true;
    }
    
    public vb() {
        super();
    }
    
    @Override
    public J M(NBTTagCompound nbtTagCompound) {
        final ItemStack m = Pd.M(nbtTagCompound);
        final Template template2;
        final Template template = template2 = new Template();
        template.read(nbtTagCompound);
        nbtTagCompound = (NBTTagCompound)new Id(m, template2.size.getX(), template2.size.getY(), template2.size.getZ(), template2.getAuthor());
        final Iterator iterator = template.blocks.iterator();
    Label_0062:
        while (true) {
            Iterator<Template$BlockInfo> iterator2 = (Iterator<Template$BlockInfo>)iterator;
            while (iterator2.hasNext()) {
                final Template$BlockInfo template$BlockInfo = iterator.next();
                final NBTTagCompound nbtTagCompound2 = nbtTagCompound;
                final BlockPos pos = template$BlockInfo.pos;
                final Template$BlockInfo template$BlockInfo2 = template$BlockInfo;
                ((Id)nbtTagCompound2).M(pos, template$BlockInfo2.blockState);
                if (template$BlockInfo2.tileentityData != null) {
                    try {
                        final Template$BlockInfo template$BlockInfo3 = template$BlockInfo;
                        template$BlockInfo3.tileentityData.setInteger("x", template$BlockInfo.pos.getX());
                        final NBTTagCompound tileentityData = template$BlockInfo3.tileentityData;
                        final String s = "y";
                        final Template$BlockInfo template$BlockInfo4 = template$BlockInfo;
                        tileentityData.setInteger(s, template$BlockInfo4.pos.getY());
                        template$BlockInfo4.tileentityData.setInteger("z", template$BlockInfo.pos.getZ());
                        final TileEntity i;
                        if ((i = KA.M(template$BlockInfo3.tileentityData)) == null) {
                            iterator2 = (Iterator<Template$BlockInfo>)iterator;
                            continue;
                        }
                        ((Id)nbtTagCompound).M(template$BlockInfo.pos, i);
                    }
                    catch (Exception ex) {
                        ld.H.error("TileEntity failed to load properly!", (Throwable)ex);
                    }
                    continue Label_0062;
                }
                iterator2 = (Iterator<Template$BlockInfo>)iterator;
            }
            break;
        }
        return (J)nbtTagCompound;
    }
}
