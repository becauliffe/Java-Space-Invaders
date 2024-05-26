//package UnitTests;

import com.zetcode.Commons;

import com.zetcode.sprite.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.swing.*;
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
    public void rightWall_keyPressed_PressRightArrowKey() {
        // Arrange
        Player player = new Player();

        for (int i = 0; i < 29; i++) {

        }

        Component sourceComponent = new Component() {};
        KeyEvent keyEvent = new KeyEvent(sourceComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);

        var playerImg = "src/images/player.png";
        var ii = new ImageIcon(playerImg);

        int width = ii.getImage().getWidth(null);
        int player_x_pos = player.getX();
        // Get to the right wall
        // player starts at 270 and moves by 2 per press
        // wall = Commons.BOARD_WIDTH - 2 * width
        player.keyPressed(keyEvent);

        while(player_x_pos < Commons.BOARD_WIDTH - 2 * width){
            player.act();
            player_x_pos = player.getX();
        }

        int initialX = player.getX();

        // Act
        player.keyPressed(keyEvent);
        player.act();

        // Assert
        assertEquals(initialX, player.getX(), "Player should not be able to move passed the right wall");
    }

    @Test
    public void leftWall_keyPressed_PressleftArrowKey() {
        // Arrange
        Player player = new Player();

        Component sourceComponent = new Component() {};
        KeyEvent keyEvent = new KeyEvent(sourceComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);

        var playerImg = "src/images/player.png";
        var ii = new ImageIcon(playerImg);

        int player_x_pos = player.getX();
        // Get to the right wall
        // player starts at 270 and moves by 2 per press
        // wall = Commons.BOARD_WIDTH - 2 * width
        player.keyPressed(keyEvent);

        while(player_x_pos > 2){
            player.act();
            player_x_pos = player.getX();
        }

        int initialX = player.getX();

        // Act
        player.keyPressed(keyEvent);
        player.act();

        // Assert
        assertEquals(initialX, player.getX(), "Player should not be able to move passed the left wall");
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

    // If an arrow is pressed and held, then the other arrow is pressed
    // direction changes, however if the original arrow is released
    // movement stops even if second arrow is held
    @Test
    public void keyCombination_hold_hold_release_hold_release() {

        Player player = new Player();
        int initialX = player.getX();
        Component sourceComponent = new Component() {};
        KeyEvent right_press = new KeyEvent(sourceComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        KeyEvent left_press = new KeyEvent(sourceComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);
        KeyEvent right_release = new KeyEvent(sourceComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        KeyEvent left_release = new KeyEvent(sourceComponent, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);

        // Act
        // initx + 2
        player.keyPressed(right_press);
        player.act();
        player.keyPressed(left_press);
        // Initx + 2 - 2
        player.act();
        // initx + 2 - 2 - 2
        player.act();
        player.keyReleased(right_release);
        // no more movement should occur
        // x should be at initx - 2
        player.act();
        player.act();
        player.act();
        player.keyReleased(left_release);
        // Assert
        assertEquals(initialX-2, player.getX());

    }

    // Sprite Specific Tests

//    @ParameterizedTest
//    @CsvSource({"1,2"})
//
    @Test
    public void player_sprite_getY() {
        // Arrange
        Player player = new Player();

        int initialY = player.getY();
        Component sourceComponent = new Component() {};
        KeyEvent keyEvent_right = new KeyEvent(sourceComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);
        KeyEvent keyEvent_left = new KeyEvent(sourceComponent, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);

        // Act
        player.keyPressed(keyEvent_right);
        player.act();
        player.act();
        player.keyPressed(keyEvent_left);
        player.act();
        // 280 is the starting place for Y

        assertEquals(initialY, player.getY(), "Y should not change with any key input");

    }

    @ParameterizedTest
    @CsvSource({"250","0","300","-1000","1000"})
    public void player_sprite_setY_getY(int y) {
        // Arrange
        Player player = new Player();

        player.setY(y);
        // 280 is the starting place for Y

        assertEquals(y, player.getY(), "Y should get set properly");


    }
    @ParameterizedTest
    @CsvSource({"250","0","300","-1000","1000"})
    public void player_sprite_setX_getX(int x) {
        // Arrange
        Player player = new Player();

        player.setY(x);
        // 280 is the starting place for Y

        assertEquals(x, player.getY(), "Y should get set properly");


    }

    @Test
    public void player_sprite_isVisible() {
        // Arrange
        Player player = new Player();
        assertEquals(player.isVisible(), true, "Player should start be visible");

    }

    @Test
    public void player_sprite_notVisible_onDeath() {
        // Arrange
        Player player = new Player();
        player.die();
        assertEquals(player.isVisible(), false, "Player should not be visible on death");

    }
    @Test
    public void player_sprite_notdying_alive() {
        // Arrange
        Player player = new Player();
        assertEquals(player.isDying(), false, "Sprite returns false when not dying");
    }

    @Test
    public void player_sprite_setDying() {
        // Arrange
        Player player = new Player();
        player.setDying(true);
        assertEquals(player.isDying(), true, "Sprite returns true when dying");
    }

    @Test
    public void player_sprite_getImage() {
        var playerImg = "src/images/player.png";
        Player player = new Player();
        var ii = new ImageIcon(playerImg);
        assertEquals(player.getImage(),ii.getImage(),"the player image should match the input");
    }
    @Test
    public void player_sprite_setImage() {
        var explodeImg = "src/images/explosion.png";
        Player player = new Player();
        var ii = new ImageIcon(explodeImg);
        player.setImage(ii.getImage());

    }


}
