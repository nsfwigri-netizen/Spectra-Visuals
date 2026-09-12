package com.spectra.animation;

/** Один ключевой кадр: момент времени (в тиках), значение и easing к следующему кадру. */
public record Keyframe(float time, float value, Easing easing) {
}
