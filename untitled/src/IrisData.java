import java.util.List;
import java.util.Vector;

public class IrisData {
    public List<Double> attributes;

    public IrisData(List<Double> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Iris attributes: " + attributes + '\n';
    }
}
