package tests;
import com.zetcode.sprite.Alien;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class AlienTest {

    // methodName_expectedBehavior_scenariowithinputvalues
    @Test
    public void constructor_InitializeAlienWithXY_ValidCoordinateValuesXY() {
        // arrange
        int x = 10;
        int y = 14;

        // act
        Alien alien = new Alien(x,y);

        // assert
        assertEquals(x, alien.getX(), "The Alien's X coordinate should be initialized correctly");
        assertEquals(y, alien.getY(), "The Alien's Y coordinate should be initialized correctly");
    }

    @ParameterizedTest
    @CsvSource({
            // Move to right
            "0, 1",
            // Move to left
            "0, -1",
    })
    public void act_MoveAlienLeftOrRight_ModifiesXCoordinate(int initialX, int direction) {
        // arrange
        Alien alien = new Alien(initialX, 0);

        // act
        alien.act(direction);

        // assert
        assertEquals(initialX + direction, alien.getX());
    }

    @Test
    public void getBomb_NotNull() {
        // arrange
        Alien alien = new Alien(0, 0);

        // act
        Alien.Bomb bomb = alien.getBomb();

        // assert
        assertNotNull(bomb, "Bomb should not be null");
    }

    // Bomb class
    @Test
    public void constructor_InitializeBombWithXY_ValidCoordinateValuesXY() {
        // arrange
        int x = 15;
        int y = 20;
        Alien alien = new Alien(0, 0);

        // act
        Alien.Bomb bomb = alien.new Bomb(x, y);

        // assert
        assertEquals(x, bomb.getX(), "The bomb's X coordinate should be initialized correctly");
        assertEquals(y, bomb.getY(), "The bomb's Y coordinate should be initialized correctly");
    }

    @ParameterizedTest
    @CsvSource({
            ("false"),
            ("true")
    })
    public void setDestroyed_AssignsDestroyedCorrectly(boolean destroyed) {
        // arrange
        Alien alien = new Alien(0, 0);
        Alien.Bomb bomb = alien.getBomb();

        // act
        bomb.setDestroyed(destroyed);

        // assert
        assertEquals(bomb.isDestroyed(), destroyed, "The bomb's destroyed flag should be set correctly");
    }

    @ParameterizedTest
    @CsvSource({
            ("true"),
            ("false")
    })
    public void isDestroyed_ReturnsDestroyedCorrectly(boolean destroyed) {
        // arrange
        Alien alien = new Alien(0,0);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(destroyed);

        // act

        // assert
        assertEquals(destroyed, bomb.isDestroyed(),
                "The bomb should return the correct destroyed state");

    }

}
