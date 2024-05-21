package UnitTests;

import com.zetcode.sprite.Player;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.awt.Component;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlayerTest {
    // methodName_expectedBehavior_scenariowithinputvalues
    @Test
    public void constructor_initializePlayer() {
        // arrange
        Player player = new Player();

        // act

        // assert
        assertNotNull(player, "Player successfully initialized.");
    }

    @Test
    public void just_act() {
        // arrange
        Player player = new Player();

        // act
        player.act();

        // assert
        assertNotNull(player, "Player successfully initialized.");
    }

    @Test
    public void keyPressed_PressLeftArrowKey_MovePlayerLeft() {
        // Arrange
        Player player = new Player();
        int initialX = player.getX();
        Component sourceComponent = new Component() {};
        KeyEvent keyEvent = new KeyEvent(sourceComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);

        // Act
        player.keyPressed(keyEvent);
        player.act();

        // Assert
        assertEquals(initialX - 2, player.getX(), "Player should move left by 2 units after pressing the left arrow key");
    }

    @Test
    public void keyPressed_PressRightArrowKey_MovePlayerRight() {
        // Arrange
        Player player = new Player();
        int initialX = player.getX();
        Component sourceComponent = new Component() {};
        KeyEvent keyEvent = new KeyEvent(sourceComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);

        // Act
        player.keyPressed(keyEvent);
        player.act();

        // Assert
        assertEquals(initialX + 2, player.getX(), "Player should move right by 2 units after pressing the right arrow key");
    }

    @Test
    public void keyReleased_ReleaseLeftArrowKey_StopPlayer() {
        // Arrange
        Player player = new Player();
        int initialX = player.getX();
        Component sourceComponent = new Component() {};
        KeyEvent keyEvent = new KeyEvent(sourceComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);

        // Act
        player.keyReleased(keyEvent);
        player.act();


        // Assert
        assertEquals(initialX, player.getX(), "Player should stop moving after releasing the left arrow key");
    }

    @Test
    public void keyReleased_ReleaseRightArrowKey_StopPlayer() {
        // Arrange
        Player player = new Player();
        int initialX = player.getX();
        Component sourceComponent = new Component() {};
        KeyEvent keyEvent = new KeyEvent(sourceComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);

        // Act
        player.keyReleased(keyEvent);
        player.act();


        // Assert
        assertEquals(initialX, player.getX(), "Player should stop moving after releasing the right arrow key");
    }
}
