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

public class Snake extends GuiScreen
{
    DrawUtils draw;
    int lengthX = 0;
    int posX = 1;
    int posY = 4;
    int lengthY = 0;
    int speed = 120;
    int powerUpAmount = 40;
    boolean cooldown = false;
    GuiButton quit;
    GuiButton restart;
    int score = 0;
    Thread thread;
    boolean gameOver = false;
    Snake.EnumDirection snakeDirection = Snake.EnumDirection.DOWN;
    ArrayList<Snake.Location> points = new ArrayList();
    ArrayList<Snake.Location> snake = new ArrayList();

    public Snake()
    {
        this.draw = LabyMod.getInstance().draw;
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
            else
            {
                this.points.clear();
                this.addPoint();

                if (this.getHead().x > this.lengthX || this.getHead().y > this.lengthY)
                {
                    this.expand(this.lengthX / 2, this.lengthY / 2);
                }
            }

            super.initGui();
        }
    }

    private void create()
    {
        this.score = 0;
        this.snakeDirection = Snake.EnumDirection.DOWN;
        this.gameOver = false;
        this.snake.clear();

        for (int i = 0; i < 3; ++i)
        {
            this.expand(this.lengthX / 2, this.lengthY / 2 + i);
        }

        this.addPoint();
        this.start();
    }

    private void start()
    {
        this.thread = new Thread(new Runnable()
        {
            public void run()
            {
                while (!Snake.this.gameOver && Snake.this.mc.currentScreen == Snake.this)
                {
                    Snake.this.tick();

                    try
                    {
                        synchronized (Snake.this.thread)
                        {
                            Snake.this.thread.wait((long)Snake.this.speed);
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

            if (StatsLoader.stats.containsKey("snake"))
            {
                arraylist = (ArrayList)StatsLoader.stats.get("snake");
            }

            if (StatsLoader.isHighScore(this.score, arraylist))
            {
                arraylist.add(0, "" + this.score);
            }

            if (arraylist.size() > 15)
            {
                arraylist.remove(15);
            }

            StatsLoader.stats.put("snake", arraylist);
            StatsLoader.savestats();
        }

        this.gameOver = true;
        this.initGui();
    }

    private void tick()
    {
        boolean flag = true;

        if (this.snakeDirection == Snake.EnumDirection.UP)
        {
            Snake.Location snake$location = this.getHead();

            if (snake$location.y < this.posY)
            {
                snake$location.y = this.lengthY;
            }

            flag = this.expand(snake$location.x, snake$location.y - 1);
        }

        if (this.snakeDirection == Snake.EnumDirection.DOWN)
        {
            Snake.Location snake$location1 = this.getHead();

            if (snake$location1.y > this.lengthY)
            {
                snake$location1.y = this.posY;
            }

            flag = this.expand(snake$location1.x, snake$location1.y + 1);
        }

        if (this.snakeDirection == Snake.EnumDirection.RIGHT)
        {
            Snake.Location snake$location2 = this.getHead();

            if (snake$location2.x > this.lengthX)
            {
                snake$location2.x = this.posX;
            }

            flag = this.expand(snake$location2.x + 1, snake$location2.y);
        }

        if (this.snakeDirection == Snake.EnumDirection.LEFT)
        {
            Snake.Location snake$location3 = this.getHead();

            if (snake$location3.x < this.posX)
            {
                snake$location3.x = this.lengthX;
            }

            flag = this.expand(snake$location3.x - 1, snake$location3.y);
        }

        if (flag)
        {
            this.devalue();
        }

        this.cooldown = false;
    }

    private Snake.Location getHead()
    {
        ArrayList<Snake.Location> arraylist = this.getSnake();
        return arraylist.size() == 0 ? new Snake.Location(0, 0, Snake.EnumPixelType.PIXEL) : (Snake.Location)arraylist.get(arraylist.size() - 1);
    }

    private void devalue()
    {
        if (this.snake != null)
        {
            this.snake.remove(0);
        }
    }

    private void setSpeed(int speed)
    {
        if (speed > 300)
        {
            speed = 300;
        }

        if (speed < 10)
        {
            speed = 10;
        }

        this.speed = speed;
    }

    private boolean expand(int x, int y)
    {
        Snake.Location snake$location = new Snake.Location(x, y, Snake.EnumPixelType.SNEAK);

        for (Snake.Location snake$location1 : this.getSnake())
        {
            if (snake$location1 != null && snake$location1.x == x && snake$location1.y == y)
            {
                this.gameOver();
                return false;
            }
        }

        for (Snake.Location snake$location2 : this.points)
        {
            if (snake$location2 != null && snake$location2.x == x && snake$location2.y == y)
            {
                this.addScore();
                this.addPoint();

                if (snake$location2.type == Snake.EnumPixelType.MOREFRUITS)
                {
                    this.addScore();
                    this.addPoint();
                }

                if (snake$location2.type == Snake.EnumPixelType.INCREASESPEED)
                {
                    this.addScore();
                    this.setSpeed(this.speed - LabyMod.random.nextInt(50));
                }

                if (snake$location2.type == Snake.EnumPixelType.DECREASESPEED)
                {
                    this.addScore();
                    this.setSpeed(this.speed + LabyMod.random.nextInt(50));
                }

                this.snake.add(snake$location);
                this.points.remove(snake$location2);
                return false;
            }
        }

        this.snake.add(snake$location);
        return true;
    }

    private void addScore()
    {
        this.score += 10;
    }

    public void addPoint()
    {
        Snake.EnumPixelType snake$enumpixeltype = Snake.EnumPixelType.FRUIT;
        int i = LabyMod.random.nextInt(this.powerUpAmount);

        if (i == 0)
        {
            snake$enumpixeltype = Snake.EnumPixelType.INCREASESPEED;
        }

        if (i == 1)
        {
            snake$enumpixeltype = Snake.EnumPixelType.DECREASESPEED;
        }

        if (i == 2)
        {
            snake$enumpixeltype = Snake.EnumPixelType.MOREFRUITS;
        }

        Snake.Location snake$location = new Snake.Location(LabyMod.random.nextInt(this.lengthX - this.posX) + this.posX, LabyMod.random.nextInt(this.lengthY - this.posY) + this.posY, snake$enumpixeltype);

        for (Snake.Location snake$location1 : this.points)
        {
            if (snake$location1 != null && snake$location1.x == snake$location.x && snake$location1.y == snake$location.y)
            {
                this.addPoint();
                return;
            }
        }

        this.points.add(snake$location);
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
            this.mc.displayGuiScreen(new Snake());
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
            if (!this.gameOver && !this.cooldown)
            {
                boolean flag = true;

                if ((keyCode == 200 || keyCode == 17) && this.snakeDirection != Snake.EnumDirection.DOWN && this.snakeDirection != Snake.EnumDirection.UP)
                {
                    this.snakeDirection = Snake.EnumDirection.UP;
                    this.cooldown = true;
                    flag = false;
                }

                if ((keyCode == 208 || keyCode == 31) && this.snakeDirection != Snake.EnumDirection.UP && this.snakeDirection != Snake.EnumDirection.DOWN)
                {
                    this.snakeDirection = Snake.EnumDirection.DOWN;
                    this.cooldown = true;
                    flag = false;
                }

                if ((keyCode == 205 || keyCode == 32) && this.snakeDirection != Snake.EnumDirection.LEFT && this.snakeDirection != Snake.EnumDirection.RIGHT)
                {
                    this.snakeDirection = Snake.EnumDirection.RIGHT;
                    this.cooldown = true;
                    flag = false;
                }

                if ((keyCode == 203 || keyCode == 30) && this.snakeDirection != Snake.EnumDirection.RIGHT && this.snakeDirection != Snake.EnumDirection.LEFT)
                {
                    this.snakeDirection = Snake.EnumDirection.LEFT;
                    this.cooldown = true;
                    flag = false;
                }

                if (flag && LabyMod.random.nextInt(3) == 0)
                {
                    this.tick();
                }
            }

            super.keyTyped(typedChar, keyCode);
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

        for (Snake.Location snake$location : this.getSnake())
        {
            if (snake$location != null)
            {
                this.drawPixel(snake$location.x, snake$location.y, java.awt.Color.WHITE.getRGB());
            }
        }

        for (Snake.Location snake$location1 : this.getPoints())
        {
            if (snake$location1 != null)
            {
                if (snake$location1.type == Snake.EnumPixelType.FRUIT)
                {
                    this.drawPixel(snake$location1.x, snake$location1.y, java.awt.Color.RED.getRGB());
                }

                if (snake$location1.type == Snake.EnumPixelType.MOREFRUITS)
                {
                    this.drawPixel(snake$location1.x, snake$location1.y, java.awt.Color.ORANGE.getRGB());
                }

                if (snake$location1.type == Snake.EnumPixelType.INCREASESPEED)
                {
                    this.drawPixel(snake$location1.x, snake$location1.y, java.awt.Color.CYAN.getRGB());
                }

                if (snake$location1.type == Snake.EnumPixelType.DECREASESPEED)
                {
                    this.drawPixel(snake$location1.x, snake$location1.y, java.awt.Color.BLUE.getRGB());
                }
            }
        }

        if (this.gameOver)
        {
            GL11.glPushMatrix();
            int j = 3;
            GL11.glScaled((double)j, (double)j, (double)j);
            this.draw.drawCenteredString(Color.cl("c") + "Game Over", this.width / 2 / j, (this.height / 4 - 5) / j);
            GL11.glPopMatrix();

            if (StatsLoader.stats.containsKey("snake"))
            {
                ArrayList<String> arraylist = (ArrayList)StatsLoader.stats.get("snake");
                int i = 1;

                for (String s : arraylist)
                {
                    this.draw.drawString(i + ". Place - " + Color.cl("b") + s + " Points", (double)(this.width / 2 - 50), (double)(this.height / 4 - 5 + i * 10 + 20));
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
        this.draw.drawString("Score: " + this.score, 5.0D, (double)(this.height - 24));
        this.draw.drawString("Speed: " + (double)this.speed / 1000.0D + " pixel/s", 5.0D, (double)(this.height - 13));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private ArrayList<Snake.Location> getSnake()
    {
        ArrayList<Snake.Location> arraylist = new ArrayList();
        arraylist.addAll(this.snake);
        return arraylist;
    }

    private ArrayList<Snake.Location> getPoints()
    {
        ArrayList<Snake.Location> arraylist = new ArrayList();
        arraylist.addAll(this.points);
        return arraylist;
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
        SNEAK,
        FRUIT,
        INCREASESPEED,
        DECREASESPEED,
        MOREFRUITS;
    }

    class Location
    {
        int x = 0;
        int y = 0;
        Snake.EnumPixelType type = Snake.EnumPixelType.PIXEL;

        public Location(int x, int y, Snake.EnumPixelType type)
        {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }
}
