package com.spectra.animation;

/** Плеер: проигрывает одну анимацию, тикает каждый клиентский тик, выдаёт значения дорожек. */
public class AnimationPlayer {
    private Animation current;
    private float time;

    public void play(Animation animation) {
        this.current = animation;
        this.time = 0f;
    }

    public void stop() {
        this.current = null;
        this.time = 0f;
    }

    public void tick() {
        if (current == null) {
            return;
        }
        time++;
        if (!current.loop() && time >= current.duration()) {
            stop();
        }
    }

    public boolean isPlaying() {
        return current != null;
    }

    public Animation current() {
        return current;
    }

    public float time() {
        return time;
    }

    public float value(String trackName) {
        return current == null ? 0f : current.sample(trackName, time);
    }
}
