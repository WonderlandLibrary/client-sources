package com.viaversion.viaversion.api.minecraft.signature.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.signature.util.DataConsumer;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class ChatSession implements StorableObject {
   private final UUID uuid;
   private final PrivateKey privateKey;
   private final ProfileKey profileKey;
   private final Signature signer;

   public ChatSession(UUID uuid, PrivateKey privateKey, ProfileKey profileKey) {
      Objects.requireNonNull(uuid, "uuid");
      Objects.requireNonNull(privateKey, "privateKey");
      Objects.requireNonNull(profileKey, "profileKey");
      this.uuid = uuid;
      this.privateKey = privateKey;
      this.profileKey = profileKey;

      try {
         this.signer = Signature.getInstance("SHA256withRSA");
         this.signer.initSign(this.privateKey);
      } catch (Throwable var5) {
         throw new RuntimeException("Failed to initialize signature", var5);
      }
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public ProfileKey getProfileKey() {
      return this.profileKey;
   }

   public byte[] sign(Consumer<DataConsumer> dataConsumer) throws SignatureException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "parameterTypes" because the return value of "org.jetbrains.java.decompiler.struct.StructMethod.getSignature()" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.getInferredExprType(InvocationExprent.java:472)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:893)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 00: aload 1
      // 01: aload 0
      // 02: invokedynamic accept (Lcom/viaversion/viaversion/api/minecraft/signature/storage/ChatSession;)Lcom/viaversion/viaversion/api/minecraft/signature/util/DataConsumer; bsm=java/lang/invoke/LambdaMetafactory.metafactory (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; args=[ (Ljava/lang/Object;)V, com/viaversion/viaversion/api/minecraft/signature/storage/ChatSession.lambda$sign$0 ([B)V, ([B)V ]
      // 07: invokeinterface java/util/function/Consumer.accept (Ljava/lang/Object;)V 2
      // 0c: aload 0
      // 0d: getfield com/viaversion/viaversion/api/minecraft/signature/storage/ChatSession.signer Ljava/security/Signature;
      // 10: invokevirtual java/security/Signature.sign ()[B
      // 13: areturn
   }
}
