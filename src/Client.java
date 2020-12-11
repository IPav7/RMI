import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Scanner;

public class Client {

    private Client() {
    }

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        IMath stub = null;
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            stub = (IMath) registry.lookup("Math");
        } catch (RemoteException | NotBoundException exception) {
            System.out.println("Client exception: " + exception.getLocalizedMessage());
        }

        // Multiplying Two matrices
        if (stub != null) {
            Scanner myInput = new Scanner(System.in);
            System.out.println("Enter the first matrix rows number: ");
            int firstRows = myInput.nextInt();
            System.out.println("Enter the first matrix columns number: ");
            int firstColumns = myInput.nextInt();
            System.out.println("Enter the second matrix columns number: ");
            int secondColumns = myInput.nextInt();
            int[][] firstMatrix = generateMatrix(firstRows, firstColumns);
            System.out.println("The first matrix is: ");
            displayMatrix(firstMatrix);
            int[][] secondMatrix = generateMatrix(firstColumns, secondColumns);
            System.out.println("The second matrix is: ");
            displayMatrix(secondMatrix);
            try {
                int[][] product = stub.multiplyMatrices(firstMatrix, secondMatrix);
                // Displaying the result
                System.out.println("The result matrix is: ");
                displayMatrix(product);
            } catch (RemoteException exception) {
                System.out.println("Error while multiplying matrices: " + exception.getLocalizedMessage());
            }
        } else {
            System.out.println("Error while connecting to the server");
        }
    }

    public static void displayMatrix(int[][] product) {
        for (int[] row : product) {
            for (int column : row) {
                System.out.print(column + "    ");
            }
            System.out.println();
        }
    }

    public static int[][] generateMatrix(int rows, int columns){
        if (rows < 1 || columns < 1)
            throw new IllegalArgumentException("The matrix size should be more than 0x0");
        else {
            int[][] matrix = new int[rows][columns];
            Random random = new Random();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = random.nextInt(100);
                }
            }
            return matrix;
        }
    }

}
