import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.stream.IntStream;

public class Server implements IMath {

    public Server() {
    }

    @Override
    public int[][] multiplyMatrices(int[][] firstMatrix, int[][] secondMatrix) {
        if (firstMatrix.length == 0 || secondMatrix.length == 0)
            return new int[][]{};

        int[][] resultMatrix = new int[firstMatrix.length][secondMatrix[0].length];

        IntStream.range(0, firstMatrix.length).parallel().forEach(i -> {
            IntStream.range(0, secondMatrix[0].length).parallel().forEach(j -> {
                for (int k = 0; k < secondMatrix.length; k++) {
                    resultMatrix[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            });
        });

        return resultMatrix;
    }

    public static void main(String args[]) {

        try {
            Server obj = new Server();
            IMath stub = (IMath) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Math", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
