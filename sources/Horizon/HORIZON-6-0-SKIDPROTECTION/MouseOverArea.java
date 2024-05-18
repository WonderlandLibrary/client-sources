package HORIZON-6-0-SKIDPROTECTION;

public class MouseOverArea extends AbstractComponent
{
    private static final int Ø­áŒŠá = 1;
    private static final int Âµá€ = 2;
    private static final int Ó = 3;
    private Image à;
    private Image Ø;
    private Image áŒŠÆ;
    private Color áˆºÑ¢Õ;
    private Color ÂµÈ;
    private Color á;
    private Sound ˆÏ­;
    private Sound £á;
    private Shape Å;
    private Image £à;
    private Color µà;
    private boolean ˆà;
    private boolean ¥Æ;
    private int Ø­à;
    private boolean µÕ;
    
    public MouseOverArea(final GUIContext container, final Image image, final int x, final int y, final ComponentListener listener) {
        this(container, image, x, y, image.ŒÏ(), image.Çªà¢());
        this.HorizonCode_Horizon_È(listener);
    }
    
    public MouseOverArea(final GUIContext container, final Image image, final int x, final int y) {
        this(container, image, x, y, image.ŒÏ(), image.Çªà¢());
    }
    
    public MouseOverArea(final GUIContext container, final Image image, final int x, final int y, final int width, final int height, final ComponentListener listener) {
        this(container, image, x, y, width, height);
        this.HorizonCode_Horizon_È(listener);
    }
    
    public MouseOverArea(final GUIContext container, final Image image, final int x, final int y, final int width, final int height) {
        this(container, image, new Rectangle(x, y, width, height));
    }
    
    public MouseOverArea(final GUIContext container, final Image image, final Shape shape) {
        super(container);
        this.áˆºÑ¢Õ = Color.Ý;
        this.ÂµÈ = Color.Ý;
        this.á = Color.Ý;
        this.Ø­à = 1;
        this.Å = shape;
        this.à = image;
        this.£à = image;
        this.Ø = image;
        this.áŒŠÆ = image;
        this.µà = this.áˆºÑ¢Õ;
        this.Ø­à = 1;
        final Input input = container.á€();
        this.ˆà = this.Å.HorizonCode_Horizon_È(input.á(), input.ˆÏ­());
        this.¥Æ = input.áŒŠÆ(0);
        this.á();
    }
    
    public void HorizonCode_Horizon_È(final float x, final float y) {
        if (this.Å != null) {
            this.Å.c_(x);
            this.Å.d_(y);
        }
    }
    
    public void HorizonCode_Horizon_È(final float x) {
        this.Å.c_(x);
    }
    
    public void Â(final float y) {
        this.Å.d_(y);
    }
    
    @Override
    public int Â() {
        return (int)this.Å.£á();
    }
    
    @Override
    public int Ó() {
        return (int)this.Å.Å();
    }
    
    public void HorizonCode_Horizon_È(final Color color) {
        this.áˆºÑ¢Õ = color;
    }
    
    public void Â(final Color color) {
        this.ÂµÈ = color;
    }
    
    public void Ý(final Color color) {
        this.á = color;
    }
    
    public void HorizonCode_Horizon_È(final Image image) {
        this.à = image;
    }
    
    public void Â(final Image image) {
        this.Ø = image;
    }
    
    public void Ý(final Image image) {
        this.áŒŠÆ = image;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GUIContext container, final Graphics g) {
        if (this.£à != null) {
            final int xp = (int)(this.Å.£á() + (this.à() - this.£à.ŒÏ()) / 2);
            final int yp = (int)(this.Å.Å() + (this.Ø() - this.£à.Çªà¢()) / 2);
            this.£à.HorizonCode_Horizon_È(xp, yp, this.µà);
        }
        else {
            g.Â(this.µà);
            g.Â(this.Å);
        }
        this.á();
    }
    
    private void á() {
        if (!this.ˆà) {
            this.£à = this.à;
            this.µà = this.áˆºÑ¢Õ;
            this.Ø­à = 1;
            this.µÕ = false;
        }
        else {
            if (this.¥Æ) {
                if (this.Ø­à != 2 && this.µÕ) {
                    if (this.£á != null) {
                        this.£á.HorizonCode_Horizon_È();
                    }
                    this.£à = this.áŒŠÆ;
                    this.µà = this.á;
                    this.Ø­à = 2;
                    this.HorizonCode_Horizon_È();
                    this.µÕ = false;
                }
                return;
            }
            this.µÕ = true;
            if (this.Ø­à != 3) {
                if (this.ˆÏ­ != null) {
                    this.ˆÏ­.HorizonCode_Horizon_È();
                }
                this.£à = this.Ø;
                this.µà = this.ÂµÈ;
                this.Ø­à = 3;
            }
        }
        this.¥Æ = false;
        this.Ø­à = 1;
    }
    
    public void HorizonCode_Horizon_È(final Sound sound) {
        this.ˆÏ­ = sound;
    }
    
    public void Â(final Sound sound) {
        this.£á = sound;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int oldx, final int oldy, final int newx, final int newy) {
        this.ˆà = this.Å.HorizonCode_Horizon_È(newx, newy);
    }
    
    @Override
    public void Â(final int oldx, final int oldy, final int newx, final int newy) {
        this.HorizonCode_Horizon_È(oldx, oldy, newx, newy);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int button, final int mx, final int my) {
        this.ˆà = this.Å.HorizonCode_Horizon_È(mx, my);
        if (button == 0) {
            this.¥Æ = true;
        }
    }
    
    @Override
    public void Â(final int button, final int mx, final int my) {
        this.ˆà = this.Å.HorizonCode_Horizon_È(mx, my);
        if (button == 0) {
            this.¥Æ = false;
        }
    }
    
    @Override
    public int Ø() {
        return (int)(this.Å.Æ() - this.Å.Å());
    }
    
    @Override
    public int à() {
        return (int)(this.Å.µÕ() - this.Å.£á());
    }
    
    public boolean ÂµÈ() {
        return this.ˆà;
    }
    
    @Override
    public void Ý(final int x, final int y) {
        this.HorizonCode_Horizon_È(x, (float)y);
    }
}
