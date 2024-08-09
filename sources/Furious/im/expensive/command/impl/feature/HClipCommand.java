package im.expensive.command.impl.feature;

import im.expensive.command.*;
import im.expensive.command.impl.CommandException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HClipCommand implements Command, CommandWithAdvice {
    final Prefix prefix;
    final Logger logger;
    final Minecraft mc;

    @Override
    public void execute(Parameters parameters) {

        String direction = parameters.asString(0).orElseThrow(() -> new CommandException(TextFormatting.RED + "Необходимо указать расстояние."));

        if (!NumberUtils.isNumber(direction)) {
            logger.log(TextFormatting.RED + "Пожалуйста, введите число для этой команды.");
            return;
        }

        double blocks = Double.parseDouble(direction);
        Vector3d lookVector = Minecraft.getInstance().player.getLook(1F).mul(blocks, 0, blocks);

        double newX = mc.player.getPosX() + lookVector.getX();
        double newZ = mc.player.getPosZ() + lookVector.getZ();

        for (int i = 0; i < 5; i++) {
            mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(newX, mc.player.getPosY(), newZ, false));
        }

        mc.player.setPositionAndUpdate(newX, mc.player.getPosY(), newZ);

        for (int i = 0; i < 5; i++) {
            mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(newX, mc.player.getPosY(), newZ, false));
        }
        String blockUnit = (blocks > 1) ? "блока" : "блок";
        String message = String.format("Вы были успешно телепортированы на %.1f %s по горизонтали", blocks, blockUnit);
        logger.log(TextFormatting.GRAY + message);
    }

    @Override
    public String name() {
        return "hclip";
    }

    @Override
    public String description() {
        return "Телепортирует вперёд/назад по горизонтали";
    }


    @Override
    public List<String> adviceMessage() {
        String commandPrefix = prefix.get();
        return List.of(
                TextFormatting.GRAY + commandPrefix + "hclip <distance> - Телепортация на указанное расстояние",
                "Пример: " + TextFormatting.RED + commandPrefix + "hclip 1"
        );
    }
}