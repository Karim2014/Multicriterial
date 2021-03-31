package methods;

import table.DecisionTable;

import java.util.List;
import java.util.stream.Collectors;

public class MultiplicativeConvolution extends Convolution {

    public MultiplicativeConvolution(List<Float> alphas) {
        super(alphas);
    }

    @Override
    public List<Double> solve(DecisionTable decisionTable) {
        // нормализуем значения
        decisionTable.normalizeZero();
        // транспонируем матрицу решений и возвращааем в виде двумерных списков
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        // умножаем элементы и возвращае
        return transposedMatrix
                .stream()
                .map(doubles -> {
                    double p = 1;
                    for (int i = 0; i < doubles.size(); i++) {
                        p *= Math.pow(doubles.get(i), alphas.get(i));
                    }
                    return p;
                })
                .collect(Collectors.toList());
    }

}
