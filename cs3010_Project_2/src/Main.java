import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int decimalPlaces;
    public static void main(String[] args) {
        int numOfEquations;
        double errorVal;
        int userChoice;
        double[] iterationValues;
        double[][] matrix;
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many linear equations would you like to see get solved with " +
                "the Jacobi and Gauss-Seidel methods? ");
        numOfEquations = Integer.parseInt(scanner.nextLine());

        System.out.println("\nWould you like to manually enter the matrix values for " +
                "the equations (option 1) or enter a matrix text file? (option 2) \n" +
                "Enter 1 for option 1 or 2 for option 2: ");
        userChoice = Integer.parseInt(scanner.nextLine());

        if (userChoice == 1) {
            matrix = manualMatrix(scanner, numOfEquations);
        }
        else {
            matrix = txtFileMatrix(scanner, numOfEquations);
        }

        System.out.println("\nYour matrix is: ");
        printMatrix(matrix);

        System.out.println("\nYour matrix is diagonally dominant: \n" + isDiagonallyDom(matrix) + "\n");
        if(!isDiagonallyDom(matrix)) System.out.println("Sorry, unable to solve this " +
                "matrix with Jacobi or Gauss-Seidel methods. Please try another matrix. ");
        else {
            System.out.println("Please input a desired stopping error: ");
            String errorString = scanner.nextLine();
            decimalPlaces = errorString.length() - 1;
            errorVal = Double.parseDouble(errorString);
            System.out.println();

            iterationValues = new double[matrix.length];
            char var = (char)(90 - matrix.length +1);
            System.out.println("Please enter the starting solution for the iterative methods: ");
            for (int i =0; i < matrix.length; i++) {
                System.out.print(var + " = ");
                iterationValues[i] = Double.parseDouble(scanner.nextLine());
                var++;
            }
            System.out.println();

            System.out.println("Would you like to use the Gauss-Seidel (option 1) or Jacobi method (option 2) to solve the matrix?\n" +
                    "Type 1 for Gauss-Seidel or 2 for Jacobi: ");
            userChoice = Integer.parseInt(scanner.nextLine());
            System.out.println();
            if (userChoice == 1) {
                System.out.println("Solving the matrix with the Gauss-Seidel method gives us: ");
                gaussSeidelMethod(matrix, iterationValues, errorVal);
            }
            else {
                System.out.println("Solving the matrix with the Jacobi method gives us: ");
                jacobiMethod(matrix, iterationValues, errorVal);
            }
        }
    }

    public static boolean isDiagonallyDom (double[][] matrix) {
        boolean answer = true;
        double diagonalEl = 0.0;
        double otherEl = 0.0;

        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length -1; j++) {
                if (i == j) diagonalEl = Math.abs(matrix[i][j]);
                else {
                    otherEl += Math.abs(matrix[i][j]);
                }
            }
            if (otherEl > diagonalEl) {
                answer = false;
                break;
            }
            diagonalEl = 0.0;
            otherEl = 0.0;
        }
        return answer;
    }

    public static double[] jacobiMethod(double[][] matrix, double[] firstItVals, double errorVal) {
        int maxIterations = 1; //up to 50
        double[] secondItVals = new double[firstItVals.length];
        double numerator = 0;
        double denomenator = 0;

        while(maxIterations != 50) {
            int skipper = 0;
            int counter = 0;
            for (int i = 0; i < matrix.length; i++) {
                numerator = matrix[i][matrix[0].length-1];
                denomenator = matrix[i][i];

                for (int j = 0; j < matrix[0].length -1; j++) {
                    if (counter == skipper) {
                        skipper++;
                        continue;
                    }
                    else skipper++;
                    numerator -= matrix[i][j] * firstItVals[j];
                }
                secondItVals[i] = numerator/denomenator;
                skipper = 0;
                counter++;
            }

            if (maxIterations == 1) {
                System.out.println("X column vector at iteration " + maxIterations + ": ");
                System.out.print("[");
                for(int i =0; i < firstItVals.length; i++) {
                    String format = "%." + decimalPlaces + "f";
                    System.out.printf(format, firstItVals[i]);
                    if (i < firstItVals.length-1) System.out.print(" ");
                }
                System.out.println("]T");
            }
            else {
                System.out.println("X column vector at iteration " + maxIterations + ": ");
                System.out.print("[");
                for(int i =0; i < firstItVals.length; i++) {
                    String format = "%." + decimalPlaces + "f";
                    System.out.printf(format, secondItVals[i]);
                    if (i < firstItVals.length-1) System.out.print(" ");
                }
                System.out.println("]T");
            }

            double l2NormVal = l2Norm(firstItVals, secondItVals);
            if (l2NormVal < errorVal) return secondItVals;
            maxIterations++;
            firstItVals = Arrays.copyOf(secondItVals, secondItVals.length);
        }
        System.out.println("\nSorry the desired error wasn't achieved in 50 iterations. Here is " +
                "the value at the 50th iteration: ");
        System.out.print("[");
        for(int i =0; i < firstItVals.length; i++) {
            String format = "%." + decimalPlaces + "f";
            System.out.printf(format, secondItVals[i]);
            if (i < firstItVals.length-1) System.out.print(" ");
        }
        System.out.println("]T");
        return secondItVals;
    }

    public static double[] gaussSeidelMethod(double[][] matrix, double[] startingItVals, double errorVal) {
        int maxIterations = 1; //up to 50
        double[] updatedItVals = new double[startingItVals.length];
        double numerator = 0;
        double denomenator = 0;

        while(maxIterations != 50) {
            int skipper = 0;
            int counter = 0;
            for (int i = 0; i < matrix.length; i++) {
                numerator = matrix[i][matrix[0].length-1];
                denomenator = matrix[i][i];

                for (int j = 0; j < matrix[0].length -1; j++) {
                    if (counter == skipper) {
                        skipper++;
                        continue;
                    }
                    else skipper++;
                    numerator -= matrix[i][j] * updatedItVals[j];
                }
                updatedItVals[i] = numerator/denomenator;
                skipper = 0;
                counter++;
            }

            if (maxIterations == 1) {
                System.out.println("X column vector at iteration " + maxIterations + ": ");
                System.out.print("[");
                for(int i =0; i < startingItVals.length; i++) {
                    String format = "%." + decimalPlaces + "f";
                    System.out.printf(format, startingItVals[i]);
                    if (i < startingItVals.length-1) System.out.print(" ");
                }
                System.out.println("]T");
            }
            else {
                System.out.println("X column vector at iteration " + maxIterations + ": ");
                System.out.print("[");
                for(int i =0; i < startingItVals.length; i++) {
                    String format = "%." + decimalPlaces + "f";
                    System.out.printf(format, updatedItVals[i]);
                    if (i < startingItVals.length-1) System.out.print(" ");
                }
                System.out.println("]T");
            }

            double l2NormVal = l2Norm(startingItVals, updatedItVals);
            if (l2NormVal < errorVal) return updatedItVals;
            maxIterations++;
            startingItVals = Arrays.copyOf(updatedItVals, updatedItVals.length);
        }
        System.out.println("\nSorry the desired error wasn't achieved in 50 iterations. Here is " +
                "the value at the 50th iteration: ");
        System.out.print("[");
        for(int i =0; i < startingItVals.length; i++) {
            String format = "%." + decimalPlaces + "f";
            System.out.printf(format, updatedItVals[i]);
            if (i < startingItVals.length-1) System.out.print(" ");
        }
        System.out.println("]T");

        return updatedItVals;
    }

    public static double l2Norm(double[] firstItVals, double[] secondItVals) {
        double numerator = 0;
        double denomenator = 0;

        for (int i = 0; i < firstItVals.length; i++) {
            numerator += Math.pow((secondItVals[i]-firstItVals[i]), 2);
            denomenator += Math.pow(secondItVals[i], 2);
        }
        return Math.sqrt(numerator) / Math.sqrt(denomenator);
    }

    public static double[][] manualMatrix(Scanner scanner, int numOfEquations) {
        double[][] matrix = new double[numOfEquations][numOfEquations + 1];
        for(int i = 0; i < numOfEquations; i++) {
            for(int j = 0; j < numOfEquations +1; j ++) {
                if (j < numOfEquations) {
                    System.out.println("Enter coefficient value for row " + i  +
                            " column " + j + ":");
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