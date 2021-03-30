package methods;

import table.DecisionTable;

import java.util.List;
import java.util.stream.Collectors;

public class AdditiveConvolution {

    private List<Float> alphas;

    public AdditiveConvolution(List<Float> alphas) {
        float sum = alphas.stream().reduce(Float::sum).orElse(0f);
        if (sum != 1) {
            // TODO Сделать обработку исключения
        }
        this.alphas = alphas;
    }

    public List<Double> doConvolution(DecisionTable decisionTable) {
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
