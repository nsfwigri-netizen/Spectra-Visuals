package com.spectra.animation;

import java.util.Map;

/** Анимация: id, длительность в тиках, флаг цикла и набор дорожек по именам свойств. */
public class Animation {
    private final String id;
    private final float duration;
    private final boolean loop;
    private final Map<String, AnimationTrack> tracks;

    public Animation(String id, float duration, boolean loop, Map<String, AnimationTrack> tracks) {
        this.id = id;
        this.duration = duration;
        this.loop = loop;
        this.tracks = Map.copyOf(tracks);
    }

    public String id() {
        return id;
    }

    public float duration() {
        return duration;
    }

    public boolean loop() {
        return loop;
    }

    public Map<String, AnimationTrack> tracks() {
        return tracks;
    }

    public float sample(String trackName, float time) {
        AnimationTrack track = tracks.get(trackName);
        if (track == null) {
            return 0f;
        }
        float t = loop && duration > 0f ? time % duration : Math.min(time, duration);
        return track.sample(t);
    }
}
