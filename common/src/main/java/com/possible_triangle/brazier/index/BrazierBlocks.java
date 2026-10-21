package com.possible_triangle.brazier.index;

import static com.possible_triangle.brazier.index.BrazierContent.REGISTRATE;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;

import com.possible_triangle.brazier.platform.Datagen;
import com.possible_triangle.brazier.platform.services.IDatagen;
import com.possible_triangle.brazier.world.block.*;
import com.possible_triangle.brazier.world.block.tile.BrazierBlockEntity;
import com.possible_triangle.brazier.world.block.tile.render.BrazierRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import java.util.function.Supplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

public class BrazierBlocks {

    public static final BlockEntry<BrazierBlock> BRAZIER = REGISTRATE.object("brazier")
            .block(BrazierBlock::new)
            .properties(it -> it
                    .strength(1.5F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .lightLevel(s -> s.getValue(BlockStateProperties.LIT) ? 15 : 0)
            )
            .addLayer(() -> RenderType::cutout)
            .blockstate(Datagen.consume(IDatagen::brazierBlockState))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .tab(CreativeModeTabs.FUNCTIONAL_BLOCKS)
            .recipe((context, provider) ->
                    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, context.get())
                            .pattern("BFB")
                            .pattern("SSS")
                            .define('B', Blocks.IRON_BARS)
                            .define('S', Blocks.BLACKSTONE)
                            .define('F', BrazierItems.LIVING_FLAME.get())
                            .unlockedBy("has_living_flame", has(BrazierItems.LIVING_FLAME))
                            .save(provider)
            )
            .build()
            .register();

    public static final BlockEntityEntry<BrazierBlockEntity> BRAZIER_ENTITY = REGISTRATE.object("brazier")
            .blockEntity(BrazierBlockEntity::new)
            .validBlock(BRAZIER)
            .renderer(() -> $ -> new BrazierRenderer())
            .register();

    public static final BlockEntry<? extends Block> WHITE_PUMPKIN = REGISTRATE.object("white_pumpkin")
            .block(Block::new)
            .initialProperties(() -> Blocks.PUMPKIN)
            .properties(it -> it.mapColor(MapColor.TERRACOTTA_WHITE))
            .blockstate(Datagen.consume(IDatagen::pumpkin))
            .simpleItem()
            .register();

    public static final BlockEntry<? extends HorizontalDirectionalBlock> CARVED_WHITE_PUMPKIN = REGISTRATE.object("carved_white_pumpkin")
            .block(CarvedWhitePumpkinBlock::new)
            .initialProperties(() -> Blocks.CARVED_PUMPKIN)
            .properties(it -> it.mapColor(MapColor.TERRACOTTA_WHITE))
            .blockstate(Datagen.consume(IDatagen::carvedPumpkin))
            .simpleItem()
            .register();

    @Nullable
    @SuppressWarnings("unchecked")
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> candidate, Supplier<BlockEntityType<E>> desired, BlockEntityTicker<? super E> ticker) {
        return desired.get() == candidate ? (BlockEntityTicker<A>) ticker : null;
    }

    static void init() {
        // Load this class
    }

}
