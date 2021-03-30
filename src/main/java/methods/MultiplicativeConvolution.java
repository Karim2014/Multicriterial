package methods;

import table.DecisionTable;

import java.util.List;
import java.util.stream.Collectors;

public class MultiplicativeConvolution extends Convolution {

    public MultiplicativeConvolution(List<Float> alphas) {
        super(alphas);
    }

    @Override
    public List<Double> doConvolution(DecisionTable decisionTable) {
        // нормализуем значения
        decisionTable.normalizeZero();
        // транспонируем матрицу решений и возвращааем в виде двумерных списков
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();
        // суммируем элементы и возвращае
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

    public double findMax(List<Double> convolution) {
        return convolution.stream().max(Double::compareTo).get();
    }

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

}
