import com.zetcode.SpaceInvaders;
import com.zetcode.sprite.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpaceInvadersTest {
    // Essentially System test
    // Only run idependantly

    @Test
    public void default_constructor() {
        // arrange
        var ex = new SpaceInvaders();
    }

    @Test
    public void run_main() {
        // arrange

        SpaceInvaders.main(null);
    }




}
