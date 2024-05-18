package tests;
import com.zetcode.Commons;
import com.zetcode.sprite.Alien;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class AlienTest {

    // methodName_expectedBehavior_scenariowithinputvalues
    @ParameterizedTest
    @CsvSource({
            // game board's origin (0,0) is top-left corner
            Commons.BORDER_LEFT + ", " + Commons.GROUND,          // boundary: border left and on ground
            Commons.BOARD_WIDTH + ", " + Commons.BOARD_HEIGHT,    // boundary: border width and height
            "-" + Commons.BORDER_LEFT + ", -" + Commons.GROUND,   // equivalence partition: negative border left and on the ground
            Commons.BORDER_RIGHT + ", 0",                         // boundary: border right and ground
            Commons.BORDER_LEFT + ", 0",                          // boundary: border left and ground
            Commons.ALIEN_INIT_X + ", " + Commons.ALIEN_INIT_Y,   // default position of alien

    })
    public void testAlienConstructorInitialization_ValidCoordinates(int x, int y) {
        // arrange
        // act
        Alien alien = new Alien(x, y);

        // assert
        assertEquals(x, alien.getX(), "The Alien's X coordinate should be initialized correctly");
        assertEquals(y, alien.getY(), "The Alien's Y coordinate should be initialized correctly");
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",  // Move to right
            "0, -1", // Move to left
            "0, 0",   // No movement
            Commons.BOARD_WIDTH + ", 1",  // Move right at board width
            "-" + Commons.BORDER_LEFT + ", -1" // Move left at border left

    })
    public void act_MoveAlienLeftOrRight_ModifiesXCoordinate(int initialX, int direction) {
        // arrange
        Alien alien = new Alien(initialX, 0);

        // act
        alien.act(direction);

        // assert
        assertEquals(initialX + direction, alien.getX());
    }

    // Bomb class tests
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
    @ParameterizedTest
    @CsvSource({
            Commons.BOARD_WIDTH + ", " + Commons.BOARD_HEIGHT, // boundary: board width and height
            "-" + Commons.BORDER_LEFT + ", -" + Commons.GROUND,// equivalence partition: negative board left and ground
            Commons.BORDER_RIGHT + ", " + Commons.GROUND,      // boundary: board right and ground
            Commons.BORDER_LEFT + ", " + Commons.GROUND,       // boundary: board left and ground
            Commons.ALIEN_INIT_X + ", " + Commons.ALIEN_INIT_Y // default position
    })
    public void constructor_InitializeBombWithXY_ValidCoordinateValuesXY(int x, int y) {
        // arrange
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
