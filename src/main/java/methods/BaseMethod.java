package methods;

import table.DecisionTable;

import java.util.List;

public abstract class BaseMethod {

    public abstract List<Double> solve(DecisionTable decisionTable);

    /**
     * Выполняет поиск альтернативы, которая является оптимальной, исходя из решения задачи
     *
     * @param transposedMatrix транспонированная матрица - таблица критериев
     * @param convolution список новых альтернатив из решения
     * @return список альтернатив, являющихся оптимальными
     * */
    public List<Double> findAlt(List<List<Double>> transposedMatrix, List<Double> convolution) {
        int index = 0;
        double max = convolution.get(index);
        for (int i = 0; i < convolution.size(); i++) {
            if (convolution.get(i) > max) {
                max = convolution.get(i);
                index = i;
            }
        }
        return transposedMatrix.get(index);
    }

    public Double findMin(List<Double> list) {
        return list.stream().min(Double::compareTo).get();
    }

    public double findMax(List<Double> convolution) {
        return convolution.stream().max(Double::compareTo).get();
    }
}
