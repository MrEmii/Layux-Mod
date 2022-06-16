package dev.emir.mixins;

import dev.emir.data.IPlayerPersistentData;
import dev.emir.render.RenderTickSimulator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IPlayerPersistentData {
    private Vec3d homeLocation;
    private Vec3d location;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public Vec3d getLocation() {
        return location;
    }

    public void setLocation(Vec3d location) {
        this.location = location;
    }

    public Vec3d getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(Vec3d homeLocation) {
        this.homeLocation = homeLocation;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo callbackInfo) {
        if (homeLocation != null) {
            nbt.putDouble("HomeX", homeLocation.x);
            nbt.putDouble("HomeY", homeLocation.y);
            nbt.putDouble("HomeZ", homeLocation.z);
        }

        if (location != null) {
            nbt.putDouble("TeleportX", location.x);
            nbt.putDouble("TeleportY", location.y);
            nbt.putDouble("TeleportZ", location.z);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo callbackInfo) {
        double x = nbt.getDouble("HomeX");
        double y = nbt.getDouble("HomeY");
        double z = nbt.getDouble("HomeZ");

        setHomeLocation(new Vec3d(x, y, z));

        double tX = nbt.getDouble("TeleportX");
        double tY = nbt.getDouble("TeleportY");
        double tZ = nbt.getDouble("TeleportZ");

        setLocation(new Vec3d(tX, tY, tZ));


    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;tick()V", shift = At.Shift.AFTER))
    private void tick(CallbackInfo info) {
        RenderTickSimulator.onUpdate();
    }
}
