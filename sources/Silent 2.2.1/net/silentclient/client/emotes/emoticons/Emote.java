package net.silentclient.client.emotes.emoticons;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.bobj.BOBJBone;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Emote {
   public final String id;
   public final String title;
   public int looping = 1;
   public Icon icon = Icon.DEFAULT;
   public Random rand = new Random();
   public List<String> armatures = new ArrayList<>();

   public Emote(String s, String s1) {
      this.id = s;
      this.title = s1;
   }

   public Emote looping(int i) {
      this.looping = i;
      return this;
   }

   public Emote armatures(String... astring) {
      Collections.addAll(this.armatures, astring);
      return this;
   }

   public void progressAnimation(IEmoteAccessor var1, BOBJArmature var2, int var3, float var4) {
   }

   public void startAnimation(IEmoteAccessor iemoteaccessor) {
      for (String s : this.armatures) {
         iemoteaccessor.getData().armatures.get(s).enabled = true;
      }
   }

   public void stopAnimation(IEmoteAccessor iemoteaccessor) {
      for (String s : this.armatures) {
         iemoteaccessor.getData().armatures.get(s).enabled = false;
      }
   }

   public Emote getDynamicEmote() {
      return this;
   }

   public Emote getDynamicEmote(String var1) {
      return this;
   }

   public Emote set(String var1) {
      return this;
   }

   public String getKey() {
      return this.id;
   }

   public String getActionName() {
      return "emote_" + this.id;
   }

   public void spawnParticle(IEmoteAccessor iemoteaccessor, ParticleType particletype, double d0, double d1, double d2, float f) {
      double d3 = this.rand.nextDouble() * (double) f * 2.0 - (double) f;
      double d4 = this.rand.nextDouble() * (double) f * 2.0 - (double) f;
      double d5 = this.rand.nextDouble() * (double) f * 2.0 - (double) f;
      iemoteaccessor.spawnParticle(particletype, d0, d1, d2, d3, d4, d5);
   }

   public Vector4f direction(IEmoteAccessor iemoteaccessor, BOBJBone bobjbone, float f) {
      return this.direction(iemoteaccessor, bobjbone, 0.0F, 0.1F, 0.0F, f);
   }

   public Vector4f direction(IEmoteAccessor iemoteaccessor, BOBJBone bobjbone, float f, float f1, float f2, float f3) {
      Vector4f vector4f = iemoteaccessor.calcPosition(bobjbone, 0.0F, 0.0F, 0.0F, f3);
      float f4 = vector4f.x;
      float f5 = vector4f.y;
      float f6 = vector4f.z;
      vector4f = iemoteaccessor.calcPosition(bobjbone, f, f1, f2, f3);
      vector4f.set(vector4f.x - f4, vector4f.y - f5, vector4f.z - f6, vector4f.w);
      return vector4f;
   }

   public Vector4f position(IEmoteAccessor iemoteaccessor, BOBJArmature bobjarmature, String s, float f, float f1, float f2, float f3) {
      return iemoteaccessor.calcPosition(bobjarmature.bones.get(s), f, f1, f2, f3);
   }

   public float rand(float f) {
      return this.rand.nextFloat() * f - f / 2.0F;
   }

   protected int tick(int i) {
      return (int) ((float) i / 30.0F * 20.0F);
   }
}
