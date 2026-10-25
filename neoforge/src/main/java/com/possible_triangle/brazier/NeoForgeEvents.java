package com.possible_triangle.brazier;

import com.possible_triangle.brazier.logic.BrazierLogic;
import com.possible_triangle.brazier.logic.SoulCapturing;
import com.possible_triangle.brazier.world.item.BrazierIndicator;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public class NeoForgeEvents {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    private static void onMobSpawn(MobSpawnEvent.PositionCheck event) {
        if (BrazierLogic.prevents(event.getEntity(), event.getLevel(), event.getSpawnType())) {
            event.setResult(MobSpawnEvent.PositionCheck.Result.FAIL);
        }
    }

    @SubscribeEvent
    private static void onDeath(LivingDeathEvent event) {
        SoulCapturing.onDeath(event.getEntity(), event.getSource());
    }

    @SubscribeEvent
    private static void onTooltip(ItemTooltipEvent event) {
        SoulCapturing.addTooltip(event.getItemStack(), event.getToolTip());
    }

    @SubscribeEvent
    private static void onPlayerTick(PlayerTickEvent.Pre event) {
        BrazierIndicator.playerTick(event.getEntity());
    }

}
