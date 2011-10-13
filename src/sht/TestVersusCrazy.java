package sht;

import static org.junit.Assert.assertEquals;
import robocode.BattleResults;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.testing.RobotTestBed;

/**
 * Illustrates JUnit testing of Robocode robots.
 * This test simply verifies that Michelangelo always beats Crazy.
 * 
 * Also illustrates the overriding of a set of methods from RobotTestBed to show how the testing
 * behavior can be customized and controlled. 
 * 
 * @author Ted Shaw
 */
public class TestVersusCrazy extends RobotTestBed {
  
  /**
   * Specifies that Crazy and Michelangelo are to be matched up in this test case.
   * 
   * @return The comma-delimited list of robots in this match.
   */
  @Override
  public String getRobotNames() {
    return "sample.Crazy,sht.Michelangelo";
  }

  /**
   * This test runs for 10 rounds.
   * 
   * @return The number of rounds.
   */
  @Override
  public int getNumRounds() {
    return 10;
  }

  /**
   * The actual test, which asserts that Michelangelo has won every round against Crazy.
   * 
   * @param event Details about the completed battle.
   */
  @Override
  public void onBattleCompleted(BattleCompletedEvent event) {
    // Return the results in order of getRobotNames.
    BattleResults[] battleResults = event.getIndexedResults();
    // Sanity check that results[1] is Michelangelo (not strictly necessary, but illustrative).
    BattleResults michelangeloResults = battleResults[1];
    String robotName = michelangeloResults.getTeamLeaderName();
    assertEquals("Check that results[1] is Michelangelo", robotName, "sht.Michelangelo*");
    // Check to make sure Michelangelo won every round.
    assertEquals("Check Michelangelo winner", michelangeloResults.getFirsts(), getNumRounds());
  }
  
}
