package sht;

import static org.junit.Assert.assertTrue;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.testing.RobotTestBed;

/**
 * Tests that Michelangelo moved from one position to the next.
 * 
 * @author Ted Shaw
 */
public class TestMovement2 extends RobotTestBed {
  boolean wentPos1 = false;
  boolean wentPos2 = false;

  /**
   * Specifies that SittingDuck and Michelangelo are to be matched up in this test case.
   * 
   * @return The comma-delimited list of robots in this match.
   */
  @Override
  public String getRobotNames() {
    return "sample.SittingDuck,sht.Michelangelo";
  }

  /**
   * This test runs for 10 round.
   * 
   * @return The number of rounds.
   */
  @Override
  public int getNumRounds() {
    return 10;
  }

  /**
   * If we are at position 1 previosuly, check to see if we are position 2.
   * 
   * @param event Info about the current state of the battle.
   */
  @Override
  public void onTurnEnded(TurnEndedEvent event) {
    // Get the snapshot of the robot Michelangelo
    IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[1];

    // Get robot's current position
    double xPos = robot.getX();
    double yPos = robot.getY();
    // The set the width of all robots
    final double robotWidth = 40.0;

    // check if robot is at top right
    if (wentPos1) {
      if ((xPos > (width - robotWidth / 2) && (yPos > (height - robotWidth / 2)))) {
        wentPos2 = true;
      }
      wentPos1 = false;
    }
    // check if robot is at top middle
    if ((xPos > (width - robotWidth / 2) && (yPos > (height - robotWidth / 2)))) {
      wentPos1 = true;
    }

  }

  /**
   * After the battle, check to see that we've visited the corners.
   * 
   * @param event Details about the completed battle.
   */
  @Override
  public void onBattleCompleted(BattleCompletedEvent event) {
    assertTrue(wentPos2);

  }

}
