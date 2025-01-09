import java.util.*;

public class Strategy2 implements ParsedDataStrategy{

    public boolean isDate(double sensorDatum){
        return sensorDatum > 19700101.0;
    }

    public boolean isDateTime(double sensorDatum){
        return sensorDatum > 19700101000000.0;
    }

    public double toDate(double dateTime){
        return Math.floor(dateTime / 1000000.0);
    }

    public boolean sameDate(double date1, double date2){
        return Math.abs(date1 - date2) < 0.001;
    }

    ArrayList<Double> data = new ArrayList<>();
    ArrayList<Double> temp = new ArrayList<>();
    ArrayList<Double> humid = new ArrayList<>();
    ArrayList<Date> dates = new ArrayList<>();

    public void clean(List<Double> dirty){
        dirty.removeIf(h -> h == -999.0);
    }

    public void parse(ArrayList<Double> data, ArrayList<Double> humid, ArrayList<Double> temp){
        for(int i = 0; i < data.size(); i++){
            if((i % 2) == 0){
                temp.add(data.get(i));
            }
            else{
                humid.add(data.get(i));
            }
        }
    }

    @Override
    public TempHumidReading midRead() {
        int a = temp.size();
        int b = humid.size();
        if (!(a == 0) && !(b == 0)) {
            return new SuperTempHumidReading(temp.get(a / 2), humid.get(b / 2));
        } else if (a == 0 && !(b == 0)) {
            return new SuperTempHumidReading(-999.0, humid.get(b / 2));
        } else if (b == 0 && !(a == 0)) {
            return new SuperTempHumidReading(temp.get(a / 2), -999.0);
        } else {
            return new SuperTempHumidReading(-999.0, -999.0);
        }
    }

    @Override
    public TempHumidReading midRead(double onDate) {
        ArrayList<Double> nums = new ArrayList<Double>();
        for (Date d : dates) {
            nums.add(d.date);
        }
        int n = 0;

        if(nums.contains(onDate)){
            for (Double num : nums) {
                if (sameDate(num, onDate)) {
                    n = nums.indexOf(num);
                }
            }
        } else{
            return new SuperTempHumidReading(-999.0, -999.0);
        }
        Date g = dates.get(n);

        int a = g.temp.size();
        int b = g.humid.size();
        if (!(a == 0) && !(b == 0)) {
            return new SuperTempHumidReading(g.temp.get(a / 2), g.humid.get(b / 2));
        } else if (a == 0 && !(b == 0)) {
            return new SuperTempHumidReading(-999.0, g.humid.get(b / 2));
        } else if (b == 0 && !(a == 0)) {
            return new SuperTempHumidReading(g.temp.get(a / 2), -999.0);
        } else {
            return new SuperTempHumidReading(-999.0, -999.0);
        }
    }

    public void switchStrategy(ParsedDataStrategy otherStrategy){
        otherStrategy.moveData(this.data);
        this.data.clear();
    }

    @Override
    public void moveData(List<Double> data) {}

    @Override
    public void process(List<Double> data) {
        ArrayList<Double> noDate = new ArrayList<>();
        ArrayList<Double> yesDate = new ArrayList<>();
        for (Double v : data) {
            ArrayList<Double> dtemp = new ArrayList<>();
            ArrayList<Double> dhumid = new ArrayList<>();
            noDate = new ArrayList<>();
            ArrayList<Double> vData = new ArrayList<Double>();
            if (isDateTime(v)) {
                int p = data.indexOf(v);
                int i = 1;
                while(p + i < data.size() && !(isDateTime(data.get(p+i)))){
                    vData.add(data.get(p+i));
                    i = i+1;
                }
                parse(vData, dhumid, dtemp);
                int y = 0;
                if (yesDate.contains(toDate(v))) {
                    y = yesDate.indexOf(toDate(v));
                    dates.get(y).temp.addAll(dtemp);
                    dates.get(y).humid.addAll(dhumid);
                    Collections.sort(dates.get(y).temp);
                    Collections.sort(dates.get(y).humid);
                    clean(dates.get(y).temp);
                    clean(dates.get(y).humid);
                } else{
                    Date d = new Date(toDate(v), dtemp, dhumid);
                    dates.add(d);
                    yesDate.add(toDate(v));
                    Collections.sort(dtemp);
                    Collections.sort(dhumid);
                    clean(dtemp);
                    clean(dhumid);
                }
            }
        }
        for(Double v : data){
            if(!isDateTime(v)){
                noDate.add(v);
            }
        }
        parse(noDate, humid, temp);
        Collections.sort(temp);
        Collections.sort(humid);
        clean(temp);
        clean(humid);

        data.clear();
        yesDate.clear();
        noDate.clear();
    }
}
