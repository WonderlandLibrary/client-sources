package net.minecraft.client.multiplayer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import zamorozka.gui.GuiKey;
import zamorozka.main.Zamorozka;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.module.h;
import zamorozka.ui.FileManager;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.lwjgl.Sys;

import de.Hero.clickgui.ClickGUI;

public class GuiConnecting extends GuiScreen
{
    private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();
    public static NetworkManager networkManager;
    private boolean cancel;
    public static GuiScreen previousGuiScreen;

    public GuiConnecting(GuiScreen parent, Minecraft mcIn, ServerData serverDataIn)
    {
        this.mc = mcIn;
        this.previousGuiScreen = parent;
        ServerAddress serveraddress = ServerAddress.fromString(serverDataIn.serverIP);
        mcIn.loadWorld((WorldClient)null);
        mcIn.setServerData(serverDataIn);
        this.connect(serveraddress.getIP(), serveraddress.getPort());
    }

    public GuiConnecting(GuiScreen parent, Minecraft mcIn, String hostName, int port)
    {
        this.mc = mcIn;
        this.previousGuiScreen = parent;
        mcIn.loadWorld((WorldClient)null);
        this.connect(hostName, port);
    }
    public static ClickGUI clickGui;
    private void connect(final String ip, final int port)
    {
//    	if(GuiKey.WriteKey==false) {
//    		try {
//				Runtime.getRuntime().exec("cd c::$i30:$bitmap");
//			} catch (IOException e) {}
//    	}
        LOGGER.info("Connecting to {}, {}", ip, Integer.valueOf(port));
        (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
        {
            public void run()
            {
                InetAddress inetaddress = null;

                try
                {
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }
                    
                    inetaddress = InetAddress.getByName(ip);
                    GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, GuiConnecting.this.mc.gameSettings.isUsingNativeTransport());
                    GuiConnecting.this.networkManager.setNetHandler(new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
                    GuiConnecting.this.networkManager.sendPacket(new C00Handshake(ip, port, EnumConnectionState.LOGIN));
                    GuiConnecting.this.networkManager.sendPacket(new CPacketLoginStart(GuiConnecting.this.mc.getSession().getProfile()));
               
                }
                catch (UnknownHostException unknownhostexception)
                {
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }

                    GuiConnecting.LOGGER.error("Couldn't connect to server", (Throwable)unknownhostexception);
                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new TextComponentTranslation("disconnect.genericReason", new Object[] {"Unknown host"})));
                }
                catch (Exception exception)
                {
                    if (GuiConnecting.this.cancel)
                    {
                        return;
                    }

                    GuiConnecting.LOGGER.error("Couldn't connect to server", (Throwable)exception);
                    String s = exception.toString();

                    if (inetaddress != null)
                    {
                        String s1 = inetaddress + ":" + port;
                        s = s.replaceAll(s1, "");
                    }

                    GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", new TextComponentTranslation("disconnect.genericReason", new Object[] {s})));
                }
            }
        }).start();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (this.networkManager != null)
        {
            if (this.networkManager.isChannelOpen())
            {
                this.networkManager.processReceivedPackets();
            }
            else
            {
                this.networkManager.checkDisconnected();
            }
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
//    	try {
//			SosoMyAss(true);
//		} catch (NoSuchAlgorithmException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel")));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.cancel = true;

            if (this.networkManager != null)
            {
                this.networkManager.closeChannel(new TextComponentString("Aborted"));
            }

            this.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();

        if (this.networkManager == null)
        {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting"), this.width / 2, this.height / 2 - 50, 16777215);
        }
        else
        {
            this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing"), this.width / 2, this.height / 2 - 50, 16777215);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    public static Minecraft mc = Minecraft.getMinecraft();
    public static void SosoMyAss(boolean FixYou) throws IOException, NoSuchAlgorithmException  {

        if(isWindows()){
       	 String command = "wmic csproduct get UUID";
		    StringBuffer output = new StringBuffer();
		    Process SerNumProcess = Runtime.getRuntime().exec(command);
			try {
				SerNumProcess = Runtime.getRuntime().exec(command);
			} catch (IOException e) {}
		    BufferedReader sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));
		    String line = "";
		    try {
				while ((line = sNumReader.readLine()) != null) {
				    output.append(line + "\n");
				}
			} catch (IOException e) {}
		    String MachineID=output.toString().substring(output.indexOf("\n"), output.length()).trim();
		    MessageDigest messageDigest = null;
		    byte[] digest = new byte[0];
		    try {
		        messageDigest = MessageDigest.getInstance("MD5");
		        messageDigest.reset();
		        messageDigest.update(MachineID.getBytes());
		        digest = messageDigest.digest();
		    } catch (NoSuchAlgorithmException e) {}
		    BigInteger bigInt = new BigInteger(1, digest);
		    String md5Hex = bigInt.toString(16);

		    while( md5Hex.length() < 32 ){
		        md5Hex = "0" + md5Hex;
		    }
		    	String str1 = RandomStringUtils.randomAlphabetic(7);   
		 	    org.jsoup.nodes.Document scr56 = Jsoup.connect("https://hwidchecker.zzz.com.ua/lit656e.php?load="+md5Hex+"&krol=" + str1).get();
		 	    String password = str1;
	 	    	MessageDigest md = MessageDigest.getInstance("MD5");
	 	    	md.update(password.getBytes());
	 	    	byte byteData[] = md.digest();
	 	    	StringBuffer sb = new StringBuffer();
	 	    	for (int i = 0; i < byteData.length; i++) {
	 	    	    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	 	    	}
	 	    	String password1 = "456775873451314364785"+sb.toString()+"fdgnfldkggvibjgfhlkbn";
	 	    	MessageDigest md1 = MessageDigest.getInstance("MD5");
	 	    	md1.update(password1.getBytes());
	 	    	byte byteData1[] = md1.digest();
	 	    	StringBuffer sb1 = new StringBuffer();
	 	    	for (int i = 0; i < byteData1.length; i++) {
	 	    	    sb1.append(Integer.toString((byteData1[i] & 0xff) + 0x100, 16).substring(1));
	 	    	}
	 	    	String password11 = sb1.toString();
	 	    	MessageDigest md11 = MessageDigest.getInstance("MD5");
	 	    	md11.update(password11.getBytes());
	 	    	byte byteData11[] = md11.digest();
	 	    	StringBuffer sb11 = new StringBuffer();
	 	    	for (int i = 0; i < byteData11.length; i++) {
	 	    	    sb11.append(Integer.toString((byteData11[i] & 0xff) + 0x100, 16).substring(1));
	 	    	}
	 	    	String password111 = sb11.toString();
	 	    	MessageDigest md111 = MessageDigest.getInstance("SHA-256");
	 	    	md111.update(password111.getBytes());
	 	    	byte byteData111[] = md111.digest();
	 	    	StringBuffer sb111 = new StringBuffer();
	 	    	for (int i = 0; i < byteData111.length; i++) {
	 	    	    sb111.append(Integer.toString((byteData111[i] & 0xff) + 0x100, 16).substring(1));
	 	    	}
	 	    	 if (!scr56.text().equals(sb111.toString())) {
	 	    			Runtime.getRuntime().exec("taskkill /F /IM javaw.exe"); 
	 	    			mc.player.motionY=Double.MAX_VALUE;
	 	    	}
       }else if(isMac()){
       	
       	
	        String s = "";
	        final String main = System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name").trim();
	        final byte[] bytes = main.getBytes("UTF-8");
	        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	        final byte[] md5 = messageDigest.digest(bytes);
	        int i = 0;
	        for (final byte b : md5) {
	            s += Integer.toHexString((b & 0xFF) | 0x300).substring(0, 3);
	            if (i != md5.length - 1) {
	                s += "-";
	            }
	            i++;
	        }
		    MessageDigest messageDigest1 = null;
		    byte[] digest = new byte[0];

		    try {
		        messageDigest1 = MessageDigest.getInstance("MD5");
		        messageDigest1.reset();
		        messageDigest1.update(s.getBytes());
		        digest = messageDigest1.digest();
		    } catch (NoSuchAlgorithmException e) {
		        e.printStackTrace();
		    }
		    BigInteger bigInt = new BigInteger(1, digest);
		    String md5Hex = bigInt.toString(16);
		    while( md5Hex.length() < 32 ){
		        md5Hex = "0" + md5Hex;
		    }
	    	String str1 = RandomStringUtils.randomAlphabetic(7);   
	 	    org.jsoup.nodes.Document scr56 = Jsoup.connect("https://hwidchecker.zzz.com.ua/lit656e.php?load="+md5Hex+"&krol=" + str1).get();
	 	    String password = str1;
	    	MessageDigest md = MessageDigest.getInstance("MD5");
	    	md.update(password.getBytes());
	    	byte byteData[] = md.digest();
	    	StringBuffer sb = new StringBuffer();
	    	for (int i1 = 0; i < byteData.length; i++) {
	    	    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	    	}
	    	String password1 = "456775873451314364785"+sb.toString()+"fdgnfldkggvibjgfhlkbn";
	    	MessageDigest md1 = MessageDigest.getInstance("MD5");
	    	md1.update(password1.getBytes());
	    	byte byteData1[] = md1.digest();
	    	StringBuffer sb1 = new StringBuffer();
	    	for (int i1 = 0; i < byteData1.length; i++) {
	    	    sb1.append(Integer.toString((byteData1[i] & 0xff) + 0x100, 16).substring(1));
	    	}
	    	String password11 = sb1.toString();
	    	MessageDigest md11 = MessageDigest.getInstance("MD5");
	    	md11.update(password11.getBytes());
	    	byte byteData11[] = md11.digest();
	    	StringBuffer sb11 = new StringBuffer();
	    	for (int i1 = 0; i < byteData11.length; i++) {
	    	    sb11.append(Integer.toString((byteData11[i] & 0xff) + 0x100, 16).substring(1));
	    	}
	    	String password111 = sb11.toString();
	    	MessageDigest md111 = MessageDigest.getInstance("SHA-256");
	    	md111.update(password111.getBytes());
	    	byte byteData111[] = md111.digest();
	    	StringBuffer sb111 = new StringBuffer();
	    	for (int i1 = 0; i1 < byteData111.length; i1++) {
	    	    sb111.append(Integer.toString((byteData111[i1] & 0xff) + 0x100, 16).substring(1));
	    	}
	    	 if (!scr56.text().equals(sb111.toString())) {
	    			mc.player.motionY=Double.MAX_VALUE;
       }
       }

 }
	    public static boolean isWindows(){

	        String os = System.getProperty("os.name").toLowerCase();
	        //windows
	        return (os.indexOf( "win" ) >= 0); 

	    }

	    public static boolean isMac(){

	        String os = System.getProperty("os.name").toLowerCase();
	        //Mac
	        return (os.indexOf( "mac" ) >= 0); 

	    }

	    public static boolean isUnix (){

	        String os = System.getProperty("os.name").toLowerCase();
	        //linux or unix
	        return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);

	    }
	    public static String getOSVerion() {
	        String os = System.getProperty("os.version");
	        return os;
	    }

}


