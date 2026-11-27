package com.azathorpe.utils.struct.game.components;

import com.azathorpe.utils.struct.game.Component;

/**
 * 每帧更新调用所有挂有这个组件的对象
 */
public abstract class UpdateComponent extends Component {
    public abstract void input(long window);
}
