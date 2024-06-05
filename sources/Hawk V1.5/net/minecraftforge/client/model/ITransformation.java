package net.minecraftforge.client.model;

import javax.vecmath.Matrix4f;
import net.minecraft.util.EnumFacing;

public interface ITransformation {
   int rotate(EnumFacing var1, int var2);

   EnumFacing rotate(EnumFacing var1);

   Matrix4f getMatrix();
}
