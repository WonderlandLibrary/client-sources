/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AttributeModifier {
    private static final Logger LOGGER = LogManager.getLogger();
    private final double amount;
    private final Operation operation;
    private final Supplier<String> name;
    private final UUID id;

    public AttributeModifier(String string, double d, Operation operation) {
        this(MathHelper.getRandomUUID(ThreadLocalRandom.current()), () -> AttributeModifier.lambda$new$0(string), d, operation);
    }

    public AttributeModifier(UUID uUID, String string, double d, Operation operation) {
        this(uUID, () -> AttributeModifier.lambda$new$1(string), d, operation);
    }

    public AttributeModifier(UUID uUID, Supplier<String> supplier, double d, Operation operation) {
        this.id = uUID;
        this.name = supplier;
        this.amount = d;
        this.operation = operation;
    }

    public UUID getID() {
        return this.id;
    }

    public String getName() {
        return this.name.get();
    }

    public Operation getOperation() {
        return this.operation;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            AttributeModifier attributeModifier = (AttributeModifier)object;
            return Objects.equals(this.id, attributeModifier.id);
        }
        return true;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name='" + this.name.get() + "', id=" + this.id + "}";
    }

    public CompoundNBT write() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("Name", this.getName());
        compoundNBT.putDouble("Amount", this.amount);
        compoundNBT.putInt("Operation", this.operation.getId());
        compoundNBT.putUniqueId("UUID", this.id);
        return compoundNBT;
    }

    @Nullable
    public static AttributeModifier read(CompoundNBT compoundNBT) {
        try {
            UUID uUID = compoundNBT.getUniqueId("UUID");
            Operation operation = Operation.byId(compoundNBT.getInt("Operation"));
            return new AttributeModifier(uUID, compoundNBT.getString("Name"), compoundNBT.getDouble("Amount"), operation);
        } catch (Exception exception) {
            LOGGER.warn("Unable to create attribute: {}", (Object)exception.getMessage());
            return null;
        }
    }

    private static String lambda$new$1(String string) {
        return string;
    }

    private static String lambda$new$0(String string) {
        return string;
    }

    public static enum Operation {
        ADDITION(0),
        MULTIPLY_BASE(1),
        MULTIPLY_TOTAL(2);

        private static final Operation[] VALUES;
        private final int id;

        private Operation(int n2) {
            this.id = n2;
        }

        public int getId() {
            return this.id;
        }

        public static Operation byId(int n) {
            if (n >= 0 && n < VALUES.length) {
                return VALUES[n];
            }
            throw new IllegalArgumentException("No operation with value " + n);
        }

        static {
            VALUES = new Operation[]{ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL};
        }
    }
}

