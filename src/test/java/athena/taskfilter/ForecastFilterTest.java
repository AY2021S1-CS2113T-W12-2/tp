package athena.taskfilter;

import athena.Forecast;
import athena.Importance;
import athena.task.Task;
import athena.task.taskfilter.ForecastFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ForecastFilterTest {

    @Test
    void testIsTaskIncluded_all_returnsTrue() {
        ForecastFilter forecastFilter = new ForecastFilter(Forecast.ALL);
        Task inputTask = new Task("testName", "0900", "1", "05-11-2020",
                "20-12-2020", Importance.MEDIUM, "testNotes", 0);
        boolean isTaskIncluded = forecastFilter.isTaskIncluded(inputTask);
        assertEquals(isTaskIncluded, true);
    }

    @Test
    void testIsTaskIncluded_week_returnsTrue() {
        ForecastFilter forecastFilter = new ForecastFilter(Forecast.WEEK);
        Task inputTask = new Task("testName", "0900", "1", "05-11-2020",
                "16-10-2020", Importance.LOW, "testNotes", 0); // Tested on 12-10-2020
        boolean isTaskIncluded = forecastFilter.isTaskIncluded(inputTask);
        assertEquals(isTaskIncluded, true);
    }

    @Test
    void testIsTaskIncluded_week_returnsFalse() {
        ForecastFilter forecastFilter = new ForecastFilter(Forecast.WEEK);
        Task inputTask = new Task("testName", "0900", "1", "05-11-2020",
                "23-10-2020", Importance.LOW, "testNotes", 0); // Tested on 12-10-2020
        boolean isTaskIncluded = forecastFilter.isTaskIncluded(inputTask);
        assertEquals(isTaskIncluded, false);
    }

    @Test
    void testIsTaskIncluded_day_returnsTrue() {
        ForecastFilter forecastFilter = new ForecastFilter(Forecast.TODAY);
        Task inputTask = new Task("testName", "0900", "1", "05-11-2020",
                "12-10-2020", Importance.LOW, "testNotes", 0); // Tested on 12-10-2020
        boolean isTaskIncluded = forecastFilter.isTaskIncluded(inputTask);
        assertEquals(isTaskIncluded, true);
    }

    @Test
    void testIsTaskIncluded_day_returnsFalse() {
        ForecastFilter forecastFilter = new ForecastFilter(Forecast.TODAY);
        Task inputTask = new Task("testName", "0900", "1", "05-11-2020",
                "13-10-2020", Importance.LOW, "testNotes", 0); // Tested on 12-10-2020
        boolean isTaskIncluded = forecastFilter.isTaskIncluded(inputTask);
        assertEquals(isTaskIncluded, false);
    }
}