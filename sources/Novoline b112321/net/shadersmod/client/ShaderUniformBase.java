package net.shadersmod.client;

import net.shadersmod.client.ShaderOption;
import org.lwjgl.opengl.ARBShaderObjects;

public abstract class ShaderUniformBase {
   private String name;
   private int program = -1;
   private int location = -1;

   public ShaderUniformBase(String var1) {
      this.name = var1;
   }

   public void setProgram(int var1) {
      String[] var2 = ShaderOption.p();
      if(this.program != var1) {
         this.program = var1;
         this.location = ARBShaderObjects.glGetUniformLocationARB(var1, this.name);
         this.onProgramChanged();
      }

   }

   protected abstract void onProgramChanged();

   public String getName() {
      return this.name;
   }

   public int getProgram() {
      return this.program;
   }

   public int getLocation() {
      return this.location;
   }

   public boolean d() {
      String[] var1 = ShaderOption.p();
      return this.location >= 0;
   }
}
