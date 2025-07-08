package net.hendersondaniel.gu_daoism.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.hendersondaniel.gu_daoism.networking.ModMessages;
import net.hendersondaniel.gu_daoism.networking.packet.PrimevalEssenceSyncS2CPacket;
import net.hendersondaniel.gu_daoism.networking.packet.RawStageSyncS2CPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import static net.hendersondaniel.gu_daoism.util.CalculationMethods.clampPrimevalEssence;

public class SetRawStageCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(GuDaoism.MOD_ID + ":set_raw_stage").requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.player())
                        .then(Commands.argument("stage", IntegerArgumentType.integer())
                                .executes(SetRawStageCommand::setRawStageCultivation)
                        )
                )
        );
    }

    public static int setRawStageCultivation(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
        int stage = IntegerArgumentType.getInteger(ctx, "stage");

        target.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(s -> {
            s.setRawStage(stage);
            clampPrimevalEssence(s);

            ModMessages.sendToPlayer(new PrimevalEssenceSyncS2CPacket(s.getPrimevalEssence()),target);
            ModMessages.sendToPlayer(new RawStageSyncS2CPacket(s.getRawStage()), target);
        });

        ctx.getSource().sendSuccess(Component.literal("Set raw stage to " + stage + " for " + target.getName().getString()), true);

        return 1;
    }
}
