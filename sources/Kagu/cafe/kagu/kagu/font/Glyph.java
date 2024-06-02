package cafe.kagu.kagu.font;

public class Glyph {

    public Glyph(int width, int height, double scaledX, double scaledY, double scaledWidth, double scaledHeight,
            int renderYOffset) {
        this.width = width;
        this.height = height;
        this.scaledX = scaledX;
        this.scaledY = scaledY;
        this.scaledWidth = scaledWidth;
        this.scaledHeight = scaledHeight;
        this.renderYOffset = renderYOffset;
    }

    private int width, height, renderYOffset;
    private double scaledX, scaledY, scaledWidth, scaledHeight;

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * @return the scaledX
     */
    public double getScaledX() {
        return scaledX;
    }

    /**
     * @return the scaledY
     */
    public double getScaledY() {
        return scaledY;
    }

    /**
     * @return the scaledWidth
     */
    public double getScaledWidth() {
        return scaledWidth;
    }

    /**
     * @return the scaledHeight
     */
    public double getScaledHeight() {
        return scaledHeight;
    }

    /**
     * @return the renderYOffset
     */
    public double getRenderYOffset() {
        return renderYOffset;
    }

}