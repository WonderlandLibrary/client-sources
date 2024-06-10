/*   1:    */ package org.newdawn.slick.command;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.newdawn.slick.Input;
/*  11:    */ import org.newdawn.slick.util.InputAdapter;
/*  12:    */ 
/*  13:    */ public class InputProvider
/*  14:    */ {
/*  15:    */   private HashMap commands;
/*  16: 25 */   private ArrayList listeners = new ArrayList();
/*  17:    */   private Input input;
/*  18: 31 */   private HashMap commandState = new HashMap();
/*  19: 34 */   private boolean active = true;
/*  20:    */   
/*  21:    */   public InputProvider(Input input)
/*  22:    */   {
/*  23: 44 */     this.input = input;
/*  24:    */     
/*  25: 46 */     input.addListener(new InputListenerImpl(null));
/*  26: 47 */     this.commands = new HashMap();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public List getUniqueCommands()
/*  30:    */   {
/*  31: 58 */     List uniqueCommands = new ArrayList();
/*  32: 60 */     for (Iterator it = this.commands.values().iterator(); it.hasNext();)
/*  33:    */     {
/*  34: 61 */       Command command = (Command)it.next();
/*  35: 63 */       if (!uniqueCommands.contains(command)) {
/*  36: 64 */         uniqueCommands.add(command);
/*  37:    */       }
/*  38:    */     }
/*  39: 68 */     return uniqueCommands;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public List getControlsFor(Command command)
/*  43:    */   {
/*  44: 80 */     List controlsForCommand = new ArrayList();
/*  45: 82 */     for (Iterator it = this.commands.entrySet().iterator(); it.hasNext();)
/*  46:    */     {
/*  47: 83 */       Map.Entry entry = (Map.Entry)it.next();
/*  48: 84 */       Control key = (Control)entry.getKey();
/*  49: 85 */       Command value = (Command)entry.getValue();
/*  50: 87 */       if (value == command) {
/*  51: 88 */         controlsForCommand.add(key);
/*  52:    */       }
/*  53:    */     }
/*  54: 91 */     return controlsForCommand;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setActive(boolean active)
/*  58:    */   {
/*  59:101 */     this.active = active;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isActive()
/*  63:    */   {
/*  64:110 */     return this.active;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void addListener(InputProviderListener listener)
/*  68:    */   {
/*  69:121 */     this.listeners.add(listener);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void removeListener(InputProviderListener listener)
/*  73:    */   {
/*  74:132 */     this.listeners.remove(listener);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void bindCommand(Control control, Command command)
/*  78:    */   {
/*  79:144 */     this.commands.put(control, command);
/*  80:146 */     if (this.commandState.get(command) == null) {
/*  81:147 */       this.commandState.put(command, new CommandState(null));
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void clearCommand(Command command)
/*  86:    */   {
/*  87:157 */     List controls = getControlsFor(command);
/*  88:159 */     for (int i = 0; i < controls.size(); i++) {
/*  89:160 */       unbindCommand((Control)controls.get(i));
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void unbindCommand(Control control)
/*  94:    */   {
/*  95:171 */     Command command = (Command)this.commands.remove(control);
/*  96:172 */     if ((command != null) && 
/*  97:173 */       (!this.commands.keySet().contains(command))) {
/*  98:174 */       this.commandState.remove(command);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   private CommandState getState(Command command)
/* 103:    */   {
/* 104:187 */     return (CommandState)this.commandState.get(command);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean isCommandControlDown(Command command)
/* 108:    */   {
/* 109:199 */     return getState(command).isDown();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isCommandControlPressed(Command command)
/* 113:    */   {
/* 114:211 */     return getState(command).isPressed();
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected void firePressed(Command command)
/* 118:    */   {
/* 119:222 */     getState(command).down = true;
/* 120:223 */     getState(command).pressed = true;
/* 121:225 */     if (!isActive()) {
/* 122:226 */       return;
/* 123:    */     }
/* 124:229 */     for (int i = 0; i < this.listeners.size(); i++) {
/* 125:230 */       ((InputProviderListener)this.listeners.get(i)).controlPressed(command);
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected void fireReleased(Command command)
/* 130:    */   {
/* 131:242 */     getState(command).down = false;
/* 132:244 */     if (!isActive()) {
/* 133:245 */       return;
/* 134:    */     }
/* 135:248 */     for (int i = 0; i < this.listeners.size(); i++) {
/* 136:249 */       ((InputProviderListener)this.listeners.get(i)).controlReleased(command);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   private class CommandState
/* 141:    */   {
/* 142:    */     private boolean down;
/* 143:    */     private boolean pressed;
/* 144:    */     
/* 145:    */     private CommandState() {}
/* 146:    */     
/* 147:    */     public boolean isPressed()
/* 148:    */     {
/* 149:272 */       if (this.pressed)
/* 150:    */       {
/* 151:273 */         this.pressed = false;
/* 152:274 */         return true;
/* 153:    */       }
/* 154:277 */       return false;
/* 155:    */     }
/* 156:    */     
/* 157:    */     public boolean isDown()
/* 158:    */     {
/* 159:286 */       return this.down;
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   private class InputListenerImpl
/* 164:    */     extends InputAdapter
/* 165:    */   {
/* 166:    */     private InputListenerImpl() {}
/* 167:    */     
/* 168:    */     public boolean isAcceptingInput()
/* 169:    */     {
/* 170:300 */       return true;
/* 171:    */     }
/* 172:    */     
/* 173:    */     public void keyPressed(int key, char c)
/* 174:    */     {
/* 175:307 */       Command command = (Command)InputProvider.this.commands.get(new KeyControl(key));
/* 176:308 */       if (command != null) {
/* 177:309 */         InputProvider.this.firePressed(command);
/* 178:    */       }
/* 179:    */     }
/* 180:    */     
/* 181:    */     public void keyReleased(int key, char c)
/* 182:    */     {
/* 183:317 */       Command command = (Command)InputProvider.this.commands.get(new KeyControl(key));
/* 184:318 */       if (command != null) {
/* 185:319 */         InputProvider.this.fireReleased(command);
/* 186:    */       }
/* 187:    */     }
/* 188:    */     
/* 189:    */     public void mousePressed(int button, int x, int y)
/* 190:    */     {
/* 191:327 */       Command command = (Command)InputProvider.this.commands.get(new MouseButtonControl(
/* 192:328 */         button));
/* 193:329 */       if (command != null) {
/* 194:330 */         InputProvider.this.firePressed(command);
/* 195:    */       }
/* 196:    */     }
/* 197:    */     
/* 198:    */     public void mouseReleased(int button, int x, int y)
/* 199:    */     {
/* 200:338 */       Command command = (Command)InputProvider.this.commands.get(new MouseButtonControl(
/* 201:339 */         button));
/* 202:340 */       if (command != null) {
/* 203:341 */         InputProvider.this.fireReleased(command);
/* 204:    */       }
/* 205:    */     }
/* 206:    */     
/* 207:    */     public void controllerLeftPressed(int controller)
/* 208:    */     {
/* 209:349 */       Command command = 
/* 210:350 */         (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, 
/* 211:351 */         ControllerDirectionControl.LEFT));
/* 212:352 */       if (command != null) {
/* 213:353 */         InputProvider.this.firePressed(command);
/* 214:    */       }
/* 215:    */     }
/* 216:    */     
/* 217:    */     public void controllerLeftReleased(int controller)
/* 218:    */     {
/* 219:361 */       Command command = 
/* 220:362 */         (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, 
/* 221:363 */         ControllerDirectionControl.LEFT));
/* 222:364 */       if (command != null) {
/* 223:365 */         InputProvider.this.fireReleased(command);
/* 224:    */       }
/* 225:    */     }
/* 226:    */     
/* 227:    */     public void controllerRightPressed(int controller)
/* 228:    */     {
/* 229:373 */       Command command = 
/* 230:374 */         (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, 
/* 231:375 */         ControllerDirectionControl.RIGHT));
/* 232:376 */       if (command != null) {
/* 233:377 */         InputProvider.this.firePressed(command);
/* 234:    */       }
/* 235:    */     }
/* 236:    */     
/* 237:    */     public void controllerRightReleased(int controller)
/* 238:    */     {
/* 239:385 */       Command command = 
/* 240:386 */         (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, 
/* 241:387 */         ControllerDirectionControl.RIGHT));
/* 242:388 */       if (command != null) {
/* 243:389 */         InputProvider.this.fireReleased(command);
/* 244:    */       }
/* 245:    */     }
/* 246:    */     
/* 247:    */     public void controllerUpPressed(int controller)
/* 248:    */     {
/* 249:397 */       Command command = 
/* 250:398 */         (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, 
/* 251:399 */         ControllerDirectionControl.UP));
/* 252:400 */       if (command != null) {
/* 253:401 */         InputProvider.this.firePressed(command);
/* 254:    */       }
/* 255:    */     }
/* 256:    */     
/* 257:    */     public void controllerUpReleased(int controller)
/* 258:    */     {
/* 259:408 */       Command command = 
/* 260:409 */         (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, 
/* 261:410 */         ControllerDirectionControl.UP));
/* 262:411 */       if (command != null) {
/* 263:412 */         InputProvider.this.fireReleased(command);
/* 264:    */       }
/* 265:    */     }
/* 266:    */     
/* 267:    */     public void controllerDownPressed(int controller)
/* 268:    */     {
/* 269:420 */       Command command = 
/* 270:421 */         (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, 
/* 271:422 */         ControllerDirectionControl.DOWN));
/* 272:423 */       if (command != null) {
/* 273:424 */         InputProvider.this.firePressed(command);
/* 274:    */       }
/* 275:    */     }
/* 276:    */     
/* 277:    */     public void controllerDownReleased(int controller)
/* 278:    */     {
/* 279:432 */       Command command = 
/* 280:433 */         (Command)InputProvider.this.commands.get(new ControllerDirectionControl(controller, 
/* 281:434 */         ControllerDirectionControl.DOWN));
/* 282:435 */       if (command != null) {
/* 283:436 */         InputProvider.this.fireReleased(command);
/* 284:    */       }
/* 285:    */     }
/* 286:    */     
/* 287:    */     public void controllerButtonPressed(int controller, int button)
/* 288:    */     {
/* 289:445 */       Command command = 
/* 290:446 */         (Command)InputProvider.this.commands.get(new ControllerButtonControl(controller, button));
/* 291:447 */       if (command != null) {
/* 292:448 */         InputProvider.this.firePressed(command);
/* 293:    */       }
/* 294:    */     }
/* 295:    */     
/* 296:    */     public void controllerButtonReleased(int controller, int button)
/* 297:    */     {
/* 298:457 */       Command command = 
/* 299:458 */         (Command)InputProvider.this.commands.get(new ControllerButtonControl(controller, button));
/* 300:459 */       if (command != null) {
/* 301:460 */         InputProvider.this.fireReleased(command);
/* 302:    */       }
/* 303:    */     }
/* 304:    */   }
/* 305:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.command.InputProvider
 * JD-Core Version:    0.7.0.1
 */