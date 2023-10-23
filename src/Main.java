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
                "the Jacobi and Gauss-Seidel methods?");
        numOfEquations = Integer.parseInt(scanner.nextLine());

        System.out.println("Would you like to manually enter the matrix values for " +
                "the equations (option 1) or enter a matrix text file? (option 2) \n " +
                "Enter 1 for option 1 or 2 for option 2. \n" +
                "REMINDER: matrix must be diagonally dominant in order to be solved with " +
                "the Jacobi and Gauss-Seidel methods");
        userChoice = Integer.parseInt(scanner.nextLine());

        if (userChoice == 1) {
            matrix = manualMatrix(scanner, numOfEquations);
        }
    }

    public static boolean isDiagnoallyDom (double[][] matrix) {
        boolean answer = false;
        return answer;
    }

    public static double[] jocabiMethod(double[][] matrix) {
        double[] answer;
        return answer;
    }

    public static double[] gaussSeidelMethod(double[][] matrix) {
        double[] answer;
        return answer;
    }

    public static double l2Norm(double[][] matrix) {
        double answer;
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
                            "column " + j); //**** LEFT OFF HERE******
                    matrix[i][j] = Double.parseDouble(scanner.nextLine());
                }
                else {
                    System.out.println("Enter b value for row " + i);
                    matrix[i][j] = Double.parseDouble(scanner.nextLine());
                }
            }
        }
        return matrix;
    }

    public static double[][] txtFileMatrix() {
        double[][] matrix;
        return matrix;
    }
}