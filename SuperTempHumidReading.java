public class SuperTempHumidReading extends TempHumidReading{

    public SuperTempHumidReading(double temperature, double humidity) {
        super(temperature, humidity);
    }

    public SuperTempHumidReading(){
        super(-999, -999);
    }

    public SuperTempHumidReading(TempHumidReading thr){
        super(thr.temperature, thr.humidity);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof SuperTempHumidReading)){
            return false;
        }
        SuperTempHumidReading a = (SuperTempHumidReading) o;
        return Math.abs(this.temperature - a.temperature) <= 0.001 && Math.abs(this.humidity - a.humidity) <= 0.001;
    }

    @Override
    public String toString(){
        String tmp;
        if(this.temperature == -999.0){
            tmp = "Err;";
        }else{
            tmp = String .format("%,.1fF;", this.temperature);
        }
        String hum;
        if(this.humidity == -999.0){
            hum = "Err";
        }else{
            hum = String .format("%,.1f%%", this.humidity);
        }
        return "{" + tmp + "" + hum + "}";
    }

}
