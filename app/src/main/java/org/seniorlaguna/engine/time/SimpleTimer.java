package org.seniorlaguna.engine.time;


public class SimpleTimer {

    private long time = 0;
    private long pauseTime = 0;

    public void start() {
        time = System.currentTimeMillis();
    }

    public void pause() {
        pauseTime = System.currentTimeMillis();
    }

    public void resume() {
        pauseTime = System.currentTimeMillis() - pauseTime;
        time += pauseTime;
    }

    public void stop() {
        time = System.currentTimeMillis() - time;
    }

    public void reset() {
        time = 0;
        pauseTime = 0;
    }

    public long getTimeMillies() {
        return time;
    }

    public float getTimeSeconds() {
        return (getTimeMillies() / 1000f);
    }
}
