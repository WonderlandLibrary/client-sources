package wwe.modules.render;

import wwe.*;
import wwe.utils.*;
import wwe.modules.*;

public class NoRender extends qMIe
{
    public BooleanValue fire;
    public BooleanValue wither;
    public BooleanValue lightLag;
    public BooleanValue hurtCam;
    public BooleanValue nausea;
    public BooleanValue potionIcon;
    public BooleanValue portalOverLay;
    public BooleanValue scoreBoard;
    
    public NoRender() {
        super("No Render", "Removes some stuff from rendering on screen", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        this.fire = new BooleanValue("Fire", this, "Dont render fire", true);
        this.wither = new BooleanValue("Wither", this, "Dont render wither boss bar", true);
        this.lightLag = new BooleanValue("Light Lag", this, "Doesnt allow lag via light", true);
        this.hurtCam = new BooleanValue("Hurt Cam", this, "No hurt cam affect", true);
        this.nausea = new BooleanValue("Nausea", this, "No nausea affect", true);
        this.potionIcon = new BooleanValue("Potion Icon", this, "Doesnt render minecrafts potion icon", false);
        this.portalOverLay = new BooleanValue("Portal Over Lay", this, "Doesnt render the nether portal affect", true);
        this.scoreBoard = new BooleanValue("Score Board", this, "Doesnt render the games score board", true);
    }
}
