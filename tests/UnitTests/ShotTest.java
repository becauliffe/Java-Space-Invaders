package UnitTests;

import com.zetcode.sprite.Player;
import com.zetcode.sprite.Shot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShotTest {

    @ParameterizedTest
    @CsvSource("1,2")
    public void constructor_initializeShot(int x,int y) {
        // arrange
        Shot shot = new Shot(x,y);

        // act

        // assert
        assertNotNull(shot, "shot successfully initialized.");
    }

}
