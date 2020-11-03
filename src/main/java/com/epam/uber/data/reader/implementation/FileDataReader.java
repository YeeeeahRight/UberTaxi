package com.epam.uber.data.reader.implementation;

import com.epam.uber.data.reader.DataReader;
import com.epam.uber.exceptions.DataReaderException;
import com.epam.uber.logic.UberCabCompany;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader implements DataReader {
    private static final Logger LOGGER = Logger.getLogger(UberCabCompany.class);

    @Override
    public String readData(String filePath) throws DataReaderException {
        LOGGER.info("Starting reading all lines from " + filePath + " file...");
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(filePath));
            String dataLine = fileReader.readLine();
            while (dataLine != null) {
                stringBuilder.append(dataLine);
                dataLine = fileReader.readLine();
            }
        } catch (IOException e) {
            throw new DataReaderException(e.getMessage(), e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        LOGGER.info(filePath + " file was read!");
        return stringBuilder.toString();
    }
}
