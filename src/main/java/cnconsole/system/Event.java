package cnconsole.system;

public class Event<T> {
	public Event(String name, T value) {
		this.name = name;
		this.value = value;
	}

	public Event() {
	}

	public String name;
	public T value;
}