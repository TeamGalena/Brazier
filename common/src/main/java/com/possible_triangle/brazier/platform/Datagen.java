package com.possible_triangle.brazier.platform;

import com.possible_triangle.brazier.platform.services.IDatagen;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.lang3.function.TriConsumer;

public class Datagen {

    public static <T1, T2> NonNullBiConsumer<T1, T2> consume(TriConsumer<IDatagen, T1, T2> consumer) {
        return run(service -> (a, b) -> consumer.accept(service, a, b), NonNullBiConsumer::noop);
    }

    public static <T> NonNullConsumer<T> consume(BiConsumer<IDatagen, T> consumer) {
        return run(service -> it -> consumer.accept(service, it), NonNullConsumer::noop);
    }

    private static <T> T run(Function<IDatagen, T> factory, Supplier<T> fallback) {
        return Services.DATAGEN
                .map(factory)
                .orElseGet(fallback);
    }

}
