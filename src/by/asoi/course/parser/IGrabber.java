package by.asoi.course.parser;

import java.io.IOException;
import java.io.InputStream;
/*
 * Интерфейс Grabber
 * Интерфейс объявляет метод, который должен возвращаться входной поток
 * полученного по заданному URI исходного текста страницы.
 * 
 * Другими словами, класс, реализующий данный интерфейс "грабит" данные
 * и возвращает поток данных, из которого будет прочитано содержимое
 */
public interface IGrabber {
	// Получем входной поток для чтения исходного кода страницы
	public InputStream getPage(String uri) throws IOException;
}
