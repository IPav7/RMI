import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMath extends Remote {

    int[][] multiplyMatrices(int[][] firstMatrix, int[][] secondMatrix) throws RemoteException;

}
