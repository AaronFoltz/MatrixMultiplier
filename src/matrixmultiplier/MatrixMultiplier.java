package matrixmultiplier;

/**
 *
 * @author aaron
 */

public class MatrixMultiplier {
    public static int[][] A = {{1,2,3},{4,5,6},{7,8,9}}; //3x3
    public static int[][] B = {{1,2,3,4,5}, {6,7,8,9,10}, {11,12,13,14,15}}; //3x5
    public static int[][] C = new int[A.length][B[0].length]; // Init C based on length of other 2

    public static void main(String[] args) {
        int count =0;   // Counter to count the number of threads
        for(int x=0;x<A.length;x++){ //for loop for the rows of C
            for(int y=0;y<B[0].length;y++){ //for loop for the cols of C
                count++;
                String threads = "thread".concat(Integer.toString(count));  //Concatenates thread count to end of thread
                Thread thread = new Thread(new WorkerThread(x,y,A,B,C),threads); //Creates new thread with new runnable worker
                thread.start(); //Starts the run method of the runnable worker
                try {
                    thread.join(); //Joins the main thread to every other thread
                } catch (InterruptedException ex) {}
            }
        }
        for(int x=0;x<A.length;x++){ //for loop for the rows of C
            for(int y=0;y<B[0].length;y++){ //for loop for the cols of C
                System.out.printf("%5d"+"\t",C[x][y]); //Prints numbers in width =5.
            }
            System.out.println("");
        }
    }
}
class WorkerThread implements Runnable
{
    private int row;
    private int col;
    private int[][] A;
    private int[][] B;
    private int[][] C;

    public WorkerThread(int row, int col, int [][] A, int [][] B, int [][] C) {
        this.row =row;
        this.col=col;
        this.A=A;
        this.B=B;
        this.C=C;
    }
    public void run(){
        
        int total=0;    // Keeps a running tabulation of the total for that entry in C
        for(int y=0;y<(A[row].length);y++){ // For loop, keeping the row invariant and looping until at the last entry
            total+= A[row][y] *B[y][col];   //Total calculated from A and B entries
        }
        C[row][col]=total;  // Change the entry in C to correlate with the multiplication
    }
}

