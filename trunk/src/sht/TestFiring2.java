package sht;

import static org.junit.Assert.assertTrue;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IBulletSnapshot;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.testing.RobotTestBed;

/**
 * This tests max bullet power fired when within 150 pixels of enemy robot.
 * 
 * @author Ted Shaw
 */
public class TestFiring2 extends RobotTestBed {
  // True if Michelangelo has fired a bullet with max power and within 150 pixels.
  boolean closeFire = false;

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
   * This test runs for 2 rounds.
   * 
   * @return The number of rounds.
   */
  @Override
  public int getNumRounds() {

    return 2;
  }

  /**
   * At the end of each turn, checks the power of all bullets moving across the battlefield. Checks
   * if max power is fired when with 150 pixels to enemy robot.
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

    // All active bullets belong to Michelangelo since SittingDuck does not fire.
    IBulletSnapshot bullets[] = event.getTurnSnapshot().getBullets();
    double bulletPower;

    // Get robot's current position
    double myX = myRobot.getX();
    double myY = myRobot.getY();
    double enemyX = enemyRobot.getX();
    double enemyY = enemyRobot.getY();

    if (mike.getDistance(myX, myY, enemyX, enemyY) < 150) {
      for (int i = 0; i < bullets.length; i++) {
        bulletPower = bullets[i].getPower();

        if (bulletPower == 3.0) {
          closeFire = true;
        }
      }
    }
  }

  /**
   * After the battle, check to see that we've fired at max power when robot was within 150 pixels.
   * 
   * @param event Details about the completed battle.
   */
  @Override
  public void onBattleCompleted(BattleCompletedEvent event) {
    assertTrue(closeFire);
  }

}