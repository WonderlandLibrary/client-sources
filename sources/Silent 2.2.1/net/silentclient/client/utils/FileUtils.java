package net.silentclient.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileUtils {
	public static String readInputStream(InputStream inputStream) {
		StringBuilder stringBuilder = new StringBuilder();

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = bufferedReader.readLine()) != null)
				stringBuilder.append(line).append('\n');

		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	public static ResourceLocation fileToResourceLocation(File file) {
		ResourceLocation location = null;
		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		location = Minecraft.getMinecraft().getRenderManager().renderEngine.getDynamicTextureLocation(file.getName(), new DynamicTexture(img));
		
		return location;
	}

	public static void exportResource(String resourceName, String path) throws Exception {
		InputStream stream = null;
		OutputStream resStreamOut = null;
		String jarFolder;
		try {
			stream = Client.getInstance().getClass().getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
			if(stream == null) {
				throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
			}

			int readBytes;
			byte[] buffer = new byte[4096];
			resStreamOut = new FileOutputStream(path);
			while ((readBytes = stream.read(buffer)) > 0) {
				resStreamOut.write(buffer, 0, readBytes);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			stream.close();
			resStreamOut.close();
		}
	}

	public static void copyFile(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}
}
