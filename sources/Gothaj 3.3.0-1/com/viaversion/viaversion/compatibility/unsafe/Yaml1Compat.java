package com.viaversion.viaversion.compatibility.unsafe;

import com.viaversion.viaversion.compatibility.YamlCompat;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.representer.Representer;

public final class Yaml1Compat implements YamlCompat {
   @Override
   public Representer createRepresenter(DumperOptions dumperOptions) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: Constructor org/yaml/snakeyaml/representer/Representer.<init>()V not found
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:49)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:957)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:460)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1018)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExitExprent.toJava(ExitExprent.java:86)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 0: new org/yaml/snakeyaml/representer/Representer
      // 3: dup
      // 4: invokespecial org/yaml/snakeyaml/representer/Representer.<init> ()V
      // 7: areturn
   }

   @Override
   public SafeConstructor createSafeConstructor() {
      return new Yaml1Compat.CustomSafeConstructor();
   }

// $VF: Couldn't be decompiled
// Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
// java.lang.RuntimeException: Constructor org/yaml/snakeyaml/constructor/SafeConstructor.<init>()V not found
//   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:49)
//   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:35)
//   at org.jetbrains.java.decompiler.main.InitializerProcessor.hideEmptySuper(InitializerProcessor.java:110)
//   at org.jetbrains.java.decompiler.main.InitializerProcessor.extractInitializers(InitializerProcessor.java:51)
//   at org.jetbrains.java.decompiler.main.ClassWriter.invokeProcessors(ClassWriter.java:97)
//   at org.jetbrains.java.decompiler.main.ClassWriter.writeClass(ClassWriter.java:348)
//   at org.jetbrains.java.decompiler.main.ClassWriter.writeClass(ClassWriter.java:492)
//   at org.jetbrains.java.decompiler.main.ClassesProcessor.writeClass(ClassesProcessor.java:474)
//   at org.jetbrains.java.decompiler.main.Fernflower.getClassContent(Fernflower.java:191)
//   at org.jetbrains.java.decompiler.struct.ContextUnit.lambda$save$3(ContextUnit.java:187)
}
