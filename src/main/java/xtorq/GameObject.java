package xtorq;

import com.component.Rigidbody;
import com.dataStructure.Transform;
import com.dataStructure.Tuple;
import com.file.Parser;
import com.physics.Collision;
import com.physics.Trigger;
import com.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class GameObject extends Object {
    private List<Component> components;
    private float lastZIndex;

    private String name;
    private boolean serializable = true;

    public boolean isUi = false;

    private boolean isStarted = false;

    private Tuple<Integer> gridCoords = new Tuple<>(0, 0, 0);

    public GameObject(String name, Transform transform, int zIndex) {
        this.name = name;
        this.transform = transform;
        this.components = new ArrayList<>();
        this.zIndex = zIndex;
        this.lastZIndex = this.zIndex;
    }

    public String getName() {
        return this.name;
    }

    public void collision(Collision coll) {
        for (Component c : components) {
            c.collision(coll);
        }
    }

    public void trigger(Trigger trigger) {
        for (Component c : components) {
            c.trigger(trigger);
        }
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(c);
                return;
            }
        }
    }

    public List<Component> getAllComponents() {
        return this.components;
    }

    public void addComponent(Component c) {
        components.add(c);
        c.gameObject = this;
    }

    public Tuple<Integer> getGridCoords() {
        Integer gridX = (int)(Math.floor(this.transform.position.x / Constants.TILE_WIDTH) * Constants.TILE_WIDTH);
        Integer gridY = (int)(Math.floor(this.transform.position.y / Constants.TILE_WIDTH) * Constants.TILE_HEIGHT);
        gridCoords.x = gridX;
        gridCoords.y = gridY;
        gridCoords.z = this.zIndex;

        return gridCoords;
    }

    public GameObject copy() {
        GameObject newGameObject = new GameObject(this.name, transform.copy(), this.zIndex);
        for (Component c : components) {
            Component copy = c.copy();
            if (copy != null) {
                newGameObject.addComponent(copy);
            }
        }

        newGameObject.start();

        return newGameObject;
    }


    public void update(double dt) {
        // Not really sure how game objects are getting added that haven't been started...
        if (!isStarted) {
            start();
        }

        for (Component c : components) {
            if (!(c instanceof Rigidbody)) {
                c.update(dt);
            }
        }

        lastZIndex = this.zIndex;
    }

    public void setNonserializable() {
        serializable = false;
    }

    public void start() {
        this.isStarted = true;
        for (Component c : components) {
            c.start();
        }
    }

    @Override
    public String serialize(int tabSize) {
        if (!serializable) return "";

        StringBuilder builder = new StringBuilder();
        // Game Object
        builder.append(beginObjectProperty("GameObject", tabSize));

        // Transform
        builder.append(transform.serialize(tabSize + 1));
        builder.append(addEnding(true, true));

        builder.append(addStringProperty("Name", name, tabSize + 1, true, true));

        // Name
        if (components.size() > 0) {
            builder.append(addIntProperty("ZIndex", this.zIndex, tabSize + 1, true, true));
            builder.append(beginObjectProperty("Components", tabSize + 1));
        } else {
            builder.append(addIntProperty("ZIndex", this.zIndex, tabSize + 1, true, false));
        }

        int i = 0;
        for (Component c : components) {
            String str = c.serialize(tabSize + 2);
            if (str.compareTo("") != 0) {
                builder.append(str);
                if (i != components.size() - 1) {
                    builder.append(addEnding(true, true));
                } else {
                    builder.append(addEnding(true, false));
                }
            }
            i++;
        }

        if (components.size() > 0) {
            builder.append(closeObjectProperty(tabSize + 1));
        }

        builder.append(addEnding(true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static GameObject deserialize() {
        Parser.consumeBeginObjectProperty("GameObject");

        Transform transform = Transform.deserialize();
        Parser.consume(',');
        String name = Parser.consumeStringProperty("Name");
        Parser.consume(',');
        int zIndex = Parser.consumeIntProperty("ZIndex");

        GameObject go = new GameObject(name, transform, zIndex);

        if (Parser.peek() == ',') {
            Parser.consume(',');
            Parser.consumeBeginObjectProperty("Components");
            go.addComponent(Parser.parseComponent());

            while (Parser.peek() == ',') {
                Parser.consume(',');
                go.addComponent(Parser.parseComponent());
            }
            Parser.consumeEndObjectProperty();
        }
        Parser.consumeEndObjectProperty();

        return go;
    }

    public void setUi(boolean val) {
        this.isUi = val;
    }
}
