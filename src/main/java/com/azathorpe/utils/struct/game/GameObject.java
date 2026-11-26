package com.azathorpe.utils.struct.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GameObject {
    List<Component> components = new ArrayList<>();

    public <T extends Component> Optional<T> getComponent(Class<T> componentClass) {
        for (Component component : components) {
            if (componentClass.isInstance(component))
                return Optional.of(componentClass.cast(component));
        }
        return Optional.empty();
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }
}
