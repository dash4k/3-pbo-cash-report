/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cashreport;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import static java.lang.Integer.parseInt;
import java.sql.*;
import java.util.Calendar;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author johntor81
 */
public class Keuangan extends javax.swing.JFrame {
    private DefaultTableModel model_pendapatan = null;
    private DefaultTableModel model_pengeluaran = null;
    private int selected_pendapatan_year = 0, selected_pendapatan_month = 0, selected_pengeluaran_year = 0, selected_pengeluaran_month = 0;
    String selected_starting_date = "", selected_ending_date = "";
    
    public void update_dashboard() {
        int laba_bersih = 0, pendapatan = 0, pengeluaran = 0;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        
        try {
            Connection c = KoneksiMySql.getKoneksi(); try (Statement s = c.createStatement()) {
                String sql = "SELECT nominal, jenis_pembayaran FROM transaksi";
                try (ResultSet r = s.executeQuery(sql)) {
                    while(r.next()) {
                        if ("pemasukan".equals(r.getString("jenis_pembayaran"))) {
                            laba_bersih += r.getInt("nominal");
                            pendapatan += r.getInt("nominal");
                        }
                        else {
                            laba_bersih -= r.getInt("nominal");
                            pengeluaran += r.getInt("nominal");
                        }
                    }
                }
            }
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
            DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
            String formatted_pendapatan = decimalFormat.format(pendapatan), formatted_pengeluaran = decimalFormat.format(pengeluaran), formatted_laba_bersih = decimalFormat.format(laba_bersih);
            laba_bersih_text_field.setText("Rp. " + String.valueOf(formatted_laba_bersih));
            pendapatan_text_field.setText("Rp. " + String.valueOf(formatted_pendapatan));
            pengeluaran_text_field.setText("Rp. " + String.valueOf(formatted_pengeluaran));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while processing the data.");
        }
        try {
            Connection c = KoneksiMySql.getKoneksi(); try (Statement s = c.createStatement()) {
                String sqll = """
                    SELECT tanggal,
                        SUM(CASE WHEN jenis_pembayaran = 'pemasukan' THEN nominal ELSE 0 END) AS total_pemasukan, 
                        SUM(CASE WHEN jenis_pembayaran = 'pengeluaran' THEN nominal ELSE 0 END) AS total_pengeluaran 
                    FROM transaksi
                    GROUP BY tanggal
                    ORDER BY tanggal
                """;
                try (ResultSet rl = s.executeQuery(sqll)) {
                    while (rl.next()) {
                        String tanggal = rl.getString("tanggal");
                        double totalPemasukan = rl.getDouble("total_pemasukan");
                        double totalPengeluaran = rl.getDouble("total_pengeluaran");
                        dataset.setValue(totalPemasukan, "Pendapatan", tanggal);
                        dataset.setValue(totalPengeluaran, "Pengeluaran", tanggal);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while processing the data.");
        }
        try {
            Connection c = KoneksiMySql.getKoneksi(); try (Statement s = c.createStatement()) {
                String sqlp = """
                    SELECT 
                        CASE 
                            WHEN jenis_pembayaran = 'pemasukan' THEN 'Pendapatan'
                            WHEN jenis_pembayaran = 'pengeluaran' THEN 'Pengeluaran'
                        END AS category,
                        SUM(nominal) AS total 
                    FROM transaksi 
                    GROUP BY category
                """;
                try (ResultSet rp = s.executeQuery(sqlp)) {
                    while (rp.next()) {
                        String category = rp.getString("category");
                        double total = rp.getDouble("total");
                        pieDataset.setValue(category, total);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while processing the data.");
        }
        
        JFreeChart lineChart = ChartFactory.createLineChart(
                "",  // Title
                "Date",                 // X-axis label
                "Amount",               // Y-axis label
                dataset,                // Dataset
                PlotOrientation.VERTICAL,  // Chart orientation
                true,                    // Include legend
                false,                    // Tooltips
                false                    // URLs
        );
        
        JFreeChart pieChart = ChartFactory.createPieChart(
            "",                   // Title
            pieDataset,           // Dataset
            true,                // Legend
            false,                 // Tooltips
            false                 // URLs
        );
        
        CategoryPlot lineCategoryPlot = lineChart.getCategoryPlot();
        lineCategoryPlot.setBackgroundPaint(Color.white);
        lineCategoryPlot.setOutlinePaint(null);
        
        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        piePlot.setBackgroundPaint(Color.white);
        piePlot.setLabelGenerator(null);
        piePlot.setOutlinePaint(null);
        
        CategoryAxis xAxis = lineCategoryPlot.getDomainAxis();
        xAxis.setTickLabelsVisible(false);
        xAxis.setAxisLineVisible(true);
        xAxis.setLabelPaint(Color.BLACK);

        ValueAxis yAxis = lineCategoryPlot.getRangeAxis();
        yAxis.setTickLabelsVisible(false);
        yAxis.setAxisLineVisible(true); 
        yAxis.setLabelPaint(Color.BLACK); 
        
        piePlot.setSectionPaint("Pendapatan", new Color(6,214,160));
        piePlot.setSectionPaint("Pengeluaran", new Color(239,71,111));

        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();

        lineRenderer.setSeriesPaint(0, new Color(6,214,160));
        lineRenderer.setSeriesPaint(1, new Color(239,71,111));
        
        ChartPanel lineChartPanel = new ChartPanel(lineChart);
        lineChartPanel.setPreferredSize(new Dimension(319, 285));
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(new Dimension(157, 41));
        line_chart_dashboard.setLayout(new BorderLayout());
        line_chart_dashboard.removeAll();
        line_chart_dashboard.add(lineChartPanel, BorderLayout.CENTER);
        line_chart_dashboard.validate();
        line_chart_dashboard.revalidate();
        line_chart_dashboard.repaint();
        pie_chart_dashboard.setLayout(new BorderLayout());
        pie_chart_dashboard.removeAll();
        pie_chart_dashboard.add(pieChartPanel, BorderLayout.CENTER);
        pie_chart_dashboard.validate();
        pie_chart_dashboard.revalidate();
        pie_chart_dashboard.repaint();
    }
    
    public void update_pendapatan() {
        model_pendapatan.getDataVector().removeAllElements();
        model_pendapatan.fireTableDataChanged();

        int selected_month = pendapatan_month.getSelectedIndex();
        String y = pendapatan_year.getText();
        int selected_year = parseInt(y);
      
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selected_year);
        calendar.set(Calendar.MONTH, selected_month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        java.sql.Date startDate = new java.sql.Date(calendar.getTimeInMillis());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        java.sql.Date endDate = new java.sql.Date(calendar.getTimeInMillis());

        String sql = "SELECT id_transaksi, nominal, tanggal, metode_pembayaran FROM transaksi WHERE jenis_pembayaran = 'pemasukan' AND tanggal BETWEEN ? AND ? ORDER BY tanggal DESC";

        try (Connection c = KoneksiMySql.getKoneksi(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setDate(1, startDate); 
            s.setDate(2, endDate); 

            try (ResultSet r = s.executeQuery()) {
                while (r.next()) {
                    Object[] o = new Object[4];
                    o[0] = r.getString("id_transaksi");
                    o[1] = r.getString("nominal");
                    o[2] = r.getString("tanggal");
                    o[3] = r.getString("metode_pembayaran");
                    model_pendapatan.addRow(o);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while processing the data.");
        }
    }

    public void update_pengeluaran() {
        model_pengeluaran.getDataVector().removeAllElements();
        
        model_pengeluaran.fireTableDataChanged();
        
        int selected_month = pengeluaran_month.getSelectedIndex();
        String y = pengeluaran_year.getText();
        int selected_year = parseInt(y);
      
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selected_year);
        calendar.set(Calendar.MONTH, selected_month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        java.sql.Date startDate = new java.sql.Date(calendar.getTimeInMillis());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        java.sql.Date endDate = new java.sql.Date(calendar.getTimeInMillis());

        String sql = "SELECT id_transaksi, nominal, tanggal, metode_pembayaran FROM transaksi WHERE jenis_pembayaran = 'pengeluaran' AND tanggal BETWEEN ? AND ? ORDER BY tanggal DESC";

        try (Connection c = KoneksiMySql.getKoneksi(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setDate(1, startDate); 
            s.setDate(2, endDate); 

            try (ResultSet r = s.executeQuery()) {
                while (r.next()) {
                    Object[] o = new Object[4];
                    o[0] = r.getString("id_transaksi");
                    o[1] = r.getString("nominal");
                    o[2] = r.getString("tanggal");
                    o[3] = r.getString("metode_pembayaran");
                    model_pengeluaran.addRow(o);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while processing the data.");
        }
    }
    
    public void generate_report() {
        PDDocument document = new PDDocument();
        int pendapatan = 0, pengeluaran = 0, laba_bersih = 0;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String formattedDate = currentDate.format(formatter);
        
        java.util.Date sdate = laporan_starting_date.getDate(); 
        if (sdate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
            this.selected_starting_date = sdf.format(sdate); 
        }
        java.util.Date edate = laporan_ending_date.getDate(); 
        if (edate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
            this.selected_ending_date = sdf.format(edate);  
        }
        
        java.sql.Date sqlStartDate = new java.sql.Date(sdate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(edate.getTime());
        
        if (this.selected_starting_date.compareTo(this.selected_ending_date) > 0) {
            JOptionPane.showMessageDialog(this, "An error occurred while processing the data.");
        }
        
        String sql = "SELECT nominal, jenis_pembayaran FROM transaksi WHERE tanggal BETWEEN ? AND ?";

        try (Connection c = KoneksiMySql.getKoneksi(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setDate(1, sqlStartDate); 
            s.setDate(2, sqlEndDate); 

            try (ResultSet r = s.executeQuery()) {
                while (r.next()) {
                    if ("pemasukan".equals(r.getString("jenis_pembayaran"))) {
                        laba_bersih += r.getInt("nominal");
                        pendapatan += r.getInt("nominal");
                    }
                    else {
                        laba_bersih -= r.getInt("nominal");
                        pengeluaran += r.getInt("nominal");
                    } 
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while processing the data.");
        }
        
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        String formatted_pendapatan = decimalFormat.format(pendapatan), formatted_pengeluaran = decimalFormat.format(pengeluaran), formatted_laba_bersih = decimalFormat.format(laba_bersih);

        try {
            PDPage page = new PDPage();
            document.addPage(page);

            PDFont italic_font = PDType0Font.load(document, new File("/home/johntor81/Documents/NetBeans/CashReport/src/cashreport/fonts/FreeSerifItalic.ttf"));
            PDFont bold_italic_font = PDType0Font.load(document, new File("/home/johntor81/Documents/NetBeans/CashReport/src/cashreport/fonts/FreeSerifBoldItalic.ttf"));
            PDFont bold_font = PDType0Font.load(document, new File("/home/johntor81/Documents/NetBeans/CashReport/src/cashreport/fonts/FreeSerifBold.ttf"));
            PDFont regular_font = PDType0Font.load(document, new File("/home/johntor81/Documents/NetBeans/CashReport/src/cashreport/fonts/FreeSerif.ttf"));
            
            float page_width = page.getMediaBox().getWidth(), title_width,  centerX;
            String text;

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Start the text
                contentStream.beginText();
                contentStream.setFont(bold_font, 20);
                text = "PT PENIKMAT BABI GULING";
                title_width = bold_font.getStringWidth(text)/1000*20;
                centerX = (page_width - title_width)/2;
                contentStream.newLineAtOffset(centerX, 750);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "Jl. Pan Kenzie 15K No. 20, Ngawi Utara";
                title_width = regular_font.getStringWidth(text)/1000*12;
                centerX = (page_width - title_width)/2;
                contentStream.newLineAtOffset(centerX, 730);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "Telp/Fax: 021 88994433, email: pan_kenzie@enak.banget";
                title_width = regular_font.getStringWidth(text)/1000*12;
                centerX = (page_width - title_width)/2;
                contentStream.newLineAtOffset(centerX, 710);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.moveTo(50, 700);
                contentStream.lineTo(550, 700);
                contentStream.stroke();
                contentStream.moveTo(50, 701);
                contentStream.lineTo(550, 701);
                contentStream.stroke();
                contentStream.moveTo(50, 702);
                contentStream.lineTo(550, 702);
                contentStream.stroke();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "Ngawi Utara, " + formattedDate;
                title_width = regular_font.getStringWidth(text)/1000*12;
                centerX = (page_width - title_width - 65);
                contentStream.newLineAtOffset(centerX, 680);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "No              : " + this.selected_starting_date + "/" + this.selected_ending_date;
                contentStream.newLineAtOffset(55, 665);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "Lampiran   : -";
                contentStream.newLineAtOffset(55, 650);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "Hal             : Arus Kas Per Tanggal " + this.selected_starting_date + " Hingga " + this.selected_ending_date;
                contentStream.newLineAtOffset(55, 635);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "Dengan hormat, ";
                contentStream.newLineAtOffset(55, 550);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "Sehubungan dengan kegiatan operasional PT PENIKMAT BABI GULING pada tanggal " + this.selected_starting_date + ",";
                contentStream.newLineAtOffset(55, 530);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "hingga tanggal " + this.selected_ending_date + ". Menyampaikan laporan keuangan sebagai berikut:";
                contentStream.newLineAtOffset(55, 510);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(bold_font, 12);
                text = "Pendapatan   : Rp. " + formatted_pendapatan;
                contentStream.newLineAtOffset(90, 465);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(bold_font, 12);
                text = "Pengeluaran  : Rp. " + formatted_pengeluaran;
                contentStream.newLineAtOffset(90, 445);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(bold_font, 12);
                text = "———————————————————  -";
                contentStream.newLineAtOffset(90, 433);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(bold_font, 12);
                text = "Laba Bersih  : Rp. " + formatted_laba_bersih;
                contentStream.newLineAtOffset(90, 420);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "Kami berharap laporan keuangan ini, dapat memberikan gambaran yang jelas tentang kondisi keuangan";
                contentStream.newLineAtOffset(55, 370);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "perusahaan PT PENIKMAT BABI GULING pada tanggal " + this.selected_starting_date + " hingga " + this.selected_ending_date + ". Apabila ada";
                contentStream.newLineAtOffset(55, 350);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "pertanyaan atau klarifikasi lebih lanjut, kamis siap memberikan penjelasan lebih detail.";
                contentStream.newLineAtOffset(55, 330);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "Demikian lapooran keuangan ini kami sampaikan. Terima kasih atas perhatian dan kerjasamanya.";
                contentStream.newLineAtOffset(55, 290);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "Hormat kami,";
                title_width = regular_font.getStringWidth(text)/1000*12;
                centerX = (page_width - title_width - 65);
                contentStream.newLineAtOffset(centerX, 230);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "PT PENIKMAT BABI GULING";
                title_width = regular_font.getStringWidth(text)/1000*12;
                centerX = (page_width - title_width - 65);
                contentStream.newLineAtOffset(centerX, 210);
                contentStream.showText(text);
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(regular_font, 12);
                text = "Manajemen";
                title_width = regular_font.getStringWidth(text)/1000*12;
                centerX = (page_width - title_width - 65);
                contentStream.newLineAtOffset(centerX, 110);
                contentStream.showText(text);
                contentStream.endText();
                
            }

            // Save the document
            String outputPath = "/home/johntor81/Documents/NetBeans/CashReport/src/cashreport/pdf/laporan_"+this.selected_starting_date + "_" + this.selected_ending_date+".pdf";
            document.save(outputPath);
            System.out.println("PDF created at: " + outputPath);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Creates new form Keuangan
     */
    public Keuangan() {
        initComponents();
        
        laba_bersih_text_field.setEditable(false);
        pendapatan_text_field.setEditable(false);
        pengeluaran_text_field.setEditable(false);
        
        model_pendapatan = new DefaultTableModel();
        model_pengeluaran = new DefaultTableModel();

        tabel_keuangan_pendapatan.setModel(model_pendapatan);
        tabel_keuangan_pengeluaran.setModel(model_pengeluaran);

        TableRowSorter<TableModel> sorter_pendapatan = new TableRowSorter<>(model_pendapatan);
        TableRowSorter<TableModel> sorter_pengeluaran = new TableRowSorter<>(model_pengeluaran);
        tabel_keuangan_pendapatan.setRowSorter(sorter_pendapatan);
        tabel_keuangan_pengeluaran.setRowSorter(sorter_pengeluaran);

        model_pendapatan.addColumn("id_transaksi");
        model_pendapatan.addColumn("nominal");
        model_pendapatan.addColumn("tanggal");
        model_pendapatan.addColumn("metode_pembayaran");

        model_pengeluaran.addColumn("id_transaksi");
        model_pengeluaran.addColumn("nominal");
        model_pengeluaran.addColumn("tanggal");
        model_pengeluaran.addColumn("metode_pembayaran");
        
        sorter_pendapatan.setComparator(0, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
            }
        });
        sorter_pengeluaran.setComparator(0, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
            }
        });        
        sorter_pendapatan.setComparator(1, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
            }
        });
        sorter_pengeluaran.setComparator(1, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
            }
        });
        
        this.update_dashboard();
        this.update_pendapatan();
        this.update_pengeluaran();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        side_bar = new javax.swing.JPanel();
        dashboard_button_side = new javax.swing.JButton();
        laporan_button_edit = new javax.swing.JButton();
        pendapatan_button_edit = new javax.swing.JButton();
        pengeluaran_button_edit = new javax.swing.JButton();
        Parent = new javax.swing.JPanel();
        main_content_dashboard = new javax.swing.JPanel();
        header_dashboard = new javax.swing.JLabel();
        laba_bersih_panel_dashboard = new javax.swing.JPanel();
        laba_bersih_text_field = new javax.swing.JTextField();
        laba_bersih_label = new javax.swing.JLabel();
        laba_bersih_icon = new javax.swing.JLabel();
        pendapatan_panel_dashboard = new javax.swing.JPanel();
        pendapatan_text_field = new javax.swing.JTextField();
        pendapatan_label = new javax.swing.JLabel();
        pendapatan_icon = new javax.swing.JLabel();
        pengeluaran_panel_dashboard = new javax.swing.JPanel();
        pengeluaran_text_field = new javax.swing.JTextField();
        pengeluaran_label = new javax.swing.JLabel();
        pengeluaran_icon = new javax.swing.JLabel();
        line_chart_dashboard = new javax.swing.JPanel();
        header2 = new javax.swing.JLabel();
        pie_chart_dashboard = new javax.swing.JPanel();
        header1 = new javax.swing.JLabel();
        main_content_laporan = new javax.swing.JPanel();
        header_laporan = new javax.swing.JLabel();
        create_laporan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        laporan_starting_date = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        laporan_ending_date = new com.toedter.calendar.JDateChooser();
        main_content_pengeluaran = new javax.swing.JPanel();
        header_pengeluaran = new javax.swing.JLabel();
        scroll_tabel_keuangan_pengeluaran = new javax.swing.JScrollPane();
        tabel_keuangan_pengeluaran = new javax.swing.JTable();
        pengeluaran_year = new javax.swing.JTextField();
        pengeluaran_month = new javax.swing.JComboBox<>();
        main_content_pendapatan = new javax.swing.JPanel();
        header_pendapatan = new javax.swing.JLabel();
        scroll_tabel_keuangan_pendapatan = new javax.swing.JScrollPane();
        tabel_keuangan_pendapatan = new javax.swing.JTable();
        pendapatan_month = new javax.swing.JComboBox<>();
        pendapatan_year = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        side_bar.setBackground(new java.awt.Color(89, 131, 146));
        side_bar.setToolTipText("");

        dashboard_button_side.setBackground(new java.awt.Color(239, 246, 224));
        dashboard_button_side.setFont(new java.awt.Font("JetBrainsMono NF ExtraBold", 1, 18)); // NOI18N
        dashboard_button_side.setText("DASHBOARD");
        dashboard_button_side.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboard_button_sideActionPerformed(evt);
            }
        });

        laporan_button_edit.setBackground(new java.awt.Color(150, 149, 146));
        laporan_button_edit.setFont(new java.awt.Font("JetBrainsMono NF ExtraBold", 1, 18)); // NOI18N
        laporan_button_edit.setText("LAPORAN");
        laporan_button_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporan_button_editActionPerformed(evt);
            }
        });

        pendapatan_button_edit.setBackground(new java.awt.Color(150, 149, 146));
        pendapatan_button_edit.setFont(new java.awt.Font("JetBrainsMono NF ExtraBold", 1, 18)); // NOI18N
        pendapatan_button_edit.setText("PENDAPATAN");
        pendapatan_button_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pendapatan_button_editActionPerformed(evt);
            }
        });

        pengeluaran_button_edit.setBackground(new java.awt.Color(150, 149, 146));
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

        Parent.setPreferredSize(new java.awt.Dimension(806, 503));
        Parent.setRequestFocusEnabled(false);
        Parent.setLayout(new java.awt.CardLayout());

        main_content_dashboard.setBackground(new java.awt.Color(255, 255, 255));
        main_content_dashboard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(174, 195, 176), 4));

        header_dashboard.setFont(new java.awt.Font("JetBrainsMono NFM", 1, 36)); // NOI18N
        header_dashboard.setText("Dashboard Keuangan");

        laba_bersih_panel_dashboard.setBackground(new java.awt.Color(255, 209, 102));
        laba_bersih_panel_dashboard.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        laba_bersih_text_field.setBackground(new java.awt.Color(255, 209, 102));
        laba_bersih_text_field.setFont(new java.awt.Font("JetBrainsMono NFP ExtraLight", 0, 17)); // NOI18N
        laba_bersih_text_field.setText("Rp. -");
        laba_bersih_text_field.setBorder(null);
        laba_bersih_text_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laba_bersih_text_fieldActionPerformed(evt);
            }
        });

        laba_bersih_label.setFont(new java.awt.Font("JetBrainsMono NFM Thin", 1, 17)); // NOI18N
        laba_bersih_label.setText("Laba-Bersih");

        laba_bersih_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cashreport/img/cashflow.png"))); // NOI18N

        javax.swing.GroupLayout laba_bersih_panel_dashboardLayout = new javax.swing.GroupLayout(laba_bersih_panel_dashboard);
        laba_bersih_panel_dashboard.setLayout(laba_bersih_panel_dashboardLayout);
        laba_bersih_panel_dashboardLayout.setHorizontalGroup(
            laba_bersih_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, laba_bersih_panel_dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(laba_bersih_icon)
                .addGap(18, 18, 18)
                .addGroup(laba_bersih_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(laba_bersih_panel_dashboardLayout.createSequentialGroup()
                        .addGap(0, 42, Short.MAX_VALUE)
                        .addComponent(laba_bersih_label))
                    .addComponent(laba_bersih_text_field))
                .addGap(19, 19, 19))
        );
        laba_bersih_panel_dashboardLayout.setVerticalGroup(
            laba_bersih_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(laba_bersih_panel_dashboardLayout.createSequentialGroup()
                .addGroup(laba_bersih_panel_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(laba_bersih_panel_dashboardLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(laba_bersih_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(laba_bersih_text_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(laba_bersih_panel_dashboardLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(laba_bersih_icon)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pendapatan_panel_dashboard.setBackground(new java.awt.Color(6, 214, 160));
        pendapatan_panel_dashboard.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        pendapatan_text_field.setBackground(new java.awt.Color(6, 214, 160));
        pendapatan_text_field.setFont(new java.awt.Font("JetBrainsMono NFP ExtraLight", 0, 17)); // NOI18N
        pendapatan_text_field.setText("Rp. -");
        pendapatan_text_field.setBorder(null);

        pendapatan_label.setFont(new java.awt.Font("JetBrainsMono NFM Thin", 1, 17)); // NOI18N
        pendapatan_label.setText("Pendapatan");

        pendapatan_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cashreport/img/cashin.png"))); // NOI18N

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
        pengeluaran_panel_dashboard.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        pengeluaran_text_field.setBackground(new java.awt.Color(239, 71, 111));
        pengeluaran_text_field.setFont(new java.awt.Font("JetBrainsMono NFP ExtraLight", 0, 17)); // NOI18N
        pengeluaran_text_field.setText("Rp. -");
        pengeluaran_text_field.setBorder(null);

        pengeluaran_label.setFont(new java.awt.Font("JetBrainsMono NFM Thin", 1, 17)); // NOI18N
        pengeluaran_label.setText("Pengeluaran");

        pengeluaran_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cashreport/img/cashout.png"))); // NOI18N

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

        line_chart_dashboard.setBackground(new java.awt.Color(255, 255, 255));
        line_chart_dashboard.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        header2.setFont(new java.awt.Font("JetBrainsMono NFM Light", 0, 18)); // NOI18N
        header2.setText("Grafik Keuangan");

        javax.swing.GroupLayout line_chart_dashboardLayout = new javax.swing.GroupLayout(line_chart_dashboard);
        line_chart_dashboard.setLayout(line_chart_dashboardLayout);
        line_chart_dashboardLayout.setHorizontalGroup(
            line_chart_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(line_chart_dashboardLayout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(header2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        line_chart_dashboardLayout.setVerticalGroup(
            line_chart_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(line_chart_dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header2)
                .addContainerGap(250, Short.MAX_VALUE))
        );

        pie_chart_dashboard.setBackground(new java.awt.Color(255, 255, 255));
        pie_chart_dashboard.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        header1.setFont(new java.awt.Font("JetBrainsMono NFM Light", 0, 18)); // NOI18N
        header1.setText("Diagram");

        javax.swing.GroupLayout pie_chart_dashboardLayout = new javax.swing.GroupLayout(pie_chart_dashboard);
        pie_chart_dashboard.setLayout(pie_chart_dashboardLayout);
        pie_chart_dashboardLayout.setHorizontalGroup(
            pie_chart_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pie_chart_dashboardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(header1)
                .addGap(70, 70, 70))
        );
        pie_chart_dashboardLayout.setVerticalGroup(
            pie_chart_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pie_chart_dashboardLayout.createSequentialGroup()
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
                            .addComponent(line_chart_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(main_content_dashboardLayout.createSequentialGroup()
                                .addComponent(laba_bersih_panel_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pendapatan_panel_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(main_content_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pengeluaran_panel_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pie_chart_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(header_dashboard))
                .addGap(0, 134, Short.MAX_VALUE))
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
                    .addComponent(laba_bersih_panel_dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(main_content_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pie_chart_dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(line_chart_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        Parent.add(main_content_dashboard, "card5");

        main_content_laporan.setBackground(new java.awt.Color(255, 255, 255));
        main_content_laporan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(174, 195, 176), 4));

        header_laporan.setFont(new java.awt.Font("JetBrainsMono NFM", 1, 36)); // NOI18N
        header_laporan.setText("Laporan Keuangan");

        create_laporan.setFont(new java.awt.Font("JetBrainsMono NFM", 1, 17)); // NOI18N
        create_laporan.setText("Generate .pdf");
        create_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_laporanActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("JetBrainsMono NF", 1, 17)); // NOI18N
        jLabel1.setText("Select Starting Date:");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        laporan_starting_date.setBackground(new java.awt.Color(89, 131, 146));
        laporan_starting_date.setDateFormatString("yyyy-MM-dd");

        jLabel2.setFont(new java.awt.Font("JetBrainsMono NF", 1, 17)); // NOI18N
        jLabel2.setText("Select  Ending  Date:");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        laporan_ending_date.setBackground(new java.awt.Color(89, 131, 146));
        laporan_ending_date.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout main_content_laporanLayout = new javax.swing.GroupLayout(main_content_laporan);
        main_content_laporan.setLayout(main_content_laporanLayout);
        main_content_laporanLayout.setHorizontalGroup(
            main_content_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_content_laporanLayout.createSequentialGroup()
                .addGroup(main_content_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(create_laporan)
                    .addGroup(main_content_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(main_content_laporanLayout.createSequentialGroup()
                            .addGap(146, 146, 146)
                            .addComponent(header_laporan))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_content_laporanLayout.createSequentialGroup()
                            .addGap(146, 146, 146)
                            .addGroup(main_content_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(69, 69, 69)
                            .addGroup(main_content_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(laporan_starting_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(laporan_ending_date, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(179, Short.MAX_VALUE))
        );
        main_content_laporanLayout.setVerticalGroup(
            main_content_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_content_laporanLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(header_laporan)
                .addGap(105, 105, 105)
                .addGroup(main_content_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(laporan_starting_date, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(30, 30, 30)
                .addGroup(main_content_laporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(laporan_ending_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99)
                .addComponent(create_laporan)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        Parent.add(main_content_laporan, "card5");

        main_content_pengeluaran.setBackground(new java.awt.Color(255, 255, 255));
        main_content_pengeluaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(174, 195, 176), 4));

        header_pengeluaran.setFont(new java.awt.Font("JetBrainsMono NFM", 1, 36)); // NOI18N
        header_pengeluaran.setText("Pengeluaran Keuangan");

        scroll_tabel_keuangan_pengeluaran.setBackground(new java.awt.Color(255, 255, 255));
        scroll_tabel_keuangan_pengeluaran.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabel_keuangan_pengeluaran.setFont(new java.awt.Font("Helvetica", 0, 17)); // NOI18N
        tabel_keuangan_pengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scroll_tabel_keuangan_pengeluaran.setViewportView(tabel_keuangan_pengeluaran);

        pengeluaran_year.setFont(new java.awt.Font("JetBrainsMono NF SemiBold", 0, 14)); // NOI18N
        pengeluaran_year.setText("2024");
        pengeluaran_year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pengeluaran_yearActionPerformed(evt);
            }
        });

        pengeluaran_month.setFont(new java.awt.Font("JetBrainsMono NFM SemiBold", 0, 14)); // NOI18N
        pengeluaran_month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        pengeluaran_month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pengeluaran_monthActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout main_content_pengeluaranLayout = new javax.swing.GroupLayout(main_content_pengeluaran);
        main_content_pengeluaran.setLayout(main_content_pengeluaranLayout);
        main_content_pengeluaranLayout.setHorizontalGroup(
            main_content_pengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_content_pengeluaranLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(main_content_pengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll_tabel_keuangan_pengeluaran, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_content_pengeluaranLayout.createSequentialGroup()
                        .addGroup(main_content_pengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(main_content_pengeluaranLayout.createSequentialGroup()
                                .addComponent(header_pengeluaran)
                                .addGap(0, 276, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_content_pengeluaranLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(pengeluaran_month, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pengeluaran_year, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48))
        );
        main_content_pengeluaranLayout.setVerticalGroup(
            main_content_pengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_content_pengeluaranLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(header_pengeluaran)
                .addGap(18, 18, 18)
                .addGroup(main_content_pengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pengeluaran_month)
                    .addComponent(pengeluaran_year, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_tabel_keuangan_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        Parent.add(main_content_pengeluaran, "card5");

        main_content_pendapatan.setBackground(new java.awt.Color(255, 255, 255));
        main_content_pendapatan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(174, 195, 176), 4));

        header_pendapatan.setFont(new java.awt.Font("JetBrainsMono NFM", 1, 36)); // NOI18N
        header_pendapatan.setText("Pendapatan Keuangan");

        scroll_tabel_keuangan_pendapatan.setBackground(new java.awt.Color(204, 204, 204));
        scroll_tabel_keuangan_pendapatan.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabel_keuangan_pendapatan.setFont(new java.awt.Font("Helvetica", 0, 17)); // NOI18N
        tabel_keuangan_pendapatan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scroll_tabel_keuangan_pendapatan.setViewportView(tabel_keuangan_pendapatan);

        pendapatan_month.setFont(new java.awt.Font("JetBrainsMono NFM SemiBold", 0, 14)); // NOI18N
        pendapatan_month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" }));
        pendapatan_month.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pendapatan_monthActionPerformed(evt);
            }
        });

        pendapatan_year.setFont(new java.awt.Font("JetBrainsMono NFM SemiBold", 0, 14)); // NOI18N
        pendapatan_year.setText("2024");
        pendapatan_year.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pendapatan_yearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout main_content_pendapatanLayout = new javax.swing.GroupLayout(main_content_pendapatan);
        main_content_pendapatan.setLayout(main_content_pendapatanLayout);
        main_content_pendapatanLayout.setHorizontalGroup(
            main_content_pendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_content_pendapatanLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(main_content_pendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(main_content_pendapatanLayout.createSequentialGroup()
                        .addComponent(header_pendapatan)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_content_pendapatanLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pendapatan_month, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pendapatan_year, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scroll_tabel_keuangan_pendapatan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 812, Short.MAX_VALUE))
                .addGap(48, 48, 48))
        );
        main_content_pendapatanLayout.setVerticalGroup(
            main_content_pendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_content_pendapatanLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(header_pendapatan)
                .addGap(18, 18, 18)
                .addGroup(main_content_pendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pendapatan_month)
                    .addComponent(pendapatan_year, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll_tabel_keuangan_pendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        Parent.add(main_content_pendapatan, "card4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(side_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Parent, javax.swing.GroupLayout.PREFERRED_SIZE, 906, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(side_bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Parent, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboard_button_sideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboard_button_sideActionPerformed
        Parent.removeAll();
        Parent.add(main_content_dashboard);
        Parent.repaint();
        Parent.revalidate();
        laporan_button_edit.setBackground(new java.awt.Color(150, 149, 146));
        pendapatan_button_edit.setBackground(new java.awt.Color(150, 149, 146));
        pengeluaran_button_edit.setBackground(new java.awt.Color(150, 149, 146));
        dashboard_button_side.setBackground(new java.awt.Color(239, 246, 224));
        this.update_dashboard();
    }//GEN-LAST:event_dashboard_button_sideActionPerformed

    private void laporan_button_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporan_button_editActionPerformed
        Parent.removeAll();
        Parent.add(main_content_laporan);
        Parent.repaint();
        Parent.revalidate();
        laporan_button_edit.setBackground(new java.awt.Color(239, 246, 224));
        pendapatan_button_edit.setBackground(new java.awt.Color(150, 149, 146));
        pengeluaran_button_edit.setBackground(new java.awt.Color(150, 149, 146));
        dashboard_button_side.setBackground(new java.awt.Color(150, 149, 146));
    }//GEN-LAST:event_laporan_button_editActionPerformed

    private void pendapatan_button_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pendapatan_button_editActionPerformed
        Parent.removeAll();
        Parent.add(main_content_pendapatan);
        Parent.repaint();
        Parent.revalidate();
        laporan_button_edit.setBackground(new java.awt.Color(150, 149, 146));
        pendapatan_button_edit.setBackground(new java.awt.Color(239, 246, 224));
        pengeluaran_button_edit.setBackground(new java.awt.Color(150, 149, 146));
        dashboard_button_side.setBackground(new java.awt.Color(150, 149, 146));
        this.update_pendapatan();
    }//GEN-LAST:event_pendapatan_button_editActionPerformed

    private void pengeluaran_button_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pengeluaran_button_editActionPerformed
        Parent.removeAll();
        Parent.add(main_content_pengeluaran);
        Parent.repaint();
        Parent.revalidate();
        laporan_button_edit.setBackground(new java.awt.Color(150, 149, 146));
        pendapatan_button_edit.setBackground(new java.awt.Color(150, 149, 146));
        pengeluaran_button_edit.setBackground(new java.awt.Color(239, 246, 224));
        dashboard_button_side.setBackground(new java.awt.Color(150, 149, 146));
        this.update_pengeluaran();
    }//GEN-LAST:event_pengeluaran_button_editActionPerformed

    private void laba_bersih_text_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laba_bersih_text_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_laba_bersih_text_fieldActionPerformed

    private void create_laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_laporanActionPerformed
        this.generate_report();
    }//GEN-LAST:event_create_laporanActionPerformed

    private void pendapatan_monthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pendapatan_monthActionPerformed
        this.update_pendapatan();
    }//GEN-LAST:event_pendapatan_monthActionPerformed

    private void pendapatan_yearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pendapatan_yearActionPerformed
        this.update_pendapatan();
    }//GEN-LAST:event_pendapatan_yearActionPerformed

    private void pengeluaran_monthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pengeluaran_monthActionPerformed
        this.update_pengeluaran();
    }//GEN-LAST:event_pengeluaran_monthActionPerformed

    private void pengeluaran_yearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pengeluaran_yearActionPerformed
        this.update_pengeluaran();
    }//GEN-LAST:event_pengeluaran_yearActionPerformed

    
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
            java.util.logging.Logger.getLogger(Keuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Keuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Keuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Keuangan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Keuangan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Parent;
    private javax.swing.JButton create_laporan;
    private javax.swing.JButton dashboard_button_side;
    private javax.swing.JLabel header1;
    private javax.swing.JLabel header2;
    private javax.swing.JLabel header_dashboard;
    private javax.swing.JLabel header_laporan;
    private javax.swing.JLabel header_pendapatan;
    private javax.swing.JLabel header_pengeluaran;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JLabel laba_bersih_icon;
    private javax.swing.JLabel laba_bersih_label;
    private javax.swing.JPanel laba_bersih_panel_dashboard;
    private javax.swing.JTextField laba_bersih_text_field;
    private javax.swing.JButton laporan_button_edit;
    private com.toedter.calendar.JDateChooser laporan_ending_date;
    private com.toedter.calendar.JDateChooser laporan_starting_date;
    private javax.swing.JPanel line_chart_dashboard;
    private javax.swing.JPanel main_content_dashboard;
    private javax.swing.JPanel main_content_laporan;
    private javax.swing.JPanel main_content_pendapatan;
    private javax.swing.JPanel main_content_pengeluaran;
    private javax.swing.JButton pendapatan_button_edit;
    private javax.swing.JLabel pendapatan_icon;
    private javax.swing.JLabel pendapatan_label;
    private javax.swing.JComboBox<String> pendapatan_month;
    private javax.swing.JPanel pendapatan_panel_dashboard;
    private javax.swing.JTextField pendapatan_text_field;
    private javax.swing.JTextField pendapatan_year;
    private javax.swing.JButton pengeluaran_button_edit;
    private javax.swing.JLabel pengeluaran_icon;
    private javax.swing.JLabel pengeluaran_label;
    private javax.swing.JComboBox<String> pengeluaran_month;
    private javax.swing.JPanel pengeluaran_panel_dashboard;
    private javax.swing.JTextField pengeluaran_text_field;
    private javax.swing.JTextField pengeluaran_year;
    private javax.swing.JPanel pie_chart_dashboard;
    private javax.swing.JScrollPane scroll_tabel_keuangan_pendapatan;
    private javax.swing.JScrollPane scroll_tabel_keuangan_pengeluaran;
    private javax.swing.JPanel side_bar;
    private javax.swing.JTable tabel_keuangan_pendapatan;
    private javax.swing.JTable tabel_keuangan_pengeluaran;
    // End of variables declaration//GEN-END:variables
}
