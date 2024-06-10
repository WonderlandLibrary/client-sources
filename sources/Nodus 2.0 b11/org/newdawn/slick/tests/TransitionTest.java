/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.Color;
/*   5:    */ import org.newdawn.slick.GameContainer;
/*   6:    */ import org.newdawn.slick.Graphics;
/*   7:    */ import org.newdawn.slick.Image;
/*   8:    */ import org.newdawn.slick.Input;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.state.BasicGameState;
/*  11:    */ import org.newdawn.slick.state.StateBasedGame;
/*  12:    */ import org.newdawn.slick.state.transition.BlobbyTransition;
/*  13:    */ import org.newdawn.slick.state.transition.FadeInTransition;
/*  14:    */ import org.newdawn.slick.state.transition.FadeOutTransition;
/*  15:    */ import org.newdawn.slick.state.transition.HorizontalSplitTransition;
/*  16:    */ import org.newdawn.slick.state.transition.RotateTransition;
/*  17:    */ import org.newdawn.slick.state.transition.SelectTransition;
/*  18:    */ import org.newdawn.slick.state.transition.Transition;
/*  19:    */ import org.newdawn.slick.state.transition.VerticalSplitTransition;
/*  20:    */ import org.newdawn.slick.util.Log;
/*  21:    */ 
/*  22:    */ public class TransitionTest
/*  23:    */   extends StateBasedGame
/*  24:    */ {
/*  25: 29 */   private Class[][] transitions = {
/*  26: 30 */     { 0, VerticalSplitTransition.class }, 
/*  27: 31 */     { FadeOutTransition.class, FadeInTransition.class }, 
/*  28: 32 */     { 0, RotateTransition.class }, 
/*  29: 33 */     { 0, HorizontalSplitTransition.class }, 
/*  30: 34 */     { 0, BlobbyTransition.class }, 
/*  31: 35 */     { 0, SelectTransition.class } };
/*  32:    */   private int index;
/*  33:    */   
/*  34:    */   public TransitionTest()
/*  35:    */   {
/*  36: 44 */     super("Transition Test - Hit Space To Transition");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void initStatesList(GameContainer container)
/*  40:    */     throws SlickException
/*  41:    */   {
/*  42: 51 */     addState(new ImageState(0, "testdata/wallpaper/paper1.png", 1));
/*  43: 52 */     addState(new ImageState(1, "testdata/wallpaper/paper2.png", 2));
/*  44: 53 */     addState(new ImageState(2, "testdata/bigimage.tga", 0));
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Transition[] getNextTransitionPair()
/*  48:    */   {
/*  49: 62 */     Transition[] pair = new Transition[2];
/*  50:    */     try
/*  51:    */     {
/*  52: 65 */       if (this.transitions[this.index][0] != null) {
/*  53: 66 */         pair[0] = ((Transition)this.transitions[this.index][0].newInstance());
/*  54:    */       }
/*  55: 68 */       if (this.transitions[this.index][1] != null) {
/*  56: 69 */         pair[1] = ((Transition)this.transitions[this.index][1].newInstance());
/*  57:    */       }
/*  58:    */     }
/*  59:    */     catch (Throwable e)
/*  60:    */     {
/*  61: 72 */       Log.error(e);
/*  62:    */     }
/*  63: 75 */     this.index += 1;
/*  64: 76 */     if (this.index >= this.transitions.length) {
/*  65: 77 */       this.index = 0;
/*  66:    */     }
/*  67: 80 */     return pair;
/*  68:    */   }
/*  69:    */   
/*  70:    */   private class ImageState
/*  71:    */     extends BasicGameState
/*  72:    */   {
/*  73:    */     private int id;
/*  74:    */     private int next;
/*  75:    */     private String ref;
/*  76:    */     private Image image;
/*  77:    */     
/*  78:    */     public ImageState(int id, String ref, int next)
/*  79:    */     {
/*  80:106 */       this.ref = ref;
/*  81:107 */       this.id = id;
/*  82:108 */       this.next = next;
/*  83:    */     }
/*  84:    */     
/*  85:    */     public int getID()
/*  86:    */     {
/*  87:115 */       return this.id;
/*  88:    */     }
/*  89:    */     
/*  90:    */     public void init(GameContainer container, StateBasedGame game)
/*  91:    */       throws SlickException
/*  92:    */     {
/*  93:122 */       this.image = new Image(this.ref);
/*  94:    */     }
/*  95:    */     
/*  96:    */     public void render(GameContainer container, StateBasedGame game, Graphics g)
/*  97:    */       throws SlickException
/*  98:    */     {
/*  99:129 */       this.image.draw(0.0F, 0.0F, 800.0F, 600.0F);
/* 100:130 */       g.setColor(Color.red);
/* 101:131 */       g.fillRect(-50.0F, 200.0F, 50.0F, 50.0F);
/* 102:    */     }
/* 103:    */     
/* 104:    */     public void update(GameContainer container, StateBasedGame game, int delta)
/* 105:    */       throws SlickException
/* 106:    */     {
/* 107:138 */       if (container.getInput().isKeyPressed(57))
/* 108:    */       {
/* 109:139 */         Transition[] pair = TransitionTest.this.getNextTransitionPair();
/* 110:140 */         game.enterState(this.next, pair[0], pair[1]);
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static void main(String[] argv)
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:153 */       AppGameContainer container = new AppGameContainer(
/* 120:154 */         new TransitionTest());
/* 121:155 */       container.setDisplayMode(800, 600, false);
/* 122:156 */       container.start();
/* 123:    */     }
/* 124:    */     catch (SlickException e)
/* 125:    */     {
/* 126:158 */       e.printStackTrace();
/* 127:    */     }
/* 128:    */   }
/* 129:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.TransitionTest
 * JD-Core Version:    0.7.0.1
 */