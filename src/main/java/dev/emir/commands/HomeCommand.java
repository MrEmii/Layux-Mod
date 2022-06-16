package dev.emir.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.emir.data.IPlayerPersistentData;
import dev.emir.mixins.PlayerEntityMixin;
import dev.emir.render.RenderTickSimulator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class HomeCommand {

    public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("home")
                .then(CommandManager.literal("set").executes(HomeCommand::setHome))
                .executes(HomeCommand::home));
    }

    private static int home(CommandContext<ServerCommandSource> ctx) {
        try {
            final ServerCommandSource source = ctx.getSource();

            final ServerPlayerEntity self = source.getPlayer();

            Vec3d home = ((IPlayerPersistentData) self).getHomeLocation();
            self.requestTeleport(home.getX(), home.getY(), home.getZ());
            source.sendFeedback(new TranslatableText("home.teleport").formatted(Formatting.DARK_GREEN), false);
            return Command.SINGLE_SUCCESS;
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static int setHome(CommandContext<ServerCommandSource> ctx) {
        final ServerCommandSource source = ctx.getSource();
        try {

            final PlayerEntity self = source.getPlayer();
            Vec3d pos = self.getPos();
            ((IPlayerPersistentData) self).setHomeLocation(pos);

            source.sendFeedback(new TranslatableText("home.set", pos.toString()).formatted(Formatting.GREEN), false);
            return Command.SINGLE_SUCCESS;
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
