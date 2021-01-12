package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.naming.SizeLimitExceededException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Frame extends JFrame {
    private JPanel panel1, panel_dostepu_1, panel_dostepu_2, panel_dostepu_3, panel_dodawania_rekordu, panel_przyciskow, panel_z_tabelka, panel_alter, panel_add_table, panelestetyczny, panelZlista, panelzprzyciskami;
    private JButton alter_button, but3, but4, delete_button, insertbutton, droptablebutton, alter_accept, button_add_table, button_create_table, button_add_primary_key, invisible, invisible2, invisible3, invisible4;
    private static JLabel succes, passwordLabel, label;
    private static JButton button;
    private static JPasswordField passwordText;
    private static JTextField userText;
    JTextField nazwaTabeli;
    private static JTable table;
    private String current_table = "USERS";
    private JComboBox<String> lista_tabel;
    private JScrollPane panelskrolowania;
    private ArrayList<JTextField> helper, listaZPolamiTekstowymi, listaZArgumentamiTypowDanych;
    private String insertowy = "";
    private String[] combobox;
    private String input;
    private ArrayList<JComboBox<String>> listaZComboBoxami;
    private Database baza = new Database();


    public Frame() throws SQLException {
        createLoginPanel();
        addPanel();
        setTitle("Aplikacja BADA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setVisible(true);
    }

    public void createLoginPanel() throws SQLException {
        createPanelAddTable();
        initiatePanels();
        createAdminPanel();
        createPanelDodawaniaRekordu();
        createPanelDeleteReplace();

        label = new JLabel("Login");
        label.setBounds(262, 347, 80, 30);
        label.setFont(new Font("Verdana", Font.PLAIN, 23));

        succes = new JLabel("");
        succes.setBounds(10, 110, 300, 23);

        passwordLabel = new JLabel("Hasło");
        passwordLabel.setFont(new Font("Verdana", Font.PLAIN, 23));
        passwordLabel.setBounds(260, 397, 150, 30);

        button = new JButton("Zaloguj się");
        button.setBounds(450, 450, 200, 40);
        buttonFormatting(button);
        button.addActionListener(new addButtonListener());

        passwordText = new JPasswordField();
        passwordText.setBounds(350, 400, 300, 30);
        passwordText.setFont(new Font("Verdana", Font.PLAIN, 23));

        userText = new JTextField(20);
        userText.setBounds(350, 350, 300, 30);
        userText.setFont(new Font("Verdana", Font.PLAIN, 23));

    }

    private void initiatePanels() {

        panel1 = new JPanel();
        panel1.setLayout(null);

        panelestetyczny = new JPanel();
        panelestetyczny.setLayout(new BorderLayout());


        panel_add_table = new JPanel();
        panel_add_table.setLayout(null);


        panel_alter = new JPanel();
        panel_alter.setLayout(null);

        panelZlista = new JPanel();
        panelZlista.setLayout(new BorderLayout());

        panel_dostepu_1 = new JPanel();
        panel_dostepu_1.setLayout(null);

        panelzprzyciskami = new JPanel();
        panelzprzyciskami.setLayout(new BorderLayout());

        panel_dostepu_2 = new JPanel();
        panel_dostepu_2.setLayout(null);

        panel_przyciskow = new JPanel();
        GridLayout gridLayout = new GridLayout(6, 3);
        gridLayout.setVgap(23);
        gridLayout.setHgap(23);
        panel_przyciskow.setLayout(gridLayout);

        panel_z_tabelka = new JPanel();
        panel_z_tabelka.setLayout(new GridLayout(1, 1));

        panel_dostepu_3 = new JPanel();
        panel_dostepu_3.setLayout(new BorderLayout());

        panel_dodawania_rekordu = new JPanel();
        panel_dodawania_rekordu.setLayout(null);
    }

    public void createAdminPanel() throws SQLException {


        alter_button = new JButton("Zmień rekord");
        alter_button.addActionListener(new addButtonListener1());

        combobox = baza.getTables().toArray(new String[0]);
        lista_tabel = new JComboBox<>(combobox);
        ((JLabel) lista_tabel.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        lista_tabel.setBackground(Color.lightGray);
        lista_tabel.setForeground(Color.black);
        lista_tabel.addActionListener(new addJComboBoxListener());

        button_add_primary_key = new JButton("Dodaj/Zmień klucz główny");
        button_add_primary_key.addActionListener(new addButtonAddPrimaryKeyListener());

        delete_button = new JButton("Usuń rekord");
        delete_button.addActionListener(new addDeleteButtonListener());

        droptablebutton = new JButton("Usuń tabelę");
        droptablebutton.addActionListener(new addDropTableButtonListener());

        current_table = (String) lista_tabel.getSelectedItem();
        panelskrolowania = new JScrollPane();
        table = baza.getTable(current_table);
        panelskrolowania.setViewportView(table);

        panel_z_tabelka.add(panelskrolowania);
        panel_dostepu_3.add(panel_z_tabelka, BorderLayout.NORTH);


        but3 = new JButton("Dodaj rekord");
        but3.addActionListener(new addButtonListener2());


        buttonFormatting(but3);
        buttonFormatting(button_add_primary_key);
        buttonFormatting(delete_button);
        buttonFormatting(droptablebutton);
        buttonFormatting(button_add_table);
        buttonFormatting(alter_button);

        panel_przyciskow.add(but3);
        panel_przyciskow.add(alter_button);
        panel_przyciskow.add(delete_button);
        panel_przyciskow.add(button_add_table);
        panel_przyciskow.add(droptablebutton);
        panel_przyciskow.add(button_add_primary_key);


        invisible = new JButton();
        invisible.setPreferredSize(new Dimension(50, 100));
        invisible.setEnabled(false);

        invisible2 = new JButton();
        invisible2.setPreferredSize(new Dimension(50, 100));
        invisible2.setEnabled(false);


        invisible3 = new JButton();
        invisible3.setEnabled(false);
        invisible3.setPreferredSize(new Dimension(10, 100));


        invisible4 = new JButton();
        invisible4.setEnabled(false);
        invisible4.setPreferredSize(new Dimension(10, 50));


        lista_tabel.setPreferredSize(new Dimension(460, 50));
        lista_tabel.setFont(new Font("Verdana", Font.PLAIN, 23));


        panelzprzyciskami.add(panel_przyciskow, BorderLayout.CENTER);


        panelZlista.add(lista_tabel, BorderLayout.NORTH);
        panelZlista.add(invisible3, BorderLayout.CENTER);


        panelestetyczny.add(panelZlista, BorderLayout.EAST);
        panelestetyczny.add(panelzprzyciskami, BorderLayout.CENTER);


        panel_dostepu_3.add(panelestetyczny, BorderLayout.CENTER);


    }

    public void buttonFormatting(JButton button) {
        button.setFont(new Font("Verdana", Font.PLAIN, 23));
        button.setBackground(Color.lightGray);
        button.setForeground(Color.black);

    }

    public void createPanelDodawaniaRekordu() throws SQLException {

        but4 = new JButton("Powrót");
        but4.setBounds(25, 800, 250, 50);
        buttonFormatting(but4);
        but4.addActionListener(new addButtonListener3());

        insertbutton = new JButton("Insert");
        insertbutton.addActionListener(new addInsertButtonListener());
        insertbutton.setBounds(600, 800, 250, 50);


    }

    public void createPanelDeleteReplace() {

        alter_accept = new JButton("Zatwierdź");
        alter_accept.addActionListener(new addAlterAcceptButtonListener());
        alter_accept.setBounds(610, 800, 250, 50);
        buttonFormatting(alter_accept);

    }

    public void createPanelAddTable() {
        button_add_table = new JButton("Dodaj tabelę");
        button_add_table.addActionListener(new addButtonAddTableListener());

        button_create_table = new JButton("Stwórz");
        button_create_table.setBounds(600, 800, 250, 50);
        button_create_table.addActionListener(new addButtonCreateTableListener());
        buttonFormatting(button_create_table);

    }

    private void addPanel() {

        panel1.setBackground(new Color(255, 190, 134, 59));
        panel1.add(succes);
        panel1.add(label);
        panel1.add(button);
        panel1.add(passwordText);
        panel1.add(passwordLabel);
        panel1.add(userText);
        add(panel1);

    }

    class addButtonAddTableListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            listaZPolamiTekstowymi = new ArrayList<>();
            listaZArgumentamiTypowDanych = new ArrayList<>();
            listaZComboBoxami = new ArrayList<>();

            String[] data_types = {"VARCHAR(x)", "CHAR(x)", "INTEGER(x)", "BOOLEAN", "TIME(x)", "FLOAT(x)"};

            String resp = "Wpisz liczbę kolumn";
            String input = JOptionPane.showInputDialog(null, resp);
            if (input == null) {
                getContentPane().removeAll();
                getContentPane().add(panel_dostepu_3);
                repaint();
                printAll(getGraphics());
            } else {
                for (int i = 0; i < Integer.parseInt(input); i++) {

                    JComboBox<String> poleTekstoweZNazwaTypuDanych = new JComboBox<>(data_types);
                    int y = 80 + (700 / Integer.parseInt(input)) * i;
                    poleTekstoweZNazwaTypuDanych.setBounds(325, y, 200, 30);
                    listaZComboBoxami.add(poleTekstoweZNazwaTypuDanych);

                    JTextField textField = new JTextField();
                    textField.setBounds(100, y, 200, 30);
                    textField.setFont(new Font("Verdana", Font.PLAIN, 17));
                    textField.setHorizontalAlignment(JTextField.CENTER);

                    listaZPolamiTekstowymi.add(textField);

                    JTextField textFieldZArgumentamiTypowDanych = new JTextField();
                    textFieldZArgumentamiTypowDanych.setFont(new Font("Verdana", Font.PLAIN, 17));
                    textFieldZArgumentamiTypowDanych.setBounds(550, y, 200, 30);
                    textFieldZArgumentamiTypowDanych.setHorizontalAlignment(JTextField.CENTER);
                    listaZArgumentamiTypowDanych.add(textFieldZArgumentamiTypowDanych);

                    int j = i + 1;
                    JLabel nr = new JLabel("" + j + ".");
                    nr.setBounds(80, y, 100, 30);
                    panel_add_table.add(nr);

                    panel_add_table.add(textFieldZArgumentamiTypowDanych);
                    panel_add_table.add(poleTekstoweZNazwaTypuDanych);
                    panel_add_table.add(textField);
                }

                JLabel nrkolumny = new JLabel("Nazwa kolumny");
                nrkolumny.setBounds(100, 48, 100, 30);
                panel_add_table.add(nrkolumny);

                JLabel nrx = new JLabel("Wartość x");
                nrx.setBounds(550, 48, 100, 30);
                panel_add_table.add(nrx);

                JLabel typzmiennej = new JLabel("Typ zmiennej");
                typzmiennej.setBounds(325, 48, 100, 30);
                panel_add_table.add(typzmiennej);

                nazwaTabeli = new JTextField();
                nazwaTabeli.setHorizontalAlignment(JTextField.CENTER);
                nazwaTabeli.setFont(new Font("Verdana", Font.PLAIN, 17));
                nazwaTabeli.setBounds(320, 800, 240, 30);
                panel_add_table.add(nazwaTabeli);

                JLabel nazwatabelilabel = new JLabel("Nazwa tabeli");
                nazwatabelilabel.setBounds(386, 765, 100, 30);
                nazwatabelilabel.setHorizontalAlignment(JLabel.CENTER);
                panel_add_table.add(nazwatabelilabel);


                panel_add_table.add(but4);
                panel_add_table.add(button_create_table);
                getContentPane().removeAll();
                getContentPane().add(panel_add_table);
                repaint();
                printAll(getGraphics());

            }

        }
    }

    class addButtonCreateTableListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            String[] choices = new String[listaZPolamiTekstowymi.size()];
            for (int i = 0; i < listaZPolamiTekstowymi.size(); i++) {
                choices[i] = listaZPolamiTekstowymi.get(i).getText();
            }
            input = (String) JOptionPane.showInputDialog(null, "Wybierz klucz główny..",
                    "Wybór klucza głównego", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

            try {
                baza.createTable(listaZPolamiTekstowymi, listaZArgumentamiTypowDanych, listaZComboBoxami, nazwaTabeli);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                baza.addPrimaryKey(nazwaTabeli.getText(), input);
            } catch (SQLException e) {
                e.printStackTrace();
            }


            current_table = nazwaTabeli.getText();
            getContentPane().removeAll();
            getContentPane().add(panel_add_table);
            repaint();
            printAll(getGraphics());

        }
    }


    class addButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            try {
                int poziom_dostepu = baza.validate(userText.getText(), baza.md5Hash(passwordText.getText()));

                switch (poziom_dostepu) {
                    case 0:
                        JOptionPane.showMessageDialog(null, "Błędny login/hasło, lub nieprzypisany poziom dostępu");
                        break;
                    case 1:
                        getContentPane().removeAll();
                        getContentPane().add(panel_dostepu_1);
                        repaint();
                        printAll(getGraphics());
                        break;
                    case 2:
                        getContentPane().removeAll();
                        getContentPane().add(panel_dostepu_2);
                        repaint();
                        printAll(getGraphics());
                        break;
                    case 3:
                        getContentPane().removeAll();
                        getContentPane().add(panel_dostepu_3);
                        repaint();
                        printAll(getGraphics());
                        break;

                }
            } catch (SQLException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws SQLException {
        Frame frame = new Frame();

    }

    class addButtonListener1 implements ActionListener {
        public void actionPerformed(ActionEvent ae) {

            try {
                if (!baza.getPrimaryKey(current_table).isEmpty()) {

                    ArrayList<String> helper1 = new ArrayList<>();
                    {
                        String[] choices = new String[0];
                        try {
                            choices = baza.select(baza.getPrimaryKey(current_table), current_table, "").toArray(new String[0]);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        input = (String) JOptionPane.showInputDialog(null, "Wybierz wartość klucza głównego rekordu do zmiany",
                                "Wybór zmienianego rekordu", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
                        if ((input != null) && (input.length() > 0)) {


                            try {
                                helper1 = baza.select("*", current_table, baza.getPrimaryKey(current_table) + "=" + "'" + input + "'");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            try {
                                table = baza.getTable(current_table);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            panelskrolowania.setViewportView(table);

                            getContentPane().removeAll();
                            panel_alter.removeAll();
                            panel_alter.add(but4);
                            panel_alter.add(alter_accept);
                            helper = new ArrayList<>();

                            try {
                                for (int i = 0; i < baza.getColumnsAndTypes(current_table).size(); i++) {
                                    insertowy += baza.getColumnsAndTypes(current_table).keySet().toArray()[i].toString() + ",";
                                    JLabel label = new JLabel();
                                    JLabel label1 = new JLabel();
                                    JLabel label2 = new JLabel();
                                    label2.setText(baza.getColumns(current_table).get(i) + " : " + helper1.get(i));
                                    label.setText(baza.getColumnsAndTypes(current_table).keySet().toArray()[i].toString().toUpperCase());
                                    label1.setText(baza.getColumnsAndTypes(current_table).values().toArray()[i].toString());
                                    label1.setBounds(100, 60 + (700 / baza.getColumnsAndTypes(current_table).size()) * i, 200, 23);
                                    label.setBounds(100, 45 + (700 / baza.getColumnsAndTypes(current_table).size()) * i, 200, 23);
                                    label2.setBounds(435, 123 + 50 * i, 450, 35);
                                    label2.setFont(new Font("Verdana", Font.PLAIN, 23));
                                    panel_alter.add(label);
                                    panel_alter.add(label1);
                                    panel_alter.add(label2);
                                    JTextField pole_tekstowe = new JTextField();
                                    pole_tekstowe.setBounds(100, 81 + (700 / baza.getColumnsAndTypes(current_table).size()) * i, 100, 23);
                                    helper.add(pole_tekstowe);
                                    panel_alter.add(pole_tekstowe);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            JLabel label3 = new JLabel("<html>" + "<div style='text-align: center;'>" + "<B>" + "Aktualne wartości w bazie danych:" + "<B>" + "<div>" + "</html>");
                            label3.setForeground(Color.RED);
                            label3.setBounds(350, 10, 500, 100);
                            label3.setFont(new Font("Verdana", Font.PLAIN, 30));

                            JLabel label4 = new JLabel("<html>" + "<div style='text-align: center;'>" + "<B>" + "UWAGA: Jeśli nie chcesz zmieniać danej kolumny, to pozostaw puste pole." + "<B>" + "<div>" + "</html>");
                            label4.setFont(new Font("Verdana", Font.PLAIN, 13));
                            label4.setBounds(320, 795, 250, 50);
                            label4.setHorizontalAlignment(JTextField.CENTER);
                            panel_alter.add(label3);
                            panel_alter.add(label4);
                            insertowy = insertowy.substring(0, insertowy.length() - 1);
                            getContentPane().add(panel_alter);
                            repaint();
                            printAll(getGraphics());

                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Wybrana tabela nie zawiera unikalnego identyfikatora, aby przejść dalej, należy go ustawić.", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    class addButtonListener2 implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            getContentPane().removeAll();
            panel_dodawania_rekordu.removeAll();
            panel_dodawania_rekordu.add(but4);
            buttonFormatting(insertbutton);
            panel_dodawania_rekordu.add(insertbutton);
            JLabel label4 = new JLabel("<html>" + "<div style='text-align: center;'>" + "<B>" + "UWAGA: Jeśli nie chcesz ustawiać wartości danej kolumny, to pozostaw puste pole." + "<B>" + "<div>" + "</html>");
            label4.setFont(new Font("Verdana", Font.PLAIN, 13));
            label4.setBounds(320, 800, 250, 50);
            label4.setHorizontalAlignment(JTextField.CENTER);
            panel_dodawania_rekordu.add(label4);
            helper = new ArrayList<>();
            try {
                for (int i = 0; i < baza.getColumnsAndTypes(current_table).size(); i++) {
                    insertowy += baza.getColumnsAndTypes(current_table).keySet().toArray()[i].toString() + ",";
                    JLabel label = new JLabel();
                    if (baza.isNullable(baza.getColumnsAndTypes(current_table).keySet().toArray()[i].toString())) {
                        label.setText(baza.getColumnsAndTypes(current_table).keySet().toArray()[i].toString().toUpperCase() + ", typ - " + baza.getColumnsAndTypes(current_table).values().toArray()[i].toString());
                    } else {
                        label.setText(baza.getColumnsAndTypes(current_table).keySet().toArray()[i].toString().toUpperCase() + ", typ - " + baza.getColumnsAndTypes(current_table).values().toArray()[i].toString() + ", NOT NULL");
                    }
                    label.setBounds(450, 75 + (750 / baza.getColumnsAndTypes(current_table).size()) * i, 1000, 30);
                    label.setFont(new Font("Verdana", Font.PLAIN, 15));
                    panel_dodawania_rekordu.add(label);
                    int j = i + 1;
                    JLabel nr = new JLabel("" + j + ".");
                    nr.setBounds(110, 75 + (750 / baza.getColumnsAndTypes(current_table).size()) * i, 100, 30);
                    panel_dodawania_rekordu.add(nr);
                    JTextField pole_tekstowe = new JTextField();
                    pole_tekstowe.setBounds(130, 75 + (750 / baza.getColumnsAndTypes(current_table).size()) * i, 300, 30);
                    pole_tekstowe.setFont(new Font("Verdana", Font.PLAIN, 15));
                    pole_tekstowe.setHorizontalAlignment(JTextField.CENTER);
                    helper.add(pole_tekstowe);
                    panel_dodawania_rekordu.add(pole_tekstowe);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            insertowy = insertowy.substring(0, insertowy.length() - 1);
            getContentPane().add(panel_dodawania_rekordu);
            repaint();
            printAll(getGraphics());//Extort print all content

        }
    }

    class addButtonListener3 implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            panel_add_table.removeAll();
            try {
                combobox = baza.getTables().toArray(new String[0]);
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(combobox);
                lista_tabel.setModel(model);
                lista_tabel.setSelectedItem(current_table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            insertowy = "";
            try {
                table = baza.getTable(current_table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            panelskrolowania.setViewportView(table);
            getContentPane().removeAll();
            getContentPane().add(panel_dostepu_3);
            repaint();
            printAll(getGraphics());

        }
    }

    class addJComboBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            current_table = (String) lista_tabel.getSelectedItem();
            getContentPane().removeAll();
            try {
                table = baza.getTable(current_table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            panelskrolowania.setViewportView(table);
            getContentPane().add(panel_dostepu_3);
            repaint();
            printAll(getGraphics());

        }
    }

    class addInsertButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            String wartosci = "";
            for (JTextField jTextField : helper) {
                wartosci += "'" + jTextField.getText() + "'" + ",";
            }
            wartosci = wartosci.substring(0, wartosci.length() - 1);

            String[] wartosciowy = wartosci.replace("'", "").split(",");
            wartosci = "";
            String[] haslowy = insertowy.split(",");
            for (int i = 0; i < haslowy.length; i++) {
                if (haslowy[i].equals("password")) {
                    try {
                        wartosciowy[i] = baza.md5Hash(wartosciowy[i]);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
                wartosci += "'" + wartosciowy[i] + "'" + ",";
            }
            wartosci = wartosci.substring(0, wartosci.length() - 1);


            System.out.println(Arrays.toString(wartosciowy));
            try {
                baza.insert(current_table + "(" + insertowy + ")", "(" + wartosci + ")");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            getContentPane().removeAll();
            getContentPane().add(panel_dodawania_rekordu);
            repaint();
            printAll(getGraphics());

        }
    }

    class addDropTableButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            if (JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz usunąć tabelę " + current_table + "?", "Ostrzeżenie!",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    baza.dropTable(current_table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    combobox = baza.getTables().toArray(new String[0]);
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(combobox);
                    lista_tabel.setModel(model);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                getContentPane().removeAll();
                getContentPane().add(panel_dostepu_3);
                repaint();
                printAll(getGraphics());
            }

            current_table = "USERS";
            try {
                table = baza.getTable(current_table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            panelskrolowania.setViewportView(table);


            lista_tabel.setSelectedItem("users");

            getContentPane().removeAll();
            getContentPane().add(panel_dostepu_3);
            repaint();
            printAll(getGraphics());


        }
    }

    class addDeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            try {
                if (!baza.getPrimaryKey(current_table).isEmpty()) {

                    String[] choices = new String[0];
                    try {
                        choices = baza.select(baza.getPrimaryKey(current_table), current_table, "").toArray(new String[0]);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    input = (String) JOptionPane.showInputDialog(null, "Wybierz id rekordu do usunięcia..",
                            "Wybór usuwanego rekordu", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

                    try {
                        baza.deleteRecord(current_table, baza.getPrimaryKey(current_table) + "=" + "'" + input + "'");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        table = baza.getTable(current_table);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    panelskrolowania.setViewportView(table);
                    getContentPane().removeAll();
                    getContentPane().add(panel_dostepu_3);
                    repaint();
                    printAll(getGraphics());

                } else {
                    JOptionPane.showMessageDialog(null, "Wybrana tabela nie zawiera unikalnego identyfikatora, aby przejść dalej, należy go ustawić.", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    class addAlterAcceptButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {

            String wartosci = "";
            for (int i = 0; i < helper.size(); i++) {
                wartosci += "'" + helper.get(i).getText() + "'" + ",";
            }
            wartosci = wartosci.substring(0, wartosci.length() - 1);

            String[] pomocniczy = wartosci.replace("'", "").split(",");
            String[] kolumny = insertowy.split(",");

            for (int i = 0; i < pomocniczy.length; i++) {
                try {
                    if (!pomocniczy[i].equals("")) {
                        if (kolumny[i].equals("password")) {
                            baza.updateRecord(current_table, "'" + baza.md5Hash(pomocniczy[i]) + "'", kolumny[i], baza.getPrimaryKey(current_table) + "=" + "'" + input + "'");
                        } else
                            baza.updateRecord(current_table, "'" + pomocniczy[i] + "'", kolumny[i], baza.getPrimaryKey(current_table) + "=" + "'" + input + "'");
                    }
                } catch (SQLException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }

            getContentPane().removeAll();
            getContentPane().add(panel_alter);
            repaint();
            printAll(getGraphics());


        }

    }


    class addButtonAddPrimaryKeyListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            try {
                String[] choices;
                choices = baza.getColumns(current_table).toArray(new String[0]);

                input = (String) JOptionPane.showInputDialog(null, "Wybierz klucz główny..",
                        "Wybór klucza głównego", JOptionPane.QUESTION_MESSAGE, null, choices, null);

                if (input != null && baza.getPrimaryKey(current_table).isEmpty()) {
                    baza.addPrimaryKey(current_table, input);
                    JOptionPane.showConfirmDialog(null, "Klucz główny został pomyślnie zmieniony", "Sukces", JOptionPane.DEFAULT_OPTION);
                } else {
                    if (input != null && JOptionPane.showConfirmDialog(null, "Tabela " + "'" + current_table + "'" + " zawiera już klucz główny " + "'" + baza.getPrimaryKey(current_table) + "'" + ". Czy chcesz go usunąć i ustawić nowy?", "Ostrzeżenie!",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        baza.dropPrimaryKey(current_table);
                        baza.addPrimaryKey(current_table, input);

                        JOptionPane.showConfirmDialog(null, "Klucz główny został pomyślnie zmieniony", "Sukces", JOptionPane.DEFAULT_OPTION);
                    }
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {
                table = baza.getTable(current_table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            panelskrolowania.setViewportView(table);
            panel_dodawania_rekordu.removeAll();
            getContentPane().removeAll();
            getContentPane().add(panel_dostepu_3);
            repaint();
            printAll(getGraphics());

        }
    }

}