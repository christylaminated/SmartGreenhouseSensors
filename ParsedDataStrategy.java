import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface ParsedDataStrategy {


    void moveData(List<Double> data);


    /**
     * Processes the data so it is organized by temperature, humidity, and dates
     *
     * @param data the list of all data added from the green house sensors
     */
    void process(List<Double> data);

    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     *
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     * and the middle humidity of the sorted humidities
     * If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    TempHumidReading midRead();
    /**
     * produces a pair of the middle temperature and humidity (respectively) from the stored readings ignoring error values (-999s)
     *
     * @param onDate the date which to consider medianReadings for (inclusive) with the format YYYYMMDD.0
     * @return a new SensorReading object that has the middle temperature of all the sensor values (value at index (size() / 2) of the sorted temperatures)
     * and the middle humidity of the sorted humidities
     * If there are no valid temperature or humidity values, respectively, then the resulting sensor reading should have -999 for that data
     */
    TempHumidReading midRead(double onDate);

    void switchStrategy(ParsedDataStrategy otherStrategy);

}
