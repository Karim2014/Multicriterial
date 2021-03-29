package table;

public class Criterion implements Comparable<Criterion> {

    public enum ASPIRATION {
        MAX {
            @Override
            public double normalize(double X, double max, double min) {
                return (X - min) / (max - min);
            }
        },
        MIN {
            @Override
            public double normalize(double X, double max, double min) {
                return (max - X) / (max - min);
            }
        };

        public abstract double normalize(double X, double max, double min);
    }

    private int name;
    private ASPIRATION aspiration;

    public Criterion(int name, ASPIRATION aspiration) {
        this.name = name;
        this.aspiration = aspiration;
    }

    public ASPIRATION getAspiration() {
        return aspiration;
    }

    public int getName() {
        return name;
    }

    @Override
    public String toString() {
        return "C" + name + "->" + aspiration;
    }

    @Override
    public int compareTo(Criterion o) {
        return Integer.compare(name, o.getName());
    }
}
