/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.AppGameContainer;
/*  4:   */ import org.newdawn.slick.BasicGame;
/*  5:   */ import org.newdawn.slick.GameContainer;
/*  6:   */ import org.newdawn.slick.Graphics;
/*  7:   */ import org.newdawn.slick.SlickException;
/*  8:   */ import org.newdawn.slick.command.BasicCommand;
/*  9:   */ import org.newdawn.slick.command.Command;
/* 10:   */ import org.newdawn.slick.command.ControllerButtonControl;
/* 11:   */ import org.newdawn.slick.command.ControllerDirectionControl;
/* 12:   */ import org.newdawn.slick.command.InputProvider;
/* 13:   */ import org.newdawn.slick.command.InputProviderListener;
/* 14:   */ import org.newdawn.slick.command.KeyControl;
/* 15:   */ import org.newdawn.slick.command.MouseButtonControl;
/* 16:   */ 
/* 17:   */ public class InputProviderTest
/* 18:   */   extends BasicGame
/* 19:   */   implements InputProviderListener
/* 20:   */ {
/* 21:25 */   private Command attack = new BasicCommand("attack");
/* 22:27 */   private Command jump = new BasicCommand("jump");
/* 23:29 */   private Command run = new BasicCommand("run");
/* 24:   */   private InputProvider provider;
/* 25:33 */   private String message = "";
/* 26:   */   
/* 27:   */   public InputProviderTest()
/* 28:   */   {
/* 29:39 */     super("InputProvider Test");
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void init(GameContainer container)
/* 33:   */     throws SlickException
/* 34:   */   {
/* 35:46 */     this.provider = new InputProvider(container.getInput());
/* 36:47 */     this.provider.addListener(this);
/* 37:   */     
/* 38:49 */     this.provider.bindCommand(new KeyControl(203), this.run);
/* 39:50 */     this.provider.bindCommand(new KeyControl(30), this.run);
/* 40:51 */     this.provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), this.run);
/* 41:52 */     this.provider.bindCommand(new KeyControl(200), this.jump);
/* 42:53 */     this.provider.bindCommand(new KeyControl(17), this.jump);
/* 43:54 */     this.provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), this.jump);
/* 44:55 */     this.provider.bindCommand(new KeyControl(57), this.attack);
/* 45:56 */     this.provider.bindCommand(new MouseButtonControl(0), this.attack);
/* 46:57 */     this.provider.bindCommand(new ControllerButtonControl(0, 1), this.attack);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void render(GameContainer container, Graphics g)
/* 50:   */   {
/* 51:64 */     g.drawString("Press A, W, Left, Up, space, mouse button 1,and gamepad controls", 10.0F, 50.0F);
/* 52:65 */     g.drawString(this.message, 100.0F, 150.0F);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void update(GameContainer container, int delta) {}
/* 56:   */   
/* 57:   */   public void controlPressed(Command command)
/* 58:   */   {
/* 59:78 */     this.message = ("Pressed: " + command);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void controlReleased(Command command)
/* 63:   */   {
/* 64:85 */     this.message = ("Released: " + command);
/* 65:   */   }
/* 66:   */   
/* 67:   */   public static void main(String[] argv)
/* 68:   */   {
/* 69:   */     try
/* 70:   */     {
/* 71:95 */       AppGameContainer container = new AppGameContainer(new InputProviderTest());
/* 72:96 */       container.setDisplayMode(800, 600, false);
/* 73:97 */       container.start();
/* 74:   */     }
/* 75:   */     catch (SlickException e)
/* 76:   */     {
/* 77:99 */       e.printStackTrace();
/* 78:   */     }
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.InputProviderTest
 * JD-Core Version:    0.7.0.1
 */