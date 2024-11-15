public class MatrixOperator {
    public MatrixOperator(){
        
    }
    //Returns the dot product between matrix a and b
    //@param row1 - a
    //@param row2 - b
    public double dotProduct(double[] row1, double[] row2){
        double out = 0;
        if(row1.length==row2.length){
            for(int i=0;i<row1.length;i++){
                out+=row1[i]*row2[i];
            }
        }
        return out;
    }
    public double dotProduct(Matrix row1, Matrix row2){
        return multiply(row1.transpose(),row2).get(0,0);
    }
    //Adds matrices A and B
    //@param A - A
    //@param B - B
    public Matrix add(Matrix A, Matrix B){
        Matrix out = new Matrix(A.getLength(),A.getHeight());
        if(A.getLength()==B.getLength() && A.getHeight()== B.getHeight()){
            for(int i=0; i < A.getLength();i++){
                for(int j=0; j < A.getHeight();j++){
                    out.set(i,j,A.get(i,j)+B.get(i,j));
                }
            }
        }else{
            System.out.println("Matrices don't add");
        }
        return out;
    }
    //Adds matrices A, B, and C
    //@param A - A
    //@param B - B
    //@param C - C
    public Matrix add(Matrix A, Matrix B, Matrix C){
        return add(A, add(B,C));
    }
    //Adds a list of Matrices
    //@param A - array of Matrices
    public Matrix add(Matrix[] A){
        Matrix out;
        if(A.length==2){
            out = add(A[0],A[1]);
        }else{
            Matrix[] B = new Matrix[A.length-1];
            for(int i = 1; i < A.length;i++){
                B[i-1]=A[i];
            }
            out = add(A[0],add(B));
        }
        return out;
    }
    //Multiplys matrices A and B
    //@param A - A
    //@param B - B
    public Matrix multiply(Matrix A, Matrix B){
        double[][] out = new double[A.getHeight()][B.getLength()];
        if(A.getLength()==B.getHeight()){
            for(int i =0;i<out.length;i++){
                for(int j=0;j<out[0].length;j++){
                    out[i][j] += dotProduct(A.getRow(i),B.getCollumn(j));
                }
            }
        }else{
            System.out.println("Matrices can't multiply");
        }
        Matrix outM = new Matrix(out);
        return outM;
    }
    //Reutrns matrix ABC
    //@param A
    //@param B
    //@param C
    public Matrix multiply(Matrix A, Matrix B, Matrix C){
        return multiply(A,multiply(B,C));
    }
    //Reutrns product of Matrix Array
    //@param A is the matrix array
    public Matrix multiply(Matrix[] A){
        Matrix out;
        if(A.length==2){
            out = multiply(A[0],A[1]);
        }else{
            Matrix[] B = new Matrix[A.length-1];
            for(int i = 1; i < A.length;i++){
                B[i-1]=A[i];
            }
            out = multiply(A[0],multiply(B));
        }
        return out;
    }
    //Returns matrix A to the nth power
    //@param n - power
    //@param A
    public Matrix pow(Matrix A, int n){
        Matrix out = identity(A.getLength());
        for(int i = 0;i<n;i++){
            out=multiply(A,out);
        }
        return out;
    }
    //returns matrix I_n
    //@param int n
    public Matrix identity(int n){
        Matrix out = new Matrix(n,n);
        for(int i = 0; i < n; i++){
            out.set(i,i,1);
        }
        return out;
    }
    //returns exp(A)
    //@param Matrix A
    public Matrix exp(Matrix A){
        Matrix out = identity(A.getLength());
        for(int i =1; i<10;i++){
            out=add(out,pow(A,i).shrink(factorial(i)));
        }
        return out;
    }
    //returns exp(A) after n iterations
    //@param Matrix A
    //@param n
    public Matrix exp(Matrix A, int n){
        Matrix out = identity(A.getLength());
        for(int i =1; i<n;i++){
            out=add(out,pow(A,i).shrink(factorial(i)));
        }
        return out;
    }
    //returns Matrix B that satisfies A=PBP^-1 or simplified, B = P^-1AP
    //@param Matrix A
    ///@param Matrix B
    public Matrix similar(Matrix A, Matrix P){
        return multiply(P.inverse(),A,P);
    }
    //returns Rayleigh Quotient
    public double rayleighQuotient(Matrix A, Matrix x){
        return multiply(multiply(x.transpose(),A),x).get(0,0)/multiply(x.transpose(),x).get(0,0);
    }
    //returns factorial(n)
    //@param n
    private double factorial(double n){
        if(n<2){
            return n;
        }else{
            return n*factorial(n-1);
        }
    }
    // Returns functions of the operator
    @Override
    public String toString(){
        return "add(A,B):add two Matrices A and B\nmultiply(A,B): returns AB\neigenValues(A): returns the Eigen Values of A";
    }
}