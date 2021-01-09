package ACen1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;

public class Slanderer extends RobotPlayer {
    static int dirIdx;

    static int xLean;
    static int yLean;

    static RobotInfo[] nearby;

    // Setup the slanderer
    static void setup() throws GameActionException {
        dirIdx = (int) (Math.random() * directions.length);

    }

    // Run the slanderer
    static void run() throws GameActionException {
        xLean = 0; yLean = 0; // Reset guiding
        analyze();
        move();
    }

    static void analyze() throws GameActionException {
        nearby = rc.senseNearbyRobots();
        if (nearby.length == 0) {
            System.out.println("No one nearby.");
            return;
        }
        int x = rc.getLocation().x;
        int y = rc.getLocation().y;
        for (RobotInfo robot : nearby) {
            if (robot.getTeam() == rc.getTeam().opponent()) {
                xLean += robot.getLocation().x - x;
                yLean += robot.getLocation().y - y;
            }
        }
    }

    static void move() throws GameActionException {
        // Random movement if not leans
        if (xLean == 0 && yLean == 0) {
            int[] x = {0, 1, -1, 3, -3, 2, -2, 4, -4};
            for (int i: x) {
                if (rc.canMove(directions[myMod((dirIdx + i), directions.length)])) {
                    rc.move(directions[myMod((dirIdx + i), directions.length)]);
                    dirIdx += i;
                    break;
                }
            }
        }
        else {
            // Clean the leans somewhat
            if (Math.abs(xLean) > 2 * Math.abs(yLean)) {yLean = 0;}
            else if (Math.abs(yLean) > 2 * Math.abs(xLean)) {xLean = 0;}
            xLean = Math.min(1, Math.max(-1, xLean)) * -1;
            yLean = Math.min(1, Math.max(-1, yLean)) * -1;
            for (Direction dir : directions) {
                if (dir.getDeltaY() == yLean && dir.getDeltaX() == xLean) {
                    System.out.println("Attempting to move " + dir);
                    if (rc.canMove(dir)) { rc.move(dir); }
                    return;
                }
            }
            System.out.println("Unable to move.");
        }
    }
}
