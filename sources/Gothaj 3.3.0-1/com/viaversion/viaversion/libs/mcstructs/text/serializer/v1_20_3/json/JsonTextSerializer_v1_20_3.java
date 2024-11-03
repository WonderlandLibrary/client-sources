package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json;

import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.components.KeybindComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.NbtComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.ScoreComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.SelectorComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.nbt.BlockNbtComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.nbt.EntityNbtComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.nbt.StorageNbtComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.ITypedSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentCodec;
import com.viaversion.viaversion.libs.mcstructs.text.utils.CodecUtils;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.Map.Entry;

public class JsonTextSerializer_v1_20_3 implements ITypedSerializer<JsonElement, ATextComponent> {
   private final ITypedSerializer<JsonElement, Style> styleSerializer;

   public JsonTextSerializer_v1_20_3(TextComponentCodec codec, SNbtSerializer<CompoundTag> sNbtSerializer) {
      this.styleSerializer = new JsonStyleSerializer_v1_20_3(codec, this, sNbtSerializer);
   }

   public JsonElement serialize(ATextComponent object) {
      JsonObject out = new JsonObject();
      if (object instanceof StringComponent) {
         StringComponent component = (StringComponent)object;
         if (component.getSiblings().isEmpty() && component.getStyle().isEmpty()) {
            return new JsonPrimitive(component.getText());
         }

         out.addProperty("text", component.getText());
      } else if (object instanceof TranslationComponent) {
         TranslationComponent component = (TranslationComponent)object;
         out.addProperty("translate", component.getKey());
         if (component.getFallback() != null) {
            out.addProperty("fallback", component.getFallback());
         }

         if (component.getArgs().length > 0) {
            JsonArray args = new JsonArray();

            for (Object arg : component.getArgs()) {
               args.add(this.convert(arg));
            }

            out.add("with", args);
         }
      } else if (object instanceof KeybindComponent) {
         KeybindComponent componentx = (KeybindComponent)object;
         out.addProperty("keybind", componentx.getKeybind());
      } else if (object instanceof ScoreComponent) {
         ScoreComponent componentx = (ScoreComponent)object;
         JsonObject score = new JsonObject();
         score.addProperty("name", componentx.getName());
         score.addProperty("objective", componentx.getObjective());
         out.add("score", score);
      } else if (object instanceof SelectorComponent) {
         SelectorComponent componentx = (SelectorComponent)object;
         out.addProperty("selector", componentx.getSelector());
         if (componentx.getSeparator() != null) {
            out.add("separator", this.serialize(componentx.getSeparator()));
         }
      } else {
         if (!(object instanceof NbtComponent)) {
            throw new IllegalArgumentException("Unknown component type: " + object.getClass().getName());
         }

         NbtComponent componentx = (NbtComponent)object;
         out.addProperty("nbt", componentx.getComponent());
         if (componentx.isResolve()) {
            out.addProperty("interpret", 1);
         }

         if (componentx instanceof EntityNbtComponent) {
            EntityNbtComponent entityComponent = (EntityNbtComponent)componentx;
            out.addProperty("entity", entityComponent.getSelector());
         } else if (componentx instanceof BlockNbtComponent) {
            BlockNbtComponent blockNbtComponent = (BlockNbtComponent)componentx;
            out.addProperty("block", blockNbtComponent.getPos());
         } else {
            if (!(componentx instanceof StorageNbtComponent)) {
               throw new IllegalArgumentException("Unknown Nbt component type: " + componentx.getClass().getName());
            }

            StorageNbtComponent storageNbtComponent = (StorageNbtComponent)componentx;
            out.addProperty("storage", storageNbtComponent.getId().get());
         }
      }

      JsonObject style = this.styleSerializer.serialize(object.getStyle()).getAsJsonObject();
      if (style.size() > 0) {
         for (Entry<String, JsonElement> entry : style.entrySet()) {
            out.add(entry.getKey(), entry.getValue());
         }
      }

      if (!object.getSiblings().isEmpty()) {
         JsonArray siblings = new JsonArray();

         for (ATextComponent sibling : object.getSiblings()) {
            siblings.add(this.serialize(sibling));
         }

         out.add("extra", siblings);
      }

      return out;
   }

   private JsonElement convert(Object object) {
      if (object instanceof Boolean) {
         return new JsonPrimitive((Boolean)object);
      } else if (object instanceof Number) {
         return new JsonPrimitive((Number)object);
      } else if (object instanceof String) {
         return new JsonPrimitive((String)object);
      } else if (object instanceof ATextComponent) {
         return this.serialize((ATextComponent)object);
      } else {
         throw new IllegalArgumentException("Unknown object type: " + object.getClass().getName());
      }
   }

   public ATextComponent deserialize(JsonElement object) {
      if (CodecUtils.isString(object)) {
         return new StringComponent(object.getAsString());
      } else if (object.isJsonArray()) {
         if (object.getAsJsonArray().isEmpty()) {
            throw new IllegalArgumentException("Empty json array");
         } else {
            JsonArray array = object.getAsJsonArray();
            ATextComponent[] components = new ATextComponent[array.size()];

            for (int i = 0; i < array.size(); i++) {
               components[i] = this.deserialize(array.get(i));
            }

            if (components.length == 1) {
               return components[0];
            } else {
               ATextComponent parent = components[0];

               for (int i = 1; i < components.length; i++) {
                  parent.append(components[i]);
               }

               return parent;
            }
         }
      } else if (!object.isJsonObject()) {
         throw new IllegalArgumentException("Unknown component type: " + object.getClass().getSimpleName());
      } else {
         ATextComponent component = null;
         JsonObject obj = object.getAsJsonObject();
         String type = CodecUtils.optionalString(obj, "type");
         if (!CodecUtils.containsString(obj, "text") || type != null && !type.equals("text")) {
            if (!CodecUtils.containsString(obj, "translate") || type != null && !type.equals("translatable")) {
               if (!CodecUtils.containsString(obj, "keybind") || type != null && !type.equals("keybind")) {
                  if (!CodecUtils.containsObject(obj, "score")
                     || !CodecUtils.containsString(obj.getAsJsonObject("score"), "name")
                     || !CodecUtils.containsString(obj.getAsJsonObject("score"), "objective")
                     || type != null && !type.equals("score")) {
                     if (!CodecUtils.containsString(obj, "selector") || type != null && !type.equals("selector")) {
                        if (!CodecUtils.containsString(obj, "nbt") || type != null && !type.equals("nbt")) {
                           throw new IllegalArgumentException("Unknown component type: " + obj.getClass().getSimpleName());
                        }

                        String nbt = obj.get("nbt").getAsString();
                        boolean interpret = Boolean.TRUE.equals(CodecUtils.optionalBoolean(obj, "interpret"));
                        ATextComponent separator = null;
                        if (obj.has("separator")) {
                           try {
                              separator = this.deserialize(obj.get("separator"));
                           } catch (Throwable var12) {
                           }
                        }

                        String source = CodecUtils.optionalString(obj, "source");
                        boolean typeFound = false;
                        if (!CodecUtils.containsString(obj, "entity") || source != null && !source.equals("entity")) {
                           if (!CodecUtils.containsString(obj, "block") || source != null && !source.equals("block")) {
                              if (CodecUtils.containsString(obj, "storage") && (source == null || source.equals("storage"))) {
                                 try {
                                    component = new StorageNbtComponent(nbt, interpret, separator, Identifier.of(obj.get("storage").getAsString()));
                                    typeFound = true;
                                 } catch (Throwable var11) {
                                 }
                              }
                           } else {
                              component = new BlockNbtComponent(nbt, interpret, separator, obj.get("block").getAsString());
                              typeFound = true;
                           }
                        } else {
                           component = new EntityNbtComponent(nbt, interpret, separator, obj.get("entity").getAsString());
                           typeFound = true;
                        }

                        if (!typeFound) {
                           throw new IllegalArgumentException("Unknown Nbt component type: " + obj.getClass().getSimpleName());
                        }
                     } else {
                        String selector = obj.get("selector").getAsString();
                        ATextComponent separatorx = null;
                        if (obj.has("separator")) {
                           separatorx = this.deserialize(obj.get("separator"));
                        }

                        component = new SelectorComponent(selector, separatorx);
                     }
                  } else {
                     JsonObject score = obj.getAsJsonObject("score");
                     String name = score.get("name").getAsString();
                     String objective = score.get("objective").getAsString();
                     component = new ScoreComponent(name, objective);
                  }
               } else {
                  component = new KeybindComponent(obj.get("keybind").getAsString());
               }
            } else {
               String key = obj.get("translate").getAsString();
               String fallback = CodecUtils.optionalString(obj, "fallback");
               if (obj.has("with")) {
                  if (!CodecUtils.containsArray(obj, "with")) {
                     throw new IllegalArgumentException("Expected json array for 'with' tag");
                  }

                  JsonArray with = obj.getAsJsonArray("with");
                  Object[] args = new Object[with.size()];

                  for (int i = 0; i < with.size(); i++) {
                     JsonElement arg = with.get(i);
                     if (CodecUtils.isNumber(arg)) {
                        if (arg.getAsJsonPrimitive().isNumber()) {
                           args[i] = arg.getAsInt();
                        } else {
                           args[i] = arg.getAsBoolean() ? 1 : 0;
                        }
                     } else if (CodecUtils.isString(arg)) {
                        args[i] = arg.getAsString();
                     } else {
                        args[i] = this.deserialize(arg);
                     }
                  }

                  component = new TranslationComponent(key, args).setFallback(fallback);
               } else {
                  component = new TranslationComponent(key).setFallback(fallback);
               }
            }
         } else {
            component = new StringComponent(obj.get("text").getAsString());
         }

         Style style = this.styleSerializer.deserialize(obj);
         if (!style.isEmpty()) {
            component.setStyle(style);
         }

         if (obj.has("extra")) {
            if (!obj.has("extra") || !obj.get("extra").isJsonArray()) {
               throw new IllegalArgumentException("Expected json array for 'extra' tag");
            }

            JsonArray extraList = obj.getAsJsonArray("extra");
            if (extraList.isEmpty()) {
               throw new IllegalArgumentException("Empty extra json array");
            }

            ATextComponent[] extra = new ATextComponent[extraList.size()];

            for (int ix = 0; ix < extraList.size(); ix++) {
               extra[ix] = this.deserialize(extraList.get(ix));
            }

            component.append(extra);
         }

         return component;
      }
   }
}
