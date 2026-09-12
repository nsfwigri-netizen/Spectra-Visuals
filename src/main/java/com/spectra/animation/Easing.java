package com.spectra.animation;

public enum Easing {
    LINEAR,
    EASE_IN,
    EASE_OUT,
    EASE_IN_OUT,
    SINE;

    public float apply(float t) {
        return switch (this) {
            case LINEAR -> t;
            case EASE_IN -> t * t;
            case EASE_OUT -> 1f - (1f - t) * (1f - t);
            case EASE_IN_OUT -> t < 0.5f
                    ? 2f * t * t
                    : 1f - (float) Math.pow(-2f * t + 2f, 2f) / 2f;
            case SINE -> (float) Math.sin(t * Math.PI / 2.0);
        };
    }

    public static Easing byName(String name) {
        for (Easing easing : values()) {
            if (easing.name().equalsIgnoreCase(name)) {
                return easing;
            }
        }
        return LINEAR;
    }
}
