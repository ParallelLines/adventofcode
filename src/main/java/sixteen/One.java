package sixteen;

import javafx.util.Pair;

/**
 * Created by parallellines on 29.09.2017.
 */
public class One extends Task<Integer> {

    private Pair<Integer, Integer> position;

    private enum Direction {
        XR, XL, YU, YD
    }

    public One(String fileName) {
        super(fileName);
        this.position = new Pair<>(0, 0);
    }

    @Override
    public Integer solve() {

        return 0;
    }

}
