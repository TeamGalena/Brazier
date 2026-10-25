package com.possible_triangle.brazier.datagen;

import static net.minecraft.world.level.block.Blocks.NETHER_WART;

import com.possible_triangle.brazier.BrazierConstants;
import com.possible_triangle.brazier.datagen.providers.LootInjectRegistrateProvider;
import com.possible_triangle.brazier.index.BrazierItems;
import com.possible_triangle.brazier.platform.Services;
import com.possible_triangle.brazier.world.InjectLootModifier;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import java.util.function.Function;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

public final class LootInjects {

    private static final AbstractRegistrate<?> REGISTRATE = Services.PLATFORM.getRegistrate();

    private static void inject(String name, ResourceKey<LootTable> target, Function<HolderLookup.Provider, LootTable.Builder> factory, ICondition... conditions) {
        var id = BrazierConstants.createId("inject/" + name);

        REGISTRATE.addDataGenerator(ProviderType.LOOT, provider -> {
            var registries = provider.getProvider();

            provider.addLootAction(LootContextParamSets.EMPTY, consumer ->
                    consumer.accept(ResourceKey.create(Registries.LOOT_TABLE, id), factory.apply(registries))
            );
        });

        REGISTRATE.addDataGenerator(LootInjectRegistrateProvider.TYPE, provider -> {
            provider.add(id.getPath(), new InjectLootModifier(
                    new LootItemCondition[]{
                            LootTableIdCondition.builder(target.location()).build()
                    },
                    id
            ), conditions);
        });
    }

    public static void generate() {
        inject("warped_wart", NETHER_WART.getLootTable(), registries -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(BrazierItems.WARPED_NETHER_WART)
                                .when(LootItemRandomChanceCondition.randomChance(0.02F))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(NETHER_WART)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(NetherWartBlock.AGE, 3)))
                        )
                )
        );

        inject("flame_jungle_temple", BuiltInLootTables.JUNGLE_TEMPLE, registries -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(BrazierItems.LIVING_FLAME))
                        .add(EmptyLootItem.emptyItem().setWeight(1))
                )
        );

        inject("wither_ash", EntityType.WITHER_SKELETON.getDefaultLootTable(), registries -> LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(BrazierItems.ASH)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(-1, 2)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0, 1)))
                                )
                        ),
                new NotCondition(new ModLoadedCondition("supplementaries"))
        );
    }

}
