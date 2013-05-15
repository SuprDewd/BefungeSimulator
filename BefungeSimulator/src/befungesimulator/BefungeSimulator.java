package befungesimulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class BefungeSimulator {
    public static void main(String[] args) throws Exception {
        // TODO: Add a GUI.
        
        Befunge befunge = new Befunge();
        
        PrintWriter out = new PrintWriter(System.out, true);
        Scanner in = new Scanner(System.in);
        
        boolean display = false;
        boolean colors = true;
        String normalbg = "\033[47m";
        String curbg = "\033[41m";
        
        String file = "code.befunge";
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int y = 0, x = 0;
        
        while ((line = br.readLine()) != null) {
            for (char c : line.toCharArray()) {
                befunge.set(y, x, new Befunge.Value((long)c, Befunge.Value.Type.CHAR));
                x++;
            }
            
            y++; x = 0;
        }
        
        br.close();
        
        boolean done = false;
        while (true) {
            if (display) {
                for (int i = 0; i < Befunge.HEIGHT; i++) {
                    for (int j = 0; j < Befunge.WIDTH; j++) {
                        if (colors) {
                            out.print(i == befunge.getPCY() && j == befunge.getPCX() ? curbg : normalbg);
                        }

                        out.print(befunge.get(i, j).toString());
                    }

                    out.println();
                }
                
                out.print("STACK: ");
                boolean first = true;
                for (Befunge.Value v : befunge.getStack()) {
                    if (first) first = false;
                    else out.print(", ");
                    out.print(v.toString());
                }
                
                out.println();
            }
            
            if (done) {
                break;
            }
            
            if (display) in.nextLine();
            done = !befunge.step(out, in);
        }
        
        out.flush();
    }
}
