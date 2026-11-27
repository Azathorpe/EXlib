package org.impl.azathorpe.pong.state;

import com.azathorpe.utils.struct.game.manage.game.GameContext;
import com.azathorpe.utils.struct.game.manage.game.GameState;

public class RunState implements GameState {
    @Override
    public void enter(GameContext ctx) {
        ctx.getScene().init(ctx, ctx.getWindow());
    }

    @Override
    public void exit(GameContext ctx) {
        ctx.getScene().exit(ctx.getWindow());
    }

    @Override
    public void update(GameContext ctx, float dt) {
        ctx.getScene().update(ctx, ctx.getWindow());
    }

    @Override
    public void render(GameContext ctx) {
        ctx.getScene().render();
    }
}
