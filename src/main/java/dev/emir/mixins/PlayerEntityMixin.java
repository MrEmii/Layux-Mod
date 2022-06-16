package dev.emir.mixins;

import dev.emir.data.IPlayerPersistentData;
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

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
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
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo callbackInfo) {
        double x = nbt.getDouble("HomeX");
        double y = nbt.getDouble("HomeY");
        double z = nbt.getDouble("HomeZ");

        setHomeLocation(new Vec3d(x, y, z));
    }
}
