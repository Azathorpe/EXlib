package com.azathorpe.utils.struct.game.components;

import com.azathorpe.utils.struct.game.Component;

public abstract class UpdateComponent extends Component {
    public abstract void input(long window);
}
