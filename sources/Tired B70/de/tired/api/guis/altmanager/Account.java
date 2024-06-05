package de.tired.api.guis.altmanager;

import de.tired.interfaces.IHook;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class Account implements IHook {

	@Getter
	@Setter
	private String userName, password;
	@Setter
	@Getter
	private String realName;
	@Getter
	private String mask;

	@Getter
	@Setter
	private String key;

	@Getter
	@Setter
	private boolean isTheAltening = false;

	@Getter
	private ResourceLocation head;

	public Account(String username, String password) {
		this(username, password, "");
	}

	public Account(String key) {
		this.key = key;
		isTheAltening = true;
	}

	public Account(final String userName, final String pass, final String mask) {
		this.userName = userName;
		this.password = pass;
		this.mask = mask;
	}

	public void loadHead() {
		if (!isTheAltening) {
			if (head == null) {

				head = new ResourceLocation("heads/" + mask);

				ThreadDownloadImageData textureHead = new ThreadDownloadImageData(null, "https://mineskin.de/avatar/" + mask.replace("minecraft:heads/", ""), null, null);
				MC.getTextureManager().loadTexture(head, textureHead);
			}
		}
	}

	private BufferedImage getSkinLocation(String uuid) {
		try {
			final URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
			final HttpURLConnection http = (HttpURLConnection) url.openConnection();
			final InputStream stream = http.getInputStream();

			if (http.getResponseCode() == 200) {
				final BufferedReader in = new BufferedReader(new InputStreamReader(stream));
				final StringBuilder sb = new StringBuilder();

				String currentLine;

				while ((currentLine = in.readLine()) != null) {
					sb.append(currentLine);
				}
				in.close();

				//noinspection deprecation
				final JsonElement jsonData = new JsonParser().parse(sb.toString());
				final JsonObject res = jsonData.getAsJsonObject();
				final JsonArray array = res.getAsJsonArray("properties");

				final String base64Encoded = array.get(0).getAsJsonObject().get("value").getAsString();

				final byte[] base64Decoded = Base64.getDecoder().decode(base64Encoded);

				final String decodedString = new String(base64Decoded);

				//noinspection deprecation
				final JsonElement element = new JsonParser().parse(decodedString);
				final JsonObject object = element.getAsJsonObject();

				final String skinUrl = object.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();

				return ImageIO.read(new URL(skinUrl));
			}
			http.disconnect();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public ResourceLocation setHead(ResourceLocation location) {
		return head = location;
	}

}

