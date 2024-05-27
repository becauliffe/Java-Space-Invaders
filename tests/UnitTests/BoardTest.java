import com.zetcode.Board;
import com.zetcode.sprite.Alien;
import com.zetcode.sprite.Player;
import com.zetcode.sprite.Shot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class BoardTest {
    private Board board;
    private Graphics graphicsMock;

    @BeforeEach
    void setUp() {
        board = new Board();
        graphicsMock = Mockito.mock(Graphics.class);
    }

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

    @Test
    void drawBombing_DrawBombs() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // Arrange
        Alien alienWithDestroyedBomb = Mockito.mock(Alien.class);
        Alien alienWithActiveBomb = Mockito.mock(Alien.class);
        Alien.Bomb destroyedBomb = Mockito.mock(Alien.Bomb.class);
        Alien.Bomb activeBomb = Mockito.mock(Alien.Bomb.class);
        when(alienWithDestroyedBomb.getBomb()).thenReturn(destroyedBomb);
        when(alienWithActiveBomb.getBomb()).thenReturn(activeBomb);
        when(destroyedBomb.isDestroyed()).thenReturn(true);
        when(activeBomb.isDestroyed()).thenReturn(false);

        List<Alien> aliens = new ArrayList<>();
        aliens.add(alienWithDestroyedBomb);
        aliens.add(alienWithActiveBomb);

        // Set private field aliens using reflection
        setPrivateField("aliens", aliens);

        // Act
        callPrivateMethod("drawBombing", graphicsMock);

        // Assert
        verify(graphicsMock, times(1)).drawImage(any(), anyInt(), anyInt(), any());
    }

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
}
