package net.silentclient.client.emotes.emoticons.accessor;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.silentclient.client.emotes.animation.AnimationMeshConfig;
import net.silentclient.client.emotes.bobj.BOBJBone;
import net.silentclient.client.emotes.bobj.BOBJLoader;
import org.joml.Vector4f;

public interface IEmoteAccessor {
   Vector4f calcPosition(BOBJBone var1, float var2, float var3, float var4, float var5);

   AnimationMeshConfig getConfig(String var1);

   BOBJLoader.BOBJData getData();

   void setupMatrix(BOBJBone var1);

   void setItem(ItemStack var1);

   void setItemScale(float var1);

   void setHand(boolean var1);

   void spawnParticle(ParticleType var1, double var2, double var4, double var6, double var8, double var10, double var12);

   void spawnItemParticle(ItemStack var1, double var2, double var4, double var6, double var8, double var10, double var12);

   void renderBlock(ItemStack var1);

   void throwSnowball(double var1, double var3, double var5, double var7, double var9, double var11);

   void createFirework(double var1, double var3, double var5, double var7, double var9, double var11, NBTTagCompound var13);
}
