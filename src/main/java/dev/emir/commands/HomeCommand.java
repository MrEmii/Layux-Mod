package dev.emir.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.emir.data.HomePersistentData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
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

            final ServerPlayerEntity self = source.getPlayer(); // If not a player than the command ends

            final ServerWorld world = source.getWorld();


            HomePersistentData data = HomePersistentData.get(world);

            System.out.println(data);
            return 1;
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static int setHome(CommandContext<ServerCommandSource> ctx) {
        try {
            final ServerCommandSource source = ctx.getSource();

            final ServerPlayerEntity self = source.getPlayer(); // If not a player than the command ends

            final ServerWorld world = source.getWorld();

            Vec3d pos = self.getPos();

            HomePersistentData data = HomePersistentData.get(world);

            NbtCompound compound = new NbtCompound();

            compound.putDouble("home.x", pos.getX());
            compound.putDouble("home.y", pos.getY());
            compound.putDouble("home.z", pos.getZ());

            data.writeNbt(compound);

            return 1;
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
