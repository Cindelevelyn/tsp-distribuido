package caixeiroviajante;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

  private static double menor = Integer.MAX_VALUE;

  public static void main(String[] args) throws IOException {
    try {
      ServerSocket server = new ServerSocket(8082);
      System.out.println("Servidor ouvindo a porta 3030");

      while (true) {
        Socket cliente = server.accept();
        System.out.println("Coordenador conectado!");

        ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
        ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

        Graph grafo = (AdjMatrix) entrada.readObject();
        boolean av[] = (boolean[]) entrada.readObject();
        int node = (int) entrada.readObject();
        int vetorCaminho[] = (int[]) entrada.readObject();
        int prof = (int) entrada.readObject();
        int caminho[] = new int[grafo.getVertexSize()];

        backTracking(grafo, av, node, vetorCaminho, prof, caminho);

        saida.writeObject(menor);
        saida.writeObject(caminho);

        server.close();
        cliente.close();
      }
    } catch (Exception e) {
      System.out.println("Erro: " + e.getMessage());
    }
  }

  public static void backTracking(Graph grafo, boolean av[], int node, int vetorCaminho[], int prof, int[] caminho) {

    double menorCaminhoAux = 0;

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

}
