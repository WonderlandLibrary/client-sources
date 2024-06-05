package net.minecraftforge.common.property;

public interface IUnlistedProperty {
   String getName();

   boolean isValid(Object var1);

   Class getType();

   String valueToString(Object var1);
}
