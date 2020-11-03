package com.epam.uber.data.reader;

import com.epam.uber.exceptions.DataReaderException;

public interface DataReader {
    String readData(String filePath) throws DataReaderException;
}
