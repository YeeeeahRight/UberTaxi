package сom.epam.uber.data.parser;

import com.epam.uber.data.parser.implementation.JsonHumanParser;
import com.epam.uber.logic.Human;
import com.epam.uber.logic.UberCabCompany;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class JsonHumanParserTest {
    private static final String CORRECT_JSON_DATA = "{\"humans\":[{\"locationCoordinateX\":1500," +
            "\"locationCoordinateY\":1500,\"destinationPointX\":2000,\"destinationPointY\": -500, \"name\": \"Sasha\"}," +
            "{\"locationCoordinateX\":200,\"locationCoordinateY\":200,\"destinationPointX\":800," +
            "\"destinationPointY\": 400, \"name\": \"Pasha\"}]}";
    private static final String INCORRECT_JSON_DATA = "{dasd\"dasdas{[]][][{";
    private static final UberCabCompany CAB_COMPANY = Mockito.mock(UberCabCompany.class);
    private static final List<Human> EXPECTED_HUMANS = Arrays.asList(
            new Human(1500, 1500, 2000, -500, "Sasha", CAB_COMPANY),
            new Human(200, 200, 800, 400, "Pasha", CAB_COMPANY));
    private final JsonHumanParser humanParser = new JsonHumanParser(CAB_COMPANY);

    @Test
    public void testParseHumansShouldParseWhenDataIsCorrect() {
        //given
        List<Human> parsedHumans;
        //when
        parsedHumans = humanParser.parseHumans(CORRECT_JSON_DATA);
        //then
        Assert.assertEquals(EXPECTED_HUMANS, parsedHumans);
    }

    @Test(expected = JSONException.class)//then
    public void testParseHumansShouldThrowExceptionWhenDataIsIncorrect() {
        humanParser.parseHumans(INCORRECT_JSON_DATA);
    }
}
