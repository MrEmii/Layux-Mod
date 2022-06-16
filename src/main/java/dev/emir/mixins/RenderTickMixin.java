package dev.emir.mixins;

import dev.emir.render.IRenderTickCounter;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(RenderTickCounter.class)
public abstract class RenderTickMixin implements IRenderTickCounter {

    @Shadow
    @Mutable
    private float tickTime;

    public float getTickTime() {
        return tickTime;
    }

    public void setTickTime(float tickTime) {
        this.tickTime = tickTime;
    }
}
