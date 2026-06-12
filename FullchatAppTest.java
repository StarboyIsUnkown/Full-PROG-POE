/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FullchatAppTest {

    private FullchatApp app;
    private FullchatApp.QuickChatApp chat;

    @BeforeEach
    public void setUp() {
        app = new FullchatApp();
        chat = new FullchatApp.QuickChatApp();
    }

    @Test
    public void testUsernameValid() {
        assertTrue(app.checkUserName("abc_"));
    }

    @Test
    public void testUsernameInvalid() {
        assertFalse(app.checkUserName("abcdef"));
    }

    @Test
    public void testPasswordValid() {
        assertTrue(app.checkPasswordComplexity("Password1!"));
    }

    @Test
    public void testPasswordInvalid() {
        assertFalse(app.checkPasswordComplexity("password"));
    }

    @Test
    public void testPhoneValid() {
        assertTrue(app.checkCellPhoneNumber("+27834567890"));
    }

    @Test
    public void testPhoneInvalid() {
        assertFalse(app.checkCellPhoneNumber("0834567890"));
    }

    @Test
    public void testLoginSuccess() {
        assertTrue(app.loginUser("user_", "Password1!", "user_", "Password1!"));
    }

    @Test
    public void testLoginFail() {
        assertFalse(app.loginUser("user_", "wrong", "user_", "Password1!"));
    }

    @Test
    public void testMessageIDValid() {
        assertTrue(chat.checkMessageID("1234567890"));
    }

    @Test
    public void testMessageIDInvalid() {
        assertFalse(chat.checkMessageID("12345678901"));
    }

    @Test
    public void testRecipientNumberValid() {
        assertEquals("Valid",
                chat.checkRecipientCell("+27834567890"));
    }

    @Test
    public void testMessageHashCreated() {
        String hash =
                chat.createMessageHash(
                        "1234567890",
                        1,
                        "Did you get the cake");

        assertNotNull(hash);
    }
}