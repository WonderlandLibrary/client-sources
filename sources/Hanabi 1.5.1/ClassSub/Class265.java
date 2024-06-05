package ClassSub;

import java.net.*;
import java.io.*;

public class Class265 extends Class220
{
    private int tw;
    private int th;
    private int margin;
    private Class220[][] subImages;
    private int spacing;
    private Class220 target;
    
    
    public Class265(final URL url, final int n, final int n2) throws Class341, IOException {
        this(new Class220(url.openStream(), url.toString(), false), n, n2);
    }
    
    public Class265(final Class220 target, final int tw, final int th) {
        super(target);
        this.margin = 0;
        this.target = target;
        this.tw = tw;
        this.th = th;
        this.initImpl();
    }
    
    public Class265(final Class220 target, final int tw, final int th, final int spacing, final int margin) {
        super(target);
        this.margin = 0;
        this.target = target;
        this.tw = tw;
        this.th = th;
        this.spacing = spacing;
        this.margin = margin;
        this.initImpl();
    }
    
    public Class265(final Class220 class220, final int n, final int n2, final int n3) {
        this(class220, n, n2, n3, 0);
    }
    
    public Class265(final String s, final int n, final int n2, final int n3) throws Class341 {
        this(s, n, n2, null, n3);
    }
    
    public Class265(final String s, final int n, final int n2) throws Class341 {
        this(s, n, n2, null);
    }
    
    public Class265(final String s, final int n, final int n2, final Class26 class26) throws Class341 {
        this(s, n, n2, class26, 0);
    }
    
    public Class265(final String s, final int tw, final int th, final Class26 class26, final int spacing) throws Class341 {
        super(s, false, 2, class26);
        this.margin = 0;
        this.target = this;
        this.tw = tw;
        this.th = th;
        this.spacing = spacing;
    }
    
    public Class265(final String s, final InputStream inputStream, final int tw, final int th) throws Class341 {
        super(inputStream, s, false);
        this.margin = 0;
        this.target = this;
        this.tw = tw;
        this.th = th;
    }
    
    @Override
    protected void initImpl() {
        if (this.subImages != null) {
            return;
        }
        final int n = (this.getWidth() - this.margin * 2 - this.tw) / (this.tw + this.spacing) + 1;
        int n2 = (this.getHeight() - this.margin * 2 - this.th) / (this.th + this.spacing) + 1;
        if ((this.getHeight() - this.th) % (this.th + this.spacing) != 0) {
            ++n2;
        }
        this.subImages = new Class220[n][n2];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                this.subImages[i][j] = this.getSprite(i, j);
            }
        }
    }
    
    public Class220 getSubImage(final int n, final int n2) {
        this.init();
        if (n < 0 || n >= this.subImages.length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + n + "," + n2);
        }
        if (n2 < 0 || n2 >= this.subImages[0].length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + n + "," + n2);
        }
        return this.subImages[n][n2];
    }
    
    public Class220 getSprite(final int n, final int n2) {
        this.target.init();
        this.initImpl();
        if (n < 0 || n >= this.subImages.length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + n + "," + n2);
        }
        if (n2 < 0 || n2 >= this.subImages[0].length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + n + "," + n2);
        }
        return this.target.getSubImage(n * (this.tw + this.spacing) + this.margin, n2 * (this.th + this.spacing) + this.margin, this.tw, this.th);
    }
    
    public int getHorizontalCount() {
        this.target.init();
        this.initImpl();
        return this.subImages.length;
    }
    
    public int getVerticalCount() {
        this.target.init();
        this.initImpl();
        return this.subImages[0].length;
    }
    
    public void renderInUse(final int n, final int n2, final int n3, final int n4) {
        this.subImages[n3][n4].drawEmbedded(n, n2, this.tw, this.th);
    }
    
    @Override
    public void endUse() {
        if (this.target == this) {
            super.endUse();
            return;
        }
        this.target.endUse();
    }
    
    @Override
    public void startUse() {
        if (this.target == this) {
            super.startUse();
            return;
        }
        this.target.startUse();
    }
    
    @Override
    public void setTexture(final Class282 class282) {
        if (this.target == this) {
            super.setTexture(class282);
            return;
        }
        this.target.setTexture(class282);
    }
}
