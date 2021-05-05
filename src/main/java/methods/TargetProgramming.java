package methods;

import table.DecisionTable;

import java.util.List;
import java.util.stream.Collectors;

public class TargetProgramming extends BaseMethod {

    private final int p = 2;
    private final int w = 1;

    @Override
    public List<Double> solve(DecisionTable decisionTable) {
        DecisionTable normalized = decisionTable.normalizeZero();

        List<Double> maximumList = normalized.toList()
                .stream()
                .map(doubles -> doubles.stream().max(Double::compareTo).get())
                .collect(Collectors.toList());

        return normalized.transposeToList().stream()
                .map(doubles -> {
                    double z = 0;
                    for (int i = 0; i < doubles.size(); i++) {
                        z += Math.pow(Math.abs(doubles.get(i) - maximumList.get(i)), 2);
                    }
                    return Math.pow(z, (float) 1/p);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Double> findAlt(List<List<Double>> transposedMatrix, List<Double> convolution) {
        int index = 0;
        double min = convolution.get(index);
        for (int i = 0; i < convolution.size(); i++) {
            if (convolution.get(i) < min) {
                min = convolution.get(i);
                index = i;
            }
        }
        return transposedMatrix.get(index);
    }
}
