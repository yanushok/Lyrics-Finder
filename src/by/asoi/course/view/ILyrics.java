package by.asoi.course.view;

/*
 * Интерфейс, определяющий метод вывода текста песни на форме
 * нужен для того, чтобы вывести текст песни после того как он
 * загрузиться параллельном в потоке
 */

public interface ILyrics {
	public void setLyrics(String lyrics);
}
