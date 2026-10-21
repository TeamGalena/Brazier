package com.possible_triangle.brazier.platform;

import com.possible_triangle.brazier.platform.services.*;

import java.util.Optional;
import java.util.ServiceLoader;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IConfigs CONFIGS = load(IConfigs.class);
    static final Optional<IDatagen> DATAGEN = loadOptional(IDatagen.class);

    private static <T> T load(Class<T> clazz) {
        return loadOptional(clazz)
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }

    private static <T> Optional<T> loadOptional(Class<T> clazz) {
        var classLoader = Services.class.getClassLoader();
        return ServiceLoader.load(clazz, classLoader)
                .findFirst();
    }

    public static class Client {
        public static final IClientHelper PLATFORM = load(IClientHelper.class);
    }

}
