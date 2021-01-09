package AlphaCentauri;

import battlecode.common.*;

public strictfp class RobotPlayer {
    static final Direction[] directions = {
            Direction.NORTH, // 0, 1
            Direction.NORTHEAST, // 1, 1
            Direction.EAST, // 1, 0
            Direction.SOUTHEAST, // 1, -1
            Direction.SOUTH, // 0, -1
            Direction.SOUTHWEST, // -1, -1
            Direction.WEST, // -1, 0
            Direction.NORTHWEST, // -1, 1
    };

    static int slandererInf = 41;
    static int slandererMinToBuild = 120;

    static int muckrakerInf = 25;
    static int everyNShouldBeMuck = 5;

    static int politicianInf = 25;

    static final float percentBid = 0.1f;

    static RobotController rc;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.
        AlphaCentauri.RobotPlayer.rc = rc;

        switch (rc.getType()) {
            case ENLIGHTENMENT_CENTER: EnlightenmentCenter.setup(); break;
            case POLITICIAN:           Politician.setup();          break;
            case SLANDERER:            Slanderer.setup();           break;
            case MUCKRAKER:            Muckraker.setup();           break;
        }

        while (true) {
            // Try/catch blocks stop unhandled exceptions, which cause your robot to freeze
            try {
                System.out.println("I'm a " + rc.getType() + "! Location " + rc.getLocation());
                switch (rc.getType()) {
                    case ENLIGHTENMENT_CENTER: EnlightenmentCenter.run(); break;
                    case POLITICIAN:           Politician.run();          break;
                    case SLANDERER:            Slanderer.run();           break;
                    case MUCKRAKER:            Muckraker.run();           break;
                }

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();
            }
        }
    }

    static int myMod(int i, int j) {
        return (((i % j) + j) % j);
    }
}
