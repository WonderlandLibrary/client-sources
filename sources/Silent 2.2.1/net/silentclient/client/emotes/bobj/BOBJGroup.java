package net.silentclient.client.emotes.bobj;

import java.util.ArrayList;
import java.util.List;

public class BOBJGroup {
   public String name;
   public List<BOBJChannel> channels = new ArrayList<>();

   public BOBJGroup(String s) {
      this.name = s;
   }

   public void apply(BOBJBone bobjbone, float f) {
      for (BOBJChannel bobjchannel : this.channels) {
         bobjchannel.apply(bobjbone, f);
      }
   }

   public void applyInterpolate(BOBJBone bobjbone, float f, float f1) {
      for (BOBJChannel bobjchannel : this.channels) {
         bobjchannel.applyInterpolate(bobjbone, f, f1);
      }
   }

   public int getDuration() {
      int i = 0;

      for (BOBJChannel bobjchannel : this.channels) {
         int j = bobjchannel.frames.size();
         if (j > 0) {
            i = Math.max(i, bobjchannel.frames.get(j - 1).frame);
         }
      }

      return i;
   }
}
