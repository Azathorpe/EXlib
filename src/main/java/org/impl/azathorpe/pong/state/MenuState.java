package org.impl.azathorpe.pong.state;

import com.azathorpe.utils.struct.game.manage.game.GameContext;
import com.azathorpe.utils.struct.game.manage.game.GameState;

public class MenuState implements GameState {
    @Override
    public void enter(GameContext ctx) {
        ctx.getCurrentScene().init(ctx, ctx.getWindow());
    }

    @Override
    public void exit(GameContext ctx) {
        ctx.getCurrentScene().exit();
    }

    @Override
    public void update(GameContext ctx, float dt) {
        ctx.getCurrentScene().update(ctx, ctx.getWindow());
    }

    @Override
    public void render(GameContext ctx) {
        ctx.getCurrentScene().render();
    }
}
