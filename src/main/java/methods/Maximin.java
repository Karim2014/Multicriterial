package methods;

import table.DecisionTable;

import java.util.List;
import java.util.stream.Collectors;

public class Maximin extends BaseMethod {

    public Maximin(DecisionTable decisionTable) {
        super(decisionTable);
    }

    @Override
    public List<Double> solve() {
        // возвращаем минимальные элементы в строках таблицы
        return decisionTable.normalizeZero().transposeToList()
                .stream()
                .map(doubles -> doubles.stream().min(Double::compareTo).get())
                .collect(Collectors.toList());
    }

}
