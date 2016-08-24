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
 * Это класс самой формы, которую пользователь видит на экране. По сути это
 * элемент View из модели MVC. Но этот элемент так же совмещён с контроллером,
 * из той же модели. Т.е. это объединение контроллера и отображения.
 */

public class LyricsFrame extends JFrame implements ActionListener, ILyrics {

	private static final long serialVersionUID = 1L;
	
	// Команды кнопок
	private static final String GET = "GET";
    private static final String EXIT = "EXIT";
    
    // Константы отступов
    // Размер отступа
    private static final int PAD = 10;
    // Ширина метки
    private static final int W_L = 100;
    // Ширина поля для ввода
    private static final int W_T = 325;
    // Ширина кнопки
    private static final int W_B = 157;
    // высота элемента - общая для всех
    private static final int H_B = 25;
    
    // Ширина формы
    private static final int F_W = 350;
    // Высота
    private static final int F_H = 600;
    
    // Поле менеджера парсера
    private ParserManager manager = new ParserManager(this);
    // Поле для ввода исполнителя
    private final JTextField artist = new JTextField();
    // Поле для ввода названия песни
    private final JTextField song = new JTextField();
    // Поле для вывода текста песни
    private final JTextPane lyricsTextPane = new JTextPane();
    
 // В конструкторе мы создаем нужные элементы
    public LyricsFrame() {
    	// Название приложения
    	super("Lyrics Finder");
    	// Убираем менеджер компоновки, используем абсолютное позиционирование
    	setLayout(null);
        
    	// Устанавливаем кнопки на форму
        buildButtons();
        
        // Устанавливаем поля на форму
        buildFields();
        
        // Получаем разрешение монитора
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        int vert = sSize.height;
        int hor  = sSize.width;
        
        // выставляем координаты формы по центру экрана
        setBounds(hor / 2 - F_W / 2, vert / 2 - F_H / 2, F_W, F_H);
        // При закрытии формы заканчиваем работу приложения
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Запрещаем изменение размеров
        setResizable(false);
    }
            
    // Размещаем метки и поля ввода на форме
    private void buildFields() {
        // Набор метки и поля для исполнителя
        JLabel lblArtist = new JLabel("Исполнитель:");
        // Выравнивание текста с правой стороны
        lblArtist.setHorizontalAlignment(SwingConstants.LEFT);
        // Выставляем координаты метки
        lblArtist.setBounds(new Rectangle(PAD, 0 * H_B + PAD, W_L, H_B));
        // Кладем метку на форму
        add(lblArtist);
        // Выставляем координаты поля
        artist.setBounds(new Rectangle(PAD, 1 * H_B + PAD, W_T, H_B));
        // Делаем "бордюр" для поля
        artist.setBorder(BorderFactory.createEtchedBorder());
        // Кладем поле на форму
        add(artist);
 
        // Набор метки и поля для названия песни
        JLabel lblSong = new JLabel("Название песни:");
        lblSong.setHorizontalAlignment(SwingConstants.LEFT);
        lblSong.setBounds(new Rectangle(PAD, 2 * H_B + PAD, W_L, H_B));
        add(lblSong);
        song.setBounds(new Rectangle(PAD, 3 * H_B + PAD, W_T, H_B));
        song.setBorder(BorderFactory.createEtchedBorder());
        add(song);
        
        // Набор метки и поля для текста песни
        JLabel lblLyrics = new JLabel("Текст песни:");
        lblLyrics.setHorizontalAlignment(SwingConstants.LEFT);
        lblLyrics.setBounds(new Rectangle(PAD, 4 * H_B + PAD, W_L, H_B));
        add(lblLyrics);
        JScrollPane scroll = new JScrollPane(lyricsTextPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setViewportView(lyricsTextPane);
        scroll.setBounds(new Rectangle(PAD, 5 * H_B + PAD, W_T, 13 * H_B));
        lyricsTextPane.setEditable(false);
        add(scroll);
        
        // Вывод справочной информации
        JLabel lblAdvise = new JLabel("<html>Если вы не видите результата длительное время, возможно вы не верно указали название композиции или исполнителя</html>");
        lblAdvise.setHorizontalAlignment(SwingConstants.LEFT);
        lblAdvise.setBounds(new Rectangle(PAD, 18 * H_B + PAD, W_T, 2 * H_B));
        add(lblAdvise);
    }
    
    // Размещаем кнопки на форме
    private void buildButtons() {
    	// Создаём кнопку
        JButton btnGet = new JButton("Получить");
        // Задаём команду, которую будем проверять при нажатии
        btnGet.setActionCommand(GET);
        // Задаём обработчик события нажатия, т.е. наш контроллер, который и есть этот класс
        btnGet.addActionListener(this);
        // Задаём рамположение на форме
        btnGet.setBounds(new Rectangle(PAD, 21 * H_B + PAD, W_B, H_B));
        // Добавляем на форму
        add(btnGet);
 
        JButton btnExit = new JButton("Выход");
        btnExit.setActionCommand(EXIT);
        btnExit.addActionListener(this);
        btnExit.setBounds(new Rectangle(W_B + 2 * PAD, 21 * H_B + PAD, W_B, H_B));
        add(btnExit);
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
		// Получаем команду - ActionCommand
        String action = ae.getActionCommand();
        // В зависимости от команды выполняем действия
        switch (action) {
            // Получение текста песни
            case GET:
                getLyrics();
                break;
            // Выход
            case EXIT:
                System.exit(0);
                break;
        }
	}

	private void getLyrics() {
		// Проверяем поля ввода на пустое значение
		// Если пусто - выводим предупреждение
        if (artist.getText().isEmpty()) {
        	JOptionPane.showMessageDialog(this, "Введите имя исполнителя или название группы", "ERROR", JOptionPane.ERROR_MESSAGE);
        	return;
        } else if (song.getText().isEmpty()) {
        	JOptionPane.showMessageDialog(this, "Введите название песни или трека", "ERROR", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        // Очищаем поле вывода текста песни
        lyricsTextPane.setText("");
        
        //Передаём менеджеру название и исполнителя
        manager.setArtist(artist.getText());
        manager.setSong(song.getText());
        
        // Делаем запрос на получение текста песни
        manager.getLyrics();
    }

	@Override
	public void setLyrics(String lyrics) {
		// Выводим текст песни на экран
		lyricsTextPane.setText(lyrics);
	}
}
