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

    public DecisionTable(Map<Criterion, List<Double>> criterion) {
        this.criterion = criterion;
    }

    public Map<Criterion, List<Double>> getCriterion() {
        return criterion;
    }

    /**
     * Выполняет нормализацию критериев
     * @return
     */
    public DecisionTable normalize() {
        // для каждой строки нетранспонированной матрицы
        Map<Criterion, List<Double>> criterionListMap = new LinkedHashMap<>();
        criterion.forEach((criterion1, doubles) -> {
            // находим минимальный и максимальный элемент в векторе
            double min = doubles.stream().min(Double::compareTo).get();
            double max = doubles.stream().max(Double::compareTo).get();

            // преобазуем текущий вектор в нормализованный
            // для этого вызываем функцию нормализации
            List<Double> newDoubleList = doubles
                    .stream()
                    .map(aDouble -> criterion1.getAspiration().normalize(aDouble, max, min))
                    .collect(Collectors.toList());
            criterionListMap.put(criterion1, newDoubleList);
        });
        return new DecisionTable(criterionListMap);
    }

    /**
     * Выполняет нормализацию критериев с учетом отрицательных элементов в матрице
     * @return
     */
    public DecisionTable normalizeZero() {
        Map<Criterion, List<Double>> criterionListMap = new LinkedHashMap<>();
        // для каждой строки нетранспонированной матрицы
        criterion.forEach((criterion1, doubles) -> {
            // находим минимальный элемент
            double min = doubles.stream().min(Double::compareTo).get();
            List<Double> newDoubles = doubles;
            // если есть минимальный
            if (min <= 0) {
                //  добавляем ко всем элементам abs(min)+1
                newDoubles = doubles.stream()
                        .map(aDouble -> aDouble + Math.abs(min) + 1)
                        .collect(Collectors.toList());
            }
            // выполняем нормализацю по 3 и 4 формуле
            double newMin = newDoubles.stream().min(Double::compareTo).get();
            double max = newDoubles.stream().max(Double::compareTo).get();
            newDoubles = newDoubles.stream()
                    .map(aDouble -> criterion1.getAspiration().normalizeZero(aDouble, max, newMin))
                    .collect(Collectors.toList());
            criterionListMap.put(criterion1, newDoubles);
        });
        return new DecisionTable(criterionListMap);
    }

    public List<List<Double>> toList() {
        return new ArrayList<>(criterion.values());
    }

    /**
     * Транспонирует матрицу решений
     * @param lists матрица решений
     * @return транспонированная матрица
     */
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

    /**
     * Транспонирует матрицу решений
     * @return транспонированная матрица
     */
    public List<List<Double>> transposeToList() {
        return transposeToList(toList());
    }

    /**
     * Загружеаем матрицу решений из файла
     * @param file файл с критериями (*.csv)
     * @return объект таблицы
     * @throws IOException
     */
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
