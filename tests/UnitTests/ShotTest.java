
import com.zetcode.Commons;
import com.zetcode.sprite.Player;
import com.zetcode.sprite.Shot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShotTest {

    @ParameterizedTest
    @CsvSource({"1,2","1000,3000","0,0","-100,-270"})
    public void constructor_initializeShot(int x,int y) {
        // arrange
        Shot shot = new Shot(x,y);

        // act

        // assert
        assertNotNull(shot, "shot successfully initialized.");
    }
    @Test
    public void defaultShotContructor(){
        Shot default_shot = new Shot();
        assertNotNull(default_shot, "shot successfully initialized.");

    }

    @Test
    public void defaultShotContructor_withInput(){
        Shot default_shot = new Shot(1,2);
        assertNotNull(default_shot, "shot successfully initialized.");

    }
}
