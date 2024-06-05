package Ascii4UwUWareClient.API.Auth;

import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Util.MainMenuUtil;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class GuiAuth extends GuiScreen {

    public boolean login = false;

    public void initGui() {
        if (!login){
            try {
                /*if (Auth.authManualHWID(Hwid.getHWID()) && Auth.loggedIn){
                    Client.instance.startClient();

                    login = true;
                }else {
                    login = false;
                }*/
            }catch (Exception e){

            }

        }
        super.initGui();
    }
    boolean full;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
       /* keyPressed();

        this.drawDefaultBackground();
        if (!full){
            mc.toggleFullscreen();
            full = true;
        }

        MainMenuUtil.drawString(Auth.getStatus(), width/2 - this.fontRendererObj.getStringWidth(Client.instance.name)/10, height/3, new Color(0, 224, 255, 0).getRGB());
        //try {
       //     if (!Auth.authManualHWID(Hwid.getHWID()) && !Auth.loggedIn){
      ///          try {
     //               MainMenuUtil.drawString("Your HWID is: " + Hwid.getHWID(), width/2 - this.fontRendererObj.getStringWidth(Client.instance.name)/10, height/2, new Color(255, 0, 0, 0).getRGB());
      ////              System.out.println("Your HWID is: " + Hwid.getHWID());
      //         }catch (Exception e){
//
    //           }
            }
     //   } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
    //        e.printStackTrace();
  //      }*/
        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    public void keyPressed() {
        if (Keyboard.isKeyDown(64) || Keyboard.isKeyDown(1)) {
            this.mc.shutdown();
        }
    }

}
