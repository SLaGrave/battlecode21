package ACen1;

import battlecode.common.*;

public class Politician extends RobotPlayer {
    static Direction dir;
    static int movesTaken = 0;

    // Setup the politician
    static void setup() throws GameActionException {
        RobotInfo[] nearby = rc.senseNearbyRobots(); // All nearby Robots
        for (RobotInfo robot: nearby) {
            if (robot.getType() == RobotType.ENLIGHTENMENT_CENTER) { // If its an enlightenment center
                if (rc.canGetFlag(robot.getID())) {
                    dir = directions[rc.getFlag(robot.getID())];
                }
            }
        }
    }

    // Run the politician
    static void run() throws GameActionException {
        move();
    }

    // Move the politician
    static void move() throws GameActionException {
        if (rc.canMove(dir) && movesTaken < 5) {
            rc.move(dir);
            movesTaken++;
        }
        else if (movesTaken < 5) {
            Direction rdir = directions[(int) (Math.random() * directions.length)];
            if (rc.canMove(rdir) && (rdir.getDeltaX()*-1 != dir.getDeltaX() && rdir.getDeltaY()*-1 != dir.getDeltaY())) {
                rc.move(rdir);
                movesTaken++;
            }
        }
    }
}
