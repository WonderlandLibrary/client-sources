package net.augustus.modules.render;

import java.awt.Color;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;

public class BlockAnimation extends Module {
   public final StringValue animation = new StringValue(69, "Animation", this, "1.7", new String[] {"None", "1.7", "Exhibition", "Slide", "Slide2", "Slide3", "Slide4", "Spin", "Spin2", "Spin3", "Leaked"});
   public final DoubleValue height = new DoubleValue(2, "Height", this, 0.0, -0.5, 1.5, 2);
   public final BooleanValue fakeBlock = new BooleanValue(3, "FakeBlock", this, true);
   public final DoubleValue speed = new DoubleValue(4, "Speed", this, 1.0, 0.1, 3.0, 1);

   public BlockAnimation() {
      super("BlockAnimation", new Color(136, 29, 163), Categorys.RENDER);
   }
}
