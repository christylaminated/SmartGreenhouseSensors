import java.util.List;

public class Date {
    public double date;
    public List<Double> temp;
    public List<Double> humid;

    public Date(double date, List<Double> temp, List<Double> humid) {
        this.date = date;
        this.temp = temp;
        this.humid = humid;
    }
}
