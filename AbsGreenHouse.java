import java.util.*;

public abstract class AbsGreenHouse implements QualityControlable{

    protected GregorianCalendar clock;
    protected ParsedDataStrategy strategy = new Strategy1();

    public AbsGreenHouse(){
    }

    public AbsGreenHouse(GregorianCalendar aClock){
        this.clock = (GregorianCalendar) aClock.clone();
    }

  public boolean isDateTime(double sensorDatum){
      return sensorDatum > 19700101000000.0;
  }

    LinkedList<Double> data = new LinkedList<>();

    public void addData(List<Double> values){
        for(Double v : values){
            if(isDateTime(v)){
                if(clockAsDatetime() <= v ){
                    int p = values.indexOf(v);
                    int i = 1;
                    while(p + i < values.size() && !(isDateTime(values.get(p + i)))){
                        data.add(values.get(p + i));
                        i = i + 1;
                        setClockTo(v);
                    }
                }
            }
        }
    }

    @Override
    public double percentError() {
        double e = 0.0;
        double a = 0.0;
        for(Double d : data){
            if(d == -999.0){
                e = e +1.0;
                a = a +1.0;
            }
            else{
                a = a +1.0;
            }
        }

        return (e/a) * 100.0;
    }

    private double clockAsDatetime(){
        double year = clock.get(Calendar.YEAR);
        double month = clock.get(Calendar.MONTH) + 1;
        double day = clock.get(Calendar.DAY_OF_MONTH);
        double hour = clock.get(Calendar.HOUR_OF_DAY);
        double minute = clock.get(Calendar.MINUTE);
        double second = clock.get(Calendar.SECOND);
        return second +
                (minute * 100.0) +
                (hour * 100.0 * 100.0) +
                (day * 100.0 * 100.0 * 100.0) +
                (month * 100.0 * 100.0 * 100.0 * 100.0) +
                (year * 100.0 * 100.0 * 100.0 * 100.0 * 100.0);
    }

    public void setClockTo(double datetime) {
        String datetimeStr = String.format("%.0f", datetime);

        int year = Integer.parseInt(datetimeStr.substring(0, 4));
        int month = Integer.parseInt(datetimeStr.substring(4, 6)) - 1;
        int day = Integer.parseInt(datetimeStr.substring(6, 8));
        int hour = Integer.parseInt(datetimeStr.substring(8, 10));
        int minute = Integer.parseInt(datetimeStr.substring(10, 12));
        int second = Integer.parseInt(datetimeStr.substring(12, 14));
        this.clock = new GregorianCalendar(year, month, day, hour, minute, second);
    }

}
