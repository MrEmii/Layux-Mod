package dev.emir;

import dev.emir.commands.CommandRegister;
import dev.emir.context.GameContext;
import dev.emir.features.FeaturesRegister;
import dev.emir.register.Element;
import dev.emir.register.Register;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Main implements ModInitializer {

    public static final String MOD_ID = "orichalcum";
    public static final Logger LOGGER = LogManager.getLogger("Orichalcum");
    private static final GameContext context = new GameContext();

    @Override
    public void onInitialize() {
        registerBootstrap("dev.emir.items", Registry.ITEM);
        registerBootstrap("dev.emir.blocks", Registry.BLOCK);
        FeaturesRegister.registerBootstrap();
        CommandRegister.register();
    }

    public static GameContext getContext() {
        return context;
    }

    public void registerBootstrap(String package_name, Registry<?> registry) {
        new Reflections(package_name).getTypesAnnotatedWith(Register.class).forEach(clazz -> {
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
                            Object type = field.get(null);
                            Registry.register((Registry<Object>) registry, new Identifier(MOD_ID, field.getAnnotation(Element.class).id()), element.cast(type));
                            if (type instanceof BlockItem) {
                                Item.BLOCK_ITEMS.put(((BlockItem) type).getBlock(), (Item) type);
                            }
                            //TODO: Add anothers types
                            LOGGER.info("Registered " + type + " to registry from " + field.getName());
                        } catch (IllegalAccessException e) {
                            throw new AssertionError(e);
                        }
                    });
        });
    }

}
