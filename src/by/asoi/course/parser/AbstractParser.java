package by.asoi.course.parser;

/*
 * Абстрактный класс AbstractParser.
 * Задаёт правила для всех наследуемых от него классов-парсеров.
 * Определяет минимальный набор методов, необходимых для работы
 * любого парсера HTML.
 * 
 * Метод getLyrics не имеет входных параметров, и возвращает текст песни.
 * Поле grabber содержит граббер, необходимый для получения входного потока
 * данных.
 * Поле url хранит адрес запрашиваемого ресурса.
 * Методы getGrabber и setGrabber необходимы для задания и получения граббера.
 */

public abstract class AbstractParser {
	// Поле граббера
	protected IGrabber grabber;
		
	// конструктор, устанавливаем граббер
	public AbstractParser(IGrabber grabber) {
		setGrabber(grabber);
	}
	
	// возвращаем граббер
	protected IGrabber getGrabber() {
        return grabber;
    }
 
	// установка граббера
	protected void setGrabber(IGrabber grabber) {
        this.grabber = grabber;
    }
	
	// метод получения текста песни
	public abstract String getLyrics(String uri);
}
