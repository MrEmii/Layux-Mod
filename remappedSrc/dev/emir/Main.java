package dev.emir;

import dev.emir.blocks.OrichalcumBlocks;
import dev.emir.items.OrichalcumItems;
import dev.emir.register.Element;
import dev.emir.register.Register;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Main implements ModInitializer {

    public static final String MOD_ID = "orichalcum";

    public static ConfiguredFeature<?, ?> ORE_WOOL_OVERWORLD = Feature.ORE
            .configure(new OreFeatureConfig(
                    OreFeatureConfig.DEFAULT.getDecoratedFeatures(),
                    Blocks.WHITE_WOOL.getDefaultState(),
                    9)) // vein size
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(
                    0, // bottom offset
                    0, // min y level
                    64))) // max y level
            .spreadHorizontally()
            .repeat(20); // number of veins per chunk
    @Override
    public void onInitialize() {
        registerBootstrap(OrichalcumItems.class, Registry.ITEM);
        registerBootstrap(OrichalcumBlocks.class, Registry.BLOCK);
    }

    public void registerBootstrap(Class<?> clazz, Registry<?> registry) {
        Register annotations = clazz.getAnnotation(Register.class);
        if (annotations == null) {
            return;
        }

        Class<?> element = annotations.type();

        Arrays.stream(clazz.getFields())
                .filter(field -> field.isAnnotationPresent(Element.class)
                        && Modifier.isPublic(field.getModifiers())
                        && Modifier.isStatic(field.getModifiers())
                        && Modifier.isFinal(field.getModifiers())
                        && element.isAssignableFrom(field.getType())
                )
                .forEach(field -> {
                    try {
                        Object value = field.get(null);
                        Registry.register((Registry) registry, new Identifier(MOD_ID, field.getAnnotation(Element.class).id()), element.cast(value));
                        if (value instanceof BlockItem) {
                            Item.BLOCK_ITEMS.put(((BlockItem) value).getBlock(), (Item) value);
                        }
                        //TODO: Add anothers types
                    } catch (IllegalAccessException e) {
                        throw new AssertionError(e);
                    }
                });
    }

}
