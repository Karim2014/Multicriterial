import ahp.AnalyticHierarchyProcess;
import methods.*;
import table.DecisionTable;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Launcher {

    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        try {
            new Launcher().start();
        } catch (Exception e) {
            System.err.println("Произошла ошибка " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private DecisionTable decisionTable;
    private Scanner sc;

    private void start() throws Exception {
        sc = new Scanner(System.in);
        System.out.println("Курсовая работа по дисциплине \"Теория принятия решений\"\nст. группы ИСзск-19 Сабитова К.А.");
        System.out.println("TODO: Описать задание");
        System.out.print("Выберите номер задачи:\n 1: Решение многокритериальных задач различными методами;" +
                "\n 2: Решение многокритериальной задачи интерактивным методом\n" +
                " -> ");
        byte task = sc.nextByte();
        while(task != 1 && task  != 2) {
            System.out.println("Повторите ввод");
            task = sc.nextByte();
        }

        if (task == 1) {
            System.out.println("Выбрано задание \"Решение многокритериальных задач различными методами\"");
            System.out.print("Введите имя файла с таблицей: ");
            sc.nextLine();
            String fileName = DEBUG ? "input.csv" : sc.nextLine();
            decisionTable = DecisionTable.fromFile(new File(fileName));
            //System.out.println(fileName);
            System.out.println("\n======================= 1 =======================");
            System.out.println("Решение методом аддитивной свертки критериев");
            solveConvolution(AdditiveConvolution.class);
            System.out.println("\n======================= 2 =======================");
            System.out.println("Решение методом мультипликативной свертки критериев");
            solveConvolution(MultiplicativeConvolution.class);
            System.out.println("\n======================= 3 =======================");
            System.out.println("Решение максиминным методом");
            solve(Maximin.class, BaseMethod::findMax, "Максимальный");
            System.out.println("\n======================= 4 =======================");
            System.out.println("Решение задачи методом главного критерия");
            solveMainCriterion();

            System.out.println("\n======================= 6 =======================");
            System.out.println("Решение методом целевого программирования");
            solve(TargetProgramming.class, BaseMethod::findMin, "Минимальный");
        } else {
            System.out.println("Выбрано задание \"Решение многокритериальной задачи интерактивным методом\"");
            System.out.println("Метод анализа иерархий");
            new AnalyticHierarchyProcess().start(sc);
        }
    }

    private void solveConvolution(Class<? extends Convolution> aClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("Загруженная матрица:");
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        printMatrix(transposedMatrix);
        System.out.println("Необходимо ввести " + transposedMatrix.get(0).size() + " коэффициента важности критериев в процентах (Сумма = 100%): ");
        float[] alphas = new float[transposedMatrix.get(0).size()];
        //TODO: Организовать проверку SUM(alphas) = 100
        //do {
        if (!DEBUG) {
            for (int i = 0; i < alphas.length; i++) {
                System.out.print((i + 1) + ": ");
                alphas[i] = (float) sc.nextInt() / 100;
            }
        } else {
            alphas = new float[] { 0.3f, 0.1f, 0.2f, 0.4f };
        }
        //} while (Stream.of(alphas).mapToInt);
        Convolution convolution = (Convolution) aClass.getDeclaredConstructors()[0].newInstance(decisionTable, alphas);
        List<Double> decision   = convolution.solve();
        List<Double> alt        = convolution.findAlt(transposedMatrix, decision);

        footer(BaseMethod::findMax, decision, alt, "Максимальный");
    }

    private void solve(Class<? extends BaseMethod> aClass, Function<List<Double>, Double> opt, String which) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("Загруженная матрица:");
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        printMatrix(transposedMatrix);
        BaseMethod method       = (BaseMethod) aClass.getDeclaredConstructors()[0].newInstance(decisionTable);
        List<Double> decision   = method.solve();
        List<Double> alt        = method.findAlt(transposedMatrix, decision);

        footer(opt, decision, alt, which);
    }

    private void solveMainCriterion() {
        System.out.println("Загруженная матрица:");
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        printMatrix(transposedMatrix);

        System.out.println("Введите номер главного критерия\n -> ");
        int mainCriterionIndex = DEBUG ? 3 : sc.nextByte();
        MainCriterion method = new MainCriterion(decisionTable, mainCriterionIndex);

        List<Double> opt = method.findOpt();
        System.out.println("Оптимальные значения");
        opt.forEach(aDouble -> System.out.print(aDouble + " \t"));
        System.out.println("\nЗадайте ограничения для каждого критерия:");
        if (!DEBUG) {
            double[] limits = new double[opt.size()];
            for (int i = 0; i < limits.length; i++) {
                if (i != mainCriterionIndex) {
                    System.out.printf("C%d: ", i);
                    limits[i] = sc.nextByte();
                } else {
                    limits[i] = 0;
                }
            }
            method.setLimits(limits);
        } else {
            method.setLimits(new double[] { 0d, 8d, 3d, 0d });
        }
        List<Double> alt = method.solve();
        System.out.println("Оптимальная альтернатива: ");
        System.out.println(alt);
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
