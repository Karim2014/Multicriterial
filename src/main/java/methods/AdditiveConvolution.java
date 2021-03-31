package methods;

import table.DecisionTable;

import java.util.List;
import java.util.stream.Collectors;

public class AdditiveConvolution extends Convolution {

    public AdditiveConvolution(List<Float> alphas) {
        super(alphas);
    }

    @Override
    public List<Double> solve(DecisionTable decisionTable) {
        // нормализуем значения
        decisionTable.normalize();
        // транспонируем матрицу решений и возвращааем в виде двумерных списков
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        // суммируем элементы и возвращае
        return transposedMatrix
                .stream()
                .map(doubles -> {
                    double sum = 0;
                    for (int i = 0; i < doubles.size(); i++) {
                        sum += alphas.get(i) * doubles.get(i);
                    }
                    return sum;
                })
                .collect(Collectors.toList());
    }

}
