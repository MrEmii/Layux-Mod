package dev.emir.data;

import net.minecraft.util.math.Vec3d;

public interface IPlayerPersistentData {
    void setHomeLocation(Vec3d pos);

    Vec3d getHomeLocation();
}
