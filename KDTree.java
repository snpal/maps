package bearmaps;
import java.util.List;

public class KDTree implements PointSet {

    private Node root;

    private class Node implements Comparable<Node> {
        private Point p;
        private boolean horizontal;
        private Node leftChild;
        private Node rightChild;

        Node(Point p, boolean horizontal) {
            this.p = p;
            this.horizontal = horizontal;
        }

        @Override
        public int compareTo(Node N) {
            if (N == null) {
                throw new NullPointerException();
            }
            return comparePoints(p, N.p, horizontal);
        }
    }
    public KDTree(List<Point> points) {
        for (int i = 0; i < points.size(); i++) {
            root = add(points.get(i), root, true);
        }
    }

    private Node add(Point p, Node N, boolean horizontal) {
        if (N == null) {
            return new Node(p, horizontal);
        }
        if (p.equals(N.p)) {
            return N;
        }
        int compared = comparePoints(p, N.p, N.horizontal);
        if (compared < 0) {
            N.leftChild = add(p, N.leftChild, !horizontal);
        } else if (compared >= 0) {
            N.rightChild = add(p, N.rightChild, !horizontal);
        }
        return N;

    }

    private int comparePoints(Point A, Point B, boolean horizontal) {
        if (horizontal) {
            return Double.compare(A.getX(), B.getX());
        } else {
            return Double.compare(A.getY(), B.getY());
        }
    }
    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node nearestNode = nearestHelper(root, goal, root);
        return nearestNode.p;
    }

    private Node nearestHelper(Node N, Point goal, Node best) {
        if (N == null) {
            return best;
        }
        if (Point.distance(N.p, goal) < Point.distance(best.p, goal)) {
            best = N;
        }

        Node goodSide;
        Node badSide;

        if (N.compareTo(new Node(goal, true)) > 0) {
            goodSide = N.leftChild;
            badSide = N.rightChild;
        } else {
            goodSide = N.rightChild;
            badSide = N.leftChild;
        }

        best = nearestHelper(goodSide, goal, best);

        if (badSide != null) {
            Point badSideNearest;
            if (N.horizontal) {
                badSideNearest = new Point(N.p.getX(), goal.getY());
            } else {
                badSideNearest = new Point(goal.getX(), N.p.getY());
            }
            if (Point.distance(badSideNearest, goal) <= Point.distance(best.p, goal)) {
                best = nearestHelper(badSide, goal, best);
            }
        }
        return best;
    }

    public static void main(String[] args) {

    }
}
