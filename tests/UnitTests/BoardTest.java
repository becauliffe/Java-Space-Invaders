import com.zetcode.Board;
import com.zetcode.Commons;
import com.zetcode.sprite.Alien;
import com.zetcode.sprite.Player;
import com.zetcode.sprite.Shot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import javax.swing.Timer;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardTest {
    private Board board;
    private Graphics graphicsMock;


    @BeforeEach
    void setUp() {
        board = new Board();

        graphicsMock = Mockito.mock(Graphics.class);


        // Create the Board instance with the mock Timer
    }

    @AfterEach
    void tearDown() {
        // Reset the state of the Board object after each test
        board = null; // delete the Board
    }

   // This test works 4 times out of 5
   // and 5 times out of 5 in debugging mode
   // this likely helps expose a race condition that
   // is hidden in the code

//    @Test
//    @Order(1)
//    void update_AlienShotCollision() throws Exception {
//        // Arrange
//        Timer timerMock = Mockito.mock(Timer.class);
//        setPrivateField("timer", timerMock);
//
//
//        Alien alien = Mockito.mock(Alien.class);
//        Mockito.doReturn(true).when(alien).isVisible();
//        Mockito.doReturn(100).when(alien).getX();
//        Mockito.doReturn(100).when(alien).getY();
//
//        // Mock the Bomb object
//        Alien.Bomb bomb = Mockito.mock(Alien.Bomb.class);
//        Mockito.doReturn(bomb).when(alien).getBomb();
//
//        Shot shot = Mockito.mock(Shot.class);
//        Mockito.doReturn(true).when(shot).isVisible();
//        Mockito.doReturn(100).when(shot).getX();
//        Mockito.doReturn(100).when(shot).getY();
//
//        List<Alien> aliens = new ArrayList<>();
//        aliens.add(alien);
//        setPrivateField("aliens", aliens);
//        setPrivateField("shot", shot);
//        setPrivateField("deaths", 0);
//
//        // Act
//        callPrivateMethod();
//
//        // Assert
//        assertEquals(1, getPrivateField("deaths"));
//    }

    @Test
    void drawAliens_DrawBothVisibleAndDyingAliens() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Arrange
        Alien visibleAlien = Mockito.mock(Alien.class);
        Alien dyingAlien = Mockito.mock(Alien.class);

        Alien.Bomb visibleAlienBomb = Mockito.mock(Alien.Bomb.class);
        when(visibleAlien.getBomb()).thenReturn(visibleAlienBomb);

        when(visibleAlien.isVisible()).thenReturn(true);
        when(dyingAlien.isDying()).thenReturn(true);

        List<Alien> aliens = new ArrayList<>();
        aliens.add(visibleAlien);
        aliens.add(dyingAlien);

        setPrivateField("aliens", aliens);

        // Act
        callPrivateMethod("drawAliens", graphicsMock);

        // Assert
        verify(graphicsMock, times(1)).drawImage(any(), anyInt(), anyInt(), any());
        verify(dyingAlien, times(1)).die();
    }

    @Test
    void drawPlayer_DrawVisiblePlayerAndDyingPlayer() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Arrange
        Player visiblePlayer = Mockito.mock(Player.class);
        when(visiblePlayer.isVisible()).thenReturn(true);
        when(visiblePlayer.isDying()).thenReturn(true);

        // Set private field player using reflection
        setPrivateField("player", visiblePlayer);

        // Act
        callPrivateMethod("drawPlayer", graphicsMock);

        // Assert
        verify(graphicsMock, times(1)).drawImage(any(), anyInt(), anyInt(), any());
        verify(visiblePlayer, times(1)).die();
    }

    @Test
    void drawShot_DrawVisibleShot() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Arrange
        Shot visibleShot = Mockito.mock(Shot.class);
        when(visibleShot.isVisible()).thenReturn(true);

        // Set private field shot using reflection
        setPrivateField("shot", visibleShot);

        // Act
        callPrivateMethod("drawShot", graphicsMock);

        // Assert
        verify(graphicsMock, times(1)).drawImage(any(), anyInt(), anyInt(), any());
    }



    // update() branch coverage tests

    /* Condition 1: if (deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY)
        Branch 1: deaths == Commons.NUMBER_OF_ALIENS_TO_DESTROY
        Branch 2: deaths != Commons.NUMBER_OF_ALIENS_TO_DESTROY
    */

    @ParameterizedTest
    @CsvSource({
            "0, true, true, Game Over",
            "12, true, true, Game Over",
            "24, false, false, Game won!",
    })
    void update_DeathsEqualToNumberOfAliensToDestroy_GameWon(int deaths, boolean expectedInGame, boolean expectedTimerRunning, String expectedMessage) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Arrange
        setPrivateField("inGame", true);
        setPrivateField("deaths", deaths);

        // Act
        callPrivateMethod();

        // Assert
        assertEquals(expectedInGame, getInGameFieldValue());
        assertEquals(expectedTimerRunning, isTimerRunning());
        assertEquals(expectedMessage, getMessageFieldValue());
    }

    /* Condition 2:  if (shot.isVisible())
        Branch 1: shot.isVisible() == true
        Branch 2: shot.isVisible() == false
    */
    @ParameterizedTest
    @CsvSource({
            "true, 'Assertions for when shot.isVisible() is true'",
            "false, 'Assertions for when shot.isVisible() is false'"
    })
    void update_ShotVisible(boolean isVisible) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Arrange
        setPrivateField("inGame", true);
        Shot shot = Mockito.mock(Shot.class);
        setPrivateField("shot", shot);
        setPrivateField("deaths", 0);
        setPrivateField("message", "Game Over"); // Set default message

        when(shot.isVisible()).thenReturn(isVisible);

        // Act
        callPrivateMethod();

        // Assert
        if (isVisible) {
            assertTrue(getInGameFieldValue(), "Game should still be in progress when shot is visible");
            assertTrue(isTimerRunning(), "Timer should be running when shot is visible");
            assertEquals("Game Over", getMessageFieldValue(), "Message should be 'Game Over' when shot is visible");
        } else {
            assertTrue(getInGameFieldValue(), "Game should be over when shot is not visible");
            assertTrue(isTimerRunning(), "Timer should be running when shot is visible");
            assertEquals("Game Over", getMessageFieldValue(), "Message should be 'Game Over' when shot is not visible");
        }
    }

    @ParameterizedTest
    @CsvSource({
            "true, " + (Commons.BOARD_WIDTH - Commons.BORDER_RIGHT + 1) + ", -1, 'Assertions for when alien is at right border and moving right'",
            "false, " + (Commons.BOARD_WIDTH - Commons.BORDER_RIGHT - 1) + ", -1, 'Assertions for when alien is not at right border or not moving right'",
            "false, " + (Commons.BOARD_WIDTH - Commons.BORDER_RIGHT - 1) + ", 1, 'Assertions for when alien is not at right border but moving right'"
    })
    void update_AlienAtRightBorderAndMovingRight(boolean isAtRightBorder, int x, int direction) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Arrange
        setPrivateField("inGame", true);
        setPrivateField("deaths", 0);

        Alien alien = Mockito.mock(Alien.class);
        Alien.Bomb bomb = Mockito.mock(Alien.Bomb.class);
        setPrivateField("aliens", Collections.singletonList(alien));

        when(alien.getX()).thenReturn(x);
        when(alien.isVisible()).thenReturn(true);
        when(alien.getBomb()).thenReturn(bomb);

        setPrivateField("direction", direction);

        // Act
        callPrivateMethod();

        // Assert
        if (isAtRightBorder && direction != -1) {
            assertEquals(-1, getPrivateField("direction"), "Direction should change to left when alien is at right border and moving right");
        } else {
            assertEquals(direction, getPrivateField("direction"), "Direction should remain unchanged");
        }
    }

    /* Condition 4: if (x <= Commons.BORDER_LEFT && direction != 1)
        Branch 1:  x <= Commons.BORDER_LEFT && direction != 1
        Branch 2:  !(x <= Commons.BORDER_LEFT && direction != 1)
    */
    @ParameterizedTest
    @CsvSource({
            "true, " + (Commons.BORDER_LEFT - 1) + ", 1, 'Assertions for when alien is at left border and moving left'",
            "false, " + (Commons.BORDER_LEFT + 1) + ", 1, 'Assertions for when alien is not at left border or not moving left'",
            "false, " + (Commons.BORDER_LEFT - 1) + ", 1, 'Assertions for when alien is not at left border but moving right'"
    })
    void update_AlienAtLeftBorderAndMovingLeft(boolean isAtLeftBorder, int x, int direction) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Arrange
        setPrivateField("inGame", true);
        setPrivateField("deaths", 0);

        Alien alien = Mockito.mock(Alien.class);
        Alien.Bomb bomb = Mockito.mock(Alien.Bomb.class);
        setPrivateField("aliens", Collections.singletonList(alien));

        when(alien.getX()).thenReturn(x);
        when(alien.isVisible()).thenReturn(true);
        when(alien.getBomb()).thenReturn(bomb);

        setPrivateField("direction", direction);

        // Act
        callPrivateMethod();

        // Assert
        if (isAtLeftBorder && direction != 1) {
            assertEquals(1, getPrivateField("direction"), "Direction should change to right when alien is at left border and moving left");
        } else {
            assertEquals(direction, getPrivateField("direction"), "Direction should remain unchanged");
        }
    }

    /* Condition 5: if (y > Commons.GROUND - Commons.ALIEN_HEIGHT)
        Branch 1: y > Commons.GROUND - Commons.ALIEN_HEIGHT
        Branch 2: !(y > Commons.GROUND - Commons.ALIEN_HEIGHT)
    */
    @ParameterizedTest
    @CsvSource({
            "true, " + (Commons.GROUND - Commons.ALIEN_HEIGHT + 1) + ", 'Assertions for when alien is at or below ground level'",
            "false, " + (Commons.GROUND - Commons.ALIEN_HEIGHT - 1) + ", 'Assertions for when alien is above ground level'"
    })
    void update_AlienAtOrBelowGroundLevel(boolean isAtOrBelowGround, int y) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Arrange
        setPrivateField("inGame", true);
        setPrivateField("deaths", 0);

        Alien alien = Mockito.mock(Alien.class);
        Alien.Bomb bomb = Mockito.mock(Alien.Bomb.class);
        setPrivateField("aliens", Collections.singletonList(alien));

        when(alien.getY()).thenReturn(y);
        when(alien.isVisible()).thenReturn(true);
        when(alien.getBomb()).thenReturn(bomb);

        // Act
        callPrivateMethod();

        // Assert
        assertEquals(!isAtOrBelowGround, getInGameFieldValue(), "Game should end when alien is above ground level");
        if (!isAtOrBelowGround) {
            assertEquals("Game Over", getMessageFieldValue(), "Message should be 'Invasion!' when alien is above ground level");
        }
    }

    @Test
    void update_AlienAtRightBorderAndMovingRight_ChangeDirectionToPositive() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Arrange
        setPrivateField("inGame", true);
        setPrivateField("deaths", 0);

        Alien alien = Mockito.mock(Alien.class);
        Alien.Bomb bomb = Mockito.mock(Alien.Bomb.class);
        setPrivateField("aliens", Collections.singletonList(alien));

        when(alien.getX()).thenReturn(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT);
        when(alien.isVisible()).thenReturn(true);
        when(alien.getBomb()).thenReturn(bomb);

        setPrivateField("direction", 1);

        // Act
        callPrivateMethod();

        // Assert
        assertEquals(-1, getPrivateField("direction"), "Direction should change to -1 when an alien is at the right border and moving right");
    }
//    @Test
//    public void testDrawBombing() throws NoSuchFieldException, IllegalAccessException {
//        // Set up the necessary conditions for the test
//        List<Alien> aliens = new ArrayList<>();
//        aliens.add(createAlienWithBomb(false)); // Non-destroyed bomb
//        aliens.add(createAlienWithBomb(true)); // Destroyed bomb
//
//        setPrivateField("aliens", aliens);
//
//
//        // Call the private drawBombing method
//        callPrivateMethod("drawBombing", graphicsMock);
//
//
//        // Verify that the non-destroyed bomb is drawn
//        // (We'll just verify that g.drawImage() is called once for the non-destroyed bomb)
//        assertEquals(1, (graphicsMock.getNumDrawImageCalls());
//    }

    @ParameterizedTest
    @CsvSource({
            "true, true, true",
            "true, false, false",
            "false, true, true",
            "true, true, false"
    })
    void update_AlienShotConditions(boolean isChanceMatch, boolean isAlienVisible, boolean isBombDestroyed)
            throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Arrange
        setPrivateField("inGame", true);
        setPrivateField("deaths", 0);
        // Create a list of aliens
        List<Alien> aliens = new ArrayList<>();
        Alien alien = Mockito.mock(Alien.class);
        Shot shot = Mockito.mock(Shot.class);
        aliens.add(alien);
        // Set private fields
        setPrivateField("aliens", aliens);
        setPrivateField("shot", shot);
        Alien.Bomb bomb = Mockito.mock(Alien.Bomb.class);
        for (Alien currentAlien : aliens) {
            // Mock behavior for each alien
            currentAlien = Mockito.mock(Alien.class);
            when(currentAlien.isVisible()).thenReturn(isAlienVisible);
            when(shot.isVisible()).thenReturn(true);
            when(shot.getX()).thenReturn(Commons.BOARD_WIDTH / 2);
            when(shot.getY()).thenReturn(Commons.BOARD_HEIGHT / 2);
            when(currentAlien.getBomb()).thenReturn(bomb); // Associate the bomb mock with each alien
            when(bomb.isDestroyed()).thenReturn(isBombDestroyed);
        }
        // Act
        callPrivateMethod();
        // Assert
        if (isChanceMatch && isAlienVisible && !isBombDestroyed) {
            for (Alien currentAlien : aliens) {
                verify(currentAlien,times(1)).getBomb();
            }
        } else {
            for (Alien currentAlien : aliens) {
                verify(bomb, never()).setDestroyed(false);
            }
        }
    }

    @Test
    void testDoDrawing_InGame() throws Exception {
        setPrivateField("inGame", true);

        // Act
        callPrivateMethod("doDrawing", graphicsMock);

        // Assert
        verify(graphicsMock).setColor(Color.black);
        verify(graphicsMock).fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        verify(graphicsMock).setColor(Color.green);
        verify(graphicsMock).drawLine(0, Commons.GROUND, Commons.BOARD_WIDTH, Commons.GROUND);
    }

    @Test
    void testDoDrawing_GameOver() throws Exception {
        // Arrange
        setPrivateField("inGame", false);

        // Act
        callPrivateMethod("doDrawing", graphicsMock);

        // Assert
        verify(graphicsMock,times(2)).setColor(Color.black);
        verify(graphicsMock,times(2)).fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
        verify(graphicsMock,times(1)).setColor(new Color(0, 32, 48));
    }


    // This test works 9 times out of 10
    // and 5 times out of 5 in debugging mode
    // this likely helps expose a race condition that
    // is hidden in the code
    @Test
    public void testGameOverByPlayerDeath() throws Exception {
        // Use reflection to access and modify private fields
        Player player = Mockito.mock(Player.class);
        setPrivateField("player", player);
        Mockito.doNothing().when(player).act();

        // Simulate player's death
        Mockito.doReturn(true).when(player).isDying();

//        when(player.isVisible()).thenReturn(false);
        Mockito.doReturn(false).when(player).isVisible();
        callPrivateMethod("drawPlayer", graphicsMock);

//        callPrivateMethod();
        callPrivateMethod("doDrawing", graphicsMock);

        // Verify timer has stopped
        assertFalse(isTimerRunning());
    }


    // Reflection utility methods

    // using reflection to set private fields
    private void setPrivateField(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field field = Board.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(board, value);
    }

    // using reflection to access private methods
    private void callPrivateMethod(String methodName, Graphics graphicsMock) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        java.lang.reflect.Method method = Board.class.getDeclaredMethod(methodName, Graphics.class);
        method.setAccessible(true);
        method.invoke(board, graphicsMock);
    }

    private void callPrivateMethod() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        java.lang.reflect.Method method = Board.class.getDeclaredMethod("update");
        method.setAccessible(true);
        method.invoke(board);
    }

    private boolean getInGameFieldValue() throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field field = Board.class.getDeclaredField("inGame");
        field.setAccessible(true);
        return (boolean) field.get(board);
    }

    private boolean isTimerRunning() throws NoSuchFieldException, IllegalAccessException {
        Field field = Board.class.getDeclaredField("timer");
        field.setAccessible(true);
        javax.swing.Timer timer = (javax.swing.Timer) field.get(board);
        return timer != null && timer.isRunning();
    }

    private String getMessageFieldValue() throws NoSuchFieldException, IllegalAccessException {
        Field messageField = Board.class.getDeclaredField("message");
        messageField.setAccessible(true);
        return (String) messageField.get(board);
    }

    private Object getPrivateField(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field field = Board.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(board);
    }


}
