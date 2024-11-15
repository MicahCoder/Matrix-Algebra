public class Main
{
    public static void main(String[] args)
    {

        MatrixOperator operator = new MatrixOperator();
        Matrix A = new Matrix(new double[][]{
            {1,0,0},
            {1,1,0},
            {1,1,1},
            {1,1,1}
        });
        
        Matrix B = new Matrix(new double[][]{
            {1},
            {0}
        });
        Matrix[] QR = A.QR();
        System.out.println("Q=\n"+ QR[0] +"\nR=\n" + QR[1]);
        System.out.println(A);
    }
}