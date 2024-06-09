package HORIZON-6-0-SKIDPROTECTION;

import javax.swing.SwingUtilities;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import java.awt.Canvas;

public class CanvasGameContainer extends Canvas
{
    protected HorizonCode_Horizon_È HorizonCode_Horizon_È;
    protected Game Â;
    
    public CanvasGameContainer(final Game game) throws SlickException {
        this(game, false);
    }
    
    public CanvasGameContainer(final Game game, final boolean shared) throws SlickException {
        this.Â = game;
        this.setIgnoreRepaint(true);
        this.requestFocus();
        this.setSize(500, 500);
        (this.HorizonCode_Horizon_È = new HorizonCode_Horizon_È(game, shared)).Ø­áŒŠá(false);
    }
    
    public void HorizonCode_Horizon_È() throws SlickException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Input.HorizonCode_Horizon_È();
                    try {
                        Display.setParent((Canvas)CanvasGameContainer.this);
                    }
                    catch (LWJGLException e) {
                        throw new SlickException("Failed to setParent of canvas", (Throwable)e);
                    }
                    CanvasGameContainer.this.HorizonCode_Horizon_È.Âµá€();
                    CanvasGameContainer.this.Ø­áŒŠá();
                }
                catch (SlickException e2) {
                    e2.printStackTrace();
                    System.exit(0);
                }
            }
        });
    }
    
    private void Ø­áŒŠá() {
        if (!this.isVisible()) {
            return;
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    CanvasGameContainer.this.HorizonCode_Horizon_È.Ó();
                }
                catch (SlickException e) {
                    e.printStackTrace();
                }
                CanvasGameContainer.this.HorizonCode_Horizon_È.ˆà();
                CanvasGameContainer.this.Ø­áŒŠá();
            }
        });
    }
    
    public void Â() {
    }
    
    public GameContainer Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    private class HorizonCode_Horizon_È extends AppGameContainer
    {
        public HorizonCode_Horizon_È(final Game game, final boolean shared) throws SlickException {
            super(game, CanvasGameContainer.this.getWidth(), CanvasGameContainer.this.getHeight(), false);
            this.ˆÏ­ = CanvasGameContainer.this.getWidth();
            this.£á = CanvasGameContainer.this.getHeight();
            if (shared) {
                Šáƒ();
            }
        }
        
        @Override
        protected void C_() {
            super.C_();
        }
        
        @Override
        protected boolean Å() {
            return super.Å() && CanvasGameContainer.this.isDisplayable();
        }
        
        @Override
        public int £à() {
            return CanvasGameContainer.this.getHeight();
        }
        
        @Override
        public int µà() {
            return CanvasGameContainer.this.getWidth();
        }
        
        public void ˆà() {
            if (this.ˆÏ­ == CanvasGameContainer.this.getWidth()) {
                if (this.£á == CanvasGameContainer.this.getHeight()) {
                    return;
                }
            }
            try {
                this.HorizonCode_Horizon_È(CanvasGameContainer.this.getWidth(), CanvasGameContainer.this.getHeight(), false);
            }
            catch (SlickException e) {
                Log.HorizonCode_Horizon_È(e);
            }
        }
    }
}
