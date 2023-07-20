package dungeon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Grid2D implements Grid {

  private final int row;
  private final int col;
  private int totalCaves;
  private int totalTunnel;
  private final Location[][] grid;

  Grid2D(int row, int col) {
    if (row < 0) {
      throw new IllegalArgumentException("Invalid row!");
    }
    if (col < 0) {
      throw new IllegalArgumentException("Invalid col!");
    }
    this.row = row;
    this.col = col;
    grid = new Location[row][col];
  }

  @Override
  public Grid copy() {
    try {
      return (Grid) this.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }

  @Override
  public int getRow() {
    return row;
  }

  @Override
  public int getCol() {
    return col;
  }

  @Override
  public Location getLocation(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("Invalid position!");
    }
    return grid[position.getX()][position.getY()];
  }

  @Override
  public Location getLocation(int x, int y) {
    if (x < 0) {
      throw new IllegalArgumentException("Invalid x!");
    }
    if (y < 0) {
      throw new IllegalArgumentException("Invalid y!");
    }
    return grid[x][y];
  }

  @Override
  public int getTotalCaves() {
    return totalCaves;
  }

  @Override
  public int getTotalTunnel() {
    return totalTunnel;
  }

  @Override
  public Location[][] generateMaze(int interconnectivity, boolean isWrapping) {
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Invalid interconnectivity!");
    }
    // Initialise
    List<Edge> leftovers = new ArrayList<>();
    List<List<Edge>> minimalSpanningTree = new ArrayList<>();
    List<Edge> possiblePaths = generateAllPossiblePaths(isWrapping);

    // Kruskal's algorithm
    while (!possiblePaths.isEmpty()) {
      int randomIndex = RandomNetwork.nextInt(possiblePaths.size());
      Edge edge = possiblePaths.get(randomIndex);
      if (minimalSpanningTree.isEmpty()) {
        List<Edge> path = new ArrayList<>();
        path.add(edge);
        minimalSpanningTree.add(path);
      } else {
        // Find all paths in minimum spanning tree that contains this edge
        List<List<Edge>> findPath = minimalSpanningTree.stream()
                .filter(p -> p.stream().anyMatch(e -> e.contains(edge)))
                .collect(Collectors.toList());
        if (findPath.size() == 0) {
          // If no such path found; add to minimum spanning tree
          List<Edge> path = new ArrayList<>();
          path.add(edge);
          minimalSpanningTree.add(path);
        } else if (findPath.size() == 1) {
          // If only one such path found; find all edges in path that contains this edge
          List<Edge> findEdge = findPath.get(0).stream()
                  .filter(e -> e.contains(edge)).collect(Collectors.toList());
          if (findEdge.size() == 1) {
            // If only one such edge found, it's terminal; so add edge to path
            findPath.get(0).add(edge);
          } else {
            // Else add edge to leftovers
            leftovers.add(edge);
          }
        } else {
          leftovers.add(edge);
          minimalSpanningTree.remove(findPath.get(0));
          minimalSpanningTree.remove(findPath.get(1));
          minimalSpanningTree.add(
                  Stream.concat(findPath.get(0).stream(), findPath.get(1).stream())
                          .collect(Collectors.toList())
          );
          minimalSpanningTree.get(minimalSpanningTree.size() - 1).add(edge);
        }
      }
      possiblePaths.remove(randomIndex);
    }

    // Adding leftovers
    List<Edge> grid = minimalSpanningTree.get(0);
    for (int i = 0; i < interconnectivity && (!(leftovers.isEmpty())); i++) {
      int randomIndex = RandomNetwork.nextInt(leftovers.size());
      grid.add(leftovers.get(randomIndex));
      leftovers.remove(randomIndex);
    }

    // Mutating locations to dungeon.Cave or dungeon.Tunnel depending on connection
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        Position position = new Position(i, j);
        Set<Action> validActions = new HashSet<>();
        grid.forEach(e -> {
          if (e.contains(position)) {
            validActions.add(e.getDirection(position, isWrapping));
          }
        });
        List<Action> validActionsList = new ArrayList<>(validActions);
        if (validActions.size() == 2) {
          this.grid[i][j] = new Tunnel(position, validActionsList);
          totalTunnel++;
        } else {
          this.grid[i][j] = new Cave(position, validActionsList);
          totalCaves++;
        }
      }
    }
    return this.grid;
  }

  private List<Edge> generateAllPossiblePaths(boolean isWrapping) {
    List<Edge> possiblePaths = new ArrayList<>();
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        if (isWrapping) {
          possiblePaths.add(new Edge(new Position(i, 0), new Position(i, col - 1)));
          possiblePaths.add(new Edge(new Position(0, j), new Position(row - 1, j)));
        }
        if (i < row - 1) {
          possiblePaths.add(new Edge(new Position(i, j), new Position(i + 1, j)));
        }
        if (j < col - 1) {
          possiblePaths.add(new Edge(new Position(i, j), new Position(i, j + 1)));
        }
      }
    }
    return possiblePaths;
  }

  @Override
  public List<Action> calculatePath(Position start, Position end, String method) {
    if (start == null) {
      throw new IllegalArgumentException("Invalid position!");
    }
    if (end == null) {
      throw new IllegalArgumentException("Invalid position!");
    }
    if (method == null) {
      throw new IllegalArgumentException("Invalid method!");
    }
    switch (method.toLowerCase(Locale.getDefault())) {
      case "dfs":
        return calculatePathByDfs(start, end);
      case "bfs":
        return calculatePathByBfs(start, end);
      default:
        throw new IllegalArgumentException("No such method!");
    }
  }

  private List<Action> calculatePathByBrute(Position start, Position end) {
    return new ArrayList<>();
  }

  private List<Action> calculatePathByDfs(Position start, Position end) {
    List<Action> sequence = new ArrayList<>();
    List<Position> explored = new ArrayList<>();
    Stack<Tuple<Position, List<Action>>> frontier = new Stack<>();

    Position node = start;
    frontier.add(new Tuple<>(node, sequence));

    while (!(node.equals(end))) {
      if (!(explored.contains(node))) {
        explored.add(node);
        for (Action a : getLocation(node).getValidActions()) {
          sequence.add(a);
          frontier.add(new Tuple<>(a.getPosition(), sequence));
        }
      }
      if (!(frontier.isEmpty())) {
        Tuple<Position, List<Action>> tuple = frontier.pop();
        node = tuple.x;
        sequence = tuple.y;
      } else {
        throw new IllegalStateException("Frontier is empty!");
      }
    }
    return sequence;
  }

  private List<Action> calculatePathByBfs(Position start, Position end) {
    List<Action> sequence = new ArrayList<>();
    List<Position> explored = new ArrayList<>();
    Queue<Tuple<Position, List<Action>>> frontier = new LinkedList<>();

    Position node = start;
    frontier.add(new Tuple<>(node, sequence));

    while (!(node.equals(end))) {
      Tuple<Position, List<Action>> tuple = frontier.remove();
      node = tuple.x;
      sequence = tuple.y;
      explored.add(node);
      for (Action a : getLocation(node).getValidActions()) {
        if (!(explored.contains(a.getPosition()))) {
          List<Action> tempSeq = new ArrayList<>(sequence);
          tempSeq.add(a);
          frontier.add(new Tuple<>(a.getPosition(), tempSeq));
        }
      }
    }
    return sequence;
  }

  private class Edge {

    private final Position start;
    private final Position end;

    private Edge(Position start, Position end) {
      if (start == null) {
        throw new IllegalArgumentException("Invalid position!");
      }
      if (end == null) {
        throw new IllegalArgumentException("Invalid position!");
      }
      this.start = start;
      this.end = end;
    }

    Action getDirection(Position position, boolean isWrapping) {
      if (position == null) {
        throw new IllegalArgumentException("Invalid position!");
      }
      if (start.getY() + 1 == end.getY()) {
        if (position.equals(start)) {
          return new Action(end, Direction.EAST);
        } else if (position.equals(end)) {
          return new Action(start, Direction.WEST);
        }
      }
      if (start.getY() - 1 == end.getY()) {
        if (position.equals(start)) {
          return new Action(end, Direction.WEST);
        } else if (position.equals(end)) {
          return new Action(start, Direction.EAST);
        }
      }
      if (start.getX() + 1 == end.getX()) {
        if (position.equals(start)) {
          return new Action(end, Direction.SOUTH);
        } else if (position.equals(end)) {
          return new Action(start, Direction.NORTH);
        }
      }
      if (start.getX() - 1 == end.getX()) {
        if (position.equals(start)) {
          return new Action(end, Direction.NORTH);
        } else if (position.equals(end)) {
          return new Action(start, Direction.SOUTH);
        }
      }
      if (isWrapping) {
        if (start.getY() == col - 1 && end.getY() == 0) {
          if (position.equals(start)) {
            return new Action(end, Direction.EAST);
          } else if (position.equals(end)) {
            return new Action(start, Direction.WEST);
          }
        }
        if (start.getY() == 0 && end.getY() == col - 1) {
          if (position.equals(start)) {
            return new Action(end, Direction.WEST);
          } else if (position.equals(end)) {
            return new Action(start, Direction.EAST);
          }
        }
        if (start.getX() == row - 1 && end.getX() == 0) {
          if (position.equals(start)) {
            return new Action(end, Direction.SOUTH);
          } else if (position.equals(end)) {
            return new Action(start, Direction.NORTH);
          }
        }
        if (start.getX() == 0 && end.getX() == row - 1) {
          if (position.equals(start)) {
            return new Action(end, Direction.NORTH);
          } else if (position.equals(end)) {
            return new Action(start, Direction.SOUTH);
          }
        }
      }
      throw new IllegalStateException("No valid direction found at "
              + position + " " + start + " " + end);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Edge)) {
        return false;
      }
      Edge edge = (Edge) o;
      return Objects.equals(start, edge.start) && Objects.equals(end, edge.end);
    }

    @Override
    public int hashCode() {
      return Objects.hash(start, end);
    }

    boolean contains(Position p) {
      return p.equals(start) || p.equals(end);
    }

    boolean contains(Edge e) {
      return contains(e.start) || contains(e.end);
    }

    @Override
    public String toString() {
      return String.format("%s<->%s", start, end);
    }
  }

  private static class Tuple<X, Y> {

    public final X x;
    public final Y y;

    Tuple(X x, Y y) {
      if (x == null) {
        throw new IllegalArgumentException("Invalid x!");
      }
      if (y == null) {
        throw new IllegalArgumentException("Invalid y!");
      }
      this.x = x;
      this.y = y;
    }

    @Override
    public String toString() {
      return String.format("(%s, %s)", x, y);
    }
  }
}
