package сom.epam.uber.logic;

import com.epam.uber.exceptions.UberException;
import com.epam.uber.logic.Human;
import com.epam.uber.logic.UberCabCompany;
import com.epam.uber.logic.UberTaxi;
import org.junit.Assert;
import org.junit.Test;

public class UberCabCompanyTest {
    private static final UberCabCompany COMPANY = UberCabCompany.getInstance();
    private static final Human IN_RANGE_HUMAN = new Human(0, 0, 20, 20, "Sasha", COMPANY);
    private static final Human OUT_OF_RANGE_HUMAN = new Human(20000, 20000, 20, 20, "Sasha", COMPANY);

    @Test
    public void testGetInstanceShouldReturnSameObjectWhenInstanceAlreadyCreated() {
        //given
        UberCabCompany actualCompany;
        //when
        actualCompany = UberCabCompany.getInstance();
        //then
        Assert.assertEquals(COMPANY, actualCompany);
    }

    @Test
    public void testAcquireTaxiShouldAcquireTaxiWhenHumanIsInRange() throws UberException {
        //given
        UberTaxi taxi;
        //when
        taxi = COMPANY.acquireTaxi(IN_RANGE_HUMAN);
        //then
        Assert.assertEquals(UberTaxi.class, taxi.getClass());
    }

    @Test
    public void testAcquireTaxiShouldReturnNullWhenHumanIsOutOfRange() throws UberException {
        //given
        UberTaxi taxi;
        //when
        taxi = COMPANY.acquireTaxi(OUT_OF_RANGE_HUMAN);
        //then
        Assert.assertNull(taxi);
    }
}
