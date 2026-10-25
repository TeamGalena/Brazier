package com.possible_triangle.brazier.logic;

import com.possible_triangle.brazier.index.BrazierComponents;
import com.possible_triangle.brazier.index.BrazierTags;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SoulCapturing {

    public static void onDeath(LivingEntity deceased, DamageSource source) {
        if (!BrazierTags.SOUL_CAPTURE.matches(deceased.getType()::is)) return;

        if (!(source.getEntity() instanceof LivingEntity killer)) return;

        var stacks = Stream.of(killer.getMainHandItem(), killer.getOffhandItem());
        stacks.filter(it -> it.is(BrazierTags.CAPTURES_SOULS)).findFirst().ifPresent(stack -> {
            capture(stack, killer, deceased);
        });
    }

    private static void capture(ItemStack stack, LivingEntity killer, LivingEntity deceased) {
        stack.set(BrazierComponents.CAPTURED_SOUL.value(), new CapturedSoul(deceased.getType().builtInRegistryHolder()));

        killer.playSound(SoundEvents.LODESTONE_COMPASS_LOCK, 1F, 0.2F);
    }

    public static void addTooltip(ItemStack stack, List<Component> tooltip) {
        var soul = stack.get(BrazierComponents.CAPTURED_SOUL.value());
        if (soul != null) {
            tooltip.add(Component.literal("").append(soul.type().value().getDescription()).withStyle(ChatFormatting.RED));
        }
    }

}
