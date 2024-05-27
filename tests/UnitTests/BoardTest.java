import com.zetcode.Board;
import com.zetcode.sprite.Alien;
import com.zetcode.sprite.Player;
import com.zetcode.sprite.Shot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        // Arrange
        board = new Board();
        javax.swing.Timer timerMock = mock(javax.swing.Timer.class);

        try {
            Field timerField = Board.class.getDeclaredField("timer");
            timerField.setAccessible(true);
            timerField.set(board, timerMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        resetBoardState();
    }

    private void resetBoardState() {
        List<Alien> aliens = getFieldValue(board, "aliens");
        aliens.clear();

        setField(board, "inGame", true);
        setField(board, "player", new Player());
        setField(board, "shot", new Shot());
    }
    @Test
    void gameInit_CreatesAliensAndPlayer() {
        // Act
        List<Alien> aliens = getFieldValue(board, "aliens");
        Player player = getFieldValue(board, "player");

        // Assert
        assertNotNull(aliens);
        assertEquals(24, aliens.size());
        assertNotNull(player);
    }

    @Test
    void drawAliens_DrawsVisibleAliens() {
        // Arrange
        List<Alien> aliens = getFieldValue(board, "aliens");
        for (Alien alien : aliens) {
            Image imageMock = mock(Image.class);
            alien.setImage(imageMock);
            setField(alien, "visible", true);
        }

        Graphics2D graphicsMock = mock(Graphics2D.class);
        when(graphicsMock.create()).thenReturn(graphicsMock);

        // Act
        board.paintComponent(graphicsMock);

        // Assert
        verify(graphicsMock, times(aliens.size())).drawImage(any(), anyInt(), anyInt(), any());
    }

    @Test
    void drawPlayer_DrawsPlayerWhenVisible() {
        // Arrange
        Player player = getFieldValue(board, "player");
        setField(player, "visible", true);

        Graphics2D graphicsMock = mock(Graphics2D.class);
        when(graphicsMock.create()).thenReturn(graphicsMock);

        // Act
        board.paintComponent(graphicsMock);

        // Assert
        verify(graphicsMock, times(1)).drawImage(any(), eq(player.getX()), eq(player.getY()), any());
    }

    @Test
    void drawShot_DrawsShotWhenVisible() {
        // Arrange
        Graphics2D graphicsMock = mock(Graphics2D.class);
        when(graphicsMock.create()).thenReturn(graphicsMock);

        Shot shot = getFieldValue(board, "shot");
        setField(shot, "visible", true);

        // Act
        board.paintComponent(graphicsMock);

        // Assert
        verify(graphicsMock, times(1)).drawImage(any(), eq(shot.getX()), eq(shot.getY()), any());
    }

    @Test
    void gameOver_ShowsCorrectMessage() {
        // Arrange
        Graphics2D graphicsMock = mock(Graphics2D.class);
        when(graphicsMock.create()).thenReturn(graphicsMock);

        setField(board, "inGame", false);

        // Act
        board.paintComponent(graphicsMock);

        // Assert
        verify(graphicsMock, times(1)).drawString(eq("Game Over"), anyInt(), anyInt());
    }


    // Utility method to set field value using reflection b/c we can't change original code
    @SuppressWarnings("unchecked")
    private <T> T getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Utility method to set field value using reflection b/c we can't change original code
    private void setField(Object obj, String fieldName, Object value) {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(obj, value);
                return;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Field not found: " + fieldName);
    }
}
