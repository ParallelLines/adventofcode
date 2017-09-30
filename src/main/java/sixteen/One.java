package sixteen;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import javafx.util.Pair;

import java.util.LinkedList;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

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
        private LinkedList<Segment> allSegments;
        private Pair<Integer, Integer> firstPositionVisitedTwice = null;


        Me(Direction currentDirection, Pair<Integer, Integer> position) {
            this.currentDirection = currentDirection;
            this.startPosition = position;
            this.position = position;
            allSegments = new LinkedList<>();
        }

        void move(String instruction) {
            if (firstPositionVisitedTwice == null) {
                currentDirection = getDirection(instruction);
                Integer newX = position.getKey() + currentDirection.getX() * getNumberOfSteps(instruction);
                Integer newY = position.getValue() + currentDirection.getY() * getNumberOfSteps(instruction);
                Segment newSegment = new Segment(position.getKey(), position.getValue(), newX, newY);
                position = new Pair<>(newX, newY);
                for (Segment segment : allSegments) {
                    Pair<Integer, Integer> crossPoint = segment.doesCross(newSegment);
                    if (crossPoint != null) {
                        firstPositionVisitedTwice = crossPoint;
                        position = crossPoint;
                    }
                }
                allSegments.add(newSegment);
            }

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

    class Segment {

        private Integer startX;
        private Integer startY;
        private Integer endX;
        private Integer endY;

        Segment(Integer startX, Integer startY, Integer endX, Integer endY) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }

        private boolean isVertical(Segment segment) {
            return segment.getStartX().equals(segment.getEndX());
        }

        private Segment getVertical(Segment one, Segment two) {
            if (isVertical(one))
                return one;
            return two;
        }

        private Segment getHorizontal(Segment one, Segment two) {
            if (!isVertical(one))
                return one;
            return two;
        }

        public Pair<Integer, Integer> doesCross(Segment otherSegment) {
            if (isVertical(this) ^ isVertical(otherSegment)) {
                Segment vertical = getVertical(this, otherSegment);
                Segment horizontal = getHorizontal(this, otherSegment);
                if (vertical.getStartX() > min(horizontal.getStartX(), horizontal.endX) &&
                        vertical.getStartX() < max(horizontal.getStartX(), horizontal.endX) &&
                        horizontal.getStartY() > min(vertical.getStartY(), vertical.getEndY()) &&
                        horizontal.getStartY() < max(vertical.getStartY(), vertical.getEndY()))
                    return new Pair<>(vertical.getStartX(), horizontal.getStartY());
            }
            return null;
        }

        public Integer getStartX() {
            return startX;
        }

        public Integer getStartY() {
            return startY;
        }

        public Integer getEndX() {
            return endX;
        }

        public Integer getEndY() {
            return endY;
        }
    }
}
