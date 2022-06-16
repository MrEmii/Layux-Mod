package dev.emir.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static dev.emir.Main.MOD_ID;

public class HomePersistentData extends PersistentState {
    private Map<String, Double> coordinators = new HashMap<>();
    ;
    final ServerWorld world;

    public HomePersistentData(ServerWorld world) {
        super();
        this.world = world;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        if (nbt.getKeys().size() < 3) return null;
        for (String key :
                nbt.getKeys()) {
            this.coordinators.put(key, nbt.getDouble(key));
        }

        return nbt;
    }

    public static HomePersistentData get(ServerWorld world) {
        final String id = MOD_ID + "_home_data";
        return world.getPersistentStateManager().getOrCreate(tag -> fromNbt(world, tag), () -> new HomePersistentData(world), id);
    }

    public static HomePersistentData fromNbt(ServerWorld world, NbtCompound nbt) {
        HomePersistentData ret = new HomePersistentData(world);
        for (String key :
                nbt.getKeys()) {
            ret.coordinators.put(key, nbt.getDouble(key));
        }
        return ret;
    }

    @Override
    public String toString() {
        return "HomePersistentData{" +
                "coordinators=" + coordinators +
                '}';
    }
}
