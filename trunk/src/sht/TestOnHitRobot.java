package sht;

import static org.junit.Assert.assertTrue;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.testing.RobotTestBed;

/**
 * This tests that Michelangelo turns around when hitting another robot.
 * 
 * @author Ted Shaw
 */
public class TestOnHitRobot extends RobotTestBed {
  boolean closeBy = false;
  boolean didReverse = false;

  /**
   * Specifies that Corners and Michelangelo are to be matched up in this test case.
   * 
   * @return The comma-delimited list of robots in this match.
   */
  @Override
  public String getRobotNames() {

    return "sample.Corners,sht.Michelangelo";
  }

  /**
   * This test runs for2 rounds.
   * 
   * @return The number of rounds.
   */
  @Override
  public int getNumRounds() {

    return 2;
  }

  /**
   * At the end of each turn, checks Michelangelo reverses away from enemy robot after hitting it.
   * 
   * @param event Info about the current state of the battle.
   */
  @Override
  public void onTurnEnded(TurnEndedEvent event) {
    Michelangelo mike = new Michelangelo();
    // Get the snapshot of the robots
    IRobotSnapshot myRobot = event.getTurnSnapshot().getRobots()[0];
    // Get the snapshot of the robots
    IRobotSnapshot enemyRobot = event.getTurnSnapshot().getRobots()[1];

    // Get robot's current position
    double myX = myRobot.getX();
    double myY = myRobot.getY();
    double enemyX = enemyRobot.getX();
    double enemyY = enemyRobot.getY();

    if (closeBy) {
      if (mike.getDistance(myX, myY, enemyX, enemyY) > 50) {
        didReverse = true;
      }
      closeBy = false;
    }
    if (mike.getDistance(myX, myY, enemyX, enemyY) <= 50) {
      closeBy = true;
    }

  }

  /**
   * After the battle, check to see that we've reversed direction.
   * 
   * @param event Details about the completed battle.
   */
  @Override
  public void onBattleCompleted(BattleCompletedEvent event) {
    assertTrue(didReverse);
  }

}