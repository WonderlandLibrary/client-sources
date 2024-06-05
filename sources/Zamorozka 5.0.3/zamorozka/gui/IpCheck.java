package zamorozka.gui;

import java.awt.Container;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import javax.swing.UnsupportedLookAndFeelException;
public class IpCheck 
extends GuiScreen
{
  private GuiScreen parentScreen;
  private GuiTextField ipPortTextField;
  public static String strIpPort = "";
  
  public IpCheck(GuiScreen guiscreen)
  {
    this.parentScreen = guiscreen;
  }
  
  public void updateScreen()
  {
    this.ipPortTextField.updateCursorCounter();
  }
  
  public void onGuiClosed()
  {
    Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton guibutton)
  {
    if (!guibutton.enabled) {
      return;
    }
    if (guibutton.id == 1) {
      this.mc.displayGuiScreen(this.parentScreen);
    } else if (guibutton.id == 0) {

    }
    this.mc.displayGuiScreen(this.parentScreen);
  }
  
  protected void keyTyped(char c, int i)
  {
    this.ipPortTextField.textboxKeyTyped(c, i);
    if ((c == '\t') && 
      (this.ipPortTextField.isFocused())) {
      this.ipPortTextField.setFocused(false);
    }
    if (c == '\r') {
      actionPerformed((GuiButton)this.buttonList.get(0));
    }
  }
  
  protected void mouseClicked(int i, int j, int k)
    throws IOException
  {
    super.mouseClicked(i, j, k);
    this.ipPortTextField.mouseClicked(i, j, k);
  }
  
  public void initGui()
  {
    Keyboard.enableRepeatEvents(true);
    this.buttonList.clear();
    this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 75 + 12, "Cancel"));
    this.ipPortTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
  }
  
  public void drawScreen(int i, int j, float f)
  {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, "You Ip check:", this.width / 2, this.height / 4 - 60 + 20, 16777215);
	  try (java.util.Scanner s = new java.util.Scanner(new java.net.URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
		    drawString(this.fontRendererObj, "                  IP:"+s.next(), this.width / 2 - 100, 104, 16777215);
		} catch (java.io.IOException e) {
		    e.printStackTrace();
		}
    super.drawScreen(i, j, f);
  }
}

