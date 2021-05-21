package methods;

import table.Criterion;
import table.DecisionTable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainCriterion {

    private double[] limits;
    private int mainCriterionIndex;
    private DecisionTable decisionTable;

    public MainCriterion(DecisionTable decisionTable, int mainCriterion) {
        this.decisionTable = decisionTable;
        this.mainCriterionIndex = mainCriterion;
    }

    public List<Double> findOpt() {
        List<Double> ret = new ArrayList<>(decisionTable.getCriterion().size());
        decisionTable
                .getCriterion()
                .forEach((criterion, doubles) -> {
                    if (criterion.getAspiration() == Criterion.ASPIRATION.MAX)
                        ret.add(BaseMethod.findMax(doubles));
                    else
                        ret.add(BaseMethod.findMin(doubles));
                });
        return ret;
    }

    public List<Double> findOptReverse() {
        List<Double> ret = new ArrayList<>(decisionTable.getCriterion().size());
        decisionTable
                .getCriterion()
                .forEach((criterion, doubles) -> {
                    if (criterion.getAspiration() == Criterion.ASPIRATION.MAX)
                        ret.add(BaseMethod.findMin(doubles));
                    else
                        ret.add(BaseMethod.findMax(doubles));
                });
        return ret;
    }

    public void setLimits(double[] limits) {
        this.limits = limits;
        // TODO: Жуть как неэффективно. ИСПРАВЬ ЭТО
        this.limits[mainCriterionIndex] = findOptReverse().get(mainCriterionIndex);
    }

    public List<Double> solve() {
        List<Criterion.ASPIRATION> aspirations = decisionTable
                .getCriterion()
                .keySet()
                .stream()
                .map(Criterion::getAspiration)
                .collect(Collectors.toList());

        return decisionTable.transposeToList()
                .stream()
                .filter(doubles -> {
                    for (int i = 0; i < doubles.size(); i++) {
                        if (!aspirations.get(i).compare(doubles.get(i), limits[i]))
                            return false;
                    }
                    return true;
                })
                .max(Comparator.comparing(doubles -> doubles.get(mainCriterionIndex)))
                .orElse(null);

    }

}
