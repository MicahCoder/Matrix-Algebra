public class Matrix {
    private final int length;
    private final int height;
    private double[][] matrix;
    private boolean augmented;
    //Constructs Matrix
    //@param length -  Amount of Vectors
    //@param height - Size of Vectors
    public Matrix(int length, int height){
        this.length = length;
        this.height = height;
        matrix = new double[height][length];
        augmented=(length-height==1);
    }
    //Constructs Matrix
    //@param matrix - double array equal to matrix
    public Matrix(double[][] matrix){
        length = matrix[0].length;
        height =  matrix.length;
        this.matrix = matrix;
        augmented=(length-height==1);
    }
    //Constructs Matrix
    //@param mathPrint -Takes in String input from MathLab to construct Matrix
     public Matrix(String mathPrint){
         String initial = mathPrint.substring(7);
         String initial2 = initial.substring(0, initial.length() - 2);
         String[] rows = initial2.split("};");
         String[][] initial3 = new String[rows.length][(int)Math.ceil(rows[0].length()/2)];
         for(int i = 0; i<rows.length;i++){
            if(i!=0){
                rows[i]=rows[i].substring(1);
            }
            initial3[i]=rows[i].split(";");
            
         }
         length=initial3[0].length;
         height =  initial3.length;
         matrix = new double[height][length];
         for(int i = 0; i<height;i++){
             for(int j = 0;j<length;j++){
                 matrix[i][j]=Double.valueOf(initial3[i][j]);
            }
         }
     }
    //Sets Matrix Value to double array
    //@param matrix - double array equal to matrix
    public void setMatrix(double[][] matrix){
        this.matrix = matrix;
    }
    //Sets value of matrix entry
    //@param i - x cord
    //@param j - y cord
    //@param value - value of entry
    public void set(int i,int j,double value){
        matrix[j][i]=value;
    }
    //Adds value to current component of matrix
    //@param i - x cord
    //@param j - y cord
    //@param value - value of addend
    public void addValue(int i,int j,double value){
        matrix[j][i]+=value;
    }
    //Gets value of current component of matrix
    //@param i - x cord
    //@param j - y cord
    public double get(int i, int j){
        return matrix[j][i];
    }
    //Gets length of matrix
    public int getLength(){
        return length;
    }
    //Gets height of matrix
    public int getHeight(){
        return height;
    }
    //Gets double[][] of matrix
    public double[][] getArray(){
        return matrix;
    }
    //Returns if the matrix is augmented
    public boolean isAugmented(){
        return augmented; 
    }
    //Returns row n of the matrix
    //@param row - n
    public double[] getRow(int row){
        return matrix[row];
    }
    //Returns collumn Vector n of the matrix in array form
    //@param row - n
    public double[] getCollumn(int collumn){
        double[] out = new double[height];
        for(int i =0;i<height;i++){
            out[i]=matrix[i][collumn];
        }
        return out;
    }
    //Returns collumn Vector n of the matrix in matrix form
    //@param row - n
    public Matrix getCollumnMatrix(int collumn){
        double[] values = getCollumn(collumn);
        Matrix out = new Matrix(1,values.length);
        for(int i = 0; i<values.length;i++){
            out.set(0,i,values[i]);
        }
        return out;
    }
    public void setCollumn(int number,Matrix collumn){
        for(int i = 0; i<collumn.getHeight();i++){
            set(number,i,collumn.get(0,i));
        }
    }
    //Multiplys row n by a scalar
    //@param row - n
    //@param scalar - the scalar
    public void multiplyRow(int row,double scalar){
        for(int i =0; i<length;i++){
            matrix[row-1][i]*= scalar;
        }
    }
    //Divides row n by a scalar
    //@param row - n
    //@param scalar - the scalar
    public void divideRow(int row,double scalar){
        for(int i =0; i<length;i++){
            matrix[row-1][i]= matrix[row-1][i]/scalar;
        }
    }
    //Divides row n by a scalar
    //@param row - n
    //@param scalar - the scalar
    public void truncateRow(int row,double scalar){
        for(int i =0; i<length;i++){
            matrix[row-1][i]= matrix[row-1][i]/scalar;
        }
    }
    //Adds rows a and b
    //@param intial - row a
    //@param modifier - row b
    public void addRow(int initial, int modifier){
        for(int i =0; i<length;i++){
            matrix[initial-1][i]+= matrix[modifier-1][i];
        }
    }
    //Adds rows a and b times n
    //@param intial - row a
    //@param modifier - row b
    //@param scalar - n
    public void addRowTimesScalar(int initial, int modifier, double scalar){
        for(int i =0; i<length;i++){
            matrix[initial-1][i]+= matrix[modifier-1][i]*scalar;
        }
    }
    //Adds rows a and b / n
    //@param intial - row a
    //@param modifier - row b
    //@param scalar - n
    public void addRowDividedByScalar(int initial, int modifier, double scalar){
        for(int i =0; i<length;i++){
            matrix[initial-1][i]+= matrix[modifier-1][i]/scalar;
        }
    }
    //Switches rows a and b
    //@param row1 - a
    //@param row2 - b
    public void switchRows(int row1, int row2){
        double[] storage = matrix[row1-1];
        matrix[row1-1] = matrix[row2-1];
        matrix[row2-1]=storage;
    }
    //Solves the matrix
    public Matrix solve(){
        for(int j = 0; j<height-1;j++){
            for(int i = j;i<height;i++){
                if(matrix[i][j]!=0){
                    switchRows(j+1,i+1);
                    break;
                }
            }
            if(matrix[j][j]==0){
                break;
            }
            if(matrix[j][j]!=0){
                divideRow(j+1,matrix[j][j]);
            }
            for(int i = j+1;i<height;i++){
                 addRowTimesScalar(i+1,j+1,-matrix[i][j]);
            }
        }
        if(matrix[height-1][height-1]!=0){
            divideRow(height, matrix[height-1][height-1]);
        }
        for(int j = height-1; j>=0;j--){
            for(int i = j-1;i>=0;i--){
                 addRowTimesScalar(i+1,j+1,-matrix[i][j]);
             }
        }
        return this;
    }
    //Finds the sub matrix by taking out collumn x and row y
    //@param x - collumn x
    //@param y - row y
    public Matrix subMatrix(int x, int y){
        double[][] out = new double[height-1][length-1];
        for(int i=0;i<x;i++){
            for(int j = 0;j<y;j++){
                out[j][i]=matrix[j][i];
            }
            for(int j=y;j<height-1;j++){
                out[j][i]=matrix[j+1][i];
            }
        }
        for(int i=x;i<length-1;i++){
            for(int j = 0;j<y;j++){
                out[j][i]=matrix[j][i+1];
            }
            for(int j=y;j<height-1;j++){
                out[j][i]=matrix[j+1][i+1];
            }
        }
        return new Matrix(out);
    }
    //Returns the determinant of the matrix
    public double det(){
        double out = 0;
        if(length==height){
            switch(length){
                case 1:
                    out=matrix[0][0];
                    break;
                case 2:
                    out = matrix[0][0]*matrix[1][1]-matrix[0][1]*matrix[1][0];
                    break;
                default: 
                    for(int j =0; j<length;j++){
                        out+= Math.pow(-1,j)*matrix[j][0]*subMatrix(0,j).det();
                    }
                    break;
            }
        }
        return out;
    
    }
    //Returns the transpose of the matrix
    public Matrix transpose(){
        Matrix out = new Matrix(height,length);
        for(int i=0;i<length;i++){
            for(int j=0;j<height;j++){
                out.set(j,i,matrix[j][i]);
            }
        }
        return out;
    }
    //Returns if a matrix is invertable
    public boolean isInvertable(){
        return det()!=0;
    }
    //Scales the matrix by n
    //@param factor - n
    public Matrix scale(double factor){
        for(int i=0;i<length;i++){
            for(int j=0;j<height;j++){
                matrix[j][i]*=factor;
            }
        }
        return this;
    }
    public Matrix scaleWithoutChange(double factor){
        Matrix out = new Matrix(length,height);
        for(int i=0;i<length;i++){
            for(int j=0;j<height;j++){
                out.set(i,j,matrix[j][i]*factor);
            }
        }
        return out;
    }
    //Scales the matrix by 1/n
    //@param factor - n
    public Matrix shrink(double factor){
        for(int i=0;i<length;i++){
            for(int j=0;j<height;j++){
                matrix[j][i]=matrix[j][i]/factor;
            }
        }
        return this;
    }
    //Returns the adjugate matrix
    public Matrix adj(){
        double[][] out = new double[height][length];
        for(int i = 0 ; i<length;i++){
            for(int j=0;j<height;j++){
                out[j][i] = Math.pow(-1,j+i%2)*subMatrix(j,i).det();
            }
        }
        return new Matrix(out);
    }
    //Returns the inverse matrix
    public Matrix inverse(){
        Matrix out = adj();
        out.shrink(det());
        return out;
    }
    //returns an estimate for eigen values
    public double eigenvalueEstimate(){
        MatrixOperator operator = new MatrixOperator();
        Matrix x = new Matrix(1,height);
        x.set(0,0,1);
        double max = 0;
        for(int i = 0; i<10;i++){
            max = 0;
            for(int j = 0; j<height;j++){
                if(max<Math.abs(x.get(0,j))){
                    max = x.get(0,j);
                }
            }
            x=operator.multiply(this,x).shrink(max);
        }
        return max;
    }
     //returns an estimate for eigen values
     //@param iterations
    public double eigenvalueEstimate(int iterations){
        MatrixOperator operator = new MatrixOperator();
        Matrix x = new Matrix(1,height);
        x.set(0,0,1);
        double max = 0;
        for(int i = 0; i<iterations+2;i++){
            max = 0;
            for(int j = 0; j<height;j++){
                if(max<Math.abs(x.get(0,j))){
                    max = x.get(0,j);
                }
            }
            x=operator.multiply(this,x).shrink(max);
        }
        return max;
    }
     //returns an estimate for eigen values
     //@param startVector - starting vector
     //@param iterations = iterations
    public double eigenvalueEstimate(Matrix startingVector, int iterations){
        MatrixOperator operator = new MatrixOperator();
        Matrix x = startingVector;
        double max = 0;
        for(int i = 0; i<iterations+2;i++){
            max = 0;
            for(int j = 0; j<height;j++){
                if(max<Math.abs(x.get(0,j))){
                    max = x.get(0,j);
                }
            }
            x=operator.multiply(this,x).shrink(max);
        }
        return max;
    }
    //returns an estimate for eigenvectors
     //@param iterations
    public Matrix eigenvectorEstimate(){
        MatrixOperator operator = new MatrixOperator();
        Matrix x = new Matrix(1,height);
        x.set(0,0,1);
        double max = 0;
        for(int i = 0; i<10;i++){
            max = 0;
            for(int j = 0; j<height;j++){
                if(max<Math.abs(x.get(0,j))){
                    max = x.get(0,j);
                }
            }
            x=operator.multiply(this,x).shrink(max);
        }
        return x.shrink(max);
    }
    public Matrix eigenvectorEstimate(int iterations){
        MatrixOperator operator = new MatrixOperator();
        Matrix x = new Matrix(1,height);
        x.set(0,0,1);
        double max = 0;
        for(int i = 0; i<iterations + 2;i++){
            max = 0;
            for(int j = 0; j<height;j++){
                if(max<Math.abs(x.get(0,j))){
                    max = x.get(0,j);
                }
            }
            x=operator.multiply(this,x).shrink(max);
        }
        return x.shrink(max);
    }  
    //returns a Rayleigh Iteration Estimate
    public double rayleighEstimate(Matrix startingVector, int iterations){
        MatrixOperator operator = new MatrixOperator();
        Matrix x = startingVector;
        for(int i = 0; i<iterations;i++){
            x=operator.multiply(this,x).shrink(operator.rayleighQuotient(this,x));
        }
        return operator.rayleighQuotient(this,x);
    }
    
     //returns an estimate for eigenvalues
     //@param startVector - starting vector
     //@param iterations = iterations
    public Matrix eigenvectorEstimate(Matrix startingVector, int iterations){
        MatrixOperator operator = new MatrixOperator();
        Matrix x = startingVector;
        double max = 0;
        for(int i = 0; i<iterations+2;i++){
            max = 0;
            for(int j = 0; j<height;j++){
                if(max<Math.abs(x.get(0,j))){
                    max = x.get(0,j);
                }
            }
            x=operator.multiply(this,x).shrink(max);
        }
        return x.shrink(max);
    }
    //returns an eigenvalue estimate using the inverse power method
    //@param guess- closest eigen value
    //@param starting vector - the starting vector
    //@param iterations - iterations
    public double IPMValueEstimate(double guess, Matrix startingVector, int iterations){
        MatrixOperator operator = new MatrixOperator();
        Matrix x = startingVector;
        double max = 0;
        double v = 0;
        for(int i = 0; i<iterations+2;i++){
            Matrix y = operator.multiply(operator.add(this,operator.identity(height).scale(-guess)).inverse(),x);
            max = 0;
            for(int j = 0; j<height;j++){
                if(max<Math.abs(y.get(0,j))){
                    max = y.get(0,j);
                }
            }
            v=guess+1.0/max;
            x=y.shrink(max);
        }
        return v;
    }
    //returns an eigenvector estimate using the inverse power method
    //@param guess- closest eigen value
    //@param starting vector - the starting vector
    //@param iterations - iterations
    public Matrix IPMVectorEstimate(double guess, Matrix startingVector, int iterations){
        MatrixOperator operator = new MatrixOperator();
        Matrix x = startingVector;
        double max = 0;
        for(int i = 0; i<iterations+2;i++){
            Matrix y = operator.multiply(operator.add(this,operator.identity(height).scale(-guess)).inverse(),x);
            max = 0;
            for(int j = 0; j<height;j++){
                if(max<Math.abs(y.get(0,j))){
                    max = y.get(0,j);
                }
            }
            x=y.shrink(max);
        }
        return x;
    }
    //Turns the matrix into its orthonormal form
    //returns the matrix
    // this uses the Gram-Schmidt Process
    public Matrix orthonormalBasis(){
        Matrix out = new Matrix(length,height);
        MatrixOperator operator = new MatrixOperator();
        if(length!=1){
            out.setCollumn(0,getCollumnMatrix(0));
            for(int i = 1; i<length;i++){
                Matrix proj = getCollumnMatrix(i);
                for(int j = i-1;j>=0;j--){
                    Matrix vj=out.getCollumnMatrix(j);
                    proj = operator.add(proj,vj.scaleWithoutChange(-operator.dotProduct(getCollumnMatrix(i),vj)/operator.dotProduct(vj,vj)));
                }
                out.setCollumn(i, proj);
            }
        }
        out.normalize();
        return out;
    }
    public void normalize(){
        MatrixOperator operator = new MatrixOperator();
        for(int i = 0; i < length; i++){
            Matrix vi = getCollumnMatrix(i);
            setCollumn(i, vi.shrink(Math.sqrt(operator.dotProduct(vi,vi))));
        }
    }
    //Reutrns the QR factorization of A
    public Matrix[] QR(){
        MatrixOperator operator = new MatrixOperator();
        Matrix orthonormal = orthonormalBasis();
        return new Matrix[] {orthonormal,operator.multiply(orthonormal.transpose(),this)};
    }
    //Returns a printable version of the matrix
    @Override
    public String toString(){
        String out = "";
        for(int j = 0; j<getHeight();j++){
            out+="| ";
            for(int i = 0; i<getLength();i++){
                out+=Math.floor(this.get(i,j) * 100) / 100 + " ";
            }
            out+="|\n";
        }
        return out;
    }
    //Returns a printable version of the matrix
    //@param places - rturns the place value entered in 10^n
    public String toString(int places){
        String out = "";
        for(int j = 0; j<getHeight();j++){
            out+="| ";
            for(int i = 0; i<getLength();i++){
                out+=Math.floor(this.get(i,j) * places) / places+" ";
            }
            out+="|\n";
        }
        return out;
    }
    //Returns the matrix in integers in math print
    public String toMathPrint(){
        String out = "@MATX{";
        for(int j = 0; j<getHeight();j++){
            out+="{";
            for(int i = 0; i<getLength();i++){
                out+=(int)this.get(i,j);
                out+= i<(length-1)?";":"";
            }
            out+= j<(height-1)?"};":"}";
        }
        return out+"}";
    }
    //Returns the matrix in doubles in math print with n decimal places
    //@param decimals - n
    public String toMathPrint(int decimals){
        String out = "@MATX{";
        double places= Math.pow(10,decimals);
        for(int j = 0; j<getHeight();j++){
            out+="{";
            for(int i = 0; i<getLength();i++){
                out+=Math.floor(this.get(i,j) * places) / places;
                out+= i<(length-1)?";":"";
            }
            out+= j<(height-1)?"};":"}";
        }
        return out+"}";
    }
}