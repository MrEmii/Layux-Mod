package dev.emir.context;

import net.minecraft.util.math.Vec3d;

public interface IGameContext {

    boolean inTransition();

    void setTransition(boolean value);

    void setLocation(Vec3d pos);

    Vec3d getLocation();
}
