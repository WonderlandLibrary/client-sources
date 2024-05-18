package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import net.SliceClient.Slice;
import net.SliceClient.module.Module;
import net.SliceClient.module.ModuleManager;
import net.SliceClient.modules.BetterBow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public abstract class AbstractClientPlayer extends EntityPlayer
{
  private NetworkPlayerInfo field_175157_a;
  private ResourceLocation ofLocationCape = null;
  private static final String __OBFID = "CL_00000935";
  
  public AbstractClientPlayer(World worldIn, GameProfile p_i45074_2_)
  {
    super(worldIn, p_i45074_2_);
    String username = p_i45074_2_.getName();
    downloadCape(username);
  }
  
  public boolean func_175149_v()
  {
    Minecraft.getMinecraft();NetworkPlayerInfo var1 = Minecraft.getNetHandler().func_175102_a(getGameProfile().getId());
    Slice.getTrap();return Slice.getState("Freecam");
  }
  
  public boolean hasCape()
  {
    return func_175155_b() != null;
  }
  
  protected NetworkPlayerInfo func_175155_b()
  {
    if (field_175157_a == null)
    {
      Minecraft.getMinecraft();field_175157_a = Minecraft.getNetHandler().func_175102_a(getUniqueID());
    }
    
    return field_175157_a;
  }
  
  public boolean hasSkin()
  {
    NetworkPlayerInfo var1 = func_175155_b();
    return (var1 != null) && (var1.func_178856_e());
  }
  
  public ResourceLocation getLocationSkin()
  {
    NetworkPlayerInfo var1 = func_175155_b();
    return var1 == null ? DefaultPlayerSkin.func_177334_a(getUniqueID()) : var1.func_178837_g();
  }
  
  public ResourceLocation getLocationCape()
  {
    if (!Config.isShowCapes())
    {
      return null;
    }
    if (ofLocationCape != null)
    {
      return ofLocationCape;
    }
    

    NetworkPlayerInfo var1 = func_175155_b();
    return var1 == null ? null : var1.func_178861_h();
  }
  

  public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username)
  {
    TextureManager var2 = Minecraft.getMinecraft().getTextureManager();
    Object var3 = var2.getTexture(resourceLocationIn);
    
    if (var3 == null)
    {
      var3 = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] { StringUtils.stripControlCodes(username) }), DefaultPlayerSkin.func_177334_a(func_175147_b(username)), new ImageBufferDownload());
      var2.loadTexture(resourceLocationIn, (ITextureObject)var3);
    }
    
    return (ThreadDownloadImageData)var3;
  }
  
  public static ResourceLocation getLocationSkin(String username)
  {
    return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
  }
  
  public String func_175154_l()
  {
    NetworkPlayerInfo var1 = func_175155_b();
    return var1 == null ? DefaultPlayerSkin.func_177332_b(getUniqueID()) : var1.func_178851_f();
  }
  
  public float func_175156_o()
  {
    float var1 = 1.0F;
    
    if (capabilities.isFlying)
    {
      var1 *= 1.0F;
    }
    
    IAttributeInstance var2 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
    var1 = (float)(var1 * ((var2.getAttributeValue() / capabilities.getWalkSpeed() + 1.0D) / 2.0D));
    
    if ((capabilities.getWalkSpeed() == 0.0F) || (Float.isNaN(var1)) || (Float.isInfinite(var1)))
    {
      var1 = 1.0F;
    }
    
    if ((isUsingItem()) && (getItemInUse().getItem() == Items.bow))
    {
      int var3 = getItemInUseDuration();
      float var4 = var3 / 20.0F;
      
      if (var4 > 1.0F)
      {
        if (!ModuleManager.getModule(BetterBow.class).isEnabled()) {
          var4 = 1.0F;
        } else {
          var4 = 4.0F;
        }
        
      }
      else {
        var4 *= var4;
      }
      
      var1 *= (1.0F - var4 * 0.15F);
    }
    
    return var1;
  }
  
  private void downloadCape(String username)
  {
    if ((username != null) && (!username.isEmpty()))
    {
      username = StringUtils.stripControlCodes(username);
      boolean custom = getMinecraftsession.getUsername().equals(StringUtils.stripControlCodes(username));
      String ofCapeUrl;
      String ofCapeUrl; if (custom) {
        ofCapeUrl = "http://s.optifine.net/capes/Fazon.png";
      } else {
        ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
      }
      MinecraftProfileTexture mpt = new MinecraftProfileTexture(ofCapeUrl, new HashMap());
      final ResourceLocation rl = new ResourceLocation("skins/" + mpt.getHash());
      IImageBuffer iib = new IImageBuffer()
      {
        ImageBufferDownload ibd = new ImageBufferDownload();
        
        public BufferedImage parseUserSkin(BufferedImage var1) {
          return AbstractClientPlayer.this.parseCape(var1);
        }
        
        public void func_152634_a() {
          ofLocationCape = rl;
        }
      };
      ThreadDownloadImageData textureCape = new ThreadDownloadImageData(null, mpt.getUrl(), null, iib);
      Minecraft.getMinecraft().getTextureManager().loadTexture(rl, textureCape);
    }
  }
  

  private BufferedImage parseCape(BufferedImage img)
  {
    int imageWidth = 64;
    int imageHeight = 32;
    int srcWidth = img.getWidth();
    
    for (int srcHeight = img.getHeight(); (imageWidth < srcWidth) || (imageHeight < srcHeight); imageHeight *= 2)
    {
      imageWidth *= 2;
    }
    
    BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
    Graphics g = imgNew.getGraphics();
    g.drawImage(img, 0, 0, null);
    g.dispose();
    return imgNew;
  }
}
