import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numOfEquations = 0;
        //double errorVal = 0.0; don't delete
        int userChoice;
        /*//double[] iterationValues; //don't delete
        //double[][] matrix; // don't delete
        double[] iterationValues = {0,0,0}; // DEBUG CODE
        double[][] matrix = {{5, -1, 2, 12},{3, 8, -2, -25},{1, 1, 4, 6}}; // debug code*/

        //*****BIG DECIMAL TEST CODE *********
        BigDecimal errorVal = new BigDecimal("0.0");
        BigDecimal[] iterationValues = new BigDecimal[3];
        iterationValues[0] = new BigDecimal("0.0");
        iterationValues[1] = new BigDecimal("0.0");
        iterationValues[2] = new BigDecimal("0.0");
        BigDecimal[][] matrix = new BigDecimal[3][4];
        //{{5 , -1, 2, 12},{3, 8, -2, -25},{1, 1, 4, 6}}; // debug code
        matrix[0][0] = new BigDecimal("5");
        matrix[0][1] = new BigDecimal("-1");
        matrix[0][2] = new BigDecimal("2");
        matrix[0][3] = new BigDecimal("12");
        matrix[1][0] = new BigDecimal("3");
        matrix[1][1] = new BigDecimal("8");
        matrix[1][2] = new BigDecimal("-2");
        matrix[1][3] = new BigDecimal("-25");
        matrix[2][0] = new BigDecimal("1");
        matrix[2][1] = new BigDecimal("1");
        matrix[2][2] = new BigDecimal("4");
        matrix[2][3] = new BigDecimal("6");

        Scanner scanner = new Scanner(System.in);

        System.out.println("How many linear equations would you like to see get solved with " +
                "the Jacobi and Gauss-Seidel methods? ");
        //numOfEquations = Integer.parseInt(scanner.nextLine()); //don't delete
        numOfEquations = 3; //DEBUG CODE

        System.out.println("Would you like to manually enter the matrix values for " +
                "the equations (option 1) or enter a matrix text file? (option 2) \n " +
                "Enter 1 for option 1 or 2 for option 2: ");
        //userChoice = Integer.parseInt(scanner.nextLine()); //don't delete
        userChoice = 2; //DEBUG CODE

        /*if (userChoice == 1) { //don't delete
            matrix = manualMatrix(scanner, numOfEquations);
        }
        else {
            matrix = txtFileMatrix(scanner, numOfEquations);
        }*/


        System.out.println("Your matrix is: ");
        printMatrix(matrix);

        System.out.println("\nYour matrix is diagonally dominant: \n" + isDiagonallyDom(matrix) + "\n");
        if(!isDiagonallyDom(matrix)) System.out.println("Sorry, unable to solve this " +
                "matrix with Jacobi or Gauss-Seidel methods. Please try another matrix. ");
        else {
            System.out.println("Please input a desired stopping error: ");
            //errorVal = Double.parseDouble(scanner.nextLine()); //don't delete
            errorVal = new BigDecimal(".000000000000000001"); //test code
            System.out.println();

           /* iterationValues = new double[matrix.length]; // don't delete
            char var = (char)(90 - matrix.length +1);
            System.out.println("Please enter the starting solution for the iterative methods: ");
            for (int i =0; i < matrix.length; i++) {
                System.out.print(var + " = ");
                iterationValues[i] = Double.parseDouble(scanner.nextLine());
                var++;
            }*/
            System.out.println();

            System.out.println("Would you like to use the Gauss-Seidel (option 1) or Jacobi method (option 2) to solve the matrix?\n" +
                    "Type 1 for option 1 or 2 for option 2: \n");
            userChoice = Integer.parseInt(scanner.nextLine());
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

    public static boolean isDiagonallyDom (BigDecimal[][] matrix) { //test code
    //public static boolean isDiagonallyDom (double[][] matrix) { //don't delete
        boolean answer = true;
        /*double diagonalEl = 0.0; // don't delete
        double otherEl = 0.0;*/
        BigDecimal diagonalEl = new BigDecimal("0.0"); //test code
        BigDecimal otherEl = new BigDecimal("0.0"); // test code
        BigDecimal zero = new BigDecimal("0.0"); //test code

        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length -1; j++) {
                if (i == j) diagonalEl = matrix[i][j].abs();
                else {
                    //otherEl += Math.abs(matrix[i][j]); don't delete
                    otherEl = otherEl.add(matrix[i][j]); //test code
                }
            }
            if (otherEl.compareTo(diagonalEl) > 0) {
                answer = false;
                break;
            }
            diagonalEl = zero; //0.0;
            otherEl = zero; //0.0;
        }
        return answer;
    }

    // ********* JACOBI WORKS BUT GIVES YOU AN OFF ROUNDING VALUE *****************
    public static BigDecimal[] jacobiMethod(BigDecimal[][] matrix, BigDecimal[] firstItVals, BigDecimal errorVal) { //test code
    //public static double[] jacobiMethod(double[][] matrix, double[] firstItVals, double errorVal) { // don't delete
        int maxIterations = 1; //up to 51
        /*double[] secondItVals = new double[firstItVals.length]; //don't delete
        double numerator = 0;
        double denomenator = 0;*/
        BigDecimal[] secondItVals = new BigDecimal[firstItVals.length]; // test code
        BigDecimal numerator = BigDecimal.ZERO;
        BigDecimal denomenator = BigDecimal.ZERO;


        while(maxIterations != 51) {
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
                    //numerator -= matrix[i][j] * firstItVals[j]; don't delete
                    numerator = numerator.subtract(matrix[i][j].multiply(firstItVals[j])); //test code
                }
                //secondItVals[i] = numerator/denomenator; //don't delete
                secondItVals[i] = numerator.divide(denomenator);
                skipper = 0;
                counter++;
            }

            if (maxIterations == 1) {
                System.out.println("X column vector at iteration " + maxIterations + ": ");
                System.out.print("[");
                for(int i =0; i < firstItVals.length; i++) {
                    System.out.printf("%.4f", firstItVals[i]);
                    if (i < firstItVals.length-1) System.out.print(" ");
                }
                System.out.println("]T");
            }
            System.out.println("X column vector at iteration " + maxIterations + ": ");
            System.out.print("[");
            for(int i =0; i < firstItVals.length; i++) {
                System.out.printf("%.4f", secondItVals[i]);
                if (i < firstItVals.length-1) System.out.print(" ");
            }
            System.out.println("]T");

            //double l2NormVal = l2Norm(firstItVals, secondItVals); // don't delete
            BigDecimal l2NormVal = l2Norm(firstItVals, secondItVals); //test code
            //if (l2NormVal < errorVal) return secondItVals; //don't delete
            if (l2NormVal.compareTo(errorVal) < 0) return secondItVals;
            maxIterations++;
            firstItVals = Arrays.copyOf(secondItVals, secondItVals.length);
        }
        System.out.println("Sorry the desired error wasn't achieved in 50 iterations. Here is " +
                "the value at the 50th iteration: ");
        return secondItVals;
    }

    // ******************************* WORKS BUT GIVES OFF ROUNDING VALUES *****************************************************************
  /*  public static double[] gaussSeidelMethod(double[][] matrix, double[] startingItVals, double errorVal) {
        int maxIterations = 1; //up to 51
        double[] updatedItVals = new double[startingItVals.length]; //this code might be wrong
        double[] updatedItVals = Arrays.copyOf(startingVals, startingVals.length); //this might be the correct code, havent tested it
        double numerator = 0;
        double denomenator = 0;


        while(maxIterations != 51) {
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
                    System.out.printf("%.4f", startingItVals[i]);
                    if (i < startingItVals.length-1) System.out.print(" ");
                }
                System.out.println("]T");
            }
            System.out.println("X column vector at iteration " + maxIterations + ": ");
            System.out.print("[");
            for(int i =0; i < startingItVals.length; i++) {
                System.out.printf("%.4f", updatedItVals[i]);
                if (i < startingItVals.length-1) System.out.print(" ");
            }
            System.out.println("]T");

            double l2NormVal = l2Norm(startingItVals, updatedItVals);
            if (l2NormVal < errorVal) return updatedItVals;
            maxIterations++;
            startingItVals = Arrays.copyOf(updatedItVals, updatedItVals.length);
        }
        System.out.println("Sorry the desired error wasn't achieved in 50 iterations. Here is " +
                "the value at the 50th iteration: ");
        return updatedItVals;
    }*/ // don't delete
    //whole method unerneith is test code
    public static BigDecimal[] gaussSeidelMethod(BigDecimal[][] matrix, BigDecimal[] firstItVals, BigDecimal errorVal) { //test code
        int maxIterations = 1; //up to 51
        BigDecimal[] secondItVals = new BigDecimal[firstItVals.length]; // test code
        for (int i = 0; i < firstItVals.length; i++) {
            secondItVals[i] = firstItVals[i];
        }
        BigDecimal numerator = BigDecimal.ZERO;
        BigDecimal denomenator = BigDecimal.ZERO;


        while(maxIterations != 51) {
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
                    numerator = numerator.subtract(matrix[i][j].multiply(secondItVals[j])); //test code
                    //numerator -= matrix[i][j] * updatedItVals[j];
                }
                //secondItVals[i] = numerator/denomenator; //don't delete
                secondItVals[i] = numerator.divide(denomenator);
                skipper = 0;
                counter++;
            }

            if (maxIterations == 1) {
                System.out.println("X column vector at iteration " + maxIterations + ": ");
                System.out.print("[");
                for(int i =0; i < firstItVals.length; i++) {
                    System.out.printf("%.4f", firstItVals[i]);
                    if (i < firstItVals.length-1) System.out.print(" ");
                }
                System.out.println("]T");
            }
            System.out.println("X column vector at iteration " + maxIterations + ": ");
            System.out.print("[");
            for(int i =0; i < firstItVals.length; i++) {
                System.out.printf("%.4f", secondItVals[i]);
                if (i < firstItVals.length-1) System.out.print(" ");
            }
            System.out.println("]T");

            //double l2NormVal = l2Norm(firstItVals, secondItVals); // don't delete
            BigDecimal l2NormVal = l2Norm(firstItVals, secondItVals); //test code
            //if (l2NormVal < errorVal) return secondItVals; //don't delete
            if (l2NormVal.compareTo(errorVal) < 0) return secondItVals;
            maxIterations++;
            firstItVals = Arrays.copyOf(secondItVals, secondItVals.length);
        }
        System.out.println("Sorry the desired error wasn't achieved in 50 iterations. Here is " +
                "the value at the 50th iteration: ");
        return secondItVals;
    }

    public static BigDecimal l2Norm(BigDecimal[] firstItVals, BigDecimal[] secondItVals) { //test code
    //public static double l2Norm(double[] firstItVals, double[] secondItVals) { //don't delete
        /*double numerator = 0; // don't delete
        double denomenator = 0;*/
        BigDecimal numerator = new BigDecimal("0.0"); // test code
        BigDecimal denomenator = new BigDecimal("0.0"); //test code

        for (int i = 0; i < firstItVals.length; i++) {
            //numerator += Math.pow((secondItVals[i]-firstItVals[i]), 2); //don't delete
            //denomenator += Math.pow(secondItVals[i], 2); //don't delete
            numerator = numerator.add(secondItVals[i].subtract(firstItVals[i]).multiply(secondItVals[i].subtract(firstItVals[i])));
            denomenator = denomenator.add(secondItVals[i].multiply(secondItVals[i]));
        }
        //return Math.sqrt(numerator) / Math.sqrt(denomenator); //don't delete
        MathContext mc = new MathContext(20);
        BigDecimal sqrtNumerator = numerator.sqrt(mc);
        BigDecimal sqrtDenominator = denomenator.sqrt(mc);
        BigDecimal value = sqrtNumerator.divide(sqrtDenominator, mc);
        return value;
    }


    public static BigDecimal[][] manualMatrix(Scanner scanner, int numOfEquations) { //test code
    //public static double[][] manualMatrix(Scanner scanner, int numOfEquations) { //don't delete
        //double[][] matrix = new double[numOfEquations][numOfEquations + 1]; don't delete
        BigDecimal[][] matrix = new BigDecimal[numOfEquations][numOfEquations + 1]; // test code
        for(int i = 0; i < numOfEquations; i++) {
            for(int j = 0; j < numOfEquations +1; j ++) {
                if (j < numOfEquations) {
                    System.out.println("Enter coefficient value for row " + i  +
                            " column " + j + ":");
                    //matrix[i][j] = Double.parseDouble(scanner.nextLine()); //don't delete
                    matrix[i][j] = new BigDecimal (scanner.nextLine()); //test code
                }
                else {
                    System.out.println("Enter b value for row " + i + ":");
                    //matrix[i][j] = Double.parseDouble(scanner.nextLine()); don't delete
                    matrix[i][j] = new BigDecimal (scanner.nextLine());
                }
            }
        }
        return matrix;
    }
    public static BigDecimal[][] txtFileMatrix(Scanner scanner, int numOfEquations) {//test code
    //public static double[][] txtFileMatrix(Scanner scanner, int numOfEquations) { // don't delete
        //double[][] matrix = new double[numOfEquations][numOfEquations + 1]; don't delete
        BigDecimal[][] matrix = new BigDecimal[numOfEquations][numOfEquations + 1]; //test code
        System.out.println("Enter your matrix text file path here: ");
        String filePath = scanner.nextLine();
        filePath = filePath.substring(1, filePath.length() - 1);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String [] temp = line.split(" ");

                for (int i = 0; i < temp.length; i++) {
                    //matrix[row][i] = Double.parseDouble(temp[i]); //don't delete
                    matrix[row][i] = new BigDecimal(temp[i]); //test code
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matrix;
    }

    public static void printMatrix(BigDecimal[][] matrix) {
    //public static void printMatrix(double[][] matrix) { // don't delete
        for(int i = 0; i < matrix.length ; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}