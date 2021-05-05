package methods;

import java.util.List;

public abstract class Convolution extends BaseMethod {

    protected List<Float> alphas;

    /**
     * Конструктор. Выполняет инициализацию коэффициентов важности
     * Сумма коэффициентов должна быть 1 (100%)
     * @param alphas
     */
    public Convolution(List<Float> alphas) {
        float sum = alphas.stream().reduce(Float::sum).orElse(0f);
        if (sum != 1) {
            // TODO Сделать обработку исключения
        }
        this.alphas = alphas;
    }



}
