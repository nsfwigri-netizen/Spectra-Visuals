package com.spectra.cosmetics;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** Реестр всей косметики мода. Наполняется ContentLoader'ом из JSON. */
public final class CosmeticRegistry {
    private static final Map<String, Cosmetic> COSMETICS = new LinkedHashMap<>();

    private CosmeticRegistry() {
    }

    public static void clear() {
        COSMETICS.clear();
    }

    public static void register(Cosmetic cosmetic) {
        COSMETICS.put(cosmetic.id(), cosmetic);
    }

    public static Optional<Cosmetic> get(String id) {
        return Optional.ofNullable(COSMETICS.get(id));
    }

    public static Collection<Cosmetic> all() {
        return COSMETICS.values();
    }

    public static int size() {
        return COSMETICS.size();
    }
}
