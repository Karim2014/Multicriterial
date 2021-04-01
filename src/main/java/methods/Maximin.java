package methods;

import table.DecisionTable;

import java.util.List;
import java.util.stream.Collectors;

public class Maximin extends BaseMethod {

    @Override
    public List<Double> solve(DecisionTable decisionTable) {
        decisionTable.normalizeZero();

        return decisionTable.transposeToList()
                .stream()
                .map(doubles -> doubles.stream().min(Double::compareTo).get())
                .collect(Collectors.toList());
    }

}
