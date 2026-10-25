package com.possible_triangle.brazier.index;

import static com.possible_triangle.brazier.BrazierConstants.createId;

import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BrazierTags {
    public static final TagKey<Block> BRAZIER_BASE_BLOCKS = TagKey.create(Registries.BLOCK, createId("brazier_base_blocks"));
    public static final TagKey<Block> BRAZIER_STRIPE_BLOCKS = TagKey.create(Registries.BLOCK, createId("brazier_stripe_blocks"));
    public static final Filter<EntityType<?>> PREVENTABLE = Filter.create(Registries.ENTITY_TYPE, createId("preventable"));
    public static final TagKey<Item> TORCHES = TagKey.create(Registries.ITEM, createId("torches"));
    public static final TagKey<Item> ASH = TagKey.create(Registries.ITEM, createId("ash"));
    public static final TagKey<Item> RANGE_INDICATOR = TagKey.create(Registries.ITEM, createId("range_indicator"));
    public static final TagKey<Item> WARPED_WART_TAG = TagKey.create(Registries.ITEM, createId("warped_wart"));
    public static final TagKey<Item> CAPTURES_SOULS = TagKey.create(Registries.ITEM, createId("captures_soul"));
    public static final Filter<EntityType<?>> SOUL_CAPTURE = Filter.create(Registries.ENTITY_TYPE, createId("soul_capture"));

    public record Filter<T>(TagKey<T> whitelist, TagKey<T> blacklist) {
        public static <T> Filter<T> create(ResourceKey<Registry<T>> registry, ResourceLocation name) {
            return new Filter<>(
                    TagKey.create(registry, name.withSuffix("/whitelist")),
                    TagKey.create(registry, name.withSuffix("/blacklist"))
            );
        }

        public boolean matches(Predicate<TagKey<T>> predicate) {
            return matches(predicate, true);
        }

        public boolean matches(Predicate<TagKey<T>> predicate, boolean fallback) {
            if (predicate.test(whitelist())) return true;
            if (predicate.test(blacklist())) return false;
            return fallback;
        }
    }
}
