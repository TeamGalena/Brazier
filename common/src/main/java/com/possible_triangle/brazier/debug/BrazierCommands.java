package com.possible_triangle.brazier.debug;

import static net.minecraft.commands.Commands.literal;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.possible_triangle.brazier.BrazierConstants;
import com.possible_triangle.brazier.index.BrazierTags;
import com.possible_triangle.brazier.logic.CapturedSoul;
import com.possible_triangle.brazier.logic.SoulCapturing;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class BrazierCommands {

    private static final DynamicCommandExceptionType CANNOT_HOLD_SOUL = new DynamicCommandExceptionType(it -> {
        var stack = (ItemStack) it;
        return Component.translatable(stack.getDescriptionId())
                .append(Component.literal(" cannot hold souls"));
    });

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                literal(BrazierConstants.MOD_ID)
                        .then(literal("soul")
                                .then(literal("place")
                                        .executes(BrazierCommands::placeSoul)
                                )
                        )
        );
    }

    private static int placeSoul(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var player = context.getSource().getPlayerOrException();
        var stack = player.getMainHandItem();

        if (!stack.is(BrazierTags.CAPTURES_SOULS)) {
            throw CANNOT_HOLD_SOUL.create(stack);
        }

        var soul = new CapturedSoul(EntityType.ZOMBIE.builtInRegistryHolder());
        SoulCapturing.capture(stack, player, soul);

        return 1;
    }

}
