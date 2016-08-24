package by.asoi.course.view;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import by.asoi.course.manager.ParserManager;


/*
 * ��� ����� ����� �����, ������� ������������ ����� �� ������. �� ���� ���
 * ������� View �� ������ MVC. �� ���� ������� ��� �� �������� � ������������,
 * �� ��� �� ������. �.�. ��� ����������� ����������� � �����������.
 */

public class LyricsFrame extends JFrame implements ActionListener, ILyrics {

	private static final long serialVersionUID = 1L;
	
	// ������� ������
	private static final String GET = "GET";
    private static final String EXIT = "EXIT";
    
    // ��������� ��������
    // ������ �������
    private static final int PAD = 10;
    // ������ �����
    private static final int W_L = 100;
    // ������ ���� ��� �����
    private static final int W_T = 325;
    // ������ ������
    private static final int W_B = 157;
    // ������ �������� - ����� ��� ����
    private static final int H_B = 25;
    
    // ������ �����
    private static final int F_W = 350;
    // ������
    private static final int F_H = 600;
    
    // ���� ��������� �������
    private ParserManager manager = new ParserManager(this);
    // ���� ��� ����� �����������
    private final JTextField artist = new JTextField();
    // ���� ��� ����� �������� �����
    private final JTextField song = new JTextField();
    // ���� ��� ������ ������ �����
    private final JTextPane lyricsTextPane = new JTextPane();
    
 // � ������������ �� ������� ������ ��������
    public LyricsFrame() {
    	// �������� ����������
    	super("Lyrics Finder");
    	// ������� �������� ����������, ���������� ���������� ����������������
    	setLayout(null);
        
    	// ������������� ������ �� �����
        buildButtons();
        
        // ������������� ���� �� �����
        buildFields();
        
        // �������� ���������� ��������
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int vert = sSize.height;
        int hor  = sSize.width;
        
        // ���������� ���������� ����� �� ������ ������
        setBounds(hor / 2 - F_W / 2, vert / 2 - F_H / 2, F_W, F_H);
        // ��� �������� ����� ����������� ������ ����������
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ��������� ��������� ��������
        setResizable(false);
    }
            
    // ��������� ����� � ���� ����� �� �����
    private void buildFields() {
        // ����� ����� � ���� ��� �����������
        JLabel lblArtist = new JLabel("�����������:");
        // ������������ ������ � ������ �������
        lblArtist.setHorizontalAlignment(SwingConstants.LEFT);
        // ���������� ���������� �����
        lblArtist.setBounds(new Rectangle(PAD, 0 * H_B + PAD, W_L, H_B));
        // ������ ����� �� �����
        add(lblArtist);
        // ���������� ���������� ����
        artist.setBounds(new Rectangle(PAD, 1 * H_B + PAD, W_T, H_B));
        // ������ "������" ��� ����
        artist.setBorder(BorderFactory.createEtchedBorder());
        // ������ ���� �� �����
        add(artist);
 
        // ����� ����� � ���� ��� �������� �����
        JLabel lblSong = new JLabel("�������� �����:");
        lblSong.setHorizontalAlignment(SwingConstants.LEFT);
        lblSong.setBounds(new Rectangle(PAD, 2 * H_B + PAD, W_L, H_B));
        add(lblSong);
        song.setBounds(new Rectangle(PAD, 3 * H_B + PAD, W_T, H_B));
        song.setBorder(BorderFactory.createEtchedBorder());
        add(song);
        
        // ����� ����� � ���� ��� ������ �����
        JLabel lblLyrics = new JLabel("����� �����:");
        lblLyrics.setHorizontalAlignment(SwingConstants.LEFT);
        lblLyrics.setBounds(new Rectangle(PAD, 4 * H_B + PAD, W_L, H_B));
        add(lblLyrics);
        JScrollPane scroll = new JScrollPane(lyricsTextPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportView(lyricsTextPane);
        scroll.setBounds(new Rectangle(PAD, 5 * H_B + PAD, W_T, 13 * H_B));
        lyricsTextPane.setEditable(false);
        add(scroll);
        
        // ����� ���������� ����������
        JLabel lblAdvise = new JLabel("<html>���� �� �� ������ ���������� ���������� �����, �������� �� �� ����� ������� �������� ���������� ��� �����������</html>");
        lblAdvise.setHorizontalAlignment(SwingConstants.LEFT);
        lblAdvise.setBounds(new Rectangle(PAD, 18 * H_B + PAD, W_T, 2 * H_B));
        add(lblAdvise);
    }
    
    // ��������� ������ �� �����
    private void buildButtons() {
    	// ������ ������
        JButton btnGet = new JButton("��������");
        // ����� �������, ������� ����� ��������� ��� �������
        btnGet.setActionCommand(GET);
        // ����� ���������� ������� �������, �.�. ��� ����������, ������� � ���� ���� �����
        btnGet.addActionListener(this);
        // ����� ������������ �� �����
        btnGet.setBounds(new Rectangle(PAD, 21 * H_B + PAD, W_B, H_B));
        // ��������� �� �����
        add(btnGet);
 
        JButton btnExit = new JButton("�����");
        btnExit.setActionCommand(EXIT);
        btnExit.addActionListener(this);
        btnExit.setBounds(new Rectangle(W_B + 2 * PAD, 21 * H_B + PAD, W_B, H_B));
        add(btnExit);
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
		// �������� ������� - ActionCommand
        String action = ae.getActionCommand();
        // � ����������� �� ������� ��������� ��������
        switch (action) {
            // ��������� ������ �����
            case GET:
                getLyrics();
                break;
            // �����
            case EXIT:
                System.exit(0);
                break;
        }
	}

	private void getLyrics() {
		// ��������� ���� ����� �� ������ ��������
		// ���� ����� - ������� ��������������
        if (artist.getText().isEmpty()) {
        	JOptionPane.showMessageDialog(this, "������� ��� ����������� ��� �������� ������", "ERROR", JOptionPane.ERROR_MESSAGE);
        	return;
        } else if (song.getText().isEmpty()) {
        	JOptionPane.showMessageDialog(this, "������� �������� ����� ��� �����", "ERROR", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        // ������� ���� ������ ������ �����
        lyricsTextPane.setText("");
        
        //������� ��������� �������� � �����������
        manager.setArtist(artist.getText());
        manager.setSong(song.getText());
        
        // ������ ������ �� ��������� ������ �����
        manager.getLyrics();
    }

	@Override
	public void setLyrics(String lyrics) {
		// ������� ����� ����� �� �����
		lyricsTextPane.setText(lyrics);
	}
}
