package net.minecraft.client.renderer.model;

import com.google.common.collect.Maps;
import com.google.gson.*;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class BlockPart
{
    public final Vector3f positionFrom;
    public final Vector3f positionTo;
    public final Map<Direction, BlockPartFace> mapFaces;
    public final BlockPartRotation partRotation;
    public final boolean shade;

    public BlockPart(Vector3f positionFrom, Vector3f positionTo, Map<Direction, BlockPartFace> mapFaces, @Nullable BlockPartRotation partRotation, boolean shade)
    {
        this.positionFrom = positionFrom;
        this.positionTo = positionTo;
        this.mapFaces = mapFaces;
        this.partRotation = partRotation;
        this.shade = shade;
        this.setDefaultUvs();
    }

    private void setDefaultUvs()
    {
        for (Entry<Direction, BlockPartFace> entry : this.mapFaces.entrySet())
        {
            float[] afloat = this.getFaceUvs(entry.getKey());
            (entry.getValue()).blockFaceUV.setUvs(afloat);
        }
    }

    private float[] getFaceUvs(Direction facing)
    {
        switch (facing)
        {
            case DOWN:
                return new float[] {this.positionFrom.getX(), 16.0F - this.positionTo.getZ(), this.positionTo.getX(), 16.0F - this.positionFrom.getZ()};
            case UP:
                return new float[] {this.positionFrom.getX(), this.positionFrom.getZ(), this.positionTo.getX(), this.positionTo.getZ()};
            case NORTH:
            default:
                return new float[] {16.0F - this.positionTo.getX(), 16.0F - this.positionTo.getY(), 16.0F - this.positionFrom.getX(), 16.0F - this.positionFrom.getY()};
            case SOUTH:
                return new float[] {this.positionFrom.getX(), 16.0F - this.positionTo.getY(), this.positionTo.getX(), 16.0F - this.positionFrom.getY()};
            case WEST:
                return new float[] {this.positionFrom.getZ(), 16.0F - this.positionTo.getY(), this.positionTo.getZ(), 16.0F - this.positionFrom.getY()};
            case EAST:
                return new float[] {16.0F - this.positionTo.getZ(), 16.0F - this.positionTo.getY(), 16.0F - this.positionFrom.getZ(), 16.0F - this.positionFrom.getY()};
        }
    }

    public static class Deserializer implements JsonDeserializer<BlockPart>
    {
        protected Deserializer()
        {
        }

        public BlockPart deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            Vector3f vector3f = this.validateFromVector(jsonobject);
            Vector3f vector3f1 = this.validateToVector(jsonobject);
            BlockPartRotation blockpartrotation = this.parseRotation(jsonobject);
            Map<Direction, BlockPartFace> map = this.parseFacesCheck(p_deserialize_3_, jsonobject);

            if (jsonobject.has("shade") && !JSONUtils.isBoolean(jsonobject, "shade"))
            {
                throw new JsonParseException("Expected shade to be a Boolean");
            }
            else
            {
                boolean flag = JSONUtils.getBoolean(jsonobject, "shade", true);
                return new BlockPart(vector3f, vector3f1, map, blockpartrotation, flag);
            }
        }

        @Nullable
        private BlockPartRotation parseRotation(JsonObject object)
        {
            BlockPartRotation blockpartrotation = null;

            if (object.has("rotation"))
            {
                JsonObject jsonobject = JSONUtils.getJsonObject(object, "rotation");
                Vector3f vector3f = this.deserializeVec3f(jsonobject, "origin");
                vector3f.mul(0.0625F);
                Direction.Axis direction$axis = this.parseAxis(jsonobject);
                float f = this.parseAngle(jsonobject);
                boolean flag = JSONUtils.getBoolean(jsonobject, "rescale", false);
                blockpartrotation = new BlockPartRotation(vector3f, direction$axis, f, flag);
            }

            return blockpartrotation;
        }

        private float parseAngle(JsonObject object)
        {
            float f = JSONUtils.getFloat(object, "angle");

            if (f != 0.0F && MathHelper.abs(f) != 22.5F && MathHelper.abs(f) != 45.0F)
            {
                throw new JsonParseException("Invalid rotation " + f + " found, only -45/-22.5/0/22.5/45 allowed");
            }
            else
            {
                return f;
            }
        }

        private Direction.Axis parseAxis(JsonObject object)
        {
            String s = JSONUtils.getString(object, "axis");
            Direction.Axis direction$axis = Direction.Axis.byName(s.toLowerCase(Locale.ROOT));

            if (direction$axis == null)
            {
                throw new JsonParseException("Invalid rotation axis: " + s);
            }
            else
            {
                return direction$axis;
            }
        }

        private Map<Direction, BlockPartFace> parseFacesCheck(JsonDeserializationContext deserializationContext, JsonObject object)
        {
            Map<Direction, BlockPartFace> map = this.parseFaces(deserializationContext, object);

            if (map.isEmpty())
            {
                throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
            }
            else
            {
                return map;
            }
        }

        private Map<Direction, BlockPartFace> parseFaces(JsonDeserializationContext deserializationContext, JsonObject object)
        {
            Map<Direction, BlockPartFace> map = Maps.newEnumMap(Direction.class);
            JsonObject jsonobject = JSONUtils.getJsonObject(object, "faces");

            for (Entry<String, JsonElement> entry : jsonobject.entrySet())
            {
                Direction direction = this.parseEnumFacing(entry.getKey());
                map.put(direction, deserializationContext.deserialize(entry.getValue(), BlockPartFace.class));
            }

            return map;
        }

        private Direction parseEnumFacing(String name)
        {
            Direction direction = Direction.byName(name);

            if (direction == null)
            {
                throw new JsonParseException("Unknown facing: " + name);
            }
            else
            {
                return direction;
            }
        }

        private Vector3f validateToVector(JsonObject json)
        {
            Vector3f vector3f = this.deserializeVec3f(json, "to");

            if (!(vector3f.getX() < -16.0F) && !(vector3f.getY() < -16.0F) && !(vector3f.getZ() < -16.0F) && !(vector3f.getX() > 32.0F) && !(vector3f.getY() > 32.0F) && !(vector3f.getZ() > 32.0F))
            {
                return vector3f;
            }
            else
            {
                throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + vector3f);
            }
        }

        private Vector3f validateFromVector(JsonObject json)
        {
            Vector3f vector3f = this.deserializeVec3f(json, "from");

            if (!(vector3f.getX() < -16.0F) && !(vector3f.getY() < -16.0F) && !(vector3f.getZ() < -16.0F) && !(vector3f.getX() > 32.0F) && !(vector3f.getY() > 32.0F) && !(vector3f.getZ() > 32.0F))
            {
                return vector3f;
            }
            else
            {
                throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + vector3f);
            }
        }

        private Vector3f deserializeVec3f(JsonObject json, String name)
        {
            JsonArray jsonarray = JSONUtils.getJsonArray(json, name);

            if (jsonarray.size() != 3)
            {
                throw new JsonParseException("Expected 3 " + name + " values, found: " + jsonarray.size());
            }
            else
            {
                float[] afloat = new float[3];

                for (int i = 0; i < afloat.length; ++i)
                {
                    afloat[i] = JSONUtils.getFloat(jsonarray.get(i), name + "[" + i + "]");
                }

                return new Vector3f(afloat[0], afloat[1], afloat[2]);
            }
        }
    }
}
