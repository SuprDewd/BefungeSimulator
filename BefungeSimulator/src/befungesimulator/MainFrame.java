/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package befungesimulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author suprdewd
 */
public class MainFrame extends javax.swing.JFrame {

    private final Befunge befunge;
    private JPanel[][] boardCell = new JPanel[Befunge.HEIGHT][Befunge.WIDTH];
    private Border boardCellBorder = BorderFactory.createLineBorder(Color.lightGray, 1);
    private Border boardCellBorderHighlighted = BorderFactory.createLineBorder(Color.blue, 3);
    private Border boardCellBorderCursor = BorderFactory.createLineBorder(Color.red, 3);
    private int editY = 0;
    private int editX = 0;
    private StringInputReader befungeIn;
    private boolean isRunning = false;
    private Befunge.Value[][] tmpBoard = new Befunge.Value[Befunge.HEIGHT][Befunge.WIDTH];
    private String lastSaveFile = null;
    
    private static class StringInputReader implements Befunge.InputReader {
        
        private int at = 0;
        private String s;
        
        public StringInputReader(String s) {
            this.s = s;
        }
        
        @Override
        public long nextLong() {
            long res = 0;
            while (!Character.isDigit(s.charAt(at))) at++;
            while (at < s.length() && Character.isDigit(s.charAt(at))) {
                res = res * 10 + (s.charAt(at) - '0');
                at++;
            }
            
            return res;
        }
        
        @Override
        public char nextChar() {
            return this.s.charAt(at++);
        }
    }

    /**
     * Creates new form MainFrame
     */
    public MainFrame(Befunge befunge) {
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.befunge = befunge;
        initComponents();
        this.boardGrid.setLayout(new GridLayout(Befunge.HEIGHT, Befunge.WIDTH));
        //this.boardGrid.setSize(40 * Befunge.WIDTH, 40 * Befunge.HEIGHT);
        
        for (int y = 0; y < Befunge.HEIGHT; y++) {
            for (int x = 0; x < Befunge.WIDTH; x++) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                panel.setBorder(this.boardCellBorder);
                panel.setBackground(Color.white);
                
                JLabel label = new JLabel();
                label.setFont(new Font("SansSerif", Font.BOLD, 16));
                
                panel.add(label);
                
                final int cy = y, cx = x;
                panel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {
                        focusBoardCell(cy, cx);
                        requestFocus();
                    }
                });
                this.boardCell[y][x] = panel;
                this.boardGrid.add(this.boardCell[y][x]);
                panel.setFocusable(true);
                label.setFocusable(true);
            }
        }
        
        this.focusBoardCell(0, 0);
        this.requestFocus();
    }
    
    private void focusBoardCell(int y, int x) {
        if (!this.isRunning) {
            this.boardCell[this.editY][this.editX].setBorder(this.boardCellBorder);

            if (x < 0) {
                y--;
                x = Befunge.WIDTH - 1;
            }

            if (x >= Befunge.WIDTH) {
                y++;
                x = 0;
            }

            if (y < 0) {
                y = Befunge.HEIGHT - 1;
            }

            if (y >= Befunge.HEIGHT) {
                y = 0;
            }

            this.editY = y;
            this.editX = x;

            this.boardCell[this.editY][this.editX].setBorder(this.boardCellBorderHighlighted);
        }
    }
    
    private void setBoardCellValue(int y, int x, Befunge.Value val) {
        this.befunge.set(y, x, val);
        ((JLabel)this.boardCell[y][x].getComponent(0)).setText(val.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem3 = new javax.swing.JMenuItem();
        boardGrid = new javax.swing.JPanel();
        DebugControls = new javax.swing.JPanel();
        lblStack = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtInput = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtOutput = new javax.swing.JTextArea();
        btnStartReset = new javax.swing.JButton();
        btnStep = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        boardGrid.setBackground(new java.awt.Color(204, 204, 204));
        boardGrid.setToolTipText("");
        boardGrid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                boardGridKeyReleased(evt);
            }
        });
        boardGrid.setLayout(new java.awt.GridLayout(1, 0));
        getContentPane().add(boardGrid, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1500, 600));

        DebugControls.setFocusable(false);
        DebugControls.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                DebugControlsKeyReleased(evt);
            }
        });
        DebugControls.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblStack.setText("Stack:");
        lblStack.setFocusable(false);
        DebugControls.add(lblStack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jScrollPane1.setFocusable(false);

        txtInput.setColumns(20);
        txtInput.setRows(5);
        txtInput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtInputMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtInput);

        DebugControls.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel1.setText("Input");
        jLabel1.setFocusable(false);
        DebugControls.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel2.setText("Output");
        jLabel2.setFocusable(false);
        DebugControls.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        txtOutput.setEditable(false);
        txtOutput.setColumns(20);
        txtOutput.setRows(5);
        txtOutput.setFocusable(false);
        jScrollPane2.setViewportView(txtOutput);

        DebugControls.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        btnStartReset.setText("Start");
        btnStartReset.setFocusable(false);
        btnStartReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartResetActionPerformed(evt);
            }
        });
        DebugControls.add(btnStartReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        btnStep.setText("Step");
        btnStep.setEnabled(false);
        btnStep.setFocusable(false);
        btnStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStepActionPerformed(evt);
            }
        });
        DebugControls.add(btnStep, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, -1, -1));

        getContentPane().add(DebugControls, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 610, 590, 350));

        jMenu1.setText("File");

        jMenuItem1.setText("Clear");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Open File...");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setText("Save");
        jMenuItem4.setEnabled(false);
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setText("Save As...");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("Exit");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped

    }//GEN-LAST:event_formKeyTyped

    private static boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
        return (!Character.isISOControl(c)) &&
               c != KeyEvent.CHAR_UNDEFINED &&
               block != null &&
               block != Character.UnicodeBlock.SPECIALS;
    }
    
    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if (!this.isRunning) {
            switch (evt.getExtendedKeyCode()) {
                case KeyEvent.VK_ENTER:
                    this.focusBoardCell(this.editY+1, 0);
                    break;
                case KeyEvent.VK_LEFT:
                    this.focusBoardCell(this.editY, this.editX-1);
                    break;
                case KeyEvent.VK_RIGHT:
                    this.focusBoardCell(this.editY, this.editX+1);
                    break;
                case KeyEvent.VK_DOWN:
                    this.focusBoardCell(this.editY+1, this.editX);
                    break;
                case KeyEvent.VK_UP:
                    this.focusBoardCell(this.editY-1, this.editX);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    this.focusBoardCell(this.editY, this.editX-1);
                    this.setBoardCellValue(this.editY, this.editX, new Befunge.Value(' ', Befunge.Value.Type.CHAR));
                    break;
                case KeyEvent.VK_DELETE:
                    this.setBoardCellValue(this.editY, this.editX, new Befunge.Value(' ', Befunge.Value.Type.CHAR));
                    break;
                default:
                    if (isPrintableChar(evt.getKeyChar()) || Character.isSpaceChar(evt.getKeyChar())) {
                        this.setBoardCellValue(this.editY, this.editX, new Befunge.Value(evt.getKeyChar(), Befunge.Value.Type.CHAR));
                        this.focusBoardCell(this.editY, this.editX+1);
                    }
            }
        } else {
            if (evt.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
                btnStepActionPerformed(null);
            }
        }
    }//GEN-LAST:event_formKeyReleased

    private void btnStartResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartResetActionPerformed
        if (this.isRunning) {
            this.isRunning = false;
            this.boardCell[this.befunge.getPCY()][this.befunge.getPCX()].setBorder(this.boardCellBorder);
            this.focusBoardCell(this.editY, this.editX);
            this.btnStartReset.setText("Start");
            this.btnStep.setEnabled(false);
            this.txtInput.setEditable(true);
            this.befunge.reset();
            this.lblStack.setText("Stack:");
            this.txtOutput.setText("");
            for (int i = 0; i < Befunge.HEIGHT; i++) {
                for (int j = 0; j < Befunge.WIDTH; j++) {
                    this.setBoardCellValue(i, j, this.tmpBoard[i][j]);
                }
            }
            
            this.jMenuItem1.setEnabled(true);
            this.jMenuItem2.setEnabled(true);
            if (this.lastSaveFile != null) this.jMenuItem4.setEnabled(true);
            this.jMenuItem5.setEnabled(true);
        } else {
            this.boardCell[this.editY][this.editX].setBorder(this.boardCellBorder);
            this.boardCell[this.befunge.getPCY()][this.befunge.getPCX()].setBorder(this.boardCellBorderCursor);
            this.btnStartReset.setText("Reset");
            this.btnStep.setEnabled(true);
            this.txtInput.setEditable(false);
            this.befungeIn = new StringInputReader(this.txtInput.getText());
            for (int i = 0; i < Befunge.HEIGHT; i++) {
                for (int j = 0; j < Befunge.WIDTH; j++) {
                    this.tmpBoard[i][j] = this.befunge.get(i, j);
                }
            }
            
            this.isRunning = true;
            this.jMenuItem1.setEnabled(false);
            this.jMenuItem2.setEnabled(false);
            this.jMenuItem4.setEnabled(false);
            this.jMenuItem5.setEnabled(false);
            this.requestFocus();
        }
    }//GEN-LAST:event_btnStartResetActionPerformed

    private void btnStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStepActionPerformed
        if (!this.btnStep.isEnabled()) return;
        
        try {
            this.boardCell[this.befunge.getPCY()][this.befunge.getPCX()].setBorder(this.boardCellBorder);
            
            String res = this.befunge.step(this.befungeIn);
            if (res == null) {
                this.btnStep.setEnabled(false);
            } else {
                if (!res.equals("")) {
                    this.txtOutput.setText(this.txtOutput.getText() + res);
                }
                
                StringBuilder sb = new StringBuilder();
                sb.append("Stack:");
                boolean first = true;
                for (Befunge.Value val : this.befunge.getStack()) {
                    if (first) first = false;
                    else sb.append(",");
                    sb.append(" ");
                    sb.append(val.toString());
                }
                
                this.lblStack.setText(sb.toString());
                this.boardCell[this.befunge.getPCY()][this.befunge.getPCX()].setBorder(this.boardCellBorderCursor);
                
                for (int i = 0; i < Befunge.HEIGHT; i++) {
                    for (int j = 0; j < Befunge.WIDTH; j++) {
                        ((JLabel)this.boardCell[i][j].getComponent(0)).setText(this.befunge.get(i, j).toString());
                    }
                }
            }
        } catch (Exception ex) {
            this.btnStep.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Runtime error");
        }
    }//GEN-LAST:event_btnStepActionPerformed

    private void boardGridKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_boardGridKeyReleased
        this.formKeyReleased(evt);
    }//GEN-LAST:event_boardGridKeyReleased

    private void DebugControlsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DebugControlsKeyReleased
        this.formKeyReleased(evt);
    }//GEN-LAST:event_DebugControlsKeyReleased

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        this.requestFocus();
    }//GEN-LAST:event_formMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        for (int i = 0; i < Befunge.HEIGHT; i++) {
            for (int j = 0; j < Befunge.WIDTH; j++) {
                this.setBoardCellValue(i, j, new Befunge.Value(' ', Befunge.Value.Type.CHAR));
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private static String trimLeft(String s) {
        int i = s.length();
        while (i - 1 >= 0 && s.charAt(i-1) == ' ')  i--;
        return s.substring(0, i);
    }
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        if (this.lastSaveFile != null) {
            fc.setCurrentDirectory(new File(this.lastSaveFile));
        }
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            ArrayList<String> lines = new ArrayList<String>();

            String line;
            BufferedReader br = null;
            boolean ok = true;
            try {
                br = new BufferedReader(new FileReader(fc.getSelectedFile()));

                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file");
                ok = false;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file");
                ok = false;
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if (ok) {
                int mx = 0;
                for (int i = 0; i < lines.size(); i++) {
                    lines.set(i, trimLeft(lines.get(i)));
                    mx = Math.max(mx, lines.get(i).length());
                }
                
                ok = mx < Befunge.WIDTH;
            }
            
            if (ok) {
                int at = lines.size() - 1;
                while (at >= 0 && lines.get(at).equals("")) {
                    lines.remove(at);
                    at--;
                }
                
                ok = lines.size() < Befunge.HEIGHT;
            }
            
            if (ok) {
                for (int i = 0; i < Befunge.HEIGHT; i++) {
                    for (int j = 0; j < Befunge.WIDTH; j++) {
                        this.setBoardCellValue(i, j, new Befunge.Value(' ', Befunge.Value.Type.CHAR));
                    }
                }
                
                for (int i = 0; i < lines.size(); i++) {
                    String s = lines.get(i);
                    for (int j = 0; j < s.length(); j++) {
                        this.setBoardCellValue(i, j, new Befunge.Value(s.charAt(j), Befunge.Value.Type.CHAR));
                    }
                }
                
                this.lastSaveFile = fc.getSelectedFile().getAbsolutePath();
                this.jMenuItem4.setEnabled(true);
                this.focusBoardCell(0, 0);
            } else {
                JOptionPane.showMessageDialog(this, "Board in file too big (max rows: " + Befunge.HEIGHT + ", max columns: " + Befunge.WIDTH + ")");
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        if (this.lastSaveFile != null) {
            fc.setCurrentDirectory(new File(this.lastSaveFile));
        }
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.lastSaveFile = fc.getSelectedFile().getAbsolutePath();
            jMenuItem4ActionPerformed(null);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        BufferedWriter bw = null;
        
        try {
            File file = new File(this.lastSaveFile);
            if (file.exists()) {
                if (JOptionPane.showConfirmDialog(this, "File already exists. Do you want to overwrite it?") != JOptionPane.YES_OPTION) {
                    return;
                }
            } else {
                file.createNewFile();
            }
            
            ArrayList<String> lines = new ArrayList<String>();
            for (int i = 0; i < Befunge.HEIGHT; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < Befunge.WIDTH; j++) {
                    sb.append(Character.toString((char)(int)this.befunge.get(i, j).getValue()));
                }
                
                lines.add(sb.toString());
            }
            
            for (int i = 0; i < lines.size(); i++) {
                lines.set(i, trimLeft(lines.get(i)));
            }
            
            int at = lines.size() - 1;
            while (at >= 0 && lines.get(at).equals("")) {
                lines.remove(at);
                at--;
            }
            
            bw = new BufferedWriter(new FileWriter(this.lastSaveFile));
            for (String line : lines) {
                bw.write(line);
                bw.write("\n");
            }
            
            bw.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing file");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void txtInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtInputMouseClicked
        this.txtInput.requestFocus();
        this.txtInput.requestFocusInWindow();
        evt.consume();
    }//GEN-LAST:event_txtInputMouseClicked

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });*/
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DebugControls;
    private javax.swing.JPanel boardGrid;
    private javax.swing.JButton btnStartReset;
    private javax.swing.JButton btnStep;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblStack;
    private javax.swing.JTextArea txtInput;
    private javax.swing.JTextArea txtOutput;
    // End of variables declaration//GEN-END:variables
}
