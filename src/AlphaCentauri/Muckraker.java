package AlphaCentauri;

import battlecode.common.*;

public class Muckraker extends RobotPlayer {
    static int dirIdx;

    static int xLean;
    static int yLean;

    static RobotInfo[] nearby;

    static RobotInfo target = null;

    // Setup the muckraker
    static void setup() throws GameActionException {
        dirIdx = (int) (Math.random() * directions.length);

    }

    // Run the muckraker
    static void run() throws GameActionException {
        xLean = 0; yLean = 0; target = null; // Reset guiding
        analyze();
        snipe();
        move();

        int flag = Utils.genFlagNearestEC();
        System.out.println("My Flag is " + flag);
        if (rc.canSetFlag(flag)) { rc.setFlag(flag); }
        else { System.out.println("Can't set it to that!"); }
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
                if (robot.type.canBeExposed() && rc.canExpose(robot.location)) {
                    // Target the best robot to hit
                    if (target == null) { target = robot; }
                    else if (robot.getConviction() > target.getConviction()) { target = robot; }
                }
                xLean += robot.getLocation().x - x;
                yLean += robot.getLocation().y - y;
            }
        }
    }

    static void snipe() throws GameActionException {
        if (target != null) {
            System.out.println("Shooting target");
            rc.expose(target.location);
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
            xLean = Math.min(1, Math.max(-1, xLean));
            yLean = Math.min(1, Math.max(-1, yLean));
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
