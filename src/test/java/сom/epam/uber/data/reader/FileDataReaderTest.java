package сom.epam.uber.data.reader;

import com.epam.uber.data.reader.implementation.FileDataReader;
import com.epam.uber.exceptions.DataReaderException;
import org.junit.Assert;
import org.junit.Test;

public class FileDataReaderTest {
    private static final String FILE_DATA = "{\"humans\":[{\"locationCoordinateX\":1500," +
            "\"locationCoordinateY\":1500,\"destinationPointX\":2000,\"destinationPointY\":-500,\"name\": \"Sasha\"}]}";
    private static final String VALID_FILE_PATH = "src/test/resources/human.json";
    private static final String INVALID_FILE_PATH = "src/test/resources/invalid.json";
    private final FileDataReader fileDataReader = new FileDataReader();

    @Test
    public void testReadDataShouldReadWhenFileIsExist() throws DataReaderException {
        //given
        String actualData;
        //when
        actualData = fileDataReader.readData(VALID_FILE_PATH);
        //then
        Assert.assertEquals(FILE_DATA, actualData);
    }

    @Test(expected = DataReaderException.class)//then
    public void testReadDataShouldThrowExceptionWhenFileIsNotExist() throws DataReaderException {
        //when
        fileDataReader.readData(INVALID_FILE_PATH);
    }
}
