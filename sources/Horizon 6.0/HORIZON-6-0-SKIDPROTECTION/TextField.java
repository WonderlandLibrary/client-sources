package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.Sys;

public class TextField extends AbstractComponent
{
    private static final int Ó = 400;
    private static final int à = 50;
    private int Ø;
    private int áŒŠÆ;
    protected int Ø­áŒŠá;
    protected int Âµá€;
    private int áˆºÑ¢Õ;
    private String ÂµÈ;
    private Font á;
    private Color ˆÏ­;
    private Color £á;
    private Color Å;
    private int £à;
    private boolean µà;
    private int ˆà;
    private char ¥Æ;
    private long Ø­à;
    private String µÕ;
    private int Æ;
    private boolean Šáƒ;
    
    public TextField(final GUIContext container, final Font font, final int x, final int y, final int width, final int height, final ComponentListener listener) {
        this(container, font, x, y, width, height);
        this.HorizonCode_Horizon_È(listener);
    }
    
    public TextField(final GUIContext container, final Font font, final int x, final int y, final int width, final int height) {
        super(container);
        this.áˆºÑ¢Õ = 10000;
        this.ÂµÈ = "";
        this.ˆÏ­ = Color.Ý;
        this.£á = Color.Ý;
        this.Å = new Color(0.0f, 0.0f, 0.0f, 0.5f);
        this.µà = true;
        this.ˆà = -1;
        this.¥Æ = '\0';
        this.Šáƒ = true;
        this.á = font;
        this.Ý(x, y);
        this.Ø = width;
        this.áŒŠÆ = height;
    }
    
    public void Â(final boolean consume) {
        this.Šáƒ = consume;
    }
    
    public void ÂµÈ() {
        this.HorizonCode_Horizon_È(false);
    }
    
    @Override
    public void Ý(final int x, final int y) {
        this.Ø­áŒŠá = x;
        this.Âµá€ = y;
    }
    
    @Override
    public int Â() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public int Ó() {
        return this.Âµá€;
    }
    
    @Override
    public int à() {
        return this.Ø;
    }
    
    @Override
    public int Ø() {
        return this.áŒŠÆ;
    }
    
    public void HorizonCode_Horizon_È(final Color color) {
        this.Å = color;
    }
    
    public void Â(final Color color) {
        this.ˆÏ­ = color;
    }
    
    public void Ý(final Color color) {
        this.£á = color;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final GUIContext container, final Graphics g) {
        if (this.ˆà != -1) {
            if (this.Ý.Ø(this.ˆà)) {
                if (this.Ø­à < System.currentTimeMillis()) {
                    this.Ø­à = System.currentTimeMillis() + 50L;
                    this.HorizonCode_Horizon_È(this.ˆà, this.¥Æ);
                }
            }
            else {
                this.ˆà = -1;
            }
        }
        final Rectangle oldClip = g.£á();
        g.Ý(this.Ø­áŒŠá, this.Âµá€, this.Ø, this.áŒŠÆ);
        final Color clr = g.áˆºÑ¢Õ();
        if (this.Å != null) {
            g.Â(this.Å.HorizonCode_Horizon_È(clr));
            g.Ø­áŒŠá(this.Ø­áŒŠá, this.Âµá€, this.Ø, this.áŒŠÆ);
        }
        g.Â(this.£á.HorizonCode_Horizon_È(clr));
        final Font temp = g.Âµá€();
        final int cpos = this.á.Ý(this.ÂµÈ.substring(0, this.£à));
        int tx = 0;
        if (cpos > this.Ø) {
            tx = this.Ø - cpos - this.á.Ý("_");
        }
        g.Â(tx + 2, 0.0f);
        g.HorizonCode_Horizon_È(this.á);
        g.HorizonCode_Horizon_È(this.ÂµÈ, this.Ø­áŒŠá + 1, this.Âµá€ + 1);
        if (this.áŒŠÆ() && this.µà) {
            g.HorizonCode_Horizon_È("_", this.Ø­áŒŠá + 1 + cpos + 2, this.Âµá€ + 1);
        }
        g.Â(-tx - 2, 0.0f);
        if (this.ˆÏ­ != null) {
            g.Â(this.ˆÏ­.HorizonCode_Horizon_È(clr));
            g.Â(this.Ø­áŒŠá, this.Âµá€, this.Ø, this.áŒŠÆ);
        }
        g.Â(clr);
        g.HorizonCode_Horizon_È(temp);
        g.á();
        g.Â(oldClip);
    }
    
    public String á() {
        return this.ÂµÈ;
    }
    
    public void HorizonCode_Horizon_È(final String value) {
        this.ÂµÈ = value;
        if (this.£à > value.length()) {
            this.£à = value.length();
        }
    }
    
    public void áˆºÑ¢Õ(final int pos) {
        this.£à = pos;
        if (this.£à > this.ÂµÈ.length()) {
            this.£à = this.ÂµÈ.length();
        }
    }
    
    public void Ý(final boolean visibleCursor) {
        this.µà = visibleCursor;
    }
    
    public void ÂµÈ(final int length) {
        this.áˆºÑ¢Õ = length;
        if (this.ÂµÈ.length() > this.áˆºÑ¢Õ) {
            this.ÂµÈ = this.ÂµÈ.substring(0, this.áˆºÑ¢Õ);
        }
    }
    
    protected void Â(final String text) {
        this.ˆÏ­();
        for (int i = 0; i < text.length(); ++i) {
            this.HorizonCode_Horizon_È(-1, text.charAt(i));
        }
    }
    
    protected void ˆÏ­() {
        this.µÕ = this.á();
        this.Æ = this.£à;
    }
    
    protected void HorizonCode_Horizon_È(final int oldCursorPos, final String oldText) {
        if (oldText != null) {
            this.HorizonCode_Horizon_È(oldText);
            this.áˆºÑ¢Õ(oldCursorPos);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int key, final char c) {
        if (this.áŒŠÆ()) {
            if (key != -1) {
                if (key == 47 && (this.Ý.Ø(29) || this.Ý.Ø(157))) {
                    final String text = Sys.getClipboard();
                    if (text != null) {
                        this.Â(text);
                    }
                    return;
                }
                if (key == 44 && (this.Ý.Ø(29) || this.Ý.Ø(157))) {
                    if (this.µÕ != null) {
                        this.HorizonCode_Horizon_È(this.Æ, this.µÕ);
                    }
                    return;
                }
                if (this.Ý.Ø(29) || this.Ý.Ø(157)) {
                    return;
                }
                if (this.Ý.Ø(56) || this.Ý.Ø(184)) {
                    return;
                }
            }
            if (this.ˆà != key) {
                this.ˆà = key;
                this.Ø­à = System.currentTimeMillis() + 400L;
            }
            else {
                this.Ø­à = System.currentTimeMillis() + 50L;
            }
            this.¥Æ = c;
            if (key == 203) {
                if (this.£à > 0) {
                    --this.£à;
                }
                if (this.Šáƒ) {
                    this.HorizonCode_Horizon_È.á€().£à();
                }
            }
            else if (key == 205) {
                if (this.£à < this.ÂµÈ.length()) {
                    ++this.£à;
                }
                if (this.Šáƒ) {
                    this.HorizonCode_Horizon_È.á€().£à();
                }
            }
            else if (key == 14) {
                if (this.£à > 0 && this.ÂµÈ.length() > 0) {
                    if (this.£à < this.ÂµÈ.length()) {
                        this.ÂµÈ = String.valueOf(this.ÂµÈ.substring(0, this.£à - 1)) + this.ÂµÈ.substring(this.£à);
                    }
                    else {
                        this.ÂµÈ = this.ÂµÈ.substring(0, this.£à - 1);
                    }
                    --this.£à;
                }
                if (this.Šáƒ) {
                    this.HorizonCode_Horizon_È.á€().£à();
                }
            }
            else if (key == 211) {
                if (this.ÂµÈ.length() > this.£à) {
                    this.ÂµÈ = String.valueOf(this.ÂµÈ.substring(0, this.£à)) + this.ÂµÈ.substring(this.£à + 1);
                }
                if (this.Šáƒ) {
                    this.HorizonCode_Horizon_È.á€().£à();
                }
            }
            else if (c < '\u007f' && c > '\u001f' && this.ÂµÈ.length() < this.áˆºÑ¢Õ) {
                if (this.£à < this.ÂµÈ.length()) {
                    this.ÂµÈ = String.valueOf(this.ÂµÈ.substring(0, this.£à)) + c + this.ÂµÈ.substring(this.£à);
                }
                else {
                    this.ÂµÈ = String.valueOf(this.ÂµÈ.substring(0, this.£à)) + c;
                }
                ++this.£à;
                if (this.Šáƒ) {
                    this.HorizonCode_Horizon_È.á€().£à();
                }
            }
            else if (key == 28) {
                this.HorizonCode_Horizon_È();
                if (this.Šáƒ) {
                    this.HorizonCode_Horizon_È.á€().£à();
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean focus) {
        this.ˆà = -1;
        super.HorizonCode_Horizon_È(focus);
    }
}
