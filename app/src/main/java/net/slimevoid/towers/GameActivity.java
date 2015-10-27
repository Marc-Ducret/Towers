package net.slimevoid.towers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.slimevoid.math.Vec2;
import net.slimevoid.towers.entity.Creep;
import net.slimevoid.towers.entity.DamageEffect;
import net.slimevoid.towers.entity.Entity;
import net.slimevoid.towers.entity.SlowEffect;
import net.slimevoid.towers.entity.Tower;
import net.slimevoid.towers.entity.TowerProps;
import net.slimevoid.towers.view.GameView;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity implements Runnable {

    public GameView view;
    public SoundPool soundPool;
    public HashMap<Integer, Integer> soundsLoaded;
    public Random rand;

    public List<Entity> entities;
    public List<Entity> entitiesToAdd;
    public List<Entity> entitiesToRm;

    public int tickCT = 0;
    public long lastCTReset = 0;
    public int frameCT = 0;
    public int tps = 0;
    public int fps = 0;

    public int difficulty, level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rand = new Random();
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
        while(!GameView.isInitialized()) {
            try{
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }

        long beginTime;		// the time when the cycle begun
        long timeDiff;		// the time it took for the cycle to execute
        int sleepTime;		// ms to sleep (<0 if we're behind)
        double dt = FRAME_PERIOD / 1000.0;

        //TEST
        for(int x = 0; x <= 10; x ++) {
            for(int y = 0; y <= 10; y ++) {
                Entity e = new Creep();
                e.pos = Vec2.NULL.add(x, y);
                addEntity(e);
            }
        }
        addEntity(new Tower(
                new TowerProps(5, 2, 0, 2, new DamageEffect(10)),
                Vec2.NULL.add(5, 5)));
        addEntity(new Tower(
                new TowerProps(3, .5, 2, 1.5, new SlowEffect()),
                Vec2.NULL.add(9, 9)));

        //TEST END

        while (true) {
            beginTime = System.currentTimeMillis();
            tick(dt);
            Collections.sort(entities, new Comparator<Entity>() {
                @Override
                public int compare(Entity lhs, Entity rhs) {
                    return (int) ((lhs.pos.y - rhs.pos.y) * 1000);
                }
            });
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
