package cc.novoline.gui.screen.test;

import cc.novoline.Novoline;
import cc.novoline.gui.NovoGuiScreen;
import cc.novoline.gui.button.FunctionalButton;
import cc.novoline.gui.group2.BasicRoundedGroupWithTitle;
import cc.novoline.gui.label.BasicLabel;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_16;
import cc.novoline.utils.notifications.NotificationType;
import java.util.function.Consumer;
import net.aJi;

public class TestScreen extends NovoGuiScreen {
   protected void onInitialize() {
      aJi.b();
      this.register(new FunctionalButton(new BasicLabel("Test label", -16711936, Fonts$SFBOLD$SFBOLD_16.SFBOLD_16), 10, 10, TestScreen::lambda$onInitialize$0));
      this.register(new BasicRoundedGroupWithTitle(new BasicLabel("", -16711936, Fonts$SFBOLD$SFBOLD_16.SFBOLD_16), 8, 10, 40, 400, 200));
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
   }

   private static void lambda$onInitialize$0(Integer var0) {
      Novoline.getInstance().getNotificationManager().pop("Clicked " + var0, NotificationType.SUCCESS);
   }
}
