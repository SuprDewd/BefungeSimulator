package befungesimulator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;

public class Befunge {

    public enum Direction {
        RIGHT(0),
        DOWN(1),
        LEFT(2),
        UP(3);

        private final int[] dy = new int[] {0, 1, 0, -1};
        private final int[] dx = new int[] {1, 0, -1, 0};

        private int dir;
        Direction(int dir) {
            this.dir = dir;
        }

        public int getDY() {
            return this.dy[this.dir];
        }

        public int getDX() {
            return this.dx[this.dir];
        }
    }


    public static class Value {

        public enum Type { INT, BOOL, CHAR, IDK }

        private long value;
        private Type type;

        public Value(long value, Type type) {
            this.value = value;
            this.type = type;
        }

        public long getValue() {
            return this.value;
        }

        public Type getType() {
            return this.type;
        }

        @Override
        public String toString() {
            if (this.type == Type.CHAR) {
                return Character.toString((char)(int)this.value);
            } else if (this.type == Type.INT) {
                return Long.toString(value);
            } else if (this.type == Type.BOOL) {
                return this.value == 0 ? "false" : this.value == 1 ? "true" : (this.value == 0 ? "false" : "true") + " (" + Long.toString(this.value) + ")";
            } else {
                return Character.toString((char)(int)this.value) + " (" + Long.toString(value) + ")";
            }
        }
    }

    public static final int HEIGHT = 10;
    public static final int WIDTH = 90;

    private int pc_y;
    private int pc_x;
    private Direction pc_dir;
    private boolean string_mode;
    private LinkedList<Value> stack;
    private Value[][] board;
    private Random rand;

    public Befunge() {
        this.rand = new Random();
        this.clear();
    }

    public void reset() {
        this.pc_y = this.pc_x = 0;
        this.pc_dir = Direction.RIGHT;
        this.stack = new LinkedList<Value>();
    }

    public void clear() {
        this.reset();
        this.board = new Value[Befunge.HEIGHT][Befunge.WIDTH];
        for (int i = 0; i < Befunge.HEIGHT; i++) {
            for (int j = 0; j < Befunge.WIDTH; j++) {
                this.board[i][j] = new Value((int)' ', Value.Type.CHAR);
            }
        }
    }

    public int getPCY() {
        return this.pc_y;
    }

    public int getPCX() {
        return this.pc_x;
    }

    public Direction getPCDirection() {
        return this.pc_dir;
    }

    public boolean getStringMode() {
        return this.string_mode;
    }

    public Iterable<Value> getStack() {
        return this.stack;
    }

    public Value get(int y, int x) {
        return this.board[y][x];
    }

    public void set(int y, int x, Value value) {
        this.board[y][x] = value;
    }

    private void push(Value value) {
        this.stack.push(value);
    }

    private Value pop() throws Exception {
        if (this.stack.isEmpty()) {
            // TODO: Throw appropriate exception.
            throw new Exception();
        }

        Value res = this.stack.peek();
        this.stack.pop();
        return res;
    }

    public boolean step(PrintWriter out, Scanner in) throws Exception {
        Value cur = this.board[this.pc_y][this.pc_x];

        if (cur.type != Value.Type.CHAR) {
            // TODO: What happens when PC enters a cell with an integer value?
            throw new Exception();
        }

        char curc = (char)(int)cur.getValue();
        int jumplen = 1;
        boolean done = false;

        if (this.string_mode) {
            if (curc == '"') {
                this.string_mode = false;
            } else {
                this.stack.push(cur);
            }
        } else {
            Value a, b, x, y;
            switch (curc) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    this.push(new Value((int)(curc - '0'), Value.Type.INT));
                break;
                case '"':
                    this.string_mode = true;
                break;
                case '+':
                    b = this.pop();
                    a = this.pop();
                    this.push(new Value(a.getValue() + b.getValue(), Value.Type.INT));
                    break;
                case '-':
                    b = this.pop();
                    a = this.pop();
                    this.push(new Value(a.getValue() - b.getValue(), Value.Type.INT));
                    break;
                case '*':
                    b = this.pop();
                    a = this.pop();
                    this.push(new Value(a.getValue() * b.getValue(), Value.Type.INT));
                    break;
                case '/':
                    b = this.pop();
                    a = this.pop();

                    if (b.getValue() == 0) {
                        // TODO: Throw appropriate exception.
                        throw new Exception();
                    }

                    this.push(new Value(a.getValue() / b.getValue(), Value.Type.INT));
                    break;
                case '%':
                    b = this.pop();
                    a = this.pop();

                    if (b.getValue() == 0) {
                        // TODO: Throw appropriate exception.
                        throw new Exception();
                    }

                    this.push(new Value(a.getValue() % b.getValue(), Value.Type.INT));
                    break;
                case '!':
                    a = this.pop();
                    this.push(new Value(a.getValue() == 0 ? 1 : 0, Value.Type.BOOL));
                    break;
                case '`':
                    b = this.pop();
                    a = this.pop();
                    this.push(new Value(a.getValue() > b.getValue() ? 1 : 0, Value.Type.INT));
                    break;
                case '>':
                    this.pc_dir = Direction.RIGHT;
                    break;
                case '<':
                    this.pc_dir = Direction.LEFT;
                    break;
                case '^':
                    this.pc_dir = Direction.UP;
                    break;
                case 'v':
                    this.pc_dir = Direction.DOWN;
                    break;
                case '?':
                    int r = this.rand.nextInt(4);
                    this.pc_dir = r == 0 ? Direction.RIGHT :
                                  r == 1 ? Direction.LEFT :
                                  r == 2 ? Direction.UP :
                                           Direction.DOWN;
                    break;
                case '_':
                    a = this.pop();
                    this.pc_dir = a.getValue() == 0 ? Direction.RIGHT :
                                                      Direction.LEFT;
                    break;
                case '|':
                    a = this.pop();
                    this.pc_dir = a.getValue() == 0 ? Direction.DOWN :
                                                      Direction.UP;
                    break;
                case ':':
                    a = this.pop();
                    this.push(a);
                    this.push(a);
                    break;
                case '\\':
                    b = this.pop();
                    a = this.pop();
                    this.push(b);
                    this.push(a);
                    break;
                case '$':
                    this.pop();
                    break;
                case '.':
                    a = this.pop();
                    // TODO: Output value as integer.
                    out.println("OUT: " + Long.toString(a.getValue()));
                    break;
                case ',':
                    a = this.pop();
                    // TODO: Output value as ASCII character.
                    out.println("OUT: " + Character.toString((char)a.getValue()));
                    break;
                case '#':
                    jumplen = 2;
                    break;
                case 'g':
                    y = this.pop();
                    x = this.pop();

                    if (y.getValue() < 0 || y.getValue() >= Befunge.HEIGHT || x.getValue() < 0 || x.getValue() >= Befunge.WIDTH) {
                        // TODO: Throw appropriate exception.
                        throw new Exception();
                    }

                    this.push(this.board[(int)y.getValue()][(int)x.getValue()]);
                    break;
                case 'p':
                    y = this.pop();
                    x = this.pop();
                    a = this.pop();

                    if (y.getValue() < 0 || y.getValue() >= Befunge.HEIGHT || x.getValue() < 0 || x.getValue() >= Befunge.WIDTH) {
                        // TODO: Throw appropriate exception.
                        throw new Exception();
                    }

                    this.board[(int)y.getValue()][(int)x.getValue()] = a;
                    break;
                case '&':
                    // TODO: Input value as integer.
                    out.print("IN: ");
                    out.flush();
                    this.push(new Value(in.nextLong(), Value.Type.INT));
                    break;
                case '~':
                    // TODO: Input value as ASCII character.
                    out.print("IN: ");
                    out.flush();
                    this.push(new Value((char)System.in.read(), Value.Type.CHAR));
                    break;
                case '@':
                    done = true;
                    break;
            }
        }

        if (done) {
            jumplen = 0;
        }

        for (int i = 0; i < jumplen; i++) {
            this.pc_y = (this.pc_y + this.pc_dir.getDY() + Befunge.HEIGHT) % Befunge.HEIGHT;
            this.pc_x = (this.pc_x + this.pc_dir.getDX() + Befunge.WIDTH) % Befunge.WIDTH;
        }

        return !done;
    }
}
