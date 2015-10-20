package net.slimevoid.towers;

import static java.lang.Math.atan2;
import static java.lang.Math.floor;
import static java.lang.Math.toDegrees;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import net.slimevoid.towers.entity.Entity;
import net.slimevoid.towers.view.GameView;
import net.slimevoid.math.Vec2;
import android.app.Activity;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity implements Runnable {

    public GameView view;
    public Timer timer;
    public final Handler handler = new Handler();
    public final Random rand = new Random();
    public SoundPool soundPool;
    public HashMap<Integer, Integer> soundsLoaded;

    public List<Entity> entities;
    public List<Entity> entitiesToAdd;
    public List<Entity> entitiesToRm;

    public int tickCT = 0;
    public long lastCTReset = 0;
    public int frameCT = 0;
    public int tps = 0;
    public int fps = 0;

    public int difficulty,level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        entities = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        entitiesToRm = new ArrayList<>();
        difficulty = getIntent().getExtras().getInt("difficulty");
        level = getIntent().getExtras().getInt("level");
        view = new GameView(this);
        soundPool = new SoundPool(64, AudioManager.STREAM_MUSIC, 0);
        soundsLoaded = new HashMap<>();
        setContentView(view);
        loadSounds();
        new Thread(this).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void loadSounds() {
    }

    public void playSound(float volume, int sound) {
        if(soundsLoaded.containsKey(sound)) {
            soundPool.play(soundsLoaded.get(sound), volume, volume, 0, 0, 1);
        } else {
            loadSound(sound);
        }
    }

    public void loadSound(int sound) {
        int soundID = soundPool.load(view.getContext(), sound, 1);
        soundsLoaded.put(sound, soundID);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private final static int 	MAX_FPS = 50;
    private final static int	FRAME_PERIOD = 1000 / MAX_FPS;

    @Override
    public void run() {
        long beginTime;		// the time when the cycle begun
        long timeDiff;		// the time it took for the cycle to execute
        int sleepTime;		// ms to sleep (<0 if we're behind)
        double dt = FRAME_PERIOD / 1000.0;

        while (true) {
            beginTime = System.currentTimeMillis();
            tick(dt);
            draw();
            timeDiff = System.currentTimeMillis() - beginTime;
            sleepTime = (int)(FRAME_PERIOD - timeDiff);

            if (sleepTime > 0) {
                dt = FRAME_PERIOD / 1000.0;
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {}
            } else {
                dt = timeDiff / 1000.0;
            }
        }
    }

    private void draw() {
        Canvas c = null;
        SurfaceHolder holder = view.getHolder();
        try {
            c = holder.lockCanvas();
            synchronized (holder) {
                view.gameDraw(c);
            }
        } catch(Exception e) {
//            e.printStackTrace();
        } finally {
            if (c != null) {
                holder.unlockCanvasAndPost(c);
            }
        }
    }

    public void tick(double dt) {
        synchronized (entities) {
            for(Entity e : entities) {
                e.tick(dt);
            }
            entities.addAll(entitiesToAdd);
            entitiesToAdd.clear();
            entities.removeAll(entitiesToRm);
            entitiesToRm.clear();
        }

        tickCT ++;
        if(System.currentTimeMillis() - lastCTReset > 1000) {
            tps = tickCT;
            fps = frameCT;
            tickCT = 0;
            frameCT = 0;
            lastCTReset = (System.currentTimeMillis() / 1000) * 1000;
        }
    }

    public void addEntity(Entity e) {
        entitiesToAdd.add(e);
        e.enterGame(this);
    }

    public void removeEntitity(Entity e) {
        if(e.game != null) e.delete();
        else entitiesToRm.add(e);
    }
}
