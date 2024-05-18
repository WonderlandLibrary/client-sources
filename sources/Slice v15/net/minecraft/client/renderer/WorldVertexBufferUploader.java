package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.util.Iterator;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import org.lwjgl.opengl.GL11;

public class WorldVertexBufferUploader
{
  private static final String __OBFID = "CL_00002567";
  
  public WorldVertexBufferUploader() {}
  
  public int draw(WorldRenderer p_178177_1_, int p_178177_2_)
  {
    if (p_178177_2_ > 0)
    {
      net.minecraft.client.renderer.vertex.VertexFormat var3 = p_178177_1_.func_178973_g();
      int var4 = var3.func_177338_f();
      ByteBuffer var5 = p_178177_1_.func_178966_f();
      java.util.List var6 = var3.func_177343_g();
      Iterator var7 = var6.iterator();
      



      while (var7.hasNext())
      {
        VertexFormatElement var8 = (VertexFormatElement)var7.next();
        net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUseage var9 = var8.func_177375_c();
        int var10 = var8.func_177367_b().func_177397_c();
        int var11 = var8.func_177369_e();
        
        switch (SwitchEnumUseage.field_178958_a[var9.ordinal()])
        {
        case 1: 
          var5.position(var8.func_177373_a());
          GL11.glVertexPointer(var8.func_177370_d(), var10, var4, var5);
          GL11.glEnableClientState(32884);
          break;
        
        case 2: 
          var5.position(var8.func_177373_a());
          OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var11);
          GL11.glTexCoordPointer(var8.func_177370_d(), var10, var4, var5);
          GL11.glEnableClientState(32888);
          OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
          break;
        
        case 3: 
          var5.position(var8.func_177373_a());
          GL11.glColorPointer(var8.func_177370_d(), var10, var4, var5);
          GL11.glEnableClientState(32886);
          break;
        
        case 4: 
          var5.position(var8.func_177373_a());
          GL11.glNormalPointer(var10, var4, var5);
          GL11.glEnableClientState(32885);
        }
        
      }
      GL11.glDrawArrays(p_178177_1_.getDrawMode(), 0, p_178177_1_.func_178989_h());
      var7 = var6.iterator();
      
      while (var7.hasNext())
      {
        VertexFormatElement var8 = (VertexFormatElement)var7.next();
        net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUseage var9 = var8.func_177375_c();
        int var10 = var8.func_177369_e();
        
        switch (SwitchEnumUseage.field_178958_a[var9.ordinal()])
        {
        case 1: 
          GL11.glDisableClientState(32884);
          break;
        
        case 2: 
          OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var10);
          GL11.glDisableClientState(32888);
          OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
          break;
        
        case 3: 
          GL11.glDisableClientState(32886);
          GlStateManager.func_179117_G();
          break;
        
        case 4: 
          GL11.glDisableClientState(32885);
        }
        
      }
    }
    p_178177_1_.reset();
    return p_178177_2_;
  }
  
  static final class SwitchEnumUseage
  {
    static final int[] field_178958_a = new int[net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUseage.values().length];
    private static final String __OBFID = "CL_00002566";
    
    static
    {
      try
      {
        field_178958_a[net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError1) {}
      



      try
      {
        field_178958_a[net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUseage.UV.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError2) {}
      



      try
      {
        field_178958_a[net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUseage.COLOR.ordinal()] = 3;
      }
      catch (NoSuchFieldError localNoSuchFieldError3) {}
      



      try
      {
        field_178958_a[net.minecraft.client.renderer.vertex.VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
      }
      catch (NoSuchFieldError localNoSuchFieldError4) {}
    }
    
    SwitchEnumUseage() {}
  }
}
