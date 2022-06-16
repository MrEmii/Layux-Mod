package dev.emir.items;

import dev.emir.Main;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class OrichalcumItem extends Item {
    protected static final ItemGroup ORICHALCUM_ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(Main.MOD_ID), () -> new ItemStack(OrichalcumItems.INGOT));

    public OrichalcumItem() {
        super(new FabricItemSettings().group(ORICHALCUM_ITEM_GROUP));
    }

    public OrichalcumItem(Settings settings) {
        super(settings.group(ORICHALCUM_ITEM_GROUP));
    }
}
