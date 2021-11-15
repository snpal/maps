package bearmaps.proj2c;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private int numStatesExplored;
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private HashMap<Vertex, Vertex> edgeTo;
    private HashMap<Vertex, Double> distTo;
    private ArrayHeapMinPQ<Vertex> pq;
    private AStarGraph<Vertex> input;
    private Vertex goal;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        this.pq = new ArrayHeapMinPQ<>();
        this.pq.add(start, 0);
        this.input = input;
        this.goal = end;
        this.solution = new ArrayList<>();
        this.solutionWeight = 0;
        this.outcome = SolverOutcome.UNSOLVABLE;
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();
        this.edgeTo.put(start, null);
        this.distTo.put(start, 0.0);

        while (!(pq.size() == 0) //&& !(sw.elapsedTime() >= timeout)
                && !(outcome == SolverOutcome.SOLVED)) {
            Vertex current = pq.removeSmallest();
            numStatesExplored += 1;
            if (current.equals(goal)) {
                solutionWeight = distTo.get(current);
                outcome = SolverOutcome.SOLVED;
            }
            List<WeightedEdge<Vertex>> neighbors = input.neighbors(current);
            for (WeightedEdge<Vertex> neighbor : neighbors) {
                numStatesExplored += 1;
                relax(neighbor);
            }
        }
        if (!(outcome == SolverOutcome.SOLVED)) {
            if (pq.size() == 0) {
                this.outcome = SolverOutcome.UNSOLVABLE;
            } else if (sw.elapsedTime() > timeout) {
                this.outcome = SolverOutcome.TIMEOUT;
            }
        }

        timeSpent = sw.elapsedTime();
    }

    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();

        if (!distTo.containsKey(q)) {
            distTo.put(q, Double.POSITIVE_INFINITY);
        }

        if (distTo.get(p) + w < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (pq.contains(q)) {
                pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, goal));
            } else {
                pq.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, goal));
            }
        }
    }

    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        if (outcome == SolverOutcome.UNSOLVABLE) {
            return List.of();
        }
        List<Vertex> solnBackwards = new ArrayList<>();
        Vertex from = edgeTo.get(goal);
        solnBackwards.add(from);
        while (edgeTo.containsKey(from)) {
            from = edgeTo.get(from);
            solnBackwards.add(from);
        }
        for (int i = solnBackwards.size() - 1; i >= 0; i--) {
            solution.add(solnBackwards.get(i));
        }
        solution.remove(0);
        if (outcome == SolverOutcome.SOLVED) {
            solution.add(goal);
        }
        return solution;
    }
    public double solutionWeight() {
        return solutionWeight;
    }
    public int numStatesExplored() {
        return numStatesExplored;
    }
    public double explorationTime() {
        return timeSpent;
    }
}
