package net.minecraft.client.resources;

import com.google.gson.JsonParseException;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.Logger;

public class ResourcePackListEntryDefault extends ResourcePackListEntry
{
  private static final Logger logger = ;
  






  private final IResourcePack field_148320_d = field_148317_a.getResourcePackRepository().rprDefaultResourcePack;
  private final net.minecraft.util.ResourceLocation field_148321_e;
  private static final String __OBFID = "CL_00000822";
  
  public ResourcePackListEntryDefault(GuiScreenResourcePacks p_i45052_1_)
  {
    super(p_i45052_1_);
    
    DynamicTexture var2;
    
    try
    {
      var2 = new DynamicTexture(field_148320_d.getPackImage());
    }
    catch (IOException var4) {
      DynamicTexture var2;
      var2 = net.minecraft.client.renderer.texture.TextureUtil.missingTexture;
    }
    
    field_148321_e = field_148317_a.getTextureManager().getDynamicTextureLocation("texturepackicon", var2);
  }
  
  protected String func_148311_a()
  {
    try
    {
      PackMetadataSection var1 = (PackMetadataSection)field_148320_d.getPackMetadata(field_148317_a.getResourcePackRepository().rprMetadataSerializer, "pack");
      
      if (var1 != null)
      {
        return var1.func_152805_a().getFormattedText();
      }
    }
    catch (JsonParseException var2)
    {
      logger.error("Couldn't load metadata info", var2);
    }
    catch (IOException var3)
    {
      logger.error("Couldn't load metadata info", var3);
    }
    
    return EnumChatFormatting.RED + "Missing " + "pack.mcmeta" + " :(";
  }
  
  protected boolean func_148309_e()
  {
    return false;
  }
  
  protected boolean func_148308_f()
  {
    return false;
  }
  
  protected boolean func_148314_g()
  {
    return false;
  }
  
  protected boolean func_148307_h()
  {
    return false;
  }
  
  protected String func_148312_b()
  {
    return "Default";
  }
  
  protected void func_148313_c()
  {
    field_148317_a.getTextureManager().bindTexture(field_148321_e);
  }
  
  protected boolean func_148310_d()
  {
    return false;
  }
}
