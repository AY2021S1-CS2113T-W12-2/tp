package athena.task.taskfilter;

import athena.Forecast;
import athena.Importance;
import athena.exceptions.InvalidForecastException;
import athena.exceptions.InvalidImportanceException;

public class FilterCalculator {
    Forecast forecast;
    Importance importance;

    public FilterCalculator(String importance, String forecast)
            throws InvalidImportanceException, InvalidForecastException {
        this.forecast = calculateForecast(forecast);
        this.importance = calculateImportance(importance);
    }

    private Importance calculateImportance(String importance) throws InvalidImportanceException {
        try {
            return Importance.valueOf(importance.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidImportanceException();
        }
    }

    private Forecast calculateForecast(String forecast) throws InvalidForecastException {
        try {
            return Forecast.valueOf(forecast.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidForecastException();
        }
    }

    public Forecast getForecast() {
        return forecast;
    }

    public Importance getImportance() {
        return importance;
    }
}
