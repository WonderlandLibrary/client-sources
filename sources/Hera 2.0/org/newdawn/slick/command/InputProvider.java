/*     */ package org.newdawn.slick.command;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.newdawn.slick.Input;
/*     */ import org.newdawn.slick.InputListener;
/*     */ import org.newdawn.slick.util.InputAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputProvider
/*     */ {
/*     */   private HashMap commands;
/*  25 */   private ArrayList listeners = new ArrayList();
/*     */ 
/*     */   
/*     */   private Input input;
/*     */ 
/*     */   
/*  31 */   private HashMap commandState = new HashMap<Object, Object>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean active = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputProvider(Input input) {
/*  44 */     this.input = input;
/*     */     
/*  46 */     input.addListener((InputListener)new InputListenerImpl());
/*  47 */     this.commands = new HashMap<Object, Object>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getUniqueCommands() {
/*  58 */     List<Command> uniqueCommands = new ArrayList();
/*     */     
/*  60 */     for (Iterator<Command> it = this.commands.values().iterator(); it.hasNext(); ) {
/*  61 */       Command command = it.next();
/*     */       
/*  63 */       if (!uniqueCommands.contains(command)) {
/*  64 */         uniqueCommands.add(command);
/*     */       }
/*     */     } 
/*     */     
/*  68 */     return uniqueCommands;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getControlsFor(Command command) {
/*  80 */     List<Control> controlsForCommand = new ArrayList();
/*     */     
/*  82 */     for (Iterator<Map.Entry> it = this.commands.entrySet().iterator(); it.hasNext(); ) {
/*  83 */       Map.Entry entry = it.next();
/*  84 */       Control key = (Control)entry.getKey();
/*  85 */       Command value = (Command)entry.getValue();
/*     */       
/*  87 */       if (value == command) {
/*  88 */         controlsForCommand.add(key);
/*     */       }
/*     */     } 
/*  91 */     return controlsForCommand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActive(boolean active) {
/* 101 */     this.active = active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 110 */     return this.active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListener(InputProviderListener listener) {
/* 121 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(InputProviderListener listener) {
/* 132 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindCommand(Control control, Command command) {
/* 144 */     this.commands.put(control, command);
/*     */     
/* 146 */     if (this.commandState.get(command) == null) {
/* 147 */       this.commandState.put(command, new CommandState());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearCommand(Command command) {
/* 157 */     List<Control> controls = getControlsFor(command);
/*     */     
/* 159 */     for (int i = 0; i < controls.size(); i++) {
/* 160 */       unbindCommand(controls.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unbindCommand(Control control) {
/* 171 */     Command command = (Command)this.commands.remove(control);
/* 172 */     if (command != null && 
/* 173 */       !this.commands.keySet().contains(command)) {
/* 174 */       this.commandState.remove(command);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CommandState getState(Command command) {
/* 187 */     return (CommandState)this.commandState.get(command);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCommandControlDown(Command command) {
/* 199 */     return getState(command).isDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCommandControlPressed(Command command) {
/* 211 */     return getState(command).isPressed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firePressed(Command command) {
/* 222 */     (getState(command)).down = true;
/* 223 */     (getState(command)).pressed = true;
/*     */     
/* 225 */     if (!isActive()) {
/*     */       return;
/*     */     }
/*     */     
/* 229 */     for (int i = 0; i < this.listeners.size(); i++) {
/* 230 */       ((InputProviderListener)this.listeners.get(i)).controlPressed(command);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireReleased(Command command) {
/* 242 */     (getState(command)).down = false;
/*     */     
/* 244 */     if (!isActive()) {
/*     */       return;
/*     */     }
/*     */     
/* 248 */     for (int i = 0; i < this.listeners.size(); i++) {
/* 249 */       ((InputProviderListener)this.listeners.get(i)).controlReleased(command);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class CommandState
/*     */   {
/*     */     private boolean down;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean pressed;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private CommandState() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isPressed() {
/* 272 */       if (this.pressed) {
/* 273 */         this.pressed = false;
/* 274 */         return true;
/*     */       } 
/*     */       
/* 277 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isDown() {
/* 286 */       return this.down;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class InputListenerImpl
/*     */     extends InputAdapter
/*     */   {
/*     */     private InputListenerImpl() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAcceptingInput() {
/* 300 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void keyPressed(int key, char c) {
/* 307 */       Command command = (Command)InputProvider.this.commands.get(new KeyControl(key));
/* 308 */       if (command != null) {
/* 309 */         InputProvider.this.firePressed(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void keyReleased(int key, char c) {
/* 317 */       Command command = (Command)InputProvider.this.commands.get(new KeyControl(key));
/* 318 */       if (command != null) {
/* 319 */         InputProvider.this.fireReleased(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mousePressed(int button, int x, int y) {
/* 327 */       Command command = (Command)InputProvider.this.commands.get(new MouseButtonControl(button));
/*     */       
/* 329 */       if (command != null) {
/* 330 */         InputProvider.this.firePressed(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(int button, int x, int y) {
/* 338 */       Command command = (Command)InputProvider.this.commands.get(new MouseButtonControl(button));
/*     */       
/* 340 */       if (command != null) {
/* 341 */         InputProvider.this.fireReleased(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void controllerLeftPressed(int controller) {
/* 349 */       Command command = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, ControllerDirectionControl.LEFT));
/*     */ 
/*     */       
/* 352 */       if (command != null) {
/* 353 */         InputProvider.this.firePressed(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void controllerLeftReleased(int controller) {
/* 361 */       Command command = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, ControllerDirectionControl.LEFT));
/*     */ 
/*     */       
/* 364 */       if (command != null) {
/* 365 */         InputProvider.this.fireReleased(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void controllerRightPressed(int controller) {
/* 373 */       Command command = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, ControllerDirectionControl.RIGHT));
/*     */ 
/*     */       
/* 376 */       if (command != null) {
/* 377 */         InputProvider.this.firePressed(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void controllerRightReleased(int controller) {
/* 385 */       Command command = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, ControllerDirectionControl.RIGHT));
/*     */ 
/*     */       
/* 388 */       if (command != null) {
/* 389 */         InputProvider.this.fireReleased(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void controllerUpPressed(int controller) {
/* 397 */       Command command = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, ControllerDirectionControl.UP));
/*     */ 
/*     */       
/* 400 */       if (command != null) {
/* 401 */         InputProvider.this.firePressed(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void controllerUpReleased(int controller) {
/* 408 */       Command command = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, ControllerDirectionControl.UP));
/*     */ 
/*     */       
/* 411 */       if (command != null) {
/* 412 */         InputProvider.this.fireReleased(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void controllerDownPressed(int controller) {
/* 420 */       Command command = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, ControllerDirectionControl.DOWN));
/*     */ 
/*     */       
/* 423 */       if (command != null) {
/* 424 */         InputProvider.this.firePressed(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void controllerDownReleased(int controller) {
/* 432 */       Command command = (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, ControllerDirectionControl.DOWN));
/*     */ 
/*     */       
/* 435 */       if (command != null) {
/* 436 */         InputProvider.this.fireReleased(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void controllerButtonPressed(int controller, int button) {
/* 445 */       Command command = (Command)InputProvider.this.commands.get(new ControllerButtonControl(controller, button));
/*     */       
/* 447 */       if (command != null) {
/* 448 */         InputProvider.this.firePressed(command);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void controllerButtonReleased(int controller, int button) {
/* 457 */       Command command = (Command)InputProvider.this.commands.get(new ControllerButtonControl(controller, button));
/*     */       
/* 459 */       if (command != null)
/* 460 */         InputProvider.this.fireReleased(command); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\command\InputProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */