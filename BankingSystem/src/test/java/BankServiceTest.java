import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BankServiceTest {

    private BankService bankService;

    @BeforeEach
    public void setUp() {
        bankService = new BankService();
        bankService.createAccount("A1", "Alice", 1000);
        bankService.createAccount("B1", "Bob", 500);
    }

    @Test
    public void testCreateAccountSuccess() {
        boolean created = bankService.createAccount("C1", "Charlie", 300);
        assertTrue(created);
        assertNotNull(bankService.getAccount("C1"));
    }

    @Test
    public void testCreateAccountDuplicateId() {
        boolean created = bankService.createAccount("A1", "Alice Duplicate", 200);
        assertFalse(created);
    }

    @Test
    public void testDepositSuccess() {
        boolean result = bankService.deposit("A1", 500);
        assertTrue(result);
        assertEquals(1500, bankService.getAccount("A1").getBalance());
    }

    @Test
    public void testDepositNegativeAmount() {
        boolean result = bankService.deposit("A1", -100);
        assertFalse(result);
    }

    @Test
    public void testWithdrawSuccess() {
        boolean result = bankService.withdraw("A1", 400);
        assertTrue(result);
        assertEquals(600, bankService.getAccount("A1").getBalance());
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        boolean result = bankService.withdraw("B1", 1000);
        assertFalse(result);
    }

    @Test
    public void testTransferSuccess() {
        boolean result = bankService.transfer("A1", "B1", 300);
        assertTrue(result);
        assertEquals(700, bankService.getAccount("A1").getBalance());
        assertEquals(800, bankService.getAccount("B1").getBalance());
    }

    @Test
    public void testTransferInvalidAccounts() {
        boolean result = bankService.transfer("A1", "Z9", 300);
        assertFalse(result);
    }
}
