import java.util.List;
import java.util.Vector;

public class IrisData {
    public List<Double> attributes;
    public String name;

    public IrisData(List<Double> attributes) {
        this.attributes = attributes;
    }
    public IrisData(List<Double> attributes, String name) {
        this.attributes = attributes;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Iris attributes: " + attributes + '\n';
    }
}
