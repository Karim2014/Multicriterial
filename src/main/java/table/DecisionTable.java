package table;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DecisionTable {

    private Map<Criterion, List<Double>> criterion;

    private DecisionTable() {
        criterion = new LinkedHashMap<>();
    }

    public Map<Criterion, List<Double>> getCriterion() {
        return criterion;
    }

    /**
     * Выполняем нормализацию критериев
     */
    public void normalize() {
        criterion.forEach((criterion1, doubles) -> {
            double min = doubles.stream().min(Double::compareTo).get();
            double max = doubles.stream().max(Double::compareTo).get();

            List<Double> newDoubleList = doubles
                    .stream()
                    .map(aDouble -> criterion1.getAspiration().normalize(aDouble, max, min))
                    .collect(Collectors.toList());
            doubles.clear();
            doubles.addAll(newDoubleList);
        });
    }

    public void normalizeZero() {
        criterion.forEach((criterion1, doubles) -> {
            double min = doubles.stream().min(Double::compareTo).get();
            List<Double> newDoubles = doubles;
            if (min <= 0) {
                newDoubles = doubles.stream()
                        .map(aDouble -> aDouble + Math.abs(min) + 1)
                        .collect(Collectors.toList());
            }
            double newMin = newDoubles.stream().min(Double::compareTo).get();
            double max = newDoubles.stream().max(Double::compareTo).get();
            newDoubles = newDoubles.stream()
                    .map(aDouble -> criterion1.getAspiration().normalizeZero(aDouble, max, newMin))
                    .collect(Collectors.toList());
            doubles.clear();
            doubles.addAll(newDoubles);
        });
    }

    public List<List<Double>> toList() {
        return new ArrayList<>(criterion.values());
    }

    public List<List<Double>> transposeToList(List<List<Double>> lists) {
        List<List<Double>> ret = new ArrayList<>();
        int N = lists.get(0).size();

        for (int i = 0; i < N; i++) {
            List<Double> col = new ArrayList();
            for (List<Double> row : lists) {
                col.add(row.get(i));
            }
            ret.add(col);
        }

        return ret;
    }

    public List<List<Double>> transposeToList() {
        return transposeToList(toList());
    }

    public static DecisionTable fromFile(File file) throws IOException {

        DecisionTable decisionTable = new DecisionTable();
        int index = 0;
        try (Scanner rowScanner = new Scanner(file)) {
            while (rowScanner.hasNextLine()) {
                try (Scanner line = new Scanner(rowScanner.nextLine())) {
                    line.useDelimiter(";");
                    Criterion.ASPIRATION aspiration = Criterion.ASPIRATION.valueOf(line.next().toUpperCase());
                    Criterion key = new Criterion(index++, aspiration);
                    while (line.hasNext()) {
                        if (!decisionTable.criterion.containsKey(key)) {
                            decisionTable.criterion.put(key, new ArrayList<>());
                        }
                        decisionTable.criterion.get(key).add(Double.parseDouble(line.next()));
                    }
                }
            }
        }

        return decisionTable;
    }


}
