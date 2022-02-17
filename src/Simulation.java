import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

/**
 * Class to manage simulation of a solo instance queue
 * 
 * @author Mary Elaine Califf and Frank Watring
 */
public class Simulation {
    public final int MAX_PLAYERS_PER_TICK = 8;
    private Queue tankQueue;
    private Queue healerQueue;
    private Queue dpsQueue;
    private PrintWriter reportFile;
    private Random randGenerator;
    private RoleManager roleMgr;
    private int simLength;
    private int nextPlayerNum;

    /**
     * Default constructor
     */
    public Simulation() {
        dpsQueue = new Queue();
        tankQueue = new Queue();
        healerQueue = new Queue();

    }

    /**
     * Set up the simulation given the configuration data
     * 
     * Configuration files hold the following data, with one item per line:
     * 
     * 1) seed for the random number generator, 2) chance out of 100 that a DPS/Tank
     * will choose to queue as a tank, 3) chance out of 100 that a DPS/Heal will
     * choose to queue as a healer, 4) number of ticks to run the simulation
     * 
     * @param configFileName The name of the configuration file to use for this
     *                       simulation run
     */
    public void setup(String configFileName) {
        try {
            Scanner input = new Scanner(new File(configFileName));
            long seed = input.nextLong();
            int tankPercent = input.nextInt();
            int healPercent = input.nextInt();
            simLength = input.nextInt();
            input.close();

            // set up the random number generator, using the specified seed if one was
            // provided
            if (seed != 0)
                randGenerator = new Random(seed);
            else
                randGenerator = new Random();

            // set up the role manager with the configuration data
            roleMgr = new RoleManager(randGenerator, tankPercent, healPercent);
            nextPlayerNum = 1;
            reportFile = new PrintWriter("simReport.txt");

        } catch (FileNotFoundException e) {
            System.err.println("Could not open configuration file: " + configFileName);
            System.err.println("Ending Simulation");
            System.exit(1);
        }
    }

    /**
     * Run the simulation
     */
    public void runSimulation() {
        int maxTankLength = 0;
        int maxHealLength = 0;
        int maxDPSLength = 0;

        double totalTankWait = 0;
        double totalHealWait = 0;
        double totalDPSWait = 0;

        int maxTankWait = 0;
        int maxHealWait = 0;
        int maxDPSWait = 0;

        int numInstancesStarted = 0;

        int finalDPSLength = 0;
        int finalTankLength = 0;
        int finalHealerLength = 0;

        double finalTotalDPSWait = 0;
        double finalTotalTankWait = 0;
        double finalTotalHealerWait = 0;

        int finalMaxDPSWait = 0;
        int finalMaxTankWait = 0;
        int finalMaxHealerWait = 0;

        // your code here

        // run the simulation loop
        for (int ticks = 1; ticks <= simLength; ticks++) {
            int playersThisTick = 0;
            playersThisTick = randGenerator.nextInt(MAX_PLAYERS_PER_TICK) + 1;

            for (int j = 0; j < playersThisTick; j++) {
                Player player = null;
                String baseRole = roleMgr.getBaseRole();

                // Check if either DPS/Healer or DPS/Tank
                if (baseRole.equals("DPS/Heal")) {
                    player = new Player(nextPlayerNum, roleMgr.getHealerDPSChoice(), ticks);

                } else if (baseRole.equals("DPS/Tank")) {
                    player = new Player(nextPlayerNum, roleMgr.getTankDPSChoice(), ticks);

                } else {
                    player = new Player(nextPlayerNum, baseRole, ticks);
                }

                // Prints the Player to file and enqueues the Player into the Queue
                reportFile.write("Player " + player.getPlayerNum() + " (" + baseRole + ") queued as "
                        + player.getPlayerRole() + " at tick " + ticks + "\n");
                if (player.getPlayerRole().equals("DPS")) {
                    dpsQueue.enqueue(player);
                } else if (player.getPlayerRole().equals("Tank")) {
                    tankQueue.enqueue(player);
                } else if (player.getPlayerRole().equals("Healer")) {
                    healerQueue.enqueue(player);
                }
                nextPlayerNum++;

            }

            // Checks if there is an instance
            if (tankQueue.getSize() >= 1 && healerQueue.getSize() >= 1 && dpsQueue.getSize() >= 3) {
                numInstancesStarted++;
                if (maxTankWait < ticks - tankQueue.front().getArrivalTime()) {
                    maxTankWait = ticks - tankQueue.front().getArrivalTime();
                }
                if (maxHealWait < ticks - healerQueue.front().getArrivalTime()) {
                    maxHealWait = ticks - healerQueue.front().getArrivalTime();
                }
                if (maxDPSWait < ticks - dpsQueue.front().getArrivalTime()) {
                    maxDPSWait = ticks - dpsQueue.front().getArrivalTime();
                }

                totalTankWait += ticks - tankQueue.front().getArrivalTime();
                totalHealWait += ticks - healerQueue.front().getArrivalTime();

                reportFile.write("Instance started at " + ticks + " with " + tankQueue.front().getPlayerNum() + ", "
                        + healerQueue.front().getPlayerNum() + ", " + dpsQueue.front().getPlayerNum() + ", ");

                // Dequeueing Instanced Roles
                tankQueue.dequeue();
                healerQueue.dequeue();

                // DPS
                totalDPSWait += ticks - dpsQueue.front().getArrivalTime();
                dpsQueue.dequeue();

                totalDPSWait += ticks - dpsQueue.front().getArrivalTime();
                reportFile.write(dpsQueue.front().getPlayerNum() + ", ");
                dpsQueue.dequeue();

                totalDPSWait += ticks - dpsQueue.front().getArrivalTime();
                reportFile.write(dpsQueue.front().getPlayerNum() + "\n");
                dpsQueue.dequeue();
            }

            // Determining the maxSizes of Roles
            if (maxDPSLength < dpsQueue.getSize()) {
                maxDPSLength = dpsQueue.getSize();
            }

            if (maxTankLength < tankQueue.getSize()) {
                maxTankLength = tankQueue.getSize();
            }

            if (maxHealLength < healerQueue.getSize()) {
                maxHealLength = healerQueue.getSize();
            }
        }

        finalDPSLength = dpsQueue.getSize();
        finalTankLength = tankQueue.getSize();
        finalHealerLength = healerQueue.getSize();

        if (dpsQueue.getSize() > 0) {
            while (dpsQueue.getSize() > 0) {
                int wait = simLength - dpsQueue.front().getArrivalTime() + 1;
                finalTotalDPSWait += wait;
                if (wait > finalMaxDPSWait) {
                    finalMaxDPSWait = wait;
                }
                dpsQueue.dequeue();

            }
        }
        if (tankQueue.getSize() > 0) {
            while (tankQueue.getSize() > 0) {
                int wait = simLength - tankQueue.front().getArrivalTime() + 1;
                finalTotalTankWait += wait;
                if (wait > finalMaxTankWait) {
                    finalMaxTankWait = wait;
                }
                tankQueue.dequeue();
            }
        }
        if (healerQueue.getSize() > 0) {
            while (healerQueue.getSize() > 0) {
                int wait = simLength - healerQueue.front().getArrivalTime() + 1;
                finalTotalHealerWait += wait;
                if (wait > finalMaxHealerWait) {
                    finalMaxHealerWait = wait;
                }
                healerQueue.dequeue();
            }
        }

        Double averageFinalDPSWait = finalDPSLength == 0 ? Double.NaN : finalTotalDPSWait / finalDPSLength;
        Double averageFinalHealerWait = finalHealerLength == 0 ? Double.NaN : finalTotalHealerWait / finalHealerLength;
        Double averageFinalTankWait = finalTankLength == 0 ? Double.NaN : finalTotalTankWait / finalTankLength;
        Double averageDPSWait = totalDPSWait / (3 * numInstancesStarted);

        DecimalFormat df = new DecimalFormat("#.0");

        // handle end of simulation stat printing
        reportFile.write("\n" + "Instances started: " + numInstancesStarted);

        reportFile.write("\n\n" + "Statistics for Players Who Started Instances" + "\n\n");
        reportFile.write("DPS: " + maxDPSLength + "\t" + maxDPSWait + "\t" + df.format(averageDPSWait) + '\n');
        reportFile.write("Tanks: " + maxTankLength + "\t" + maxTankWait + "\t"
                + df.format(totalTankWait / numInstancesStarted) + '\n');
        reportFile.write("Healers: " + maxHealLength + "\t" + maxHealWait + "\t"
                + df.format(totalHealWait / numInstancesStarted));

        reportFile.write("\n\n" + "All Players Average Wait Time: "
                + df.format((totalHealWait + totalTankWait + totalDPSWait) / (5 * numInstancesStarted)) + "\n\n");

        reportFile.write("Statistics for Players Remaining in Queue" + "\n\n");
        reportFile.write(
                "DPS: " + finalDPSLength + "\t" + finalMaxDPSWait + "\t" + df.format(averageFinalDPSWait) + '\n');
        reportFile.write(
                "Tanks: " + finalTankLength + "\t" + finalMaxTankWait + "\t" + df.format(averageFinalTankWait) + '\n');
        reportFile.write("Healers: " + finalHealerLength + "\t" + finalMaxHealerWait + "\t"
                + df.format(averageFinalHealerWait) + '\n');
    }

    /**
     * Cleans up after the simulation
     */
    public void cleanup() {
        reportFile.close();
    }

    // add other methods to handle portions of the simulation process here

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the name of the configuration file: ");
        String configFileName = keyboard.nextLine();

        Simulation theSim = new Simulation();
        theSim.setup(configFileName);
        theSim.runSimulation();
        theSim.cleanup();
    }

}
