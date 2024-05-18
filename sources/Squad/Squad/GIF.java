package Squad;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

public class GIF {

    private List<BufferedImage> images;

    private int width;
    private int height;
    	

    private int index;

    public GIF(InputStream inputStream) throws IOException {
        Objects.requireNonNull(inputStream, "InputStream cannot be null.");
        // It should be just one reader available.
        ImageReader reader = ImageIO.getImageReadersByFormatName("GIF").next();

        reader.setInput(ImageIO.createImageInputStream(inputStream), false, true);
        images = new ArrayList<>();
        int imageCount = reader.getNumImages(true);
        int min = reader.getMinIndex();
        for (int i = min; i < imageCount; i++) {
            images.add(reader.read(i, reader.getDefaultReadParam()));
        }

        width = reader.getWidth(min);
        height = reader.getHeight(min);
    }


    public void increment() {
        index++;
        if (index >= images.size()) {
            index = 0;
        }
    }

    public List<BufferedImage> getImages() {
        return Collections.unmodifiableList(images);
    }

    public BufferedImage getCurrentImage() {
        return images.get(index);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        if (index >= images.size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

    }

}
