package dev.emir.blocks;

import dev.emir.register.Element;
import dev.emir.register.Register;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

@Register(type = Block.class)
public class OrichalcumBlocks {

  @Element(id = "orichalcum_block")
  public static final Block BLOCK = new Block(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK));

  @Element(id = "orichalcum_ore")
  public static final Block ORE = new Block(AbstractBlock.Settings.copy(Blocks.GOLD_ORE));

  @Element(id = "raw_orichalcum_block")
  public static final Block RAW = new Block(AbstractBlock.Settings.copy(Blocks.RAW_GOLD_BLOCK));
}
