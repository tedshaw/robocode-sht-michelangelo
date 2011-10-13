package sht;

import static org.junit.Assert.assertTrue;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.testing.RobotTestBed;

/**
 * Tests that Michelangelo's colors are correct.
 * 
 * @author Ted Shaw
 */
public class TestColors extends RobotTestBed {
  boolean bodyColor = false;
  boolean gunColor = false;
  boolean radarColor = false;

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
   * This test runs for 2 round.
   * 
   * @return The number of rounds.
   */
  @Override
  public int getNumRounds() {
    return 2;
  }

  /**
   * After each turn, check to see if color is correct.
   * 
   * @param event Info about the current state of the battle.
   */
  @Override
  public void onTurnEnded(TurnEndedEvent event) {
    // Get the snapshot of the robot Michelangelo
    IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[1];

    // check gun is Orange
    if (-14336 == robot.getGunColor()) {
      gunColor = true;
    }
    // check body is Dark Green
    if (-16751616 == robot.getBodyColor()) {
      bodyColor = true;
    }
    // check radar is Dark Green
    if (-16751616 == robot.getRadarColor()) {
      radarColor = true;
    }
  }

  /**
   * After the battle, check to if the robot's colors are correct.
   * 
   * @param event Details about the completed battle.
   */
  @Override
  public void onBattleCompleted(BattleCompletedEvent event) {
    // check gun is Orange
    assertTrue(gunColor);
    // check body is Dark Green
    assertTrue(bodyColor);
    // check radar is Dark Green
    assertTrue(radarColor);
  }

}
