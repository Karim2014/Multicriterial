package methods;

import table.DecisionTable;

import java.util.List;
import java.util.stream.Collectors;

public class AdditiveConvolution extends Convolution {

    /**
     * Конструктор. Выполняет инициализацию коэффициентов важности
     * Сумма коэффициентов должна быть 1 (100%)
     *
     * @param table
     * @param alphas
     */
    public AdditiveConvolution(DecisionTable table, float[] alphas) {
        super(table, alphas);
    }

    @Override
    public List<Double> solve() {
        // нормализуем значения
        // транспонируем матрицу решений и возвращааем в виде двумерных списков
        List<List<Double>> transposedMatrix = decisionTable
                .normalize()
                .transposeToList();
        // суммируем элементы и возвращае
        return transposedMatrix
                .stream()
                .map(doubles -> {
                    double sum = 0;
                    for (int i = 0; i < doubles.size(); i++) {
                        sum += alphas[i] * doubles.get(i);
                    }
                    return sum;
                })
                .collect(Collectors.toList());
    }

}
