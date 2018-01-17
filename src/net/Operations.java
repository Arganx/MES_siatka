package net;

import java.text.MessageFormat;

/**
 * Created by qwerty on 03-Jan-18.
 */
public class Operations {

    public double determinant (double[][] matrix) {
        double temporary[][];
        double result = 0;

        if (matrix.length == 1) {
            result = matrix[0][0];
            return (result);
        }

        if (matrix.length == 2) {
            result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
            return (result);
        }

        for (int i = 0; i < matrix[0].length; i++) {
            temporary = new double[matrix.length - 1][matrix[0].length - 1];

            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    if (k < i) {
                        temporary[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        temporary[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            result += matrix[0][i] * Math.pow(-1, (double) i) * determinant (temporary);
        }
        return (result);
    }

    public double[][] invertMatrix (double[][] matrix) {
        double[][] auxiliaryMatrix, invertedMatrix;
        int[] index;

        auxiliaryMatrix = new double[matrix.length][matrix.length];
        invertedMatrix = new double[matrix.length][matrix.length];
        index = new int[matrix.length];

        for (int i = 0; i < matrix.length; ++i) {
            auxiliaryMatrix[i][i] = 1;
        }

        transformToUpperTriangle(matrix, index);

        for (int i = 0; i < (matrix.length - 1); ++i) {
            for (int j = (i + 1); j < matrix.length; ++j) {
                for (int k = 0; k < matrix.length; ++k) {
                    auxiliaryMatrix[index[j]][k] -= matrix[index[j]][i] * auxiliaryMatrix[index[i]][k];
                }
            }
        }

        for (int i = 0; i < matrix.length; ++i) {
            invertedMatrix[matrix.length - 1][i] = (auxiliaryMatrix[index[matrix.length - 1]][i] / matrix[index[matrix.length - 1]][matrix.length - 1]);

            for (int j = (matrix.length - 2); j >= 0; --j) {
                invertedMatrix[j][i] = auxiliaryMatrix[index[j]][i];

                for (int k = (j + 1); k < matrix.length; ++k) {
                    invertedMatrix[j][i] -= (matrix[index[j]][k] * invertedMatrix[k][i]);
                }

                invertedMatrix[j][i] /= matrix[index[j]][j];
            }
        }

        return (invertedMatrix);
    }

    public void transformToUpperTriangle (double[][] matrix, int[] index) {
        double[] c;
        double c0, c1, pi0, pi1, pj;
        int itmp, k;

        c = new double[matrix.length];

        for (int i = 0; i < matrix.length; ++i) {
            index[i] = i;
        }

        for (int i = 0; i < matrix.length; ++i) {
            c1 = 0;

            for (int j = 0; j < matrix.length; ++j) {
                c0 = Math.abs (matrix[i][j]);

                if (c0 > c1) {
                    c1 = c0;
                }
            }

            c[i] = c1;
        }

        k = 0;

        for (int j = 0; j < (matrix.length - 1); ++j) {
            pi1 = 0;

            for (int i = j; i < matrix.length; ++i) {
                pi0 = Math.abs (matrix[index[i]][j]);
                pi0 /= c[index[i]];

                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;

            for (int i = (j + 1); i < matrix.length; ++i) {
                pj = matrix[index[i]][j] / matrix[index[j]][j];
                matrix[index[i]][j] = pj;

                for (int l = (j + 1); l < matrix.length; ++l) {
                    matrix[index[i]][l] -= pj * matrix[index[j]][l];
                }
            }
        }
    }

    public void printMatrix (int[][] matrix, int id) {
        double doubleMatrix[][] = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                doubleMatrix[i][j] = (double) matrix[i][j];
            }
        }

        printMatrix (doubleMatrix, id);
    }

    public void printMatrix (double[][] matrix, int id) {
        int cols, rows;

        rows = matrix.length;
        cols = matrix[0].length;

        switch (id) {
            case 1:
                System.out.print (MessageFormat.format ("First matrix[{0}][{1}]:", rows, cols));
                break;
            case 2:
                System.out.print (MessageFormat.format ("Second matrix[{0}][{1}]:", rows, cols));
                break;
            case 3:
                System.out.print (MessageFormat.format ("Result[{0}][{1}]:", rows, cols));
                break;
            case 4:
                System.out.print (MessageFormat.format ("Inverted matrix[{0}][{1}]:", rows, cols));
                break;
            default:
                System.out.print (MessageFormat.format ("Matrix[{0}][{1}]:", rows, cols));
                break;
        }

        System.out.println ();

        for (int i = 0; i < matrix.length; i++) {
            System.out.print ("[");

            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print (matrix[i][j]);
                if ((j + 1) != matrix[i].length) {
                    System.out.print (", ");
                }
            }

            if ((i + 1) != matrix.length) {
                System.out.println ("]");
            } else {
                System.out.println ("].");
            }
        }

        System.out.println ();
    }

    public double[][] multiplyMatrices (double[][] x, double[][] y) {
        double[][] result;
        int xColumns, xRows, yColumns, yRows;

        xRows = x.length;
        xColumns = x[0].length;
        yRows = y.length;
        yColumns = y[0].length;
        result = new double[xRows][yColumns];

        if (xColumns != yRows) {
            throw new IllegalArgumentException (
                    MessageFormat.format ("Matrices don't match: {0} != {1}.", xColumns, yRows));
        }


        for (int i = 0; i < xRows; i++) {
            for (int j = 0; j < yColumns; j++) {
                for (int k = 0; k < xColumns; k++) {
                    result[i][j] += (x[i][k] * y[k][j]);
                }
            }
        }

        return (result);
    }

    public double[] multiplyVector(double[][] a, double[] x) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += a[i][j] * x[j];
        return y;
    }

    public double[][] transpose(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        double[][] b = new double[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                b[j][i] = a[i][j];
        return b;
    }

    public double[][] multiplyVectorVectorTrans(double[] vector, double[] trans) {
        int m = vector.length;
        int n = trans.length;
        if (m != n) throw new RuntimeException("Illegal matrix dimensions.");

        double[][] result = new double[m][m];
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<m;j++)
            {
                result[i][j]=vector[i]*trans[j];
            }
        }
        return result;
    }

    public double[][] matrixAdd(double[][] A, double[][] B)
    {
        // Check if matrices have contents
        if ((A.length < 0) || (A[0].length < 0)) return B;
        if ((B.length < 0) || (B[0].length < 0)) return A;

        // create new matrix to store added values in
        double[][] C = new double[A.length][A[0].length];

        for (int i = 0; i < A.length; i++)
        {
            for (int j = 0; j < A[i].length; j++)
            {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    public double[][] multiplyMatrixScalar(double[][] matrix, double scalar)
    {
        double[][] result = new double[matrix.length][matrix.length];
        for(int i=0;i<matrix.length;i++)
        {
            for(int j=0;j<matrix.length;j++)
            {
                result[i][j]=matrix[i][j]*scalar;
            }
        }
        return result;
    }

    public double[][] divideMatrixScalar(double[][] matrix, double scalar) {
        double[][] result = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i][j] = matrix[i][j] / scalar;
            }
        }
        return result;
    }

    public double[] multiplyVectorScalar(double[] vector, double scalar)
    {
        double[] result = new double[vector.length];
        for(int i=0;i<vector.length;i++)
        {
            result[i]=vector[i]*scalar;
        }
        return result;
    }

    public void printVector(double[] vector)
    {
        for(int i=0;i<vector.length;i++)
        {
            System.out.println(vector[i]);
        }
    }

    public double[] addVectors(double[] vector1, double[] vector2)
    {
        double[] vector = new double[vector1.length];
        for(int i=0;i<vector.length;i++)
        {
            vector[i]=vector1[i]+vector2[i];
        }
        return vector;
    }

    public double[] findMinMaxInVector(double[] vector)
    {
        double min=vector[0];
        double max = vector[0];

        for(int i=0;i<vector.length;i++)
        {
            if(vector[i]<min)
            {
                min=vector[i];
            }
            if(vector[i]>max)
            {
                max=vector[i];
            }
        }

        double[] result = new double[2];
        result[0]=min;
        result[1]=max;
        return result;
    }
}
