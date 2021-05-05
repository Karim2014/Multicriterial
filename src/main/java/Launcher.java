import methods.*;
import table.DecisionTable;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Launcher {
    public static void main(String[] args) {
        try {
            new Launcher().start();
        } catch (Exception e) {
            System.err.println("Произошла ошибка");
            //e.printStackTrace();
        }
    }

    DecisionTable decisionTable;
    Scanner sc;

    private void start() throws Exception {
        sc = new Scanner(System.in);
        System.out.println("Курсовая работа по дисциплине \"Теория принятия решений\"\nст. группы ИСзск-19 Сабитова К.А.");
        System.out.println("TODO: Описать задание");
        System.out.print("Выберите номер задачи:\n 1: Решение многокритериальных задач различными методами;" +
                "\n 2: Решение многокритериальной задачи интерактивным методом\n" +
                " -> ");
        byte task = 1;//sc.nextByte();
        while(task != 1 && task  != 2) {
            System.out.println("Повторите ввод");
            task = sc.nextByte();
        }

        if (task == 1) {
            System.out.println("Выбрано задание \"Решение многокритериальных задач различными методами\"");
            System.out.print("Введите имя файла с таблицей: ");
            //sc.nextLine();
            String fileName = "input.csv";//sc.nextLine();
            decisionTable = DecisionTable.fromFile(new File(fileName));
            System.out.println(fileName);
            System.out.println("\n======================= 1 =======================");
            System.out.println("Решение методом аддитивной свертки критериев");
            solveConvolution(AdditiveConvolution.class);
            System.out.println("\n======================= 2 =======================");
            System.out.println("Решение методом мультипликативной свертки критериев");
            solveConvolution(MultiplicativeConvolution.class);
            System.out.println("\n======================= 3 =======================");
            System.out.println("Решение максиминным методом");
            solveMaximin();


            System.out.println("\n======================= 6 =======================");
            System.out.println("Решение методом целевого программирования");
            solveTarget();
        }
    }

    private void solveConvolution(Class<? extends Convolution> aClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("Загруженная матрица:");
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        printMatrix(transposedMatrix);
        System.out.println("Необходимо ввести " + transposedMatrix.get(0).size() + " коэффициента важности критериев (Сумма = 1): ");
        /*float[] alphas = new float[transposedMatrix.get(0).size()];
        //TODO: Организовать проверку SUM(alphas) = 100
        //do {
            for (int i = 0; i < alphas.length; i++) {
                System.out.print((i + 1) + ": ");
                alphas[i] = sc.nextByte();
            }
       // } while (IntStream.of(alphas).sum() != 1);*/
        float[] alphas = new float[] { 0.3f, 0.1f, 0.2f, 0.4f };
        //Convolution additiveConvolution;//new MultiplicativeConvolution(alphas);
        Convolution additiveConvolution     = (Convolution) aClass.getDeclaredConstructors()[0].newInstance(alphas);
        List<Double> convolution            = additiveConvolution.solve(decisionTable);
        List<Double> alt                    = additiveConvolution.findAlt(transposedMatrix, convolution);

        footer(additiveConvolution::findMax, convolution, alt, "Максимальный");
    }

    private void solveMaximin() {
        System.out.println("Загруженная матрица:");
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        printMatrix(transposedMatrix);
        Maximin maximin         = new Maximin();
        List<Double> decision   = maximin.solve(decisionTable);
        List<Double> alt        = maximin.findAlt(transposedMatrix, decision);

        footer(maximin::findMax, decision, alt, "Максимальный");
    }

    private void solveTarget() {
        System.out.println("Загруженная матрица:");
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        printMatrix(transposedMatrix);
        TargetProgramming targetProgramming = new TargetProgramming();
        List<Double> decision               = targetProgramming.solve(decisionTable);
        List<Double> alt                    = targetProgramming.findAlt(transposedMatrix, decision);

        footer(targetProgramming::findMin, decision, alt, "Минимальный");
    }

    private void footer(Function<List<Double>, Double> opt, List<Double> decision, List<Double> alt, String which) {
        System.out.println("Решение задачи: ");
        decision.forEach(aDouble -> System.out.printf("%.3f\n", aDouble));
        System.out.printf(which + " элемент -> %.3f\n", opt.apply(decision));
        System.out.println("Оптимальная альтернатива: ");
        System.out.println(alt);
    }

    private void printMatrix(List<List<Double>> transposedMatrix) {
        decisionTable.getCriterion().keySet().forEach(criterion -> System.out.print(criterion.toString() + "\t"));
        System.out.println();
        transposedMatrix.forEach(doubles -> {
            doubles.forEach(aDouble -> System.out.print(aDouble + " \t"));
            System.out.println();
        });
    }

}
