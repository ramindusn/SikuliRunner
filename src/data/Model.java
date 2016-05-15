package data;


public abstract class Model {
	protected String name;
	protected Status status;

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean equals(Object object) {
		if (object != null && object instanceof Model && ((Model) object).getName().equals(name)) {
			return true;
		} else {
			return false;
		}
	}
}
