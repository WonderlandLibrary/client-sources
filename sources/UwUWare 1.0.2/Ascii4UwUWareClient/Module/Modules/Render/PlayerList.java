
package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventRender2D;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.UI.Font.FontLoaders;
import Ascii4UwUWareClient.Util.Render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.util.List;

public class PlayerList extends Module {
    public static Numbers<Double> red = new Numbers("Red", "Red", 255.0D, 0.0D, 255.0D, 1.0D);
    public static Numbers<Double> green = new Numbers("Green", "Green", 125.0D, 0.0D, 255.0D, 1.0D);
    public static Numbers<Double> blue = new Numbers("Blue", "Blue", 0.0D, 0.0D, 255.0D, 1.0D);
    public static Numbers<Double> alpha = new Numbers("alpha", "alpha", 50.0D, 0.0D, 255.0D, 1.0D);
    float ULX2 = 2;

    public PlayerList() {
        super("Player List", new String[]{"PlayerList"}, ModuleType.Render);
        this.addValues(red, green, blue, alpha);
        this.setRemoved(true);
    }

    @EventHandler
    private void renderHud(EventRender2D event) {
        try{
        float ULY2 = RenderUtil.height() / 6;

        float last = 2;
        List<EntityPlayerSP> PlayerLists = (List) Minecraft.theWorld.getLoadedEntityList();
        for (EntityPlayer Player : PlayerLists) {
            ULY2 += 10;
            String Textx = Player.getName() + " [ " + (int) Player.posX + " , " + (int) Player.posY + " , " + (int) Player.posZ + " ]";
            if (FontLoaders.Comfortaa16.getStringWidth(Textx) + 2 > ULX2) {
                ULX2 = FontLoaders.NovICON34.getStringWidth(Textx) + 2;
            }
            FontLoaders.Comfortaa16.drawStringWithShadow(Textx, last, ULY2, new Color(red.getValue().intValue(), green.getValue().intValue(), blue.getValue().intValue(), alpha.getValue().intValue()).getRGB());
            if (RenderUtil.height() / 100 + 35 * 100 == ULY2) {
                ULY2 = RenderUtil.height() / 3;
                last += ULX2;
                ULX2 = 0;
            }
        }
        }catch (Exception e){}
    }

}

