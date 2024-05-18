package net.SliceClient.Gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.awt.Color;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

public class GuiSessionManager extends GuiScreen
{
  private GuiScreen parentScreen;
  private GuiTextField sessionField;
  private String status;
  
  public GuiSessionManager(GuiScreen parentScreen)
  {
    this.parentScreen = parentScreen;
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    ScaledResolution scaledRes = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
    mc.getTextureManager().bindTexture(new ResourceLocation("textures/3.jpg"));
    Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), 
      scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), 
      scaledRes.getScaledHeight());
    buttonList.clear();
    int var3 = height / 4 + 48;
    net.SliceClient.Utils.RenderUtil.drawRect(width / 2 - 115, 78, width / 2 + 115, var3 + 72 + 17, ColorUtil.transparency(new Color(0, 0, 0).getRGB(), 0.5D));
    buttonList.add(new GuiButton(7, width / 2 - 100, var3 + 72 + 12 - 50, 200, 20, "Login"));
    buttonList.add(new GuiButton(8, width / 2 - 100, var3 + 72 + 12 - 25, 200, 20, "Back"));
    drawCenteredString(fontRendererObj, "§c" + status, width / 2, 45, 1644825);
    Minecraft.fontRendererObj.drawStringWithShadow("§7SessionID", width / 2 - Minecraft.fontRendererObj.getStringWidth("Username / Email") - 15, 93.0F, -1);
    sessionField.drawTextBox();
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  
  public void initGui()
  {
    sessionField = new GuiTextField(0, Minecraft.fontRendererObj, width / 2 - 100, 106, 200, 20);
    sessionField.setFocused(true);
    sessionField.setMaxStringLength(65);
    status = "Waiting...";
  }
  
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    sessionField.mouseClicked(mouseX, mouseY, mouseButton);
  }
  
  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    sessionField.textboxKeyTyped(typedChar, keyCode);
    if (keyCode == 15) {
      sessionField.setFocused(!sessionField.isFocused());
    }
  }
  
  public void updateScreen()
  {
    sessionField.updateCursorCounter();
  }
  
  protected void actionPerformed(GuiButton button)
    throws IOException
  {
    switch (id)
    {
    case 7: 
      login();
      break;
    case 8: 
      mc.displayGuiScreen(parentScreen);
    }
  }
  
  public void login()
  {
    String input = sessionField.getText();
    if ((input.length() != 65) || (!input.substring(32, 33).equals(":")) || (input.split(":").length != 2))
    {
      status = "Not a session token!";
      sessionField.setText("");
      return;
    }
    String uuid = input.split(":")[1];
    if (uuid.contains("-"))
    {
      status = "Try without dashes!";
      sessionField.setText("");
      return;
    }
    JsonElement rawJson = null;
    try
    {
      rawJson = 
        new JsonParser().parse(new InputStreamReader(new URL("https://api.mojang.com/user/profiles/" + uuid + "/names")
        .openConnection().getInputStream()));
    }
    catch (com.google.gson.JsonIOException|JsonSyntaxException|IOException e)
    {
      e.printStackTrace();
      status = "Mojang servers might be down, or you have an IP-Ban.";
      return;
    }
    if (!rawJson.isJsonArray())
    {
      status = "Fake session!";
      sessionField.setText("");
      return;
    }
    JsonArray json = rawJson.getAsJsonArray();
    String name = json.get(json.size() - 1).getAsJsonObject().get("name").getAsString();
    try
    {
      HttpURLConnection connection = (HttpURLConnection)new URL("https://authserver.mojang.com/validate")
        .openConnection(Proxy.NO_PROXY);
      
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      
      String content = "{\"accessToken\":\"" + input.split(":")[0] + "\"}";
      
      connection.setRequestProperty("Content-Length", String.valueOf(content.getBytes().length));
      connection.setRequestProperty("Content-Language", "en-US");
      connection.setUseCaches(false);
      connection.setDoInput(true);
      connection.setDoOutput(true);
      
      DataOutputStream output = new DataOutputStream(connection.getOutputStream());
      output.writeBytes(content);
      output.flush();
      output.close();
      if (connection.getResponseCode() != 204) {
        throw new IOException();
      }
    }
    catch (IOException e)
    {
      status = "Token doesn't work anymore!";
      sessionField.setText("");
      return;
    }
    mc.session = new Session(name, uuid, input.split(":")[0], "mojang");
    status = ("§aLogged in! -" + name);
    sessionField.setText("");
  }
}
