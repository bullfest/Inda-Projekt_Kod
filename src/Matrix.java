/**
 * A matrix with support for scalar multiplication and matrix addition and multiplication.
 */
public class Matrix {
    private double[][] data;

    /**
     * Creates a (m x n) matrix
     * @param m The number of rows in the matrix
     * @param n The numer of columns in the matrix
     */
    public Matrix(int m, int n) {
        if (m <=0 || n<=0)
            throw new IllegalArgumentException("Matrix dimensions has to be bigger than 0");
        data = new double[m][n];
    }

    /**
     * Creates a matrix from a 2 dimensional array
     */
    public Matrix(double[][] data)
    {
        this.data = data.clone();
    }

    public int getRows() {
        return data.length;
    }

    public int getCols() {
        return data[0].length;
    }

    public void setElem(int m, int n, double value) {
        if (m <0 || n<0 || m > getRows()-1 || n>getCols()-1)
            throw new IllegalArgumentException("m and n has to be within the matrix.");
        data[m][n] = value;
    }

    public double getElem(int m, int n) {
        if (m <0 || n<0 || m > getRows()-1 || n>getCols()-1)
            throw new IllegalArgumentException("m and n has to be within the matrix.");
        return data[m][n];
    }

    /**
     * Computes the matrix A that's a result of A = B+k*M
     * where B is this(invoked on) matrix and M is the provided matrix.
     *
     * (Hint) if k =-1 then A = B-M
     *
     * @param M The matrix to add to this
     * @param k The coefficient for this matrix
     * @return this + k*M
     */
    public Matrix add(Matrix M, double k) {
        if (M.getCols() != this.getCols() || M.getRows() != this.getRows())
            throw new IllegalArgumentException("Matrices has to have the same dimensions.");
        Matrix res = new Matrix(getCols(),getRows());
        for (int i = 0; i < data.length; i++) {
            double[] row = data[i];
            for (int j = 0; j < row.length; j++) {
                res.setElem(i,j,data[i][j] + k*M.data[i][j]);
            }
        }
        return res;
    }

    /**
     * Computes the multiplication A = B*M where B is this matrix and M is the provided one.
     * @param M The right-hand matrix in the B*M multiplication
     * @return B*M
     */
    public Matrix multiply(Matrix M) {
        if (this.getCols() != M.getRows())
            throw new IllegalArgumentException("Matrix dimension mismatch. The number of cols in \"this\" Matrix " +
                    "has to be the same as the number of rows in M");

        Matrix res = new Matrix(this.getRows(),M.getCols());
        for (int i = 0; i <res.getRows(); i++) {
            for (int j = 0; j < res.getCols(); j++) {
                double sum = 0;
                for (int k = 0; k < getCols(); k++) {
                    sum += data[i][k]*data[k][j];
                }
            }
        }
        return res;
    }

    public Matrix multiply(double k) {
        Matrix res = new Matrix(data);
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                res.setElem(i,j,k*res.getElem(i,j));
            }
        }
        return res;
    }

    protected double[][] getData() {
        return data;
    }

    protected void setData(double[][] data) {
        this.data = data;
    }

    public Matrix transpose() {
        Matrix res = new Matrix(getCols(),getRows());
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                res.setElem(j,i,getElem(i,j));
            }
        }
        return res;
    }
}
