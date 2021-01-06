package ACen1;

import battlecode.common.*;

public strictfp class RobotPlayer {
    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    static final float percentBid = 0.01f;

    static final float percentMuckracker = 0.5f;
    static final int minimumMuckraker = 10;

    static final float percentSlanderer = 0.1f;
    static final int minimumSlanderer = 100;

    static RobotController rc;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.
        ACen1.RobotPlayer.rc = rc;

        switch (rc.getType()) {
            case ENLIGHTENMENT_CENTER: EnlightenmentCenter.setup(); break;
            case POLITICIAN:           Politician.setup();          break;
            case SLANDERER:            Slanderer.setup();           break;
            case MUCKRAKER:            Muckraker.setup();           break;
        }

        while (true) {
            // Try/catch blocks stop unhandled exceptions, which cause your robot to freeze
            try {
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
}
