package semestr1;
import l16_03.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LevitAlgorithm {
    static final int INF = Integer.MAX_VALUE; // бесконечность

    static class Edge {
        int  to, weight;
        public Edge( int to, int weight) {

            this.to = to;
            this.weight = weight;
        }

        public int getTo() {
            return to;
        }
    }

    static void levitAlgorithm(List<Edge>[] graph, int source, int[] distance) {
        int n = graph.length;
        Arrays.fill(distance, INF);
        distance[source] = 0;

        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(source);
        boolean[] used = new boolean[n];
        int[] count = new int[n];
        Arrays.fill(count, 1);
        int i=0;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            used[v] = false;

            for (Edge e : graph[v]) {
                if (distance[e.to] > distance[v] + e.weight) {
                    distance[e.to] = distance[v] + e.weight;

                    if (!used[e.to]) {
                        if (count[e.to]++ >= n) {
                            // Обнаружено отрицательное цикл
                            return;
                        }
                        used[e.to] = true;

                        if (!queue.isEmpty() && distance[e.to] < distance[queue.peekFirst()]) {
                            queue.offerFirst(e.to);
                        } else {
                            queue.offerLast(e.to);
                        }
                    }
                }
                i++;
            }
            i++;
        }
        System.out.println("Количество итераций:"+ i);
    }

    public static void main(String[] args) throws FileNotFoundException  {

        Scanner scanner = new Scanner(new File("C:\\Users\\User\\IdeaProjects\\Main\\src\\input.txt"));
        ArrayList<Integer> a = new ArrayList<>();

        while (scanner.hasNextLine()){
            String [] strings=scanner.nextLine().split(";");
            Set <Integer> set=new HashSet<>();
            int countReber=strings.length;

            for (int i = 0; i < countReber; i++) {
                String tek=strings[i];
                String [] versh=tek.split(",");
                set.add(Integer.parseInt(versh[0]));
                set.add(Integer.parseInt(versh[1]));
            }

            int countVersh= Collections.max(set)+1;
            List<Edge>[] graph = new List[countVersh];

            for (int i = 0; i < countVersh; i++) {
                graph[i] = new ArrayList<>();
            }
            for (int i = 0; i <countReber ; i++) {
                String stroka=strings[i];
                String [] value =stroka.split(",");
                graph[Integer.parseInt(value[0])].add(new Edge(Integer.parseInt(value[1]),Integer.parseInt(value[2])));
            }
            int[] distance = new int[countVersh];
            long start =System.nanoTime();
            levitAlgorithm(graph, 0, distance);
            long finish= System.nanoTime();
            System.out.println(Arrays.toString(distance));

            long time=finish-start;

            System.out.println("Время работы:"+ time/1000);
            System.out.println("Количество ребер:"+countReber);
            System.out.println("       ");

        }
    }
}
