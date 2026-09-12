package com.spectra.animation;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** Реестр всех анимаций мода. Наполняется ContentLoader'ом из JSON. */
public final class AnimationRegistry {
    private static final Map<String, Animation> ANIMATIONS = new LinkedHashMap<>();

    private AnimationRegistry() {
    }

    public static void clear() {
        ANIMATIONS.clear();
    }

    public static void register(Animation animation) {
        ANIMATIONS.put(animation.id(), animation);
    }

    public static Optional<Animation> get(String id) {
        return Optional.ofNullable(ANIMATIONS.get(id));
    }

    public static Collection<Animation> all() {
        return ANIMATIONS.values();
    }

    public static int size() {
        return ANIMATIONS.size();
    }
}
