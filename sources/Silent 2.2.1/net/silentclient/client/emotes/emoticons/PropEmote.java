package net.silentclient.client.emotes.emoticons;

import net.silentclient.client.emotes.animation.AnimationMeshConfig;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PropEmote extends Emote {
   public List<String> props = new ArrayList<>();

   public PropEmote(String s, String s1) {
      super(s, s1);
   }

   public PropEmote props(String... astring) {
      Collections.addAll(this.props, astring);
      return this;
   }

   @Override
   public void startAnimation(IEmoteAccessor iemoteaccessor) {
      super.startAnimation(iemoteaccessor);
      this.setVisible(iemoteaccessor, true);
   }

   @Override
   public void stopAnimation(IEmoteAccessor iemoteaccessor) {
      super.stopAnimation(iemoteaccessor);
      this.setVisible(iemoteaccessor, false);
   }

   public void setVisible(IEmoteAccessor iemoteaccessor, boolean flag) {
      for (String s : this.props) {
         AnimationMeshConfig animationmeshconfig = iemoteaccessor.getConfig(s);
         if (animationmeshconfig != null) {
            animationmeshconfig.visible = flag;
         }
      }
   }
}
