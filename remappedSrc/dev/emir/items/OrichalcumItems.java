package dev.emir.items;

import dev.emir.blocks.OrichalcumBlocks;
import dev.emir.register.Element;
import dev.emir.register.Register;

import dev.emir.Main;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

@Register(type = Item.class)
public class OrichalcumItems {
  @Element(id = "orichalcum_ingot")
  public static final Item INGOT = new OrichalcumItem();

  @Element(id = "raw_orichalcum")
  public static final Item RAW = new OrichalcumItem();

  @Element(id = "orichalcum_nugget")
  public static final Item NUGGET = new OrichalcumItem();

  @Element(id = "orichalcum_block")
  public static final Item BLOCK = new BlockItem(OrichalcumBlocks.BLOCK, new FabricItemSettings().group(OrichalcumItem.ORICHALCUM_ITEM_GROUP));

  @Element(id = "orichalcum_ore")
  public static final Item ORE = new BlockItem(OrichalcumBlocks.ORE, new FabricItemSettings().group(OrichalcumItem.ORICHALCUM_ITEM_GROUP));

  @Element(id = "raw_orichalcum_block")
  public static final Item RAW_BLOCK = new BlockItem(OrichalcumBlocks.RAW, new FabricItemSettings().group(OrichalcumItem.ORICHALCUM_ITEM_GROUP));

}
