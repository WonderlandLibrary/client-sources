/*     */ package org.spongepowered.asm.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.Opcodes;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.lib.tree.FrameNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.LabelNode;
/*     */ import org.spongepowered.asm.lib.tree.LocalVariableNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.analysis.Analyzer;
/*     */ import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
/*     */ import org.spongepowered.asm.lib.tree.analysis.BasicValue;
/*     */ import org.spongepowered.asm.lib.tree.analysis.Frame;
/*     */ import org.spongepowered.asm.lib.tree.analysis.Interpreter;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
/*     */ import org.spongepowered.asm.util.asm.MixinVerifier;
/*     */ import org.spongepowered.asm.util.throwables.LVTGeneratorException;
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
/*     */ 
/*     */ 
/*     */ public final class Locals
/*     */ {
/*  63 */   private static final Map<String, List<LocalVariableNode>> calculatedLocalVariables = new HashMap<String, List<LocalVariableNode>>();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadLocals(Type[] locals, InsnList insns, int pos, int limit) {
/*  80 */     for (; pos < locals.length && limit > 0; pos++) {
/*  81 */       if (locals[pos] != null) {
/*  82 */         insns.add((AbstractInsnNode)new VarInsnNode(locals[pos].getOpcode(21), pos));
/*  83 */         limit--;
/*     */       } 
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
/*     */   
/*     */   public static LocalVariableNode[] getLocalsAt(ClassNode classNode, MethodNode method, AbstractInsnNode node) {
/* 131 */     for (int i = 0; i < 3 && (node instanceof LabelNode || node instanceof org.spongepowered.asm.lib.tree.LineNumberNode); i++) {
/* 132 */       node = nextNode(method.instructions, node);
/*     */     }
/*     */     
/* 135 */     ClassInfo classInfo = ClassInfo.forName(classNode.name);
/* 136 */     if (classInfo == null) {
/* 137 */       throw new LVTGeneratorException("Could not load class metadata for " + classNode.name + " generating LVT for " + method.name);
/*     */     }
/* 139 */     ClassInfo.Method methodInfo = classInfo.findMethod(method);
/* 140 */     if (methodInfo == null) {
/* 141 */       throw new LVTGeneratorException("Could not locate method metadata for " + method.name + " generating LVT in " + classNode.name);
/*     */     }
/* 143 */     List<ClassInfo.FrameData> frames = methodInfo.getFrames();
/*     */     
/* 145 */     LocalVariableNode[] frame = new LocalVariableNode[method.maxLocals];
/* 146 */     int local = 0, index = 0;
/*     */ 
/*     */     
/* 149 */     if ((method.access & 0x8) == 0) {
/* 150 */       frame[local++] = new LocalVariableNode("this", classNode.name, null, null, null, 0);
/*     */     }
/*     */ 
/*     */     
/* 154 */     for (Type argType : Type.getArgumentTypes(method.desc)) {
/* 155 */       frame[local] = new LocalVariableNode("arg" + index++, argType.toString(), null, null, null, local);
/* 156 */       local += argType.getSize();
/*     */     } 
/*     */     
/* 159 */     int initialFrameSize = local;
/* 160 */     int frameIndex = -1, locals = 0;
/*     */     
/* 162 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/* 163 */       AbstractInsnNode insn = iter.next();
/* 164 */       if (insn instanceof FrameNode) {
/* 165 */         frameIndex++;
/* 166 */         FrameNode frameNode = (FrameNode)insn;
/* 167 */         ClassInfo.FrameData frameData = (frameIndex < frames.size()) ? frames.get(frameIndex) : null;
/*     */         
/* 169 */         locals = (frameData != null && frameData.type == 0) ? Math.min(locals, frameData.locals) : frameNode.local.size();
/*     */ 
/*     */         
/* 172 */         for (int localPos = 0, framePos = 0; framePos < frame.length; framePos++, localPos++) {
/*     */           
/* 174 */           Object localType = (localPos < frameNode.local.size()) ? frameNode.local.get(localPos) : null;
/*     */           
/* 176 */           if (localType instanceof String) {
/* 177 */             frame[framePos] = getLocalVariableAt(classNode, method, node, framePos);
/* 178 */           } else if (localType instanceof Integer) {
/* 179 */             boolean isMarkerType = (localType == Opcodes.UNINITIALIZED_THIS || localType == Opcodes.NULL);
/* 180 */             boolean is32bitValue = (localType == Opcodes.INTEGER || localType == Opcodes.FLOAT);
/* 181 */             boolean is64bitValue = (localType == Opcodes.DOUBLE || localType == Opcodes.LONG);
/* 182 */             if (localType != Opcodes.TOP)
/*     */             {
/* 184 */               if (isMarkerType) {
/* 185 */                 frame[framePos] = null;
/* 186 */               } else if (is32bitValue || is64bitValue) {
/* 187 */                 frame[framePos] = getLocalVariableAt(classNode, method, node, framePos);
/*     */                 
/* 189 */                 if (is64bitValue) {
/* 190 */                   framePos++;
/* 191 */                   frame[framePos] = null;
/*     */                 } 
/*     */               } else {
/* 194 */                 throw new LVTGeneratorException("Unrecognised locals opcode " + localType + " in locals array at position " + localPos + " in " + classNode.name + "." + method.name + method.desc);
/*     */               } 
/*     */             }
/* 197 */           } else if (localType == null) {
/* 198 */             if (framePos >= initialFrameSize && framePos >= locals && locals > 0) {
/* 199 */               frame[framePos] = null;
/*     */             }
/*     */           } else {
/* 202 */             throw new LVTGeneratorException("Invalid value " + localType + " in locals array at position " + localPos + " in " + classNode.name + "." + method.name + method.desc);
/*     */           }
/*     */         
/*     */         } 
/* 206 */       } else if (insn instanceof VarInsnNode) {
/* 207 */         VarInsnNode varNode = (VarInsnNode)insn;
/* 208 */         frame[varNode.var] = getLocalVariableAt(classNode, method, node, varNode.var);
/*     */       } 
/*     */       
/* 211 */       if (insn == node) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 217 */     for (int l = 0; l < frame.length; l++) {
/* 218 */       if (frame[l] != null && (frame[l]).desc == null) {
/* 219 */         frame[l] = null;
/*     */       }
/*     */     } 
/*     */     
/* 223 */     return frame;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LocalVariableNode getLocalVariableAt(ClassNode classNode, MethodNode method, AbstractInsnNode node, int var) {
/* 239 */     return getLocalVariableAt(classNode, method, method.instructions.indexOf(node), var);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static LocalVariableNode getLocalVariableAt(ClassNode classNode, MethodNode method, int pos, int var) {
/* 254 */     LocalVariableNode localVariableNode = null;
/* 255 */     LocalVariableNode fallbackNode = null;
/*     */     
/* 257 */     for (LocalVariableNode local : getLocalVariableTable(classNode, method)) {
/* 258 */       if (local.index != var) {
/*     */         continue;
/*     */       }
/* 261 */       if (isOpcodeInRange(method.instructions, local, pos)) {
/* 262 */         localVariableNode = local; continue;
/* 263 */       }  if (localVariableNode == null) {
/* 264 */         fallbackNode = local;
/*     */       }
/*     */     } 
/*     */     
/* 268 */     if (localVariableNode == null && !method.localVariables.isEmpty()) {
/* 269 */       for (LocalVariableNode local : getGeneratedLocalVariableTable(classNode, method)) {
/* 270 */         if (local.index == var && isOpcodeInRange(method.instructions, local, pos)) {
/* 271 */           localVariableNode = local;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 276 */     return (localVariableNode != null) ? localVariableNode : fallbackNode;
/*     */   }
/*     */   
/*     */   private static boolean isOpcodeInRange(InsnList insns, LocalVariableNode local, int pos) {
/* 280 */     return (insns.indexOf((AbstractInsnNode)local.start) < pos && insns.indexOf((AbstractInsnNode)local.end) > pos);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<LocalVariableNode> getLocalVariableTable(ClassNode classNode, MethodNode method) {
/* 295 */     if (method.localVariables.isEmpty()) {
/* 296 */       return getGeneratedLocalVariableTable(classNode, method);
/*     */     }
/* 298 */     return method.localVariables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<LocalVariableNode> getGeneratedLocalVariableTable(ClassNode classNode, MethodNode method) {
/* 309 */     String methodId = String.format("%s.%s%s", new Object[] { classNode.name, method.name, method.desc });
/* 310 */     List<LocalVariableNode> localVars = calculatedLocalVariables.get(methodId);
/* 311 */     if (localVars != null) {
/* 312 */       return localVars;
/*     */     }
/*     */     
/* 315 */     localVars = generateLocalVariableTable(classNode, method);
/* 316 */     calculatedLocalVariables.put(methodId, localVars);
/* 317 */     return localVars;
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
/*     */   public static List<LocalVariableNode> generateLocalVariableTable(ClassNode classNode, MethodNode method) {
/* 329 */     List<Type> interfaces = null;
/* 330 */     if (classNode.interfaces != null) {
/* 331 */       interfaces = new ArrayList<Type>();
/* 332 */       for (String iface : classNode.interfaces) {
/* 333 */         interfaces.add(Type.getObjectType(iface));
/*     */       }
/*     */     } 
/*     */     
/* 337 */     Type objectType = null;
/* 338 */     if (classNode.superName != null) {
/* 339 */       objectType = Type.getObjectType(classNode.superName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 344 */     Analyzer<BasicValue> analyzer = new Analyzer((Interpreter)new MixinVerifier(Type.getObjectType(classNode.name), objectType, interfaces, false));
/*     */     try {
/* 346 */       analyzer.analyze(classNode.name, method);
/* 347 */     } catch (AnalyzerException ex) {
/* 348 */       ex.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 352 */     Frame[] arrayOfFrame = analyzer.getFrames();
/*     */ 
/*     */     
/* 355 */     int methodSize = method.instructions.size();
/*     */ 
/*     */     
/* 358 */     List<LocalVariableNode> localVariables = new ArrayList<LocalVariableNode>();
/*     */     
/* 360 */     LocalVariableNode[] localNodes = new LocalVariableNode[method.maxLocals];
/* 361 */     BasicValue[] locals = new BasicValue[method.maxLocals];
/* 362 */     LabelNode[] labels = new LabelNode[methodSize];
/* 363 */     String[] lastKnownType = new String[method.maxLocals];
/*     */ 
/*     */     
/* 366 */     for (int i = 0; i < methodSize; i++) {
/* 367 */       Frame<BasicValue> f = arrayOfFrame[i];
/* 368 */       if (f != null) {
/*     */ 
/*     */         
/* 371 */         LabelNode labelNode = null;
/*     */         
/* 373 */         for (int j = 0; j < f.getLocals(); j++) {
/* 374 */           BasicValue local = (BasicValue)f.getLocal(j);
/* 375 */           if (local != null || locals[j] != null)
/*     */           {
/*     */             
/* 378 */             if (local == null || !local.equals(locals[j])) {
/*     */ 
/*     */ 
/*     */               
/* 382 */               if (labelNode == null) {
/* 383 */                 AbstractInsnNode existingLabel = method.instructions.get(i);
/* 384 */                 if (existingLabel instanceof LabelNode) {
/* 385 */                   labelNode = (LabelNode)existingLabel;
/*     */                 } else {
/* 387 */                   labels[i] = labelNode = new LabelNode();
/*     */                 } 
/*     */               } 
/*     */               
/* 391 */               if (local == null && locals[j] != null) {
/* 392 */                 localVariables.add(localNodes[j]);
/* 393 */                 (localNodes[j]).end = labelNode;
/* 394 */                 localNodes[j] = null;
/* 395 */               } else if (local != null) {
/* 396 */                 if (locals[j] != null) {
/* 397 */                   localVariables.add(localNodes[j]);
/* 398 */                   (localNodes[j]).end = labelNode;
/* 399 */                   localNodes[j] = null;
/*     */                 } 
/*     */                 
/* 402 */                 String desc = (local.getType() != null) ? local.getType().getDescriptor() : lastKnownType[j];
/* 403 */                 localNodes[j] = new LocalVariableNode("var" + j, desc, null, labelNode, null, j);
/* 404 */                 if (desc != null) {
/* 405 */                   lastKnownType[j] = desc;
/*     */                 }
/*     */               } 
/*     */               
/* 409 */               locals[j] = local;
/*     */             }  } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 414 */     LabelNode label = null;
/* 415 */     for (int k = 0; k < localNodes.length; k++) {
/* 416 */       if (localNodes[k] != null) {
/* 417 */         if (label == null) {
/* 418 */           label = new LabelNode();
/* 419 */           method.instructions.add((AbstractInsnNode)label);
/*     */         } 
/*     */         
/* 422 */         (localNodes[k]).end = label;
/* 423 */         localVariables.add(localNodes[k]);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 428 */     for (int n = methodSize - 1; n >= 0; n--) {
/* 429 */       if (labels[n] != null) {
/* 430 */         method.instructions.insert(method.instructions.get(n), (AbstractInsnNode)labels[n]);
/*     */       }
/*     */     } 
/*     */     
/* 434 */     return localVariables;
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
/*     */   private static AbstractInsnNode nextNode(InsnList insns, AbstractInsnNode insn) {
/* 446 */     int index = insns.indexOf(insn) + 1;
/* 447 */     if (index > 0 && index < insns.size()) {
/* 448 */       return insns.get(index);
/*     */     }
/* 450 */     return insn;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\Locals.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */