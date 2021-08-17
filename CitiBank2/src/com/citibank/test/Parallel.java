package com.citibank.test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parallel {

    public int calcMaxParallel(List<Line> lines){
        List<NumberedPoint> points = getNumberedPoints(lines);
        return computeMaxParallel(points);
    }

    private int computeMaxParallel(List<NumberedPoint> points) {
        points.sort(Comparator.comparing(NumberedPoint::getX));

        Set<Integer> set = new HashSet<>();
        int counter = 0;
        int max = 0;
        for (NumberedPoint p : points){
            int num = p.number;
            if(!set.contains(num)) {
                set.add(num);
                counter++;
                max = Math.max(max, counter);
            } else {
                set.remove(num);
                counter--;
            }
        }
        return max;
    }

    private List<NumberedPoint> getNumberedPoints(List<Line> lines) {
        AtomicInteger counter = new AtomicInteger(1);
        return lines.stream()
                .flatMap(l -> Stream.of(new NumberedPoint(l.p1.x, counter.get()), new NumberedPoint(l.p2.x, counter.getAndIncrement())))
                .collect(Collectors.toList());
    }

    private static class NumberedPoint {
        int x;
        int number;

        public NumberedPoint(int x, int num) {
            this.x = x;
            this.number = num;
        }

        public int getX() {
            return x;
        }
    }

    public static class Line {
        Point p1, p2;

        public Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    public static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


    public static void main(String[] args) {
        List<Line> lines = new ArrayList<>();

        Point p11 = new Point(1,6);
        Point p12 = new Point(4,6);

        Point p21 = new Point(2,4);
        Point p22 = new Point(6,4);

        Point p31 = new Point(3,2);
        Point p32 = new Point(10,2);

        Point p41 = new Point(8,5);
        Point p42 = new Point(9,5);

        Point p51 = new Point(3,8);
        Point p52 = new Point(9,8);

        Point p61 = new Point(1,8);
        Point p62 = new Point(2,8);

        lines.add(new Line(p31, p32));
        lines.add(new Line(p11, p12));
        lines.add(new Line(p41, p42));
        lines.add(new Line(p21, p22));
        lines.add(new Line(p61, p62));
        lines.add(new Line(p51, p52));

        Parallel p = new Parallel();
        System.out.println(p.calcMaxParallel(lines));
    }
}