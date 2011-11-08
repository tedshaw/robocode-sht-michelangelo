package sht;

import robocode.Robot;
import robocode.Rules;
import java.awt.Color;
import robocode.ScannedRobotEvent;
import robocode.HitRobotEvent;
import robocode.util.Utils;
import robocode.BulletMissedEvent;
import robocode.BulletHitEvent;

/**
 * Robot that circles the perimeter, scanning for robots.
 * 
 * @author Ted Shaw
 */
public class Michelangelo extends Robot {
  int pos;
  double BF_WIDTH;
  double BF_HEIGHT;
  double ROBOT_WIDTH;
  boolean peek, forward;

  /**
   * Move around the track.
   */
  public void run() {
    pos = 1;
    BF_WIDTH = getBattleFieldWidth();
    BF_HEIGHT = getBattleFieldHeight();
    ROBOT_WIDTH = getWidth();
    Color DARK_GREEN = new Color(0, 100, 0);
    setColors(DARK_GREEN, Color.ORANGE, DARK_GREEN);
    setBulletColor(Color.ORANGE);

    while (true) {
      nextPos();
      movePos();
      peek = true;
      aimGun();
    }
  }

  /**
   * Move to the (x,y) coordinates given by parameters.
   * 
   * @param x coordinate to move to
   * @param y coordinate to move to
   */
  public void move(double x, double y) {
    double heading, distance;
    distance = getDistance(getX(), getY(), x, y);
    heading = Math.toDegrees(Math.atan2(getX() - x, getY() - y));
    turnRight(Utils.normalRelativeAngleDegrees(180 + heading - getHeading()));
    ahead(distance);
  }

  /**
   * Move to the next position. Positions are corners and midpoints between corners.
   */
  public void movePos() {
    if (pos < 1) {
      pos = pos + 8;
    }
    else if (pos > 8) {
      pos = pos - 8;
    }
    switch (pos) {
    case 1:
      // move top left
      move(ROBOT_WIDTH / 2, BF_HEIGHT - ROBOT_WIDTH / 2);
      break;
    case 2:
      // move left
      move(ROBOT_WIDTH / 2, BF_HEIGHT / 2);
      break;
    case 3:
      // move bottom left
      move(ROBOT_WIDTH / 2, ROBOT_WIDTH / 2);
      break;
    case 4:
      // move bottom
      move(BF_WIDTH / 2 - ROBOT_WIDTH / 2, ROBOT_WIDTH / 2);
      break;
    case 5:
      // move bottom right
      move(BF_WIDTH - ROBOT_WIDTH / 2, ROBOT_WIDTH / 2);
      break;
    case 6:
      // move right
      move(BF_WIDTH - ROBOT_WIDTH / 2, BF_HEIGHT / 2);
      break;
    case 7:
      // move top right
      move(BF_WIDTH - ROBOT_WIDTH / 2, BF_HEIGHT - ROBOT_WIDTH / 2);
      break;
    case 8:
      // move top
      move(BF_WIDTH / 2, BF_HEIGHT - ROBOT_WIDTH / 2);
      break;
    default:
      break;
    }
  }

  /**
   * Returns the distance of 2 sets of (x,y) coordinate.
   * 
   * 
   * @param x1 x-coordinate of robot 1
   * @param y1 y-coordinate of robot 1
   * @param x2 x-coordinate of robot 2
   * @param y2 y-coordinate of robot 2
   * 
   * @return distance to x and y coordinates
   */
  public double getDistance(double x1, double y1, double x2, double y2) {
    return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
  }

  /**
   * If enemy robot is with in 150 pixels, then shoot maximum power. If robot is at corner or
   * midpoint, then shoot proportional to distance.
   * 
   * @param event ScannedRobotEvent
   */
  @Override
  public void onScannedRobot(ScannedRobotEvent event) {
    double firePower = Math.min(400 / event.getDistance(), 3);
    double gunChange =
        Utils.normalRelativeAngleDegrees(getHeading() - getGunHeading() + event.getBearing());
    if ((event.getDistance() < 150) && (event.getEnergy() <= getEnergy())) {
      peek = true;
      turnGunRight(gunChange);
      fire(Rules.MAX_BULLET_POWER);
      scan();
    }
    if (peek) {
      turnGunRight(gunChange);
      fire(firePower);
      peek = false;
    }
  }

  /**
   * Moves to the next position, corner or midpoint.
   */
  public void nextPos() {
    if (forward) {
      pos++;
    }
    else {
      pos--;
    }
  }

  /**
   * If at a midpoint or corner scan for robots. If at midpoint, use radar. If at corner, move gun
   * and radar.
   */
  public void aimGun() {
    if (pos % 2 == 0) {
      setAdjustGunForRobotTurn(false);
      turnRadarRight(360);
    }
    else {
      setAdjustGunForRobotTurn(true);
      if (forward) {
        turnGunLeft(90);
      }
      else {
        turnGunRight(90);
      }
    }
  }

  /**
   * If hit another robot, then change direction on track.
   * 
   * @param event hit another robot
   */
  @Override
  public void onHitRobot(HitRobotEvent event) {
    double gunChange =
        Utils.normalRelativeAngleDegrees(getHeading() - getGunHeading() + event.getBearing());
    turnGunRight(gunChange);
    fire(Rules.MAX_BULLET_POWER);
    scan();

    forward = !forward;
    if (pos % 2 == 0) {
      nextPos();
    }

  }

  /**
   * If bullet hit, then shoot again if robot is scanned.
   * 
   * @param event bullet fired and hit
   */
  @Override
  public void onBulletHit(BulletHitEvent event) {
    peek = true;
    scan();
  }

  /**
   * If the bullet missed, then don't shoot again.
   * 
   * @param event bullet fired and missed
   */
  @Override
  public void onBulletMissed(BulletMissedEvent event) {
    peek = false;
  }

}