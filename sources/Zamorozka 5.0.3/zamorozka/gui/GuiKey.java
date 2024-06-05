package zamorozka.gui;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import zamorozka.ui.FileManager;
import zamorozka.ui.font.CFontRenderer;
import zamorozka.ui.font.Fonts;

public class GuiKey extends GuiScreen {

	private GuiScreen parentScreen;
	public static boolean WriteKey = true;
	private GuiTextField usernameTextField;

	public GuiKey(GuiScreen guiscreen) {
		this.parentScreen = guiscreen;
	}

	protected void actionPerformed(GuiButton guibutton) throws IOException {
		if (!guibutton.enabled) {
			return;
		}

		if (guibutton.id == 0) {
			mc.displayGuiScreen(new GuiMainMenu());

			if (isWindows()) {
				String command = "wmic csproduct get UUID";
				StringBuffer output = new StringBuffer();
				Process SerNumProcess = Runtime.getRuntime().exec(command);
				try {
					SerNumProcess = Runtime.getRuntime().exec(command);
				} catch (IOException e) {
				}
				BufferedReader sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));
				String line = "";
				try {
					while ((line = sNumReader.readLine()) != null) {
						output.append(line + "\n");
					}
				} catch (IOException e) {
				}
				String MachineID = output.toString().substring(output.indexOf("\n"), output.length()).trim();

				MessageDigest messageDigest = null;
				byte[] digest = new byte[0];

				try {
					messageDigest = MessageDigest.getInstance("MD5");
					messageDigest.reset();
					messageDigest.update(MachineID.getBytes());
					digest = messageDigest.digest();
				} catch (NoSuchAlgorithmException e) {
				}

				BigInteger bigInt = new BigInteger(1, digest);
				String md5Hex = bigInt.toString(16);

				while (md5Hex.length() < 32) {
					md5Hex = "0" + md5Hex;
				}
				String url = "https://hwidchecker.zzz.com.ua/k1r2.php?key=" + usernameTextField.getText() + "-" + md5Hex;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				final String USER_AGENT = "Mozilla/5.0";
				con.setRequestProperty("User-Agent", USER_AGENT);
				int responseCode = con.getResponseCode();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				if (usernameTextField.getText() == "" || usernameTextField.getText().indexOf("") == -1 || usernameTextField.getText() == " " || usernameTextField.getText().length() < 2 || usernameTextField.getText().contains(" ")) {
					usernameTextField.setTextColor(14745762);
					usernameTextField.setText("Êëþ÷ íå äîëæåí ðàâíÿòüñÿ íóëþ.");
				} else {
					if (!response.toString().equals(usernameTextField.getText() + "-" + md5Hex)) {

						usernameTextField.setTextColor(255);
						usernameTextField.setText("Îøèáêà, íåâåðíûé êëþ÷.");
						new java.util.Timer().schedule(new java.util.TimerTask() {
							@Override
							public void run() {
								System.exit(0);
							}
						}, 1000);
					} else {

						WriteKey = true;
						mc.displayGuiScreen(new GuiMainMenu());
						File f = new File("password");
						if (f.exists() && !f.isDirectory()) {
						} else {
							try {
								File file = new File(FileManager.Zamorozka.getAbsolutePath(), "password");
								BufferedWriter out = new BufferedWriter(new FileWriter(file));
								out.write(usernameTextField.getText());
								out.write("\r\n");
								out.close();
							} catch (Exception e) {
							}
						}
					}
				}
			} else if (isMac()) {

				String s = "";
				final String main = System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name").trim();
				final byte[] bytes = main.getBytes("UTF-8");
				MessageDigest messageDigest = null;
				try {
					messageDigest = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
				} catch (NoSuchAlgorithmException j) {
					NoSuchAlgorithmException e = null;
					e.printStackTrace();
				}
				BigInteger bigInt = new BigInteger(1, digest);
				String md5Hex = bigInt.toString(16);

				while (md5Hex.length() < 32) {
					md5Hex = "0" + md5Hex;
				}
				String url = "https://hwidchecker.zzz.com.ua/k1r2.php?key=" + usernameTextField.getText() + "-" + md5Hex;
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				final String USER_AGENT = "Mozilla/5.0";
				con.setRequestProperty("User-Agent", USER_AGENT);
				int responseCode = con.getResponseCode();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				int indexJava = response.toString().indexOf(usernameTextField.getText() + "-" + md5Hex);
				if (usernameTextField.getText() == "" || usernameTextField.getText().indexOf("") == -1 || usernameTextField.getText() == " " || usernameTextField.getText().length() < 2 || usernameTextField.getText().contains(" ")) {
					usernameTextField.setTextColor(14745762);
					usernameTextField.setText("Êëþ÷ íå äîëæåí ðàâíÿòüñÿ íóëþ.");
				} else {
					if (!response.toString().equals(usernameTextField.getText() + "-" + md5Hex)) {
						usernameTextField.setTextColor(255);
						usernameTextField.setText("Îøèáêà, íåâåðíûé êëþ÷.");
						new java.util.Timer().schedule(new java.util.TimerTask() {
							@Override
							public void run() {
								System.exit(0);
							}
						}, 1000);
					} else {
						WriteKey = true;
						mc.displayGuiScreen(new GuiMainMenu());
						File f = new File("password");
						if (f.exists() && !f.isDirectory()) {
						} else {
							try {
								File file = new File(FileManager.Zamorozka.getAbsolutePath(), "password");
								BufferedWriter out = new BufferedWriter(new FileWriter(file));
								out.write(usernameTextField.getText());
								out.write("\r\n");
								out.close();
							} catch (Exception e) {
							}
						}
					}

				}
			}
		} else {
			System.exit(0);
		}
	}

	protected void keyTyped(char c, int i) throws IOException {
		this.usernameTextField.textboxKeyTyped(c, i);
		if ((c == '\t') && (this.usernameTextField.isFocused())) {
			this.usernameTextField.setFocused(false);
		}
		if (c == '\r') {
			actionPerformed((GuiButton) this.buttonList.get(0));
		}
	}

	protected void mouseClicked(int i, int j, int k) throws IOException {
		super.mouseClicked(i, j, k);
		this.usernameTextField.mouseClicked(i, j, k);
	}

	public void initGui() {

		if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
			if (mc.entityRenderer.theShaderGroup != null) {
				mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			}
			mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
		}
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Auth"));
		this.usernameTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
		File f1 = new File("password");
		try {
			File file = new File(FileManager.Zamorozka.getAbsolutePath(), "password");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in1 = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in1));
			String line1;
			while ((line1 = br.readLine()) != null) {
				String curLine = line1.trim();
				// System.out.print(curLine);
				usernameTextField.setText(curLine);
			}
			br.close();
		} catch (Exception e) {
		}

	}

	public void drawScreen(int i, int j, float f) {

		this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
		final CFontRenderer font2 = Fonts.default20;
		drawDefaultBackground();

		drawString(this.fontRendererObj, "Developers » Shalopay & Smertnix & Maxsimus", 1, this.height / 4 - 40, 10526880);
		drawString(this.fontRendererObj, "Update » 0.5", 1, this.height / 4 - 30, 10526880);
		drawString(this.fontRendererObj, "Site » Zamorozka.fun", 1, this.height / 4 - 20, 10526880);
		drawString(this.fontRendererObj, "Vk Group » vk.com/ZamorozkaClient", 1, this.height / 4 - 10, 10526880);
		drawString(this.fontRendererObj, "Ââåäèòå âàø ïàðîëü:", this.width / 2 - 100, 104, 10526880);

		this.usernameTextField.drawTextBox();
		super.drawScreen(i, j, f);
		drawGradientRect(0, 0, this.width, this.height, 0x220044A0, 0x770044A0);

	}

	public static boolean isWindows() {

		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);

	}

	public static String getOSVerion() {
		String os = System.getProperty("os.version");
		return os;
	}

	public static void mackcheck(String mac1) throws IOException {
		String url11 = mac1;

		URL obj11 = new URL(url11);
		HttpURLConnection connection = (HttpURLConnection) obj11.openConnection();

		connection.setRequestMethod("GET");

		BufferedReader in11 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine1;
		StringBuffer response1 = new StringBuffer();

		while ((inputLine1 = in11.readLine()) != null) {
			response1.append(inputLine1);
		}
		in11.close();
	}

	@Override
	public void onGuiClosed() {

		if (mc.entityRenderer.theShaderGroup != null) {
			mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			mc.entityRenderer.theShaderGroup = null;
		}
	}
}