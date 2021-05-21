package ahp;

import java.util.Scanner;

public class AnalyticHierarchyProcess {

    private final String TASK = "1. Школьный совет решает вопрос об открытии одной из следующих спортивных секций в " +
            "школе:\nшахматы, волейбол, спортивная гимнастика, фехтование или баскетбол. Критериями являются:\n" +
            "оплата тренера, количество желающих, стоимость инвентаря, затраты родителей. \n";

    private final String[] criterion = {
            "С1;Стоимость инвентаря, тыс. руб.",
            "С2;Оплата тренера, тыс. руб.",
            "C3;Количество желающих",
            "C4;Затраты родителей, тыс. руб" };

    private final String[] alternates = {
            "A;Шахматы",
            "B;Волейбол",
            "C;Фехтование",
            "D;Баскетбол",
            "E;Спортивная гимнастика"
    };

    private final float[][] table = {
            {55f, 35f, 12f, 0.7f},
            {78f, 5f, 34f, 2.5f},
            {145f, 22f, 56f, 35f},
            {112f, 6f, 37f, 2.5f},
            {69f, 5.5f, 23f, 3.5f}
    };

    // матрица попарных сравнений для критериев
    private float[][] t_CperC;
    // матрицы сравнений нижнего уровня ***
    // По критерию С1
    private float[][] t_AperC1;
    // По критерию С2
    private float[][] t_AperC2;
    // По критерию С3
    private float[][] t_AperC3;
    // По критерию С4
    private float[][] t_AperC4;
    // ***

    // векторы коэфициентов важности критериев
    private float[] WCC;
    private float[] WAC1;
    private float[] WAC2;
    private float[] WAC3;
    private float[] WAC4;



    private final String purpose = "Цель: открытие спортивной секции в школе";

    private Scanner scanner;

    public void start(Scanner sc) {
        this.scanner = sc;
        section("Условие задачи");
        condition();
        section("1-й этап. Структуризация");
        step1();
        section("2-й этап. Попарные сравнения");
        step2();
        section("3-й этап. Вычисление коеффициентов важности");
        step3();
        section("4-1 этап. Выбор оптимальной альтернативы");
        step4();
    }

    private void section(String s) {
        System.out.println("================ "+s+" ================");
    }

    private void condition() {
        System.out.println(TASK);
        printTable(criterion, alternates, table);
    }

    private void printTable(String[] header, String[] rowNames, float[][] table) {
        System.out.print("\t");
        // выводим заголовок критериев
        for (String s : header) {
            System.out.printf("%4s\t", s.substring(0, s.indexOf(";")));
        }
        System.out.println();
        for (int i = 0; i < rowNames.length; i++) {
            System.out.print(rowNames[i].substring(0, rowNames[i].indexOf(";")) + "\t");
            for (float v : table[i]) {
                System.out.printf("%4.1f\t", v);
            }
            System.out.println();
        }
    }

    private void step1() {
        System.out.println(purpose);
        System.out.println("Критерии:");
        for (String s : criterion) {
            System.out.println(" "+s.replaceFirst(";", " - "));
        }
        System.out.println("Альтернативы:");
        for (String s : alternates) {
            System.out.println(" "+s.replaceFirst(";", " - "));
        }
    }

    private void step2() {
        //TODO: Обеспечить возможость ввода пользователем своего мнения по поводу критерия

        // Сранения критериев
        t_CperC = new float[][]{
                {1, 3, 5, 3},
                {0.333333333f,	1f,	2f,	4f},
                {0.2f, 0.5f, 1f, 3f},
                {0.3333333f, 0.25f, 0.3333333f, 1f}
        };
        System.out.println("Матрица сравнений для критериев");
        printTable(criterion, criterion, t_CperC);

        t_AperC1 = new float[][] {
                {1,	3,	7,	5,	2},
                {0.33f,	1f,	5f, 4, 0.5f},
                {1/7f,	0.2f, 1,	0.5f, 1/6f},
                {0.2f, 0.25f, 2f, 1f, 0.333333333f},
                {0.5f, 2f, 6f, 3f, 1f}
        };
        System.out.printf("По критерию С1 (%s)\n", criterion[0].substring(3));
        printTable(alternates, alternates, t_AperC1);

        t_AperC2 = new float[][] {
                {1f, 0.111111111f, 0.2f, 0.125f, 0.125f},
                {9f, 1f, 4f, 2f, 1f},
                {5f, 0.25f, 1f, 0.166666667f, 0.142857143f},
                {8f,	0.5f, 6f, 1f, 0.5f},
                {8f, 1f, 7f, 2f, 1f}

        };
        System.out.printf("По критерию С2 (%s)\n", criterion[1].substring(3));
        printTable(alternates, alternates, t_AperC2);

        t_AperC3 = new float[][] {
                {1f, 0.2f, 0.142857143f, 0.166666667f, 0.333333333f},
                {5f, 1f, 0.25f, 0.5f, 3},
                {7f, 4f, 1f, 4f, 6},
                {6f, 2f, 0.25f, 1f, 4},
                {3f, 0.333333333f, 0.166666667f, 0.25f, 1}

        };
        System.out.printf("По критерию С3 (%s)\n", criterion[2].substring(3));
        printTable(alternates, alternates, t_AperC3);

        t_AperC4 = new float[][] {
                {1f, 3f, 9f, 3f, 4},
                {0.333333333f, 1f, 7f, 1f, 2},
                {0.111111111f, 0.142857143f, 1f, 0.142857143f, 0.166666667f},
                {0.333333333f, 1f, 7f, 1f, 2},
                {0.25f, 0.5f, 6f, 0.5f, 1}

        };
        System.out.printf("По критерию С4 (%s)\n", criterion[3].substring(3));
        printTable(alternates, alternates, t_AperC4);
    }

    private void step3() {
        // нормируем С per C
        normalize(criterion.length, criterion.length, t_CperC);
        WCC = calcW(criterion.length, criterion.length, t_CperC);
        System.out.println("Нормированная матрица сравнения критериев с коэффициентов важности");
        printTableW(criterion, criterion, t_CperC, WCC);

        // нормируем A per C1
        normalize(alternates.length, alternates.length, t_AperC1);
        WAC1 = calcW(criterion.length, alternates.length, t_AperC1);
        System.out.println("Нормированная матрица сравнения альтернативы А по первому критерию");
        printTableW(alternates, alternates, t_AperC1, WAC1);

        // нормируем A per C2
        normalize(alternates.length, alternates.length, t_AperC2);
        WAC2 = calcW(criterion.length, alternates.length, t_AperC2);
        System.out.println("Нормированная матрица сравнения альтернативы А по второму критерию");
        printTableW(alternates, alternates, t_AperC2, WAC2);

        // нормируем A per C3
        normalize(alternates.length, alternates.length, t_AperC3);
        WAC3 = calcW(criterion.length, alternates.length, t_AperC3);
        System.out.println("Нормированная матрица сравнения альтернативы А по третьему критерию");
        printTableW(alternates, alternates, t_AperC3, WAC3);

        // нормируем A per C4
        normalize(alternates.length, alternates.length, t_AperC4);
        WAC4 = calcW(criterion.length, alternates.length, t_AperC4);
        System.out.println("Нормированная матрица сравнения альтернативы А по четвертому критерию");
        printTableW(alternates, alternates, t_AperC4, WAC4);
    }

    private void step4() {
        System.out.println("Коэффициенты важности для альтернатив:");
        double[] S = new double[alternates.length];
        for (int i = 0; i < alternates.length; i++) {
            S[i] = WCC[0]*WAC1[i]+WCC[1]*WAC2[i]+WCC[2]*WAC3[i]+WCC[3]*WAC4[i];
            System.out.printf(" S%s=%.4f;\n", alternates[i].substring(0, 1).toLowerCase(), S[i]);
        }
        double max = S[0];
        int maxIndex = 0;
        for (int i = 0; i < S.length; i++) {
            if (max < S[i]) {
                max = S[i];
                maxIndex = i;
            }
        }
        System.out.printf("Оптимальная альтернатива: S%s=%.4f;\n", alternates[maxIndex].substring(0, 1).toLowerCase(), S[maxIndex]);
        System.out.println(alternates[maxIndex].substring(2));
    }

    private void normalize(int colSize, int rowSize, float[][] table) {
        // Нормирование матрицы
        for (int j = 0; j < colSize; j++) {
            float sum = 0;
            for (int i = 0; i < rowSize; i++) {
                sum += table[i][j];
            }
            for (int i = 0; i < rowSize; i++) {
                table[i][j] = table[i][j] / sum;
            }
        }
    }

    private void printTableW(String[] header, String[] rowNames, float[][] table, float[] w) {
        System.out.print("\t");
        // выводим заголовок критериев
        for (String s : header) {
            System.out.printf("%4s\t", s.substring(0, s.indexOf(";")));
        }
        System.out.printf("%4s\n", "W");
        for (int i = 0; i < rowNames.length; i++) {
            System.out.print(rowNames[i].substring(0, rowNames[i].indexOf(";")) + "\t");
            for (float v : table[i]) {
                System.out.printf("%4.2f\t", v);
            }
            System.out.printf("%4.3f\t", w[i]);
            System.out.println();
        }
    }

    private float[] calcW(int colSize, int rowSize, float[][] table) {
        float[] res = new float[rowSize];
        for (int i = 0; i < rowSize; i++) {
            float avg = 0;
            for (int j = 0; j < colSize; j++) {
                avg += table[i][j];
            }
            res[i] = avg / colSize;
        }
        return res;
    }

}
