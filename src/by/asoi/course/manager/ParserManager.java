package by.asoi.course.manager;

import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;

import by.asoi.course.factory.ParserFactory;
import by.asoi.course.parser.AbstractParser;
import by.asoi.course.view.ILyrics;

/*
 * Класс менеджер
 * является частью контроллера шаблона MVC
 * Связующее звено между парсером(ами) и графическим интерфейсом
 */

public class ParserManager implements Runnable {
	// Поле парсера
	private AbstractParser parser;
	// Фабрика парсеров
	private ParserFactory factory;
	// Ссылка на интерфейс
	private ILyrics frame;
	
	// Поля артиста и названия песни
	private String artist;
	private String song;
	
	// Конструктор, принимает ссылку на интерфейс
	public ParserManager(ILyrics frame) {
		factory = new ParserFactory();
		this.frame = frame;
	}
	
	// установка артиста и нахвания песни
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public void setSong(String song) {
		this.song = song;
	}
	
	// Получение текста песни
	// Метод запускает на выполнение поток
	// Нужно для того, чтобы не зависал графический интерфейс
	// И вся работа выполнялась параллельно
	public void getLyrics() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	// То, что происходит в потоке
	@Override
	public void run() {
		// Задаём свой уникальный ключ в системе MusixMatch
		// И подключаемся
		String apiKey = "aeda754a55418a343f5f70fa392cce22";
		MusixMatch musixMatch = new MusixMatch(apiKey);
		
		// Получаем трек из базы данных системы MusixMatch
		Track track = null;
		try {
			track = musixMatch.getMatchingTrack(song, artist);
		} catch (MusixMatchException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		// Получаем информацию о треке
		TrackData data = track.getTrack();
		// Создаём парсер
		parser = factory.createParser("MUSIXMATCH");
		// Парсим песню
		String lyrics = parser.getLyrics(data.getTrackShareURL());
		// устанавливаем текст песни на форму
		frame.setLyrics(lyrics);
	}
}
