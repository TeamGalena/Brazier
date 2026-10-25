package com.possible_triangle.brazier.platform.services;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface IDatagen {

    void brazierBlockState(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider);

    void pumpkin(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider);

    void carvedPumpkin(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider);

    void cursedSkull(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider);

    void cursedSkull(DataGenContext<Item, BlockItem> context, RegistrateItemModelProvider provider);
}
