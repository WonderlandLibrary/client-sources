package de.labystudio.games;

import de.labystudio.gui.GuiGames;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.StatsLoader;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Mario extends GuiScreen
{
    DrawUtils draw;
    int lengthX = 0;
    int posX = 1;
    int posY = 4;
    int lengthY = 0;
    int speed = 60;
    int score = 0;
    GuiButton quit;
    GuiButton restart;
    Thread thread;
    boolean gameOver = false;
    ArrayList<Mario.Location> terrain = new ArrayList();
    double fallDistance = 0.0D;
    int jumpVelocity = 0;
    Mario.Location playerLocation;
    int tick = 0;

    public Mario()
    {
        this.draw = LabyMod.getInstance().draw;
        this.thread = null;
    }

    public boolean collideWithPixelType(Mario.Location loc, Mario.EnumPixelType pixel)
    {
        for (Mario.Location mario$location : this.getTerrain())
        {
            if (mario$location.collideWith(loc) && mario$location.type == pixel)
            {
                return true;
            }
        }

        return false;
    }

    public boolean collideWithBlock(Mario.Location loc)
    {
        for (Mario.Location mario$location : this.getTerrain())
        {
            if (mario$location.collideWith(loc) && (mario$location.type == Mario.EnumPixelType.BLOCK || mario$location.type == Mario.EnumPixelType.TUBE || mario$location.type == Mario.EnumPixelType.BONUS || mario$location.type == Mario.EnumPixelType.CLOUDBLOCK))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.quit = new GuiButton(0, 5, 5, 30, 20, Color.cl("c") + "Quit");
        this.buttonList.add(this.quit);
        this.lengthX = this.width / 10 - 2;
        this.lengthY = this.height / 10 - 4;

        if (this.gameOver)
        {
            this.restart = new GuiButton(1, this.width / 2 - 100, this.height - 25, 200, 20, "Play again");
            this.buttonList.add(this.restart);
            super.initGui();
        }
        else
        {
            if (this.thread == null)
            {
                this.create();
            }

            super.initGui();
        }
    }

    private void create()
    {
        this.gameOver = false;
        this.buildTerrain();
        this.spawnPlayer();
        this.start();
    }

    private void spawnPlayer()
    {
        this.playerLocation = new Mario.Location(5, 2, Mario.EnumPixelType.MARIO);
    }

    public void moveTerrain(int x)
    {
        for (Mario.Location mario$location : this.getTerrain())
        {
            mario$location.add(x, 0);
        }

        this.getPlayerLocation().add(x, 0);
    }

    private void buildTerrain()
    {
        for (int i = 0; i <= 50; ++i)
        {
            this.terrain.add(new Mario.Location(-1, i, Mario.EnumPixelType.BLOCK));
        }

        int k1 = 0;
        int j = 14;

        for (int k = 0; k <= 400; ++k)
        {
            if (k == 0)
            {
                for (int l = 0; l <= 20; ++l)
                {
                    for (int i1 = 0; i1 <= 50; ++i1)
                    {
                        this.terrain.add(new Mario.Location(0 + k1, j + i1, Mario.EnumPixelType.BLOCK));
                    }

                    ++k1;
                }
            }

            for (int l1 = 0; l1 <= 50; ++l1)
            {
                this.terrain.add(new Mario.Location(0 + k1, j + l1, Mario.EnumPixelType.BLOCK));
            }

            if (LabyMod.random.nextInt(20) == 0)
            {
                this.terrain.add(new Mario.Location(0 + k1, j - 1, Mario.EnumPixelType.BUSH));
            }

            ++k1;

            if (LabyMod.random.nextInt(10) == 0)
            {
                for (int i2 = 0; i2 <= 3; ++i2)
                {
                    if (LabyMod.random.nextBoolean())
                    {
                        ++k1;

                        if (LabyMod.random.nextBoolean())
                        {
                            if (j < 30)
                            {
                                ++j;
                            }
                        }
                        else if (j > 0)
                        {
                            --j;
                        }
                    }
                }
            }

            if (LabyMod.random.nextInt(5) == 0)
            {
                int j2 = 0;
                this.terrain.add(new Mario.Location(0 + k1, j - j2 - 1, Mario.EnumPixelType.TUBE));
                ++j2;

                for (int l2 = 0; l2 <= 1; ++l2)
                {
                    if (LabyMod.random.nextBoolean())
                    {
                        this.terrain.add(new Mario.Location(0 + k1, j - j2 - 1, Mario.EnumPixelType.TUBE));
                        ++j2;
                    }
                }
            }

            if (LabyMod.random.nextInt(3) == 0)
            {
                int k2 = 0;

                if (j - 5 > 0)
                {
                    int i3 = LabyMod.random.nextInt(j - 5);

                    for (int j1 = 0; j1 <= 4; ++j1)
                    {
                        if (LabyMod.random.nextBoolean())
                        {
                            this.terrain.add(new Mario.Location(0 + k1 + k2, i3, Mario.EnumPixelType.CLOUD));
                            ++k2;
                        }
                    }
                }
            }
        }
    }

    public Mario.Location getPlayerLocation()
    {
        return this.playerLocation;
    }

    public Mario.Location getPlayerHeadLocation()
    {
        if (this.getPlayerLocation() != null)
        {
            Mario.Location mario$location = this.getPlayerLocation().clone().add(0, -1);
            return mario$location;
        }
        else
        {
            return null;
        }
    }

    public boolean isPlayer()
    {
        return this.getPlayerLocation() != null;
    }

    public ArrayList<Mario.Location> getTerrain()
    {
        return this.terrain;
    }

    public boolean isOnGround()
    {
        return this.fallDistance == 0.0D;
    }

    public int getFallDistance()
    {
        return (int)this.fallDistance;
    }

    private void start()
    {
        this.thread = new Thread(new Runnable()
        {
            public void run()
            {
                while (!Mario.this.gameOver && Mario.this.mc.currentScreen == Mario.this)
                {
                    Mario.this.tick();

                    try
                    {
                        synchronized (Mario.this.thread)
                        {
                            Mario.this.thread.wait((long)Mario.this.speed);
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            }
        });
        this.thread.start();
    }

    private void gameOver()
    {
        if (!this.gameOver)
        {
            ArrayList<String> arraylist = new ArrayList();

            if (StatsLoader.stats.containsKey("mario"))
            {
                arraylist = (ArrayList)StatsLoader.stats.get("mario");
            }

            if (StatsLoader.isHighScore(this.score, arraylist))
            {
                arraylist.add(0, "" + this.score);
            }

            if (arraylist.size() > 15)
            {
                arraylist.remove(15);
            }

            StatsLoader.stats.put("mario", arraylist);
            StatsLoader.savestats();
        }

        this.gameOver = true;
        this.initGui();
    }

    private void tick()
    {
        ++this.fallDistance;

        if (this.collideWithBlock(this.playerLocation.clone().add(0, 1)))
        {
            this.fallDistance = 0.0D;
        }

        if (this.isPlayer())
        {
            if (!this.isOnGround() && LabyMod.random.nextInt(this.getFallDistance()) != 0 && this.jumpVelocity == 0 && !this.collideWithBlock(this.playerLocation.clone().add(0, 1)))
            {
                this.playerLocation.add(0, 1);
            }

            if (this.jumpVelocity > 0 && !this.collideWithBlock(this.playerLocation.clone().add(0, -1)))
            {
                this.playerLocation.add(0, -1);
                --this.jumpVelocity;
            }

            if (this.collideWithPixelType(this.getPlayerLocation(), Mario.EnumPixelType.LAVA))
            {
                this.gameOver();
            }

            if (this.getPlayerLocation().getX() > 30)
            {
                this.moveTerrain(-1);
            }

            if (this.getPlayerLocation().getY() > 30)
            {
                this.gameOver();
            }
        }

        this.handleKeyBoardInput();
        ++this.tick;
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    public void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(new GuiGames());
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new Mario());
        }

        super.actionPerformed(button);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            this.mc.displayGuiScreen(new GuiGames());
        }
        else
        {
            super.keyTyped(typedChar, keyCode);
        }
    }

    protected void handleKeyBoardInput()
    {
        if (!this.gameOver)
        {
            if ((Keyboard.isKeyDown(205) || Keyboard.isKeyDown(32)) && !this.collideWithBlock(this.getPlayerLocation().clone().add(1, 0)) && !this.collideWithBlock(this.getPlayerHeadLocation().clone().add(1, 0)))
            {
                this.getPlayerLocation().add(1, 0);
                ++this.score;
            }

            if ((Keyboard.isKeyDown(203) || Keyboard.isKeyDown(30)) && !this.collideWithBlock(this.getPlayerLocation().clone().add(-1, 0)) && !this.collideWithBlock(this.getPlayerHeadLocation().clone().add(-1, 0)))
            {
                this.getPlayerLocation().add(-1, 0);
                --this.score;
            }

            if (Keyboard.isKeyDown(57) && this.isOnGround())
            {
                this.jumpVelocity = 3;
            }
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (LabyMod.getInstance().isInGame())
        {
            GlStateManager.enableBlend();
            this.draw.drawTransparentBackground(0, 32, this.width, this.height - 33);
        }
        else
        {
            this.drawDefaultBackground();
            this.draw.drawChatBackground(0, 32, this.width, this.height - 33);
        }

        DrawUtils drawutils = this.draw;
        DrawUtils.drawRect(0, 0, this.width, this.height, (new java.awt.Color(107, 140, 255)).getRGB());

        for (Mario.Location mario$location : this.getTerrain())
        {
            if (mario$location.type == Mario.EnumPixelType.CLOUD)
            {
                this.drawPixel(mario$location.getX(), mario$location.getY(), java.awt.Color.WHITE.getRGB());
            }
        }

        Mario.Location mario$location3 = this.getPlayerLocation();

        if (mario$location3 != null)
        {
            this.drawPixel(mario$location3.getX(), mario$location3.getY(), java.awt.Color.BLUE.getRGB());
            mario$location3 = this.getPlayerHeadLocation();
            this.drawPixel(mario$location3.getX(), mario$location3.getY(), java.awt.Color.BLUE.getRGB());
        }

        for (Mario.Location mario$location1 : this.getTerrain())
        {
            if (mario$location1.type == Mario.EnumPixelType.LAVA)
            {
                this.drawPixel(mario$location1.getX(), mario$location1.getY(), java.awt.Color.ORANGE.getRGB());
            }

            if (mario$location1.type == Mario.EnumPixelType.BLOCK)
            {
                this.drawPixel(mario$location1.getX(), mario$location1.getY(), (new java.awt.Color(231, 99, 24)).getRGB());
            }

            if (mario$location1.type == Mario.EnumPixelType.TUBE)
            {
                this.drawPixel(mario$location1.getX(), mario$location1.getY(), (new java.awt.Color(0, 173, 0)).getRGB());
            }

            if (mario$location1.type == Mario.EnumPixelType.BUSH)
            {
                this.drawPixel(mario$location1.getX(), mario$location1.getY(), (new java.awt.Color(189, 247, 24)).getRGB());
            }

            if (mario$location1.type == Mario.EnumPixelType.CLOUDBLOCK)
            {
                this.drawPixel(mario$location1.getX(), mario$location1.getY(), (new java.awt.Color(239, 239, 239)).getRGB());
            }

            if (mario$location1.type == Mario.EnumPixelType.BONUS)
            {
                this.drawPixel(mario$location1.getX(), mario$location1.getY(), (new java.awt.Color(255, 165, 66)).getRGB());
            }
        }

        ArrayList<Mario.Location> arraylist = new ArrayList();

        for (Mario.Location mario$location2 : this.getTerrain())
        {
            if (mario$location3.getX() < 0)
            {
                arraylist.add(mario$location3);
            }
        }

        for (Mario.Location mario$location4 : arraylist)
        {
            this.getTerrain().remove(mario$location4);
        }

        if (this.gameOver)
        {
            GL11.glPushMatrix();
            int j = 3;
            GL11.glScaled((double)j, (double)j, (double)j);
            this.draw.drawCenteredString(Color.cl("c") + "Game Over", this.width / 2 / j, (this.height / 4 - 5) / j);
            GL11.glPopMatrix();

            if (StatsLoader.stats.containsKey("mario"))
            {
                ArrayList<String> arraylist1 = (ArrayList)StatsLoader.stats.get("mario");
                int i = 1;

                for (String s : arraylist1)
                {
                    this.draw.drawString(i + ". Place - " + Color.cl("b") + s + "m", (double)(this.width / 2 - 43), (double)(this.height / 4 - 5 + i * 10 + 20));
                    ++i;

                    if (i > 10)
                    {
                        break;
                    }
                }
            }
            else
            {
                this.draw.drawCenteredString(Color.cl("f") + "No stats found", this.width / 2, this.height / 4 - 5 + 30);
            }
        }

        GlStateManager.disableBlend();
        this.draw.overlayBackground(0, 32);
        this.draw.overlayBackground(this.height - 33, this.height);
        int k = this.score;

        if (k < 0)
        {
            k = 0;
        }

        this.draw.drawString("Score: " + k + "m", 5.0D, (double)(this.height - 24));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void drawPixel(int x, int y, int color)
    {
        DrawUtils drawutils = this.draw;
        DrawUtils.drawRect(x * 10, y * 10, (x + 1) * 10, (y + 1) * 10, color);
    }

    static enum EnumDirection
    {
        UP,
        RIGHT,
        DOWN,
        LEFT;
    }

    static enum EnumPixelType
    {
        PIXEL,
        MARIO,
        LAVA,
        BLOCK,
        CLOUD,
        BUSH,
        TUBE,
        BONUS,
        CLOUDBLOCK;
    }

    class Location
    {
        int x = 0;
        int y = 0;
        Mario.EnumPixelType type = Mario.EnumPixelType.PIXEL;

        public Location(int x, int y, Mario.EnumPixelType type)
        {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        public Mario.Location add(int x, int y)
        {
            this.x += x;
            this.y += y;
            return this;
        }

        public Mario.Location clone()
        {
            return Mario.this.new Location(this.x, this.y, this.type);
        }

        public int getX()
        {
            return this.x;
        }

        public int getY()
        {
            return this.y;
        }

        public boolean collideWith(Mario.Location loc)
        {
            return this.getX() == loc.getX() && this.getY() == loc.getY();
        }
    }
}
