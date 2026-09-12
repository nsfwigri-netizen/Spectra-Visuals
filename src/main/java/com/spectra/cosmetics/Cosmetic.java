package com.spectra.cosmetics;

import java.util.List;

/** Косметический предмет: слот, пути к модели и текстуре, привязанные анимации. */
public class Cosmetic {
    public enum Slot {
        HEAD, BODY, BACK, HAND
    }

    private final String id;
    private final Slot slot;
    private final String model;
    private final String texture;
    private final List<String> animations;

    public Cosmetic(String id, Slot slot, String model, String texture, List<String> animations) {
        this.id = id;
        this.slot = slot;
        this.model = model;
        this.texture = texture;
        this.animations = List.copyOf(animations);
    }

    public String id() {
        return id;
    }

    public Slot slot() {
        return slot;
    }

    public String model() {
        return model;
    }

    public String texture() {
        return texture;
    }

    public List<String> animations() {
        return animations;
    }
}
