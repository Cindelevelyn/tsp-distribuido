package caixeiroviajante;

import java.util.ArrayList;

/**
 *
 * @author Cindel
 */
public class CaixeiroViajante {

    private static int menor = Integer.MAX_VALUE;

    public static void main(String[] args) {

        FileManager fileManager = new FileManager();
        ArrayList<String> text = fileManager.stringReader(
                "C:/Users/cinde/Documents/facul/8_PERIODO/Sistemas Distribuidos/tsp/CaixeiroViajante/src/caixeiroviajante/teste1.txt");

        int nVertex;
        Graph grafo = new AdjMatrix(0);

        nVertex = Integer.parseInt(text.get(0));

        for (int i = 0; i < nVertex; i++) {

            String line = text.get(i);

            if (i == 0) {
                grafo = new AdjMatrix(nVertex);
            } else {

                int xi = Integer.parseInt(line.split(" ")[0]);
                int yi = Integer.parseInt(line.split(" ")[1]);

                int aux = i + 1;

                while (aux <= nVertex) {

                    String nextLine = text.get(aux);

                    int x2 = Integer.parseInt(nextLine.split(" ")[0]);
                    int y2 = Integer.parseInt(nextLine.split(" ")[1]);

                    double peso = addPeso(xi, yi, x2, y2);

                    grafo.setEdge(i - 1, aux - 1, peso);
                    grafo.setEdge(aux - 1, i - 1, peso);

                    aux++;
                }
            }
        }

        int vetorCaminho[] = new int[nVertex];
        int caminho[] = new int[nVertex];

        boolean[] av = new boolean[nVertex];
        for (int i = 0; i < nVertex; i++) {
            av[i] = true;
            vetorCaminho[i] = -1;
        }

        vetorCaminho[0] = 0;
        av[0] = false;

        backTracking(grafo, av, 0, vetorCaminho, 1, caminho);

        System.out.println(menor);
        for (int i = 0; i < nVertex; i++) {
            if (i == nVertex - 1) {
                System.out.print(caminho[i] + " - " + caminho[0]);
            } else {
                System.out.print(caminho[i] + " - ");
            }
        }
    }

    public static void backTracking(Graph grafo, boolean av[], int node, int vetorCaminho[], int prof, int[] caminho) {

        int menorCaminhoAux = 0;

        /* operacao de parada */
        if (prof >= grafo.getVertexSize()) {
            for (int i = 0, j = 0; i < prof; i++, j++) {
                if (j < prof - 1) {
                    menorCaminhoAux += grafo.getPeso(vetorCaminho[j], vetorCaminho[j + 1]);
                }

                /* operação menor caminho */
                if (j == prof - 1) {
                    menorCaminhoAux += grafo.getPeso(vetorCaminho[j], vetorCaminho[j - (prof - 1)]);
                    if (menorCaminhoAux <= menor) {
                        for (int k = 0; k < prof; k++) {
                            caminho[k] = vetorCaminho[k];
                        }
                        menor = menorCaminhoAux;
                    }
                }
            }
        } else {
            ArrayList<Integer> adj = grafo.getAdjVertex(node);
            for (int i : adj) {
                if (av[i] == true) {
                    av[i] = false;
                    vetorCaminho[prof] = i;
                    prof++;
                    backTracking(grafo, av, i, vetorCaminho, prof, caminho);
                    prof--;
                    av[i] = true;
                }
            }
        }
    }

    public static double addPeso(int x1, int y1, int x2, int y2) {
        double res;

        res = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));

        return res;
    }
}
