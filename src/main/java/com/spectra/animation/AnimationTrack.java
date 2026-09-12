package com.spectra.animation;

import java.util.List;

/** Дорожка одного свойства (scale, alpha, x, y...): набор ключевых кадров + сэмплирование. */
public class AnimationTrack {
    private final List<Keyframe> keyframes;

    public AnimationTrack(List<Keyframe> keyframes) {
        this.keyframes = List.copyOf(keyframes);
    }

    public List<Keyframe> keyframes() {
        return keyframes;
    }

    public float sample(float time) {
        if (keyframes.isEmpty()) {
            return 0f;
        }
        if (keyframes.size() == 1 || time <= keyframes.get(0).time()) {
            return keyframes.get(0).value();
        }
        Keyframe last = keyframes.get(keyframes.size() - 1);
        if (time >= last.time()) {
            return last.value();
        }
        for (int i = 0; i < keyframes.size() - 1; i++) {
            Keyframe a = keyframes.get(i);
            Keyframe b = keyframes.get(i + 1);
            if (time >= a.time() && time <= b.time()) {
                float span = b.time() - a.time();
                float t = span <= 0f ? 0f : (time - a.time()) / span;
                float eased = b.easing().apply(t);
                return a.value() + (b.value() - a.value()) * eased;
            }
        }
        return last.value();
    }
}
