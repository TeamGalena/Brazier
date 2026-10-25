package com.possible_triangle.brazier.world.block;

import com.mojang.serialization.MapCodec;
import com.possible_triangle.brazier.BrazierConstants;
import com.possible_triangle.brazier.world.block.tile.CursedSkullBlockEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CursedSkullBlock extends SkullBlock {

    public static final SkullBlock.Type TYPE = () -> BrazierConstants.createId("cursed_skull").toString();
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(BrazierConstants.createId("cursed_skull"), "main");
    public static final ResourceLocation TEXTURE = BrazierConstants.createId("textures/entity/cursed_skull.png");

    private static final MapCodec<CursedSkullBlock> CODEC = simpleCodec(CursedSkullBlock::new);

    public CursedSkullBlock(Properties properties) {
        super(TYPE, properties);
    }

    @Override
    public MapCodec<? extends SkullBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CursedSkullBlockEntity(pos, state);
    }
}
