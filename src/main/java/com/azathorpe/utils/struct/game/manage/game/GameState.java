package com.azathorpe.utils.struct.game.manage.game;

public interface GameState {
    void enter(GameContext ctx);
    void exit(GameContext ctx);
    void update(GameContext ctx, float dt);
    void render(GameContext ctx);
}