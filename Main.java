public class Main
{
    public static void main(String[] args)
    {

        MatrixOperator operator = new MatrixOperator();
        Matrix A = new Matrix(new double[][]{
            {1,1,1,1},
            {1,2,4,8},
            {1,3,9,27},
            {1,4,16,64},
            {1,5,25,125}
        });
        
        Matrix B = new Matrix(new double[][]{
            {1},
            {3},
            {4},
            {6},
            {15}
        });
        // Matrix[] QR = A.QR();
        // System.out.println("Q=\n"+ QR[0] +"\nR=\n" + QR[1]);
        // System.out.println(A);
        System.out.println(A.proj(B).toString(10000));
        System.out.println(A.residual(B).toString(10000));
    }
}