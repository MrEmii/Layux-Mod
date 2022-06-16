package dev.emir.mixins;

import dev.emir.Main;
import dev.emir.data.IPlayerPersistentData;
import dev.emir.render.RenderTickSimulator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class ProjectileMixin extends Entity {

    public ProjectileMixin(EntityType<? extends PersistentProjectileEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "setOwner", at = @At("HEAD"))
    public void setOwner(@Nullable Entity entity, CallbackInfo info) {
        if (entity instanceof PlayerEntity) {
            ((IPlayerPersistentData) entity).setLocation(entity.getPos());
            Main.getContext().setTransition(true);
            RenderTickSimulator.update(10.0f);
            MinecraftClient.getInstance().gameRenderer.setRenderHand(false);
            MinecraftClient.getInstance().mouse.lockCursor();
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setPosition(DDD)V"))
    public void onTick(CallbackInfo info) {
        try {
            PersistentProjectileEntity projectile = ((PersistentProjectileEntity) (Object) this);

            if (projectile.getOwner() instanceof PlayerEntity player) {
                boolean isHeadHit = true; //TODO: Hit on head

                if (isHeadHit) {
                    player.teleport(prevX - 1, prevY - 1, prevZ - 1);
                    player.setHeadYaw(projectile.getYaw());
                }
            }

        } catch (ClassCastException e) {
            Main.LOGGER.warn(e.getStackTrace());
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onHitTick(CallbackInfo info) {
        try {
            PersistentProjectileEntity projectile = ((PersistentProjectileEntity) (Object) this);

            if (projectile.getOwner() instanceof PlayerEntity player && player == MinecraftClient.getInstance().player) {
                if (projectile.isOnGround()) {
                    disableTransition();
                }

            }

        } catch (ClassCastException e) {
            Main.LOGGER.warn(e.getStackTrace());
        }
    }

    @Inject(method = "onHit", at = @At("HEAD"))
    public void onHit(LivingEntity entity, CallbackInfo info) {
        disableTransition();
    }

    @Inject(method = "onBlockHit", at = @At("HEAD"))
    public void onBlockHit(BlockHitResult blockHitResult, CallbackInfo info) {
        disableTransition();
    }

    public void disableTransition() {
        try {
            if (Main.getContext().inTransition()) {
                Main.getContext().setTransition(false);
                PersistentProjectileEntity projectile = ((PersistentProjectileEntity) (Object) this);
                Vec3d pos = ((IPlayerPersistentData) projectile.getOwner()).getLocation();
                System.out.println(pos);
                RenderTickSimulator.update(20.0f);
                MinecraftClient.getInstance().gameRenderer.setRenderHand(true);
                projectile.getOwner().teleport(pos.getX(), pos.getY(), pos.getZ());
            }
        } catch (NullPointerException e) {
            Main.LOGGER.warn(e.getStackTrace());
        }
    }
}
