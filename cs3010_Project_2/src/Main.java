import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numOfEquations = 0;
        double errorVal = 0.0;
        int userChoice;
        double[] iterationValues;
        int iterationCount = 0;
        double[][] matrix;
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many linear equations would you like to see get solved with " +
                "the Jacobi and Gauss-Seidel methods? ");
        numOfEquations = Integer.parseInt(scanner.nextLine());

        System.out.println("Would you like to manually enter the matrix values for " +
                "the equations (option 1) or enter a matrix text file? (option 2) \n " +
                "Enter 1 for option 1 or 2 for option 2. \n" +
                "REMINDER: Matrix must be diagonally dominant in order to be solved with " +
                "the Jacobi and Gauss-Seidel methods");
        userChoice = Integer.parseInt(scanner.nextLine());

        if (userChoice == 1) {
            matrix = manualMatrix(scanner, numOfEquations);
        }
        else {
            matrix = txtFileMatrix(scanner, numOfEquations);
        }
        System.out.println("Your matrix is: ");
        printMatrix(matrix);

        System.out.println("\nYour matrix is diagonally dominant: " + isDiagonallyDom(matrix));
        if(!isDiagonallyDom(matrix)) System.out.println("Sorry, unable to solve this " +
                "matrix with Jacobi or Gauss-Seidel methods. Please try another matrix. ");
    }

    public static boolean isDiagonallyDom (double[][] matrix) {
        boolean answer = true;
        double diagonalEl = 0.0;
        double otherEl = 0.0;

        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i == j) diagonalEl = matrix[i][j];
                else {
                    otherEl += matrix[i][j];
                }
            }
            if (otherEl < diagonalEl) {
                answer = false;
                break;
            }
        }
        return answer;
    }

    public static double[] jocabiMethod(double[][] matrix) {
        double[] answer = {};
        return answer;
    }

    public static double[] gaussSeidelMethod(double[][] matrix) {
        double[] answer = {};
        return answer;
    }

    public static double l2Norm(double[][] matrix) {
        double answer = 0;
        return answer;
    }

    public static void printXColVector(double[] vector) {
        for (double value: vector) {
            System.out.println(value + " ");
        }
    }

    public static double[][] manualMatrix(Scanner scanner, int numOfEquations) {
        double[][] matrix = new double[numOfEquations][numOfEquations + 1];
        for(int i = 0; i < numOfEquations; i++) {
            for(int j = 0; j < numOfEquations +1; j ++) {
                if (j < numOfEquations) {
                    System.out.println("Enter coefficient value for row " + i  +
                            " column " + j + ":"); //**** LEFT OFF HERE******
                    matrix[i][j] = Double.parseDouble(scanner.nextLine());
                }
                else {
                    System.out.println("Enter b value for row " + i + ":");
                    matrix[i][j] = Double.parseDouble(scanner.nextLine());
                }
            }
        }
        return matrix;
    }

    public static double[][] txtFileMatrix(Scanner scanner, int numOfEquations) {
        double[][] matrix = new double[numOfEquations][numOfEquations + 1];
        System.out.println("Enter your matrix text file path here: ");
        String filePath = scanner.nextLine();
        filePath = filePath.substring(1, filePath.length() - 1);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String [] temp = line.split(" ");

                for (int i = 0; i < temp.length; i++) {
                    matrix[row][i] = Double.parseDouble(temp[i]);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matrix;
    }

    public static void printMatrix(double[][] matrix) {
        for(int i = 0; i < matrix.length ; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}