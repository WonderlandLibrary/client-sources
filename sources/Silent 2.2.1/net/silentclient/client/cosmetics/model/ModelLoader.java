package net.silentclient.client.cosmetics.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;

public class ModelLoader
{
    private static ModelBuffer.Vertex2f parseTextureCoord(final String s, final boolean b) {
        final String[] split = s.split(" ");
        final float floatValue = Float.valueOf(split[1]);
        final float floatValue2 = Float.valueOf(split[2]);
        return new ModelBuffer.Vertex2f(floatValue, b ? (1.0f - floatValue2) : floatValue2);
    }
    
    private static ModelBuffer.Vertex3f parseVertex(final String s) {
        final String[] split = s.split(" ");
        return new ModelBuffer.Vertex3f(Float.valueOf(split[1]), Float.valueOf(split[2]), Float.valueOf(split[3]));
    }
    
    private static ModelBuffer.Vertex3f parseNormal(final String s) {
        final String[] split = s.split(" ");
        return new ModelBuffer.Vertex3f(Float.valueOf(split[1]), Float.valueOf(split[2]), Float.valueOf(split[3]));
    }
    
    private static Face parseFace(final boolean b, final boolean b2, final String s) {
        final String[] split = s.split(" ");
        final int[] array = { Integer.parseInt(split[1].split("/")[0]), Integer.parseInt(split[2].split("/")[0]), Integer.parseInt(split[3].split("/")[0]) };
        if (b2) {
            return new Face(array, new int[] { Integer.parseInt(split[1].split("/")[2]), Integer.parseInt(split[2].split("/")[2]), Integer.parseInt(split[3].split("/")[2]), 0 }, new int[] { Integer.parseInt(split[1].split("/")[1]), Integer.parseInt(split[2].split("/")[1]), Integer.parseInt(split[3].split("/")[1]), 0 }, null);
        }
        if (b) {
            return new Face(array, new int[] { Integer.parseInt(split[1].split("/")[2]), Integer.parseInt(split[2].split("/")[2]), Integer.parseInt(split[3].split("/")[2]), 0 });
        }
        return new Face(array);
    }
    
    public static Model loadModel(final ResourceLocation aj) {
        return loadModel(aj, false);
    }
    
    public static Model loadModel(final ResourceLocation aj, final boolean b) {
        InputStream a = null;
        try {
            a = Minecraft.getMinecraft().getResourceManager().getResource(aj).getInputStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(a));
            final Model model = new Model();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String s = line.split(" ")[0];
                if (!s.equals("#")) {
                    if (line.startsWith("#")) {
                        continue;
                    }
                    if (s.equals("o")) {
                        model.setName(line.split(" ")[1]);
                    }
                    else if (s.equals("v")) {
                        model.getVertices().add(parseVertex(line));
                    }
                    else if (s.equals("vn")) {
                        model.getNormals().add(parseNormal(line));
                    }
                    else if (s.equals("f")) {
                        model.getFaces().add(parseFace(model.getNormals().size() > 0, model.getTextureCoordinates().size() > 0, line));
                    }
                    else {
                        if (!s.equals("vt")) {
                            continue;
                        }
                        model.getTextureCoordinates().add(parseTextureCoord(line, b));
                    }
                }
            }
            bufferedReader.close();
            return model;
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            Client.logger.error("MODEL LOADER ERROR: " + ex.getMessage());
        }
        finally {
            if (a != null) {
                try {
                    a.close();
                }
                catch (final IOException ex2) {}
            }
        }
        return null;
    }
}
