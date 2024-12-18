/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cashreport;

/**
 *
 * @author johntor81
 */
public class DashboardKeuangan extends javax.swing.JFrame {

    /**
     * Creates new form reportGUI
     */
    public DashboardKeuangan() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dashboard_panel = new javax.swing.JPanel();
        side_bar = new javax.swing.JPanel();
        dashboard_button_side = new javax.swing.JButton();
        laporan_button_edit = new javax.swing.JButton();
        pendapatan_button_edit = new javax.swing.JButton();
        pengeluaran_button_edit = new javax.swing.JButton();
        main_content_dashboard = new javax.swing.JPanel();
        header_dashboard = new javax.swing.JLabel();
        laba_rugi_panel_dashboard = new javax.swing.JPanel();
        laba_rugi_text_field = new javax.swing.JTextField();
        laba_rugi_label = new javax.swing.JLabel();
        laba_rugi_icon = new javax.swing.JLabel();
        pendapatan_panel_dashboard = new javax.swing.JPanel();
        pendapatan_text_field = new javax.swing.JTextField();
        pendapatan_label = new javax.swing.JLabel();
        pendapatan_icon = new javax.swing.JLabel();
        pengeluaran_panel_dashboard = new javax.swing.JPanel();
        pengeluaran_text_field = new javax.swing.JTextField();
        pengeluaran_label = new javax.swing.JLabel();
        pengeluaran_icon = new javax.swing.JLabel();
        graph_chart_dashboard = new javax.swing.JPanel();
        header2 = new javax.swing.JLabel();
        diagram_chart_dashboard = new javax.swing.JPanel();
        header1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(202, 240, 248));

        side_bar.setBackground(new java.awt.Color(2, 62, 138));
        side_bar.setToolTipText("");

        dashboard_button_side.setBackground(new java.awt.Color(225, 229, 240));
        dashboard_button_side.setFont(new java.awt.Font("JetBrainsMono NF ExtraBold", 1, 18)); // NOI18N
        dashboard_button_side.setText("DASHBOARD");
        dashboard_button_side.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboard_button_sideActionPerformed(evt);
            }
        });

        laporan_button_edit.setBackground(new java.awt.Color(166, 179, 208));
        laporan_button_edit.setFont(new java.awt.Font("JetBrainsMono NF ExtraBold", 1, 18)); // NOI18N
        laporan_button_edit.setText("LAPORAN");
        laporan_button_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporan_button_editActionPerformed(evt);
            }
        });

        pendapatan_button_edit.setBackground(new java.awt.Color(166, 179, 208));
        pendapatan_button_edit.setFont(new java.awt.Font("JetBrainsMono NF ExtraBold", 1, 18)); // NOI18N
        pendapatan_button_edit.setText("PENDAPATAN");
        pendapatan_button_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pendapatan_button_editActionPerformed(evt);
            }
        });

        pengeluaran_button_edit.setBackground(new java.awt.Color(166, 179, 208));
        pengeluaran_button_edit.setFont(new java.awt.Font("JetBrainsMono NF ExtraBold", 1, 18)); // NOI18N
        pengeluaran_button_edit.setText("PENGELUARAN");
        pengeluaran_button_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pengeluaran_button_editActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout side_barLayout = new javax.swing.GroupLayout(side_bar);
        side_bar.setLayout(side_barLayout);
        side_barLayout.setHorizontalGroup(
            side_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(side_barLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(side_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pengeluaran_button_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pendapatan_button_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(laporan_button_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dashboard_button_side, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        side_barLayout.setVerticalGroup(
            side_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, side_barLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dashboard_button_side)
                .addGap(18, 18, 18)
                .addComponent(laporan_button_edit)
                .addGap(18, 18, 18)
                .addComponent(pendapatan_button_edit)
                .addGap(18, 18, 18)
                .addComponent(pengeluaran_button_edit)
                .addGap(122, 122, 122))
        );

        main_content_dashboard.setBackground(new java.awt.Color(202, 240, 248));

        header_dashboard.setFont(new java.awt.Font("JetBrainsMono NFM", 1, 36)); // NOI18N
        header_dashboard.setText("Dashboard Keuangan");

        laba_rugi_panel_dashboard.setBackground(new java.awt.Color(255, 209, 102));

        laba_rugi_text_field.setBackground(new java.awt.Color(255, 209, 102));
        laba_rugi_text_field.setFont(new java.awt.Font("JetBrainsMono NFP ExtraLight", 0, 17)); // NOI18N
        laba_rugi_text_field.setText("Rp. -");
        laba_rugi_text_field.setBorder(null);

        laba_rugi_label.setFont(new java.awt.Font("JetBrainsMono NFM Thin", 1, 17)); // NOI18N
        laba_rugi_label.setText("Laba-Bersih");

        laba_rugi_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cashreport/cashflow.png"))); // NOI18N

        javax.swing.GroupLayout laba_rugi_panel_dashboardLayout = new javax.swing.GroupLayout(laba_rugi_panel_dashboard);
        laba_rugi_panel_dashboard.setLayout(laba_rugi_panel_dashboardLayout);
        laba_rugi_panel_dashboardLayout.setHorizontalGroup(
            laba_rugi_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, laba_rugi_panel_dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(laba_rugi_icon)
                .addGap(18, 18, 18)
                .addGroup(laba_rugi_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(laba_rugi_panel_dashboardLayout.createSequentialGroup()
                        .addGap(0, 42, Short.MAX_VALUE)
                        .addComponent(laba_rugi_label))
                    .addComponent(laba_rugi_text_field))
                .addGap(19, 19, 19))
        );
        laba_rugi_panel_dashboardLayout.setVerticalGroup(
            laba_rugi_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laba_rugi_panel_dashboardLayout.createSequentialGroup()
                .addGroup(laba_rugi_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(laba_rugi_panel_dashboardLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(laba_rugi_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(laba_rugi_text_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(laba_rugi_panel_dashboardLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(laba_rugi_icon)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pendapatan_panel_dashboard.setBackground(new java.awt.Color(6, 214, 160));

        pendapatan_text_field.setBackground(new java.awt.Color(6, 214, 160));
        pendapatan_text_field.setFont(new java.awt.Font("JetBrainsMono NFP ExtraLight", 0, 17)); // NOI18N
        pendapatan_text_field.setText("Rp. -");
        pendapatan_text_field.setBorder(null);

        pendapatan_label.setFont(new java.awt.Font("JetBrainsMono NFM Thin", 1, 17)); // NOI18N
        pendapatan_label.setText("Pendapatan");

        pendapatan_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cashreport/cashin.png"))); // NOI18N

        javax.swing.GroupLayout pendapatan_panel_dashboardLayout = new javax.swing.GroupLayout(pendapatan_panel_dashboard);
        pendapatan_panel_dashboard.setLayout(pendapatan_panel_dashboardLayout);
        pendapatan_panel_dashboardLayout.setHorizontalGroup(
            pendapatan_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pendapatan_panel_dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pendapatan_icon)
                .addGap(18, 18, 18)
                .addGroup(pendapatan_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pendapatan_panel_dashboardLayout.createSequentialGroup()
                        .addGap(0, 32, Short.MAX_VALUE)
                        .addComponent(pendapatan_label))
                    .addComponent(pendapatan_text_field))
                .addGap(19, 19, 19))
        );
        pendapatan_panel_dashboardLayout.setVerticalGroup(
            pendapatan_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendapatan_panel_dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pendapatan_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pendapatan_text_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pendapatan_panel_dashboardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pendapatan_icon)
                .addGap(15, 15, 15))
        );

        pengeluaran_panel_dashboard.setBackground(new java.awt.Color(239, 71, 111));

        pengeluaran_text_field.setBackground(new java.awt.Color(239, 71, 111));
        pengeluaran_text_field.setFont(new java.awt.Font("JetBrainsMono NFP ExtraLight", 0, 17)); // NOI18N
        pengeluaran_text_field.setText("Rp. -");
        pengeluaran_text_field.setBorder(null);

        pengeluaran_label.setFont(new java.awt.Font("JetBrainsMono NFM Thin", 1, 17)); // NOI18N
        pengeluaran_label.setText("Pengeluaran");

        pengeluaran_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cashreport/cashout.png"))); // NOI18N

        javax.swing.GroupLayout pengeluaran_panel_dashboardLayout = new javax.swing.GroupLayout(pengeluaran_panel_dashboard);
        pengeluaran_panel_dashboard.setLayout(pengeluaran_panel_dashboardLayout);
        pengeluaran_panel_dashboardLayout.setHorizontalGroup(
            pengeluaran_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pengeluaran_panel_dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pengeluaran_icon)
                .addGap(18, 18, 18)
                .addGroup(pengeluaran_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pengeluaran_text_field)
                    .addGroup(pengeluaran_panel_dashboardLayout.createSequentialGroup()
                        .addGap(0, 21, Short.MAX_VALUE)
                        .addComponent(pengeluaran_label)))
                .addGap(19, 19, 19))
        );
        pengeluaran_panel_dashboardLayout.setVerticalGroup(
            pengeluaran_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pengeluaran_panel_dashboardLayout.createSequentialGroup()
                .addGroup(pengeluaran_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pengeluaran_panel_dashboardLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pengeluaran_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pengeluaran_text_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pengeluaran_panel_dashboardLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(pengeluaran_icon)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        graph_chart_dashboard.setBackground(new java.awt.Color(255, 255, 255));

        header2.setFont(new java.awt.Font("JetBrainsMono NFM Light", 0, 18)); // NOI18N
        header2.setText("Grafik Keuangan");

        javax.swing.GroupLayout graph_chart_dashboardLayout = new javax.swing.GroupLayout(graph_chart_dashboard);
        graph_chart_dashboard.setLayout(graph_chart_dashboardLayout);
        graph_chart_dashboardLayout.setHorizontalGroup(
            graph_chart_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graph_chart_dashboardLayout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(header2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        graph_chart_dashboardLayout.setVerticalGroup(
            graph_chart_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graph_chart_dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header2)
                .addContainerGap(250, Short.MAX_VALUE))
        );

        diagram_chart_dashboard.setBackground(new java.awt.Color(255, 255, 255));

        header1.setFont(new java.awt.Font("JetBrainsMono NFM Light", 0, 18)); // NOI18N
        header1.setText("Diagram");

        javax.swing.GroupLayout diagram_chart_dashboardLayout = new javax.swing.GroupLayout(diagram_chart_dashboard);
        diagram_chart_dashboard.setLayout(diagram_chart_dashboardLayout);
        diagram_chart_dashboardLayout.setHorizontalGroup(
            diagram_chart_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, diagram_chart_dashboardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(header1)
                .addGap(70, 70, 70))
        );
        diagram_chart_dashboardLayout.setVerticalGroup(
            diagram_chart_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diagram_chart_dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout main_content_dashboardLayout = new javax.swing.GroupLayout(main_content_dashboard);
        main_content_dashboard.setLayout(main_content_dashboardLayout);
        main_content_dashboardLayout.setHorizontalGroup(
            main_content_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_content_dashboardLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(main_content_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(main_content_dashboardLayout.createSequentialGroup()
                        .addGroup(main_content_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(graph_chart_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(main_content_dashboardLayout.createSequentialGroup()
                                .addComponent(laba_rugi_panel_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pendapatan_panel_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(main_content_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pengeluaran_panel_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(diagram_chart_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(header_dashboard))
                .addGap(0, 54, Short.MAX_VALUE))
        );
        main_content_dashboardLayout.setVerticalGroup(
            main_content_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_content_dashboardLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(header_dashboard)
                .addGap(18, 18, 18)
                .addGroup(main_content_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_content_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pengeluaran_panel_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pendapatan_panel_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(laba_rugi_panel_dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(main_content_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(diagram_chart_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(graph_chart_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dashboard_panelLayout = new javax.swing.GroupLayout(dashboard_panel);
        dashboard_panel.setLayout(dashboard_panelLayout);
        dashboard_panelLayout.setHorizontalGroup(
            dashboard_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_panelLayout.createSequentialGroup()
                .addComponent(side_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(main_content_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashboard_panelLayout.setVerticalGroup(
            dashboard_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(side_bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(main_content_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboard_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboard_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboard_button_sideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard_button_sideActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dashboard_button_sideActionPerformed

    private void laporan_button_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporan_button_editActionPerformed
//        this.setVisible(false);
        new LaporanKeuangan().setVisible(true);
    }//GEN-LAST:event_laporan_button_editActionPerformed

    private void pendapatan_button_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pendapatan_button_editActionPerformed
        this.setVisible(false);
        new PendapatanKeuangan().setVisible(true);
    }//GEN-LAST:event_pendapatan_button_editActionPerformed

    private void pengeluaran_button_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pengeluaran_button_editActionPerformed
        this.setVisible(false);
        new PengeluaranKeuangan().setVisible(true);
    }//GEN-LAST:event_pengeluaran_button_editActionPerformed

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
            java.util.logging.Logger.getLogger(DashboardKeuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardKeuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardKeuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardKeuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardKeuangan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dashboard_button_side;
    private javax.swing.JPanel dashboard_panel;
    private javax.swing.JPanel diagram_chart_dashboard;
    private javax.swing.JPanel graph_chart_dashboard;
    private javax.swing.JLabel header1;
    private javax.swing.JLabel header2;
    private javax.swing.JLabel header_dashboard;
    private javax.swing.JLabel laba_rugi_icon;
    private javax.swing.JLabel laba_rugi_label;
    private javax.swing.JPanel laba_rugi_panel_dashboard;
    private javax.swing.JTextField laba_rugi_text_field;
    private javax.swing.JButton laporan_button_edit;
    private javax.swing.JPanel main_content_dashboard;
    private javax.swing.JButton pendapatan_button_edit;
    private javax.swing.JLabel pendapatan_icon;
    private javax.swing.JLabel pendapatan_label;
    private javax.swing.JPanel pendapatan_panel_dashboard;
    private javax.swing.JTextField pendapatan_text_field;
    private javax.swing.JButton pengeluaran_button_edit;
    private javax.swing.JLabel pengeluaran_icon;
    private javax.swing.JLabel pengeluaran_label;
    private javax.swing.JPanel pengeluaran_panel_dashboard;
    private javax.swing.JTextField pengeluaran_text_field;
    private javax.swing.JPanel side_bar;
    // End of variables declaration//GEN-END:variables
}
