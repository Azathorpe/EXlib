package com.azathorpe.utils.struct.game;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameObject {
    List<Component> components = new ArrayList<>();

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component component : components) {
            if (componentClass.isInstance(component))
                return componentClass.cast(component);
        }
        throw new IllegalArgumentException("Component " + componentClass.getName() + " not found");
    }

    public <T extends Component> Optional<T> getComponentSafely(Class<T> componentClass) {
        for (Component component : components) {
            if (componentClass.isInstance(component))
                return Optional.of(componentClass.cast(component));
        }
        return Optional.empty();
    }

    public void addComponent(Component component) {
        component.setOwner(this);
        components.add(component);
    }

    public void removeComponent(Component component) {
        component.setOwner(null);
        components.remove(component);
    }
}
