/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.quizapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author steve
 */
public class Resolver extends javax.swing.JFrame {

    int numero_pregunta;
    int numero_respuesta;
    String correct_ans;
    String codigo = get_id();
    String usuario = get_user();
    
    private void get_qst(){
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "SELECT * FROM pregunta;";
        String sql2 = "SELECT * FROM respuestas;";
        
       try {
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql); 
           
           numero_pregunta = rs.getInt("numero_pregunta");
           String pregunta = rs.getString("texto_pregunta");
           correct_ans = rs.getString("respuesta_correcta");
           
           rs = stmt.executeQuery(sql2);
           
           numero_respuesta = rs.getInt("numero_pregunta");
           String opcion1 = rs.getString("opcion1");
           String opcion2 = rs.getString("opcion2");
           String opcion3 = rs.getString("opcion3");
           
           this.jTextArea2.setText(pregunta);
           this.jTextField1.setText(opcion1);
           this.jTextField2.setText(opcion2);
           this.jTextField3.setText(opcion3);
           
           conn.close();
       } catch (SQLException e) { 
           System.out.println(e.getMessage());
       }
       
    }
    
    private void next_qst(){
        
        numero_pregunta = numero_pregunta + 1;
        numero_respuesta = numero_respuesta + 1;
        
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql1 = "SELECT * FROM pregunta WHERE numero_pregunta = '" + numero_pregunta + "';";
        String sql2 = "SELECT * FROM respuestas WHERE numero_pregunta = '" + numero_pregunta + "';;";
        
       try {
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql1); 
           
           numero_pregunta = rs.getInt("numero_pregunta");
           String pregunta = rs.getString("texto_pregunta");
           correct_ans = rs.getString("respuesta_correcta");
           
           rs = stmt.executeQuery(sql2);
           
           numero_respuesta = rs.getInt("numero_pregunta");
           String opcion1 = rs.getString("opcion1");
           String opcion2 = rs.getString("opcion2");
           String opcion3 = rs.getString("opcion3");
           
           this.jTextArea2.setText(pregunta);
           this.jTextField1.setText(opcion1);
           this.jTextField2.setText(opcion2);
           this.jTextField3.setText(opcion3);
           
           conn.close();
           
       } catch (SQLException e) { 
           System.out.println(e.getMessage());
       }
       
    }
    
    private String get_id(){
        String sqlcodigo = null;
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "SELECT id FROM temp_user;";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            sqlcodigo = rs.getString("id");
            
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sqlcodigo;
    }
    
    private String get_user(){
        String sqlnombre = null;
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "SELECT nombre FROM temp_user;";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            sqlnombre = rs.getString("nombre");
            
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sqlnombre;
    }
    
    private int get_suma(){
        int x = 0;
        
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "SELECT is_correct FROM cuestionario;";
        
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                int temp = rs.getInt("is_correct");
                x = x + temp;
            }
            conn.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return x;
        
    }
    
    private boolean last_qst(){
        boolean x = false;
        
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "SELECT numero_pregunta FROM pregunta WHERE numero_pregunta = (SELECT MAX(numero_pregunta) FROM pregunta);";
        
        try {
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql); 
           int sqlcodigo = rs.getInt("numero_pregunta");
           
           x = numero_pregunta == sqlcodigo;
           
           conn.close();
        } catch (SQLException e) { 
           System.out.println(e.getMessage());
        }
        
        return x;
    }
    
    /*private void set_suma(){
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "INSERT INTO cuestionario VALUES (?, 0, '" + get_suma() + "');";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private String get_ncorrect(){
        String x = null;
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "SELECT * FROM cuestionario WHERE suma = (SELECT MAX(suma) FROM cuestionario);";
        
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            set_suma();
            x= rs.getString("suma");
            conn.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return x;
    } */
    
    private void set_result(){
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "INSERT INTO resultado VALUES(?, '" + codigo + "', '" + usuario + "', " + get_suma() + ");";
        
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private String get_result(){
        String x = null;
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "SELECT * FROM resultado WHERE n = (SELECT MAX(n) FROM resultado);";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String id = rs.getString("id");
            String nombre = rs.getString("nombre");
            float result = rs.getInt(4);
            float y = (result / n_ans()) * 100;
            String resultado = String.format("%.2f", y) + "%";
            x = "'" + id + "', '" + nombre + "', '" + resultado + "'";
            conn.close();
        } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        return x;
    }
    
    public float n_ans(){
        float x = 0;
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "SELECT COUNT(*) FROM pregunta;";
        
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            x = rs.getInt(1);
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
          
        return x;
    }
    
    
    private void set_table(){
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "INSERT INTO result_final VALUES (" + get_result() + ");";
        
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            conn.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    private void temp_result(){
        boolean x;
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql;
        
        x = correct_ans == null ? get_ans() == null : correct_ans.equals(get_ans());
        
        if (x == true){
            sql = "INSERT INTO cuestionario VALUES (?, 1, ?);";
        } else {
            sql = "INSERT INTO cuestionario VALUES (?, 0, ?);";
        }
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    private String get_ans(){
        String x = null;
        
        if(this.jRadioButton1.isSelected()){
            x = this.jTextField1.getText();
        }
        if(this.jRadioButton2.isSelected()){
            x = this.jTextField2.getText();
        }
        if(this.jRadioButton3.isSelected()){
            x = this.jTextField3.getText();
        }
        
        return x;
    }
    
    private void erase_tempresult(){
        conexion instancia = new conexion();
        Connection conn = instancia.con();
        String sql = "DELETE FROM cuestionario;";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());            
        }
    }
    
    
    
    /**
     * Creates new form Resolver
     */
    public Resolver() {
        initComponents();
        this.setLocationRelativeTo(this);
        this.get_qst();
        this.jLabel1.setFont(new Menu().titulo);
        this.jLabel2.setFont(new Menu().texto);
        this.jLabel3.setFont(new Menu().texto);
        this.jLabel4.setFont(new Menu().texto);
        this.jButton1.setFont(new Menu().boton);
        this.jButton3.setFont(new Menu().boton);
        this.jButton4.setFont(new Menu().boton);
        erase_tempresult();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(238, 238, 231));

        jLabel1.setText("Cuestionario");

        buttonGroup1.add(jRadioButton1);

        buttonGroup1.add(jRadioButton2);

        buttonGroup1.add(jRadioButton3);

        jButton3.setText("Salir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Siguiente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("Entregar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setText("Opción 1:");

        jLabel3.setText("Opción 2:");

        jLabel4.setText("Opción 3:");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton4))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jRadioButton2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jRadioButton1)
                                            .addGap(18, 18, 18)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jRadioButton3)
                                            .addGap(18, 18, 18)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(408, 408, 408)
                                .addComponent(jLabel1)))
                        .addGap(0, 41, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jRadioButton2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(jLabel4))
                    .addComponent(jScrollPane2))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton4))
                        .addGap(60, 60, 60))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jRadioButton3)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new Menu().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(last_qst() == true){
            JOptionPane.showMessageDialog(rootPane, "Esta es la ultima pregunta");
        } else {
            if (this.jRadioButton1.isSelected() || this.jRadioButton2.isSelected() || this.jRadioButton3.isSelected()){
            get_ans();
            temp_result();
            this.jTextArea2.setText("");
            this.jTextField1.setText("");
            this.jTextField2.setText("");
            this.jTextField3.setText("");
            next_qst();
            this.buttonGroup1.clearSelection();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Elija una opcion");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(last_qst() == true && (this.jRadioButton1.isSelected() || this.jRadioButton2.isSelected() || this.jRadioButton3.isSelected())){
            JOptionPane.showMessageDialog(rootPane, "Buen trabajo");
            get_ans();
            temp_result();
            set_result();
            get_result();
            set_table();
            new Menu().setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Aun no se ha completado el cuestionario");
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Resolver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Resolver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Resolver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Resolver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Resolver().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
