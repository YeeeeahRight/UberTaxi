package сom.epam.uber.logic;

import com.epam.uber.logic.Human;
import com.epam.uber.logic.UberCabCompany;
import com.epam.uber.logic.UberTaxi;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class UberTaxiTest {
    private static final UberCabCompany COMPANY = Mockito.mock(UberCabCompany.class);
    private static final Human HUMAN = new Human(0, 0, 20, 20, "Sasha", COMPANY);
    private final UberTaxi uberTaxi = new UberTaxi(0, 0, 1, 2000);

    @Test
    public void testTransferShouldTransferWhenHumanIsCreated() {
        //when
        uberTaxi.transfer(HUMAN);
        //then
        Assert.assertTrue(HUMAN.isArrived());
    }

    @Test
    public void testTransferShouldChangeTaxiLocationWhenHumanIsDelivered() {
        //given
        int locationX = uberTaxi.getLocationX();
        int locationY = uberTaxi.getLocationY();
        int expectedValue = locationX * locationY;
        //when
        uberTaxi.transfer(HUMAN);
        locationX = uberTaxi.getLocationX();
        locationY = uberTaxi.getLocationY();
        int actualValue = locationX * locationY;
        //then
        Assert.assertNotEquals(expectedValue, actualValue);
    }

}
