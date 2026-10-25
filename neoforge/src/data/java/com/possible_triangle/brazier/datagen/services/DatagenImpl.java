package com.possible_triangle.brazier.datagen.services;

import static com.possible_triangle.brazier.BrazierConstants.createId;

import com.possible_triangle.brazier.platform.services.IDatagen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;

public class DatagenImpl implements IDatagen {

    @Override
    public void brazierBlockState(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        provider.getVariantBuilder(context.get()).forAllStatesExcept(state -> {
            var lit = state.getValue(BlockStateProperties.LIT);
            var suffix = lit ? "_lit" : "";
            var model = provider.models().getExistingFile(createId(context.getName() + suffix));
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });
    }

    @Override
    public void pumpkin(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        var texture = provider.blockTexture(context.get());
        var model = provider.models().cubeColumn(context.getName(), texture.withSuffix("_side"), texture.withSuffix("_top"));
        provider.simpleBlock(context.get(), model);
    }

    @Override
    public void carvedPumpkin(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        var texture = provider.blockTexture(context.get());
        var baseTexture = texture.withPath(path -> path.replace("carved_", ""));
        provider.horizontalBlock(context.get(), baseTexture.withSuffix("_side"), texture, baseTexture.withSuffix("_top"));
    }

    @Override
    public void cursedSkull(DataGenContext<Block, ? extends Block> context, RegistrateBlockstateProvider provider) {
        provider.simpleBlock(context.get(), provider.models().getExistingFile(ResourceLocation.withDefaultNamespace("block/skull")));
    }

}
