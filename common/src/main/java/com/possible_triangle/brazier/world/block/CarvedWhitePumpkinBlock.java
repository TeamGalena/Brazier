package com.possible_triangle.brazier.world.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class CarvedWhitePumpkinBlock extends HorizontalDirectionalBlock {

    private static final MapCodec<CarvedWhitePumpkinBlock> CODEC = simpleCodec(CarvedWhitePumpkinBlock::new);

    public CarvedWhitePumpkinBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

}
