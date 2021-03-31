package methods;

import table.DecisionTable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Maximin extends BaseMethod {

    @Override
    public List<Double> solve(DecisionTable decisionTable) {
        decisionTable.normalizeZero();
        List<List<Double>> transposedMatrix = decisionTable.transposeToList();

        List<List<Double>> newMatrix = transposedMatrix
                .stream()
                .map(doubles -> {
                    double max = doubles.stream().max(Double::compareTo).get();
                    return doubles.stream()
                            .map(aDouble -> aDouble / max)
                            .collect(Collectors.toList());
                })
                .collect(Collectors.toList());

        List<Integer> indexList = newMatrix.stream()
                .map(doubles -> {
                    int index = 0;
                    double min = doubles.get(index);
                    for (int i = 1; i < doubles.size(); i++) {
                        if (doubles.get(i) < min) {
                            min = doubles.get(i);
                            index = i;
                        }
                    }
                    return index;
                })
                .collect(Collectors.toList());
        return zip(transposedMatrix, indexList);
    }

    private List<Double> zip(List<List<Double>> matrix, List<Integer> indexList) {
        List<Double> ret = new ArrayList<>();
        for (int i = 0; i < indexList.size(); i++) {
            ret.add(matrix.get(i).get(indexList.get(i)));
        }
        return ret;
    }

}
