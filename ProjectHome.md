**Movement:** Michelangelo's movement strategy is to move around the wall perimeter.  When Michelangelo hits another robot, it turns around and moves around the perimeter in the opposite direction.  I originally had my robot moving in a diamond shape, when it would go to the midpoint of each wall.  This strategy didn't prove as well because there were some scanning blind spots (the corners) when moving.

**Targeting:** Michelangelo is doing full 360 radar scans at the midpoint of each wall.  Michelangelo is not doing full scan at the corners because it also turning at the corners and the added time for scanning made me more susceptible to being shot.  When it turns at the corners, the radar does a 90 degree scan because of the 90 degree turn.  This proves that it finds the enemy robot almost all the time.

**Firing:** Michelangelo fires its gun once at corners and at wall midpoints.  This is when it is doing scanning.  I chose this strategy because if I decided to scan and fire more, Michelangelo would be more likely to get shot since these methods take time and my robot stays in the same position.  When it fires at the corners and midpoints, the fire power is proportional to the distance between my robot and the enemy robot.  I used 400/<distance [pixels](pixels.md)> as my firing power, which can have a maximum value of 3.  This formula proved the best value after testing with different equations.  If Michelangelo is within 150 pixels of the enemy robot, it fires the maximum bullet power, which is 3, continuously at the enemy robot.  This continues until the enemy robot and my robot are not within 150 pixels anymore.  150 pixels was found to be an optimal value from testing.