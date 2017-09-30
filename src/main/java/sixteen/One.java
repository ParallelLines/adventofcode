package sixteen;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import javafx.util.Pair;

import java.util.LinkedList;

import static java.lang.Math.abs;

/**
 * Created by parallellines on 29.09.2017.
 */
public class One extends Task<Integer> {

    private Pair<Integer, Integer> defaultPosition = new Pair<>(0, 0);
    private Direction defaultDirection = Direction.XR;

    private enum Direction {
        XR (1, 0), XL(-1, 0), YU(0, 1), YD(0, -1);

        private Integer x;
        private Integer y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Integer getX() {
            return x;
        }

        public Integer getY() {
            return y;
        }
    }

    One(String fileName) {
        super(fileName);
    }



    @Override
    public Integer solve() {
        LinkedList<String> instructions = Lists.newLinkedList(Splitter.on(",").trimResults().split(readFile().getFirst()));
        Me meMoving = new Me(defaultDirection, defaultPosition);
        instructions.forEach(meMoving::move);
        return meMoving.getDistance();
    }

    private class Me {

        private Direction currentDirection;
        private Pair<Integer, Integer> startPosition;
        private Pair<Integer, Integer> position;

        Me(Direction currentDirection, Pair<Integer, Integer> position) {
            this.currentDirection = currentDirection;
            this.startPosition = position;
            this.position = position;
        }

        void move(String instruction) {
            currentDirection = getDirection(instruction);
            Integer newX = position.getKey() + currentDirection.getX() * getNumberOfSteps(instruction);
            Integer newY = position.getValue() + currentDirection.getY() * getNumberOfSteps(instruction);
            position = new Pair<>(newX, newY);
        }

        private Integer getNumberOfSteps(String instruction) {
            return Integer.parseInt(instruction.substring(1, instruction.length()));
        }

        private Direction getDirection(String instruction) {
            String leftOrRight = instruction.substring(0, 1);
            Direction newDirection = null;
            switch (currentDirection) {
                case XR:
                    if (leftOrRight.equalsIgnoreCase("r"))
                         newDirection = Direction.YD;
                    else newDirection = Direction.YU;
                    break;
                case XL:
                    if (leftOrRight.equalsIgnoreCase("r"))
                        newDirection = Direction.YU;
                    else newDirection = Direction.YD;
                    break;
                case YU:
                    if (leftOrRight.equalsIgnoreCase("r"))
                        newDirection = Direction.XR;
                    else newDirection = Direction.XL;
                    break;
                case YD:
                    if (leftOrRight.equalsIgnoreCase("r"))
                        newDirection = Direction.XL;
                    else newDirection = Direction.XR;
                    break;
            }
            return newDirection;
        }

        Integer getDistance() {
            Integer projectionX = abs(startPosition.getKey()) - abs(position.getKey());
            Integer projectionY = abs(startPosition.getValue()) - abs(position.getValue());
            return abs(projectionX) + abs(projectionY);
        }
    }
}
