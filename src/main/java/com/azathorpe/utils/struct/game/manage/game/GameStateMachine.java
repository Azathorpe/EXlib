package com.azathorpe.utils.struct.game.manage.game;

public class GameStateMachine {
    private GameState current;
    private final GameContext ctx;   // 门面，不依赖具体场景

    public GameStateMachine(GameContext ctx, GameState initial) {
        this.ctx = ctx;
        this.current = initial;
        current.enter(ctx);
    }

    public void update(float dt) {
        current.update(ctx, dt);
    }

    public void render() {
        current.render(ctx);
    }

    public void transitionTo(GameState next) {
        current.exit(ctx);
        current = next;
        current.enter(ctx);
    }
}