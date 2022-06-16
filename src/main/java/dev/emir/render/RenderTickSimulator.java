package dev.emir.render;

import dev.emir.mixins.ClientMixin;
import net.minecraft.client.MinecraftClient;

public class RenderTickSimulator {
    private static final Float tps = 20.0F;
    private static float prevTps = 20.0F;

    public static void onUpdate() {
        if (tps != prevTps) {
            update(tps);
            prevTps = tps;
        }
    }

    public static void update(float tps) {
        ClientMixin client = (ClientMixin) MinecraftClient.getInstance();
        IRenderTickCounter tickCounter = (IRenderTickCounter) client.getRenderTickCounter();

        if (tickCounter.getTickTime() != 1000f / tps) {
            tickCounter.setTickTime(1000f / tps);
        }
    }
}
