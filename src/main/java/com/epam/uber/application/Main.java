package com.epam.uber.application;

import com.epam.uber.data.parser.HumanParser;
import com.epam.uber.data.parser.implementation.JsonHumanParser;
import com.epam.uber.data.reader.DataReader;
import com.epam.uber.data.reader.implementation.FileDataReader;
import com.epam.uber.exceptions.DataReaderException;
import com.epam.uber.logic.Human;
import com.epam.uber.logic.UberCabCompany;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static final String HUMAN_DATA_JSON = "humans.json";

    public static void main(String[] args) {
        try {
            processUber();
        } catch (DataReaderException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private static void processUber() throws DataReaderException {
        DataReader dataReader = new FileDataReader();
        String data = dataReader.readData(HUMAN_DATA_JSON);
        UberCabCompany uberCabCompany = UberCabCompany.getInstance();
        HumanParser humanParser = new JsonHumanParser(uberCabCompany);
        List<Human> humans = humanParser.parseHumans(data);

        int humansSize = humans.size();
        ExecutorService service = Executors.newFixedThreadPool(humansSize);
        humans.forEach(service::execute);
        service.shutdown();
    }
}
