package com.prefabs;

import com.component.*;
import com.component.bricks.BreakableBrick;
import com.component.bricks.QuestionBlock;
import com.dataStructure.AssetPool;
import com.dataStructure.Transform;
import xtorq.GameObject;
import com.physics.BoxBounds;
import com.ui.JWindow;
import com.util.Constants;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Prefabs {
    private static JWindow questionBlockWindow = null;

    public static void setQuestionBlockWindow(JWindow window) {
        questionBlockWindow = window;
    }

    public static GameObject MARIO_PREFAB() {
        GameObject player = new GameObject("Mario_Prefab", new Transform(new Vector2f()), 0);

        Spritesheet characterSprites = AssetPool.getSpritesheet("assets/spritesheets/character_and_enemies_32.png");
        Spritesheet bigSprites = AssetPool.getSpritesheet("assets/spritesheets/character_and_enemies_64.png");

        AnimationMachine playerMachine = new AnimationMachine();
        // Small mario animations
        Animation idle = new Animation("Idle", 0.1f, characterSprites.sprites.subList(0, 1), false);
        Animation run = new Animation("Run", 0.1f, characterSprites.sprites.subList(0, 4), true);
        Animation jump = new Animation("Jump", 0.1f, characterSprites.sprites.subList(4, 6), false);
        Animation swim = new Animation("Swim", 0.1f, characterSprites.sprites.subList(9, 14), true);
        playerMachine.setStartAnimation("Idle");

        // Big mario animations
        Animation bigIdle = new Animation("BigIdle", 0.1f, bigSprites.sprites.subList(0, 1), false);
        Animation bigRun = new Animation("BigRun", 0.1f, bigSprites.sprites.subList(0, 4), true);
        Animation bigJump = new Animation("BigJump", 0.1f, bigSprites.sprites.subList(4, 6), false);
        Animation bigSwim = new Animation("BigSwim", 0.1f, bigSprites.sprites.subList(9, 14), true);

        // Fire mario animations
        Animation fireIdle = new Animation("FireIdle", 0.1f, bigSprites.sprites.subList(21, 22), false);
        Animation fireRun = new Animation("FireRun", 0.1f, bigSprites.sprites.subList(21, 25), true);
        Animation fireJump = new Animation("FireJump", 0.1f, bigSprites.sprites.subList(25, 27), false);
        Animation fireSwim = new Animation("FireSwim", 0.1f, bigSprites.sprites.subList(30, 35), true);

        Animation death = new Animation("Death", 0.1f, characterSprites.sprites.subList(6, 7), false);

        // Set up animation state transfers
        // IDLE
        idle.addStateTransfer("StartRunning", "Run");
        idle.addStateTransfer("StartJumping", "Jump");
        idle.addStateTransfer("StartSwim", "Swim");
        idle.addStateTransfer("StartBig", "BigIdle");
        idle.addStateTransfer("Die", "Death");

        bigIdle.addStateTransfer("StartRunning", "BigRun");
        bigIdle.addStateTransfer("StartJumping", "BigJump");
        bigIdle.addStateTransfer("StartSwim", "BigSwim");
        bigIdle.addStateTransfer("StartSmall", "Idle");
        bigIdle.addStateTransfer("StartFire", "FireIdle");

        fireIdle.addStateTransfer("StartRunning", "FireRun");
        fireIdle.addStateTransfer("StartJumping", "FireJump");
        fireIdle.addStateTransfer("StartSwim", "FireSwim");
        fireIdle.addStateTransfer("StartBig", "BigIdle");

        // RUN
        run.addStateTransfer("StartJumping", "Jump");
        run.addStateTransfer("StartIdle", "Idle");
        run.addStateTransfer("StartSwim", "Swim");
        run.addStateTransfer("StartBig", "BigRun");
        run.addStateTransfer("Die", "Death");

        bigRun.addStateTransfer("StartJumping", "BigJump");
        bigRun.addStateTransfer("StartIdle", "BigIdle");
        bigRun.addStateTransfer("StartSwim", "BigSwim");
        bigRun.addStateTransfer("StartSmall", "Run");
        bigRun.addStateTransfer("StartFire", "FireRun");

        fireRun.addStateTransfer("StartJumping", "FireJump");
        fireRun.addStateTransfer("StartIdle", "FireIdle");
        fireRun.addStateTransfer("StartSwim", "FireSwim");
        fireRun.addStateTransfer("StartBig", "BigRun");

        // JUMP
        jump.addStateTransfer("StartRunning", "Run");
        jump.addStateTransfer("StartIdle", "Idle");
        jump.addStateTransfer("StartSwim", "Swim");
        jump.addStateTransfer("StartBig", "BigJump");
        jump.addStateTransfer("Die", "Death");

        bigJump.addStateTransfer("StartRunning", "BigRun");
        bigJump.addStateTransfer("StartIdle", "BigIdle");
        bigJump.addStateTransfer("StartSwim", "BigSwim");
        bigJump.addStateTransfer("StartSmall", "Jump");
        bigJump.addStateTransfer("StartFire", "FireJump");

        fireJump.addStateTransfer("StartRunning", "FireRun");
        fireJump.addStateTransfer("StartIdle", "FireIdle");
        fireJump.addStateTransfer("StartSwim", "FireSwim");
        fireJump.addStateTransfer("StartBig", "BigJump");

        // SWIM
        swim.addStateTransfer("StartRunning", "Run");
        swim.addStateTransfer("StartJumping", "Jump");
        swim.addStateTransfer("StartIdle", "Idle");
        swim.addStateTransfer("StartBig", "BigSwim");
        swim.addStateTransfer("Die", "Death");

        bigSwim.addStateTransfer("StartRunning", "BigRun");
        bigSwim.addStateTransfer("StartJumping", "BigJump");
        bigSwim.addStateTransfer("StartIdle", "BigIdle");
        bigSwim.addStateTransfer("StartSmall", "Swim");
        bigSwim.addStateTransfer("StartFire", "FireSwim");

        fireSwim.addStateTransfer("StartRunning", "FireRun");
        fireSwim.addStateTransfer("StartJumping", "FireJump");
        fireSwim.addStateTransfer("StartIdle", "FireIdle");
        fireSwim.addStateTransfer("StartBig", "BigSwim");

        // Add animations to the machine
        playerMachine.addAnimation(idle);
        playerMachine.addAnimation(run);
        playerMachine.addAnimation(jump);
        playerMachine.addAnimation(swim);
        playerMachine.addAnimation(death);

        playerMachine.addAnimation(bigIdle);
        playerMachine.addAnimation(bigRun);
        playerMachine.addAnimation(bigJump);
        playerMachine.addAnimation(bigSwim);

        playerMachine.addAnimation(fireIdle);
        playerMachine.addAnimation(fireRun);
        playerMachine.addAnimation(fireJump);
        playerMachine.addAnimation(fireSwim);

        // Add the components to the GameObject
        player.addComponent(playerMachine);
        player.addComponent(new SpriteRenderer(playerMachine.getPreviewSprite()));

        BoxBounds playerBoxBounds = new BoxBounds(30, 31, false, false);
        playerBoxBounds.setXBuffer(1);
        player.addComponent(playerBoxBounds);

        player.addComponent(new Rigidbody());
        player.addComponent(new PlayerController());

        player.transform.scale.x = 32;
        player.transform.scale.y = 32;

        return player;
    }

    public static GameObject GOOMBA_PREFAB(int type) {
        GameObject goomba = new GameObject("Goomba_Prefab", new Transform(new Vector2f()), 0);

        Spritesheet characterSprites = AssetPool.getSpritesheet("assets/spritesheets/character_and_enemies_32.png");

        AnimationMachine goombaMachine = new AnimationMachine();
        Animation walk = null;
        Animation squash = null;
        goombaMachine.setStartAnimation("Walk");

        switch (type) {
            case 1:
                walk = new Animation("Walk", 0.2f, characterSprites.sprites.subList(17, 19), true);
                squash = new Animation("Squash", 0.2f, characterSprites.sprites.subList(19, 20), false);
                break;
            case 2:
                walk = new Animation("Walk", 0.2f, characterSprites.sprites.subList(20, 22), true);
                squash = new Animation("Squash", 0.2f, characterSprites.sprites.subList(22, 23), false);
                break;
            case 3:
                walk = new Animation("Walk", 0.2f, characterSprites.sprites.subList(23, 25), true);
                squash = new Animation("Squash", 0.2f, characterSprites.sprites.subList(25, 26), false);
                break;
            case 0:
            default:
                walk = new Animation("Walk", 0.2f, characterSprites.sprites.subList(14, 16), true);
                squash = new Animation("Squash", 0.2f, characterSprites.sprites.subList(16, 17), false);
                break;
        }

        // WALK
        walk.addStateTransfer("StartSquash", "Squash");

        // SQUASH
        squash.addStateTransfer("StartWalk", "Walk");

        goombaMachine.addAnimation(walk);
        goombaMachine.addAnimation(squash);

        goomba.addComponent(goombaMachine);

        goomba.addComponent(new SpriteRenderer(goombaMachine.getPreviewSprite()));
        goomba.transform.scale.x = 32;
        goomba.transform.scale.y = 32;

        goomba.addComponent(new BoxBounds(32, 32, false, false));
        goomba.addComponent(new Rigidbody());
        goomba.addComponent(new GoombaAI());

        return goomba;
    }

    public static GameObject TURTLE() {
        GameObject turtle = new GameObject("Goomba_Prefab", new Transform(new Vector2f()), 0);

        Spritesheet turtleSprites = AssetPool.getSpritesheet("assets/spritesheets/turtle.png");

        AnimationMachine turtleMachine = new AnimationMachine();
        Animation walk = new Animation("Walk", 0.2f, turtleSprites.sprites.subList(0, 2), true);
        Animation spin = new Animation("Spin", 0.1f, turtleSprites.sprites.subList(2, 4), true);
        turtleMachine.setStartAnimation("Walk");

        // Switch to spin
        walk.addStateTransfer("StartSpin", "Spin");

        turtleMachine.addAnimation(walk);
        turtleMachine.addAnimation(spin);

        turtle.addComponent(turtleMachine);

        turtle.addComponent(new SpriteRenderer(turtleMachine.getPreviewSprite()));
        turtle.transform.scale.x = 32;
        turtle.transform.scale.y = 48;

        turtle.addComponent(new BoxBounds(32, 48, false, false));
        turtle.addComponent(new Rigidbody());
        turtle.addComponent(new TurtleAI());

        return turtle;
    }

    public static GameObject QUESTION_BLOCK() {
        Spritesheet items = AssetPool.getSpritesheet("assets/spritesheets/items.png");

        // Set up animation machine
        AnimationMachine questionBlockMachine = new AnimationMachine();

        List<Float> waitTimeIdle = new ArrayList<>(Arrays.asList(0.4f, 0.2f, 0.2f));
        Animation idle = new Animation("Idle", waitTimeIdle, items.sprites.subList(0, 3), true);
        Animation blockHit = new Animation("BlockHit", 0.1f, items.sprites.subList(3, 4), false);

        idle.addStateTransfer("HitBlock", "BlockHit");

        questionBlockMachine.addAnimation(idle);
        questionBlockMachine.addAnimation(blockHit);
        questionBlockMachine.setStartAnimation("Idle");

        // Create game object and add components
        GameObject questionBlock = new GameObject("Question_Block_Prefab", new Transform(new Vector2f()), 0);

        questionBlock.addComponent(questionBlockMachine);
        questionBlock.addComponent(new SpriteRenderer(questionBlockMachine.getPreviewSprite()));

        questionBlock.addComponent(new QuestionBlock(questionBlockWindow.getTitle()));
        questionBlock.addComponent(new BoxBounds(Constants.TILE_WIDTH, Constants.TILE_HEIGHT, true, false));

        questionBlock.transform.scale.x = 32;
        questionBlock.transform.scale.y = 32;

        return questionBlock;
    }

    public static GameObject BRICK_BLOCK() {
        Spritesheet items = AssetPool.getSpritesheet("assets/spritesheets/items.png");

        GameObject brickBlock = new GameObject("Brick_Block_Prefab", new Transform(new Vector2f()), 0);
        brickBlock.addComponent(new BreakableBrick());
        brickBlock.addComponent(new BoxBounds(Constants.TILE_WIDTH, Constants.TILE_HEIGHT, true, false));
        brickBlock.addComponent(new SpriteRenderer(items.sprites.get(5)));

        brickBlock.transform.scale.x = 32;
        brickBlock.transform.scale.y = 32;

        return brickBlock;
    }

    public static GameObject COIN() {
        Spritesheet items = AssetPool.getSpritesheet("assets/spritesheets/items.png");

        // Set up animation machine
        AnimationMachine machine = new AnimationMachine();

        List<Float> waitTimes = new ArrayList<>(Arrays.asList(0.4f, 0.2f, 0.2f));
        Animation idle = new Animation("Idle", waitTimes, items.sprites.subList(7, 10), true);
        Animation collect = new Animation("Collect", 0.1f, items.sprites.subList(7, 8), false);

        idle.addStateTransfer("CollectCoin", "Collect");

        machine.addAnimation(idle);
        machine.addAnimation(collect);
        machine.setStartAnimation("Idle");

        // Add components
        GameObject coin = new GameObject("Coin_Prefab", new Transform(new Vector2f()), 0);
        coin.addComponent(machine);

        coin.addComponent(new Coin());
        coin.addComponent(new BoxBounds(32, 32, false, true));
        coin.addComponent(new SpriteRenderer(machine.getPreviewSprite()));

        coin.transform.scale.x = 32;
        coin.transform.scale.y = 32;

        return coin;
    }

    public static GameObject MUSHROOM_ITEM() {
        Spritesheet items = AssetPool.getSpritesheet("assets/spritesheets/items.png");

        // Add components
        GameObject mushroom = new GameObject("Mushroom_Item_Prefab", new Transform(new Vector2f()), 0);
        mushroom.addComponent(new Mushroom());
        mushroom.addComponent(new BoxBounds(32, 32, false, true));
        mushroom.addComponent(new SpriteRenderer(items.sprites.get(10)));
        mushroom.addComponent(new Rigidbody());

        mushroom.transform.scale.x = 32;
        mushroom.transform.scale.y = 32;

        return mushroom;
    }

    public static GameObject FLOWER_ITEM() {
        Spritesheet items = AssetPool.getSpritesheet("assets/spritesheets/items.png");

        // Add components
        GameObject flower = new GameObject("Flower_Item_Prefab", new Transform(new Vector2f()), 0);
        flower.addComponent(new Flower());
        flower.addComponent(new BoxBounds(32, 32, false, true));
        flower.addComponent(new SpriteRenderer(items.sprites.get(20)));
        flower.addComponent(new Rigidbody());

        flower.transform.scale.x = 32;
        flower.transform.scale.y = 32;

        return flower;
    }

    public static GameObject PIPE_ENTRANCE(int sprite) {
        Spritesheet pipes = AssetPool.getSpritesheet("assets/spritesheets/pipes.png");

        // Create gameobject and add components
        GameObject pipe = new GameObject("Pipe_Prefab", new Transform(new Vector2f()), 0);
        pipe.addComponent(new BoxBounds(64, 64, true, false));
        pipe.addComponent(new SpriteRenderer(pipes.sprites.get(sprite)));
        pipe.addComponent(new Pipe(true, sprite));

        pipe.transform.scale.x = 64;
        pipe.transform.scale.y = 64;

        return pipe;
    }

    public static GameObject PIPE_EXIT(int sprite) {
        Spritesheet pipes = AssetPool.getSpritesheet("assets/spritesheets/pipes.png");

        // Create gameObject and add components
        GameObject pipe = new GameObject("Pipe_Prefab", new Transform(new Vector2f()), 0);
        pipe.addComponent(new BoxBounds(64, 64, true, false));
        pipe.addComponent(new SpriteRenderer(pipes.sprites.get(sprite)));
        pipe.addComponent(new Pipe(false, sprite));

        pipe.transform.scale.x = 64;
        pipe.transform.scale.y = 64;

        return pipe;
    }

    public static GameObject FLAG_TOP() {
        Spritesheet items = AssetPool.getSpritesheet("assets/spritesheets/items.png");

        // Create gameObject add components
        GameObject flagTop = new GameObject("Flag_Top_Prefab", new Transform(new Vector2f()), 0);
        flagTop.addComponent(new BoxBounds(32, 32, false, true));
        flagTop.addComponent(new SpriteRenderer(items.sprites.get(6)));
        flagTop.addComponent(new FlagTop());

        flagTop.transform.scale.x = 32;
        flagTop.transform.scale.y = 32;

        return flagTop;
    }

    public static GameObject FLAG_POLE() {
        Spritesheet items = AssetPool.getSpritesheet("assets/spritesheets/items.png");

        // Create gameObject add components
        GameObject flagTop = new GameObject("Flag_Top_Prefab", new Transform(new Vector2f()), 0);
        flagTop.addComponent(new BoxBounds(32, 32, false, true));
        flagTop.addComponent(new SpriteRenderer(items.sprites.get(33)));
        flagTop.addComponent(new FlagPole());

        flagTop.transform.scale.x = 32;
        flagTop.transform.scale.y = 32;

        return flagTop;
    }

    public static GameObject FIREBALL() {
        Spritesheet items = AssetPool.getSpritesheet("assets/spritesheets/items.png");

        // Create gameObject add components
        GameObject fireball = new GameObject("Fireball_Prefab", new Transform(new Vector2f()), 0);
        fireball.addComponent(new BoxBounds(16, 16, false, true));
        fireball.addComponent(new SpriteRenderer(items.sprites.get(32)));
        fireball.addComponent(new Fireball());
        fireball.addComponent(new Rigidbody());

        fireball.transform.scale.x = 16;
        fireball.transform.scale.y = 16;

        return fireball;
    }

    public static GameObject STAR() {
        Spritesheet items = AssetPool.getSpritesheet("assets/spritesheets/items.png");

        // Create gameObject and add components
        GameObject star = new GameObject("Star_Prefab", new Transform(new Vector2f()), 0);
        star.addComponent(new BoxBounds(32, 32, false, true));
        star.addComponent(new SpriteRenderer(items.sprites.get(24)));
        star.addComponent(new Rigidbody());
        star.addComponent(new Star());

        star.transform.scale.x = 32;
        star.transform.scale.y = 32;

        return star;
    }
}
