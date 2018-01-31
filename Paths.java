import edu.princeton.cs.algs4.Stopwatch;
import se.kth.id1020.Edge;
import se.kth.id1020.Graph;
import se.kth.id1020.DataSource;

public class Paths{

    private static ShortestPath a = new ShortestPath();
    public static void main(String[] args) {
        Graph g = DataSource.load();
        CC d = new CC (g);
        System.out.println("# Edges: " + g.numberOfEdges());
        System.out.println("# Vertices: " + g.numberOfVertices());
        System.out.print("Number of disconnected components: ");d.PrintCount();
        Stopwatch stopwatch = new Stopwatch();
        a.ShortestPath(g, "Renyn","Parses", true);
        System.out.println(stopwatch.elapsedTime());
    }


    private static class CC{
        private boolean marked[];
        private int[] id;
        private int count;

        private CC(Graph g) {  // Find out how many connected components exist in the graph
            marked = new boolean[g.numberOfVertices()];
            id = new int[g.numberOfVertices()];
            for (int v = 0; v < g.numberOfVertices(); v++) {
                if (!marked[v]) {
                    DFS(g, v);
                    count++;
                }
            }
        }
    private void DFS (Graph g, int v){

            marked[v] = true;
            id[v] = count;
            Iterable<Edge> temp = g.adj(v);
            Edge temporary;
            while (temp.iterator().hasNext()) {
                temporary = temp.iterator().next();
                if (!marked[temporary.to]) {
                    DFS(g, temporary.to);
                }
            }
    }

    private boolean Connected (int a, int b){return (id[a] == id[b]);}

    private void PrintCount(){System.out.println(count);}

    }
}



