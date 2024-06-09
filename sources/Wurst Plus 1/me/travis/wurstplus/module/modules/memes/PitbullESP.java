package me.travis.wurstplus.module.modules.memes;

import me.travis.wurstplus.event.events.PlayerSkin;
import me.travis.wurstplus.module.Module;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Info(name = "Pitbull ESP", category = Module.Category.MEMES)
public class PitbullESP extends Module {   
    
  private ResourceLocation yourMum;

  public PitbullESP() {
    this.yourMum = new ResourceLocation("textures/pitbull.png");
    
  }
        
  @SubscribeEvent
  public void hasSkin(final PlayerSkin.HasSkin event) {
      if (this.isEnabled()) {
          event.result = true;
      }
  }
  
  @SubscribeEvent
  public void getSkin(final PlayerSkin.GetSkin event) {
      if (this.isEnabled()) {
          event.skinLocation = this.yourMum;
      }
  }

  @Override
  public void onUpdate() {
    if (this.isDisabled()) {
      return;
    }
  }

}