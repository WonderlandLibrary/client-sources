package xyz.vergoclient.ui.guis;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatAllowedCharacters;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.security.ApiResponse;
import xyz.vergoclient.security.HWID;
import xyz.vergoclient.security.account.AccountUtils;
import xyz.vergoclient.ui.fonts.FontUtil;
import xyz.vergoclient.ui.fonts.JelloFontRenderer;
import xyz.vergoclient.util.datas.DataDouble5;
import xyz.vergoclient.util.main.*;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class LogInGui extends GuiScreen {

    public String uidText = "UID";

    public DataDouble5 uidTextBox = new DataDouble5(), loginBox = new DataDouble5(), getHWID = new DataDouble5(), selectedDataDouble5 = null;;

    public boolean isLoggingIn = false;

    public static String loggingInStatus;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        GlStateManager.pushMatrix();

        if (selectedDataDouble5 != uidTextBox && uidText.isEmpty())
            uidText = "UID";

        if(loggingInStatus == null) {
            loggingInStatus = "Awaiting Authentication...";
        }

        DisplayUtils.setCustomTitle("Waiting For Authentication");

        Gui.drawRect(0, 0, width, height, new Color(29, 29, 29).getRGB());

        JelloFontRenderer fr = FontUtil.neurialGroteskBig;

        ScaledResolution sr = new ScaledResolution(mc);

        String authenticateText = "Authentication";

        String logInString = "Login";
        String getHWIDString = "Get HWID";

        fr.drawString(authenticateText, width / 2 - fr.getStringWidth(authenticateText) / 2, 20, -1);

        int loginBoxWidth = 300;
        int loginBoxHeight = 170;

        int loginButtonWidth = 90;
        int loginButtonHeight = 30;

        // Measurements
        uidTextBox.x1 = width / 2 - loginBoxWidth / 3;
        uidTextBox.x2 = width / 2 - loginBoxWidth / 3 + 200;

        uidTextBox.y1 = loginBoxHeight / 1.6f - 2;
        uidTextBox.y2 = loginBoxHeight / 1.6f - 20;

        loginBox.x1 = width / 2 - loginButtonWidth / 2;
        loginBox.x2 = width / 2 - loginButtonWidth / 2 + 90;

        loginBox.y1 = loginBoxHeight / 1.3f;
        loginBox.y2 = loginBoxHeight / 1.3f + 30;

        getHWID.x1 = width / 2 - loginButtonWidth / 2;
        getHWID.x2 = width / 2 - loginButtonWidth / 2 + 90;

        getHWID.y1 = loginBoxHeight / 1f;
        getHWID.y2 = loginBoxHeight / 1f + 30;


        // This is where all the actual buttons, backgrounds, shapes and boxes are drawn.
        // Only change this if you are altering colours or adding things.
        // Please do not change this file too much as one small issue could be devastating towards client protection.

        RenderUtils.drawRoundedRect(width / 2 - loginBoxWidth / 2, 50, loginBoxWidth, loginBoxHeight, 3f, new Color(45, 45, 45));

        Gui.drawRect(width / 2 - loginBoxWidth / 3, loginBoxHeight / 1.6f, width / 2 - loginBoxWidth / 3 + 200, loginBoxHeight / 1.6f - 2, new Color(73, 73, 73).getRGB());
        Gui.drawRect(uidTextBox.x1, uidTextBox.y1, uidTextBox.x2, uidTextBox.y2, new Color(27, 27, 27).getRGB());

        fr.drawString(uidText, uidTextBox.x1 + 2, (float)uidTextBox.y2 + 5, new Color(255, 255, 255, 100).getRGB());

        RenderUtils.drawRoundedRect(width / 2 - loginButtonWidth / 2, loginBoxHeight / 1.3f, loginButtonWidth, loginButtonHeight, 3f, new Color(175, 51, 64));
        fr.drawString(logInString, width / 2 - loginButtonWidth / 5f, loginBoxHeight / 1.22f, -1);

        RenderUtils.drawRoundedRect(getHWID.x1, getHWID.y1, loginButtonWidth, loginButtonHeight, 3f, new Color(15, 208, 108));
        fr.drawString(getHWIDString, width/2 - fr.getStringWidth(getHWIDString)/2, loginBoxHeight / 1f + 10, -1);

        fr.drawString(loggingInStatus, width / 2 - fr.getStringWidth(loggingInStatus) / 2, (float) getHWID.y1 / 2.8f, -1);

        GlStateManager.popMatrix();

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        Keyboard.enableRepeatEvents(true);
        if (selectedDataDouble5 != null) {
            if (keyCode == Keyboard.KEY_V && isCtrlKeyDown()) {
                if (selectedDataDouble5 == uidTextBox)
                    uidText += getClipboardString();
            }else {
                if (keyCode == Keyboard.KEY_BACK) {

                    if (selectedDataDouble5 == uidTextBox) {
                        if (uidText.isEmpty()) {

                        }
                        else if (uidText.length() > 0) {
                            uidText = uidText.substring(0, uidText.length() - 1);
                        }
                    }

                }
                else if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {

                    if (selectedDataDouble5 == uidTextBox)
                        uidText += Character.toString(typedChar);

                }

            }

        }

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        if (Mouse.isInsideWindow() && mouseButton == 0) {

            if (GuiUtils.isMouseOverDataDouble5(mouseX, mouseY, loginBox) && !isLoggingIn) {
                if(uidText == "UID" || uidText.isEmpty()) {
                    loggingInStatus = "Please enter a UID.";
                }
                new Thread(() -> {
                    try {
                        isLoggingIn = true;

                        String response1 = NetworkManager.getNetworkManager().sendPost(new HttpPost("https://vergoclient.xyz/api/verCheck.php"));
                        if(!response1.equals(Vergo.version)) {

                            // 'Pings' the web server to get a response code.
                            URL url = new URL("https://vergoclient.xyz/api/verCheck.php");
                            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                            connection.connect();
                            int httpStatusCode = connection.getResponseCode();

                            // If response code is 521 then the webserver is down, abort everything.
                            if(httpStatusCode == 521) {

                                loggingInStatus = "Vergo is down. Check log for more information.";
                                System.out.println("The Vergo website is currently down or unreachable. Make sure you have an internet connection.");
                                System.out.println("If you are sure that you do, check https://vergoclient.xyz | If it is down, check Discord for information.");
                                System.out.println("If nothing has been said on Discord, please report it to one of Vergo's developers.");
                                System.out.println("We apologise for the any inconvenience caused.");

                                connection.disconnect();

                                Thread.sleep(5000);
                                mc.shutdown();
                                return;
                            } else if (httpStatusCode == 200) {

                                // Else, print outdated version since everything else is working accordingly.
                                loggingInStatus = "Outdated Version!";

                                connection.disconnect();

                                Thread.sleep(2500);
                                //mc.shutdown();
                                return;

                            } else {
                                loggingInStatus = "Error! Check logs.";
                                System.out.println("Something has gone wrong on our end.");
                                System.out.println("Please try and access https://vergoclient.xyz | If it is down, check Discord for information");
                                System.out.println("If nothing has been said on Discord, please report it to one of Vergo's developers.");
                                System.out.println("We apologise for the any inconvenience caused.");

                                connection.disconnect();

                                Thread.sleep(2500);
                                mc.shutdown();
                                return;
                            }

                        }

                        loggingInStatus = "Logging in...";
                        System.out.println("Logging in...");
                        String response = NetworkManager.getNetworkManager().sendPost(new HttpPost("https://vergoclient.xyz/api/authentication.php?usersIdentificationNumber=" + uidText + "&hardwareIDCheckValue=" + Base64.getEncoder().encodeToString(HWID.getHWIDForWindows().getBytes())));
                        ApiResponse apiResponse = MiscellaneousUtils.parseApiResponse(response);
                        if(apiResponse.status == ApiResponse.ResponseStatus.FORBIDDEN) {
                            loggingInStatus = "Account access disallowed.";
                            Thread.sleep(2500);
                            mc.shutdown();
                            return;
                        } else
                        if (apiResponse.status == ApiResponse.ResponseStatus.OK) {
                            AccountUtils.account = MiscellaneousUtils.parseAccount(new JSONObject(response).toString());
                            new Thread(() -> {
                                while (true) {
                                    try {
                                        ApiResponse apiResponse1 = MiscellaneousUtils.parseApiResponse(NetworkManager.getNetworkManager().sendPost(new HttpPost("https://vergoclient.xyz/api/authentication.php"), new BasicNameValuePair("uid", AccountUtils.account.uid + ""), new BasicNameValuePair("username", AccountUtils.account.username), new BasicNameValuePair("hwid", AccountUtils.account.hwid), new BasicNameValuePair("banned", AccountUtils.account.banned + "")));
                                        if (apiResponse1.status == ApiResponse.ResponseStatus.OK) {
                                            break;
                                        }
                                        Thread.sleep(10000);
                                    } catch (Exception e) {

                                    }
                                }
                            }).start();
                            loggingInStatus = "Logged in!";
                            Vergo.protTime();
                            Vergo.verProtTime();
                            Thread.sleep(1000);
                            GuiStart.hasLoaded = true;
                            Keyboard.enableRepeatEvents(false);
                            mc.displayGuiScreen(new GuiStart());
                        } else {
                            System.out.println("Vergo cannot authenticate you right now. Seek support in the Discord.");
                            loggingInStatus = "Auth Failed! Read logs.";
                            Thread.sleep(1000);
                            GuiStart.hasLoaded = false;
                            return;
                        }
                    } catch (Exception e) {
                        loggingInStatus = "Login Failed!";
                        e.printStackTrace();
                    }
                    isLoggingIn = false;
                }).start();
            }

            else if (GuiUtils.isMouseOverDataDouble5(mouseX, mouseY, uidTextBox)) {
                selectedDataDouble5 = uidTextBox;
                if (uidText.equals("UID")) {
                    uidText = "";
                }
            }

            else if (GuiUtils.isMouseOverDataDouble5(mouseX, mouseY, getHWID)) {
                selectedDataDouble5 = getHWID;
                try {
                    setClipboardString(HWID.getHWIDForWindows());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }

            else {
                selectedDataDouble5 = null;
            }
        }

    }

    // If Vergo's website is NOT reachable, try to send a ping. If it returns false, the website is dead and Vergo should cease to function
    // This is simply a safety procedure.
    public static boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }

}
