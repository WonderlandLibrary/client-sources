package net.minecraft.util.datafix.fixes;

import java.util.Random;
import net.minecraft.entity.monster.ZombieType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class ZombieProfToType implements IFixableData
{
    private static final Random field_190049_a = new Random();

    public int getFixVersion()
    {
        return 502;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if ("Zombie".equals(compound.getString("id")) && compound.getBoolean("IsVillager"))
        {
            if (!compound.hasKey("ZombieType", 99))
            {
                ZombieType zombietype = null;

                if (compound.hasKey("VillagerProfession", 99))
                {
                    try
                    {
                        zombietype = ZombieType.func_190146_a(compound.getInteger("VillagerProfession") + 1);
                    }
                    catch (RuntimeException var4)
                    {
                        ;
                    }
                }

                if (zombietype == null)
                {
                    zombietype = ZombieType.func_190146_a(field_190049_a.nextInt(5) + 1);
                }

                compound.setInteger("ZombieType", zombietype.func_190150_a());
            }

            compound.removeTag("IsVillager");
        }

        return compound;
    }
}
