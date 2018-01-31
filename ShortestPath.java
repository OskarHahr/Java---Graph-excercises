import se.kth.id1020.Edge;
import se.kth.id1020.Graph;
import se.kth.id1020.Vertex;

import java.util.*;

public class ShortestPath {
    public void ShortestPath (Graph g, String fromV, String toV, boolean weight){

        Iterable<Vertex> a = g.vertices();
        Vertex temp;
        Vertex fromVertex = new Vertex();
        Vertex toVertex = new Vertex();
        ArrayList<Vertex> reList;
        ArrayList<Edge> returnList;

        while (a.iterator().hasNext() && ((fromVertex.label == null || toVertex.label == null) )) {
            temp = a.iterator().next();
            if (temp.label.equals(fromV))
                fromVertex = temp;
            if (temp.label.equals(toV))
                toVertex = temp;
        }

        System.out.println(fromVertex);
        System.out.println(toVertex);
        reList = Astar(g, fromVertex, toVertex, weight);
        System.out.println(reList.size());
        for (int i = 0; i < reList.size(); i ++)
        {
            System.out.println(reList.get(i));
        }

    }

    private ArrayList<Vertex> Astar(Graph g, Vertex from, Vertex to, boolean weight){

        Map<Vertex, Vertex> cameFrom = new HashMap<Vertex, Vertex>(g.numberOfVertices());
        Set<Vertex> closedSet  = new HashSet<>(g.numberOfVertices()) ;
        ArrayList<Vertex> openSet = new ArrayList<>(); openSet.add(from);
        double[] gscore = new double[g.numberOfVertices()];
        double[] fscore = new double[g.numberOfVertices()];
        double tentative_gscore = 0;

        for (int v = 0;  v < gscore.length; v++) { gscore[v] = Double.POSITIVE_INFINITY; }

        for (int v = 0;  v < fscore.length; v++) { fscore[v] = Double.POSITIVE_INFINITY; }

        Comparator<Vertex> comparator = new Comparator<Vertex>(){
            @Override
            public int compare(Vertex a, Vertex b){
                return Double.compare (fscore[a.id],fscore[b.id]);
            }

        };

        gscore[from.id] = 0;
        Vertex current;

        while (!openSet.isEmpty()){

            current = openSet.get(0);

            if (current == to){
                System.out.println(gscore[to.id]);
                return Reconstruct(cameFrom, to);
            }

            openSet.remove(0);
            closedSet.add(current);
            Iterable<Edge> tempIterable = g.adj(current.id);

            while (tempIterable.iterator().hasNext()){
                Edge temp = tempIterable.iterator().next();
                if (closedSet.contains(g.vertex(temp.to))) continue;
                else
                {

                    if (!openSet.contains(g.vertex((temp.to)))) {openSet.add(g.vertex(temp.to)); }

                    if (weight) tentative_gscore = gscore[current.id] + current.distance(g.vertex(temp.to));
                    else tentative_gscore = gscore[current.id] + 1;

                    if (tentative_gscore > gscore[temp.to])
                        continue;

                    cameFrom.put(g.vertex(temp.to),current );
                    gscore[temp.to] = tentative_gscore;

                    if (weight) fscore[temp.to] = gscore[temp.to] + (g.vertex(temp.to).distance(to));

                    else fscore[temp.to] = gscore[temp.to];

                    Collections.sort(openSet, comparator);

                }
            }
        }
        return null;

    }
    private ArrayList<Vertex> Reconstruct(Map<Vertex, Vertex> a, Vertex from)
    {
        ArrayList<Vertex> totalPath = new ArrayList<>();
        totalPath.add(from);
        while (from != null){
            from = a.get(from);
            if (from != null){
                totalPath.add(from);
            }

        }
        Collections.reverse(totalPath);
        return totalPath;
    }

}
