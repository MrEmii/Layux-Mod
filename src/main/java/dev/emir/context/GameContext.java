package dev.emir.context;

import net.minecraft.util.math.Vec3d;

public class GameContext implements IGameContext {
    private boolean inTransition = false;
    private Vec3d location;

    @Override
    public boolean inTransition() {
        return inTransition;
    }

    @Override
    public void setTransition(boolean value) {
        inTransition = value;
    }

    @Override
    public void setLocation(Vec3d pos) {
        location = pos;
    }

    @Override
    public Vec3d getLocation() {
        return location;
    }
}
